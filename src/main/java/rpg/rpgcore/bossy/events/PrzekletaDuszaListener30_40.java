package rpg.rpgcore.bossy.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.BossyManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

public class PrzekletaDuszaListener30_40 implements Listener {
    private final BossyManager bossyManager = RPGCORE.getInstance().getBossyManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;

        if (e.getItem().getType() != Material.FIREBALL || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()
                || !Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Przywolanie * Przekleta Dusza")) return;

        final Player player = e.getPlayer();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (!player.getWorld().getName().equals("30-40map")) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNie mozesz tego uzyc w tej lokacji!"));
            return;
        }
        if (user.getLvl() < 30) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPosiadasz zbyt niski poziom, zeby wykonacz ta czynnosc!"));
            return;
        }

        if (bossyManager.getBoss30_40count() == 3) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cW tym momencie nie mozesz przywolac wiecej &7&lPrzekletej Duszy! &7(Limit na mape: 3)"));
            return;
        }
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);
        e.setCancelled(true);
        player.getInventory().removeItem(new ItemBuilder(Bossy.I30_40.getItemStack().clone()).setAmount(1).toItemStack());
        final Location loc = player.getLocation();
        final String cordsToString = loc.getWorld().getName() + "," + loc.getX() + "," + (loc.getY() + 3) + "," + loc.getZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 30-40-BOSS 1 " + cordsToString);
        for (Player server : Bukkit.getOnlinePlayers()) {
            if (!RPGCORE.getInstance().getChatManager().find(server.getUniqueId()).isBoss30_40()) continue;
            server.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &7&lPrzekleta Dusza &7przybyla do swojego pana!"));
        }
        bossyManager.incrementBoss30_40count();
    }
}
