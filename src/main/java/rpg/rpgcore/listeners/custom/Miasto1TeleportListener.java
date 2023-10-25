package rpg.rpgcore.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Miasto1TeleportListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalEnter(final PlayerPortalEvent e) {
        if (!e.getFrom().getWorld().equals(RPGCORE.getInstance().getSpawnManager().getSpawn().getWorld())) return;
        e.useTravelAgent(false);
        e.setCancelled(true);
        final Player player = e.getPlayer();
        if (RPGCORE.getInstance().getDisabledManager().getDisabled().getDisabledDungeons().contains("Miasto1")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Miasto 1 &cjest aktualnie niedostepne!"));
            player.teleport(RPGCORE.getInstance().getSpawnManager().getSpawn());
            return;
        }
        player.teleport(new Location(Bukkit.getWorld("miasto1"), 217.5, 89, -217.5, 180F, 0F));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aTeleportowano na &6Miasto 1&a!"));
    }
}
