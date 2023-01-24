package rpg.rpgcore.commands.player.enderchest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class EnderChestInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).contains("EnderChest gracza ")) {
            final String player = Utils.removeColor(e.getInventory().getTitle()).replace("EnderChest gracza ", "");
            final User user = RPGCORE.getInstance().getUserManager().find(player);

            if (user == null) {
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cCos poszlo nie tak podczas zapisu endechesta gracza &6" + player + "&c! &4(USER IS NULL)"));
                return;
            }

            user.getInventoriesUser().setEnderchest(Utils.itemStackArrayToBase64(e.getInventory().getContents()));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zapisano enderchest gracza &6" + player + "&a!"));
        }
    }
}
