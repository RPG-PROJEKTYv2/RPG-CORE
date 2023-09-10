package rpg.rpgcore.npc.mistyczny_kowal.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.mistyczny_kowal.enums.SwordType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

public class MistycznyKowalInventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Mistyczny Kowal")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (slot == 11) {
                final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
                if (!(player.getInventory().containsAtLeast(GlobalItem.RUDA_MITHRYLU.getItemStack(), 5) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 16) &&
                        player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 20) &&
                        player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 20) &&
                        player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 16) &&
                        player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 16) &&
                        user.getKasa() >= 10_000_000
                )) {
                    player.sendMessage(Utils.format("&7&lMistyczny Kowal &8>> &fCzegos Ci tutaj brakuje&c..."));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.RUDA_MITHRYLU.getItemStack().clone()).setAmount(5).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(20).toItemStack(),
                        new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(20).toItemStack(),
                        new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(16).toItemStack()
                );
                user.setKasa(DoubleUtils.round(user.getKasa() - 10_000_000, 2));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                RPGCORE.getInstance().getMistycznyKowalManager().getRandomMiecz(player, SwordType.TYRA);
                return;
            }
            if (slot == 15) {
                final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
                if (!(player.getInventory().containsAtLeast(GlobalItem.RUDA_MITHRYLU.getItemStack(), 5) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 16) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 16) &&
                        player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 24) &&
                        player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 20) &&
                        player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 20) &&
                        player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 16) &&
                        player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 16) &&
                        user.getKasa() >= 10_000_000
                )) {
                    player.sendMessage(Utils.format("&7&lMistyczny Kowal &8>> &fCzegos Ci tutaj brakuje&c..."));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(GlobalItem.RUDA_MITHRYLU.getItemStack().clone()).setAmount(5).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(24).toItemStack(),
                        new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(20).toItemStack(),
                        new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(20).toItemStack(),
                        new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(16).toItemStack(),
                        new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(16).toItemStack()
                );
                user.setKasa(DoubleUtils.round(user.getKasa() - 10_000_000, 2));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                RPGCORE.getInstance().getMistycznyKowalManager().getRandomMiecz(player, SwordType.KS);
            }

        }
    }
}
