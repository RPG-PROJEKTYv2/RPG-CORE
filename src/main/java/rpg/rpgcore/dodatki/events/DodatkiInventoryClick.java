package rpg.rpgcore.dodatki.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class DodatkiInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Menu Dodatkow")) {
            e.setCancelled(true);
            if (slot == 10) {
                RPGCORE.getInstance().getDodatkiManager().openAkcePodsGUI(player, player);
                return;
            }
            if (slot == 13) {
                RPGCORE.getInstance().getDodatkiManager().openBonyGUI(player, player);
                return;
            }
            if (slot == 16) {
                RPGCORE.getInstance().getDodatkiManager().openAkceDodaGUI(player, player);
            }
        }
    }

}
