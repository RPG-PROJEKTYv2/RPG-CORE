package rpg.rpgcore.npc.magazynier.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class MagazynyCommand extends CommandAPI {
    public MagazynyCommand() {
        super("magazyny");
        this.setRestrictedForPlayer(true);
        this.setAliases(Arrays.asList("magazyn", "mag"));
        this.setRankLevel(RankType.GRACZ);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final MagazynierUser magazynierUser = RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId());
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
        if (user.getRankPlayerUser().getRankType().getPriority() == 3 || (user.getRankUser().isStaff() && user.isAdminCodeLogin())) {
            RPGCORE.getInstance().getMagazynierNPC().openMagazynyList(player);
            return;
        }
        if (!magazynierUser.isRemoteCommand()) {
            player.sendMessage(Utils.format("&b&lMagazynier &8>> &7Niestety jeszcze nie odblokowales/-as tych super umiejetnosci!"));
            player.sendMessage(Utils.format("&b&lMagazynier &8>> &7Widzimy sie u mnie :)"));
            return;
        }
        RPGCORE.getInstance().getMagazynierNPC().openMagazynyList(player);
    }
}
