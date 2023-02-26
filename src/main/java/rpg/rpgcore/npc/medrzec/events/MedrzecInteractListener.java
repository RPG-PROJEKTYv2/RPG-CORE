package rpg.rpgcore.npc.medrzec.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class MedrzecInteractListener implements Listener {
    private final RPGCORE rpgcore;

    public MedrzecInteractListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;

        if (e.getItem() != GlobalItem.getItem("RUBINOWE_SERCE", 1)) return;

        final MedrzecUser user = rpgcore.getMedrzecNPC().find(e.getPlayer().getUniqueId());

        if (user.getBonus() == 40) return;

        user.setBonus(user.getBonus() + 1);
        e.getPlayer().getInventory().removeItem(GlobalItem.getItem("RUBINOWE_SERCE", 1));
        e.getPlayer().sendMessage(Utils.format("&a&lPomyslnie uzyt rubinowego serca!"));
        user.save();
    }
}
