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
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.GornikItems;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;


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


        if (player.getItemInHand().getType().equals(Material.CHEST)
                || player.getItemInHand().getType().equals(Material.ENDER_CHEST)
                || player.getItemInHand().getType().equals(Material.JUKEBOX)
                || player.getItemInHand().getType().equals(Material.FLOWER_POT_ITEM)) {

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
                // SKRZYNIA GORNIKA
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(GornikItems.I8.getItemStack().getItemMeta().getDisplayName()))) {
                    final GornikUser gornikUser = rpgcore.getGornikNPC().find(player.getUniqueId());
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(GornikItems.getItem("Skrzynia_Gornika", 1));
                        if (gornikUser.getMission() == 10 || gornikUser.getMission() == 25) gornikUser.setProgress(gornikUser.getProgress() + 1);
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
                        final Items item = rpgcore.getDowodcaRozbojnikow().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();
                        switch (is.getType()) {
                            case STORAGE_MINECART:
                                is = AkcesoriaPodsHelper.createNaszyjnik(5, 5, 5, 5,"&c&lNaszyjnik Dowodcy Rozbojnikow");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(3, 5, 2, 5, "&c&lDiadem Dowodcy Rozbojnikow");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(3, 5, 17, 5, "&c&lPierscien Dowodcy Rozbojnikow");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(7, 6, 2, 5, "&c&lTarcza Dowodcy Rozbojnikow");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(7, 5, 5, 15,"&a&lNaszyjnik Wodza Goblinow");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(5, 7, 2, 15, "&a&lDiadem Wodza Goblinow");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(4, 6, 20, 15, "&a&lPierscien Wodza Goblinow");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(11, 10, 4, 15, "&a&lTarcza Wodza Goblinow");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(14, 6, 6, 25,"&f&lNaszyjnik Krola Goryli");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(7, 9, 2, 25, "&f&lDiadem Krola Goryli");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(5, 9, 23, 25, "&f&lPierscien Krola Goryli");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(15, 12, 6, 25, "&f&lTarcza Krola Goryli");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(30, 9, 9,35, "&7&lNaszyjnik Przekletej Duszy");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(10, 12, 3, 35, "&7&lDiadem Przekletej Duszy");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(7, 10, 25, 35, "&7&lPierscien Przekletej Duszy");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(17, 16, 7, 35, "&7&lTarcza Przekletej Duszy");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(43, 12, 10, 45,"&5&lNaszyjnik Trytona");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(12, 14, 4, 45, "&5&lDiadem Trytona");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(8, 11, 30, 45, "&5&lPierscien Trytona");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(22, 20, 8,45, "&5&lTarcza Trytona");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(150, 20, 15, 65,"&c&lNaszyjnik Piekielnego Rycerza");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(16, 21, 5, 65, "&c&lDiadem Piekielnego Rycerza");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(12, 13, 48, 65, "&c&lPierscien Piekielnego Rycerza");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(29, 26, 12,65, "&c&lTarcza Piekielnego Rycerza");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(13, 20, -55,65, "&c&lKolczyki Piekielnego Rycerza");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(340, 20, 17, 75,"&5&lNaszyjnik Przekletego Czarnoksieznika");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(18, 22, 5, 75, "&5&lDiadem Przekletego Czarnoksieznika");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(13, 15, 52, 75, "&5&lPierscien Przekletego Czarnoksieznika");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(30, 32, 14,75, "&5&lTarcza Przekletego Czarnoksieznika");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(14, 21, -70,75, "&5&lKolczyki Przekletego Czarnoksieznika");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(710, 21, 18, 85,"&e&lNaszyjnik Mitycznego Pajaka");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(20, 26, 5, 85, "&e&lDiadem Mitycznego Pajaka");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(15, 16, 57, 85, "&e&lPierscien Mitycznego Pajaka");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(32, 36, 17,85, "&e&lTarcza Mitycznego Pajaka");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(16, 23, -83,85, "&e&lKolczyki Mitycznego Pajaka");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(1100, 23, 19, 95,"&6&lNaszyjnik Podziemnego Rozpruwacza");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(22, 28, 5, 95, "&6&lDiadem Podziemnego Rozpruwacza");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(20, 20, 59, 95, "&6&lPierscien Podziemnego Rozpruwacza");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(38, 42, 20,95, "&6&lTarcza Podziemnego Rozpruwacza");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(19, 25, -83,95, "&6&lKolczyki Podziemnego Rozpruwacza");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(2030, 25, 20, 105,"&b&lNaszyjnik Mitycznego Krakena");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(26, 36, 5, 105, "&b&lDiadem Mitycznego Krakena");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(22, 22, 68, 105, "&b&lPierscien Mitycznego Krakena");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(46, 44, 27,105, "&b&lTarcza Mitycznego Krakena");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(21, 28, -90,105, "&b&lKolczyki Mitycznego Krakena");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(2997, 28, 23, 115,"&b&lNaszyjnik Krysztalowego Wladcy");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(29, 45, 5, 115, "&b&lDiadem Krysztalowego Wladcy");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(24, 24, 65, 115, "&b&lPierscien Krysztalowego Wladcy");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(51, 50, 35,115, "&b&lTarcza Krysztalowego Wladcy");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(23, 30, -105,115, "&b&lKolczyki Krysztalowego Wladcy");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(4544, 30, 25, 125,"&5&lNaszyjnik Smoczego Cesarza");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(32, 52, 5, 125, "&5&lDiadem Smoczego Cesarza");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(29, 28, 70, 125, "&5&lPierscien Smoczego Cesarza");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(63, 55, 45,125, "&5&lTarcza Smoczego Cesarza");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(26, 36, -135,125, "&5&lKolczyki Smoczego Cesarza");
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
                                is = AkcesoriaPodsHelper.createNaszyjnik(75, 15, 15, 55, "&b&lNaszyjnik Krola Lodu");
                                break;
                            case WATCH:
                                is = AkcesoriaPodsHelper.createDiadem(14, 17, 4,55, "&b&lDiadem Krola Lodu");
                                break;
                            case EXPLOSIVE_MINECART:
                                is = AkcesoriaPodsHelper.createPierscien(11, 13, 40, 55, "&b&lPierscien Krola Lodu");
                                break;
                            case ITEM_FRAME:
                                is = AkcesoriaPodsHelper.createTarcza(26, 23, 10, 55, "&b&lTarcza Krola Lodu");
                                break;
                            case HOPPER_MINECART:
                                is = AkcesoriaPodsHelper.createKolczyki(10, 16, -50,55, "&b&lKolczyki Krola Lodu");
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // TODO DUNGEONY
                // TODO DUNGEONY

                // SAKIEWKA Z ULEPSZCZAMI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(RybakItems.I5.getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(new ItemBuilder(RybakItems.I5.getItemStack().clone()).setAmount(1).toItemStack().clone());
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);

                        final Items item = rpgcore.getRybakChestManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }

                        ItemStack is = item.getRewardItem();
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }

                // KUFER RYBACKI
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Dungeony.getByName("I_SAKIEWKA_ULEPSZACZY").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Dungeony.getItem("I_SAKIEWKA_ULEPSZACZY", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);

                        rpgcore.getSakiewkaUlepszaczyManager().getDrawnItems(player);
                        return;
                    }
                }






                // DUNGEON 60-70
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Dungeony.getByName("I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Dungeony.getItem("I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        final Items item = rpgcore.getPiekielnyWladcaManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case MINECART:
                                is = AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-25,-15),
                                        ChanceHelper.getRandInt(27,41),
                                        ChanceHelper.getRandInt(33,53),
                                        ChanceHelper.getRandDouble(0.1,0.2),
                                        ChanceHelper.getRandInt(-50,-35),
                                        ChanceHelper.getRandInt(60,70), "&c&lEnergia Piekielnego Wladcy");
                                break;
                            case FIREBALL:
                                is = AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(10,16),
                                        ChanceHelper.getRandInt(7,10),
                                        ChanceHelper.getRandInt(60,70),"&c&lMedalion Piekielnego Wladcy");
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // DUNGEON 70-80
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Dungeony.getByName("I_KOLOSEUM_SKRZYNKA").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Dungeony.getItem("I_KOLOSEUM_SKRZYNKA", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        final Items item = rpgcore.getCzempionArenyManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case MINECART:
                                is = AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-30,-21),
                                        ChanceHelper.getRandInt(33,46),
                                        ChanceHelper.getRandInt(32,56),
                                        ChanceHelper.getRandDouble(0.1,0.3),
                                        ChanceHelper.getRandInt(-55,-40),
                                        ChanceHelper.getRandInt(70,80), "&6&lEnergia Czempiona Areny");
                                break;
                            case FIREBALL:
                                is = AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(13,21),
                                        ChanceHelper.getRandInt(11,15),
                                        ChanceHelper.getRandInt(70,80),"&6&lMedalion Czempiona Areny");
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Dungeon 80-90
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Dungeony.getByName("I_TAJEMNICZE_PIASKI_SKRZYNKA").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Dungeony.getItem("I_TAJEMNICZE_PIASKI_SKRZYNKA", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        final Items item = rpgcore.getCesarzPustyniManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case MINECART:
                                is = AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-36,-28),
                                        ChanceHelper.getRandInt(40,55),
                                        ChanceHelper.getRandInt(38,56),
                                        ChanceHelper.getRandDouble(0.2,0.4),
                                        ChanceHelper.getRandInt(-60,-45),
                                        ChanceHelper.getRandInt(80,90), "&4&lEnergia Cesarza Pustyni");
                                break;
                            case FIREBALL:
                                is = AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(16,24),
                                        ChanceHelper.getRandInt(14,20),
                                        ChanceHelper.getRandInt(80,90),"&4&lMedalion Cesarza Pustyni");
                                break;
                            default:
                                break;
                        }
                        is.setAmount(item.getAmount());
                        player.getInventory().addItem(is);
                        return;
                    }
                }
                // Dungeon 90-100
                if (playerItem.getItemMeta().getDisplayName().equals(Utils.format(Dungeony.getByName("I_DEMONICZNE_SALE_SKRZYNKA").getItemStack().getItemMeta().getDisplayName()))) {
                    if (!player.getCanPickupItems()) {
                        player.getInventory().removeItem(Dungeony.getItem("I_DEMONICZNE_SALE_SKRZYNKA", 1));
                        osUser.setSkrzynkiProgress(osUser.getSkrzynkiProgress() + 1);
                        final Items item = rpgcore.getDemonCiemnosciManager().getDrawnItems(player);
                        if (item == null) {
                            return;
                        }
                        ItemStack is = item.getRewardItem();

                        switch (is.getType()) {
                            case MINECART:
                                is = AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-45,-33),
                                        ChanceHelper.getRandInt(50,61),
                                        ChanceHelper.getRandInt(40,65),
                                        ChanceHelper.getRandDouble(0.3,0.5),
                                        ChanceHelper.getRandInt(-60,-45),
                                        ChanceHelper.getRandInt(90,100), "&4&lEnergia Demona Ciemnosci");
                                break;
                            case FIREBALL:
                                is = AkcesoriaDodatHelper.createMedalion(ChanceHelper.getRandInt(19,27),
                                        ChanceHelper.getRandInt(19,30),
                                        ChanceHelper.getRandInt(90,100),"&4&lMedalion Demona Ciemnosci");
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
