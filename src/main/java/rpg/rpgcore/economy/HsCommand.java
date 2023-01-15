package rpg.rpgcore.economy;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.io.IOException;
import java.util.Arrays;

public class HsCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public HsCommand(final RPGCORE rpgcore) {
        super("hs");
        this.setAliases(Arrays.asList("hellsy", "coins"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }


    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Utils.format("&7Twoj aktualny stan &4&lH&6&lS&7: &c" + Utils.spaceNumber(String.valueOf(rpgcore.getUserManager().find(player.getUniqueId()).getHellcoins()))));
            return;
        }

        if (args.length == 2 && args[0].equals("wyplac")) {
            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                return;
            }
            if (amount < 1) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Niepoprawna liczba!"));
                return;
            }

            User user = rpgcore.getUserManager().find(player.getUniqueId());

            if (user.getHellcoins() < amount) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie posiadasz tylu &4&lH&6&lS&7!"));
                return;
            }

            user.setHellcoins(user.getHellcoins() - amount);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(player.getUniqueId(), user));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wyplacono &6" + Utils.spaceNumber(String.valueOf(amount)) + " &4&lH&6&lS&a!"));
            player.getInventory().addItem(GlobalItem.getHells(amount).clone());
            return;
        }

        player.sendMessage(Utils.poprawneUzycie("hs [wyplac] [ilosc]"));
    }
}
