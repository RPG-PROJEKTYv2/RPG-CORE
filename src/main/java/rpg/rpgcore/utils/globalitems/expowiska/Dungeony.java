package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;

public enum Dungeony {

    I_KLUCZ_ARENA_PRZEKLETYCH_WOJOWNIKOW("I_KLUCZ_ARENA_PRZEKLETYCH_WOJOWNIKOW", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&c&lKlucz Przekletych Wojownikow").setLore(Arrays.asList(" ", "&7Klucz ten otwiera wrota na arene &4&lWOJOWNIKOW&7!")).hideFlag().toItemStack().clone()),
    I_KLUCZ_PIEKIELNA_KRYJOWKA("I_KLUCZ_PIEKIELNA_KRYJOWKA", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&c&lKlucz Piekielnej Kryjowki").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &4&lPIEKIELNEJ KRYJOWKI&7!")).hideFlag().toItemStack().clone());


    private final ItemStack itemStack;
    private final String name;

    Dungeony(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Dungeony rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Dungeony getByName(String name) {
        for (Dungeony rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Dungeony.getByName(string).getItemStack();
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
