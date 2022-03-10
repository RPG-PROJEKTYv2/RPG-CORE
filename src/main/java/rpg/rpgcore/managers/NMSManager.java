package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import rpg.rpgcore.utils.Utils;

public class NMSManager {

    public PacketPlayOutChat makeActionBar(final String str) {
        return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), (byte) 2);
    }
}
