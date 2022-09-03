package rpg.rpgcore.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class ChatCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public ChatCommand(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("admin.rpg.chat"))) {
            player.sendMessage(Utils.permisje("admin.rpg.chat"));
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                if (rpgcore.getChatManager().isChatEnabled()) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest juz &awlaczony&7!"));
                    return false;
                }
                rpgcore.getChatManager().setChatEnabled(true);
                rpgcore.getChatManager().resetLvlReqForChat();
                rpgcore.getChatManager().resetRankReqForChat();
                this.clearChat();
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Chat zostal &awlaczony &7przez &6" + player.getName()));
                return false;
            }

            if (args[0].equalsIgnoreCase("off")) {
                if (!rpgcore.getChatManager().isChatEnabled()) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest juz &cwylaczony&7!"));
                    return false;
                }
                rpgcore.getChatManager().setChatEnabled(false);
                rpgcore.getChatManager().resetLvlReqForChat();
                rpgcore.getChatManager().resetRankReqForChat();
                this.clearChat();
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Chat zostal &cwylaczony &7przez &6" + player.getName()));
                return false;
            }
            if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")) {
                this.clearChat();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat zostal czyszczony przez &6" + player.getName()));
                return false;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                rpgcore.getChatManager().setChatEnabled(true);
                rpgcore.getChatManager().resetLvlReqForChat();
                rpgcore.getChatManager().resetRankReqForChat();
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Zresetowano wymagania dotyczace chatu!"));
                return false;
            }
            player.sendMessage(Utils.poprawneUzycie("chat <on/off/rank/lvl/clear/reset> <wartosc>"));
            return false;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("rank")) {
                if (rpgcore.getChatManager().getRankReqForChat().getName().equalsIgnoreCase(args[1])) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest juz ustawiony na chat dla &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                    return false;
                }
                rpgcore.getChatManager().setChatEnabled(true);
                rpgcore.getChatManager().resetLvlReqForChat();
                rpgcore.getChatManager().resetRankReqForChat();
                rpgcore.getChatManager().setRankReqForChat(RankType.getByName(args[1]));
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Chat zostal ustawiony na chat dla rangi &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                return false;
            }
            if (args[0].equalsIgnoreCase("lvl")) {
                try {
                    final int lvl = Integer.parseInt(args[1]);

                    if (rpgcore.getChatManager().getLvlReqForChat() == lvl) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest juz ustawiony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                        return false;
                    }

                    rpgcore.getChatManager().setChatEnabled(true);
                    rpgcore.getChatManager().resetLvlReqForChat();
                    rpgcore.getChatManager().resetRankReqForChat();
                    rpgcore.getChatManager().setLvlReqForChat(lvl);
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.SERVERNAME + "&7Chat zostal ustawiony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                } catch (final NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podana wartosc &6" + args[1] + "&7 nie jest liczba!"));
                    return false;
                }
                return false;
            }
            player.sendMessage(Utils.poprawneUzycie("chat <on/off/rank/lvl/clear/reset> <wartosc>"));
            return false;
        }


        player.sendMessage(Utils.poprawneUzycie("chat <on/off/rank/lvl/clear/reset> <wartosc>"));
        return false;
    }

    private void clearChat() {
        for (int i = 0; i < 100; i++) {
            rpgcore.getServer().broadcastMessage("");
        }
    }

}
