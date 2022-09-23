package rpg.rpgcore.npc.lowca;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class LowcaInventoryClick implements Listener {
    private final RPGCORE rpgcore;
    public LowcaInventoryClick(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory inv = e.getClickedInventory();
        final String title = Utils.removeColor(inv.getTitle());
        final Player player = (Player) e.getWhoClicked();
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Lowca")) {
            e.setCancelled(true);
            final UUID uuid = player.getUniqueId();
            final LowcaObject object = rpgcore.getLowcaNPC().find(uuid);
            final LowcaUser user = object.getLowcaUser();

            if (item.getType().equals(Material.BARRIER) || item.getType().equals(Material.BOOK_AND_QUILL) || Utils.checkIfLoreContainsString(item.getItemMeta().getLore(), "Wykonane")) {
                return;
            }

            final LowcaMissions mission = LowcaMissions.getMission(slot + 1);
            final ItemStack reqItem = mission.getReqItem();
            final int reqAmount = mission.getReqAmount();

            if (user.getProgress() == reqAmount) {
                final Bonuses bonuses = rpgcore.getBonusesManager().find(uuid);
                user.setProgress(0);
                user.setMission(user.getMission() + 1);
                user.setSzczescie(user.getSzczescie() + mission.getSzczescie());
                user.setSzybkosc(user.getSzybkosc() + mission.getSzybkosc());
                user.setTruedmg(user.getTruedmg() + mission.getTruedmg());

                bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + mission.getSzczescie());
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + mission.getSzybkosc());
                bonuses.getBonusesUser().setTruedamage(bonuses.getBonusesUser().getTruedamage() + mission.getTruedmg());
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataLowca(object.getId(), object);
                    rpgcore.getMongoManager().saveDataBonuses(bonuses.getId(), bonuses);
                });
                rpgcore.getServer().broadcastMessage(Utils.format("&4&lLowca &8>> &7Gracz &c" + player.getName() + " &7wykonal moja &c" + (user.getMission() - 1) + " &7misje!"));
                rpgcore.getLowcaNPC().openLowcaGUI(player);
                return;
            }

            if (!player.getInventory().containsAtLeast(reqItem, 1)) {
                player.getInventory().addItem(reqItem);
                return;
            }

            player.getInventory().removeItem(reqItem);
            user.setProgress(user.getProgress() + 1);
            player.sendMessage(Utils.format("&4&lLowca &8>> &7Dziekuje Ci za to trofeum!"));
            rpgcore.getLowcaNPC().openLowcaGUI(player);
        }
    }
}
