package rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.events;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.RegisteredListener;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

public class PiekielnyPrzedsionekPortalEntryListener implements Listener {
    private final RPGCORE rpgcore;

    public PiekielnyPrzedsionekPortalEntryListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityPortalEntry(final EntityPortalEnterEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!e.getLocation().getWorld().getName().equals("60-70map")) return;
        final Player player = (Player) e.getEntity();
        for (RegisteredListener listener : e.getHandlers().getRegisteredListeners()) {
            if (listener.getListener() instanceof PiekielnyPrzedsionekPortalEntryListener) {
                if (!rpgcore.getPrzedsionekManager().isCanJoin()) {
                    player.teleport(rpgcore.getSpawnManager().getSpawn());
                    player.sendMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cProbowales/-as wejsc do zamknietego dungeonu! Teleportowano na spawn!"));
                    return;
                }
                player.teleport(rpgcore.getPrzedsionekManager().getSpawnLocation());
                ((TextHologramLine) rpgcore.getPrzedsionekManager().getDungeonHologram().getLines().get(4)).setText(Utils.format("&7Ilosc graczy: &c" + rpgcore.getPrzedsionekManager().getDungeonWorld().getPlayers().size()));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEntityEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("Dungeon60-70")) return;
        if (e.getRightClicked() instanceof Player) return;
        final int counter = rpgcore.getPrzedsionekManager().getCounter();
        switch (rpgcore.getPrzedsionekManager().getDungeonStatus()) {
            case ETAP_1:
                e.getPlayer().sendMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cPostep: &7" + counter + "&c/&796 &c(&7" + DoubleUtils.round((counter / 96.0) * 100, 2) + "%&c)"));
                return;
            case ETAP_2:
                e.getPlayer().sendMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cPostep: &7" + counter + "&c/&712 &c(&7" + DoubleUtils.round((counter / 12.0) * 100, 2) + "%&c)"));
                return;
            case ETAP_3:
                e.getPlayer().sendMessage(Utils.format("&4&lPiekielny Przedsionek &8>> &cPostep: &7" + counter + "&c/&7144 &c(&7" + DoubleUtils.round((counter / 144.0) * 100, 2) + "%&c)"));
                return;
            default:
                return;
        }
    }
}
