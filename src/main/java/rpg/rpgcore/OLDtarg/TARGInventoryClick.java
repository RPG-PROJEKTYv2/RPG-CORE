package rpg.rpgcore.OLDtarg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TARGInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TARGInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void targInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Lista Targow")) {
            e.setCancelled(true);

            if (clickedItem.getType() == Material.AIR) {
                return;
            }

            if (clickedItem.getType().equals(Material.BARRIER) || clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            int task;

            if (rpgcore.getTargManager().isInTaskMap(playerUUID)) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getTargManager().getPlayerTaskId(playerUUID));
                rpgcore.getTargManager().removePlayerFromTaskMap(playerUUID);
            }

            int page = Integer.parseInt(Utils.removeColor(clickedInventoryTitle.replace("Lista Targow ", "").replace("    ", "").trim()));


            if (clickedItem.getItemMeta().getDisplayName().contains("nastepna")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(page + 1));
                return;
            }

            if (clickedItem.getItemMeta().getDisplayName().contains("poprzednia")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(page - 1));
                return;
            }

            final UUID playerTargUUID = rpgcore.getUserManager().find(Utils.removeColor(clickedItem.getItemMeta().getDisplayName().trim())).getId();
            if (rpgcore.getTargManager().getPlayerTarg(playerTargUUID).getViewers().size() != 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cKtos aktualnie przeglada ten OLDtarg, sprobuj ponownie za chwile"));
                return;
            }
            player.openInventory(rpgcore.getTargManager().getPlayerTarg(playerTargUUID));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otworzyles OLDtarg gracza &6" + Utils.removeColor(clickedItem.getItemMeta().getDisplayName().trim())));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie OLDtarg zostanie zamkniety po &c60 sekundach"));
            task = rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {
                if (player.getOpenInventory().getTopInventory().getName().contains("Targ gracza") || player.getOpenInventory().getTopInventory().getName().contains("Kup przedmiot ")) {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie OLDtarg zostal zamkniety poniewaz byl otwarty dluzej niz &c60 sekund"));
                }
            }, 1200L);
            rpgcore.getTargManager().putPlayerTask(playerUUID, task);
            return;
        }

        if (clickedInventoryTitle.contains("Targ gracza")) {
            e.setCancelled(true);

            if (clickedItem == null) {
                return;
            }

            if (clickedItem.getType().equals(Material.ARROW) && Utils.removeColor(clickedItem.getItemMeta().getDisplayName()).equals("Powrot")) {
                player.openInventory(rpgcore.getTargManager().openTargGUI(1));
                return;
            }

            if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }

            final String targetName = Utils.removeColor(clickedInventoryTitle).replace("Targ gracza ", "").trim();
            final UUID targetUUID = rpgcore.getUserManager().find(targetName).getId();

            if (player.getName().equals(targetName)) {
                rpgcore.getTargManager().updatePlayerTarg(player, clickedSlot);
                if (clickedItem.getItemMeta().getDisplayName() == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getType()));
                } else {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zdjales ze swojego targu &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName()));
                }

                if (rpgcore.getTargManager().getPlayerTarg(playerUUID).getItem(0) != null) {
                    player.openInventory(rpgcore.getTargManager().getPlayerTarg(playerUUID));
                    return;
                }
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma wystawionych zadnych przedmiotow"));
                return;
            }


            final User playerUser = rpgcore.getUserManager().find(playerUUID);
            final User targetUser = rpgcore.getUserManager().find(targetUUID);

            final double kasaGracza = rpgcore.getUserManager().find(playerUUID).getKasa();
            final double itemCena = rpgcore.getTargManager().getItemCena(clickedItem);

            if (kasaGracza < itemCena) {

                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz wystarczajacych srodkow, zeby kupic ten przedmiot."));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cBrakuje ci &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena - kasaGracza)) + " &2$"));


                return;
            }

            playerUser.setKasa(kasaGracza - itemCena);
            targetUser.setKasa(targetUser.getKasa() + itemCena);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataUser(playerUUID, playerUser);
                rpgcore.getMongoManager().saveDataUser(targetUUID, targetUser);
            });
            rpgcore.getTargManager().givePlayerBoughtItem(player, clickedItem);

            rpgcore.getTargManager().updatePlayerTarg(Bukkit.getPlayer(targetUUID), clickedSlot);
            player.closeInventory();
            for (int i = 0; i < clickedInventory.getViewers().size(); i++) {
                Player p = (Player) clickedInventory.getViewers().get(i);
                p.updateInventory();
            }
            if (clickedItem.getItemMeta().getDisplayName() == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
                Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getType() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie kupiles przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &aod gracza &6" + targetName + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            Bukkit.getPlayer(targetUUID).sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie sprzedales przedmiot &6" + clickedItem.getAmount() + "x " + clickedItem.getItemMeta().getDisplayName() + " &adla gracza &6" + player.getName() + " &aza kwote &6&o" + Utils.spaceNumber(Utils.kasaFormat.format(itemCena)) + " &2$"));
            return;
        }


        if (clickedInventoryTitle.equals(Utils.format("&7&lWystaw przedmiot"))) {
            e.setCancelled(true);
            if (clickedSlot == 4) {
                return;
            }

            if (clickedSlot < 4) {
                String cena = "";
                String podatek = "";
                final ItemStack item = clickedInventory.getItem(4);


                for (String s : item.getItemMeta().getLore()) {
                    if (s.contains("Cena: ")) {
                        cena = Utils.removeColor(s).replace("Cena: ", "").replace(" $", "").trim();
                    }
                    if (s.contains("5% ceny wystawienia")) {
                        podatek = Utils.removeColor(s).replace("W wysokosci 5% ceny wystawienia, czyli ", "").replace(" $", "");
                    }
                }

                final User user = rpgcore.getUserManager().find(playerUUID);
                final double podatekInDouble = Double.parseDouble(podatek.replaceAll(" ", "").trim());
                final double kasa = user.getKasa();

                if (kasa < podatekInDouble) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie masz pieniedzy, zeby wystawic ten przedmiot"));

                    rpgcore.getTargManager().returnPlayerItem(player, item);
                    rpgcore.getTargManager().removeFromWystawia(playerUUID);
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
                    return;
                }
                user.setKasa(kasa - podatekInDouble);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(playerUUID, user));

                final ItemMeta meta = item.getItemMeta();
                final List<String> lore = meta.getLore();
                final int loreSize = lore.size() -1;

                lore.remove(loreSize);
                lore.remove(loreSize-1);
                lore.remove(loreSize-2);
                lore.remove(loreSize-3);
                lore.remove(loreSize-4);
                meta.setLore(Utils.format(lore));
                item.setItemMeta(meta);

                rpgcore.getTargManager().getPlayerTarg(playerUUID).setItem(rpgcore.getTargManager().getPlayerTarg(playerUUID).firstEmpty(), item);
                for (int i = 0; i < rpgcore.getTargManager().getPlayerTarg(playerUUID).getViewers().size(); i++) {
                    Player p = (Player) rpgcore.getTargManager().getPlayerTarg(playerUUID).getViewers().get(i);
                    p.updateInventory();
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wystawiles przedmiot za &6&o" + cena + " &2$"));
                rpgcore.getTargManager().removeFromWystawia(playerUUID);
                player.closeInventory();
                return;
            }

            rpgcore.getTargManager().returnPlayerItem(player, clickedInventory.getItem(4));
            rpgcore.getTargManager().removeFromWystawia(playerUUID);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cAnulowales wystawianie przedmiotu"));

        }

    }
}
