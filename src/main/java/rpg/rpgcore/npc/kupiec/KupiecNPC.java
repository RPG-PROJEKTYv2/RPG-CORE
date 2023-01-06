package rpg.rpgcore.npc.kupiec;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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

    public KupiecNPC(RPGCORE rpgcore) {
    }

    // LISTY I MAPY DO SPRAWDZENIA CZY PRZEDMIOT JEST W BAZIE

    // KASA ZAROBIONA W DZEIN
    private double moneyEarnedPerDay = 0.0;

    // ITEMY DO GUI
    private final ItemBuilder glass1 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);
    private final ItemBuilder glass2 = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);
    private final ItemBuilder sell = new ItemBuilder(Material.EMERALD);
    private final ItemBuilder dailyIncome = new ItemBuilder(Material.PAPER);

    // LISTA PRZEDMIOTOW GRACZY
    private final Map<UUID, Multimap<ItemStack, Double>> playerItemStackList = new HashMap<>();
    private final Map<UUID, Double> playerSellValueItems = new HashMap<>();

    public void loadAll() {

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
        lore.add("&7Gracze zarobili:");
        lore.add("&6" + Utils.spaceNumber(String.format("%.2f", moneyEarnedPerDay)) +"&2$");

        dailyIncome.setName("&c&lZarobek od ostatniego Restartu").setLore(lore);

        return dailyIncome.toItemStack().clone();
    }





    public void addMoneyEarnedPerDay(double money) {
        moneyEarnedPerDay += money;
    }

    public void addPlayerItemStack(final UUID uuid, final ItemStack itemStack, final double price) {
        if (playerItemStackList.containsKey(uuid)) {
            playerItemStackList.get(uuid).put(itemStack, price);
        } else {
            final Multimap<ItemStack, Double> itemStackList = ArrayListMultimap.create();
            itemStackList.put(itemStack, price);
            playerItemStackList.put(uuid, itemStackList);
        }
    }
    public void removePlayerItemStack(final UUID uuid, final ItemStack itemStack, final double price) {
        playerItemStackList.get(uuid).remove(itemStack, price);
    }

    public void resetPlayerItemStack(final UUID uuid) {
        playerItemStackList.remove(uuid);
    }

    public Multimap<ItemStack, Double> getPlayerItemStackList(final UUID uuid) {
        if (playerItemStackList.containsKey(uuid)) {
            return playerItemStackList.get(uuid);
        } else {
            return ArrayListMultimap.create();
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
