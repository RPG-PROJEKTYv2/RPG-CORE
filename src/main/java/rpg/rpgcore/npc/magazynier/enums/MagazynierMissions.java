package rpg.rpgcore.npc.magazynier.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

public enum MagazynierMissions {
    M1(1, new ItemBuilder(Material.BOOK).toItemStack(), 1);
    private final int id;
    private final ItemStack missionItem;
    private final double reqAmount;

    MagazynierMissions(final int id, final ItemStack missionItem, final double reqAmount) {
        this.id = id;
        this.missionItem = missionItem;
        this.reqAmount = reqAmount;
    }

    public int getId() {
        return this.id;
    }

    public ItemStack getMissionItem() {
        return this.missionItem;
    }

    public double getReqAmount() {
        return this.reqAmount;
    }

    public static MagazynierMissions getMissionById(final int id) {
        for (final MagazynierMissions mission : values()) {
            if (mission.getId() == id) {
                return mission;
            }
        }
        return null;
    }
}
