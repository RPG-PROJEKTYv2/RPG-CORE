package rpg.rpgcore.dungeons.maps.demoniczneSale.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

public class DemoniczneSaleInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public DemoniczneSaleInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (player.getLocation().getWorld().equals(rpgcore.getDemoniczneSaleManager().getMap())) {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
            if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.IRON_FENCE)) return;
            e.setCancelled(true);
            e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
            e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
            if (RestartManager.restart.isRestarting()) {
                player.sendMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cNie mozesz tego zrobic, poniewaz aktualnie trwa restart serwera!"));
                return;
            }
            if (rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains("Demoniczne Sale")) {
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen Dungeon zostal wylaczony przez administracje!"));
                return;
            }
            if (e.getItem() == null || !new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack().equals(Dungeony.I_KLUCZ_DEMONICZNE_SALE.getItemStack())) return;
            if (rpgcore.getDemoniczneSaleManager().isOccupied()) {
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon jest aktualnie zajety!"));
                return;
            }
            e.getPlayer().getInventory().removeItem(new ItemBuilder(Dungeony.I_KLUCZ_DEMONICZNE_SALE.getItemStack()).setAmount(1).toItemStack());
            rpgcore.getDemoniczneSaleManager().startDungeon(e.getPlayer());
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSarkofagInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
        if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.ENDER_PORTAL_FRAME)) return;
        if (e.getItem() == null || !new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack().equals(Dungeony.I_DEMONICZNY_SARKOFAG.getItemStack().clone())) return;
        if (!e.getClickedBlock().getLocation().equals(new Location(rpgcore.getDemoniczneSaleManager().getDungeon(), 0, 65, 18))) return;
        e.setCancelled(true);
        e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
        e.setUseItemInHand(PlayerInteractEvent.Result.DENY);

        final Player player = e.getPlayer();

        player.getInventory().removeItem(new ItemBuilder(Dungeony.I_DEMONICZNY_SARKOFAG.getItemStack().clone()).setAmount(1).toItemStack().clone());

        if (!ChanceHelper.getChance(25)) {
            player.sendMessage(Utils.format("&4&lDemoniczne Sale &8>> &cNie udalo Ci sie otworzyc sarkofagu!"));
            rpgcore.getDemoniczneSaleManager().startPhase1();
            return;
        }
        player.sendMessage(Utils.format("&4&lDemoniczne Sale &8>> &cPomyslnie otworzyles &4&lDemoniczny Sarkofag!"));
        rpgcore.getDemoniczneSaleManager().startPhase2();
    }
}
