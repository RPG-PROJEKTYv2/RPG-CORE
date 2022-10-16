package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

public enum GlobalItem {
    // SKRZYNKI //
    // RARE
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&6&lWartosciowy Kufer").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&8&lSkrzynia Kowala").setLore(Arrays.asList("&8&oTa skrzynia zawiera asortyment kowalski...")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "Normal").hideFlag().toItemStack().clone()),
   // INNE
    I4("I4", new ItemBuilder(Material.CHEST).setName("&3Tajemnicza Skrzynia").setLore(Arrays.asList("&8&oSkrzynia ta zawiera cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.CHEST).setName("&2Skrzynia z Roznosciami").setLore(Arrays.asList("&8&oSkrzynia ta zawiera rozne materialy...")).hideFlag().toItemStack().clone()),


    I6("I6", new ItemBuilder(Material.EXP_BOTTLE, 1).setName("&8• &eSakwa &8•").addGlowing().toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.PAPER).setName("&bBon na powiekszenie magazynu").setLore(Arrays.asList("&8&oChyba &b&lMagazynier &8tego potrzebuje")).addGlowing().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.EMERALD).setName("&4&lArtefakt &b&lMagazyniera").setLore(Arrays.asList("&8Artefakt ten pozwala na otwieranie","&8swoich magazynow nie chodzac do &b&lMagazyniera","&6Wlasciciel: &7playerName", " ","&4&lARTEFAKT")).addGlowing().toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.BOOK).setName("&a&lZwoj Blogoslawienstwa").setLore(Arrays.asList("&8Przedmiot ten ochroni twoj","&8przedmiot przed spaleniem podczas","&8ulepszania go u &4&lKowala")).addGlowing().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.EMERALD).setName("&c&lvalue &4&lH&8&lC").setLore(Arrays.asList("&8&oKliknij&8, zeby zasilic swoj balans")).addGlowing().toItemStack().clone()),
    // MATERIALY
    I11("I11", new ItemBuilder(Material.GOLD_INGOT).setName("&eZloto").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I12("I12", new ItemBuilder(Material.DIAMOND).setName("&bBrylant").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.EMERALD).setName("&aSzmaragd").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.REDSTONE).setName("&cPyl").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.STONE).setName("&7Kamien").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.IRON_INGOT).setName("&8Stal").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.SULPHUR).setName("&7Proch").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),

    // SKRZYNKI CIAG DALSZY BO ORZEL KURWA NIE UMIE ZROBIC DOBRZE...........
    I21("I21", new ItemBuilder(Material.CHEST).setName("&8&lSzkrzynia Gornika").setLore(Arrays.asList("&8&oMoze zawierac wartosciowe przedmioty...")).hideFlag().toItemStack().clone()),
    I22("I22", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "ItemShop").hideFlag().toItemStack().clone()),


    // KONIEC MOZLIWYCH MISJI U NPC
    I_ERROR("error", new ItemBuilder(Material.BARRIER).setName("&aUkonczono!").setLore(Arrays.asList("&7Ukonczyles/as juz wszystkie dostepne", "&7Misje u tego npc!", "", "&8Mozliwe ze w przyszloscie", "&8pojawi sie ich wiecej")).toItemStack().clone());



    private final ItemStack itemStack;
    private final String name;

    GlobalItem(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static void getList(Player player) {
        int i = 1;
        for (GlobalItem rank : values()) {
            ItemStack itemStack = rank.getItemStack();
            player.sendMessage(Utils.format(i + ". " + itemStack.getItemMeta().getDisplayName()));
            i = i + 1;
        }
    }

    public static GlobalItem getByName(String name) {
        for (GlobalItem rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public static ItemStack getItem(String string, int amount) {
        ItemStack itemStack = GlobalItem.getByName(string).getItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack getArtefakt(final String itemName, final String playerName) {
        ItemStack itemStack = GlobalItem.getByName(itemName).getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("playerName", playerName));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getHellCoin(final int value) {
        ItemStack itemStack = GlobalItem.getByName("I20").getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("value", String.valueOf(value)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
