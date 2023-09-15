package rpg.rpgcore.msg.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class ReplyCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public ReplyCommand(final RPGCORE rpgcore) {
        super("reply");
        this.setAliases(Arrays.asList("r"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("r <wiadomosc>"));
            return;
        }
        final Player target = Bukkit.getPlayer(rpgcore.getUserManager().find(player.getUniqueId()).getLastMsgUUID());

        if (target == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
            return;
        }

        if (!rpgcore.getChatManager().find(target.getUniqueId()).isMsgEnabled()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ten gracz ma wylaczone prywatne wiadomosci"));
            return;
        }

        if (rpgcore.getChatManager().find(target.getUniqueId()).getIgnoredPlayers().contains(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz ignoruje twoje wiadomosci!"));
            return;
        }

        final StringBuilder message = new StringBuilder();
        for (final String s : args) {
            if (!(s.equalsIgnoreCase(""))) {
                message.append(" ").append(s);
            }
        }
        rpgcore.getMsgManager().sendMessages(player, target, Utils.removeColor(String.valueOf(message)));
    }
}
