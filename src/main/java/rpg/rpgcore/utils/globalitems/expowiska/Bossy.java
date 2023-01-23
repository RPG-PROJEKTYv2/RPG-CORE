package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;

public enum Bossy {
    I1("I1", new ItemBuilder(Material.SLIME_BALL).setName("ITEM DO RESPU BOSSA X").setLore(Arrays.asList(" ","&7SDFSDFASFAS..")).hideFlag().toItemStack().clone());

    private final ItemStack itemStack;
    private final String name;

    Bossy(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Bossy rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Bossy getByName(String name) {
        for (Bossy rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Bossy.getByName(string).getItemStack();
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