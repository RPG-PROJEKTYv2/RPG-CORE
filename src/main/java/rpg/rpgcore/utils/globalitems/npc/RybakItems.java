package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.npc.rybak.enums.WedkaStats;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;

public enum RybakItems {
    I1("I1", new ItemBuilder(Material.RAW_FISH).setName("&6Sledz").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I2("I2", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Dorsz").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I3("I3", new ItemBuilder(Material.RAW_FISH, 1, (short) 2).setName("&6Krasnopiorka").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I4("I4", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Fladra").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I5("I5", new ItemBuilder(Material.RAW_FISH, 1, (short) 1).setName("&6Karas").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I6("I6", new ItemBuilder(Material.COOKED_FISH).setName("&6Karp").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I7("I7", new ItemBuilder(Material.COOKED_FISH, 1, (short) 1).setName("&6Leszcz").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I8("I8", new ItemBuilder(Material.COOKED_FISH).setName("&6Makrela").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I9("I9", new ItemBuilder(Material.COOKED_FISH).setName("&6Mintaj").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I10("I10", new ItemBuilder(Material.RAW_FISH, 1, (short) 3).setName("&6Okon").setLore(Arrays.asList("&8&oChyba &8&orybak&r &8&otego potrzebuje")).hideFlag().toItemStack().clone()),
    I11("I11", new ItemBuilder(Material.CHEST).setName("&a&lKufer Rybacki").setLore(Arrays.asList("&8&oKliknij&r &8&ozeby otworzyc i sprawdzic co skrywa...")).hideFlag().toItemStack().clone());

    private final String name;
    private final ItemStack itemStack;

    RybakItems(String name, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.name = name;
    }

    public static RybakItems getByName(String name) {
        for (RybakItems rank : values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static ItemStack getWedka(final String playerName, final int lvl) {
        final WedkaStats wedkaStats = WedkaStats.getByLvl(lvl);
        assert wedkaStats != null;
        List<String> lore;
        if (lvl == 20) {
            lore = Arrays.asList(
                    "&bPoziom: &4&lMAX",
                    "&bExp: &f0&b/&4&lMAX",
                    "",
                    "&f&lBONUSY",
                    "&bMorskie Szczescie: &f+" + wedkaStats.getMorskieSzczescie(),
                    "&bSzansa Na Podwojny Polow: &f+" + wedkaStats.getPodwojnyDrop() + "%",
                    "&bSzansa Na Kufer Rybacki: &f" + wedkaStats.getKufer() + "%",
                    "&bSzansa Na Niesamowity Przedmiot: &f" + wedkaStats.getNies() + "%",
                    "",
                    "&7Wlasnosc: &6" + playerName);
        } else {
            lore = Arrays.asList(
                    "&bPoziom: &f" + lvl,
                    "&bExp: &f0&b/&f" + wedkaStats.getExp(),
                    "",
                    "&f&lBONUSY",
                    "&bMorskie Szczescie: &f+" + wedkaStats.getMorskieSzczescie(),
                    "&bSzansa Na Podwojny Polow: &f+" + wedkaStats.getPodwojnyDrop() + "%",
                    "&bSzansa Na Kufer Rybacki: &f" + wedkaStats.getKufer() + "%",
                    "&bSzansa Na Niesamowity Przedmiot: &f" + wedkaStats.getNies() + "%",
                    "",
                    "&7Wlasnosc: &6" + playerName);
        }
        return new ItemBuilder(Material.FISHING_ROD).setName("&6Wedka").setLore(lore).addTagInt("lvl", lvl).addTagDouble("exp", 0)
                .addTagDouble("reqExp", wedkaStats.getExp()).addTagDouble("szczescie", wedkaStats.getMorskieSzczescie()).addTagDouble("podwojnyDrop", wedkaStats.getPodwojnyDrop())
                .addTagDouble("kufer", wedkaStats.getKufer()).addTagDouble("nies", wedkaStats.getNies()).addTagString("owner", playerName).hideFlag().toItemStack().clone();
    }
    public ItemStack getRybak() {
        return this.itemStack;
    }
}
