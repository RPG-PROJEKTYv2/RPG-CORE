package rpg.rpgcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.npc.czarownica.enums.CzarownicaMissions;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.npc.przyrodnik.enums.PrzyrodnikMissions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.utils.globalitems.AkceItems;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.NiesyItems;
import rpg.rpgcore.utils.globalitems.expowiska.*;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.globalitems.npc.LowcaItems;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;
import rpg.rpgcore.wyszkolenie.enums.WyszkolenieItems;

import java.util.UUID;

public class MobDropHelper {

    public static void addDropPlayer(Player player, ItemStack is, double chance, boolean message, boolean pickup, Entity entity) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        if (pickup) {
            if (ChanceHelper.getChance(chance)) {
                player.getInventory().addItem(is);
                if (message) {
                    if (is.equals(BonType.WZM_KRYTYK_10.getBon())) {
                        Bukkit.getServer().broadcastMessage("");
                        Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + player.getName() + " &7zdobyl " + is.getItemMeta().getDisplayName() + " &7z " + entity.getName()));
                        Bukkit.getServer().broadcastMessage("");
                    }
                    if (is.equals(BonType.PRZESZYWKA_20.getBon())) {
                        Bukkit.getServer().broadcastMessage("");
                        Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + player.getName() + " &7zdobyl " + is.getItemMeta().getDisplayName() + " &7z " + entity.getName()));
                        Bukkit.getServer().broadcastMessage("");
                    }
                    if (user.isItemDropEnabled())
                        player.sendMessage(Utils.format("&8+ &7x" + is.getAmount() + " " + is.getItemMeta().getDisplayName()));
                }
            }
        } else {
            if (ChanceHelper.getChance(chance)) {
                entity.getWorld().dropItem(entity.getLocation(), is);
                if (message) {
                    if (user.isItemDropEnabled())
                        player.sendMessage(Utils.format(("&8[ &7DROP &8] &8&l>> &7x" + is.getAmount() + " " + is.getItemMeta().getDisplayName())));
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

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (player.getInventory().getHelmet() != null) {
            szczescie += Utils.getTagInt(player.getInventory().getHelmet(), "szczescie");
        }
        if (player.getInventory().getChestplate() != null) {
            szczescie += Utils.getTagInt(player.getInventory().getChestplate(), "szczescie");
        }
        if (player.getInventory().getLeggings() != null) {
            szczescie += Utils.getTagInt(player.getInventory().getLeggings(), "szczescie");
        }
        if (player.getInventory().getBoots() != null) {
            szczescie += Utils.getTagInt(player.getInventory().getBoots(), "szczescie");
        }

        final double akceDropChance50lvl = getDropChance(szczescie, 0.1);
        final double niesDropChance50lvl = getDropChance(szczescie, 0.06);
        final double niesDropChance50plus = getDropChance(szczescie, 0.04);
        final double chestDropChance50lvl = getDropChance(szczescie, 1.2);
        final double chestDropChance50plus = getDropChance(szczescie, 0.7);


        rpgcore.getOsManager().find(uuid).setMobyProgress(rpgcore.getOsManager().find(uuid).getMobyProgress() + 1);
        if (rpgcore.getCzarownicaNPC().find(uuid).getMission() == 5) {
            final CzarownicaUser czarownicaUser = rpgcore.getCzarownicaNPC().find(uuid);
            czarownicaUser.getProgressMap().put(CzarownicaMissions.mission5Item, czarownicaUser.getProgressMap().get(CzarownicaMissions.mission5Item) + 1);
        }

        // -------------------- SKRZYNKI PODSTAWOWE --------------------
        // POZLACANY SKARB
        addDropPlayer(player, SkrzynkiOther.getItem("I1", 1), getDropChance(szczescie, 0.02), true, true, entity);
        // SKRZYNIA KOWALA
        addDropPlayer(player, SkrzynkiOther.getItem("I2", 1), getDropChance(szczescie, 0.03), true, true, entity);
        // TAJEMNICZA SKRZYNIA
        addDropPlayer(player, SkrzynkiOther.getItem("I4", 1), getDropChance(szczescie, 0.35), true, true, entity);
        // SKRZYNIA Z SUROWCAMI
        addDropPlayer(player, SkrzynkiOther.getItem("I5", 1), getDropChance(szczescie, 1.6), true, true, entity);
        // SKRZYNIA ZE ZWIERZAKAMI
        //addDropPlayer(player, GlobalItem.getItem("I3", 1), getDropChance(szczescie, 0.001), true, true, entity);

        // LESNIK NPC
        addDropPlayer(player, LesnikItems.getByItem("I1", 1), getDropChance(szczescie, 0.045), true, true, entity);
        // FRAGMENT STALI
        addDropPlayer(player, GlobalItem.getItem("I_FRAGMENT_STALI", 1), getDropChance(szczescie, 0.04), true, true, entity);

        // MEDRZEC
        final int medrzecBonus = rpgcore.getMedrzecNPC().find(uuid).getBonus();
        if (medrzecBonus < 20) {
            addDropPlayer(player, GlobalItem.getItem("ZNISZCZONE_RUBINOWE_SERCE", 1), getDropChance(szczescie, 0.05), true, true, entity);
        } else if (medrzecBonus < 40) {
            addDropPlayer(player, GlobalItem.getItem("ZNISZCZONE_SZAFIROWE_SERCE", 1), getDropChance(szczescie, 0.015), true, true, entity);
        }


        final PrzyrodnikMissions przyrodnikMission = PrzyrodnikMissions.getByNumber(rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission());
        switch (entityName) {
            // ----------------------------------------- EXPOWISKO 1 -----------------------------------------
            // BOSS
            case "[BOSS] Dowodca Rozbojnikow":
                for (final Player server : Bukkit.getOnlinePlayers()) {
                    if (RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss1_10()) {
                        server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &c&lDowodca Rozbojnikow &fzostal zabity przez: &e" + player.getName()));
                    }
                }
                rpgcore.getBossyManager().decrementBoss1_10count();
                addDropPlayer(player, Skrzynki.getItem("I1", 1), 100, true, true, entity);
                // LOWCA
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 1) {
                    addDropPlayer(player, LowcaItems.getItem("1-10", 1), getDropChance(szczescie, 35), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 1) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Rozbojnik Lvl. 3":
            case "Rozbojnik Lvl. 5":
            case "Rozbojnik Lvl. 7":
                // SKRZYNKA MOBA
                addDropPlayer(player, Skrzynki.getItem("I2", 1), getDropChance(szczescie, 1.6), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A1.getItemStack(), getDropChance(szczescie, 0.4), true, false, entity);
                // NIESAMOWITY PRZEDMIOT
                addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance50lvl, true, false, entity);
                // Ulepszacze
                addDropPlayer(player, Ulepszacze.getItem("1-10", 1), getDropChance(szczescie, 2.5), true, true, entity);
                // przywolanie boss:
                addDropPlayer(player, Bossy.getItem("I1_10", 1), getDropChance(szczescie, 0.95), true, true, entity);
                // PRZYRODNIK MISJE
                if (przyrodnikMission.getNumber() == 0) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("1-10"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                // WYSLANNIK
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 1) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
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
                for (final Player server : Bukkit.getOnlinePlayers()) {
                    if (RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss10_20()) {
                        server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &a&lWodz Goblinow &fzostal zabity przez: &e" + player.getName()));
                    }
                }
                rpgcore.getBossyManager().decrementBoss10_20count();
                addDropPlayer(player, Skrzynki.getItem("I3", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 2) {
                    addDropPlayer(player, LowcaItems.getItem("10-20", 1), getDropChance(szczescie, 30), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 2) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Goblin Lvl. 14":
            case "Goblin Lvl. 16":
            case "Goblin Lvl. 19":
                addDropPlayer(player, Skrzynki.getItem("I4", 1), getDropChance(szczescie, 1.5), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A2.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N2.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("10-20", 1), getDropChance(szczescie, 2.0), true, true, entity);
                // przywolanie boss:
                addDropPlayer(player, Bossy.getItem("I10_20", 1), getDropChance(szczescie, 0.85), true, true, entity);
                if (przyrodnikMission.getNumber() == 1) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("10-20"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 2) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }
                break;
            // ----------------------------------------- EXPOWISKO 3 -----------------------------------------
            // BOSS
            case "[BOSS] Krol Goryli":
                for (final Player server : Bukkit.getOnlinePlayers()) {
                    if (RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss20_30()) {
                        server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &f&lKrol Goryli &fzostal zabity przez: &e" + player.getName()));
                    }
                }
                rpgcore.getBossyManager().decrementBoss20_30count();
                addDropPlayer(player, Skrzynki.getItem("I5", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 3) {
                    addDropPlayer(player, LowcaItems.getItem("20-30", 1), getDropChance(szczescie, 25), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 3) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Goryl Lvl. 21":
            case "Goryl Lvl. 25":
            case "Goryl Lvl. 28":
                addDropPlayer(player, Skrzynki.getItem("I6", 1), getDropChance(szczescie, 1.5), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A3.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N3.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("20-30", 1), getDropChance(szczescie, 1.6), true, true, entity);
                // przywolanie boss:
                addDropPlayer(player, Bossy.getItem("I20_30", 1), getDropChance(szczescie, 0.65), true, true, entity);
                if (przyrodnikMission.getNumber() == 2) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("20-30"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 3) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 4 -----------------------------------------
            // BOSS
            case "[BOSS] Przekleta Dusza":
                for (final Player server : Bukkit.getOnlinePlayers()) {
                    if (RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss30_40()) {
                        server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &7&lPrzekleta Dusza &fzostal zabity przez: &e" + player.getName()));
                    }
                }
                rpgcore.getBossyManager().decrementBoss30_40count();
                addDropPlayer(player, Skrzynki.getItem("I7", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 4) {
                    addDropPlayer(player, LowcaItems.getItem("30-40", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 3) {
                    rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 4) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Zjawa Lvl. 32":
            case "Zjawa Lvl. 36":
            case "Zjawa Lvl. 39":
                addDropPlayer(player, Skrzynki.getItem("I8", 1), getDropChance(szczescie, 1.5), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A4.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N4.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("30-40", 1), getDropChance(szczescie, 2.5), true, true, entity);
                // przywolanie boss:
                addDropPlayer(player, Bossy.getItem("I30_40", 1), getDropChance(szczescie, 0.45), true, true, entity);
                if (przyrodnikMission.getNumber() == 3) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("30-40"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 4) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }
                break;
            // ----------------------------------------- EXPOWISKO 5 -----------------------------------------
            // BOSS
            case "[BOSS] Tryton":
                for (final Player server : Bukkit.getOnlinePlayers()) {
                    if (RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss40_50()) {
                        server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &e&lTryton &fzostal zabity przez: &e" + player.getName()));
                    }
                }
                rpgcore.getBossyManager().decrementBoss40_50count();
                addDropPlayer(player, Skrzynki.getItem("I9", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 5) {
                    addDropPlayer(player, LowcaItems.getItem("40-50", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 5) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Straznik Swiatyni Lvl. 43":
            case "Straznik Swiatyni Lvl. 46":
            case "Straznik Swiatyni Lvl. 47":
                addDropPlayer(player, Skrzynki.getItem("I10", 1), getDropChance(szczescie, 1.3), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A5.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N5.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("40-50", 1), getDropChance(szczescie, 2.5), true, true, entity);
                // przywolanie boss:
                addDropPlayer(player, Bossy.getItem("I40_50", 1), getDropChance(szczescie, 0.30), true, true, entity);
                if (przyrodnikMission.getNumber() == 4) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("40-50"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 5) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 6 -----------------------------------------
            // MOB
            case "Mrozny Wilk Lvl. 52":
            case "Mrozny Wilk Lvl. 55":
            case "Mrozny Wilk Lvl. 56":
                // zmianki
                addDropPlayer(player, GlobalItem.I50.getItemStack(), getDropChance(szczescie, 0.2), true, false, entity);
                addDropPlayer(player, Skrzynki.getItem("I12", 1), getDropChance(szczescie, 1.3), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A6.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N6.getItemStack(), niesDropChance50lvl, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("50-60", 1), getDropChance(szczescie, 2.0), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I1.getItem().clone(), getDropChance(szczescie, 0.06), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I2.getItem().clone(), getDropChance(szczescie, 0.055), true, true, entity);
                if (przyrodnikMission.getNumber() == 5) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("50-60"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 6) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 7 -----------------------------------------
            // BOSS
            case "[BOSS] Piekielny Rycerz":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &c&lPiekielny Rycerz &fzostal zabity przez: &e" + player.getName()));
                rpgcore.getBossyManager().decrementBoss60_70count();
                addDropPlayer(player, Skrzynki.getItem("I13", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 7) {
                    addDropPlayer(player, LowcaItems.getItem("60-70", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 7) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Zywiolak Ognia Lvl. 64":
            case "Zywiolak Ognia Lvl. 66":
            case "Zywiolak Ognia Lvl. 68":
                addDropPlayer(player, GlobalItem.I_KAMIENBAO.getItemStack().clone(), getDropChance(szczescie, 0.015), true, false, entity);
                addDropPlayer(player, Skrzynki.getItem("I14", 1), getDropChance(szczescie, 1.0), true, true, entity);
                addDropPlayer(player, Bossy.getItem("I60_70", 1), getDropChance(szczescie, 0.085), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A7.getItemStack(), getDropChance(szczescie, 0.08), true, false, entity);
                addDropPlayer(player, NiesyItems.N7.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("60-70", 1), getDropChance(szczescie, 1.5), true, true, entity);
                addDropPlayer(player, Dungeony.I_KLUCZ_PIEKIELNY_PRZEDSIONEK.getItemStack().clone(), getDropChance(szczescie, 0.03), true, true, entity);
                addDropPlayer(player, GlobalItem.RUDA_MITHRYLU.getItemStack().clone(), getDropChance(szczescie, 0.25), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I3.getItem().clone(), getDropChance(szczescie, 0.055), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I9.getItem().clone(), getDropChance(szczescie, 0.055), true, true, entity);
                if (przyrodnikMission.getNumber() == 6) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("60-70"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 7) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 8 -----------------------------------------
            // BOSS
            case "[BOSS] Przeklety Czarnoksieznik":
                addDropPlayer(player, Bossy.I70_80_BONUS.getItemStack().clone(), getDropChance(szczescie, 20), true, true, entity);
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lPrzeklety Czarnoksieznik &fzostal zabity przez: &e" + player.getName()));
                rpgcore.getBossyManager().reset70_80();
                addDropPlayer(player, Skrzynki.getItem("I15", 1), 100, true, true, entity);
                addDropPlayer(player, GlobalItem.RUDA_MITHRYLU.getItemStack().clone(), getDropChance(szczescie, 5), true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 8) {
                    addDropPlayer(player, LowcaItems.getItem("70-80", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 8) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Mroczna Dusza Lvl. 71":
            case "Mroczna Dusza Lvl. 75":
            case "Mroczna Dusza Lvl. 78":
                // bao
                addDropPlayer(player, GlobalItem.I_KAMIENBAO.getItemStack().clone(), getDropChance(szczescie, 0.025), true, false, entity);
                // przedmiot do wzm mieczy [wygnany kowal]
                addDropPlayer(player, GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack(), getDropChance(szczescie, 0.15), true, true, entity);
                addDropPlayer(player, GlobalItem.I50.getItemStack(), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, Skrzynki.getItem("I16", 1), getDropChance(szczescie, 0.8), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, Bossy.I2.getItemStack(), getDropChance(szczescie, 0.2), true, true, entity);
                addDropPlayer(player, Dungeony.I_KLUCZ_KOLOSEUM.getItemStack(), getDropChance(szczescie, 0.0095), true, true, entity);
                addDropPlayer(player, AkceItems.A8.getItemStack(), getDropChance(szczescie, 0.07), true, false, entity);
                addDropPlayer(player, NiesyItems.N8.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, Ulepszacze.getItem("70-80", 1), getDropChance(szczescie, 1.5), true, true, entity);
                addDropPlayer(player, GlobalItem.RUDA_MITHRYLU.getItemStack().clone(), getDropChance(szczescie, 0.4), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I12.getItem().clone(), getDropChance(szczescie, 0.045), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I4.getItem().clone(), getDropChance(szczescie, 0.04), true, true, entity);
                if (przyrodnikMission.getNumber() == 7) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("70-80"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 8) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 10 -----------------------------------------
            // BOSS
            case "[BOSS] Mityczny Pajak":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &e&lMityczny Pajak &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I17", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 9) {
                    addDropPlayer(player, LowcaItems.getItem("80-90", 1), getDropChance(szczescie, 25), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 9) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Pustynny Ptasznik Lvl. 84":
            case "Pustynny Ptasznik Lvl. 87":
            case "Pustynny Ptasznik Lvl. 89":
                addDropPlayer(player, Dungeony.I_KLUCZ_TAJEMNICZE_PIASKI.getItemStack(), getDropChance(szczescie, 0.008), true, true, entity);
                addDropPlayer(player, NiesyItems.N9.getItemStack(), niesDropChance50plus, true, false, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A9.getItemStack(), getDropChance(szczescie, 0.06), true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("80-90", 1), getDropChance(szczescie, 1.4), true, true, entity);
                addDropPlayer(player, Bossy.I3.getItemStack(), getDropChance(szczescie, 0.08), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I10.getItem().clone(), getDropChance(szczescie, 0.04), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I13.getItem().clone(), getDropChance(szczescie, 0.035), true, true, entity);
                if (przyrodnikMission.getNumber() == 8) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("80-90"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 9) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
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
                    addDropPlayer(player, LowcaItems.getItem("90-100", 1), getDropChance(szczescie, 50), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 10) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Podziemna Lowczyni Lvl. 92":
            case "Podziemna Lowczyni Lvl. 95":
            case "Podziemna Lowczyni Lvl. 98":
                // przepa do krakenow
                addDropPlayer(player, Przepustki.I1.getItemStack().clone(), getDropChance(szczescie, 0.15), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, Dungeony.I_KLUCZ_DEMONICZNE_SALE.getItemStack(), getDropChance(szczescie, 0.005), true, true, entity);
                addDropPlayer(player, AkceItems.A10.getItemStack(), getDropChance(szczescie, 0.055), true, false, entity);
                addDropPlayer(player, NiesyItems.N10.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("90-100", 1), getDropChance(szczescie, 1.35), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I5.getItem().clone(), getDropChance(szczescie, 0.035), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I11.getItem().clone(), getDropChance(szczescie, 0.03), true, true, entity);
                if (ChanceHelper.getChance(getDropChance(szczescie, 0.02))) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 90-100-BOSS 1 90-100map,413.5,86,47.5");
                    Bukkit.getServer().broadcastMessage(" ");
                    Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lPodziemny Rozpruwacz &dpojawil sie w swojej komnacie!"));
                    Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &dZostal przywolany przez &5" + player.getName() + "&d!"));
                    Bukkit.getServer().broadcastMessage(" ");
                }
                if (przyrodnikMission.getNumber() == 9) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("90-100"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 10) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 12 -----------------------------------------
            // BOSS
            case "[BOSS] Mistyczny Kraken":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &b&lMityczny Kraken &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I21", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 11) {
                    addDropPlayer(player, LowcaItems.getItem("100-110", 1), getDropChance(szczescie, 25), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 11) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Podwodny Straznik Lvl. 104":
            case "Podwodny Straznik Lvl. 106":
            case "Podwodny Straznik Lvl. 109":
                // przepa do krakenow
                addDropPlayer(player, Przepustki.I1.getItemStack().clone(), getDropChance(szczescie, 0.15), true, true, entity);
                // przepa do krysztalowej sali
                addDropPlayer(player, Przepustki.I2.getItemStack().clone(), getDropChance(szczescie, 0.05), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A11.getItemStack(), getDropChance(szczescie, 0.05), true, false, entity);
                addDropPlayer(player, NiesyItems.N11.getItemStack(), getDropChance(szczescie, 0.02), true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("100-110", 1), getDropChance(szczescie, 1.3), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I14.getItem().clone(), getDropChance(szczescie, 0.03), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I6.getItem().clone(), getDropChance(szczescie, 0.025), true, true, entity);
                final BossyUser bossyUser = RPGCORE.getInstance().getBossyManager().getBossyUser();
                bossyUser.incrementMobsCount100_110();
                if (bossyUser.getMobsCount100_110() == 10_000) {
                    rpgcore.getBossyManager().spawn100_110Boss();
                }
                if (przyrodnikMission.getNumber() == 10) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("100-110"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 11) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 13 -----------------------------------------
            // BOSS
            case "[BOSS] Krysztalowy Wladca":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &1&lKrysztalowy Wladca &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I23", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 12) {
                    addDropPlayer(player, LowcaItems.getItem("110-120", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 12) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Mrozny Stroz Lvl. 114":
            case "Mrozny Stroz Lvl. 116":
            case "Mrozny Stroz Lvl. 118":
                // przepa do krysztalowej sali
                addDropPlayer(player, Przepustki.I2.getItemStack().clone(), getDropChance(szczescie, 0.05), true, true, entity);
                // przepa do tajemniczej siedziby
                addDropPlayer(player, Przepustki.I3.getItemStack().clone(), getDropChance(szczescie, 0.01), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A12.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N12.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("110-120", 1), getDropChance(szczescie, 1.5), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I15.getItem().clone(), getDropChance(szczescie, 0.01), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I7.getItem().clone(), getDropChance(szczescie, 0.005), true, true, entity);
                addDropPlayer(player, Bossy.I4.getItemStack().clone(), getDropChance(szczescie, 0.5), true, true, entity);
                if (przyrodnikMission.getNumber() == 11) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("110-120"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 12) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- EXPOWISKO 14 -----------------------------------------
            // BOSS
            case "[BOSS] Starozytny Smoczy Cesarz":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5&lStarozytny Smoczy Cesarz &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.getItem("I25", 1), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 13) {
                    addDropPlayer(player, LowcaItems.getItem("120-130", 1), getDropChance(szczescie, 20), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 13) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                break;
            // MOB
            case "Mnich Lvl. 123":
            case "Mnich Lvl. 126":
            case "Mnich Lvl. 129":
                // przepa do tajemniczej siedziby
                addDropPlayer(player, Przepustki.I3.getItemStack().clone(), getDropChance(szczescie, 0.01), true, true, entity);
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A13.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, NiesyItems.N13.getItemStack(), niesDropChance50plus, true, false, entity);
                addDropPlayer(player, Bossy.I5.getItemStack(), getDropChance(szczescie, 0.01), true, false, entity);
                addDropPlayer(player, Bossy.I5_1.getItemStack(), getDropChance(szczescie, 0.0012), true, false, entity);
                addDropPlayer(player, Ulepszacze.getItem("120-130", 1), getDropChance(szczescie, 0.5), true, true, entity);
                addDropPlayer(player, GlobalItem.getItem("I_CZASTKA_MAGII", 1), getDropChance(szczescie, 0.02), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I16.getItem().clone(), getDropChance(szczescie, 0.0025), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I8.getItem().clone(), getDropChance(szczescie, 0.001), true, true, entity);
                addDropPlayer(player, WyszkolenieItems.I17.getItem().clone(), getDropChance(szczescie, 0.0005), true, true, entity);
                if (przyrodnikMission.getNumber() == 12) {
                    addDropPlayer(player, PrzyrodnikItems.getItem("120-130"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 13) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillMobsMissionProgress() + 1);
                }

                break;
            // ----------------------------------------- ICE TOWER -----------------------------------------
            case "Lodowy Sluga Lvl. 57":
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A6.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, Skrzynki.getItem("I_LODOWY_CHEST", 1), getDropChance(szczescie, 0.55), true, true, entity);
                addDropPlayer(player, SkrzynkiOther.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (rpgcore.getMroznyStrozNPC().find(uuid).getMission() == 5) {
                    rpgcore.getMroznyStrozNPC().find(uuid).setProgress(rpgcore.getMroznyStrozNPC().find(uuid).getProgress() + 1);
                }
                if (!player.getWorld().getName().equals("DemonTower")) break;
                if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_1) {
                    rpgcore.getIceTowerManager().incrementCount();
                }
                break;
            case "Lodowy Sluga Lvl. 58":
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A6.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, Skrzynki.getItem("I_LODOWY_CHEST", 1), getDropChance(szczescie, 0.55), true, true, entity);
                addDropPlayer(player, SkrzynkiOther.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (rpgcore.getMroznyStrozNPC().find(uuid).getMission() == 5) {
                    rpgcore.getMroznyStrozNPC().find(uuid).setProgress(rpgcore.getMroznyStrozNPC().find(uuid).getProgress() + 1);
                }
                if (!player.getWorld().getName().equals("DemonTower")) break;
                if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_2) {
                    rpgcore.getIceTowerManager().incrementCount();
                }
                break;
            case "Lodowy Sluga Lvl. 59":
                // AKCESORIUM
                addDropPlayer(player, AkceItems.A6.getItemStack(), akceDropChance50lvl, true, false, entity);
                addDropPlayer(player, Skrzynki.getItem("I_LODOWY_CHEST", 1), getDropChance(szczescie, 0.55), true, true, entity);
                addDropPlayer(player, SkrzynkiOther.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
                if (rpgcore.getMroznyStrozNPC().find(uuid).getMission() == 5) {
                    rpgcore.getMroznyStrozNPC().find(uuid).setProgress(rpgcore.getMroznyStrozNPC().find(uuid).getProgress() + 1);
                }
                if (!player.getWorld().getName().equals("DemonTower")) break;
                if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.ETAP_4) {
                    rpgcore.getIceTowerManager().incrementCount();
                }
                break;
            case "[BOSS] Krol Lodu":
                Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &b&lKrol Lodu &fzostal zabity przez: &e" + player.getName()));
                addDropPlayer(player, Skrzynki.I11.getItemStack().clone(), 100, true, true, entity);
                if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 6) {
                    addDropPlayer(player, LowcaItems.getItem("Lodowej-Wiezy", 1), getDropChance(szczescie, 15), true, true, entity);
                }
                if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 6) {
                    rpgcore.getWyslannikNPC().find(uuid).setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getKillBossMissionProgress() + 1);
                }
                if (rpgcore.getMroznyStrozNPC().find(uuid).getMission() == 2 || rpgcore.getMroznyStrozNPC().find(uuid).getMission() == 4) {
                    rpgcore.getMroznyStrozNPC().find(uuid).setProgress(rpgcore.getMroznyStrozNPC().find(uuid).getProgress() + 1);
                }
                if (!player.getWorld().getName().equals("DemonTower")) break;
                if (rpgcore.getIceTowerManager().getStatus() == DungeonStatus.BOSS) {
                    rpgcore.getIceTowerManager().incrementCount();
                }
                break;
            // ----------------------------------------- PIEKIELNY PRZEDSIONEK -----------------------------------------
            case "Ognisty Duch Lvl. 69":
                addDropPlayer(player, AkceItems.A7.getItemStack(), getDropChance(szczescie, 0.06), true, false, entity);
                if (rpgcore.getPrzedsionekManager().getDungeonStatus() == DungeonStatus.ETAP_1 || rpgcore.getPrzedsionekManager().getDungeonStatus() == DungeonStatus.ETAP_3) {
                    rpgcore.getPrzedsionekManager().incrementCounter();
                }
                break;
            case "[BOSS] Piekielny Wladca":
                addDropPlayer(player, Dungeony.getItem("I_SAKIEWKA_ULEPSZACZY", ChanceHelper.getRandInt(1, 5)), getDropChance(szczescie, 45), true, true, entity);
                addDropPlayer(player, Dungeony.getItem("I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA", 1), 100, true, true, entity);
                rpgcore.getPrzedsionekManager().incrementCounter();
                break;
            // ----------------------------------------- PIEKIELNY PRZEDSIONEK -----------------------------------------
            case "Zapomniany Wojownik Lvl. 75":
                addDropPlayer(player, AkceItems.A8.getItemStack(), getDropChance(szczescie, 0.055), true, false, entity);
                if (rpgcore.getKoloseumManager().getDungeonStatus() == DungeonStatus.ETAP_1) {
                    rpgcore.getKoloseumManager().incrementCounter();
                }
                break;
            case "Zapomniany Wojownik Lvl. 79":
                addDropPlayer(player, AkceItems.A8.getItemStack(), getDropChance(szczescie, 0.06), true, false, entity);
                if (rpgcore.getKoloseumManager().getDungeonStatus() == DungeonStatus.ETAP_5) {
                    rpgcore.getKoloseumManager().incrementCounter();
                }
                break;
            case "[MiniBOSS] Wyznawca Ateny":
            case "[MiniBOSS] Wyznawca Posejdona":
            case "[MiniBOSS] Wyznawca Zeusa":
            case "[MiniBOSS] Wyznawca Hadesa":
                addDropPlayer(player, Dungeony.getItem("I_SAKIEWKA_ULEPSZACZY", ChanceHelper.getRandInt(1, 3)), getDropChance(szczescie, 30), true, true, entity);
                addDropPlayer(player, AkceItems.A8.getItemStack(), getDropChance(szczescie, 2), true, false, entity);
                if (ChanceHelper.getChance(getDropChance(szczescie, 15))) {
                    player.getInventory().addItem(Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack());
                    Bukkit.getServer().broadcastMessage(" ");
                    Bukkit.getServer().broadcastMessage(Utils.format("&6&lKoloseum &8>> &7Gracz &f" + player.getName() + " &7znalazl &eSakiewke ze Zlotym Proszkiem&7!"));
                    Bukkit.getServer().broadcastMessage(" ");
                }
                if (rpgcore.getKoloseumManager().getDungeonStatus() == DungeonStatus.ETAP_2 || rpgcore.getKoloseumManager().getDungeonStatus() == DungeonStatus.ETAP_4 ||
                        rpgcore.getKoloseumManager().getDungeonStatus() == DungeonStatus.ETAP_6) {
                    rpgcore.getKoloseumManager().incrementCounter();
                }
                break;
            case "[BOSS] Czempion Areny":
                addDropPlayer(player, Dungeony.getItem("I_SAKIEWKA_ULEPSZACZY", ChanceHelper.getRandInt(1, 5)), getDropChance(szczescie, 45), true, true, entity);
                addDropPlayer(player, Dungeony.I_KOLOSEUM_SKRZYNKA.getItemStack(), 100, true, true, entity);
                rpgcore.getKoloseumManager().incrementCounter();
                break;
            // ----------------------------------------- TAJEMNICZE PIASKI -----------------------------------------
            case "Truposz Lvl. 85":
                addDropPlayer(player, AkceItems.A9.getItemStack(), getDropChance(szczescie, 0.055), true, false, entity);
                if (rpgcore.getTajemniczePiaskiManager().getDungeonStatus() == DungeonStatus.ETAP_1) {
                    rpgcore.getTajemniczePiaskiManager().incrementCounter();
                }
                break;
            case "Truposz Lvl. 89":
                addDropPlayer(player, AkceItems.A9.getItemStack(), getDropChance(szczescie, 0.06), true, false, entity);
                if (rpgcore.getTajemniczePiaskiManager().getDungeonStatus() == DungeonStatus.ETAP_5) {
                    rpgcore.getTajemniczePiaskiManager().incrementCounter();
                }
                rpgcore.getTajemniczePiaskiManager().getRdzenieLocation().values().forEach(rdzen -> {
                    if (rdzen.getEntityId().contains("" + entity.getEntityId()))
                        rdzen.getEntityId().remove("" + entity.getEntityId());
                });
                break;
            case "[MiniBOSS] Pustynny Tarczownik":
            case "[MiniBOSS] Pustynny Przyzywacz":
                addDropPlayer(player, AkceItems.A9.getItemStack(), getDropChance(szczescie, 2), true, false, entity);
                if (rpgcore.getTajemniczePiaskiManager().getDungeonStatus() == DungeonStatus.ETAP_3) {
                    rpgcore.getTajemniczePiaskiManager().incrementCounter();
                }
                break;
            case "[BOSS] Cesarz Pustyni":
                addDropPlayer(player, Dungeony.I_TAJEMNICZE_PIASKI_SKRZYNKA.getItemStack(), 100, true, true, entity);
                rpgcore.getTajemniczePiaskiManager().incrementCounter();
                break;
            // ----------------------------------------- DEMONICZNE SALE -----------------------------------------
            case "Demoniczny Lowca Lvl. 95":
                addDropPlayer(player, Dungeony.I_DEMONICZNY_SARKOFAG.getItemStack(), 0.0075, true, true, entity);
                addDropPlayer(player, AkceItems.A10.getItemStack(), getDropChance(szczescie, 0.07), true, false, entity);
                addDropPlayer(player, NiesyItems.N10.getItemStack(), getDropChance(szczescie, 0.055), true, false, entity);
                break;
            case "[MiniBOSS] Elitarny Sluga":
                addDropPlayer(player, AkceItems.A10.getItemStack(), getDropChance(szczescie, 50), true, false, entity);
                addDropPlayer(player, NiesyItems.N10.getItemStack(), getDropChance(szczescie, 10), true, false, entity);
                addDropPlayer(player, Dungeony.I_SAKIEWKA_ULEPSZACZY.getItemStack(), 100, true, true, entity);
                if (rpgcore.getDemoniczneSaleManager().getDungeonStatus() == DungeonStatus.ETAP_2) {
                    rpgcore.getDemoniczneSaleManager().incrementCounter();
                }
                break;
            case "[BOSS] Demon Ciemnosci":
                addDropPlayer(player, Dungeony.I_DEMONICZNE_SALE_SKRZYNKA.getItemStack(), 100, true, true, entity);
                if (rpgcore.getDemoniczneSaleManager().getDungeonStatus() == DungeonStatus.BOSS) {
                    rpgcore.getDemoniczneSaleManager().incrementCounter();
                }
                break;

            //                                            RYBAK WYSPA 2
            case "Wodny Stwor Lvl. 50":
                addDropPlayer(player, new ItemBuilder(Material.WOOL, 1, (short) 10).setName("&5&lKrysztal Czarnoksieznika").toItemStack().clone(), getDropChance(szczescie, 0.1), true, false, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I15.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 5)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I16.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 4)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I17.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 3)).toItemStack().clone(), 100, true, true, entity);
                break;
            case "Nurek Glebinowy Lvl. 90":
                addDropPlayer(player, new ItemBuilder(Material.WOOL, 1, (short) 10).setName("&5&lKrysztal Czarnoksieznika").toItemStack().clone(), getDropChance(szczescie, 2.5), true, false, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I17.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 6)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I18.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 5)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I19.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 3)).toItemStack().clone(), 100, true, true, entity);
                break;
            case "Morski Ksiaze Lvl. 999":
                addDropPlayer(player, new ItemBuilder(Material.WOOL, 1, (short) 10).setName("&5&lKrysztal Czarnoksieznika").toItemStack().clone(), getDropChance(szczescie, 25), true, false, entity);
                addDropPlayer(player, BonType.PRZESZYWKA_20.getBon(), getDropChance(szczescie, 0.01), true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I18.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(10, 15)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I19.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(8, 12)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I19.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(5, 10)).toItemStack().clone(), 100, true, true, entity);
                break;
            case "Posejdon Lvl. 130":
                addDropPlayer(player, new ItemBuilder(Material.WOOL, 1, (short) 10).setName("&5&lKrysztal Czarnoksieznika").toItemStack().clone(), getDropChance(szczescie, 100), true, false, entity);
                addDropPlayer(player, RybakItems.I22.getItemStack().clone(), getDropChance(szczescie, 100), true, false, entity);
                addDropPlayer(player, BonType.WZM_KRYTYK_10.getBon(), getDropChance(szczescie, 1), true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I15.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(32, 64)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I19.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(16, 32)).toItemStack().clone(), 100, true, true, entity);
                addDropPlayer(player, new ItemBuilder(RybakItems.I23.getItemStack().clone()).setAmount(ChanceHelper.getRandInt(1, 3)).toItemStack().clone(), 100, true, true, entity);
                break;
        }

        final double kasa = rpgcore.getLvlManager().getKasa(entityName, uuid);
        rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() + kasa);
        rpgcore.getUserManager().find(uuid).incrementMobKill(entityName);
        if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 7) {
            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(DoubleUtils.round(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + kasa, 2));
        } else if (rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 12) {
            rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(rpgcore.getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
        }
        rpgcore.getLvlManager().updateExp(player, entityName);
    }

    public static double getDropChance(int szczescie, double chance) {
        return DoubleUtils.round(chance + ((chance * szczescie) / 1000.0), 2);
    }

    public static double getDropChance(final UUID uuid, double chance) {
        return DoubleUtils.round(chance + ((chance * RPGCORE.getInstance().getBonusesManager().find(uuid).getBonusesUser().getSzczescie()) / 1000.0), 2);
    }
}
