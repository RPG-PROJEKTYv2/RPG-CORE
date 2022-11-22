package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.Arrays;

public class SetAdminRankCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public SetAdminRankCommand(final RPGCORE rpgcore) {
        super("setadminrank");
        this.setRankLevel(RankType.HA);
        this.setAliases(Arrays.asList("admin", "setadmin", "setrank"));
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(Utils.poprawneUzycie("setadminrank <nick> <gracz/juniorhelper/helper/kidmod/mod/gm/admmin/ha/dev> <broadcast?>"));
            return;
        }
        if (args[0] == null || args[1] == null || args[2] == null) {
            sender.sendMessage(Utils.poprawneUzycie("setadminrank <nick> <gracz/juniorhelper/helper/kidmod/mod/gm/admmin/ha/dev> <broadcast?>"));
            return;
        }

        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza!"));
            return;
        }

        final User user = rpgcore.getUserManager().find(args[0]);
        final RankType rankTypeBefore = user.getRankUser().getRankType();
        final RankType rankTypeAfter = RankType.valueOf(args[1].toUpperCase());

        if (sender instanceof Player) {
            if (rpgcore.getUserManager().find(((Player) sender).getUniqueId()).getRankUser().getRankType().getPriority() <= rankTypeBefore.getPriority() && !sender.getName().equals("Mires_") && !args[0].equalsIgnoreCase("Mires_")) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zmienic rangi gracza z ranga wyzsza lub rowna jak twoja!"));
                return;
            }
        }

        if (rankTypeBefore == rankTypeAfter) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cTen gracz posiada juz ta ranga!"));
            return;
        }

        user.getRankUser().setRank(rankTypeAfter);

        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles ranga gracza &6" + user.getName() + " &az &6" + rankTypeBefore.getName() + " &ana &6" + rankTypeAfter.getName() + "&a!"));
        if (Bukkit.getPlayer(user.getId()) != null && Bukkit.getPlayer(user.getId()).isOnline()) {
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                NameTagUtil.setPlayerNameTag(Bukkit.getPlayer(user.getId()), "updatePrefix");
                TabManager.removePlayer(Bukkit.getPlayer(user.getId()));
                TabManager.addPlayer(Bukkit.getPlayer(user.getId()));
                for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                    TabManager.update(restOfServer.getUniqueId());
                }
            });
        }
        if (rankTypeAfter.getName().equalsIgnoreCase("gracz")) {
            if (args[2].equals("true")) {
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("&cGracz &6" + user.getName()  + " &czostal usuniety z administracji serwera przez &6" + sender.getName() + "&c!"));
                rpgcore.getServer().broadcastMessage(Utils.format("&8Press 'F' to pay respect."));
                rpgcore.getServer().broadcastMessage(" ");

            }
            if (Bukkit.getPlayer(user.getId()) != null && Bukkit.getPlayer(user.getId()).isOnline()) {
                rpgcore.getNmsManager().sendTitleAndSubTitle(Bukkit.getPlayer(user.getId()), rpgcore.getNmsManager().makeTitle("&4&lDegrad", 5, 20 ,5), rpgcore.getNmsManager().makeSubTitle("&cZostales usuniety z administracji serwera przez &6" + sender.getName() + "&c!", 5, 20, 5));
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            RPGCORE.getDiscordBot().sendChannelMessage("adminrank-logs", EmbedUtil.create("**DEGRAD**",
                    "**Gracz:** `" + args[0] + "`** zostal wyrzucony z administracji**\n" +
                            "**Nadane Przez:** " + sender.getName(), Color.RED));
            return;
        }

        if (rankTypeBefore.getPriority() > rankTypeAfter.getPriority()) {
            if (args[2].equals("true")) {
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("&cGracz &6" + user.getName()  + " &czostal zdegradowany do rangi &6" + rankTypeAfter.getName() + " &cprzez &6" + sender.getName() + "&c!"));
                rpgcore.getServer().broadcastMessage(Utils.format("&8Press 'F' to pay respect."));
                rpgcore.getServer().broadcastMessage(" ");

            }
            if (Bukkit.getPlayer(user.getId()) != null && Bukkit.getPlayer(user.getId()).isOnline()) {
                rpgcore.getNmsManager().sendTitleAndSubTitle(Bukkit.getPlayer(user.getId()), rpgcore.getNmsManager().makeTitle("&4&lDegrad", 5, 20 ,5), rpgcore.getNmsManager().makeSubTitle("&cZostales zdegradowany do rangi &6" + rankTypeAfter.getName() + " &cprzez &6" + sender.getName() + "&c!", 5, 20, 5));
            }
            RPGCORE.getDiscordBot().sendChannelMessage("adminrank-logs", EmbedUtil.create("**DEGRAD**",
                    "**Gracz:** `" + args[0] + "`** zostal zdegradowany**\n" +
                            "**Ranga Przed Degradem:** " + rankTypeBefore.getName() + "\n" +
                            "**Ranga Po Degradzie:** " + rankTypeAfter.getName() + "\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.RED));
        } else {
            if (args[2].equals("true")) {
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("&aGracz &6" + user.getName()  + " &azostal awansowany do rangi &6" + rankTypeAfter.getName() + " &aprzez &6" + sender.getName() + "&a!"));
                rpgcore.getServer().broadcastMessage(Utils.format("&6Gratulujemy i liczymy na dalsza wspolprace! &cZespol Hellrpg.pl"));
                rpgcore.getServer().broadcastMessage(" ");

            }
            if (Bukkit.getPlayer(user.getId()) != null && Bukkit.getPlayer(user.getId()).isOnline()) {
                rpgcore.getNmsManager().sendTitleAndSubTitle(Bukkit.getPlayer(user.getId()), rpgcore.getNmsManager().makeTitle("&a&lAwans", 5, 20 ,5), rpgcore.getNmsManager().makeSubTitle("&aZostales awansowany do rangi &6" + rankTypeAfter.getName() + " &aprzez &6" + sender.getName() + "&a! &6Gratulacje!", 5, 20, 5));
            }
            RPGCORE.getDiscordBot().sendChannelMessage("adminrank-logs", EmbedUtil.create("**AWANS**",
                    "**Gracz:** `" + args[0] + "` **zostal awansowany**\n" +
                            "**Ranga Przed Awansem:** " + rankTypeBefore.getName() + "\n" +
                            "**Ranga Po Awansie:** " + rankTypeAfter.getName() + "\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.GREEN));
        }
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));

    }
}
