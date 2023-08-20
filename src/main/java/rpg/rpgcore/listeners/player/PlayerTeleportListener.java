package rpg.rpgcore.listeners.player;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.PetArmorStand.PetArmorStand;
import rpg.rpgcore.pets.objects.Pet;

public class PlayerTeleportListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerTeleportListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(final PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND ||
                e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN || e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL ||
                e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {

            if (e.getFrom().getWorld() == e.getTo().getWorld()) {
                return;
            }

            final Pet pet = rpgcore.getPetyManager().findActivePet(e.getPlayer().getUniqueId()).getPet();

            if (pet.getItem() != null) {
                if (EntityTypes.isPetSpawned(e.getPlayer().getUniqueId())) {
                    EntityTypes.despawnPet(e.getPlayer().getUniqueId());
                }
                EntityTypes.spawnEntity(new PetArmorStand(((CraftWorld) e.getPlayer().getLocation().getWorld()).getHandle(), e.getPlayer()), e.getPlayer().getUniqueId(), e.getPlayer().getLocation(), pet.getItem().clone().getItemMeta().getDisplayName()); //.substring(0, item.clone().getItemMeta().getDisplayName().indexOf(" "))
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> EntityTypes.addEquipment(EntityTypes.getEntity(e.getPlayer().getUniqueId()), pet.getItem().clone()), 20L);
            }
        }
    }
}
