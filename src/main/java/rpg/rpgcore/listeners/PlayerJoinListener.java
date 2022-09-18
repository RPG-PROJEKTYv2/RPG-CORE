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
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerJoinListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinWhiteList(final PlayerLoginEvent e) {
        if (!e.getPlayer().isWhitelisted() && Bukkit.hasWhitelist()) {
            String msg = Utils.getWhiteListMessage().replace("@p", e.getPlayer().getName());
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getPlayer().kickPlayer(msg), 1L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(final PlayerJoinEvent e) {
        if (!rpgcore.getUserManager().isUser(e.getPlayer().getUniqueId())) {
            if (rpgcore.getUserManager().isUserName(e.getPlayer().getName())) {
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> e.getPlayer().kickPlayer(Utils.format(Utils.SERVERNAME + "\n&c&lCos poszlo nie tak! :(\n&\n&8Skontaktuj Sie z &4Administracja &8z ss'em tego bledu.\n&8(&c&lKod Bledu: #USER_ALREADY_EXISTING_DATABASE&8)")), 1L);
                return;
            }
            final Player player = e.getPlayer();
            final UUID uuid = player.getUniqueId();

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getEnderChest().clear();

            player.getInventory().addItem(ItemHelper.createArmor("&8Helm Poczatkujacego", Material.LEATHER_HELMET, 1, 0, true, false));
            player.getInventory().addItem(ItemHelper.createArmor("&8Zbroja Poczatkujacego", Material.LEATHER_CHESTPLATE, 1, 0, true, false));
            player.getInventory().addItem(ItemHelper.createArmor("&8Spodnie Poczatkujacego", Material.LEATHER_LEGGINGS, 1, 0, true, false));
            player.getInventory().addItem(ItemHelper.createArmor("&8Buty Poczatkujacego", Material.LEATHER_BOOTS, 1, 0, true, false));
            player.getInventory().addItem(ItemHelper.createSword("&7Startowa Maczeta", Material.STONE_SWORD, 1, 0, true, false));
            rpgcore.getMongoManager().createPlayer(player, uuid, player.getName());

            player.setLevel(1);
            player.setExp(0);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.kickPlayer(Utils.format(Utils.SERVERNAME + "\n&aPomyslnie stworzono twoje konto!\n&aWejdz Jeszcze Raz i daj sie wciagnac w emocjonujaca rywalizacje")), 1L);
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
        user.setHellCodeLogin(false);
        user.setAdminCodeLogin(false);
        try {
            if (user.getInventoriesUser().getArmor() != null && user.getInventoriesUser().getInventory() != null && user.getInventoriesUser().getEnderchest() != null
                    && !user.getInventoriesUser().getInventory().isEmpty() && !user.getInventoriesUser().getEnderchest().isEmpty() && !user.getInventoriesUser().getArmor().isEmpty()) {
                p.getInventory().setArmorContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getArmor()));
                p.getInventory().setContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getInventory()));
                p.getEnderChest().setContents(Utils.itemStackArrayFromBase64(user.getInventoriesUser().getEnderchest()));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        rpgcore.getBackupManager().savePlayer(p, playerUUID);


        final int playerLvl = user.getLvl();
        final double playerExp = user.getExp() / rpgcore.getLvlManager().getExpForLvl(playerLvl + 1);

        p.setLevel(playerLvl);
        p.setExp((float) playerExp);
        for (Player rest : Bukkit.getOnlinePlayers()) {
            rpgcore.getLvlManager().updateLvlBelowName(rest, playerName, playerLvl);
        }


        //TODO Zrobic ladowanie z klasy BONUSES dodatkowegohp
        p.setMaxHealth(rpgcore.getBonusesManager().find(playerUUID).getBonusesUser().getDodatkowehp() * 2);
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


        e.setJoinMessage(null);
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(p, rpgcore.getNmsManager().makeTitle("&fWitaj na &4Hell&8RPG&f!", 5, 20, 5), rpgcore.getNmsManager().makeSubTitle("", 5, 20, 5)));
        if (!p.hasPlayedBefore()) {
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aWitamy Cie, na serwerze typu &6metin2 &aw minecraft. Pod &6/pomoc &aznajdziesz najwazniejsze informacje i przydatne komendy."));
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aZachecamy tez do dolaczenia na nasz server discord &6dc.hellrpg.pl &ana ktorym znajdziecie giveaway'e, informacje o eventach, nadchodzacych aktualizacjach oraz kanaly pomocy."));
                p.sendMessage(Utils.format(Utils.SERVERNAME + "&aZyczymy milej gry i udanej rywalizacji! &cZespol Hellrpg.pl"));
            }, 20L);

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
