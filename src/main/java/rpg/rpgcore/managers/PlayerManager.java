package rpg.rpgcore.managers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {


    private final ArrayList<UUID> players = new ArrayList<>();

    private final HashMap<String, UUID> playerUUID = new HashMap<>();
    private final HashMap<UUID, String> playerName = new HashMap<>();
    private final HashMap<UUID, String> playerBanInfo = new HashMap<>();
    private final HashMap<UUID, String> playerPunishmentHistory = new HashMap<>();
    private final HashMap<UUID, Integer> playerLvl = new HashMap<>();
    private final HashMap<UUID, Double> playerExp = new HashMap<>();

    public UUID getPlayerUUID(final String playerName) {
        return this.playerUUID.get(playerName);
    }

    public String getPlayerName(final UUID uuid) {
        return this.playerName.get(uuid);
    }

    public ArrayList<UUID> getPlayers() {
        return this.players;
    }

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo, final String punishmentHistory, final int playerLvl, final double playerExp) {
        this.players.add(playerUUID);
        this.playerUUID.put(playerName, playerUUID);
        this.playerName.put(playerUUID, playerName);
        this.playerLvl.put(playerUUID, playerLvl);
        this.playerExp.put(playerUUID, playerExp);
        this.playerBanInfo.put(playerUUID, banInfo);
        this.playerPunishmentHistory.put(playerUUID, punishmentHistory);
    }

    public void removeAllPlayers() {
        this.playerName.clear();
        this.playerUUID.clear();
        this.players.clear();
        this.playerLvl.clear();
        this.playerExp.clear();
        this.playerBanInfo.clear();
        this.playerPunishmentHistory.clear();
    }

    public void updatePlayerBanInfo(final UUID uuid, final String banInfo) {
        this.playerBanInfo.replace(uuid, banInfo);
    }

    public String getPlayerBanInfo(final UUID uuid) {
        return this.playerBanInfo.get(uuid);
    }

    public boolean isBanned(final UUID uuid) {
        return !(getPlayerBanInfo(uuid).equalsIgnoreCase("false"));
    }

    public void updatePlayerPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        this.playerPunishmentHistory.replace(uuid, punishmentHistory);
    }

    public String getPlayerPunishmentHistory(final UUID uuid) {
        return this.playerPunishmentHistory.get(uuid);
    }

    public int getPlayerLvl(final UUID uuid) {return this.playerLvl.get(uuid);}

    public void updatePlayerLvl(final UUID uuid, final int lvl) {this.playerLvl.replace(uuid, lvl);}

    public double getPlayerExp(final UUID uuid) {return this.playerExp.get(uuid);}

    public void updatePlayerExp(final UUID uuid, final double exp) {this.playerExp.replace(uuid, exp);}

    public HashMap<UUID, Integer> getPlayerLvl() {return playerLvl;}

    public HashMap<UUID, Double> getPlayerExp() {return playerExp;}

}
