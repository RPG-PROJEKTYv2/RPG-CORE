package rpg.rpgcore.osiagniecia;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.osiagniecia.enums.*;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class OsManager {
    private final Map<UUID, OsUser> userMap;

    public OsManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllOs();
    }

    public void openOsGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&6&lOsiagniecia"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 45; i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
            }
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND_SWORD).setName("&cZabici Gracze").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getGracze() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(11, new ItemBuilder(Material.GOLD_SWORD).setName("&cZabite Potwory").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getMoby() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(12, new ItemBuilder(Material.NETHER_STAR).setName("&cZniszczone Kamienie Metin").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getMetiny() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());

        gui.setItem(14, new ItemBuilder(Material.CHEST).setName("&cOtworzone Skrzynki &8(&4&lWKROTCE&8)").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getSkrzynki() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());
        gui.setItem(15, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cZnalezione Niesamowite Przedmioty").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getNiesy() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());
        gui.setItem(16, new ItemBuilder(Material.ANVIL).setName("&cUdane Ulepszenia u Kowala &8(&4&lWKROTCE&8)").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getUlepszenia() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).toItemStack().clone());

        gui.setItem(28, new ItemBuilder(Material.FISHING_ROD).setName("&cUdane Polowy").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getRybak() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());
        gui.setItem(29, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&cWydobyte Rudy").setLore(Arrays.asList(
                "&7Wykonane: &6" + osUser.getGornik() + "&7/&69",
                "",
                "&8Kliknij, zeby zobaczyc wiecej szczegolow."
        )).hideFlag().toItemStack().clone());

        player.openInventory(gui);
    }

    public void openOsGracze(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cZabici Gracze"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getGracze()) {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_SWORD).setName("&cZabici Gracze #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsGracze.getByMission(i + 1).getReqProgress() + "&7/&f" + OsGracze.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsGracze.getByMission(i + 1).getReward().getAmount() + "x " + OsGracze.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_SWORD).setName("&cZabici Gracza #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getGraczeProgress() + "&7/&f" + OsGracze.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getGraczeProgress(), OsGracze.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsGracze.getByMission(i + 1).getReward().getAmount() + "x " + OsGracze.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsMoby(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cZabite Potwory"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getMoby()) {
                gui.setItem(9 + i, new ItemBuilder(Material.GOLD_SWORD).setName("&cZabite Potwory #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsMoby.getByMission(i + 1).getReqProgress() + "&7/&f" + OsMoby.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsMoby.getByMission(i + 1).getReward().getAmount() + "x " + OsMoby.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.GOLD_SWORD).setName("&cZabite Potwory #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getMobyProgress() + "&7/&f" + OsMoby.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getMobyProgress(), OsMoby.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsMoby.getByMission(i + 1).getReward().getAmount() + "x " + OsMoby.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsMetiny(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cZniszczone Metiny"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getMetiny()) {
                gui.setItem(9 + i, new ItemBuilder(Material.NETHER_STAR).setName("&cZniszczone Kamienie Metin #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsMetiny.getByMission(i + 1).getReqProgress() + "&7/&f" + OsMetiny.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsMetiny.getByMission(i + 1).getReward().getAmount() + "x " + OsMetiny.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.NETHER_STAR).setName("&cZniszczone Kamienie Metin #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getMetinyProgress() + "&7/&f" + OsMetiny.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getMetinyProgress(), OsMetiny.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsMetiny.getByMission(i + 1).getReward().getAmount() + "x " + OsMetiny.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsSkrzynki(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cOtworzone Skrzynie"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getSkrzynki()) {
                gui.setItem(9 + i, new ItemBuilder(Material.CHEST).setName("&cOtworzone Skrzynie #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsSkrzynki.getByMission(i + 1).getReqProgress() + "&7/&f" + OsSkrzynki.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsSkrzynki.getByMission(i + 1).getReward().getAmount() + "x " + OsSkrzynki.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.CHEST).setName("&cOtworzone Skrzynie #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getSkrzynkiProgress() + "&7/&f" + OsSkrzynki.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getSkrzynkiProgress(), OsSkrzynki.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsSkrzynki.getByMission(i + 1).getReward().getAmount() + "x " + OsSkrzynki.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsNiesy(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cZnalezione Niesy"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getNiesy()) {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cZnalezione Niesamowite Przedmioty #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsNiesy.getByMission(i + 1).getReqProgress() + "&7/&f" + OsNiesy.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsNiesy.getByMission(i + 1).getReward().getAmount() + "x " + OsNiesy.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_BLOCK).setName("&cZnalezione Niesamowite Przedmioty #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getNiesyProgress() + "&7/&f" + OsNiesy.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getNiesyProgress(), OsNiesy.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsNiesy.getByMission(i + 1).getReward().getAmount() + "x " + OsNiesy.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsUlepszenia(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cUdane Ulepszenia"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getUlepszenia()) {
                gui.setItem(9 + i, new ItemBuilder(Material.ANVIL).setName("&cUdane Ulepszenia #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsUlepszenia.getByMission(i + 1).getReqProgress() + "&7/&f" + OsUlepszenia.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsUlepszenia.getByMission(i + 1).getReward().getAmount() + "x " + OsUlepszenia.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.ANVIL).setName("&cUdane Ulepszenia #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getUlepszeniaProgress() + "&7/&f" + OsUlepszenia.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getUlepszeniaProgress(), OsUlepszenia.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsUlepszenia.getByMission(i + 1).getReward().getAmount() + "x " + OsUlepszenia.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsRybak(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cUdane Polowy"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getRybak()) {
                gui.setItem(9 + i, new ItemBuilder(Material.FISHING_ROD).setName("&cUdane Polowy #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsRybak.getByMission(i + 1).getReqProgress() + "&7/&f" + OsRybak.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsRybak.getByMission(i + 1).getReward().getAmount() + "x " + OsRybak.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.FISHING_ROD).setName("&cUdane Polowy #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getRybakProgress() + "&7/&f" + OsRybak.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getRybakProgress(), OsRybak.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsRybak.getByMission(i + 1).getReward().getAmount() + "x " + OsRybak.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public void openOsGornik(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lOsiagniecia &8>> &cWydobyte Rudy"));
        final OsUser osUser = this.find(player.getUniqueId());

        for (int i = 0; i < 27; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
        }

        for (int i = 0; i < 9; i++) {
            if (i < osUser.getGornik()) {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&cWydobyte Rudy #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + OsGornik.getByMission(i + 1).getReqProgress() + "&7/&f" + OsGornik.getByMission(i + 1).getReqProgress(),
                        "&7Status: &a&lODEBRANE",
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m----",
                        "  &8- &6" + OsGornik.getByMission(i + 1).getReward().getAmount() + "x " + OsGornik.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", true).addGlowing().toItemStack().clone());
            } else {
                gui.setItem(9 + i, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("&cWydobyte Rudy #" + (i + 1)).setLore(Arrays.asList(
                        "&7Postep: &f" + osUser.getGornikProgress() + "&7/&f" + OsGornik.getByMission(i + 1).getReqProgress(),
                        "&7Status: " + this.getProgress(osUser.getGornikProgress(), OsGornik.getByMission(i + 1).getReqProgress()),
                        "",
                        "&8&m---- &7Nagroda za odebranie &8&m ----",
                        "  &8- &6" + OsGornik.getByMission(i + 1).getReward().getAmount() + "x " + OsGornik.getByMission(i + 1).getReward().getItemMeta().getDisplayName()
                )).addTagInt("mission", i+1).addTagBoolean("done", false).hideFlag().toItemStack().clone());
            }
        }
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        gui.setItem(22, new ItemBuilder(Material.ARROW).setName("&cPowrot").toItemStack());
        gui.setItem(23, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());

        player.openInventory(gui);
    }

    public String getProgress(final int progress, final int reqAmount) {
        final double percent = DoubleUtils.round((double) progress / (double) reqAmount * 100.0, 2);
        if (percent < 10.0) {
            return "&8[&c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 20.0) {
            return "&8[&a⬛&c⬛⬛⬛⬛⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 30.0) {
            return "&8[&a⬛⬛&c⬛⬛⬛⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 40.0) {
            return "&8[&a⬛⬛⬛&c⬛⬛⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 50.0) {
            return "&8[&a⬛⬛⬛⬛&c⬛⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 60.0) {
            return "&8[&a⬛⬛⬛⬛⬛&c⬛⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 70.0) {
            return "&8[&a⬛⬛⬛⬛⬛⬛&c⬛⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 80.0) {
            return "&8[&a⬛⬛⬛⬛⬛⬛⬛&c⬛⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 90.0) {
            return "&8[&a⬛⬛⬛⬛⬛⬛⬛⬛&c⬛⬛&8] &f" + percent + "%";
        }
        if (percent < 100.0) {
            return "&8[&a⬛⬛⬛⬛⬛⬛⬛⬛⬛&c⬛&8] &f" + percent + "%";
        }
        if (percent >= 100.0) {
            return "&8[&a⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛&8] &f100.00%";
        }
        return "";
    }


    public OsUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final OsUser osUser) {
        this.userMap.put(osUser.getUuid(), osUser);
    }

    public void set(final UUID uuid, final OsUser osUser) {
        this.userMap.replace(uuid, osUser);
    }

    public ImmutableSet<OsUser> getOsUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
