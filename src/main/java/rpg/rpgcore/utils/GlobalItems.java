package rpg.rpgcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GlobalItems {
    private static final ItemBuilder sledz = new ItemBuilder(Material.RAW_FISH);
    private static final ItemBuilder dorsz = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
    private static final ItemBuilder losos = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
    private static final ItemBuilder krasnopiorka = new ItemBuilder(Material.RAW_FISH, 1, (short) 2);
    private static final ItemBuilder dorszCzarny = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
    private static final ItemBuilder dorada = new ItemBuilder(Material.RAW_FISH);
    private static final ItemBuilder cierniczek = new ItemBuilder(Material.COOKED_FISH);
    private static final ItemBuilder fladra = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
    private static final ItemBuilder karas = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
    private static final ItemBuilder karp = new ItemBuilder(Material.COOKED_FISH);
    private static final ItemBuilder leszcz = new ItemBuilder(Material.COOKED_FISH, 1, (short) 1);
    private static final ItemBuilder makrela = new ItemBuilder(Material.COOKED_FISH);
    private static final ItemBuilder mintaj = new ItemBuilder(Material.COOKED_FISH);
    private static final ItemBuilder okon = new ItemBuilder(Material.RAW_FISH, 1, (short) 3);
    private static final ItemBuilder plotka = new ItemBuilder(Material.RAW_FISH, 1, (short) 1);
    private static final ItemBuilder sakwa = new ItemBuilder(Material.EXP_BOTTLE, 1);
    private static final ItemBuilder nies = new ItemBuilder(Material.DIAMOND_BLOCK);
    private static final ItemBuilder bonNaMagazyn = new ItemBuilder(Material.PAPER);
    
    private static final List<String> lore = new ArrayList<>();

    // NIESY
    public static ItemStack getNies(final String expo, final int amount) {
        nies.setName("&b&lNiesamowity Przedmiot " + expo).addGlowing().setAmount(amount);
        return nies.toItemStack().clone();
    }

    // SAKWY I DROPY Z NICH
    public static ItemStack getSakwa(final int amount) {
        sakwa.setName("&8• &eSakwa &8•").addGlowing().setAmount(amount);

        return sakwa.toItemStack().clone();
    }


    // RYBKI
    public static ItemStack getSledz(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        sledz.setName("&6Sledz").setLore(lore).hideFlag().setAmount(amount);
        return sledz.toItemStack().clone();
    }

    public static ItemStack getDorsz(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        dorsz.setName("&6Dorsz").setLore(lore).hideFlag().setAmount(amount);
        return dorsz.toItemStack().clone();
    }

    public static ItemStack getLosos(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        losos.setName("&6Losos").setLore(lore).hideFlag().setAmount(amount);
        return losos.toItemStack().clone();
    }

    public static ItemStack getKrasnopiorka(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        krasnopiorka.setName("&6Krasnopiorka").setLore(lore).hideFlag().setAmount(amount);
        return krasnopiorka.toItemStack().clone();
    }

    public static ItemStack getDorszCzarny(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        dorszCzarny.setName("&6Dorsz Czarny").setLore(lore).hideFlag().setAmount(amount);
        return dorszCzarny.toItemStack().clone();
    }

    public static ItemStack getDorada(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        dorada.setName("&6Dorada").setLore(lore).hideFlag().setAmount(amount);
        return dorada.toItemStack().clone();
    }

    public static ItemStack getCierniczek(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        cierniczek.setName("&6Cierniczek").setLore(lore).hideFlag().setAmount(amount);
        return cierniczek.toItemStack().clone();
    }

    public static ItemStack getFladra(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        fladra.setName("&6Fladra").setLore(lore).hideFlag().setAmount(amount);
        return fladra.toItemStack().clone();
    }

    public static ItemStack getKaras(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        karas.setName("&6Karas").setLore(lore).hideFlag().setAmount(amount);
        return karas.toItemStack().clone();
    }

    public static ItemStack getKarp(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        karp.setName("&6Karp").setLore(lore).hideFlag().setAmount(amount);
        return karp.toItemStack().clone();
    }

    public static ItemStack getLeszcz(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        leszcz.setName("&6Leszcz").setLore(lore).hideFlag().setAmount(amount);
        return leszcz.toItemStack().clone();
    }

    public static ItemStack getMakrela(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        makrela.setName("&6Makrela").setLore(lore).hideFlag().setAmount(amount);
        return makrela.toItemStack().clone();
    }

    public static ItemStack getMintaj(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        mintaj.setName("&6Mintaj").setLore(lore).hideFlag().setAmount(amount);
        return mintaj.toItemStack().clone();
    }

    public static ItemStack getOkon(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        okon.setName("&6Okon").setLore(lore).hideFlag().setAmount(amount);
        return okon.toItemStack().clone();
    }

    public static ItemStack getPlotka(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &8&n&orybak&r &8&otego potrzebuje");
        plotka.setName("&6Plotka").setLore(lore).hideFlag().setAmount(amount);
        return plotka.toItemStack().clone();
    }

    // RÓŻNE BONY

    public static ItemStack getBonNaMagazyn(final int amount) {
        lore.clear();
        lore.add("&8&oChyba &b&lMagazynier &8tego potrzebuje");

        bonNaMagazyn.setName("&bBon na powiekszenie magazynu").setLore(lore).addGlowing().setAmount(amount);
        return bonNaMagazyn.toItemStack().clone();
    }

}
