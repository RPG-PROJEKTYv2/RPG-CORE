package rpg.rpgcore.npc.magazynier;

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

import java.util.HashMap;
import java.util.UUID;

public class MagazynierInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Magazynier")) {
            e.setCancelled(true);
            if (clickedSlot == 0) {
                rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
                return;
            }
            if (clickedSlot == 4) {
                // OPEN KAMPANIA
                return;
            }

            return;
        }

        if (Utils.removeColor(clickedInventoryTitle).equals("Lista Magazynow")) {
            e.setCancelled(true);
            if (clickedItem.getType().equals(Material.CHEST)) {
                player.openInventory(rpgcore.getMagazynierNPC().openMagazyn(playerUUID, (clickedSlot + 1)));
            }
        }

    }

}
