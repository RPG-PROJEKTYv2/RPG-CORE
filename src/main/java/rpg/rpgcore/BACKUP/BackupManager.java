package rpg.rpgcore.BACKUP;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.PageUtils;
import rpg.rpgcore.utils.Utils;

import java.util.*;

public class BackupManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, List<Backup>> backupMap;

    public BackupManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.backupMap = this.rpgcore.getBackupMongoManager().loadAll();
    }


    public void openPlayerBackups(final Player player, final UUID target, final int page) {
        final List<Backup> backups = this.backupMap.get(target);
        if (backups == null || backups.isEmpty()) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cGracz &6" + this.rpgcore.getUserManager().find(target).getName() + " &cnie posiada zadnych backupow"));
            return;
        }
        backups.sort(Comparator.comparing(Backup::getDateToCompare).reversed());

        final List<ItemStack> backupItems = new ArrayList<>();

        for (final Backup backup : backups) backupItems.add(new ItemBuilder(Material.CHEST).setName("&6" + backup.getDate()).setLore(Arrays.asList(
                "&7Poziom: &f" + backup.getUser().getLvl(),
                "&7Exp: &f" + DoubleUtils.round((backup.getUser().getExp() / rpgcore.getLvlManager().getExpForLvl(backup.getUser().getLvl() + 1)) * 100, 2) + "%",
                "&7Kliknij aby przywrocic")).addTagString("uuid", backup.getUuid().toString()).toItemStack().clone());

        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&6&lBackupy gracza &e&l" + this.rpgcore.getUserManager().find(target).getName() + " &6#" + page));


        for (int i = 45; i < 54; i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack().clone());
        }

        if (PageUtils.isPageValid(backupItems, page - 1, 45)) gui.setItem(45, new ItemBuilder(Material.ARROW).setName("&aPoprzednia").addTagBoolean("valid", true).toItemStack().clone());
        else gui.setItem(45, new ItemBuilder(Material.ARROW).setName("&cPoprzednia").addTagBoolean("valid", false).toItemStack().clone());

        if (PageUtils.isPageValid(backupItems, page + 1, 45)) gui.setItem(53, new ItemBuilder(Material.ARROW).setName("&aNastepna").addTagBoolean("valid", true).toItemStack().clone());
        else gui.setItem(53, new ItemBuilder(Material.ARROW).setName("&cNastepna").addTagBoolean("valid", false).toItemStack().clone());

        for (ItemStack is : PageUtils.getPageItems(backupItems, page, 45)) gui.setItem(gui.firstEmpty(), is);

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
