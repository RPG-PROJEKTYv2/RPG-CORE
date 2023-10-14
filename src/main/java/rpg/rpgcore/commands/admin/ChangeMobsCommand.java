package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

public class ChangeMobsCommand extends CommandAPI {

    public ChangeMobsCommand() {
        super("changemobs");
        this.setRankLevel(RankType.HA);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;
        if (args.length < 4) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /changemobs <leashrange/warmup> <mobek> <liczba> <ostatnie-id-mobka>"));
            return;
        }
        if (args[0].equals("leashrange")) {
            try {
                for (int i = 0; i <= Integer.parseInt(args[3]); i++) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm s set " + args[1] + "-RESP-" + i + " leashrange " + Integer.parseInt(args[2]));
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniono leashrange we wszystkich plikach!"));
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /changemobs <leashrange/warmup> <mobek> <liczba> <ostatnie-id-mobka>"));
            }
        }
        if (args[0].equals("warmup")) {
            try {
                for (int i = 0; i <= Integer.parseInt(args[3]); i++) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm s set " + args[1] + "-RESP-" + i + " warmup " + Integer.parseInt(args[2]));
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm s set " + args[1] + "-RESP-" + i + " cooldown " + 0);
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniono warmup we wszystkich plikach oraz ustawiono cooldown na 0!"));
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPoprawne uzycie: /changemobs <leashrange/warmup> <mobek> <liczba> <ostatnie-id-mobka>"));
            }
        }
    }
}
