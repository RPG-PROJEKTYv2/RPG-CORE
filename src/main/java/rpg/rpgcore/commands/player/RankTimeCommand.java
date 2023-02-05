package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class RankTimeCommand extends CommandAPI {

    public RankTimeCommand() {
        super("ranktime");
        setRankLevel(RankType.GRACZ);
        setAliases(Arrays.asList("czasrangi"));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (user.getRankPlayerUser().getRankType() == RankTypePlayer.GRACZ) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz rangi!"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Mozesz ja zakupic na naszej stronie www.hellrpg.pl"));
            return;
        }


        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoja ranga: " + user.getRankPlayerUser().getRankType().getPrefix()));
        if (user.getRankPlayerUser().getTime() == -1) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pozostaly czas: &6LifeTime"));
            return;
        } else {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pozostaly czas: &6" + Utils.durationToString(user.getRankPlayerUser().getTime(), false)));
        }
        if (user.getRankPlayerUser().getTime() <= System.currentTimeMillis()) {
            user.getRankPlayerUser().setRank(RankTypePlayer.GRACZ);
            user.getRankPlayerUser().setTime(0L);
            if (user.isTworca()) {
                user.getRankPlayerUser().setRank(RankTypePlayer.TWORCA);
                user.getRankPlayerUser().setTime(-1L);
            }
            RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&4&l!&8&l]", 5, 20, 5), RPGCORE.getInstance().getNmsManager().makeSubTitle("&cTwoja ranga wlasnie wygasla!", 5, 20, 5));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
        }
    }
}
