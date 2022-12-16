package rpg.rpgcore.npc.wyslannik.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum WyslannikMissionKillBoss {
    M1(1, "&8[&4&lBOSS&8] &9&lKrol Wygnancow", 5, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M2(2, "&8[&4&lBOSS&8] &a&lWodz Goblinow", 5, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M3(3, "&8[&4&lBOSS&8] &f&lKrol Goryli", 5, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M4(4, "&8[&4&lBOSS&8] &7&lPrzekleta Dusza", 5, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M5(5, "&8[&4&lBOSS&8] &e&lTryton", 5, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M6(6, "&8[&4&lBOSS&8] &f&lMityczny Lodowy Golem", 4, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M7(7, "&8[&4&lBOSS&8] &0&lPiekielny Rycerz", 4, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack()),
    M8(8, "&8[&4&lBOSS&8] &5&lPrzeklety Czarnoksieznik", 3, new ItemBuilder(Material.STONE).setName("&c&lDo ustawienia!").toItemStack());
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
