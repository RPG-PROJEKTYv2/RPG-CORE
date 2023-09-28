package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

public class RybakItemPickupListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickup(final PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().getType() == Material.WOOL && e.getItem().getItemStack().getDurability() == 10) {
            e.getItem().remove();
            e.setCancelled(true);
            final OsUser osUser = RPGCORE.getInstance().getOsManager().find(e.getPlayer().getUniqueId());
            osUser.setNiesyProgress(osUser.getNiesyProgress() + 1);

            int amount = e.getItem().getItemStack().getAmount();

            if (e.getPlayer().getInventory().containsAtLeast(e.getItem().getItemStack(), 1)) {
                for (ItemStack is : e.getPlayer().getInventory().getContents()) {
                    if (is != null && is.isSimilar(e.getItem().getItemStack())) {
                        amount += is.getAmount();
                    }
                }
            }


            for (int i = 0; i < amount; i++) {
                final ItemStack krysztal = RybakItems.getRandomKrysztal();

                Bukkit.getServer().broadcastMessage(" ");
                Bukkit.getServer().broadcastMessage(Utils.format("&7&lStara Fabryka &8>> &7Gracz &e" + e.getPlayer().getName() + " &7znalazl " + krysztal.getItemMeta().getDisplayName() + "&7!"));
                Bukkit.getServer().broadcastMessage(" ");

                e.getPlayer().getInventory().addItem(krysztal.clone());
            }

        }
    }
}
