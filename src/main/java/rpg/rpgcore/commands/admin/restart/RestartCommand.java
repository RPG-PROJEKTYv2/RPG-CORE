package rpg.rpgcore.commands.admin.restart;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.commands.admin.restart.objects.Restart;
import rpg.rpgcore.commands.admin.restart.tasks.RestartTask;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

import java.util.Objects;

public class RestartCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public RestartCommand(final RPGCORE rpgcore) {
        super("stop");
        this.rpgcore = rpgcore;
        this.setRankLevel(RankType.HA);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        final Restart restart = RestartManager.restart;

        if (restart.isRestarting()) {
            sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cSerwer jest juz w trakcie restartu!"));
            return;
        }
        if (args.length != 0) {
            sender.sendMessage(Utils.poprawneUzycie("restart"));
            return;
        }


        for (final Player player : Bukkit.getOnlinePlayers()) {

            final User user = rpgcore.getUserManager().find(player.getUniqueId());
            if (user.getPierscienDoswiadczeniaTime() != 0 && user.getPierscienDoswiadczeniaTime() - System.currentTimeMillis() > 900_000L) {
                if (user.getPierscienDoswiadczenia() == 25) {
                    player.getInventory().addItem((user.getPierscienDoswiadczeniaTime() - System.currentTimeMillis() > 2_700_000L ? GlobalItem.I53.getItemStack().clone() : GlobalItem.I52.getItemStack().clone()));
                } else {
                    player.getInventory().addItem((user.getPierscienDoswiadczeniaTime() - System.currentTimeMillis() > 2_700_000L ? GlobalItem.I55.getItemStack().clone() : GlobalItem.I54.getItemStack().clone()));
                }
                Bukkit.getServer().broadcastMessage(Utils.format("&6&lPierscien Doswiadczenia &8&l>> &fGracz &e" + player.getName() + " &fotrzymal zwrot Pierscienia Doswiadczenia!"));
            }


            final Location location = player.getLocation();
            switch (location.getWorld().getName()) {
                case "Dungeon60-70":
                    if (rpgcore.getPrzedsionekManager().getDungeonStatus() != DungeonStatus.ENDED && rpgcore.getPrzedsionekManager().getDungeonStatus() != DungeonStatus.RESETTING) {
                        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) rpgcore.getPrzedsionekManager().getDungeonHologram().getLines().get(3)).getText())).replace("Przechodzi: ", "");
                        final Player dungOwner = Bukkit.getPlayer(playerName);
                        if (dungOwner == null) break;
                        dungOwner.getInventory().addItem(Dungeony.I_KLUCZ_PIEKIELNY_PRZEDSIONEK.getItemStack());
                        rpgcore.getPrzedsionekManager().resetDungeon();
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDUNGEON 60-70 &8&l>> &fGracz &e" + playerName + " &fotrzymal zwrot klucza do dungeonu!"));
                        break;
                    }
                    break;

                case "Dungeon70-80":
                    if (rpgcore.getKoloseumManager().getDungeonStatus() != DungeonStatus.ENDED && rpgcore.getKoloseumManager().getDungeonStatus() != DungeonStatus.RESETTING) {
                        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) rpgcore.getKoloseumManager().getHologram().getLines().get(3)).getText())).replace("Przechodzi: ", "");
                        final Player dungOwner = Bukkit.getPlayer(playerName);
                        if (dungOwner == null) break;
                        dungOwner.getInventory().addItem(Dungeony.I_KLUCZ_KOLOSEUM.getItemStack());
                        rpgcore.getKoloseumManager().resetDungeon();
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDUNGEON 70-80 &8&l>> &fGracz &e" + playerName + " &fotrzymal zwrot klucza do dungeonu!"));
                        break;
                    }
                    break;

                case "Dungeon80-90":
                    if (rpgcore.getTajemniczePiaskiManager().getDungeonStatus() != DungeonStatus.ENDED && rpgcore.getTajemniczePiaskiManager().getDungeonStatus() != DungeonStatus.RESETTING) {
                        final String playerName = Utils.removeColor(Objects.requireNonNull(((TextHologramLine) rpgcore.getTajemniczePiaskiManager().getHologram().getLines().get(3)).getText())).replace("Przechodzi: ", "");
                        final Player dungOwner = Bukkit.getPlayer(playerName);
                        if (dungOwner == null) break;
                        dungOwner.getInventory().addItem(Dungeony.I_KLUCZ_TAJEMNICZE_PIASKI.getItemStack());
                        rpgcore.getTajemniczePiaskiManager().resetDungeon();
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDUNGEON 80-90 &8&l>> &fGracz &e" + playerName + " &fotrzymal zwrot klucza do dungeonu!"));
                        break;
                    }
                    break;

                case "Kopalnia":
                    if (rpgcore.getGornikNPC().find(player.getUniqueId()).getTimeLeft() > 0L) {
                        player.getInventory().addItem(GlobalItem.getCheck(250_000).clone());
                        player.teleport(rpgcore.getSpawnManager().getSpawn());
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lKOPALNIA &8&l>> &fGracz &e" + player.getName() + " &fotrzymal zwrot za teleport na kopalnie!"));
                        break;
                    }
            }
        }

        restart.setRestarting(true);
        new RestartTask(rpgcore);
        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aRozpoczeto restart serwera!"));

    }
}
