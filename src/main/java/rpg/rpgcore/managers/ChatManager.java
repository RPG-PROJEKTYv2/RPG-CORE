package rpg.rpgcore.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ChatManager {

    private final RPGCORE rpgcore;

    public ChatManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public String formatujChat(final Player player , final String format, String message) {
        final int playerLVL =  rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId());
        String playerName = player.getName();
        String poFormacie;
        String playerRank = PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");

        switch (playerRank){
            case "H@":
                message = Utils.format(message);
                playerName = Utils.format("&c" + playerName);
                break;
            case "Admin":
                message = Utils.format(message);
                playerName = Utils.format("&c" + playerName);
                break;
            case "GM":
                message = Utils.format(message);
                playerName = Utils.format("&a" + playerName);
                break;
            case "Mod":
                playerName = Utils.format("&a" + playerName);
                break;
            case "KidMod":
                playerName = Utils.format("&a" + playerName);
                break;
            case "Helper":
                playerName = Utils.format("&b" + playerName);
                break;
            case "JuniorHelper":
                playerName = Utils.format("&b" + playerName);
                break;
            default:
                playerName = Utils.format("&7" + playerName);
                break;
        }
        poFormacie = PlaceholderAPI.setPlaceholders(player, format.replace("<player-lvl>", String.valueOf(playerLVL)).replace("<player-group>", "%uperms_prefixes%").replace("<player-name>", playerName).replace("<message>", message));



        return poFormacie;
    }
}
