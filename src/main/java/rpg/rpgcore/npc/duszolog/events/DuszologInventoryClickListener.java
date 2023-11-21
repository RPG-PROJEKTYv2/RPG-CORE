package rpg.rpgcore.npc.duszolog.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.duszolog.enums.DuszologMissions;
import rpg.rpgcore.npc.duszolog.objects.DuszologUser;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class DuszologInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(e.getClickedInventory().getTitle());
        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (player.getOpenInventory().getTopInventory().getTitle().contains("Duszolog #") && e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (e.isShiftClick()) {
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
                return;
            }
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            return;
        }


        if (title.contains("Duszolog #")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (item.getType() == Material.STAINED_GLASS_PANE) return;
            final int page = Integer.parseInt(title.split("#")[1]);
            if (slot == 47 && item.getType() == Material.SKULL_ITEM) {
                RPGCORE.getInstance().getDuszologNPC().openMainGUI(player, page - 1);
                return;
            }
            if (slot == 51 && item.getType() == Material.SKULL_ITEM) {
                RPGCORE.getInstance().getDuszologNPC().openMainGUI(player, page + 1);
                return;
            }

            if (item.getType() != Material.SKULL_ITEM) return;

            final String entityName = Utils.removeColor(item.getItemMeta().getDisplayName());
            final DuszologMissions mission = DuszologMissions.getByEntityName(entityName);

            final DuszologUser user = RPGCORE.getInstance().getDuszologNPC().find(uuid);
            if (user.getCompletionMap().getOrDefault(entityName, false)) {
                player.sendMessage(Utils.format("&5&lDuszolog &8>> &cTa misja zostala juz wykonana!"));
                return;
            }
            if (user.getProgressMap().getOrDefault(entityName, 0) < mission.getReqAmount()) {
                player.sendMessage(Utils.format("&5&lDuszolog &8>> &cNie zebrales jeszcze wszystkich potrzebnych &ddusz&c!"));
                return;
            }

            user.getCompletionMap().put(entityName, true);
            user.getProgressMap().put(entityName, 0);
            user.setSilnyNaLudzi(user.getSilnyNaLudzi() + mission.getSilnyNaLudzi());
            user.setKrytyk(user.getKrytyk() + mission.getKrytyk());

            final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
            bonuses.getBonusesUser().setSilnynaludzi(bonuses.getBonusesUser().getSilnynaludzi() + mission.getSilnyNaLudzi());
            bonuses.getBonusesUser().setSzansanakryta(bonuses.getBonusesUser().getSzansanakryta() + mission.getKrytyk());
            player.sendMessage(Utils.format("&5&lDuszolog &8>> &aDziekuje Ci za te dusze &d" + entityName + "&a. Na pewno sie przydadza!"));

            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                RPGCORE.getInstance().getMongoManager().saveDataDuszolog(uuid, user);
                RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
            });
        }
    }
}
