package rpg.rpgcore.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.concurrent.TimeUnit;

public class PlayerCommandPreprocessListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerCommandPreprocessListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandUse(final PlayerCommandPreprocessEvent e) {
        final Player player = e.getPlayer();

        if (rpgcore.getCooldownManager().hasCommandCooldown(player.getUniqueId())) {
            final long sekundy = rpgcore.getCooldownManager().getPlayerCommandCooldown(player.getUniqueId()) - System.currentTimeMillis();
            final String mili = String.valueOf(TimeUnit.MILLISECONDS.toMillis(sekundy) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(sekundy)));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nastepnej komendy mozesz uzyc za &c" + TimeUnit.MILLISECONDS.toSeconds(sekundy) + "&7.&c" + mili + " sekund"));
            e.setCancelled(true);
            return;
        }
        if (rpgcore.getPlayerManager().getPlayerGroup(player).equals("H@") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("Admin") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("GM")) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerCommandCooldown(player.getUniqueId());

    }
}
