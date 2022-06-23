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
        if (clickedInventoryTitle.equals(rpgcore.getDuszologNPC().dodawanieKAMIENIA().getName())) {
            e.setCancelled(true);
            if (clickedSlot == 2) {
                if (clickedItem.getType() == Material.DIAMOND_CHESTPLATE) {
                    player.sendMessage("ta juz tworzysz itd klata");
                    player.closeInventory();
                }
            }
        }
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (player.getOpenInventory().getTitle().contains(Utils.format("&eDodaj &3Kamien &bUzbrojenia"))) {
                e.setCancelled(true);
                player.sendMessage(clickedSlot + " - slot");
                if (!(player.getOpenInventory().getTopInventory().getItem(0).getType().equals(Material.STAINED_GLASS_PANE))) {
                    player.sendMessage("test");

                    ItemStack is = player.getOpenInventory().getTopInventory().getItem(0).clone();
                    ItemMeta meta = is.getItemMeta();

                    List<String> lore = meta.getLore();

                    int miejsce = 0;

                    if (!(lore.contains("Miejsce"))) {
                        for (int i = 0; i < lore.size(); i++) {
                            if (lore.get(i).contains("Thorns")) {
                                miejsce = i;
                                break;
                            }
                        }
                    } else {
                        for (int i = 0; i < lore.size(); i++) {
                            if (lore.get(i).contains("Miejsce")) {
                                miejsce = i;
                            }
                        }
                    }

                    //Obrona: 100
                    //Throns: 70

                    //Obrona: 100
                    //Throns: 70
                    //
                    //Miejsce: ...
                    //
                    //Dodakowa Odpornosc: ...
                    //Dodatkowy Exp: ...
                    //DOdatkowy dmg: ...



                    List<String> loreAfterMiejsce = new ArrayList<>();

                    for (int i = miejsce + 1; i < lore.size(); i++) {
                        loreAfterMiejsce.add(lore.get(miejsce));
                        lore.remove(miejsce);
                    }
                    if (!(lore.contains("Miejsce"))) {
                        lore.add(" ");
                        lore.add("Miejsce na cos tam:");
                    } else {
                        lore.add("Miejsce na cos tam:");
                    }

                    lore.addAll(loreAfterMiejsce);

                    meta.setLore(lore);
                    is.setItemMeta(meta);

                    player.getOpenInventory().getTopInventory().setItem(2, is);

                    return;
                }
                if (String.valueOf(clickedItem.getType()).contains("CHESTPLATE")) {
                    ItemStack klata = e.getCurrentItem();

                    player.getOpenInventory().getTopInventory().setItem(0, klata);
                    itemMapToRemove.put(1, klata);
                    player.getInventory().removeItem(itemMapToRemove.get(1));
                }
            }
        }

    }

}
