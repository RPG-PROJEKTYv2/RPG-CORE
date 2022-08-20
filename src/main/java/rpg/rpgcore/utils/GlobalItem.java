package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum GlobalItem {
    // RYBKI
    I1("I1", new ItemBuilder(Material.RAW_FISH).setName("&6Sledz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Dorsz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Losos").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.RAW_FISH, 1, (short) 2).setName("&6Krasnopiorka").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Dorsz Czarny").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.RAW_FISH).setName("&6Dorada").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.COOKED_FISH).setName("&6Cierniczek").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Fladra").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Karas").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.COOKED_FISH).setName("&6Karp").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Leszcz").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.COOKED_FISH).setName("&6Makrela").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.COOKED_FISH).setName("&6Mintaj").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Okon").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Plotka").setLore(Arrays.asList("&8&oChyba &8&n&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    // INNE
    I16("I16", new ItemBuilder(Material.EXP_BOTTLE, 1).setName("&8• &eSakwa &8•").addGlowing().toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.PAPER).setName("&bBon na powiekszenie magazynu").setLore(Arrays.asList("&8&oChyba &b&lMagazynier &8tego potrzebuje")).addGlowing().toItemStack().clone()),
    I18("I18", new ItemBuilder(Material.EMERALD).setName("&4&lArtefakt &b&lMagazyniera").setLore(Arrays.asList("&8Artefakt ten pozwala na otwieranie","&8swoich magazynow nie chodzac do &b&lMagazyniera","&6Wlasciciel: &7playerName", " ","&4&lARTEFAKT")).addGlowing().toItemStack().clone()),
    I19("I19", new ItemBuilder(Material.BOOK).setName("&a&lZwoj Blogoslawienstwa").setLore(Arrays.asList("&8Przedmiot ten ochroni twoj","&8przedmiot przed spaleniem podczas","&8ulepszania go u &4&lKowala")).addGlowing().toItemStack().clone()),
    I20("I20", new ItemBuilder(Material.EMERALD).setName("&c&lvalue &4&lH&8&lC").setLore(Arrays.asList("&8&oKliknij&8, zeby zasilic swoj balans")).addGlowing().toItemStack().clone()),
    I21("I21", new ItemBuilder(Material.STICK).setName("&6&lMythic &4&lSTICK").setLore(Arrays.asList("&8Ustawienia:","&f* expowisko 1, mobek 1-10-LEVEL1")).addGlowing().toItemStack().clone()),
    // MATERIALY
    I22("I22", new ItemBuilder(Material.GOLD_INGOT).setName("&eZloto").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I23("I23", new ItemBuilder(Material.DIAMOND).setName("&bBrylant").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I24("I24", new ItemBuilder(Material.EMERALD).setName("&aSzmaragd").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I25("I25", new ItemBuilder(Material.REDSTONE).setName("&cPyl").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I26("I26", new ItemBuilder(Material.STONE).setName("&7Kamien").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I27("I27", new ItemBuilder(Material.IRON_INGOT).setName("&8Stal").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I28("I28", new ItemBuilder(Material.SULPHUR).setName("&7Proch").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    // ULEPSZACZE

    // SKRZYNKI PODSTAWOWE
    I29("I29", new ItemBuilder(Material.CHEST).setName("&7&lSkrzynia z Roznosciami").setLore(Arrays.asList(" ","&8&oTa skrzynia zawiera wszelakie materialy...")).hideFlag().toItemStack().clone()),
    I30("I30", new ItemBuilder(Material.CHEST).setName("&6&lSkrzynia z Ulepszaczami").setLore(Arrays.asList(" ","&8&oTa skrzynia zawiera wszelakie ulepszacze...")).hideFlag().toItemStack().clone()),
    I31("I31", new ItemBuilder(Material.CHEST).setName("&b&lSkrzynia z Dodatkami").setLore(Arrays.asList(" ","&8&oTa skrzynia zawiera magiczne przedmioty...")).hideFlag().toItemStack().clone()),
    I32("I32", new ItemBuilder(Material.ENDER_PORTAL_FRAME).setName("&f&lKapsuła Kresu").setLore(Arrays.asList(" ","&8&oOtworz aby dowiedziec sie co kryje ta skrzynia...")).hideFlag().toItemStack().clone()),
    // SKRZYNKI BOSSOW
    I33("I33", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lKufer &9&lWygnanca").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I34("I34", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer &2Elendila").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I35("I35", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer &6Lwa").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I36("I36", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I37("I37", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I38("I38", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I39("I39", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I40("I40", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I41("I41", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I42("I42", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I43("I43", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone()),
    I44("I44", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3Kufer ").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc zawartosc.")).hideFlag().toItemStack().clone());
    // SKRZYNKI EXPOWISK



    private final ItemStack itemStack;
    private final String name;

    GlobalItem(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (GlobalItem rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static GlobalItem getByName(String name) {
        for (GlobalItem rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = GlobalItem.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack getArtefakt(final String itemName, final String playerName) {
        ItemStack itemStack = GlobalItem.getByName(itemName).getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("playerName", playerName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getHellCoin(final int value) {
        ItemStack itemStack = GlobalItem.getByName("I20").getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("value", String.valueOf(value)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
