package rpg.rpgcore.pomoc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Skrzynki;

import java.io.IOException;


public class PomocCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public PomocCommand(RPGCORE rpgcore) {
        super("pomoc");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            rpgcore.getPomocManager().openPomocInventory(player);
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("pomoc"));
    }
}
