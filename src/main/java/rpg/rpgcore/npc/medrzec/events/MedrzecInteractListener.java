package rpg.rpgcore.npc.medrzec.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.medrzec.objects.MedrzecUser;
import rpg.rpgcore.utils.ItemBuilder;
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

        final MedrzecUser user = rpgcore.getMedrzecNPC().find(e.getPlayer().getUniqueId());
        final Bonuses bonuses = rpgcore.getBonusesManager().find(e.getPlayer().getUniqueId());

        if (e.getItem() != null && e.getItem().getType() == Material.INK_SACK && e.getItem().getDurability() == 1 &&
                e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() &&
                Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Rubinowe Serce"))  {
            if (user.getBonus() == 40) return;
            if (user.getBonus() > 20) {
                e.getPlayer().sendMessage(Utils.format("&6&lMedrzec &8>> &bSzafir &7to to, czego teraz potrzebuje!"));
                return;
            }
            user.setBonus(user.getBonus() + 1);
            bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 1);
            e.getPlayer().setMaxHealth(e.getPlayer().getMaxHealth() + 1);
            e.getPlayer().getInventory().removeItem(new ItemBuilder(GlobalItem.RUBINOWE_SERCE.getItemStack().clone()).setAmount(1).toItemStack());
            e.getPlayer().sendMessage(Utils.format("&a&lPomyslnie uzyto rubinowego serca!"));
            user.save();
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses));
            return;
        }
        if (e.getItem() != null && e.getItem().getType() == Material.INK_SACK && e.getItem().getDurability() == 6 &&
                e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() &&
                Utils.removeColor(e.getItem().getItemMeta().getDisplayName()).equals("Szafirowe Serce")) {
            if (user.getBonus() == 40) return;
            if (user.getBonus() < 20) {
                e.getPlayer().sendMessage(Utils.format("&6&lMedrzec &8>> &cRubin &7to to, czego teraz potrzebuje!"));
                return;
            }
            user.setBonus(user.getBonus() + 1);
            bonuses.getBonusesUser().setDodatkowehp(bonuses.getBonusesUser().getDodatkowehp() + 1);
            e.getPlayer().setMaxHealth(e.getPlayer().getMaxHealth() + 1);
            e.getPlayer().getInventory().removeItem(new ItemBuilder(GlobalItem.SZAFIROWE_SERCE.getItemStack().clone()).setAmount(1).toItemStack());
            e.getPlayer().sendMessage(Utils.format("&a&lPomyslnie uzyto szafirowego serca!"));
            user.save();
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses));
        }
    }
}
