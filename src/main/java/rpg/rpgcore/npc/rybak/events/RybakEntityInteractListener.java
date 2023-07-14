package rpg.rpgcore.npc.rybak.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RybakEntityInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public RybakEntityInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rybakPlayerInteract(final PlayerInteractAtEntityEvent e) {

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();



        if (e.getRightClicked().getType() != EntityType.ARMOR_STAND) {
            return;
        }


        if (!rpgcore.getRybakNPC().checkIfMapContainsArmorStand(e.getRightClicked().getLocation())) return;

        e.setCancelled(true);
        if (rpgcore.getUserManager().find(uuid).getLvl() < 30) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Osiagnij &c30 &7poziom, zeby to zebrac!"));
            return;
        }
        final ArmorStand as = (ArmorStand) e.getRightClicked();

        final RybakUser user = rpgcore.getRybakNPC().find(uuid);
        final int key = rpgcore.getRybakNPC().getKeyByValue(as);
        if (key == -1) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &cCos poszlo nie tak!"));
            return;
        }
        if (user.getClickedArmorStands().contains(key)) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Juz to zebrales!"));
            return;
        }

        final Material type = (as.getItemInHand() == null || as.getItemInHand().getType().equals(Material.AIR) ? as.getHelmet().getType() : as.getItemInHand().getType());

        player.sendMessage(type.toString());

        switch (type) {
            case WOOD:
                player.getInventory().addItem(RybakItems.I1.getItemStack().clone());
                break;
            case STICK:
                player.getInventory().addItem(RybakItems.I2.getItemStack().clone());
                break;
            case WEB:
                player.getInventory().addItem(RybakItems.I3.getItemStack().clone());
                break;
        }
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zebrales jeden z moich przedmiotow&7!"));
        user.getClickedArmorStands().add(key);
        user.save();
    }

    private final List<Chunk> blindWaterChunks = Arrays.asList(
            Bukkit.getWorld("world").getChunkAt(-6, -10),
            Bukkit.getWorld("world").getChunkAt(-7, -10),
            Bukkit.getWorld("world").getChunkAt(-6, -11),
            Bukkit.getWorld("world").getChunkAt(-7, -11)
    );

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWalkInWater(final PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("world")) return;
        if (!blindWaterChunks.contains(e.getPlayer().getLocation().getChunk())) return;
        if (e.getPlayer().getLocation().getY() >= 95) {
            e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
            return;
        }
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBoatPlace(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getItemInHand().getType() == Material.BOAT) {
                e.setCancelled(true);
            }
        }
    }
}
