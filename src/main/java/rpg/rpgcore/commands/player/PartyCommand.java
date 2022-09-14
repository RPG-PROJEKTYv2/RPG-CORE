package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class PartyCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public PartyCommand(final RPGCORE rpgcore) {
        super("party");
        this.setAliases(Arrays.asList("p"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            rpgcore.getPartyManager().sendAllCommands(player);
            return;
        }

        if (args[0].equalsIgnoreCase("zaloz")) {
            rpgcore.getPartyManager().createParty(player);
            return;
        }
        if (args[0].equalsIgnoreCase("usun")) {
            rpgcore.getPartyManager().removeParty(player);
            return;
        }
        if (args[0].equalsIgnoreCase("opusc")) {
            rpgcore.getPartyManager().leaveParty(player);
            return;
        }
        if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("info")) {
            rpgcore.getPartyManager().listParty(player);
            return;
        }
        if (args.length == 2) {
            if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()) {
                player.sendMessage(Utils.SERVERNAME + Utils.NIEMATAKIEGOGRACZA);
                return;
            }
            if (args[0].equalsIgnoreCase("zapros")) {
                rpgcore.getPartyManager().invitePlayer(player, Bukkit.getPlayer(rpgcore.getUserManager().find(args[1]).getId()));
                return;
            }
            if (args[0].equalsIgnoreCase("wyrzuc")) {
                rpgcore.getPartyManager().removeFromParty(player, Bukkit.getPlayer(rpgcore.getUserManager().find(args[1]).getId()));
                return;
            }
            if (args[0].equalsIgnoreCase("dolacz")) {
                rpgcore.getPartyManager().joinParty(player, Bukkit.getPlayer(rpgcore.getUserManager().find(args[1]).getId()));
                return;
            }
        }
        rpgcore.getPartyManager().sendAllCommands(player);
    }
}
