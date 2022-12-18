package rpg.rpgcore.chests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;


public class DropFromChestsListener implements Listener {

    private final RPGCORE rpgcore;

    public DropFromChestsListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (player.getItemInHand().getType().equals(Material.CHEST) || player.getItemInHand().getType().equals(Material.ENDER_CHEST)) {

            final ItemStack playerItem = player.getItemInHand();

            if (playerItem.getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                // ================================ SKRZYNKI INNE ================================
                // WARTOSCIOWY KUFER
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I1", 1));
                        final Items item = rpgcore.getWartosciowykuferManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA KOWALA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I2").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I2", 1));
                        final Items item = rpgcore.getKowalManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA Z SUROWCAMI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I5").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I5", 1));
                        final Items item = rpgcore.getSurowceManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // HELLCASE
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I6").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I6", 1));
                        final Items item = rpgcore.gethellcaseManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        if (is.getType().equals(Material.STORAGE_MINECART)) {
                            is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(1, 10), ChanceHelper.getRandDouble(0.01, 0.05), ChanceHelper.getRandDouble(0.01, 0.05), ChanceHelper.getRandInt(1, 10), "&7Naszyjnik Najemnika");
                        }

                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // TAJEMNICZA SKRZYNIA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I4", 1));
                        final Items item = rpgcore.getTajemniczaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        if (is.equals(BonType.SREDNIE_5.getBon()) || is.equals(BonType.KRYTYK_5.getBon()) || is.equals(BonType.DEFENSYWA_5.getBon())) {
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage(Utils.format("&3Tajemnicza Skrzynia &8>> &7Gracz &6" + player.getName() + " &7znalazl jeden z rzadkich przedmiotow!"));
                            Bukkit.broadcastMessage(" ");
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA ZE ZWIERZAKAMI ZWYKLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I3").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I3", 1));
                        ItemStack item = rpgcore.getZwierzakiManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        player.getInventory().addItem(item);
                        return;
                    }
                }
                // SKRZYNIA ZE ZWIERZAKAMI RARE ITEMSHOP
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GlobalItem.getByName("I22").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GlobalItem.getItem("I22", 1));
                        ItemStack item = null;
                        if (Utils.getTagString(playerItem, "Type").equals("Normal")) {
                            item = rpgcore.getZwierzakiManager().getDrawnItems(player);
                        }
                        if (item == null) {
                            return;
                        }
                        player.getInventory().addItem(item);
                        player.sendMessage(Utils.format("&8[&2+&8] &61x " + item.getItemMeta().getDisplayName()));
                        return;
                    }
                }

                // SKRZYNIA GORNIKA TODO

                // ================================ SKRZYNKI EXPOWISKA ================================

                // Expowisko 1
                //  SKRZYNIA WYGNANCA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I1", 1));
                        final Items item = rpgcore.getWygnaniecManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA NAJEMNIKA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I2").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I2", 1));
                        final Items item = rpgcore.getNajemnikManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                    }
                }

                // Expowisko 2
                // SKRZYNIA WODZA GOBLINOW
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I3", 1));
                        final Items item = rpgcore.getWodzGoblinowManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA GOBLINA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I3").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I4", 1));
                        final Items item = rpgcore.getGoblinManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 3
                // SKRZYNIA GORYLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I6", 1));
                        final Items item = rpgcore.getGorylManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA KROLA GORYLI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I5", 1));
                        final Items item = rpgcore.getKrolGoryliManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 4
                // Skrzynia zjawy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I8", 1));
                        final Items item = rpgcore.getZjawaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia przekletej duszy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I7", 1));
                        final Items item = rpgcore.getPrzekletaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 5
                // Skrzynia straznika swiatyni
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I10", 1));
                        final Items item = rpgcore.getStraznikSwiatyniManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia trytona
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I9", 1));
                        final Items item = rpgcore.getTrytonManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
            }
        }
    }
}
