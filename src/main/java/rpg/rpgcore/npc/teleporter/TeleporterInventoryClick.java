package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import java.util.HashMap;
import java.util.UUID;

public class TeleporterInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TeleporterInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void teleporterInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER")) {
            e.setCancelled(true);
            rpgcore.getTeleporterNPC().openTeleporterMAIN(player);
        }
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - MENU1")) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
            }
            if (clickedSlot == 15) {
                rpgcore.getTeleporterNPC().openTeleporterDODATKOWEMAPY(player);
            }
        }
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - MENU2")) {
            e.setCancelled(true);
            if (clickedSlot == 0) {
                if (clickedItem.getType() == Material.IRON_FENCE) {
                    Location locEXP1 = new Location(Bukkit.getWorld("1-10exp"), -361, 7, 20);
                    player.teleport(locEXP1);
                    player.closeInventory();
                }
            }
            if (clickedSlot == 1) {
                if (clickedItem.getType() == Material.RED_MUSHROOM) {
                    Location locEXP2 = new Location(Bukkit.getWorld("10-20exp"), 254, 16, -248);
                    player.teleport(locEXP2);
                    player.closeInventory();
                }
            }
            if (clickedSlot == 3) {
                if (clickedItem.getType() == Material.IRON_BLOCK) {
                    Location locEXP2 = new Location(Bukkit.getWorld("20-30exp"), -50, 11, 132);
                    player.teleport(locEXP2);
                    player.closeInventory();
                }
            }
            if (clickedSlot == 4) {
                if (clickedItem.getType() == Material.SEA_LANTERN) {
                    Location locEXP2 = new Location(Bukkit.getWorld("30-40exp"), 254, 16, -248);
                    player.teleport(locEXP2);
                    player.closeInventory();
                }
            }
        }

    }

}
