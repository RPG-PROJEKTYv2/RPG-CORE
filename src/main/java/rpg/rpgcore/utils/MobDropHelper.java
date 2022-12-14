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
        final double niesDropChance = 0.01 + ((0.01 * szczescie) / 100.0);
        final double sakwaDropChance = 0.03 + ((0.03 * szczescie) / 100.0);
        final double chestDropChance = 1 + ((1 * szczescie) / 100.0);
        final double ulepszaczDropChance = 0.5 + ((0.5 * szczescie) / 100.0);

        // TU BEDA PRZELICZNIKI I MNOZNIKI Z SETOW/MIECZOW/ULEPSZEN ITD.

        rpgcore.getOsManager().find(uuid).getOsUser().setMobKills(rpgcore.getOsManager().find(uuid).getOsUser().getMobKills() + 1);

        // TUTAJ ZROB DROP SKRZYNNEK TYCH CO MAJA DROPIC Z KAZDEGO MOBA ( TAK JAK NA HYPE BYL DROP SKRZYNI Z MATERIALAMI Z KAZDEGO MOBA ) FUNCKJA addDropPlayer()
        addDropPlayer(player, GlobalItem.getItem("I1", 1), 0.1, true, true, entity);
        addDropPlayer(player, GlobalItem.getItem("I2", 1), 0.05, true, true, entity);
        addDropPlayer(player, GlobalItem.getItem("I3", 1), 0.001, true, true, entity);
        addDropPlayer(player, GlobalItem.getItem("I4", 1), 0.7, true, true, entity);
        addDropPlayer(player, GlobalItem.getItem("I5", 1), 0.5, true, true, entity);

        // ----------------------------------------- EXPOWISKO 1 -----------------------------------------
        // BOSS
        if (entityName.equals("[BOSS] Krol Wygnancow")) {
            addDropPlayer(player, Skrzynki.getItem("I1", 1), 100, true, true, entity);
            if (rpgcore.getLowcaNPC().find(uuid).getLowcaUser().getMission() == 0) {
                addDropPlayer(player, LowcaItems.getItem("I1", 1), 100, true, true, entity);
            }
        }
        // MOB
        if (entityName.equals("Najemnik Lvl. 3") || entityName.equals("Najemnik Lvl. 5") || entityName.equals("Najemnik Lvl. 7")) {
            player.sendMessage("ZABILES NAJEMNICZKA");
            addDropPlayer(player, Skrzynki.getItem("I2", 1), 1, true, true, entity);
            addDropPlayer(player, NiesyItems.N1.getItemStack(), niesDropChance, true, true, entity);
            addDropPlayer(player, LesnikItems.getByItem("I1", 1), 3, true, true, entity);
           // if (rpgcore.getPrzyrodnikNPC().find(uuid))

            if (rpgcore.getDuszologNPC().find(uuid).getDuszologUser().getMission() == 0) {
                if (ChanceHelper.getChance(100)) {
                    rpgcore.getDuszologNPC().spawnDusza(player, entity);
                }
            }
        }
        // ----------------------------------------- EXPOWISKO 2 -----------------------------------------
        // BOSS
        // MOB




        rpgcore.getLvlManager().updateExp(player, entityName);
    }
}
