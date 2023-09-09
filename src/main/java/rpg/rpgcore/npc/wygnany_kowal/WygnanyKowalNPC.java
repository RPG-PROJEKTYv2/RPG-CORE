package rpg.rpgcore.npc.wygnany_kowal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

import java.util.Arrays;
import java.util.List;

public class WygnanyKowalNPC {
    public void click(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&3&lWygnany Kowal"));
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            else gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        }

        gui.setItem(4, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Wzmocnij Swoj Miecz").setLore(Arrays.asList(
                "&f&lWymagane Przedmioty:",
                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack(), 16) ? "&8&m" : "&8") +
                        "Odlamek Zakletej Duszy&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 128) ? "&8&m" : "&8") +
                        "Szata Rozbojnika&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SZATAROZBOJNIKA.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 128) ? "&a&m" : "&a") +
                        "Oko Goblina&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OKOGOBLINA.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 128) ? "&f&m" : "&f") +
                        "Skora Goryla&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_SKORAGORYLA.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 128) ? "&7&m" : "&7") +
                        "Zlamana Kosc&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_ZLAMANAKOSC.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 128) ? "&b&m" : "&b") +
                        "Lza Oceanu&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_LZAOCEANU.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 128) ? "&7&m" : "&7") +
                        "Wilcze Futro&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_WILCZEFUTRO.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 128) ? "&c&m" : "&c") +
                        "Ognisty Pyl&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_OGNISTYPYL.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA.getItem(), 128) ? "&5&m" : "&5") +
                        "Trujaca Roslina&8 x128 (" + Utils.getPlayerInventoryItemCount(player, Ulepszacze.I_TRUJACAROSLINA.getItem()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 128) ? "&e&m" : "&e") +
                        "Zloto&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I12.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 128) ? "&b&m" : "&b") +
                        "Brylant&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I13.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 128) ? "&a&m" : "&a") +
                        "Szmaragd&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I14.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 128) ? "&c&m" : "&c") +
                        "Pyl&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I15.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 128) ? "&7&m" : "&7") +
                        "Kamien&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I16.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 128) ? "&8&m" : "&8") +
                        "Stal&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I17.getItemStack()) + "/128)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 128) ? "&7&m" : "&7") +
                        "Proch&8 x128 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I18.getItemStack()) + "/128)",

                "&7- " + Bossy.I1_10.getItemStack().getItemMeta().getDisplayName() +
                        "&8 x24 " + (player.getInventory().containsAtLeast(Bossy.I1_10.getItemStack(), 24) ? "&8&m" : "&8") + "(" + Utils.getPlayerInventoryItemCount(player, Bossy.I1_10.getItemStack()) + "/24)",

                "&7- " + Bossy.I10_20.getItemStack().getItemMeta().getDisplayName() +
                        "&8 x16 " + (player.getInventory().containsAtLeast(Bossy.I10_20.getItemStack(), 16) ? "&8&m" : "&8") + "(" + Utils.getPlayerInventoryItemCount(player, Bossy.I10_20.getItemStack()) + "/16)",

                "&7- " + Bossy.I40_50.getItemStack().getItemMeta().getDisplayName() +
                        "&8 x8 " + (player.getInventory().containsAtLeast(Bossy.I40_50.getItemStack(), 8) ? "&8&m" : "&8") + "(" + Utils.getPlayerInventoryItemCount(player, Bossy.I40_50.getItemStack()) + "/8)",

                "&7- " + Bossy.I60_70.getItemStack().getItemMeta().getDisplayName() +
                        "&8 x4 " + (player.getInventory().containsAtLeast(Bossy.I60_70.getItemStack(), 4) ? "&8&m" : "&8") + "(" + Utils.getPlayerInventoryItemCount(player, Bossy.I60_70.getItemStack()) + "/4)",

                "&7- " + (user.getKasa() >= 250_000_000 ? "&6&m" : "&6") + "250 000 000&2$",
                "",
                "",
                "&c&lPRZEDMIOT TEN OTRZYMA NOWY WYMAGANY POZIOM: &6&l75"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void upgradeSword(final ItemStack sword) {
        final String type = Utils.getTagString(sword, "type");

        if (type.equals("ks")) {
            int mobyP = Utils.getTagInt(sword, "mobyProcentKS");
            int moby = Utils.getTagInt(sword, "moby");
            int dmg = Utils.getTagInt(sword, "dmg");
            mobyP += 20;
            Utils.setTagInt(sword, "mobyProcentKS", mobyP, true);
            if (moby + 15 <= 2500) {
                moby += 15;
                Utils.setTagInt(sword, "moby", moby, true);
            }
            if (dmg + 50 <= 2500) {
                dmg += 50;
                Utils.setTagInt(sword, "dmg", dmg, true);
            }
            Utils.setTagInt(sword, "lvl", 75, false);


            final ItemMeta meta = sword.getItemMeta();
            meta.setDisplayName(Utils.format("&8&lWzmocniony &9&lMithrylowy Sztylet"));
            final List<String> lore = sword.getItemMeta().getLore();
            lore.set(0, "&7Obrazenia: &c" + dmg);
            lore.set(1, "&7Obrazenia na potwory: &c" + moby);
            lore.set(3, "&3Silny Na Potwory: &f+" + mobyP + "%");
            lore.set(5, "&cWymagany Poziom: &675");
            meta.setLore(Utils.format(lore));
            sword.setItemMeta(meta);
            return;
        }
        int ludzieP = Utils.getTagInt(sword, "ludzieProcentTYRA");
        int dmg = Utils.getTagInt(sword, "dmg");
        ludzieP += 20;
        Utils.setTagInt(sword, "ludzieProcentTYRA", ludzieP, true);
        if (dmg + 50 <= 2500) {
            dmg += 50;
            Utils.setTagInt(sword, "dmg", dmg, true);
        }
        Utils.setTagInt(sword, "lvl", 75, false);

        final ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(Utils.format("&8&lWzmocnione &9&lMithrylowe Ostrze"));
        final List<String> lore = sword.getItemMeta().getLore();
        lore.set(0, "&7Obrazenia: &c" + dmg);
        lore.set(2, "&3Silny Na Ludzi: &f+" + ludzieP + "%");
        lore.set(4, "&cWymagany Poziom: &675");
        meta.setLore(Utils.format(lore));
        sword.setItemMeta(meta);

    }


}
