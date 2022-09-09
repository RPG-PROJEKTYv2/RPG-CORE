package rpg.rpgcore.discord;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.GatewayIntent;
import rpg.rpgcore.utils.Utils;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.List;

public class DiscordBot {
    private final JDA jda;

    public DiscordBot(final String token) throws LoginException {
        this.jda = JDABuilder.createDefault(token).setActivity(Activity.watching("Serwer HellRPG.PL")).setStatus(OnlineStatus.ONLINE).enableIntents(GatewayIntent.GUILD_MEMBERS, new GatewayIntent[0]).build();
        System.out.println("[HellRPGCore] Pomyslnie zalogowanoo jako: " + jda.getSelfUser().getName());
    }

    public void sendChannelMessage(final String channelName, final EmbedBuilder embed) {
        this.jda.getTextChannelsByName(channelName, true).get(0).sendMessage((new MessageBuilder()).setEmbeds(new MessageEmbed[]{embed.build()}).build()).queue();
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
