package rpg.rpgcore.metiny;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetinsObject {
    private String world;
    private String coordinates;
    private int maxhealth;
    private int health;
    private int resp;

    public MetinsObject(String world, String coordinates, int maxhealth, int health, int resp) {
        this.world = world;
        this.coordinates = coordinates;
        this.maxhealth = maxhealth;
        this.health = health;
        this.resp = resp;
    }
}
