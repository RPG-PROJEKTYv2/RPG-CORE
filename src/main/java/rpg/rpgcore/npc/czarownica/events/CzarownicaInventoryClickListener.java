package rpg.rpgcore.npc.czarownica.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.czarownica.enums.CzarownicaMissions;
import rpg.rpgcore.npc.czarownica.objects.CzarownicaUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CzarownicaInventoryClickListener implements Listener {
    private final RPGCORE rpgcore;

    public CzarownicaInventoryClickListener(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(final InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final Inventory inv = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();
        final String title = Utils.removeColor(inv.getTitle());
        final int slot = e.getSlot();
        final ItemStack item = e.getCurrentItem();

        if (title.equals("Czarownica")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            final CzarownicaUser user = rpgcore.getCzarownicaNPC().find(uuid);
            if (slot == 12) {
                rpgcore.getCzarownicaNPC().openKampania(player);
            }
            if (slot == 14) {
                if (!user.isUnlocked()) return;
                rpgcore.getCzarownicaNPC().openCraftingi(player);
            }
            return;
        }
        if (title.equals("Czarownica - Kampania")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (item == null || item.getType() == Material.STAINED_GLASS_PANE) return;

            if (item.getItemMeta().getLore().contains(Utils.format("&a&lUKONCZONE")) || item.getItemMeta().getLore().contains(Utils.format("&7Ukoncz poprzednia")))
                return;

            final CzarownicaUser user = rpgcore.getCzarownicaNPC().find(uuid);
            final CzarownicaMissions mission = Objects.requireNonNull(CzarownicaMissions.getMissionById(user.getMission()));
            final LinkedHashMap<ItemStack, Integer> missionMap = mission.getReqProgressMap(user);

            boolean canComplete = true;
            for (Map.Entry<ItemStack, Integer> entry : missionMap.entrySet()) {
                if (user.getProgressMap().get(entry.getKey()) < entry.getValue()) {
                    canComplete = false;
                    break;
                }
            }

            if (canComplete) {
                mission.addRewards(player);
                user.setMission(user.getMission() + 1);
                user.getProgressMap().clear();
                Bukkit.getServer().broadcastMessage(Utils.format("&5&lCzarownica &8>> &dGracz &5" + player.getName() + " &dukonczyl moja &5" + mission.getMissionId() + " &dmisje!"));
                player.closeInventory();
                return;
            }

            if (user.getMission() == 3) {
                final int reqAmount = missionMap.get(CzarownicaMissions.mission3Item) - user.getProgressMap().get(CzarownicaMissions.mission3Item);
                if (reqAmount <= 0) return;
                for (final ItemStack chests : player.getInventory().getContents()) {
                    if (chests == null || chests.getType() != Material.CHEST) continue;
                    if (chests.getAmount() >= reqAmount) {
                        player.getInventory().removeItem(new ItemBuilder(chests.clone()).setAmount(reqAmount).toItemStack().clone());
                        user.getProgressMap().put(CzarownicaMissions.mission3Item, user.getProgressMap().get(CzarownicaMissions.mission3Item) + reqAmount);
                    } else {
                        player.getInventory().removeItem(chests);
                        user.getProgressMap().put(CzarownicaMissions.mission3Item, user.getProgressMap().get(CzarownicaMissions.mission3Item) + chests.getAmount());
                    }
                    if (user.getProgressMap().get(CzarownicaMissions.mission3Item) >= missionMap.get(CzarownicaMissions.mission3Item)) break;
                }
                player.closeInventory();
                return;
            }

            if (user.getMission() == 5) return;
            if (user.getMission() == 7) return;

            for (Map.Entry<ItemStack, Integer> entry : missionMap.entrySet()) {
                if (!player.getInventory().containsAtLeast(entry.getKey(), 1)) continue;
                final int reqAmount = entry.getValue() - user.getProgressMap().get(entry.getKey());
                if (reqAmount <= 0) continue;
                final int itemAmount = Utils.getPlayerInventoryItemCount(player, entry.getKey());
                if (player.getInventory().containsAtLeast(entry.getKey(), reqAmount)) {
                    player.getInventory().removeItem(new ItemBuilder(entry.getKey().clone()).setAmount(reqAmount).toItemStack().clone());
                    user.getProgressMap().put(entry.getKey(), user.getProgressMap().get(entry.getKey()) + reqAmount);
                } else {
                    player.getInventory().removeItem(new ItemBuilder(entry.getKey().clone()).setAmount(itemAmount).toItemStack().clone());
                    user.getProgressMap().put(entry.getKey(), user.getProgressMap().get(entry.getKey()) + itemAmount);
                }
            }
            player.closeInventory();
            return;
        }
        if (title.equals("Czarownica - Crafting")) {
            e.setCancelled(true);
        }
    }
}
