package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AkcesoriaHelper {

    private static final List<String> lore = new ArrayList<>();

    public static ItemStack createTarcza(final String name, final int obrona, final int blok, final int dmg, final int wymaganyPoziom) {
        return new ItemBuilder(Material.ITEM_FRAME).setName(name).setLore(Arrays.asList("&3Obrona: &f" + obrona + "%", "&3Blok Ciosu: &f" + blok + "%", "&3Obrazenia: &f" + dmg, "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createMedalion(final String name, final int przeszywka, final int dmg, final int wymaganyPoziom) {
        return new ItemBuilder(Material.FIREBALL).setName(name).setLore(Arrays.asList("&3Przeszycie Bloku: &f" + przeszywka + "%", "&3Obrazenia: &f" + dmg, "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createPas(final String name, final int krytyk, final int sredniDmg, final int wymaganyPoziom) {
        return new ItemBuilder(Material.LEASH).setName(name).setLore(Arrays.asList("&3Szansa na Krytyk: &f" + krytyk + "%", "&3Srednie Obrazenia: &f" + sredniDmg, "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createKolczyki(final String name, final int hp, final int silnyNaLudzi, final int wymaganyPoziom) {
        return new ItemBuilder(Material.HOPPER_MINECART).setName(name).setLore(Arrays.asList("&3Dodatkowe HP: &f" + hp, "&3Silny na Ludzi: &f" + silnyNaLudzi + "%", "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createSygnet(final String name, final int hp, final int silnynamoby, final int wymaganyPoziom) {
        return new ItemBuilder(Material.GOLD_NUGGET).setName(name).setLore(Arrays.asList("&3Dodatkowe HP: &f" + hp, "&3Silny przeciwko Potworom: &f" + silnynamoby + "%",  "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createEnergia(final String name, final int obrona, final int blok, final int sredniDmg, final int wymaganyPoziom) {
        return new ItemBuilder(Material.MINECART).setName(name).setLore(Arrays.asList("&3Obrona: &f" + obrona + "%", "&3Blok Ciosu: &f" + blok + "%", "&3Srednie Obrazenia: &f-" + sredniDmg, "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }

    public static ItemStack createZegarek(final String name, final int odpoNaMoby, final int silnyNaMoby, final int wymaganyPoziom) {
        return new ItemBuilder(Material.WATCH).setName(name).setLore(Arrays.asList("&3Odpornosc na Potwory: &f" + odpoNaMoby + "%", "&3Silny na Potwory: &f" + silnyNaMoby + "%", "&c&oWymagany Poziom: &6" + wymaganyPoziom)).toItemStack().clone();
    }


}
