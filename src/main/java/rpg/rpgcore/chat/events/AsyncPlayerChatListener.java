package rpg.rpgcore.chat.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.guilds.Guild;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {
    private final RPGCORE rpgcore;
    public AsyncPlayerChatListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String message = e.getMessage().replaceAll("%", "%%");
        final User user = rpgcore.getUserManager().find(uuid);
        e.setCancelled(true);
        if (!user.isHellCodeLogin() && !user.getHellCode().equals("off")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zeby pisac na chacie musisz sie najpierw zalogowac swoim HellCode. Uzyj: &c/hellcode <kod>"));
            return;
        }

        if (rpgcore.getCooldownManager().hasChatCooldown(uuid)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nastepna wiadomosc mozesz wyslac za &c" + Utils.durationToString(rpgcore.getCooldownManager().getPlayerChatCooldown(uuid), false)));
            return;
        }

        if (!rpgcore.getChatManager().isChatEnabled()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie &cwylaczony&7!"));
            return;
        }

        if (!rpgcore.getChatManager().getRankReqForChat().equals(RankType.GRACZ)) {
            if (!rpgcore.getChatManager().getRankReqForChat().can(user.getRankUser().getRankType())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od rangi &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                return;
            }
        }

        if (rpgcore.getChatManager().getLvlReqForChat() != 1) {
            if (user.getLvl() < rpgcore.getChatManager().getLvlReqForChat()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                return;
            }
        }

        if (user.isMuted()) {
            final String[] muteInfo = user.getMuteInfo().split(";");
            if (muteInfo[2].equalsIgnoreCase("Permamentny")) {
                Utils.youAreMuted(player, muteInfo[0], muteInfo[1], muteInfo[2]);
                return;
            }
            try {
                final Date teraz = new Date();
                final Date dataMuta = Utils.dateFormat.parse(muteInfo[2]);

                if (teraz.after(dataMuta)) {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().unMutePlayer(player.getUniqueId()));
                } else {
                    Utils.youAreMuted(player, muteInfo[0], muteInfo[1], Utils.convertDatesToTimeLeft(dataMuta, teraz));
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
                return;
            }
        }
        if (user.getRankUser().isStaff()) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerChatCooldown(uuid);
    }

    private void formatuj(final String message, final Player player) {
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            return;
        }
        final TextComponent main = new TextComponent();
        if (message.contains("@")) {
            rpgcore.getChatManager().pingPlayer(player, message);
        }
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
            final Guild guild = rpgcore.getGuildManager().find(tag).getGuild();
            final TextComponent guildM = new TextComponent(Utils.format("&7[&6" + tag + "&7] "));
            guildM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                    new TextComponent(Utils.format("&7Tag gildii: &6" + tag)),
                    new TextComponent(Utils.format("&7Opis gildi: &6" + guild.getDescription())),
                    new TextComponent(Utils.format("&7Lider Gildii: &6" + (Bukkit.getPlayer(guild.getOwner()).isOnline() ? "&a" + Bukkit.getPlayer(guild.getOwner()).getName() : "&c" + Bukkit.getOfflinePlayer(guild.getOwner()).getName()))),
                    new TextComponent(Utils.format("&7Ilosc czlonkow: &6" + guild.getMembers().size() + "&7/&615")),
                    new TextComponent(Utils.format("&7Poziom: &6" + guild.getLevel())),
                    new TextComponent(Utils.format("&7Doswiadczenie: &6" + Utils.convertDoublesToPercentage(guild.getExp(), rpgcore.getGuildManager().getGuildNextLvlExp(tag)) + "%")),
            }));
            guildM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/klan info " + tag));
            main.addExtra(guildM);
        }
        //TODO Dokonczyc lvl i range i ogolnie chat
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final TextComponent lvl = new TextComponent(Utils.format(""));
        TextComponent rank;
        if (user.getRankUser().isStaff()) {
            rank = new TextComponent(Utils.format(user.getRankUser().getRankType().getPrefix() + player.getName()));
        } else {

        }
        TextComponent before = new TextComponent(Utils.format(Utils.SERVERNAME + "&7" + player.getName() + "&8: &7"));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage();
        }
    }

    @EventHandler
    public void onExecutingCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        final User user = this.rpgcore.getUserManager().find(player.getUniqueId());
        final HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(message.split(" ")[0]);
        if (!user.getRankUser().isStaff()) {
            for (final String command : Arrays.asList("/pl", "/plugins", "bukkit:ban", "logout", "bukkit:banip", "/?", "/ver", "/version", "/bukkit", "/bukkit:ver", "/bukkit:version", "/icanhasbukkit", "/bukkit:help", "bukkit:?", "/me", "/bukkit:me", "/minecraft:me", "/about", "//calc", "//calculate", "mv", "/mv", "/multiverse-core:mv", "multiverse-core:mv")) {
                if (message.toLowerCase().contains(command)) {
                    event.setCancelled(true);
                    player.sendMessage(Utils.format("&cPodana komenda nie została odnaleziona!"));
                    return;
                }
            }
        }
        if (topic == null) {
            event.setCancelled(true);
            player.sendMessage(Utils.format("&cPodana komenda nie została odnaleziona!"));
        }
    }
}
