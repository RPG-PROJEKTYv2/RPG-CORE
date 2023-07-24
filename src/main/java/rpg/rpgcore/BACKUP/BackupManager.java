package rpg.rpgcore.BACKUP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class BackupManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, List<Backup>> backupMap;

    public BackupManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.backupMap = this.rpgcore.getBackupMongoManager().loadAll();
    }


    public void openPlayerBackups(final Player player, final UUID target) {
        final List<Backup> backups = this.backupMap.get(target);
        if (backups.size() == 0) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz &6" + this.rpgcore.getUserManager().find(target).getName() + " &cnie posiada zadnych backupow"));
            return;
        }
        backups.sort(Comparator.comparing(Backup::getDateToCompare));
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&6&lBackupy gracza &e&l" + this.rpgcore.getUserManager().find(target).getName()));
        for (int i = 0; i < backups.size(); i++) {
            final Backup backup = backups.get(i);
            gui.setItem(i, new ItemBuilder(Material.CHEST).setName("&6" + backup.getDate()).setLore(Arrays.asList("&7Kliknij aby przywrocic")).toItemStack().clone());
        }

        player.openInventory(gui);
    }

    public Backup find(final UUID uuid, final String date) {
        return this.backupMap.get(uuid).stream().filter(backup -> backup.getDate().equals(date)).findFirst().orElse(null);
    }

    public void add(final UUID uuid, final Backup backup) {
        if (this.backupMap.containsKey(uuid)) {
            this.backupMap.get(uuid).add(backup);
        }
        else {
            final List<Backup> backups = new ArrayList<>();
            backups.add(backup);
            this.backupMap.put(uuid, backups);
        }
    }



}
