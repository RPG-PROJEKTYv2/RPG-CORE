package rpg.rpgcore.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class PlayerChatListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerChatListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e){
        final Player player = e.getPlayer();
        //TODO zrobic klany xd
        String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8] <player-group> <player-name>&7:<message>");
        przedFormatem = PlaceholderAPI.setPlaceholders(player, przedFormatem.replace("<player-lvl>", String.valueOf(rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()))));
        e.setFormat(przedFormatem);
    }
}
