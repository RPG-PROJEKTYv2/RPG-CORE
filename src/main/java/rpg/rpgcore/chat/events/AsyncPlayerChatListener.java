package rpg.rpgcore.chat.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.guilds.Guild;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.text.ParseException;
import java.util.*;

public class AsyncPlayerChatListener implements Listener {
    private final RPGCORE rpgcore;
    public AsyncPlayerChatListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        String message = e.getMessage();
        final User user = rpgcore.getUserManager().find(uuid);
        e.setCancelled(true);
        if (!user.isHellCodeLogin() && !user.getHellCode().equals("off")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zeby pisac na chacie musisz sie najpierw zalogowac swoim HellCode. Uzyj: &c/hellcode <kod>"));
            return;
        }

        if (rpgcore.getCooldownManager().hasChatCooldown(uuid)) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nastepna wiadomosc mozesz wyslac za &c" + Utils.durationToString(rpgcore.getCooldownManager().getPlayerChatCooldown(uuid), false)));
            return;
        }

        if (rpgcore.getUserManager().find(uuid).getLvl() < 10) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Potrzebujesz przynajmniej &c10 poziom &7zeby pisac na chacie!"));
            return;
        }

        if (!rpgcore.getChatManager().isChatEnabled() && !(user.getRankUser().isStaff() && user.isAdminCodeLogin())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie &cwylaczony&7!"));
            return;
        }

        if (!rpgcore.getChatManager().getRankReqForChat().equals(RankType.GRACZ)) {
            if (user.getRankPlayerUser().getRankType().getPriority() < rpgcore.getChatManager().getRankReqForChat().getPriority()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od rangi &6" + rpgcore.getChatManager().getRankReqForChat().getName() + "&7!"));
                return;
            }
        }

        if (rpgcore.getChatManager().getLvlReqForChat() != 1) {
            if (user.getLvl() < rpgcore.getChatManager().getLvlReqForChat()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Chat jest aktualnie wlaczony od poziomu &6" + rpgcore.getChatManager().getLvlReqForChat() + "&7!"));
                return;
            }
        }

        if ((user.isMuted() && !user.getRankUser().isHighStaff()) || (user.isMuted() && user.getRankUser().isHighStaff() && !user.isAdminCodeLogin())) {
            final String[] muteInfo = user.getMuteInfo().split(";");
            if (muteInfo[2].equalsIgnoreCase("Permamentny")) {
                Utils.youAreMuted(player, muteInfo[0], muteInfo[1], muteInfo[2]);
                return;
            }
            try {
                final Date teraz = new Date();
                final Date dataMuta = Utils.dateFormat.parse(muteInfo[2]);

                if (teraz.after(dataMuta)) {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().unMutePlayer(player.getUniqueId()));
                } else {
                    Utils.youAreMuted(player, muteInfo[0], muteInfo[1], Utils.convertDatesToTimeLeft(dataMuta, teraz));
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
                return;
            }
            return;
        }


        if (message.isEmpty()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz wyslac pustej wiadomosci!"));
            return;
        }
        if (message.length() > 2) {
            if (Utils.removeColor(message).isEmpty()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz wyslac wiadomosci z samymi kolorami!"));
                return;
            }
            if (message.startsWith("www") || message.startsWith("http") || message.startsWith("https")) {
                if (!(user.getRankUser().isStaff() && user.isAdminCodeLogin())) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie mozesz wyslac wiadomosci z linkiem!"));
                    return;
                }
            }
        }
        formatuj(player, message);
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            return;
        }
        rpgcore.getCooldownManager().givePlayerChatCooldown(uuid);
    }

    private void formatuj(final Player player, final String message) {
        if (message.contains("[eq]") || message.contains("[i]") ||message.contains("[item]")) {
            rpgcore.getChatManager().updateMessageWithEQ(player.getUniqueId(), message);
            rpgcore.getChatManager().formatujChatEQ(player);
            return;
        }
        final TextComponent main = new TextComponent("");
        if (message.contains("@")) {
            rpgcore.getChatManager().pingPlayer(player, message);
        }
        if (rpgcore.getGuildManager().hasGuild(player.getUniqueId())) {
            final String tag = rpgcore.getGuildManager().getGuildTag(player.getUniqueId());
            final Guild guild = rpgcore.getGuildManager().find(tag).getGuild();
            final TextComponent guildM = new TextComponent(Utils.format("&8[&e" + tag + "&8] "));
            guildM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                    new TextComponent(Utils.format("&7Tag gildii: &6" + tag +
                            "\n&7Opis gildi: &6" + guild.getDescription() +
                            "\n&7Lider Gildii: &6" + (Bukkit.getPlayer(guild.getOwner()) != null ? "&a" + Bukkit.getPlayer(guild.getOwner()).getName() : "&c" + Bukkit.getOfflinePlayer(guild.getOwner()).getName()) +
                            "\n&7Ilosc czlonkow: &6" + guild.getMembers().size() + "&7/&615" +
                            "\n&7Poziom: &6" + guild.getLevel() +
                            "\n&7Doswiadczenie: &6" + String.format("%.2f", Utils.convertDoublesToPercentage(guild.getExp(), rpgcore.getGuildManager().getGuildNextLvlExp(tag))) + "%"))}));
            guildM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/klan info " + tag));
            main.addExtra(guildM);
        }
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        final TextComponent lvl = (user.getLvl() == 130 ? new TextComponent(Utils.format("&8[&bLvl. &4&lMAX&8] ")) : new TextComponent(Utils.format("&8[&bLvl. &f" + user.getLvl() + "&8] ")));
        lvl.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent(Utils.format("&7Poziom: &6" + user.getLvl() +
                        "\n&7Postep do nastepnego poziomu: &6" + DoubleUtils.round((user.getExp() * rpgcore.getLvlManager().getExpForLvl(user.getLvl())) / 100, 2)  + "%"))}));
        main.addExtra(lvl);

        TextComponent rank;
        if (user.getRankUser().isStaff() && user.isAdminCodeLogin()) {
            rank = new TextComponent(Utils.format(user.getRankUser().getRankType().getPrefix() + player.getName() + "&f: " + message));
        } else {
            rank = new TextComponent(Utils.format(user.getRankPlayerUser().getRankType().getPrefix() + player.getName() + "&f: " + Utils.removeColor(message)));
        }
        rank.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Utils.format("&7Kliknij, zeby napisac wiadomosc"))}));
        rank.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " "));
        main.addExtra(rank);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(main);
        }
    }

    final List<String> ownCommandsPlayer = Arrays.asList(
            "administracja", "admin", "adm",
            "hellcode", "hc", "admincode", "ac", "code",
            "spawn",
            "bossy",
            "pomoc", "help",
            "lvl", "os", "osiagniecia",
            "message", "msg", "pv" ,"pw", "m", "reply", "r",
            "targ", "ah", "gielda",
            "ec", "enderchest",
            "kasa", "money", "bal", "balance", "wyplac", "withdraw", "wystaw", "sprawdz", "helpop",
            "guild", "g", "klan", "gildia", "kosz", "chatpanel", "panel", "chatp", "party", "p",
            "pety", "pets", "ignore", "ignoruj", "dodatki", "bony", "akce", "akcesoria", "ekwipunek",
            "profile", "magazyny", "magazyn", "mag", "ranktime", "czasrangi", "pd", "piersciendoswiadczenia",
            "artefakty", "craft", "rozpiska", "crafting", "listanpc", "lnpc", "npc", "poziom", "level",
            "showcaseitem", "profil", "staty", "hs", "stats", "hellsy", "statystyki", "coins", "online", "k",
            "misje", "list", "lista", "gracze", "onlinelist", "listagraczy", "craftingi", "rangi", "vip", "elita",
            "topki", "top", "ping", "tower", "dt", "demontower", "live", "gamma", "nv", "nightvision", "icetower", "gornikzaplac", "kontakt", "socjale", "www", "strona", "discord", "dc", "facebook", "fb"
    );
    final List<String> ownCommandsAdmin = Arrays.asList(
            "adminpanel", "paneladmin",
            "ban", "kick", "tempban", "unban", "ub",
            "dodatkowyexp",
            "god", "g",
            "serwerwhitelist", "swl", "wls", "servwerwl",
            "teleport", "tp",
            "ustawieniakonta", "konto", "ustawienia",
            "vanish", "v", "van", "vs",
            "adminchat", "adc", "achat", "adminc",
            "admincode", "ac",
            "back",
            "broadcast", "bc", "alert", "title", "ogloszenie",
            "changeleashrange",
            "clear",
            "disable", "wylacz",
            "enchant", "ench",
            "enchantcustom", "cenchant", "enchc",
            "fly",
            "giveakcesoria", "akcedaj", "dajakce", "giveakce",
            "gm", "gamemode",
            "heal",
            "itemshop", "sklep", "is",
            "mem", "ram", "memory",
            "removenearbyentities",
            "resetdungeon",
            "rozdaj", "losuj", "losowanie",
            "savestop", "pause", "stop",
            "setadminrank", "admin", "setadmin", "setrank",
            "setdmg",
            "name", "setname",
            "setpremium", "spremium", "setp", "setpremium", "premium", "tworca",
            "speed",
            "testanimation",
            "test",
            "drop"
    );

    @EventHandler
    public void onExecutingCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        final String command = message.split(" ")[0].replace("/", "");
        final User user = this.rpgcore.getUserManager().find(player.getUniqueId());
        final HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(message.split(" ")[0]);
        if (!user.getRankUser().isHighStaff() || (user.getRankUser().isHighStaff() && !user.isAdminCodeLogin())) {
            if (!ownCommandsPlayer.contains(command.toLowerCase()) && !ownCommandsAdmin.contains(command.toLowerCase())) {
                event.setCancelled(true);
                player.sendMessage(Utils.format("&8[&4!&8] &cKomenda nie istnieje lub nie masz do niej uprawnien!"));
                return;
            }
        }
        if (topic == null) {
            event.setCancelled(true);
            player.sendMessage(Utils.format("&8[&4!&8] &cKomenda nie istnieje lub nie masz do niej uprawnien!"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(final PlayerChatTabCompleteEvent e) {
        if (e.getChatMessage().contains("/")) {
            final User user = this.rpgcore.getUserManager().find(e.getPlayer().getUniqueId());
            if (!user.getRankUser().isHighStaff() || (user.getRankUser().isHighStaff() && !user.isAdminCodeLogin())) {
                e.getTabCompletions().clear();
            }
        }
    }
}
