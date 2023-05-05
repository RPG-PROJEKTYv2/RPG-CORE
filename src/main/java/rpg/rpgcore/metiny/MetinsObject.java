package rpg.rpgcore.metiny;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinsObject {
    private String name;
    private String world;
    private double x;
    private double y;
    private double z;
    private int maxhealth;
    private int health;
    private int resp;
    private int moby;

    public MetinsObject(final String name, final String world, final double x, final double y, final double z, final int maxhealth, final int health, final int resp, final int moby) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxhealth = maxhealth;
        this.health = health;
        this.resp = resp;
        this.moby = moby;
    }
}
