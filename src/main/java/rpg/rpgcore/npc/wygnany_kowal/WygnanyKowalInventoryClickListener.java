package rpg.rpgcore.npc.wygnany_kowal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

public class WygnanyKowalInventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onWygnanyKowalClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getInventory() == null) {
            return;
        }

        final String title = Utils.removeColor(gui.getTitle());
        final int slot = e.getSlot();

        if (title.equals("Wygnany Kowal")) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            if (slot != 4) return;
            if (player.getItemInHand() == null || !player.getItemInHand().getType().toString().contains("_SWORD") || !player.getItemInHand().hasItemMeta()
                    || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().hasLore()) {
                player.sendMessage(Utils.format("&3&lWygnany Kowal &8>> &cMozesz wzmocnic tylko &9&lMithrylowe Ostrze &club &9&lMithrylowy Sztylet&c!"));
                player.closeInventory();
                return;
            }
            if (!(Utils.getTagString(player.getItemInHand(), "type").equals("ks") || Utils.getTagString(player.getItemInHand(), "type").equals("tyra"))) {
                player.sendMessage(Utils.format("&3&lWygnany Kowal &8>> &cMozesz wzmocnic tylko &9&lMithrylowe Ostrze &club &9&lMithrylowy Sztylet&c!"));
                player.closeInventory();
            }
            if (!(player.getItemInHand().getItemMeta().getDisplayName().contains("Mithrylowe Ostrze") || player.getItemInHand().getItemMeta().getDisplayName().contains("Mithrylowy Sztylet"))) {
                player.sendMessage(Utils.format("&3&lWygnany Kowal &8>> &cMozesz wzmocnic tylko &9&lMithrylowe Ostrze &club &9&lMithrylowy Sztylet&c!"));
                player.closeInventory();
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().contains("Wzmocniony") || player.getItemInHand().getItemMeta().getDisplayName().contains("Wzmocnione")) {
                player.sendMessage(Utils.format("&3&lWygnany Kowal &8>> &cTen miecz zostal juz wzmocniony!"));
                player.closeInventory();
                return;
            }
            final User user = RPGCORE.getInstance().getUserManager().find(player.getUniqueId());
            if (!(player.getInventory().containsAtLeast(GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack(), 16) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_SZATAROZBOJNIKA.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_OKOGOBLINA.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_SKORAGORYLA.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_ZLAMANAKOSC.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_LZAOCEANU.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_WILCZEFUTRO.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_OGNISTYPYL.getItem(), 128) &&
                    player.getInventory().containsAtLeast(Ulepszacze.I_TRUJACAROSLINA.getItem(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I12.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I13.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I14.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I15.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I16.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I17.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(GlobalItem.I18.getItemStack(), 128) &&
                    player.getInventory().containsAtLeast(Bossy.I1_10.getItemStack(), 24) &&
                    player.getInventory().containsAtLeast(Bossy.I10_20.getItemStack(), 16) &&
                    player.getInventory().containsAtLeast(Bossy.I40_50.getItemStack(), 8) &&
                    player.getInventory().containsAtLeast(Bossy.I60_70.getItemStack(), 4) &&
                    user.getKasa() >= 250_000_000
            )) {
                player.sendMessage(Utils.format("&3&lWygnany Kowal &8>> &7Czegos Ci tutaj brakuje&c..."));
                player.closeInventory();
                return;
            }
            player.getInventory().removeItem(new ItemBuilder(GlobalItem.I_ODLAMEK_ZAKLETEJ_DUSZY.getItemStack().clone()).setAmount(16).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_SZATAROZBOJNIKA.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_OKOGOBLINA.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_SKORAGORYLA.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_ZLAMANAKOSC.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_LZAOCEANU.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_WILCZEFUTRO.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_OGNISTYPYL.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Ulepszacze.I_TRUJACAROSLINA.getItem().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I12.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I13.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I14.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I15.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I16.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I17.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(GlobalItem.I18.getItemStack().clone()).setAmount(128).toItemStack(),
                    new ItemBuilder(Bossy.I1_10.getItemStack().clone()).setAmount(24).toItemStack(),
                    new ItemBuilder(Bossy.I10_20.getItemStack().clone()).setAmount(16).toItemStack(),
                    new ItemBuilder(Bossy.I40_50.getItemStack().clone()).setAmount(8).toItemStack(),
                    new ItemBuilder(Bossy.I60_70.getItemStack().clone()).setAmount(4).toItemStack()
            );
            user.setKasa(DoubleUtils.round(user.getKasa() - 250_000_000, 2));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(user.getId(), user));
            RPGCORE.getInstance().getWygnanyKowalManager().upgradeSword(player.getItemInHand());
            Bukkit.getServer().broadcastMessage(Utils.format("&3&lWygnany Kowal &8>> &7&lGracz &6&l" + player.getName() + " &7&lwzmocnil swoj miecz!"));
        }
    }
}
