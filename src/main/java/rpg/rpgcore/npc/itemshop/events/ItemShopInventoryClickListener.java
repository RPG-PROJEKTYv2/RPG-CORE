package rpg.rpgcore.npc.itemshop.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;

import java.util.UUID;

public class ItemShopInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.equals("ItemShop")) {
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }

            final User user = RPGCORE.getInstance().getUserManager().find(uuid);

            final int price = Utils.getTagInt(item, "price");
            if (user.getHellcoins() < price) {
                player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &7Nie posiadasz wystarczajaco &4&lH&6&lS'ow&7!"));
                return;
            }

            user.setHellcoins(user.getHellcoins() - price);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &aPomyslnie zakupiles/-as " + item.getItemMeta().getDisplayName()));
            switch (slot) {
                case 10:
                    player.getInventory().addItem(ItemShop.IS18.getItems().get(0));
                    return;
                case 19:
                    player.getInventory().addItem(ItemShop.IS17.getItems().get(0));
                    return;
                case 28:
                    player.getInventory().addItem(ItemShop.IS16.getItems().get(0));
                    return;
                case 37:
                    player.getInventory().addItem(ItemShop.IS15.getItems().get(0));
                    return;
                case 12:
                    player.getInventory().addItem(ItemShop.IS14.getItems().get(0));
                    return;
                case 21:
                    player.getInventory().addItem(ItemShop.IS13.getItems().get(0));
                    return;
                case 30:
                    player.getInventory().addItem(ItemShop.IS12.getItems().get(0));
                    return;
                case 39:
                    player.getInventory().addItem(ItemShop.IS19.getItems().get(0));
                    return;
                case 14:
                    player.getInventory().addItem(ItemShop.IS11.getItems().get(0));
                    return;
                case 23:
                    player.getInventory().addItem(ItemShop.IS10.getItems().get(0));
                    return;
                case 32:
                    player.getInventory().addItem(ItemShop.IS9.getItems().get(0));
                    return;
                case 41:
                    player.getInventory().addItem(ItemShop.IS20.getItems().get(0));
                    return;
                case 16:
                    player.getInventory().addItem(GlobalItem.getItem("I52", 1));
                    return;
                case 25:
                    player.getInventory().addItem(GlobalItem.getItem("I53", 1));
                    return;
                case 34:
                    player.getInventory().addItem(GlobalItem.getItem("I54", 1));
                    return;
                case 43:
                    player.getInventory().addItem(GlobalItem.getItem("I55", 1));
            }
        }
    }
}
