package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.helpers.RybakHelper;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;

    private final List<Chunk> allowedChunksList = Arrays.asList(
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-5, -22),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-5, -23),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-6, -22),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-6, -23),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-7, -22),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-7, -23),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-8, -22),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-8, -23),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-9, -22),
            Bukkit.getWorld("spawnOFFICIAL").getChunkAt(-9, -23)
    );

    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        e.setExpToDrop(0);

        if (!allowedChunksList.contains(player.getLocation().getChunk())) {
            e.setCancelled(true);
            e.getHook().remove();
            player.sendMessage(Utils.format("&6&lRybak &8>> &cNie mozesz tutaj lowic!"));
            return;
        }

        if (rpgcore.getRybakNPC().getFishingCount(uuid) == 10) {
            e.setCancelled(true);
            e.getHook().remove();
            rpgcore.getRybakNPC().getAntyAfk(player);
            return;
        }

        if (!Utils.getTagString(player.getItemInHand(), "owner").equals(player.getName())) {
            e.setCancelled(true);
            e.getHook().remove();
            player.sendMessage(Utils.format("&8[&c✘&8] &cTa wedka nie nalezy do ciebie!"));
            return;
        }
        
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            e.getCaught().remove();
            final int check = new Random().nextInt(4) + 1;
            switch (check) {
                case 1:
                    rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lDOL", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6dol&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                    break;
                case 2:
                    rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lGORA", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6gore&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                    break;
                case 3:
                    rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lLEWO", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6lewo&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                    break;
                case 4:
                    rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lPRAWO", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6prawo&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                    break;
            }
            final float toCheckBefore = (check == 1 || check == 2 ? player.getLocation().getPitch() : player.getLocation().getYaw());
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                final float toCheckAfter = (check == 1 || check == 2 ? player.getLocation().getPitch() : player.getLocation().getYaw());
                rpgcore.getRybakNPC().addFishingCount(player.getUniqueId());
                player.sendMessage(Utils.format("&6&lRybak &8>> &7Do ochrony &cAnty-AFK &7pozostalo &6" + (10 - rpgcore.getRybakNPC().getFishingCount(uuid) + " &7polowow.")));
                if (toCheckBefore == toCheckAfter) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                    return;
                }
                if (check == 1) {
                    if (toCheckAfter <= 20) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    RybakHelper.getDrop(player);
                    return;
                }
                if (check == 2) {
                    if (toCheckAfter >= -20) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    RybakHelper.getDrop(player);
                    return;
                }
                if (check == 3) {
                    if (toCheckAfter >= -35) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    RybakHelper.getDrop(player);
                    return;
                }
                if (check == 4) {
                    if (toCheckAfter <= 35) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    RybakHelper.getDrop(player);
                }
            }, 40L);
        }
    }
}

/*if (e.getState() == PlayerFishEvent.State.FISHING) {
        Entity entity = player.getWorld().spawn(e.getHook().getLocation(), Fish.class);
        entity.setVelocity(getVector(player.getLocation(), entity));
        Bukkit.broadcastMessage("1");
        final double szczescie = rpgcore.getRybakNPC().find(e.getPlayer().getUniqueId()).getRybakUser().getMorskieSzczescie() + Utils.getTagDouble(e.getPlayer().getItemInHand(), "szczescie");
        this.timeMap.put(e.getPlayer().getUniqueId(), (long) (System.currentTimeMillis() + (15000 - ((15000 * szczescie) / 100))));
        this.setBiteTime(e.getHook(), Math.round(300 - ((300 * (float) szczescie) / 100)) + 60);
        int a = rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {
            if (e.getPlayer().getItemInHand().getType() != Material.FISHING_ROD || !Utils.getTagString(e.getPlayer().getItemInHand(), "owner").equals(e.getPlayer().getName())) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
                rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
                this.timeMap.remove(e.getPlayer().getUniqueId());
                return;
            }
            if (System.currentTimeMillis() > this.timeMap.get(e.getPlayer().getUniqueId()) + 3000) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
                rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
                this.timeMap.remove(e.getPlayer().getUniqueId());
                return;
            }
            final String time = Utils.durationToString(this.timeMap.get(e.getPlayer().getUniqueId()), false);
            e.getPlayer().sendMessage(e.getState().name()); //TODO <- PRZETESTOWAC!
            if (e.getState() == PlayerFishEvent.State.FAILED_ATTEMPT || e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
                rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
                this.timeMap.remove(e.getPlayer().getUniqueId());
                return;
            }
            if (time.contains("ms") || time.equals("<1s")) {
                e.getHook().setCustomName(Utils.format("&c" + e.getPlayer().getName() + " &7| &eplynie..."));
                return;
            }

            e.getHook().setCustomNameVisible(true);
            e.getHook().setCustomName(Utils.format("&c" + e.getPlayer().getName() + " &7| oczekuje na rybe... &c" + time));
        }, 1L, 20L);
        if (rpgcore.getRybakNPC().getTaskMap().containsKey(e.getPlayer().getUniqueId())) {
            rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
            rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
        }
        rpgcore.getRybakNPC().addTaskId(e.getPlayer().getUniqueId(), a);
    }
    private void setBiteTime(FishHook hook, int time) {
        EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();

        Field fishCatchTime = null;

        try {
            fishCatchTime = EntityFishingHook.class.getDeclaredField("aw");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        fishCatchTime.setAccessible(true);

        try {
            fishCatchTime.setInt(hookCopy, time);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Vector getVector(Location owner, Entity entity) {
        double d0 = owner.getX() - entity.getLocation().getX();
        double d1 = owner.getY() - entity.getLocation().getY();
        double d2 = owner.getZ() - entity.getLocation().getZ();
        return new Vector(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
    }*/
