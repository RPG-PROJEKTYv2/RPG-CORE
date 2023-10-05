package rpg.rpgcore.spawn;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class SpawnCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public SpawnCommand(RPGCORE rpgcore) {
        super("spawn");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0) {
            if (rpgcore.getCooldownManager().hasAntyLogout(player.getUniqueId())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz uzywac tej komendy podczas &cwalki&7!"));
                return;
            }
            player.teleport(rpgcore.getSpawnManager().getSpawn());
            rpgcore.getCooldownManager().givePlayerTeleporterCooldown(player.getUniqueId());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano na spawna!"));
            //if (rpgcore.getCooldownManager().hasAntyLogout(player.getUniqueId())) {
             //   if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() != null && player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                 //   player.damage(player.getMaxHealth(), ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager());
                 //   return;
              //  }
           // }
            player.setHealth(player.getMaxHealth());
            if (player.hasPotionEffect(PotionEffectType.SLOW)) {
                player.removePotionEffect(PotionEffectType.SLOW);
            }
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }
            if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }
            return;
        }
        if (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType() == RankType.HA || rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().getRankType() == RankType.DEV) {
            if (args[0] == null) {
                return;
            }

            if (args[0].equalsIgnoreCase("set")) {
                final double x = player.getLocation().getX();
                final double y = player.getLocation().getY();
                final double z = player.getLocation().getZ();
                final World w = player.getLocation().getWorld();

                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().setSpawn(player.getLocation()));
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie ustawiono nowego spawna! Na kordach: " + " &7x: " + Utils.df.format(x) + " &7y: " + Utils.df.format(y) + " &7z: " + Utils.df.format(z) + " &7w swiecie " + w.getName()));
                return;

            }

            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie ma takiego gracza!"));
                return;
            }
            this.rpgcore.getTeleportManager().setBeforeTeleportLocation(target.getUniqueId(), target.getLocation());
            target.teleport(rpgcore.getSpawnManager().getSpawn());
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPrzeteleportowano gracza &6" + target.getName() + " &ana spawna!"));
            target.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales przeteleportowany na spawna!"));
            if (rpgcore.getCooldownManager().hasAntyLogout(target.getUniqueId())) return;
            target.setHealth(target.getMaxHealth());
            if (target.hasPotionEffect(PotionEffectType.SLOW)) {
                target.removePotionEffect(PotionEffectType.SLOW);
            }
            if (target.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                target.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            }
            if (target.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
                target.removePotionEffect(PotionEffectType.FAST_DIGGING);
            }
        }

    }
}
