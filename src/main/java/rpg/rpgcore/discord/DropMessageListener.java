package rpg.rpgcore.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.awt.*;
import java.util.Objects;

public class DropMessageListener extends ListenerAdapter {

    final RPGCORE rpgcore = RPGCORE.getInstance();

    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        final Message message = e.getMessage();
        final User user = e.getAuthor();
        final Channel channel = e.getChannel();

        if (!channel.getId().equals("1112498451536683098")) return;
        if (user.getId().equals("1014495263156600832")) {
            final MessageEmbed embed = message.getEmbeds().get(0);
            assert embed != null;
            if (Objects.requireNonNull(embed.getTitle()).contains("Niestety!") || embed.getTitle().contains("Błąd!")) return;
            String nick = "";
            String prize = "";
            for (String s : Objects.requireNonNull(embed.getDescription()).split("\n")) {
                if (s.contains("Nick:")) nick = s.trim().replace("**Nick:** ", "").replaceAll("`", "");
                else if (s.contains("Nagroda:")) prize = s.trim().replace("**Nagroda:** ", "").replaceAll("`", "");
            }
            if (nick.isEmpty() || prize.isEmpty()) return;
            if (!rpgcore.getUserManager().isUserName(nick)) {
                RPGCORE.getDiscordBot().sendChannelMessage("drop-logs", new EmbedBuilder()
                        .setTitle("**Drop!**")
                        .setDescription("Gracz `" + nick + "` znalazł nagrodę, ale nie znaleziono go w bazie!\n" +
                                "Nagroda: **" + prize + "**")
                        .setColor(Color.decode("#0fb8d6"))
                        .setFooter("© 2023 HELLRPG.PL"));
                return;
            }
            if (prize.contains("HS") && Bukkit.getServer().getPlayer(nick) == null) {
                RPGCORE.getDiscordBot().sendChannelMessage("drop-logs", new EmbedBuilder()
                        .setTitle("**Drop!**")
                        .setDescription("Gracz `" + nick + "` znalazł nagrodę, ale nie znaleziono go na serwerze!\n" +
                                "Nagroda: **" + prize + "**")
                        .setColor(Color.decode("#0fb8d6"))
                        .setFooter("© 2023 HELLRPG.PL"));
                return;
            }
            final String finalNick = nick;
            final String finalPrize = prize;
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.givePrize(rpgcore.getUserManager().find(finalNick), finalPrize), 1L);
        }
    }

    private void givePrize(final rpg.rpgcore.user.User user, final String prize) {
        final Player player = Bukkit.getServer().getPlayer(user.getId());
        switch (prize) {
            case "ELITA na 7 dni":
                if (user.getRankPlayerUser().hasRank(RankTypePlayer.ELITA)) {
                    final long time = Utils.durationFromString("7d", false);
                    user.getRankPlayerUser().setTime(user.getRankPlayerUser().getTime() + time);
                    break;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "premium " + user.getName() + " Elita 7d true");
                break;
            case "ELITA na 3 dni":
                if (user.getRankPlayerUser().hasRank(RankTypePlayer.ELITA)) {
                    final long time = Utils.durationFromString("3d", false);
                    user.getRankPlayerUser().setTime(user.getRankPlayerUser().getTime() + time);
                    break;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "premium " + user.getName() + " Elita 3d true");
                break;
            case "VIP na 7 dni":
                if (user.getRankPlayerUser().hasRank(RankTypePlayer.VIP)) {
                    final long time = Utils.durationFromString("7d", false);
                    user.getRankPlayerUser().setTime(user.getRankPlayerUser().getTime() + time);
                    break;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "premium " + user.getName() + " Vip 7d true");
                break;
            case "VIP na 3 dni":
                if (user.getRankPlayerUser().hasRank(RankTypePlayer.VIP)) {
                    final long time = Utils.durationFromString("3d", false);
                    user.getRankPlayerUser().setTime(user.getRankPlayerUser().getTime() + time);
                    break;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "premium " + user.getName() + " Vip 3d true");
                break;
            case "HS 25":
                player.getInventory().addItem(GlobalItem.getHells(25).clone());
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItemShop&8&l]", 5, 20, 5),
                        RPGCORE.getInstance().getNmsManager().makeSubTitle("&7Pomyslnie zakupiles pakiet &6HS 25&7! &6Dziekujemy!", 5, 20, 5));
                break;
            case "HS 15":
                player.getInventory().addItem(GlobalItem.getHells(15).clone());
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItemShop&8&l]", 5, 20, 5),
                        RPGCORE.getInstance().getNmsManager().makeSubTitle("&7Pomyslnie zakupiles pakiet &6HS 15&7! &6Dziekujemy!", 5, 20, 5));
                break;
            case "HS 5":
                player.getInventory().addItem(GlobalItem.getHells(5).clone());
                RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&6&lItemShop&8&l]", 5, 20, 5),
                        RPGCORE.getInstance().getNmsManager().makeSubTitle("&7Pomyslnie zakupiles pakiet &6HS 5&7! &6Dziekujemy!", 5, 20, 5));
                break;
        }
        if (player == null) return;
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otrzymales/-as nagrode z dropu na naszym discordzie! &6&lGratulacje!"));
    }
}
