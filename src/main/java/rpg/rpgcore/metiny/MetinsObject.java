package rpg.rpgcore.metiny;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinsObject {
    private String name;
    private String world;
    private String coordinates;
    private int maxhealth;
    private int health;
    private int resp;
    private int moby;

    public MetinsObject(String name, String world, String coordinates, int maxhealth, int health, int resp, int moby) {
        this.name = name;
        this.world = world;
        this.coordinates = coordinates;
        this.maxhealth = maxhealth;
        this.health = health;
        this.resp = resp;
        this.moby = moby;
    }
}
