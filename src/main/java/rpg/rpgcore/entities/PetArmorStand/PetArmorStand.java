package rpg.rpgcore.entities.PetArmorStand;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PetArmorStand extends EntityArmorStand {

    public UUID player;
    public int tick = 0;
    public PetArmorStand(net.minecraft.server.v1_8_R3.World world, Player player) {
        super(world);
        this.player = player.getUniqueId();
    }
    // This is the update method of the entity
    @Override
    public void t_() {

        // Getting the nms class of the player to have easier access to some
        // data.
        EntityPlayer p = ((CraftPlayer) Bukkit.getPlayer(player)).getHandle();
        // Setting the position
        double x = Math.cos(Math.toRadians(p.yaw - 180));
        double z = Math.sin(Math.toRadians(p.yaw - 180));
        setPosition(p.locX + x, p.locY + 0.1 + fly(), p.locZ + z);
        //Setting the yaw and pitch of the armor stand. If it fails we get another change to set it with the codes below.
        this.yaw = p.yaw;
        this.pitch = p.pitch;
        // Setting the yaw of the armor stand. I don't know for sure if this
        // works.
        setBodyPose(new Vector3f(0.0F, p.yaw, 0.0F));
        setSmall(true);
        setInvisible(true);
        // Setting the pitch of the head. I also don't know for sure if this
        // works.
        //setHeadPose(new Vector3f(p.pitch, 0.0F, 0.0F));

        // If you want to still do the normal entity update instead of just
        // standing totally still. Use this line.
        // super.m();
    }

    private double fly() {
        int maxTime = 200;
        double toAdd = Math.sin(Math.toRadians(tick * 360.0 / maxTime)/2);
        tick++;
        if (tick >= maxTime) {
            tick = 0;
        }
        return toAdd;
    }


}
