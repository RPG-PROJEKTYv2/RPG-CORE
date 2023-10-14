package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;


public class ResetKrytCommand extends CommandAPI {
    public ResetKrytCommand() {
        super("resetkryt");
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length > 1) {
            player.sendMessage(Utils.poprawneUzycie("resetkryta [player?]"));
            return;
        }

        if (args.length == 0) {
            for (final User user : RPGCORE.getInstance().getUserManager().getUserObjects()) {
                user.setKrytyk(0.0D);
            }
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getTopkiManager().updateTopki());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zresetowano krytyki wszystkim graczom!"));
            return;
        }
        final User user = RPGCORE.getInstance().getUserManager().find(args[0]);
        if (user == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0] + "&c!"));
            return;
        }

        user.setKrytyk(0.0D);
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getTopkiManager().updateTopki());
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zresetowano krytyk gracza &e" + user.getName() + "&a!"));
        return;
    }
}
