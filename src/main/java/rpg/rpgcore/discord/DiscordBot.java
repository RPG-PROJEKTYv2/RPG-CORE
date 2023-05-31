package rpg.rpgcore.discord;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import rpg.rpgcore.utils.Utils;

import javax.security.auth.login.LoginException;
import java.util.List;

public class DiscordBot extends ListenerAdapter {
    private final JDA jda;

    public DiscordBot(final String token) throws LoginException {
        this.jda = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("Serwer HellRPG.PL"))
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT).build();
        System.out.println("[HellRPGCore] Pomyslnie zalogowanoo jako: " + jda.getSelfUser().getName());
        this.jda.addEventListener(new KickMessageListener(), new DropMessageListener());
    }

    public void sendChannelMessage(final String channelName, final EmbedBuilder embed) {
        this.jda.getTextChannelsByName(channelName, true).get(0).sendMessage(new MessageCreateBuilder().setEmbeds(new MessageEmbed[]{embed.build()}).build()).queue();
    }

    public String buildStringFromLore(final List<String> lore) {
        final StringBuilder builder = new StringBuilder();
        for (final String line : lore) {
            builder.append("- ").append(Utils.removeColor(line)).append("\n");
        }
        return builder.toString();
    }

    public JDA getBot() {
        return this.jda;
    }
}
