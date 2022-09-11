package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class BackCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public BackCommand(RPGCORE rpgcore) {
        super("back");
        this.setRankLevel(RankType.GM);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("back"));
            return;
        }
        final UUID uuid = player.getUniqueId();
        if (rpgcore.getTeleportManager().getBeforeTeleportLocation(uuid) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNiestety nie znam miesjca, do ktorego moglbys sie cofnac"));
            return;
        }
        player.teleport(rpgcore.getTeleportManager().getBeforeTeleportLocation(uuid));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano do poprzedniej lokalizacji"));
    }
}
