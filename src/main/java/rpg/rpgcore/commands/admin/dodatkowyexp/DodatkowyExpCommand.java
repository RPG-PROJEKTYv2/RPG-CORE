package rpg.rpgcore.commands.admin.dodatkowyexp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.server.Server;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class DodatkowyExpCommand extends CommandAPI {
    public DodatkowyExpCommand() {
        super("dodatkowyexp");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("dodatkowyexp <on/off> <wartosc> <czas>"));
            return;
        }

        if (args.length > 3) {
            player.sendMessage(Utils.poprawneUzycie("dodatkowyexp <on/off> <wartosc> <czas>"));
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("off")) {
            Server data = RPGCORE.getInstance().getServerManager().find("dodatkowyExp").getServer();
            if (!data.isAktywny()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Dodatkowy exp jest obecnie &cwylaczony&7!"));
                return;
            }

            data.setDodatkowyExp(0);
            data.setCzas(0L);
            data.setAktywny(false);
            RPGCORE.getInstance().getMongoManager().saveOtherData("dodatkowyExp");
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&7Dodatkowy exp zostal &cwylaczony &7przez &4" + player.getName() + "&7!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
            return;

        }

        if (args.length == 3 && args[0].equalsIgnoreCase("on")) {
            Server data = RPGCORE.getInstance().getServerManager().find("dodatkowyExp").getServer();
            if (data.isAktywny()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Dodatkowy exp jest juz &awlaczony&7!"));
                return;
            }
            long time = Utils.durationFromString(args[2], false);
            data.setDodatkowyExp(Integer.parseInt(args[1]));
            data.setCzas(System.currentTimeMillis() + time);
            data.setAktywny(true);

            RPGCORE.getInstance().getMongoManager().saveOtherData("dodatkowyExp");
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&7Dodatkowy exp zostal &awlaczony &7przez &4" + player.getName() + "&7!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&7Wartosc: &c" + data.getDodatkowyExp() + " &7%, Czas trwania: &c" + args[2]));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&8===============&6EXP&8==============="));
        }
    }
}
