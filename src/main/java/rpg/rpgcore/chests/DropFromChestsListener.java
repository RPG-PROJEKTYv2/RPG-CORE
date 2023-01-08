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
                // TODO SKRZYNIA GORNIKA

                // ================================ SKRZYNKI EXPOWISKA ================================

                // Expowisko 1
                //  SKRZYNIA WYGNANCA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I1", 1));
                        Items item = rpgcore.getWygnaniecManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(5, 5, 5, 10,"&8&lZwykly Naszyjnik Wygnanca");
                                is = AkcesoriaPodsHelper.createNaszyjnik(8, 6, 6, 10,"&8&lUlepszony Naszyjnik Wygnanca");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(3, 5, 2, 10, "&8&lZwykly Diadem Wygnanca");
                                is = AkcesoriaPodsHelper.createDiadem(6, 8, 2, 10, "&8&lUlepszony Diadem Wygnanca");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(3, 5, 17, 10, "&8&lZwykly Pierscien Wygnanca");
                                is = AkcesoriaPodsHelper.createPierscien(7, 9, 30, 10, "&8&lUlepszony Pierscien Wygnanca");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(7, 15, 2, 10, "&8&lZwykla Tarcza Wygnanca");
                                is = AkcesoriaPodsHelper.createTarcza(10, 20, 5, 10, "&8&lUlepszona Tarcza Wygnanca");
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
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 1) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(7, 5, 5, 20,"&a&lZwykly Naszyjnik Wodza Goblinow");
                                is = AkcesoriaPodsHelper.createNaszyjnik(10, 7, 7, 20,"&a&lUlepszony Naszyjnik Wodza Goblinow");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(5, 7, 2, 20, "&a&lZwykly Diadem Wodza Goblinow");
                                is = AkcesoriaPodsHelper.createDiadem(8, 10, 2, 20, "&a&lUlepszony Diadem Wodza Goblinow");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(4, 6, 20, 20, "&a&lZwykly Pierscien Wodza Goblinow");
                                is = AkcesoriaPodsHelper.createPierscien(8, 10, 30, 20, "&a&lUlepszony Pierscien Wodza Goblinow");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(11, 21, 4, 20, "&a&lZwykla Tarcza Wodza Goblinow");
                                is = AkcesoriaPodsHelper.createTarcza(15, 25, 5, 20, "&a&lUlepszona Tarcza Wodza Goblinow");
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
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 2) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        return;
                    }
                }
                // Expowisko 3
                // SKRZYNIA GORYLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I6").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I6", 1));
                        Items item = rpgcore.getGorylManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 3) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(14, 6, 6, 30,"&f&lZwykly Naszyjnik Krola Goryli");
                                is = AkcesoriaPodsHelper.createNaszyjnik(20, 10, 10, 30,"&f&lUlepszony Naszyjnik Krola Goryli");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(7, 9, 2, 30, "&f&lZwykly Diadem Krola Goryli");
                                is = AkcesoriaPodsHelper.createDiadem(8, 10, 2, 30, "&f&lUlepszony Diadem Krola Goryli");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(5, 9, 23, 30, "&f&lZwykly Pierscien Krola Goryli");
                                is = AkcesoriaPodsHelper.createPierscien(8, 12, 30, 30, "&f&lUlepszony Pierscien Krola Goryli");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(15, 25, 6, 30, "&f&lZwykla Tarcza Krola Goryli");
                                is = AkcesoriaPodsHelper.createTarcza(17, 35, 8, 30, "&f&lUlepszona Tarcza Krola Goryli");
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
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 4) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }

                        return;
                    }
                }
                // Skrzynia przekletej duszy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I7").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I7", 1));
                        Items item = rpgcore.getPrzekletaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(30, 9, 9, 40, "&7&lZwykly Naszyjnik Przekletej Duszy");
                                is = AkcesoriaPodsHelper.createNaszyjnik(44, 13, 13, 40,"&7&lUlepszony Naszyjnik Przekletej Duszy");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(10, 12, 3, 40, "&7&lZwykly Diadem Przekletej Duszy");
                                is = AkcesoriaPodsHelper.createDiadem(15, 20, 4, 40, "&7&lUlepszony Diadem Przekletej Duszy");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(7, 10, 25, 40, "&7&lZwykly Pierscien Przekletej Duszy");
                                is = AkcesoriaPodsHelper.createPierscien(12, 15, 35, 40, "&7&lUlepszony Pierscien Przekletej Duszy");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(17, 29, 7, 40, "&7&lZwykla Tarcza Przekletej Duszy");
                                is = AkcesoriaPodsHelper.createTarcza(20, 35, 10, 40, "&7&lUlepszona Tarcza Przekletej Duszy");
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
                        Items item = rpgcore.getStraznikSwiatyniManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);

                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 5) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }

                        return;
                    }
                }
                // Skrzynia trytona
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I9").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I9", 1));
                        Items item = rpgcore.getTrytonManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(43, 12, 10, 50,"&5&lZwykly Naszyjnik Trytona");
                                is = AkcesoriaPodsHelper.createNaszyjnik(65, 15, 15, 50,"&5&lUlepszony Naszyjnik Trytona");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(12, 14, 4, 50, "&5&lZwykly Diadem Trytona");
                                is = AkcesoriaPodsHelper.createDiadem(18, 22, 5, 50, "&5&lUlepszony Diadem Trytona");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(8, 11, 30, 50, "&5&lZwykly Pierscien Trytona");
                                is = AkcesoriaPodsHelper.createPierscien(15, 17, 45, 50, "&5&lUlepszony Pierscien Trytona");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(22, 35, 8, 50, "&5&lZwykla Tarcza Trytona");
                                is = AkcesoriaPodsHelper.createTarcza(25, 45, 10, 50, "&5&lUlepszona Tarcza Trytona");
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 6
                // Skrzynia Mroznego Wilka
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I12").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I12", 1));
                        Items item = rpgcore.getMroznyWilkManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 6) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        return;
                    }
                }
                // Expowisko 7
                // Skrzynia Zywiolaka Ognia
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I14").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I14", 1));
                        Items item = rpgcore.getZywiolakOgniaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 7) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        return;
                    }
                }
                // Skrzynia Przekletego Rycerza
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I13").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I13", 1));
                        Items item = rpgcore.getPrzekletyRycerzManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 8
                // Skrzynia Mrocznej Duszy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I16").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I16", 1));
                        Items item = rpgcore.getMrocznaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 8) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        return;
                    }
                }
                // Skrzynia Przekletego Czarnoksieznika
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I15").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I15", 1));
                        Items item = rpgcore.getPrzekletyCzarnoksieznikManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 9
                // Skrzynia Mitycznego Pajaka
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I17").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I17", 1));
                        Items item = rpgcore.getMitycznyPajakManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 10
                // Skrzynia Podziemnego Rozpruwacza
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I19").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I19", 1));
                        Items item = rpgcore.getPodziemnyRozpruwaczManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 11
                // Skrzynia Mitycznego Krakena
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I21").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I21", 1));
                        Items item = rpgcore.getMitycznyKrakenManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 12
                // Skrzynia Hadesa
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I23").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I23", 1));
                        Items item = rpgcore.getHadesManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 13
                // Skrzynia Archaniola
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I25").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I25", 1));
                        Items item = rpgcore.getArchaniolManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
            }
        }
    }
}
