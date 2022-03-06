package rpg.rpgcore.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static final String NIEGRACZ = format("&cNie jesteś graczem!");
    public static final String SERVERNAME = format("&cNie jesteś graczem!");

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replace(">>", "»").replace("<<", "«"));
    }

    public static String permisje(final String perm) {
        return format("&6Nie masz wystarczajacych permisji &8(&6" + perm + "&8)");
    }

    public static String poprawneUzycie(final String cmd) {
        return format(SERVERNAME + "&7Poprawne uzycie to &c/" + cmd + " &chelp");
    }

    public static String offline(final String targetName) {
        return format(SERVERNAME + "&7Gracz &c" + targetName + " &7jest aktualnie &coffline!");
    }

}