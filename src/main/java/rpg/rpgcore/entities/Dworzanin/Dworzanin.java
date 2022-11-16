package rpg.rpgcore.entities.Dworzanin;

import net.minecraft.server.v1_8_R3.*;
import rpg.rpgcore.utils.Utils;

import java.util.List;

public class Dworzanin extends EntityVillager {

    public Dworzanin(net.minecraft.server.v1_8_R3.World world, int i) {
        super(world);
        this.world = world;
        this.setProfession(i);
        this.setSize(0.6F, 1.8F);
        List goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List) Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(350000);
        this.setHealth(350000);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(150);
        this.j(false);
    }

}
