package rpg.rpgcore.npc.zlotnik.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Dungeony;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

public class ZlotnikInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final ItemStack item = e.getCurrentItem();
        final int slot = e.getSlot();

        if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Zlotnik")) {

            if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
                if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore()) return;
                if (!(item.getType() == Material.WATCH || item.getType() == Material.ITEM_FRAME || item.getType() == Material.HOPPER_MINECART || item.getType() == Material.STORAGE_MINECART
                        || item.getType() == Material.EXPLOSIVE_MINECART)) return;
                if (item.getItemMeta().getDisplayName().contains("Wzmocnion")) return;
                if (item.getItemMeta().getLore().stream().noneMatch(s -> Utils.removeColor(s).equals("Typ: Akcesorium Podstawowe"))) return;
                if (!player.getOpenInventory().getTopInventory().getItem(2).isSimilar(RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem())) return;
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
                final ItemStack itemToGui = item.clone();
                player.getInventory().setItem(slot, null);
                player.getOpenInventory().getTopInventory().setItem(2, itemToGui);
                player.getOpenInventory().getTopInventory().setItem(6, RPGCORE.getInstance().getZlotnikNPC().getWzmocnionyItem(itemToGui));
                return;
            }
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);

            if (slot == 2) {
                if (item.isSimilar(RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem())) return;
                player.getInventory().addItem(item.clone());
                e.getClickedInventory().setItem(2, RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem());
                e.getClickedInventory().setItem(6, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 4).setName(" ").toItemStack());
                return;
            }

            if (slot == 4) {
                if (e.getClickedInventory().getItem(2).isSimilar(RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem())) return;
                final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
                if (!(player.getInventory().containsAtLeast(Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack(), 3) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 96) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 64) &&
                        player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA.getItem(), 48) &&
                        player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 64) &&
                        player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 64) &&
                        player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 64) &&
                        player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 48) &&
                        player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 48) &&
                        player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 48) &&
                        player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 48) &&
                        user.getKasa() >= 100_000_000
                )) {
                    player.sendMessage(Utils.format("&6&lZlotnik &8>> &7Czegos Ci tutaj brakuje&c..."));
                    player.closeInventory();
                    return;
                }
                player.getInventory().removeItem(new ItemBuilder(Dungeony.I_SAKIEWKA_ZE_ZLOTYM_PROSZKIEM.getItemStack().clone()).setAmount(3).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(96).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(64).toItemStack(),
                        new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(48).toItemStack(),
                        new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(64).toItemStack(),
                        new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(64).toItemStack(),
                        new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(64).toItemStack(),
                        new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(48).toItemStack(),
                        new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(48).toItemStack(),
                        new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(48).toItemStack(),
                        new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(48).toItemStack()
                );
                user.setKasa(DoubleUtils.round(user.getKasa() - 100_000_000, 2));
                RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
                RPGCORE.getInstance().getZlotnikNPC().wzmAkce(player, e.getClickedInventory().getItem(2).clone());
                e.getClickedInventory().setItem(2, RPGCORE.getInstance().getZlotnikNPC().getMiejsceItem());
                player.closeInventory();
            }
        }
    }
}
