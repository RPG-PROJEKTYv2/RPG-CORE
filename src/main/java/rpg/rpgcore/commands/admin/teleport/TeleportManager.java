package rpg.rpgcore.commands.admin.teleport;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class TeleportManager {

    private final RPGCORE rpgcore;
    private final HashMap<UUID, Location> beforeTeleportLocation = new HashMap<>();

    public TeleportManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private void setBeforeTeleportLocation(final UUID uuid, final Location loc) {
        if (!(this.beforeTeleportLocation.containsKey(uuid))) {
            this.beforeTeleportLocation.put(uuid, loc);
            return;
        }
        this.beforeTeleportLocation.replace(uuid, loc);
    }

    public Location getBeforeTeleportLocation(final UUID uuid) {
        return this.beforeTeleportLocation.get(uuid);
    }

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z, final World world) {
        final UUID uuid = p.getUniqueId();
        final Location loc = new Location(world, x, y, z);

        this.setBeforeTeleportLocation(uuid, loc);
        p.teleport(loc);
        p.playSound(loc, Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPomyslnie teleportowano do swiata:&7 " + world.getName() + " &a na kordy: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z) {
        final UUID uuid = p.getUniqueId();
        final Location loc = new Location(p.getWorld(), x, y, z);

        this.setBeforeTeleportLocation(uuid, loc);
        p.teleport(loc);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPomyslnie teleportowano na kody: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToTarget(final Player p, final Player target) {
        final UUID uuid = p.getUniqueId();
        final Location loc = target.getLocation();

        this.setBeforeTeleportLocation(uuid, loc);
        p.teleport(loc);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.SERVERNAME + Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
    }

    public void playerToTeleport(final Player p, final Player playerToTeleport, final Player target) {
        final UUID uuid = playerToTeleport.getUniqueId();
        final Location loc = target.getLocation();

        this.setBeforeTeleportLocation(uuid, loc);
        playerToTeleport.teleport(loc);
        playerToTeleport.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowano gracza&7 " + playerToTeleport.getName() + " &ado gracza&7 " + target.getName()));
        playerToTeleport.sendMessage(Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
        target.sendMessage(Utils.format("&aPrzeteleportowano do ciebie gracza&7 " + playerToTeleport.getName()));

    }

    public void teleportToSpawn(final Player playerToTeleport, final String mess) {

        this.setBeforeTeleportLocation(playerToTeleport.getUniqueId(), playerToTeleport.getLocation());

        playerToTeleport.teleport(rpgcore.getSpawnManager().getSpawn());
        playerToTeleport.playSound(playerToTeleport.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        playerToTeleport.sendMessage(Utils.format(mess));
    }

}
