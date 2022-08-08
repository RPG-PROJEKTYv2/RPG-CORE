package rpg.rpgcore.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;

public class MobDropHelper {

    public static void addDropPlayer(Player player, ItemStack is, double chance, boolean message, boolean pickup, Entity entity) {
        if (pickup) {
            if (DropChanceHelper.getChance(chance)) {
                player.getInventory().addItem(is);
                if (message) {
                    player.sendMessage(Utils.format("&8[&2+&8] &7x &f" + is.getAmount() + " " + is.getItemMeta().getDisplayName()));
                }
            }
        } else {
            if (DropChanceHelper.getChance(chance)) {
                entity.getWorld().dropItem(entity.getLocation(), is);
                if (message) {
                    player.sendMessage(Utils.format(("&8[&6&lDROP&8] &7x &f" + is.getAmount() + " " + is.getItemMeta().getDisplayName())));
                }
            }
        }
    }
}
