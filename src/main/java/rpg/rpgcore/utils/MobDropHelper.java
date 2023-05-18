package rpg.rpgcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.npc.przyrodnik.Missions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.NiesyItems;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.globalitems.npc.LowcaItems;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MobDropHelper {

    public static void addDropPlayer(Player player, ItemStack is, double chance, boolean message, boolean pickup, Entity entity) {
        if (pickup) {
            if (ChanceHelper.getChance(chance)) {
                player.getInventory().addItem(is);
                if (message) {
                    player.sendMessage(Utils.format("&8+ &7x" + is.getAmount() + " " + is.getItemMeta().getDisplayName()));
                }
            }
        } else {
            if (ChanceHelper.getChance(chance)) {
                entity.getWorld().dropItem(entity.getLocation(), is);
                if (message) {
                    player.sendMessage(Utils.format(("&8[ &7DROP &8] &7x" + is.getAmount() + " " + is.getItemMeta().getDisplayName())));
                }
            }
        }
    }

    public static void dropFromMob(final Player player, final Entity entity) {
        final RPGCORE rpgcore = RPGCORE.getInstance();
        final UUID uuid = player.getUniqueId();
        final String entityName = Utils.removeColor(entity.getName());

        int szczescie = rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSzczescie();
        final RankTypePlayer rank = rpgcore.getUserManager().find(uuid).getRankPlayerUser().getRankType();
        if (rank == RankTypePlayer.VIP) szczescie += 25;
        if (rank == RankTypePlayer.TWORCA) szczescie += 35;
        if (rank == RankTypePlayer.ELITA) szczescie += 50;
        final double niesDropChance50lvl = getDropChance(szczescie, 0.02);
        final double niesDropChance50plus = getDropChance(szczescie, 0.01);
        final double chestDropChance50lvl = getDropChance(szczescie, 2.7);
        final double chestDropChance50plus = getDropChance(szczescie, 1.25);
        final double sakwaDropChance = getDropChance(szczescie, 0.03);


        rpgcore.getOsManager().find(uuid).setMobyProgress(rpgcore.getOsManager().find(uuid).getMobyProgress() + 1);

        // -------------------- SKRZYNKI PODSTAWOWE --------------------
        // TODO PODSTAWOWY HELLCASE DROPI TYLKO Z KUFRA WARTOSCIOWEGO BADZ MISJI :]
        // WARTOSCIOWY KUFER
        addDropPlayer(player, GlobalItem.getItem("I1", 1), getDropChance(szczescie, 0.005), true, true, entity);
        // SKRZYNIA KOWALA
        addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.025), true, true, entity);
        // SKRZYNIA ZE ZWIERZAKAMI
        //addDropPlayer(player, GlobalItem.getItem("I3", 1), getDropChance(szczescie, 0.001), true, true, entity);
        // TAJEMNICZA SKRZYNIA
        addDropPlayer(player, GlobalItem.getItem("I4", 1), getDropChance(szczescie, 0.05), true, true, entity);
        // SKRZYNIA Z SUROWCAMI
        addDropPlayer(player, GlobalItem.getItem("I5", 1), getDropChance(szczescie, 1.5), true, true, entity);
        // LESNIK NPC
        addDropPlayer(player, LesnikItems.getByItem("I1", 1), getDropChance(szczescie, 1.0), true, true, entity);
        // FRAGMENT STALI
        addDropPlayer(player, GlobalItem.getItem("I_FRAGMENT_STALI", 1), getDropChance(szczescie, 0.015), true, true, entity);

        final int medrzecBonus = rpgcore.getMedrzecNPC().find(uuid).getBonus();
        if (medrzecBonus < 20) {
            addDropPlayer(player, GlobalItem.getItem("ZNISZCZONE_RUBINOWE_SERCE", 1), getDropChance(szczescie, 0.05), true, true, entity);
        } else if (medrzecBonus < 40) {
            addDropPlayer(player, GlobalItem.getItem("ZNISZCZONE_SZAFIROWE_SERCE", 1), getDropChance(szczescie, 0.015), true, true, entity);
        }


        final Missions przyrodnikMission = Missions.getByNumber(rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission());
        switch (entityName) {
            // ----------------------------------------- EXPOWISKO 1 -----------------------------------------
            // BOSS
            case "[BOSS] Dowodca Rozbojnikow":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &c&lDowodca Rozbojnikow &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I1", 1), 100, true, true, entity);
                rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-1-10 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
                // LOWCA
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 1) {
                    addDropPlayer(player, LowcaItems.getItem("1-10", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 1) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Rozbojnik Lvl. 3":
            case "Rozbojnik Lvl. 5":
            case "Rozbojnik Lvl. 7":
                // SKRZYNKA MOBA
                addDropPlayer(player, Skrzynki.getItem("I2", 1), chestDropChance50lvl, true, true, entity);
                // NIESAMOWITY PRZEDMIOT
                addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance50lvl, true, false, entity);
                // Ulepszacze
                addDropPlayer(player, Ulepszacze.getItem("I_SZATAROZBOJNIKA", 1), getDropChance(szczescie, 2.5), true, true, entity);
                // PRZYRODNIK MISJE
                if (przyrodnikMission.getNumber() == 0) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("1-10"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                // WYSLANNIK
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 1) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                // DUSZOLOG MISJE
            /*if (rpgcore.getDuszologNPC().find(uuid).getDuszologUser().getMission() == 0) {
                if (ChanceHelper.getChance(100)) {
                    rpgcore.getDuszologNPC().spawnDusza(player, entity);
                }
            }*/
                break;
            // ----------------------------------------- EXPOWISKO 2 -----------------------------------------
            // BOSS
            case "[BOSS] Wodz Goblinow":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &a&lWodz Goblinow &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I3", 1), 100, true, true, entity);
                rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-10-20 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 2) {
                    addDropPlayer(player, LowcaItems.getItem("10-20", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 2) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Goblin Lvl. 14":
            case "Goblin Lvl. 16":
            case "Goblin Lvl. 19":
                addDropPlayer(player, Skrzynki.getItem("I4", 1), chestDropChance50lvl, true, true, entity);
                addDropPlayer(player, NiesyItems.N2.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_OKOGOBLINA", 1), getDropChance(szczescie, 2.0), true, true, entity);
                if (przyrodnikMission.getNumber() == 1) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("10-20"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 2) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }
                break;
            // ----------------------------------------- EXPOWISKO 3 -----------------------------------------
            // BOSS
            case "[BOSS] Krol Goryli":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &f&lKrol Goryli &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I5", 1), 100, true, true, entity);
                rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-20-30 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 3) {
                    addDropPlayer(player, LowcaItems.getItem("20-30", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 3) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Goryl Lvl. 21":
            case "Goryl Lvl. 25":
            case "Goryl Lvl. 28":
                addDropPlayer(player, Skrzynki.getItem("I6", 1), chestDropChance50lvl, true, true, entity);
                addDropPlayer(player, NiesyItems.N3.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_SKORAGORYLA", 1), getDropChance(szczescie, 1.6), true, true, entity);
                if (przyrodnikMission.getNumber() == 2) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("20-30"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 3) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 4 -----------------------------------------
            // BOSS
            case "[BOSS] Przekleta Dusza":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &7&lPrzekleta Dusza &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I7", 1), 100, true, true, entity);
                rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-30-40 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 4) {
                    addDropPlayer(player, LowcaItems.getItem("30-40", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 3) {
                    rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 4) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Zjawa Lvl. 32":
            case "Zjawa Lvl. 36":
            case "Zjawa Lvl. 39":
                addDropPlayer(player, Skrzynki.getItem("I8", 1), chestDropChance50lvl, true, true, entity);
                addDropPlayer(player, NiesyItems.N4.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_ZLAMANAKOSC", 1), getDropChance(szczescie, 2.5), true, true, entity);
                if (przyrodnikMission.getNumber() == 3) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("30-40"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 4) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }
                break;
            // ----------------------------------------- EXPOWISKO 5 -----------------------------------------
            // BOSS
            case "[BOSS] Tryton":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &e&lTryton &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I9", 1), 100, true, true, entity);
                rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-40-50 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 5) {
                    addDropPlayer(player, LowcaItems.getItem("40-50", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 5) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Straznik Swiatyni Lvl. 43":
            case "Straznik Swiatyni Lvl. 46":
            case "Straznik Swiatyni Lvl. 47":
                addDropPlayer(player, Skrzynki.getItem("I10", 1), chestDropChance50lvl, true, true, entity);
                addDropPlayer(player, NiesyItems.N5.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_LZAOCEANU", 1), getDropChance(szczescie, 2.5), true, true, entity);
                if (przyrodnikMission.getNumber() == 4) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("40-50"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 5) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 6 -----------------------------------------
            // BOSS
            // TODO JEST BOSS Z DT I PODPIETE NA SAMYM DOLE
            // MOB
            case "Mrozny Wilk Lvl. 52":
            case "Mrozny Wilk Lvl. 55":
            case "Mrozny Wilk Lvl. 56":
                addDropPlayer(player, Skrzynki.getItem("I12", 1), chestDropChance50lvl, true, true, entity);
                addDropPlayer(player, NiesyItems.N6.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_MROZNYPAZUR", 1), getDropChance(szczescie, 2.0), true, true, entity);
                if (przyrodnikMission.getNumber() == 5) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("50-60"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 6) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 7 -----------------------------------------
            // BOSS
            case "[BOSS] Piekielna Dusza":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &c&lPiekielna Dusza &fzostal zabity przez: &e" + player.getName()));
                rpgcore.getBossyManager().decrementBoss60_70count();
                addDropPlayer(player, Skrzynki.getItem("I13", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 7) {
                    addDropPlayer(player, LowcaItems.getItem("60-70", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 7) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Zywiolak Ognia Lvl. 64":
            case "Zywiolak Ognia Lvl. 66":
            case "Zywiolak Ognia Lvl. 68":
                addDropPlayer(player, Skrzynki.getItem("I14", 1), chestDropChance50plus, true, true, entity);
                addDropPlayer(player, NiesyItems.N7.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_OGNISTYPYL", 1), getDropChance(szczescie, 1.5), true, true, entity);
                if (przyrodnikMission.getNumber() == 6) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("60-70"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 7) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 8 -----------------------------------------
            // BOSS
            case "[BOSS] Przeklety Czarnoksieznik":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lPrzeklety Czarnoksieznik &fzostal zabity przez: &e" + player.getName()));
                rpgcore.getBossyManager().reset70_80();
                addDropPlayer(player, Skrzynki.getItem("I15", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 8) {
                    addDropPlayer(player, LowcaItems.getItem("70-80", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 8) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Mroczna Dusza Lvl. 71":
            case "Mroczna Dusza Lvl. 75":
            case "Mroczna Dusza Lvl. 78":
                addDropPlayer(player, Skrzynki.getItem("I16", 1), chestDropChance50plus, true, true, entity);
                addDropPlayer(player, NiesyItems.N8.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_TRUJACAROSLINA", 1), getDropChance(szczescie, 1.5), true, true, entity);
                if (przyrodnikMission.getNumber() == 7) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("70-80"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 8) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 10 -----------------------------------------
            // BOSS
            case "[BOSS] Mityczny Pajak":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &e&lMityczny Pajak &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I17", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 9) {
                    addDropPlayer(player, LowcaItems.getItem("80-90", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 9) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Pustynny Ptasznik Lvl. 84":
            case "Pustynny Ptasznik Lvl. 87":
            case "Pustynny Ptasznik Lvl. 89":
                addDropPlayer(player, NiesyItems.N9.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_JADPTASZNIKA", 1), getDropChance(szczescie, 1.4), true, true, entity);
                addDropPlayer(player, Bossy.I3.getItemStack(), getDropChance(szczescie, 0.15), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true,true, entity);
                if (przyrodnikMission.getNumber() == 8) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("80-90"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 9) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                final PustelnikUser pustelnikUser = rpgcore.getPustelnikNPC().find(uuid);
                if (pustelnikUser.getMissionId() == 1) {
                    pustelnikUser.setProgress(pustelnikUser.getProgress() + 1);
                }
                break;
            // ----------------------------------------- EXPOWISKO 11 -----------------------------------------
            // BOSS
            case "[BOSS] Podziemny Rozpruwacz":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lPodziemny Rozpruwacz &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I19", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 10) {
                    addDropPlayer(player, LowcaItems.getItem("90-100", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 10) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Podziemna Lowczyni Lvl. 92":
            case "Podziemna Lowczyni Lvl. 95":
            case "Podziemna Lowczyni Lvl. 98":
                addDropPlayer(player, NiesyItems.N10.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_MROCZNYMATERIAL", 1), getDropChance(szczescie, 1.35), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true,true, entity);
                if (ChanceHelper.getChance(getDropChance(szczescie, 0.0015))) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 90-100-BOSS 1 90-100map,366.5,80,235.5");
                    Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lPodziemny Rozpruwacz &dpojawil sie w swojej komnacie!"));
                    Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &dZostal przywolany przez &5" + player.getName() + "&d!"));
                }
                if (przyrodnikMission.getNumber() == 9) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("90-100"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 10) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 12 -----------------------------------------
            // BOSS
            case "[BOSS] Mityczny Kraken":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &b&lMityczny Kraken &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I21", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 11) {
                    addDropPlayer(player, LowcaItems.getItem("100-110", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 11) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Podwodny Straznik Lvl. 104":
            case "Podwodny Straznik Lvl. 106":
            case "Podwodny Straznik Lvl. 109":
                addDropPlayer(player, NiesyItems.N11.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_SZAFIROWESERCE", 1), getDropChance(szczescie, 2.0), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true,true, entity);
                final BossyUser bossyUser = RPGCORE.getInstance().getBossyManager().getBossyUser();
                bossyUser.incrementMobsCount100_110();
                if (bossyUser.getMobsCount100_110() == 10_000) {
                    rpgcore.getBossyManager().spawn100_110Boss();
                }
                if (przyrodnikMission.getNumber() == 10) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("100-110"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 11) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 13 -----------------------------------------
            // BOSS
            case "[BOSS] Krysztalowy Wladca":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &1&lKrysztalowy Wladca &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I23", 1), 100, true, true, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_KRYSZTAL", 1), getDropChance(szczescie, 1.5), true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 12) {
                    addDropPlayer(player, LowcaItems.getItem("110-120", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 12) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Mrozny Stroz Lvl. 114":
            case "Mrozny Stroz Lvl. 116":
            case "Mrozny Stroz Lvl. 118":
                addDropPlayer(player, NiesyItems.N12.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_ZAKLETYLOD", 1), getDropChance(szczescie, 1.5), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true,true, entity);
                if (przyrodnikMission.getNumber() == 11) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("110-120"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 12) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 14 -----------------------------------------
            // BOSS
            case "[BOSS] Archaniol":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &f&lArchaniol &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I25", 1), 100, true, true, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_NIEBIANSKIMATERIAL", 1), getDropChance(szczescie, 1.5), true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 13) {
                    addDropPlayer(player, LowcaItems.getItem("120-130", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 13) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Straznik Nieba Lvl. 123":
            case "Straznik Nieba Lvl. 126":
            case "Straznik Nieba Lvl. 129":
                addDropPlayer(player, NiesyItems.N13.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Bossy.I5.getItemStack(), getDropChance(szczescie, 0.01), true, false, entity);
                addDropPlayer(player, Bossy.I5_1.getItemStack(), getDropChance(szczescie, 0.0012), true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("I_NIEBIANSKIMATERIAL", 1), getDropChance(szczescie, 0.5), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true,true, entity);
                if (przyrodnikMission.getNumber() == 12) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("120-130"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 13) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- ICE TOWER -----------------------------------------
            case "Lodowy Sluga Lvl. 57":
                addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (!player.getWorld().getName().equals("demontower")) return;
                RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 50) {
                    IceTowerManager.actionBar(50, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f50");
                }
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 50) {
                    IceTowerManager.startIceTowerEtap2();
                }
                break;
            case "Lodowy Sluga Lvl. 58":
                addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (!player.getWorld().getName().equals("demontower")) return;
                RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 80) {
                    IceTowerManager.actionBar(80, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f80");
                }
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 80) {
                    IceTowerManager.startIceTowerEtapBOSS();
                }
                break;
            case "Lodowy Sluga Lvl. 59":
                addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (!player.getWorld().getName().equals("demontower")) return;
                RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 75) {
                    IceTowerManager.actionBar(80, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f80");
                }
                if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 75) {
                    IceTowerManager.startIceTowerEtap3();
                }
                break;
            case "[BOSS] Mrozny Wladca":
                addDropPlayer(player, Skrzynki.getItem("I_DEMONTOWER_BOSS", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 6) {
                    addDropPlayer(player, LowcaItems.getItem("50-60", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 6) {
                    rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
                }
                if (!player.getWorld().getName().equals("demontower")) return;
                IceTowerManager.teleportKowal();
                break;
        }

        final double kasa = rpgcore.getLvlManager().getKasa(entityName, uuid);
        rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() + kasa);
        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 7) {
            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + kasa);
        } else if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 12) {
            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
        }
        rpgcore.getLvlManager().updateExp(player, entityName);
    }

    public static double getDropChance(int szczescie, double chance) {
        return DoubleUtils.round(chance + ((chance * szczescie) / 1000.0), 2);
    }
}
