package rpg.rpgcore.mythicstick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Set;

public class MythicstickPlayerInteract implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void mythicstickPlayerInteract(final PlayerInteractEvent e) {

        final ItemStack eventItem = e.getItem();
        final Player player = e.getPlayer();


        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (eventItem == null) {
                return;
            }
            if (eventItem.getType() == Material.STICK) {
                if (eventItem.getItemMeta().getDisplayName() == null || eventItem.getItemMeta().getLore() == null) {
                    return;
                }
                if (eventItem.getItemMeta().getDisplayName().contains(Utils.format("&6&lMythic &4&lSTICK"))) {
                    if (!player.hasPermission("dev.rpg.mythicstick")) {
                        e.setCancelled(true);
                        player.getInventory().removeItem(eventItem);
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cPosiadales w swoim ekwipunku przedmiot &4Developerski &cw zwiazku z tym zostal on usuniety. &7(&6&lMythic &4&lSTICK&7)"));
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cJezeli uwazasz ze to blad, zglos sie do wyzszej administracji z ss'em tej wiadomosci"));
                        return;
                    }

                    Block b = player.getTargetBlock((Set<Material>) null, 150);

                    if (b.getType().equals(Material.AIR)) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie mozesz ustawic spawnera w powietrzu"));
                        return;
                    }
                    final String mobName = Utils.removeColor(eventItem.getItemMeta().getLore().get(0)).replace("Aktualnie ustawiasz spawner mobow:", "").replace(" ", "");
                    final String spawnerName = Utils.removeColor(eventItem.getItemMeta().getLore().get(1)).replace("Nazwa Spawnera:", "").replace(" ", "");
                    player.sendMessage(mobName + " " + spawnerName);
                    Bukkit.getServer().dispatchCommand(player, "mm s create " + spawnerName + " " + mobName  + " " + b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ());
                    Bukkit.getServer().dispatchCommand(player, "mm s set " + spawnerName + " cooldown 15");
                    int spawnerCount = Integer.parseInt(Utils.removeColor(eventItem.getItemMeta().getLore().get(1)).replace("Nazwa Spawnera:", "").replace(mobName + "-RESP-", "").replace(" ", ""));
                    player.sendMessage(spawnerCount + "");
                    spawnerCount++;
                    ItemMeta meta = eventItem.getItemMeta();
                    meta.setLore(Arrays.asList(Utils.format("&7Aktualnie ustawiasz spawner mobow: &c" + mobName), Utils.format("&7Nazwa Spawnera: &c" + mobName + "-RESP-" + spawnerCount)));
                    eventItem.setItemMeta(meta);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aUstawiles spawner mobow: &c" + mobName));
                }
            }
        }
    }
}
