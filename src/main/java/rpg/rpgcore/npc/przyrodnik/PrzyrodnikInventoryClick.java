package rpg.rpgcore.npc.przyrodnik;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PrzyrodnikInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Przyrodnik")) {
            e.setCancelled(true);

            if (slot != 13) {
                return;
            }

            if (item.getType().equals(Material.BARRIER)) {
                return;
            }

            final PrzyrodnikUser user = RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid).getUser();
            final Missions mission = Missions.getByNumber(user.getMission());

            if (user.getProgress() >= mission.getReqAmount()) {
                user.setMission(user.getMission() + 1);
                user.setProgress(0);
                user.setDmg(user.getDmg() + mission.getDmg());
                user.setDef(user.getDef() + mission.getDef());
                final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(uuid);
                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + mission.getDmg());
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + mission.getDef());
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> {
                    RPGCORE.getInstance().getMongoManager().saveDataPrzyrodnik(uuid, RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid));
                    RPGCORE.getInstance().getMongoManager().saveDataBonuses(uuid, bonuses);
                });
                RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&2&lPrzyrodnik &8>> &6" + player.getName() + " &7ukonczyl moja &6" + user.getMission() + " &7misje "));
                RPGCORE.getInstance().getPrzyrodnikNPC().openMainGUI(player);
                return;
            }

            if (!player.getInventory().containsAtLeast(mission.getItemStack(), 1)) {
                player.getInventory().addItem(mission.getItemStack());
                return;
            }

            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                int amount = 0;
                final ItemStack reqItem = mission.getItemStack().clone();
                if (player.getInventory().containsAtLeast(reqItem, mission.getReqAmount() - user.getProgress())) {
                    amount = mission.getReqAmount() - user.getProgress();
                } else {
                    for (ItemStack is : player.getInventory().getContents()) {
                        if (is.isSimilar(reqItem)) {
                            amount += is.getAmount();
                        }
                    }
                }
                reqItem.setAmount(amount);
                player.getInventory().removeItem(reqItem);
                user.setProgress(user.getProgress() + amount);
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataPrzyrodnik(uuid, RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid)));
                player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &7Pomyslnie oddales/as &6" + amount + " &7" + mission.getItemStack().getItemMeta().getDisplayName() + " &7do misji"));
                RPGCORE.getInstance().getPrzyrodnikNPC().openMainGUI(player);
                return;
            }

            player.getInventory().removeItem(mission.getItemStack());
            user.setProgress(user.getProgress() + 1);
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataPrzyrodnik(uuid, RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid)));
            player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &7Pomyslnie oddales/as &6" + 1 + " &7" + mission.getItemStack().getItemMeta().getDisplayName() + " &7do misji"));
            RPGCORE.getInstance().getPrzyrodnikNPC().openMainGUI(player);

        }
    }
}
