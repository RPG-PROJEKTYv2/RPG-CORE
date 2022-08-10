package rpg.rpgcore.msg;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class MSGManager {

    private final HashMap<UUID, UUID> messageMap = new HashMap<>();

    public void sendMessages(final Player sender, final Player target, final String message) {

        sender.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]:&3" + message));
        target.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]:&3" + message));
    }


    public HashMap<UUID, UUID> getMessageMap() {
        return messageMap;
    }

    public UUID getTargetUUID(final UUID sender) {
        return this.messageMap.get(sender);
    }

    public void putInMessageMap(final UUID sender, final UUID target) {
        this.messageMap.put(sender, target);
    }

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
