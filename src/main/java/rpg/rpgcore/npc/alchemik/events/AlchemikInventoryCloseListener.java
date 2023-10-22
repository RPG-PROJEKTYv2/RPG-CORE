package rpg.rpgcore.npc.alchemik.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

public class AlchemikInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Alchemik » Ulepszanie")) {
            if (!e.getInventory().getItem(11).equals(new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Alchemicki Krysztal").toItemStack())) {
                e.getPlayer().getInventory().addItem(e.getInventory().getItem(11));
                e.getInventory().setItem(11, new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na Alchemicki Krysztal").toItemStack());
            }
        }
    }
}
