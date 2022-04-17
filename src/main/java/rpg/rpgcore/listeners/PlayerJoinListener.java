package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(final PlayerJoinEvent e) {

        final Player p = e.getPlayer();
        final UUID playerUUID = p.getUniqueId();
        final String playerName = p.getName();

        if (!(rpgcore.getPlayerManager().getPlayers().contains(playerUUID))) {
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().createPlayer(playerName, playerUUID, "false"));
            e.setJoinMessage(Utils.firstJoinMessage(playerName));
        }

        if (
                (!(rpgcore.getPlayerManager().getPlayerLvl().containsKey(playerUUID))) ||
                        (!(rpgcore.getPlayerManager().getPlayerExp().containsKey(playerUUID)))) {

            return;
        }

        int playerLvl = rpgcore.getPlayerManager().getPlayerLvl(playerUUID);
        final double playerExp = rpgcore.getPlayerManager().getPlayerExp(playerUUID) / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);

        if (playerLvl == 0) {
            playerLvl = 1;
        }
        p.setLevel(playerLvl);
        p.setExp((float) playerExp);
        for (Player rest : Bukkit.getOnlinePlayers()) {
            rpgcore.getLvlManager().updateLvlBelowName(rest, playerName, playerLvl);
        }
        e.setJoinMessage(Utils.joinMessage(playerName));
    }


    //TODO Dokonczyc dla TempBana by Caufland
    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();

        if (rpgcore.getPlayerManager().getPlayers().contains(uuid)) {
            if (rpgcore.getPlayerManager().isBanned(uuid)) {

                final String[] banInfo = rpgcore.getPlayerManager().getPlayerBanInfo(uuid).split(";");

                try {
                    final Date teraz = new Date();
                    final Date dataWygasnieciaBana = Utils.dateFormat.parse(banInfo[2]);

                    if (teraz.after(dataWygasnieciaBana)) {
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getSQLManager().unBanPlayer(e.getUniqueId()));
                        return;
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }


                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Utils.banMessage(banInfo[0], banInfo[1], banInfo[2], banInfo[3]));
            }
        }

    }

}
