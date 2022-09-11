package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class RemoveNearbyEntitiesCommand extends CommandAPI {

    public RemoveNearbyEntitiesCommand() {
        super("removenearbyentities");
        this.setRankLevel(RankType.DEV);
        this.setRestrictedForPlayer(true);
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("removenearbyentities [radius]"));
            return;
        }
        try {
            final double radius = Double.parseDouble(args[0]);
            for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius)) {
                if (entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aUsunieto wszystkie armorstandy w poblizu &6" + radius + " &ablokow"));
        } catch (final NumberFormatException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodaj odleglosc"));
        }
    }

}
