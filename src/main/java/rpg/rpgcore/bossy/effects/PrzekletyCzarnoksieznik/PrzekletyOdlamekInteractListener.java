package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

public class PrzekletyOdlamekInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Bossy.I70_80_BONUS.getItemStack())) return;
        e.setCancelled(true);
        e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);

        RPGCORE.getInstance().getPrzekletyCzarnoksieznikBossManager().openWyborGUI(player);
    }
}
