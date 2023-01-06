package rpg.rpgcore.npc.kowal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class KowalInventoryCloseListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Kowal - Ulepszanie")) {
            if (RPGCORE.getInstance().getKowalNPC().getOstatniUlepszonyItem(e.getPlayer().getUniqueId()) != null &&
            !RPGCORE.getInstance().getKowalNPC().getOstatniUlepszonyItem(e.getPlayer().getUniqueId()).isEmpty()) {
                for (ItemStack is : RPGCORE.getInstance().getKowalNPC().getOstatniUlepszonyItem(e.getPlayer().getUniqueId())) {
                    e.getPlayer().getInventory().addItem(is);
                }
                RPGCORE.getInstance().getKowalNPC().clearOstatniUlepszonyItem(e.getPlayer().getUniqueId());
            }
        }
    }
}
