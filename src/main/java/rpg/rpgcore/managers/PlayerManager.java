package rpg.rpgcore.managers;


//import jdk.jfr.BooleanFlag;


import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class PlayerManager {


    private final ArrayList<UUID> players = new ArrayList<>();
    private final ArrayList<String> playersNames = new ArrayList<>();

    private final HashMap<String, UUID> playerUUID = new HashMap<>();
    private final HashMap<UUID, String> playerName = new HashMap<>();
    private final HashMap<UUID, String> playerBanInfo = new HashMap<>();
    private final HashMap<UUID, String> playerMuteInfo = new HashMap<>();
    private final HashMap<UUID, String> playerPunishmentHistory = new HashMap<>();
    private final HashMap<UUID, Double> playerKasa = new HashMap<>();
    private final HashMap<UUID, Integer> playerLvl = new HashMap<>();
    private final HashMap<UUID, Double> playerExp = new HashMap<>();
    private final HashMap<UUID, Double> playerDamage = new HashMap<>();
    private final HashMap<UUID, Double> playerHP = new HashMap<>();
    private final HashMap<UUID, Double> playerSrednie = new HashMap<>();
    private final HashMap<UUID, Double> playerMinusSrednie = new HashMap<>();
    private final HashMap<UUID, Double> playerDef = new HashMap<>();
    private final HashMap<UUID, Double> playerMinusDef = new HashMap<>();
    private final HashMap<UUID, Double> playerKryt = new HashMap<>();
    private final HashMap<UUID, Double> playerBlok = new HashMap<>();
    private final HashMap<UUID, Double> playerPrzeszywka = new HashMap<>();
    private final HashMap<UUID, Double> playerSilnyNaLudzi = new HashMap<>();
    private final HashMap<UUID, Double> playerDefNaLudzi = new HashMap<>();
    private final HashMap<UUID, Double> playerSilnyNaMoby = new HashMap<>();
    private final HashMap<UUID, Double> playerDefNaMoby = new HashMap<>();

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

    public void createPlayer(final String playerName, final UUID playerUUID, final String banInfo, final String muteinfo, final String punishmentHistory, final int playerLvl, final double playerExp,
                             final double srednie, final double minusSrednie, final double def, final double minusDef, final double silnyNaLudzi, final double defNaLudzi, final double silnyNaMoby, final double defNaMoby,
                             final double damage, final double blok, final double przeszywka, final double kryt, final double hp, final double playerKasa) {
        this.players.add(playerUUID);
        this.playersNames.add(playerName);
        this.playerUUID.put(playerName, playerUUID);
        this.playerName.put(playerUUID, playerName);
        this.playerKasa.put(playerUUID, playerKasa);
        this.playerLvl.put(playerUUID, playerLvl);
        this.playerExp.put(playerUUID, playerExp);
        this.playerBanInfo.put(playerUUID, banInfo);
        this.playerMuteInfo.put(playerUUID, muteinfo);
        this.playerPunishmentHistory.put(playerUUID, punishmentHistory);
        this.playerSrednie.put(playerUUID, srednie);
        this.playerMinusSrednie.put(playerUUID, minusSrednie);
        this.playerDef.put(playerUUID, def);
        this.playerMinusDef.put(playerUUID, minusDef);
        this.playerSilnyNaLudzi.put(playerUUID, silnyNaLudzi);
        this.playerDefNaLudzi.put(playerUUID, defNaLudzi);
        this.playerSilnyNaMoby.put(playerUUID, silnyNaMoby);
        this.playerDefNaMoby.put(playerUUID, defNaMoby);
        this.playerDamage.put(playerUUID, damage);
        this.playerBlok.put(playerUUID, blok);
        this.playerPrzeszywka.put(playerUUID, przeszywka);
        this.playerKryt.put(playerUUID, kryt);
        this.playerHP.put(playerUUID, hp);

    }

    public void removeAllPlayers() {
        this.playerName.clear();
        this.playerUUID.clear();
        this.players.clear();
        this.playersNames.clear();
        this.playerKasa.clear();
        this.playerLvl.clear();
        this.playerExp.clear();
        this.playerBanInfo.clear();
        this.playerMuteInfo.clear();
        this.playerPunishmentHistory.clear();
    }

    public void updatePlayerBanInfo(final UUID uuid, final String banInfo) {
        this.playerBanInfo.replace(uuid, banInfo);
    }

    public void updatePlayerMuteInfo(final UUID uuid, final String banInfo) {
        this.playerMuteInfo.replace(uuid, banInfo);
    }

    public String getPlayerBanInfo(final UUID uuid) {
        return this.playerBanInfo.get(uuid);
    }

    public String getPlayerMuteInfo(final UUID uuid) {
        return this.playerMuteInfo.get(uuid);
    }

    public void updatePlayerPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        this.playerPunishmentHistory.replace(uuid, punishmentHistory);
    }

    public void updatePlayerLvl(final UUID uuid, final int lvl) {
        this.playerLvl.replace(uuid, lvl);
    }

    public void updatePlayerExp(final UUID uuid, final double exp) {
        this.playerExp.replace(uuid, exp);
    }

    public double getPlayerExp(final UUID uuid) {
        return this.playerExp.get(uuid);
    }

    public String getPlayerPunishmentHistory(final UUID uuid) {
        return this.playerPunishmentHistory.get(uuid);
    }

    public int getPlayerLvl(final UUID uuid) {
        return this.playerLvl.get(uuid);
    }

    public boolean isBanned(final UUID uuid) {
        return !(getPlayerBanInfo(uuid).equalsIgnoreCase("false"));
    }

    public boolean isMuted(final UUID uuid) {
        return !(getPlayerMuteInfo(uuid).equalsIgnoreCase("false"));
    }

    public void updatePlayerDamage(final UUID uuid, final double nowyDamage) {
        this.playerDamage.replace(uuid, nowyDamage);
    }

    public void updatePlayerHP(final UUID uuid, final double noweHP) {
        this.playerHP.replace(uuid, noweHP);
    }

    public void updatePlayerSrednie(final UUID uuid, final double noweSrednie) {
        this.playerSrednie.replace(uuid, noweSrednie);
    }

    public void updatePlayerMinusSrednie(final UUID uuid, final double noweMinusSrednie) {
        this.playerMinusSrednie.replace(uuid, noweMinusSrednie);
    }

    public void updatePlayerDef(final UUID uuid, final double nowyDef) {
        this.playerDef.replace(uuid, nowyDef);
    }

    public void updatePlayerMinusDef(final UUID uuid, final double nowyMinusDef) {
        this.playerMinusDef.replace(uuid, nowyMinusDef);
    }

    public void updatePlayerBlok(final UUID uuid, final double nowyBlok) {
        this.playerBlok.replace(uuid, nowyBlok);
    }

    public void updatePlayerKryt(final UUID uuid, final double nowyKryt) {
        this.playerKryt.replace(uuid, nowyKryt);
    }

    public void updatePlayerSilnyNaLudzi(final UUID uuid, final double nowySilnyNaLudzi) {
        this.playerSilnyNaLudzi.replace(uuid, nowySilnyNaLudzi);
    }

    public void updatePlayerDefNaLudzi(final UUID uuid, final double nowyDefNaLudzi) {
        this.playerDefNaLudzi.replace(uuid, nowyDefNaLudzi);
    }

    public void updatePlayerSilnyNaMoby(final UUID uuid, final double nowySilnyNaMoby) {
        this.playerSilnyNaMoby.replace(uuid, nowySilnyNaMoby);
    }

    public void updatePlayerDefNaMoby(final UUID uuid, final double nowyDefNaLudzi) {
        this.playerDefNaLudzi.replace(uuid, nowyDefNaLudzi);
    }

    public void updatePlayerPrzeszywka(final UUID uuid, final double nowaPrzeszywka) {
        this.playerPrzeszywka.replace(uuid, nowaPrzeszywka);
    }

    public Double getPlayerDamage(final UUID uuid) {
        return this.playerDamage.get(uuid);
    }

    public Double getPlayerSrednie(final UUID uuid) {
        return this.playerSrednie.get(uuid);
    }

    public Double getPlayerMinusSrednie(final UUID uuid) {
        return this.playerMinusSrednie.get(uuid);
    }

    public Double getPlayerDef(final UUID uuid) {
        return this.playerDef.get(uuid);
    }

    public Double getPlayerMinusDef(final UUID uuid) {
        return this.playerMinusDef.get(uuid);
    }

    public Double getPlayerHP(final UUID uuid) {
        return this.playerHP.get(uuid);
    }

    public Double getPlayerBlok(final UUID uuid) {
        return this.playerBlok.get(uuid);
    }

    public Double getPlayerKryt(final UUID uuid) {
        return this.playerKryt.get(uuid);
    }

    public Double getPlayerPrzeszywka(final UUID uuid) {
        return this.playerPrzeszywka.get(uuid);
    }

    public Double getPlayerSilnyNaLudzi(final UUID uuid) {
        return this.playerSilnyNaLudzi.get(uuid);
    }

    public Double getPlayerDefNaLudzi(final UUID uuid) {
        return this.playerDefNaLudzi.get(uuid);
    }

    public Double getPlayerSilnyNaMoby(final UUID uuid) {
        return this.playerSilnyNaMoby.get(uuid);
    }

    public Double getPlayerDefNaMoby(final UUID uuid) {
        return this.playerDefNaMoby.get(uuid);
    }


    public String getPlayerGroup(final Player player) {
        return PlaceholderAPI.setPlaceholders(player, "%uperms_rank%");
    }

    public HashMap<UUID, Integer> getPlayerLvl() {
        return playerLvl;
    }

    public HashMap<UUID, Double> getPlayerExp() {
        return playerExp;
    }

    public double getPlayerKasa(final UUID uuid) {
        return this.playerKasa.get(uuid);
    }

    public void updatePlayerKasa(final UUID uuid, final double kasa) {
        this.playerKasa.replace(uuid, kasa);
    }

    public void putPlayerKasa(final double kasa, final UUID uuid) {
        this.playerKasa.put(uuid, kasa);
    }

    public boolean isPlayerInKasaMap(final UUID uuid) {
        return this.playerKasa.containsKey(uuid);
    }

    public boolean isPlayerStaff(final Player player) {
        final String playerGroup = this.getPlayerGroup(player);
        return playerGroup.equals("H@") || playerGroup.equals("Admin") || playerGroup.equals("GM") || playerGroup.equals("Mod")
                || playerGroup.equals("KidMod") || playerGroup.equals("Helper") || playerGroup.equals("JuniorHelper");
    }

    public boolean isPlayerHighStaff(final Player player) {
        final String playerGroup = this.getPlayerGroup(player);
        return playerGroup.equals("H@") || playerGroup.equals("Admin") || playerGroup.equals("GM");
    }

}
