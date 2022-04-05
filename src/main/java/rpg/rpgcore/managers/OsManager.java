package rpg.rpgcore.managers;

import com.sun.org.apache.bcel.internal.generic.RET;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OsManager {

    private Inventory gui;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<String> itemLore = new ArrayList<>();

    private final HashMap<Integer, Integer> requiredForOsMoby = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsLudzie = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsSakwy = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsNiesy = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsRybak = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsDrwal = new HashMap<>();
    private final HashMap<Integer, Integer> requiredForOsGornik = new HashMap<>();


    private final RPGCORE rpgcore;

    public OsManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void loadAllRequiredOs() {
        System.out.println(rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Moby").getKeys(false));
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Moby").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Moby").getInt("Moby_" + i));
        }
        /*for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gracze").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gracze").getInt("Gracze_" + i));
        }
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Sakwy").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Sakwy").getInt("Sakwy_" + i));
        }
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Niesy").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Niesy").getInt("Niesy_" + i));
        }
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Rybak").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Rybak").getInt("Rybak_" + i));
        }
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Drwal").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Drwal").getInt("Drwal_" + i));
        }
        for (int i = 1; i < rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").getKeys(false).size(); i++) {
            this.requiredForOsMoby.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").getInt("Gornik_" + i));
        }*/
    }


    public Inventory osGuiMain() {
        this.gui = Bukkit.createInventory(null, 3*9, Utils.format("&6&lOsiagniecia"));

        for (int i = 0; i < gui.getSize(); i++) {
            this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
            this.itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(" ");

            this.itemStack.setItemMeta(itemMeta);
            gui.setItem(i, itemStack);
        }


        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z MOBOW
        this.itemStack = new ItemStack(Material.GOLD_SWORD);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Zabite Potwory"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec zabitych potworow"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(10, itemStack);

        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z LUDZI
        this.itemStack = new ItemStack(Material.DIAMOND_SWORD);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Zabici Gracze"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec zabitych graczy"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(11, itemStack);


        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z SAKW
        this.itemStack = new ItemStack(Material.EXP_BOTTLE);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Zebrane Sakwy"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec zebranych sakw"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(12, itemStack);

        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z NIESOW
        this.itemStack = new ItemStack(Material.DIAMOND_BLOCK);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Zebrane Niesamowite Przedmioty"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec zebranych niesamowitych przedmiotow"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(13, itemStack);


        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z RYBAK
        this.itemStack = new ItemStack(Material.FISHING_ROD);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Wylowione Ryby"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec wylowionych ryb"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(14, itemStack);


        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z DRWAL
        this.itemStack = new ItemStack(Material.DIAMOND_AXE);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Wydobyte drzewo"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec wydobytego drzewa"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(15, itemStack);


        this.itemLore.clear();

        //                  ITEM OD OSIAGNIEC Z GORNIKA
        this.itemStack = new ItemStack(Material.GOLD_PICKAXE);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.format("&6Wydobyte Rudy"));

        this.itemLore.add(" ");
        this.itemLore.add(Utils.format("&8&oKliknij, zeby zobaczyc drzewko osiganiec wydobytych rud"));

        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemMeta.setLore(itemLore);

        this.itemStack.setItemMeta(itemMeta);

        gui.setItem(16, itemStack);

        this.itemLore.clear();


        return this.gui;
    }


    public Inventory osMobyGui(final UUID uuid) {
        this.gui = Bukkit.createInventory(null, 2*9, Utils.format("&6&lOsiagniecia - Zabite Potwory"));

        String[] osMobyAccepted = rpgcore.getPlayerManager().getOsMobyAccept(uuid).split(",");
        final int playerMobs = rpgcore.getPlayerManager().getPlayerOsMoby(uuid);

        for (int i = 1; i < 10; i++){
            this.itemLore.clear();
            this.itemStack = new ItemStack(Material.GOLD_SWORD);
            this.itemMeta = this.itemStack.getItemMeta();

            itemMeta.setDisplayName(Utils.format("&6&lZabite Potwory #" + i));
            itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            this.itemLore.add(" ");

            if (osMobyAccepted[i-1].equalsIgnoreCase("false")) {

                this.itemLore.add(Utils.format("&3Postep: &c" + playerMobs + " &3/ &c" + this.requiredForOsMoby.get(i) + " &3(&c" + Utils.procentFormat.format((playerMobs / this.requiredForOsMoby.get(i)) * 100) + " %&3)"));


            } else  {

                this.itemLore.add(Utils.format("&3Postep: &a&lWykonano!"));
            }

            itemMeta.setLore(itemLore);
            this.itemStack.setItemMeta(itemMeta);


            this.gui.setItem(i-1, this.itemStack);
        }

        return this.gui;
    }

}
