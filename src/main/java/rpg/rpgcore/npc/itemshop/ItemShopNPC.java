package rpg.rpgcore.npc.itemshop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.ItemShop;

import java.util.Arrays;

public class ItemShopNPC {

    public void openItemShop(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lItem&2&lShop"));

        gui.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(2, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(4, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(8, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(24, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(26, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(33, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(35, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(45, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(47, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(49, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(51, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());
        gui.setItem(53, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName("").toItemStack());

        gui.setItem(1, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(7, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(9, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(17, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(36, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(40, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(42, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(44, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(46, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(48, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(50, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());
        gui.setItem(52, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 13).setName("").toItemStack());

        gui.setItem(10, addPrice(ItemShop.IS15.getItems().get(0), 400));
        gui.setItem(19, addPrice(ItemShop.IS14.getItems().get(0), 280));
        gui.setItem(28, addPrice(ItemShop.IS13.getItems().get(0), 195));
        gui.setItem(37, addPrice(ItemShop.IS12.getItems().get(0), 150));

        gui.setItem(39, addPrice(ItemShop.IS19.getItems().get(0), 650));

        gui.setItem(14, addPrice(ItemShop.IS11.getItems().get(0), 150));
        gui.setItem(23, addPrice(ItemShop.IS10.getItems().get(0), 100));
        gui.setItem(32, addPrice(ItemShop.IS9.getItems().get(0), 50));
        gui.setItem(41, addPrice(ItemShop.IS20.getItems().get(0), 1300));

        gui.setItem(16, addPrice(GlobalItem.getItem("I52", 1), 25));
        gui.setItem(25, addPrice(GlobalItem.getItem("I53", 1), 35));
        gui.setItem(34, addPrice(GlobalItem.getItem("I54", 1), 50));
        gui.setItem(43, addPrice(GlobalItem.getItem("I55", 1), 75));


        player.openInventory(gui);
    }

    private ItemStack addPrice(final ItemStack item, final int price) {
        return new ItemBuilder(item.clone()).setLoreCrafting(item.clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Cena: &4" + price + " &4&lH&6&lS"
        )).addTagInt("price", price).toItemStack().clone();
    }
}
