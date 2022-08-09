package rpg.rpgcore.chat;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ranks.RankType;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PlayerChatListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerChatListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage().replaceAll("%", "%%");

        if (rpgcore.getCooldownManager().hasChatCooldown(player.getUniqueId())) {
            final long sekundy = rpgcore.getCooldownManager().getPlayerChatCooldown(player.getUniqueId()) - System.currentTimeMillis();
            final String mili = String.valueOf(TimeUnit.MILLISECONDS.toMillis(sekundy) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(sekundy)));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&3Nastepna wiadomosc mozesz wyslac za &f" + TimeUnit.MILLISECONDS.toSeconds(sekundy) + "&f.&f" + mili + " &3sekund"));
            e.setCancelled(true);
            return;
        }

        if (!rpgcore.getChatManager().isChatEnabled()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie &cwylaczony&7!"));
            e.setCancelled(true);
            return;
        }

        if (!rpgcore.getChatManager().getRankReqForChat().equals(RankType.GRACZ)) {
            System.out.println(rpgcore.getPlayerManager().getPlayerGroup(player));
            System.out.println(RankType.getByName(rpgcore.getPlayerManager().getPlayerGroup(player)));
            if (rpgcore.getChatManager().getRankReqForChat().can(RankType.getByName(rpgcore.getPlayerManager().getPlayerGroup(player)))) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od rangi &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                e.setCancelled(true);
                return;
            }
        }

        if (rpgcore.getChatManager().getLvlReqForChat() != 1) {
            if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < rpgcore.getChatManager().getLvlReqForChat()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                e.setCancelled(true);
                return;
            }
        }

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
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().unMutePlayer(player.getUniqueId()));
                    formatuj(e, message, player);
                    if (rpgcore.getPlayerManager().getPlayerGroup(player).equals("H@") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("Admin") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("GM")) {
                        return;
                    }
                    rpgcore.getCooldownManager().givePlayerChatCooldown(player.getUniqueId());
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
        if (rpgcore.getPlayerManager().getPlayerGroup(player).equals("H@") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("Admin") || rpgcore.getPlayerManager().getPlayerGroup(player).equals("GM")) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerChatCooldown(player.getUniqueId());
    }


    private void formatuj(final AsyncPlayerChatEvent e, final String message, final Player player) {
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            e.setCancelled(true);
        }
        final String przedFormatem = Utils.format("<player-klan>&8[&bLvl. &f<player-lvl>&8]<player-group> &7<player-name>:&f <message>");
        final String poFormacie = rpgcore.getChatManager().formatujChat(player, przedFormatem, message);
        e.setFormat(poFormacie);
    }
}
