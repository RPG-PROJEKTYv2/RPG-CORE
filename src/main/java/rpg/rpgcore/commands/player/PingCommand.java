package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class PingCommand extends CommandAPI {
    public PingCommand() {
        super("ping");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 1) {
            player.sendMessage(Utils.poprawneUzycie("ping <gracz>"));
            return;
        }
        if (args.length == 1) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany gracz nie jest online!"));
                return;
            }

            if (target == player) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoj ping wynosi: &e" + ((CraftPlayer) player).getHandle().ping));
                return;
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Ping gracza: &e" + target.getName() + " &7wynosi: &e" + ((CraftPlayer) target).getHandle().ping));
        } else {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoj ping wynosi: &e" + ((CraftPlayer) player).getHandle().ping));
        }
    }
}
