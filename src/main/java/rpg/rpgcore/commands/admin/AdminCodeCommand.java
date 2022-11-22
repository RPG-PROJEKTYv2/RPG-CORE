package rpg.rpgcore.commands.admin;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.tab.TabManager;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.NameTagUtil;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AdminCodeCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public AdminCodeCommand(RPGCORE rpgcore) {
        super("admincode");
        this.setRankLevel(RankType.JUNIORHELPER);
        this.setAliases(Arrays.asList("ac"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Player player = (Player) sender;

        if (!rpgcore.getUserManager().isUser(player.getUniqueId())) {
            player.kickPlayer(Utils.format(Utils.SERVERNAME + "\nCos poszlo nie tak :(\n&8Skontaktuj sie z &cadministracja &8z ss'em tej wiadomosci.\n&8Kod Bledu: &c#ADMINCODE_USER_NOT_FOUND?"));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("admincode <stworz/zmien/twoj kod> [kod]"));
            return;
        }
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        if (args[0].equalsIgnoreCase("stworz")) {
            if (args.length < 3 || args[1] == null || args[2] == null) {
                player.sendMessage(Utils.poprawneUzycie("admincode stworz <kod> <kod>"));
                return;
            }

            if (!args[1].equals(args[2])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podane AdminCodey nie sa takie same!"));
                return;
            }

            if (args[1].length() < 4 || args[1].length() > 16) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode musi miec od 4 do 16 znakow!"));
                return;
            }

            if (!user.getAdminCode().isEmpty()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Posiadasz juz AdminCode!"));
                return;
            }

            if (user.getHellCode().equals(args[1])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode jest taki sam jak HellCode!"));
                return;
            }

            user.setAdminCode(args[1]);
            user.setAdminCodeLogin(false);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPomyslnie ustawiles AdminCode na &6: "));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[1] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[1])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));

            RPGCORE.getDiscordBot().sendChannelMessage("admincode-logs", EmbedUtil.create("**AdminCode Create**",
                    "**Gracz:** `" + player.getName() + "` **stworzyl swoj AdminCode!**\n"
                            + "**Ranga:** " + user.getRankUser().getRankType().getName() + "\n"
                            + "**AdminCode: **||" + args[2] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.red));
            return;
        }
        if (args[0].equalsIgnoreCase("zmien")) {

            if (user.getAdminCode().isEmpty()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpier stworzyc AdminCode, zeby moc go zmienic!"));
                return;
            }

            if (!user.isAdminCodeLogin()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw zalogowac sie AdminCode, zeby uzyc tej komendy!"));
                return;
            }

            if (args.length < 4 || args[1] == null || args[2] == null || args[3] == null) {
                player.sendMessage(Utils.poprawneUzycie("admincode zmien <stary kod> <nowy kod> <nowy kod>"));
                return;
            }

            if (!user.getAdminCode().equals(args[1])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode jest nieprawidlowy!"));
                return;
            }

            if (args[2].length() < 4 || args[2].length() > 16) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode musi miec od 4 do 16 znakow!"));
                return;
            }

            if (!args[2].equals(args[3])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podane AdminCode'y nie sa takie same!"));
                return;
            }

            if (user.getHellCode().equals(args[2])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode jest taki sam jak HellCode!"));
                return;
            }

            if (user.getAdminCode().equalsIgnoreCase(args[2])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode jest taki sam jak poprzedni!"));
                return;
            }

            user.setAdminCode(args[2]);
            user.setAdminCodeLogin(false);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles AdminCode na &6: "));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[2] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[2])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            RPGCORE.getDiscordBot().sendChannelMessage("admincode-logs", EmbedUtil.create("**AdminCode Change**",
                    "**Gracz:** `" + player.getName() + "` **zmienil swoj AdminCode!**\n"
                            + "**Ranga:** " + user.getRankUser().getRankType().getName() + "\n"
                            + "**Stary AdminCode: **||" + args[1] + "||\n"
                            + "**Nowy AdminCode: **||" + args[2] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.red));

        }

        if (user.isAdminCodeLogin()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Juz jestes zalogowany!"));
            return;
        }

        if (user.getAdminCode().equals(args[0])) {
            user.setAdminCodeLogin(true);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPommyslnie zalogowano AdminCode: &6"));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[0] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[0])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            RPGCORE.getDiscordBot().sendChannelMessage("admincode-logs", EmbedUtil.create("**AdminCode LogIn**",
                    "**Gracz:** `" + player.getName() + "` **zalogowal sie swoim hellcode!**\n"
                            + "**Kod: **||" + args[0] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.GREEN));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                rpgcore.getMongoManager().saveDataUser(user.getId(), user);
                NameTagUtil.setPlayerNameTag(Bukkit.getPlayer(user.getId()), "updatePrefix");
                TabManager.removePlayer(Bukkit.getPlayer(user.getId()));
                TabManager.addPlayer(Bukkit.getPlayer(user.getId()));
                for (Player restOfServer : Bukkit.getOnlinePlayers()) {
                    TabManager.update(restOfServer.getUniqueId());
                }
            });
        } else {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany AdminCode jest nieprawidlowy!"));
        }
    }
}
