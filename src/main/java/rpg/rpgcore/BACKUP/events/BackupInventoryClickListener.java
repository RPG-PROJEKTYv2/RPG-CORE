package rpg.rpgcore.BACKUP.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.BACKUP.objects.Backup;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BackupInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public BackupInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBackupClick(final InventoryClickEvent e) {
        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(clickedInventory.getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (title.contains("Backupy gracza ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);


            if (item == null || item.getType().equals(Material.AIR) || item.getType().equals(Material.STAINED_GLASS_PANE)) return;

            final int page = Integer.parseInt(title.split("#")[1]);
            final String targetNick = Utils.removeColor(title.split(" ")[2]);
            final User user = rpgcore.getUserManager().find(targetNick);

            if (slot == 45) {
                if (Utils.getTagBoolean(item, "valid")) {
                    this.rpgcore.getBackupManager().openPlayerBackups(player, user.getId(), page - 1);
                    return;
                }
                return;
            }
            if (slot == 53) {
                if (Utils.getTagBoolean(item, "valid")) {
                    this.rpgcore.getBackupManager().openPlayerBackups(player, user.getId(), page + 1);
                    return;
                }
                return;
            }

            if (!user.getId().toString().equals(Utils.getTagString(item, "uuid"))) {
                player.sendMessage(Utils.format("&6&lBACKUP &8>> &cCos poszlo nie tak i ten backup nie nalezy do tego gracza!"));
                player.closeInventory();
                return;
            }

            final Backup backup = rpgcore.getBackupManager().find(user.getId(), Utils.removeColor(item.getItemMeta().getDisplayName()));

            if (backup == null) {
                player.sendMessage(Utils.format("&6&lBACKUP &8>> &cCos poszlo nie tak i ten backup nie istnieje!"));
                player.closeInventory();
                return;
            }

            final Player target = Bukkit.getPlayer(user.getId());

            if (target != null) player.kickPlayer(Utils.kickMessage("CONSOLE", "Przywrocenia Backupu!"));

            final UUID targetUUID = user.getId();
            
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                final long start = System.currentTimeMillis();
                rpgcore.getUserManager().set(targetUUID, backup.getUser());
                rpgcore.getBonusesManager().set(targetUUID, backup.getBonuses());
                rpgcore.getOsManager().set(targetUUID, backup.getOs());
                rpgcore.getKolekcjonerNPC().set(targetUUID, backup.getKolekcjoner());
                rpgcore.getBaoManager().set(targetUUID, backup.getBao());
                rpgcore.getRybakNPC().set(targetUUID, backup.getRybak());
                rpgcore.getDodatkiManager().set(targetUUID, backup.getDodatki());
                rpgcore.getMetinologNPC().set(targetUUID, backup.getMetinolog());
                rpgcore.getKlasyManager().set(targetUUID, backup.getKlasa());
                rpgcore.getMedrzecNPC().set(targetUUID, backup.getMedrzec());
                rpgcore.getGornikNPC().set(targetUUID, backup.getGornik());
                rpgcore.getDuszologNPC().set(targetUUID, backup.getDuszolog());
                rpgcore.getPrzyrodnikNPC().set(targetUUID, backup.getPrzyrodnik());
                rpgcore.getChatManager().set(targetUUID, backup.getChat());
                rpgcore.getMagazynierNPC().set(targetUUID, backup.getMagazynier());
                rpgcore.getLowcaNPC().set(targetUUID, backup.getLowca());
                rpgcore.getLesnikNPC().set(targetUUID, backup.getLesnik());
                rpgcore.getPetyManager().setActivePet(targetUUID, backup.getPet());
                rpgcore.getPetyManager().setUserPets(targetUUID, backup.getUserPets());
                //rpgcore.getWyslannikNPC().set(targetUUID, backup.getWyslannik());
                rpgcore.getHandlarzNPC().set(targetUUID, backup.getHandlarz());
                rpgcore.getKociolkiManager().set(targetUUID, backup.getKociolki());
                rpgcore.getWyszkolenieManager().set(targetUUID, backup.getWyszkolenie());
                rpgcore.getUserManager().setWWWUser(targetUUID, backup.getWwwUser());
                rpgcore.getPustelnikNPC().set(targetUUID, backup.getPustelnik());
                rpgcore.getMistrzYangNPC().set(targetUUID, backup.getMistrzYang());
                rpgcore.getCzarownicaNPC().set(targetUUID, backup.getCzarownica());
                player.sendMessage(Utils.format("&6&lBACKUP &8>> &aPrzywrocono backup &6" + backup.getDate() + " &agracza &6" + targetNick + "&aw czasie &6" + (System.currentTimeMillis() - start) + "ms &a!"));
            });

        }


    }
}
