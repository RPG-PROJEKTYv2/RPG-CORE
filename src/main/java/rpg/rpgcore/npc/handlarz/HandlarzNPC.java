package rpg.rpgcore.npc.handlarz;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.handlarz.objects.HandlarzUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;
import rpg.rpgcore.utils.globalitems.expowiska.Przepustki;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class  HandlarzNPC {
    private final Map<UUID, HandlarzUser> userMap;

    private final Map<UUID, Multimap<ItemStack, Double>> userItemMap = new HashMap<>();

    public HandlarzNPC(RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllHandlarz();
    }

    public void openHandlarzGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND).setName("&bWartosciowe przedmioty").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.GOLD_INGOT).setName("&6Sprzedaz/Kupno").toItemStack());
        gui.setItem(16, new ItemBuilder(Material.EMERALD).setName("&6&lItem&2&lShop").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzWartosciowe(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz &8>> &b&lWartosciowe przedmioty"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(12, new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &bSwiatyni").setLore(Arrays.asList("", "&7Cena przedmiotu: &e" + Przepustki.I1.getCena())).toItemStack());
        gui.setItem(13, new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &fKrysztalowej Sali").setLore(Arrays.asList("", "&7Cena przedmiotu: &e" + Przepustki.I2.getCena())).toItemStack());
        gui.setItem(14, new ItemBuilder(Material.PAPER).setName("&eWejsciowka do &6Tajemniczej Siedziby").setLore(Arrays.asList("", "&7Cena przedmiotu: &e" + Przepustki.I3.getCena())).toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzKupnoSprzedaz(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&a&lHandlarz &8>> &6&lSprzedaz/Kupno"));

        for (int i = 0; i < 27; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        gui.setItem(11, new ItemBuilder(Material.NETHER_STAR).setName("&a&lSklep Dzienny").setLore(Arrays.asList("", "&c&lDostepne od aktualizacji V0.2")).toItemStack());
        gui.setItem(15, new ItemBuilder(Material.GOLD_NUGGET).setName("&e&lSprzedaz").toItemStack());

        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        player.openInventory(gui);
    }

    public void openHandlarzItemShop(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lHandlarz &8>> &6&lItem&2&lShop"));

        for (int i = 0; i < 54; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            }
        }

        // VIP
        gui.setItem(10, addPrice(ItemShop.IS9.getItems().get(0).clone(), 50));
        gui.setItem(11, addPrice(ItemShop.IS10.getItems().get(0).clone(), 100));
        gui.setItem(12, addPrice(ItemShop.IS11.getItems().get(0).clone(), 150));
        gui.setItem(13, addPrice(ItemShop.IS22.getItems().get(0).clone(), 280));

        // PELERYNKI
        gui.setItem(15, addPrice(ItemShop.IS19.getItems().get(0).clone(), 750));
        gui.setItem(16, addPrice(ItemShop.IS20.getItems().get(0).clone(), 1350));

        // ELITA
        gui.setItem(19, addPrice(ItemShop.IS12.getItems().get(0).clone(), 80));
        gui.setItem(20, addPrice(ItemShop.IS13.getItems().get(0).clone(), 150));
        gui.setItem(21, addPrice(ItemShop.IS14.getItems().get(0).clone(), 280));
        gui.setItem(22, addPrice(ItemShop.IS15.getItems().get(0).clone(), 480));

        // PDKI
        gui.setItem(37, addPrice(GlobalItem.getItem("I52", 1), 25).clone());
        gui.setItem(38, addPrice(GlobalItem.getItem("I53", 1), 35).clone());
        gui.setItem(39, addPrice(GlobalItem.getItem("I54", 1), 50).clone());
        gui.setItem(40, addPrice(GlobalItem.getItem("I55", 1), 75).clone());

        // TU RESZTA
        gui.setItem(33, addPrice(ItemShop.IS21.getItems().get(0).clone(), 100));
        gui.setItem(34, addPrice(ItemShop.IS23.getItems().get(0).clone(), 350));
        /*gui.setItem(42, null);
        gui.setItem(43, null);*/

        gui.setItem(49, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());

        gui.setItem(53, new ItemBuilder(Material.GOLD_NUGGET).setName("&cTwoje &4&lH&6&lS&c: " + Utils.spaceNumber(String.valueOf(RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getHellcoins()))).toItemStack());
        player.openInventory(gui);
    }

    private ItemStack addPrice(final ItemStack item, final int price) {
        return new ItemBuilder(item.clone()).setLoreCrafting(item.clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Cena: &4" + price + " &4&lH&6&lS"
        )).addTagInt("price", price).toItemStack().clone();
    }

    public void openHandlarzSprzedaz(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&a&lHandlarz &8>> &e&lSprzedaz"));

        for (int i = 45; i < 54; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName(" ").toItemStack());
            }
        }

        gui.setItem(49, getSprzedajItem(player.getUniqueId()));

        player.openInventory(gui);
    }

    public ItemStack getSprzedajItem(final UUID uuid) {
        final double sellValue = getItemsValue(uuid);
        return new ItemBuilder(Material.NETHER_STAR).setName("&eSprzedaj przedmioty").setLore(Arrays.asList(
                "&7Po sprzedaniu tych przedmiotow",
                "&7otrzymasz &6" + Utils.spaceNumber(sellValue) + "&2$"
        )).addTagDouble("sellValue", sellValue).addGlowing().toItemStack().clone();
    }

    private double getItemsValue(final UUID uuid) {
        return DoubleUtils.round(this.userItemMap.getOrDefault(uuid, ArrayListMultimap.create()).entries().stream().mapToDouble(entry -> entry.getValue() * entry.getKey().getAmount()).sum(), 2);
    }

    public Multimap<ItemStack, Double> getUserItemMap(final UUID uuid) {
        if (!this.userItemMap.containsKey(uuid)) {
            this.userItemMap.put(uuid, ArrayListMultimap.create());
        }
        return this.userItemMap.get(uuid);
    }

    public void removeUserItemMap(final UUID uuid) {
        this.userItemMap.remove(uuid);
    }

    public void addItem(final UUID uuid, final ItemStack item, final double price) {
        if (!this.userItemMap.containsKey(uuid)) {
            this.userItemMap.put(uuid, ArrayListMultimap.create());
        }
        this.userItemMap.get(uuid).put(item.clone(), DoubleUtils.round(price * item.getAmount(), 2));
    }

    public void removeItem(final UUID uuid, final ItemStack item, final double price) {
        this.userItemMap.get(uuid).remove(item.clone(), DoubleUtils.round(price * item.getAmount(),2));
    }

    public HandlarzUser find(final UUID uuid) {
        return userMap.get(uuid);
    }

    public void add(final HandlarzUser user) {
        userMap.put(user.getUuid(), user);
    }

    public void set(final UUID uuid, final HandlarzUser user) {
        userMap.replace(uuid, user);
    }

    public ImmutableSet<HandlarzUser> getHandlarzUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }


}
