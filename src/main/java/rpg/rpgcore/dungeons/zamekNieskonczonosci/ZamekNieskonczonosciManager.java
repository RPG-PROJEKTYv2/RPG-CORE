package rpg.rpgcore.dungeons.zamekNieskonczonosci;


import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.dungeons.zamekNieskonczonosci.tasks.SideBarTask;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.KsiazeMroku.KsiazeMroku;
import rpg.rpgcore.metiny.MetinyHelper;
import rpg.rpgcore.party.Party;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class ZamekNieskonczonosciManager {
    private final RPGCORE rpgcore;

    public ZamekNieskonczonosciManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.deleteScoreboard();
        this.resetGate(4, 15, 3);
        this.resetGate(4, 13, 50);
        this.resetMiniBossesGate();
        for (Entity e : Bukkit.getWorld("zamekNieskonczonosci").getEntities()) {
            if (e instanceof ArmorStand) {
                e.remove();
                continue;
            }
            if (e instanceof EnderCrystal) {
                e.remove();
                continue;
            }
            if (e instanceof PigZombie) {
                e.remove();
                continue;
            }
            if (e instanceof Zombie) {
                e.remove();
            }
        }
    }

    public final Map<Party, EntityInsentient> bossMap = new HashMap<>();
    public final Location spawn = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 19, -10.5);
    public final Location parkour = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 14, 6.5);
    public final Location ksiazeMrokuSpawnLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 16, 114.5, 180L, 35L);
    public final Location phase1StartLocation = new Location(Bukkit.getWorld("zamekNieskonczonosci"), 3.5, 12, 47.5);
    public Player partyLeader;
    public Player player2;
    public Player player3;
    public Player player4;
    public final List<Player> players = new ArrayList<>();
    public DungeonStatus status = DungeonStatus.ENDED;
    public boolean success = false;
    public DungeonStatus phase = DungeonStatus.ETAP_0;
    public int ready = 0;
    public long time = 0;
    private int tick = 0;
    public int destroyed = 0;
    public int miniBossesKilled = 0;
    public Party party;
    public List<Integer> taskList = new ArrayList<>();
    public List<Integer> ksiazeTasks = new ArrayList<>();
    public final List<String> teamList = Arrays.asList(ChatColor.GREEN.toString(), ChatColor.BLUE.toString(), ChatColor.RED.toString(), ChatColor.YELLOW.toString());

    private List goalB = new ArrayList();
    private List goalC = new ArrayList();
    private List targetB = new ArrayList();
    private List targetC = new ArrayList();
    private Map<UUID, ScoreboardObjective> objMap = new HashMap<>();
    public Map<Integer, ArmorStand> rdzenMap = new HashMap<>();

    public Zombie mini1;
    public Zombie mini2;

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
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
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
        time = System.currentTimeMillis();
        status = DungeonStatus.ONGOING;
        phase = DungeonStatus.ETAP_0;
        this.newScoreboard();
        this.liftUpGate(4, 15, 3);
        for (Player player : players) {
            rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l0&f&l: &4&lParkour", 5, 20, 5));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWitaj &6" + player.getName() + " &fw moich skromnych progach."));
            if (player.getUniqueId() != party.getLeaderUUID()) {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + partyLeader.getName() + " &fpodbic moj zamek..."));
            } else {
                player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec postanowiles wraz z &6" + (party.getMembers().size() - 1) + " &frownie slabymi wojownikami podbic moj zamek..."));
            }
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fdobrze, wrecz &eZNAKOMICIE&f!"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fa wiec &cZACZNIJMY PRZEDSTAWIENIE&f!"));
            player.sendMessage(Utils.format("&8Waszym zadaniem jest ukonczyc parkour i dostac sie na druga strone. Wystarczy, ze ukoncza go &c2 &8osoby"));
        }
        Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8postanowila wyruszyc na wyprawe!"));
    }

    private void newScoreboard() {
        for (Player player : players) {
            createNewScreboardForPlayer(player);
        }
        new SideBarTask(rpgcore);
    }

    public void createNewScreboardForPlayer(final Player player) {
        final net.minecraft.server.v1_8_R3.Scoreboard scoreboard1 = new net.minecraft.server.v1_8_R3.Scoreboard();
        final ScoreboardObjective scoreboardObjective = scoreboard1.registerObjective("dungeon", IScoreboardCriteria.b);
        if (!objMap.containsKey(player.getUniqueId())) {
            objMap.put(player.getUniqueId(), scoreboardObjective);
        }

        scoreboardObjective.setDisplayName(Utils.format("&4&lZamek Nieskonczonosci"));


        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardObjective(scoreboardObjective, 1));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardObjective(scoreboardObjective, 0));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective));

        final ScoreboardTeam etap = scoreboard1.createTeam("etap");
        etap.getPlayerNameSet().add(ChatColor.ITALIC.toString());
        etap.setDisplayName(ChatColor.ITALIC.toString());
        etap.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
        etap.setPrefix(Utils.format("&7Etap: &c" + this.phase.getName() + " &7- "));
        etap.setSuffix(Utils.format(this.getEtapString()));

        final ScoreboardScore empty = new ScoreboardScore(scoreboard1, scoreboardObjective, " ");
        empty.setScore(14);
        final ScoreboardScore wojownicy = new ScoreboardScore(scoreboard1, scoreboardObjective, Utils.format("&7Wojownicy:"));
        wojownicy.setScore(7);


        int i = 0;
        for (Player p : players) {
            final User user = rpgcore.getUserManager().find(p.getUniqueId());
            final ScoreboardTeam team = scoreboard1.createTeam("dungeon_p" + i);
            team.getPlayerNameSet().add(teamList.get(i));
            team.setDisplayName(teamList.get(i));
            team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);

            if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
                if (user.getRankUser().getRankType().getPrefix().substring(user.getRankUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim().length() + p.getName().trim().length() + 1 >= 16) {
                    final String name = user.getRankUser().getRankType().getPrefix().substring(user.getRankUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim() + p.getName().trim() + " ";
                    final String prefix = name.substring(0, 16);
                    final String color = name.substring(0, 2);
                    final String suffix = color + name.substring(16);
                    team.setPrefix(Utils.format(prefix));
                    team.setSuffix(Utils.format(suffix + this.getSuffix(p.getHealth(), p.getMaxHealth()) + String.format("%.0f", p.getHealth()) + "&c♥"));
                } else {
                    team.setPrefix(Utils.format(user.getRankUser().getRankType().getPrefix().substring(user.getRankUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim() + p.getName().trim() + " "));
                    team.setSuffix(Utils.format(this.getSuffix(p.getHealth(), p.getMaxHealth()) + String.format("%.0f", p.getHealth()) + "&c♥"));
                }
            } else {
                if (user.getRankPlayerUser().getRankType().getPrefix().substring(user.getRankPlayerUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim().length() + p.getName().trim().length() + 1 >= 16) {
                    final String name = user.getRankPlayerUser().getRankType().getPrefix().substring(user.getRankPlayerUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim() + p.getName() + " ";
                    final String prefix = name.substring(0, 16);
                    final String color = name.substring(0, 2);
                    final String suffix = color + name.substring(16);
                    team.setPrefix(Utils.format(prefix));
                    team.setSuffix(Utils.format(suffix + this.getSuffix(p.getHealth(), p.getMaxHealth()) + String.format("%.0f", p.getHealth()) + "&c♥"));
                } else {
                    team.setPrefix(Utils.format(user.getRankPlayerUser().getRankType().getPrefix().substring(user.getRankPlayerUser().getRankType().getPrefix().indexOf(" ") + 1).replace(" ", "").trim() + p.getName().trim() + " "));
                    team.setSuffix(Utils.format(this.getSuffix(p.getHealth(), p.getMaxHealth()) + String.format("%.0f", p.getHealth()) + "&c♥"));
                }
            }
            final ScoreboardScore teamScore = new ScoreboardScore(scoreboard1, scoreboardObjective, team.getPrefix() + team.getDisplayName() + team.getSuffix());
            teamScore.setScore(6 - i);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(teamScore));
            i++;
        }


        final ScoreboardTeam time = scoreboard1.createTeam("time");
        time.getPlayerNameSet().add(ChatColor.BOLD.toString());
        time.setDisplayName(ChatColor.BOLD.toString());
        time.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
        time.setPrefix(Utils.format("&7Czas: "));
        time.setSuffix(Utils.format("&c" + Utils.durationToString(System.currentTimeMillis() - this.time, true)));


        final ScoreboardScore etapS = new ScoreboardScore(scoreboard1, scoreboardObjective, etap.getPrefix() + etap.getDisplayName() + etap.getSuffix());
        etapS.setScore(15);
        final ScoreboardScore empty2 = new ScoreboardScore(scoreboard1, scoreboardObjective, "  ");
        empty2.setScore(2);
        final ScoreboardScore empty5 = new ScoreboardScore(scoreboard1, scoreboardObjective, "  ");
        empty5.setScore(1);
        final ScoreboardScore timeS = new ScoreboardScore(scoreboard1, scoreboardObjective, time.getPrefix() + time.getDisplayName() + time.getSuffix());
        timeS.setScore(0);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(etapS));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(empty));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(wojownicy));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(empty2));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(empty5));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardScore(timeS));
    }

    private String getSuffix(final double hp, final double maxHp) {
        final double percent = hp / maxHp;
        if (percent > 0.9) {
            return "&a";
        } else if (percent > 0.75) {
            return "&6";
        } else if (percent > 0.5) {
            return "&e";
        } else if (percent > 0.25) {
            return "&c";
        } else {
            return "&4";
        }
    }

    public String getEtapString() {
        switch (this.phase) {
            case ETAP_0:
                return "&cParkour";
            case ETAP_1:
            case ETAP_1_RDZENIE:
            case ETAP_1_MINIBOSS:
                return "&cRdzenie";
            case ETAP_2:
                return "&cFilary";
            case ETAP_3:
                return "&4&lWladca Zamku";
            default:
                return "";
        }
    }

    private void deleteScoreboard() {
        for (Player player : players) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardObjective(objMap.get(player.getUniqueId()), 1));
        }
    }

    public void startPhase1(final Party party) {

        for (Player player : players) {
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fWidze, ze daliscie rade pokonac kilka &6prostych &fskokow"));
            player.sendMessage(Utils.format("&4&l&kBoss Nieskonczonosci&4&l: &fDobrze wiedziec, ze nie mam doczynienia z &aamatorami"));
            int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&4&lZamek Nieskonczonosci", 5, 20, 5),
                    rpgcore.getNmsManager().makeSubTitle("&f&lEtap &c&l1&f&l: &4&lRdzenie", 5, 20, 5)), 20L).getTaskId();
            taskList.add(i);
        }
        int i = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.liftUpGate(4, 13, 50), 20L).getTaskId();
        taskList.add(i);

        final EntityInsentient ksiazeMroku = (EntityInsentient) EntityTypes.spawnEntity(new KsiazeMroku(((org.bukkit.craftbukkit.v1_8_R3.CraftWorld) spawn.getWorld()).getHandle()), UUID.randomUUID(), ksiazeMrokuSpawnLocation, "&c&lKsiaze Mroku");
        goalB = (List) Utils.getPrivateField("b", PathfinderGoalSelector.class, ksiazeMroku.goalSelector);
        goalC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, ksiazeMroku.goalSelector);
        targetB = (List)Utils.getPrivateField("b", PathfinderGoalSelector.class, ksiazeMroku.targetSelector);
        targetC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, ksiazeMroku.targetSelector);
        ksiazeMroku.a(ksiazeMroku, 180F, 35F);
        bossMap.put(party, ksiazeMroku);
        ksiazeMroku.goalSelector.a(0, new PathfinderGoalFloat(ksiazeMroku));

        final PacketPlayOutEntity.PacketPlayOutEntityLook look = new PacketPlayOutEntity.PacketPlayOutEntityLook(ksiazeMroku.getId(), (byte) ((ksiazeMrokuSpawnLocation.getYaw() * 256.0F) / 360.0F), (byte) ((ksiazeMrokuSpawnLocation.getPitch() * 256.0F) / 360.0F), true);
        final PacketPlayOutEntityHeadRotation headRotation = new PacketPlayOutEntityHeadRotation(ksiazeMroku, (byte) ((ksiazeMrokuSpawnLocation.getYaw() * 256.0F) / 360.0F));
        int p1 = rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {
            for (Player p : players) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(headRotation);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(look);
            }
        }, 1L, 80L);
        taskList.add(p1);
        ksiazeTasks.add(p1);


        int k = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            for (Player p : players) {
                p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fWiec postanowilisci przybyc do zamku mojego &4Ojca&f..."));
            }
            int l = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, ()-> {
                for (Player p : players) {
                    p.sendMessage(Utils.format("&c&lKsiaze Mroku: &dGlupcy&f... hahahaha"));
                }
                int m = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, ()-> {
                    for (Player p : players) {
                        p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fMyslicie, ze dacie rade nas powstrzymac?"));
                    }
                    int n = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, ()-> {
                        for (Player p : players) {
                            p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fPrzez wieki bylismy w stanie &4powstrzymac &fkazdego, kto probowal stanac nam na drodze"));
                        }
                        int o = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, ()-> {
                            for (Player p : players) {
                                p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fDo &6Wieczmej Chwaly!"));
                            }
                            int r = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, ()-> {
                                for (Player p : players) {
                                    p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fI tym razem... &4&lNIE BEDZIE INACZEJ!"));
                                }
                            }, 40L).getTaskId();
                            taskList.add(r);
                        }, 40L).getTaskId();
                        taskList.add(o);
                    }, 40L).getTaskId();
                    taskList.add(n);
                }, 40L).getTaskId();
                taskList.add(m);
            }, 40L).getTaskId();
            taskList.add(l);
        }, 40L).getTaskId();
        taskList.add(k);


        int j = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            MetinyHelper.spawnMetinByPass(10000);
            MetinyHelper.spawnMetinByPass(10001);
            MetinyHelper.spawnMetinByPass(10002);
            MetinyHelper.spawnMetinByPass(10003);
            int a = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                for (Player p : players) {
                    p.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7Ahhh tak, pamietam to jedno z zabezpieczen tego zamku."));
                }
                int b = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    for (Player p : players) {
                        p.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7zeby przejsc dalej &akazdy &7z was musi zniszczyc jeden rdzen."));
                    }
                    int c = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        for (Player p : players) {
                            p.sendMessage(Utils.format("&e" + partyLeader.getName() + ": &fA skad mamy wiedziec, ktory jest kogo?"));
                        }
                        int d = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            for (Player p : players) {
                                p.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7Hmmm... Jesli jeszcze pamietam gdzie jest ten guzik to zaraz wam je wskaze."));
                            }
                            int e = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                for (Player p : players) {
                                    p.sendMessage(Utils.format("&e" + partyLeader.getName() + ": &fZaczynajmy!"));
                                }
                            }, 40L).getTaskId();
                            taskList.add(e);
                        }, 40L).getTaskId();
                        taskList.add(d);
                    }, 40L).getTaskId();
                    taskList.add(c);
                }, 40L).getTaskId();
                taskList.add(b);
            }, 40L).getTaskId();
            taskList.add(a);
            int o = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                for (Player p : players) {
                    p.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7Dobra mam to!"));
                    p.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7Juz wszystko powinno byc jasne!"));
                }
                final List<String> randomNames = getRandomPlayer();
                final ArmorStand as1 = (ArmorStand) Bukkit.getWorld("zamekNieskonczonosci").spawnEntity(new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 5, 108.5), EntityType.ARMOR_STAND);
                as1.setCustomName(Utils.format("&cRdzen: &a" + randomNames.get(0)));
                as1.setCustomNameVisible(true);
                as1.setGravity(false);
                as1.setVisible(false);
                rdzenMap.put(10000, as1);
                final ArmorStand as2 = (ArmorStand) Bukkit.getWorld("zamekNieskonczonosci").spawnEntity(new Location(Bukkit.getWorld("zamekNieskonczonosci"), 10.5, 5, 114.5), EntityType.ARMOR_STAND);
                as2.setCustomName(Utils.format("&cRdzen: &a" + randomNames.get(1)));
                as2.setCustomNameVisible(true);
                as2.setGravity(false);
                as2.setVisible(false);
                rdzenMap.put(10001, as2);
                /*final ArmorStand as3 = (ArmorStand) Bukkit.getWorld("zamekNieskonczonosci").spawnEntity(new Location(Bukkit.getWorld("zamekNieskonczonosci"), 4.5, 5, 120.5), EntityType.ARMOR_STAND);
                as3.setCustomName(Utils.format("&cRdzen: &a" + randomNames.get(2)));
                as3.setCustomNameVisible(true);
                as3.setGravity(false);
                as3.setVisible(false);
                rdzenMap.put(10002, as3);*/
                /*final ArmorStand as4 = (ArmorStand) Bukkit.getWorld("zamekNieskonczonosci").spawnEntity(new Location(Bukkit.getWorld("zamekNieskonczonosci"), -1.5, 5, 114.5), EntityType.ARMOR_STAND);
                as4.setCustomName(Utils.format("&cRdzen: &a" + randomNames.get(3)));
                as4.setCustomNameVisible(true);
                as4.setGravity(false);
                as4.setVisible(false);
                rdzenMap.put(10003, as4);*/
                this.phase = DungeonStatus.ETAP_1_RDZENIE;
            }, 500L).getTaskId();
            taskList.add(o);
        }, 300L).getTaskId();
        taskList.add(j);
    }

    public void spawnMiniBoses() {
        int a = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            for (Player p : players) {
                p.sendMessage(Utils.format("&c&lKsiaze Mroku: &fA wiec nie pozostaje mi nic innego jak was przeprosic..."));
            }
            int b = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                for (Player p : players) {
                    p.sendMessage(Utils.format("&c&lKsiaze Mroku: &4&lZARTOWALEM!"));
                }
                int c = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    for (Player p : players) {
                        p.sendMessage(Utils.format("&c&lKsiaze Mroku: &aSludzy&f, zabierzcie ich stad!"));
                    }

                }, 40L).getTaskId();
                taskList.add(c);
            }, 60L).getTaskId();
            taskList.add(b);
        }, 40L).getTaskId();
        taskList.add(a);

        int d = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            try {
                mini1 = (Zombie) MythicMobs.inst().getAPIHelper().spawnMythicMob("ZAMEK_NIESKONCZONOSCI-MINIBOSS", new Location(Bukkit.getWorld("zamekNieskonczonosci"), 36.5, 5, 114.5));
                mini2 = (Zombie) MythicMobs.inst().getAPIHelper().spawnMythicMob("ZAMEK_NIESKONCZONOSCI-MINIBOSS", new Location(Bukkit.getWorld("zamekNieskonczonosci"), -27.5, 5, 114.5));
            } catch (InvalidMobTypeException e) {
                e.printStackTrace();
            }
            if (mini1 == null && mini2 == null) {
                for (Player p : players) {
                    p.sendMessage(Utils.format(Utils.SERVERNAME + "&cCos sie popsulo! Udaj sie z ss tej wiadomosci do &4Administracji &c! &8(ID: &4ZNDUNGEON_ENITYTNOTFOUND&8)"));
                    endDungeonByPass(players);
                }
            }

            assert mini1 != null;
            assert mini2 != null;


            mini1.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- &f" + getRandomPlayerName(players) + " 5"));
            mini1.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addGlowing().toItemStack());
            mini1.getEquipment().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addGlowing().toItemStack());
            mini1.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addGlowing().toItemStack());
            mini1.getEquipment().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addGlowing().toItemStack());
            mini1.getEquipment().setItemInHand(new ItemBuilder(Material.IRON_SWORD).addGlowing().toItemStack());
            mini1.setCustomNameVisible(true);


            mini2.setCustomName(Utils.format("&cSluga Ksiecia Mroku &7- " + getRandomPlayerName(players) + " 5"));
            mini2.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addGlowing().toItemStack());
            mini2.getEquipment().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addGlowing().toItemStack());
            mini2.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addGlowing().toItemStack());
            mini2.getEquipment().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addGlowing().toItemStack());
            mini2.getEquipment().setItemInHand(new ItemBuilder(Material.IRON_SWORD).addGlowing().toItemStack());
            mini2.setCustomNameVisible(true);

            int e = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.liftGateMiniBosses(mini1, mini2), 20L).getTaskId();
            taskList.add(e);
            this.phase = DungeonStatus.ETAP_1_MINIBOSS;
        }, 150L).getTaskId();
        taskList.add(d);

    }

    private void liftGateMiniBosses(final Entity mini1, final Entity mini2) {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(18, 5, 113).setType(Material.BARRIER);
        world.getBlockAt(18, 5, 114).setType(Material.BARRIER);
        world.getBlockAt(18, 5, 115).setType(Material.BARRIER);

        world.getBlockAt(-10, 5, 113).setType(Material.BARRIER);
        world.getBlockAt(-10, 5, 114).setType(Material.BARRIER);
        world.getBlockAt(-10, 5, 115).setType(Material.BARRIER);
        int e = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            world.getBlockAt(18, 6, 113).setType(Material.BARRIER);
            world.getBlockAt(18, 6, 114).setType(Material.BARRIER);
            world.getBlockAt(18, 6, 115).setType(Material.BARRIER);

            world.getBlockAt(-10, 6, 113).setType(Material.BARRIER);
            world.getBlockAt(-10, 6, 114).setType(Material.BARRIER);
            world.getBlockAt(-10, 6, 115).setType(Material.BARRIER);

            int f = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                world.getBlockAt(18, 7, 113).setType(Material.BARRIER);
                world.getBlockAt(18, 7, 114).setType(Material.BARRIER);
                world.getBlockAt(18, 7, 115).setType(Material.BARRIER);

                world.getBlockAt(-10, 7, 113).setType(Material.BARRIER);
                world.getBlockAt(-10, 7, 114).setType(Material.BARRIER);
                world.getBlockAt(-10, 7, 115).setType(Material.BARRIER);

                int g = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    for (int i = 5; i < 8; i++) {
                        world.getBlockAt(18, i, 113).setType(Material.AIR);
                        world.getBlockAt(18, i, 114).setType(Material.AIR);
                        world.getBlockAt(18, i, 115).setType(Material.AIR);

                        world.getBlockAt(-10, i, 113).setType(Material.AIR);
                        world.getBlockAt(-10, i, 114).setType(Material.AIR);
                        world.getBlockAt(-10, i, 115).setType(Material.AIR);
                    }
                    mini1.setVelocity(new Vector(-6, 0.2, 0));
                    mini2.setVelocity(new Vector(6, 0.2, 0));
                }, 20L).getTaskId();
                taskList.add(g);
            }, 40L).getTaskId();
            taskList.add(f);
        }, 40L).getTaskId();
        taskList.add(e);
        int k = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, this::closeMiniBossesGate, 120L).getTaskId();
        taskList.add(k);
    }

    private void closeMiniBossesGate() {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(18, 7, 113).setType(Material.COBBLE_WALL);
        world.getBlockAt(18, 7, 114).setType(Material.COBBLE_WALL);
        world.getBlockAt(18, 7, 115).setType(Material.COBBLE_WALL);

        world.getBlockAt(-10, 7, 113).setType(Material.COBBLE_WALL);
        world.getBlockAt(-10, 7, 114).setType(Material.COBBLE_WALL);
        world.getBlockAt(-10, 7, 115).setType(Material.COBBLE_WALL);

        int a = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            world.getBlockAt(18, 6, 113).setType(Material.COBBLE_WALL);
            world.getBlockAt(18, 6, 114).setType(Material.COBBLE_WALL);
            world.getBlockAt(18, 6, 115).setType(Material.COBBLE_WALL);

            world.getBlockAt(-10, 6, 113).setType(Material.COBBLE_WALL);
            world.getBlockAt(-10, 6, 114).setType(Material.COBBLE_WALL);
            world.getBlockAt(-10, 6, 115).setType(Material.COBBLE_WALL);

            int b = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                world.getBlockAt(18, 5, 113).setType(Material.COBBLE_WALL);
                world.getBlockAt(18, 5, 114).setType(Material.COBBLE_WALL);
                world.getBlockAt(18, 5, 115).setType(Material.COBBLE_WALL);

                world.getBlockAt(-10, 5, 113).setType(Material.COBBLE_WALL);
                world.getBlockAt(-10, 5, 114).setType(Material.COBBLE_WALL);
                world.getBlockAt(-10, 5, 115).setType(Material.COBBLE_WALL);

            }, 20L).getTaskId();
            taskList.add(b);
        }, 20L).getTaskId();
        taskList.add(a);
    }

    private void resetMiniBossesGate() {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        for (int i = 5; i < 8; i++) {
            world.getBlockAt(18, i, 113).setType(Material.COBBLE_WALL);
            world.getBlockAt(18, i, 114).setType(Material.COBBLE_WALL);
            world.getBlockAt(18, i, 115).setType(Material.COBBLE_WALL);

            world.getBlockAt(-10, i, 113).setType(Material.COBBLE_WALL);
            world.getBlockAt(-10, i, 114).setType(Material.COBBLE_WALL);
            world.getBlockAt(-10, i, 115).setType(Material.COBBLE_WALL);
        }
    }

    public List<String> getRandomPlayer() {
        final List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        Collections.shuffle(playerNames);
        return playerNames;
    }

    public String getRandomPlayerName(final List<Player> playerNames) {
        final int random = new Random().nextInt(playerNames.size());
        return playerNames.get(random).getName();
    }

    public void startKsiazeBossFight() {
        this.phase = DungeonStatus.ETAP_1_BOSS;
        int a = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            for (Player player : players) {
                player.sendMessage(Utils.format("&c&lKsiaze Mroku: &fWidze, ze i oni nie dali wam rady..."));
            }
            int b = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                for (Player player : players) {
                    player.sendMessage(Utils.format("&c&lKsiaze Mroku: &fEhhh... No dobrze"));
                }
                int c = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    for (Player player : players) {
                        player.sendMessage(Utils.format("&c&lKsiaze Mroku: &fJak zwykle wszystko musze zalatwic &4&lSAM!"));
                    }
                    int d = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        final World world = Bukkit.getWorld("zamekNieskonczonosci");
                        for (int y = 0; y < 3; y++) {
                            for (int x = 0; x < 3; x++) {
                                world.getBlockAt(3 + x, 15 + y, 112).setType(Material.AIR);
                            }
                        }
                    }, 40L).getTaskId();
                    taskList.add(d);
                }, 40L).getTaskId();
                taskList.add(c);
            }, 40L).getTaskId();
            taskList.add(b);
        }, 40L).getTaskId();
        taskList.add(a);

        int e = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            for (Integer i : ksiazeTasks) {
                if (rpgcore.getServer().getScheduler().isCurrentlyRunning(i) || rpgcore.getServer().getScheduler().isQueued(i)) {
                    rpgcore.getServer().getScheduler().cancelTask(i);
                }
            }
            ksiazeTasks.clear();

            final EntityInsentient ksiaze = this.bossMap.get(party);
            ((List) Utils.getPrivateField("b", PathfinderGoalSelector.class, ksiaze.goalSelector)).clear();
            ((List) Utils.getPrivateField("c", PathfinderGoalSelector.class, ksiaze.goalSelector)).clear();

            final Entity ksiazeBukkit = ksiaze.getBukkitEntity();

            ksiazeBukkit.setVelocity(new Vector(0, 0.1, -2));

            assert ksiaze instanceof EntityPigZombie;
            //TODO zrobic entityTargetChangeEvent i ustawiac target na tego ktory ostatniu udzerzy
            EntityPigZombie ksiazePig = (EntityPigZombie) ksiaze;
            ksiaze.goalSelector.a(2, new PathfinderGoalMeleeAttack(ksiazePig, EntityHuman.class, 1.0, false));
            ksiaze.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(ksiazePig, 1.0));
            ksiaze.goalSelector.a(7, new PathfinderGoalRandomStroll(ksiazePig, 1.0));
            ksiaze.goalSelector.a(8, new PathfinderGoalLookAtPlayer(ksiaze, EntityHuman.class, 8.0F));
            ksiaze.goalSelector.a(8, new PathfinderGoalRandomLookaround(ksiaze));
            ksiaze.targetSelector.a(1, new PathfinderGoalHurtByTarget(ksiazePig, false));
            ksiaze.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(ksiazePig, EntityHuman.class, true));


            int f = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                final World world = Bukkit.getWorld("zamekNieskonczonosci");
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        world.getBlockAt(3 + x, 15 + y, 112).setType(Material.BARRIER);
                    }
                }
            }, 20L).getTaskId();
            taskList.add(f);
        }, 170L).getTaskId();
        taskList.add(e);

    }

    public Player getRandomPlayerp() {
        return players.get(new Random().nextInt(players.size()));
    }

    public void startPhase2() {

    }


    private void liftUpGate(int middleX, int middleY, int z) {
        final World world = Bukkit.getWorld("zamekNieskonczonosci");
        world.getBlockAt(middleX + 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX - 1, middleY, z).setTypeIdAndData(139, (byte) 1, false);
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
            world.getBlockAt(middleX - 1, middleY, z).setType(Material.BARRIER);
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
        world.getBlockAt(middleX - 1, middleY - 1, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX - 1, middleY, z).setType(Material.BARRIER);
        world.getBlockAt(middleX + 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
        world.getBlockAt(middleX - 1, middleY + 1, z).setTypeIdAndData(139, (byte) 1, false);
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
            world.getBlockAt(middleX - 1, middleY + 1, z).setTypeIdAndData(139, (byte) 0, false);
            world.getBlockAt(middleX, middleY + 2, z).setTypeIdAndData(139, (byte) 0, false);
            int j = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.resetGate(middleX, middleY, z), 20L).getTaskId();
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
            if (!player.isOnline()) continue;
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
        this.deleteScoreboard();
        goalB.clear();
        goalC.clear();
        targetB.clear();
        targetC.clear();
        status = DungeonStatus.ENDED;
        phase = DungeonStatus.ETAP_0;
        ready = 0;
        destroyed = 0;
        this.resetGate(4, 15, 3);
        this.resetGate(4, 13, 50);
        this.resetMiniBossesGate();

        for (Entity e : Bukkit.getWorld("zamekNieskonczonosci").getEntities()) {
            if (e instanceof ArmorStand) {
                e.remove();
                continue;
            }
            if (e instanceof EnderCrystal) {
                e.remove();
                continue;
            }
            if (e instanceof PigZombie) {
                e.remove();
                continue;
            }
            if (e instanceof Zombie) {
                e.remove();
            }
        }

        if (success) {
            //sht
        } else {
            Bukkit.broadcastMessage(Utils.format("&4&lZamek Nieskoncznosci &8Grupa gracza &c" + partyLeader.getName() + " &8nie zdolala podbic zamku!"));
        }
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
        phase = DungeonStatus.ETAP_0;
    }

    public void updateBar(Party party, String text, float healthPercent) {
        if (bossMap.containsKey(party)) {
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
