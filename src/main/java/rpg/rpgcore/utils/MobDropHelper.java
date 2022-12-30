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
        final double niesDropChance = 0.01 + ((0.01 * szczescie) / 1000.0);
        //final double sakwaDropChance = 0.03 + ((0.03 * szczescie) / 1000.0);
        final double chestDropChance50lvl = 2.5 + ((2.5 * szczescie) / 1000.0);
        //final double chestDropChance50plus = 1 + ((2.5 * szczescie) / 1000.0);


        rpgcore.getOsManager().find(uuid).getOsUser().setMobKills(rpgcore.getOsManager().find(uuid).getOsUser().getMobKills() + 1);

        // -------------------- SKRZYNKI PODSTAWOWE --------------------
        // PRZELICZNIK -> 0.1 - OKOŁO CO TYSIAC MOBOW /// 0.01 - OKOLO CO 10K MOBOW /// 0.001 - OKOLO CO 100k MOBOW
        // TODO PODSTAWOWY HELLCASE DROPI TYLKO Z KUFRA WARTOSCIOWEGO BADZ MISJI :]
        // WARTOSCIOWY KUFER
        addDropPlayer(player, GlobalItem.getItem("I1", 1), getDropChance(szczescie, 0.005), true, true, entity);
        // SKRZYNIA KOWALA
        addDropPlayer(player, GlobalItem.getItem("I2", 1), getDropChance(szczescie, 0.025), true, true, entity);
        // SKRZYNIA ZE ZWIERZAKAMI ZWYKLA
        addDropPlayer(player, GlobalItem.getItem("I3", 1), getDropChance(szczescie, 0.001), true, true, entity);
        // TAJEMNICZA SKRZYNIA
        addDropPlayer(player, GlobalItem.getItem("I4", 1), getDropChance(szczescie, 1.0), true, true, entity);
        // SKRZYNIA Z SUROWCAMI
        addDropPlayer(player, GlobalItem.getItem("I5", 1), getDropChance(szczescie, 2.0), true, true, entity);
        // LESNIK NPC
        addDropPlayer(player, LesnikItems.getByItem("I1", 1), getDropChance(szczescie, 1), true, true, entity);


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
            addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance, true, false, entity);
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
            addDropPlayer(player, NiesyItems.N2.getItemStack(), niesDropChance, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 1) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("10-20")).getItemStack(), getDropChance(szczescie, 1), true, true, entity);
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
            addDropPlayer(player, NiesyItems.N3.getItemStack(), niesDropChance, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 2) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("20-30")).getItemStack(), getDropChance(szczescie, 1), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 3) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 2) {
                addDropPlayer(player, PrzyrodnikItems.getItem("20-30"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 3 -----------------------------------------
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
            addDropPlayer(player, NiesyItems.N4.getItemStack(), niesDropChance, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 3) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("30-40")).getItemStack(), getDropChance(szczescie, 1), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 4) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 3) {
                addDropPlayer(player, PrzyrodnikItems.getItem("30-40"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }
        // ----------------------------------------- EXPOWISKO 3 -----------------------------------------
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
            addDropPlayer(player, NiesyItems.N5.getItemStack(), niesDropChance, true, false, entity);
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 4) {
                addDropPlayer(player, Objects.requireNonNull(PrzyrodnikItems.getByName("40-50")).getItemStack(), getDropChance(szczescie, 1), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMission() == 5) {
                rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().setKillMobsMissionProgress(rpgcore.getWyslannikNPC().find(uuid).getWyslannikUser().getKillMobsMissionProgress() + 1);
            }
            if (przyrodnikMission.getNumber() == 4) {
                addDropPlayer(player, PrzyrodnikItems.getItem("40-50"), getDropChance(szczescie, przyrodnikMission.getDropChance()), true, true, entity);
            }
        }



        // ----------------------------------------- ICE TOWER -----------------------------------------
        if (entityName.equals("Lodowy Sluga Lvl. 57")) {
            //TODO dodac expa
            //TODO dodac drop jakichs skrzynek (moze kowala z wiekszym % na drop ?)
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
            //TODO dodac expa
            //TODO dodac drop jakichs skrzynek (moze kowala z wiekszym % na drop ?)
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
            //TODO dodac expa
            //TODO dodac drop jakichs skrzynek (moze kowala z wiekszym % na drop ?)
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
            //TODO dodac expa
            //TODO dodac skrzynke z bossa
            if (!player.getWorld().getName().equals("demontower")) return;
            IceTowerManager.teleportKowal();
        }

        rpgcore.getUserManager().find(uuid).setKasa(rpgcore.getUserManager().find(uuid).getKasa() + rpgcore.getLvlManager().getKasa(entityName));
        rpgcore.getLvlManager().updateExp(player, entityName);
    }

    private static double getDropChance(int szczescie, double chance) {
        return chance + ((chance * szczescie) / 1000.0);
    }
}
