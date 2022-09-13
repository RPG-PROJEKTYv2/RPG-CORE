package rpg.rpgcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import rpg.rpgcore.RPGCORE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
    public static final String CHAT_FORMAT = format("<player-klan>&8[&bLvl. &f<player-lvl>&8] <player-group><player-name>:&f <message>");
    public static final String NIEGRACZ = format("&cNie jesteś graczem!");
    public static final String SERVERNAME = format("&4&lHELL&8&lRPG&7 ");
    public static final String BANPREFIX = format("&4&lHELL&8&lBAN&7 ");
    public static final String MUTEPREFIX = format("&4&lHELL&8&lMUTE&7 ");
    public static final String LVLPREFIX = format("&4&lHELL&6&lLVL&7 ");
    public static final String KICKPREFIX = format("&4&lHELL&8&lKICK&7 ");
    public static final String TRADEPREFIX = format("&4&lHELL&8&lTRADE&7 ");
    public static final String GUILDSPREFIX = format("&4&lHELL&8&lKLANY&7 ");
    public static final String WHITELIST = format("&7&lWHITE&6&lLIST&7 ");
    public static final String RYBAK = format("&6&lRybak &8>> &7");
    public static final String KUPIEC = format("&2&lKupiec &8>> &a");
    public static final String KOLEKCJONER = format("&6&lKolekcjoner &8>> &7");
    public static final String TRENER = format("&6&lTrener &8>> &7");
    public static final String NIEMATAKIEGOGRACZA = format("&cNie znaleziono podanego gracza");
    public static final String ALREADYBANNED = (BANPREFIX + format("&cTen gracz jest juz zbanowany!"));
    public static final String ALREADYMUTED = (BANPREFIX + format("&cTen gracz jest juz zbanowany!"));
    public static final String NOALREADYBANNED = (BANPREFIX + format("&cTen gracz nie jest zbanowany!"));
    public static DecimalFormat df = new DecimalFormat("0.0");
    public static DecimalFormat kasaFormat = new DecimalFormat("0.00");
    public static DecimalFormat procentFormat = new DecimalFormat("00.00");
    public static int MAXLVL = 130;

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str.replace(">>", "»").replace("<<", "«"));
    }

    public static List<String> format(List<String> lore) {

        lore.replaceAll(Utils::format);
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

    public static String muteBroadcast(final String namePlayerToMute, final String senderName, final String reason, final String muteExpiry) {
        return (MUTEPREFIX + format("&7Gracz&c " + namePlayerToMute + " &7zostal wyciszony na serwerze przez&c " + senderName + ". &7Wygasa: " + muteExpiry + ". &7Powod:&c " + reason));
    }


    public static String unBanBroadcast(final String namePlayerToUnBan, final String senderName) {
        return (BANPREFIX + format("&7Gracz&c " + namePlayerToUnBan + " &7zostal odbanowany przez&c " + senderName));
    }

    public static String unMuteBroadcast(final String namePlayerToUnMute, final String senderName) {
        return (MUTEPREFIX + format("&7Gracz&c " + namePlayerToUnMute + " &7zostal odciszony przez&c " + senderName));
    }

    public static String theSenderCannotBeTarget(final String type) {
        return (SERVERNAME + format("&cNie mozesz " + type + " samego siebie!"));
    }

    public static String getWhiteListMessage() {
        return format(WHITELIST + "\n" +
                "&6Witaj &f@p&6!\n" +
                "&6Aktualnie serwer jest &cniedostepny &6dla graczy\n" +
                "&6Wiecej informacji znajdziesz\n" +
                "&6Na naszych social mediach\n" +
                "&6Discord: &fdc.hellrpg.pl\n" +
                "&6Facebook: &ffb.hellrpg.com");
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


            toRemove = toRemove.replace(znak + kolor, "");
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

    public static String[] playerInventoryToBase64(final PlayerInventory playerInventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        final String content = toBase64(playerInventory);
        final String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[]{content, armor};
    }

    public static String itemStackArrayToBase64(final ItemStack[] items) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static String toBase64(final Inventory inventory) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static Inventory fromBase64(final String data, final String nazwaInventory) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(), Utils.format(nazwaInventory));

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(final String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            final ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static String serializeItem(ItemStack item) {
        String encodedObject;
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(item);
            os.flush();
            byte[] serializedObject = io.toByteArray();
            encodedObject = new String(Base64.getEncoder().encode(serializedObject));
            return encodedObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack deserializeItem(String encodedObject) {
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            byte[] serializedObject = io.toByteArray();
            serializedObject = Base64.getDecoder().decode(encodedObject);
            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void youAreMuted(final Player player, final String adminNick, final String reason, final String expiry) {
        player.sendMessage(format(MUTEPREFIX + "&7Zostales wyciszony przez &c" + adminNick + " &7za: &c" + reason));
        player.sendMessage(format(MUTEPREFIX + "&4Wygasa: &6" + expiry));
    }

    public static String spaceNumber(String numberInString) {
        String afterDot = "";
        if (numberInString.contains(".")) {
            int dotIndex = numberInString.indexOf('.');
            afterDot = numberInString.substring(dotIndex, dotIndex + 3);
            numberInString = numberInString.substring(0, dotIndex);
        }
        numberInString = reverseString(numberInString).replaceAll("...(?!$)", "$0 ");

        if (afterDot.equals("")) {
            return reverseString(numberInString);
        }

        return reverseString(numberInString) + afterDot;
    }

    public static String getGroupColor(final String group) {
        switch (group) {
            case "H@":
            case "Admin":
                return "&4&l" + group + "&c ";
            case "GM":
            case "Mod":
            case "KidMod":
                return "&2&l" + group + "&a ";
            case "Helper":
                return "&3&l" + group + "&b ";
            case "JuniorHelper":
                return "&3&lJrHelper" + "&b ";
            case "ELITA":
                return "&5&lELITA &7";
            case "Svip":
                return "&6&lS&e&lvip &7";
            case "Vip":
                return "&e&lVip &7";
            case "Budowniczy":
                return "&d&lBud &7";
            default:
                return "&7";
        }
    }

    public static void sendToHighStaff(final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()) {
                player.sendMessage(format("&4&lHell&6&lDB " + message));
            }
        }
    }
    public static void sendToStaff(final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isStaff()) {
                player.sendMessage(format(message));
            }
        }
    }

    private static String reverseString(final String str) {
        final StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    public static int getProtectionLevel(final ItemStack is) {

        if (is.getItemMeta().getLore() == null) {
            return 0;
        }

        for (final String s : is.getItemMeta().getLore()) {
            if (removeColor(s).contains("Obrona: ")) {
                return Integer.parseInt(Utils.removeColor(s).substring(Utils.removeColor(s).lastIndexOf(" ") + 1).replace(" ", "").trim());
            }
        }

        return 0;
    }

    public static int getThornsLevel(final ItemStack is) {

        if (is.getItemMeta().getLore() == null) {
            return 0;
        }

        for (final String s : is.getItemMeta().getLore()) {
            if (removeColor(s).contains("Thorns: ")) {
                return Integer.parseInt(Utils.removeColor(s).substring(Utils.removeColor(s).lastIndexOf(" ") + 1).replace(" ", "").trim());
            }
        }

        return 0;
    }

    public static int getSharpnessLevel(final ItemStack is) {

        if (is.getItemMeta().getLore() == null) {
            return 0;
        }

        for (final String s : is.getItemMeta().getLore()) {
            if (removeColor(s).contains("Obrazenia: ")) {
                return Integer.parseInt(Utils.removeColor(s).substring(Utils.removeColor(s).lastIndexOf(" ") + 1).replace(" ", "").trim());
            }
        }

        return 0;
    }

    public static int getObrazeniaMobyLevel(final ItemStack is) {

        if (is.getItemMeta().getLore() == null) {
            return 0;
        }

        for (final String s : is.getItemMeta().getLore()) {
            if (removeColor(s).contains("Obrazenia na potwory: ")) {
                return Integer.parseInt(Utils.removeColor(s).substring(Utils.removeColor(s).lastIndexOf(" ") + 1).replace(" ", "").trim());
            }
        }

        return 0;
    }

    public static long durationFromString(String string, boolean now) {
        if (string == null || string.isEmpty()) {
            return 0L;
        }
        StringBuilder stringBuilder = new StringBuilder();
        long time = now ? System.currentTimeMillis() : 0L;
        char[] var5 = string.toCharArray();

        for (char character : var5) {
            if (Character.isDigit(character)) {
                stringBuilder.append(character);
            } else {
                int amount = Integer.parseInt(stringBuilder.toString());
                switch (character) {
                    case 'd':
                        time += (long) amount * 86400000L;
                        break;
                    case 'h':
                        time += (long) amount * 3600000L;
                        break;
                    case 'm':
                        time += (long) amount * 60000L;
                        break;
                    case 's':
                        time += (long) amount * 1000L;
                }

                stringBuilder = new StringBuilder();
            }
        }

        return time;
    }

    public static String durationToString(long time, boolean now) {
        if (!now) {
            time -= System.currentTimeMillis();
        }

        if (time < 1L) {
            return "<1s";
        }
        long months = TimeUnit.MILLISECONDS.toDays(time) / 30L;
        long days = TimeUnit.MILLISECONDS.toDays(time) % 30L;
        long hours = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(time));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
        StringBuilder stringBuilder = new StringBuilder();
        if (months > 0L) {
            stringBuilder.append(months).append("msc").append(" ");
        }

        if (days > 0L) {
            stringBuilder.append(days).append("d").append(" ");
        }

        if (hours > 0L) {
            stringBuilder.append(hours).append("h").append(" ");
        }

        if (minutes > 0L) {
            stringBuilder.append(minutes).append("m").append(" ");
        }

        if (seconds > 0L) {
            stringBuilder.append(seconds).append("s");
        }

        return stringBuilder.length() > 0 ? stringBuilder.toString().trim() : time + "ms";
    }

    public static double convertIntegersToPercentage(final int n1, final int n2)  {
        return Double.parseDouble(String.valueOf((n1 / n2) * 100));
    }
    public static double convertLongsToPercentage(final long n1, final long n2)  {
        return Double.parseDouble(String.valueOf((n1 / n2) * 100));
    }
    public static double convertDoublesToPercentage(final double n1, final double n2)  {
        return (n1 / n2) * 100;
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }
}