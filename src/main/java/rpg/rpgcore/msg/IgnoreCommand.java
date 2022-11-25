package rpg.rpgcore.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class IgnoreCommand extends CommandAPI {

    public IgnoreCommand() {
        super("ignore");
        this.setAliases(Arrays.asList("ignoruj"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(final CommandSender sender, final String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("ignore <gracz>"));
            return;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie odnaleziono takiego gracza!"));
            return;
        }

        final Player target = Bukkit.getPlayer(args[0]);

        if (target == player) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz ignorowac samego siebie!"));
            return;
        }

        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());

        if (user.getIgnoredPlayers().contains(target.getUniqueId())) {
            user.getIgnoredPlayers().remove(target.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aUsunieto gracza &6" + target.getName() + " &az listy ignorowanych!"));
        } else {
            user.getIgnoredPlayers().add(target.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aDodano gracza &6" + target.getName() + " &ado listy ignorowanych!"));
        }

        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataChatUsers(user.getUuid(), user));
    }
}
