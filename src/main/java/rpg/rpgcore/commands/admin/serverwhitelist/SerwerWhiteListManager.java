package rpg.rpgcore.commands.admin.serverwhitelist;

import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.serverwhitelist.objects.SerwerWhiteList;

import java.util.UUID;

public class SerwerWhiteListManager {
    private final SerwerWhiteList whitelist;

    public SerwerWhiteListManager(final RPGCORE rpgcore) {
        this.whitelist = rpgcore.getMongoManager().loadSerwerWhiteList();
    }

    public SerwerWhiteList getWhitelist() {
        return this.whitelist;
    }

    public void addPlayer(final UUID uuid) {
        this.whitelist.getWhitelisted().add(uuid);
        this.save();
    }

    public void removePlayer(final UUID uuid) {
        this.whitelist.getWhitelisted().remove(uuid);
        this.save();
    }

    public boolean isWhiteListed(final UUID uuid) {
        return this.whitelist.getWhitelisted().contains(uuid);
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveSerwerWhiteList(this.whitelist));
    }
}
