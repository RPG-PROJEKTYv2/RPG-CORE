package rpg.rpgcore.bao;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class BAOPlayerInteract implements Listener {

    private final RPGCORE rpgcore;

    public BAOPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void baoPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        ItemStack itemToSet;

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (eventItem.equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                if (rpgcore.getBaoManager().getBaoBonusy(uuid).contains("Brak Bonusu")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw wylosowac swoje bonusy w &5&lStole Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (eventItem == null) {
                return;
            }

            if (eventItem.equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                if (rpgcore.getBaoManager().getBaoBonusy(uuid).contains("Brak Bonusu")) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz najpierw wylosowac swoje bonusy w &5&lStole Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
            }
        }

    }
}
