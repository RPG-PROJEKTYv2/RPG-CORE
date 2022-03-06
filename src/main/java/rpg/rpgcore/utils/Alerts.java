package rpg.rpgcore.utils;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;

public class Alerts {

    private final Colorize colorize;
    private final RPGCORE rpgcore;

    public Alerts(RPGCORE rpgcore, Colorize colorize) {
        this.rpgcore = rpgcore;
        this.colorize = colorize;
    }

    public String nieGracz(){
        return colorize.format("[rpg.core] &cTylko gracze moga uzywac tej komendy");
    }

    public String permisje(final String perm){
        return colorize.format(rpgcore.nazwaserwera + "&6Nie masz permisji zeby uzywac tej komendy &8(&6" + perm + "&8)") ;
    }

    public void poprawneUzycie(final Player p, final String cmd) {
        colorize.sendMessage(p, colorize.format(rpgcore.nazwaserwera + "&7Poprawne uzycie to &c/" + cmd + " &chelp"));
    }

    public String offline(final String targetName){
        return colorize.format(rpgcore.nazwaserwera + "&7Gracz &c" + targetName + " &7jest aktualnie &coffline!");
    }

    public String serverName(){
        if (rpgcore.getConfig().getString("nazwa_serwera") != null){
            return rpgcore.getConfig().getString("nazwa_serwera");
        } else {
            return "Brak Nazwy";
        }
    }

}
