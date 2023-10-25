package rpg.rpgcore.utils.globalitems.npc;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;

public enum NereusItems {
    I1("Fragment-Reliktu", new ItemBuilder(Material.RED_ROSE, 1, (short) 1).setName("&bFragment Reliktu").setLore(Arrays.asList(
            "&8Potrzebny &9&lNereusowi"
    )).toItemStack().clone());


    @Getter
    private final String id;
    private final ItemStack itemStack;

    NereusItems(final String id, final ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack.clone();
    }


    public static NereusItems getById(final String id) {
        for (final NereusItems nereusItems : values()) {
            if (nereusItems.getId().equals(id)) {
                return nereusItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(final String id, final int amount) {
        final NereusItems nereusItems = getById(id);
        if (nereusItems == null) {
            return null;
        }
        final ItemStack itemStack = nereusItems.getItemStack().clone();
        itemStack.setAmount(amount);
        return itemStack;
    }

    private static final List<String> relikty = Arrays.asList("Potegi", "Wiecznosci", "Starozytnosci", "Przodkow");

    public static ItemStack getRandomRelikt() {
        int val;
        switch (relikty.get(ChanceHelper.getRandInt(0, relikty.size() - 1))) {
            case "Potegi":
                val = ChanceHelper.getRandInt(1, 15);
                return new ItemBuilder(Material.CARPET, 1, (short) 14).setName("&4&lRelikt Potegi").setLore(Arrays.asList(
                        "&7Srednie Obrazenia: &c+" + val + "%",
                        "",
                        "&cWymagany poziom: &660"
                )).addTagString("relikt", "Potegi").addTagInt("val", val).addTagInt("lvl", 60).toItemStack();
            case "Wiecznosci":
                val = ChanceHelper.getRandInt(1, 20);
                return new ItemBuilder(Material.CARPET, 1, (short) 13).setName("&2&lRelikt Wiecznosci").setLore(Arrays.asList(
                        "&7Dodatkowe HP: &2+" + val + "HP",
                        "",
                        "&cWymagany poziom: &660"
                )).addTagString("relikt", "Wiecznosci").addTagInt("val", val).addTagInt("lvl", 60).toItemStack();
            case "Starozytnosci":
                val = ChanceHelper.getRandInt(1, 25);
                return new ItemBuilder(Material.CARPET, 1, (short) 11).setName("&6&lRelikt Starozytnosci").setLore(Arrays.asList(
                        "&7Przeszycie Bloku Ciosu: &6+" + val + "%",
                        "",
                        "&cWymagany poziom: &660"
                )).addTagString("relikt", "Starozytnosci").addTagInt("val", val).addTagInt("lvl", 60).toItemStack();
            case "Przodkow":
                val = ChanceHelper.getRandInt(1, 15);
                return new ItemBuilder(Material.CARPET, 1, (short) 5).setName("&a&lRelikt Przodkow").setLore(Arrays.asList(
                        "&7Srednia Defensywa: &a+" + val + "%",
                        "",
                        "&cWymagany poziom: &660"
                )).addTagString("relikt", "Przodkow").addTagInt("val", val).addTagInt("lvl", 60).toItemStack();
            default:
                return null;
        }
    }
}


