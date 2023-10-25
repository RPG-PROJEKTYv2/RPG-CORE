package rpg.rpgcore.npc.sezonowiec.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.sezonowiec.SezonowiecNPC;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class SezonowiecInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (title.equals("Sezonowiec")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (slot != 2) return;
            if (user.getSezonowiecPoints() < 1000) {
                player.sendMessage(Utils.format("&e&lSezonowiec &8>> &cNie posiadasz wystarczajacej ilosci punktow!"));
                return;
            }
            user.setSezonowiecPoints(0);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
            SezonowiecNPC.getRewards(player);
        }
    }
}
