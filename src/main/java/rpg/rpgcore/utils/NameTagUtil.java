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
        if (playerGroup.equals("H@") || playerGroup.equals("Admin")) {
            NameTagUtil.changePlayerName(p, "&4&l" + playerGroup + " &c", "updatePrefix");
        } else if (playerGroup.equals("GM") || playerGroup.equals("Mod") || playerGroup.equals("KidMod")) {
            NameTagUtil.changePlayerName(p, "&2&l" + playerGroup + " &a", "updatePrefix");
        } else if (playerGroup.equals("Helper")) {
            NameTagUtil.changePlayerName(p, "&3&l" + playerGroup + " &b", "updatePrefix");
        } else if (playerGroup.equals("JuniorHelper")) {
            NameTagUtil.changePlayerName(p, "&3&lJrHelper &b", "updatePrefix");
        }else if (playerGroup.equals("ELITA")) {
            NameTagUtil.changePlayerName(p, "&5&l" + playerGroup + " &7", "updatePrefix");
        } else if (playerGroup.equals("Svip")) {
            NameTagUtil.changePlayerName(p, "&6&lS&e&lvip &7", "updatePrefix");
        } else if (playerGroup.equals("Vip")) {
            NameTagUtil.changePlayerName(p, "&e&lVip &7", "updatePrefix");
        } else if (playerGroup.equals("Budowniczy")) {
            NameTagUtil.changePlayerName(p, "&d&lBud &7", "updatePrefix");
        } else {
            NameTagUtil.changePlayerName(p, "&7", "updatePrefix");
        }
    }

    public static void setPlayerDisplayNameGuild(final Player p, final String playerGroup, final String tag) {
        if (playerGroup.equals("H@") || playerGroup.equals("Admin")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &4&l" + playerGroup + " &c", "updatePrefix");
        } else if (playerGroup.equals("GM") || playerGroup.equals("Mod") || playerGroup.equals("KidMod")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &2&l" + playerGroup + " &a", "updatePrefix");
        } else if (playerGroup.equals("Helper")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &3&l" + playerGroup + " &b", "updatePrefix");
        } else if (playerGroup.equals("JuniorHelper")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &3&lJrHelper &b", "updatePrefix");
        }else if (playerGroup.equals("ELITA")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &5&l" + playerGroup + " &7", "updatePrefix");
        } else if (playerGroup.equals("Svip")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &6&lS&e&lvip &7", "updatePrefix");
        } else if (playerGroup.equals("Vip")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &e&lVip &7", "updatePrefix");
        } else if (playerGroup.equals("Budowniczy")) {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &d&lBud &7", "updatePrefix");
        } else {
            NameTagUtil.changePlayerName(p, "&8[&3" + tag + "&8] &7", "updatePrefix");
        }
    }

}