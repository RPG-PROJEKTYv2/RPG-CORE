package rpg.rpgcore.npc.pustelnik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public enum PustelnikMissions {
    M0(0, new ItemBuilder(Material.BARRIER).setName("&c&lBrak Misji").setLore(Arrays.asList(
            "&7Prawdopodobnie misja nie zostala jeszcze wylosowana"
    )).addGlowing().toItemStack().clone(), 0, 0),
    M1(1, new ItemBuilder(Material.BOOK).setName("&e&lPogromca Ptasznikow").setLore(Arrays.asList(
            "&7Zabij &e6 000 &7potworow z mapy",
            " "
    )).toItemStack().clone(), 6_000, 30.0),
    M2(2, new ItemBuilder(Material.BOOK).setName("&e&lPogromca Podziemia").setLore(Arrays.asList(
            "&7Ukoncz &e1 &7dungeony z mapy",
            " "
    )).toItemStack().clone(), 1, 20.0),
    M3(3, new ItemBuilder(Material.BOOK).setName("&e&lCzempion Areny").setLore(Arrays.asList(
            "&7Zabij &e5 &7graczy z nizszym, rownym badz wyzszym poziomem &8(w zakresie 10 lvli)",
            " "
    )).toItemStack().clone(), 5, 15.0),
    M4(4, new ItemBuilder(Material.BOOK).setName("&e&lDoswiadczenie Trzeba Zdobyc").setLore(Arrays.asList(
            "&7Awansuj o &e1 &7poziom",
            " "
    )).toItemStack().clone(), 1, 10.0),
    M5(5, new ItemBuilder(Material.BOOK).setName("&e&lSloneczny Patrol").setLore(Arrays.asList(
            "&7Zniszcz &e80 &7metinow z mapy",
            " "
    )).toItemStack().clone(), 80, 25.0);

    private final int id;
    private final ItemStack item;
    private final int reqAmount;
    private final double chance;

    PustelnikMissions(final int id, final ItemStack item, final int reqAmount, final double chance) {
        this.id = id;
        this.item = item;
        this.reqAmount = reqAmount;
        this.chance = chance;
    }

    public int getId() {
        return this.id;
    }

    public ItemStack getItem(final int progress) {
        if (getId() == 0) return this.item;
        final ItemStack toReturn = this.item.clone();
        final ItemBuilder itemBuilder = new ItemBuilder(toReturn);
        itemBuilder.setLoreCrafting(toReturn.getItemMeta().getLore(), Arrays.asList(
                "&7Postep: &e" + progress + "&7/&e" + Utils.spaceNumber(String.valueOf(this.reqAmount)) + " &7(" + (DoubleUtils.round(((double) progress /this.reqAmount) * 100, 2)) + "%)"
        ));
        return itemBuilder.toItemStack().clone();
    }

    public int getReqAmount() {
        return this.reqAmount;
    }

    public double getChance() {
        return this.chance;
    }

    public static PustelnikMissions getById(final int id) {
        for (final PustelnikMissions pustelnikMissions : values()) {
            if (pustelnikMissions.getId() == id) {
                return pustelnikMissions;
            }
        }
        return null;
    }

    public static PustelnikMissions getRandom() {
        for (final PustelnikMissions pustelnikMissions : values()) {
            if (pustelnikMissions.getChance() >= 100.0 || pustelnikMissions.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                return pustelnikMissions;
            }
        }
        return null;
    }
}
