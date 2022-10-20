package rpg.rpgcore.dungeons.zamekNieskonczonosci;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.ZamekNieskonczonosciBoss.ZamekNieskonczonosciBoss;
import rpg.rpgcore.party.Party;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ZamekNieskonczonosciManager {
    private final RPGCORE rpgcore;
    public ZamekNieskonczonosciManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<Party, EntityInsentient> bossMap =  new HashMap<>();
    private final Location spawn = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 0, 5, 0);
    private final Location bossSpawnLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 5, 7, 0);
    private Player partyLeader;
    private Player player2;
    private Player player3;
    private Player player4;
    private boolean success = false;

    private List goalB = new ArrayList();
    private List goalC = new ArrayList();
    private List targetB = new ArrayList();
    private List targetC = new ArrayList();

    public void startDungeon(final Party party) {

        for (final UUID uuid : party.getMembers()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (uuid == party.getLeaderUUID()) {
                partyLeader = player;
            }
            if (player2 == null) {
                player2 = player;
            } else if (player3 == null) {
                player3 = player;
            } else if (player4 == null) {
                player4 = player;
            }
            player.teleport(spawn);
            rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lFaza &c&l1&f&l: &4&lParkour", 5, 20, 5));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWitaj &6" + player.getName() + " &fw moich skromnych progach."));
            if (uuid != party.getLeaderUUID()) {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + partyLeader.getName() + " &fpodbic moj zamek..."));
            } else {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + (party.getMembers().size() -1) + " &frownie slabymi wojownikami podbic moj zamek..."));
            }
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fdobrze, wrecz &eZNAKOMICIE&f!"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec &cZACZNIJMY PRZEDSTAWIENIE&f!"));
        }
        EntityInsentient entity = (EntityInsentient) EntityTypes.spawnEntity(new ZamekNieskonczonosciBoss(((org.bukkit.craftbukkit.v1_8_R3.CraftWorld) spawn.getWorld()).getHandle()), UUID.randomUUID(), bossSpawnLocation, "Dinnerbone");
        // Przetestowac: USTAWIC TAG Z CUSTOMNAME NA DINNERBONE A W RESPIE DAC NORMALNA NAZWE
        goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, entity.goalSelector);
        goalC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, entity.goalSelector);
        targetB = (List)Utils.getPrivateField("b", PathfinderGoalSelector.class, entity.targetSelector);
        targetC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, entity.targetSelector);
        bossMap.put(party, entity);
        for (DataWatcher.WatchableObject object : entity.getDataWatcher().c()) {
            System.out.println(object.b());
        }
        Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8postanowila wyruszyc na wyprawe!"));
    }

    public void endDungeon(final Party party) {
        for (final UUID uuid : party.getMembers()) {
            final Player player = Bukkit.getPlayer(uuid);
            player.teleport(RPGCORE.getInstance().getSpawnManager().getSpawn());
        }
        if (bossMap.get(party) != null) {
            bossMap.get(party).die();
            bossMap.remove(party);
        }
        goalB.clear();
        goalC.clear();
        targetB.clear();
        targetC.clear();
        if (success) {
            //sht
        } else {
            Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8nie zdolala podbic zamku!"));
        }
    }

}
