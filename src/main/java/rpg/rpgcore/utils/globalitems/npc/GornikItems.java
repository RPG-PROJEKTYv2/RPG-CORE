package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.enums.GornikLevels;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.UUID;

public enum GornikItems {
    I1("Ruda_Wegla", new ItemBuilder(Material.COAL_ORE).setName("&8Ruda Wegla").hideFlag().toItemStack()),
    I1_1("Ruda_Wegla x64", new ItemBuilder(Material.COAL_ORE).setName("&8Ruda Wegla &8&l(x64)").hideFlag().toItemStack()),
    I2("Ruda_Zelaza", new ItemBuilder(Material.IRON_ORE).setName("&7Ruda Zelaza").hideFlag().toItemStack()),
    I2_1("Ruda_Zelaza x64", new ItemBuilder(Material.IRON_ORE).setName("&7Ruda Zelaza &8&l(x64)").hideFlag().toItemStack()),
    I3("Ruda_Topazu", new ItemBuilder(Material.GOLD_ORE).setName("&eRuda Topazu").hideFlag().toItemStack()),
    I3_1("Ruda_Topazu x64", new ItemBuilder(Material.GOLD_ORE).setName("&eRuda Topazu &8&l(x64)").hideFlag().toItemStack()),
    I4("Ruda_Tanzanitu", new ItemBuilder(Material.LAPIS_ORE).setName("&1Ruda Tanzanitu").hideFlag().toItemStack()),
    I4_1("Ruda_Tanzanitu x64", new ItemBuilder(Material.LAPIS_ORE).setName("&1Ruda Tanzanitu &8&l(x64)").hideFlag().toItemStack()),
    I5("Ruda_Jadeitu", new ItemBuilder(Material.EMERALD_ORE).setName("&aRuda Jadeitu").hideFlag().toItemStack()),
    I5_1("Ruda_Jadeitu x64", new ItemBuilder(Material.EMERALD_ORE).setName("&aRuda Jadeitu &8&l(x64)").hideFlag().toItemStack()),
    I6("Ruda_Diamentu", new ItemBuilder(Material.DIAMOND_ORE).setName("&bRuda Diamentu").hideFlag().toItemStack()),
    I6_1("Ruda_Diamentu x64", new ItemBuilder(Material.DIAMOND_ORE).setName("&bRuda Diamentu &8&l(x64)").hideFlag().toItemStack()),
    I7("Ruda_Runinu", new ItemBuilder(Material.REDSTONE_ORE).setName("&cRuda Rubinu").hideFlag().toItemStack()),
    I7_1("Ruda_Runinu x64", new ItemBuilder(Material.REDSTONE_ORE).setName("&cRuda Rubinu &8&l(x64)").hideFlag().toItemStack()),
    I8("Skrzynia_Gornika", new ItemBuilder(Material.CHEST).setName("&7Skrzynia Gornika").hideFlag().toItemStack()),
    I9("Helm_Gornika", new ItemBuilder(Material.LEATHER_HELMET).setName("&8Helm Gornika").setLeatherArmorColorHEX(47, 82, 41).setLore(Arrays.asList(
            "&7Dodatkowy Czas: &a+5 min",
            "&7Szansa Na Podwojenie Rudy: &a+3.5%",
            "&7Szansa Na Zakopana Skrzynie: &a+1%",
            " ",
            "&6&lBonus z pelnej zbroi",
            "&7Otrzymujesz permamentny efekt &eHaste I",
            "&7kiedy jestes na Kopalni"
    )).addTagInt("bonusTime", 300_000).addTagDouble("doubleDrop", 3.5).addTagDouble("chestDrop", 1).hideFlag().toItemStack()),
    I10("Zbroja_Gornika", new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("&8Zbroja Gornika").setLeatherArmorColorHEX(47, 82, 41).setLore(Arrays.asList(
            "&7Dodatkowy Czas: &a+8 min",
            "&7Szansa Na Podwojenie Rudy: &a+5%",
            "&7Szansa Na Zakopana Skrzynie: &a+2.5%",
            " ",
            "&6&lBonus z pelnej zbroi",
            "&7Otrzymujesz permamentny efekt &eHaste I",
            "&7kiedy jestes na Kopalni"
    )).addTagInt("bonusTime", 480_000).addTagDouble("doubleDrop", 5).addTagDouble("chestDrop", 2.5).hideFlag().toItemStack()),
    I11("Spodnie_Gornika", new ItemBuilder(Material.LEATHER_LEGGINGS).setName("&8Spodnie Gornika").setLeatherArmorColorHEX(47, 82, 41).setLore(Arrays.asList(
            "&7Dodatkowy Czas: &a+7 min",
            "&7Szansa Na Podwojenie Rudy: &a+4%",
            "&7Szansa Na Zakopana Skrzynie: &a+1.75%",
            " ",
            "&6&lBonus z pelnej zbroi",
            "&7Otrzymujesz permamentny efekt &eHaste I",
            "&7kiedy jestes na Kopalni"
    )).addTagInt("bonusTime", 420_000).addTagDouble("doubleDrop", 4).addTagDouble("chestDrop", 1.75).hideFlag().toItemStack()),
    I12("Buty_Gornika", new ItemBuilder(Material.LEATHER_BOOTS).setName("&8Buty Gornika").setLeatherArmorColorHEX(47, 82, 41).setLore(Arrays.asList(
            "&7Dodatkowy Czas: &a+4 min",
            "&7Szansa Na Podwojenie Rudy: &a+2.5%",
            "&7Szansa Na Zakopana Skrzynie: &a+0.5%",
            " ",
            "&6&lBonus z pelnej zbroi",
            "&7Otrzymujesz permamentny efekt &eHaste I",
            "&7kiedy jestes na Kopalni"
    )).addTagInt("bonusTime", 240_000).addTagDouble("doubleDrop", 2.5).addTagDouble("chestDrop", 0.5).hideFlag().toItemStack());


    private final String id;
    private final ItemStack itemStack;

    GornikItems(final String id, final ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;
    }

    public String getId() {
        return this.id;
    }

    public ItemStack getItemStack() {
        return this.itemStack.clone();
    }


    public static GornikItems getById(final String id) {
        for (final GornikItems gornikItems : values()) {
            if (gornikItems.getId().equals(id)) {
                return gornikItems;
            }
        }
        return null;
    }

    public static ItemStack getItem(final String id, final int amount) {
        final GornikItems gornikItems = getById(id);
        if (gornikItems == null) {
            return null;
        }
        final ItemStack itemStack = gornikItems.getItemStack().clone();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static ItemStack getKilof(final UUID uuid) {
        final int reqExp = GornikLevels.getByLvl(1).getReqExp();
        final User user = RPGCORE.getInstance().getUserManager().find(uuid);
        return new ItemBuilder(Material.STONE_PICKAXE).setName("&6Kilof Gornika").setLore(Arrays.asList(
                "&7Poziom: &61",
                "&7Exp: &60&7/&6" + reqExp,
                "&7Wlasciciel: &6" + user.getName()
        ))
                .addTagString("owner", user.getName())
                .addTagString("owner-uuid", uuid.toString())
                .addTagInt("lvl", 1)
                .addTagInt("exp", 0)
                .addTagInt("reqExp", reqExp)
                .hideFlag()
                .toItemStack().clone();
    }


}
