package rpg.rpgcore.klasy.choice;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.objects.KlasaUser;
import rpg.rpgcore.klasy.objects.Klasy;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class KlasyInventoryClick implements Listener {

    private final RPGCORE rpgcore;

    public KlasyInventoryClick(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(final InventoryClickEvent e) {
        final Inventory gui = e.getClickedInventory();
        final Player player = (Player) e.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (e.getClickedInventory() == null) {
            return;
        }

        final String title = gui.getTitle();
        final int slot = e.getSlot();

        if (Utils.removeColor(title).equals("Klasy")) {
            e.setCancelled(true);
            if (rpgcore.getklasyHelper().find(uuid).getKlasaUser() == null) {
                rpgcore.getklasyHelper().add(new Klasy(uuid));
            }
            final KlasaUser user = rpgcore.getklasyHelper().find(uuid).getKlasaUser();
            switch (slot) {
                case 0:
                    user.setName("Wojownik");
                    player.getInventory().addItem(new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName("&cSzal Bitewny").hideFlag().toItemStack().clone());
                    break;
                case 1:
                    return;
                case 2:
                    user.setName("Obronca");
                    player.getInventory().addItem(new ItemBuilder(Material.INK_SACK, 1, (short) 10).setName("&aPrzysiega Rycerska").hideFlag().toItemStack().clone());
                    break;
                case 3:
                    return;
                case 4:
                    user.setName("Mag");
                    player.getInventory().addItem(new ItemBuilder(Material.BLAZE_ROD).setName("&dRozdzka Czarodzieja").hideFlag().toItemStack().clone());
                    break;
            }
            user.setMission("");
            user.setMissionProgress(0);
            user.setLvl(0);
            user.setExp(0);
            user.setGlobalPoints(0);
            user.setPoints(0);
            user.setValue1(0);
            user.setValue2(0);
            user.setValue3(0);
            user.setValue4(0);
            user.setValue5(0);
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aWybrales klase: &6Wojownik"));
            player.getActivePotionEffects().clear();
            player.closeInventory();
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getMongoManager().saveKlasyData(uuid, rpgcore.getklasyHelper().find(uuid)));
        }
    }
}
