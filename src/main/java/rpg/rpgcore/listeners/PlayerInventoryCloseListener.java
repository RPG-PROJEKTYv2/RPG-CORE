package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class PlayerInventoryCloseListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        if (e.getInventory().getName().equals(Utils.format("&4&lEQ GUI"))) {
            rpgcore.getChatManager().updateMessageWithEQ(e.getPlayer().getUniqueId(), "");
            return;
        }

        if (Utils.removeColor(e.getInventory().getName()).contains("Wymiana ")) {
            final Inventory inv = e.getInventory();
            final List<ItemStack> firstViewerItems = new ArrayList<>();
            final List<ItemStack> secViewerItems = new ArrayList<>();
            if (e.getViewers().size() == 2) {
                final Player firstViewer = (Player) e.getViewers().get(1);
                final Player secViewer = (Player) e.getViewers().get(0);


                for(int i = 0; i < e.getInventory().getSize(); i++) {

                    if ( (i > 9 && i < 13) || (i > 18 && i < 22) || (i > 27 && i < 31) || (i > 36 && i < 40) ) {
                        if (inv.getItem(i) != null) {
                            firstViewerItems.add(inv.getItem(i));
                        }
                    }

                    if ( (i > 13 && i < 17) || (i > 22 && i < 26) || (i > 31 && i < 35) || (i > 40 && i < 44) ) {
                        if (inv.getItem(i) != null) {
                            secViewerItems.add(inv.getItem(i));
                        }
                    }

                }

                for (ItemStack is : firstViewerItems){
                    firstViewer.getInventory().addItem(is);
                }

                for (ItemStack is : secViewerItems){
                    secViewer.getInventory().addItem(is);
                }

                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, firstViewer::closeInventory, 1L);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, secViewer::closeInventory, 1L);

                rpgcore.getTradeManager().getTradeMap().remove(firstViewer.getUniqueId(), secViewer.getUniqueId());

                return;
            }
            if (e.getViewers().size() == 1) {
                final Player lastViewer = (Player) e.getViewers().get(0);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, lastViewer::closeInventory, 1L);
                return;
            }

//            if (rpgcore.getTradeManager().isInTradeMapAsKey(e.getPlayer().getUniqueId())) {
//                rpgcore.getTradeManager().getTradeMap().remove(e.getPlayer().getUniqueId());
//            }
//            rpgcore.getTradeManager().getTradeMap().remove(viewer.getUniqueId(), e.getPlayer().getUniqueId());
        }
    }
}
