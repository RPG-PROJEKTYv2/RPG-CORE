package rpg.rpgcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static final String NIEGRACZ = format("&7[rpg.core] &cNie jesteś graczem!");
    public static final String SERVERNAME = format("&f&lTEST&3&lRPG &7");
    public static final String BANPREFIX = format("&f&lTEST&3&lBANS &7");
    public static final String UNBANPREFIX = format("&f&lTEST&3&lUNBANS &7");

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replace(">>", "»").replace("<<", "«"));
    }

    public static String permisje(final String perm) {
        return format(SERVERNAME + "&6Nie masz wystarczajacych permisji &8(&6" + perm + "&8)");
    }

    public static String poprawneUzycie(final String cmd) {
        return format(SERVERNAME + "&7Poprawne uzycie to &c/" + cmd + " &chelp");
    }

    public static String offline(final String targetName) {
        return format(SERVERNAME + "&7Gracz &c" + targetName + " &7jest aktualnie &coffline!");
    }

}