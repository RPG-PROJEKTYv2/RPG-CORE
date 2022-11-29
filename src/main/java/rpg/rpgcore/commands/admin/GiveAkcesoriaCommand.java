package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GiveAkcesoriaCommand extends CommandAPI {
    public GiveAkcesoriaCommand() {
        super("giveakcesoria");
        this.setAliases(Arrays.asList("akcedaj", "dajakce", "giveakce"));
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 5) {
            player.sendMessage(Utils.poprawneUzycie("giveakcesoria <typ> <val1> <val2> <val3> <lvl> <nazwa...>"));
            return;
        }

        int val1 = 0;
        int val2 = 0;
        int val3 = 0;
        int lvl = 0;

        try {
            val1 = Integer.parseInt(args[1]);
            val2 = Integer.parseInt(args[2]);
            val3 = Integer.parseInt(args[3]);
            lvl = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
            return;
        }

        switch (args[0]) {
            case "tarcza":
                player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                break;
            case "naszyjnik":
                player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                break;
            case "kolczyki":
                player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                break;
            case "pierscien":
                player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                break;
            case "diadem":
                player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                break;
        }
    }
}
