package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

public class HandlarzInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) return;

        final Inventory gui = e.getClickedInventory();
        final String title = Utils.removeColor(gui.getTitle());
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();


        if (title.equals("Handlarz")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            if (slot == 10) rpgcore.getHandlarzNPC().openHandlarzWartosciowe(player);
            if (slot == 13) rpgcore.getHandlarzNPC().openHandlarzKupnoSprzedaz(player);
            if (slot == 16) rpgcore.getHandlarzNPC().openHandlarzItemShop(player);
            return;
        }

        if (title.equals("Handlarz » Wartosciowe przedmioty")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (slot == 22) rpgcore.getHandlarzNPC().openHandlarzGUI(player);
            return;
        }


        if (title.equals("Handlarz » Sprzedaz/Kupno")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (slot == 15) rpgcore.getHandlarzNPC().openHandlarzSprzedaz(player);

            if (slot == 22) rpgcore.getHandlarzNPC().openHandlarzGUI(player);

            return;
        }

        if (title.equals("Handlarz » ItemShop")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) {
                return;
            }

            if (slot == 49) rpgcore.getHandlarzNPC().openHandlarzGUI(player);

            final User user = rpgcore.getUserManager().find(uuid);

            final int price = Utils.getTagInt(item, "price");
            if (user.getHellcoins() < price) {
                player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &7Nie posiadasz wystarczajaco &4&lH&6&lS'ow&7!"));
                return;
            }

            user.setHellcoins(user.getHellcoins() - price);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format("&6&lItem&2&lShop &8>> &aPomyslnie zakupiles/-as " + item.getItemMeta().getDisplayName()));
            switch (slot) {
                case 10:
                    player.getInventory().addItem(ItemShop.IS9.getItems().get(0));
                    return;
                case 11:
                    player.getInventory().addItem(ItemShop.IS10.getItems().get(0));
                    return;
                case 12:
                    player.getInventory().addItem(ItemShop.IS11.getItems().get(0));
                    return;
                case 15:
                    player.getInventory().addItem(ItemShop.IS19.getItems().get(0));
                    return;
                case 16:
                    player.getInventory().addItem(ItemShop.IS20.getItems().get(0));
                    return;
                case 19:
                    player.getInventory().addItem(ItemShop.IS12.getItems().get(0));
                    return;
                case 20:
                    player.getInventory().addItem(ItemShop.IS13.getItems().get(0));
                    return;
                case 21:
                    player.getInventory().addItem(ItemShop.IS14.getItems().get(0));
                    return;
                case 22:
                    player.getInventory().addItem(ItemShop.IS15.getItems().get(0));
                    return;
                case 37:
                    player.getInventory().addItem(GlobalItem.getItem("I52", 1));
                    return;
                case 38:
                    player.getInventory().addItem(GlobalItem.getItem("I53", 1));
                    return;
                case 39:
                    player.getInventory().addItem(GlobalItem.getItem("I54", 1));
                    return;
                case 40:
                    player.getInventory().addItem(GlobalItem.getItem("I55", 1));
            }
            return;
        }

        if (title.equals("Handlarz » Sprzedaz")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            player.sendMessage(player.getOpenInventory().getBottomInventory() + "");
            player.sendMessage(gui + "");
            player.sendMessage((player.getOpenInventory().getBottomInventory() == gui) + "");
        }



    }
}
