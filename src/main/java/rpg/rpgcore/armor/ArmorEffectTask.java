package rpg.rpgcore.armor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ArmorEffectTask implements Runnable {

    private final RPGCORE rpgcore;


    public ArmorEffectTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 0L, 60L);
    }

    @Override
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().getHelmet() != null) ArmorEffectsHelper.addEffectHelmet(player, Utils.getTagInt(player.getInventory().getHelmet(), "prot"));
            if (player.getInventory().getLeggings() != null) ArmorEffectsHelper.addEffectLeggings(player, Utils.getTagInt(player.getInventory().getLeggings(), "prot"));
            if (player.getInventory().getBoots() != null) ArmorEffectsHelper.addEffectBoots(player, Utils.getTagInt(player.getInventory().getBoots(), "prot"));
        }
    }
}
