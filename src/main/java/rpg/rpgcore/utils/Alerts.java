package rpg.rpgcore.utils;

import org.bukkit.Sound;
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
        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F,1.0F);
    }

    public String offline(final String targetName){
        return "&cGracz &7" + targetName + " jest aktualnie offline!";
    }

    public String serverName(){
        return "RPG-CORE";
    }

}
