package rpg.rpgcore.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final Cache<UUID, Long> chatCooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> commandCooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> helpopCooldown = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> metinyCooldown = CacheBuilder.newBuilder().expireAfterWrite(500, TimeUnit.MILLISECONDS).build();
    private final Cache<UUID, Long> partyInviteCooldown = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> magazynyCooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> bifrostCooldown = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> rogCooldown = CacheBuilder.newBuilder().expireAfterWrite(45, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> serceCooldown = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> eliksirPotegiCooldown = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> eliksirObronncyCooldown = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> egzekutorCooldown = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();
    private final Cache<UUID, Long> pvpCooldown = CacheBuilder.newBuilder().expireAfterWrite(300, TimeUnit.MILLISECONDS).build();
    private final Cache<UUID, Long> pelerynkaCooldown = CacheBuilder.newBuilder().expireAfterWrite(300, TimeUnit.MILLISECONDS).build();
    private final Cache<UUID, Long> odlamkiCooldown = CacheBuilder.newBuilder().expireAfterWrite(300, TimeUnit.MILLISECONDS).build();
    private final Cache<UUID, Long> klejnoty120_130Cooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> serce70_80Cooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();

    public long getPlayerChatCooldown(final UUID uuid) {
        return this.chatCooldown.asMap().get(uuid);
    }

    public void givePlayerChatCooldown(final UUID uuid) {
        this.chatCooldown.put(uuid, System.currentTimeMillis() + 3000L);
    }

    public boolean hasChatCooldown(final UUID uuid) {
        return this.chatCooldown.asMap().containsKey(uuid);
    }

    public long getPlayerCommandCooldown(final UUID uuid) {
        return this.commandCooldown.asMap().get(uuid);
    }

    public void givePlayerCommandCooldown(final UUID uuid) {
        this.commandCooldown.put(uuid, System.currentTimeMillis() + 3000L);
    }

    public void givePlayerBiFrostCooldown(final UUID uuid) {
        this.bifrostCooldown.put(uuid, System.currentTimeMillis() + 60000L);
    }

    public void givePlayerRogCooldown(final UUID uuid) {
        this.rogCooldown.put(uuid, System.currentTimeMillis() + 45000L);
    }

    public void givePlayerSerceCooldown(final UUID uuid) {
        this.serceCooldown.put(uuid, System.currentTimeMillis() + 120000L);
    }

    public void givePlayerEgzekutorCooldown(final UUID uuid) {
        this.egzekutorCooldown.put(uuid, System.currentTimeMillis() + 600000L);
    }

    public void givePlayerEliksirPotegiCooldown(final UUID uuid) {
        this.eliksirPotegiCooldown.put(uuid, System.currentTimeMillis() + 600000L);
    }

    public void givePlayerEliksirObronncyCooldown(final UUID uuid) {
        this.eliksirObronncyCooldown.put(uuid, System.currentTimeMillis() + 600000L);
    }

    public boolean hasBiFrostCooldown(final UUID uuid) {
        return this.bifrostCooldown.asMap().containsKey(uuid);
    }

    public boolean hasRogCooldown(final UUID uuid) {
        return this.rogCooldown.asMap().containsKey(uuid);
    }

    public boolean hasSerceCooldown(final UUID uuid) {
        return this.serceCooldown.asMap().containsKey(uuid);
    }

    public boolean hasEgzekutorCooldown(final UUID uuid) {
        return this.egzekutorCooldown.asMap().containsKey(uuid);
    }

    public boolean hasEliksirPotegiCooldown(final UUID uuid) {
        return this.eliksirPotegiCooldown.asMap().containsKey(uuid);
    }

    public boolean hasEliksirObronncyCooldown(final UUID uuid) {
        return this.eliksirObronncyCooldown.asMap().containsKey(uuid);
    }

    public String getBiFrostCooldown(final UUID uuid) {
        return Utils.durationToString(this.bifrostCooldown.asMap().get(uuid), false);
    }

    public String getRogCooldown(final UUID uuid) {
        return Utils.durationToString(this.rogCooldown.asMap().get(uuid), false);
    }

    public String getSerceCooldown(final UUID uuid) {
        return Utils.durationToString(this.serceCooldown.asMap().get(uuid), false);
    }

    public String getEgzekutorCooldown(final UUID uuid) {
        return Utils.durationToString(this.egzekutorCooldown.asMap().get(uuid), false);
    }

    public String getEliksirPotegiCooldown(final UUID uuid) {
        return Utils.durationToString(this.eliksirPotegiCooldown.asMap().get(uuid), false);
    }

    public String getEliksirObronncyCooldown(final UUID uuid) {
        return Utils.durationToString(this.eliksirObronncyCooldown.asMap().get(uuid), false);
    }

    public boolean hasCommandCooldown(final UUID uuid) {
        return this.commandCooldown.asMap().containsKey(uuid);
    }

    public boolean hasHelpopCooldown(final UUID uuid) {
        return this.helpopCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerHelpopCooldown(final UUID uuid) {
        this.helpopCooldown.put(uuid, System.currentTimeMillis() + 30000L);
    }
    public long getPlayerHelpopCooldown(final UUID uuid) {
        return this.helpopCooldown.asMap().get(uuid);
    }


    public boolean hasMetinyCooldown(final UUID uuid) {
        return this.metinyCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerMetinyCooldown(final UUID uuid) {
        this.metinyCooldown.put(uuid, System.currentTimeMillis() + 10L);
    }

    public long getPlayerMetinyCooldown(final UUID uuid) {
        return this.metinyCooldown.asMap().get(uuid);
    }

    public boolean hasPartyInviteCooldown(final UUID uuid) {
        return this.partyInviteCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerPartyInviteCooldown(final UUID uuid) {
        this.partyInviteCooldown.put(uuid, System.currentTimeMillis() + 60000L);
    }

    public long getPlayerPartyInviteCooldown(final UUID uuid) {
        return this.partyInviteCooldown.asMap().get(uuid);
    }

    public boolean hasMagazynyCooldown(final UUID uuid) {
        return this.magazynyCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerMagazynyCooldown(final UUID uuid) {
        this.magazynyCooldown.put(uuid, System.currentTimeMillis() + 5000L);
    }

    public long getPlayerMagazynyCooldown(final UUID uuid) {
        return this.magazynyCooldown.asMap().get(uuid);
    }

    public void givePvpCooldown(final UUID uuid) {
        this.pvpCooldown.put(uuid, System.currentTimeMillis() + 300L);
    }

    public boolean hasPvpCooldown(final UUID uuid) {
        return this.pvpCooldown.asMap().containsKey(uuid);
    }
    public void givePelerynkaCooldown(final UUID uuid) {
        this.pelerynkaCooldown.put(uuid, System.currentTimeMillis() + 300L);
    }

    public boolean hasPelerynkaCooldown(final UUID uuid) {
        return this.pelerynkaCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerOdlamkiCooldown(final UUID uuid) {
        this.odlamkiCooldown.put(uuid, System.currentTimeMillis() + 300L);
    }

    public boolean hasOdlamkiCooldown(final UUID uuid) {
        return this.odlamkiCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerKlejnoty120_130Cooldown(final UUID uuid) {
        this.klejnoty120_130Cooldown.put(uuid, System.currentTimeMillis() + 3000L);
    }

    public boolean hasKlejnoty120_130Cooldown(final UUID uuid) {
        return this.klejnoty120_130Cooldown.asMap().containsKey(uuid);
    }

    public void givePlayerSerce70_80Cooldown(final UUID uuid) {
        this.serce70_80Cooldown.put(uuid, System.currentTimeMillis() + 3000L);
    }

    public boolean hasSerce70_80Cooldown(final UUID uuid) {
        return this.serce70_80Cooldown.asMap().containsKey(uuid);
    }
}
