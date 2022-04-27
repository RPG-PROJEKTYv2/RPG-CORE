package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();
        final Player player = (Player) e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (closedInventoryTitle.equals(Utils.format("&4&lEQ GUI"))) {
            rpgcore.getChatManager().updateMessageWithEQ(uuid, "");
            return;
        }

        if (Utils.removeColor(closedInventoryTitle).contains("Wymiana ")) {
            final List<ItemStack> firstViewerItems = new ArrayList<>();
            final List<ItemStack> secViewerItems = new ArrayList<>();
            if (e.getViewers().size() == 2) {
                final Player firstViewer = (Player) e.getViewers().get(1);
                final Player secViewer = (Player) e.getViewers().get(0);

                if (!(rpgcore.getTradeManager().getTradeMap().containsKey(firstViewer.getUniqueId()) && rpgcore.getTradeManager().getTradeMap().containsValue(secViewer.getUniqueId()))) {
                    return;
                }

                for(int i = 0; i < e.getInventory().getSize(); i++) {

                    if ( (i > 9 && i < 13) || (i > 18 && i < 22) || (i > 27 && i < 31) || (i > 36 && i < 40) ) {
                        if (closedInventory.getItem(i) != null) {
                            firstViewerItems.add(closedInventory.getItem(i));
                        }
                    }

                    if ( (i > 13 && i < 17) || (i > 22 && i < 26) || (i > 31 && i < 35) || (i > 40 && i < 44) ) {
                        if (closedInventory.getItem(i) != null) {
                            secViewerItems.add(closedInventory.getItem(i));
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
        }

        if (Utils.removeColor(closedInventoryTitle).equals("Wystaw przedmiot")) {
            final ItemStack itemToGiveBack = closedInventory.getItem(4);
            final ItemMeta itemToGiveBackMeta = itemToGiveBack.getItemMeta();

            final List<String> loreItemToGiveBack = new ArrayList<>(itemToGiveBackMeta.getLore());

            int sizeOfLore = loreItemToGiveBack.size();
            for (int i = 0; i < 7; i++) {
                loreItemToGiveBack.set(sizeOfLore - i, "");
            }

            itemToGiveBackMeta.setLore(loreItemToGiveBack);
            itemToGiveBack.setItemMeta(itemToGiveBackMeta);

            player.getInventory().addItem(itemToGiveBack);
        }
    }
}
