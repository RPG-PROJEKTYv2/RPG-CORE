package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Skrzynki {

    // Expowisko 1
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &9&lWygnanca").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&8Skrzynia Najemnika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 2
    I3("I3", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &a&lWodza Goblinow").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.CHEST).setName("&fSkrzynia Goblina").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 3
    I5("I5", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &f&lKrola Goryli").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.CHEST).setName("&7Skrzynia Goryla").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 4
    I7("I7", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &7&lPrzekletej Duszy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.CHEST).setName("&8Skrzynia Zjawy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 5
    I9("I9", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &3&lSkrzynia &e&lTrytona").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.CHEST).setName("&3Skrzynia Straznika Swiatyni").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 6
    I11("I11", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &f&lSkrzynia Mitycznego Lodowego Golema").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.CHEST).setName("&bSkrzynia Mroznego Wilka").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 7
    I13("I13", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &1&lSkrzynia Piekielnego Rycerza").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.CHEST).setName("&6Skrzynia Zywiolaka Ognia").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 8
    I15("I15", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &5&lSkrzynia Przekletego Czarnoksieznika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.CHEST).setName("&fSkrzynia Mrocznej Duszy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 9
    I17("I17", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &e&lSkrzynia Mitycznego Pajaka").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I18("I18", new ItemBuilder(Material.CHEST).setName("&6Skrzynia Pustynnego Ptasznika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 10
    I19("I19", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &6&lSkrzynia Podziemnego Rozpruwacza").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I20("I20", new ItemBuilder(Material.CHEST).setName("&eSkrzynia  Podziemnej Lowczyni").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 11
    I21("I21", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &b&lSkrzynia Mitycznego Krakena").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I22("I22", new ItemBuilder(Material.CHEST).setName("&bSkrzynia Podwodnego Straznika").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 12
    I23("I23", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &c&lSkrzynia Hadesu").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I24("I24", new ItemBuilder(Material.CHEST).setName("&fSkrzynia Centaura").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    // Expowisko 13
    I25("I25", new ItemBuilder(Material.ENDER_CHEST).setName("&8[&4&lBOSS&8] &f&lSkrzynia Archaniola").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone()),
    I26("I26", new ItemBuilder(Material.CHEST).setName("&bSkrzynia Straznika Nieba").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone());
    private final ItemStack itemStack;
    private final String name;

    Skrzynki(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Skrzynki rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Skrzynki getByName(String name) {
        for (Skrzynki rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Skrzynki.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }
    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
