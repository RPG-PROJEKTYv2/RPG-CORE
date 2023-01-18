package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class ClearCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public ClearCommand(final RPGCORE rpgcore) {
        super("clear");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            int playerInventory = player.getInventory().getSize();
            player.getInventory().getItem(1);

            if (player.getCanPickupItems()) {
                player.sendMessage("masz puste eq");
            } else {

            }

            if (playerInventory > 0) {
                player.sendMessage("masz pelne eq");
            } else {
                player.sendMessage("Twoje eq jest puste");
            }
            return;
        }
        if (args.length > 1) {
            player.sendMessage(Utils.poprawneUzycie("clear <gracz>"));
            return;
        }
    }
}
