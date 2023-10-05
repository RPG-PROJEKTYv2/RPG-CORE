package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Ulepszacze {
    I_SZATAROZBOJNIKA("1-10", new ItemBuilder(Material.RABBIT_HIDE).setName("&8Szata Rozbojnika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OKOGOBLINA("10-20", new ItemBuilder(Material.EYE_OF_ENDER).setName("&aOko Goblina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SKORAGORYLA("20-30", new ItemBuilder(Material.INK_SACK, 1 , (short)15).setName("&fSkora Goryla").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZLAMANAKOSC("30-40", new ItemBuilder(Material.BONE).setName("&7Zlamana Kosc").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_LZAOCEANU("40-50", new ItemBuilder(Material.GHAST_TEAR).setName("&bLza Oceanu").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_WILCZEFUTRO("50-60", new ItemBuilder(Material.LEATHER).setName("&7Wilcze Futro").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OGNISTYPYL("60-70", new ItemBuilder(Material.BLAZE_POWDER).setName("&cOgnisty Pyl").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_TRUJACAROSLINA("70-80", new ItemBuilder(Material.RED_ROSE, 1, (short)2).setName("&5Trujaca Roslina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_JADPTASZNIKA("80-90", new ItemBuilder(Material.SPIDER_EYE).setName("&6Jad Ptasznika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROCZNYMATERIAL("90-100", new ItemBuilder(Material.NETHER_BRICK_ITEM).setName("&9Mroczny Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SZAFIROWESERCE("100-110", new ItemBuilder(Material.INK_SACK, 1, (short)4).setName("&bSzafirowy Skrawek").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZAKLETYLOD("110-120", new ItemBuilder(Material.ICE).setName("&bZaklety Lod").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_NIEBIANSKIMATERIAL("120-130", new ItemBuilder(Material.INK_SACK, 1, (short)12).setName("&3Niebianki Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    //x64
    I_SZATAROZBOJNIKA64("1-10-64", new ItemBuilder(Material.RABBIT_HIDE).setName("&8Szata Rozbojnika &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OKOGOBLINA64("10-20-64", new ItemBuilder(Material.EYE_OF_ENDER).setName("&aOko Goblina &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SKORAGORYLA64("20-30-64", new ItemBuilder(Material.INK_SACK, 1 , (short)15).setName("&fSkora Goryla &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZLAMANAKOSC64("30-40-64", new ItemBuilder(Material.BONE).setName("&7Zlamana Kosc &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_LZAOCEANU64("40-50-64", new ItemBuilder(Material.GHAST_TEAR).setName("&bLza Oceanu &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_WILCZEFUTRO64("50-60-64", new ItemBuilder(Material.LEATHER).setName("&7Wilcze Futro &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OGNISTYPYL64("60-70-64", new ItemBuilder(Material.BLAZE_POWDER).setName("&cOgnisty Pyl &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_TRUJACAROSLINA64("70-80-64", new ItemBuilder(Material.RED_ROSE, 1, (short)2).setName("&5Trujaca Roslina &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_JADPTASZNIKA64("80-90-64", new ItemBuilder(Material.SPIDER_EYE).setName("&6Jad Ptasznika &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROCZNYMATERIAL64("90-100-64", new ItemBuilder(Material.NETHER_BRICK_ITEM).setName("&9Mroczny Material &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SZAFIROWESERCE64("100-110-64", new ItemBuilder(Material.INK_SACK, 1, (short)4).setName("&bSzafirowy Skrawek &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_ZAKLETYLOD64("110-120-64", new ItemBuilder(Material.ICE).setName("&bZaklety Lod &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_NIEBIANSKIMATERIAL64("120-130-64", new ItemBuilder(Material.INK_SACK, 1, (short)12).setName("&3Niebianski Material &8(&ex64&8)").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone());
    private final ItemStack itemStack;
    private final String name;

    Ulepszacze(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (Ulepszacze rank : values()) {
            ItemStack itemStack = rank.getItem();
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
        ItemStack itemStack = Ulepszacze.getByName(string).getItem();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return itemStack.clone();
    }
}
