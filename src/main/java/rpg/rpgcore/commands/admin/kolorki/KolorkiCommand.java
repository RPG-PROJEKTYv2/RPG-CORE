package rpg.rpgcore.commands.admin.kolorki;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class KolorkiCommand extends CommandAPI {

    public KolorkiCommand() {
        super("kolory");
        this.setAliases(Arrays.asList("color", "colors"));
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("kolory <fraza>"));
            return;
        }
        final StringBuilder mess = new StringBuilder();
        for (final String arg : args) {
            if (!(arg.equalsIgnoreCase(""))) {
                mess.append(" ").append(arg);
            }
        }
        final String message = String.valueOf(mess);
        String colour = "4c6e2ab310d5f780";
        player.sendMessage(Utils.format("&f&lLISTA KOLOROW FRAZY:"));
        for (Character c : colour.toCharArray()) {
            player.sendMessage(Utils.format("&" + c + message));
        }
    }
}

