package rpg.rpgcore.npc.alchemik.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.AlchemikItems;

public class AlchemikItemPickupListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickup(final PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().isSimilar(AlchemikItems.I3.getItemStack())) {
            e.getItem().remove();
            e.setCancelled(true);
            int amount = e.getItem().getItemStack().getAmount();

            if (e.getPlayer().getInventory().containsAtLeast(e.getItem().getItemStack(), 1)) {
                for (ItemStack is : e.getPlayer().getInventory().getContents()) {
                    if (is != null && is.isSimilar(e.getItem().getItemStack())) {
                        amount += is.getAmount();
                    }
                }
            }

            for (int i = 0; i < amount; i++) {
                final ItemStack nies = AlchemikItems.getRandomZnisczonyKrysztal();

                e.getPlayer().sendMessage(Utils.format("&a+ " + nies.getItemMeta().getDisplayName()));
                e.getPlayer().getInventory().addItem(nies.clone());
            }
        }
    }
}
