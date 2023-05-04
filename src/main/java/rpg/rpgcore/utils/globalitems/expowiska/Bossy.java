package rpg.rpgcore.utils.globalitems.expowiska;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum Bossy {
    // ITEM DO RESPU BOSSA 60-70
    I1("I1", new ItemBuilder(Material.FIREBALL).setName("&cZaczarowana Kula").setLore(Arrays.asList(" ","&7Dzieki tej kuli przywolasz &c&lPiekielna Dusze&7!")).hideFlag().toItemStack().clone()),
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
    I5_2("I5_2", new ItemBuilder(Material.HOPPER).setName("&6Rog Naszych Przodkow").setLore(Arrays.asList("&7W polaczeniu z &aKlejnotami &7umozliwia przywolanie smoka")).hideFlag().toItemStack().clone());
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