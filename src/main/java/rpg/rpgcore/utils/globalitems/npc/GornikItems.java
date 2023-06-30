package rpg.rpgcore.utils.globalitems.npc;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public enum GornikItems {
    I1("I1", new ItemBuilder(Material.NETHER_STAR).setName("&6&lLegendarny Krysztal Wzmocnienia").setLore(Arrays.asList("&8Posiada niesamowita moc, ktora pozwala na", "&8rozwiniecie swoich umiejetnosci", "&8w dziedzinie gornictwa")).toItemStack()),
    R1("R1", new ItemBuilder(Material.COAL_ORE).setName("&8Ruda Mroku").toItemStack()),
    R2("R2", new ItemBuilder(Material.IRON_ORE).setName("&7Ruda Cyrkonu").toItemStack()),
    R3("R3", new ItemBuilder(Material.GOLD_ORE).setName("&eRuda Blasku").toItemStack()),
    R4("R4", new ItemBuilder(Material.LAPIS_ORE).setName("&1Ruda Szafiru").toItemStack()),
    R5("R5", new ItemBuilder(Material.EMERALD_ORE).setName("&2Ruda Jadeitu").toItemStack()),
    R6("R6", new ItemBuilder(Material.DIAMOND_ORE).setName("&bRuda Tanzanitu").toItemStack()),
    R7("R7", new ItemBuilder(Material.REDSTONE_ORE).setName("&cRuda Rubinu").toItemStack()),
    KILOF("KILOF", new ItemBuilder(Material.STONE_PICKAXE).setName("&6Kilof Gornika").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&6100")).toItemStack()),
    ZMIOTKA_T0("ZMIOTKA_T0", new ItemBuilder(Material.WOOD_SPADE).setName("&8Drewniane Dluto").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&620", "&7Szansa na znalezienie Krysztalu: +&60&7%", "&7Szansa na dodatkowy Krysztal: &60&7%")).toItemStack()),
    ZMIOTKA_T1("ZMIOTKA_T1", new ItemBuilder(Material.STONE_SPADE).setName("&8Kamienne Dluto").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&620", "&7Szansa na znalezienie Krysztalu: +&67.5&7%", "&7Szansa na dodatkowy Krysztal: &67.5&7%")).toItemStack()),
    ZMIOTKA_T2("ZMIOTKA_T2", new ItemBuilder(Material.IRON_SPADE).setName("&8Metalowe Dluto").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&620", "&7Szansa na znalezienie Krysztalu: +&615&7%", "&7Szansa na dodatkowy Krysztal: &615&7%")).toItemStack()),
    ZMIOTKA_T3("ZMIOTKA_T3", new ItemBuilder(Material.GOLD_SPADE).setName("&8Zlote Dluto").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&620", "&7Szansa na znalezienie Krysztalu: +&625&7%", "&7Szansa na dodatkowy Krysztal: &630&7%")).toItemStack()),
    ZMIOTKA_T4("ZMIOTKA_T4", new ItemBuilder(Material.DIAMOND_SPADE).setName("&8Diamentowe Dluto").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&620", "&7Szansa na znalezienie Krysztalu: +&640&7%", "&7Szansa na dodatkowy Krysztal: &650&7%")).toItemStack()),
    WODA("WODA", new ItemBuilder(Material.WATER_BUCKET).setName("&1Wiadro Wody").setLore(Arrays.asList("&8Potrzebne do oczyszczenia &6Rud &8z kamienia,", "&8zeby otrzymac cenne &5Krysztaly")).toItemStack()),
    G1("G1", new ItemBuilder(Material.COAL).setName("&8Krysztal Mroku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G2("G2", new ItemBuilder(Material.IRON_INGOT).setName("&7Krysztal Powietrza").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G3("G3", new ItemBuilder(Material.GOLD_INGOT).setName("&eKrysztal Blasku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G4("G4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Krysztal Wody").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G5("G5", new ItemBuilder(Material.EMERALD).setName("&2Krysztal Lasu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G6("G6", new ItemBuilder(Material.DIAMOND).setName("&bKrysztal Lodu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G7("G7", new ItemBuilder(Material.REDSTONE).setName("&cKrysztal Ognia").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    P1("P1", new ItemBuilder(Material.COAL).setName("&8Polyskujacy Krysztal Mroku").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P2("P2", new ItemBuilder(Material.IRON_INGOT).setName("&7Polyskujacy Krysztal Powietrza").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P3("P3", new ItemBuilder(Material.GOLD_INGOT).setName("&ePolyskujacy Krysztal Blasku").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P4("P4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Polyskujacy Krysztal Wody").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P5("P5", new ItemBuilder(Material.EMERALD).setName("&2Polyskujacy Krysztal Lasu").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P6("P6", new ItemBuilder(Material.DIAMOND).setName("&bPolyskujacy Krysztal Lodu").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    P7("P7", new ItemBuilder(Material.REDSTONE).setName("&cPolyskujacy Krysztal Ognia").setLore(Arrays.asList("&8&oPieknie mieni sie w blasku slonca...")).toItemStack()),
    S1("S1", new ItemBuilder(Material.COAL).setName("&8Lsniacy Krysztal Mroku").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S2("S2", new ItemBuilder(Material.IRON_INGOT).setName("&7Lsniacy Krysztal Powietrza").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S3("S3", new ItemBuilder(Material.GOLD_INGOT).setName("&eLsniacy Krysztal Blasku").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S4("S4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Lsniacy Krysztal Wody").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S5("S5", new ItemBuilder(Material.EMERALD).setName("&2Lsniacy Krysztal Lasu").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S6("S6", new ItemBuilder(Material.DIAMOND).setName("&bLsniacy Krysztal Lodu").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    S7("S7", new ItemBuilder(Material.REDSTONE).setName("&cLsniacy Krysztal Ognia").setLore(Arrays.asList("&8&oPieknie blyszczy sie w blasku slonca...")).toItemStack()),
    C1("C1", new ItemBuilder(Material.COAL).setName("&8Czysty Krysztal Mroku").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack()),
    C2("C2", new ItemBuilder(Material.IRON_INGOT).setName("&7Czysty Krysztal Powietrza").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na buty")).toItemStack()),
    C3("C3", new ItemBuilder(Material.GOLD_INGOT).setName("&eCzysty Krysztal Blasku").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack()),
    C4("C4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Czysty Krysztal Wody").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    C5("C5", new ItemBuilder(Material.EMERALD).setName("&2Czysty Krysztal Lasu").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    C6("C6", new ItemBuilder(Material.DIAMOND).setName("&bCzysty Krysztal Lodu").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    C7("C7", new ItemBuilder(Material.REDSTONE).setName("&cCzysty Krysztal Ognia").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &eszlacheckie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack()),
    W1("W1", new ItemBuilder(Material.COAL).setName("&8Wypolerowany Krysztal Mroku").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack()),
    W2("W2", new ItemBuilder(Material.IRON_INGOT).setName("&7Wypolerowany Krysztal Powietrza").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na buty")).toItemStack()),
    W3("W3", new ItemBuilder(Material.GOLD_INGOT).setName("&eWypolerowany Krysztal Blasku").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack()),
    W4("W4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Wypolerowany Krysztal Wody").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    W5("W5", new ItemBuilder(Material.EMERALD).setName("&2Wypolerowany Krysztal Lasu").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    W6("W6", new ItemBuilder(Material.DIAMOND).setName("&bWypolerowany Krysztal Lodu").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na zbroje")).toItemStack()),
    W7("W7", new ItemBuilder(Material.REDSTONE).setName("&cWypolerowany Krysztal Ognia").setLore(Arrays.asList("&8&oPosiada niesamowite wlasciwosci &6Krolewskie", "&8&oMozliwy do zalozenia na ostrze")).toItemStack());


    private final String name;
    private final ItemStack is;

    GornikItems(String name, ItemStack is) {
        this.name = name;
        this.is = is;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return is.clone();
    }

    public static GornikItems getByName(String name) {
        for (GornikItems gornikItems : GornikItems.values()) {
            if (gornikItems.getName().equalsIgnoreCase(name)) {
                return gornikItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(String name, int amount) {
        ItemStack is = GornikItems.getByName(name).getItemStack().clone();
        is.setAmount(amount);
        return is;
    }

    public static ItemStack getKilof(final String name) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(KILOF.getItemStack().clone());
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("Lvl", 1);
        tag.setInt("Exp", 0);
        tag.setInt("ReqExp", 100);
        tag.setBoolean("Unbreakable", true);
        tag.setString("owner", name);

        nmsStack.setTag(tag);
        final ItemStack newItem = CraftItemStack.asBukkitCopy(nmsStack);
        final ItemMeta meta = newItem.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        final List<String> lore = meta.getLore();
        lore.add("");
        lore.add(Utils.format("&7Wlasciciel: &6" + name));

        meta.setLore(lore);
        newItem.setItemMeta(meta);
        return newItem;
    }

    public static ItemStack getZmiotka(final String tier, final int lvl, final String name) {
        ItemStack is = getByName("ZMIOTKA_" + tier.toUpperCase()).getItemStack().clone();
        final ItemMeta meta = is.getItemMeta();
        final List<String> lore = meta.getLore();
        lore.set(0, Utils.format("&7Poziom: &6" + lvl));
        if (lvl == 50) {
            lore.set(1, Utils.format("&7Exp: &60&7/&6MAX"));
        } else {
            //lore.set(1, Utils.format("&7Exp: &60&7/&6" + GornikDlutoLevels.getExpByLevel(lvl + 1, tier)));
        }
        lore.add("");
        lore.add(Utils.format("&7Wlasciciel: &6" + name));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setLore(Utils.format(lore));

        is.setItemMeta(meta);
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(is);
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("Lvl", lvl);
        tag.setInt("Exp", 0);
//        tag.setInt("ReqExp", GornikDlutoLevels.getExpByLevel(lvl + 1, tier));
        tag.setBoolean("Unbreakable", true);
        tag.setDouble("Chance", Double.parseDouble(Utils.removeColor(is.getItemMeta().getLore().get(2).substring(is.getItemMeta().getLore().get(2).lastIndexOf(" ")).replace("+", "").replace("%", ""))));
        tag.setDouble("DubleDrop", Double.parseDouble(Utils.removeColor(is.getItemMeta().getLore().get(3).substring(is.getItemMeta().getLore().get(2).lastIndexOf(" ")).replace("+", "").replace("%", ""))));
        tag.setString("Tier", tier);
        tag.setString("owner", name);
        nmsStack.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static ItemStack getCompressed(String name, int amount) {
        ItemStack is = getByName(name).getItemStack().clone();
        is.setAmount(amount);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(Utils.format(meta.getDisplayName() + " &8(x64)"));
        is.setItemMeta(meta);
        return is;
    }
}
