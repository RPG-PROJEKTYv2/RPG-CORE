package rpg.rpgcore.bossy.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class DowodcaRozbojnikowListener1_10 implements Listener {
    private final BossyManager bossyManager = RPGCORE.getInstance().getBossyManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;

        if (e.getItem().getType() != Material.FIREBALL || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()
                || !Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Przywolanie * Dowodca Rozbojnikow")) return;

        final Player player = e.getPlayer();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (!player.getWorld().getName().equals("1-10map")) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNie mozesz tego uzyc w tej lokacji!"));
            return;
        }

        if (bossyManager.getBoss1_10count() == 5) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cW tym momencie nie mozesz przywolac wiecej &c&lDowodcow Rozbojnikow! &7(Limit na mape: 3)"));
            return;
        }
        player.getInventory().removeItem(new ItemBuilder(Bossy.I1_10.getItemStack().clone()).setAmount(1).toItemStack());
        final Location loc = player.getLocation();
        final String cordsToString = loc.getWorld().getName() + "," + loc.getX() + "," + (loc.getY() + 3) + "," + loc.getZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 1-10-BOSS 1 " + cordsToString);
        Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &c&lDowodca Rozbojnikow &7przybyl do swojego pana!"));
        bossyManager.incrementBoss1_10count();
    }
}
