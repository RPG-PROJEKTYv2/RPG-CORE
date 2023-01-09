package rpg.rpgcore.lvl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class LvlCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public LvlCommand(RPGCORE rpgcore) {
        super("lvl");
        this.setAliases(Arrays.asList("level", "poziom"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
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

            if (rpgcore.getUserManager().find(args[0]) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &e" + args[0]));
                return;
            }

            final UUID uuidPlayerToSeeInfo = rpgcore.getUserManager().find(args[0]).getId();
            rpgcore.getLvlManager().getPlayerLvl(player, uuidPlayerToSeeInfo);
            return;
        }

        if (args.length < 3) {
            if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()
                    || (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && !rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin())) {
                return;
            }
            player.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-_-_-"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setlvl> <int> &7- pozwala usatwic lvl podanego gracza"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setexp> <double> &7- pozwala usatwic exp podanego gracza"));
            player.sendMessage(Utils.format("&8/&clvl <gracz> <setprocent> <double> &7- pozwala usatwic procent expa podanego gracza"));
            player.sendMessage(Utils.format("&8-_-_-_-_-_-_-_-_-_-_-{&b&lLVL&8}-_-_-_-_-_-_-_-_-_-_-"));
            return;
        }
        if (args.length == 3) {
            if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff()
                    || (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && !rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin())) {
                return;
            }
            if (!rpgcore.getUserManager().isUserName(args[0])) {
                player.sendMessage(Utils.LVLPREFIX + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            final User user = rpgcore.getUserManager().find(args[0]);
            final UUID uuidPlayerToSet = user.getId();

            if (args[1].equalsIgnoreCase("setlvl")) {
                try {
                    final int nowyLvl = Integer.parseInt(args[2]);
                    rpgcore.getLvlManager().setPlayerLvl(player.getName(), uuidPlayerToSet, nowyLvl);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono poziom gracza &6" + user.getName() + " &ana &6" + nowyLvl));
                } catch (final NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe calkowita"));
                    return;
                }
            }
            if (args[1].equalsIgnoreCase("setexp")) {
                try {
                    final double nowyExp = Double.parseDouble(args[2]);
                    rpgcore.getLvlManager().setPlayerExp(player.getName(), uuidPlayerToSet, nowyExp);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono exp gracza &6" + user.getName() + " &ana &6" + nowyExp + " &aexp"));
                } catch (final NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe"));
                    return;
                }
            }
            if (args[1].equalsIgnoreCase("setprocent")) {
                try {
                    final double nowyProcent = Double.parseDouble(args[2]);
                    rpgcore.getLvlManager().setPlayerProcent(player.getName(), uuidPlayerToSet, nowyProcent);
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&aPomyslnie ustawiono postep gracza &6" + user.getName() + " &ana &6" + nowyProcent + "&a%"));
                } catch (final NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.LVLPREFIX + "&cMusisz podac liczbe"));
                }
            }
        }
    }
}
