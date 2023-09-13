package rpg.rpgcore.npc.zlotnik;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZlotnikNPC {

    public void click(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lZlotnik"));
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0)
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 1).setName(" ").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
        }

        gui.setItem(2, getMiejsceItem());
        gui.setItem(4, new ItemBuilder(Material.ANVIL).setName("&6&lWzmocnij swoje akcesorium").setLore(Arrays.asList(
                "&f&lWymagane Przedmioty:",
                "&7- " + (player.getInventory().containsAtLeast(Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack(), 3) ? "&e&m" : "&e") +
                        "Sakiewka ze Zlotym Proszkiem&8 x3 (" + Utils.getPlayerInventoryItemCount(player, Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack()) + "/3)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 96) ? "&8&m" : "&8") +
                        "Szata Rozbojnika&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SZATAROZBOJNIKA.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 96) ? "&a&m" : "&a") +
                        "Oko Goblina&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OKOGOBLINA.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 96) ? "&f&m" : "&f") +
                        "Skora Goryla&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SKORAGORYLA.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 96) ? "&7&m" : "&7") +
                        "Zlamana Kosc&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_ZLAMANAKOSC.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 96) ? "&b&m" : "&b") +
                        "Lza Oceanu&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_LZAOCEANU.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 96) ? "&7&m" : "&7") +
                        "Wilcze Futro&8 x96 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_WILCZEFUTRO.getItem()) + "/96)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 64) ? "&c&m" : "&c") +
                        "Ognisty Pyl&8 x64 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OGNISTYPYL.getItem()) + "/64)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA.getItem(), 48) ? "&5&m" : "&5") +
                        "Trujaca Roslina&8 x48 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_TRUJACAROSLINA.getItem()) + "/48)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 64) ? "&e&m" : "&e") +
                        "Zloto&8 x64 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I12.getItemStack()) + "/64)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 64) ? "&b&m" : "&b") +
                        "Brylant&8 x64 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I13.getItemStack()) + "/64)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 64) ? "&a&m" : "&a") +
                        "Szmaragd&8 x64 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I14.getItemStack()) + "/64)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 48) ? "&c&m" : "&c") +
                        "Pyl&8 x48 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I15.getItemStack()) + "/48)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 48) ? "&7&m" : "&7") +
                        "Kamien&8 x48 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I16.getItemStack()) + "/48)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 48) ? "&8&m" : "&8") +
                        "Stal&8 x48 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I17.getItemStack()) + "/48)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 48) ? "&7&m" : "&7") +
                        "Proch&8 x48 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I18.getItemStack()) + "/48)",

                "&7- " + (user.getKasa() >= 100_000_000 ? "&6&m" : "&6") + "100 000 000&2$"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public ItemStack getMiejsceItem() {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lMiejsce na twoje akcesorium").toItemStack();
    }

    public ItemStack getWzmocnionyItem(final ItemStack akce) {
        switch (akce.getType()) {
            case ITEM_FRAME:
                return new ItemBuilder(akce.clone()).setName("&6&lWzmocniona " + akce.getItemMeta().getDisplayName()).setLore(Arrays.asList(
                        "&7Typ: &cAkcesorium Podstawowe",
                        "&7Zwiekszona Defensywa: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "def") + 15, 0) + "%",
                        "&7Szansa na Blok: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "blok") + 15, 0) + "%",
                        "&7Dodatkowe Serca: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "hp") + 10, 0),
                        " ",
                        (Utils.getTagInt(akce, "lvl") > 90 ? "&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl") : "&cWymagany poziom: &685")
                )).toItemStack().clone();
            case HOPPER_MINECART:
                return new ItemBuilder(akce.clone()).setName("&6&lWzmocnione " + akce.getItemMeta().getDisplayName()).setLore(Arrays.asList(
                        "&7Typ: &cAkcesorium Podstawowe",
                        "&7Silny przeciwko ludziom: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "ludzie") + 10, 0) + "%",
                        "&7Odpornosc na Ludzi: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "odpo") + 10, 0) + "%",
                        "&7Zmniejszona Szybkosc Ruchu: &c" + (int) DoubleUtils.round(Utils.getTagInt(akce, "mspeed") - (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "mspeed") * 4) / 100), 0), 0),
                        " ",
                        (Utils.getTagInt(akce, "lvl") > 90 ? "&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl") : "&cWymagany poziom: &685")
                )).toItemStack().clone();
            case STORAGE_MINECART:
                return new ItemBuilder(akce.clone()).setName("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()).setLore(Arrays.asList(
                        "&7Typ: &cAkcesorium Podstawowe",
                        "&7Dodatkowe Obrazenia: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "ddmg") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "ddmg") * 20) / 100), 0), 0),
                        "&7Szansa na Cios Krytyczny: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "kryt") + 20, 0) + "%",
                        "&7Zwiekszone Obrazenia: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "srdmg") + 20, 0) + "%",
                        " ",
                        (Utils.getTagInt(akce, "lvl") > 90 ? "&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl") : "&cWymagany poziom: &685")
                )).toItemStack().clone();
            case EXPLOSIVE_MINECART:
                return new ItemBuilder(akce.clone()).setName("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()).setLore(Arrays.asList(
                        "&7Typ: &cAkcesorium Podstawowe",
                        "&7Szansa na Przeszycie Bloku: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "przebicie") + 10, 0) + "%",
                        "&7Wzmocnienie Ciosu Krytycznego: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "wkryt") + 10, 0) + "%",
                        "&7Zwiekszona Szybkosc Ruchu: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "speed") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "speed") * 5) / 100), 0), 0),
                        " ",
                        (Utils.getTagInt(akce, "lvl") > 90 ? "&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl") : "&cWymagany poziom: &685")
                )).toItemStack().clone();
                case WATCH:
                    return new ItemBuilder(akce.clone()).setName("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()).setLore(Arrays.asList(
                            "&7Typ: &cAkcesorium Podstawowe",
                            "&7Srednie Obrazenia: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "srdmg") + 10, 0) + "%",
                            "&7Silny przeciwko potworom: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "potwory") + 10, 0) + "%",
                            "&7Dodatkowy EXP: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "exp") + 5, 0) + "%",
                            " ",
                            (Utils.getTagInt(akce, "lvl") > 90 ? "&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl") : "&cWymagany poziom: &685")
                    )).toItemStack().clone();
            default:
                return new ItemBuilder(Material.BARRIER).setName("&cCos poszlo nie tak :<").toItemStack().clone();
        }
    }

    public void wzmAkce(final Player player, final ItemStack akce) {
        final ItemMeta meta = akce.getItemMeta();
        final List<String> lore = new ArrayList<>();
        Bukkit.getServer().broadcastMessage(Utils.format("&6&lZlotnik &8>> &7Gracz &6" + player.getName() + " &7wzmocnil " + akce.getItemMeta().getDisplayName() + "&7!"));
        switch (akce.getType()) {
            case ITEM_FRAME:
                meta.setDisplayName(Utils.format("&6&lWzmocniona " + akce.getItemMeta().getDisplayName()));
                lore.add(Utils.format("&7Typ: &cAkcesorium Podstawowe"));
                lore.add(Utils.format("&7Zwiekszona Defensywa: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "def") + 15, 0) + "%"));
                lore.add(Utils.format("&7Szansa na Blok: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "blok") + 15, 0) + "%"));
                lore.add(Utils.format("&7Dodatkowe Serca: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "hp") + 10, 0)));
                lore.add(Utils.format(" "));
                if (Utils.getTagInt(akce, "lvl") > 90) {
                    lore.add(Utils.format("&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl")));
                } else {
                    lore.add(Utils.format("&cWymagany poziom: &690"));
                    Utils.setTagInt(akce, "lvl", 90, false);
                }
                Utils.setTagDouble(akce, "def", Utils.getTagDouble(akce, "def") + 15, true);
                Utils.setTagDouble(akce, "blok", Utils.getTagDouble(akce, "blok") + 15, true);
                Utils.setTagInt(akce, "hp", Utils.getTagInt(akce, "hp") + 10, true);

                meta.setLore(lore);
                akce.setItemMeta(meta);
                break;

            case HOPPER_MINECART:
                meta.setDisplayName(Utils.format("&6&lWzmocnione " + akce.getItemMeta().getDisplayName()));
                lore.add(Utils.format("&7Typ: &cAkcesorium Podstawowe"));
                lore.add(Utils.format("&7Silny przeciwko ludziom: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "ludzie") + 10, 0) + "%"));
                lore.add(Utils.format("&7Odpornosc na Ludzi: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "odpo") + 10, 0) + "%"));
                lore.add(Utils.format("&7Zmniejszona Szybkosc Ruchu: &c" + (int) DoubleUtils.round(Utils.getTagInt(akce, "mspeed") - (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "mspeed") * 4) / 100), 0), 0)));
                lore.add(Utils.format(" "));
                if (Utils.getTagInt(akce, "lvl") > 90) {
                    lore.add(Utils.format("&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl")));
                } else {
                    lore.add(Utils.format("&cWymagany poziom: &690"));
                    Utils.setTagInt(akce, "lvl", 90, false);
                }
                Utils.setTagDouble(akce, "ludzie", Utils.getTagDouble(akce, "ludzie") + 10, true);
                Utils.setTagDouble(akce, "odpo", Utils.getTagDouble(akce, "odpo") + 10, true);
                Utils.setTagInt(akce, "mspeed", Utils.getTagInt(akce, "mspeed") - (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "mspeed") * 4) / 100), 0), true);

                meta.setLore(lore);
                akce.setItemMeta(meta);
                break;
            case STORAGE_MINECART:
                meta.setDisplayName(Utils.format("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()));
                lore.add(Utils.format("&7Typ: &cAkcesorium Podstawowe"));
                lore.add(Utils.format("&7Dodatkowe Obrazenia: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "ddmg") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "ddmg") * 20) / 100), 0), 0)));
                lore.add(Utils.format("&7Szansa na Cios Krytyczny: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "kryt") + 20, 0) + "%"));
                lore.add(Utils.format("&7Zwiekszone Obrazenia: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "srdmg") + 20, 0) + "%"));
                lore.add(Utils.format(" "));
                if (Utils.getTagInt(akce, "lvl") > 90) {
                    lore.add(Utils.format("&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl")));
                } else {
                    lore.add(Utils.format("&cWymagany poziom: &690"));
                    Utils.setTagInt(akce, "lvl", 90, false);
                }
                Utils.setTagInt(akce, "ddmg", Utils.getTagInt(akce, "ddmg") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "ddmg") * 20) / 100), 0), true);
                Utils.setTagDouble(akce, "kryt", Utils.getTagDouble(akce, "kryt") + 20, true);
                Utils.setTagDouble(akce, "srdmg", Utils.getTagDouble(akce, "srdmg") + 20, true);

                meta.setLore(lore);
                akce.setItemMeta(meta);
                break;
            case EXPLOSIVE_MINECART:
                meta.setDisplayName(Utils.format("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()));
                lore.add(Utils.format("&7Typ: &cAkcesorium Podstawowe"));
                lore.add(Utils.format("&7Szansa na Przeszycie Bloku: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "przebicie") + 10, 0) + "%"));
                lore.add(Utils.format("&7Wzmocnienie Ciosu Krytycznego: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "wkryt") + 10, 0) + "%"));
                lore.add(Utils.format("&7Zwiekszona Szybkosc Ruchu: &c+" + (int) DoubleUtils.round(Utils.getTagInt(akce, "speed") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "speed") * 5) / 100), 0), 0)));
                lore.add(Utils.format(" "));
                if (Utils.getTagInt(akce, "lvl") > 90) {
                    lore.add(Utils.format("&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl")));
                } else {
                    lore.add(Utils.format("&cWymagany poziom: &690"));
                    Utils.setTagInt(akce, "lvl", 90, false);
                }
                Utils.setTagDouble(akce, "przebicie", Utils.getTagDouble(akce, "przebicie") + 10, true);
                Utils.setTagDouble(akce, "wkryt", Utils.getTagDouble(akce, "wkryt") + 10, true);
                Utils.setTagInt(akce, "speed", Utils.getTagInt(akce, "speed") + (int) DoubleUtils.round(Math.ceil((double) (Utils.getTagInt(akce, "speed") * 5) / 100), 0), true);

                meta.setLore(lore);
                akce.setItemMeta(meta);
                break;
            case WATCH:
                meta.setDisplayName(Utils.format("&6&lWzmocniony " + akce.getItemMeta().getDisplayName()));
                lore.add(Utils.format("&7Typ: &cAkcesorium Podstawowe"));
                lore.add(Utils.format("&7Srednie Obrazenia: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "srdmg") + 10, 0) + "%"));
                lore.add(Utils.format("&7Silny przeciwko potworom: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "potwory") + 10, 0) + "%"));
                lore.add(Utils.format("&7Dodatkowy EXP: &c" + (int) DoubleUtils.round(Utils.getTagDouble(akce, "exp") + 5, 0) + "%"));
                lore.add(Utils.format(" "));
                if (Utils.getTagInt(akce, "lvl") > 90) {
                    lore.add(Utils.format("&cWymagany poziom: &6" + Utils.getTagInt(akce, "lvl")));
                } else {
                    lore.add(Utils.format("&cWymagany poziom: &690"));
                    Utils.setTagInt(akce, "lvl", 90, false);
                }
                Utils.setTagDouble(akce, "srdmg", Utils.getTagDouble(akce, "srdmg") + 10, true);
                Utils.setTagDouble(akce, "potwory", Utils.getTagDouble(akce, "potwory") + 10, true);
                Utils.setTagDouble(akce, "exp", Utils.getTagDouble(akce, "exp") + 5, true);

                meta.setLore(lore);
                akce.setItemMeta(meta);
                break;
        }
        player.getInventory().addItem(akce);
    }
}
