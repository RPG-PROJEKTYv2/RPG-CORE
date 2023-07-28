package rpg.rpgcore.pomoc;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.wyslannik.enums.WyslannikMissionKillMob;
import rpg.rpgcore.npc.wyslannik.objects.WyslannikObject;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;


public class PomocCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public PomocCommand(RPGCORE rpgcore) {
        super("pomoc");
        this.setAliases(Arrays.asList("help"));
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
