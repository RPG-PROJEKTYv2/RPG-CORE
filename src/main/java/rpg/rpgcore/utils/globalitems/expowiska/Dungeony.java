package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Dungeony {

    I_KLUCZ_ARENA_PRZEKLETYCH_WOJOWNIKOW("I_KLUCZ_ARENA_PRZEKLETYCH_WOJOWNIKOW", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&c&lKlucz Przekletych Wojownikow").setLore(Arrays.asList(" ", "&7Klucz ten otwiera wrota na arene &4&lWOJOWNIKOW&7!")).hideFlag().toItemStack().clone()),
    I_KLUCZ_PIEKIELNY_PRZEDSIONEK("I_KLUCZ_PIEKIELNY_PRZEDSIONEK", new ItemBuilder(Material.TRIPWIRE_HOOK).setName("&c&lKlucz do Piekielnego Przedsionka").setLore(Arrays.asList(" ", "&7Klucz ten otwiera droge do &4&lPiekielnego Przedsionka&7!")).hideFlag().toItemStack().clone()),
    I_PIEKIELNY_PRZEDSIONEK_SKRZYNKA("I_PRZEDSIONEK_BOSS", new ItemBuilder(Material.JUKEBOX).setName("&4Skrzynia Piekielnego Wladcy").setLore(Arrays.asList(" ","&7Kliknij aby zobaczyc co skrywa...")).hideFlag().toItemStack().clone());


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
        return itemStack.clone();
    }


    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
