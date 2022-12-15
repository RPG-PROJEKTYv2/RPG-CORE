package rpg.rpgcore.zmianki.events;

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
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.UUID;

public class ZmiankiInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();


        if (title.equals("Zmiana Bonusow")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }


            if (slot == 13) {
                if (clickedInventory.getItem(13) == null) {
                    if (e.getCursor() == null || e.getCursor().getType() == Material.AIR) {
                        return;
                    }

                    final String type = e.getCursor().getType().toString();
                    if (!type.contains("_SWORD") && !type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS")) {
                        return;
                    }


                    final ItemStack is = e.getCursor().clone();
                    e.setCursor(null);
                    RPGCORE.getInstance().getZmiankiManager().openGUI(player, is);
                } else {
                    if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                        if (e.getCursor().getAmount() > 1) {
                            return;
                        }

                        if (clickedInventory.getItem(13).getType().toString().contains("_SWORD")) {
                            if (!e.getCursor().equals(GlobalItem.getItem("I50", 1))) {
                                player.getInventory().addItem(GlobalItem.getItem("I50", 1));
                                return;
                            }
                            e.setCursor(null);
                            player.getInventory().addItem(RPGCORE.getInstance().getZmiankiManager().rollNewBonuses(clickedInventory.getItem(13), player)); //NIE DZIALA
                            clickedInventory.setItem(13, new ItemStack(Material.AIR));
                            player.closeInventory();
                            return;
                        }
                    }
                    player.getInventory().addItem(clickedInventory.getItem(13).clone());
                    clickedInventory.setItem(13, new ItemStack(Material.AIR));
                    RPGCORE.getInstance().getZmiankiManager().openGUI(player, null);
                }
            }


        }
    }
}
