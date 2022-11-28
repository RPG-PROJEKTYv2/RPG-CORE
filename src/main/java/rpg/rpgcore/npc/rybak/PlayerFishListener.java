package rpg.rpgcore.npc.rybak;

import net.minecraft.server.v1_8_R3.EntityFishingHook;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PlayerFishListener implements Listener {

    private final RPGCORE rpgcore;
    private final Map<UUID, Long> timeMap = new HashMap<>();

    public PlayerFishListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFish(final PlayerFishEvent e) {
        final Player player = e.getPlayer();
        e.setExpToDrop(0);
        if (e.getState() == PlayerFishEvent.State.FISHING) {
            this.runAnimation(e);
        }
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(player.getUniqueId()));
            rpgcore.getRybakNPC().removeTaskId(player.getUniqueId());
            this.timeMap.remove(player.getUniqueId());
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
                if (toCheckBefore == toCheckAfter) {
                    player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                }

                if (check == 1) {
                    if (toCheckAfter <= 20) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    player.sendMessage("dobrze - dol");
                    //addDrop(player);
                    return;
                }
                if (check == 2) {
                    if (toCheckAfter >= -20) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    player.sendMessage("dobrze - gora");
                    //addDrop(player);
                    return;
                }
                if (check == 3) {
                    if (toCheckAfter >= -35) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    player.sendMessage("dobrze - lewo");
                    //addDrop(player);
                    return;
                }
                if (check == 4) {
                    if (toCheckAfter <= 35) {
                        player.sendMessage(Utils.format("&8[&c✘&8] &cNiestety ryba zerwala sie z linki..."));
                        return;
                    }
                    player.sendMessage("dobrze - prawo");
                    //addDrop(player);
                    return;
                }
            }, 40L);
        }
    }

    private void runAnimation(final PlayerFishEvent e) {
        this.timeMap.put(e.getPlayer().getUniqueId(), System.currentTimeMillis() + 15000);
        this.setBiteTime(e.getHook(), 360);
        int a = rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {
            final String time = Utils.durationToString(this.timeMap.get(e.getPlayer().getUniqueId()), false);
            e.getPlayer().sendMessage(e.getState().name()); //TODO <- PRZETESTOWAC!
            if (e.getState() == PlayerFishEvent.State.FAILED_ATTEMPT || e.getState() == PlayerFishEvent.State.CAUGHT_FISH || e.getState() == PlayerFishEvent.State.IN_GROUND) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
                rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
                this.timeMap.remove(e.getPlayer().getUniqueId());
                return;
            }
            if (time.equals("<1s")) {
                e.getHook().setCustomName(Utils.format("&c" + e.getPlayer().getName() + " &7| &eplynie..."));
                return;
            }
            e.getHook().setCustomName(Utils.format("&c" + e.getPlayer().getName() + " &7| oczekuje na rybe... &c" + time));
        }, 1L, 20L);
        if (rpgcore.getRybakNPC().getTaskMap().containsKey(e.getPlayer().getUniqueId())) {
            rpgcore.getServer().getScheduler().cancelTask(rpgcore.getRybakNPC().getTaskId(e.getPlayer().getUniqueId()));
            rpgcore.getRybakNPC().removeTaskId(e.getPlayer().getUniqueId());
        }
        rpgcore.getRybakNPC().addTaskId(e.getPlayer().getUniqueId(), a);
    }

    private void setBiteTime(FishHook hook, int time) {
        net.minecraft.server.v1_8_R3.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();

        Field fishCatchTime = null;

        try {
            fishCatchTime = net.minecraft.server.v1_8_R3.EntityFishingHook.class.getDeclaredField("aw");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        fishCatchTime.setAccessible(true);

        try {
            fishCatchTime.setInt(hookCopy, time);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        fishCatchTime.setAccessible(false);
    }
}
