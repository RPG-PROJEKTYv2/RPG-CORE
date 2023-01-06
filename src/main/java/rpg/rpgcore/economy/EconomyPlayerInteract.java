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
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class EconomyPlayerInteract implements Listener {


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

        if (eventItem == null) {
            return;
        }

        if (eventItem.getType().equals(Material.DOUBLE_PLANT) && eventItem.getItemMeta().hasDisplayName() && eventItem.getItemMeta().getDisplayName().contains("Czek na ")) {
            final ItemStack is = e.getItem().clone();
            if (!player.isSneaking()) {
                is.setAmount(1);
            }
            final double kwotaZCzeku = DoubleUtils.round(Double.parseDouble(Utils.removeColor(eventItem.getItemMeta().getDisplayName()).replace("Czek na ", "").replaceAll(" ", "").replace("$", "").trim()) * is.getAmount(), 2);
            final User user = rpgcore.getUserManager().find(uuid);
            player.getInventory().removeItem(is);
            user.setKasa(user.getKasa() + kwotaZCzeku);
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zwiekszono stan twojego konta o &6" + Utils.spaceNumber(Utils.kasaFormat.format(kwotaZCzeku)) + " &2$"));
        }
        if (eventItem.getType() == Material.GOLD_NUGGET && eventItem.getItemMeta().hasDisplayName() && Utils.removeColor(eventItem.getItemMeta().getDisplayName()).contains("HS")) {
            final int hells = Utils.getTagInt(eventItem, "value");
            if (hells == 0) {
                player.getInventory().removeItem(eventItem);
                return;
            }
            int amount = 1;
            final User user = rpgcore.getUserManager().find(uuid);
            if (player.isSneaking()) {
                amount = eventItem.getAmount();
            }
            player.getInventory().removeItem(new ItemBuilder(eventItem.clone()).setAmount(amount).toItemStack());
            user.setHellcoins(user.getHellcoins() + (hells * amount));
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataUser(uuid, user));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zwiekszono stan twojego konta o &6" + Utils.spaceNumber(String.valueOf((hells * amount))) + " &4&lH&6&lS"));
        }

    }

}
