package rpg.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class BanCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public BanCommand(RPGCORE rpgcore){
        this.rpgcore = rpgcore;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)){
            rpgcore.getAlerts().nieGracz();
            return false;
        }
        final Player player = (Player) sender;

        if (!(player.hasPermission("rpg.ban"))){
            rpgcore.getAlerts().permisje("rpg.ban");
            return false;
        }

        if (args.length == 0){
            rpgcore.getAlerts().poprawneUzycie(player, "ban");
            return false;
        } else if (args.length == 1){
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("pomoc")){
                help(player);
            }
        }

        return false;
    }

    private void help(final Player player){
        rpgcore.getColorize().sendMessage(player, "&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-");
        rpgcore.getColorize().sendMessage(player, "&c/ban <gracz> [-s] [powod] &7- blokuje gracza na zawsze za podany powod, [-s] jesli ma sie nie pokazywac na chacie");
        rpgcore.getColorize().sendMessage(player, "&8-_-_-_-_-_-_-_-_-_-_-{&4&lBAN&8}-_-_-_-_-_-_-_-_-_-_-");
    }
}
