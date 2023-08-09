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
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;


public class DropFromChestsListener implements Listener {

    private final RPGCORE rpgcore;

    public DropFromChestsListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.PHYSICAL) {
            return;
        }


        if (player.getItemInHand().getType().equals(Material.CHEST) || player.getItemInHand().getType().equals(Material.ENDER_CHEST)) {

            final ItemStack playerItem = player.getItemInHand();

            if (playerItem.getItemMeta().hasDisplayName()) {
                e.setCancelled(true);

                if (player.getInventory().firstEmpty() == -1) {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player,
                            rpgcore.getNmsManager().makeTitle(Utils.CLEANSERVERNAME, 5, 10, 5),
                            rpgcore.getNmsManager().makeSubTitle(Utils.format("&c&lBrak miejsca w ekwipunku!"), 5, 10, 5)));
                    return;
                }


                final OsUser osUser = rpgcore.getOsManager().find(player.getUniqueId());
                // ================================ SKRZYNKI INNE ================================
                // POZLACANY SKARB
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I1", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getPozlacanySkarbManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        if (is.equals(GlobalItem.getItem("I_FRAGMENT_BONA", 1))) {
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage(Utils.format("&e&lPozlacany Skarb &8>> &7Gracz &6" + player.getName() + " &7znalazl &3&lFragment Tajemniczego Bona&7!"));
                            Bukkit.broadcastMessage(" ");
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // CIEZKA SKRZYNIA KOWALA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.getByName("I2").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I2", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        rpgcore.getCiezkaSkrzyniaKowalaManager().getDrawnItems(player);
                        return;
                    }
                }
                // SKRZYNIA Z SUROWCAMI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.getByName("I5").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I5", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getSurowceManager().getDrawnItems(player);
                        if (item == null) { return; }
                        final ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // TAJEMNICZA SKRZYNIA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.getByName("I4").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I4", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getTajemniczaManager().getDrawnItems(player);
                        if (item == null) { return; }
                        ItemStack is = item.getRewardItem();
                        if (is.equals(GlobalItem.getItem("I_FRAGMENT_BONA", 1))) {
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage(Utils.format("&3Tajemnicza Skrzynia &8>> &7Gracz &6" + player.getName() + " &7znalazl &3&lFragment Tajemniczego Bona&7!"));
                            Bukkit.broadcastMessage(" ");
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }



                // SKRZYNIA ZE ZWIERZAKAMI ZWYKLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.I_PETY1.getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I_PETY1", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        ItemStack item = rpgcore.getZwierzakiManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        player.getInventory().addItem(item);
                        return;
                    }
                }
                // SKRZYNIA ZE ZWIERZAKAMI RARE ITEMSHOP
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(SkrzynkiOther.I_PETY2.getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(SkrzynkiOther.getItem("I_PETY2", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
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
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GornikItems.I8.getItemStack().getItemMeta().getDisplayName()))) {
                    final GornikUser gornikUser = rpgcore.getGornikNPC().find(player.getUniqueId());
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GornikItems.getItem("Skrzynia_Gornika", 1));
                        if (gornikUser.getMission() == 10 || gornikUser.getMission() == 24) gornikUser.setProgress(gornikUser.getProgress() + 1);
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getGornikChestManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }

                // ================================ SKRZYNKI EXPOWISKA ================================

                // Expowisko 1
                //  SKRZYNIA DOWODCY ROZBOJNIKOW
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I1").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I1", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getWodzGoblinowManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(7, 5, 5, 15,"&a&lZwykly Naszyjnik Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(10, 7, 7, 20,"&a&lUlepszony Naszyjnik Wodza Goblinow");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(5, 7, 2, 15, "&a&lZwykly Diadem Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(8, 10, 2, 20, "&a&lUlepszony Diadem Wodza Goblinow");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(4, 6, 20, 15, "&a&lZwykly Pierscien Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(8, 10, 30, 20, "&a&lUlepszony Pierscien Wodza Goblinow");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(11, 10, 4, 15, "&a&lZwykla Tarcza Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(15, 12, 5, 20, "&a&lUlepszona Tarcza Wodza Goblinow");
                                }
                                break;
                            default:
                                break;
                        }


                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // SKRZYNIA ROZBOJNIKA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I2").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I2", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 1) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        rpgcore.getNajemnikManager().getDrawnItems(player);
                        return;
                    }
                }

                // Expowisko 2
                // SKRZYNIA WODZA GOBLINOW
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I3").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I3", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getWodzGoblinowManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(7, 5, 5, 15,"&a&lZwykly Naszyjnik Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(10, 7, 7, 20,"&a&lUlepszony Naszyjnik Wodza Goblinow");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(5, 7, 2, 15, "&a&lZwykly Diadem Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(8, 10, 2, 20, "&a&lUlepszony Diadem Wodza Goblinow");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(4, 6, 20, 15, "&a&lZwykly Pierscien Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(8, 10, 30, 20, "&a&lUlepszony Pierscien Wodza Goblinow");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(11, 10, 4, 15, "&a&lZwykla Tarcza Wodza Goblinow");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(15, 12, 5, 20, "&a&lUlepszona Tarcza Wodza Goblinow");
                                }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 2) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        final Items item = rpgcore.getGoblinManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 3
                // SKRZYNIA GORYLA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I6").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I6", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 3) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
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
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I5").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I5", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getKrolGoryliManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(14, 6, 6, 25,"&f&lZwykly Naszyjnik Krola Goryli");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(20, 10, 10, 30,"&f&lUlepszony Naszyjnik Krola Goryli");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(7, 9, 2, 25, "&f&lZwykly Diadem Krola Goryli");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(12, 15, 4, 30, "&f&lUlepszony Diadem Krola Goryli");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(5, 9, 23, 25, "&f&lZwykly Pierscien Krola Goryli");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(8, 12, 30, 30, "&f&lUlepszony Pierscien Krola Goryli");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(15, 12, 6, 25, "&f&lZwykla Tarcza Krola Goryli");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(17, 14, 8, 30, "&f&lUlepszona Tarcza Krola Goryli");
                                }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 4) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        final Items item = rpgcore.getZjawaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia przekletej duszy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I7").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I7", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getPrzekletaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(30, 9, 9,35, "&7&lZwykly Naszyjnik Przekletej Duszy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(44, 13, 13, 40,"&7&lUlepszony Naszyjnik Przekletej Duszy");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(10, 12, 3, 35, "&7&lZwykly Diadem Przekletej Duszy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(15, 20, 4, 40, "&7&lUlepszony Diadem Przekletej Duszy");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(7, 10, 25, 35, "&7&lZwykly Pierscien Przekletej Duszy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(12, 15, 35, 40, "&7&lUlepszony Pierscien Przekletej Duszy");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(17, 16, 7, 35, "&7&lZwykla Tarcza Przekletej Duszy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(20, 18, 10, 40, "&7&lUlepszona Tarcza Przekletej Duszy");
                                }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 5) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        final Items item = rpgcore.getStraznikSwiatyniManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia trytona
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I9").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I9", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        final Items item = rpgcore.getTrytonManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(43, 12, 10, 45,"&5&lZwykly Naszyjnik Trytona");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(65, 15, 15, 50,"&5&lUlepszony Naszyjnik Trytona");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(12, 14, 4, 45, "&5&lZwykly Diadem Trytona");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(18, 22, 5, 50, "&5&lUlepszony Diadem Trytona");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(8, 11, 30, 45, "&5&lZwykly Pierscien Trytona");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(15, 17, 45, 50, "&5&lUlepszony Pierscien Trytona");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(22, 20, 8,45, "&5&lZwykla Tarcza Trytona");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(25, 24, 10, 50, "&5&lUlepszona Tarcza Trytona");
                                }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 6) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        Items item = rpgcore.getMroznyWilkManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 7
                // Skrzynia Zywiolaka Ognia
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I14").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I14", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 7) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        Items item = rpgcore.getZywiolakOgniaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia Piekielnego Rycerza
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I13").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I13", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getPrzekletyRycerzManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(150, 20, 15, 65,"&c&lZwykly Naszyjnik Piekielnego Rycerza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(180, 20, 17, 70,"&c&lUlepszony Naszyjnik Piekielnego Rycerza");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(16, 21, 5, 65, "&c&lZwykly Diadem Piekielnego Rycerza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(22, 29, 6, 70, "&c&lUlepszony Diadem Piekielnego Rycerza");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(12, 13, 48, 65, "&c&lZwykly Pierscien Piekielnego Rycerza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(22, 22, 75, 70, "&c&lUlepszony Pierscien Piekielnego Rycerza");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(29, 26, 12,65, "&c&lZwykly Tarcza Piekielnego Rycerza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(35, 30, 15, 70, "&c&lUlepszona Tarcza Piekielnego Rycerza");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(13, 20, -55,65, "&c&lZwykle Kolczyki Piekielnego Rycerza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(16, 13, -50, 70, "&c&lUlepszone Kolczyki Piekielnego Rycerza");
                                }
                                break;
                            default:
                                break;
                        }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        if (rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMission() == 8) {
                            rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().setOpenChestMissionProgress(rpgcore.getWyslannikNPC().find(player.getUniqueId()).getWyslannikUser().getOpenChestMissionProgress() + 1);
                        }
                        Items item = rpgcore.getMrocznaDuszaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia Przekletego Czarnoksieznika
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I15").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I15", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getPrzekletyCzarnoksieznikManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(340, 20, 17, 75,"&5&lZwykly Naszyjnik Przekletego Czarnoksieznika");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(450, 22, 18, 80,"&5&lUlepszony Naszyjnik Przekletego Czarnoksieznika");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(18, 22, 5, 75, "&5&lZwykly Diadem Przekletego Czarnoksieznika");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(24, 32, 6, 80, "&5&lUlepszony Diadem Przekletego Czarnoksieznika");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(13, 15, 52, 75, "&5&lZwykly Pierscien Przekletego Czarnoksieznika");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(24, 24, 80, 80, "&5&lUlepszony Pierscien Przekletego Czarnoksieznika");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(30, 32, 14,75, "&5&lZwykla Tarcza Przekletego Czarnoksieznika");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(40, 35, 20, 80, "&5&lUlepszona Tarcza Przekletego Czarnoksieznika");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(14, 21, -70,75, "&5&lZwykle Kolczyki Przekletego Czarnoksieznika");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(20, 26, -64, 80, "&5&lUlepszone Kolczyki Przekletego Czarnoksieznika");
                                }
                                break;
                            default:
                                break;
                        }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getMitycznyPajakManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(710, 21, 18, 85,"&e&lZwykly Naszyjnik Mitycznego Pajaka");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(970, 25, 20, 90,"&e&lUlepszony Naszyjnik Mitycznego Pajaka");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(20, 26, 5, 85, "&e&lZwykly Diadem Mitycznego Pajaka");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(26, 35, 7, 90, "&e&lUlepszony Diadem Mitycznego Pajaka");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(15, 16, 57, 85, "&e&lZwykly Pierscien Mitycznego Pajaka");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(25, 25, 85, 90, "&e&lUlepszony Pierscien Mitycznego Pajaka");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(32, 36, 17,85, "&e&lZwykla Tarcza Mitycznego Pajaka");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(45, 40, 25, 90, "&e&lUlepszona Tarcza Mitycznego Pajaka");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(16, 23, -83,85, "&e&lZwykle Kolczyki Mitycznego Pajaka");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(22, 29, -68, 90, "&e&lUlepszone Kolczyki Mitycznego Pajaka");
                                }
                                break;
                            default:
                                break;
                        }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getPodziemnyRozpruwaczManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(1300, 23, 19, 95,"&6&lZwykly Naszyjnik Podziemnego Rozpruwacza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(1600, 25, 21, 100,"&6&lUlepszony Naszyjnik Podziemnego Rozpruwacza");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(22, 28, 5, 95, "&6&lZwykly Diadem Podziemnego Rozpruwacza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(28, 40, 7, 100, "&6&lUlepszony Diadem Podziemnego Rozpruwacza");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(20, 20, 59, 95, "&6&lZwykly Pierscien Podziemnego Rozpruwacza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(27, 27, 90, 100, "&6&lUlepszony Pierscien Podziemnego Rozpruwacza");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(38, 42, 20,95, "&6&lZwykla Tarcza Podziemnego Rozpruwacza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(50, 45, 28, 100, "&6&lUlepszona Tarcza Podziemnego Rozpruwacza");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(19, 25, -83,95, "&6&lZwykle Kolczyki Podziemnego Rozpruwacza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(23, 31, -79, 100, "&6&lUlepszone Kolczyki Podziemnego Rozpruwacza");
                                }
                                break;
                            default:
                                break;
                        }
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
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getMitycznyKrakenManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(2030, 25, 20, 105,"&b&lZwykly Naszyjnik Mitycznego Krakena");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(2469, 28, 23, 110,"&b&lUlepszony Naszyjnik Mitycznego Krakena");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(26, 36, 5, 105, "&b&lZwykly Diadem Mitycznego Krakena");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(30, 48, 8, 110, "&b&lUlepszony Diadem Mitycznego Krakena");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(22, 22, 68, 105, "&b&lZwykly Pierscien Mitycznego Krakena");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(31, 30, 95, 110, "&b&lUlepszony Pierscien Mitycznego Krakena");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(46, 44, 27,105, "&b&lZwykla Tarcza Mitycznego Krakena");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(57, 50, 35, 110, "&b&lUlepszona Tarcza Mitycznego Krakena");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(21, 28, -90,105, "&b&lZwykle Kolczyki Mitycznego Krakena");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(25, 35, -87, 110, "&b&lUlepszone Kolczyki Mitycznego Krakena");
                                }
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 12
                // Skrzynia Krysztalowego Wladcy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I23").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I23", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getKrysztalowyWladcaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(2997, 28, 23, 115,"&b&lZwykly Naszyjnik Krysztalowego Wladcy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(3610, 30, 25, 120,"&b&lUlepszony Naszyjnik Krysztalowego Wladcy");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(29, 45, 5, 115, "&b&lZwykly Diadem Krysztalowego Wladcy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(35, 56, 10, 120, "&b&lUlepszony Diadem Krysztalowego Wladcy");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(24, 24, 65, 115, "&b&lZwykly Pierscien Krysztalowego Wladcy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(34, 34, 105, 120, "&b&lUlepszony Pierscien Krysztalowego Wladcy");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(51, 50, 35,115, "&b&lZwykla Tarcza Krysztalowego Wladcy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(65, 55, 42, 120, "&b&lUlepszona Tarcza Krysztalowego Wladcy");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(23, 30, -105,115, "&b&lZwykle Kolczyki Krysztalowego Wladcy");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(30, 39, -100, 120, "&b&lUlepszone Kolczyki Krysztalowego Wladcy");
                                }
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Expowisko 13
                // Skrzynia Starozytnego Smoczego Cesarza
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I25").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I25", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 2) {
                            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                        }
                        Items item = rpgcore.getStarozytnySmoczyCesarzManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(4544, 30, 25, 125,"&5&lZwykly Naszyjnik Smoczego Cesarza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(5455, 34, 28, 130,"&5&lUlepszony Naszyjnik Smoczego Cesarza");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(32, 52, 5, 125, "&5&lZwykly Diadem Smoczego Cesarza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(40, 70, 12, 130, "&5&lUlepszony Diadem Smoczego Cesarza");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(29, 28, 70, 125, "&5&lZwykly Pierscien Smoczego Cesarza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(36, 36, 115, 130, "&5&lUlepszony Pierscien Smoczego Cesarza");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(63, 55, 45,125, "&5&lZwykla Tarcza Smoczego Cesarza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(75, 60, 57, 130, "&5&lUlepszona Tarcza Smoczego Cesarza");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(26, 36, -135,125, "&5&lZwykle Kolczyki Smoczego Cesarza");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(33, 46, -130, 130, "&5&lUlepszone Kolczyki Smoczego Cesarza");
                                }
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                    }
                }
                // TODO DUNGEONY & MAPY SPECJALNE DROP
                // TODO S
                // TODO S
                // TODO S
                // TODO DUNGEONY & MAPY SPECJALNE DROP

                // Ice Tower
                // Skrzynia Lodowego Slugi
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I_LODOWY_CHEST").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I_LODOWY_CHEST", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        Items item = rpgcore.getLodowySlugaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Skrzynia Mroznego Wladcy
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Skrzynki.getByName("I11").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Skrzynki.getItem("I11", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        final Items item = rpgcore.getKrolLoduManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(75, 15, 15, 55, "&b&lZwykly Naszyjnik Krola Lodu");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createNaszyjnik(100, 20, 16, 60, "&b&lUlepszony Naszyjnik Krola Lodu");
                                }
                                break;
                            case WATCH:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createDiadem(14, 17, 4,55, "&b&lZwykly Diadem Krola Lodu");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createDiadem(19, 26, 5, 60, "&b&lUlepszony Diadem Krola Lodu");
                                }
                                break;
                            case EXPLOSIVE_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykly")) {
                                    is = AkcesoriaPodsHelper.createPierscien(11, 13, 40, 55, "&b&lZwykly Pierscien Krola Lodu");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszony")) {
                                    is = AkcesoriaPodsHelper.createPierscien(20, 20, 60, 60, "&b&lUlepszony Pierscien Krola Lodu");
                                }
                                break;
                            case ITEM_FRAME:
                                if (is.getItemMeta().getDisplayName().contains("Zwykla")) {
                                    is = AkcesoriaPodsHelper.createTarcza(26, 23, 10, 55, "&b&lZwykla Tarcza Krola Lodu");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszona")) {
                                    is = AkcesoriaPodsHelper.createTarcza(30, 28, 10, 60, "&b&lUlepszona Tarcza Krola Lodu");
                                }
                                break;
                            case HOPPER_MINECART:
                                if (is.getItemMeta().getDisplayName().contains("Zwykle")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(10, 16, -50,55, "&b&lZwykle Kolczyki Krola Lodu");
                                } else if (is.getItemMeta().getDisplayName().contains("Ulepszone")) {
                                    is = AkcesoriaPodsHelper.createKolczyki(12, 18, -45, 60, "&b&lUlepszone Kolczyki Krola Lodu");
                                }
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
