package rpg.rpgcore.commands.admin.god;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

import java.util.UUID;

public class SendGodBar implements Runnable {

    private final RPGCORE rpgcore;
    private final Player p;

    public SendGodBar(final RPGCORE rpgcore, final Player p) {
        this.rpgcore = rpgcore;
        this.p = p;
    }

    @Override
    public void run() {
        final UUID uuid = p.getUniqueId();
        if (rpgcore.getGodManager().containsPlayer(uuid) && rpgcore.getVanishManager().isVisible(uuid)) {
            rpgcore.getNmsManager().sendActionBar(p, "&3&lVANISH + GOD");
            return;
        }
        rpgcore.getNmsManager().sendActionBar(p, "&3&lGOD");
    }

}
