package rpg.rpgcore.artefakty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;

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

        gui.setItem(10, new ItemBuilder(Artefakty.A2.getItem().clone()).setLoreCrafting(Artefakty.A2.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 80 poziom",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());
        gui.setItem(11, new ItemBuilder(Artefakty.A6.getItem().clone()).setLoreCrafting(Artefakty.A6.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 90 poziom",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());
        gui.setItem(12, new ItemBuilder(Artefakty.A1.getItem().clone()).setLoreCrafting(Artefakty.A1.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 100 poziom",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());
        gui.setItem(12, new ItemBuilder(Artefakty.A3.getItem().clone()).setLoreCrafting(Artefakty.A3.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cOsiagnij 110 poziom",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());

        gui.setItem(28, new ItemBuilder(Artefakty.A4.getItem().clone()).setLoreCrafting(Artefakty.A4.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cUkoncz Kampanie Rybaka",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());
        gui.setItem(29, new ItemBuilder(Artefakty.A5.getItem().clone()).setLoreCrafting(Artefakty.A5.getItem().clone().getItemMeta().getLore(), Arrays.asList(
                "",
                "&7Sposob Zdobycia: &cUkoncz Kampanie Gornika",
                "&7Ilosc do zdobycia: &c4 sztuk",
                "&7Zdobyte przez:",
                "&41 &cBrak",
                "&62 &cBrak",
                "&e3 &cBrak",
                "&74 &cBrak"
        )).toItemStack());


        player.openInventory(gui);
    }
}
