package rpg.rpgcore.npc.lowca;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.GlobalItems.npc.LowcaItems;

public enum LowcaMissions {

    M1(1, LowcaItems.getItem("1-10", 1), 2, 3, 0),
    M2(2, LowcaItems.getItem("10-20", 1), 2, 3, 0),
    M3(3, LowcaItems.getItem("20-30", 1), 2, 3, 0),
    M4(4, LowcaItems.getItem("30-40", 1), 2, 3, 0),
    M5(5, LowcaItems.getItem("40-50", 1), 2, 3, 1),
    M6(6, LowcaItems.getItem("50-60", 1), 2, 3, 0),
    M7(7, LowcaItems.getItem("60-70", 1), 2, 3, 0),
    M8(8, LowcaItems.getItem("70-80", 1), 2, 3, 0),
    M9(9, LowcaItems.getItem("80-90", 1), 2, 3, 0),
    M10(10, LowcaItems.getItem("90-100", 1), 2, 3, 1),
    M11(11, LowcaItems.getItem("100-110", 1), 2, 3, 0),
    M12(12, LowcaItems.getItem("110-120", 1), 2, 3, 0),
    M13(13, LowcaItems.getItem("120-130", 1), 2, 3, 1);

    private final int mission;
    private final ItemStack reqItem;
    private final int szczescie, szybkosc, truedmg;

    LowcaMissions(int mission, ItemStack reqItem, int szczescie, int szybkosc, int truedmg) {
        this.mission = mission;
        this.reqItem = reqItem;
        this.szczescie = szczescie;
        this.szybkosc = szybkosc;
        this.truedmg = truedmg;
    }

    public ItemStack getReqItem() {
        return this.reqItem;
    }

    public int getSzczescie() {
        return this.szczescie;
    }

    public int getSzybkosc() {
        return this.szybkosc;
    }

    public int getTruedmg() {
        return this.truedmg;
    }

    public int getReqAmount() {
        return 5;
    }

    public double getAcceptPercentage() {
        return 50.0;
    }

    public static LowcaMissions getMission(int mission) {
        for (LowcaMissions lowcaMissions : LowcaMissions.values()) {
            if (lowcaMissions.mission == mission) {
                return lowcaMissions;
            }
        }
        return null;
    }
}
