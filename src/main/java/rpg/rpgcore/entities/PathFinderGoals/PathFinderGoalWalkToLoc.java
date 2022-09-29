package rpg.rpgcore.entities.PathFinderGoals;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import org.bukkit.Location;

public class PathFinderGoalWalkToLoc extends PathfinderGoal {
    private double speed;
    private EntityInsentient entity;
    private Location loc;
    private NavigationAbstract navigation;

    public PathFinderGoalWalkToLoc(EntityInsentient entity, Location loc, double speed) {
        this.entity = entity;
        this.loc = loc;
        this.navigation = this.entity.getNavigation();
        this.speed = speed;
    }

    @Override
    public boolean a() {
        return false;
    }


    public void c() {
        if (this.loc.distance(this.entity.getBukkitEntity().getLocation()) > 20) {
            System.out.println("[HellRPGCore] Can not spawn the entity because the location was more than 20 block away");
            return;
        }
        PathEntity pathEntity = this.navigation.a(loc.getX(), loc.getY(), loc.getZ());
        this.navigation.a(pathEntity, speed);
    }


}
