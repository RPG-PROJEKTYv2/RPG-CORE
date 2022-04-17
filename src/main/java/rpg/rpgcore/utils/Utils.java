package rpg.rpgcore.utils;

import org.bukkit.ChatColor;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final Random random = new Random();
    public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
    public static final String NIEGRACZ = format("&cNie jesteś graczem!");
    public static final String SERVERNAME = format("&f&lTEST&3&lRPG&7 ");
    public static final String BANPREFIX = format("&f&lTEST&3&lBANS&7 ");
    public static final String LVLPREFIX = format("&f&lTEST&6&lLVL&7 ");
    public static final String KICKPREFIX = format("&f&lTEST&3&lKICK&7 ");
    public static final String NIEMATAKIEGOGRACZA = format("&cNie znaleziono podanego gracza");
    public static final String ALREADYBANNED = (BANPREFIX + format("&cTen gracz jest juz zbanowany!"));
    public static final String NOALREADYBANNED = (BANPREFIX + format("&cTen gracz nie jest zbanowany!"));
    public static DecimalFormat df = new DecimalFormat("0.0");
    public static DecimalFormat procentFormat = new DecimalFormat("##.##");
    public static int MAXLVL = 130;

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replace(">>", "»").replace("<<", "«"));
    }

    public static List<String> format(List<String> lore) {

        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, format(lore.get(i)));
        }
        return lore;
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

    public static String firstJoinMessage(final String name) {
        return format(" &8[&a+&8] &7" + name);
    }


    public static String joinMessage(final String name) {
        return format(" &8[&a+&8] &7" + name);
    }

    public static String quitMessage(final String name) {
        return format(" &8[&c-&8]&7 " + name);
    }

    public static String kickMessage(final String senderName, final String reason) {
        return format(SERVERNAME + format("\n" +
                "&7Zostales wyrzucony z serwera\n" +
                "&7Przez: &c" + senderName +
                "\n&7Za: &c" + reason +
                "\n\n&8Jezeli chcesz zglosic skarge na administratora, ktory wyrzucil Cie z" +
                " serwera\n" +
                "&8zglos sie na przykladowyts3.pl lub przykladowydc z ss tego kicka!"));
    }

    public static String banMessage(final String senderName, final String reason, final String banExpiry, final String dateOfBan) {
        return (SERVERNAME + format("\n&7Zostales zablokowany na tym serwerze" +
                "\n&7Za:&c " + reason +
                "\n&7Wygasa:&c " + banExpiry +
                "\n&7Nadany:&c " + dateOfBan +
                "\n&7Przez:&c " + senderName +
                "\n\n&3&lJezeli uwazasz ze to blad, skontaktuj sie" +
                "\n&3&lz &f&lAdministracja &3&ltego serwera. ts3: przykladowyts3.pl"));
    }

    public static String normalKickBroadcast(final String playerToKick, final String adminName, final String reason) {
        return (KICKPREFIX + format("&7Gracz &c" + playerToKick + " &7zostal wyrzucony z serwera przez &c" + adminName + ". &7Powod: &c" + reason));
    }

    public static String kickBroadcast(final String namePlayerToBan, final String senderName, final String reason) {
        return (BANPREFIX + format("&7Gracz&c " + namePlayerToBan + " &7zostal wyrzucony z serwera przez&c " + senderName + ". &7Powod:&c" + reason));
    }

    public static String banBroadcast(final String namePlayerToBan, final String senderName, final String reason, final String banExpiry) {
        return (BANPREFIX + format("&7Gracz&c " + namePlayerToBan + " &7zostal zbanowany na serwerze przez&c " + senderName + ". &7Wygasa: " + banExpiry + ". &7Powod:&c " + reason));
    }

    public static String unBanBroadcast(final String namePlayerToUnBan, final String senderName) {
        return (BANPREFIX + format("&7Gracz&c " + namePlayerToUnBan + " &7zostal odbanowany przez&c " + senderName));
    }

    public static String theSenderCannotBeTarget(final String type) {
        return (BANPREFIX + format("&cNie mozesz " + type + " samego siebie!"));
    }

    public static String removeColor(String toRemove) {
        char znakToLook;
        if (toRemove.contains("§")) {
            znakToLook = '§';
        } else {
            znakToLook = '&';
        }
        int index = toRemove.indexOf(znakToLook);
        while (index >= 0) {
            String znak = String.valueOf(toRemove.charAt(index));
            String kolor = String.valueOf(toRemove.charAt(index + 1));

            toRemove = toRemove.replaceFirst(kolor, "").replaceFirst(znak, "");
            index = toRemove.indexOf(znakToLook);
        }
        return toRemove;
    }

    public static String convertDatesToTimeLeft(final Date dateGive, final Date dateExpire) {
        final long diff = Math.abs(dateGive.getTime() - dateExpire.getTime());
        return String.format("%d d %d h %d min %d sec",
                TimeUnit.MILLISECONDS.toDays(diff),
                TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(diff)),
                TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
                TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff))
        );
    }
}