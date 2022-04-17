package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PlayerInteractEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e){

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        player.sendMessage(e.getRightClicked().getEntityId() + " -ID");

        if (e.getRightClicked().getEntityId() == 2 || e.getRightClicked().getEntityId() == 3 || e.getRightClicked().getEntityId() == 4 || e.getRightClicked().getEntityId() == 9 ||
                e.getRightClicked().getEntityId() == 5 || e.getRightClicked().getEntityId() == 8 || e.getRightClicked().getEntityId() == 6 || e.getRightClicked().getEntityId() == 19 ||
                e.getRightClicked().getEntityId() == 22 || e.getRightClicked().getEntityId() == 7 || e.getRightClicked().getEntityId() == 21 || e.getRightClicked().getEntityId() == 20) {
            e.setCancelled(true);
            if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < 74) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGI"));
                return;
            }
            player.openInventory(rpgcore.getBaoManager().baoGUI(uuid));
            return;
        }

    }
}
