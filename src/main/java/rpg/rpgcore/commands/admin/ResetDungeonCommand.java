package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ResetDungeonCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public ResetDungeonCommand(final RPGCORE rpgcore) {
        super("resetdungeon");
        this.setRankLevel(RankType.ADMIN);
        this.rpgcore = rpgcore;
    }

    private final List<String> dungeons = Arrays.asList(
            "Piekielny Przedsionek"
    );

    public void executeCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("resetdungeon <dungeon/list>"));
            return;
        }

        if (args[0].equals("list")) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&7Lista dungeonow:"));
            for (final String dungeon : dungeons) {
                sender.sendMessage(Utils.format("&8- &c" + dungeon));
            }
            return;
        }
        final StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        final String dungeon = sb.toString().trim();
        if (!dungeons.contains(dungeon)) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiego dungeonu!"));
            return;
        }
        switch (dungeon) {
            case "Piekielny Przedsionek":
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&7Resetuje dungeon &c" + dungeon + "&7..."));
                rpgcore.getPrzedsionekManager().resetDungeon();
                break;
        }
    }
}
