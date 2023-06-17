package rpg.rpgcore.npc.magazynier.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.magazynier.objects.MagazynierUser;
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
        final String closedInventoryTitle = Utils.removeColor(closedInventory.getTitle());
        final Player player = (Player) e.getPlayer();

        if (closedInventoryTitle.contains("Magazyn #")) {
            final int nrMagazynu = Integer.parseInt(closedInventoryTitle.split("-")[0].replace("Magazyn #", "").trim());
            UUID target = player.getUniqueId();
            if (closedInventoryTitle.contains(" - ")) target = UUID.fromString(closedInventoryTitle.replaceAll("Magazyn #" + nrMagazynu + " - ", "").trim());
            final MagazynierUser user = rpgcore.getMagazynierNPC().find(target);
            switch (nrMagazynu) {
                case 1:
                    user.setMagazyn1(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 2:
                    user.setMagazyn2(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 3:
                    user.setMagazyn3(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 4:
                    user.setMagazyn4(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
                case 5:
                    user.setMagazyn5(Utils.itemStackArrayToBase64(closedInventory.getContents()));
                    break;
            }
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataMagazynier(user.getUuid(), user));
            rpgcore.getCooldownManager().givePlayerMagazynyCooldown(target);
        }

    }

}
