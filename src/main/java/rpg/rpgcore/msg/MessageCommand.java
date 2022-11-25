package rpg.rpgcore.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class MessageCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public MessageCommand(final RPGCORE rpgcore) {
        super("message");
        this.setAliases(Arrays.asList("msg", "pv", "pw", "m"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Utils.poprawneUzycie("msg <gracz> <wiadomosc>"));
            return;
        }
        final UUID playerUUID = player.getUniqueId();

        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
            return;
        }
        final StringBuilder message = new StringBuilder();
        final Player target = Bukkit.getPlayer(args[0]);
        final UUID targetUUID = target.getUniqueId();

        if (player == target) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz napisac sam do siebie"));
            return;
        }

        if (!(target.isOnline())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany gracz jest &coffline"));
            return;
        }

        if (rpgcore.getChatManager().find(targetUUID).isMsgEnabled()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ten gracz ma wylaczone prywatne wiadomosci"));
            return;
        }

        if (rpgcore.getChatManager().find(targetUUID).getIgnoredPlayers().contains(playerUUID)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz ignoruje twoje wiadomosci!"));
            return;
        }

        args[0] = "";

        for (final String arg : args) {
            if (!(arg.equalsIgnoreCase(""))) {
                message.append(" ").append(arg);
            }
        }

        rpgcore.getMsgManager().sendMessages(player, target, Utils.removeColor(String.valueOf(message)));
        if (rpgcore.getMsgManager().isInMessageMapAsKey(playerUUID)) {
            rpgcore.getMsgManager().updateMessageMap(playerUUID, targetUUID);
            return;
        }

        rpgcore.getMsgManager().putInMessageMap(playerUUID, targetUUID);
    }
}
