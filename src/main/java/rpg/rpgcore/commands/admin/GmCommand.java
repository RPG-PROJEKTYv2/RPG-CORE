package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class GmCommand extends CommandAPI {
    
    public GmCommand() {
        super("gm");
        this.setAliases(Arrays.asList("gamemode"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    public static void mode(final CommandSender sender, final Player target, final GameMode tryb) {
        if (target != sender) {
            sender.sendMessage(Utils.format("&7>> &7Zmieniono tryb gracza: &e" + target.getName() + " &7na: &e" + tryb.toString().toLowerCase()));
        }
        target.setGameMode(tryb);
        target.sendMessage(Utils.format("&7>> &7Zmieniono twoj tryb gry na: &e" + tryb.toString().toLowerCase()));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("gm <0-3 / survival / creative / adventure / spectator> [gracz]"));
            return;
        }
        if (args.length == 1) {
            final Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                mode(p, p, GameMode.SURVIVAL);
                return;
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                mode(p, p, GameMode.CREATIVE);
                return;
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                mode(p, p, GameMode.ADVENTURE);
                return;
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                mode(p, p, GameMode.SPECTATOR);
            }
        }
        if (args.length == 2) {

            final Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(Utils.offline(args[1]));
                return;
            }

            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                mode(sender, target, GameMode.SURVIVAL);
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                mode(sender, target, GameMode.CREATIVE);
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                mode(sender, target, GameMode.ADVENTURE);
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                mode(sender, target, GameMode.SPECTATOR);
            }
        }
    }
}
