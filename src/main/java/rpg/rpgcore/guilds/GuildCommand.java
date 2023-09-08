package rpg.rpgcore.guilds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.*;

public class GuildCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public GuildCommand(RPGCORE rpgcore) {
        super("guild");
        this.setAliases(Arrays.asList("klan", "g", "gildia"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;

        if (args.length < 1) {
            rpgcore.getGuildManager().listAllCommands(player);
            return;
        }
        final UUID uuid = player.getUniqueId();
        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        String tag = rpgcore.getGuildManager().getGuildTag(uuid);

        if (args.length == 1) {

            if (args[0].equals("zaloz")) {
                player.sendMessage(Utils.poprawneUzycie("klan zaloz <tag> <opis>"));
                return;
            }

            if (tag.equals("Brak Klanu")) {
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                rpgcore.getGuildManager().showInfo(tag, player);
                return;
            }
            if (args[0].equalsIgnoreCase("panel")) {
                rpgcore.getGuildManager().showPanel(tag, player);
                return;
            }

            if (args[0].equalsIgnoreCase("usun")) {

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return;
                }

                this.updateGuild(tag);

                rpgcore.getGuildManager().deleteGuild(tag);
                final String toRemove = tag;
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().removeDataGuild(toRemove));
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cKlan &6" + toRemove + "&c zostal usuniety przez &6" + player.getName()));
                return;
            }

            if (args[0].equalsIgnoreCase("opusc")) {

                if (rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cKapitan zawsze jako ostatni opuszcza swoj statek"));
                    return;
                }

                rpgcore.getGuildManager().removePlayerFromGuild(tag, uuid);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + player.getName() + "&c opuscil klan &6" + tag));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> NameTagUtil.setPlayerNameTag(player, "updatePrefix"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateOneMember(player));
                return;
            }

            if (args[0].equalsIgnoreCase("usunzastepce")) {
                if (!rpgcore.getGuildManager().find(tag).getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem tego klanu!"));
                    return;
                }
                rpgcore.getGuildManager().setGuildCoOwner(tag, "");
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aPomyslnie usunieto zastepce klanu"));
                return;
            }

            if (args[0].equalsIgnoreCase("pvp")) {

                final boolean pvpStatus = rpgcore.getGuildManager().getGuildPvPStatus(tag);

                if (pvpStatus) {
                    rpgcore.getGuildManager().setGuildPvPStatus(tag, false);
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cWylaczono &7pvp w klanie"));
                } else {
                    rpgcore.getGuildManager().setGuildPvPStatus(tag, true);
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aWlaczono pvp w klanie"));
                }
                return;
            }

            if (args[0].equalsIgnoreCase("stats")) {
                rpgcore.getGuildManager().showGuildStats(tag, player);
                return;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                tag = args[1].toUpperCase(Locale.ROOT);
                rpgcore.getGuildManager().showInfo(tag, player);
                return;
            }
            if (args[0].equalsIgnoreCase("zapros")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return;
                }

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuid.toString()))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem / zastepca klanu"));
                    return;
                }

                final UUID uuidToInvite = rpgcore.getUserManager().find(args[1]).getId();

                if (uuidToInvite == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz zaprosic sam siebie"));
                    return;
                }

                final Player target = Bukkit.getPlayer(uuidToInvite);

                if (!target.isOnline()) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cPodany gracz jest offline"));
                    return;
                }

                if (rpgcore.getGuildManager().hasGuild(uuidToInvite)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz jest juz w klanie"));
                    return;
                }
                rpgcore.getGuildManager().invitePlayer(tag, uuidToInvite, target);
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aPomyslnie zaprosiles gracza &6" + target.getName() + " &ado tswojego klanu!"));
                return;
            }

            if (args[0].equalsIgnoreCase("dolacz")) {

                if (user.getLvl() < 50) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego poziomu, zeby dolaczyc do klau &8(50lvl)"));
                    return;
                }

                if (user.getKasa() < 25000000) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wystarczajacej ilosci pieniedzy, zeby dolaczyc do klau &8(25.000.000$)"));
                    return;
                }

                tag = args[1].toUpperCase(Locale.ROOT);

                if (rpgcore.getGuildManager().hasGuild(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cJestes juz w klanie"));
                    return;
                }

                if (rpgcore.getGuildManager().getGuildInviteTag(uuid).isEmpty()) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie masz zaproszenia do zadnego klanu"));
                    return;
                }

                if (rpgcore.getGuildManager().getGuildInviteTag(uuid).contains(tag)) {
                    if (rpgcore.getGuildManager().getGuildMembers(tag).size() >= 15) {
                        player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cKlan jest juz pelny"));
                        return;
                    }
                    user.setKasa(user.getKasa() - 25000000);
                    rpgcore.getGuildManager().acceptInvite(tag, uuid, player);
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&aGracz &6" + player.getName() + " &awlasnie dolaczyl do klanu &6" + tag));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                        NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                        this.updateOneMember(player);
                        rpgcore.getMongoManager().saveDataUser(user.getId(), user);
                    });
                    return;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie masz zaproszenia od klanu &6" + tag));
                }
                return;
            }

            if (args[0].equalsIgnoreCase("wyrzuc")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return;
                }

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuid.toString()))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem / zastepca klanu"));
                    return;
                }

                final UUID uuidToKick = rpgcore.getUserManager().find(args[1]).getId();

                if (uuidToKick == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz wyrzucic samego siebie"));
                    return;
                }

                if (rpgcore.getGuildManager().getGuildOwner(tag).equals(uuidToKick)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz wyrzucic lidera klanu"));
                    return;
                }

                if (rpgcore.getGuildManager().getGuildMembers(tag).contains(uuidToKick)) {
                    rpgcore.getGuildManager().removePlayerFromGuild(tag, uuidToKick);
                    return;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + rpgcore.getUserManager().find(uuidToKick).getName() + " &cnie jest czlonkiem twojego klanu"));
                    return;
                }
            }

            if (args[0].equalsIgnoreCase("zastepca")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return;
                }

                if (!rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return;
                }

                final UUID uuidToSet = rpgcore.getUserManager().find(args[1]).getId();

                if (uuidToSet == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz zostac zastepca tego klanu"));
                    return;
                }

                if (!rpgcore.getGuildManager().getGuildCoOwner(tag).isEmpty() && rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuidToSet.toString())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cTen gracz jest juz zastepca twojego klanu"));
                    return;
                }

                if (rpgcore.getGuildManager().getGuildMembers(tag).contains(uuidToSet)) {
                    rpgcore.getGuildManager().setGuildCoOwner(tag, uuidToSet.toString());
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&aGracz &6" + rpgcore.getUserManager().find(uuidToSet).getName() + " &awlasnie zostal nowym zastepca klanu &6" + tag));
                    return;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + rpgcore.getUserManager().find(uuidToSet).getName() + " &cnie jest czlonkiem twojego klanu"));
                    return;
                }

            }

        }

        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("zaloz")) {

                if (user.getLvl() < 75) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymagannego poziomu, zeby zalozyc swoj klan! &8(75lvl)"));
                    return;
                }

                if (user.getKasa() < 100000000) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wystarczajacej ilosci pieniedzy, zeby zalozyc swoj klan! &8(100.000.000$)"));
                    return;
                }

                if (!tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cMasz juz klan"));
                    return;
                }

                tag = args[1].toUpperCase(Locale.ROOT);

                if (rpgcore.getGuildManager().getListOfGuilds().contains(tag)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cKlan o takiej nazwie juz istnieje"));
                    return;
                }

                if (args.length < 3) {
                    player.sendMessage(Utils.poprawneUzycie("klan zaloz <tag> <opis>"));
                    return;
                }

                final StringBuilder sb = new StringBuilder();
                args[0] = "";
                args[1] = "";
                args[2] = args[2].replaceFirst(" ", "");
                for (String s : args) {
                    sb.append(s).append(" ");
                }
                final String description = sb.toString().trim();
                user.setKasa(user.getKasa() - 100000000);
                rpgcore.getGuildManager().createGuild(tag, description, player.getUniqueId());
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&7Klan &6" + tag + " - " + description + " &7zostal zalozony przez &6" + player.getName() + " &6&lGratulacje!"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    NameTagUtil.setPlayerNameTag(player, "updatePrefix");
                    this.updateOneMember(player);
                    rpgcore.getMongoManager().saveDataUser(user.getId(), user);
                });
                return;
            }

            if (args[0].equalsIgnoreCase("chat")) {
                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return;
                }

                final StringBuilder sb = new StringBuilder();
                args[0] = "";

                for (String s : args) {
                    sb.append(s).append(" ");
                }

                final String message = sb.toString().trim();

                rpgcore.getGuildManager().sendMessageToGuild(tag, message, player);
            }
            if (args[0].equalsIgnoreCase("alert")) {
                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return;
                }

                if (!rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return;
                }

                final StringBuilder sb = new StringBuilder();
                args[0] = "";

                for (String s : args) {
                    sb.append(s).append(" ");
                }

                final String message = sb.toString().trim();

                rpgcore.getGuildManager().sendAlertToGuild(tag, message);
            }
        }
    }

    private void updateGuild(final String tag) {
        final List<UUID> members = new ArrayList<>(20);
        for (final UUID uuid1 : rpgcore.getGuildManager().getGuildMembers(tag)) {
            final Player p = Bukkit.getPlayer(uuid1);
            if (p != null && p.isOnline()) {
                NameTagUtil.setPlayerNameTag(p, "updatePrefix");
                TabManager.removePlayer(p);
                members.add(uuid1);
            }
        }
        rpgcore.getGuildManager().getGuildMembers(tag).clear();

        for (UUID uuid1 : members) {
            if (Bukkit.getPlayer(uuid1) != null && Bukkit.getPlayer(uuid1).isOnline()) {
                TabManager.addPlayer(Bukkit.getPlayer(uuid1));
            }
        }
        members.clear();
        for (Player restOfServer : Bukkit.getOnlinePlayers()) {
            TabManager.update(restOfServer.getUniqueId());
        }
    }

    private void updateOneMember(final Player player) {
        if (player != null && player.isOnline()) {
            TabManager.removePlayer(player);
            TabManager.addPlayer(player);
            for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                TabManager.update(restOfServer.getUniqueId());
            }
        }
    }
}
