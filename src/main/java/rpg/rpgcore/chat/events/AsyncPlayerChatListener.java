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
import rpg.rpgcore.utils.globalitems.GlobalItem;

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
        final String message = e.getMessage();
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

        if (!rpgcore.getChatManager().isChatEnabled() && (!user.getRankUser().isStaff() && !user.isAdminCodeLogin())) {
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

        if (user.isMuted() && (!user.getRankUser().isStaff() && !user.isAdminCodeLogin())) {
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
        if (Utils.removeColor(message).isEmpty()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz wyslac pustej wiadomosci!"));
            return;
        }
        formatuj(player, message);
        player.getInventory().addItem(GlobalItem.getItem("I3", 1));
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerChatCooldown(uuid);
    }

    private void formatuj(final Player player, final String message) {
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            return;
        }
        final TextComponent main = new TextComponent("");
        if (message.contains("@")) {
            rpgcore.getChatManager().pingPlayer(player, message);
        }
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
            final Guild guild = rpgcore.getGuildManager().find(tag).getGuild();
            final TextComponent guildM = new TextComponent(Utils.format("&8[&e" + tag + "&8] "));
            guildM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                    new TextComponent(Utils.format("&7Tag gildii: &6" + tag +
                            "\n&7Opis gildi: &6" + guild.getDescription() +
                            "\n&7Lider Gildii: &6" + (Bukkit.getPlayer(guild.getOwner()).isOnline() ? "&a" + Bukkit.getPlayer(guild.getOwner()).getName() : "&c" + Bukkit.getOfflinePlayer(guild.getOwner()).getName()) +
                            "\n&7Ilosc czlonkow: &6" + guild.getMembers().size() + "&7/&615" +
                            "\n&7Poziom: &6" + guild.getLevel() +
                            "\n&7Doswiadczenie: &6" + String.format("%.2f", Utils.convertDoublesToPercentage(guild.getExp(), rpgcore.getGuildManager().getGuildNextLvlExp(tag))) + "%"))}));
            guildM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/klan info " + tag));
            main.addExtra(guildM);
        }
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final TextComponent lvl = (user.getLvl() == 130 ? new TextComponent(Utils.format("&8[&bLvl. &4&lMAX&8] ")) : new TextComponent(Utils.format("&8[&bLvl. &f" + user.getLvl() + "&8] ")));
        lvl.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent(Utils.format("&7Poziom: &6" + user.getLvl() +
                        "\n&7Postep do nastepnego poziomu: &6" + String.format("%.2f", Utils.convertDoublesToPercentage(user.getExp(), rpgcore.getLvlManager().getExpForLvl(user.getLvl()))) + "%"))}));
        main.addExtra(lvl);

        TextComponent rank;
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            rank = new TextComponent(Utils.format(user.getRankUser().getRankType().getPrefix() + player.getName() + "&f: " + message));
        } else {
            rank = new TextComponent(Utils.format(user.getRankPlayerUser().getRankType().getPrefix() + player.getName() + "&f: " + Utils.removeColor(message)));
        }
        rank.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Utils.format("&7Kliknij, zeby napisac wiadomosc"))}));
        rank.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
        main.addExtra(rank);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(main);
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
