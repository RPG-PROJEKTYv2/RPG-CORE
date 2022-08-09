package rpg.rpgcore.commands.admin.vanish;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class SendVanishBar implements Runnable {

    private final RPGCORE rpgcore;
    private final Player p;

    public SendVanishBar(final RPGCORE rpgcore, final Player p) {
        this.rpgcore = rpgcore;
        this.p = p;
    }

    @Override
    public void run() {
        final UUID uuid = p.getUniqueId();
        if (rpgcore.getGodManager().containsPlayer(uuid) && rpgcore.getVanishManager().isVisible(uuid)) {
            return;
        }
        rpgcore.getNmsManager().sendActionBar(p, "&3&lVanish");
    }

}
