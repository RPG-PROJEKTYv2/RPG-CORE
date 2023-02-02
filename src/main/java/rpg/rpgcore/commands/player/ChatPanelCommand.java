package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class ChatPanelCommand extends CommandAPI {
    public ChatPanelCommand() {
        super("chatpanel");
        this.setAliases(Arrays.asList("panel", "chatp", "panelgracza", "graczpanel"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("chatpanel"));
        } else {
            RPGCORE.getInstance().getChatManager().openChatPanel(player);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otwarto panel powiadomien chatu!"));
        }
    }
}
