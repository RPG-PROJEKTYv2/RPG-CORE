package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJooinWhiteList(final PlayerLoginEvent e) {
        if (!e.getPlayer().isWhitelisted() && Bukkit.hasWhitelist()) {
            String msg = Utils.getWhiteListMessage().replace("@p", e.getPlayer().getName());
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getPlayer().kickPlayer(msg), 1L);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinListener(final PlayerJoinEvent e) {

        final Player p = e.getPlayer();
        final UUID playerUUID = p.getUniqueId();
        final String playerName = p.getName();

        if (!rpgcore.getUserManager().isUser(playerUUID)) {
            p.kickPlayer(Utils.format(Utils.SERVERNAME + "\n&cCos poszl nie tak! :(\n&4Jak najszybciej skontaktuj sie z administacja z ss'em tej wiadomosci\n&4&lKod Bledu: (#999NULL001)"));
            return;
        }
        final User user = rpgcore.getUserManager().find(playerUUID);
        rpgcore.getBackupManager().savePlayer(p, playerUUID);

        p.setMaxHealth(20);

        final int playerLvl = user.getLvl();
        final double playerExp = user.getExp() / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);

        p.setLevel(playerLvl);
        p.setExp((float) playerExp);
        for (Player rest : Bukkit.getOnlinePlayers()) {
            rpgcore.getLvlManager().updateLvlBelowName(rest, playerName, playerLvl);
        }

        if (rpgcore.getBaoManager().find(playerUUID).getBaoUser().getBonus5().equalsIgnoreCase("dodatkowe hp")) {
            p.setMaxHealth(p.getMaxHealth() + (rpgcore.getBaoManager().find(playerUUID).getBaoUser().getValue5() * 2));
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

        final String playerGroup = rpgcore.getUserManager().getPlayerGroup(p);

        if (rpgcore.getGuildManager().hasGuild(playerUUID)) {
            final String tag = rpgcore.getGuildManager().getGuildTag(playerUUID);
            NameTagUtil.setPlayerDisplayNameGuild(p, playerGroup, tag);
            rpgcore.getGuildManager().getGuildLastOnline(tag).remove(playerUUID);
        } else {
            NameTagUtil.setPlayerDisplayNameNoGuild(p, playerGroup);
        }


        e.setJoinMessage(Utils.joinMessage(playerName));
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(p, rpgcore.getNmsManager().makeTitle("&fWitaj na &4Hell&8RPG&f!", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("", 5, 20, 5)));
        if (!p.hasPlayedBefore()) {
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWitamy Cie, na serwerze typu &6metin2 &aw minecraft. Pod &6/pomoc &aznajdziesz najwazniejsze informacje i przydatne komendy."));
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aZachecamy tez do dolaczenia na nasz server discord &6dc.hellrpg.pl &ana ktorym znajdziecie giveaway'e, informacje o eventach, nadchodzacych aktualizacjach oraz kanaly pomocy."));
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aZyczymy milej gry i udanej rywalizacji! &cZespol Hellrpg.pl"));
            }, 20L);
            p.getInventory().addItem(ItemHelper.createArmor("&8Helm Poczatkujacego", Material.LEATHER_HELMET, 2, 0, true, false));
            p.getInventory().addItem(ItemHelper.createArmor("&8Zbroja Poczatkujacego", Material.LEATHER_CHESTPLATE, 2, 0, true, false));
            p.getInventory().addItem(ItemHelper.createArmor("&8Spodnie Poczatkujacego", Material.LEATHER_LEGGINGS, 2, 0, true, false));
            p.getInventory().addItem(ItemHelper.createArmor("&8Buty Poczatkujacego", Material.LEATHER_BOOTS, 2, 0, true, false));
            p.getInventory().addItem(ItemHelper.createSword("&7Startowa Maczeta", Material.STONE_SWORD, 5, 1, true, false));
        }
        p.teleport(rpgcore.getSpawnManager().getSpawn());

        TabManager.addPlayer(p);
        TabManager.add(p);
        TabManager.update(p.getUniqueId());
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLoginListener(final AsyncPlayerPreLoginEvent e) {
        final UUID uuid = e.getUniqueId();

        if (!rpgcore.getUserManager().isUser(uuid)) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utils.format(Utils.SERVERNAME + "\n&cCos poszl nie tak! :(\n&4Jak najszybciej skontaktuj sie z administacja z ss'em tej wiadomosci\n&4&lKod Bledu: (#999NULL000)"));
            return;
        }

        final User user = rpgcore.getUserManager().find(uuid);

        if (user.isBanned()) {

            final String[] banInfo = user.getBanInfo().split(";");

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
