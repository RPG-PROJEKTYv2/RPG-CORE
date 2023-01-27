package rpg.rpgcore.trade.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.trade.objects.Trade;
import rpg.rpgcore.utils.Utils;

public class TradeInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public TradeInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() == null || e.getClickedInventory() == null) return;

        Inventory guiPlayer1 = e.getClickedInventory();
        String title = Utils.removeColor(guiPlayer1.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();
        Player player1 = (Player) e.getWhoClicked();

        if (title.contains(" - " + player1.getName()) || title.contains(player1.getName() + " - ")) {
            e.setCancelled(true);
            Trade trade = rpgcore.getTradeManager().find(title);

            if (trade == null) {
                trade = rpgcore.getTradeManager().find(title.split(" - ")[1] + " - " + title.split(" - ")[0]);
                if (trade == null) return;
            }
            
            player1 = trade.getPlayer1();
            final Player player2 = trade.getPlayer2();

            guiPlayer1 = trade.getGuiPlayer1();
            final Inventory guiPlayer2 = trade.getGuiPlayer2();

            if (e.getClick() == ClickType.CREATIVE) return;

            if (slot == 48) {
                if (e.getWhoClicked() == player1) {
                    acceptTrade(guiPlayer1, player1, trade, guiPlayer2);
                } else {
                    acceptTrade(guiPlayer2, player2, trade, guiPlayer1);
                }
                return;
            }

            if (!((slot > 9 && slot < 13) || (slot > 18 && slot < 22) || (slot > 27 && slot < 31) || (slot > 36 && slot < 40))) return;

            if (item == null || item.getType() == Material.AIR || item.getType() == Material.STAINED_GLASS_PANE) return;

            if (e.getWhoClicked() == player1) {
                if (trade.isPartAccepted()) return;
                if (!trade.getItems(player1.getUniqueId()).contains(item)) {
                    // TODO Logi z bledu (item jakims chujem znalazl sie w trade a nie ma go w liscie)
                    return;
                }
                trade.removeItem(player1.getUniqueId(), item);
                player1.getInventory().addItem(item.clone());
                trade.setItemAdded(true);
                rpgcore.getTradeManager().openGUI(trade);
            } else {
                if (trade.isPartAccepted()) return;
                if (!trade.getItems(player2.getUniqueId()).contains(item)) {
                    // TODO Logi z bledu (item jakims chujem znalazl sie w trade a nie ma go w liscie)
                    return;
                }
                trade.removeItem(player2.getUniqueId(), item);
                player2.getInventory().addItem(item.clone());
                trade.setItemAdded(true);
                rpgcore.getTradeManager().openGUI(trade);
            }
        }
        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER) && (Utils.removeColor(player1.getOpenInventory().getTopInventory().getTitle()).contains(" - " + player1.getName())
                || Utils.removeColor(player1.getOpenInventory().getTopInventory().getTitle()).contains(player1.getName() + " - "))) {
            e.setCancelled(true);
            title = Utils.removeColor(player1.getOpenInventory().getTopInventory().getTitle());
            Trade trade = rpgcore.getTradeManager().find(title);

            if (trade == null) {
                trade = rpgcore.getTradeManager().find(title.split(" - ")[1] + " - " + title.split(" - ")[0]);
                if (trade == null) return;
            }

            player1 = trade.getPlayer1();
            final Player player2 = trade.getPlayer2();

            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

            if (e.getWhoClicked() == player1) {
                if (trade.isPartAccepted()) return;
                if (trade.getItems(trade.getPlayer1().getUniqueId()).size() == 12) return;
                trade.addItem(player1.getUniqueId(), item.clone());
                player1.getInventory().removeItem(item);
                trade.setItemAdded(true);
                rpgcore.getTradeManager().openGUI(trade);
            } else {
                if (trade.isPartAccepted()) return;
                if (trade.getItems(trade.getPlayer2().getUniqueId()).size() == 12) return;
                trade.addItem(player2.getUniqueId(), item.clone());
                player2.getInventory().removeItem(item);
                trade.setItemAdded(true);
                rpgcore.getTradeManager().openGUI(trade);
            }
        }
    }

    private void acceptTrade(final Inventory guiPlayer1, final Player player1, final Trade trade, final Inventory guiPlayer2) {
        if (trade.getAcceptMap().get(player1.getUniqueId())) {
            trade.setAccept(player1.getUniqueId(), false);
        } else {
            trade.setAccept(player1.getUniqueId(), true);
            if (trade.isAccepted()) {
                rpgcore.getTradeManager().finishTrade(trade);
                return;
            }
        }
        guiPlayer1.setItem(48, rpgcore.getTradeManager().getAcceptItem(trade, player1));
        guiPlayer2.setItem(50, rpgcore.getTradeManager().getAcceptItem(trade, player1));
    }
}
