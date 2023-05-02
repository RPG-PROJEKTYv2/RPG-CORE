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

public class PiekielnaDuszaListener60_70 implements Listener {
    private final BossyManager bossyManager = RPGCORE.getInstance().getBossyManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;

        if (e.getItem().getType() != Material.FIREBALL || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()
                || !Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Zaczarowana Kula")) return;

        final Player player = e.getPlayer();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (!player.getWorld().getName().equals("60-70map")) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNie mozesz tego uzyc w tej lokacji!"));
            return;
        }

        if (user.getLvl() < 60) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPosiadasz zbyt niski poziom, zeby wykonacz ta czynnosc!"));
            return;
        }

        if (bossyManager.getBoss60_70count() == 2) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cW tym momencie nie mozesz przywolac wiecej &c&lPiekielnych Dusz! &7(Limit: 2)"));
            return;
        }

        player.getInventory().removeItem(new ItemBuilder(Bossy.I1.getItemStack().clone()).setAmount(1).toItemStack());
        final Location loc = player.getLocation();
        final String cordsToString = loc.getWorld().getName() + "," + loc.getX() + "," + (loc.getY() + 3) + "," + loc.getZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 1 " + cordsToString);
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &aPomyslnie przywolano &c&lPiekielna Dusze!"));
        bossyManager.incrementBoss60_70count();
    }

}
