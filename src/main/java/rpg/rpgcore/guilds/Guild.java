package rpg.rpgcore.guilds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Guild implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Guild(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.guilds")) {
            player.sendMessage(Utils.permisje("rpg.guilds"));
            return false;
        }

        final UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            rpgcore.getGuildManager().listAllCommands(player);
            return false;
        }

        String tag = rpgcore.getGuildManager().getGuildTag(uuid);
        final String playerGroup = rpgcore.getPlayerManager().getPlayerGroup(player);

        if (args.length == 1){
            //TODO statystyki i sortowanie od najwyzszej
            if (tag.equals("Brak Klanu")) {
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                return false;
            }

            if (args[0].equalsIgnoreCase("info")) {
                rpgcore.getGuildManager().showInfo(tag, player);
                return false;
            }
            if (args[0].equalsIgnoreCase("panel")) {
                rpgcore.getGuildManager().showPanel(tag, player);
                return false;
            }

            if (args[0].equalsIgnoreCase("usun")) {

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return false;
                }

                this.updateGuild(tag);

                rpgcore.getGuildManager().deleteGuild(tag);
                final String toRemove = tag;
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().removeGuild(toRemove));
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cKlan &6" + toRemove + "&c zostal usuniety przez &6" + player.getName()));
                return false;
            }

            if (args[0].equalsIgnoreCase("opusc")) {

                if (rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cKapitan zawsze jako ostatni opuszcza swoj statek"));
                    return false;
                }

                rpgcore.getGuildManager().removePlayerFromGuild(tag, uuid);
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + player.getName() + "&c opuscil klan &6" + tag));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> NameTagUtil.setPlayerDisplayNameNoGuild(player, playerGroup));
                final String tagForLambda = tag;
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateOneMember(tagForLambda, player));
                return false;
            }

            if (args[0].equalsIgnoreCase("usunzastepce")) {
                rpgcore.getGuildManager().setGuildCoOwner(tag, null);
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aPomyslnie usunieto zastepce klanu"));
                return false;
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
                return false;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                tag = args[1].toUpperCase(Locale.ROOT);
                rpgcore.getGuildManager().showInfo(tag, player);
                return false;
            }
            if (args[0].equalsIgnoreCase("zapros")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return false;
                }

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuid))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem / zastepca klanu"));
                    return false;
                }

                final UUID uuidToInvite = rpgcore.getPlayerManager().getPlayerUUID(args[1]);

                if (uuidToInvite == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return false;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz zaprosic sam siebie"));
                    return false;
                }

                final Player target = Bukkit.getPlayer(uuidToInvite);

                if (!target.isOnline()) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cPodany gracz jest offline"));
                    return false;
                }

                if (rpgcore.getGuildManager().hasGuild(uuidToInvite)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz jest juz w klanie"));
                    return false;
                }
                rpgcore.getGuildManager().invitePlayer(tag, uuidToInvite, target);
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&aPomyslnie zaprosiles gracza &6" + target.getName() + " &ado tswojego klanu!"));
                return false;
            }

            if (args[0].equalsIgnoreCase("dolacz")) {
                tag = args[1].toUpperCase(Locale.ROOT);

                if (rpgcore.getGuildManager().hasGuild(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cJestes juz w klanie"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildInviteTag(uuid) == null) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie masz zaproszenia do zadnego klanu"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildInviteTag(uuid).contains(tag)) {
                    rpgcore.getGuildManager().acceptInvite(tag, uuid, player);
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&aGracz &6" + player.getName() + " &awlasnie dolaczyl do klanu &6" + tag));
                    final String tagForLambda = tag;
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> NameTagUtil.setPlayerDisplayNameGuild(player, playerGroup, tagForLambda));
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateOneMember(tagForLambda, player));
                    return false;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie masz zaproszenia od klanu &6" + tag));
                }
                return false;
            }

            if (args[0].equalsIgnoreCase("wyrzuc")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return false;
                }

                if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuid))) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem / zastepca klanu"));
                    return false;
                }

                final UUID uuidToKick = rpgcore.getPlayerManager().getPlayerUUID(args[1]);

                if (uuidToKick == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return false;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz wyrzucic samego siebie"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildOwner(tag).equals(uuidToKick)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz wyrzucic lidera klanu"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildMembers(tag).contains(uuidToKick)) {
                    rpgcore.getGuildManager().removePlayerFromGuild(tag, uuidToKick);
                    return false;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + rpgcore.getPlayerManager().getPlayerName(uuidToKick) + " &cnie jest czlonkiem twojego klanu"));
                    return false;
                }
            }

            if (args[0].equalsIgnoreCase("lider")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return false;
                }

                if (!rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return false;
                }

                final UUID uuidToSet = rpgcore.getPlayerManager().getPlayerUUID(args[1]);

                if (uuidToSet == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return false;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cJestes juz liderem tego klanu"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildMembers(tag).contains(uuidToSet)) {
                    rpgcore.getGuildManager().setGuildOwner(tag, uuidToSet);
                    if (rpgcore.getGuildManager().getGuildCoOwner(tag) != null && rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuidToSet)) {
                        rpgcore.getGuildManager().setGuildCoOwner(tag, null);
                    }
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&aGracz &6" + rpgcore.getPlayerManager().getPlayerName(uuidToSet) + " &awlasnie zostal nowym liderem klanu &6" + tag));
                    return false;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + rpgcore.getPlayerManager().getPlayerName(uuidToSet) + " &cnie jest czlonkiem twojego klanu"));
                    return false;
                }
            }

            if (args[0].equalsIgnoreCase("zastepca")) {

                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return false;
                }

                if (!rpgcore.getGuildManager().getGuildOwner(tag).equals(uuid)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie jestes zalozycielem klanu"));
                    return false;
                }

                final UUID uuidToSet = rpgcore.getPlayerManager().getPlayerUUID(args[1]);

                if (uuidToSet == null) {
                    player.sendMessage(Utils.GUILDSPREFIX + Utils.NIEMATAKIEGOGRACZA);
                    return false;
                }

                if (args[1].equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie mozesz zostac zastepca tego klanu"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildCoOwner(tag) != null && rpgcore.getGuildManager().getGuildCoOwner(tag).equals(uuidToSet)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cTen gracz jest juz zastepca twojego klanu"));
                    return false;
                }

                if (rpgcore.getGuildManager().getGuildMembers(tag).contains(uuidToSet)) {
                    rpgcore.getGuildManager().setGuildCoOwner(tag, uuidToSet);
                    rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "&aGracz &6" + rpgcore.getPlayerManager().getPlayerName(uuidToSet) + " &awlasnie zostal nowym zastepca klanu &6" + tag));
                    return false;
                } else {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cGracz &6" + rpgcore.getPlayerManager().getPlayerName(uuidToSet) + " &cnie jest czlonkiem twojego klanu"));
                    return false;
                }

            }

        }

        if (args.length > 2) {
            if (args[0].equalsIgnoreCase("zaloz")) {

                if (!tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cMasz juz klan"));
                    return false;
                }

                tag = args[1].toUpperCase(Locale.ROOT);

                if (rpgcore.getGuildManager().getListOfGuilds().contains(tag)) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cKlan o takiej nazwie juz istnieje"));
                    return false;
                }

                final StringBuilder sb = new StringBuilder();
                args[0] = "";
                args[1] = "";
                args[2] = args[2].replaceFirst(" ", "");
                for (String s : args) {
                    sb.append(s).append(" ");
                }
                final String description = sb.toString().trim();
                rpgcore.getGuildManager().createGuild(tag, description, player.getUniqueId());
                rpgcore.getServer().broadcastMessage(Utils.format(Utils.GUILDSPREFIX + "Klan &6" + tag + " - " + description + " &7zostal zalozony przez &6" + player.getName() + " &6&lGratulacje!"));
                final String tagForLambda = tag;
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> NameTagUtil.setPlayerDisplayNameGuild(player, playerGroup, tagForLambda));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> this.updateOneMember(tagForLambda, player));
                return false;
            }

            if (args[0].equalsIgnoreCase("chat")) {
                if (tag.equals("Brak Klanu")) {
                    player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&cNie posiadasz klanu"));
                    return false;
                }

                final StringBuilder sb = new StringBuilder();
                args[0] = "";

                for (String s : args) {
                    sb.append(s).append(" ");
                }

                final String message = sb.toString().trim();

                rpgcore.getGuildManager().sendMessageToGuild(tag, message, player);
                return false;
            }
        }


        rpgcore.getGuildManager().listAllCommands(player);
        return false;
    }

    private void updateGuild(final String tag) {
        final List<UUID> members = new ArrayList<>(20);
        for (final UUID uuid1 : rpgcore.getGuildManager().getGuildMembers(tag)) {
            final Player p = Bukkit.getPlayer(uuid1);
            if (p != null && p.isOnline()) {
                final String group = rpgcore.getPlayerManager().getPlayerGroup(p);
                NameTagUtil.setPlayerDisplayNameNoGuild(p, group);
                TabManager.removePlayer(p);
                members.add(uuid1);
            }
        }
        rpgcore.getGuildManager().getGuildMembers(tag).clear();

        for (UUID uuid1 : members) {
            TabManager.addPlayer(Bukkit.getPlayer(uuid1));
        }
        members.clear();
        for (Player restOfServer : Bukkit.getOnlinePlayers()) {
            TabManager.update(restOfServer.getUniqueId());
        }
    }

    private void updateOneMember(final String tag, final Player player) {
        if (player != null && player.isOnline()) {
            TabManager.removePlayer(player);
            TabManager.addPlayer(player);
            for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                TabManager.update(restOfServer.getUniqueId());
            }
        }
    }
}
