package rpg.rpgcore.npc.czarownica;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.czarownica.enums.CzarownicaMissions;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CzarownicaNPC {
    private final Map<UUID, CzarownicaUser> userMap;

    public CzarownicaNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllCzarownica();
    }


    public void click(final Player player) {
        CzarownicaUser czarownicaUser = this.userMap.get(player.getUniqueId());
        if (czarownicaUser == null) {
            czarownicaUser = new CzarownicaUser(player.getUniqueId());
            this.userMap.put(player.getUniqueId(), czarownicaUser);
        }
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&5&lCzarownica"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        gui.setItem(12, new ItemBuilder(Material.BOOK).setName("&5&kaa&e Kampania &5&kaa").toItemStack());
        if (czarownicaUser.isUnlocked()) {
            gui.setItem(14, new ItemBuilder(Material.WORKBENCH).setName("&5&kaa&e Crafting &5&kaa").toItemStack());
        } else {
            gui.setItem(14, new ItemBuilder(Material.WORKBENCH).setName("&5&kaa&e Crafting &5&kaa").setLore(Arrays.asList(" ", "&4&lZABLOKOWANE")).toItemStack());
        }

        player.openInventory(gui);
    }

    public void openCraftingi(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&5&lCzarownica &8- &eCrafting"));
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        gui.setItem(3, new ItemBuilder(GlobalItem.I_KSIEGAMAGII.getItemStack().clone()).setLoreCrafting(GlobalItem.I_KSIEGAMAGII.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                "&f&lWymagane Przedmioty:",
                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I_KAMIENBAO.getItemStack(), 2) ? "&3&l&m" : "&3&l") +
                        "Kamien Zaczarowania Stolu&8 x2 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KAMIENBAO.getItemStack()) + "/2)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I_CZASTKA_MAGII.getItemStack(), 2) ? "&d&l&m" : "&d&l") +
                        "Czastka Magii&8 x2 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_CZASTKA_MAGII.getItemStack()) + "/2)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 16) ? "&e&m" : "&e") +
                        "Zloto&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I12.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 16) ? "&b&m" : "&b") +
                        "Brylant&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I13.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 16) ? "&a&m" : "&a") +
                        "Szmaragd&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I14.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 16) ? "&c&m" : "&c") +
                        "Pyl&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I15.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 16) ? "&7&m" : "&7") +
                        "Kamien&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I16.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 16) ? "&8&m" : "&8") +
                        "Stal&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I17.getItemStack()) + "/16)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 16) ? "&7&m" : "&7") +
                        "Proch&8 x16 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I18.getItemStack()) + "/16)",
                "&7- " + (user.getKasa() >= 5_000_000 ? "&6&m" : "&6") + "5 000 000&2$"
        )).toItemStack().clone());

        gui.setItem(5, new ItemBuilder(GlobalItem.I_KSIEGAMAGII_PLUS.getItemStack().clone()).setLoreCrafting(GlobalItem.I_KSIEGAMAGII_PLUS.getItemStack().clone().getItemMeta().getLore(), Arrays.asList(
                "&f&lWymagane Przedmioty:",
                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I_KSIEGAMAGII.getItemStack(), 1) ? "&5&l&m" : "&5&l") +
                        "Ksiega Magii&8 x1 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_KSIEGAMAGII.getItemStack()) + "/1)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I_CZASTKA_MAGII.getItemStack(), 3) ? "&d&l&m" : "&d&l") +
                        "Czastka Magii&8 x3 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I_CZASTKA_MAGII.getItemStack()) + "/3)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 8) ? "&e&m" : "&e") +
                        "Zloto&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I12.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 8) ? "&b&m" : "&b") +
                        "Brylant&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I13.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 8) ? "&a&m" : "&a") +
                        "Szmaragd&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I14.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 8) ? "&c&m" : "&c") +
                        "Pyl&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I15.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 8) ? "&7&m" : "&7") +
                        "Kamien&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I16.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 8) ? "&8&m" : "&8") +
                        "Stal&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I17.getItemStack()) + "/8)",

                "&7- " + (player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 8) ? "&7&m" : "&7") +
                        "Proch&8 x8 (" + Utils.getPlayerInventoryItemCount(player, GlobalItem.I18.getItemStack()) + "/8)",
                "&7- " + (user.getKasa() >= 10_000_000 ? "&6&m" : "&6") + "10 000 000&2$"
        )).toItemStack().clone());

        player.openInventory(gui);
    }

    public void openKampania(final Player player) {
        final CzarownicaUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&5&lCzarownica &8- &eKampania"));
        for (int i = 9; i < gui.getSize(); i++) {
            if (i % 2 != 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 10).setName(" ").toItemStack());
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (i == user.getMission()) {
                gui.setItem(i- 1, Objects.requireNonNull(CzarownicaMissions.getMissionById(i)).getMissionItem(user));
                continue;
            }
            if (i > user.getMission()) {
                gui.setItem(i -1, new ItemBuilder(Material.BOOK).setName("&5Misja " + i).setLore(Arrays.asList(" ", "&7Ukoncz poprzednia misje, zeby odblokowac")).toItemStack().clone());
                continue;
            }
            gui.setItem(i - 1, new ItemBuilder(Material.BOOK).setName("&5Misja " + i).setLore(Arrays.asList(" ", "&a&lUKONCZONE")).toItemStack().clone());
        }

        player.openInventory(gui);
    }


    public CzarownicaUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final CzarownicaUser czarownicaUser) {
        this.userMap.put(czarownicaUser.getUuid(), czarownicaUser);
    }

    public void set(final UUID uuid, final CzarownicaUser czarownicaUser) {
        this.userMap.replace(uuid, czarownicaUser);
    }

    public ImmutableSet<CzarownicaUser> getCzarownicaUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
