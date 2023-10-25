package rpg.rpgcore.npc.sezonowiec.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

@Getter
public enum SezonowiecRewards {
    I1(1, 1, new ItemBuilder(Material.LADDER).setName("&6&lJesienna Szarfa").toItemStack(), 1, 1),
    I2(2, 1, new ItemBuilder(Material.LEASH).setName("&6&lJesienny Pas").toItemStack(), 1, 1),
    I3(3, 50, SkrzynkiOther.I1.getItemStack(), 1, 5),
    I4(4, 40, GlobalItem.I_KSIEGAMAGII_PLUS.getItemStack(), 1, 1),
    I5(5, 15, new ItemBuilder(Material.GOLD_NUGGET).setName("&4&lH&6&lS").toItemStack(), 10, 75),
    I6(6, 5, new ItemBuilder(Material.IRON_BLOCK).setName("&6&lLosowe Akcesorium").toItemStack(), 1, 1),
    I7(7, 80, SkrzynkiOther.I4.getItemStack(), 1, 16),
    I8(8, 100, SkrzynkiOther.I5.getItemStack(), 16, 64),
    I9(9, 10, GlobalItem.I55.getItemStack(), 1, 1),
    I10(10, 2, GlobalItem.I_FRAGMENT_BONA.getItemStack(), 1, 1),
    I11(11, 25, GlobalItem.I_OCZYSZCZENIE.getItemStack(), 1, 12),
    I12(12, 0.25, BonType.SREDNIE_5.getBon(), 1, 1),
    I13(13, 0.25, BonType.DEFENSYWA_5.getBon(), 1, 1),
    I14(14, 0.25, BonType.KRYTYK_5.getBon(), 1, 1),
    I15(15, 0.25, BonType.HP_10.getBon(), 1, 1);

    private final int id;
    private final double chance;
    private final ItemStack reward;
    private final int minAmount;
    private final int maxAmount;

    SezonowiecRewards(final int id, final double chance, final ItemStack reward, final int minAmount, final int maxAmount) {
        this.id = id;
        this.chance = chance;
        this.reward = reward;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
