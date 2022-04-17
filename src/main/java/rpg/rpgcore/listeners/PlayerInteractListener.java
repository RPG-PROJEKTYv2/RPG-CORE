package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final RPGCORE rpgcore;

    public PlayerInteractListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }
    private ItemBuilder test = new ItemBuilder(Material.ITEM_FRAME, 1);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        ItemStack itemToSet;

        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem().equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }

            if (e.getItem().getType() == Material.ITEM_FRAME) {

                if (e.getItem().getItemMeta().getDisplayName() == null) {
                    test.setName("&7&lTestowa Tarcza");
                    List<String> testlore = new ArrayList<>();
                    testlore.add(" ");
                    testlore.add("&3Obrona: &f45%");
                    testlore.add("&3Blok Ciosu: &f25$");
                    testlore.add("&3Obrazenia: &f120");
                    testlore.add(" ");
                    testlore.add("&c&oWymagany Poziom: &640");
                    test.setLore(testlore);

                    player.getInventory().addItem(test.toItemStack());
                    return;
                }

                if (e.getItem().getItemMeta().getDisplayName().contains("Tarcza")) {

                    if (rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).getItem(10).getType() != Material.BARRIER){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Masz juz zalazona &cTarcze"));
                        return;
                    }
                    itemToSet = e.getItem();

                    if (e.getItem().getAmount() != 1){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz zalozyc wiecej niz jednego przedmiotu"));
                        return;
                    }

                    if (rpgcore.getPlayerManager().getPlayerLvl(uuid) < this.getItemLvlReq(itemToSet)){
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie posiadasz wymaganego lvl'a zeby zalozyc ten przedmiot"));
                        return;
                    }
                    rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid).setItem(10, itemToSet);
                    player.getInventory().remove(itemToSet);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zalozono " + itemToSet.getItemMeta().getDisplayName()));
                    return;
                }
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz tego zalozyc :)"));
                return;
            }

            if (e.getItem().getType() == Material.STORAGE_MINECART) {

            }

            if (e.getItem().getType() == Material.HOPPER_MINECART) {

            }

            if (e.getItem().getType() == Material.EXPLOSIVE_MINECART) {

            }

            if (e.getItem().getType() == Material.MINECART) {

            }

            if (e.getItem().getType() == Material.WATCH) {

            }

            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }

            if (e.getItem().equals(rpgcore.getBaoManager().getItemDoZmianki())) {
                if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 80) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Musisz posiadac minimum &c80 &7poziom, zeby uzywac &4&lKsiegi Magii"));
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                player.openInventory(rpgcore.getBaoManager().ksiegaMagiiGUI(player.getUniqueId()));
                return;
            }
        }

    }


    private int getItemLvlReq(final ItemStack item) {
        int wymagany = 0;

        for (String s : item.getItemMeta().getLore()) {
            if (s.contains("Wymagany Poziom: ")){
                wymagany = Integer.parseInt(Utils.removeColor(s).replace("Wymagany Poziom: ", "").trim());
            }
        }

        return wymagany;
    }
}
