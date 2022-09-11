package rpg.rpgcore.economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class KasaCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public KasaCommand(final RPGCORE rpgcore) {
        super("kasa");
        this.setAliases(Arrays.asList("money", "bal", "balance"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 2) {
            player.sendMessage(Utils.poprawneUzycie("kasa"));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(Utils.format("&2Twoj aktualny stan konta wynosi: &6&o" + Utils.spaceNumber(String.valueOf(Utils.kasaFormat.format(rpgcore.getUserManager().find(player.getUniqueId()).getKasa()))) + "&2$"));
            return;
        }

        if (args.length == 1) {
            if (args[0].equals("daj")) {
                if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()) {
                    return;
                }
                rpgcore.getUserManager().find(player.getUniqueId()).setKasa(100000000000.0);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(player.getUniqueId(), rpgcore.getUserManager().find(player.getUniqueId())));
            }
        }
    }
}
