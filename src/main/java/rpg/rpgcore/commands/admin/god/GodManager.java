package rpg.rpgcore.commands.admin.god;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class GodManager {

    private final ArrayList<UUID> godList = new ArrayList<>();

    public GodManager(final RPGCORE rpgcore) {
    }

    public boolean containsPlayer(final UUID uuid) {
        return godList.contains(uuid);
    }

    public void addUserToGodList(final UUID uuid) {
        this.godList.add(uuid);
    }

    public void removeUserFromGodList(final UUID uuid) {
        this.godList.remove(uuid);
    }

    public void changeStatusGod(final Player p) {

        final UUID uuid = p.getUniqueId();

        if (!(this.containsPlayer(uuid))) {
            p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWlaczono&7 goda"));
            this.addUserToGodList(uuid);
            return;
        }

        p.sendMessage(Utils.format(Utils.SERVERNAME + "&cWylaczono&7 goda"));
        this.removeUserFromGodList(uuid);

    }

}
