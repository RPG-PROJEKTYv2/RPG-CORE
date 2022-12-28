package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.lesnik.LesnikUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class MisjeCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public MisjeCommand(final RPGCORE rpgcore) {
        super("misje");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Deprecated
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType().getPriority() >= RankType.HA.getPriority()) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
                }
                this.openMissionGUI(player, target);
                return;
            }
        }
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("misje"));
            return;
        }
        this.openMissionGUI(player, player);
    }


    public void openMissionGUI(Player player, Player target) {
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&8Misje gracza &e" + target.getName()));
        if (player == target) {
            final LesnikUser lesnikUser = rpgcore.getLesnikNPC().find(player.getUniqueId()).getUser();
            gui.setItem(0, null);
        } else {

        }
        player.openInventory(gui);
    }
}
