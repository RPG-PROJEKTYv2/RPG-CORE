package rpg.rpgcore.armor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ArmorEffectTask implements Runnable {


    public ArmorEffectTask(final RPGCORE rpgcore) {
        rpgcore.getServer().getScheduler().scheduleSyncRepeatingTask(rpgcore, this, 0L, 60L);
    }

    @Override
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            System.out.println("ArmorEffectTask.run");
            System.out.println("prot (helm) = " + Utils.getTagInt(player.getInventory().getHelmet(), "prot"));
            System.out.println("prot (chest) = " + Utils.getTagInt(player.getInventory().getChestplate(), "prot"));
            System.out.println("prot (legs) = " + Utils.getTagInt(player.getInventory().getLeggings(), "prot"));
            System.out.println("prot (boots) = " + Utils.getTagInt(player.getInventory().getBoots(), "prot"));
            if (player.getInventory().getHelmet() != null) ArmorEffectsHelper.addEffectHelmet(player, Utils.getTagInt(player.getInventory().getHelmet(), "prot"));
            if (player.getInventory().getLeggings() != null) ArmorEffectsHelper.addEffectLeggings(player, Utils.getTagInt(player.getInventory().getLeggings(), "prot"));
            if (player.getInventory().getBoots() != null) ArmorEffectsHelper.addEffectBoots(player, Utils.getTagInt(player.getInventory().getBoots(), "prot"));
        }
    }
}
