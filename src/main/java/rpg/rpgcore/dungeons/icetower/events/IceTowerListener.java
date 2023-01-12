package rpg.rpgcore.dungeons.icetower.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dungeons.icetower.IceTowerManager;
import rpg.rpgcore.utils.Utils;

public class IceTowerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }
            if (e.getPlayer().getWorld().getName().equals("demontower")) {
                if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                    e.setCancelled(true);
                    if (e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR || !e.getPlayer().getItemInHand().hasItemMeta()
                            || !e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        e.getPlayer().sendMessage(Utils.format("&8[&c✘&8] &cMusisz miec cos w rece, zeby moc to oczyscic!"));
                        return;
                    }
                    final String type = e.getPlayer().getItemInHand().getType().toString();

                    if (!type.contains("_HELMET") && !type.contains("_CHESTPLATE") && !type.contains("_LEGGINGS") && !type.contains("_BOOTS") && !type.contains("_SWORD")) {
                        e.getPlayer().sendMessage(Utils.format("&8[&c✘&8] &cNie mozesz oczyscic tego przedmiotu!"));
                        return;
                    }

                    if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("+1") &&
                            !e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("+2") &&
                            !e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("+3")) {
                        e.getPlayer().sendMessage(Utils.format("&8[&c✘&8] &cTen Przedmiot nie wymaga oczyszczenia!"));
                        return;
                    }
                    RPGCORE.getInstance().getKowalNPC().openOczyszczanieGUI(e.getPlayer());
                    return;
                }
            }
        }
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock() == null) {
                return;
            }
            Player player = e.getPlayer();
            if (player.getWorld().getName().equals("50-60map")) {
                if (e.getClickedBlock().getType().equals(Material.LAPIS_BLOCK) && IceTowerManager.getKamienLocations().contains(e.getClickedBlock().getLocation())) {
                    if (RPGCORE.getInstance().getIceTowerManager().getHp() > 0) {
                        if (!IceTowerManager.damagers.contains(player.getUniqueId())) {
                            IceTowerManager.damagers.add(player.getUniqueId());
                        }
                        RPGCORE.getInstance().getIceTowerManager().setHp(RPGCORE.getInstance().getIceTowerManager().getHp() - 1);
                        if (RPGCORE.getInstance().getIceTowerManager().getHp() < 200) {
                            for (Player players : Bukkit.getWorld("50-60map").getPlayers()) {
                                RPGCORE.getInstance().getNmsManager().sendActionBar(players, "&bKrysztal Lodowej Wiezy &c( " + RPGCORE.getInstance().getIceTowerManager().getHp() + "HP &c)");
                            }
                        }
                    }
                    if (RPGCORE.getInstance().getIceTowerManager().getHp() == 0) {
                        IceTowerManager.startIceTower();
                        RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("&b&oKrysztal Lodowej Wiezy zostal naruszony przez: " + player.getName()));
                    }
                }
            }
        }
    }
}
