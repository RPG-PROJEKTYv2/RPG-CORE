package rpg.rpgcore.utils.globalitems.npc;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

public enum KolekcjonerItems {
    I99("ERROR", new ItemBuilder(Material.DIRT).setName("&c&lCos sie popsulo :<").toItemStack().clone());

    private final ItemStack itemStack;
    private final String name;

    KolekcjonerItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = KolekcjonerItems.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static KolekcjonerItems getByName(String name) {
        for (KolekcjonerItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
    public static void getList(Player player) {
        int i = 1;
        for (KolekcjonerItems rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}