package rpg.rpgcore.dungeons.niebiosa.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.niebiosa.items.NiebiosaItems;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class NiebiosaPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public NiebiosaPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void economyInventoryClick(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (eventItem == null) {
                return;
            }

            if (e.getClickedBlock().getType().equals(Material.DROPPER) && e.getClickedBlock().getLocation().getWorld().getName().equals("60-70exp")
                    && eventItem.isSimilar(NiebiosaItems.getItem("klucz", 1))) {
                e.setCancelled(true);
                if (rpgcore.getNiebiosaManager().isActive()) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cKtos aktualnie przechodzi ten Dungeon."));
                    return;
                }
                player.getInventory().removeItem(NiebiosaItems.getItem("klucz", 1));
                rpgcore.getNiebiosaManager().start(player);
            }
        }
    }
}
