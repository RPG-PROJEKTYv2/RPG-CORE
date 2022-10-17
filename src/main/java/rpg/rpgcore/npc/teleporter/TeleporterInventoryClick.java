package rpg.rpgcore.npc.teleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class TeleporterInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public TeleporterInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void teleporterInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final String clickedInventoryTitle = clickedInventory.getTitle();
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
            if (clickedSlot == 2) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("20-30exp"), -206.493, 11, 30.590, (float)-16.5, (float)1.2));
            }
            if (clickedSlot == 3) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("30-40exp"), -258, 14, 107, (float)-82.9, (float)0.4));
            }
            if (clickedSlot == 4) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("40-50exp"), -22.547, 4, 187.617, (float)-180.0, (float)0.3));
            }
            if (clickedSlot == 5) {
                player.closeInventory();
                player.teleport(new Location(Bukkit.getWorld("50-60exp"), -42.490, 8, -48.476, (float)-110.7, (float)0.2));
            }
            if (clickedSlot == 6) {
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
