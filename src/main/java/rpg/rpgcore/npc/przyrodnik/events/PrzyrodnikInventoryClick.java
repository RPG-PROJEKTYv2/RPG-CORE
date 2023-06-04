package rpg.rpgcore.npc.przyrodnik.events;

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
import rpg.rpgcore.npc.przyrodnik.enums.PrzyrodnikMissions;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class PrzyrodnikInventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCLick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
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
            final PrzyrodnikMissions mission = PrzyrodnikMissions.getByNumber(user.getMission());

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
                return;
            }

            /*if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                int amount = 0;
                final ItemStack reqItem = mission.getItemStack().clone();
                if (player.getInventory().containsAtLeast(reqItem, mission.getReqAmount() - user.getProgress())) {
                    amount = mission.getReqAmount() - user.getProgress();
                } else {
                    for (ItemStack is : player.getInventory().getContents()) {
                        if (is != null && is.isSimilar(reqItem)) {
                            amount += is.getAmount();
                        }
                    }
                }
                reqItem.setAmount(amount);
                player.getInventory().removeItem(reqItem);
                for (int i = 0; i < amount; i++) {
                    if (ChanceHelper.getChance(mission.getAcceptPercent())) {
                        user.setProgress(user.getProgress() + 1);
                        player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &aDziekuje ci za ten przedmiot!"));
                    } else {
                        player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &cNiestety ten przedmiot sie nie nadaje!"));
                    }
                }
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataPrzyrodnik(uuid, RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid)));
                RPGCORE.getInstance().getPrzyrodnikNPC().openMainGUI(player);
                return;
            }*/

            player.getInventory().removeItem(mission.getItemStack());
            if (ChanceHelper.getChance(mission.getAcceptPercent())) {
                user.setProgress(user.getProgress() + 1);
                player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &aDziekuje ci za ten przedmiot!"));
            } else {
                player.sendMessage(Utils.format("&2&lPrzyrodnik &8>> &cNiestety ten przedmiot sie nie nadaje!"));
            }
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataPrzyrodnik(uuid, RPGCORE.getInstance().getPrzyrodnikNPC().find(uuid)));
            RPGCORE.getInstance().getPrzyrodnikNPC().openMainGUI(player);

        }
    }
}
