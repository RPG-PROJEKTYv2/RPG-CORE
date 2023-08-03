package rpg.rpgcore.npc.oldwyslannik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

public enum WyslannikMissionKillBoss {
    M1(1, "&8[&4&lBOSS&8] &c&lDowodca Rozbojnikow", 2, ItemHelper.createSword("&dSlaby Miecz Wyslannika", Material.WOOD_SWORD, 2, 2, false)),
    M2(2, "&8[&4&lBOSS&8] &a&lWodz Goblinow", 2, SkrzynkiOther.getItem("I4", 1)),
    M3(3, "&8[&4&lBOSS&8] &f&lKrol Goryli", 1, ItemHelper.createSword("&cOstry Miecz", Material.STONE_SWORD, 9, 4, false)),
    M4(4, "&8[&4&lBOSS&8] &7&lPrzekleta Dusza", 1, SkrzynkiOther.getItem("I4", 2)),
    M5(5, "&8[&4&lBOSS&8] &e&lTryton", 3, ItemHelper.createSword("&5Mityczne Ostrze Wyslannika", Material.STONE_SWORD, 25, 9, false)),
    M6(6, "&8[&4&lBOSS&8] &bKrol Lodu", 2, SkrzynkiOther.getItem("I4", 3)),
    M7(7, "&8[&4&lBOSS&8] &c&lPiekielny Rycerz", 2, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M8(8, "&8[&4&lBOSS&8] &5&lPrzeklety Czarnoksieznik", 2, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack());
    private final int mission;
    private final String mobName;
    private final int reqAmount;
    private final ItemStack reward;

    WyslannikMissionKillBoss(final int mission, final String mobName, final int reqAmount, final ItemStack reward) {
        this.mission = mission;
        this.mobName = mobName;
        this.reqAmount = reqAmount;
        this.reward = reward;
    }

    public int getMission() {
        return this.mission;
    }

    public String getMobName() {
        return this.mobName;
    }

    public int getReqAmount() {
        return this.reqAmount;
    }

    public ItemStack getReward() {
        return this.reward;
    }

    public static WyslannikMissionKillBoss getByMission(final int mission) {
        for (final WyslannikMissionKillBoss wyslannikMissionKillBoss : values()) {
            if (wyslannikMissionKillBoss.getMission() == mission) {
                return wyslannikMissionKillBoss;
            }
        }
        return null;
    }
}
