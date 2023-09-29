package rpg.rpgcore.bossy.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

public class MitycznyPajakListener80_90 implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        final ItemStack item = e.getItem();
        if (item == null || item.getType() != Material.SPIDER_EYE || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !Utils.removeColor(item.getItemMeta().getDisplayName()).equals("Przywolanie"))
            return;
        final Player player = e.getPlayer();

        if (!player.getWorld().getName().equals("80-90map")) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cNie mozesz tego uzyc w tej lokacji!"));
            return;
        }

        if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getLvl() < 80) {
            player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPosiadasz zbyt niski poziom, zeby wykonacz ta czynnosc!"));
            return;
        }

        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);

        if (RestartManager.restart.isRestarting()) {
            player.sendMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cNie mozesz tego zrobic, poniewaz aktualnie trwa restart serwera!"));
            return;
        }

        player.getInventory().removeItem(new ItemBuilder(item.clone()).setAmount(1).toItemStack().clone());
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &fGracz &6" + player.getName() + " &fprzywolal &e&lMitycznego Pajaka&f!"));
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 80-90-BOSS 1 80-90map,-40.5,68,146.5");
    }
}
