package rpg.rpgcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.utils.NameTagUtil;
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

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String name = player.getName();

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().savePlayer(player, uuid));


        rpgcore.getServer().getScheduler().cancelTask(rpgcore.getBackupManager().getTaskId(uuid));
        rpgcore.getBackupManager().removeFromTaskMap(uuid);

        rpgcore.getVanishManager().getVanishList().remove(uuid);
        rpgcore.getMsgManager().getMessageMap().remove(uuid);


        if (rpgcore.getGuildManager().hasGuild(uuid)) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            rpgcore.getGuildManager().putGuildLastOnline(tag, uuid, new Date());
        }

        e.setQuitMessage(Utils.quitMessage(name));
        TabManager.removePlayer(player);
        NameTagUtil.setPlayerNameTag(player, "delete");

        if (EntityTypes.isPetSpawned(uuid)) {
            EntityTypes.despawnPet(uuid);
        }

    }

}
