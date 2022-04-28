package rpg.rpgcore.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.managers.npc.DuszologNPC;
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


        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            player.sendMessage(e.getRightClicked().getEntityId() + " -ID");
            if (e.getRightClicked().getEntityId() == 2 || e.getRightClicked().getEntityId() == 3 || e.getRightClicked().getEntityId() == 4 || e.getRightClicked().getEntityId() == 9 ||
                    e.getRightClicked().getEntityId() == 5 || e.getRightClicked().getEntityId() == 8 || e.getRightClicked().getEntityId() == 6 || e.getRightClicked().getEntityId() == 19 ||
                    e.getRightClicked().getEntityId() == 22 || e.getRightClicked().getEntityId() == 7 || e.getRightClicked().getEntityId() == 21 || e.getRightClicked().getEntityId() == 20) {
                e.setCancelled(true);
                if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < 74) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c75 &7poziom, zeby uzywac &6STOLU MAGI"));
                    return;
                }
                player.openInventory(rpgcore.getBaoManager().baoGUI(uuid));
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
                player.openInventory(rpgcore.getTeleporterNPC().teleporterMAIN());
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
                    return;
                }

            }
        }

    }
}
