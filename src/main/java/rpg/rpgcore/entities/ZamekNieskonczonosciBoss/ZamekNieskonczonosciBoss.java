package rpg.rpgcore.entities.ZamekNieskonczonosciBoss;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.List;

public class ZamekNieskonczonosciBoss extends EntityWither {


    public ZamekNieskonczonosciBoss(World world) {
        super(world);
        List goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.r(2000);
        this.k(true);
        /*this.datawatcher.watch(7, 1);
        this.datawatcher.watch(8, 1);
        this.datawatcher.watch(9, 1);
        this.datawatcher.watch(15, 1);*/


        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        PacketDataSerializer test = new PacketDataSerializer(Unpooled.buffer());
        test.writeByte(1);
        final PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("0x0C", test);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            for (Player player : world.getWorld().getPlayers()) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }, 100L);
    }
}
