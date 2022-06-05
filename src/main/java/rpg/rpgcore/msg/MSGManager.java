package rpg.rpgcore.msg;


import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class MSGManager {

    private final HashMap<UUID, UUID> messageMap = new HashMap<>();

    public void sendMessages(final Player sender, final Player target, final String message) {

        sender.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]:&3" + message));
        target.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]:&3" + message));
    }


    @Getter
    public HashMap<UUID, UUID> getMessageMap() {
        return messageMap;
    }

    @Getter
    public UUID getTargetUUID(final UUID sender) {
        return this.messageMap.get(sender);
    }

    @Setter
    public void putInMessageMap(final UUID sender, final UUID target) {
        this.messageMap.put(sender, target);
    }

    @Setter
    public void updateMessageMap(final UUID sender, final UUID target) {
        this.messageMap.replace(sender, target);
    }

    public boolean isInMessageMapAsKey(final UUID uuid) {
        return this.messageMap.containsKey(uuid);
    }

    public boolean isInMessageMapAsValue(final UUID uuid) {
        return this.messageMap.containsValue(uuid);
    }

}
