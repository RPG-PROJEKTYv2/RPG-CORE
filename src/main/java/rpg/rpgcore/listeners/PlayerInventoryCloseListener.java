package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;


public class PlayerInventoryCloseListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        if (e.getInventory().getName().equals(Utils.format("&4&lEQ GUI"))) {
            rpgcore.getChatManager().updateMessageWithEQ(e.getPlayer().getUniqueId(), "");
            return;
        }

        if (Utils.removeColor(e.getInventory().getName()).contains("Wymiana ")) {
            for (int i = 0; i < e.getViewers().size(); i++) {
                final Player viewer = (Player) e.getViewers().get(i);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, viewer::closeInventory, 1L);
                if (rpgcore.getTradeManager().isInTradeMapAsKey(e.getPlayer().getUniqueId())) {
                    rpgcore.getTradeManager().getTradeMap().remove(e.getPlayer().getUniqueId());
                }
                rpgcore.getTradeManager().getTradeMap().remove(viewer.getUniqueId(), e.getPlayer().getUniqueId());
            }
        }
    }
}
