package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.utils.Utils;

public class ReloadPetsTask implements Runnable{

    public ReloadPetsTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20L, 3600L);
    }

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            EntityTypes.reloadAllPetsOnWorld(world);
        }
        Utils.sendToHighStaff("&a&lWszystkie textury petow zostaly przeladowane!");
    }
}
