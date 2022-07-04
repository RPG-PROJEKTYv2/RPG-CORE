package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;

import java.util.HashMap;
import java.util.UUID;

public class TeleporterInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TeleporterInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void teleporterInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        // HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.equals(rpgcore.getTeleporterNPC().teleporterMAIN(player).getName())) {
            if (clickedSlot == 0) {
                if (clickedItem.getType() == Material.IRON_FENCE) {
                    Location locEXP1 = new Location(Bukkit.getWorld("Expowisko1"), 1, 5, 30);
                    player.teleport(locEXP1);
                    player.closeInventory();
                }
            }
            if (clickedSlot == 1) {
                if (clickedItem.getType() == Material.RED_MUSHROOM) {
                    Location locEXP2 = new Location(Bukkit.getWorld("Expowisko1"), 1, 5, 30);
                    player.teleport(locEXP2);
                    player.closeInventory();
                }
            }
            e.setCancelled(true);
        }

    }

}
