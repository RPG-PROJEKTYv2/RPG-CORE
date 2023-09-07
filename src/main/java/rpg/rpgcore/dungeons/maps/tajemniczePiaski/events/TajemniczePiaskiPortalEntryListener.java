package rpg.rpgcore.dungeons.maps.tajemniczePiaski.events;

import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.plugin.RegisteredListener;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.RdzenPiaszczystychWydm;
import rpg.rpgcore.utils.Utils;

public class TajemniczePiaskiPortalEntryListener implements Listener {
    private final RPGCORE rpgcore;

    public TajemniczePiaskiPortalEntryListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityPortalEntry(final EntityPortalEnterEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!e.getLocation().getWorld().getName().equals("80-90map")) return;
        final Player player = (Player) e.getEntity();
        for (RegisteredListener listener : e.getHandlers().getRegisteredListeners()) {
            if (listener.getListener() instanceof TajemniczePiaskiPortalEntryListener) {
                if (!rpgcore.getTajemniczePiaskiManager().isCanJoin()) {
                    player.teleport(rpgcore.getSpawnManager().getSpawn());
                    player.sendMessage(Utils.format("&e&lTajemnicze Piaski &8>> &cProbowales/-as wejsc do zamknietego dungeonu! Teleportowano na spawn!"));
                    return;
                }
                player.teleport(rpgcore.getTajemniczePiaskiManager().getSpawnLocation());
                for (final RdzenPiaszczystychWydm rdzen : rpgcore.getTajemniczePiaskiManager().getRdzenieLocation().values()) {
                    rdzen.getHologram().getVisibilitySettings().setIndividualVisibility(player, VisibilitySettings.Visibility.HIDDEN);
                }
                ((TextHologramLine) rpgcore.getTajemniczePiaskiManager().getHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getTajemniczePiaskiManager().getDungeon().getPlayers().size()));
            }
        }
    }
}
