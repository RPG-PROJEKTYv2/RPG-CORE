package rpg.rpgcore.npc.kupiec;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class KupiecNPC {

    private final RPGCORE rpgcore;

    public KupiecNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    // LISTY I MAPY DO SPRAWDZENIA CZY PRZEDMIOT JEST W BAZIE
    private final List<Material> itemStackList = new ArrayList<>();
    private final Map<String, Double> itemPriceMap = new HashMap<>();

    // KASA ZAROBIONA W DZEIN
    private double moneyEarnedPerDay = 0.0;

    // ITEMY DO GUI
    private final ItemBuilder glass1 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder glass2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);
    private final ItemBuilder sell = new ItemBuilder(Material.EMERALD);
    private final ItemBuilder dailyIncome = new ItemBuilder(Material.PAPER);

    // LISTA PRZEDMIOTOW GRACZY
    private final Map<UUID, List<ItemStack>> playerItemStackList = new HashMap<>();
    private final Map<UUID, Double> playerSellValueItems = new HashMap<>();

    public void loadAll() {
        itemStackList.add(Material.LEATHER_CHESTPLATE);
        itemStackList.add(Material.LEATHER_HELMET);
        itemStackList.add(Material.LEATHER_LEGGINGS);
        itemStackList.add(Material.LEATHER_BOOTS);
        itemPriceMap.put("Helm Najemnika", 10.0);
        itemPriceMap.put("Zbroja Najemnika", 10.0);
        itemPriceMap.put("Spodnie Najemnika", 10.0);
        itemPriceMap.put("Buty Najemnika", 10.0);
        /*final String[] listOfMaterials = rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getString("Item_List").replaceAll(" ", "").split(",");

        for (String oneMaterial : listOfMaterials) {
            itemStackList.add(Material.getMaterial(oneMaterial.toUpperCase(Locale.ROOT)));
            if (rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").getConfigurationSection(oneMaterial.toUpperCase(Locale.ROOT)) != null) {
                Set<String> listOfItemNames = rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").getConfigurationSection(oneMaterial.toUpperCase(Locale.ROOT)).getKeys(false);
                for (String singleItemName : listOfItemNames) {
                    itemPriceMap.put(singleItemName, rpgcore.getConfig().getConfigurationSection("Kupiec_Item_List").getConfigurationSection("Item_Price_List").getConfigurationSection(oneMaterial).getConfigurationSection(singleItemName).getDouble("Price"));
                }
            }
        }*/
    }


    public void openKupiecInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, "Kupiec");

        glass1.setName(" ");
        glass2.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, glass1.toItemStack());
            } else {
                gui.setItem(i, glass2.toItemStack());
            }
        }

        for (int i = 10; i < 45; i++) {
            if (i < 17 || (i > 18 && i < 26) || (i > 27 && i < 35) || (i > 36 && i < 44)) {
                gui.setItem(i, new ItemStack(Material.AIR));
            }
        }

        gui.setItem(4, getDailyIncomeItem());

        gui.setItem(49, getSellItem(0.00));

        player.openInventory(gui);

    }

    public boolean checkIfItemIsInLists(final ItemStack itemStack) {
        if (itemStackList.contains(itemStack.getType())) {
            return itemPriceMap.containsKey(Utils.removeColor(itemStack.getItemMeta().getDisplayName()));
        } else {
            return false;
        }
    }

    public double getItemPrice(final ItemStack itemStack) {
        return itemPriceMap.get(Utils.removeColor(itemStack.getItemMeta().getDisplayName()));
    }

    public ItemStack getSellItem(final double newSellStackCash) {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("&7Sprzedaj swoje przedmioty za:");
        lore.add("&6" + Utils.spaceNumber(String.format("%.2f", newSellStackCash)) +"&2$");

        sell.setName("&6&lSprzedaj").setLore(lore);
        return sell.toItemStack().clone();
    }

    public ItemStack getDailyIncomeItem() {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("&7Gracze zarobili dzisiaj:");
        lore.add("&6" + Utils.spaceNumber(String.format("%.2f", moneyEarnedPerDay)) +"&2$");

        dailyIncome.setName("&c&lDzienny zarobek").setLore(lore);

        return dailyIncome.toItemStack().clone();
    }





    public String getMoneyEarnedPerDay() {
        return String.format("%.2f",moneyEarnedPerDay);
    }

    public void resetMoneyEarnedPerDay() {
        moneyEarnedPerDay = 0.0;
    }

    public void addMoneyEarnedPerDay(double money) {
        moneyEarnedPerDay += money;
    }

    public void addPlayerItemStack(final UUID uuid, final ItemStack itemStack) {
        if (playerItemStackList.containsKey(uuid)) {
            playerItemStackList.get(uuid).add(itemStack);
        } else {
            final List<ItemStack> itemStackList = new ArrayList<>();
            itemStackList.add(itemStack);
            playerItemStackList.put(uuid, itemStackList);
        }
    }
    public void removePlayerItemStack(final UUID uuid, final ItemStack itemStack) {
        playerItemStackList.get(uuid).remove(itemStack);
    }

    public void resetPlayerItemStack(final UUID uuid) {
        playerItemStackList.remove(uuid);
    }

    public List<ItemStack> getPlayerItemStackList(final UUID uuid) {
        if (playerItemStackList.containsKey(uuid)) {
            return playerItemStackList.get(uuid);
        } else {
            return new ArrayList<>();
        }
    }

    public void addPlayerSellValueItems(final UUID uuid, final double sellValue) {
        if (playerSellValueItems.containsKey(uuid)) {
            playerSellValueItems.put(uuid, playerSellValueItems.get(uuid) + sellValue);
        } else {
            playerSellValueItems.put(uuid, sellValue);
        }
    }

    public void removePlayerSellValueItems(final UUID uuid, final double sellValue) {
        if (playerSellValueItems.containsKey(uuid)) {
            playerSellValueItems.put(uuid, playerSellValueItems.get(uuid) - sellValue);
        }
    }

    public double getPlayerSellValueItems(final UUID uuid) {
        if (playerSellValueItems.containsKey(uuid)) {
            return playerSellValueItems.get(uuid);
        } else {
            playerSellValueItems.put(uuid, 0.0);
            return 0.0;
        }
    }

    public void resetPlayerSellValueItems(final UUID uuid) {
        if (playerSellValueItems.containsKey(uuid)) {
            playerSellValueItems.put(uuid, 0.0);
        }
    }

}
