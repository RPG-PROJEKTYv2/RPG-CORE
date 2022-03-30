package rpg.rpgcore.managers;


import jdk.jfr.BooleanFlag;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {


    private final ArrayList<UUID> players = new ArrayList<>();
    private final ArrayList<String> playersNames = new ArrayList<>();

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

    public ArrayList<String> getPlayersNames() {
        return this.playersNames;
    }

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo, final String punishmentHistory, final int playerLvl, final double playerExp) {
        this.players.add(playerUUID);
        this.playersNames.add(playerName);
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
        this.playersNames.clear();
        this.playerLvl.clear();
        this.playerExp.clear();
        this.playerBanInfo.clear();
        this.playerPunishmentHistory.clear();
    }

    @Setter
    public void updatePlayerBanInfo(final UUID uuid, final String banInfo) {
        this.playerBanInfo.replace(uuid, banInfo);
    }

    @Getter
    public String getPlayerBanInfo(final UUID uuid) {
        return this.playerBanInfo.get(uuid);
    }

    @Setter
    public void updatePlayerPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        this.playerPunishmentHistory.replace(uuid, punishmentHistory);
    }

    @Setter
    public void updatePlayerLvl(final UUID uuid, final int lvl) {
        this.playerLvl.replace(uuid, lvl);
    }

    @Setter
    public void updatePlayerExp(final UUID uuid, final double exp) {
        this.playerExp.replace(uuid, exp);
    }

    @Getter
    public double getPlayerExp(final UUID uuid) {
        return this.playerExp.get(uuid);
    }

    @Getter
    public String getPlayerPunishmentHistory(final UUID uuid) {
        return this.playerPunishmentHistory.get(uuid);
    }

    @Getter
    public int getPlayerLvl(final UUID uuid) {
        return this.playerLvl.get(uuid);
    }

    @BooleanFlag
    public boolean isBanned(final UUID uuid) {
        return !(getPlayerBanInfo(uuid).equalsIgnoreCase("false"));
    }


    public HashMap<UUID, Integer> getPlayerLvl() {
        return playerLvl;
    }

    public HashMap<UUID, Double> getPlayerExp() {
        return playerExp;
    }

}
