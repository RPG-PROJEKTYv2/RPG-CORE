package rpg.rpgcore.BACKUP.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;


public class BackupCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public BackupCommand(final RPGCORE rpgcore) {
        super("backup");
        this.rpgcore = rpgcore;
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.HA);
    }

    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Utils.poprawneUzycie("backup <nick> <option/?>"));
            return;
        }

        if (args[0].equals(player.getName())) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz wykonac tej komendy na sobie!"));
            return;
        }

        final User target = rpgcore.getUserManager().find(args[0]);
        if (target == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza o nicku &6" + args[0]));
            return;
        }

        switch (args[1]) {
            case "save":
                final long time = System.currentTimeMillis();
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aRozpoczeto tworzenie backupu gracza &6" + target.getName()));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    final Backup backup = new Backup(target.getId());
                    backup.save();
                    rpgcore.getBackupManager().add(backup.getUuid(), backup);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aBackup gracza &6" + target.getName() + " &azostal utworzony w czasie &6" + (System.currentTimeMillis() - time) + "ms"));
                });
                return;
            case "open":
                rpgcore.getBackupManager().openPlayerBackups(player, target.getId(), 1);
                return;
            case "?":
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&6Backupy gracza &e" + target.getName()));
                player.sendMessage(Utils.format("&6/backup <nick> save &7- tworzy backup gracza"));
                player.sendMessage(Utils.format("&6/backup <nick> open &7- otwiera backupy gracza"));
        }
    }
}
