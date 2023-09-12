package rpg.rpgcore.discord;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class KickMessageListener extends ListenerAdapter {

    final RPGCORE rpgcore = RPGCORE.getInstance();

    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        final Message message = e.getMessage();
        final User user = e.getAuthor();
        final String displayText = message.getContentDisplay();
        final Channel channel = e.getChannel();

        if (!channel.getName().contains("Kick-") || !Objects.requireNonNull(((TextChannel) channel).getParentCategoryId()).equals("1105464211414462574"))
            return;
        if (!displayText.startsWith("!") || !displayText.contains("!kick")) return;

        final String[] args = Arrays.stream(displayText.split(" ")).skip(1).toArray(String[]::new);
        if (args.length != 2) {
            this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> {
                MessageCreateBuilder send = new MessageCreateBuilder().setEmbeds(EmbedUtil.create(":no_entry_sign: Błąd!", "Poprawne użycie tej komendy to: !kick <nick> <hellcode>", new Color(255, 0, 33)).build());
                ((TextChannel) channel).sendMessage(send.build()).complete().delete().queueAfter(5L, TimeUnit.SECONDS);
            });
            return;
        }

        final String nick = args[0];
        final String hellcode = args[1];

        final rpg.rpgcore.user.User serverUser = this.rpgcore.getUserManager().find(nick);

        if (Objects.isNull(serverUser)) {
            this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> {
                MessageCreateBuilder send = new MessageCreateBuilder().setEmbeds(EmbedUtil.create(":no_entry_sign: Błąd!", "Nie znaleziono gracza o nicku: " + nick, new Color(255, 0, 33)).build());
                ((TextChannel) channel).sendMessage(send.build()).complete().delete().queueAfter(5L, TimeUnit.SECONDS);
            });
            return;
        }

        if (!serverUser.getHellCode().equals(hellcode)) {
            this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> {
                MessageCreateBuilder send = new MessageCreateBuilder().setEmbeds(EmbedUtil.create(":no_entry_sign: Błąd!", "Podany hellcode jest nieprawidłowy!", new Color(255, 0, 33)).build());
                ((TextChannel) channel).sendMessage(send.build()).complete().delete().queueAfter(5L, TimeUnit.SECONDS);
            });
            return;
        }

        final Player player = this.rpgcore.getServer().getPlayer(serverUser.getId());

        if (Objects.isNull(player)) {
            this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> {
                MessageCreateBuilder send = new MessageCreateBuilder().setEmbeds(EmbedUtil.create(":no_entry_sign: Błąd!", "Gracz nie jest online!", new Color(255, 0, 33)).build());
                ((TextChannel) channel).sendMessage(send.build()).complete().delete().queueAfter(5L, TimeUnit.SECONDS);
            });
            return;
        }

        this.rpgcore.getServer().getScheduler().runTask(this.rpgcore, () -> player.kickPlayer(Utils.format(Utils.SERVERNAME + "\n&7Zostales wyrzucony z serwera przez &c" + user.getName() + "&7!\n" +
                "&7Powod: &6Kick na prosbe!\n\n" +
                "&8Ta czynnosc zostala wykonana przez discorda (&6dc.hellrpg.pl&8)!\n" +
                "&8Naduzywanie jej, moze skutkowac zabraniem dostepu do tej komendy dla konta\n\n" +
                "&4Jezeli uwazasz, ze to blad, zglos sie z ss'em\n" +
                "&4tej wiadomosci do administracji serwera!")));
        Bukkit.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Gracz &6" + player.getName() + "&7 zostal wyrzucony z serwera przez &6HellRPG - BOT&7! &7Powod: &6Kick na prosbe!"));
        this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> {
            MessageCreateBuilder send = new MessageCreateBuilder().setEmbeds(
                    EmbedUtil.create(":white_check_mark: Sukces!", "Gracz " + nick + " został wyrzucony z serwera!\n\n" +
                    "**UWAGA!** Ten kanał zostanie usunięty za 5 sekund\n" +
                    "Jeśli masz jeszcze jakąś sprawę\n" +
                    "zachęcamy do otwarcie nowego ticketa", new Color(0, 255, 0)).build());
            ((TextChannel) channel).sendMessage(send.build()).complete().delete().queueAfter(5L, TimeUnit.SECONDS);
            channel.delete().completeAfter(6, TimeUnit.SECONDS);
            RPGCORE.getDiscordBot().sendChannelMessage("dc-kick-log", EmbedUtil.create("**DC KICK**",
                    "**Konto:** " + serverUser.getName() + "\n" +
                            "**Kickujacy:** " + user.getName() + "\n" +
                            "**Hellcode:** ||" + hellcode + "||\n" +
                            "**Data:** " + Utils.dateFormat.format(new Date()), Color.decode("#2794e3")));
        });


    }

}
