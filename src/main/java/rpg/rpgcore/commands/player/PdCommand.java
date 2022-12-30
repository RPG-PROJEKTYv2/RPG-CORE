package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class PdCommand extends CommandAPI {
    public PdCommand() {
        super("pd");
        this.setAliases(Arrays.asList("piersciendoswiadczenia", "pierscien"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.poprawneUzycie("pd"));
            return;
        }
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
        if (user.getPierscienDoswiadczenia() == 0) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pierscien Doswiadczenia jest nie aktywny!"));
            return;
        }
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pierscien Doswiadczenia: &6" + user.getPierscienDoswiadczenia() + "%"));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Pozostaly Czas: &6" + Utils.durationToString(user.getPierscienDoswiadczeniaTime(), false)));
        if (user.getPierscienDoswiadczeniaTime() <= System.currentTimeMillis() && user.getPierscienDoswiadczenia() != 0) {
            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(user.getId());
            bonuses.getBonusesUser().setDodatkowyExp(bonuses.getBonusesUser().getDodatkowyExp() - user.getPierscienDoswiadczenia());
            user.setPierscienDoswiadczenia(0);
            user.setPierscienDoswiadczeniaTime(0L);
            RPGCORE.getInstance().getNmsManager().sendTitleAndSubTitle(player, RPGCORE.getInstance().getNmsManager().makeTitle("&8&l[&4&l!&8&l]", 5, 20, 5), RPGCORE.getInstance().getNmsManager().makeSubTitle("&cTwoj &ePierscien Doswiadczenia &cdobiegl konca", 5, 20, 5));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user);
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(user.getId(), bonuses);
            });
        }
    }
}
