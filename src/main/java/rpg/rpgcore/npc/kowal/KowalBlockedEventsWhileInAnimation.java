package rpg.rpgcore.npc.kowal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class KowalBlockedEventsWhileInAnimation implements Listener {

    private final RPGCORE rpgcore;

    public KowalBlockedEventsWhileInAnimation(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(final PlayerMoveEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        if (rpgcore.getKowalNPC().isInAnimationList(uuid)) {
            e.setCancelled(true);
            e.getPlayer().teleport(e.getFrom());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInventoryOpen(final InventoryOpenEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        if (rpgcore.getKowalNPC().isInAnimationList(uuid)) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zrobic podczas animacji"));
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(final PlayerCommandPreprocessEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        if (rpgcore.getKowalNPC().isInAnimationList(uuid)) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zrobic podczas animacji"));
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(final PlayerInteractAtEntityEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        if (rpgcore.getKowalNPC().isInAnimationList(uuid)) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zrobic podczas animacji"));
            e.setCancelled(true);
        }
    }

}
