package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class KopalniaTask implements Runnable{
    private final RPGCORE rpgcore;
    public KopalniaTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20, 20);
    }

    @Override
    public void run() {
        Bukkit.getWorld("Kopalnia").getPlayers().forEach(player -> {
            final GornikUser user = rpgcore.getGornikNPC().find(player.getUniqueId());
            if (user != null) {
                if (user.getTimeLeft() == 0L) {
                    final User userMain = rpgcore.getUserManager().find(player.getUniqueId());
                    if (!userMain.getRankUser().isHighStaff() || (userMain.getRankUser().isHighStaff() && !userMain.isAdminCodeLogin())) {
                        player.teleport(rpgcore.getSpawnManager().getSpawn());
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Czas twojego biletu dobiegl wlasnie konca!"));
                    }
                } else {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(player, "&7Pozostaly Czas: &a" + Utils.durationToString(user.getTimeLeft(), true)));
                    user.setTimeLeft(user.getTimeLeft() - 1_000L);
                }
            }
        });
        rpgcore.getOreManager().getOres().forEach(ore -> {
            if (ore.getRespawnTime() <= System.currentTimeMillis() && ore.getRespawnTime() != -1L) {
                final Ores info = Ores.getRandomOre();
                ore.setType(info.getMaterial());
                ore.setExp(info.getExp());
                ore.setCurrentHp(info.getHp());
                ore.setMaxHp(info.getHp());
                ore.setRespawnTime(-1L);
                ore.getLocation().getBlock().setType(info.getMaterial());
            }
        });
    }
}
