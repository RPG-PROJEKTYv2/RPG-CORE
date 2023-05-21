package rpg.rpgcore.lvl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

public class LvlCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public LvlCommand(RPGCORE rpgcore) {
        super("lvl");
        this.setAliases(Arrays.asList("level", "poziom"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{2,16}$");

    public void executeCommand(CommandSender sender, String[] args){
        final Player player = (Player) sender;
        if (args.length == 0) {
            rpgcore.getLvlManager().getPlayerLvl(player, player.getUniqueId());
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(Utils.poprawneUzycie("lvl [gracz]"));
                return;
            }

            if (!pattern.matcher(args[0]).matches()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNick gracza moze zawierac tylko litery, cyfry i znak podkreslenia!"));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&8Nice try m8 ;>!"));
                return;
            }

            if (rpgcore.getUserManager().find(args[0]) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0]));
                return;
            }

            final UUID uuidPlayerToSeeInfo = rpgcore.getUserManager().find(args[0]).getId();
            rpgcore.getLvlManager().getPlayerLvl(player, uuidPlayerToSeeInfo);
        }
    }
}
