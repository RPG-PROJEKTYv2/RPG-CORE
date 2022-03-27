package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class NMSManager {

    public PacketPlayOutChat makeActionBar(final String str) {
        return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), (byte) 2);
    }

    public PacketPlayOutTitle makeTitle(final String str, final int fadeIn, final int last, final int fadeOut) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), fadeIn, last, fadeOut);
    }

    public PacketPlayOutTitle makeSubTitle(final String str, final int fadeIn, final int last, final int fadeOut) {
        return new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), fadeIn, last, fadeOut);
    }

    public void sendActionBar(final Player player, final PacketPlayOutChat packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendTitleAndSubTitle(final Player player, final PacketPlayOutTitle title, final PacketPlayOutTitle subtitle) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subtitle);
    }
}
