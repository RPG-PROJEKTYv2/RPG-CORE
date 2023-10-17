package rpg.rpgcore.npc.summonblade.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.summonblade.objects.SummonbladeUser;
import rpg.rpgcore.utils.Utils;

public class SummonbladeInventoryClickListener implements Listener {

    private final RPGCORE rpgcore = RPGCORE.getInstance();

    public Location bossLocation = new Location(Bukkit.getWorld("110-120map"), 23.5, 66.5, -155.5);
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Summonblade")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (slot != 4) return;
            final SummonbladeUser user = rpgcore.getSummonbladeNPC().find(player.getUniqueId());
            if (!user.isDone()) return;
            RPGCORE.getMythicMobs().getMobManager().spawnMob("110-120-PREBOSS", bossLocation);
            Bukkit.getServer().broadcastMessage(" ");
            Bukkit.getServer().broadcastMessage(Utils.format("&6&lSummonblade &8>> &eGracz &f" + player.getName() + " &eprzywolal &8&l[&4&lBOOSS&8&l] &f&lEreb'a"));
            Bukkit.getServer().broadcastMessage(" ");
            user.reset();
            player.closeInventory();
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataSummonblade(user.getUuid(), user));
        }
    }
}
