package rpg.rpgcore.npc.handlarz.events;

import org.bukkit.event.Listener;
import rpg.rpgcore.RPGCORE;

public class HandlarzInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public HandlarzInventoryClickListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
}
