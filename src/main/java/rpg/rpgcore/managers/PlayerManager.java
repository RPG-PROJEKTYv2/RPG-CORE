package rpg.rpgcore.managers;


//import jdk.jfr.BooleanFlag;
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
    private final HashMap<UUID, Integer> osMoby = new HashMap<>();
    private final HashMap<UUID, Integer> osLudzie = new HashMap<>();
    private final HashMap<UUID, Integer> osSakwy = new HashMap<>();
    private final HashMap<UUID, Integer> osNiesy = new HashMap<>();
    private final HashMap<UUID, Integer> osRybak = new HashMap<>();
    private final HashMap<UUID, Integer> osDrwal = new HashMap<>();

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

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo, final String punishmentHistory, final int playerLvl, final double playerExp, final int osMoby, final int osLudzie, final int osSakwy, final int osNiesy, final int osRybak, final int osDrwal) {
        this.players.add(playerUUID);
        this.playersNames.add(playerName);
        this.playerUUID.put(playerName, playerUUID);
        this.playerName.put(playerUUID, playerName);
        this.playerLvl.put(playerUUID, playerLvl);
        this.playerExp.put(playerUUID, playerExp);
        this.playerBanInfo.put(playerUUID, banInfo);
        this.playerPunishmentHistory.put(playerUUID, punishmentHistory);
        this.osMoby.put(playerUUID, osMoby);
        this.osLudzie.put(playerUUID, osLudzie);
        this.osSakwy.put(playerUUID, osSakwy);
        this.osNiesy.put(playerUUID, osNiesy);
        this.osRybak.put(playerUUID, osRybak);
        this.osDrwal.put(playerUUID, osDrwal);
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
        this.osMoby.clear();
        this.osLudzie.clear();
        this.osSakwy.clear();
        this.osNiesy.clear();
        this.osRybak.clear();
        this.osDrwal.clear();
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

    //@BooleanFlag ??????
    public boolean isBanned(final UUID uuid) {
        return !(getPlayerBanInfo(uuid).equalsIgnoreCase("false"));
    }


    public HashMap<UUID, Integer> getPlayerLvl() {
        return playerLvl;
    }

    public HashMap<UUID, Double> getPlayerExp() {
        return playerExp;
    }

    @Setter
    public void updatePlayerOsMoby(final UUID uuid, final int noweOsMoby) {
        this.osMoby.replace(uuid, noweOsMoby);
    }

    @Setter
    public void updatePlayerOsLudzie(final UUID uuid, final int noweOsLudzie) {
        this.osLudzie.replace(uuid, noweOsLudzie);
    }

    @Setter
    public void updatePlayerOsSakwy(final UUID uuid, final int noweOsSakwy) {
        this.osSakwy.replace(uuid, noweOsSakwy);
    }

    @Setter
    public void updatePlayerOsNiesy(final UUID uuid, final int noweOsNiesy) {
        this.osNiesy.replace(uuid, noweOsNiesy);
    }

    @Setter
    public void updatePlayerOsRybak(final UUID uuid, final int noweOsRybak) {
        this.osRybak.replace(uuid, noweOsRybak);
    }

    @Setter
    public void updatePlayerOsDrwal(final UUID uuid, final int noweOsDrwal) {
        this.osDrwal.replace(uuid, noweOsDrwal);
    }

    @Getter
    public int getPlayerOsMoby(final UUID uuid) {
        return this.osMoby.get(uuid);
    }

    @Getter
    public int getPlayerOsLudzie(final UUID uuid) {
        return this.osLudzie.get(uuid);
    }

    @Getter
    public int getPlayerOsSakwy(final UUID uuid) {
        return this.osSakwy.get(uuid);
    }

    @Getter
    public int getPlayerOsNiesy(final UUID uuid) {
        return this.osNiesy.get(uuid);
    }

    @Getter
    public int getPlayerOsRybak(final UUID uuid) {
        return this.osRybak.get(uuid);
    }

    @Getter
    public int getPlayerOsDrwal(final UUID uuid) {
        return this.osDrwal.get(uuid);
    }

}
