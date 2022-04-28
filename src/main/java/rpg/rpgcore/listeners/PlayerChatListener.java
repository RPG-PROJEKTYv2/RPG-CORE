package rpg.rpgcore.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Date;

public class PlayerChatListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerChatListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage().replaceAll("%", "%%");
        if (rpgcore.getPlayerManager().isMuted(player.getUniqueId())) {
            final String[] muteInfo = rpgcore.getPlayerManager().getPlayerMuteInfo(player.getUniqueId()).split(";");
            if (muteInfo[2].equalsIgnoreCase("Permamentny")) {
                Utils.youAreMuted(player, muteInfo[0], muteInfo[1], muteInfo[2]);
                e.setCancelled(true);
                return;
            }
            try {
                final Date teraz = new Date();
                final Date dataMuta = Utils.dateFormat.parse(muteInfo[2]);

                if (teraz.after(dataMuta)) {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unMutePlayer(player.getUniqueId()));
                    formatuj(e, message, player);
                    return;
                }
                Utils.youAreMuted(player, muteInfo[0], muteInfo[1], Utils.convertDatesToTimeLeft(dataMuta, teraz));
                e.setCancelled(true);
            } catch (ParseException ex) {
                ex.printStackTrace();
                e.setCancelled(true);
            }
            return;
        }
        //TODO zrobic klany xd
        formatuj(e, message, player);
    }


    private void formatuj(final AsyncPlayerChatEvent e, final String message, final Player player) {
        if (message.contains("[eq]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            e.setCancelled(true);
        }
        final String przedFormatem = Utils.format("&8[&bLvl. &f<player-lvl>&8]<player-group> &7<player-name>&7: <message>");
        final String poFormacie = rpgcore.getChatManager().formatujChat(player, przedFormatem, message);
        e.setFormat(poFormacie);
    }
}
