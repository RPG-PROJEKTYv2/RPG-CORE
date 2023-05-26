package rpg.rpgcore.utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BossBarUtil {
    private static final Map<UUID, EntityEnderDragon> dragons = new ConcurrentHashMap<>();

    public static void setBar(Player p, String text, float healthPercent) {
        Location loc = p.getLocation();
        WorldServer world = ((CraftWorld) p.getLocation().getWorld()).getHandle();

        EntityEnderDragon dragon = new EntityEnderDragon(world);
        dragon.setLocation(loc.getX(), loc.getY() - 100, loc.getZ(), 0, 0);
        dragon.setCustomNameVisible(true);
        dragon.setCustomName(text);
        dragon.setHealth(healthPercent * dragon.getMaxHealth() / 100);


        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(dragon);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        dragons.put(p.getUniqueId(), dragon);
        PacketPlayOutEntityMetadata data = new PacketPlayOutEntityMetadata(dragon.getId(), dragon.getDataWatcher(), true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(data);

    }

    public static void removeBar(Player p) {
        if (dragons.containsKey(p.getUniqueId())) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(dragons.get(p.getUniqueId()).getId());
            dragons.remove(p.getUniqueId());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static void removeFromBarMap(UUID uuid) {
        dragons.remove(uuid);
    }

    public static void teleportBar(Player p) {
        if (dragons.containsKey(p.getUniqueId())) {
            Location loc = p.getLocation();
            PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(dragons.get(p.getUniqueId()).getId(),
                    (int) loc.getX() * 32, (int) (loc.getY() - 100) * 32, (int) loc.getZ() * 32,
                    (byte) ((int) loc.getYaw() * 256 / 360), (byte) ((int) loc.getPitch() * 256 / 360), false);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static void updateText(Player p, String text) {
        updateBar(p, text, -1);
    }

    public static void updateHealth(Player p, float healthPercent) {
        updateBar(p, null, healthPercent);
    }

    public static void updateBar(Player p, String text, float healthPercent) {
        EntityEnderDragon dragon = dragons.get(p.getUniqueId());
        dragon.setCustomName(text);
        dragon.setCustomNameVisible(true);
        dragon.setHealth(healthPercent * dragon.getMaxHealth() / 100);

        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(dragons.get(p.getUniqueId()).getId(), dragon.getDataWatcher(), true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static boolean hasBar(final UUID uuid) {
        return dragons.containsKey(uuid);
    }

    public static ImmutableSet<UUID> getPlayers() {
        return ImmutableSet.copyOf(dragons.keySet());
    }
}
