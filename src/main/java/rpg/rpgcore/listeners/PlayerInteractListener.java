package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem().equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 74) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGI"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().baoGUI(player.getUniqueId()));
            }
        }

    }
}
