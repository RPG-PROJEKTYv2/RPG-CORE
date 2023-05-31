package rpg.rpgcore.commands.player.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class ProfileCommand extends CommandAPI {
    public ProfileCommand() {
        super("profile");
        this.setAliases(Arrays.asList("profil", "staty", "stats", "statystyki"));
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.GRACZ);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
            if (args.length < 1) {
                this.createProfileGUI(player);
                return;
            }
            if (args.length == 1) {
                final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);

                if (offlineTarget == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza!"));
                    return;
                }

                if (RPGCORE.getInstance().getUserManager().find(offlineTarget.getUniqueId()) == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza!"));
                    return;
                }

                this.createProfileGUI(player, offlineTarget.getUniqueId(), offlineTarget.getName());
                return;
            }
            player.sendMessage(Utils.poprawneUzycie("profile <player>"));
            return;
        }
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("profile"));
            return;
        }

        this.createProfileGUI(player);
    }

    private void createProfileGUI(final Player player) {
        final BonusesUser user = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&4&lProfil &6&l" + player.getName()));

        for (int i = 0; i < 54; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        if (player.getInventory().getHelmet() != null) {
            gui.setItem(11, player.getInventory().getHelmet().clone());
        } else {
            gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Helmu").toItemStack());
        }
        if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
            gui.setItem(19, player.getItemInHand().clone());
        } else {
            gui.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Przedmiotu").toItemStack());
        }
        if (player.getInventory().getChestplate() != null) {
            gui.setItem(20, player.getInventory().getChestplate().clone());
        } else {
            gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Zbroi").toItemStack());
        }
        if (player.getInventory().getLeggings() != null) {
            gui.setItem(29, player.getInventory().getLeggings().clone());
        } else {
            gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Spodni").toItemStack());
        }
        if (player.getInventory().getBoots() != null) {
            gui.setItem(38, player.getInventory().getBoots().clone());
        } else {
            gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Butow").toItemStack());
        }

        gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&c&lMisje").setLore(Arrays.asList("&8Kliknij, aby zobaczyc postep oraz aktualne misje")).toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&6&l" + player.getName()).setSkullOwner(player.getName()).toItemStack());
        /*if (RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet().getItem() != null) {
            gui.setItem(31, RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet().getItem().clone());
        } else {
            gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Zwierzaka").toItemStack());
        }*/

        gui.setItem(24, new ItemBuilder(Material.IRON_SWORD).setName("&4Statystyki Obrazen").setLore(Arrays.asList(
                "&7Srednie Obrazenia: &f" + user.getSrednieobrazenia() + "%",
                "&7Dodatkowe Obrazenia: &f" + user.getDodatkoweobrazenia(),
                "&7Silny Na Ludzi: &f" + user.getSilnynaludzi() + "%",
                "&7Silny Na Potwory: &f" + user.getSilnynapotwory() + "%",
                "&7Szansa Na Cios Krytyczny: &f" + (user.getSzansanakryta() / 4) + "%",
                "&7Szansa Na Wzmocniony Cios Krytyczny: &f" + user.getSzansanawzmocnieniekryta() + "%",
                "&7Wzmocnienie Ciosu Krytycznego: &f" + user.getWzmocnienieKryta() + "%",
                "&7Przeszycie Bloku Ciosu: &f" + user.getPrzeszyciebloku() + "%",
                "&7Zmniejszone Obrazenia: &f" + user.getMinussrednieobrazenia() + "%",
                "&7Zmniejszone Obrazenia W Ludzi: &f" + user.getMinusobrazenianaludzi() + "%",
                "&7Zmniejszone Obrazenia W Potwory: &f" + user.getMinusobrazenianamoby() + "%"
        )).addGlowing().toItemStack().clone());

        gui.setItem(25, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&aStatystyki Defensywy").setLore(Arrays.asList(
                "&7Srednia Defensywa: &f" + user.getSredniadefensywa() + "%",
                "&7Defensywa Przeciwko Ludziom: &f" + user.getDefnaludzi() + "%",
                "&7Defensywa Przeciwko Potworom: &f" + user.getDefnamoby() + "%",
                "&7Blok Ciosu: &f" + user.getBlokciosu() + "%",
                "&7Zmniejszona Defensywa: &f" + user.getMinussredniadefensywa() + "%",
                "&7Zmniejszona Defensywa Przeciwko Ludziom: &f" + user.getMinusdefnaludzi() + "%",
                "&7Zmniejszona Defensywa Przeciwko Potworom: &f" + user.getMinusdefnamoby() + "%"
        )).addGlowing().toItemStack().clone());

        gui.setItem(33, new ItemBuilder(Material.ENCHANTMENT_TABLE).setName("&dStatystyki Magiczne/Inne").setLore(Arrays.asList(
                "&7True DMG: &f" + user.getTruedamage(),
                "&7Szansa Na Krwawienie: &f" + user.getSzansanakrwawienie() + "%",
                "&7Szczescie: &f" + user.getSzczescie(),
                "&7Szansa Na Spowolnienie: &f" + user.getSpowolnienie() + "%",
                "&7Szansa Na Oslepienie: &f" + user.getOslepienie() + "%",
                "&7Dodatkowy EXP: &f" + user.getDodatkowyExp() + "%",
                "&7Szansa Na Przebicie Pancerza: &f" + user.getPrzebiciePancerza() + "%",
                "&7Wampiryzm: &f" + user.getWampiryzm() + "%",
                "&7Dodatkowe Obrazenia W Kamienie Metin: &f" + user.getDmgMetiny()
        )).addGlowing().toItemStack().clone());

        gui.setItem(34, new ItemBuilder(Material.GOLDEN_APPLE).setName("&cStatystyki Zdrowia").setLore(Arrays.asList(
                "&7Aktualne Zdrowie: &f" + String.format("%.0f", player.getHealth()) + "&c❤",
                "&7Maksymalne Zdrowie: &f" + String.format("%.0f", player.getMaxHealth()) + "&c❤",
                "&7Dodatkowe Zdrowie: &f" + String.format("%.0f", user.getDodatkowehp() * 2.0) + "&c❤",
                "&7Dodatkowe Zlote Serca: &f" + String.format("%.0f", user.getDodatkowezlotehp() * 2.0) + "&6❤"
        )).toItemStack().clone());

        gui.setItem(43, new ItemBuilder(Material.DIAMOND_BOOTS).setName("&fStatystyki Predkosci").setLore(Arrays.asList(
                "&7Podstawowa Szybkosc: &f100",
                "&7Szybkosc: &f" + user.getSzybkosc()
        )).addGlowing().toItemStack().clone());

        gui.setItem(48, new ItemBuilder(Material.ITEM_FRAME).setName("&6&lAkcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack().clone());
        gui.setItem(49, new ItemBuilder(Material.SIGN).setName("&6&lBony").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu bonow")).toItemStack().clone());
        gui.setItem(50, new ItemBuilder(Material.LEASH).setName("&6&lAkcesorium Dodatkowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu dodatkowego akcesorium")).toItemStack().clone());

        player.openInventory(gui);
    }

    private void createProfileGUI(final Player player, final UUID targetUUID, final String targetName) throws IOException {
        final BonusesUser user = RPGCORE.getInstance().getBonusesManager().find(targetUUID).getBonusesUser();
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&4&lProfil &6&l" + targetName));

        for (int i = 0; i < 54; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        final ItemStack[] armor = Utils.itemStackArrayFromBase64(RPGCORE.getInstance().getUserManager().find(targetUUID).getInventoriesUser().getArmor());

        if (armor[0] != null && armor[0].getType() != Material.AIR) {
            gui.setItem(11, armor[0].clone());
        } else {
            gui.setItem(11, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Helmu").toItemStack());
        }
        if (armor[1] != null && armor[1].getType() != Material.AIR) {
            gui.setItem(20, armor[1].clone());
        } else {
            gui.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Zbroi").toItemStack());
        }
        if (armor[2] != null && armor[2].getType() != Material.AIR) {
            gui.setItem(29, armor[2].clone());
        } else {
            gui.setItem(29, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Spodni").toItemStack());
        }
        if (armor[3] != null && armor[3].getType() != Material.AIR) {
            gui.setItem(38, armor[3].clone());
        } else {
            gui.setItem(38, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Butow").toItemStack());
        }
        gui.setItem(13, new ItemBuilder(Material.BOOK_AND_QUILL).setName("&c&lMisje").setLore(Arrays.asList("&8Kliknij, aby zobaczyc postep oraz aktualne misje")).toItemStack().clone());
        gui.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName("&6&l" + targetName).setSkullOwner(targetName).toItemStack());
        /*if (RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet().getItem() != null) {
            gui.setItem(31, RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet().getItem().clone());
        } else {
            gui.setItem(31, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&cBrak Zwierzaka").toItemStack());
        }*/

        gui.setItem(24, new ItemBuilder(Material.IRON_SWORD).setName("&4Statystyki Obrazen").setLore(Arrays.asList(
                "&7Srednie Obrazenia: &f" + user.getSrednieobrazenia() + "%",
                "&7Dodatkowe Obrazenia: &f" + user.getDodatkoweobrazenia(),
                "&7Silny Na Ludzi: &f" + user.getSilnynaludzi() + "%",
                "&7Silny Na Potwory: &f" + user.getSilnynapotwory() + "%",
                "&7Szansa Na Cios Krytyczny: &f" + user.getSzansanakryta() + "%",
                "&7Szansa Na Wzmocniony Cios Krytyczny: &f" + user.getSzansanawzmocnieniekryta() + "%",
                "&7Wzmocnienie Ciosu Krytycznego: &f" + user.getWzmocnienieKryta() + "%",
                "&7Przeszycie Bloku Ciosu: &f" + user.getPrzeszyciebloku() + "%",
                "&7Zmniejszone Obrazenia: &f" + user.getMinussrednieobrazenia() + "%",
                "&7Zmniejszone Obrazenia W Ludzi: &f" + user.getMinusobrazenianaludzi() + "%",
                "&7Zmniejszone Obrazenia W Potwory: &f" + user.getMinusobrazenianamoby() + "%"
        )).addGlowing().toItemStack().clone());

        gui.setItem(25, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&aStatystyki Defensywy").setLore(Arrays.asList(
                "&7Srednia Defensywa: &f" + user.getSredniadefensywa() + "%",
                "&7Defensywa Przeciwko Ludziom: &f" + user.getDefnaludzi() + "%",
                "&7Defensywa Przeciwko Potworom: &f" + user.getDefnamoby() + "%",
                "&7Blok Ciosu: &f" + user.getBlokciosu() + "%",
                "&7Zmniejszona Defensywa: &f" + user.getMinussredniadefensywa() + "%",
                "&7Zmniejszona Defensywa Przeciwko Ludziom: &f" + user.getMinusdefnaludzi() + "%",
                "&7Zmniejszona Defensywa Przeciwko Potworom: &f" + user.getMinusdefnamoby() + "%"
        )).addGlowing().toItemStack().clone());

        gui.setItem(33, new ItemBuilder(Material.ENCHANTMENT_TABLE).setName("&dStatystyki Magiczne/Inne").setLore(Arrays.asList(
                "&7True DMG: &f" + user.getTruedamage(),
                "&7Szansa Na Krwawienie: &f" + user.getSzansanakrwawienie() + "%",
                "&7Szczescie: &f" + user.getSzczescie(),
                "&7Szansa Na Spowolnienie: &f" + user.getSpowolnienie() + "%",
                "&7Szansa Na Oslepienie: &f" + user.getOslepienie() + "%",
                "&7Ddatkowy EXP: &f" + user.getDodatkowyExp() + "%",
                "&7Szansa Na Przebicie Pancerza: &f" + user.getPrzebiciePancerza() + "%",
                "&7Wampiryzm: &f" + user.getWampiryzm() + "%"
        )).addGlowing().toItemStack().clone());

        gui.setItem(34, new ItemBuilder(Material.GOLDEN_APPLE).setName("&cStatystyki Zdrowia").setLore(Arrays.asList(
                "&7Dodatkowe Zdrowie: &f" + user.getDodatkowehp() + "&c❤",
                "&7Dodatkowe Zlote Serca: &f" + user.getDodatkowezlotehp() + "&6❤"
        )).toItemStack().clone());

        gui.setItem(43, new ItemBuilder(Material.DIAMOND_BOOTS).setName("&fStatystyki Predkosci").setLore(Arrays.asList(
                "&7Podstawowa Szybkosc: &f100",
                "&7Szybkosc: &f" + user.getSzybkosc()
        )).addGlowing().toItemStack().clone());

        gui.setItem(48, new ItemBuilder(Material.ITEM_FRAME).setName("&6&lAkcesoria Podstawowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu podstawowego akcesorium")).toItemStack().clone());
        gui.setItem(49, new ItemBuilder(Material.SIGN).setName("&6&lBony").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu bonow")).toItemStack().clone());
        gui.setItem(50, new ItemBuilder(Material.LEASH).setName("&6&lAkcesorium Dodatkowe").setLore(Arrays.asList("&8Kliknij, aby otworzyc menu dodatkowego akcesorium")).toItemStack().clone());

        player.openInventory(gui);
    }
}
