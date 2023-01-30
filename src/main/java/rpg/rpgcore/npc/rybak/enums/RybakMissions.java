package rpg.rpgcore.npc.rybak.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum RybakMissions {
    M1(1, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Sledzi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 128, 2, 0, 0, 1),
    M2(2, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Dorszy",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 128, 0, 2, 2, 0),
    M3(3, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Krasnopiorek",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 128, 2, 0, 0, 1),
    M4(4, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Fladr",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 128, 0, 2, 2, 0),
    M5(5, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Karasi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 128, 3, 0, 0, 1),
    M6(6, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Karpi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 128, 0, 3, 2, 0),
    M7(7, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Osiagnij &57 &7poziom wedki",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%",
            "&aMorskie Szczescie: &f2",
            "&aSzansa Na Blok Ciosu: &f1%",
            "",
            "&8(Musisz trzymac wedke w rece&8)")).toItemStack(), 1, 3, 0, 2, 1),
    M8(8, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Leszczy",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 128, 0, 3, 0, 0),
    M9(9, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Makreli",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 128, 3, 0, 2, 1),
    M10(10, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Mintaji",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 128, 0, 3, 0, 0),
    M11(11, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &6128 &7Okoni",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 128, 3, 0, 2, 1),
    M12(12, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &65 &a&lKufrow Rybackich",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 5, 0, 3, 0, 0),
    M13(13, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &61500 &7ryb",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%",
            "&aMorskie Szczescie: &f2",
            "&aSzansa Na Blok Ciosu: &f1%")).toItemStack(), 1500, 3, 0, 2, 1),
    M14(14, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Sledzi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 640, 0, 3, 2, 0),
    M15(15, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Dorszy",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 640, 3, 0, 0, 2),
    M16(16, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Krasnopiorek",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 640, 0, 3, 2, 0),
    M17(17, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Fladr",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 640, 3, 0, 0, 1),
    M18(18, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Karasi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 640, 0, 4, 2, 0),
    M19(19, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &6640 &7Karpi",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%")).toItemStack(), 640, 3, 0, 0, 1),
    M20(20, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &63 &a&lNiesamowite Przedmioty",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1%",
            "&aSzansa Na Cios Krytyczny: &f1%",
            "&aMorskie Szczescie: &f2",
            "&aSzansa Na Blok Ciosu: &f1%")).toItemStack(), 3, 0, 4, 2, 0),
    M21(21, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Osiagnij &615 &7poziom wedki",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aMorskie Szczescie: &f2",
            "",
            "&8(Musisz trzymac wedke w rece)")).toItemStack(), 1, 3, 0, 2, 1),
    M22(22, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &61200 &7Mintaji",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aSzansa Na Cios Krytyczny: &f2%")).toItemStack(), 1200, 0, 4, 0, 0),
    M23(23, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Sprzedaj &61200 &7Okoni",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 1200, 3, 0, 2, 1),
    M24(24, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Wylow &616 &a&lKufrow Rybackich",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aSzansa Na Cios Krytyczny: &f2%")).toItemStack(), 16, 0, 4, 0, 0),
    M25(25, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Otworz &664 &a&lKufry Rybackie",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aMorskie Szczescie: &f2")).toItemStack(), 64, 3, 0, 2, 1),
    M26(26, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Otworz &63 &6&lZlote Kufry Rybackie",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f1.5%",
            "&aSzansa Na Cios Krytyczny: &f2%")).toItemStack(), 3, 0, 4, 0, 0),
    M27(27, new ItemBuilder(Material.PAPER).setLore(Arrays.asList(
            "&7Ukoncz &aKraine Posejdona",
            " ",
            "&f&lNAGRODA",
            "&aSrednia Odpornosc: &f3%",
            "&aSzansa Na Cios Krytyczny: &f2%",
            "&aMorskie Szczescie: &f2",
            "&aSzansa Na Blok Ciosu: &f2%")).toItemStack(), 1, 3, 0, 2, 1);
    /*
    MAX
    30% - DEF
    30 - SZCZESCIE
    20% - KRYT
    X% - Szansa Na Blok Ciosu
     */


    private final int mission, reqAmount;
    private final double srDef, kryt, morskieSzczescie, blok;
    private final ItemStack missionItem;

    RybakMissions(final int mission, final ItemStack missionItem, final int reqAmount, final double srDef, final double kryt, final double morskieSzczescie, final double blok) {
        this.mission = mission;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.srDef = srDef;
        this.kryt = kryt;
        this.morskieSzczescie = morskieSzczescie;
        this.blok = blok;
    }

    public int getMission() {
        return mission;
    }

    public ItemStack getMissionItem() {
        return missionItem;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getSrDef() {
        return DoubleUtils.round(srDef, 2);
    }

    public double getKryt() {
        return DoubleUtils.round(kryt, 2);
    }

    public double getMorskieSzczescie() {
        return DoubleUtils.round(morskieSzczescie, 2);
    }

    public double getBlok() {
        return DoubleUtils.round(blok, 2);
    }

    public static RybakMissions getMission(int mission) {
        for (RybakMissions missions : values()) {
            if (missions.getMission() == mission) {
                return missions;
            }
        }
        return null;
    }
}
