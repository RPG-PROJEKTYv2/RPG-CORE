package rpg.rpgcore.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.akcesoriumOLD.*;

public class AkcesoriumPickUpListener implements Listener {

    public AkcesoriumPickUpListener() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickUp(final PlayerPickupItemEvent e ) {
        if (e.getItem().getItemStack().getType().equals(Material.IRON_BLOCK) && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
            e.getItem().remove();
            e.setCancelled(true);
            final String itemName = Utils.removeColor(e.getItem().getItemStack().getItemMeta().getDisplayName());
            final String expowisko = itemName.substring(itemName.lastIndexOf(" ") + 1).trim();
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
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l1-10 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &8&lZaginione Akcesorium"));
                        break;
                    case "10-20":
                        Akce10_20.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l10-20 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &2&lZielone Akcesorium"));
                        break;
                    case "20-30":
                        Akce20_30.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l20-30 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &6&lTropikalne Akcesorium"));
                        break;
                    case "30-40":
                        Akce30_40.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l30-40 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &c&lPrzeklete Akcesorium"));
                        break;
                    case "40-50":
                        Akce40_50.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l40-50 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &b&lPradawne Akcesorium"));
                        break;
                    case "50-60":
                        Akce50_60.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l50-60 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &f&lSniezne Akcesorium"));
                        break;
                    case "60-70":
                        Akce60_70.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l60-70 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &c&lOgniste Akcesorium"));
                        break;
                    case "70-80":
                        Akce70_80.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l70-80 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &7&lMgliste Akcesorium"));
                        break;
                    case "80-90":
                        Akce80_90.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l80-90 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &e&lSloneczne Akcesorium"));
                        break;
                    case "90-100":
                        Akce90_100.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l90-100 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &9&lSkradzione Akcesorium"));
                        break;
                    case "100-110":
                        Akce100_110.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l100-110 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &b&lMityczne Akcesorium"));
                        break;
                    case "110-120":
                        Akce110_120.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l110-120 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &3&lSzkarlatne Akcesorium"));
                        break;
                    case "120-130":
                        Akce120_130.getDrop(e.getPlayer(), szczescie);
                        Bukkit.getServer().broadcastMessage(Utils.format("&6&lDROP &8* &6&l120-130 &8>> &fGracz " + e.getPlayer().getName() + " &fznalazl &9&lStarozytne Akcesorium"));
                        break;
                }
                e.getPlayer().getInventory().removeItem(e.getItem().getItemStack());
            }
        }
    }
}