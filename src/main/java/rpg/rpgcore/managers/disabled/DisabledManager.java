package rpg.rpgcore.managers.disabled;

import rpg.rpgcore.RPGCORE;

public class DisabledManager {

    private final RPGCORE rpgcore;
    private Disabled disabled;

    public DisabledManager(final RPGCORE rpgcore) {
        this.disabled = rpgcore.getMongoManager().loadAllDisabled();
        this.rpgcore = rpgcore;
    }

    public Disabled getDisabled() {
        return disabled;
    }

    public void set(final Disabled disabled) {
        this.disabled = disabled;
    }

    public void save() {
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataDisabled(disabled));
    }
}
