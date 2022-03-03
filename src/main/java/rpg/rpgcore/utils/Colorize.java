package rpg.rpgcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Colorize {

    public String format(String str) {
        return ChatColor.translateAlternateColorCodes('&',str.replace(">>", "»").replace("<<", "«"));
    }

    public void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(format(msg));
    }

}