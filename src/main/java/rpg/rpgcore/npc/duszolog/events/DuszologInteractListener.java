package rpg.rpgcore.npc.duszolog.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class DuszologInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            final ArmorStand as = (ArmorStand) e.getRightClicked();
            final Player player = e.getPlayer();
            e.setCancelled(true);


            if (!as.getHelmet().equals(RPGCORE.getInstance().getDuszologNPC().getHelm())) {
                return;
            }

            if (!e.getPlayer().isSneaking()) {
                e.getPlayer().sendMessage(Utils.format("&5&lDuszolog &8>> &7Aby zebrac dusze, kliknij na nia &6&lSHIFT+PPM&7!"));
                return;
            }

            if (!as.getCustomName().contains(e.getPlayer().getName())) {
                player.sendMessage(Utils.format("&5&lDuszolog &8>> &7Ta dusza nalezy chyba do kogos innego, prawda?"));
                return;
            }

            player.sendMessage(Utils.format("&5&lDuszolog &8>> &aPomyslnie zebrales dusze " + as.getCustomName().substring(0, as.getCustomName().indexOf('-') - 1) + "&a!"));
            as.remove();
        }
    }
}
