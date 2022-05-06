package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {

    public static ItemStack SAKWA() {
        ItemBuilder sakwa = new ItemBuilder(Material.EXP_BOTTLE, 1);

        sakwa.setName("&8• &eSakwa &8•");
        sakwa.addGlowing();

        return sakwa.toItemStack();
    }

}
