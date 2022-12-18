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
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
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
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(2, 3), ChanceHelper.getRandDouble(3, 5), ChanceHelper.getRandDouble(2, 3), ChanceHelper.getRandInt(1, 10), "&8&lNaszyjnik Wygnanca");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(1, 3), ChanceHelper.getRandDouble(1, 3), 1, ChanceHelper.getRandInt(1, 10), "&8&lDiadem Wygnanca");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.05, 0.1), ChanceHelper.getRandDouble(2, 3), 1, ChanceHelper.getRandInt(1, 10), "&8&lPierscien Wygnanca");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(1, 1, 2, ChanceHelper.getRandInt(1, 10), "&8&lKolczyki Wygnanca");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(2, 7), ChanceHelper.getRandDouble(2, 4), ChanceHelper.getRandInt(1, 2), ChanceHelper.getRandInt(1, 10), "&8&lTarcza Wygnanca");
                                break;
                            default:
                                break;
                        }


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

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getOpenChestMission() == 1) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).addOpenChestMissionProgress();
                        }

                        return;
                    }
                }

                // Expowisko 2
                // SKRZYNIA WODZA GOBLINOW
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I3").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I3", 1));
                        final Items item = rpgcore.getWodzGoblinowManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(2, 7), ChanceHelper.getRandDouble(4, 7), ChanceHelper.getRandDouble(3, 4), ChanceHelper.getRandInt(10, 20), "&a&lNaszyjnik Wodza Goblinow");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(1, 4), ChanceHelper.getRandDouble(1, 5), 1, ChanceHelper.getRandInt(10, 20), "&a&lDiadem Wodza Goblinow");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.05, 0.1), ChanceHelper.getRandDouble(2, 4), 1, ChanceHelper.getRandInt(10, 20), "&a&lPierscien Wodza Goblinow");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(3, 10), ChanceHelper.getRandDouble(2, 6), ChanceHelper.getRandInt(1, 3), ChanceHelper.getRandInt(10, 20), "&a&lTarcza Wodza Goblinow");
                                break;
                            default:
                                break;
                        }

                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA GOBLINA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I4", 1));
                        final Items item = rpgcore.getGoblinManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getOpenChestMission() == 2) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).addOpenChestMissionProgress();
                        }
                        return;
                    }
                }
                // Expowisko 3
                // SKRZYNIA GORYLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I6").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I6", 1));
                        final Items item = rpgcore.getGorylManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getOpenChestMission() == 3) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).addOpenChestMissionProgress();
                        }

                        return;
                    }
                }
                // SKRZYNIA KROLA GORYLI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I5").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I5", 1));
                        final Items item = rpgcore.getKrolGoryliManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(7, 17), ChanceHelper.getRandDouble(6, 9), ChanceHelper.getRandDouble(4, 5), ChanceHelper.getRandInt(20, 30), "&f&lNaszyjnik Krola Goryli");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(2, 6), ChanceHelper.getRandDouble(2, 8), 1, ChanceHelper.getRandInt(20, 30), "&f&lDiadem Krola Goryli");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.05, 0.1), ChanceHelper.getRandDouble(3, 4), 1, ChanceHelper.getRandInt(20, 30), "&f&lPierscien Krola Goryli");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(4, 13), ChanceHelper.getRandDouble(3, 9), ChanceHelper.getRandInt(2, 4), ChanceHelper.getRandInt(20, 30), "&f&lTarcza Krola Goryli");
                                break;
                            default:
                                break;
                        }

                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 4
                // Skrzynia zjawy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I8").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I8", 1));
                        final Items item = rpgcore.getZjawaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getOpenChestMission() == 4) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).addOpenChestMissionProgress();
                        }

                        return;
                    }
                }
                // Skrzynia przekletej duszy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I7").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I7", 1));
                        final Items item = rpgcore.getPrzekletaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(14, 31), ChanceHelper.getRandDouble(8, 14), ChanceHelper.getRandDouble(6, 9), ChanceHelper.getRandInt(30, 40), "&f&lNaszyjnik Przekletej Duszy");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(2, 12), ChanceHelper.getRandDouble(2, 11), ChanceHelper.getRandInt(1, 2), ChanceHelper.getRandInt(30, 40), "&f&lDiadem Przekletej Duszy");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.08, 0.2), ChanceHelper.getRandDouble(3, 5), 1, ChanceHelper.getRandInt(30, 40), "&f&lPierscien Przekletej Duszy");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(6, 19), ChanceHelper.getRandDouble(4, 12), ChanceHelper.getRandInt(2, 4), ChanceHelper.getRandInt(30, 40), "&f&lTarcza Przekletej Duszy");
                                break;
                            default:
                                break;
                        }

                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 5
                // Skrzynia straznika swiatyni
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I10").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I10", 1));
                        final Items item = rpgcore.getStraznikSwiatyniManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getOpenChestMission() == 5) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).addOpenChestMissionProgress();
                        }

                        return;
                    }
                }
                // Skrzynia trytona
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I9").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I9", 1));
                        final Items item = rpgcore.getTrytonManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(ChanceHelper.getRandInt(18, 38), ChanceHelper.getRandDouble(9, 16), ChanceHelper.getRandDouble(8, 16), ChanceHelper.getRandInt(40, 50), "&f&lNaszyjnik Trytona");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(ChanceHelper.getRandDouble(4, 14), ChanceHelper.getRandDouble(4, 14), ChanceHelper.getRandInt(1, 2), ChanceHelper.getRandInt(40, 50), "&f&lDiadem Trytona");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(ChanceHelper.getRandDouble(0.1, 0.2), ChanceHelper.getRandDouble(4, 6), 1, ChanceHelper.getRandInt(40, 50), "&f&lPierscien Trytona");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(ChanceHelper.getRandDouble(9, 24), ChanceHelper.getRandDouble(5, 15), ChanceHelper.getRandInt(4, 6), ChanceHelper.getRandInt(40, 50), "&f&lTarcza Trytona");
                                break;
                            default:
                                break;
                        }

                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
            }
        }
    }
}
