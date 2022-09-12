package rpg.rpgcore.magazyn;

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

public class MagazynierInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();


        if (Utils.removeColor(clickedInventoryTitle).equals("Lista Magazynow")) {
            e.setCancelled(true);
            if (clickedItem.getType().equals(Material.CHEST)) {
                if (rpgcore.getMagazynManager().find(player.getUniqueId()).getMagazynUser().isUnlocked(clickedSlot + 1)) {
                    rpgcore.getMagazynManager().openPlayerMagazyn(player, clickedSlot + 1);
                }
            }
            return;
        }
        if (clickedInventoryTitle.contains("Magazyn #")) {
            if (clickedSlot == 49) {
                e.setCancelled(true);
                rpgcore.getMagazynManager().openMagazynyList(player);
                return;
            }
            if (clickedSlot >= 45) {
                e.setCancelled(true);
            }
        }
    }

}
