package rpg.rpgcore.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;

public class NameTagUtil {


    public static void changePlayerName(final Player player, final String prefix, final String action) {
        if (prefix == null || action == null) return;
        final Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard.getTeam(player.getName()) == null)
            scoreboard.registerNewTeam(player.getName());
        Team team;
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

    public static void setPlayerNameTag(final Player p) {
        final User user = RPGCORE.getInstance().getUserManager().find(p.getUniqueId());
        if (user.getRankUser().isStaff()) {
            NameTagUtil.changePlayerName(p, RPGCORE.getInstance().getUserManager().find(p.getUniqueId()).getRankUser().getRankType().getPrefix(), "updatePrefix");
        } else {
            if (RPGCORE.getInstance().getGuildManager().hasGuild(p.getUniqueId())) {
                NameTagUtil.changePlayerName(p, "&8[&3" + RPGCORE.getInstance().getGuildManager().getGuildTag(p.getUniqueId()) + "&8] ", "updatePrefix");
            } else {
                NameTagUtil.changePlayerName(p, "&7", "updatePrefix");
            }
        }
    }

}