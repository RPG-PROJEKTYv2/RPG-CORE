package rpg.rpgcore.old.OLDtarg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class SprawdzCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public SprawdzCommand(RPGCORE rpgcore) {
        super("sprawdz");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("sprawdz <gracz>"));
            return;
        }
        if (!rpgcore.getUserManager().isUserName(args[0])) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + Utils.NIEMATAKIEGOGRACZA));
            return;
        }
        final UUID uuid = player.getUniqueId();
        final UUID targetUUID = rpgcore.getUserManager().find(args[0]).getId();
        final String targetName = rpgcore.getUserManager().find(args[0]).getName();

        if (!Bukkit.getPlayer(targetUUID).isOnline()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodany gracz jest offline"));
            return;
        }

        final Inventory targ = rpgcore.getNewTargManager().getPlayerTarg(targetName, targetUUID);

        if (targ.getItem(0) == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodany gracz nie ma wystawionych zadnych przedmiotow"));
            return;
        }

        if (targ.getViewers().size() > 0) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cKtos aktualnie przeglada ten targ. Sproboj ponownie za chwile"));
            return;
        }

        if (rpgcore.getTargManager().isInTaskMap(uuid)) {
            rpgcore.getServer().getScheduler().cancelTask(rpgcore.getTargManager().getPlayerTaskId(uuid));
            rpgcore.getTargManager().removePlayerFromTaskMap(uuid);
        }

        player.openInventory(targ);
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otworzono OLDtarg gracza &6" + args[0]));
        player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie OLDtarg zostanie zamkniety po &c60 sekundach"));
        int task = rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {
            if (player.getOpenInventory().getTopInventory().getName().contains("Targ gracza") || player.getOpenInventory().getTopInventory().getName().contains("Kup przedmiot ")) {
                player.closeInventory();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie OLDtarg zostal zamkniety poniewaz byl otwarty dluzej niz &c60 sekund"));
            }
        }, 1200L);
        rpgcore.getTargManager().putPlayerTask(uuid, task);
    }
}
