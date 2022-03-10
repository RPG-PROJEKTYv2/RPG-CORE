package rpg.rpgcore.managers;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class TeleportManager {

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z, final World world) {
        p.teleport(new Location(world, x, y, z));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowałęś się do świata:&7 " + world.getName() + " &a na kordy: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToLocation(final Player p, final Double x, final Double y, final Double z) {
        p.teleport(new Location(p.getWorld(), x, y, z));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowałęś się na kody: &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z)));
    }

    public void teleportToTarget(final Player p, final Player target) {
        p.teleport(target);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
    }

    public void playerToTeleport(final Player p, final Player playerToTeleport, final Player target) {
        playerToTeleport.teleport(target);
        playerToTeleport.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        p.sendMessage(Utils.format("&aPrzeteleportowano gracza&7 " + playerToTeleport.getName() + " &ado gracza&7 " + target.getName()));
        playerToTeleport.sendMessage(Utils.format("&aPrzeteleportowano do gracza&7 " + target.getName()));
        target.sendMessage(Utils.format("&aPrzeteleportowano do ciebie gracza&7 " + playerToTeleport.getName()));

    }

}
