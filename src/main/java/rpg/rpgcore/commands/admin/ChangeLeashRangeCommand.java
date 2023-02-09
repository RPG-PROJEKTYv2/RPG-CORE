package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

public class ChangeLeashRangeCommand extends CommandAPI {

    public ChangeLeashRangeCommand() {
        super("changeleashrange");
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 3) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /changeleashrange <mobek> <range> <ostatnie-id-mobka>"));
            return;
        }
        try {
            for (int i = 0; i <= Integer.parseInt(args[2]); i++) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm s set " + args[0] + "-RESP-" + i + " leashrange " + Integer.parseInt(args[1]));
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniono leashrange we wszystkich plikach!"));
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /changeleashrange <mobek> <range> <ostatnie-id-mobka>"));
        }


    }
}
