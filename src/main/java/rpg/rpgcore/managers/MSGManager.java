package rpg.rpgcore.managers;


import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class MSGManager {

    private final HashMap<UUID, UUID> messageMap = new HashMap<>();

    public void sendMessages(final Player sender, final Player target, final String message) {

        sender.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]: &3" + message));
        target.sendMessage(Utils.format("&8[&3" + sender.getName() + " &8-> &3" + target.getName() + "&8]: &3" + message));
    }


    @Getter
    public HashMap<UUID, UUID> getMessageMap() {
        return messageMap;
    }
}
