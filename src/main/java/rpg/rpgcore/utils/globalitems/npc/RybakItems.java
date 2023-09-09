package rpg.rpgcore.utils.globalitems.npc;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum RybakItems {
    //                                            SPAWN                                           //
    I1("Zpruchniala-Deska", new ItemBuilder(Material.WOOD).setName("&6Zpruchniala Deska").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I2("Podstawa-Masztu", new ItemBuilder(Material.STICK).setName("&ePodstawa Masztu").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I3("Podarty-Maszt", new ItemBuilder(Material.WEB).setName("&fPodarty Maszt").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I4("Stara-Lodka", new ItemBuilder(Material.BOAT).setName("&6Stara Lodka").setLore(Arrays.asList("&7Potrzebna do przedostania sie na &eWyspe Rybacka", " ", "&8Porozmawiaj z &3&lWloczykijem &8trzymajac ja w rece", "&8zeby sie na nia przedostac")).hideFlag().toItemStack()),
    I5("Kufer-Rybacki", new ItemBuilder(Material.CHEST).setName("&aKufer Rybacki").toItemStack().clone()),
    //                                            WYSPA 1                                           //
    I6("Karp", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Karp").toItemStack().clone()),
    I6_1("KarpX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Karp &7(x64)").toItemStack().clone()),
    I7("Leszcz", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Leszcz").toItemStack().clone()),
    I7_1("LeszczX64", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Leszcz &7(x64)").toItemStack().clone()),
    I8("Karas", new ItemBuilder(Material.RAW_FISH).setName("&6Karas").toItemStack().clone()),
    I8_1("KarasX64", new ItemBuilder(Material.RAW_FISH).setName("&6Karas &7(x64)").toItemStack().clone()),
    I9("Szczupak", new ItemBuilder(Material.COOKED_FISH).setName("&6Szczupak").toItemStack().clone()),
    I9_1("SzczupakX64", new ItemBuilder(Material.COOKED_FISH).setName("&6Szczupak &7(x64)").toItemStack().clone()),
    I10("Wegorz", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Wegorz").toItemStack().clone()),
    I10_1("WegorzX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Wegorz &7(x64)").toItemStack().clone()),
    I11("Zniszczony-But", new ItemBuilder(Material.LEATHER).setName("&6Zniszczony But").toItemStack().clone()),
    I12("Glina", new ItemBuilder(Material.CLAY_BALL).setName("&bGlina").toItemStack().clone()),
    I13("Zylka", new ItemBuilder(Material.STRING).setName("&fZylka").toItemStack().clone()),
    I14("Wilgotny-Wegiel", new ItemBuilder(Material.COAL).setName("&8Wilgotny Wegiel").toItemStack().clone()),
    I14_1("Wilgotny-WegielX64", new ItemBuilder(Material.COAL).setName("&8Wilgotny Wegiel &7(x64)").toItemStack().clone());

    @Getter
    private final String name;
    private final ItemStack itemStack;

    RybakItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static RybakItems getByName(String name) {
        for (RybakItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public static ItemStack getStaraWedka(final String name) {
        return new ItemBuilder(Material.FISHING_ROD).setName("&6Stara Wedka").setLore(Arrays.asList(
                "&7Poziom: &f1",
                "&7Exp: &f0&7/&f20",
                "&7Udane Polowy: &f0",
                "",
                "&f&lBONUSY",
                "&7Szansa Na Podwojenie Polowu: &f0%",
                "",
                "&7Wlasnosc: &6" + name
        )).addTagInt("lvl", 1).addTagInt("exp", 0).addTagInt("reqExp", 20).addTagInt("udanePolowy", 0).addTagDouble("doubleDrop", 0)
                .addTagString("owner", name).toItemStack().clone();
    }
}
