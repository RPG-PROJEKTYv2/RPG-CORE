package rpg.rpgcore.os;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.globalitems.OsRewards;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class OsManager {
    private final Map<UUID, OsObject> userMap;
    private final HashMap<Integer, Integer> reqForPlayerKills = new HashMap<>();
    private final HashMap<Integer, Integer> reqForMobKills = new HashMap<>();
    private final HashMap<Integer, Long> reqForTimeSpent = new HashMap<>();
    private final HashMap<Integer, Integer> reqForMinedBlocks = new HashMap<>();
    private final HashMap<Integer, Integer> reqForFishedItems = new HashMap<>();
    private final HashMap<Integer, Integer> reqForOpenedChests = new HashMap<>();
    private final HashMap<Integer, Integer> reqForPositiveUpgrades = new HashMap<>();
    private final HashMap<Integer, Integer> reqForPickedUpNies = new HashMap<>();
    private final HashMap<Integer, Integer> reqForDestroyedMetins = new HashMap<>();
    private final HashMap<Integer, Integer> reqForMinedTrees = new HashMap<>();


    private final RPGCORE rpgcore;

    public OsManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = this.rpgcore.getMongoManager().loadAllOs();
        this.loadAllRequiredOs();
    }

    public void loadAllRequiredOs() {
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Players").getKeys(false).size(); i++) {
            this.reqForPlayerKills.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Players").getInt("Players_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Mobs").getKeys(false).size(); i++) {
            this.reqForMobKills.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Mobs").getInt("Mobs_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Time").getKeys(false).size(); i++) {
            this.reqForTimeSpent.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Time").getLong("Time_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").getKeys(false).size(); i++) {
            this.reqForMinedBlocks.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Gornik").getInt("Gornik_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Fish").getKeys(false).size(); i++) {
            this.reqForFishedItems.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Fish").getInt("Fish_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Chest").getKeys(false).size(); i++) {
            this.reqForOpenedChests.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Chest").getInt("Chest_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Upgrades").getKeys(false).size(); i++) {
            this.reqForPositiveUpgrades.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Upgrades").getInt("Upgrades_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Nies").getKeys(false).size(); i++) {
            this.reqForPickedUpNies.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Nies").getInt("Nies_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Metins").getKeys(false).size(); i++) {
            this.reqForDestroyedMetins.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Metins").getInt("Metins_" + i));
        }
        for (int i = 1; i <= rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Trees").getKeys(false).size(); i++) {
            this.reqForMinedTrees.put(i, rpgcore.getConfig().getConfigurationSection("Osiagniecia").getConfigurationSection("Trees").getInt("Trees_" + i));
        }
    }

    public void osGuiMain(final Player player) {
        final UUID uuid = player.getUniqueId();
        final OsUser osUser = this.find(uuid).getOsUser();
        final Inventory gui = Bukkit.createInventory(null, 2*9, Utils.format("&6&lOsiagniecia"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 8).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Zabici Gracze").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiagniec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getPlayerKills() + " &7/ &6" + this.reqForPlayerKills.get(osUser.getPlayerKillsProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getPlayerKills(), this.reqForPlayerKills.get(osUser.getPlayerKillsProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(1, new ItemBuilder(Material.GOLD_SWORD).setName("&6Zabite Potwory").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getMobKills() + " &7/ &6" + this.reqForMobKills.get(osUser.getMobKillsProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getMobKills(), this.reqForMobKills.get(osUser.getMobKillsProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(2, new ItemBuilder(Material.WATCH).setName("&6Spedzony Czas").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + Utils.durationToString(osUser.getTimeSpent(), false) + " &7/ &6" + Utils.durationToString(this.reqForTimeSpent.get(osUser.getTimeSpentProgress() + 1), false) + " &7(&6" + String.format("%.2f", Utils.convertLongsToPercentage(osUser.getTimeSpent(), this.reqForTimeSpent.get(osUser.getTimeSpentProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(3, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&6Wykopane Bloki").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getMinedBlocks() + " &7/ &6" + this.reqForMinedBlocks.get(osUser.getMinedBlocksProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getMinedBlocks(), this.reqForMinedBlocks.get(osUser.getMinedBlocksProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(4, new ItemBuilder(Material.FISHING_ROD).setName("&6Udane Polowy").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getFishedItems() + " &7/ &6" + this.reqForFishedItems.get(osUser.getFishedItemsProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getFishedItems(), this.reqForFishedItems.get(osUser.getFishedItemsProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(5, new ItemBuilder(Material.CHEST).setName("&6Otwarte Skrzynki").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getOpenedChests() + " &7/ &6" + this.reqForOpenedChests.get(osUser.getOpenedChestsProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getOpenedChests(), this.reqForOpenedChests.get(osUser.getOpenedChestsProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(6, new ItemBuilder(Material.ANVIL).setName("&6Pomyslne Ulepszenia u Kowala").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getPositiveUpgrades() + " &7/ &6" + this.reqForPositiveUpgrades.get(osUser.getPositiveUpgradesProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getPositiveUpgrades(), this.reqForPositiveUpgrades.get(osUser.getPositiveUpgradesProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(7, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&6Znalezione Niesamowite Przedmioty").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getPickedUpNies() + " &7/ &6" + this.reqForPickedUpNies.get(osUser.getPickedUpNiesProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getPickedUpNies(), this.reqForPickedUpNies.get(osUser.getPickedUpNiesProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(8, new ItemBuilder(Material.NETHER_STAR).setName("&6Zniszczone Kamienie Metin").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getDestroyedMetins() + " &7/ &6" + this.reqForDestroyedMetins.get(osUser.getDestroyedMetinsProgress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getDestroyedMetins(), this.reqForDestroyedMetins.get(osUser.getDestroyedMetinsProgress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        gui.setItem(9, new ItemBuilder(Material.IRON_AXE).setName("&6Wykopane Drewno").setLore(Arrays.asList(
                "&8&oKliknij, zeby zobaczyc to drzewko osiganiec",
                " ",
                "&7Postep do nastepnego OsiagnieciaCommand",
                "&6" + osUser.getMinedTrees() + " &7/ &6" + this.reqForMinedTrees.get(osUser.getMinedTreesProogress() + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(osUser.getMinedTrees(), this.reqForMinedTrees.get(osUser.getMinedTreesProogress() + 1))) + "%&7)"
        )).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }


    public void openOsGuiCategory(final Player player, final int category) {
        final UUID uuid = player.getUniqueId();
        final OsUser osUser = this.find(uuid).getOsUser();
        Inventory gui;
        switch (category) {
            case 0:
                // PLAYER KILLS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Zabici Gracze"));
                final int playerKillsProgress = osUser.getPlayerKillsProgress();
                final int playerKills = osUser.getPlayerKills();

                for (int i = 0; i < this.reqForPlayerKills.size(); i++) {
                    if (i >= playerKillsProgress) {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Zabici Gracze #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + playerKills + " &7/ &6" + this.reqForPlayerKills.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(playerKills, this.reqForPlayerKills.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("P" + i).getItemName() + " x" + OsRewards.getByName("P" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6Zabici Gracze #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 1:
                // MOB KILLS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Zabite Stwory"));
                final int mobKillsProgress = osUser.getMobKillsProgress();
                final int mobKills = osUser.getMobKills();

                for (int i = 0; i < this.reqForMobKills.size(); i++) {
                    if (i >= mobKillsProgress) {
                        gui.setItem(i, new ItemBuilder(Material.GOLD_SWORD).setName("&6Zabite Stwory #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + mobKills + " &7/ &6" + this.reqForMobKills.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(mobKills, this.reqForMobKills.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("M" + i).getItemName() + " x" + OsRewards.getByName("M" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.GOLD_SWORD).setName("&6Zabite Stwory #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 2:
                // TIME SPENT
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Spedzony Czas"));
                final int timeSpentProgress = osUser.getTimeSpentProgress();
                final long timeSpent = osUser.getTimeSpent();

                for (int i = 0; i < this.reqForTimeSpent.size(); i++) {
                    if (i >= timeSpentProgress) {
                        gui.setItem(i, new ItemBuilder(Material.WATCH).setName("&6Spedzony Czas #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + Utils.durationToString(timeSpent, false) + " &7/ &6" + Utils.durationToString(this.reqForTimeSpent.get(i + 1), false) + " &7(&6" + String.format("%.2f", Utils.convertLongsToPercentage(timeSpent, this.reqForTimeSpent.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("T" + i).getItemName() + " x" + OsRewards.getByName("T" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.WATCH).setName("&6Spedzony Czas #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);

                return;
            case 3:
                // MINED BLOCKS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Wykopane Bloki"));
                final int minedBlocksProgress = osUser.getMinedBlocksProgress();
                final int minedBlocks = osUser.getMinedBlocks();

                for (int i = 0; i < this.reqForMinedBlocks.size(); i++) {
                    if (i >= minedBlocksProgress) {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&6Wykopane Bloki #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + minedBlocks + " &7/ &6" + this.reqForMinedBlocks.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(minedBlocks, this.reqForMinedBlocks.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("B" + i).getItemName() + " x" + OsRewards.getByName("B" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&6Wykopane Bloki #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);

                return;
            case 4:
                // FISHED ITEMS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Udane Polowy"));
                final int fishedItemsProgress = osUser.getFishedItemsProgress();
                final int fishedItems = osUser.getFishedItems();

                for (int i = 0; i < this.reqForFishedItems.size(); i++) {
                    if (i >= fishedItemsProgress) {
                        gui.setItem(i, new ItemBuilder(Material.FISHING_ROD).setName("&6Udane Polowy #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + fishedItems + " &7/ &6" + this.reqForFishedItems.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(fishedItems, this.reqForFishedItems.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("F" + i).getItemName() + " x" + OsRewards.getByName("F" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.FISHING_ROD).setName("&6Udane Polowy #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 5:
                // OPENED CHESTS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Otwarte Skrzynki"));
                final int openedChestsProgress = osUser.getOpenedChestsProgress();
                final int openedChests = osUser.getOpenedChests();

                for (int i = 0; i < this.reqForOpenedChests.size(); i++) {
                    if (i >= openedChestsProgress) {
                        gui.setItem(i, new ItemBuilder(Material.CHEST).setName("&6Otwarte Skrzynki #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + openedChests + " &7/ &6" + this.reqForOpenedChests.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(openedChests, this.reqForOpenedChests.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("C" + i).getItemName() + " x" + OsRewards.getByName("C" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.CHEST).setName("&6Otwarte Skrzynki #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 6:
                // POSITIVE UPGRADES
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Pomyslne Ulepszenia"));
                final int positiveUpgradesProgress = osUser.getPositiveUpgradesProgress();
                final int positiveUpgrades = osUser.getPositiveUpgrades();

                for (int i = 0; i < this.reqForPositiveUpgrades.size(); i++) {
                    if (i >= positiveUpgradesProgress) {
                        gui.setItem(i, new ItemBuilder(Material.ANVIL).setName("&6Pomyslne Ulepszenia #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + positiveUpgrades + " &7/ &6" + this.reqForPositiveUpgrades.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(positiveUpgrades, this.reqForPositiveUpgrades.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("U" + i).getItemName() + " x" + OsRewards.getByName("U" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.ANVIL).setName("&6Pomyslne Ulepszenia #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 7:
                // PICKED UP NIES
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Znalezione Niesy"));
                final int pickedUpNiesProgress = osUser.getPickedUpNiesProgress();
                final int pickedUpNies = osUser.getPickedUpNies();

                for (int i = 0; i < this.reqForPickedUpNies.size(); i++) {
                    if (i >= pickedUpNiesProgress) {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&6Znalezione Niesamowite Przedmioty #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + pickedUpNies + " &7/ &6" + this.reqForPickedUpNies.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(pickedUpNies, this.reqForPickedUpNies.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("N" + i).getItemName() + " x" + OsRewards.getByName("N" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&6Znalezione Niesamowite Przedmioty #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 8:
                // DESTROYED METINS
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Zniszczone Metiny"));
                final int destroyedMetinsProgress = osUser.getDestroyedMetinsProgress();
                final int destroyedMetins = osUser.getDestroyedMetins();

                for (int i = 0; i < this.reqForDestroyedMetins.size(); i++) {
                    if (i >= destroyedMetinsProgress) {
                        gui.setItem(i, new ItemBuilder(Material.NETHER_STAR).setName("&6Zniszczone Kamienie Metin #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + destroyedMetins + " &7/ &6" + this.reqForDestroyedMetins.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(destroyedMetins, this.reqForDestroyedMetins.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("D" + i).getItemName() + " x" + OsRewards.getByName("D" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.NETHER_STAR).setName("&6Zniszczone Kamienie Metin #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
                return;
            case 9:
                // MINED TREES
                gui = Bukkit.createInventory(null, 18, Utils.format("&6Osiagniecia - Wykopane Drewno"));
                final int minedTreesProgress = osUser.getMinedTreesProogress();
                final int minedTrees = osUser.getMinedTrees();

                for (int i = 0; i < this.reqForMinedTrees.size(); i++) {
                    if (i >= minedTreesProgress) {
                        gui.setItem(i, new ItemBuilder(Material.LOG).setName("&6Wykopane Drewno #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&6" + minedTrees + " &7/ &6" + this.reqForMinedTrees.get(i + 1) + " &7(&6" + String.format("%.2f", Utils.convertIntegersToPercentage(minedTrees, this.reqForMinedTrees.get(i + 1))) + "%&7)",
                                " ",
                                "&f&lNagroda",
                                "&8- " + OsRewards.getByName("L" + i).getItemName() + " x" + OsRewards.getByName("L" + i).getAmount()
                        )).hideFlag().toItemStack().clone());
                    } else {
                        gui.setItem(i, new ItemBuilder(Material.LOG).setName("&6Wykopane Drewno #" + (i + 1)).setLore(Arrays.asList(
                                "&7Postep osiagniecia:",
                                "&a&lWykonano!"
                        )).addGlowing().toItemStack().clone());
                    }
                }

                player.openInventory(gui);
        }
    }


    public void add(OsObject osObject) {
        this.userMap.put(osObject.getID(), osObject);
    }

    public OsObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new OsObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<OsObject> getOsObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public HashMap<Integer, Integer> getReqForPlayerKills() {
        return reqForPlayerKills;
    }
    public HashMap<Integer, Integer> getReqForMobKills() {
        return reqForMobKills;
    }
    public HashMap<Integer, Long> getReqForTimeSpent() {
        return reqForTimeSpent;
    }
    public HashMap<Integer, Integer> getReqForMinedBlocks() {
        return reqForMinedBlocks;
    }
    public HashMap<Integer, Integer> getReqForFishedItems() {
        return reqForFishedItems;
    }
    public HashMap<Integer, Integer> getReqForOpenedChests() {
        return reqForOpenedChests;
    }
    public HashMap<Integer, Integer> getReqForPositiveUpgrades() {
        return reqForPositiveUpgrades;
    }
    public HashMap<Integer, Integer> getReqForPickedUpNies() {
        return reqForPickedUpNies;
    }
    public HashMap<Integer, Integer> getReqForDestroyedMetins() {
        return reqForDestroyedMetins;
    }
    public HashMap<Integer, Integer> getReqForMinedTrees() {
        return reqForMinedTrees;
    }


}
