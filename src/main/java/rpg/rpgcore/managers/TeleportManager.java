package rpg.rpgcore.managers;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class TeleportManager {

    private HashMap<UUID, Location> beforeTeleportLocation = new HashMap<>();

    public HashMap<UUID, Location> getBeforeTeleportLocation() {return beforeTeleportLocation;}

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z, final World world) {
        putInHashMap(p.getUniqueId(), p.getLocation());
        p.teleport(new Location(world, x, y, z));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPomyslnie teleportowano do swiata:&7 " + world.getName() + " &a na kordy: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z) {
        putInHashMap(p.getUniqueId(), p.getLocation());
        p.teleport(new Location(p.getWorld(), x, y, z));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPomyslnie teleportowano na kody: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToTarget(final Player p, final Player target) {
        putInHashMap(p.getUniqueId(), p.getLocation());
        p.teleport(target);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
    }

    public void playerToTeleport(final Player p, final Player playerToTeleport, final Player target) {
        putInHashMap(playerToTeleport.getUniqueId(), playerToTeleport.getLocation());
        playerToTeleport.teleport(target);
        playerToTeleport.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowano gracza&7 " + playerToTeleport.getName() + " &ado gracza&7 " + target.getName()));
        playerToTeleport.sendMessage(Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
        target.sendMessage(Utils.format("&aPrzeteleportowano do ciebie gracza&7 " + playerToTeleport.getName()));

    }

    public void putInHashMap(final UUID uuid, final Location location){
        if (beforeTeleportLocation.containsKey(uuid)){
            beforeTeleportLocation.replace(uuid, location);
        } else {
            beforeTeleportLocation.put(uuid, location);
        }
    }
}
