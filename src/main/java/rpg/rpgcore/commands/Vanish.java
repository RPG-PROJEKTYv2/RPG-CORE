package rpg.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class Vanish implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Vanish(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }
        final Player player = (Player) sender;


        if (args.length == 0){
            int task = 0;
            if (rpgcore.getVanishManager().containsPlayer(player.getUniqueId())){
                rpgcore.getVanishManager().getVanishList().remove(player.getUniqueId());
                rpgcore.getVanishManager().revealPlayer(player);
                Bukkit.getScheduler().cancelTask(task);
            } else {
                rpgcore.getVanishManager().getVanishList().add(player.getUniqueId());
                rpgcore.getVanishManager().hidePlayer(player);
                task = Bukkit.getScheduler().scheduleSyncRepeatingTask(rpgcore, () -> {
                    if (rpgcore.getVanishManager().containsPlayer(player.getUniqueId())){
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(rpgcore.getNmsManager().makeActionBar("&3&lVanish"));
                    }
                }, 5L, 5L);
            }
        }

        return false;
    }
}
