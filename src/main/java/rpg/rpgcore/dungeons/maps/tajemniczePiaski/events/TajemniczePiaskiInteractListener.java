package rpg.rpgcore.dungeons.maps.tajemniczePiaski.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.commands.admin.restart.RestartManager;
import rpg.rpgcore.dungeons.maps.tajemniczePiaski.objects.RdzenPiaszczystychWydm;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;

public class TajemniczePiaskiInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public TajemniczePiaskiInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (player.getLocation().getWorld().equals(rpgcore.getTajemniczePiaskiManager().getMap())) {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) return;
            if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.IRON_FENCE)) return;
            e.setCancelled(true);
            e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
            e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
            if (RestartManager.restart.isRestarting()) {
                player.sendMessage(Utils.format("&8&l[&4&lRESTART&8&l] &cNie mozesz tego zrobic, poniewaz aktualnie trwa restart serwera!"));
                return;
            }
            if (rpgcore.getDisabledManager().getDisabled().getDisabledDungeons().contains("Tajemnicze Piaski")) {
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen Dungeon zostal wylaczony przez administracje!"));
                return;
            }
            if (e.getItem() == null || !new ItemBuilder(e.getItem().clone()).setAmount(1).toItemStack().equals(Dungeony.I_KLUCZ_TAJEMNICZE_PIASKI.getItemStack())) return;
            if (rpgcore.getTajemniczePiaskiManager().isOccupied()) {
                e.getPlayer().sendMessage(Utils.format(Utils.SERVERNAME + "&cTen dungeon jest aktualnie zajety!"));
                return;
            }
            e.getPlayer().getInventory().removeItem(new ItemBuilder(Dungeony.I_KLUCZ_TAJEMNICZE_PIASKI.getItemStack()).setAmount(1).toItemStack());
            rpgcore.getTajemniczePiaskiManager().startDungeon(e.getPlayer());
            return;
        }

        if (player.getLocation().getWorld().equals(rpgcore.getTajemniczePiaskiManager().getDungeon())) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) return;
            if (e.getClickedBlock() == null || e.getClickedBlock().getType().equals(Material.AIR)) return;
            if (rpgcore.getTajemniczePiaskiManager().getRdzenieLocation().values().stream().noneMatch(rdzen -> rdzen.getLocations().contains(e.getClickedBlock().getLocation()))) return;
            if (player.getGameMode() == GameMode.CREATIVE) return;
            e.setCancelled(true);
            e.setUseInteractedBlock(PlayerInteractEvent.Result.DENY);
            e.setUseItemInHand(PlayerInteractEvent.Result.DENY);
            final RdzenPiaszczystychWydm rdzen = rpgcore.getTajemniczePiaskiManager().getRdzenieLocation().values().stream().filter(rdzen1 -> rdzen1.getLocations().contains(e.getClickedBlock().getLocation())).findFirst().get();
            rdzen.damage(player);
        }
    }
}
