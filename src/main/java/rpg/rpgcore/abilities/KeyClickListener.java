package rpg.rpgcore.abilities;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import rpg.rpgcore.RPGCORE;

public class KeyClickListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(final PlayerItemHeldEvent e) {
        if (e.getPreviousSlot() == e.getNewSlot()) {
            return;
        }
        if (e.getPlayer().isSneaking() && e.getPlayer().getInventory().getItem(e.getPreviousSlot()).getType().equals(Material.DIAMOND_BLOCK)) {
            // TODO CHUJ WI CZEMU ALE URUCHAMIA SIE 2 RAZY
            e.setCancelled(true);
            RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            e.getPlayer().sendMessage("Poprzedni slot " + e.getPreviousSlot());
            e.getPlayer().sendMessage("Nowy slot " + e.getNewSlot());
            e.getPlayer().sendMessage("uruchomiles 1 abilitke");
            }, 1L);
            return;
        }
    }
}
