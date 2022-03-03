package rpg.rpgcore.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Alerts {

    private final Colorize colorize = new Colorize();

    public boolean nieGracz(final CommandSender sender,final String color){
        if (!(sender instanceof Player)) {
            colorize.sendMessage(sender, color + "Nie jesteś graczem!");
            return false;
        }
        return true;
    }

    public boolean permisje(final Player p,final String perm){
        if (!(p.hasPermission(perm))) {
            colorize.sendMessage(p,  "Nie masz permisji!");
            return false;
        }
        return true;
    }

    public void poprawneUzycie(final Player p, final String cmd) {
        colorize.sendMessage(p,"&7>> Poprawne użycie: &e" + cmd);
    }

    public boolean offline(final Player p, final Player target){
        final String targetNick = target.getName();
        if (target == null){
            colorize.sendMessage(p,"&cGracz " + targetNick + " jest aktualnie offline!" );
            return false;
        }
        return true;
    }

}
