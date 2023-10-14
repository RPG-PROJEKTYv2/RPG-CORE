package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class DodatkiCommand extends CommandAPI {
    public DodatkiCommand() {
        super("dodatki");
        this.setAliases(Arrays.asList("akcesoria", "bony", "akce", "ekwipunek"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("/dodatki"));
            return;
        }

        RPGCORE.getInstance().getDodatkiManager().openDodatkiGUI(player);
    }
}
