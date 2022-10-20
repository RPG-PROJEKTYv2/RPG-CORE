package rpg.rpgcore.entities.ZamekNieskonczonosciBoss;

import net.minecraft.server.v1_8_R3.*;
import rpg.rpgcore.utils.Utils;

import java.util.List;

public class ZamekNieskonczonosciBoss extends EntityWither {


    public ZamekNieskonczonosciBoss(World world) {
        super(world);
        this.world = world;
        List goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.r(250);
        this.k(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }
}
