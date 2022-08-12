package rpg.rpgcore.mythicstick;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.UUID;

public class MythicStick implements CommandExecutor {


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("dev.rpg.mythicstick"))) {
            player.sendMessage(Utils.permisje("dev.rpg.mythicstick"));
            return false;
        }

        if (args.length == 1) {
            player.getInventory().addItem(new ItemBuilder(Material.STICK).setName("&6&lMythic &4&lSTICK").setLore(Arrays.asList("&7Aktualnie ustawiasz spawner mobow: &c" + args[0], "&7Nazwa Spawnera: &c" + args[0] + "-RESP-0")).toItemStack().clone());
            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("mythicstick <nazwa>"));
        return false;
    }
}
