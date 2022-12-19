package rpg.rpgcore.commands.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class AdministracjaCommand extends CommandAPI {
    public AdministracjaCommand() {
        super("administracja");
        this.setAliases(Arrays.asList("admin", "adm"));
        this.setRestrictedForPlayer(true);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage("Poprawne uzycie: /administracja");
            return;
        }

        player.sendMessage(Utils.format("&8&m--------{--&3&l Lista Administracji &8&m--}--------"));
        player.sendMessage(Utils.format("&4&lDeveloper"));
        player.sendMessage(Utils.format("&8- &cMires_"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&4&lHeadAdmin"));
        player.sendMessage(Utils.format("&8- &cZwariowanyOrzel"));
        player.sendMessage(Utils.format("&8- &cChytryy"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&4&lAdmin"));
        player.sendMessage(Utils.format("&8- &cFabiLord"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lGameMaster"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lModerator"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&2&lKid Moderator"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&3&lHelper"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&3&lJuniorHelper"));
        player.sendMessage(Utils.format("&8- &7Brak"));
        player.sendMessage(Utils.format(""));
        player.sendMessage(Utils.format("&8&m--------{--&3&l Lista Administracji &8&m--}--------"));
    }
}
