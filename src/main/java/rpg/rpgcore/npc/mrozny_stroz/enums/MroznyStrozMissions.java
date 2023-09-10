package rpg.rpgcore.npc.mrozny_stroz.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

import java.util.Arrays;

@Getter
public enum MroznyStrozMissions {

    M1(1, new ItemBuilder(Material.BOOK).setName("&7Misja &c1").setLore(Arrays.asList(
            "&7Ukoncz &c2 &b&lIce Tower",
            "",
            "&f&lNAGRODA",
            "&82x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 2, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(2).toItemStack().clone()),
    M2(2, new ItemBuilder(Material.BOOK).setName("&7Misja &c2").setLore(Arrays.asList(
            "&7Zabij &c5 &8&l[&4&lBOSS&8&l] &bKrol Lodu",
            "",
            "&f&lNAGRODA",
            "&82x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 5, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(2).toItemStack().clone()),
    M3(3, new ItemBuilder(Material.BOOK).setName("&7Misja &c3").setLore(Arrays.asList(
            "&7Ukoncz &c8 &b&lIce Tower",
            "",
            "&f&lNAGRODA",
            "&84x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 8, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(4).toItemStack().clone()),
    M4(4, new ItemBuilder(Material.BOOK).setName("&7Misja &c4").setLore(Arrays.asList(
            "&7Zabij &c10 &8&l[&4&lBOSS&8&l] &bKrol Lodu",
            "",
            "&f&lNAGRODA",
            "&85x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 10, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(5).toItemStack().clone()),
    M5(5, new ItemBuilder(Material.BOOK).setName("&7Misja &c5").setLore(Arrays.asList(
            "&7Zabij &c3000 &bLodowych Slugow",
            "",
            "&f&lNAGRODA",
            "&88x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 3000, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(8).toItemStack().clone()),
    M6(6, new ItemBuilder(Material.BOOK).setName("&7Misja &c6").setLore(Arrays.asList(
            "&7Zniszcz &c60 &b&lMetinow Zniszczenia",
            "",
            "&f&lNAGRODA",
            "&86x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 60, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(6).toItemStack().clone()),
    M7(7, new ItemBuilder(Material.BOOK).setName("&7Misja &c7").setLore(Arrays.asList(
            "&7Zniszcz &c100 &b&lMetinow Zniszczenia",
            "",
            "&f&lNAGRODA",
            "&88x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 100, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(8).toItemStack().clone()),
    M8(8, new ItemBuilder(Material.BOOK).setName("&7Misja &c8").setLore(Arrays.asList(
            "&7Ukoncz &c16 &b&lIce Tower",
            "",
            "&f&lNAGRODA",
            "&88x " + SkrzynkiOther.I2.getItemStack().getItemMeta().getDisplayName()
    )).toItemStack().clone(), 16, new ItemBuilder(SkrzynkiOther.I2.getItemStack().clone()).setAmount(8).toItemStack().clone());

    private final int missionId;
    private final ItemStack missionItem;
    private final int reqAmount;
    private final ItemStack reward;


    MroznyStrozMissions(int missionId, ItemStack missionItem, int reqAmount, ItemStack reward) {
        this.missionId = missionId;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
        this.reward = reward;
    }

    public ItemStack getMissionItem(final int progress) {
        return new ItemBuilder(missionItem.clone()).setLoreCrafting(missionItem.clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Postep: &e" + progress + "&7/&e" + reqAmount + " &8(&e" + DoubleUtils.round(((double) progress / reqAmount) * 100.0, 2) + "%&8)"
                )).toItemStack().clone();
    }

    public ItemStack getReward() {
        return reward.clone();
    }

    public static MroznyStrozMissions getMissionById(int id) {
        for (MroznyStrozMissions mission : values()) {
            if (mission.getMissionId() == id) {
                return mission;
            }
        }
        return null;
    }
}
