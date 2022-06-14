package rpg.rpgcore.tab;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.Collections;

public class PacketManager {
    private static final Reflection.MethodInvoker HANDLE_METHOD_INVOKER = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle");
    private static final Reflection.MethodInvoker SEND_PACKET_METHOD_INVOKER = Reflection.getMethod(Reflection.getMinecraftClass("PlayerConnection"), "sendPacket", Reflection.getMinecraftClass("Packet"));
    private static final Reflection.MethodInvoker BASE_COMPONENT_METHOD_INVOKER = Reflection.getTypedMethod(Reflection.getMinecraftClass("IChatBaseComponent$ChatSerializer"), "a", Reflection.getMinecraftClass("IChatBaseComponent"), String.class);
    private static final Reflection.FieldAccessor<Object> PLAYER_CONNECTION_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");
    private static final Reflection.FieldAccessor<Object> HEADER_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"), "a");
    private static final Reflection.FieldAccessor<Object> FOOTER_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"), "b");
    private static final Reflection.FieldAccessor<Object> ACTION_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), "a");
    private static final Reflection.FieldAccessor<Object> DATA_LIST_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), "b");
    private static final Reflection.ConstructorInvoker PLAYER_HEADER_FOOTER_PACKET_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"));
    private static final Reflection.ConstructorInvoker PLAYER_INFO_PACKET_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"));
    private static final Reflection.ConstructorInvoker PLAYER_INFO_DATA_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$PlayerInfoData"), Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), GameProfile.class, int.class, Reflection.getMinecraftClass("WorldSettings$EnumGamemode"), Reflection.getMinecraftClass("IChatBaseComponent"));
    private static final Object[] PLAYER_INFO_ACTION_ENUM_CONSTANTS = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants();
    private static final Object WORLD_SETTINGS_GAMEMODE_ENUM_CONSTANT = Reflection.getMinecraftClass("WorldSettings$EnumGamemode").getEnumConstants()[1];

    public static void send(final Player player, final Object... packets) {
        if (HANDLE_METHOD_INVOKER == null
                || SEND_PACKET_METHOD_INVOKER == null
                || PLAYER_CONNECTION_FIELD_ACCESSOR == null)
            return;

        final Object playerConnection = PLAYER_CONNECTION_FIELD_ACCESSOR.get(HANDLE_METHOD_INVOKER.invoke(player));
        for (final Object packet : packets)
            SEND_PACKET_METHOD_INVOKER.invoke(playerConnection, packet);
    }

    public static void send(final Player player, final String header, final String footer) {
        final Object packet = PLAYER_HEADER_FOOTER_PACKET_CONSTRUCTOR_INVOKER.invoke();
        HEADER_FIELD_ACCESSOR.set(packet, toJson(header));
        FOOTER_FIELD_ACCESSOR.set(packet, toJson(footer));

        send(player, packet);
    }

    public static void send(final Player player, final TabProfile profile, final String displayName, final PlayerInfoAction action) {
        final Object packet = PLAYER_INFO_PACKET_CONSTRUCTOR_INVOKER.invoke();
        ACTION_FIELD_ACCESSOR.set(packet, PLAYER_INFO_ACTION_ENUM_CONSTANTS[action.getActionID()]);
        DATA_LIST_FIELD_ACCESSOR.set(packet, Collections.singletonList(getInfo(packet, profile, displayName)));

        send(player, packet);
    }

    public static Object getInfo(final Object packet, final TabProfile profile, final String displayName) {
        return PLAYER_INFO_DATA_CONSTRUCTOR_INVOKER.invoke(packet, profile, 9999, WORLD_SETTINGS_GAMEMODE_ENUM_CONSTANT, toJson(displayName));
    }

    public static Object toJson(final String string) {
        return BASE_COMPONENT_METHOD_INVOKER.invoke(null, "{\"text\": \"" + Utils.format(string) + "\"}");
    }
}
