package rpg.rpgcore.utils;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Utils {

    public static DecimalFormat df;
    public static SimpleDateFormat sdf;

    public static final String NIEGRACZ = format("&cNie jesteś graczem!");
    public static final String SERVERNAME = format("&f&lTEST&3&lRPG &7");
    public static final String BANPREFIX = format("&f&lTEST&3&lBANS &7");
    public static final String NIEMATAKIEGOGRACZA = (BANPREFIX + format("&cNie znaleziono podanego gracza"));
    public static final String GRACZOFFLINE = format("&cTen gracz jest offline");

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replace(">>", "»").replace("<<", "«"));
    }

    public static String permisje(final String perm) {
        return format(SERVERNAME + "&6Nie masz wystarczajacych permisji &8(&6" + perm + "&8)");
    }

    public static String poprawneUzycie(final String cmd) {
        return format(SERVERNAME + "&7Poprawne uzycie to &c/" + cmd);
    }

    public static String offline(final String targetName) {
        return format(SERVERNAME + format("&7Gracz &c" + targetName + " &7jest aktualnie &coffline!"));
    }

    public static String joinMessage(final String name) {
        return format(" &8[&a+&8] &7" + name);
    }

    public static String quitMessage(final String name) {
        return format(" &8[&c-&8]" + name);
    }

    public static String kickMessage(final String senderName, final String reason) {
        return (Utils.SERVERNAME + Utils.format("\n" +
                "&7Zostales wyrzucony z serwera\n" +
                "&7Przez: &c" + senderName +
                "\n&7Za:&c" + reason +
                "\n\n&8Jezeli chcesz zglosic skarge na administratora, ktory wyrzucil Cie z" +
                " serwera\n" +
                "&8zglos sie na przykladowyts3.pl lub przykladowydc z ss tego kicka!"));
    }

    public static String banMessage(final String senderName, final String reason, final String date) {
        return (Utils.SERVERNAME + Utils.format("\n&7Zostales zablokowany na tym serwerze" +
                "\n&7Za:&c " + reason +
                "\n&7Wygasa:&c " + date +
                "\n&7Przez:&c " + senderName +
                "\n\n&3&lJezeli uwazasz ze to blad, skontaktuj sie" +
                "\n&3&lz &f&lAdministracja &3&ltego serwera. ts3: przykladowyts3.pl"));
    }
}