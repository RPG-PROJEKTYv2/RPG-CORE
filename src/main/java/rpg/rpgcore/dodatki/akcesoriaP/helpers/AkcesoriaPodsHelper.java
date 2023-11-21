package rpg.rpgcore.dodatki.akcesoriaP.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
Akcesoria
KOLCZYKI
-Silny przeciwko ludzią %
-Odpornośc na Ludzi %
-Zmniejszona Szybkość Ruchu %
TARCZA
-Zwiększona Defensywa %
-Szansa Na Blok %
-Dodatkowe Serca xxx
NASZYJNIK
-Dodatkowe DMG xxx
-Szansa na Cios Krytyczny %
-Zmniejszone Obrażenia %
PIERSCIEŃ
-Szansa na przebicie xxx
-Wzmocnienie Ciostu Krytycznego %
-Zwiększona Szybkość Ruchu %
DIADEM
-Średnie Obrażenia %
-Silny na potwory %
-Dodatkowy EXP %
 */

public class AkcesoriaPodsHelper {
    public static ItemStack createTarcza(final int def, final int blok, final int dmg, final int lvl, final String... name) {
        return new ItemBuilder(Material.ITEM_FRAME).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString().trim()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Zwiekszona Defensywa: &c" + def + "%",
                "&7Szansa na Blok: &c" + blok + "%",
                "&7Dodatkowe Obrazenia: &c+" + dmg,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("def", def).addTagDouble("blok", blok).addTagInt("dmg", dmg).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createKolczyki(final int ludzie, final int hp, final int mspeed, final int lvl, final String... name) {
        return new ItemBuilder(Material.HOPPER_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString().trim()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Silny przeciwko ludziom: &c" + ludzie + "%",
                "&7Dodatkowe HP: &c" + hp,
                "&7Zmniejszona Szybkosc Ruchu: &c" + mspeed,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("ludzie", ludzie).addTagDouble("hp", hp).addTagInt("mspeed", mspeed).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createNaszyjnik(final int srDmg, final int kryt, final int odpoLud, final int lvl, final String... name) {
        return new ItemBuilder(Material.STORAGE_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString().trim()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Srednie Obrazenia: &c+" + srDmg + "%",
                "&7Szansa na Cios Krytyczny: &c" + kryt + "%",
                "&7Odpornosc Na Ludzi: &c" + odpoLud + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagInt("srDmg", srDmg).addTagDouble("kryt", kryt).addTagDouble("odpoLud", odpoLud).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createPierscien(final int przeszycie, final int silnyNaMoby, final int speed, final int lvl, final String... name) {
        return new ItemBuilder(Material.EXPLOSIVE_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString().trim()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Szansa na Przeszycie Bloku: &c" + przeszycie + "%",
                "&7Silny Przeciwko Potorom: &c" + silnyNaMoby + "%",
                "&7Zwiekszona Szybkosc Ruchu: &c+" + speed,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("przebicie", przeszycie).addTagDouble("silnyNaMoby", silnyNaMoby).addTagInt("speed", speed).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createDiadem(final int srdmg, final int odpoNaMoby, final double exp, final int lvl, final String... name) {
        return new ItemBuilder(Material.WATCH).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString().trim()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Srednie Obrazenia: &c" + srdmg + "%",
                "&7Odpornosc Na Potwory: &c" + odpoNaMoby + "%",
                "&7Dodatkowy EXP: &c" + exp + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("srdmg", srdmg).addTagDouble("odpoNaMoby", odpoNaMoby).addTagDouble("exp", exp).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }
}
