package rpg.rpgcore.commands.player.hellcode;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HellCodeCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public HellCodeCommand(final RPGCORE rpgcore) {
        super("hellcode");
        this.setRankLevel(RankType.GRACZ);
        this.setAliases(Arrays.asList("code", "hc"));
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    private final List<String> obvPass = Arrays.asList("gowno", "dupa", "chuj", "haslo", "essa", "1234", "12345", "54321", "4321", "cycki", "pizda", "code", "kodzik", "kod1234");

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

        final Player player = (Player) sender;

        if (!rpgcore.getUserManager().isUser(player.getUniqueId())) {
            player.kickPlayer(Utils.format(Utils.SERVERNAME + "\nCos poszlo nie tak :(\n&8Skontaktuj sie z &cadministracja &8z ss'em tej wiadomosci.\n&8Kod Bledu: &c#HELLCODE_USER_NOT_FOUND?"));
            return;
        }

        if (rpgcore.getUserManager().find(player.getUniqueId()).getHellCode().isEmpty() && args.length == 0) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw stworzyc swoj hellcode! Uzyj: &c/hellcode stworz <kod> <kod>"));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(Utils.poprawneUzycie("hellcode <stworz/zmien/wylacz/twoj kod> [kod]"));
            return;
        }

        final User user = rpgcore.getUserManager().find(player.getUniqueId());

        if (args[0].equals("przypomnij")) {
            if (args.length < 2) {
                player.sendMessage(Utils.poprawneUzycie("hellcode przypomnij <nick>"));
                return;
            }
            if (user.getRankUser().isHighStaff() && user.isAdminCodeLogin()) {
                final User target = rpgcore.getUserManager().find(args[1]);

                if (target == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku: &7" + args[1]));
                    return;
                }

                final Player targetPlayer = Bukkit.getPlayer(target.getId());
                if (targetPlayer == null) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz &6" + target.getName() + " &cnie jest online!"));
                    return;
                }

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie przypomniales hellcode gracza &6" + target.getName() + "&a!"));
                targetPlayer.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoj hellcode to: &c" + target.getHellCode()));
                return;
            }
            return;
        }

        if (args[0].equalsIgnoreCase("stworz")) {


            if (!(user.getHellCode().isEmpty() || user.getHellCode().equals("off"))) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Posiadasz juz zalozony kod na swoje konto."));
                return;
            }

            if (args.length < 3 || args[1] == null || args[2] == null) {
                player.sendMessage(Utils.poprawneUzycie("hellcode stworz <kod> <kod>"));
                return;
            }

            if (!args[1].equals(args[2])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podane kody nie sa takie same!"));
                return;
            }

            if (obvPass.contains(args[1].toLowerCase())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie za prosty ten kod? :D"));
                return;
            }

            if (args[1].length() < 4 || args[1].length() > 16) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Kod musi miec od 4 do 16 znakow!"));
                return;
            }

            user.setHellCode(args[1]);
            user.setHellCodeLogin(false);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPomyslnie ustawiles hellcode na &6: "));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[1] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[1])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("hellcode-logs", EmbedUtil.create("**HellCode Create**",
                    "**Gracz:** `" + player.getName() + "` **stworzyl swoj hellcode!**\n"
            + (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "")
                    + "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n"
                            + "**Nowy Kod: **||" + args[2] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.red));
            return;
        }

        if (args[0].equalsIgnoreCase("zmien")) {

            if (user.getHellCode().isEmpty()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw stworzyc hellcode, zeby moc go zmienic!"));
                return;
            }

            if (!user.isHellCodeLogin()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy muszisz sie zalgowac! Uzyj: &c/hellcode <kod>"));
                return;
            }

            if (user.getHellCode().equals("off")) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Twoj HellCode zostal wylaczony! Uzyj: &c/hellcode stworz <kod> <kod>&7, zeby go wlaczyc!"));
                return;
            }

            if (args.length < 4 || args[1] == null || args[2] == null || args[3] == null) {
                player.sendMessage(Utils.poprawneUzycie("hellcode zmien <stary kod> <nowy kod> <nowy kod>"));
                return;
            }

            if (!args[2].equals(args[3])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podane kody nie sa takie same!"));
                return;
            }

            if (obvPass.contains(args[2].toLowerCase()) || args[2].contains(player.getName())) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nie za prosty ten kod? :D"));
                return;
            }

            if (args[2].length() < 4 || args[2].length() > 16) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Kod musi miec od 4 do 16 znakow!"));
                return;
            }

            if (!user.getHellCode().equals(args[1])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podales zly kod!"));
                return;
            }

            user.setHellCode(args[2]);
            user.setHellCodeLogin(false);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPomyslnie zmieniles hellcode na &6: "));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[2] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[2])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("hellcode-logs", EmbedUtil.create("**HellCode Change**",
                    "**Gracz:** `" + player.getName() + "` **zmienil swoj hellcode!**\n"
                            + (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "")
                            + "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n"
                            + "**Kod Przed Zmiana: **||" + args[1] + "||\n"
                            + "**Kod Po Zmianie: **||" + args[2] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.red));
            return;
        }

        if (args[0].equalsIgnoreCase("wylacz")) {

            if (!user.isHellCodeLogin()) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy muszisz sie zalgowac! Uzyj: &c/hellcode <kod>"));
                return;
            }

            if (args.length < 2 || args[1] == null) {
                player.sendMessage(Utils.poprawneUzycie("hellcode wylacz <kod>"));
                return;
            }

            if (!user.getHellCode().equals(args[1])) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podales zly kod!"));
                return;
            }

            user.setHellCode("off");
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie wylaczyles hellcode!"));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(user.getId(), user));
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("hellcode-logs", EmbedUtil.create("**HellCode OFF**",
                    "**Gracz:** `" + player.getName() + "` **wylaczyl swoj hellcode!**\n"
                            + (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "")
                            + "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n"
                            + "**Kod Przed wylaczeniem: **||" + args[1] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.red));
            return;
        }

        if (rpgcore.getUserManager().find(player.getUniqueId()).getHellCode().isEmpty()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw stworzyc swoj hellcode! Uzyj: &c/hellcode stworz <kod> <kod>"));
            return;
        }

        if (user.isHellCodeLogin()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Juz jestes zalogowany!"));
            return;
        }

        if (user.getHellCode().equals(args[0])) {
            user.setHellCodeLogin(true);
            final TextComponent finalMessage = new TextComponent(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalogowano HellCode: &6"));
            final TextComponent message = new TextComponent(Utils.format("&6&k" + args[0] + "&a!"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(args[0])}));
            finalMessage.addExtra(message);
            player.spigot().sendMessage(finalMessage);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Zyczymy milej i udanej rozgrywki. &cZespol Hellrpg.pl"));
            double[] tps = MinecraftServer.getServer().recentTps;
            RPGCORE.getDiscordBot().sendChannelMessage("hellcode-logs", EmbedUtil.create("**HellCode LogIn**",
                    "**Gracz:** `" + player.getName() + "` **zalogowal sie swoim hellcode!**\n"
                            + (Bukkit.getPlayer(args[0]) != null ? "**Ping Gracza: **" + ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle().ping + " ms\n" : "")
                            + "**Ping Serwerowy: ** 1m - " + tps[0] + "tps, 5m - " + tps[1] + "tps, 15m - " + tps[2] + "tps\n"
                            + "**Kod: **||" + args[0] + "||\n"
                            + "**Adress IP:** ||" + player.getAddress().getAddress() + "||\n"
                            + "**Data:** " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()), Color.GREEN));
        } else {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Podany HellCode jest nieprawidlowy!"));
        }


    }
}
