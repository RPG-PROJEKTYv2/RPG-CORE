package rpg.rpgcore.dodatki.bony.helpers;

import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.dodatki.bony.enums.BonType;

public class BonyHelper {
    public static ItemStack createBon(final BonType bonType) {
        return bonType.getBon();
    }
}
