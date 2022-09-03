package rpg.rpgcore.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.UUID;

public class EconomyPlayerInteract implements Listener {

    final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

    private final RPGCORE rpgcore;

    public EconomyPlayerInteract(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void economyInventoryClick(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (eventItem.getType().equals(Material.DOUBLE_PLANT) && eventItem.getItemMeta().getDisplayName().contains("Czek na ")) {
            final double kwotaZCzeku = Double.parseDouble(Utils.removeColor(eventItem.getItemMeta().getDisplayName()).replace("Czek na ", "").replaceAll(" ", "").replace("$", "").trim());
            final User user = rpgcore.getUserManager().find(uuid);
            final ItemStack is = e.getItem().clone();
            is.setAmount(1);
            player.getInventory().removeItem(is);
            user.setKasa(user.getKasa() + kwotaZCzeku);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zwiekszono stan twojego konta o &6" + Utils.spaceNumber(Utils.kasaFormat.format(kwotaZCzeku)) + " &2$"));
        }

    }

}
