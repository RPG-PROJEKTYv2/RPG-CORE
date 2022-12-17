package rpg.rpgcore.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.NiesyItems;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.globalitems.npc.LowcaItems;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;

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
        final double sakwaDropChance = 0.03 + ((0.03 * szczescie) / 1000.0);
        final double chestDropChance50lvl = 2.5 + ((2.5 * szczescie) / 1000.0);
        final double chestDropChance50plus = 1 + ((2.5 * szczescie) / 1000.0);


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


        // ----------------------------------------- EXPOWISKO 1 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Krol Wygnancow")) {
            addDropPlayer(player, Skrzynki.getItem("I1", 1), 100, true, true, entity);
            // LOWCA
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 1) {
                addDropPlayer(player, LowcaItems.getItem("1-10", 1), getDropChance(szczescie, 60), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 1) {
                rpgcore.getWyslannikNPC().find(uuid).addKillBossMissionProgress();
            }
        }
        // MOB
        if (entityName.equals("Najemnik Lvl. 3") || entityName.equals("Najemnik Lvl. 5") || entityName.equals("Najemnik Lvl. 7")) {
            // SKRZYNKA MOBA
            addDropPlayer(player, Skrzynki.getItem("I2", 1), chestDropChance50lvl, true, true, entity);
            // NIESAMOWITY PRZEDMIOT
            addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance, true, true, entity);
            // LESNIK MISJE
            addDropPlayer(player, LesnikItems.getByItem("I1", 1), getDropChance(szczescie, 1), true, true, entity);
            // PRZYRODNIK MISJE
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 0) {
                addDropPlayer(player, PrzyrodnikItems.getByName("1-10").getItemStack(), getDropChance(szczescie, 2.5), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 1) {
               rpgcore.getWyslannikNPC().find(uuid).addKillMobsMissionProgress();
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
            addDropPlayer(player, Skrzynki.getItem("I2", 1), 100, true, true, entity);
            // LOWCA
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 2) {
                addDropPlayer(player, LowcaItems.getItem("10-20", 1), getDropChance(szczescie, 60), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getKillBossMission() == 2) {
                rpgcore.getWyslannikNPC().find(uuid).addKillBossMissionProgress();
            }
        }
        // MOB
        if (entityName.equals("Goblin Lvl. 14") || entityName.equals("Goblin Lvl. 16") || entityName.equals("Goblin Lvl. 19")) {
            // SKRZYNKA MOBA
            addDropPlayer(player, Skrzynki.getItem("I4", 1), chestDropChance50lvl, true, true, entity);
            // NIESAMOWITY PRZEDMIOT
            addDropPlayer(player, NiesyItems.N2.getItemStack(), niesDropChance, true, true, entity);
            // LESNIK MISJE
            addDropPlayer(player, LesnikItems.getByItem("I2", 1), getDropChance(szczescie, 1), true, true, entity);
            // PRZYRODNIK MISJE
            if (rpgcore.getPrzyrodnikNPC().find(uuid).getUser().getMission() == 1) {
                addDropPlayer(player, PrzyrodnikItems.getByName("10-20").getItemStack(), getDropChance(szczescie, 2.5), true, true, entity);
            }
            if (rpgcore.getWyslannikNPC().find(uuid).getKillMobsMission() == 2) {
                rpgcore.getWyslannikNPC().find(uuid).addKillMobsMissionProgress();
            }
            // DUSZOLOG MISJE
            /*if (rpgcore.getDuszologNPC().find(uuid).getDuszologUser().getMission() == 1) {
                if (ChanceHelper.getChance(100)) {
                    rpgcore.getDuszologNPC().spawnDusza(player, entity);
                }
            }*/
        }
        // ----------------------------------------- EXPOWISKO 2 -----------------------------------------
        // BOSS

        // MOB
        rpgcore.getLvlManager().updateExp(player, entityName);
    }

    private static double getDropChance(int szczescie, double chance) {
        return chance + ((chance * szczescie) / 1000.0);
    }
}
