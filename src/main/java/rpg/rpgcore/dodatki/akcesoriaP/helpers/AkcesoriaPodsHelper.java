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
    public static ItemStack createTarcza(final double def, final double blok, final int hp, final int lvl, final String... name) {
        return new ItemBuilder(Material.ITEM_FRAME).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Zwiekszona Defensywa: &c" + def + "%",
                "&7Szansa na Blok: &c" + blok + "%",
                "&7Dodatkowe Serca: &c+" + hp,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("def", def).addTagDouble("blok", blok).addTagInt("hp", hp).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createKolczyki(final double ludzie, final double odpo, final int mspeed, final int lvl, final String... name) {
        return new ItemBuilder(Material.HOPPER_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Silny przeciwko ludziom: &c" + ludzie + "%",
                "&7Odpornosc na Ludzi: &c" + odpo + "%",
                "&7Zmniejszona Szybkosc Ruchu: &c" + mspeed,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("ludzie", ludzie).addTagDouble("odpo", odpo).addTagInt("mspeed", mspeed).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createNaszyjnik(final int ddmg, final double kryt, final double srdmg, final int lvl, final String... name) {
        return new ItemBuilder(Material.STORAGE_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Dodatkowe Obrazenia: &c+" + ddmg,
                "&7Szansa na Cios Krytyczny: &c" + kryt + "%",
                "&7Zwiekszone Obrazenia: &c" + srdmg + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagInt("ddmg", ddmg).addTagDouble("kryt", kryt).addTagDouble("srdmg", srdmg).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createPierscien(final double przeszycie, final double kryt, final int speed, final int lvl, final String... name) {
        return new ItemBuilder(Material.EXPLOSIVE_MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Szansa na Przeszycie Bloku: &c" + przeszycie + "%",
                "&7Wzmocnienie Ciosu Krytycznego: &c" + kryt + "%",
                "&7Zwiekszona Szybkosc Ruchu: &c+" + speed,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("przebicie", przeszycie).addTagDouble("wkryt", kryt).addTagInt("speed", speed).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createDiadem(final double srdmg, final double potwory, final double exp, final int lvl, final String... name) {
        return new ItemBuilder(Material.WATCH).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Podstawowe",
                "&7Srednie Obrazenia: &c" + srdmg + "%",
                "&7Silny przeciwko potworom: &c" + potwory + "%",
                "&7Dodatkowy EXP: &c" + exp + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("srdmg", srdmg).addTagDouble("potwory", potwory).addTagDouble("exp", exp).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }
}
