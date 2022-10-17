package rpg.rpgcore.trade;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class TRADEInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TRADEInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void tradeInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Wymiana ")) {
            final Player firstViewer = (Player) clickedInventory.getViewers().get(1);
            final Player secViewer = (Player) clickedInventory.getViewers().get(0);
            final UUID secViewerUUID = secViewer.getUniqueId();
            final UUID firstViewerUUID = firstViewer.getUniqueId();
            if (clickedInventoryTitle.equals(rpgcore.getTradeManager().createTradeGUI(firstViewerUUID, secViewerUUID).getName())) {

                if (rpgcore.getTradeManager().isTradeAccepted(clickedInventory)) {
                    e.setCancelled(true);
                    return;
                }

                if (!((clickedSlot > 9 && clickedSlot < 13) || (clickedSlot > 13 && clickedSlot < 17) || (clickedSlot > 18 && clickedSlot < 22) || (clickedSlot > 22 && clickedSlot < 26) || (clickedSlot > 27 && clickedSlot < 31) || (clickedSlot > 31 && clickedSlot < 35) || (clickedSlot > 36 && clickedSlot < 40) ||
                        (clickedSlot > 40 && clickedSlot < 44))) {
                    e.setCancelled(true);
                    if (clickedSlot == 48) {
                        if (e.getWhoClicked().equals(firstViewer)) {
                            if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
                                clickedInventory.setItem(48, rpgcore.getTradeManager().getAcceptItem(firstViewerUUID));
                                if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(clickedInventory, firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            clickedInventory.setItem(48, rpgcore.getTradeManager().getNoAcceptItem(firstViewerUUID));
                            return;
                        }
                    }
                    if (clickedSlot == 50) {
                        if (e.getWhoClicked().equals(secViewer)) {
                            if (clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                clickedInventory.setItem(50, rpgcore.getTradeManager().getAcceptItem(secViewerUUID));
                                if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())
                                        && clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                                    e.setCancelled(true);
                                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getTradeManager().tradeAccepted(clickedInventory, firstViewer, secViewer), 1L);
                                    return;
                                }
                                return;
                            }
                            clickedInventory.setItem(50, rpgcore.getTradeManager().getNoAcceptItem(secViewerUUID));
                            return;
                        }
                    }
                    return;
                }

                if (e.getWhoClicked().equals(firstViewer)) {
                    if (! (clickedSlot < 13 || clickedSlot > 18 && clickedSlot < 22 || clickedSlot > 27 && clickedSlot < 31 || clickedSlot > 36 && clickedSlot < 40)) {
                        e.setCancelled(true);
                        return;
                    }

                    if (clickedInventory.getItem(48).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(firstViewerUUID).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }

                if (e.getWhoClicked().equals(secViewer)) {
                    if (! (clickedSlot > 13 && clickedSlot < 17 || clickedSlot > 22 && clickedSlot < 26 || clickedSlot > 31 && clickedSlot < 35 || clickedSlot > 40)) {
                        e.setCancelled(true);
                        return;
                    }
                    if (clickedInventory.getItem(50).getItemMeta().getDisplayName().equals(rpgcore.getTradeManager().getAcceptItem(secViewerUUID).getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }

}
