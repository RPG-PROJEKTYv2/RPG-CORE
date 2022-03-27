package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import static rpg.rpgcore.utils.Utils.random;

public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final double dmg, final Location entityLocation, final Player p) {
        entityLocation.add(0, 0, 0.5);
        final WorldServer s = ((CraftWorld) entityLocation.getWorld()).getHandle();
        final EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(entityLocation.getX() + random.nextDouble(), entityLocation.getY(), entityLocation.getZ(), 0, 0);
        stand.setCustomName(ChatColor.DARK_RED + "- " + Utils.df.format(dmg));
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);

        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> this.destroySendHologram(stand, p), 20L);
    }

    public void destroySendHologram(final EntityArmorStand stand, final Player p) {
        final PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroyPacket);

    }
}
