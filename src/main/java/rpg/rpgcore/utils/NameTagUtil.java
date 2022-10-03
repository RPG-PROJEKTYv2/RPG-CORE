package rpg.rpgcore.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rpg.rpgcore.RPGCORE;

public class NameTagUtil {
    private static Team team;
    private static Scoreboard scoreboard;


    public static void changePlayerName(final Player player, final String prefix, final String action) {
        if (player.getScoreboard() == null || prefix == null || action == null) return;
        scoreboard = player.getScoreboard();
        if (scoreboard.getTeam(player.getName()) == null)
            scoreboard.registerNewTeam(player.getName());
        (team = scoreboard.getTeam(player.getName())).setPrefix(Utils.format(prefix));
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        switch (action) {
            case "updatePrefix":
                team.unregister();
                scoreboard.registerNewTeam(player.getName());
                (team = scoreboard.getTeam(player.getName())).setPrefix(Utils.format(prefix));
                team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                team.addEntry(player.getName());
                break;
            case "delete":
                team.setNameTagVisibility(NameTagVisibility.NEVER);
                team.unregister();
                break;
        }
    }

    public static void setPlayerDisplayNameNoGuild(final Player p, final String playerGroup) {
        if (RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankUser().isStaff()) {
            NameTagUtil.changePlayerName(p, RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankUser().getRankType().getPrefix(), "updatePrefix");
        } else {
            NameTagUtil.changePlayerName(p, RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankPlayerUser().getRankType().getPrefix(), "updatePrefix");
        }
    }

    public static void setPlayerDisplayNameGuild(final Player p, final String playerGroup, final String tag) {
        if (RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankUser().isStaff()) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] " + RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankUser().getRankType().getPrefix(), "updatePrefix");
        } else {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] " + RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankPlayerUser().getRankType().getPrefix(), "updatePrefix");
        }
    }

}