package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class SpeedCommand extends CommandAPI {

    public SpeedCommand() {
        super("speed");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
            return;
        }
        try {
            float speed = Float.parseFloat(args[0]);
            if (!(speed >= 1 && speed <= 5)) {
                player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
                return;
            }
            if (player.isFlying()) {
                player.setFlySpeed(speed / 10);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ustawiono &cFLY &7speed na &c" + speed));
                return;
            }
            player.setWalkSpeed(speed / 10);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ustawiono &cWALK &7speed na &c" + speed));
        } catch (final NumberFormatException e) {
            player.sendMessage(Utils.poprawneUzycie("speed <1-5>"));
        }
    }
}
