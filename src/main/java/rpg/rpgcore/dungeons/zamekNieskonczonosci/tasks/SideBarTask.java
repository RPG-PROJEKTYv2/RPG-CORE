package rpg.rpgcore.dungeons.zamekNieskonczonosci.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class SideBarTask implements Runnable{
    private final RPGCORE rpgcore;
    private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    public SideBarTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        int i = this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 20L, 20L);
        rpgcore.getZamekNieskonczonosciManager().taskList.add(i);
    }

    @Override
    public void run() {

        for (final Team team : scoreboard.getTeams()) {
            if (!rpgcore.getZamekNieskonczonosciManager().teamList.contains(team.getName())) {
                continue;
            }
            for (final Player player : rpgcore.getZamekNieskonczonosciManager().players) {
                if (!team.hasEntry(player.getName())) {
                    continue;
                }
                team.setSuffix(this.getSuffix(player.getHealth(), player.getMaxHealth()) + player.getHealth() + "❤");
            }
        }
        scoreboard.getTeam("time").setSuffix(Utils.format("&c" + Utils.durationToString(System.currentTimeMillis() - rpgcore.getZamekNieskonczonosciManager().time, true)));
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
}
