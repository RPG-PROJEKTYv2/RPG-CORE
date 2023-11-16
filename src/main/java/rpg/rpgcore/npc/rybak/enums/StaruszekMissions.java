package rpg.rpgcore.npc.rybak.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum StaruszekMissions {

    M1(1, new ItemBuilder(Material.BOOK).setName("&7Misja #&c1").setLore(Arrays.asList(
            "&7Wylow &c24 &6Karpie"
    )).toItemStack().clone(), 24, 0.5),
    M2(2, new ItemBuilder(Material.BOOK).setName("&7Misja #&c2").setLore(Arrays.asList(
            "&7Wylow &c24 &6Leszcze"
    )).toItemStack().clone(), 24, 0.5),
    M3(3, new ItemBuilder(Material.BOOK).setName("&7Misja #&c3").setLore(Arrays.asList(
            "&7Wylow &c24 &6Karasie"
    )).toItemStack().clone(), 24, 0.5),
    M4(4, new ItemBuilder(Material.BOOK).setName("&7Misja #&c4").setLore(Arrays.asList(
            "&7Wylow &c24 &6Szczupaki"
    )).toItemStack().clone(), 24, 0.5),
    M5(5, new ItemBuilder(Material.BOOK).setName("&7Misja #&c5").setLore(Arrays.asList(
            "&7Wylow &c24 &6Wegorze"
    )).toItemStack().clone(), 24, 0.5),

    M6(6, new ItemBuilder(Material.BOOK).setName("&7Misja #&c6").setLore(Arrays.asList(
            "&7Przepal &c48 &6Karpie"
    )).toItemStack().clone(), 48, 0.5),
    M7(7, new ItemBuilder(Material.BOOK).setName("&7Misja #&c7").setLore(Arrays.asList(
            "&7Przepal &c48 &6Leszcze"
    )).toItemStack().clone(), 48, 0.5),
    M8(8, new ItemBuilder(Material.BOOK).setName("&7Misja #&c8").setLore(Arrays.asList(
            "&7Przepal &c48 &6Karasie"
    )).toItemStack().clone(), 48, 0.5),
    M9(9, new ItemBuilder(Material.BOOK).setName("&7Misja #&c9").setLore(Arrays.asList(
            "&7Przepal &c48 &6Szczupaki"
    )).toItemStack().clone(), 48, 0.5),
    M10(10, new ItemBuilder(Material.BOOK).setName("&7Misja #&c10").setLore(Arrays.asList(
            "&7Przepal &c48 &6Wegorze"
    )).toItemStack().clone(), 48, 0.5),

    M11(11, new ItemBuilder(Material.BOOK).setName("&7Misja #&c11").setLore(Arrays.asList(
            "&7Wylow &c96 &6Zniszczonych Butow"
    )).toItemStack().clone(), 96, 0.5),
    M12(12, new ItemBuilder(Material.BOOK).setName("&7Misja #&c12").setLore(Arrays.asList(
            "&7Wylow &c96 &bGliny"
    )).toItemStack().clone(), 96, 0.5),
    M13(13, new ItemBuilder(Material.BOOK).setName("&7Misja #&c13").setLore(Arrays.asList(
            "&7Wylow &c96 &fZylek"
    )).toItemStack().clone(), 96, 0.5),
    M14(14, new ItemBuilder(Material.BOOK).setName("&7Misja #&c14").setLore(Arrays.asList(
            "&7Wylow &c96 &8Wilgotnego Wegla"
    )).toItemStack().clone(), 96, 0.5),

    M15(15, new ItemBuilder(Material.BOOK).setName("&7Misja #&c15").setLore(Arrays.asList(
            "&7Wylow &c192 &6Karpie"
    )).toItemStack().clone(), 192, 1),
    M16(16, new ItemBuilder(Material.BOOK).setName("&7Misja #&c16").setLore(Arrays.asList(
            "&7Wylow &c192 &6Leszcze"
    )).toItemStack().clone(), 192, 1),
    M17(17, new ItemBuilder(Material.BOOK).setName("&7Misja #&c17").setLore(Arrays.asList(
            "&7Wylow &c192 &6Karasie"
    )).toItemStack().clone(), 192, 1),
    M18(18, new ItemBuilder(Material.BOOK).setName("&7Misja #&c18").setLore(Arrays.asList(
            "&7Wylow &c192 &6Szczupaki"
    )).toItemStack().clone(), 192, 1),
    M19(19, new ItemBuilder(Material.BOOK).setName("&7Misja #&c19").setLore(Arrays.asList(
            "&7Wylow &c192 &6Wegorze"
    )).toItemStack().clone(), 192, 1),

    M20(20, new ItemBuilder(Material.BOOK).setName("&7Misja #&c20").setLore(Arrays.asList(
            "&7Oddaj &c320 &6Karpie"
    )).addTagString("itemName", "Karp").toItemStack().clone(), 320, 1.5),
    M21(21, new ItemBuilder(Material.BOOK).setName("&7Misja #&c21").setLore(Arrays.asList(
            "&7Oddaj &c320 &6Leszcze"
    )).addTagString("itemName", "Leszcz").toItemStack().clone(), 320, 1.5),
    M22(22, new ItemBuilder(Material.BOOK).setName("&7Misja #&c22").setLore(Arrays.asList(
            "&7Oddaj &c320 &6Karasie"
    )).addTagString("itemName", "Karas").toItemStack().clone(), 320, 1.5),
    M23(23, new ItemBuilder(Material.BOOK).setName("&7Misja #&c23").setLore(Arrays.asList(
            "&7Oddaj &c320 &6Szczupaki"
    )).addTagString("itemName", "Szczupak").toItemStack().clone(), 320, 1.5),
    M24(24, new ItemBuilder(Material.BOOK).setName("&7Misja #&c24").setLore(Arrays.asList(
            "&7Oddaj &c320 &6Wegorze"
    )).addTagString("itemName", "Wegorz").toItemStack().clone(), 320, 1.5),

    M25(25, new ItemBuilder(Material.BOOK).setName("&7Misja #&c25").setLore(Arrays.asList(
            "&7Podwoj swoj polow &c16 &7razy"
    )).toItemStack().clone(), 16, 1.5),

    M26(26, new ItemBuilder(Material.BOOK).setName("&7Misja #&c26").setLore(Arrays.asList(
            "&7Wylow &c512 &7ryb"
    )).toItemStack().clone(), 512, 4);
    

    @Getter
    private final int id;
    private final ItemStack missionItem;
    private final int reqAmount;
    private final double srDef;


    StaruszekMissions(final int id, final ItemStack missionItem, final int reqAmount, final double srDef) {
        this.id = id;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.srDef = srDef;
    }

    public ItemStack getMissionItem(final int progress) {
        return new ItemBuilder(this.missionItem.clone()).setLoreCrafting(this.missionItem.clone().getItemMeta().getLore(), Arrays.asList(
                " ",
                "&7Postep: &c" + progress + "&7/&c" + this.reqAmount + " &8(&e" + DoubleUtils.round((double) progress / (double) this.reqAmount * 100.0, 2) + "%&8)"
        )).toItemStack().clone();
    }

    public int getReqAmount() {
        return this.reqAmount;
    }

    public double getSrDef() {
        return DoubleUtils.round(this.srDef, 2);
    }

    public static StaruszekMissions getMissionById(final int id) {
        for (final StaruszekMissions mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }


}
