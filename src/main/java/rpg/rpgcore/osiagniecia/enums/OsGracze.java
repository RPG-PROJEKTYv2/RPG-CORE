package rpg.rpgcore.osiagniecia.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public enum OsGracze {
    M1(1, 0, 5, ItemHelper.createSword("&8Miecz Skrytobojcy", Material.IRON_SWORD, 35, 15, false).clone()),
    M2(2, 1, 20, ItemHelper.createArmor("&8Helm Skrytobojcy", Material.IRON_HELMET, 45, 12).clone()),
    M3(3, 2, 50, ItemHelper.createArmor("&8Zbroja Skrytobojcy", Material.IRON_CHESTPLATE, 55, 15).clone()),
    M4(4, 3, 120, GlobalItem.getItem("I1", 1).clone()),
    M5(5, 4, 300, GlobalItem.getItem("I1", 5).clone()),
    M6(6, 5, 750, AkcesoriaPodsHelper.createNaszyjnik(155, 28, 36, 76, "&8Tarcza Skrytobojcy").clone()),
    M7(7, 6, 1_500, GlobalItem.getItem("I4", 2).clone()),
    M8(8, 7, 4_500, new ItemBuilder(Material.STONE).setName("&4&lWKROTCE").toItemStack().clone()),
    M9(9, 8, 7_500, new ItemBuilder(Material.STONE).setName("&4&lWKROTCE").toItemStack().clone());

    private final int mission;
    private final int reqMission;
    private final int reqProgress;
    private final ItemStack reward;

    OsGracze(final int mission, final int reqMission, final int reqProgress, final ItemStack reward) {
        this.mission = mission;
        this.reqMission = reqMission;
        this.reqProgress = reqProgress;
        this.reward = reward;
    }

    public int getMission() {
        return this.mission;
    }

    public int getReqMission() {
        return this.reqMission;
    }

    public int getReqProgress() {
        return this.reqProgress;
    }

    public ItemStack getReward() {
        return this.reward;
    }

    public static OsGracze getByMission(final int mission) {
        for (final OsGracze osGracze : values()) {
            if (osGracze.getMission() == mission) {
                return osGracze;
            }
        }
        return null;
    }
}
