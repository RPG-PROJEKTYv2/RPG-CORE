package rpg.rpgcore.guilds.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GuildChatCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public GuildChatCommand(RPGCORE rpgcore) {
        super("guildchat");
        this.setRestrictedForPlayer(true);
        this.setAliases("gc", "kc");
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&7Poprawne uzycie to: &c/guildchat <wiadomosc>"));
            return;
        }

        final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());

        if (tag.equals("Brak Klanu")) {
            player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
            return;
        }

        rpgcore.getGuildManager().sendMessageToGuild(tag, String.join(" ", args).trim(), player);
    }
}
