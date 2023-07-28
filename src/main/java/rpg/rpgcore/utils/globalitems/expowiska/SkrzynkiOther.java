package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum SkrzynkiOther {
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&e&lPozlacany Skarb").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.ENDER_CHEST).setName("&7&lCiezka Skrzynia Kowala").setLore(Arrays.asList("&8&oTa skrzynia zawiera asortyment kowalski...")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.CHEST).setName("&3Tajemnicza Skrzynia").setLore(Arrays.asList("&8&oSkrzynia ta zawiera cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.CHEST).setName("&2Skrzynia Z Surowcami").setLore(Arrays.asList("&8&oSkrzynia ta zawiera rozne materialy...")).hideFlag().toItemStack().clone()),

    I_PETY1("I_PETY1", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia Ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "Normal").hideFlag().toItemStack().clone()),
    I_PETY2("I_PETY2", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "ItemShop").hideFlag().toItemStack().clone());

    private final ItemStack itemStack;
    private final String name;

    SkrzynkiOther(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (SkrzynkiOther rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static SkrzynkiOther getByName(String name) {
        for (SkrzynkiOther rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = SkrzynkiOther.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }
    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
