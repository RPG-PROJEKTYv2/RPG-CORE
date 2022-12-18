package rpg.rpgcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class BlockBreakListener implements Listener {

    private final RPGCORE rpgcore;

    public BlockBreakListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockBreakEvent e) {
        final Player player = e.getPlayer();
        if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz niszczyc blokow!"));
            return;
        } else {
            if (!rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                e.setCancelled(true);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz niszczyc blokow!"));
                return;
            }
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz niszczyc blokow na tym trybie gry!"));
        }

        if (e.getBlock().getLocation().getWorld().getName().equals("Gornik")) {
            return;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeavesDecay(final LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExplosion(final BlockExplodeEvent e) {
        e.setCancelled(true);
    }

}
