package rpg.rpgcore.pets.listeners;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.EntityTypes;
import rpg.rpgcore.entities.PetArmorStand.PetArmorStand;
import rpg.rpgcore.pets.Pet;

public class PetWorldChangeListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        if (RPGCORE.getInstance().getPetyManager().findActivePet(e.getPlayer().getUniqueId()).getPet().getItem() != null && EntityTypes.isPetSpawned(e.getPlayer().getUniqueId())) {
            final Player player = e.getPlayer();
            final Pet pet = RPGCORE.getInstance().getPetyManager().findActivePet(player.getUniqueId()).getPet();
            EntityTypes.despawnPet(player.getUniqueId());
            EntityTypes.spawnEntity(new PetArmorStand(((CraftWorld) player.getLocation().getWorld()).getHandle(), player), player.getUniqueId(), player.getLocation(), pet.getItem().clone().getItemMeta().getDisplayName() + " " + pet.getItem().clone().getItemMeta().getDisplayName().substring(0, 2) + player.getName()); //.substring(0, item.clone().getItemMeta().getDisplayName().indexOf(" "))
            RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
                EntityTypes.addEquipment(EntityTypes.getEntity(player.getUniqueId()), pet.getItem().clone());
                System.out.println("Dodano item");
            }, 20L);
        }
    }
}
