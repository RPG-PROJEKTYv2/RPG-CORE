package rpg.rpgcore.server;

import com.google.common.collect.ImmutableSet;
import rpg.rpgcore.RPGCORE;

import java.util.Map;

public class ServerManager {
    private final Map<String, ServerUser> serverUserMap;

    public ServerManager(RPGCORE rpgcore) {
        this.serverUserMap = rpgcore.getMongoManager().loadAllServer();
    }

    public void add(ServerUser serverUser) {
        this.serverUserMap.put(serverUser.getName(), serverUser);
    }

    public ServerUser find(String string) {
        return this.serverUserMap.get(string);
    }

    public boolean isServerUser(String string) {
        return this.serverUserMap.containsKey(string);
    }

    public ImmutableSet<ServerUser> getServer() {
        return ImmutableSet.copyOf(this.serverUserMap.values());
    }
}
