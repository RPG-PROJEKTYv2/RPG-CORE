package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Bossy {
    I1_10("I1_10", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &c&lDowodca Rozbojnikow").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
    I10_20("I10_20", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &a&lWodz Goblinow").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
    I20_30("I20_30", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &f&lKrol Goryli").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
    I30_40("I30_40", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &7&lPrzekleta Dusza").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
    I40_50("I40_50", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &e&lTryton").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
    I60_70("I60_70", new ItemBuilder(Material.FIREBALL).setName("&f&lPrzywolanie &8&l* &c&lPiekielny Rycerz").setLore(Arrays.asList("", "&7Kliknij aby przywolac go obok siebie...")).toItemStack().clone()),
   // ITEM DO RESPU BOSSA 70-80
    I2("I2", new ItemBuilder(Material.MAGMA_CREAM).setName("&cPrzeklete Serce").setLore(Arrays.asList(" ","&7Serce to pozwoli ci ozywic krag &3&lCzarnoksieznika&7...")).hideFlag().toItemStack().clone()),
    // ITEM DO RESPU BOSSA 80-90
    I3("I3", new ItemBuilder(Material.BOOK).setName("&6Zwoj Swiatlosci").setLore(Arrays.asList(" ","&7Chyba &6Pustelnik &7tego potrzebuje!")).hideFlag().toItemStack().clone()),
    I3_1("I3_1", new ItemBuilder(Material.SPIDER_EYE).setName("&e&lPrzywolanie").setLore(Arrays.asList(" ", "&7Dzieki temu przedmiotowi przywolasz &e&lMitycznego Pajaka&7!")).addGlowing().toItemStack().clone()),
    // ITEM DO RESPU BOSSA 110-120
    I4("I4", new ItemBuilder(Material.DIAMOND).setName("&bFragment Krysztalu").setLore(Arrays.asList(" ","&7Fragment krysztalu pozwoli ci naladowac jego moc!")).hideFlag().toItemStack().clone()),
    // ITEM DO RESPU BOSSA 120-130
    I5("I5", new ItemBuilder(Material.BOOK_AND_QUILL).setName("&5Smoczy Papirus").setLore(Arrays.asList(" ","&5Papirus &7ten pozwoli ci wylosowac zadanie aby przywolac smoka...")).hideFlag().toItemStack().clone()),
    I5_1("I5_1", new ItemBuilder(Material.DOUBLE_PLANT).setName("&aSmoczy Klejnot").setLore(Arrays.asList(" ","&7Potrzebny do przywolania smoka...")).hideFlag().toItemStack().clone()),
    I5_2("I5_2", new ItemBuilder(Material.HOPPER).setName("&6Rog Naszych Przodkow").setLore(Arrays.asList("&7W polaczeniu z &aKlejnotami &7umozliwia przywolanie smoka")).hideFlag().toItemStack().clone()),

    // Itemy bonusow bossow
    I70_80_BONUS("I70_80_BONUS", new ItemBuilder(Material.COAL, 1, (short) 1).setName("&5&lPrzeklety Odlamek").setLore(Arrays.asList(" ", "&7Kliknij... przeklete zawiera mroczne zaklecie")).toItemStack().clone());


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