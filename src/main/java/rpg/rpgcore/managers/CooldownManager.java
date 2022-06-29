package rpg.rpgcore.managers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final Cache<UUID, Long> chatCooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> commandCooldown = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
    private final Cache<UUID, Long> helpopCooldown = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();

    public long getPlayerChatCooldown(final UUID uuid) {
        return this.chatCooldown.asMap().get(uuid);
    }

    public void givePlayerChatCooldown(final UUID uuid) {
        this.chatCooldown.put(uuid, System.currentTimeMillis() + 3000);
    }

    public boolean hasChatCooldown(final UUID uuid) {
        return this.chatCooldown.asMap().containsKey(uuid);
    }

    public long getPlayerCommandCooldown(final UUID uuid) {
        return this.commandCooldown.asMap().get(uuid);
    }

    public void givePlayerCommandCooldown(final UUID uuid) {
        this.commandCooldown.put(uuid, System.currentTimeMillis() + 3000);
    }

    public boolean hasCommandCooldown(final UUID uuid) {
        return this.commandCooldown.asMap().containsKey(uuid);
    }

    public boolean hasHelpopCooldown(final UUID uuid) {
        return this.helpopCooldown.asMap().containsKey(uuid);
    }

    public void givePlayerHelpopCooldown(final UUID uuid) {
        this.helpopCooldown.put(uuid, System.currentTimeMillis() + 30000);
    }

    public long getPlayerHelpopCooldown(final UUID uuid) {
        return this.helpopCooldown.asMap().get(uuid);
    }
}
