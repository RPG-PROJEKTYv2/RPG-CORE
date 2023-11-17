package rpg.rpgcore.events;

import lombok.Getter;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.events.headHuntEvent.HeadHuntManager;

public class EventManager {

    @Getter
    private HeadHuntManager headHuntManager;

    public EventManager(final RPGCORE rpgcore) {
        this.headHuntManager = new HeadHuntManager(rpgcore);
    }
}
