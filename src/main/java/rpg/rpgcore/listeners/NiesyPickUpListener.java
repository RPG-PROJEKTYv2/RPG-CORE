package rpg.rpgcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.osiagniecia.objects.OsUser;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.niesy.*;

public class NiesyPickUpListener implements Listener {
    private final RPGCORE rpgcore;

    public NiesyPickUpListener(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickUp(final PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().getType().equals(Material.DIAMOND_BLOCK) && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
            e.getItem().remove();
            e.setCancelled(true);
            final String itemName = Utils.removeColor(e.getItem().getItemStack().getItemMeta().getDisplayName());
            final String expowisko = itemName.substring(itemName.lastIndexOf(" ") + 1).trim();
            final OsUser osUser = rpgcore.getOsManager().find(e.getPlayer().getUniqueId());
            osUser.setNiesyProgress(osUser.getNiesyProgress() + 1);
            Bukkit.broadcastMessage(Utils.format("&6&lDROP " + expowisko + " &8>> &fGracz &b" + e.getPlayer().getName() + " &fznalazl &b&lNiesamowity Przedmiot"));

            int amount = e.getItem().getItemStack().getAmount();

            if (e.getPlayer().getInventory().containsAtLeast(e.getItem().getItemStack(), 1)) {
                for (ItemStack is : e.getPlayer().getInventory().getContents()) {
                    if (is != null && is.isSimilar(e.getItem().getItemStack())) {
                        amount += is.getAmount();
                    }
                }
            }
            final double szcescie = RPGCORE.getInstance().getBonusesManager().find(e.getPlayer().getUniqueId()).getBonusesUser().getSzczescie() / 100.0;
            for (int i = 0; i < amount; i++) {
                switch (expowisko) {
                    case "1-10":
                        Map1_10.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "10-20":
                        Map10_20.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "20-30":
                        Map20_30.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "30-40":
                        Map30_40.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "40-50":
                        Map40_50.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "50-60":
                        Map50_60.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "60-70":
                        Map60_70.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "70-80":
                        Map70_80.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "80-90":
                        Map80_90.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "90-100":
                        Map90_100.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "100-110":
                        Map100_110.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "110-120":
                        Map110_120.getDrop(e.getPlayer(), szcescie);
                        break;
                    case "120-130":
                        Map120_130.getDrop(e.getPlayer(), szcescie);
                        break;
                }
                e.getPlayer().getInventory().removeItem(e.getItem().getItemStack());
            }
        }
    }
}
