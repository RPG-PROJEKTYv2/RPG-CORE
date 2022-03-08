package rpg.rpgcore.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final ArrayList<UUID> players = new ArrayList<>();
    private final HashMap<String, UUID> playerUUID = new HashMap<>();
    private final HashMap<UUID, String> playerName = new HashMap<>();

    public UUID getPlayerUUID(final String playerName) {
        return this.playerUUID.get(playerName);
    }

    public String getPlayerName(final UUID uuid) {
        return this.playerName.get(uuid);
    }

    public ArrayList<UUID> getPlayers() {
        return this.players;
    }

    public void createPlayer(final String playerName, final UUID playerUUID) {
        this.players.add(playerUUID);
        this.playerUUID.put(playerName, playerUUID);
        this.playerName.put(playerUUID, playerName);
    }

    public void removeAllPlayers() {
        this.playerName.clear();
        this.playerUUID.clear();
        this.players.clear();
    }
}
