package rpg.rpgcore.npc.rybak.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

@Getter
public enum MlodszyRybakMissions {

    M1(1, new ItemBuilder(Material.BOOK).setName("&7Misja #&c1").setLore(Arrays.asList(
            "&7Wylow &c128 &6Dorszy"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M2(2, new ItemBuilder(Material.BOOK).setName("&7Misja #&c2").setLore(Arrays.asList(
            "&7Wylow &c128 &6Lososi"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M3(3, new ItemBuilder(Material.BOOK).setName("&7Misja #&c3").setLore(Arrays.asList(
            "&7Wylow &c128 &6Plotek"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M4(4, new ItemBuilder(Material.BOOK).setName("&7Misja #&c4").setLore(Arrays.asList(
            "&7Wylow &c128 &6Sandaczy"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M5(5, new ItemBuilder(Material.BOOK).setName("&7Misja #&c5").setLore(Arrays.asList(
            "&7Wylow &c128 &6Postragow"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M6(6, new ItemBuilder(Material.BOOK).setName("&7Misja #&c6").setLore(Arrays.asList(
            "&7Wylow &c128 &6Okoni"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5),
    M7(7, new ItemBuilder(Material.BOOK).setName("&7Misja #&c7").setLore(Arrays.asList(
            "&7Wylow &c128 &6Amurow"
    )).toItemStack().clone(), 128, 25, 0.5, 0.5), //DMG - 175, PRZESZYWKA - 3.5%, DEF - 3.5%


    M8(8, new ItemBuilder(Material.BOOK).setName("&7Misja #&c8").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Dorszy"
    )).addTagString("itemName", "Dorsz").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M9(9, new ItemBuilder(Material.BOOK).setName("&7Misja #&c9").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Lososi"
    )).addTagString("itemName", "Losos").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M10(10, new ItemBuilder(Material.BOOK).setName("&7Misja #&c10").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Plotek"
    )).addTagString("itemName", "Plotka").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M11(11, new ItemBuilder(Material.BOOK).setName("&7Misja #&c11").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Sandaczy"
    )).addTagString("itemName", "Sandacz").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M12(12, new ItemBuilder(Material.BOOK).setName("&7Misja #&c12").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Postragow"
    )).addTagString("itemName", "Pstrag").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M13(13, new ItemBuilder(Material.BOOK).setName("&7Misja #&c13").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Okoni"
    )).addTagString("itemName", "Okon").toItemStack().clone(), 256, 30, 0.5, 0.5),
    M14(14, new ItemBuilder(Material.BOOK).setName("&7Misja #&c14").setLore(Arrays.asList(
            "&7Oddaj &c256 &6Amurow"
    )).addTagString("itemName", "Amur").toItemStack().clone(), 256, 30, 0.5, 0.5), //DMG - 210, PRZESZYWKA - 3.5%, DEF - 3.5%


    I15(15, new ItemBuilder(Material.BOOK).setName("&7Misja #&c15").setLore(Arrays.asList(
            "&7Wylow &c4 &bWodne Stwory"
    )).toItemStack().clone(), 4, 50, 1, 1),
    I16(16, new ItemBuilder(Material.BOOK).setName("&7Misja #&c16").setLore(Arrays.asList(
            "&7Wylow &c3 &9Nurkow Glebinowych"
    )).toItemStack().clone(), 3, 50, 1, 1),
    I17(17, new ItemBuilder(Material.BOOK).setName("&7Misja #&c17").setLore(Arrays.asList(
            "&7Wylow &c2 &b&lMorskich Ksieciow"
    )).toItemStack().clone(), 2, 50, 1, 1),
    I18(18, new ItemBuilder(Material.BOOK).setName("&7Misja #&c18").setLore(Arrays.asList(
            "&7Wylow &c1 &3&lPosejdona"
    )).toItemStack().clone(), 1, 50, 1, 1), //DMG - 200, PRZESZYWKA - 4%, DEF - 4%


    I19(19, new ItemBuilder(Material.BOOK).setName("&7Misja #&c19").setLore(Arrays.asList(
            "&7Wylow &c3 &5&lKrysztaly Czarnoksieznika"
    )).toItemStack().clone(), 3, 75, 2, 2),
    I20(20, new ItemBuilder(Material.BOOK).setName("&7Misja #&c20").setLore(Arrays.asList(
            "&7Wylow &c5 &3&lNiesamowitych Przedmiotow &8&l(&7&lStara Fabryka&8&l)"
    )).toItemStack().clone(), 5, 75, 2, 2),
    I21(21, new ItemBuilder(Material.BOOK).setName("&7Misja #&c21").setLore(Arrays.asList(
            "&7Wylow &c5 &b&lPodwodnych Skarbow"
    )).toItemStack().clone(), 5, 75, 2, 2), //DMG - 225, PRZESZYWKA - 6%, DEF - 6%


    I22(22, new ItemBuilder(Material.BOOK).setName("&7Misja #&c22").setLore(Arrays.asList(
            "&7Otworz &c10 &b&lPodwodne Skarby"
    )).toItemStack().clone(), 10, 50, 2, 3), //DMG - 50, PRZESZYWKA - 2%, DEF - 3%


    I23(23, new ItemBuilder(Material.BOOK).setName("&7Misja #&c23").setLore(Arrays.asList(
            "&7Zabij &c10 &bWodnych Stworow"
    )).toItemStack().clone(), 10, 90, 1.5, 2.5),
    I24(24, new ItemBuilder(Material.BOOK).setName("&7Misja #&c24").setLore(Arrays.asList(
            "&7Zabij &c8 &9Nurkow Glebinowych"
    )).toItemStack().clone(), 8, 100, 1.5, 2.5),
    I25(25, new ItemBuilder(Material.BOOK).setName("&7Misja #&c25").setLore(Arrays.asList(
            "&7Zabij &c6 &b&lMorskich Ksieciow"
    )).toItemStack().clone(), 6, 100, 1.5, 2.5),
    I26(26, new ItemBuilder(Material.BOOK).setName("&7Misja #&c26").setLore(Arrays.asList(
            "&7Zabij &c4 &3&lPosejdonow"
    )).toItemStack().clone(), 4, 250, 1.5, 2.5); //DMG - 540, PRZESZYWKA - 6%, DEF - 10%

    //TOTAL DMG - 1400
    //TOTAL PRZESZYWKA - 25%
    //TOTAL DEF - 30%

    private final int id;
    private final ItemStack missionItem;
    private final int reqAmount;
    private final int dodatkoweDmg;
    private final double przeszywka, srDef;

    MlodszyRybakMissions(final int id, final ItemStack missionItem, final int reqAmount, final int dodatkoweDmg, final double przeszywka, final double srDef) {
        this.id = id;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.dodatkoweDmg = dodatkoweDmg;
        this.przeszywka = przeszywka;
        this.srDef = srDef;
    }

    public ItemStack getMissionItem(final int progress) {
        return new ItemBuilder(this.missionItem.clone()).setLoreCrafting(this.missionItem.clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Postep: &c" + progress + "&7/&c" + this.reqAmount + " &8(&e" + DoubleUtils.round((double) progress / (double) this.reqAmount * 100.0, 2) + "%&8)"
        )).toItemStack().clone();
    }

    public double getPrzeszywka() {
        return DoubleUtils.round(przeszywka,2);
    }

    public double getSrDef() {
        return DoubleUtils.round(srDef,2);
    }

    public static MlodszyRybakMissions getMissionById(final int id) {
        for (final MlodszyRybakMissions mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }

}
