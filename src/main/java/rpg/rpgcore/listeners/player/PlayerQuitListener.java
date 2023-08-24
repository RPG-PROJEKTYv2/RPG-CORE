package rpg.rpgcore.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.utils.BossBarUtil;
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

        e.setQuitMessage(null);

        if (BossBarUtil.hasBar(uuid)) {
            BossBarUtil.removeBar(player);
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().savePlayer(player, uuid));

        final int taskId = rpgcore.getUserSaveManager().getTaskId(uuid);

        if (taskId != -1) rpgcore.getServer().getScheduler().cancelTask(taskId);
        rpgcore.getUserSaveManager().removeFromTaskMap(uuid);

        rpgcore.getVanishManager().getVanishList().remove(uuid);
        rpgcore.getMsgManager().getMessageMap().remove(uuid);


        if (rpgcore.getGuildManager().hasGuild(uuid)) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            rpgcore.getGuildManager().putGuildLastOnline(tag, uuid, new Date());
        }

        if (!rpgcore.getUserManager().find(uuid).getRankUser().isHighStaff()) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (rpgcore.getChatManager().find(online.getUniqueId()).isQuitMessageEnabled()) {
                    online.sendMessage(Utils.format("&8[&c-&8] &7" + name + " &8(" + (Bukkit.getOnlinePlayers().size() - 1)+ "/1000)"));
                }
            }
        }

        player.closeInventory();
        player.getOpenInventory().getTopInventory().clear();

        TabManager.removePlayer(player);
        NameTagUtil.setPlayerNameTag(player, "delete");

        if (EntityTypes.isPetSpawned(uuid)) {
            EntityTypes.despawnPet(uuid);
        }

    }

}
