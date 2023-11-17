package rpg.rpgcore.events.headHuntEvent;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.events.headHuntEvent.objects.HeadHuntUser;
import rpg.rpgcore.utils.ChanceHelper;

import java.util.*;

public class HeadHuntManager {

    private final Map<UUID, HeadHuntUser> userMap;

    private final List<Location> glowka1Locations = Arrays.asList(
            new Location(Bukkit.getWorld("1-10map"), -217, 65, 380),
            new Location(Bukkit.getWorld("1-10map"), -168, 65, 265),
            new Location(Bukkit.getWorld("1-10map"), -217, 65, 380),
            new Location(Bukkit.getWorld("1-10map"), -217, 65, 380),
            new Location(Bukkit.getWorld("1-10map"), -217, 65, 380)
    );
    //...


    public HeadHuntManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllHeadHuntEvent();
        //rpgcore.getServer().getPluginManager().registerEvents(new HeadHuntEventInventoryClickListener(), rpgcore);
    }


    public Location getRandom(final UUID uuid, final int glowka) {
        final HeadHuntUser user = this.find(uuid);
        final List<Location> glokwaPlayer = new ArrayList<>();

        switch (glowka) {
            case 1:
                for (final Location location : glowka1Locations) {
                    if (!user.getGlowka1PrevLocations().contains(location)) {
                        glokwaPlayer.add(location);
                    }
                }
                break;
                /*
                case 2:
                for (final Location location : glowka2Locations) {
                    if (!user.getGlowka2PrevLocations().contains(location)) {
                        glokwaPlayer.add(location);
                    }
                }
                break;
                 */
            //...
        }


        return glokwaPlayer.get(ChanceHelper.getRandInt(0, glokwaPlayer.size() - 1));
    }



    public void add(final HeadHuntUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public HeadHuntUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void remove(final UUID uuid) {
        this.userMap.remove(uuid);
    }

    public ImmutableSet<HeadHuntUser> getHeadHuntUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }




}
