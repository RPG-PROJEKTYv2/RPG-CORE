package rpg.rpgcore.npc.gornik.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;


import java.util.Arrays;
import java.util.List;

public enum GornikMissions {
    M1(1, Arrays.asList("&7Test lore"), null, 1, 0,0),
    M2(2, Arrays.asList("&7Test lore"), null, 1, 0,0),
    M3(3, Arrays.asList("&7Test lore"), null, 1, 0,0),
    M4(4, Arrays.asList("&7Test lore"), null, 1, 0,0),
    M5(5, Arrays.asList("&7Test lore"), null, 1, 0,0),
    M6(6, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M7(7, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M8(8, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M9(9, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M10(10, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M11(11, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M12(12, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M13(13, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M14(14, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M15(15, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M16(16, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M17(17, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M18(18, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M19(19, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M20(20, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M21(21, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M22(22, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M23(23, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M24(24, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M25(25, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M26(26, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 1, 0,0),
    M27(27, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 2, 0,0),
    M28(28, Arrays.asList("&7Test lore"), GlobalItem.getItem("I2", 1), 2, 5,5),
    M99(99, Arrays.asList("&7Test lore"), null, 0, 0,0);

    private final int number;
    private final List<String> lore;
    private final ItemStack reward;
    private final double def;
    private final double przeszycie, blok;

    GornikMissions(int number, List<String> lore, ItemStack reward, double def, double przeszycie, double blok) {
        this.number = number;
        this.lore = lore;
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
