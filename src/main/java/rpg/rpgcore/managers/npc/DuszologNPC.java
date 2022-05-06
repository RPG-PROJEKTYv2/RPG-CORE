package rpg.rpgcore.managers.npc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DuszologNPC {

    private Inventory gui;

    private final ItemBuilder fillInventory = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ArrayList<String> itemLore = new ArrayList<>();
    private final ItemBuilder craftowanie = new ItemBuilder(Material.BEACON);
    private final ItemBuilder potrzebneitemy = new ItemBuilder(Material.MONSTER_EGG);
    private final ItemBuilder spiswszystkiego = new ItemBuilder(Material.EXP_BOTTLE);
    private final ItemBuilder dodawaniedusz = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder potrzebneitemydodawaniedusz = new ItemBuilder(Material.FERMENTED_SPIDER_EYE);
    private final ItemBuilder opisduszologa = new ItemBuilder(Material.REDSTONE_TORCH_ON);


    private final ItemBuilder testDUSZA1 = new ItemBuilder(Material.PRISMARINE_CRYSTALS, 1);
    private final ItemBuilder testDUSZA2 = new ItemBuilder(Material.INK_SACK, 1, (short) 1);
    private final ItemBuilder testDUSZA3 = new ItemBuilder(Material.BLAZE_POWDER, 1);
    private final ItemBuilder testDUSZA4 = new ItemBuilder(Material.IRON_INGOT, 1);
    private final ItemBuilder testDUSZA5 = new ItemBuilder(Material.NETHER_STAR, 1);
    private final ItemBuilder kamienuzbrojenia = new ItemBuilder(Material.FIREWORK_CHARGE, 1);

    private final ItemBuilder component1 = new ItemBuilder(Material.STONE);
    private final ItemBuilder component2 = new ItemBuilder(Material.COBBLESTONE);
    private final ItemBuilder component3 = new ItemBuilder(Material.IRON_INGOT);

    final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();
    List<String> testlore = new ArrayList<>();

    public void craftowanieDUSZ(final Player player) {
        if (player.getInventory().containsAtLeast(component1.toItemStack(), 1) && player.getInventory().containsAtLeast(component2.toItemStack(), 1)
        && player.getInventory().containsAtLeast(component3.toItemStack(), 1)) {
            itemMapToRemove.put(0, component1.toItemStack());
            itemMapToRemove.put(1, component2.toItemStack());
            itemMapToRemove.put(2, component3.toItemStack());
            player.getInventory().removeItem(itemMapToRemove.get(0));
            player.getInventory().removeItem(itemMapToRemove.get(1));
            player.getInventory().removeItem(itemMapToRemove.get(2));

            Random random = new Random();
            int szansa = random.nextInt(1000) + 1;
            if (szansa <= 10) {
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                this.sendBroadcast(player, "&f&lDUSZE &5&LDROPU &c0.0001%");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                dajDROPU(player);
            } else if (szansa <= 60) {
                dajODBICIA(player);
            } else if (szansa <= 160) {
                dajOBRONY(player);
            } else if (szansa <= 260) {
                dajOBRAZEN(player);
            } else if (szansa <= 410) {
                dajZYCIA(player);
            } else {
                this.sendBroadcast(player, "&4&lPUSTKE");
            }
        } else {
            player.sendMessage(Utils.format("&a&lDuszolog &8>> &7Nie posiadasz wszystkich przedmiotow potrzebnych do tego craftingu."));
        }
    }

    public void craftowanieKAMIENUZBROJENIA(final Player player) {
        if (player.getInventory().containsAtLeast(component1.toItemStack(), 1) && player.getInventory().containsAtLeast(component2.toItemStack(), 1)
                && player.getInventory().containsAtLeast(component3.toItemStack(), 1)) {
            itemMapToRemove.put(0, component1.toItemStack());
            itemMapToRemove.put(1, component2.toItemStack());
            itemMapToRemove.put(2, component3.toItemStack());
            player.getInventory().removeItem(itemMapToRemove.get(0));
            player.getInventory().removeItem(itemMapToRemove.get(1));
            player.getInventory().removeItem(itemMapToRemove.get(2));
            Random random = new Random();
            int szansa = random.nextInt(10) + 1;
            if (szansa <= 5) {
                player.sendMessage(Utils.format("&a&lDuszolog &8>> &cWytworzyles &3Kamien &bUzbrojenia &cgratulacje!"));
                dajKAMIENUZBROJENIA(player);
            } else {
                player.sendMessage(Utils.format("&a&lDuszolog &8>> &cNiestety nie udalo ci sie wytworzyc &3Kamienia &bUzbrojenia&c."));
            }
        } else {
            player.sendMessage(Utils.format("&a&lDuszolog &8>> &7Nie posiadasz wszystkich przedmiotow potrzebnych do tego craftingu."));
        }
    }

    public Inventory duszologMAIN() {
        this.gui = Bukkit.createInventory(null, 3 * 9, Utils.format("&a&lDuszolog"));

        fillInventory.setName(" ");
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillInventory.toItemStack());
        }
        this.itemLore.clear();

        // craftowanie

        craftowanie.setName("&bCraftowanie: &f&lDusza &cx1");
        this.itemLore.add(" ");
        this.itemLore.add("&8>> &eKliknij aby stworzyc losowa &f&ldusze&e!");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na dropniecie duszy:");
        this.itemLore.add("&8* &bOdbicia: &c5%");
        this.itemLore.add("&8* &cZycia: &c15%");
        this.itemLore.add("&8* &4Obrazen: &c10%");
        this.itemLore.add("&8* &aObrony: &c10%");
        this.itemLore.add("&8* &5Dropu: &c0.01%");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na inne:");
        this.itemLore.add("&8* &cPustka: &c50%");
        this.itemLore.add(" ");

        craftowanie.addGlowing();
        craftowanie.setLore(itemLore);
        gui.setItem(10, craftowanie.toItemStack());
        this.itemLore.clear();

        // potrzebne itemy

        potrzebneitemy.setName("&aPotrzebne przedmioty");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eLista itemow na crafting &fduszy&e:");
        this.itemLore.add("&8>> &f1. &6Stone &cx1&8.");
        this.itemLore.add("&8>> &f2. &6Cobblestone &cx1&8.");
        this.itemLore.add("&8>> &f3. &6Sztabka zelaza &cx1&8.");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eLista itemow na crafting &3kamien &buzbrojenia&e:");
        this.itemLore.add("&8>> &f1. &6Stone &cx1&8.");
        this.itemLore.add("&8>> &f2. &6Cobblestone &cx1&8.");
        this.itemLore.add("&8>> &f3. &6Sztabka zelaza &cx1&8.");
        this.itemLore.add(" ");
        potrzebneitemy.addGlowing();
        potrzebneitemy.setLore(itemLore);
        gui.setItem(14, potrzebneitemy.toItemStack());
        this.itemLore.clear();

        // spis wszystkiego

        spiswszystkiego.setName("&cOpis &fDusz");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eWszystkie dusze:");
        this.itemLore.add("&8>> &f&lDusza &bOdbicia: &8( &c1% &f- &c5% &8)");
        this.itemLore.add("&8>> &f&lDusza &cZycia: &8( &c+1 &f- &c+15 &8)");
        this.itemLore.add("&8>> &f&lDusza &4Obrazen: &8( &c1% &f- &c10% &8)");
        this.itemLore.add("&8>> &f&lDusza &aObrony: &8( &c1% &f- &c10% &8)");
        this.itemLore.add("&8>> &f&lDusza &5Dropu: &c0.0001% &8|| &4&lARTEFAKT");
        this.itemLore.add(" ");
        spiswszystkiego.addGlowing();
        spiswszystkiego.setLore(itemLore);
        gui.setItem(16, spiswszystkiego.toItemStack());
        this.itemLore.clear();

        // dodawanie dusz

        dodawaniedusz.setName("&3Dodawanie &f&lDusz &3do ekwipunku");
        this.itemLore.add(" ");
        this.itemLore.add("&8>> &eKliknij aby dodac &fdusze &edo twojego ekwipunku");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eWymagania:");
        this.itemLore.add("&8* &6Dodany &3Kamien &bUzbrojenia &6do ekwipunku");
        this.itemLore.add(" ");
        dodawaniedusz.addGlowing();
        dodawaniedusz.setLore(itemLore);
        gui.setItem(11, dodawaniedusz.toItemStack());
        this.itemLore.clear();

        // dodawanie miejsca na dusze

        potrzebneitemydodawaniedusz.setName("&bCraftowanie: &3Kamien &bUzbrojenia &cx1");
        this.itemLore.add(" ");
        this.itemLore.add("&8>> &eKliknij aby stworzyc &3kamien &buzbrojenia&e!");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na drop:");
        this.itemLore.add("&8* &3Kamien &bUzbrojenia: &c50%");
        this.itemLore.add(" ");
        this.itemLore.add("&8[ &e>> &8] &eSzanse na inne:");
        this.itemLore.add("&8* &cPustka: &c50%");
        this.itemLore.add(" ");
        potrzebneitemydodawaniedusz.addGlowing();
        potrzebneitemydodawaniedusz.setLore(itemLore);
        gui.setItem(13, potrzebneitemydodawaniedusz.toItemStack());
        this.itemLore.clear();

        // opis duszologa

        opisduszologa.setName("&8* &9Informacje &8*");
        this.itemLore.add(" ");
        this.itemLore.add("&8* &eTutaj mozesz stworzyc dusze, ktore");
        this.itemLore.add("&8* &edodasz do seta w zakladce 'dodawanie'");
        this.itemLore.add("&8* &eaby dodac dusze do seta musisz pierwsze");
        this.itemLore.add("&8* &ewytworzyc na nie miejsce w zakladce 'dodawanie miejsca'");
        this.itemLore.add(" ");
        opisduszologa.addGlowing();
        opisduszologa.setLore(itemLore);
        gui.setItem(26, opisduszologa.toItemStack());
        this.itemLore.clear();

        return this.gui;
    }

    public Inventory dodawanieDUSZ() {
        this.gui = Bukkit.createInventory(null, InventoryType.ANVIL, Utils.format("&3Dodawanie &f&lDusz"));

        this.itemLore.clear();
        opisduszologa.setName("test");
        gui.setItem(0, opisduszologa.toItemStack());

        this.itemLore.clear();


        return this.gui;
    }

    private void dajZYCIA(final Player player) {

        Random randomZYCIA = new Random();
        int szansaZYCIA  = randomZYCIA.nextInt(15) + 1;

        testlore.clear();
        testDUSZA2.setName("&8* &f&lDusza &cZycia &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowego zdrowia: &c+" + szansaZYCIA);
        testlore.add(" ");
        testDUSZA2.addGlowing();
        testDUSZA2.setLore(testlore);
        player.getInventory().addItem(testDUSZA2.toItemStack());
        this.sendBroadcast(player, "&f&lDUSZE &c&lZYCIA &c+" + szansaZYCIA);
    }
    private void dajOBRAZEN(final Player player) {

        Random randomOBRAZEN = new Random();
        int szansaOBRAZEN  = randomOBRAZEN.nextInt(10) + 1;

        testlore.clear();
        testDUSZA3.setName("&8* &f&lDusza &4Obrazen &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowych obrazen: &c" + szansaOBRAZEN + "&c%");
        testlore.add(" ");
        testDUSZA3.addGlowing();
        testDUSZA3.setLore(testlore);
        player.getInventory().addItem(testDUSZA3.toItemStack());
        this.sendBroadcast(player, "&f&lDUSZE &4&lOBRAZEN &c" + szansaOBRAZEN + "&c%");
    }
    private void dajOBRONY(final Player player) {

        Random randomOBRONY = new Random();
        int szansaOBRONY  = randomOBRONY.nextInt(10) + 1;

        testlore.clear();
        testDUSZA4.setName("&8* &f&lDusza &aObrony &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowej obrony: &c" + szansaOBRONY + "%");
        testlore.add(" ");
        testDUSZA4.addGlowing();
        testDUSZA4.setLore(testlore);
        player.getInventory().addItem(testDUSZA4.toItemStack());
        this.sendBroadcast(player, "&f&lDUSZE &a&lOBRONY &c" + szansaOBRONY + "&c%");
    }
    private void dajODBICIA(final Player player) {

        Random randomODBICIA = new Random();
        int szansaODBICIA  = randomODBICIA.nextInt(5) + 1;

        testlore.clear();
        testDUSZA1.setName("&8* &f&lDusza &bOdbicia &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus odbicia ciosu: &c" + szansaODBICIA + "%");
        testlore.add(" ");
        testDUSZA1.addGlowing();
        testDUSZA1.setLore(testlore);
        player.getInventory().addItem(testDUSZA1.toItemStack());
        this.sendBroadcast(player, "&f&lDUSZE &b&lODBICIA &c" + szansaODBICIA + "&c%");
    }
    private void dajDROPU(final Player player) {
        testlore.clear();
        testDUSZA5.setName("&8* &f&lDusza &5Dropu &8*");
        testlore.add(" ");
        testlore.add("&8>> &eBonus dodatkowego dropu '&e&lSEJF Z BONAMI&e'");
        testlore.add("&8>> &eSzansa: &c0.0001%!");
        testlore.add("&8>> &4&lARTEFAKT");
        testlore.add(" ");
        testDUSZA5.addGlowing();
        testDUSZA5.setLore(testlore);
        player.getInventory().addItem(testDUSZA5.toItemStack());
    }
    public void dajKAMIENUZBROJENIA(final Player player) {
        testlore.clear();
        kamienuzbrojenia.setName("&3Kamien &bUzbrojenia");
        testlore.add(" ");
        testlore.add("&8>> &eKliknij PPM aby dodac kamien do ekwipunku");
        testlore.add(" ");
        testlore.add("&8* &7Kamien ten pozwoli ci zrobic miejsce na");
        testlore.add("&7twoim secie do ktorego przypiszesz dusze");
        testlore.add("&7wytworzone u duszologa.");
        testlore.add(" ");
        kamienuzbrojenia.addGlowing();
        kamienuzbrojenia.setLore(testlore);
        player.getInventory().addItem(kamienuzbrojenia.toItemStack());
    }

    private void sendBroadcast(final Player player, final String nazwaDuszy) {
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(Utils.format("&8[ &e>> &8] &a&lDuszolog &8[ &e<< &8]"));
        Bukkit.broadcastMessage(Utils.format("&cGracz &e" + player.getName() + " &cwytworzyl " + nazwaDuszy + "&c!"));
        Bukkit.broadcastMessage(Utils.format("&8>> &4&lGRATULACJE!!!"));
        Bukkit.broadcastMessage(" ");
    }
}
