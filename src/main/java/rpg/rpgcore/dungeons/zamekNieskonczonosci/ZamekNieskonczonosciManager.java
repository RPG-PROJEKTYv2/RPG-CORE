package rpg.rpgcore.dungeons.zamekNieskonczonosci;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
    public final List<Party> animationlist = new ArrayList<>();
    public final Location spawn = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 3.5, 14, 6.5);
    public final Location bossSpawnLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 5, 7, 0);
    public final Location phase1StartLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 3.5, 12, 47.5, 0L, -25L);
    public Player partyLeader;
    public Player player2;
    public Player player3;
    public Player player4;
    private final List<Player> players = new ArrayList<>();
    public boolean success = false;
    public int phase = 0;

    private List goalB = new ArrayList();
    private List goalC = new ArrayList();
    private List targetB = new ArrayList();
    private List targetC = new ArrayList();

    public void startDungeon(final Party party) {
        partyLeader = Bukkit.getPlayer(party.getLeaderUUID());
        for (final UUID uuid : party.getMembers()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (uuid == party.getLeaderUUID()) {
                continue;
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
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l0&f&l: &4&lParkour", 5, 20, 5));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWitaj &6" + player.getName() + " &fw moich skromnych progach."));
            if (uuid != party.getLeaderUUID()) {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + partyLeader.getName() + " &fpodbic moj zamek..."));
            } else {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + (party.getMembers().size() -1) + " &frownie slabymi wojownikami podbic moj zamek..."));
            }
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fdobrze, wrecz &eZNAKOMICIE&f!"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec &cZACZNIJMY PRZEDSTAWIENIE&f!"));
            player.sendMessage(Utils.format("&8Waszym zadaniem jest ukonczyc parkour i dostac sie na druga strone. Wystarczy, ze ukoncza go &c2 &8osoby"));
        }
        players.add(partyLeader);
        players.add(player2);
        /*players.add(player3);
        players.add(player4);*/
        Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8postanowila wyruszyc na wyprawe!"));
    }

    public void startPhase1(final Party party) {
        /*EntityInsentient entity = (EntityInsentient) EntityTypes.spawnEntity(new ZamekNieskonczonosciBoss(((org.bukkit.craftbukkit.v1_8_R3.CraftWorld) spawn.getWorld()).getHandle()), UUID.randomUUID(), bossSpawnLocation, "&c&l&kDinnerbone");
        // Przetestowac: USTAWIC TAG Z CUSTOMNAME NA DINNERBONE A W RESPIE DAC NORMALNA NAZWE
        goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, entity.goalSelector);
        goalC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, entity.goalSelector);
        targetB = (List)Utils.getPrivateField("b", PathfinderGoalSelector.class, entity.targetSelector);
        targetC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, entity.targetSelector);
        bossMap.put(party, entity);*/
        for (Player player : players) {
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWidze, ze daliscie rade pokonac kilka &6prostych &fskokow"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fDobrze wiedziec, ze nie mam doczynienia z &aamatorami"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l1&f&l: &4&lRdzenie", 5, 20, 5)), 20L);
        }
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> animationlist.remove(party), 100L);
    }

    public void endDungeon(final Party party) {
        for (final UUID uuid : party.getMembers()) {
            final Player player = Bukkit.getPlayer(uuid);
            player.teleport(rpgcore.getSpawnManager().getSpawn());
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

    public void endDungeonByPass(final List<Player> players) {
        Party party = null;
        Party party2 = null;
        for (Player player : players) {
            if (party == null && rpgcore.getPartyManager().find(player.getUniqueId()) != null) {
                party = rpgcore.getPartyManager().find(player.getUniqueId());
            } else if (party != null) {
                if (rpgcore.getPartyManager().find(player.getUniqueId()) != null) {
                    party2 = rpgcore.getPartyManager().find(player.getUniqueId());
                }

                if (party != party2) {
                    if (bossMap.get(party) != null) {
                        bossMap.get(party).die();
                        bossMap.remove(party);
                    }
                    if (bossMap.get(party2) != null) {
                        bossMap.get(party2).die();
                        bossMap.remove(party2);
                    }
                }
            }
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cDungeon zostal zakonczony poniewaz jeden z graczy opuscil party!"));
        }
        Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8nie zdolala podbic zamku!"));
    }

    public void updateBar(Party party, String text, float healthPercent) {
        if(bossMap.containsKey(party)) {
            DataWatcher watcher = new DataWatcher(null);
            watcher.a(0, (byte) 0x20);
            if (healthPercent != -1) watcher.a(6, (healthPercent * 200) / 100);
            if (text != null) {
                watcher.a(10, text);
                watcher.a(2, text);
            }
            watcher.a(11, (byte) 1);
            watcher.a(3, (byte) 1);

            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(bossMap.get(party).getId(), watcher, true);
            ((CraftPlayer) partyLeader).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) player2).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) player3).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) player4).getHandle().playerConnection.sendPacket(packet);
        }
    }

}
