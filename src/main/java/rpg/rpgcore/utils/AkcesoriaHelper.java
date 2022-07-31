package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AkcesoriaHelper {

    private static final List<String> lore = new ArrayList<>();

    public static ItemStack createTarcza(final String name, final double obrona, final double blok, final int dmg, final int wymaganyPoziom) {
        final ItemBuilder tarcza = new ItemBuilder(Material.ITEM_FRAME);
        lore.clear();
        tarcza.setName(name);
        if (String.valueOf(obrona).contains(".0")) {
            lore.add("&3Obrona: &f" + String.format("%.0f", obrona) + "%");
        } else {
            lore.add("&3Obrona: &f" + obrona + "%");
        }
        if (String.valueOf(blok).contains(".0")) {
            lore.add("&3Blok Ciosu: &f" + String.format("%.0f", blok) + "%");
        } else {
            lore.add("&3Blok Ciosu: &f" + blok + "%");
        }
        lore.add("&3Obrazenia: &f" + dmg);
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        tarcza.setLore(lore);
        return tarcza.toItemStack().clone();
    }

    public static ItemStack createNaszyjnik(final String name, final double przeszywka, final int dmg, final int wymaganyPoziom) {
        final ItemBuilder naszyjnik = new ItemBuilder(Material.STORAGE_MINECART);
        lore.clear();
        naszyjnik.setName(name);
        if (String.valueOf(przeszywka).contains(".0")) {
            lore.add("&3Przeszycie Bloku: &f" + String.format("%.0f", przeszywka) + "%");
        } else  {
            lore.add("&3Przeszycie Bloku: &f" + przeszywka + "%");
        }
        lore.add("&3Obrazenia: &f" + dmg);
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        naszyjnik.setLore(lore);
        return naszyjnik.toItemStack().clone();
    }

    public static ItemStack createBransoleta(final String name, final double krytyk, final double sredniDmg, final int wymaganyPoziom) {
        final ItemBuilder bransoleta = new ItemBuilder(Material.POWERED_MINECART);
        lore.clear();
        bransoleta.setName(name);
        if (String.valueOf(krytyk).contains(".0")) {
            lore.add("&3Cios Krytyczny: &f" + String.format("%.0f", krytyk) + "%");
        } else {
            lore.add("&3Cios Krytyczny: &f" + krytyk + "%");
        }
        if (String.valueOf(sredniDmg).contains(".0")) {
            lore.add("&3Srednie Obrazenia: &f" + String.format("%.0f", sredniDmg));
        } else {
            lore.add("&3Srednie Obrazenia: &f" + sredniDmg);
        }
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        bransoleta.setLore(lore);
        return bransoleta.toItemStack().clone();
    }

    public static ItemStack createKolczyki(final String name, final int hp, final double silnyNaLudzi, final int wymaganyPoziom) {
        final ItemBuilder kolczyki = new ItemBuilder(Material.HOPPER_MINECART);
        lore.clear();
        kolczyki.setName(name);
        lore.add("&3Dodatkowe HP: &f" + hp);
        if (String.valueOf(silnyNaLudzi).contains(".0")) {
            lore.add("&3Silny przeciwko Ludziom: &f" + String.format("%.0f",silnyNaLudzi) + "%");
        } else {
            lore.add("&3Silny przeciwko Ludziom: &f" + silnyNaLudzi + "%");
        }
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        kolczyki.setLore(lore);
        return kolczyki.toItemStack().clone();
    }

    public static ItemStack createPierscien(final String name, final double blok, final int hp, final int wymaganyPoziom) {
        final ItemBuilder pierscien = new ItemBuilder(Material.EXPLOSIVE_MINECART);
        lore.clear();
        pierscien.setName(name);
        if (String.valueOf(blok).contains(".0")) {
            lore.add("&3Blok Ciosu: &f" + String.format("%.0f", blok) + "%");
        } else {
            lore.add("&3Blok Ciosu: &f" + blok + "%");
        }
        lore.add("&3Dodatkowe HP: &f" + hp);
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        pierscien.setLore(lore);
        return pierscien.toItemStack().clone();
    }

    public static ItemStack createEnergia(final String name, final double obrona, final double blok, final double sredniDmg, final int wymaganyPoziom) {
        final ItemBuilder energia = new ItemBuilder(Material.MINECART);
        lore.clear();
        energia.setName(name);
        if (String.valueOf(obrona).contains(".0")) {
            lore.add("&3Obrona: &f" + String.format("%.0f", obrona) + "%");
        } else {
            lore.add("&3Obrona: &f" + obrona + "%");
        }
        if (String.valueOf(blok).contains(".0")) {
            lore.add("&3Blok Ciosu: &f" + String.format("%.0f", blok) + "%");
        } else {
            lore.add("&3Blok Ciosu: &f" + blok + "%");
        }
        if (String.valueOf(sredniDmg).contains(".0")) {
            lore.add("&3Srednie Obrazenia: &f" + String.format("%.0f", sredniDmg) + "%");
        } else {
            lore.add("&3Srednie Obrazenia: &f" + sredniDmg + "%");
        }
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        energia.setLore(lore);
        return energia.toItemStack().clone();
    }

    public static ItemStack createZegarek(final String name, final int dmg, final double silnyNaMoby, final double obrona, final int wymaganyPoziom) {
        final ItemBuilder zegarek = new ItemBuilder(Material.WATCH);
        lore.clear();
        zegarek.setName(name);
        lore.add("&3Obrazenia: &f" + dmg);
        if (String.valueOf(silnyNaMoby).contains(".0")) {
            lore.add("&3Silny przeciwko Potworom: &f" + String.format("%.0f",silnyNaMoby) + "%");
        } else {
            lore.add("&3Silny przeciwko Potworom: &f" + silnyNaMoby + "%");
        }
        if (String.valueOf(obrona).contains(".0")) {
            lore.add("&3Obrona: &f-" + String.format("%.0f",obrona) + "%");
        } else {
            lore.add("&3Obrona: &f-" + obrona + "%");
        }
        lore.add(" ");
        lore.add("&c&oWymagany Poziom: &6" + wymaganyPoziom);
        zegarek.setLore(lore);
        return zegarek.toItemStack().clone();
    }


}
