package rpg.rpgcore.npc.gornik.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;


import java.util.Arrays;
import java.util.List;

public enum GornikMissions {
    M1(1, Arrays.asList("&7Wykop &6640 &0&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640, null, 1, 0,0),
    M2(2, Arrays.asList("&7Wykop &6640 &7&lRudy Cyrkonu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640,  null, 1, 0,0),
    M3(3, Arrays.asList("&7Otworz &640 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 40,  null, 1, 0,0),
    M4(4, Arrays.asList("&7Sprzedaj &2560 &7rud", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 2560,  null, 1, 0,0),
    M5(5, Arrays.asList("&7Wykop &6640 &e&lRudy Blasku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640,  null, 1, 0,0),
    M6(6, Arrays.asList("&7Wykop &61250 &0&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M7(7, Arrays.asList("&7Wykop &6640 &3&lRudy Szafiru", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 640,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M8(8, Arrays.asList("&7Znajdz &632 &8&lSkrzynie Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 32,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M9(9, Arrays.asList("&7Sprzedaj &61250 &7&lRudy Cyrkonu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M10(10, Arrays.asList("&7Otworz &6160 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 160,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M11(11, Arrays.asList("&7Wykop &6720 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 720,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M12(12, Arrays.asList("&7Sprzedaj &61250 &e&lRudy Blasku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M13(13, Arrays.asList("&7Wykop &62500 &0&lRudy Mroku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 2500,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M14(14, Arrays.asList("&7Sprzedaj &61250 &e&lRudy Blasku", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M15(15, Arrays.asList("&7Znajdz &648 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 48,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M16(16, Arrays.asList("&7Sprzedaj &61250 &3&lRudy Szafiru", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M17(17, Arrays.asList("&7Sprzedaj &610000 &7rud", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 10000,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M18(18, Arrays.asList("&7Otworz &6128 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 128,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M19(19, Arrays.asList("&7Wykop &61250 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M20(20, Arrays.asList("&7Wykop &61500 &2&lRudy Jadeitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1500,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M21(21, Arrays.asList("&7Wykop &6720 &b&lRudy Tanzanitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 720,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M22(22, Arrays.asList("&7Znajdz &664 &8&lSkrzynie Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 64,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M23(23, Arrays.asList("&7Sprzedaj &61250 &b&lRudy Tanzanitu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 1250,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M24(24, Arrays.asList("&7Wykop &6720 &c&lRuda Rubinu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 720,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M25(25, Arrays.asList("&7Otworz &6256 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 256,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M26(26, Arrays.asList("&7Sprzedaj &62500 &c&lRudy Rubinu", "&f&lNagroda", "&7Srednia Odpornosc: &c1%"), 2500,  GlobalItem.getItem("I2", 1), 1, 0,0),
    M27(27, Arrays.asList("&7Sprzedaj &612500 &7rud", "&f&lNagroda", "&7Srednia Odpornosc: &c2%"), 12500,  GlobalItem.getItem("I2", 1), 2, 0,0),
    M28(28, Arrays.asList("&7Wykop &6128 &8&lSkrzyn Gornika", "&f&lNagroda", "&7Srednia Odpornosc: &c2%", "&7Przeszycie Bloku Ciosu: &c5%", "&7Blok Ciosu: &c5%"), 128,  GlobalItem.getItem("I2", 1), 2, 5,5),
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
        return def;
    }

    public double getPrzeszycie() {
        return przeszycie;
    }

    public double getBlok() {
        return blok;
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
