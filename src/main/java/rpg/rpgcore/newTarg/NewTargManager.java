package rpg.rpgcore.newTarg;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.TargUtils;
import rpg.rpgcore.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewTargManager {

    private final RPGCORE rpgcore;

    public NewTargManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    // WAZNE MAPY I LISTY

    private final Map<UUID, List<ItemStack>> playerTargItems = new HashMap<>();
    private final List<ItemStack> allItems = new ArrayList<>();
    private final List<UUID> wystawia = new ArrayList<>();


    // ITEMY DO GUI, ITD.
    private final ItemBuilder szkloGracze = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);
    private final ItemBuilder szkloSety = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 1);
    private final ItemBuilder szkloBronie = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 3);
    private final ItemBuilder szkloAkcesoria = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder szkloMaterialy = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4);
    private final ItemBuilder szkloOther = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 2);

    private final ItemBuilder goBack = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goLeft = new ItemBuilder(Material.ARROW);
    private final ItemBuilder goRight = new ItemBuilder(Material.ARROW);

    private final ItemBuilder sort = new ItemBuilder(Material.HOPPER);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15);

    public void openNewTargInventory(final Player player, final int category, final int sort, final int page) {

        final ItemBuilder gracze = new ItemBuilder(Material.CHEST);
        final ItemBuilder sety = new ItemBuilder(Material.IRON_CHESTPLATE);
        final ItemBuilder bronie = new ItemBuilder(Material.DIAMOND_SWORD);
        final ItemBuilder akcesoria = new ItemBuilder(Material.STORAGE_MINECART);
        final ItemBuilder materialy = new ItemBuilder(Material.EMERALD);
        final ItemBuilder other = new ItemBuilder(Material.EYE_OF_ENDER);

        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&c&lTargi #" + page));
        allItems.clear();
        switch (category) {
            case 1:
                szkloGracze.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloGracze.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        allItems.add(gracze.setName(Utils.format("&c&l" + p.getName())).hideFlag().toItemStack().clone());
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").addGlowing().toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").toItemStack());
                gui.setItem(18, bronie.setName("&cBronie").toItemStack());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").toItemStack());
                gui.setItem(45, other.setName("&cInne").toItemStack());
                break;
            case 2:
                szkloSety.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloSety.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        for (ItemStack item : getPlayerTargItems(p.getUniqueId())) {
                            if (String.valueOf(item.getType()).contains("HELMET") || String.valueOf(item.getType()).contains("CHESTPLATE")
                                    || String.valueOf(item.getType()).contains("LEGGINGS") || String.valueOf(item.getType()).contains("BOOTS")) {
                                allItems.add(item.clone());
                            }
                        }
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").addGlowing().toItemStack());
                gui.setItem(18, bronie.setName("&cBronie").toItemStack().clone());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").toItemStack());
                gui.setItem(45, other.setName("&cInne").toItemStack());
                break;
            case 3:
                szkloBronie.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloBronie.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        for (ItemStack item : getPlayerTargItems(p.getUniqueId())) {
                            if (String.valueOf(item.getType()).contains("SWORD") || String.valueOf(item.getType()).contains("AXE")
                                    || String.valueOf(item.getType()).contains("HOE") || String.valueOf(item.getType()).contains("PICKAXE")) {
                                allItems.add(item.clone());
                            }
                        }
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").toItemStack().clone());
                gui.setItem(18, bronie.setName("&cBronie").addGlowing().toItemStack());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").toItemStack());
                gui.setItem(45, other.setName("&cInne").toItemStack());
                break;
            case 4:
                szkloAkcesoria.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloAkcesoria.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        for (ItemStack item : getPlayerTargItems(p.getUniqueId())) {
                            if (String.valueOf(item.getType()).contains("MINECART") || item.getType().equals(Material.WATCH) || item.getType().equals(Material.ITEM_FRAME)) {
                                allItems.add(item.clone());
                            }
                        }
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").toItemStack());
                gui.setItem(18, bronie.setName("&cBronie").toItemStack());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").addGlowing().toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").toItemStack());
                gui.setItem(45, other.setName("&cInne").toItemStack());
                break;
            case 5:
                szkloMaterialy.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloMaterialy.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        for (ItemStack item : getPlayerTargItems(p.getUniqueId())) {
                            if (item.getItemMeta().getLore().contains("§8§oMaterial")) {
                                allItems.add(item.clone());
                            }
                        }
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").toItemStack());
                gui.setItem(18, bronie.setName("&cBronie").toItemStack());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").addGlowing().toItemStack());
                gui.setItem(45, other.setName("&cInne").toItemStack());
                break;
            case 6:
                szkloOther.setName(" ").addGlowing();
                for (int i = 0; i < gui.getSize(); i++) {
                    if (!((i > 1 && i < 9) || (i > 10 && i < 18) || (i > 19 && i < 27) || (i > 28 && i < 36) || (i > 37 && i < 45) || (i > 46 && i < 54))) {
                        gui.setItem(i, szkloOther.toItemStack());
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!(getPlayerTargItems(p.getUniqueId()) == null || getPlayerTargItems(p.getUniqueId()).isEmpty())) {
                        for (ItemStack item : getPlayerTargItems(p.getUniqueId())) {
                            if (!(String.valueOf(item.getType()).contains("HELMET") || String.valueOf(item.getType()).contains("CHESTPLATE")
                                    || String.valueOf(item.getType()).contains("LEGGINGS") || String.valueOf(item.getType()).contains("BOOTS")
                                    || String.valueOf(item.getType()).contains("SWORD") || String.valueOf(item.getType()).contains("AXE")
                                    || String.valueOf(item.getType()).contains("HOE") || String.valueOf(item.getType()).contains("PICKAXE")
                                    || String.valueOf(item.getType()).contains("MINECART") || item.getType().equals(Material.WATCH)
                                    || item.getType().equals(Material.ITEM_FRAME) || item.getItemMeta().getLore().contains("§8§oMaterial"))) {
                                allItems.add(item.clone());
                            }
                        }
                    }
                }
                gui.setItem(0, gracze.setName("&cTargi Graczy").toItemStack());
                gui.setItem(9, sety.setName("&cZbroje").toItemStack());
                gui.setItem(18, bronie.setName("&cBronie").toItemStack());
                gui.setItem(27, akcesoria.setName("&cAkcesorium").toItemStack());
                gui.setItem(36, materialy.setName("&cMaterialy").toItemStack());
                gui.setItem(45, other.setName("&cInne").addGlowing().toItemStack());
                break;
        }

        gui.setItem(49, this.getSortItem(sort));
        gui.setItem(50, goBack.setName("&cPowrot").toItemStack());

        final List<ItemStack> finalAllItems = this.sortList(allItems, sort);

        if (TargUtils.isPageValid(finalAllItems, page - 1, 38)) {
            gui.setItem(47, goLeft.setName("&aPoprzednia").toItemStack());
        } else {
            gui.setItem(47, goLeft.setName("&cPoprzednia").toItemStack());
        }


        if (TargUtils.isPageValid(finalAllItems, page + 1, 38)) {
            gui.setItem(53, goRight.setName("&aNastepna").toItemStack());
        } else {
            gui.setItem(53, goRight.setName("&cNastepna").toItemStack());
        }

        for (ItemStack is : TargUtils.getPageItems(finalAllItems, page, 38)) {
            gui.setItem(gui.firstEmpty(), is);
        }

        player.openInventory(gui);
    }

    public final List<ItemStack> sortList(final List<ItemStack> allItems, final int sort) {
        final List<ItemStack> sortedItems = new ArrayList<>();
        final Multimap<ItemStack, Double> itemPriceMap = ArrayListMultimap.create();
        final Multimap<ItemStack, String> itemNameMap = ArrayListMultimap.create();

        if (allItems.isEmpty()) {
            return allItems;
        }

        switch (sort) {
            case 1:
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (allItems.get(0).getItemMeta().getDisplayName().contains(p.getName())) {
                        return allItems;
                    }
                }

                for (ItemStack is : allItems) {
                    for (String s : is.getItemMeta().getLore()) {
                        if (s.contains(Utils.format("&7Cena: &6"))) {
                            final double price = Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace("$", "").replaceAll(" ", "").trim());
                            itemPriceMap.put(is.clone(), price);
                        }
                    }
                }
                Stream<Map.Entry<ItemStack, Double>> sortedDesc = itemPriceMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
                sortedDesc.forEach(e -> sortedItems.add(e.getKey()));
                break;
            case 2:

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (allItems.get(0).getItemMeta().getDisplayName().contains(p.getName())) {
                        return allItems;
                    }
                }

                for (ItemStack is : allItems) {
                    for (String s : is.getItemMeta().getLore()) {
                        if (s.contains(Utils.format("&7Cena: &6"))) {
                            final double price = Double.parseDouble(Utils.removeColor(s).replace("Cena: ", "").replace("$", "").replaceAll(" ", "").trim());
                            itemPriceMap.put(is, price);
                        }
                    }
                }
                Stream<Map.Entry<ItemStack, Double>> sortedAsc = itemPriceMap.entries().stream().sorted(Map.Entry.comparingByValue());
                sortedAsc.forEach(e -> sortedItems.add(e.getKey()));
                break;
            case 3:
                for (ItemStack is : allItems) {
                    itemNameMap.put(is, Utils.removeColor(is.getItemMeta().getDisplayName()));
                }
                Stream<Map.Entry<ItemStack, String>> sortedAZ = itemNameMap.entries().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
                sortedAZ.forEach(e -> sortedItems.add(e.getKey()));
                System.out.println(sortedAZ);
                break;
            case 4:
                for (ItemStack is : allItems) {
                    itemNameMap.put(is, Utils.removeColor(is.getItemMeta().getDisplayName()));
                }
                Stream<Map.Entry<ItemStack, String>> sortedZA = itemNameMap.entries().stream().sorted(Map.Entry.comparingByValue());
                sortedZA.forEach(e -> sortedItems.add(e.getKey()));
                break;
        }


        return sortedItems;
    }

    public ItemStack getSortItem(final int sortType) {
        final List<String> lore = new ArrayList<>();

        switch (sortType) {
            case 1:
                lore.add("&3>> Cena Malejaco");
                lore.add("&8>> Cena Rosnaco");
                lore.add("&8>> A-Z");
                lore.add("&8>> Z-A");
                break;
            case 2:
                lore.add("&8>> Cena Malejaco");
                lore.add("&3>> Cena Rosnaco");
                lore.add("&8>> A-Z");
                lore.add("&8>> Z-A");
                break;
            case 3:
                lore.add("&8>> Cena Malejaco");
                lore.add("&8>> Cena Rosnaco");
                lore.add("&3>> A-Z");
                lore.add("&8>> Z-A");
                break;
            case 4:
                lore.add("&8>> Cena Malejaco");
                lore.add("&8>> Cena Rosnaco");
                lore.add("&8>> A-Z");
                lore.add("&3>> Z-A");
                break;
        }
        return sort.setName("&cSortowanie").setLore(lore).toItemStack();
    }


    public void openPlayerTarg(final String playerName, final Player player) {
        final UUID uuid = rpgcore.getUserManager().find(playerName).getId();
        final Inventory gui = this.getPlayerTarg(playerName, uuid);

        player.openInventory(gui);
    }

    public Inventory getPlayerTarg(final String playerName, final UUID uuid) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&cTarg gracza " + playerName));
        fill.setName(" ").addGlowing();

        for (int i = 27; i < 36; i++) {
            gui.setItem(i, fill.toItemStack());
        }

        gui.setItem(31, goBack.setName("&cPowrot").toItemStack());

        for (ItemStack is : getPlayerTargItems(uuid)) {
            gui.setItem(gui.firstEmpty(), is);
        }
        return gui;
    }

    public void returnPlayerItem(final Player player, final ItemStack itemStack) {
        final ItemMeta meta = itemStack.getItemMeta();

        final List<String> itemLore = meta.getLore();

        final int i = itemLore.size();
        itemLore.remove(i-1);
        itemLore.remove(i-2);
        itemLore.remove(i-3);
        itemLore.remove(i-4);
        itemLore.remove(i-5);
        itemLore.remove(i-6);
        itemLore.remove(i-7);
        itemLore.remove(i-8);

        meta.setLore(Utils.format(itemLore));
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    public void givePlayerBoughtItem(final Player player, final ItemStack is) {
        final ItemMeta meta = is.getItemMeta();

        final List<String> lore = meta.getLore();

        final int loreSize = lore.size();

        lore.remove(loreSize - 1);
        lore.remove(loreSize - 2);
        lore.remove(loreSize - 3);

        meta.setLore(Utils.format(lore));

        is.setItemMeta(meta);

        player.getInventory().addItem(is);
    }

    public List<ItemStack> getPlayerTargItems(final UUID uuid) {
        return playerTargItems.get(uuid);
    }

    public void removePlayerTargItem(final UUID uuid, final ItemStack is) {
        playerTargItems.get(uuid).remove(is);
    }

    public void addPlayerTargItems(final UUID uuid, final ItemStack item) {
        playerTargItems.computeIfAbsent(uuid, k -> new ArrayList<>());
        playerTargItems.get(uuid).add(item);
    }

    public void addToWystawia(final UUID uuid) {
        this.wystawia.add(uuid);
    }

    public void removeFromWystawia(final UUID uuid) {
        this.wystawia.remove(uuid);
    }

}
