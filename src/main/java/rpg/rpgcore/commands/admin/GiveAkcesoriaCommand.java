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
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 5) {
            player.sendMessage(Utils.poprawneUzycie("giveakcesoria <typ> <val1> <val2> <val3> <lvl> <nazwa...>"));
            return;
        }

        if (args.length == 5) {
            double val1;
            double val2;
            double val3;
            int lvl;

            try {
                val1 = Double.parseDouble(args[1]);
                val2 = Double.parseDouble(args[2]);
                val3 = Double.parseDouble(args[3]);
                lvl = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
                return;
            }

            switch (args[0]) {
                case "tarcza":
                    val3 = Integer.parseInt(args[3]);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createTarcza(val1, val2, (int) val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                    break;
                case "naszyjnik":
                    val1 = Integer.parseInt(args[1]);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createNaszyjnik((int) val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                    break;
                case "kolczyki":
                    val3 = Integer.parseInt(args[3]);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createKolczyki(val1, val2, (int) val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                    break;
                case "pierscien":
                    val3 = Integer.parseInt(args[3]);
                    player.getInventory().addItem(AkcesoriaPodsHelper.createPierscien(val1, val2, (int) val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                    break;
                case "diadem":
                    player.getInventory().addItem(AkcesoriaPodsHelper.createDiadem(val1, val2, val3, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 5, args.length)))));
                    break;
            }

            return;
        }

        if (args.length < 8) {
            player.sendMessage(Utils.poprawneUzycie("giveakcesoria <typ> <val1> <val2> <val3> <val4> <val5> <lvl> <nazwa...>"));
            return;
        }

        double val1;
        double val2;
        double val3;
        double val4;
        int val5;
        int lvl;

        try {
            val1 = Double.parseDouble(args[1]);
            val2 = Double.parseDouble(args[2]);
            lvl = Integer.parseInt(args[6]);
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
            return;
        }

        switch (args[0]) {
            case "szarfa":
                player.getInventory().addItem(AkcesoriaDodatHelper.createSzarfa(val1, val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "pas":
                player.getInventory().addItem(AkcesoriaDodatHelper.createPas(val1, val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "medalion":
                val2 = Integer.parseInt(args[2]);
                player.getInventory().addItem(AkcesoriaDodatHelper.createMedalion(val1, (int) val2, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
            case "energia":
                try {
                    val3 = Double.parseDouble(args[3]);
                    val4 = Double.parseDouble(args[4]);
                    val5 = Integer.parseInt(args[5]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac liczby!"));
                    return;
                }
                player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(val1, val2, val3, val4, val5, lvl, Utils.format(String.join(" ", Arrays.copyOfRange(args, 7, args.length)))));
                break;
        }

    }
}
