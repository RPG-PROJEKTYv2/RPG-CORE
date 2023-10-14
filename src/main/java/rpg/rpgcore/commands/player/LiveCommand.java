package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.regex.Pattern;

public class LiveCommand extends CommandAPI {
    private final RPGCORE rpgcore;
    public LiveCommand(final RPGCORE rpgcore) {
        super("live");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }

    private final Pattern ytPattern = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([\\w-]{11})");
    private final Pattern ttvPattern = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?twitch\\.tv\\/(?:videos\\/\\d+|\\w+\\/video\\/\\d+|\\w+)$");
    private final Pattern tikTokPattern = Pattern.compile("https?:\\/\\/(?:www\\.)?tiktok\\.com\\/@[\\w.-]+\\/video\\/\\d+");



    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        if (!(user.isTworca() || user.getRankUser().isHighStaff())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTa komenda jest dostepna tylko dla &9&lTworcow"));
            return;
        }
        if (args.length != 1) {
            player.sendMessage(Utils.poprawneUzycie("live <link>"));
            return;
        }
        if (rpgcore.getCooldownManager().hasLiveCommandCooldown(player.getUniqueId())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz odczekac jeszcze &e" + rpgcore.getCooldownManager().getLiveCommandCooldown(player.getUniqueId()) + " &cprzed ponownym uzyciem tej komendy!"));
            return;
        }
        final String liveLink = args[0];
        if (!ytPattern.matcher(liveLink).matches() && !ttvPattern.matcher(liveLink).matches() && !tikTokPattern.matcher(liveLink).matches()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPodany link nie jest linkiem do live"));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Dozwolone Platformy to &cYou&fTube&7, &5Twitch &7i &8Tik&dTok"));
            rpgcore.getCooldownManager().giveLiveCommandCooldown(player.getUniqueId());
            return;
        }
        Bukkit.getServer().broadcastMessage(" ");
        Bukkit.getServer().broadcastMessage(Utils.format("   &b&lLIVE &e>> &7" + player.getName() + " &ezaprasza na nowy content z serwera!"));
        Bukkit.getServer().broadcastMessage(Utils.format("   &b&lLIVE &e>> &8" + liveLink));
        Bukkit.getServer().broadcastMessage(" ");
        rpgcore.getCooldownManager().giveLiveCommandCooldown(player.getUniqueId());
    }
}
