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
    public void onChat(final AsyncPlayerChatEvent e){
        final Player player = e.getPlayer();
        //TODO zrobic klany xd
        final String message = e.getMessage();
        if (message.contains("[eq]")){
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            e.setCancelled(true);
        }
        final String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8]<player-group> &7<player-name>&7: <message>");
        final String poFormacie = rpgcore.getChatManager().formatujChat(player, przedFormatem, message);
        e.setFormat(poFormacie);
    }
}
