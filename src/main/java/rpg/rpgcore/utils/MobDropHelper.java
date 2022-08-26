package rpg.rpgcore.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.kolekcjoner.KolekcjonerUser;

import java.util.UUID;

public class MobDropHelper {

    public static void addDropPlayer(Player player, ItemStack is, double chance, boolean message, boolean pickup, Entity entity) {
        if (pickup) {
            if (DropChanceHelper.getChance(chance)) {
                player.getInventory().addItem(is);
                if (message) {
                    player.sendMessage(Utils.format("&2+ &7x &f" + is.getAmount() + " " + is.getItemMeta().getDisplayName()));
                }
            }
        } else {
            if (DropChanceHelper.getChance(chance)) {
                entity.getWorld().dropItem(entity.getLocation(), is);
                if (message) {
                    player.sendMessage(Utils.format(("&8[&b&lDROP&8] &7x &f" + is.getAmount() + " " + is.getItemMeta().getDisplayName())));
                }
            }
        }
    }

    public static void dropFromMob(final Player player, final Entity entity) {
        final RPGCORE rpgcore = RPGCORE.getInstance();
        final UUID uuid = player.getUniqueId();
        final String entityName = Utils.removeColor(entity.getName());
        final KolekcjonerUser kolekcjonerUser = rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser();

        if (rpgcore.getMedykNPC().find(uuid).getMedykUser().getBonus() < 50) {
            rpgcore.getMedykNPC().find(uuid).getMedykUser().setProgress(rpgcore.getMedykNPC().find(uuid).getMedykUser().getProgress() + 1);
        }

        double niesDropChance = 0.01;
        double sakwaDropChance = 0.03;
        double kolekcjonerDropChance = 1.0;
        double chestDropChance = 1;
        double ulepszaczDropChance = 0.5;

        // TU BEDA PRZELICZNIKI I MNOZNIKI Z SETOW/MIECZOW/ULEPSZEN ITD.

        rpgcore.getOsManager().find(uuid).getOsUser().setMobKills(rpgcore.getOsManager().find(uuid).getOsUser().getMobKills() + 1);

        // TUTAJ ZROB DROP SKRZYNNEK TYCH CO MAJA DROPIC Z KAZDEGO MOBA ( TAK JAK NA HYPE BYL DROP SKRZYNI Z MATERIALAMI Z KAZDEGO MOBA ) FUNCKJA addDropPlayer()


        if (entityName.equals("Tulacz")) {
            if (kolekcjonerUser.getMission() == 0) {
                addDropPlayer(player, GlobalItem.getItem("K1", 1), kolekcjonerDropChance, true, true, entity); // TU ZMIEN ITEMY I DODAJ DO GLOBAL ITEMS ALBO ZROB KOLEJNY ENUM Z ITEMAMI TYLKO OD KOLECJONERA
            }

        }




        rpgcore.getLvlManager().updateExp(player, entityName);
    }
}
