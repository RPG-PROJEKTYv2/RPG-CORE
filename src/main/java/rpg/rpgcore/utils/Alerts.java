package rpg.rpgcore.utils;

import org.bukkit.entity.Player;

public class Alerts {

    private final Colorize colorize;

    public Alerts(Colorize colorize) {
        this.colorize = colorize;
    }

    public String nieGracz(){
        return "Nie jesteś graczem!";
    }

    public String permisje(final String perm){
        return "&cNie masz permisji &7" + perm + "&c!";
    }

    public void poprawneUzycie(final Player p, final String cmd) {
        colorize.sendMessage(p,"&7>> Poprawne użycie: &e" + cmd);
    }

    public String offline(final String targetName){
        return "&cGracz " + targetName + " jest aktualnie offline!";
    }

    public String serverName(){
        return "";
    }

}
