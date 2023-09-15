package rpg.rpgcore.dungeons.maps.koloseum.events;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

public class KoloseumInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public KoloseumInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (!e.getPlayer().getWorld().equals(rpgcore.getKoloseumManager().getMap())) return;
        if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.IRON_FENCE)) return;
        e.setCancelled(true);
        e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
        e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
        if (RestartManager.restart.isRestarting()) {
            e.getPlayer().sendMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cNie mozesz tego zrobic, poniewaz aktualnie trwa restart serwera!"));
            return;
        }
        if (rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains("Koloseum")) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen Dungeon zostal wylaczony przez administracje!"));
            return;
        }
        if (e.getItem() == null || !new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack().equals(Dungeony.I_KLUCZ_KOLOSEUM.getItemStack())) return;
        if (rpgcore.getKoloseumManager().isOccupied()) {
            e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon jest aktualnie zajety!"));
            return;
        }
        e.getPlayer().getInventory().removeItem(new ItemBuilder(Dungeony.I_KLUCZ_KOLOSEUM.getItemStack()).setAmount(1).toItemStack());
        rpgcore.getKoloseumManager().startDungeon(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack())) return;
        e.setUseItemInHand(Event.Result.DENY);
        e.setCancelled(true);
    }
}
