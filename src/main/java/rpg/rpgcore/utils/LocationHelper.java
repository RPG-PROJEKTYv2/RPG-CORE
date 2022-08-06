package rpg.rpgcore.utils;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class LocationHelper {
    public static boolean isSimilar(final Location a, final Location b) {
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    public static Location locFromString(String str) {
        final String[] str2loc = str.split(":");
        final Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        loc.setPitch(Float.parseFloat(str2loc[3]));
        loc.setYaw(Float.parseFloat(str2loc[4]));
        return loc;
    }

    public static Location locFromStringTargs(String str) {
        final String[] str2loc = str.split(":");
        final Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        return loc;
    }

    public static String locToString(final Location loc) {
        return loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + loc.getPitch() + ":" + loc.getYaw();
    }

    public static String locToStringTargs(final Location loc) {
        return loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

    public static boolean isInLoc(final Location loc, final Location l2, final int distance) {
        final int distancex = Math.abs(loc.getBlockX() - l2.getBlockX());
        final int distancez = Math.abs(loc.getBlockZ() - l2.getBlockZ());
        return distancex <= distance && distancez <= distance;
    }

    public static Set<Player> findPlayers(final Location location, final int distance) {
        final Set<Player> playerList = Sets.newHashSet();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (location.distance(player.getLocation()) < distance) {
                if (player.getLocation().getBlock().getType().toString().contains("PLATE")) {
                    playerList.add(player);
                }
            }
        });
        return playerList;
    }
}
