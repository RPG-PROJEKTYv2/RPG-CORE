package rpg.rpgcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
    public static final String NIEGRACZ = format("&cNie jesteś graczem!");
    public static final String SERVERNAME = format("&4&lHELL&8&lRPG&7 &8>> ");
    public static final String CLEANSERVERNAME = format("&4&lHELL&8&lRPG&7");
    public static final String BANPREFIX = format("&4&lHELL&8&lBAN&7 &8>> ");
    public static final String MUTEPREFIX = format("&4&lHELL&8&lMUTE&7 &8>> ");
    public static final String LVLPREFIX = format("&4&lHELL&6&lLVL&7 &8>> ");
    public static final String KICKPREFIX = format("&4&lHELL&8&lKICK&7 &8>> ");
    public static final String TRADEPREFIX = format("&4&lHELL&8&lTRADE&7 &8>> ");
    public static final String GUILDSPREFIX = format("&4&lHELL&8&lKLANY&7 &8>> ");
    public static final String WHITELIST = format("&7&lWHITE&6&lLIST&7 >>");
    public static final String RYBAK = format("&6&lRybak &8>> &7");
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
    public static ItemStack powrot() {
        return new ItemBuilder(Material.SKULL_ITEM, 1, (short)3).setSkullOwnerByURL(
                "25a86f8d-7901-4ff9-b742-40f8fef19db0",
                "skin1c0c662d",
                "ewogICJ0aW1lc3RhbXAiIDogMTY4NDA4NzkxNzg3NSwKICAicHJvZmlsZUlkIiA6ICI0OW" +
                        "IzODUyNDdhMWY0NTM3YjBmN2MwZTFmMTVjMTc2NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJi" +
                        "Y2QyMDMzYzYzZWM0YmY4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidG" +
                        "V4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4d" +
                        "HVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzIwY2ZiNGYzN2NjZWZkMDU4OWM1NTc4YjU0" +
                        "MWU3YWY5MjNlM2U2NDIwYWRhNmJlNDQzZGZhZGNiMDViYWUxOTQiLAogICAgICAibWV0YWR" +
                        "hdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==",
                "abFP3xIqv1suUFfHx0FRPkTaT9fP4c+F2kUAASAtariuJiL6n3tPF6AtI67lntin9NWz" +
                        "WBjMLZPLS4mS06dJRuGepzBMm9aC+XXGzssmiWKfnOmJ+MCdqnwFbfQSvW/ObELFpSwJsK" +
                        "IzsRAQNp/ZIoE/PcOeTpQ6thwCBIAYVRW6eaPe7IP69E7fw8e/B7H7X98C9TVyRCPY/aEP" +
                        "SX1eLSxqFW6+IyAgt/PZ4MAkutBQXOHs19H0Wgr32UMgGiAjLNmu+S/hrAYI1pHwI87kQ6V" +
                        "7K/p62a5qckZW+832pcHHNUVsibIE1kAYKcSdBljS9Kd1wrgMUOm3nZFM3EK4CYP3P/+7o" +
                        "75izbmpR2DQO7+kKXtHyyixdFDquSwN/jjpPIRyN/Akya8iULSxXUGH23J9V+3RkQMfdCM" +
                        "YnC53gT7J82ySbGjUWme6z+ByooNOc27viYpeSkNP8U1EuH26QiLlm0GXnEzfHaTbHfV+W" +
                        "4vquxZlZs4aBK4A45YZGKg7Kh+GcYfx7ItB6VjlggNhYAvRjR0Xlo3TGbRxgN2YBvSUGo2" +
                        "O6li3WrbbKSXhTox35rp524h7COOgXYhHyO7s9b4e443ec683vYyavJSyo0xADo+Co4s4x" +
                        "mGsAiT8UQ65FacLPCnLEnJS0KJl7GWfANAH60R3+vfadQxfgL4CpTI="
        ).setName("&cPowrot").toItemStack().clone();
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
        return format(CLEANSERVERNAME + format("\n" +
                "&7Zostales wyrzucony z serwera\n" +
                "&7Przez: &c" + senderName +
                "\n&7Za: &c" + reason +
                "\n\n&8Jezeli chcesz zglosic skarge na administratora, ktory wyrzucil Cie z" +
                " serwera\n" +
                "&8zglos sie na przykladowyts3.pl lub przykladowydc z ss tego kicka!"));
    }

    public static String banMessage(final String senderName, final String reason, final String banExpiry, final String dateOfBan) {
        return format(CLEANSERVERNAME + "\n&7Zostales zablokowany na tym serwerze" +
                "\n&7Za:&c " + reason +
                "\n&7Wygasa:&c " + banExpiry +
                "\n&7Nadany:&c " + dateOfBan +
                "\n&7Przez:&c " + senderName +
                "\n\n&8&lJezeli uwazasz ze to blad, skontaktuj sie" +
                "\n&8&lz &4&lAdministracja &8&lserwera lub napisz ticket na dc &6&ldc.hellrpg.pl");
    }

    public static String kick(final String namePlayerToBan, final String senderName, final String reason) {
        return (KICKPREFIX + format("&7Gracz &c" + namePlayerToBan + " &7zostal wyrzucony z serwera przez &c" + senderName + ". &7Powod: &c" + reason));
    }

    public static String banBroadcast(final String namePlayerToBan, final String senderName, final String reason, final String banExpiry) {
        return (BANPREFIX + format("&7Gracz &c" + namePlayerToBan + " &7zostal zbanowany na serwerze przez &c" + senderName + "&7. Wygasa: &c" + banExpiry + "&7. Powod: &c" + reason));
    }

    public static String muteBroadcast(final String namePlayerToMute, final String senderName, final String reason, final String muteExpiry) {
        return (MUTEPREFIX + format("&7Gracz &c" + namePlayerToMute + " &7zostal wyciszony na serwerze przez &c" + senderName + "&7. Wygasa: &c" + muteExpiry + "&7. Powod: &c" + reason));
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
            if (index + 1 >= toRemove.length()) break;
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
        if (data.isEmpty()) return new ItemStack[0];
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
        if (encodedObject.isEmpty()) return new ItemStack(Material.AIR);
        try {
            byte[] serializedObject;
            serializedObject = Base64.getDecoder().decode(encodedObject);
            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String itemStackArrayToJSON(final ItemStack[] items) {
        final JSONObject itemsJ = new JSONObject();
        int i = 1;
        for (ItemStack item : items) {
            itemsJ.put("Item-" + i, itemStackToJSON(item));
            i++;
        }
        return itemsJ.toString();
    }

    public static String itemStackArrayToJSON(final List<ItemStack> items) {
        final JSONObject itemsJ = new JSONObject();
        int i = 1;
        for (ItemStack item : items) {
            itemsJ.put("Item-" + i, itemStackToJSON(item));
            i++;
        }
        return itemsJ.toString();
    }

    public static String itemStackToJSON(final ItemStack item) {
        final JSONObject itemJ = new JSONObject();
        if (item == null) {
            itemJ.put("type", "null");
            return itemJ.toString();
        }
        final String itemType = item.getType().toString();
        itemJ.put("type", itemType);
        itemJ.put("amount", item.getAmount());
        itemJ.put("durability", item.getDurability());
        final JSONObject itemMetaJ = new JSONObject();
        final ItemMeta itemMeta = item.getItemMeta();
        if (item.hasItemMeta()) {
            if (itemMeta.hasDisplayName()) {
                itemMetaJ.put("displayName", itemMeta.getDisplayName());
            }
            if (itemMeta.hasLore()) {
                final JSONArray loreJ = new JSONArray();
                itemMeta.getLore().forEach(loreJ::put);
                itemMetaJ.put("lore", loreJ);
            }
            if (!itemMeta.getItemFlags().isEmpty()) {
                final JSONArray itemFlagsJ = new JSONArray();
                itemMeta.getItemFlags().forEach(itemFlag -> itemFlagsJ.put(itemFlag.name()));
                itemMetaJ.put("itemFlags", itemFlagsJ);
            }
            if (itemMeta instanceof SkullMeta) {
                final SkullMeta skullMeta = (SkullMeta) itemMeta;
                if (skullMeta.hasOwner()) {
                    GameProfile profile = null;
                    Field profileField = null;

                    try {
                        profileField = skullMeta.getClass().getDeclaredField("profile"); // Get the profile field from the SkullMeta object using reflection
                        profileField.setAccessible(true); // Make the field accessible
                        profile = (GameProfile) profileField.get(skullMeta); // Get the GameProfile object from the profile field
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    if (profile != null) {
                        Property texturesProperty = profile.getProperties().get("textures").iterator().next(); // Get the textures property from the GameProfile object

                        if (texturesProperty != null) {
                            String texture = texturesProperty.getValue(); // Get the texture field from the textures property
                            String signature = texturesProperty.getSignature(); // Get the signature field from the textures property

                            itemMetaJ.put("skullTexture", texture);
                            itemMetaJ.put("skullSignature", signature);
                        }
                    }
                }
            }

            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
            if (nmsStack.hasTag()) {
                final NBTTagCompound tag = nmsStack.getTag();
                final JSONObject tagJ = new JSONObject();
                for (String key : tag.c()) {
                    final NBTBase value = tag.get(key);
                    tagJ.put(key, value);
                }
                itemMetaJ.put("tags", tagJ);
            }
        } else {
            itemMetaJ.put("meta", new JSONObject().put("displayName", item.getType().name()));
        }
        itemJ.put("meta", itemMetaJ);


        return itemJ.toString();
    }

    public static ItemStack[] deserializeStringsToItemStackArray(final String... args) {
        final ItemStack[] items = new ItemStack[args.length];
        int i = 0;
        for (String s : args) {
            if (s.isEmpty()) {
                items[i] = new ItemStack(Material.AIR);
            } else {
                items[i] = deserializeItem(s);
            }
            i++;
        }
        return items;
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

    public static String spaceNumber(double number) {
        String numberInString = String.format("%.2f", number);
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
    public static String spaceNumber(int number) {
        String numberInString = String.valueOf(number);
        numberInString = reverseString(numberInString).replaceAll("...(?!$)", "$0 ");
        return reverseString(numberInString);
    }

    public static void sendToHighStaff(final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                if (!RPGCORE.getInstance().getChatManager().find(player.getUniqueId()).isDatabaseMessageEnabled()) continue;
                player.sendMessage(format("&4&lHell&6&lINFO " + message));
            }
        }
    }

    public static void sendToHighStaff(final TextComponent message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                player.spigot().sendMessage(new TextComponent(format("&4&lHell&6&lINFO ")), message);
            }
        }
    }

    public static void sendToStaff(final String message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isStaff() && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                player.sendMessage(format(message));
            }
        }
    }

    public static void sendToStaff(final TextComponent message) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isStaff() && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
                player.spigot().sendMessage(message);
            }
        }
    }

    private static String reverseString(final String str) {
        final StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
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


    public static double convertDoublesToPercentage(final double n1, final double n2) {
        return DoubleUtils.round((n1 / n2) * 100, 2);
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

    public static boolean checkIfLoreContainsString(final List<String> lore, final String toCheck) {
        for (final String s : lore) {
            if (removeColor(s).contains(toCheck)) {
                return true;
            }
        }
        return false;
    }

    public static String getLoreLineColored(final ItemStack item, final int line) {
        try {
            return format(item.getItemMeta().getLore().get(line));
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    public static void removeBonuses(final UUID uuid, final String bonus) {
        final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
        final String bonusName = removeColor(bonus.substring(0, bonus.lastIndexOf(" ")).replace(":", ""));
        final double bonusValue = Double.parseDouble(removeColor(bonus.substring(bonus.lastIndexOf(" ") + 1).replace(" ", "").replace("%", "").trim()));

        switch (bonusName) {
            case "Srednie Obrazenia":
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() - bonusValue);
                break;
            case "Dodatkowe Obrazenia":
                bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() - (int) bonusValue);
                break;
            case "Silny Na Ludzi":
                bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() - bonusValue);
                break;
            case "Silny Przeciwko Potworom":
                bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() - bonusValue);
                break;
            case "Srednia Defensywa":
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() - bonusValue);
                break;
            case "Odpornosc Na Ludzi":
                bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() - bonusValue);
                break;
            case "Odpornosc Przeciwko Potworom":
                bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() - bonusValue);
                break;
            case "Szybkosc":
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() - (int) bonusValue);
                break;
            case "Szczescie":
                bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() - (int) bonusValue);
                break;
            case "Dodatkowe HP":
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() - (int) bonusValue);
                break;
            case "Szansa Na Cios Krytyczny":
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() - bonusValue);
                break;
            case "Szansa Na Wzmocnienie Ciosu Krytycznego":
                bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() - bonusValue);
                break;
            case "Dodatkowy EXP":
                bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - bonusValue);
            case "Szansa Na Przebicie Pancerza":
                bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() - bonusValue);
                break;
            case "Szansa Na Spowolnienie":
                bonuses.getBonusesUser().setSpowolnienie(bonuses.getBonusesUser().getSpowolnienie() - bonusValue);
                break;
            case "Szansa Na Oslepienie":
                bonuses.getBonusesUser().setOslepienie(bonuses.getBonusesUser().getOslepienie() - bonusValue);
                break;
        }
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataBonuses(bonuses.getId(), bonuses));
    }



    public static double addBonuses(final UUID uuid, final String bonus) {
        final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
        final String bonusName = removeColor(bonus.substring(0, bonus.lastIndexOf(" ")).replace(":", ""));
        final double bonusValue = Double.parseDouble(removeColor(bonus.substring(bonus.lastIndexOf(" ") + 1).replace(" ", "").replace("%", "").trim()));

        switch (bonusName) {
            case "Srednie Obrazenia":
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + bonusValue);
                break;
            case "Dodatkowe Obrazenia":
                bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + (int) bonusValue);
                break;
            case "Silny Na Ludzi":
                bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + bonusValue);
                break;
            case "Silny Przeciwko Potworom":
                bonuses.getBonusesUser().setSilnynapotwory(bonuses.getBonusesUser().getSilnynapotwory() + bonusValue);
                break;
            case "Srednia Defensywa":
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + bonusValue);
                break;
            case "Odpornosc Na Ludzi":
                bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + bonusValue);
                break;
            case "Odpornosc Przeciwko Potworom":
                bonuses.getBonusesUser().setDefnamoby(bonuses.getBonusesUser().getDefnamoby() + bonusValue);
                break;
            case "Szybkosc":
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + (int) bonusValue);
                break;
            case "Szczescie":
                bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + (int) bonusValue);
                break;
            case "Dodatkowe HP":
                bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + (int) bonusValue);
                break;
            case "Szansa Na Cios Krytyczny":
                bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + bonusValue);
                break;
            case "Szansa Na Wzmocnienie Ciosu Krytycznego":
                bonuses.getBonusesUser().setSzansanawzmocnieniekryta(bonuses.getBonusesUser().getSzansanawzmocnieniekryta() + bonusValue);
                break;
            case "Dodatkowy EXP":
                bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() + bonusValue);
            case "Szansa Na Przebicie Pancerza":
                bonuses.getBonusesUser().setPrzebiciePancerza(bonuses.getBonusesUser().getPrzebiciePancerza() + bonusValue);
                break;
            case "Szansa Na Spowolnienie":
                bonuses.getBonusesUser().setSpowolnienie(bonuses.getBonusesUser().getSpowolnienie() + bonusValue);
                break;
            case "Szansa Na Oslepienie":
                bonuses.getBonusesUser().setOslepienie(bonuses.getBonusesUser().getOslepienie() + bonusValue);
                break;
        }
        System.out.println("Dodano " + bonusName + " " + bonusValue);
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataBonuses(bonuses.getId(), bonuses));
        return bonusValue;
    }

    public static String getTagString(final ItemStack is, final String tag){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        if (!nmsStack.hasTag()) return "";
        NBTTagCompound tagCompound = nmsStack.getTag();
        if (tagCompound.hasKey(tag)) return tagCompound.getString(tag);
        return "";
    }
    public static double getTagDouble(final ItemStack is, final String tag){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        if (!nmsStack.hasTag()) return 0;
        NBTTagCompound tagCompound = nmsStack.getTag();
        if (tagCompound.hasKey(tag)) return DoubleUtils.round(tagCompound.getDouble(tag), 2);
        return 0;
    }

    public static int getTagInt(final ItemStack is, final String tag){
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        final NBTTagCompound tagCompound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if (tagCompound.hasKey(tag)) return tagCompound.getInt(tag);
        return 0;
    }

    public static boolean getTagBoolean(final ItemStack is, final String tag){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        if (!nmsStack.hasTag()) return false;
        NBTTagCompound tagCompound = nmsStack.getTag();
        if (tagCompound.hasKey(tag)) return tagCompound.getBoolean(tag);
        return false;
    }

    public static void setTagString(final ItemStack is, final String tag, final String value){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tagCompound;
        if (!nmsStack.hasTag()) {
            tagCompound = new NBTTagCompound();
            nmsStack.setTag(tagCompound);
        } else {
            tagCompound = nmsStack.getTag();
        }
        tagCompound.setString(tag, value);
        nmsStack.setTag(tagCompound);
        is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
    }

    public static void setTagDouble(final ItemStack is, final String tag, final double value){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tagCompound;
        if (!nmsStack.hasTag()) {
            tagCompound = new NBTTagCompound();
            nmsStack.setTag(tagCompound);
        } else {
            tagCompound = nmsStack.getTag();
        }
        tagCompound.setDouble(tag, DoubleUtils.round(value, 2));
        nmsStack.setTag(tagCompound);
        is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
    }

    public static void setTagInt(final ItemStack is, final String tag, final int value){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tagCompound;
        if (!nmsStack.hasTag()) {
            tagCompound = new NBTTagCompound();
            nmsStack.setTag(tagCompound);
        } else {
            tagCompound = nmsStack.getTag();
        }
        tagCompound.setInt(tag, value);
        nmsStack.setTag(tagCompound);
        is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
    }

    public static void setTagBoolean(final ItemStack is, final String tag, final boolean value){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tagCompound;
        if (!nmsStack.hasTag()) {
            tagCompound = new NBTTagCompound();
            nmsStack.setTag(tagCompound);
        } else {
            tagCompound = nmsStack.getTag();
        }
        tagCompound.setBoolean(tag, value);
        nmsStack.setTag(tagCompound);
        is.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
    }

    public static int getPlayerInventoryItemCount(final Player player, final ItemStack itemStack) {
        int count = 0;
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.isSimilar(itemStack)) count += item.getAmount();
        }
        return count;
    }
    public static int getPlayerInventoryItemCount(final Player player, final Material material) {
        int count = 0;
        for (final ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType().equals(material)) count += item.getAmount();
        }
        return count;
    }

    public static BlockFace getDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 67.5) {
            return BlockFace.NORTH;
        } else if (67.5 <= rotation && rotation < 157.5) {
            return BlockFace.EAST;
        } else if (157.5 <= rotation && rotation < 247.5) {
            return BlockFace.SOUTH;
        } else if (247.5 <= rotation && rotation < 337.5) {
            return BlockFace.WEST;
        } else if (337.5 <= rotation && rotation < 360.0) {
            return BlockFace.NORTH;
        }
        return BlockFace.NORTH;
    }

    public static String getPreciseDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "N";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }

    public static int fly(final Entity entity) {
        return RPGCORE.getInstance().getServer().getScheduler().scheduleAsyncRepeatingTask(RPGCORE.getInstance(), () -> {
            final Location location = entity.getLocation().clone();
            double delta = Math.sin(Math.toRadians(System.currentTimeMillis() / 100.0)*8) / 20;
            location.add(0, delta, 0);
            entity.teleport(location);
        }, 0L, 1L);
    }
    public static int flyAndTarget(final Entity entity, final LivingEntity target) {
        return RPGCORE.getInstance().getServer().getScheduler().scheduleAsyncRepeatingTask(RPGCORE.getInstance(), () -> {
            ((Creature) entity).setTarget(target);
            final Location location = entity.getLocation().clone();
            double delta = Math.sin(Math.toRadians(System.currentTimeMillis() / 100.0)*8) / 20;
            location.add(0, delta, 0);
            System.out.println(location.getY());
            //entity.teleport(location);
        }, 0L, 1L);
    }

    public static int flyAndSpin(final Entity entity) {
        return RPGCORE.getInstance().getServer().getScheduler().scheduleAsyncRepeatingTask(RPGCORE.getInstance(), () -> {
            final Location location = entity.getLocation().clone();
            location.setYaw(location.getYaw() + 10);
            double delta = Math.sin(Math.toRadians(System.currentTimeMillis() / 100.0)*8) / 20;
            location.add(0, delta, 0);
            entity.teleport(location);
        }, 0L, 1L);
    }

}