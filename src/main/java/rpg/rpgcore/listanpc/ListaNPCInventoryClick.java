package rpg.rpgcore.listanpc;

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

public class ListaNPCInventoryClick implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e){
        final Inventory gui = e.getInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Lista NPC")) {
            e.setCancelled(true);
            if (item.getType().equals(Material.AIR)) {
                return;
            }

            final NpcObject npc = RPGCORE.getInstance().getListaNPCManager().find(slot + 1);

            if (npc == null) {
                return;
            }

            final Location loc = npc.getLocation();

            player.teleport(loc);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie przeteleportowano do &6" + npc.getNpcName()));
        }
    }
}
