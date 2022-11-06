package rpg.rpgcore.dungeons.zamekNieskonczonosci;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.party.Party;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ZamekNieskonczonosciManager {
    private final RPGCORE rpgcore;
    public ZamekNieskonczonosciManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<Party, EntityInsentient> bossMap =  new HashMap<>();
    public final Location spawn = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 19, -10.5);
    public final Location parkour = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 14, 6.5);
    public final Location bossSpawnLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 5, 7, 0);
    public final Location phase1StartLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 3.5, 12, 47.5);
    public Player partyLeader;
    public Player player2;
    public Player player3;
    public Player player4;
    public final List<Player> players = new ArrayList<>();
    public DungeonStatus status = DungeonStatus.ENDED;
    public boolean success = false;
    public int phase = 0;
    public int ready = 0;
    public Party party;
    public List<Integer> taskList = new ArrayList<>();

    private List goalB = new ArrayList();
    private List goalC = new ArrayList();
    private List targetB = new ArrayList();
    private List targetC = new ArrayList();

    public void preDungeon(final Party party) {
        this.party = party;
        partyLeader = Bukkit.getPlayer(party.getLeaderUUID());
        partyLeader.teleport(spawn);
        status = DungeonStatus.STARTED;
        partyLeader.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7Ahhhh tak. Kolejni &dsmialkowie &7ktorzy beda probowac odbic moj zamek."));
        partyLeader.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7niestety nie udalo sie to jeszcze nikomu od kad pamietam."));
        partyLeader.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7ale moze tym razem bedzie &cinaczej&7..."));
        players.add(partyLeader);
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
            player.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7Ahhhh tak. Kolejni &dsmialkowie &7ktorzy beda probowac odbic moj zamek."));
            player.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7niestety nie udalo sie to jeszcze nikomu od kiedy pamietam."));
            player.sendMessage(Utils.format("&5&lZaginiony Wladca &8» &7ale moze tym razem bedzie &cinaczej&7..."));
            players.add(player);
        }

    }

    public void openWladcaGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&5&lZaginiony Wladca"));

        for (int i = 0; i < gui.getSize(); i++) {
           gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 0).setName(" ").toItemStack());
        }

        gui.setItem(10, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName(Utils.format("&c" + players.get(0).getName())).setSkullOwner(players.get(0).getName()).toItemStack());
        gui.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(Utils.format("&cNie gotowy!")).toItemStack());


        gui.setItem(12, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName(Utils.format("&c" + players.get(1).getName())).setSkullOwner(players.get(1).getName()).toItemStack());
        gui.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(Utils.format("&cNie gotowy!")).toItemStack());

        /*gui.setItem(14, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName(Utils.format("&c" + players.get(2).getName())).setSkullOwner(players.get(2).getName()).toItemStack());
        gui.setItem(13, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(Utils.format("&cNie gotowy!")).toItemStack());

        gui.setItem(16, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setName(Utils.format("&c" + players.get(3).getName())).setSkullOwner(players.get(3).getName()).toItemStack());
        gui.setItem(15, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(Utils.format("&cNie gotowy!")).toItemStack());*/

        player.openInventory(gui);
    }

    public void startDungeon(final Party party) {
        status = DungeonStatus.ONGOING;
        this.liftUpGate(4, 15, 3);
        for (Player player : players) {
            rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l0&f&l: &4&lParkour", 5, 20, 5));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWitaj &6" + player.getName() + " &fw moich skromnych progach."));
            if (player.getUniqueId() != party.getLeaderUUID()) {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + partyLeader.getName() + " &fpodbic moj zamek..."));
            } else {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + (party.getMembers().size() -1) + " &frownie slabymi wojownikami podbic moj zamek..."));
            }
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fdobrze, wrecz &eZNAKOMICIE&f!"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec &cZACZNIJMY PRZEDSTAWIENIE&f!"));
            player.sendMessage(Utils.format("&8Waszym zadaniem jest ukonczyc parkour i dostac sie na druga strone. Wystarczy, ze ukoncza go &c2 &8osoby"));
        }
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
            int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l1&f&l: &4&lRdzenie", 5, 20, 5)), 20L).getTaskId();
            taskList.add(i);
        }
        int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.liftUpGate(4, 13, 50), 20L).getTaskId();
        taskList.add(i);
    }

    private void liftUpGate(int middleX, int middleY, int z) {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX  - 1, middleY, z).setTypeIdAndData(139, (byte) 1, false);
        world.getBlockAt(middleX + 1, middleY + 1, z).setType(Material.AIR);
        world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 1, false);
        world.getBlockAt(middleX - 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 0, false);
        int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX + 1, middleY, z).setType(Material.BARRIER);
            world.getBlockAt(middleX, middleY, z).setType(Material.BARRIER);
            world.getBlockAt(middleX  -1, middleY, z).setType(Material.BARRIER);
            world.getBlockAt(middleX + 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX - 1, middleY + 1, z).setTypeIdAndData(139, (byte) 1, false);
            world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 1, false);
            int j = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.AIR);
                world.getBlockAt(middleX, middleY - 1, z).setType(Material.AIR);
                world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.AIR);
                world.getBlockAt(middleX + 1, middleY, z).setType(Material.AIR);
                world.getBlockAt(middleX, middleY, z).setType(Material.AIR);
                world.getBlockAt(middleX - 1, middleY, z).setType(Material.AIR);
                world.getBlockAt(middleX + 1, middleY + 1, z).setType(Material.AIR);
                world.getBlockAt(middleX, middleY + 1, z).setType(Material.AIR);
                world.getBlockAt(middleX - 1, middleY + 1, z).setType(Material.AIR);
                world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 0, false);
                int k = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.closeGate(middleX, middleY, z), 5000L).getTaskId();
                this.taskList.add(k);
            }, 20L).getTaskId();
            this.taskList.add(j);
        }, 20L).getTaskId();
        this.taskList.add(i);
    }

    private void closeGate(int middleX, int middleY, int z) {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX -1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX - 1, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX -1 , middleY + 1, z).setTypeIdAndData(139, (byte) 1, false);
        world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 1, false);
        int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.BARRIER);
            world.getBlockAt(middleX + 1, middleY, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX, middleY, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX - 1, middleY, z).setTypeIdAndData(139, (byte) 1, false);
            world.getBlockAt(middleX + 1, middleY + 1, z).setType(Material.AIR);
            world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 1, false);
            world.getBlockAt(middleX -1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 0, false);
            int j = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.resetGate(middleX, middleY, z) ,20L).getTaskId();
            this.taskList.add(j);
        }, 20L).getTaskId();
        this.taskList.add(i);
    }

    private void resetGate(final int middleX, final int middleY, final int z) {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(middleX + 1, middleY - 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY - 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX - 1, middleY - 1, z).setTypeIdAndData(139, (byte) 1, false);
        world.getBlockAt(middleX - 1, middleY, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY, z).setTypeIdAndData(139, (byte) 1, false);
        world.getBlockAt(middleX + 1, middleY, z).setType(Material.AIR);
        world.getBlockAt(middleX + 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX - 1, middleY + 1, z).setType(Material.AIR);
        world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 1, false);
    }

    public void endDungeon(final Party party) {
        status = DungeonStatus.RESETTING;
        for (final UUID uuid : party.getMembers()) {
            final Player player = Bukkit.getPlayer(uuid);
            player.teleport(rpgcore.getSpawnManager().getSpawn());
        }
        if (bossMap.get(party) != null) {
            bossMap.get(party).die();
            bossMap.remove(party);
        }
        for (final Integer i : taskList) {
            if (rpgcore.getServer().getScheduler().isCurrentlyRunning(i) || rpgcore.getServer().getScheduler().isQueued(i)) {
                rpgcore.getServer().getScheduler().cancelTask(i);
            }
        }
        goalB.clear();
        goalC.clear();
        targetB.clear();
        targetC.clear();
        phase = 0;
        ready = 0;
        this.resetGate(4, 15, 3);
        this.resetGate(4, 13, 50);

        if (success) {
            //sht
        } else {
            Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8nie zdolala podbic zamku!"));
        }
        status = DungeonStatus.ENDED;
        players.clear();
    }

    public void endDungeonByPass(final List<Player> players) {
        status = DungeonStatus.RESETTING;
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
        status = DungeonStatus.ENDED;
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
