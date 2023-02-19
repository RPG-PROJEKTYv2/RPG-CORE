package rpg.rpgcore.dodatki.bony.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;

public enum BonType {
    SREDNIE_5("Bon Srednich Obrazen 5%", new ItemBuilder(Material.SIGN).setName("&cBon Srednich Obrazen 5%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    SREDNIE_10("Bon Srednich Obrazen 10%", new ItemBuilder(Material.SIGN).setName("&cBon Srednich Obrazen 10%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    SREDNIE_15("Bon Srednich Obrazen 15%", new ItemBuilder(Material.SIGN).setName("&cBon Srednich Obrazen 15%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),

    DEFENSYWA_5("Bon Sredniej Defensywy 5%", new ItemBuilder(Material.SIGN).setName("&2Bon Sredniej Defensywy 5%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    DEFENSYWA_10("Bon Sredniej Defensywy 10%", new ItemBuilder(Material.SIGN).setName("&2Bon Sredniej Defensywy 10%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    DEFENSYWA_15("Bon Sredniej Defensywy 15%", new ItemBuilder(Material.SIGN).setName("&2Bon Sredniej Defensywy 15%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),

    KRYTYK_5("Bon Szansy Na Cios Krytyczny 5%", new ItemBuilder(Material.SIGN).setName("&5Bon Szansy Na Cios Krytyczny 5%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    KRYTYK_10("Bon Szansy Na Cios Krytyczny 10%", new ItemBuilder(Material.SIGN).setName("&5Bon Szansy Na Cios Krytyczny 10%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    KRYTYK_15("Bon Szansy Na Cios Krytyczny 15%", new ItemBuilder(Material.SIGN).setName("&5Bon Szansy Na Cios Krytyczny 15%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),

    WZM_KRYTYK_10("Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%", new ItemBuilder(Material.SIGN).setName("&6Bon Szansy Na Wzmocnienie Ciosu Krytycznego 10%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    BLOK_20("Bon Szansy Na Blok Ciosu 20%", new ItemBuilder(Material.SIGN).setName("&6Bon Szansy Na Blok Ciosu 20%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    PRZESZYWKA_20("Bon Szansy Na Przeszycie Bloku Ciosu 20%", new ItemBuilder(Material.SIGN).setName("&6Bon Szansy Na Przeszycie Bloku Ciosu 20%").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),

    HP_10("Bon Dodatkowego Zdrowia +10", new ItemBuilder(Material.SIGN).setName("&aBon Dodatkowego Zdrowia +10").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    HP_20("Bon Dodatkowego Zdrowia +20", new ItemBuilder(Material.SIGN).setName("&aBon Dodatkowego Zdrowia +20").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    HP_35("Bon Dodatkowego Zdrowia +35", new ItemBuilder(Material.SIGN).setName("&aBon Dodatkowego Zdrowia +35").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),

    SPEED_25("Bon Zwiekszonej Predkosci Ruchu +25", new ItemBuilder(Material.SIGN).setName("&fBon Zwiekszonej Predkosci Ruchu +25").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    SPEED_50("Bon Zwiekszonej Predkosci Ruchu +50", new ItemBuilder(Material.SIGN).setName("&fBon Zwiekszonej Predkosci Ruchu +50").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    DMG_METINY_2("Bon Zwiekszonych Obrazen W Kamienie Metin +2", new ItemBuilder(Material.SIGN).setName("&dBon Zwiekszonych Obrazen W Kamienie Metin +2").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    DMG_METINY_3("Bon Zwiekszonych Obrazen W Kamienie Metin +3", new ItemBuilder(Material.SIGN).setName("&dBon Zwiekszonych Obrazen W Kamienie Metin +3").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone()),
    DMG_METINY_5("Bon Zwiekszonych Obrazen W Kamienie Metin +5", new ItemBuilder(Material.SIGN).setName("&dBon Zwiekszonych Obrazen W Kamienie Metin +5").setLore(Arrays.asList(" ", "&4&lBONUS")).hideFlag().toItemStack().clone());


    private final String name;
    private final ItemStack bon;

    BonType(final String name, final ItemStack bon) {
        this.name = name;
        this.bon = bon;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getBon() {
        return this.bon;
    }
}
