package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class History implements CommandExecutor {

    private final RPGCORE rpgcore;

    public History(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender.hasPermission("rpg.history"))) {
            sender.sendMessage(Utils.permisje("rpg.history"));
            return false;
        }


        if (args.length == 1) {
            final UUID targetUUID = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (!(rpgcore.getPlayerManager().getPlayers().contains(targetUUID))) {
                sender.sendMessage(Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            final String punishmentsHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(targetUUID));

            if (punishmentsHistory.equalsIgnoreCase("")) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz nie ma zadnych kar!"));
                return false;
            }

            final String[] fullPunishmentHistory = String.valueOf(rpgcore.getPlayerManager().getPlayerPunishmentHistory(targetUUID)).split(",");

            sender.sendMessage(Utils.format("\n&cHistoria kar gracza:&7 " + args[0]));

            for (final String onePunishments : fullPunishmentHistory) {

                if (!(onePunishments.equalsIgnoreCase(""))) {

                    final String[] punishment = onePunishments.split(";");

                    String punishmentToSend;

                    if (punishment.length == 4) {
                        punishmentToSend = Utils.format(
                                "\n&cTyp:&7 " + punishment[0] + "\n" +
                                        "\n&cNadany przez:&7 " + punishment[1] + "" +
                                        "\n&cPowod:&7" + punishment[2] +
                                        "\n&cNadany dnia:&7 " + punishment[3]);
                    } else {
                        punishmentToSend = Utils.format(
                                "\n&cTyp:&7 " + punishment[0] + "\n" +
                                        "\n&cNadany przez:&7 " + punishment[1] + "" +
                                        "\n&cPowod:&7" + punishment[2] +
                                        "\n&cWygasa:&7 " + punishment[3] +
                                        "\n&cNadany dnia:&7 " + punishment[4]);
                    }
                    sender.sendMessage(punishmentToSend);
                    sender.sendMessage(Utils.format("\n-------------------------------------\n"));
                }
            }
            return false;
        }
        sender.sendMessage(Utils.poprawneUzycie("history [gracz]"));
        return false;
    }
}
