package rpg.rpgcore.dodatki.akcesoriaD.helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AkcesoriaDodatHelper {

    public static ItemStack createSzarfa(final int ludize, final int moby, final int lvl, final String... name) {
        return new ItemBuilder(Material.LADDER).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Dodatkowe",
                "&7Silny przeciwko ludziom: &c" + ludize + "%",
                "&7Silny przeciwko potworom: &c" + moby + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("ludzie", ludize).addTagDouble("moby", moby).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createPas(final int defLudzie, final int defMoby, final int lvl, final String... name) {
        return new ItemBuilder(Material.LEASH).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Dodatkowe",
                "&7Defensywa przeciwko ludziom: &c" + defLudzie + "%",
                "&7Defensywa przeciwko potworom: &c" + defMoby + "%",
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("defLudzie", defLudzie).addTagDouble("defMoby", defMoby).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createMedalion(final int srdmg, final int dodatkoweHp, final int lvl, final String... name) {
        return new ItemBuilder(Material.FIREBALL).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Dodatkowe",
                "&7Srednie obrazenia: &c" + srdmg + "%",
                "&7Dodatkowe HP: &c" + dodatkoweHp,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagDouble("srdmg", srdmg).addTagInt("dodatkoweHP", dodatkoweHp).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }

    public static ItemStack createEnergia(final int mDmg, final int def, final int blok, final double przebicie, final int mspeed, final int lvl, final String... name) {
        return new ItemBuilder(Material.MINECART).setName(Arrays.stream(name).map(arg -> arg + " ").collect((Collector) Collectors.joining()).toString()).setLore(Arrays.asList(
                "&7Typ: &cAkcesorium Dodatkowe",
                "&7Zmniejszone Obrazenia: &c" + mDmg + "%",
                "&7Zwiekszona Defensywa: &c" + def + "%",
                "&7Blok Ciosu: &c" + blok + "%",
                "&7Przebicie Pancerza: &c" + przebicie + "%",
                "&7Zmniejszona Predkosc Ruchu: &c" + mspeed,
                " ",
                "&cWymagany poziom: &6" + lvl
        )).addTagInt("mDmg", mDmg).addTagInt("def", def).addTagInt("blok", blok).addTagDouble("przebicie", przebicie).addTagInt("mspeed", mspeed).addTagInt("lvl", lvl).hideFlag().toItemStack().clone();
    }
}
/*

SZARFA:
-Silny na ludzi
-Silny na Potwory

PAS:
-Odpornośc na Ludzi
-Odporność na Potwory

MEDALION
-Średnie Obrażenia
-Złote Serca

ENERGIA
-Zmniejszone Obrażenia
-Zmniejszona Defensywa
-Szansa na Blok
-Szansa na Przebicie
-Zmniejszona szybkośc Ruchu
 */
