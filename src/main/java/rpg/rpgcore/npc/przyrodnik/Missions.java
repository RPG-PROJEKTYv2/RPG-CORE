package rpg.rpgcore.npc.przyrodnik;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.npc.PrzyrodnikItems;

public enum Missions {

    M0(0,10, 95, 1.5,1.5,"Mob1",2.5, PrzyrodnikItems.getItem("1-10")),
    M1(1,15, 90, 1.5,1.5,"Mob2",2.5, PrzyrodnikItems.getItem("10-20")),
    M2(2,20, 85, 1.5,1.5,"Mob3",2.5, PrzyrodnikItems.getItem("20-30")),
    M3(3,25, 80,1.5,1.5,"Mob4",2.5, PrzyrodnikItems.getItem("30-40")),
    M4(4,30, 75, 1.5,1.5,"Mob5",2.5, PrzyrodnikItems.getItem("40-50")),
    M5(5,40, 65, 1.5,1.5,"Mob6",2.5, PrzyrodnikItems.getItem("50-60")),
    M6(6,50, 60,1.5,1.5,"Mob7",2.5, PrzyrodnikItems.getItem("60-70")),
    M7(7,60, 60, 1.5,1.5,"Mob8",2.5, PrzyrodnikItems.getItem("70-80")),
    M8(8,70, 60,1.5,1.5,"Mob9",2.5, PrzyrodnikItems.getItem("80-90")),
    M9(9,80, 55,1.5,1.5,"Mob10",2.5, PrzyrodnikItems.getItem("90-100")),
    M10(10,90, 45,2.5,2.5,"Mob11",2.5, PrzyrodnikItems.getItem("100-110")),
    M11(11,120, 30,5,5,"Mob12",2.5, PrzyrodnikItems.getItem("110-120")),
    M_ERROR(99, 0,0,0,0,"", 0, GlobalItem.getItem("error", 1));

    private int number, reqAmount;
    private double acceptPercent, dmg,def, dropChance;
    private String mobName;
    private ItemStack reqitem;

    Missions(int number, int reqAmount, double acceptPercent, double dmg, double def, String mobName, double dropChance, ItemStack reqitem){
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
