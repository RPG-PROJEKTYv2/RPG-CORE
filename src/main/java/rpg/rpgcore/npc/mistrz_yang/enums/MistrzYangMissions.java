package rpg.rpgcore.npc.mistrz_yang.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum MistrzYangMissions {
    M1(1, new ItemBuilder(Material.BOOK).setName("&cChinski Zabojca").setLore(Arrays.asList(
            "&7Zabij &c%amount% &7potworow z mapy"
    )).toItemStack(), 33),
    M2(2, new ItemBuilder(Material.BOOK).setName("&cPogromca Krysztalow").setLore(Arrays.asList(
            "&7Zniszcz &c%amount% &7krysztalow metin z mapy"
    )).toItemStack(), 33),
    M3(3, new ItemBuilder(Material.BOOK).setName("&cMistrz Swiatyni").setLore(Arrays.asList(
            "&7Ukoncz &c%amount% &7dungeonow z mapy"
    )).toItemStack(), 33);


    private final int id;
    private final ItemStack item;
    private final double chance;

    MistrzYangMissions(final int id, final ItemStack item, final double chance) {
        this.id = id;
        this.item = item;
        this.chance = chance;
    }

    public int getId() {
        return this.id;
    }

    public ItemStack getItem(final int reqAmount, final int progress) {
        final List<String> lore = this.item.getItemMeta().getLore();
        lore.replaceAll(s -> s.replace("%amount%", String.valueOf(reqAmount)));
        return new ItemBuilder(this.item.clone()).setLoreCrafting(lore, Arrays.asList(
                " ",
                "&7Postep: &c" + progress + "&7/&c" + reqAmount + " &8(" + DoubleUtils.round(((double) progress / reqAmount) * 100, 2) + "%)"
        )).toItemStack().clone();
    }

    public int getRandomAmount(final int missionId) {
        switch (missionId) {
            case 1:
                return ChanceHelper.getRandInt(10_000, 25_000);
            case 2:
                return ChanceHelper.getRandInt(180, 350);
            case 3:
                return ChanceHelper.getRandInt(2, 5);
            default:
                return 0;
        }
    }

    public double getChance() {
        return this.chance;
    }


    public static MistrzYangMissions getById(final int id) {
        for (final MistrzYangMissions mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }

    public static MistrzYangMissions getRandom() {
        MistrzYangMissions toReturn = null;
        while (toReturn == null) {
            for (final MistrzYangMissions mistrzYangMissions : values()) {
                if (mistrzYangMissions.getChance() >= 100.0 || mistrzYangMissions.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                    toReturn = mistrzYangMissions;
                    break;
                }
            }
        }
        return toReturn;
    }
}
