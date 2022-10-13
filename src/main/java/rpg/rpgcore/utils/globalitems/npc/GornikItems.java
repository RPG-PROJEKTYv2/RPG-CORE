package rpg.rpgcore.utils.globalitems.npc;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum GornikItems {
    I1("I1", new ItemBuilder(Material.NETHER_STAR).setName("&6&lLegendarny Krysztal Wzmocnienia").setLore(Arrays.asList("&8Posiada niesamowita moc, ktora pozwala na", "&8rozwiniecie swoich umiejetnosci", "&8w dziedzinie gornictwa")).toItemStack()),
    G1("G1", new ItemBuilder(Material.COAL).setName("&0Krysztal Mroku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G2("G2", new ItemBuilder(Material.IRON_INGOT).setName("&7Krysztal Powietrza").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G3("G3", new ItemBuilder(Material.GOLD_INGOT).setName("&eKrysztal Blasku").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G4("G4", new ItemBuilder(Material.INK_SACK, 1, (short) 4).setName("&1Krysztal Wody").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G5("G5", new ItemBuilder(Material.EMERALD).setName("&2Krysztal Lasu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G6("G6", new ItemBuilder(Material.DIAMOND).setName("&bKrysztal Lodu").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    G7("G7", new ItemBuilder(Material.REDSTONE).setName("&cKrysztal Ognia").setLore(Arrays.asList("&8Chyba Gornik tego potrzebuje")).toItemStack()),
    R1("R1", new ItemBuilder(Material.COAL_ORE).setName("&0Ruda Mroku").toItemStack()),
    R2("R2", new ItemBuilder(Material.IRON_ORE).setName("&7Ruda Cyrkonu").toItemStack()),
    R3("R3", new ItemBuilder(Material.GOLD_ORE).setName("&eRuda Blasku").toItemStack()),
    R4("R4", new ItemBuilder(Material.LAPIS_ORE).setName("&1Ruda Szafiru").toItemStack()),
    R5("R5", new ItemBuilder(Material.EMERALD_ORE).setName("&2Ruda Jadeitu").toItemStack()),
    R6("R6", new ItemBuilder(Material.DIAMOND_ORE).setName("&bRuda Tanzanitu").toItemStack()),
    R7("R7", new ItemBuilder(Material.REDSTONE_ORE).setName("&cRuda Rubinu").toItemStack()),
    KILOF("KILOF", new ItemBuilder(Material.STONE_PICKAXE).setName("&6Kilof Gornika").setLore(Arrays.asList("&7Poziom: &61", "&7Exp: &60&7/&6100")).toItemStack());
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
        ItemStack is = GornikItems.getByName(name).getItemStack();
        is.setAmount(amount);
        return is;
    }

    public static ItemStack getKilof() {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(KILOF.getItemStack());
        NBTTagCompound tag = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        tag.setInt("Lvl", 1);
        tag.setInt("Exp", 0);
        tag.setInt("ReqExp", 100);
        tag.setBoolean("Unbreakable", true);

        nmsStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }
}
