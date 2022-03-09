package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class NMSManager {

    private final RPGCORE rpgcore;

    public NMSManager(RPGCORE rpgcore){this.rpgcore = rpgcore;}

    public PacketPlayOutChat makeActionBar(String str){return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Utils.format(str) + "" + "\"}"), (byte) 2);}
}
