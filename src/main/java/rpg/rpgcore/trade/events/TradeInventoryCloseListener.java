package rpg.rpgcore.trade.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.trade.objects.Trade;
import rpg.rpgcore.utils.Utils;

public class TradeInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public TradeInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory() == null) return;

        Player player1 = (Player) e.getPlayer();
        final String title = Utils.removeColor(e.getInventory().getTitle());

        if (title.contains(" - " + player1.getName()) || title.contains(player1.getName() + " - ")) {
            Trade trade = rpgcore.getTradeManager().find(title);

            if (trade == null) {
                trade = rpgcore.getTradeManager().find(title.split(" - ")[1] + " - " + title.split(" - ")[0]);
                if (trade == null) return;
            }

            if (trade.isItemAdded()) return;

            if (trade.isAccepted()) return;

            player1 = trade.getPlayer1();
            final Player player2 = trade.getPlayer2();

            rpgcore.getTradeManager().remove(trade.getName());
            player1.sendMessage(Utils.format(Utils.TRADEPREFIX + "&cPrzerwales/-as handel z &6" + player2.getName() + "&c!"));
            player2.sendMessage(Utils.format(Utils.TRADEPREFIX + "&cGracz &6" + player1.getName() + " &cprzerwal handel!"));
            rpgcore.getTradeManager().givePlayerItems(player1, trade.getItems(player1.getUniqueId()));
            rpgcore.getTradeManager().givePlayerItems(player2, trade.getItems(player2.getUniqueId()));

            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player1::closeInventory, 1L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player2::closeInventory, 1L);

            trade.cancel();

            trade.log();

        }
    }

}
