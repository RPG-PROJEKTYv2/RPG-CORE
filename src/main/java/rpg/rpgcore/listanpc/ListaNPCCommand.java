package rpg.rpgcore.listanpc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class ListaNPCCommand extends CommandAPI {

    public ListaNPCCommand() {
        super("listanpc");
        this.setAliases(Arrays.asList("lnpc", "npc"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (!player.getWorld().getName().equals("world")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTej komendy mozesz uzywac tylko na spawnie!"));
            return;
        }
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("listanpc"));
            return;
        }
        RPGCORE.getInstance().getListaNPCManager().openMainGUI(player);
    }
}
