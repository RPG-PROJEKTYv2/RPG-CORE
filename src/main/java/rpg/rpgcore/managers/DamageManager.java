package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final double dmg, Location entityLocation){
        entityLocation.add(0, 0, 0.5);
        WorldServer s = ((CraftWorld) entityLocation.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(entityLocation.getX(), entityLocation.getY(), entityLocation.getZ(), 0, 0);
        stand.setCustomName(ChatColor.RED + "- " + Utils.df.format(dmg));
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        for (Player p : Bukkit.getOnlinePlayers()){
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {destroySendHologram(stand);}, 20L);
    }

    public void destroySendHologram(final EntityArmorStand stand){
        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroyPacket);
        }
    }

    public void sendActionBarDmg(final Player target, final Entity mob){
        final String mobName = mob.getCustomName();
        //final double mob
        //rpgcore.getNmsManager().sendActionBar(target, rpgcore.getNmsManager().makeActionBar());
    }
}
