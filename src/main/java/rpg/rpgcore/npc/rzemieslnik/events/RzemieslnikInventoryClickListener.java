package rpg.rpgcore.npc.rzemieslnik.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

public class RzemieslnikInventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();
        final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());

        if (title.equals("Rzemieslnik")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (slot != 12 && slot != 14) { return; }

            if (slot == 12) {
                if (!(player.getInventory().containsAtLeast(GlobalItem.I_FRAGMENT_BONA.getItemStack(), 3) &&
                        user.getKasa() >= 200_000_000)) {
                    player.sendMessage(Utils.format("&f&lRzemieslnik &8>> &7Chyba zapomniales itemow..."));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.I_FRAGMENT_BONA.getItemStack().clone()).setAmount(3).toItemStack());
                user.setKasa(DoubleUtils.round(user.getKasa() - 200_000_000, 2));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                final int liczba = ChanceHelper.getRandInt(1, 3);
                if (liczba == 1) {
                    Bukkit.broadcastMessage(Utils.format("&f&lRzemieslnik &8>> &eGracz &6" + player.getName() + " &ewytworzyl &cBon Srednich Obrazen 5%&e!"));
                    player.getInventory().addItem(BonType.SREDNIE_5.getBon());
                } else if (liczba == 2) {
                    Bukkit.broadcastMessage(Utils.format("&f&lRzemieslnik &8>> &eGracz &6" + player.getName() + " &ewytworzyl &2Bon Sredniej Defensywy 5%&e!"));
                    player.getInventory().addItem(BonType.DEFENSYWA_5.getBon());
                } else {
                    Bukkit.broadcastMessage(Utils.format("&f&lRzemieslnik &8>> &eGracz &6" + player.getName() + " &ewytworzyl &5Bon Szansy Na Cios Krytyczny 5%&e!"));
                    player.getInventory().addItem(BonType.KRYTYK_5.getBon());
                }
            }
            if (slot == 14) {
                if (!(player.getInventory().containsAtLeast(GlobalItem.I_FRAGMENT_STALI.getItemStack(), 4) &&
                        user.getKasa() >= 3_000_000 && player.getInventory().containsAtLeast(GlobalItem.I10.getItemStack(), 1))) {
                    player.sendMessage(Utils.format("&f&lRzemieslnik &8>> &7Chyba zapomniales itemow..."));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.I_FRAGMENT_STALI.getItemStack().clone()).setAmount(4).toItemStack());
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.I10.getItemStack().clone()).setAmount(1).toItemStack());
                user.setKasa(DoubleUtils.round(user.getKasa() - 3_000_000, 2));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                player.sendMessage(Utils.format("&f&lRzemieslnik &8>> &eWytworzyles &7&lStal Kowalska&e!"));
                player.getInventory().addItem(GlobalItem.I_METAL.getItemStack());
            }
        }
    }
}
