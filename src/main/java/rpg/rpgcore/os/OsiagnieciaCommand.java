package rpg.rpgcore.os;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class OsiagnieciaCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public OsiagnieciaCommand(RPGCORE rpgcore) {
        super("osiagniecia");
        this.setAliases(Arrays.asList("os", "nagrody"));
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            rpgcore.getOsManager().osGuiMain(player);
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("os"));
    }
}
