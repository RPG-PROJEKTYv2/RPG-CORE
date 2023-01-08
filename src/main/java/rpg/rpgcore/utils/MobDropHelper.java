package rpg.rpgcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.npc.przyrodnik.Missions;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.NiesyItems;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.globalitems.npc.LowcaItems;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
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

        if (rpgcore.getMedykNPC().find(uuid).getMedykUser().getBonus() < 50) {
            rpgcore.getMedykNPC().find(uuid).getMedykUser().setProgress(rpgcore.getMedykNPC().find(uuid).getMedykUser().getProgress() + 1);
        }

        final int szczescie = rpgcore.getBonusesManager().find(uuid).getBonusesUser().getSzczescie();
        final double niesDropChance50lvl = 0.02 + ((0.02 * szczescie) / 1000.0);
        final double niesDropChance50plus = 0.01 + ((0.01 * szczescie) / 1000.0);
        final double chestDropChance50lvl = 2.5 + ((2.5 * szczescie) / 1000.0);
        final double chestDropChance50plus = 1.25 + ((1.25 * szczescie) / 1000.0);
        final double sakwaDropChance = 0.03 + ((0.03 * szczescie) / 1000.0);


        rpgcore.getOsManager().find(uuid).setMobyProgress(rpgcore.getOsManager().find(uuid).getMobyProgress() + 1);

        // -------------------- SKRZYNKI PODSTAWOWE --------------------
        // TODO PODSTAWOWY HELLCASE DROPI TYLKO Z KUFRA WARTOSCIOWEGO BADZ MISJI :]
        // WARTOSCIOWY KUFER
        addDropPlayer(player, GlobalItem.getItem("I1", 1), getDropChance(szczescie, 0.005), true, true, entity);
        // SKRZYNIA KOWALA
        addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.025), true, true, entity);
        // SKRZYNIA ZE ZWIERZAKAMI
        addDropPlayer(player, GlobalItem.getItem("I3", 1), getDropChance(szczescie, 0.001), true, true, entity);
        // TAJEMNICZA SKRZYNIA
        addDropPlayer(player, GlobalItem.getItem("I4", 1), getDropChance(szczescie, 1.0), true, true, entity);
        // SKRZYNIA Z SUROWCAMI
        addDropPlayer(player, GlobalItem.getItem("I5", 1), getDropChance(szczescie, 2.0), true, true, entity);
        // LESNIK NPC
        addDropPlayer(player, LesnikItems.getByItem("I1", 1), getDropChance(szczescie, 1), true, true, entity);
        // FRAGMENT STALI
        addDropPlayer(player, GlobalItem.getItem("I_FRAGMENT_STALI", 1), getDropChance(szczescie, 0.4), true, true, entity);


        final Missions przyrodnikMission = Missions.getByNumber(rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission());

        // ----------------------------------------- EXPOWISKO 1 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Krol Wygnancow")) {
            addDropPlayer(player, Skrzynki.getItem("I1", 1), 100, true, true, entity);
            rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-1-10 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
            // LOWCA
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 1) {
                addDropPlayer(player, LowcaItems.getItem("1-10", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 1) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Najemnik Lvl. 3") || entityName.equals("Najemnik Lvl. 5") || entityName.equals("Najemnik Lvl. 7")) {
            // SKRZYNKA MOBA
            addDropPlayer(player, Skrzynki.getItem("I2", 1), chestDropChance50lvl, true, true, entity);
            // NIESAMOWITY PRZEDMIOT
            addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance50lvl, true, false, entity);
            // Ulepszacze
            addDropPlayer(player, GlobalItem.getItem("I_SZATANAJEMNIKA", 1), getDropChance(szczescie, 2.5), true, true, entity);
            // PRZYRODNIK MISJE
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 0) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("1-10")).getItemStack(), getDropChance(szczescie, 1), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 1) {
               rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 0) {
                addDropPlayer(player, PrzyrodnikItems.getItem("1-10"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }

            // DUSZOLOG MISJE
            /*if (rpgcore.getDuszologNPC().find(uuid).getDuszologUser().getMission() == 0) {
                if (ChanceHelper.getChance(100)) {
                    rpgcore.getDuszologNPC().spawnDusza(player, entity);
                }
            }*/
        }
        // ----------------------------------------- EXPOWISKO 2 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Wodz Goblinow")) {
            addDropPlayer(player, Skrzynki.getItem("I3", 1), 100, true, true, entity);
            rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-10-20 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 2) {
                addDropPlayer(player, LowcaItems.getItem("10-20", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 2) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Goblin Lvl. 14") || entityName.equals("Goblin Lvl. 16") || entityName.equals("Goblin Lvl. 19")) {
            addDropPlayer(player, Skrzynki.getItem("I4", 1), chestDropChance50lvl, true, true, entity);
            addDropPlayer(player, NiesyItems.N2.getItemStack(), niesDropChance50lvl, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_OKOGOBLINA", 1), getDropChance(szczescie, 2.0), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 1) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("10-20")).getItemStack(), getDropChance(szczescie, 0.70), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 2) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 1) {
                addDropPlayer(player, PrzyrodnikItems.getItem("10-20"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 3 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Krol Goryli")) {
            addDropPlayer(player, Skrzynki.getItem("I5", 1), 100, true, true, entity);
            rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-20-30 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 3) {
                addDropPlayer(player, LowcaItems.getItem("20-30", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 3) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Goryl Lvl. 21") || entityName.equals("Goryl Lvl. 25") || entityName.equals("Goryl Lvl. 28")) {
            addDropPlayer(player, Skrzynki.getItem("I6", 1), chestDropChance50lvl, true, true, entity);
            addDropPlayer(player, NiesyItems.N3.getItemStack(), niesDropChance50lvl, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_SKORAGORYLA", 1), getDropChance(szczescie, 1.6), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 2) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("20-30")).getItemStack(), getDropChance(szczescie, 0.70), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 3) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 2) {
                addDropPlayer(player, PrzyrodnikItems.getItem("20-30"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 4 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Przekleta Dusza")) {
            addDropPlayer(player, Skrzynki.getItem("I7", 1), 100, true, true, entity);
            rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-30-40 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 4) {
                addDropPlayer(player, LowcaItems.getItem("30-40", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 4) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Zjawa Lvl. 32") || entityName.equals("Zjawa Lvl. 36") || entityName.equals("Zjawa Lvl. 39")) {
            addDropPlayer(player, Skrzynki.getItem("I8", 1), chestDropChance50lvl, true, true, entity);
            addDropPlayer(player, NiesyItems.N4.getItemStack(), niesDropChance50lvl, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_PROCHYZJAWY", 1), getDropChance(szczescie, 2.5), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 3) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("30-40")).getItemStack(), getDropChance(szczescie, 0.70), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 4) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 3) {
                addDropPlayer(player, PrzyrodnikItems.getItem("30-40"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 5 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Tryton")) {
            addDropPlayer(player, Skrzynki.getItem("I9", 1), 100, true, true, entity);
            rpgcore.getServer().dispatchCommand(Bukkit.getConsoleSender(), "holo setLine boss-40-50 3 &cData ostatniego zabicia: &6" + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date()));
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 5) {
                addDropPlayer(player, LowcaItems.getItem("40-50", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 5) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Straznik Swiatyni Lvl. 43") || entityName.equals("Straznik Swiatyni Lvl. 46") || entityName.equals("Straznik Swiatyni Lvl. 47")) {
            addDropPlayer(player, Skrzynki.getItem("I10", 1), chestDropChance50lvl, true, true, entity);
            addDropPlayer(player, NiesyItems.N5.getItemStack(), niesDropChance50lvl, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_LZAOCEANU", 1), getDropChance(szczescie, 2.5), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 4) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("40-50")).getItemStack(), getDropChance(szczescie, 0.70), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 5) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 4) {
                addDropPlayer(player, PrzyrodnikItems.getItem("40-50"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 6 -----------------------------------------
        // BOSS
        // TODO JEST BOSS Z DT I PODPIETE NA SAMYM DOLE
        // MOB
        if (entityName.equals("Mrozny Wilk Lvl. 52") || entityName.equals("Mrozny Wilk Lvl. 54") || entityName.equals("Mrozny Wilk Lvl. 56")) {
            addDropPlayer(player, Skrzynki.getItem("I12", 1), chestDropChance50lvl, true, true, entity);
            addDropPlayer(player, NiesyItems.N6.getItemStack(), niesDropChance50lvl, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_MROZNYPAZUR", 1), getDropChance(szczescie, 2.0), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 5) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("50-60")).getItemStack(), getDropChance(szczescie, 0.70), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 6) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 5) {
                addDropPlayer(player, PrzyrodnikItems.getItem("50-60"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 7 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Piekielny Rycerz")) {
            addDropPlayer(player, Skrzynki.getItem("I13", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 7) {
                addDropPlayer(player, LowcaItems.getItem("60-70", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 7) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Zywiolak Ognia Lvl. 64") || entityName.equals("Zywiolak Ognia Lvl. 66") || entityName.equals("Zywiolak Ognia Lvl. 68")) {
            addDropPlayer(player, Skrzynki.getItem("I14", 1), chestDropChance50plus, true, true, entity);
            addDropPlayer(player, NiesyItems.N7.getItemStack(), niesDropChance50plus, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_OGNISTYPYL", 1), getDropChance(szczescie, 1.5), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 6) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("60-70")).getItemStack(), getDropChance(szczescie, 0.60), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 7) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 6) {
                addDropPlayer(player, PrzyrodnikItems.getItem("60-70"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 8 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Przeklety Czarnoksieznik")) {
            addDropPlayer(player, Skrzynki.getItem("I15", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 8) {
                addDropPlayer(player, LowcaItems.getItem("70-80", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 8) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Mroczna Dusza Lvl. 71") || entityName.equals("Mroczna Dusza Lvl. 75") || entityName.equals("Mroczna Dusza Lvl. 78")) {
            addDropPlayer(player, Skrzynki.getItem("I16", 1), chestDropChance50plus, true, true, entity);
            addDropPlayer(player, NiesyItems.N8.getItemStack(), niesDropChance50plus, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_TRUJACAROSLINA", 1), getDropChance(szczescie, 1.5), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 7) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("70-80")).getItemStack(), getDropChance(szczescie, 0.40), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 8) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 7) {
                addDropPlayer(player, PrzyrodnikItems.getItem("70-80"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 10 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Mityczny Pajak")) {
            addDropPlayer(player, Skrzynki.getItem("I17", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 9) {
                addDropPlayer(player, LowcaItems.getItem("80-90", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 9) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Pustynny Ptasznik Lvl. 84") || entityName.equals("Pustynny Ptasznik Lvl. 87") || entityName.equals("Pustynny Ptasznik Lvl. 89")) {
            addDropPlayer(player, NiesyItems.N9.getItemStack(), niesDropChance50plus, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_JADPTASZNIKA", 1), getDropChance(szczescie, 1.4), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 8) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("80-90")).getItemStack(), getDropChance(szczescie, 0.30), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 9) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 8) {
                addDropPlayer(player, PrzyrodnikItems.getItem("80-90"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 11 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Podziemny Rozpruwacz")) {
            addDropPlayer(player, Skrzynki.getItem("I19", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 10) {
                addDropPlayer(player, LowcaItems.getItem("90-100", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 10) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Podziemna Lowczyni Lvl. 92") || entityName.equals("Podziemna Lowczyni Lvl. 95") || entityName.equals("Podziemna Lowczyni Lvl. 98")) {
            addDropPlayer(player, NiesyItems.N10.getItemStack(), niesDropChance50plus, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_MROCZNYMATERIAL", 1), getDropChance(szczescie, 1.35), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 9) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("90-100")).getItemStack(), getDropChance(szczescie, 0.15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 10) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 9) {
                addDropPlayer(player, PrzyrodnikItems.getItem("90-100"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 12 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Mityczny Kraken")) {
            addDropPlayer(player, Skrzynki.getItem("I21", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 11) {
                addDropPlayer(player, LowcaItems.getItem("100-110", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 11) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Podwodny Straznik Lvl. 104") || entityName.equals("Podwodny Straznik Lvl. 106") || entityName.equals("Podwodny Straznik Lvl. 109")) {
            addDropPlayer(player, NiesyItems.N11.getItemStack(), niesDropChance50plus, true, false, entity);
            addDropPlayer(player, GlobalItem.getItem("I_SZAFIROWESERCE", 1), getDropChance(szczescie, 2.0), true, true, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 10) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("100-110")).getItemStack(), getDropChance(szczescie, 0.10), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 11) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 10) {
                addDropPlayer(player, PrzyrodnikItems.getItem("100-110"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 13 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Hades")) {
            addDropPlayer(player, Skrzynki.getItem("I23", 1), 100, true, true, entity);
            addDropPlayer(player, GlobalItem.getItem("I_SERCEDEMONA", 1), getDropChance(szczescie, 1.5), true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 12) {
                addDropPlayer(player, LowcaItems.getItem("110-120", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 12) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Centaur Lvl. 114") || entityName.equals("Centaur Lvl. 116") || entityName.equals("Centaur Lvl. 118")) {
            addDropPlayer(player, NiesyItems.N12.getItemStack(), niesDropChance50plus, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 11) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("110-120")).getItemStack(), getDropChance(szczescie, 0.08), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 12) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 11) {
                addDropPlayer(player, PrzyrodnikItems.getItem("110-120"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 14 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Archaniol")) {
            addDropPlayer(player, Skrzynki.getItem("I25", 1), 100, true, true, entity);
            addDropPlayer(player, GlobalItem.getItem("I_NIEBIANSKIMATERIAL", 1), getDropChance(szczescie, 1.5), true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 13) {
                addDropPlayer(player, LowcaItems.getItem("120-130", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 13) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
        }
        // MOB
        if (entityName.equals("Straznik Nieba Lvl. 123") || entityName.equals("Straznik Nieba Lvl. 126") || entityName.equals("Straznik Nieba Lvl. 129")) {
            addDropPlayer(player, NiesyItems.N13.getItemStack(), niesDropChance50plus, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 12) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("120-130")).getItemStack(), getDropChance(szczescie, 0.04), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 13) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 12) {
                addDropPlayer(player, PrzyrodnikItems.getItem("120-130"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- ICE TOWER -----------------------------------------
        if (entityName.equals("Lodowy Sluga Lvl. 57")) {
            addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
            if (!player.getWorld().getName().equals("demontower")) return;
            RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 50) {
                IceTowerManager.actionBar(50, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f50");
            }
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 50) {
                IceTowerManager.startIceTowerEtap2();
            }
        }
        if (entityName.equals("Lodowy Sluga Lvl. 58")) {
            addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
            if (!player.getWorld().getName().equals("demontower")) return;
            RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 80) {
                IceTowerManager.actionBar(80, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f80");
            }
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 80) {
                IceTowerManager.startIceTowerEtapBOSS();
            }
        }
        if (entityName.equals("Lodowy Sluga Lvl. 59")) {
            addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.05), true, true, entity);
            if (!player.getWorld().getName().equals("demontower")) return;
            RPGCORE.getInstance().getIceTowerManager().setMobsAmount(RPGCORE.getInstance().getIceTowerManager().getMobsAmount() + 1);
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() < 80) {
                IceTowerManager.actionBar(80, "&b&lPostep Lodowej Wiezy: &f" + rpgcore.getIceTowerManager().getMobsAmount() + "&b/&f80");
            }
            if (RPGCORE.getInstance().getIceTowerManager().getMobsAmount() == 80) {
                IceTowerManager.startIceTowerEtap3();
            }
        }
        if (entityName.equals("[BOSS] Mrozny Wladca")) {
            addDropPlayer(player, Skrzynki.getItem("I_DEMONTOWER_BOSS", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 6) {
                addDropPlayer(player, LowcaItems.getItem("50-60", 1), getDropChance(szczescie, 15), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMission() == 6) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillBossMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillBossMissionProgress() + 1);
            }
            if (!player.getWorld().getName().equals("demontower")) return;
            IceTowerManager.teleportKowal();
        }

        // TODO Dodac drop Czastki Magii powyzej mobow 60Lvl

        rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() + rpgcore.getLvlManager().getKasa(entityName));
        rpgcore.getLvlManager().updateExp(player, entityName);
    }

    private static double getDropChance(int szczescie, double chance) {
        return chance + ((chance * szczescie) / 1000.0);
    }
}
