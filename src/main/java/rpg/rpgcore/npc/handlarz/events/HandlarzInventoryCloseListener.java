package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.utils.Utils;

import java.util.Map;

public class HandlarzInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(final InventoryCloseEvent e){
        if (e.getInventory() == null) return;
        if (Utils.removeColor(e.getInventory().getTitle()).equals("Handlarz » Sprzedaz")) {
            if (rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()).isEmpty()) return;

            int amount = 0;

            for (final Map.Entry<ItemStack, Double> entry : rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()).entries()) {
                if (entry.getKey() == null) continue;
                amount += entry.getKey().clone().getAmount();
                e.getPlayer().getInventory().addItem(entry.getKey().clone());
            }

            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> RPGCORE.getDiscordBot().sendChannelMessage("handlarz-logs", EmbedUtil.createHandlarzCancelLog((Player) e.getPlayer(), rpgcore.getHandlarzNPC().getUserItemMap(e.getPlayer().getUniqueId()))));
            e.getPlayer().sendMessage(Utils.format("&6&lHandlarz &8>> &cAnulowales sprzedawanie &6" + amount + " &cprzedmiotow i zostaly one dodane do twojego ekwipunku."));
            rpgcore.getHandlarzNPC().removeUserItemMap(e.getPlayer().getUniqueId());

        }
    }
}
