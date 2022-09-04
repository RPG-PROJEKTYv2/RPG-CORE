package rpg.rpgcore.discord;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class EmbedUtil {
    public static EmbedBuilder create(final String title, final String desc, final Color color) {
        return (new EmbedBuilder())
                .setColor(color)
                .setTitle(title, null)
                .setDescription(desc)
                .setTimestamp(Instant.now())
                .setFooter("© 2022 HELLRPG.PL");
    }
}
