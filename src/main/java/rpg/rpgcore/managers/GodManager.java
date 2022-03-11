package rpg.rpgcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class GodManager {
    private final ArrayList<UUID> godList = new ArrayList<>();

    public ArrayList<UUID> getGodList() {return godList;}
    public boolean containsPlayer(final UUID uuid){return godList.contains(uuid);}

}
