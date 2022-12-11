package rpg.rpgcore.npc.magazynier.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class MagazynierInventoryClose implements Listener {

    private final RPGCORE rpgcore;

    public MagazynierInventoryClose(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void magazynierInventoryClose (final InventoryCloseEvent e) {

        if (e.getInventory() == null) {
            return;
        }

        final Inventory closedInventory = e.getInventory();
        final String closedInventoryTitle = closedInventory.getTitle();
        final Player player = (Player) e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (closedInventoryTitle.contains("Magazyn #")) {
            final int nrMagazynu = Integer.parseInt(Utils.removeColor(closedInventoryTitle).replaceAll("Magazyn #", "").trim());
            switch (nrMagazynu) {
                case 1:
                    rpgcore.getMagazynierNPC().find(uuid).setMagazyn1(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 2:
                    rpgcore.getMagazynierNPC().find(uuid).setMagazyn2(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 3:
                    rpgcore.getMagazynierNPC().find(uuid).setMagazyn3(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 4:
                    rpgcore.getMagazynierNPC().find(uuid).setMagazyn4(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 5:
                    rpgcore.getMagazynierNPC().find(uuid).setMagazyn5(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(uuid, rpgcore.getMagazynierNPC().find(uuid)));
            rpgcore.getCooldownManager().givePlayerMagazynyCooldown(uuid);
        }

    }

}
