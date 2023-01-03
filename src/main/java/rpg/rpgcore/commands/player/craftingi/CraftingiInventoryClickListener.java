package rpg.rpgcore.commands.player.craftingi;

import org.bukkit.Material;
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

public class CraftingiInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("Craftingi")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE)  return;
            if (slot == 10) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingAnielskie(player);
                return;
            }
            if (slot == 12) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingDiabelskie(player);
                return;
            }
            if (slot == 14) {
                RPGCORE.getInstance().getCraftingiManager().openCratingSwietlnaZbroja(player);
                return;
            }
            if (slot == 16) {
                RPGCORE.getInstance().getCraftingiManager().openCraftingiInne(player);
                return;
            }
        }

        if (title.equals("Craftingi » Anielskie Akce")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE)  return;
        }

        if (title.equals("Craftingi » Diabelskie Akce")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE)  return;
        }

        if (title.equals("Craftingi » Swietlne EQ")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE)  return;
        }

        if (title.equals("Craftingi » Inne")) {
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE)  return;
        }
    }
}
