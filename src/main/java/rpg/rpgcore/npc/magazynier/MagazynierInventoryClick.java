package rpg.rpgcore.npc.magazynier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class MagazynierInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Magazynier")) {
            e.setCancelled(true);
            if (clickedSlot == 0) {
                rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
                return;
            }
            if (clickedSlot == 4) {
                rpgcore.getMagazynierNPC().openMagazynierKampania(player);
                return;
            }

            return;
        }

        if (Utils.removeColor(clickedInventoryTitle).equals("Lista Magazynow")) {
            e.setCancelled(true);
            if (clickedItem.getType().equals(Material.CHEST)) {
                player.openInventory(rpgcore.getMagazynierNPC().openMagazyn(playerUUID, (clickedSlot + 1)));
            }
            return;
        }

        if (clickedInventoryTitle.contains("Kampania Magazyniera")) {
            e.setCancelled(true);
            final String[] playerMissionStatus = rpgcore.getMagazynierNPC().getPlayerMagazynyAccess(playerUUID).split(",");

            if (clickedSlot == 0) {
                return;
            }

            if (playerMissionStatus[clickedSlot].equalsIgnoreCase("true")) {
                player.closeInventory();
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &cTa misja zostala juz wykonana!"));
                return;
            }

            final String[] clickedMission = rpgcore.getMagazynierNPC().getMissionLore(clickedSlot - 1).split(";");

            if (!rpgcore.getMagazynierNPC().isInPlayerProgress(playerUUID)) {
                rpgcore.getMagazynierNPC().setPlayerProgress(playerUUID, 0);
            }

            int progress = rpgcore.getMagazynierNPC().getPlayerProgress(playerUUID);

            if (progress < Integer.parseInt(clickedMission[1])) {
                player.closeInventory();
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &cMusisz jeszcze &f" + clickedMission[0].replace("Oddaj", "Oddac").replace("Zabij", "Zabic").replace("Sprzedaj", "Sprzedac") + " &c" + (Integer.parseInt(clickedMission[1]) - progress) + " &6" + clickedMission[2]));
                return;
            }

            ItemStack is;
            if (clickedSlot == 1) {
                int ammount = 0;

                for (ItemStack is2 : player.getInventory().getContents()) {
                    if (is2.getItemMeta().getDisplayName().contains("Skrzynia")) {
                        is = is2;
                        is.setAmount(is2.getAmount());
                        ammount = is2.getAmount();
                        player.getInventory().remove(is);
                    }
                }

                rpgcore.getMagazynierNPC().updatePlayerProgress(playerUUID, ammount);
                player.closeInventory();
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &aPomyslnie oddano &6" + ammount + " &askrzynek"));

                if (progress >= Integer.parseInt(clickedMission[1])) {
                    playerMissionStatus[clickedSlot] = "true";
                    player.sendMessage(Utils.format("&b&lMagazynier &8>> &aGracz &6" + player.getName() + " &awykonal moja &6" + (clickedSlot + 1) + " &amisje!"));

                    final String finalAccess = playerMissionStatus[0] + "," + playerMissionStatus[1] + "," + playerMissionStatus[2] + "," + playerMissionStatus[3] + "," + playerMissionStatus[4];

                    rpgcore.getMagazynierNPC().updatePlayerMagazynAccess(playerUUID, finalAccess);
                    rpgcore.getMagazynierNPC().setPlayerProgress(playerUUID, 0);
                }
                return;
            }

            if (clickedSlot == 4) {
                if (!player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 3),3 )) {
                    int amount = 0;
                    for (ItemStack is2 : player.getInventory().getContents()) {
                        if (is2.getItemMeta().getDisplayName().contains("Bon na magazyn")) {
                            amount = is2.getAmount();
                        }
                    }

                    player.sendMessage(Utils.format("&b&lMagazynier &8>> &cBrakuje Ci jeszcze &6" + (3 - amount) + " &bBon na powiekszenie magazynu"));
                    player.closeInventory();
                    return;
                }
                if (progress >= Integer.parseInt(clickedMission[1])) {
                    player.getInventory().remove(GlobalItem.getItem("I17", 3));
                    playerMissionStatus[clickedSlot] = "true";
                    player.sendMessage(Utils.format("&b&lMagazynier &8>> &aGracz &6" + player.getName() + " &awykonal moja &6" + (clickedSlot + 1) + " &amisje!"));

                    final String finalAccess = playerMissionStatus[0] + "," + playerMissionStatus[1] + "," + playerMissionStatus[2] + "," + playerMissionStatus[3] + "," + playerMissionStatus[4];

                    rpgcore.getMagazynierNPC().updatePlayerMagazynAccess(playerUUID, finalAccess);
                    rpgcore.getMagazynierNPC().setPlayerProgress(playerUUID, 0);
                }
            }

            if (clickedSlot == 5) {
                if (!player.getInventory().containsAtLeast(GlobalItem.getItem("I17", 3),3)) {
                    int amount = 0;
                    for (ItemStack is2 : player.getInventory().getContents()) {
                        if (is2.getItemMeta().getDisplayName().contains("Bon na magazyn")) {
                            amount = is2.getAmount();
                        }
                    }

                    player.sendMessage(Utils.format("&b&lMagazynier &8>> &cBrakuje Ci jeszcze &6" + (3 - amount) + " &bBon na powiekszenie magazynu"));
                    player.closeInventory();
                    return;
                }
                player.getInventory().remove(GlobalItem.getItem("I17", 3));
                playerMissionStatus[clickedSlot] = "true";
                player.sendMessage(Utils.format("&b&lMagazynier &8>> &aGracz &6" + player.getName() + " &awykonal moja &6" + (clickedSlot + 1) + " &amisje!"));

                final String finalAccess = playerMissionStatus[0] + "," + playerMissionStatus[1] + "," + playerMissionStatus[2] + "," + playerMissionStatus[3] + "," + playerMissionStatus[4];

                rpgcore.getMagazynierNPC().updatePlayerMagazynAccess(playerUUID, finalAccess);
                rpgcore.getMagazynierNPC().setPlayerProgress(playerUUID, 0);
            }
            return;
        }

        if (clickedInventoryTitle.contains("Magazyn #")) {
            if (clickedSlot == 49) {
                e.setCancelled(true);
                rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
                return;
            }
            if (clickedSlot >= 45) {
                e.setCancelled(true);
            }
        }
    }

}
