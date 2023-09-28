package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GiveAkcesoriaCommand extends CommandAPI {
    public GiveAkcesoriaCommand() {
        super("giveakcesoria");
        this.setAliases(Arrays.asList("akcedaj", "dajakce", "giveakce"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 7) {
            player.sendMessage(Utils.poprawneUzycie("giveakcesoria <typ> <val1> <val2> <val3> <val4> <val5> <lvl> <nazwa...>"));
            return;
        }

        int val1;
        int val2;
        int val3;
        int val4;
        int val5;
        int lvl;

        try {
            val1 = Integer.parseInt(args[1]);
            val2 = Integer.parseInt(args[2]);
            val3 = Integer.parseInt(args[3]);
            lvl = Integer.parseInt(args[6]);
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
            return;
        }

        switch (args[0]) {
            case "tarcza":
                val3 = Integer.parseInt(args[3]);
                player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "naszyjnik":
                val1 = Integer.parseInt(args[1]);
                player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "kolczyki":
                val3 = Integer.parseInt(args[3]);
                player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "pierscien":
                val3 = Integer.parseInt(args[3]);
                player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "diadem":
                player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "szarfa":
                player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(val1, val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "pas":
                player.getInventory().addItem(AkcesoriaDodatHelper.createPas(val1, val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "medalion":
                val2 = Integer.parseInt(args[2]);
                player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(val1, val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "energia":
                double przebicie = 0;
                try {
                    val3 = Integer.parseInt(args[3]);
                    przebicie = Double.parseDouble(args[4]);
                    val5 = Integer.parseInt(args[5]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
                    return;
                }
                player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(val1, val2, val3, przebicie, val5, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
        }
    }
}
