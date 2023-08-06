package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PrzekletyOdlamekInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void przekletamapaInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final int slot = e.getSlot();
        final Player player = (Player) e.getWhoClicked();

        if (Utils.removeColor(clickedInventoryTitle).equals("Przeklety Odlamek - wybor")) {
            e.setCancelled(true);
            if (slot == 3) {
                RPGCORE.getInstance().getPrzekletyCzarnoksieznikBossManager().LosBONUS(player);
                player.closeInventory();
                return;
            }
            if (slot == 8) {
                player.closeInventory();
            }
        }
    }
}
