package rpg.rpgcore.zmianki.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class ZmiankiInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (clickedInventory.getType().equals(InventoryType.PLAYER) && Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Zmiana Bonusow")) {
            e.setCancelled(true);
            final String type = String.valueOf(item.getType());
            if (!type.contains("_SWORD") && !type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS")) return;
            if (!item.hasItemMeta()) return;
            if (!item.getItemMeta().hasDisplayName()) return;
            if (!item.getItemMeta().hasLore()) return;
            if (!player.getOpenInventory().getTopInventory().getItem(13).equals(RPGCORE.getInstance().getZmiankiManager().ItemToBlock())) {
                return;
            }
            player.getOpenInventory().getTopInventory().setItem(13, item.clone());
            player.getInventory().removeItem(new ItemBuilder(item.clone()).setAmount(1).toItemStack());

            return;
        }

        if (title.equals("Zmiana Bonusow")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.REDSTONE_TORCH_ON) {
                return;
            }


            if (slot == 13) {
                if (item.getType() == Material.IRON_FENCE) return;

                final String type = item.getType().toString();
                if (!type.contains("_SWORD") && !type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS")) return;

                final ItemStack is = item.clone();
                player.getInventory().addItem(is);

                clickedInventory.setItem(13, new ItemBuilder(Material.IRON_FENCE).setName("&cMiejsce na Miecz/Zbroje").toItemStack());
                return;
            }

            if (slot == 14) {
                if (clickedInventory.getItem(13).getType() == Material.IRON_FENCE) return;
                final String type = clickedInventory.getItem(13).getType().toString();
                if (!type.contains("_SWORD") && !type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS")) return;

                player.getInventory().removeItem(new ItemBuilder(GlobalItem.I50.getItemStack().clone()).setAmount(1).toItemStack());

                player.getInventory().addItem(RPGCORE.getInstance().getZmiankiManager().rollNewBonuses(clickedInventory.getItem(13), player));
                clickedInventory.setItem(13, new ItemBuilder(Material.IRON_FENCE).setName("&cMiejsce na Miecz/Zbroje").toItemStack());
                RPGCORE.getInstance().getZmiankiManager().openGUI(player, false);
            }
        }
    }
}
