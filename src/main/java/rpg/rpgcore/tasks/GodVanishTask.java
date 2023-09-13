package rpg.rpgcore.tasks;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class GodVanishTask implements Runnable {

    private final RPGCORE rpgcore;

    public GodVanishTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(this.rpgcore, this, 0L, 20L);
    }

    @Override
    public void run() {
        for (final Player p : rpgcore.getServer().getOnlinePlayers()) {
            final boolean god = rpgcore.getGodManager().containsPlayer(p.getUniqueId());
            final boolean vanish = rpgcore.getVanishManager().isVanished(p.getUniqueId());
            if (god && vanish) {
                rpgcore.getNmsManager().sendActionBar(p, "&3&lVanish &8| &d&lGOD");
            } else if (god) {
                rpgcore.getNmsManager().sendActionBar(p, "&d&lGOD");
            } else if (vanish) {
                rpgcore.getNmsManager().sendActionBar(p, "&3&lVanish");
            }
        }
    }

}
