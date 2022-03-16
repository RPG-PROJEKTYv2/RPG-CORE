package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Lvl implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Lvl(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.lvl"))) {
            player.sendMessage(Utils.permisje("rpg.lvl"));
            return false;
        }

        if (args.length == 0) {
            rpgcore.getLvlManager().getPlayerLvl(player, player.getUniqueId());
            return false;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
                player.sendMessage(Utils.poprawneUzycie("lvl [gracz]"));
                return false;
            }

            final UUID uuidPlayerToSeeInfo = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (uuidPlayerToSeeInfo == null) {
                player.sendMessage(Utils.LVLPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }
            rpgcore.getLvlManager().getPlayerLvl(player, uuidPlayerToSeeInfo);
            return false;
        }

        if (args.length < 3) {
            if (!(player.hasPermission("rpg.lvl.admin"))) {
                player.sendMessage(Utils.permisje("rpg.lvl.admin"));
                return false;
            }
            player.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-_-_-"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setlvl> <int> &7- pozwala usatwic lvl podanego gracza"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setexp> <double> &7- pozwala usatwic exp podanego gracza"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setprocent> <double> &7- pozwala usatwic procent expa podanego gracza"));
            player.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-_-_-"));
            return false;
        }
        if (args.length == 3) {

            final UUID uuidPlayerToSet = rpgcore.getPlayerManager().getPlayerUUID(args[0]);
            player.sendMessage(uuidPlayerToSet.toString());

            if (uuidPlayerToSet == null) {
                player.sendMessage(Utils.LVLPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return false;
            }

            if (args[1].equalsIgnoreCase("setlvl")) {
                try{
                    final int nowyLvl= Integer.parseInt(args[2]);
                    rpgcore.getLvlManager().setPlayerLvl(player.getName(), uuidPlayerToSet, nowyLvl);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono poziom gracza &6" + rpgcore.getPlayerManager().getPlayerName(uuidPlayerToSet) + " &ana &6" + nowyLvl));
                } catch (final NumberFormatException e){
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe calkowita"));
                    return false;
                }
            }
            if (args[1].equalsIgnoreCase("setexp")) {
                try {
                    final double nowyExp = Double.parseDouble(args[2]);
                    rpgcore.getLvlManager().setPlayerExp(player.getName(), uuidPlayerToSet, nowyExp);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono exp gracza &6" + rpgcore.getPlayerManager().getPlayerName(uuidPlayerToSet) + " &ana &6" + nowyExp + " &aexp"));
                } catch (final NumberFormatException e){
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe"));
                    return false;
                }
            }
            if (args[1].equalsIgnoreCase("setprocent")){
                try {
                    final double nowyProcent = Double.parseDouble(args[2]);
                    rpgcore.getLvlManager().setPlayerProcent(player.getName(), uuidPlayerToSet, nowyProcent);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono postep gracza &6" + rpgcore.getPlayerManager().getPlayerName(uuidPlayerToSet) + " &ana &6" + nowyProcent + "&a%"));
                } catch (final NumberFormatException e){
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe"));
                    return false;
                }
            }
        }
        return false;
    }
}
