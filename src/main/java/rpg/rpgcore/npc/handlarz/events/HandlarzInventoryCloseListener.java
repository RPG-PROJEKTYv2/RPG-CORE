package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class HandlarzInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e){
        if (e.getInventory() == null) return;
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Handlarz » Sprzedaz")) {
            if (rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()).isEmpty()) return;

            for (final ItemStack item : rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()).keys()) {
                if (item == null) continue;
                e.getPlayer().getInventory().addItem(item);
            }

            e.getPlayer().sendMessage(Utils.format("&6&lHandlarz &8>> &cAnulowales sprzedawanie &6" +
                    rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()).entries().stream().mapToInt(entry -> entry.getKey().getAmount()).sum() + " &cprzedmiotow i zostaly one dodane do twojego ekwipunku."));
            rpgcore.getHandlarzNPC().removeUserItemMap(e.getPlayer().getUniqueId());
        }
    }
}
