package rpg.rpgcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class BlockPlaceListener implements Listener {
    private final RPGCORE rpgcore;

    public BlockPlaceListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(final BlockPlaceEvent e) {
        final Player player = e.getPlayer();

        if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz stawiac blokow!"));
            return;
        } else {
            if (!rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                e.setCancelled(true);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz stawiac blokow!"));
                return;
            }
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz niszczyc blokow na tym trybie gry!"));
        }
    }
}
