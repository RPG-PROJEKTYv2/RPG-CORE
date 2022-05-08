package rpg.rpgcore.listeners;

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
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&3Nastepnej komendy mozesz uzyc za &f" + TimeUnit.MILLISECONDS.toSeconds(sekundy) + "&f.&f" + mili + " &3sekund"));
            e.setCancelled(true);
            return;
        }
        rpgcore.getCooldownManager().givePlayerCommandCooldown(player.getUniqueId());

    }
}
