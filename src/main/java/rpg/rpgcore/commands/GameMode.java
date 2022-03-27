package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class GameMode implements CommandExecutor {

    private final RPGCORE rpgcore;

    public GameMode(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (args.length == 1) {
            this.changeGameMode(player, player, args[0]);
            return false;
        }
        if (args.length == 2) {

            final UUID uuidPlayerToChange = rpgcore.getPlayerManager().getPlayerUUID(args[1]);

            if (uuidPlayerToChange == null) {
                player.sendMessage(Utils.SERVERNAME + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }
            this.changeGameMode(player, Bukkit.getPlayer(uuidPlayerToChange), args[0]);
        }
        player.sendMessage(Utils.poprawneUzycie("gm <0/1/2/3/s/c/a/spec> [gracz]"));
        return false;
    }

    private void changeGameMode(final Player player, final Player target, final String arg) {
        if (target == null) {
            player.sendMessage(Utils.SERVERNAME + Utils.NIEMATAKIEGOGRACZA);
            return;
        }
        if (arg.equalsIgnoreCase("0") || arg.equalsIgnoreCase("s")) {
            target.setGameMode(org.bukkit.GameMode.SURVIVAL);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pomyslnie zmieniono tryb gry gracza &c" + player.getName() + " &7na &c" + player.getGameMode()));

            return;
        }
        if (arg.equalsIgnoreCase("1") || arg.equalsIgnoreCase("c")) {
            target.setGameMode(org.bukkit.GameMode.CREATIVE);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pomyslnie zmieniono tryb gry gracza &c" + player.getName() + " &7na &c" + player.getGameMode()));

            return;
        }
        if (arg.equalsIgnoreCase("2") || arg.equalsIgnoreCase("a")) {
            target.setGameMode(org.bukkit.GameMode.ADVENTURE);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pomyslnie zmieniono tryb gry gracza &c" + player.getName() + " &7na &c" + player.getGameMode()));

            return;
        }
        if (arg.equalsIgnoreCase("3") || arg.equalsIgnoreCase("spec")) {
            target.setGameMode(org.bukkit.GameMode.SPECTATOR);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pomyslnie zmieniono tryb gry gracza &c" + player.getName() + " &7na &c" + player.getGameMode()));

            return;
        }
        target.sendMessage(Utils.poprawneUzycie("gm <0/1/2/3/s/c/a/spec> [gracz]"));
    }
}
