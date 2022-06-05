package rpg.rpgcore.targ;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Sprawdz implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Sprawdz(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("rpg.sprawdz"))) {
            player.sendMessage(Utils.permisje("rpg.sprawdz"));
            return false;
        }


        if (args.length == 1) {

            final UUID targetUUID = rpgcore.getPlayerManager().getPlayerUUID(args[0]);

            if (targetUUID == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono podanego gracza"));
                return false;
            }

            if (!Bukkit.getPlayer(targetUUID).isOnline()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodany gracz jest offline"));
                return false;
            }

            final Inventory targ = rpgcore.getTargManager().getPlayerTarg(targetUUID);

            if (targ.getItem(0) == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodany gracz nie ma wystawionych zadnych przedmiotow"));
                return false;
            }

            if (targ.getViewers().size() > 0) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cKtos aktualnie przeglada ten targ. Sproboj ponownie za chwile"));
                return false;
            }

            if (rpgcore.getTargManager().isInTaskMap(uuid)) {
                rpgcore.getServer().getScheduler().cancelTask(rpgcore.getTargManager().getPlayerTaskId(uuid));
                rpgcore.getTargManager().removePlayerFromTaskMap(uuid);
            }

            player.openInventory(targ);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie otworzono targ gracza &6" + args[0]));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie targ zostanie zamkniety po &c60 sekundach"));
            int task = rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> {
                if (player.getOpenInventory().getTopInventory().getName().contains("Targ gracza") || player.getOpenInventory().getTopInventory().getName().contains("Kup przedmiot ")) {
                    player.closeInventory();
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&8&oOchrona AntyBlock, &8otwarty przez ciebie targ zostal zamkniety poniewaz byl otwarty dluzej niz &c60 sekund"));
                }
            }, 1200L);
            rpgcore.getTargManager().putPlayerTask(uuid, task);
            return false;
        }


        player.sendMessage(Utils.poprawneUzycie("sprawdz <gracz>"));
        return false;
    }
}
