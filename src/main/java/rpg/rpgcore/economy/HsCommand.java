package rpg.rpgcore.economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.io.IOException;
import java.util.Arrays;

public class HsCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public HsCommand(final RPGCORE rpgcore) {
        super("hs");
        this.setAliases(Arrays.asList("hellsy", "coins"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("hs"));
            return;
        }
        player.sendMessage(Utils.format("&7Twoj aktualny stan &4&lH&6&lS&7: &c" + Utils.spaceNumber(String.valueOf(rpgcore.getUserManager().find(player.getUniqueId()).getHellcoins()))));
    }
}
