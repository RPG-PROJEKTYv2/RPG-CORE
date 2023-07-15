package rpg.rpgcore.newTarg.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class NewTargCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public NewTargCommand(RPGCORE rpgcore) {
        super("targ");
        this.setAliases(Arrays.asList("ah", "gielda"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("targ"));
            return;
        }
        rpgcore.getNewTargManager().openNewTargInventory(player, 1, 1,1);

    }
}
