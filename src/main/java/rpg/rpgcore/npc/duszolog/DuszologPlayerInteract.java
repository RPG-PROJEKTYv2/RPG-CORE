package rpg.rpgcore.npc.duszolog;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class DuszologPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public DuszologPlayerInteract(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void duszologPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (eventItem.getType() == Material.FIREWORK_CHARGE) {
                if (eventItem.getItemMeta().getDisplayName() == null) {
                    return;
                }
                if (eventItem.getItemMeta().getDisplayName().contains(Utils.format("&3Kamien &bUzbrojenia"))) {
                    player.openInventory(rpgcore.getDuszologNPC().dodawanieKAMIENIA());
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (eventItem.getType() == Material.FIREWORK_CHARGE) {
                if (eventItem.getItemMeta().getDisplayName() == null) {
                    return;
                }
                if (eventItem.getItemMeta().getDisplayName().contains(Utils.format("&3Kamien &bUzbrojenia"))) {
                    player.openInventory(rpgcore.getDuszologNPC().dodawanieKAMIENIA());
                }
            }
        }
    }
}
