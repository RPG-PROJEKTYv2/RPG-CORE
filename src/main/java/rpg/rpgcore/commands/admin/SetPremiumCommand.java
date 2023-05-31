package rpg.rpgcore.commands.admin;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;

public class SetPremiumCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public SetPremiumCommand(final RPGCORE rpgcore) {
        super("setpremium");
        this.setRankLevel(RankType.HA);
        this.setAliases(Arrays.asList("spremium", "setp", "setpremium", "premium", "tworca"));
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(Utils.poprawneUzycie("setpremium <nick> <Player/Vip/Elita/Budwniczy/Tworca> <czas/-1> <broadcast (true/false)>"));
            return;
        }

        if (args[0] == null || args[1] == null || args[2] == null || args[3] == null) {
            sender.sendMessage(Utils.poprawneUzycie("setpremium <nick> <Player/Vip/Elita/Budwniczy/Tworca> <czas/-1> <broadcast (true/false)>"));
            return;
        }

        if (!rpgcore.getUserManager().isUserName(args[0])) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza!"));
            return;
        }


        final User user = rpgcore.getUserManager().find(args[0]);
        if (args[1].equalsIgnoreCase("Player")) {
            user.getRankPlayerUser().setRank(RankTypePlayer.GRACZ);
            user.getRankPlayerUser().setTime(0L);
            if (user.isTworca()) {
                user.setTworca(false);
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("               &4&lHELL&8&lRPG            "));
                rpgcore.getServer().broadcastMessage(Utils.format("  &cGracz &6" + user.getName() + " &cstracil wlasnie range &9&lTworca&c!"));
                rpgcore.getServer().broadcastMessage(Utils.format("               &4&lHELL&8&lRPG            "));
                rpgcore.getServer().broadcastMessage(" ");
            }

            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aZmieniono rangę gracza &6" + args[0] + " &ana &6Player&7!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataUser(user.getId(), user);
                rpgcore.getMongoManager().changeAuthUserRank(user.getId(), user.getRankPlayerUser().getRankType().getName());
            });


            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                final Player player = Bukkit.getPlayer(args[0]);
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                    TabManager.removePlayer(player);
                    TabManager.addPlayer(player);
                    for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                        TabManager.update(restOfServer.getUniqueId());
                    }
                });
            }
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zmiana Rangi**",
                    "**Gracz:** `" + args[0] + "`**uzyskal nowa range**\n" +
                            (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "") +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Ranga**: " + args[1].toUpperCase() + "\n" +
                            "**Czas**: LifeTime\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.GREEN));
            return;
        }

        if (args[1].equalsIgnoreCase("tworca")) {
            if (user.getRankPlayerUser().getRankType().getPriority() < RankTypePlayer.TWORCA.getPriority()) {
                user.getRankPlayerUser().setRank(RankTypePlayer.TWORCA);
                user.getRankPlayerUser().setTime(-1);
            }
            user.setTworca(true);
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aNadano rangę &9&lTworca &adla gracza &6" + args[0] + "&a!"));

            rpgcore.getServer().broadcastMessage(" ");
            rpgcore.getServer().broadcastMessage(Utils.format("               &4&lHELL&8&lRPG            "));
            rpgcore.getServer().broadcastMessage(Utils.format("  &aGracz &6" + user.getName() + " &aotrzymmal range &6" + user.getRankPlayerUser().getRankType().getPrefix().trim() + "&a!"));
            rpgcore.getServer().broadcastMessage(Utils.format("               &4&lHELL&8&lRPG            "));
            rpgcore.getServer().broadcastMessage(" ");

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                final Player player = Bukkit.getPlayer(args[0]);
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle(Utils.CLEANSERVERNAME, 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&aPomyslnie otrzymales/as range &6" + user.getRankPlayerUser().getRankType().getPrefix() + "&a na okres &6LifeTime&a!", 5, 20, 5));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                    TabManager.removePlayer(player);
                    TabManager.addPlayer(player);
                    for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                        TabManager.update(restOfServer.getUniqueId());
                    }
                });
            }
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zmiana Rangi**",
                    "**Gracz:** `" + user.getName() + "`**uzyskal nowa range**\n" +
                            (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "") +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Ranga**: " + user.getRankPlayerUser().getRankType().getName() + "\n" +
                            "**Czas**: LifeTime\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.GREEN));


            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataUser(user.getId(), user);
                rpgcore.getMongoManager().changeAuthUserRank(user.getId(), user.getRankPlayerUser().getRankType().getName());
            });
            return;
        }


        user.getRankPlayerUser().setRank(RankTypePlayer.valueOf(args[1].toUpperCase(Locale.ROOT)));
        if (args[2].equals("-1")) {
            user.getRankPlayerUser().setTime(-1);
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyślnie ustawiono rangę gracza &6" + args[0] + " &a na &6" + args[1] + " &a na okres &6LifeTime&a!"));
            if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("tak") || args[3].equalsIgnoreCase("1")) {
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
                rpgcore.getServer().broadcastMessage(Utils.format("  &aGracz &6" + args[0] + " &azakupil range &6" + user.getRankPlayerUser().getRankType().getPrefix() + "&a na okres &6LifeTime!"));
                rpgcore.getServer().broadcastMessage(Utils.format("  &6Dziekujemy za wspacie servera. &cZespol Hellrpg.pl ❤"));
                rpgcore.getServer().broadcastMessage(Utils.format("  &7Rangi oraz HellCooins'y mozesz zakupic na naszej stronie www.hellrpg.pl"));
                rpgcore.getServer().broadcastMessage(Utils.format("               &6&lItem&2&lShop            "));
                rpgcore.getServer().broadcastMessage(" ");

            }
            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                final Player player = Bukkit.getPlayer(args[0]);
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lItem&2&lShop", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&aPomyslnie otrzymales/as range &6" + user.getRankPlayerUser().getRankType().getPrefix() + "&a na okres &6LifeTime&a! &cDziekujemy za wsparcie ❤", 5, 20, 5));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                    TabManager.removePlayer(player);
                    TabManager.addPlayer(player);
                    for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                        TabManager.update(restOfServer.getUniqueId());
                    }
                });
            }
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zmiana Rangi**",
                    "**Gracz:** `" + args[0] + "`**uzyskal nowa range**\n" +
                            (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "") +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Ranga**: " + args[1].toUpperCase() + "\n" +
                            "**Czas**: LifeTime\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.GREEN));
        } else {
            user.getRankPlayerUser().setTime(System.currentTimeMillis() + Utils.durationFromString(args[2], false));
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyślnie ustawiono rangę gracza &6" + args[0] + " &a na &6" + args[1] + " &a na okres &6" + args[2] + "&a!"));
            if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("tak") || args[3].equalsIgnoreCase("1")) {
                rpgcore.getServer().broadcastMessage(" ");
                rpgcore.getServer().broadcastMessage(Utils.format("          &6&lItem&2&lShop            "));
                rpgcore.getServer().broadcastMessage(Utils.format("  &aGracz &6" + args[0] + " &azakupil range &6" + user.getRankPlayerUser().getRankType().getPrefix() + "&a na okres &6" + args[2] + "&a!"));
                rpgcore.getServer().broadcastMessage(Utils.format("  &6Dziekujemy za wspacie servera. &cZespol Hellrpg.pl ❤"));
                rpgcore.getServer().broadcastMessage(Utils.format("  &7Rangi oraz HellCooins'y mozesz zakupic na naszej stronie www.hellrpg.pl"));
                rpgcore.getServer().broadcastMessage(Utils.format("          &6&lItem&2&lShop            "));
                rpgcore.getServer().broadcastMessage(" ");
            }
            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                final Player player = Bukkit.getPlayer(args[0]);
                rpgcore.getNmsManager().sendTitleAndSubTitle(player, rpgcore.getNmsManager().makeTitle("&6&lItem&2&lShop", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("&aPomyslnie otrzymales/as range &6" + user.getRankPlayerUser().getRankType().getPrefix() + "&a na okres &6" + Utils.durationToString(user.getRankPlayerUser().getTime(), false) + "&a! &cDziekujemy za wsparcie ❤", 5, 20, 5));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                    TabManager.removePlayer(player);
                    TabManager.addPlayer(player);
                    for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                        TabManager.update(restOfServer.getUniqueId());
                    }
                });
            }
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("itemshop-logs", EmbedUtil.create("**Zmiana Rangi**",
                    "**Gracz:** `" + args[0] + "`**uzyskal nowa range**\n" +
                            (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "") +
                            "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n" +
                            "**Ranga**: " + args[1].toUpperCase() + "\n" +
                            "**Czas**: " + Utils.durationToString(user.getRankPlayerUser().getTime(), false) + "\n" +
                            "**Nadane Przez**: " + sender.getName(), Color.GREEN));
        }

        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
            rpgcore.getMongoManager().saveDataUser(user.getId(), user);
            rpgcore.getMongoManager().changeAuthUserRank(user.getId(), user.getRankPlayerUser().getRankType().getName());
        });
    }
}
