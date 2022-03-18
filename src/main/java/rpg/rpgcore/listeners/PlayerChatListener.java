package rpg.rpgcore.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerChatListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerChatListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        final Player player = e.getPlayer();
        //TODO zrobic klany xd
        String message = e.getMessage();
        String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8] <player-group> &7<player-name>&7: <message>");
        String poFormacie = rpgcore.getChatManager().formatujChat(e.getPlayer(), przedFormatem, message);
        e.setFormat(poFormacie);
    }
}
