package rpg.rpgcore.listeners.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import static org.bukkit.event.EventPriority.LOWEST;

public class BlockInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public BlockInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
                if (!rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).isHellCodeLogin() && !rpgcore.getChatManager().find(e.getPlayer().getUniqueId()).getHellcodeUser().isEnderchest()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&7Przed zrobieniem tego zaloguj sie swoim HellCode. Uzyj: &c/hellcode <kod>"));
                }
            }
        }
    }

    @EventHandler(priority = LOWEST)
    public void interactBlockEvent(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand() != null) {
                if (player.getGameMode() == GameMode.CREATIVE) return;
                if (player.getItemInHand().getType().equals(Material.ENDER_PEARL) || player.getItemInHand().getType().equals(Material.EYE_OF_ENDER)
                        || player.getItemInHand().getType().equals(Material.SNOW_BALL) || player.getItemInHand().getType().equals(Material.EGG)) {
                    e.setCancelled(true);
                    return;
                }
            }
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if ((e.getClickedBlock().getType().equals(Material.ANVIL) && !e.getPlayer().getWorld().getName().equals("demontower")) || e.getClickedBlock().getType().equals(Material.CHEST) ||
                        e.getClickedBlock().getType().equals(Material.TRAPPED_CHEST) || e.getClickedBlock().getType().equals(Material.HOPPER) ||
                        e.getClickedBlock().getType().equals(Material.HOPPER_MINECART) || e.getClickedBlock().getType().equals(Material.FURNACE) ||
                        e.getClickedBlock().getType().equals(Material.BURNING_FURNACE) || e.getClickedBlock().getType().equals(Material.STORAGE_MINECART) ||
                        e.getClickedBlock().getType().equals(Material.POWERED_MINECART) || e.getClickedBlock().getType().equals(Material.DISPENSER) ||
                        e.getClickedBlock().getType().equals(Material.DROPPER) || e.getClickedBlock().getType().equals(Material.WORKBENCH) ||
                        e.getClickedBlock().getType().equals(Material.JUKEBOX) || e.getClickedBlock().getType().equals(Material.ITEM_FRAME) ||
                        e.getClickedBlock().getType().equals(Material.BED) || e.getClickedBlock().getType().equals(Material.BREWING_STAND)
                        || e.getClickedBlock().getType().equals(Material.BEACON) || e.getClickedBlock().getType().equals(Material.TRAP_DOOR)
                        || e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE) || e.getClickedBlock().getType().equals(Material.FENCE_GATE)
                        || e.getClickedBlock().getType().equals(Material.DAYLIGHT_DETECTOR) || e.getClickedBlock().getType().equals(Material.DAYLIGHT_DETECTOR_INVERTED)) {
                    if (!rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() ||
                            (rpgcore.getUserManager().find(player.getUniqueId()).getRankUser().isHighStaff() && !rpgcore.getUserManager().find(player.getUniqueId()).isAdminCodeLogin())) {
                        e.setCancelled(true);
                        return;
                    }
                }
                if (e.getClickedBlock() instanceof ItemFrame) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
