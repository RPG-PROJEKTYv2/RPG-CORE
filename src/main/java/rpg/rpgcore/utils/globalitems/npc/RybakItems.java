package rpg.rpgcore.utils.globalitems.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.npc.rybak.enums.WedkaStats;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;

public enum RybakItems {
    I1("Zpruchniala-Deska", new ItemBuilder(Material.WOOD).setName("&6Zpruchniala Deska").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I2("Podstawa-Masztu", new ItemBuilder(Material.STICK).setName("&ePodstawa Masztu").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I3("Podarty-Maszt", new ItemBuilder(Material.WEB).setName("&fPodarty Maszt").setLore(Arrays.asList("&7Wyglada jak fragment jakiejs &6Starej Lodki&7...", "&7Ciekawe do kogo nalezala")).toItemStack()),
    I4("Stara-Lodka", new ItemBuilder(Material.BOAT).setName("&6Stara Lodka").setLore(Arrays.asList("&7Potrzebna do przedostania sie na &eWyspe Rybacka", " ", "&8Porozmawiaj z &3&lWloczykijem &8trzymajac ja w rece", "&8zeby sie na nia przedostac")).hideFlag().toItemStack()),
    I5("Kufer-Rybacki", new ItemBuilder(Material.CHEST).setName("&aKufer Rybacki").toItemStack().clone());

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
