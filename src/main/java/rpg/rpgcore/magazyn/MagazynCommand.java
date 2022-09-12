package rpg.rpgcore.magazyn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class MagazynCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public MagazynCommand(RPGCORE rpgcore) {
        super("magazyn");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("magazyn"));
            return;
        }
        rpgcore.getMagazynManager().openMagazynyList(player);
    }
}
