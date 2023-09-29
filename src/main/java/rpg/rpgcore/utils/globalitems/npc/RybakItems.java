package rpg.rpgcore.utils.globalitems.npc;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.npc.rybak.enums.LureBonuses;
import rpg.rpgcore.npc.rybak.enums.WedkaExp;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    I14_1("Wilgotny-WegielX64", new ItemBuilder(Material.COAL).setName("&8Wilgotny Wegiel &7(x64)").toItemStack().clone()),

    //                                            WYSPA 2                                           //

    I15("Okon", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Okon").toItemStack().clone()),
    I15_1("OkonX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Okon &7(x64)").toItemStack().clone()),
    I16("Pstrag", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Pstrag").toItemStack().clone()),
    I16_1("PstragX64", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Pstrag &7(x64)").toItemStack().clone()),
    I17("Sandacz", new ItemBuilder(Material.COOKED_FISH).setName("&6Sandacz").toItemStack().clone()),
    I17_1("SandaczX64", new ItemBuilder(Material.COOKED_FISH).setName("&6Sandacz &7(x64)").toItemStack().clone()),
    I18("Plotka", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Plotka").toItemStack().clone()),
    I18_1("PlotkaX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Plotka &7(x64)").toItemStack().clone()),
    I19("Amur", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Amur").toItemStack().clone()),
    I19_1("AmurX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Amur &7(x64)").toItemStack().clone()),
    I20("Dorsz", new ItemBuilder(Material.RAW_FISH).setName("&6Dorsz").toItemStack().clone()),
    I20_1("DorszX64", new ItemBuilder(Material.RAW_FISH).setName("&6Dorsz &7(x64)").toItemStack().clone()),
    I21("Losos", new ItemBuilder(Material.RAW_FISH, 1, (short) 2).setName("&6Losos").toItemStack().clone()),
    I21_1("LososX64", new ItemBuilder(Material.RAW_FISH, 1, (short) 2).setName("&6Losos &7(x64)").toItemStack().clone()),
    I22("Nies-Wyspa2", new ItemBuilder(Material.EMERALD_BLOCK).setName("&3&lNiesamowity Przedmioty &8&l(&7&lStara Fabryka&8&l)").toItemStack().clone()),
    I23("Skarb-Wyspa2", new ItemBuilder(Material.CHEST).setName("&b&lPodwodny Skarb").setLore(Arrays.asList(
            "&8&oKliknij i zobacz co skrywa"
    )).toItemStack().clone()),
    I24("Krysztal-Oceanicznej-Grozy", new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName("&5&lKrysztal &b&lOceanicznej Grozy").addTagString("krysztal", "Oceanicznej Grozy").toItemStack().clone()),
    I25("Krysztal-Zlotej-Rybki", new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName("&5&lKrysztal &6&lZlotej Rybki").addTagString("krysztal", "Zlotej Rybki").toItemStack().clone()),
    I26("Krysztal-Wladcy-Oceanow", new ItemBuilder(Material.INK_SACK, 1, (short) 6).setName("&5&lKrysztal &3&lWladcy Oceanow").addTagString("krysztal", "Wladcy Oceanow").toItemStack().clone()),
    I27("Krysztal-Rzecznego-Krola", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&5&lKrysztal &9&lRzecznego Krola").addTagString("krysztal", "Rzecznego Krola").toItemStack().clone()),
    I28("Krysztal-Mrocznych-Wod", new ItemBuilder(Material.INK_SACK).setName("&5&lKrysztal &8&lMrocznych Wod").addTagString("krysztal", "Mrocznych Wod").toItemStack().clone()),
    I29("Krysztal-Podwodnych-Spiewow", new ItemBuilder(Material.INK_SACK, 1, (short) 7).setName("&5&lKrysztal &f&lPodwodnych Spiewow").addTagString("krysztal", "Podwodnych Spiewow").toItemStack().clone());

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

    public static ItemStack getStaraWedka(final String name, final int udanePolowy) {
        return new ItemBuilder(Material.FISHING_ROD).setName("&6Stara Wedka").setLore(Arrays.asList(
                        "&7Poziom: &f1",
                        "&7Exp: &f0&7/&f20",
                        "&7Udane Polowy: &f" + udanePolowy,
                        "",
                        "&f&lBONUSY",
                        "&7Szansa Na Podwojenie Polowu: &f0%",
                        "",
                        "&7Wlasnosc: &6" + name
                )).addTagInt("lvl", 1).addTagInt("exp", 0).addTagInt("reqExp", 20).addTagInt("udanePolowy", udanePolowy).addTagDouble("doubleDrop", 0)
                .addTagString("owner", name).toItemStack().clone();
    }

    public static ItemStack getSlabaWedkaRybacka(final String name, final int lvl, final int exp, final int udanePolowy, final double doubleDrop) {
        final List<String> lore = new ArrayList<>();
        lore.add("&7Poziom: &f" + lvl);
        lore.add("&7Exp: &f" + exp + "&7/&f" + WedkaExp.getWedkaExp(lvl + 1).getExp());
        lore.add("&7Udane Polowy: &f" + udanePolowy);
        final int lureLvl = lvl / 10;
        final int fishingSpeed = LureBonuses.getLureFishingSpeed(lureLvl);
        if (lvl >= 10) {
            lore.add("");
            if (lureLvl == 7) {
                lore.add("&6Lure VII");
                lore.add("&7Daje Ci &3+" + fishingSpeed + " szybkosci lowienia");
            } else {
                lore.add("&bLure " + Utils.intToRoman(lureLvl));
                lore.add("&7Daje Ci &3+" + fishingSpeed + " szybkosci lowienia");
            }
        }
        return new ItemBuilder(Material.FISHING_ROD).setName("&6Slaba Wedka Rybacka").setLoreCrafting(lore, Arrays.asList(
                        "",
                        "&f&lBONUSY",
                        "&7Szansa Na Podwojenie Polowu: &f" + DoubleUtils.round(doubleDrop, 1) + "%",
                        "&7Szansa Na Zwyklego Potwora: &f0%",
                        "&7Szansa Na Krysztal: &f0%",
                        "",
                        "&7Aktywny Krysztal: &cBRAK",
                        "",
                        "&7Wlasnosc: &6" + name
                ))
                .addTagInt("lvl", lvl).addTagInt("exp", exp).addTagInt("reqExp", WedkaExp.getWedkaExp(lvl + 1).getExp())
                .addTagInt("udanePolowy", udanePolowy).addTagDouble("doubleDrop", doubleDrop).addTagDouble("zwyklyMob", 0)
                .addTagDouble("krysztalDrop", 0).addTagString("krysztal", "BRAK").addTagDouble("krysztalValue", 0)
                .addTagInt("lure-lvl", lureLvl).addTagInt("lure-speed", fishingSpeed).addTagString("owner", name).toItemStack().clone();
    }

    public static ItemStack getRandomKrysztal() {
        final List<RybakItems> krysztaly = new ArrayList<>();
        krysztaly.add(I24);
        krysztaly.add(I25);
        krysztaly.add(I26);
        krysztaly.add(I27);
        krysztaly.add(I28);
        krysztaly.add(I29);
        Collections.shuffle(krysztaly);

        final ItemBuilder krysztal = new ItemBuilder(krysztaly.get(0).getItemStack().clone());

        double value;
        switch (Utils.removeColor(krysztal.toItemStack().getItemMeta().getDisplayName())) {
            case "Krysztal Oceanicznej Grozy":
                value = ChanceHelper.getRandDouble(0.1, 5);
                krysztal.setLore(Arrays.asList(
                      "&7Szansa na wylowienie &9Glebinowego Nurka&7: &f" + DoubleUtils.round(value, 2) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 2));
                break;
            case "Krysztal Zlotej Rybki":
                value = ChanceHelper.getRandDouble(0.01, 3);
                krysztal.setLore(Arrays.asList(
                        "&7Szansa na wylowienie &b&lMorskiego Ksiecia&7: &f" + DoubleUtils.round(value, 2) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 2));
                break;
            case "Krysztal Wladcy Oceanow":
                value = ChanceHelper.getRandDouble(0.001, 1.5);
                krysztal.setLore(Arrays.asList(
                        "&7tSzansa na wylowienie &3&lPosejdona&7: &f" + DoubleUtils.round(value, 3) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 3));
                break;
            case "Krysztal Rzecznego Krola":
                value = ChanceHelper.getRandDouble(0.1, 5);
                krysztal.setLore(Arrays.asList(
                        "&7Szansa Na Potrojny Polow: &f" + DoubleUtils.round(value, 1) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 1));
                break;
            case "Krysztal Mrocznych Wod":
                value = ChanceHelper.getRandDouble(0.01, 5);
                krysztal.setLore(Arrays.asList(
                        "&7Szansa Na &3&lNiesamowity Przedmiot: &f" + DoubleUtils.round(value, 2) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 2));
                break;
            case "Krysztal Podwodnych Spiewow":
                value = ChanceHelper.getRandDouble(0.01, 0.4);
                krysztal.setLore(Arrays.asList(
                        "&7Szansa Na &b&lPodwodny Skarb: &f" + DoubleUtils.round(value, 2) + "%"
                )).addTagDouble("krysztalValue", DoubleUtils.round(value, 2));
                break;
        }

        return krysztal.addGlowing().toItemStack().clone();
    }
}
