package rpg.rpgcore.whitelist;

import rpg.rpgcore.RPGCORE;

public class WhitelistManager {
    private final Whitelist whitelist;

    public WhitelistManager() {
        this.whitelist = new Whitelist(RPGCORE.getInstance().getMongoManager().loadAllWhiteList());
    }
}
