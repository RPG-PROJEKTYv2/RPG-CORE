package rpg.rpgcore.dungeons.maps.demoniczneSale.events;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.plugin.RegisteredListener;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class DemoniczneSalePortalEntryListener implements Listener {
    private final RPGCORE rpgcore;

    public DemoniczneSalePortalEntryListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityPortalEntry(final EntityPortalEnterEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!e.getLocation().getWorld().getName().equals("90-100map")) return;
        final Player player = (Player) e.getEntity();
        for (RegisteredListener listener : e.getHandlers().getRegisteredListeners()) {
            if (listener.getListener() instanceof DemoniczneSalePortalEntryListener) {
                if (!rpgcore.getDemoniczneSaleManager().isCanJoin()) {
                    player.teleport(rpgcore.getSpawnManager().getSpawn());
                    player.sendMessage(Utils.format("&4&lDemoniczne Sale &8>> &cProbowales/-as wejsc do zamknietego dungeonu! Teleportowano na spawn!"));
                    return;
                }
                player.teleport(rpgcore.getDemoniczneSaleManager().getSpawnLocation());
                ((TextHologramLine) rpgcore.getDemoniczneSaleManager().getHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getDemoniczneSaleManager().getDungeon().getPlayers().size()));
            }
        }
    }
}
