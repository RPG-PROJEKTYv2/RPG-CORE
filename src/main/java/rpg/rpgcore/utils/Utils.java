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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public static final String BANPREFIX = format("&f&lTEST&3&lBAN&7 ");
    public static final String MUTEPREFIX = format("&f&lTEST&3&lMUTE&7 ");
    public static final String LVLPREFIX = format("&f&lTEST&6&lLVL&7 ");
    public static final String KICKPREFIX = format("&f&lTEST&3&lKICK&7 ");
    public static final String TRADEPREFIX = format("&f&lTEST&3&lTRADE&7 ");
    public static final String WHITELIST = format("&7&lWHITE&6&lLIST&7 ");
    public static final String NIEMATAKIEGOGRACZA = format("&cNie znaleziono podanego gracza");
    public static final String ALREADYBANNED = (BANPREFIX + format("&cTen gracz jest juz zbanowany!"));
    public static final String ALREADYMUTED = (BANPREFIX + format("&cTen gracz jest juz zbanowany!"));
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

    public static String[] playerInventoryToBase64(final PlayerInventory playerInventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        final String content = toBase64(playerInventory);
        final String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[] { content, armor };
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

    public static void youAreMuted(final Player player, final String adminNick, final String reason, final String expiry) {
        player.sendMessage(format(MUTEPREFIX + "&7Zostales wyciszony przez &c" + adminNick + " &7za: &c" + reason));
        player.sendMessage(format(MUTEPREFIX + "&4Wygasa: &6" + expiry));
    }
}