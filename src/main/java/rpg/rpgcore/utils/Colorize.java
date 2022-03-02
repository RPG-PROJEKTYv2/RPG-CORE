package rpg.rpgcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Colorize {

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&',str);
    }

    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(format(msg));
    }

}