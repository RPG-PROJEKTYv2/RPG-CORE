package rpg.rpgcore.npc.przyrodnik;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;

public enum Missions {

    M0(0,10, 95, 2,1.5,"&8Najemnik",3, PrzyrodnikItems.getItem("1-10")),
    M1(1,15, 90, 3,1.5,"&2Goblin",2.5, PrzyrodnikItems.getItem("10-20")),
    M2(2,20, 85, 4,1.5,"&7Goryl",2.3, PrzyrodnikItems.getItem("20-30")),
    M3(3,25, 80,5,1.5,"&8Zjawa",2.5, PrzyrodnikItems.getItem("30-40")),
    M4(4,30, 75, 6,1.5,"&3Straznik Swiatyni",2.5, PrzyrodnikItems.getItem("40-50")),
    M5(5,40, 65, 7,1.5,"&bMrozny Wilk",2.4, PrzyrodnikItems.getItem("50-60")),
    M6(6,50, 60,8,1.5,"&6Zywiolak Ognia",1.9, PrzyrodnikItems.getItem("60-70")),
    M7(7,60, 60, 10,1.5,"&fMroczna Dusza",1, PrzyrodnikItems.getItem("70-80")),
    M8(8,70, 60,15,1.5,"Mob9",0.66, PrzyrodnikItems.getItem("80-90")),
    M9(9,80, 55,15,1.5,"Mob10",0.24, PrzyrodnikItems.getItem("90-100")),
    M10(10,90, 45,15,2.5,"Mob11",0.16, PrzyrodnikItems.getItem("100-110")),
    M11(11,120, 30,30,5,"Mob12",0.12, PrzyrodnikItems.getItem("110-120")),
    M12(12,130, 30,30,5,"Mob12",0.06, PrzyrodnikItems.getItem("110-120")),
    M_ERROR(99, 0,0,0,0,"", 0, GlobalItem.getItem("error", 1));

    private final int number, reqAmount;
    private final double acceptPercent, dmg,def, dropChance;
    private final String mobName;
    private final ItemStack reqitem;

    Missions(final int number, final int reqAmount, final double acceptPercent, final double dmg, final double def, final String mobName, final double dropChance, final ItemStack reqitem){
        this.number = number;
        this.reqAmount = reqAmount;
        this.acceptPercent = acceptPercent;
        this.dmg = dmg;
        this.def = def;
        this.mobName = mobName;
        this.dropChance = dropChance;
        this.reqitem = reqitem;
    }

    public static Missions getByNumber(int number) {
        for (Missions rank : values()) {
            if (rank.getNumber() == number) {
                return rank;
            }
        }
        return M_ERROR;
    }

    public static ItemStack getReqItem(int number) {
        ItemStack itemStack = Missions.getByNumber(number).getItemStack();
        return itemStack;
    }

    public int getNumber() {
        return number;
    }

    public double getDmg() {
        return dmg;
    }

    public double getDef() {
        return def;
    }

    public int getReqAmount() {
        return reqAmount;
    }

    public double getAcceptPercent() {
        return acceptPercent;
    }

    public String getMobName() {
        return mobName;
    }

    public double getDropChance() {
        return dropChance;
    }

    public ItemStack getItemStack() {
        return reqitem;
    }



}
