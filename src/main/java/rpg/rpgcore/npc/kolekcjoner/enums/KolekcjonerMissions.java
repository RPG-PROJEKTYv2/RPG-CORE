package rpg.rpgcore.npc.kolekcjoner.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.niesy.*;
import rpg.rpgcore.utils.globalitems.npc.KolekcjonerItems;

public enum KolekcjonerMissions {

    M1(1, new ItemStack[]{Map1_10.I1_10_1.getItemStack(), Map1_10.I1_10_2.getItemStack(), Map1_10.I1_10_5.getItemStack(), Map1_10.I1_10_3.getItemStack(), Map1_10.I1_10_4.getItemStack()}, 2, 1, 2),
    M2(2, new ItemStack[]{Map10_20.I10_20_1.getItemStack(), Map10_20.I10_20_2.getItemStack(), Map10_20.I10_20_3.getItemStack(), Map10_20.I10_20_4.getItemStack(), Map10_20.I10_20_5.getItemStack()}, 2, 1, 4),
    M3(3, new ItemStack[]{Map20_30.I20_30_1.getItemStack(), Map20_30.I20_30_2.getItemStack(), Map20_30.I20_30_3.getItemStack(), Map20_30.I20_30_4.getItemStack(), Map20_30.I20_30_5.getItemStack()}, 2, 1, 5),
    M4(4, new ItemStack[]{Map30_40.I30_40_1.getItemStack(), Map30_40.I30_40_2.getItemStack(), Map30_40.I30_40_3.getItemStack(), Map30_40.I30_40_4.getItemStack(), Map30_40.I30_40_5.getItemStack()}, 2, 1, 7),
    M5(5, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 12),
    M6(6, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 30),
    M7(7, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 35),
    M8(8, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 55),
    M9(9, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 90),
    M10(10, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 230),
    M11(11, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 420),
    M12(12, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 1200),
    M13(13, new ItemStack[]{Map40_50.I40_50_1.getItemStack(), Map40_50.I40_50_2.getItemStack(), Map40_50.I40_50_3.getItemStack(), Map40_50.I40_50_4.getItemStack(), Map40_50.I40_50_5.getItemStack()}, 2, 1, 2000),
    M99(99, new ItemStack[]{KolekcjonerItems.I99.getItemStack(), KolekcjonerItems.I99.getItemStack(), KolekcjonerItems.I99.getItemStack(), KolekcjonerItems.I99.getItemStack(), KolekcjonerItems.I99.getItemStack()}, 0, 0, 0);

    private final int number;
    private final ItemStack[] reqItems;
    private final int szczescie, dodatkowe;
    private final double silnyNaLudzi;

    KolekcjonerMissions(final int number, final ItemStack[] reqItems, final int szczescie, final double silnyNaLudzi, final int dodatkowe) {
        this.number = number;
        this.reqItems = reqItems;
        this.szczescie = szczescie;
        this.silnyNaLudzi = silnyNaLudzi;
        this.dodatkowe = dodatkowe;
    }

    public int getNumber() {
        return this.number;
    }

    public ItemStack[] getReqItems() {
        return this.reqItems;
    }

    public int getSzczescie() {
        return this.szczescie;
    }

    public double getSilnyNaLudzi() {
        return this.silnyNaLudzi;
    }

    public int getDodatkowe() {
        return this.dodatkowe;
    }

    public static KolekcjonerMissions getByNumber(final int number) {
        for (final KolekcjonerMissions mission : KolekcjonerMissions.values()) {
            if (mission.getNumber() == number) {
                return mission;
            }
        }
        return M99;
    }
}
