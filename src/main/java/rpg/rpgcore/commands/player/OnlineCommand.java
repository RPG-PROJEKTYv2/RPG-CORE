package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class OnlineCommand extends CommandAPI {
    public OnlineCommand() {
        super("online");
        this.setAliases(Arrays.asList("list", "lista", "gracze", "onlinelist", "listagraczy"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("list"));
            return;
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Graczy na serwerze: &c" + player.getServer().getOnlinePlayers().size()));
    }
}
