package rpg.rpgcore.npc.medyk.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.medyk.objects.MedykUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MedykInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType() == Material.POTION && eventItem.getDurability() == 8197 && eventItem.getItemMeta().hasDisplayName() && eventItem.getItemMeta().getDisplayName().contains("Mikstura Medyka")) {
            e.setCancelled(true);
            player.sendMessage("1");
            final MedykUser medykUser = RPGCORE.getInstance().getMedykNPC().find(uuid).getMedykUser();
            if (Utils.getTagInt(eventItem, "maxBonus") >= medykUser.getBonus() && Utils.getTagInt(eventItem, "minBonus") <= medykUser.getProgress()) {
                medykUser.setBonus(medykUser.getBonus() + 1);
                player.setMaxHealth(player.getMaxHealth() + 2);
                player.setHealth(player.getMaxHealth());
                player.getInventory().removeItem(eventItem);
                player.sendMessage(Utils.format("&c&lMedyk &8>> &aPomyslnie zwiekszyles swoje zdrowie o &c1❤&a!"));
            } else {
                player.sendMessage(Utils.format("&c&lMedyk &8>> &cNie mozesz uzyc tej mikstury!"));
            }
        }
    }
}
