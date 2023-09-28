package rpg.rpgcore.commands.admin;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Iterator;

public class MemoryCommand extends CommandAPI {

    private final long uptimeStart = System.currentTimeMillis();

    public MemoryCommand() {
        super("memory");
        this.setAliases(Arrays.asList("mem", "ram"));
        this.setRankLevel(RankType.HA);
    }

    @Override
    public void executeCommand(final CommandSender sender, final String[] args) {
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
        long usedMemory = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
        sender.sendMessage(Utils.format("&8&m          &8( &6Statystyki Serwera &8)&m          " + "\n" +
                this.formatTPS() + "\n" +
                "&eCzas dzialania: &6" + this.time() + "\n" +
                "&eZajeta Pamiec: &6" + this.formatMemory((double) maxMemory - (double) freeMemory, (double) maxMemory) + "MB\n" +
                "&eWolna Pamiec: &6" + this.formatFree((double) freeMemory, (double) usedMemory) + "MB\n" +
                "&eEntity: &f" + this.getEntitiesCount() + "\n" +
                "&eGracze: &f" + Bukkit.getOnlinePlayers().size() + "\n" +
                "&8&m          &8( &6Statystyki Serwera &8)&m          "));
    }

    private String formatMemory(double used, double max) {
        return (used / max < 0.5D ? ChatColor.WHITE.toString() : (used / max < 0.9D ? ChatColor.WHITE.toString() : ChatColor.RED.toString())) + (int) used + ChatColor.DARK_GRAY + "/" + ChatColor.WHITE + (int) max;
    }

    private String formatFree(double free, double used) {
        return (free / used > 0.3D ? ChatColor.WHITE.toString() : (free / used > 0.15D ? ChatColor.WHITE.toString() : ChatColor.RED.toString())) + (int) free + ChatColor.DARK_GRAY + "/" + ChatColor.WHITE + (int) used;
    }

    private String formatTPS() {
        StringBuilder sb = new StringBuilder(ChatColor.YELLOW + "TPS w ostatnich 1m, 5m, 15m: ");
        double[] var2 = MinecraftServer.getServer().recentTps;
        int var3 = var2.length;

        for (double tps : var2) {
            sb.append(this.format(tps));
            sb.append(", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

    private String format(double tps) {
        return (tps > 18.0D ? ChatColor.WHITE : (tps > 16.0D ? ChatColor.WHITE : ChatColor.RED)) + (tps > 20.0D ? "*" : "") + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }

    private String time() {
        long time = (System.currentTimeMillis() - this.uptimeStart) / 1000L;
        long secs = time % 60L;
        long mins = time / 60L % 60L;
        long hours = time / 3600L % 24L;
        long days = time / 3600L / 24L;
        return (days > 0L ? days + "d " : "") + (hours > 0L ? hours + "h " : "") + (mins > 0L ? mins + "m " : "") + secs + "s";
    }

    private int getEntitiesCount() {
        int count = 0;

        World world;
        for (Iterator<World> var2 = Bukkit.getWorlds().iterator(); var2.hasNext(); count += world.getEntities().size()) {
            world = (World) var2.next();
        }

        return count;
    }
}
