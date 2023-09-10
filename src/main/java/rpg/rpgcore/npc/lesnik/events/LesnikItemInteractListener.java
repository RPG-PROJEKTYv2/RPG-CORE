package rpg.rpgcore.npc.lesnik.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.lesnik.objects.LesnikObject;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

public class LesnikItemInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;


        final ItemStack item = e.getItem();

        if (item == null) return;
        if (!item.isSimilar(LesnikItems.POTION.getItem())) return;
        final LesnikObject object = RPGCORE.getInstance().getLesnikNPC().find(e.getPlayer().getUniqueId());
        if (object == null) return;
        if (!object.getUser().hasCooldown()) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().sendMessage(Utils.format("&2&lLesnik &8>> &7Chyba Ci sie cos pomylilo... przeciez mozesz oddac mi juz przedmiot!"));
            return;
        }

        e.setCancelled(true);
        e.setUseItemInHand(Event.Result.DENY);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.getPlayer().getInventory().removeItem(new ItemBuilder(LesnikItems.POTION.getItem()).setAmount(1).toItemStack().clone());
        object.getUser().setCooldown(0L);
        e.getPlayer().sendMessage(Utils.format("&2&lLesnik &8>> &aSlyszalem ze masz cos dla mnie!"));
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataLesnik(object.getId(), object));
    }

}
