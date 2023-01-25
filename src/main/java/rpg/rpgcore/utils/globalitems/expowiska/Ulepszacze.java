package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Ulepszacze {
    I_SZATANAJEMNIKA("I_SZATANAJEMNIKA", new ItemBuilder(Material.RABBIT_HIDE).setName("&6Szata Najemnika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OKOGOBLINA("I_OKOGOBLINA", new ItemBuilder(Material.EYE_OF_ENDER).setName("&aOko Goblina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SKORAGORYLA("I_SKORAGORYLA", new ItemBuilder(Material.INK_SACK, 1 , (short)15).setName("&fSkora Goryla").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZLAMANAKOSC("I_ZLAMANAKOSC", new ItemBuilder(Material.BONE).setName("&7Zlamana Kosc").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_LZAOCEANU("I_LZAOCEANU", new ItemBuilder(Material.GHAST_TEAR).setName("&bLza Oceanu").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROZNYPAZUR("I_MROZNYPAZUR", new ItemBuilder(Material.FEATHER).setName("&bMrozny Pazur").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OGNISTYPYL("I_OGNISTYPYL", new ItemBuilder(Material.BLAZE_POWDER).setName("&cOgnisty Pyl").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_TRUJACAROSLINA("I_TRUJACAROSLINA", new ItemBuilder(Material.RED_ROSE, 1, (short)2).setName("&5Trujaca Roslina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_JADPTASZNIKA("I_JADPTASZNIKA", new ItemBuilder(Material.SPIDER_EYE).setName("&6Jad Ptasznika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROCZNYMATERIAL("I_MROCZNYMATERIAL", new ItemBuilder(Material.NETHER_BRICK).setName("&1Mroczny Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SZAFIROWESERCE("I_SZAFIROWESERCE", new ItemBuilder(Material.INK_SACK, 1, (short)4).setName("&bSzafirowe Serce").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZAKLETYLOD("I_ZAKLETYLOD", new ItemBuilder(Material.ICE).setName("&bZaklety Lod").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_NIEBIANSKIMATERIAL("I_NIEBIANSKIMATERIAL", new ItemBuilder(Material.INK_SACK, 1, (short)12).setName("&3Niebianki Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone());
    private final ItemStack itemStack;
    private final String name;

    Ulepszacze(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Ulepszacze rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static Ulepszacze getByName(String name) {
        for (Ulepszacze rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = Ulepszacze.getByName(string).getItemStack();
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
