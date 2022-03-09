package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {
    private final ArrayList<UUID> vanishList = new ArrayList<>();

    public ArrayList<UUID> getVanishList() {return vanishList;}
    public boolean containsPlayer(final UUID uuid){return vanishList.contains(uuid);}

    public void revealPlayer(final Player target){
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()){
            restOfTheServer.showPlayer(target);
        }
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono &7vanisha"));
    }

    public void hidePlayer(final Player target){
        for (Player restOfTheServer : Bukkit.getOnlinePlayers()){
            if (!(restOfTheServer.hasPermission("rpg.vanish.see_hidden_players"))){
                restOfTheServer.hidePlayer(target);
            }
        }
        target.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono &7vanisha"));
    }
}
