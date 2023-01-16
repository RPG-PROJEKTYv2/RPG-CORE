package rpg.rpgcore.artefakty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.lvl.artefaktyZaLvL.ArtefaktyZaLvl;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ArtefaktyCommand extends CommandAPI {
    public ArtefaktyCommand() {
        super("artefakty");
        this.setAliases(Arrays.asList("arte"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }


    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length == 0) {
            this.openArtefaktGUI(player);
            return;
        }
        if (RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankUser().getRankType().getPriority() >= 90 && RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).isAdminCodeLogin()) {
            if (args.length == 1) {
                if (args[0].equals("list")) {
                    Artefakty.listAll(player);
                    return;
                }
            }
            if (args.length < 2) {
                player.sendMessage(Utils.poprawneUzycie("artefakty <gracz> <artefakt>"));
                return;
            }
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz o nicku &4" + args[0] + " &cnie jest online!"));
                return;
            }
            final ItemStack artefakt = Artefakty.getArtefakt(args[1], target);
            if (artefakt == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cArtefakt o nazwie &4" + args[1] + " &cnie istnieje!"));
                return;
            }
            target.getInventory().addItem(artefakt);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano artefakt &2" + artefakt.getItemMeta().getDisplayName() + " &adla gracza &2" + target.getName() + "&a!"));
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("artefakty"));
    }

    private void openArtefaktGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 45, Utils.format("&4&lArtefakty"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 2).setName(" ").toItemStack());
            }
        }
        final ArtefaktyZaLvl arteZaLvl = RPGCORE.getInstance().getArtefaktyZaLvlManager().getArtefaktyZaLvl();
        final List<String> poziom50Names = new ArrayList<>(5);
        if (!arteZaLvl.getPoziom50().getGracze().isEmpty()) {
            poziom50Names.addAll(arteZaLvl.getPoziom50().getGracze());
        }
        if (poziom50Names.size() < 5) {
            for (int i = poziom50Names.size(); i < 5; i++) {
                poziom50Names.add("Brak");
            }
        }
        final List<String> poziom60Names = new ArrayList<>(5);
        if (!arteZaLvl.getPoziom60().getGracze().isEmpty()) {
            poziom60Names.addAll(arteZaLvl.getPoziom60().getGracze());
        }
        if (poziom60Names.size() < 5) {
            for (int i = poziom60Names.size(); i < 5; i++) {
                poziom60Names.add("Brak");
            }
        }
        final List<String> poziom70Names = new ArrayList<>(5);
        if (!arteZaLvl.getPoziom70().getGracze().isEmpty()) {
            poziom70Names.addAll(arteZaLvl.getPoziom70().getGracze());
        }
        if (poziom70Names.size() < 5) {
            for (int i = poziom70Names.size(); i < 5; i++) {
                poziom70Names.add("Brak");
            }
        }
        final List<String> poziom80Names = new ArrayList<>(4);
        if (!arteZaLvl.getPoziom80().getGracze().isEmpty()) {
            poziom80Names.addAll(arteZaLvl.getPoziom80().getGracze());
        }
        if (poziom80Names.size() < 4) {
            for (int i = poziom80Names.size(); i < 4; i++) {
                poziom80Names.add("Brak");
            }
        }
        final List<String> poziom90Names = new ArrayList<>(4);
        if (!arteZaLvl.getPoziom90().getGracze().isEmpty()) {
            poziom90Names.addAll(arteZaLvl.getPoziom90().getGracze());
        }
        if (poziom90Names.size() < 4) {
            for (int i = poziom90Names.size(); i < 4; i++) {
                poziom90Names.add("Brak");
            }
        }
        final List<String> poziom100Names = new ArrayList<>(4);
        if (!arteZaLvl.getPoziom100().getGracze().isEmpty()) {
            poziom100Names.addAll(arteZaLvl.getPoziom100().getGracze());
        }
        if (poziom100Names.size() < 4) {
            for (int i = poziom100Names.size(); i < 4; i++) {
                poziom100Names.add("Brak");
            }
        }
        final List<String> poziom110Names = new ArrayList<>(4);
        if (!arteZaLvl.getPoziom110().getGracze().isEmpty()) {
            poziom110Names.addAll(arteZaLvl.getPoziom110().getGracze());
        }
        if (poziom110Names.size() < 4) {
            for (int i = poziom110Names.size(); i < 4; i++) {
                poziom110Names.add("Brak");
            }
        }
        final List<String> rybakNames = new ArrayList<>(4);
        if (!arteZaLvl.getRybak().getGracze().isEmpty()) {
            rybakNames.addAll(arteZaLvl.getRybak().getGracze());
        }
        if (rybakNames.size() < 4) {
            for (int i = rybakNames.size(); i < 4; i++) {
                rybakNames.add("Brak");
            }
        }
        final List<String> gornikNames = new ArrayList<>(4);
        if (!arteZaLvl.getGornik().getGracze().isEmpty()) {
            gornikNames.addAll(arteZaLvl.getGornik().getGracze());
        }
        if (gornikNames.size() < 4) {
            for (int i = gornikNames.size(); i < 4; i++) {
                gornikNames.add("Brak");
            }
        }
        
        
        
            
        gui.setItem(10, new ItemBuilder(Material.SIGN).setName("&cBon Srednich Obrazen").setLore(Arrays.asList(
                "",
                "&4&lBONUS",
                "",
                "&7Sposob Zdobycia: &cOsiagnij 50 poziom",
                "&7Ilosc do zdobycia: &c5 sztuk",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom50Names.get(0) + " &8(&c15%&8)",
                "&62." + " &6" + poziom50Names.get(1) + " &8(&c10%&8)",
                "&e3." + " &e" + poziom50Names.get(2) + " &8(&c10%&8)",
                "&74." + " &7" + poziom50Names.get(3) + " &8(&c5%&8)",
                "&75." + " &7" + poziom50Names.get(4) + " &8(&c5%&8)"
        )).toItemStack());
        gui.setItem(11, new ItemBuilder(Material.SIGN).setName("&2Bon Sredniej Defensywy").setLore(Arrays.asList(
                "",
                "&4&lBONUS",
                "",
                "&7Sposob Zdobycia: &cOsiagnij 60 poziom",
                "&7Ilosc do zdobycia: &c5 sztuk",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom60Names.get(0) + " &8(&215%&8)",
                "&62." + " &6" + poziom60Names.get(1) + " &8(&210%&8)",
                "&e3." + " &e" + poziom60Names.get(2) + " &8(&210%&8)",
                "&74." + " &7" + poziom60Names.get(3) + " &8(&25%&8)",
                "&75." + " &7" + poziom60Names.get(4) + " &8(&25%&8)"
        )).toItemStack());
        gui.setItem(12, new ItemBuilder(Material.SIGN).setName("&5Bon Szansy Na Cios Krytyczny").setLore(Arrays.asList(
                "",
                "&4&lBONUS",
                "",
                "&7Sposob Zdobycia: &cOsiagnij 70 poziom",
                "&7Ilosc do zdobycia: &c5 sztuk",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom70Names.get(0) + " &8(&515%&8)",
                "&62." + " &6" + poziom70Names.get(1) + " &8(&510%&8)",
                "&e3." + " &e" + poziom70Names.get(2) + " &8(&510%&8)",
                "&74." + " &7" + poziom70Names.get(3) + " &8(&55%&8)",
                "&75." + " &7" + poziom70Names.get(4) + " &8(&55%&8)"
        )).toItemStack());

        gui.setItem(13, new ItemBuilder(Artefakty.A1.getItem().clone()).setLoreCrafting(Artefakty.A1.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 80 poziom",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom80Names.get(0),
                "&62." + " &6" + poziom80Names.get(1),
                "&e3." + " &e" + poziom80Names.get(2),
                "&74." + " &7" + poziom80Names.get(3)
        )).toItemStack());
        gui.setItem(14, new ItemBuilder(Artefakty.A3.getItem().clone()).setLoreCrafting(Artefakty.A3.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 90 poziom",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom90Names.get(0),
                "&62." + " &6" + poziom90Names.get(1),
                "&e3." + " &e" + poziom90Names.get(2),
                "&74." + " &7" + poziom90Names.get(3)
        )).toItemStack());
        gui.setItem(15, new ItemBuilder(Artefakty.A2.getItem().clone()).setLoreCrafting(Artefakty.A2.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 100 poziom",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom100Names.get(0),
                "&62." + " &6" + poziom100Names.get(1),
                "&e3." + " &e" + poziom100Names.get(2),
                "&74." + " &7" + poziom100Names.get(3)
        )).toItemStack());
        gui.setItem(16, new ItemBuilder(Artefakty.A6.getItem().clone()).setLoreCrafting(Artefakty.A6.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 110 poziom",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + poziom110Names.get(0),
                "&62." + " &6" + poziom110Names.get(1),
                "&e3." + " &e" + poziom110Names.get(2),
                "&74." + " &7" + poziom110Names.get(3)
        )).toItemStack());

        gui.setItem(28, new ItemBuilder(Artefakty.A4.getItem().clone()).setLoreCrafting(Artefakty.A4.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cUkoncz Kampanie Rybaka",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + rybakNames.get(0),
                "&62." + " &6" + rybakNames.get(1),
                "&e3." + " &e" + rybakNames.get(2),
                "&74." + " &7" + rybakNames.get(3)
        )).toItemStack());
        gui.setItem(29, new ItemBuilder(Artefakty.A5.getItem().clone()).setLoreCrafting(Artefakty.A5.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cUkoncz Kampanie Gornika",
                "&7Ilosc do zdobycia: &c4 sztuki",
                "&7Zdobyte przez:",
                "&41." + " &4" + gornikNames.get(0),
                "&62." + " &6" + gornikNames.get(1),
                "&e3." + " &e" + gornikNames.get(2),
                "&74." + " &7" + gornikNames.get(3)
        )).toItemStack());


        player.openInventory(gui);
    }
}
