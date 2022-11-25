package rpg.rpgcore.commands.admin;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class AdminChatCommand extends CommandAPI {

    public AdminChatCommand() {
        super("adminchat");
        this.setRankLevel(RankType.JUNIORHELPER);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (!user.isAdminCodeLogin()) {
            return;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.poprawneUzycie("adminchat <wiadomosc>"));
            return;
        }

        final TextComponent prefix = new TextComponent(Utils.format("&8[&4AdminChat&8] &7>> " + user.getRankUser().getRankType().getPrefix() + player.getName() + "&7: "));
        final TextComponent message = new TextComponent(Utils.format(String.join(user.getRankUser().getRankType().getMessageColour() + " ", args)));

        prefix.addExtra(message);

        Utils.sendToStaff(prefix);
    }
}
