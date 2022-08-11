package rpg.rpgcore.npc.kolekcjoner;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItem;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class KolekcjonerInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KolekcjonerInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void kolekcjonerInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("Kolekcjoner")) {
            e.setCancelled(true);

            if (clickedSlot != 13) {
                player.getInventory().addItem(GlobalItem.getItem("I21", 1));
                player.getInventory().addItem(GlobalItem.getItem("I20", 1));
                return;
            }

            final int currentMission = rpgcore.getKolekcjonerNPC().getKolekcjonerPlayerPostep(playerUUID);
            final ItemStack requiredItem = rpgcore.getKolekcjonerNPC().getRequiredItem(currentMission);

            if (!player.getInventory().containsAtLeast(requiredItem, 1)) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.KOLEKCJONER + "&cNie posiadasz wystarczajacej ilosci przedmiotow do oddania."));
                return;
            }

            player.getInventory().removeItem(requiredItem);
            rpgcore.getKolekcjonerNPC().updatePostepMisji(playerUUID, 1);
            player.closeInventory();
            rpgcore.getKolekcjonerNPC().openKolekcjonerGUI(player);
            return;
        }
    }
}
