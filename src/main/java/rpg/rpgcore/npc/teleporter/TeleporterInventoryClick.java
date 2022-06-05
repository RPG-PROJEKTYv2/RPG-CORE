package rpg.rpgcore.npc.teleporter;

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
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.equals(rpgcore.getTeleporterNPC().teleporterMAIN(player).getName())) {
            if (clickedSlot == 10) {
                if (clickedItem.getType() == Material.GRASS) {
                    player.sendMessage("test teleportacja 1 expo");
                    player.closeInventory();
                }
            }
            if (clickedSlot == 11) {
                if (clickedItem.getType() == Material.STONE) {
                    player.sendMessage("test teleportacja 2 expo");
                    player.closeInventory();
                }
            }
            e.setCancelled(true);
        }

    }

}
