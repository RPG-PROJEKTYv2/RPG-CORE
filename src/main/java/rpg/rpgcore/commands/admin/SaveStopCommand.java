package rpg.rpgcore.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class SaveStopCommand extends CommandAPI {
    public SaveStopCommand() {
        super("savestop");
        setAliases(Arrays.asList("pause", "stop"));
        this.setRankLevel(RankType.HA);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        for (Player player : Bukkit.getOnlinePlayers()) {
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().savePlayer(player, player.getUniqueId()));
            if ((sender instanceof Player) && ((Player) sender) == player) {
                continue;
            }
            player.kickPlayer(Utils.format(Utils.SERVERNAME + "\n&cAktualnie Trwa Restart Serwera!\n&7Zapraszamy ponownie za chwile"));
        }
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aZapisano graczy i wylogowano"));
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aSerwer zostanie wylaczony za 10 sekundy..."));
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(RPGCORE.getInstance(), () -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }, 200L);
    }
}
