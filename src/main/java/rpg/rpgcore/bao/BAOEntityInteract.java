package rpg.rpgcore.bao;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BAOEntityInteract implements Listener {

    private final RPGCORE rpgcore;

    public BAOEntityInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoPlayerInteractAtEntityEvent(final PlayerInteractAtEntityEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();


        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            player.sendMessage(e.getRightClicked().getEntityId() + " -ID");
            if (rpgcore.getBaoManager().checkIfClickedEntityIsInList(e.getRightClicked().getEntityId())) {
                e.setCancelled(true);
                if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < 74) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGI"));
                    return;
                }
                player.openInventory(rpgcore.getBaoManager().baoGUI(uuid));
            }
        }
    }
}
