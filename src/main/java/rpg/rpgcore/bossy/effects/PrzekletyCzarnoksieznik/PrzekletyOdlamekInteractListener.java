package rpg.rpgcore.bossy.effects.PrzekletyCzarnoksieznik;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

public class PrzekletyOdlamekInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Bossy.I70_80_BONUS.getItemStack())) return;
        if (user.getLvl() < 70) {
            player.sendMessage(Utils.format("&5&lPrzekleta Moc &8>> &cTwoj poziom jest zbyt niski!"));
            return;
        }
        e.setCancelled(true);
        e.setUseItemInHand(org.bukkit.event.Event.Result.DENY);

        if (!RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isHellCodeLogin()) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zaloguj sie przy uzyciu swojego &chellcodu &7zeby wykonac te czynnosc!"));
            return;
        }

        RPGCORE.getInstance().getPrzekletyCzarnoksieznikBossManager().openWyborGUI(player);
    }
}
