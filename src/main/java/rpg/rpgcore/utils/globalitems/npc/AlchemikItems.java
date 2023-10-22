package rpg.rpgcore.utils.globalitems.npc;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;

public enum AlchemikItems {
    I1("Alchemiczny-Pyl", new ItemBuilder(Material.GLOWSTONE_DUST).setName("&6Alchemiczny Pyl").setLore(Arrays.asList(
            "&8Sluzy do wytwarzania Krysztalow Alchemii"
    )).toItemStack().clone()),
    I2("Podrecznik-Alchemii", new ItemBuilder(Material.BOOK).setName("&5Alchemiczny Podrecznik").setLore(Arrays.asList(
            "&8Kliknij, aby zyskac &5+1 &8punkt",
            "&8doswiadczenia alchemickiego"
    )).toItemStack().clone()),
    I3("Sakwa-Alchemiczna", new ItemBuilder(Material.EXP_BOTTLE).setName("&eAlchemiczna Sakwa").toItemStack().clone()),
    I4("Zniszczony-Krysztal-Potegi", new ItemBuilder(Material.REDSTONE).setName("&cZniszczony Alchemicki Krysztal Potegi").setLore(Arrays.asList(
            "&8Potrzebne do wytworzenia",
            "&cAlchemickiego Krysztalu Potegi",
            "",
            "&8Podobno &5&lAlchemik &8tego potrzebuje..."
    )).toItemStack().clone()),
    I5("Zniszczony-Krysztal-Obrony", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&9Zniszczony Alchemicki Krysztal Obrony").setLore(Arrays.asList(
            "&8Potrzebne do wytworzenia",
            "&9Alchemickiego Krysztalu Obrony",
            "",
            "&8Podobno &5&lAlchemik &8tego potrzebuje..."
    )).toItemStack().clone()),
    I6("Zniszczony-Krysztal-Potworow", new ItemBuilder(Material.GOLD_NUGGET).setName("&2Zniszczony Alchemicki Krysztal Potworow").setLore(Arrays.asList(
            "&8Potrzebne do wytworzenia",
            "&2Alchemickiego Krysztalu Potworow",
            "",
            "&8Podobno &5&lAlchemik &8tego potrzebuje..."
    )).toItemStack().clone()),
    I7("Zniszczony-Krysztal-Ludzi", new ItemBuilder(Material.GHAST_TEAR).setName("&4Zniszczony Alchemicki Krysztal Ludzi").setLore(Arrays.asList(
            "&8Potrzebne do wytworzenia",
            "&4Alchemickiego Krysztalu Ludzi",
            "",
            "&8Podobno &5&lAlchemik &8tego potrzebuje..."
    )).toItemStack().clone()),
    I8("Rdzen-Alchemiczny", new ItemBuilder(Material.EMERALD).setName("&aAlchemiczny Rdzen").setLore(Arrays.asList(
            "&8Potrzebne do przekupienia &5&lAlchemika",
            "&8dzieki temu nie spali on twojego przedmiotu",
            "&8podczas ulepszania"
    )).toItemStack().clone());


    @Getter
    private final String id;
    private final ItemStack itemStack;

    AlchemikItems(final String id, final ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack.clone();
    }


    public static AlchemikItems getById(final String id) {
        for (final AlchemikItems alchemikItems : values()) {
            if (alchemikItems.getId().equals(id)) {
                return alchemikItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(final String id, final int amount) {
        final AlchemikItems alchemikItems = getById(id);
        if (alchemikItems == null) {
            return null;
        }
        final ItemStack itemStack = alchemikItems.getItemStack().clone();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack getRandomZnisczonyKrysztal() {
        final List<ItemStack> krysztaly = Arrays.asList(I4.getItemStack(), I5.getItemStack(), I6.getItemStack(), I7.getItemStack());
        return krysztaly.get(ChanceHelper.getRandInt(0, krysztaly.size() - 1)).clone();
    }

    private static final List<String> krysztaly = Arrays.asList("Potegi", "Obrony", "Potworow", "Ludzi");

    public static ItemStack getAlchemicznyKrysztal(String krysztal) {
        double val1, val2;
        if (krysztal.isEmpty()) {
            krysztal = krysztaly.get(ChanceHelper.getRandInt(0, krysztaly.size() - 1));
        }

        switch (krysztal) {
            case "Potegi":
                val1 = ChanceHelper.getRandDouble(0.01, 2);
                val2 = ChanceHelper.getRandInt(1, 25);
                return new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&c&lAlchemicki Krysztal Potegi &8&l+0").setLore(Arrays.asList(
                        "&7Srednie Obrazenia: &5+" + val1 + "%",
                        "&7Dodatkowe Obrazenia: &5+" + (int) val2 + " DMG",
                        "",
                        "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                        "&cWymagany Poziom: &650 lvl"
                )).addTagString("alchKrysztal", "Potegi").addTagInt("krysztalLvl", 0).addTagInt("lvl", 50).addTagInt("alch-lvl", 5)
                        .addTagDouble("srDmg", val1).addTagInt("dodatkowe", (int) val2).toItemStack().clone();
            case "Obrony":
                val1 = ChanceHelper.getRandDouble(0.01, 2);
                val2 = ChanceHelper.getRandDouble(0.01, 1);
                return new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName("&9&lAlchemicki Krysztal Obrony &8&l+0").setLore(Arrays.asList(
                        "&7Srednia Defensywa: &5+" + val1 + "%",
                        "&7Blok Ciosu: &5+" + val2 + "%",
                        "",
                        "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                        "&cWymagany Poziom: &650 lvl"
                )).addTagString("alchKrysztal", "Obrony").addTagInt("krysztalLvl", 0).addTagInt("lvl", 50).addTagInt("alch-lvl", 5)
                        .addTagDouble("srDef", val1).addTagDouble("blok", val2).toItemStack().clone();
            case "Potworow":
                val1 = ChanceHelper.getRandDouble(0.01, 3);
                val2 = ChanceHelper.getRandDouble(0.01, 3);
                return new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName("&2&lAlchemicki Krysztal Potworow &8&l+0").setLore(Arrays.asList(
                        "&7Silny Przeciwko Potwora: &5+" + val1 + "%",
                        "&7Odpornosc na Potwory: &5+" + val2 + "%",
                        "",
                        "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                        "&cWymagany Poziom: &650 lvl"
                )).addTagString("alchKrysztal", "Potworow").addTagInt("krysztalLvl", 0).addTagInt("lvl", 50).addTagInt("alch-lvl", 5)
                        .addTagDouble("silnyNaMoby", val1).addTagDouble("defNaMoby", val2).toItemStack().clone();
            case "Ludzi":
                val1 = ChanceHelper.getRandDouble(0.01, 3);
                val2 = ChanceHelper.getRandDouble(0.01, 3);
                return new ItemBuilder(Material.INK_SACK, 1, (short) 7).setName("&4&lAlchemicki Krysztal Ludzi &8&l+0").setLore(Arrays.asList(
                        "&7Silny Przeciwko Ludziom: &5+" + val1 + "%",
                        "&7Odpornosc na Ludziom: &5+" + val2 + "%",
                        "",
                        "&cWymagany Poziom &8(&5Alchemicki&8): &65 lvl",
                        "&cWymagany Poziom: &650 lvl"
                )).addTagString("alchKrysztal", "Ludzi").addTagInt("krysztalLvl", 0).addTagInt("lvl", 50).addTagInt("alch-lvl", 5)
                        .addTagDouble("silnyNaLudzi", val1).addTagDouble("defNaLudzi", val2).toItemStack().clone();
            default:
                return null;
        }
    }

    public static ItemStack getSpecificKrysztal(final String krysztal, final double val1, final double val2, final int krysztalLvl, final int alchLvl, final int lvl) {
        switch (krysztal) {
            case "Potegi":
                return new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&c&lAlchemicki Krysztal Potegi &8&l+" + krysztalLvl).setLore(Arrays.asList(
                                "&7Srednie Obrazenia: &5+" + val1 + "%",
                                "&7Dodatkowe Obrazenia: &5+" + (int) val2 + " DMG",
                                "",
                                "&cWymagany Poziom &8(&5Alchemicki&8): &6" + alchLvl + " lvl",
                                "&cWymagany Poziom: &6" + lvl +" lvl"
                        )).addTagString("alchKrysztal", "Potegi").addTagInt("krysztalLvl", krysztalLvl).addTagInt("lvl", lvl).addTagInt("alch-lvl", alchLvl)
                        .addTagDouble("srDmg", val1).addTagInt("dodatkowe", (int) val2).toItemStack().clone();
            case "Obrony":
                return new ItemBuilder(Material.INK_SACK, 1, (short) 12).setName("&9&lAlchemicki Krysztal Obrony &8&l+" + krysztalLvl).setLore(Arrays.asList(
                                "&7Srednia Defensywa: &5+" + val1 + "%",
                                "&7Blok Ciosu: &5+" + val2 + "%",
                                "",
                                "&cWymagany Poziom &8(&5Alchemicki&8): &6" + alchLvl + " lvl",
                                "&cWymagany Poziom: &6" + lvl +" lvl"
                        )).addTagString("alchKrysztal", "Obrony").addTagInt("krysztalLvl", krysztalLvl).addTagInt("lvl", lvl).addTagInt("alch-lvl", alchLvl)
                        .addTagDouble("srDef", val1).addTagDouble("blok", val2).toItemStack().clone();
            case "Potworow":
                return new ItemBuilder(Material.INK_SACK, 1, (short) 11).setName("&2&lAlchemicki Krysztal Potworow &8&l+" + krysztalLvl).setLore(Arrays.asList(
                                "&7Silny Przeciwko Potwora: &5+" + val1 + "%",
                                "&7Odpornosc na Potwory: &5+" + val2 + "%",
                                "",
                                "&cWymagany Poziom &8(&5Alchemicki&8): &6" + alchLvl + " lvl",
                                "&cWymagany Poziom: &6" + lvl +" lvl"
                        )).addTagString("alchKrysztal", "Potworow").addTagInt("krysztalLvl", krysztalLvl).addTagInt("lvl", lvl).addTagInt("alch-lvl", alchLvl)
                        .addTagDouble("silnyNaMoby", val1).addTagDouble("defNaMoby", val2).toItemStack().clone();
            case "Ludzi":
                return new ItemBuilder(Material.INK_SACK, 1, (short) 7).setName("&4&lAlchemicki Krysztal Ludzi &8&l+" + krysztalLvl).setLore(Arrays.asList(
                                "&7Silny Przeciwko Ludziom: &5+" + val1 + "%",
                                "&7Odpornosc na Ludziom: &5+" + val2 + "%",
                                "",
                                "&cWymagany Poziom &8(&5Alchemicki&8): &6" + alchLvl + " lvl",
                                "&cWymagany Poziom: &6" + lvl +" lvl"
                        )).addTagString("alchKrysztal", "Ludzi").addTagInt("krysztalLvl", krysztalLvl).addTagInt("lvl", lvl).addTagInt("alch-lvl", alchLvl)
                        .addTagDouble("silnyNaLudzi", val1).addTagDouble("defNaLudzi", val2).toItemStack().clone();
            default:
                return null;
        }
    }
}

