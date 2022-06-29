package rpg.rpgcore.npc.kupiec;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KupiecInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KupiecInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kupiecInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Kupiec")) {
            if (e.getClick().isShiftClick() || e.getClick().isKeyboardClick() || e.getClick().isCreativeAction()){
                e.setCancelled(true);
                return;
            }
        }

        if (Utils.removeColor(clickedInventoryTitle).equals("Kupiec")) {
            final List<ItemStack> playerItems = rpgcore.getKupiecNPC().getPlayerItemStackList(playerUUID);
            if (clickedItem.getType().equals(Material.STAINED_GLASS_PANE) || clickedItem.getType().equals(Material.PAPER)) {
                e.setCancelled(true);
                return;
            }

            if (e.getClick().isShiftClick() || e.getClick().isKeyboardClick() || e.getClick().isCreativeAction()){
                e.setCancelled(true);
                return;
            }

            if (clickedSlot == 49) {
                e.setCancelled(true);

                if (playerItems.isEmpty()) {
                    return;
                }

                if (!e.getCursor().getType().equals(Material.AIR)) {
                    return;
                }

                final double moneyToAdd = rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID);
                rpgcore.getPlayerManager().updatePlayerKasa(playerUUID, rpgcore.getPlayerManager().getPlayerKasa(playerUUID) + moneyToAdd);

                for (ItemStack is : playerItems) {
                    clickedInventory.remove(is);
                }
                rpgcore.getKupiecNPC().resetPlayerSellValueItems(playerUUID);
                rpgcore.getKupiecNPC().resetPlayerItemStack(playerUUID);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, player::closeInventory, 1L);
                rpgcore.getKupiecNPC().addMoneyEarnedPerDay(moneyToAdd);
                player.sendMessage(Utils.format(Utils.KUPIEC + "Pomyslnie sprzedano wszystkie przedmioty za &6" + Utils.spaceNumber(String.format("%.2f", moneyToAdd)) + " &2$"));
                return;
            }

            if (e.getCursor().getType().equals(Material.AIR)) {
                if (clickedItem.getType().equals(Material.AIR)) {
                    return;
                }
                rpgcore.getKupiecNPC().removePlayerItemStack(playerUUID, clickedItem);
                rpgcore.getKupiecNPC().removePlayerSellValueItems(playerUUID, rpgcore.getKupiecNPC().getItemPrice(clickedItem));
                clickedInventory.setItem(49, rpgcore.getKupiecNPC().getSellItem(rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID)));
            } else {
                if (!playerItems.contains(e.getCursor())) {
                    if (!rpgcore.getKupiecNPC().checkIfItemIsInLists(e.getCursor())) {
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCursor().getAmount() < 1) {
                        e.getCursor().setAmount(1);
                    }
                    rpgcore.getKupiecNPC().addPlayerItemStack(playerUUID, e.getCursor().clone());
                    rpgcore.getKupiecNPC().addPlayerSellValueItems(playerUUID, rpgcore.getKupiecNPC().getItemPrice(e.getCursor()) * e.getCursor().getAmount());

                    clickedInventory.setItem(49, rpgcore.getKupiecNPC().getSellItem(rpgcore.getKupiecNPC().getPlayerSellValueItems(playerUUID)));
                }
            }
        }
    }
}
