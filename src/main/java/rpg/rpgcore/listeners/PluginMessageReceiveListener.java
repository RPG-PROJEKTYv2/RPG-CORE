package rpg.rpgcore.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;

import java.awt.*;

public class PluginMessageReceiveListener implements PluginMessageListener {
    private final RPGCORE rpgcore;

    public PluginMessageReceiveListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getMessenger().registerIncomingPluginChannel(this.rpgcore, "rpgproxy:main:log", this);
    }
    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equals("rpgproxy:main:log")) return;
        final ByteArrayDataInput in = ByteStreams.newDataInput(message);
        final String subchannel = in.readUTF();
        if (!subchannel.equals("logs")) return;
        final String discordChannel = in.readUTF();
        final String logTitle = in.readUTF();
        final String logMessage = in.readUTF();
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> RPGCORE.getDiscordBot().sendChannelMessage(discordChannel, EmbedUtil.create(logTitle, logMessage, Color.decode("#d63c11"))));
    }
}
