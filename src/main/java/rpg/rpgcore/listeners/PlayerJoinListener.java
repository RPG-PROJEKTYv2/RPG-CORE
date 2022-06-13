package rpg.rpgcore.listeners;

import com.keenant.tabbed.Tabbed;
import com.keenant.tabbed.item.TabItem;
import com.keenant.tabbed.item.TextTabItem;
import com.keenant.tabbed.tablist.TableTabList;
import com.keenant.tabbed.tablist.TitledTabList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        p.setMaxHealth(20);

        int playerLvl;
        double playerExp;
        if (!(rpgcore.getPlayerManager().getPlayerLvl().containsKey(playerUUID)) || !(rpgcore.getPlayerManager().getPlayerExp().containsKey(playerUUID))) {
            playerLvl = 1;
            playerExp = 0 / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);
        } else {
            playerLvl = rpgcore.getPlayerManager().getPlayerLvl(playerUUID);
            playerExp = rpgcore.getPlayerManager().getPlayerExp(playerUUID) / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);
        }

        if (playerLvl == 0) {
            playerLvl = 1;
        }
        p.setLevel(playerLvl);
        p.setExp((float) playerExp);
        for (Player rest : Bukkit.getOnlinePlayers()) {
            rpgcore.getLvlManager().updateLvlBelowName(rest, playerName, playerLvl);
        }

        if (rpgcore.getBaoManager().getBaoBonusy(playerUUID) != null && rpgcore.getBaoManager().getBaoBonusy(playerUUID).split(",")[4].equalsIgnoreCase("dodatkowe hp")) {
            p.setMaxHealth(p.getMaxHealth() + Double.parseDouble(rpgcore.getBaoManager().getBaoBonusyWartosci(playerUUID).split(",")[4]) * 2);
        }

        if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(playerUUID) != null) {
            if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(playerUUID).getItem(13).getType() != Material.BARRIER) {
                p.setMaxHealth(p.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 13, "Dodatkowe HP") * 2);
            }

            if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(playerUUID).getItem(14).getType() != Material.BARRIER) {
                p.setMaxHealth(p.getMaxHealth() + (double) rpgcore.getAkcesoriaManager().getAkcesoriaBonus(playerUUID, 14, "Dodatkowe HP") * 2);
            }

            rpgcore.getAkcesoriaManager().loadAllAkceBonus(playerUUID);
        }

        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);

        final String playerGroup = rpgcore.getPlayerManager().getPlayerGroup(p);

        if (rpgcore.getGuildManager().hasGuild(playerUUID)) {
            final String tag = rpgcore.getGuildManager().getGuildTag(playerUUID);
            NameTagUtil.setPlayerDisplayNameGuild(p, playerGroup, tag);
        } else {
            NameTagUtil.setPlayerDisplayNameNoGuild(p, playerGroup);
        }

        e.setJoinMessage(Utils.joinMessage(playerName));
        p.teleport(rpgcore.getSpawnManager().getSpawn());
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();

        if (!(rpgcore.getPlayerManager().getPlayers().contains(uuid))) {
            Bukkit.getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().createPlayer(e.getName(), uuid, "false", "false"));
        }

        if (rpgcore.getPlayerManager().getPlayers().contains(uuid)) {
            if (rpgcore.getPlayerManager().isBanned(uuid)) {

                final String[] banInfo = rpgcore.getPlayerManager().getPlayerBanInfo(uuid).split(";");

                try {
                    final Date teraz = new Date();
                    final Date dataWygasnieciaBana = Utils.dateFormat.parse(banInfo[2]);

                    if (teraz.after(dataWygasnieciaBana)) {
                        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().unBanPlayer(e.getUniqueId()));
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
