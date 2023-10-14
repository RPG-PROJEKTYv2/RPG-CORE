package rpg.rpgcore.pets.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class PetCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public PetCommand(final RPGCORE rpgcore) {
        super("pety");
        this.setRankLevel(RankType.GRACZ);
        this.setAliases(Arrays.asList("pets"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 1) {
            player.sendMessage(Utils.poprawneUzycie("pety"));
            return;
        }
        rpgcore.getPetyManager().openPetyGUI(player, 1);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otwarto GUI z twoimi zwierzakami!"));
    }
}
