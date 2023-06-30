package rpg.rpgcore.npc.gornik.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GornikUser {
    private final UUID uuid;
    private int mission, progress;
    private float timeLeft;

    public GornikUser(final UUID uuid) {
        this.uuid = uuid;
        this.mission = 0;
        this.progress = 0;
        this.timeLeft = 0;
    }
}
