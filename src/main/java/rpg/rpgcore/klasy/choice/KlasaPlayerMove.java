package rpg.rpgcore.klasy.choice;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;

public class KlasaPlayerMove implements Listener {

    private final RPGCORE rpgcore;

    public KlasaPlayerMove(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(final PlayerMoveEvent e) {
        if (rpgcore.getklasyHelper().find(e.getPlayer().getUniqueId()) != null && rpgcore.getklasyHelper().find(e.getPlayer().getUniqueId()).getKlasaUser().getName().equals("")) {
            e.setCancelled(true);
            e.getPlayer().teleport(e.getFrom());
            rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendTitleAndSubTitle(e.getPlayer(), rpgcore.getNmsManager().makeTitle("&4&lWybierz Swoja klase!", 5, 25, 5), rpgcore.getNmsManager().makeSubTitle("&7Uzyj komendy &c/klasa &7aby wybrac swoja klase!", 5, 25, 5)));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 4));
        }
    }
}
