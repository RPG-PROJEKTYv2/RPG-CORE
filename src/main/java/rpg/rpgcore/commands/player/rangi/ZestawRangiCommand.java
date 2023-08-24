package rpg.rpgcore.commands.player.rangi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class ZestawRangiCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public ZestawRangiCommand(final RPGCORE rpgcore) {
        super("zestawrangi");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    public void executeCommand(final CommandSender sender, final String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("zestawrangi"));
            return;
        }

        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.VIP.getPriority()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganej rangi, zeby uzyc tej komendy!"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cRange &e&lVIP&c, &5&lELITA &cmozesz zakupic na naszym dc: &edc.hellrpg.pl"));
            return;
        }

        if (user.hasKitCooldown()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz odczekac jeszcze &e" + user.getKitCooldown() + " &c przed odebraniem zestawu!"));
            return;
        }

        switch (user.getRankPlayerUser().getRankType()) {
            case VIP:
            case TWORCA:
                // daj dawanie skrzynki
                break;
            case ELITA:
                // daj dawanie skrzynki
                break;
            default:
                return;
        }
        user.giveKitCooldown();
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
    }

}
