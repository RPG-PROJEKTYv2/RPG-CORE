package rpg.rpgcore.dungeons.maps.piekielnyPrzedsionek.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

public class PiekielnyPrzedsionekInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public PiekielnyPrzedsionekInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (!e.getPlayer().getWorld().equals(rpgcore.getPrzedsionekManager().getGateWorld())) return;
        if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.NETHER_FENCE)) return;
        if (rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains("Piekielny Przedsionek") && !rpgcore.getUserManager().find(e.getPlayer().getUniqueId()).getRankUser().isHighStaff()) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen Dungeon zostal wylaczony przez administracje!"));
            return;
        }
        if (e.getItem() == null || !new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack().equals(Dungeony.I_KLUCZ_PIEKIELNY_PRZEDSIONEK.getItemStack())) return;
        if (rpgcore.getPrzedsionekManager().isOccupied()) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon jest aktualnie zajety!"));
            return;
        }
        e.setCancelled(true);
        e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
        e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
        e.getPlayer().getInventory().removeItem(new ItemBuilder(Dungeony.I_KLUCZ_PIEKIELNY_PRZEDSIONEK.getItemStack()).setAmount(1).toItemStack());
        rpgcore.getPrzedsionekManager().startDungeon(e.getPlayer());
    }
}
