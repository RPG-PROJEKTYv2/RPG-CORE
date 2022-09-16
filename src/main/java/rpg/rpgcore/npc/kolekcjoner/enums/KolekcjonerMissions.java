package rpg.rpgcore.npc.kolekcjoner.enums;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.GlobalItems.KolekcjonerItems;

public enum KolekcjonerMissions {

    M1(1, new ItemStack[]{KolekcjonerItems.I1_10_1.getItem(), KolekcjonerItems.I1_10_2.getItem(), KolekcjonerItems.I1_10_3.getItem(), KolekcjonerItems.I1_10_4.getItem()}, 2, 1, 50),
    M99(99, new ItemStack[]{KolekcjonerItems.I99.getItem(), KolekcjonerItems.I99.getItem(), KolekcjonerItems.I99.getItem(), KolekcjonerItems.I99.getItem()}, 0, 0, 0);

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
