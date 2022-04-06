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
    private final HashMap<UUID, Integer> osGornik = new HashMap<>();
    private final HashMap<UUID, String> osMobyAccept = new HashMap<>();
    private final HashMap<UUID, String> osLudzieAccept = new HashMap<>();
    private final HashMap<UUID, String> osSakwyAccept = new HashMap<>();
    private final HashMap<UUID, String> osNiesyAccept = new HashMap<>();
    private final HashMap<UUID, String> osRybakAccept = new HashMap<>();
    private final HashMap<UUID, String> osDrwalAccept = new HashMap<>();
    private final HashMap<UUID, String> osGornikAccept = new HashMap<>();

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

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo, final String punishmentHistory, final int playerLvl, final double playerExp,
                             final int osMoby, final int osLudzie, final int osSakwy, final int osNiesy, final int osRybak, final int osDrwal, final int osGornik,
                             final String osMobyAccpet, final String osLudzieAccept, final String osSakwyAccept, final String osNiesyAccept, final String osRybakAccept, final String osDrwalAccept, final String osGornikAccept) {
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
        this.osGornik.put(playerUUID, osGornik);
        this.osMobyAccept.put(playerUUID, osMobyAccpet);
        this.osLudzieAccept.put(playerUUID, osSakwyAccept);
        this.osSakwyAccept.put(playerUUID, osSakwyAccept);
        this.osNiesyAccept.put(playerUUID, osNiesyAccept);
        this.osRybakAccept.put(playerUUID, osRybakAccept);
        this.osDrwalAccept.put(playerUUID, osDrwalAccept);
        this.osGornikAccept.put(playerUUID, osGornikAccept);
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
        this.osGornik.clear();
        this.osMobyAccept.clear();
        this.osLudzieAccept.clear();
        this.osSakwyAccept.clear();
        this.osNiesyAccept.clear();
        this.osRybakAccept.clear();
        this.osDrwalAccept.clear();
        this.osGornikAccept.clear();
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

    @Setter
    public void updatePlayerOsGornik(final UUID uuid, final int noweOsGornik) {
        this.osGornik.replace(uuid, noweOsGornik);
    }

    @Setter
    public void updatePlayerOsMobyAccepted(final UUID uuid, final String noweOsMobyAccepted) {
        this.osMobyAccept.replace(uuid, noweOsMobyAccepted);
    }

    @Setter
    public void updatePlayerOsLudzieAccepted(final UUID uuid, final String noweOsLudzieAccepted) {
        this.osLudzieAccept.replace(uuid, noweOsLudzieAccepted);
    }

    @Setter
    public void updatePlayerOsSakwyAccepted(final UUID uuid, final String noweOsSakwyAccepted) {
        this.osSakwyAccept.replace(uuid, noweOsSakwyAccepted);
    }

    @Setter
    public void updatePlayerOsNiesyAccepted(final UUID uuid, final String noweOsNiesyAccepted) {
        this.osNiesyAccept.replace(uuid, noweOsNiesyAccepted);
    }

    @Setter
    public void updatePlayerOsRybakAccepted(final UUID uuid, final String noweOsRybakAccepted) {
        this.osRybakAccept.replace(uuid, noweOsRybakAccepted);
    }

    @Setter
    public void updatePlayerOsDrwalAccepted(final UUID uuid, final String noweOsDrwalAccepted) {
        this.osDrwalAccept.replace(uuid, noweOsDrwalAccepted);
    }

    @Setter
    public void updatePlayerOsGornikAccepted(final UUID uuid, final String noweOsGornikAccepted) {
        this.osGornikAccept.replace(uuid, noweOsGornikAccepted);
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

    @Getter
    public int getPlayerOsGornik(final UUID uuid) {
        return this.osGornik.get(uuid);
    }

    @Getter
    public String getOsMobyAccept(final UUID uuid) {
        return this.osMobyAccept.get(uuid);
    }

    @Getter
    public String getOsLudzieAccept(final UUID uuid) {
        return this.osLudzieAccept.get(uuid);
    }

    @Getter
    public String getOsSakwyAccept(final UUID uuid) {
        return this.osSakwyAccept.get(uuid);
    }

    @Getter
    public String getOsNiesyAccept(final UUID uuid) {
        return this.osNiesyAccept.get(uuid);
    }

    @Getter
    public String getOsRybakAccept(final UUID uuid) {
        return this.osRybakAccept.get(uuid);
    }

    @Getter
    public String getOsDrwalAccept(final UUID uuid) {
        return this.osDrwalAccept.get(uuid);
    }

    @Getter
    public String getOsGornikAccept(final UUID uuid) {
        return this.osGornikAccept.get(uuid);
    }
}
