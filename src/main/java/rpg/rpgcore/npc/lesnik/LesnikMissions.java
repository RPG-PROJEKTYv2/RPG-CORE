package rpg.rpgcore.npc.lesnik;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Arrays;
import java.util.Objects;

public enum LesnikMissions {

    M1(1, 40, 90, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 2),
    M2(2, 60, 85, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 3),
    M3(3, 80, 80, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 3, 0.5, 3),
    M4(4, 100, 75, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 4, 0.5, 3),
    M5(5, 120, 70, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 5, 0.5, 2),
    M6(6, 140, 65, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 5, 0.5, 2),
    M7(7, 160, 60, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 4, 0.5, 3),
    M8(8, 180, 55, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 3, 0.5, 3),
    M9(9, 200, 50, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 3),
    M10(10, 250, 40, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 4, 0.5, 3),
    M11(10, 500, 40, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 3),
    M12(10, 1000, 40, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 5),
    M13(10, 1600, 40, Objects.requireNonNull(LesnikItems.getByName("I1")).getItem(), 2, 0.5, 5),
    M99(99, 9999, 0, new ItemBuilder(Material.BARRIER).setName("&c&lBrak misji").setLore(Arrays.asList("", "&7Wykonales/as juz wszystkie misje u Tego NPC!", "&8Kiedys moze jeszcze tu cos bedzie...")).toItemStack(), 0, 0, 0);


    private final int number;
    private final int reqAmount;
    private final double chance;
    private final ItemStack reqItem;
    private final double przeszywka, wzmKryta, defNaLudzi;

    LesnikMissions(final int number, final int reqAmount, final double chance, final ItemStack reqItem, final double przeszywka, final double wzmKryta, final double defNaLudzi) {
        this.number = number;
        this.reqAmount = reqAmount;
        this.chance = chance;
        this.reqItem = reqItem;
        this.przeszywka = przeszywka;
        this.wzmKryta = wzmKryta;
        this.defNaLudzi = defNaLudzi;
    }

    public static LesnikMissions getByNumber(int number) {
        for (LesnikMissions mission : LesnikMissions.values()) {
            if (mission.getNumber() == number) {
                return mission;
            }
        }
        return M99;
    }

    public int getNumber() {
        return number;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getChance() {
        return chance;
    }

    public ItemStack getReqItem() {
        return reqItem.clone();
    }

    public double getPrzeszywka() {
        return przeszywka;
    }

    public double getWzmKryta() {
        return wzmKryta;
    }

    public double getDefNaLudzi() {
        return defNaLudzi;
    }
}
