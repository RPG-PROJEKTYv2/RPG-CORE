package rpg.rpgcore.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class MobDropHelper {

    public static void addDropPlayer(Player player, String name, int value, double chance, boolean message, boolean pickup, Entity entity) {
        if (pickup) {
            if (DropChanceHelper.getChance(chance)) {
                player.getInventory().addItem(GlobalItem.getItem(name, value));
                if (message) {
                    player.sendMessage(Utils.format("&8[&2+&8] &7x &f" + value + " " + GlobalItem.getItem(name, value).getItemMeta().getDisplayName()));
                }
            }
        } else {
            if (DropChanceHelper.getChance(chance)) {
                entity.getWorld().dropItem(entity.getLocation(), GlobalItem.getItem(name, value));
                if (message) {
                    player.sendMessage(Utils.format(("&8[&6&lDROP&8] &7x &f" + value + " " + GlobalItem.getItem(name, value).getItemMeta().getDisplayName())));
                }
            }
        }
    }
}
