package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class StraganiarzCommand extends CommandAPI {
    public StraganiarzCommand() {
        super("straganiarz");
        this.setAliases(Arrays.asList("stragan", "wymien", "stackowanie", "rozmien"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("straganiarz"));
            return;
        }

        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
        if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.VIP.getPriority()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganej rangi, zeby uzyc tej komendy!"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cRange &e&lVIP&c, &5&lELITA &cmozesz zakupic na naszym dc: &edc.hellrpg.pl"));
            return;
        }

        RPGCORE.getInstance().getStraganiarzManager().openGUI1(player);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Poprawnie otworzono stragan!"));
    }
}
