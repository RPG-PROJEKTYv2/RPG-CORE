package rpg.rpgcore.bao.events;

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

public class BAOPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public BAOPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem == null) {
            return;
        }

        if (!eventItem.equals(rpgcore.getBaoManager().getItemDoZmianki())) {
            return;
        }
        e.setCancelled(true);
        if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < 80) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
            return;
        }
        if (rpgcore.getBaoManager().isNotRolled(uuid)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw wylosowac swoje bonusy w &5&lStole Magii"));
            return;
        }
        rpgcore.getBaoManager().openMainGUI(player, true);


    }
}
