package rpg.rpgcore.utils.globalitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Objects;

public enum GlobalItem {
    // SKRZYNKI //
    // RARE
    I1("I1", new ItemBuilder(Material.ENDER_CHEST).setName("&6&lWartosciowy Kufer").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.CHEST).setName("&f&lSkrzynia Kowala").setLore(Arrays.asList("&8&oTa skrzynia zawiera asortyment kowalski...")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia Ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "Normal").hideFlag().toItemStack().clone()),
    // INNE
    I4("I4", new ItemBuilder(Material.CHEST).setName("&3Tajemnicza Skrzynia").setLore(Arrays.asList("&8&oSkrzynia ta zawiera cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.CHEST).setName("&2Skrzynia Z Surowcami").setLore(Arrays.asList("&8&oSkrzynia ta zawiera rozne materialy...")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.CHEST).setName("&4&lHELLCASE").setLore(Arrays.asList("&8&oSerwerowa skrzynia skrywa cenne przedmioty...")).hideFlag().toItemStack().clone()),
    I_METAL("I_METAL", new ItemBuilder(Material.IRON_INGOT).setName("&7Magiczna Stal").setLore(Arrays.asList("&8Przedmiot ten pozwoli ci ulepszyc twoj", "&8przedmiot z szansa 100% u &4&lKowala")).addGlowing().toItemStack().clone()),
    I_OCZYSZCZENIE("I_OCZYSZCZENIE", new ItemBuilder(Material.GHAST_TEAR, 1).setName("&fLza Aniola").setLore(Arrays.asList("&8Przedmiot ten oczysci twoj przedmiot", "&8abys mogl go ponownie ulepszyc u &4&lKowala")).addGlowing().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.BOOK).setName("&a&lZwoj Kowala").setLore(Arrays.asList("&8Przedmiot ten ochroni twoj", "&8przedmiot przed spaleniem podczas", "&8ulepszania go u &4&lKowala")).addGlowing().toItemStack().clone()),
    I_KAMIENBAO("I_KAMIENBAO", new ItemBuilder(Material.COAL, 1, (short) 1).setName("&3&lKamien Zaczarowania Stolu").setLore(Arrays.asList("&8Ten magiczny kamien pozwala Ci", "&8zmienic swoje bonusy w &6Stole Magi", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy")).toItemStack().clone()),
    I_KSIEGAMAGII("I_KSIEGAMAGII", new ItemBuilder(Material.ENCHANTED_BOOK).setName("&4&lKsiega Magii").setLore(Arrays.asList("&8Ta magiczna ksiega pozwoli Ci", "&8zmienic jeden bonus w &6Stole Magii", "&8Pamietaj &c&lAdministracja &8nie odpowiada za zmieniane bonusy")).toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.GOLD_NUGGET).setName("&4&lH&6&lS &4&lvalue").setLore(Arrays.asList("&8&oKliknij&8, zeby zasilic swoj balans")).addGlowing().toItemStack().clone()),
    // MATERIALY
    I12("I12", new ItemBuilder(Material.GOLD_INGOT).setName("&eZloto").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I13("I13", new ItemBuilder(Material.DIAMOND).setName("&bBrylant").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I14("I14", new ItemBuilder(Material.EMERALD).setName("&aSzmaragd").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I15("I15", new ItemBuilder(Material.REDSTONE).setName("&cPyl").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I16("I16", new ItemBuilder(Material.STONE).setName("&7Kamien").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I17("I17", new ItemBuilder(Material.IRON_INGOT).setName("&8Stal").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    I18("I18", new ItemBuilder(Material.SULPHUR).setName("&7Proch").setLore(Arrays.asList("&8&oMaterial")).hideFlag().toItemStack().clone()),
    // ULEPSZACZE
    I_SZATANAJEMNIKA("I_SZATANAJEMNIKA", new ItemBuilder(Material.RABBIT_HIDE).setName("&6Szata Najemnika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OKOGOBLINA("I_OKOGOBLINA", new ItemBuilder(Material.EYE_OF_ENDER).setName("&aOko Goblina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SKORAGORYLA("I_SKORAGORYLA", new ItemBuilder(Material.INK_SACK, 1 , (short)15).setName("&fSkora Goryla").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_PROCHYZJAWY("I_PROCHYZJAWY", new ItemBuilder(Material.SULPHUR).setName("&7Proch Zjawy").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_LZAOCEANU("I_LZAOCEANU", new ItemBuilder(Material.GHAST_TEAR).setName("&bLza Oceanu").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROZNYPAZUR("I_MROZNYPAZUR", new ItemBuilder(Material.FEATHER).setName("&bMrozny Pazur").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_OGNISTYPYL("I_OGNISTYPYL", new ItemBuilder(Material.BLAZE_POWDER).setName("&cOgnisty Pyl").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_TRUJACAROSLINA("I_TRUJACAROSLINA", new ItemBuilder(Material.RED_ROSE, 1, (short)2).setName("&5Trujaca Roslina").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_JADPTASZNIKA("I_JADPTASZNIKA", new ItemBuilder(Material.SPIDER_EYE).setName("&6Jad Ptasznika").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_MROCZNYMATERIAL("I_MROCZNYMATERIAL", new ItemBuilder(Material.NETHER_BRICK).setName("&1Mroczny Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SZAFIROWESERCE("I_SZAFIROWESERCE", new ItemBuilder(Material.INK_SACK, 1, (short)4).setName("&bSzafirowe Serce").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_SERCEDEMONA("I_SERCEDEMONA", new ItemBuilder(Material.FLINT).setName("&dSerce Demona").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),
    I_NIEBIANSKIMATERIAL("I_NIEBIANSKIMATERIAL", new ItemBuilder(Material.INK_SACK, 1, (short)12).setName("&3Niebianki Material").setLore(Arrays.asList("&8&oUlepszacz")).hideFlag().toItemStack().clone()),

    // SKRZYNKI CIAG DALSZY BO ORZEL KURWA NIE UMIE ZROBIC DOBRZE...........
    I21("I21", new ItemBuilder(Material.CHEST).setName("&8&lSzkrzynia Gornika").setLore(Arrays.asList("&8&oMoze zawierac wartosciowe przedmioty...")).hideFlag().toItemStack().clone()),
    I22("I22", new ItemBuilder(Material.CHEST).setName("&e&lSkrzynia ze Zwierzakami").setLore(Arrays.asList("&8&oOtworz i zobacz co skrywa...")).addTagString("Type", "ItemShop").hideFlag().toItemStack().clone()),

    // Zmianki
    I50("I50", new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&6&lKamien Zaczarowania Miecza").setLore(Arrays.asList("&8Pozwala zmienic bonusy na twoim mieczu")).toItemStack().clone()),
    I51("I51", new ItemBuilder(Material.INK_SACK, 1, (short) 14).setName("&6&lKamien Zaczarowania Zbroi").setLore(Arrays.asList("&8Pozwala zmienic bonusy na twojej zbroi")).toItemStack().clone()),

    // PIERSCIENIE DOSWIADCZENIA
    I52("I52", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l25%").setLore(Arrays.asList(
            "&7Czas trwania: &e30min",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &625%"
    )).addTagDouble("exp", 25).addTagString("time", "30m").addGlowing().toItemStack().clone()),
    I53("I53", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l25%").setLore(Arrays.asList(
            "&7Czas trwania: &e1h",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &625%"
    )).addTagDouble("exp", 25).addTagString("time", "1h").addGlowing().toItemStack().clone()),
    I54("I54", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l50%").setLore(Arrays.asList(
            "&7Czas trwania: &e30min",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &650%"
    )).addTagDouble("exp", 50).addTagString("time", "30m").addGlowing().toItemStack().clone()),
    I55("I55", new ItemBuilder(Material.GHAST_TEAR).setName("&e&lPierscien Doswiadczenia &6&l50%").setLore(Arrays.asList(
            "&7Czas trwania: &e1h",
            "&8Uzycie go powoduje zwiekszenie",
            "&8twojego bonusu doswiadczenia o &650%"
    )).addTagDouble("exp", 50).addTagString("time", "1h").addGlowing().toItemStack().clone()),
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

    public static ItemStack getHells(final int value) {
        ItemStack itemStack = Objects.requireNonNull(GlobalItem.getByName("I11")).getItemStack().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("value", String.valueOf(value)));
        itemStack.setItemMeta(itemMeta);
        return new ItemBuilder(itemStack.clone()).addTagInt("value", value).toItemStack();
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
