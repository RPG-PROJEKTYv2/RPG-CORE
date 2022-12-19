package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;

import java.io.IOException;
import java.util.Arrays;

public class CaseCommand extends CommandAPI {
    public CaseCommand() {
        super("case");
        this.setAliases(Arrays.asList("skrzynka", "skrzynki"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("case"));
            return;
        }

        createCaseGUI(player);
    }

    private void createCaseGUI(Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&4&lSkrzynki"));

        gui.setItem(0, GlobalItem.getItem("I1", 1));
        gui.setItem(1, GlobalItem.getItem("I2", 1));
        gui.setItem(2, GlobalItem.getItem("I3", 1));
        gui.setItem(3, GlobalItem.getItem("I4", 1));
        gui.setItem(4, GlobalItem.getItem("I5", 1));
        gui.setItem(5, GlobalItem.getItem("I6", 1));
        gui.setItem(6, GlobalItem.getItem("I21", 1));
        gui.setItem(7, GlobalItem.getItem("I22", 1));
        gui.setItem(8, Skrzynki.getItem("I1", 1));
        gui.setItem(9, Skrzynki.getItem("I2", 1));
        gui.setItem(10, Skrzynki.getItem("I3", 1));
        gui.setItem(11, Skrzynki.getItem("I4", 1));
        gui.setItem(12, Skrzynki.getItem("I5", 1));
        gui.setItem(13, Skrzynki.getItem("I6", 1));
        gui.setItem(14, Skrzynki.getItem("I7", 1));
        gui.setItem(15, Skrzynki.getItem("I8", 1));
        gui.setItem(16, Skrzynki.getItem("I9", 1));
        gui.setItem(17, Skrzynki.getItem("I10", 1));

        player.openInventory(gui);
    }
}
