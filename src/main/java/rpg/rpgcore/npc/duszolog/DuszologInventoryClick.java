package rpg.rpgcore.npc.duszolog;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DuszologInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public DuszologInventoryClick(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void duszologInventoryClick(final InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        final Inventory clickedInventory = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();
        final HashMap<Integer, ItemStack> itemMapToRemove = new HashMap<>();

        final String clickedInventoryTitle = clickedInventory.getTitle();
        final ItemStack clickedItem = e.getCurrentItem();
        final int clickedSlot = e.getSlot();


        // DUSZOLOG
        if (clickedInventoryTitle.equals(rpgcore.getDuszologNPC().duszologMAIN().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 10) {
                rpgcore.getDuszologNPC().craftowanieDUSZ(player);
                player.closeInventory();
            }
            if (clickedSlot == 11) {
                player.openInventory(rpgcore.getDuszologNPC().dodawanieDUSZ());
            }
            if (clickedSlot == 13) {
                rpgcore.getDuszologNPC().craftowanieKAMIENUZBROJENIA(player);
                player.closeInventory();
            }
            return;
        }
        // 0 - CZESC SETA
        // 1 - CZEK KASY
        // 2 - BRAK DODAWANIA
        if (clickedInventoryTitle.equals(rpgcore.getDuszologNPC().dodawanieKAMIENIA().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 2) {
                if (clickedItem.getType() == Material.DIAMOND_CHESTPLATE) {
                    player.sendMessage("ta juz tworzysz itd klata");
                    ItemStack is = player.getOpenInventory().getTopInventory().getItem(2);
                    player.getInventory().addItem(is);
                    player.closeInventory();
                }
            }
        }
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (player.getOpenInventory().getTitle().contains(Utils.format("&eDodaj &3Kamien &bUzbrojenia"))) {
                e.setCancelled(true);
                if (clickedItem.getType() == Material.DOUBLE_PLANT) {
                    if (clickedItem.getItemMeta().getDisplayName().contains(Utils.format("&eCzek na &6&l10 000 000.00 &2$"))) {
                        if (player.getOpenInventory().getTopInventory().getItem(1).getType().equals(Material.STAINED_GLASS_PANE)) {
                            if (String.valueOf(player.getOpenInventory().getTopInventory().getItem(0).getType()).contains("CHESTPLATE")) {
                                final ItemStack czek = e.getCurrentItem().clone();
                                czek.setAmount(1);
                                player.getInventory().removeItem(czek);
                                //player.getOpenInventory().getTopInventory().removeItem(czek);
                                //player.getOpenInventory().getTopInventory().setItem(1, czek);
                                //itemMapToRemove.put(1, czek);
                                //player.getInventory().removeItem(itemMapToRemove.get(1));

                                final ItemStack is = player.getOpenInventory().getTopInventory().getItem(0).clone();
                                final ItemMeta meta = is.getItemMeta();
                                final List<String> lore = meta.getLore();
                                int miejsce = 0;

                                if (!(lore.contains(Utils.format("&eBonusy duszologa:")))) {
                                    for (int i = 0; i < lore.size(); i++) {
                                        if (lore.get(i).contains("Thorns")) {
                                            miejsce = i +1;
                                            lore.add(" ");
                                            lore.add(Utils.format("&eBonusy duszologa:"));
                                            lore.add(Utils.format("&fBrak..."));
                                            meta.setLore(lore);
                                            is.setItemMeta(meta);
                                            player.getOpenInventory().getTopInventory().setItem(2, is);
                                            return;
                                        }
                                    }
                                } else {
                                    e.setCancelled(true);
                                }
                            } else {
                                ItemStack czek = e.getCurrentItem();
                                player.getOpenInventory().getTopInventory().setItem(1, czek);
                                itemMapToRemove.put(1, czek);
                                player.getInventory().removeItem(itemMapToRemove.get(1));
                            }
                        }
                    }
                }


                if ((String.valueOf(clickedItem.getType()).contains("CHESTPLATE")) || (String.valueOf(clickedItem.getType()).contains("HELMET"))
                        || (String.valueOf(clickedItem.getType()).contains("LEGGINGS")) || (String.valueOf(clickedItem.getType()).contains("BOOTS"))) {
                    if (player.getOpenInventory().getTopInventory().getItem(0).getType().equals(Material.STAINED_GLASS_PANE)) {
                        if (player.getOpenInventory().getTopInventory().getItem(1).getType().equals(Material.DOUBLE_PLANT)) {
                            ItemStack klata = e.getCurrentItem();
                            player.getOpenInventory().getTopInventory().setItem(0, klata);
                            itemMapToRemove.put(1, klata);
                            player.getInventory().removeItem(itemMapToRemove.get(1));

                            ItemStack is = player.getOpenInventory().getTopInventory().getItem(0).clone();
                            ItemMeta meta = is.getItemMeta();
                            List<String> lore = meta.getLore();
                            List<String> loreAfterMiejsce = new ArrayList<>();
                            int miejsce = 0;

                            if (!(lore.contains(Utils.format("&eBonusy duszologa:")))) {
                                for (int i = 0; i < lore.size(); i++) {
                                    if (lore.get(i).contains("Thorns")) {
                                        miejsce = i +1;
                                        lore.add(" ");
                                        lore.add(Utils.format("&eBonusy duszologa:"));
                                        lore.add(Utils.format("&fBrak..."));
                                        meta.setLore(lore);
                                        is.setItemMeta(meta);
                                        player.getOpenInventory().getTopInventory().setItem(2, is);
                                        return;
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                            }
                        } else {
                            ItemStack klata = e.getCurrentItem();
                            player.getOpenInventory().getTopInventory().setItem(0, klata);
                            itemMapToRemove.put(1, klata);
                            player.getInventory().removeItem(itemMapToRemove.get(1));
                        }
                    }
                }
            }
        }
    }
}
