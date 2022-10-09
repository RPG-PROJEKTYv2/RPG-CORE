package rpg.rpgcore.chat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {
    private final RPGCORE rpgcore;
    public AsyncPlayerChatListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String message = e.getMessage().replaceAll("%", "%%");
        final User user = rpgcore.getUserManager().find(uuid);

        if (!user.isHellCodeLogin() && !user.getHellCode().equals("off")) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zeby pisac na chacie musisz sie najpierw zalogowac swoim HellCode. Uzyj: &c/hellcode <kod>"));
            return;
        }

        if (rpgcore.getCooldownManager().hasChatCooldown(uuid)) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nastepna wiadomosc mozesz wyslac za &c" + Utils.durationToString(rpgcore.getCooldownManager().getPlayerChatCooldown(uuid), false)));
            return;
        }

        if (!rpgcore.getChatManager().isChatEnabled()) {
            e.setCancelled(true);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie &cwylaczony&7!"));
            return;
        }

        if (!rpgcore.getChatManager().getRankReqForChat().equals(RankType.GRACZ)) {
            if (!rpgcore.getChatManager().getRankReqForChat().can(user.getRankUser().getRankType())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od rangi &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                e.setCancelled(true);
                return;
            }
        }

        if (rpgcore.getChatManager().getLvlReqForChat() != 1) {
            if (user.getLvl() < rpgcore.getChatManager().getLvlReqForChat()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                e.setCancelled(true);
                return;
            }
        }

        if (user.isMuted()) {
            final String[] muteInfo = user.getMuteInfo().split(";");
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
                } else {
                    Utils.youAreMuted(player, muteInfo[0], muteInfo[1], Utils.convertDatesToTimeLeft(dataMuta, teraz));
                    e.setCancelled(true);
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
                e.setCancelled(true);
                return;
            }
        }
        formatuj(e, message, player);
        if (user.getRankUser().isStaff()) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerChatCooldown(uuid);
    }

    private void formatuj(final AsyncPlayerChatEvent e, final String message, final Player player) {
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            e.setCancelled(true);
            return;
        }
        if (message.contains("@")) {
            rpgcore.getChatManager().pingPlayer(player, message);
        }
        e.setFormat(rpgcore.getChatManager().formatujChat(player, Utils.CHAT_FORMAT, message));
    }
}
