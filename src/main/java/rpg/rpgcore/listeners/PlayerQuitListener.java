package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.utils.Utils;

import java.util.Date;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerQuitListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitListener(final PlayerQuitEvent e) {

        final Player p = e.getPlayer();
        final UUID pUUID = p.getUniqueId();
        final String name = p.getName();

        rpgcore.getServer().getScheduler().cancelTask(rpgcore.getBackupManager().getTaskId(pUUID));
        rpgcore.getBackupManager().removeFromTaskMap(pUUID);

        rpgcore.getVanishManager().getVanishList().remove(pUUID);
        rpgcore.getMsgManager().getMessageMap().remove(pUUID);

        if (rpgcore.getGuildManager().hasGuild(pUUID)) {
            final String tag = rpgcore.getGuildManager().getGuildTag(pUUID);
            rpgcore.getGuildManager().putGuildLastOnline(tag, pUUID, new Date());
        }

        e.setQuitMessage(Utils.quitMessage(name));
        TabManager.removePlayer(p);

    }

}
