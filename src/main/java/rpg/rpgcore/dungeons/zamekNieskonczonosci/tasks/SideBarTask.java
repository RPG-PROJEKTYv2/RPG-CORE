package rpg.rpgcore.dungeons.zamekNieskonczonosci.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class SideBarTask implements Runnable {
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
            if (!team.getName().contains("dungeon_p")) continue;
            for (int i = 0; i < rpgcore.getZamekNieskonczonosciManager().players.size(); i++) {
                if (!team.getName().equals("dungeon_p" + i)) continue;
                final Player player = rpgcore.getZamekNieskonczonosciManager().players.get(i);
                String sufix = "";
                //TODO sprawdzic czy dziala jak ktos zginie
                if (scoreboard.getTeam(team.getName()).getSuffix().contains(" ") && !scoreboard.getTeam(team.getName()).getSuffix().substring(scoreboard.getTeam(team.getName()).getSuffix().indexOf(" ")).contains("ZYJE!")) {
                    sufix = scoreboard.getTeam(team.getName()).getSuffix().substring(0, scoreboard.getTeam(team.getName()).getSuffix().indexOf(" ") + 1);
                }
                if (player.isDead()) {
                    scoreboard.getTeam(team.getName()).setSuffix(Utils.format(sufix + "&4NIE ZYJE!"));
                } else {
                    scoreboard.getTeam(team.getName()).setSuffix(Utils.format(sufix + this.getSuffix(player.getHealth(), player.getMaxHealth()) + String.format("%.0f", player.getHealth()) + "❤"));
                }
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
