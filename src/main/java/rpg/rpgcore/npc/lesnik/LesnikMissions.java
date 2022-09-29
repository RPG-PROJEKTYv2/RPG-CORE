package rpg.rpgcore.npc.lesnik;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum LesnikMissions {

    M1(1,10, LesnikItems.getByName("I1").getItem(), 1, 1, 1),
    M99(99, 1, new ItemBuilder(Material.BARRIER).setName("&c&lBrak misji").setLore(Arrays.asList("", "&7Wykonales/as juz wszystkie misje u Tego NPC!", "&8Kiedys moze jeszcze tu cos bedzie...")).toItemStack(), 0, 0, 0);


    private int number;
    private int reqAmount;
    private ItemStack reqItem;
    private double przeszywka, wzmKryta, defNaLudzi;

    LesnikMissions(int number, int reqAmount, ItemStack reqItem, double przeszywka, double wzmKryta, double defNaLudzi) {
        this.number = number;
        this.reqAmount = reqAmount;
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
