package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.akcesoriumBlok.*;

public class AkcesoriumPickUpListener implements Listener {
    private final RPGCORE rpgcore;

    public AkcesoriumPickUpListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickUp(final PlayerPickupItemEvent e ) {
        if (e.getItem().getItemStack().getType().equals(Material.IRON_BLOCK) && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
            e.getItem().remove();
            e.setCancelled(true);
            final String itemName = Utils.removeColor(e.getItem().getItemStack().getItemMeta().getDisplayName());
            final String expowisko = itemName.substring(itemName.lastIndexOf(" ") + 1).trim();
            for (Player server : Bukkit.getOnlinePlayers()) {
                server.sendMessage(Utils.format("&6&lDROP &8&l* &6&l" + expowisko + " &8>> &fGracz &e" + e.getPlayer().getName() + " &fznalazl &6&lAkcesoria"));
            }
            int amount = e.getItem().getItemStack().getAmount();
            if (e.getPlayer().getInventory().containsAtLeast(e.getItem().getItemStack(), 1)) {
                for (ItemStack is : e.getPlayer().getInventory().getContents()) {
                    if (is != null && is.isSimilar(e.getItem().getItemStack())) {
                        amount += is.getAmount();
                    }
                }
            }
            final double szczescie = RPGCORE.getInstance().getBonusesManager().find(e.getPlayer().getUniqueId()).getBonusesUser().getSzczescie() / 100.0;
            for (int i = 0; i < amount; i++) {
                switch (expowisko) {
                    case "1-10":
                        Akce1_10.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "10-20":
                        Akce10_20.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "20-30":
                        Akce20_30.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "30-40":
                        Akce30_40.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "40-50":
                        Akce40_50.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "50-60":
                        Akce50_60.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "60-70":
                        Akce60_70.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "70-80":
                        Akce70_80.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "80-90":
                        Akce80_90.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "90-100":
                        Akce90_100.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "100-110":
                        Akce100_110.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "110-120":
                        Akce110_120.getDrop(e.getPlayer(), szczescie);
                        break;
                    case "120-130":
                        Akce120_130.getDrop(e.getPlayer(), szczescie);
                        break;
                }
                e.getPlayer().getInventory().removeItem(e.getItem().getItemStack());
            }
        }
    }
}
