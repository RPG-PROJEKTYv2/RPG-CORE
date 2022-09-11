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
import java.util.Map;
import java.util.UUID;

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
        final UUID playerUUID = player.getUniqueId();
        if (rpgcore.getMsgManager().isInMessageMapAsKey(playerUUID)) {
            final UUID targetUUID = rpgcore.getMsgManager().getTargetUUID(playerUUID);

            if (Bukkit.getPlayer(targetUUID) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
                return;
            }
            final Player target = Bukkit.getPlayer(targetUUID);
            final StringBuilder message = new StringBuilder();
            for (final String s : args) {
                if (!(s.equalsIgnoreCase(""))) {
                    message.append(" ").append(s);
                }
            }
            rpgcore.getMsgManager().sendMessages(player, target, String.valueOf(message));
        } else if (rpgcore.getMsgManager().isInMessageMapAsValue(playerUUID)) {
            UUID targetUUID = null;
            for (Map.Entry<UUID, UUID> entry : rpgcore.getMsgManager().getMessageMap().entrySet()) {
                if (entry.getValue() == playerUUID) {
                    targetUUID = entry.getKey();
                    break;
                }
            }
            if (Bukkit.getPlayer(targetUUID) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie odnaleziono podanego gracza"));
                return;
            }
            final Player target = Bukkit.getPlayer(targetUUID);
            final StringBuilder message = new StringBuilder();
            for (final String s : args) {
                if (!(s.equalsIgnoreCase(""))) {
                    message.append(" ").append(s);
                }
            }
            rpgcore.getMsgManager().sendMessages(player, target, String.valueOf(message));
        } else {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Jeszcze do nikogo nie napisales!"));
        }
    }
}
