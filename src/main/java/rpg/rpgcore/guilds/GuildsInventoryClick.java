package rpg.rpgcore.guilds;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class GuildsInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public GuildsInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void guildInventoryClick(final InventoryClickEvent e) {

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();

        if (clickedInventoryTitle.contains("Panel Klanu")) {
            e.setCancelled(true);

            final String playerGuild = rpgcore.getGuildManager().getGuildTag(playerUUID);

            if (clickedSlot == 10) {
                rpgcore.getGuildManager().showMembers(playerGuild, player);
                return;
            }

            if (clickedSlot == 16) {
                player.sendMessage(Utils.format(Utils.GUILDSPREFIX + "&c&lULEPSZENIA COMMING SOON!"));
                return;
            }
            return;
        }

        if (clickedInventoryTitle.contains("Czlonkowie Klanu")) {
            e.setCancelled(true);
            final String tag = rpgcore.getGuildManager().getGuildTag(playerUUID);

            if (!(rpgcore.getGuildManager().getGuildOwner(tag).equals(playerUUID) || rpgcore.getGuildManager().getGuildCoOwner(tag).equals(playerUUID))) {
                return;
            }

            if (clickedSlot == 0) {
                return;
            }


            if (clickedItem.getType().equals(Material.AIR)) {
                return;
            }

            final UUID targetUUID = rpgcore.getPlayerManager().getPlayerUUID(Utils.removeColor(clickedItem.getItemMeta().getDisplayName()));

            if (targetUUID.equals(playerUUID)) {
                return;
            }

            if (clickedSlot == 1) {
                if (rpgcore.getGuildManager().getGuildCoOwner(tag) != null) {
                    if (rpgcore.getGuildManager().getGuildOwner(tag).equals(playerUUID)) {
                        rpgcore.getGuildManager().removePlayerFromGuild(tag, targetUUID);
                        player.closeInventory();
                        return;
                    }
                }
            }

            rpgcore.getGuildManager().removePlayerFromGuild(tag, targetUUID);
            player.closeInventory();
            return;
        }
    }
}
