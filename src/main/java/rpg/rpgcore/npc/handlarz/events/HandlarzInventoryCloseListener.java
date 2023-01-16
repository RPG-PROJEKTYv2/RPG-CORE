package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.event.Listener;
import rpg.rpgcore.RPGCORE;

public class HandlarzInventoryCloseListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryCloseListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
}
