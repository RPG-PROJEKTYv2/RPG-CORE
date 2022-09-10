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
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - WYBOR")) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                rpgcore.getTeleporterNPC().openTeleporterEXPOWISKA(player);
            }
            if (clickedSlot == 15) {
                rpgcore.getTeleporterNPC().openTeleporterDODATKOWEMAPY(player);
            }
        }
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - EXPOWISKA")) {
            e.setCancelled(true);
            if (clickedSlot == 0) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("1-10exp"), -363.540, 7, 20.539, (float)-57.3, (float)0.6));
            }
            if (clickedSlot == 1) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("10-20exp"), 257.382, 16, -247.783, (float)-2.4, (float)0.2));
            }
            if (clickedSlot == 3) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("20-30exp"), -206.493, 11, 30.590, (float)-16.5, (float)1.2));
            }
            if (clickedSlot == 4) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("30-40exp"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
            if (clickedSlot == 5) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("40-50exp"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
            if (clickedSlot == 6) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("50-60exp"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
            if (clickedSlot == 7) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("60-70exp"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
        }
        if (Utils.removeColor(clickedInventoryTitle).equals("TELEPORTER - INNE")) {
            e.setCancelled(true);
            if (clickedSlot == 11) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("miasto1"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
            if (clickedSlot == 15) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("demontower"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
        }
    }
}
