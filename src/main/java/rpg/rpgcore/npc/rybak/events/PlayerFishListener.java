package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.BlockIterator;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.helpers.RybakHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;


    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        e.setExpToDrop(0);

        if (!player.getLocation().getWorld().getName().equals("Rybak")) {
            e.setCancelled(true);
            e.getHook().remove();
            player.sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz tutaj lowic!"));
            return;
        }
        if (!isLookingAtWater(player)) {
            e.setCancelled(true);
            e.getHook().remove();
            player.sendMessage(Utils.format("&8[&c✘&8] &cMozesz lowic tylko patrzac na wode!"));
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
            //rpgcore.getRybakNPC().spawnNurekGlebinowy(player, e.getHook().getLocation());
            this.checkPlayer(player, e);
        }
    }

    public static boolean isLookingAtWater(Player player) {
        BlockIterator iter = new BlockIterator(player, 15);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock.getType() == Material.WATER || lastBlock.getType() == Material.STATIONARY_WATER;
    }



    private void checkPlayer(final Player player, final PlayerFishEvent e) {
        final int check = ChanceHelper.getRandInt(1, 2); //new Random().nextInt(4) + 1;
        switch (check) {
            case 1:
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lDOL", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6dol&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                break;
            case 2:
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lGORA", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6gore&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                break;
            /*case 3:
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lLEWO", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6lewo&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                break;
            case 4:
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lPRAWO", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&7Spojrz w &6prawo&7, zeby &apomyslnie &7wyciagnac rybe", 5, 20, 5));
                break;*/
        }
        final float toCheckBefore = player.getLocation().getPitch(); //(check == 1 || check == 2 ? player.getLocation().getPitch() : player.getLocation().getYaw());
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            final float toCheckAfter = player.getLocation().getPitch(); //(check == 1 || check == 2 ? player.getLocation().getPitch() : player.getLocation().getYaw());
            final float different = player.getLocation().getYaw(); //(check == 1 || check == 2 ? player.getLocation().getYaw() : player.getLocation().getPitch());
            if (rpgcore.getRybakNPC().isSameLocation(player.getUniqueId(), String.valueOf(check), toCheckAfter, different)) {
                if (rpgcore.getRybakNPC().getAntiAfk(player.getUniqueId()) == 3) {
                    player.teleport(new Location(Bukkit.getWorld("Rybak"), 10.5, 147, -93.5, 135F, 31F));

                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        if (!player.getLocation().equals(new Location(Bukkit.getWorld("Rybak"), 10.5, 147, -93.5, 135F, 31F))) return;
                        if (e.getHook() == null) return;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " 6 h Lowienie Na Afk (skrypt?)");
                    }, 100L);

                }
                rpgcore.getRybakNPC().addAntiAfk(player.getUniqueId());
                player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                return;
            } else rpgcore.getRybakNPC().resetAntiAfk(player.getUniqueId());
            if (player.getLocation().getY() < 150) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                return;
            }
            rpgcore.getRybakNPC().addLocation(player.getUniqueId(), String.valueOf(check), toCheckAfter, different);
            rpgcore.getRybakNPC().addFishingCount(player.getUniqueId());
            player.sendMessage(Utils.format("&6&lRybak &8>> &7Do ochrony &cAnty-AFK &7pozostalo &6" + (10 - rpgcore.getRybakNPC().getFishingCount(player.getUniqueId()) + " &7polowow.")));
            if (toCheckBefore == toCheckAfter) {
                player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                return;
            }
            if (check == 1) {
                if (toCheckAfter < 60) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                    return;
                }
                RybakHelper.getDrop(player);
                return;
            }
            if (check == 2) {
                if (toCheckAfter > -30) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                    return;
                }
                RybakHelper.getDrop(player);
            }
            /*if (check == 3) {
                if (toCheckAfter <= -35 && !(different >= -50 && different <= 50)) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                    return;
                }
                RybakHelper.getDrop(player);
                return;
            }
            if (check == 4) {
                if (toCheckAfter >= 35 && !(different >= -50 && different <= 50)) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                    return;
                }
                RybakHelper.getDrop(player);
            }*/
        }, 40L);
    }


//    private org.bukkit.util.Vector getVector(Location owner, Entity entity) {
//        double d0 = owner.getX() - entity.getLocation().getX();
//        double d1 = owner.getY() - entity.getLocation().getY();
//        double d2 = owner.getZ() - entity.getLocation().getZ();
//        return new org.bukkit.util.Vector(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
//    }


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
    }*/
