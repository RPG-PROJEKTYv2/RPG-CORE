package rpg.rpgcore.old.gornik.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.globalitems.GlobalItem;


import java.util.Arrays;
import java.util.List;

public enum GornikMissions {
    M1(1, Arrays.asList("&7Wykop &664 &8&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64, null, 1, 0,0), //
    M2(2, Arrays.asList("&7Wykop &664 &7&lRudy Cyrkonu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64,  null, 1, 0,0), //
    M3(3, Arrays.asList("&7Otworz &64 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 4,  null, 1, 0,0),
    M4(4, Arrays.asList("&7Przerob &256 &7rud", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 256,  null, 1, 0,0), //
    M5(5, Arrays.asList("&7Wykop &664 &e&lRudy Blasku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64,  null, 1, 0,0), //
    M6(6, Arrays.asList("&7Wykop &6125 &8&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 125,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M7(7, Arrays.asList("&7Wykop &664 &3&lRudy Szafiru", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M8(8, Arrays.asList("&7Znajdz &63 &8&lSkrzynie Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 3,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M9(9, Arrays.asList("&7Przerob &6128 &7&lRudy Cyrkonu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M10(10, Arrays.asList("&7Otworz &68 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 8,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M11(11, Arrays.asList("&7Wykop &672 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 72,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M12(12, Arrays.asList("&7Przerob &6128 &e&lRudy Blasku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M13(13, Arrays.asList("&7Wykop &6128 &7&lRudy Cyrkonu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M14(14, Arrays.asList("&7Przerob &6128 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M15(15, Arrays.asList("&7Znajdz &616 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 16,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M16(16, Arrays.asList("&7Przerob &6128 &3&lRudy Szafiru", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M17(17, Arrays.asList("&7Sprzedaj &61000 &7rud", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1000,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M18(18, Arrays.asList("&7Otworz &632 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 32,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M19(19, Arrays.asList("&7Wykop &6128 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M20(20, Arrays.asList("&7Przerob &6196 &8&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 196,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M21(21, Arrays.asList("&7Wykop &6320 &b&lRudy Tanzanitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 320,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M22(22, Arrays.asList("&7Znajdz &632 &8&lSkrzynie Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 32,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M23(23, Arrays.asList("&7Przerob &6640 &b&lRudy Tanzanitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M24(24, Arrays.asList("&7Wykop &6320 &c&lRuda Rubinu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 320,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M25(25, Arrays.asList("&7Otworz &664 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M26(26, Arrays.asList("&7Przerob &6640 &c&lRudy Rubinu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640,  GlobalItem.getItem("I2", 1), 1, 0,0), //
    M27(27, Arrays.asList("&7Znajdz &696 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c2%"), 96,  GlobalItem.getItem("I1", 1), 2, 0,0), //
    M28(28, Arrays.asList("&7Sprzedaj &61250 &7krysztalow", "&f&lNagroda", "&7Srednia Odpornosc: &c2%", "&7Przeszycie Bloku Ciosu: &c5%", "&7Blok Ciosu: &c5%"), 1250,  GlobalItem.getItem("I1", 1), 2, 5,5),
    M99(99, Arrays.asList("&7Ukonczyles juz cala kampanie", "&7lub", "&7cos poszlo nie tak :<", "&8Jesli uwazasz, ze to blad", "&8skontaktuj sie z &cAdministracja&8!"), 1, null, 0, 0,0);

    private final int number;
    private final List<String> lore;
    private final int reqAmount;
    private final ItemStack reward;
    private final double def;
    private final double przeszycie, blok;

    GornikMissions(int number, List<String> lore, int reqAmount, ItemStack reward, double def, double przeszycie, double blok) {
        this.number = number;
        this.lore = lore;
        this.reqAmount = reqAmount;
        this.reward = reward;
        this.def = def;
        this.przeszycie = przeszycie;
        this.blok = blok;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemStack getReward() {
        return reward;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getDef() {
        return DoubleUtils.round(def, 2);
    }

    public double getPrzeszycie() {
        return DoubleUtils.round(przeszycie, 2);
    }

    public double getBlok() {
        return DoubleUtils.round(blok, 2);
    }

    public static GornikMissions getMission(int number) {
        for (GornikMissions mission : GornikMissions.values()) {
            if (mission.getNumber() == number) {
                return mission;
            }
        }
        return M99;
    }

}
