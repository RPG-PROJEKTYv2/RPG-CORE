package rpg.rpgcore.pomoc;

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

public class POMOCInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public POMOCInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void pomocInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIMAIN().getName())) {
            e.setCancelled(true);
            if (clickedItem.getType() == Material.BOOK) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR());
                return;
            }
            if (clickedSlot == 13) {
                player.openInventory(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY());
                return;
            }
            if (clickedItem.getType() == Material.FIREWORK_CHARGE) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Poradnik:"));
                player.sendMessage("1. Nie bądź kurwą...");
                player.sendMessage("2. Odpierdalasz = wypierdalasz");
                return;
            }
            return;
        }
        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIPODSTAWOWEKOMENDY().getName())) {
            e.setCancelled(true);
            return;
        }
        // POMOC 3
        if (clickedInventoryTitle.equals(rpgcore.getPomocManager().pomocGUIREGULAMINTARYFIKATOR().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 2) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do regulaminu:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
            if (clickedSlot == 6) {
                player.closeInventory();
                player.sendMessage(Utils.SERVERNAME + Utils.format(" &6Link do taryfikatora:"));
                player.sendMessage(Utils.format("&ewww.twojstarywzoo.pl"));
            }
        }

    }

}
