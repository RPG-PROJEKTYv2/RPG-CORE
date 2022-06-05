package rpg.rpgcore.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PlayerInteractEntityListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractEntityListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent e){

        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getRightClicked().getType().equals(EntityType.IRON_GOLEM)) {
            final String entityName = Utils.removeColor(e.getRightClicked().getName());
            // MAGAZYNIER
            if (entityName.equalsIgnoreCase("Magazynier")) {
                rpgcore.getMagazynierNPC().openMagazynierMain(player);
                return;
            }
        }

        // NPCTY
        if (e.getRightClicked().getType() == EntityType.PLAYER) {

            final Player playerRightClicked = (Player) e.getRightClicked();
            final String entityName = Utils.removeColor(playerRightClicked.getName());

            // DUSZOLOG
            if (entityName.equalsIgnoreCase("Duszolog")) {
                player.openInventory(rpgcore.getDuszologNPC().duszologMAIN());
                return;
            }
            // TELEPORTER
            if (entityName.equalsIgnoreCase("TELEPORTER")) {
                player.openInventory(rpgcore.getTeleporterNPC().teleporterMAIN(player));
                return;
            }
            // RYBAK
            if (entityName.equalsIgnoreCase("Rybak")) {
                rpgcore.getRybakNPC().openRybakGUI(player);
                return;
            }

            // TRADE
            if (player.isSneaking()) {
                if (entityName.equalsIgnoreCase("trener") || entityName.equalsIgnoreCase("kolekcjoner") ||
                        entityName.equalsIgnoreCase("magazynier") || entityName.equalsIgnoreCase("medyk") ||
                        entityName.equalsIgnoreCase("czarodziej") || entityName.equalsIgnoreCase("itemshop") ||
                        entityName.equalsIgnoreCase("lowca") || entityName.equalsIgnoreCase("lesnik") ||
                        entityName.equalsIgnoreCase("straznik") || entityName.equalsIgnoreCase("alchemik") ||
                        entityName.equalsIgnoreCase("rybak") || entityName.equalsIgnoreCase("dungeony") ||
                        entityName.equalsIgnoreCase("kupiec") || entityName.equalsIgnoreCase("duszolog")) {
                    return;
                }
                final UUID entityUUID = playerRightClicked.getUniqueId();

                if (rpgcore.getTradeManager().isInAcceptList(uuid)) {
                    final Inventory tradeGUi = rpgcore.getTradeManager().createTradeGUI(entityUUID, uuid);
                    player.openInventory(tradeGUi);
                    playerRightClicked.openInventory(tradeGUi);
                    rpgcore.getTradeManager().removeFromAcceptList(uuid);
                    return;
                }

                if (!(rpgcore.getTradeManager().isInTradeMapAsKey(uuid) && rpgcore.getTradeManager().isInTradeMapAsValue(entityUUID))) {
                    rpgcore.getTradeManager().putInTradeMap(uuid, entityUUID);
                    rpgcore.getTradeManager().addToAcceptList(entityUUID);
                    rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> rpgcore.getTradeManager().canceltrade(uuid, entityUUID), 600L);
                    player.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Wyslano prosbe o wymiane do &6" + entityName));
                    playerRightClicked.sendMessage(Utils.format(Utils.TRADEPREFIX + "&7Otrzymales prosbe o wymiane od gracza &6" + player.getName()));
                }

            }
        }

    }
}
