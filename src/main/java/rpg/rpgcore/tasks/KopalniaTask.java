package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.ore.Ore;
import rpg.rpgcore.utils.Utils;

import java.util.Random;

public class KopalniaTask implements Runnable{
    private final RPGCORE rpgcore;
    public KopalniaTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 100, 3600);
    }

    @Override
    public void run() {
        rpgcore.getOreManager().getOreLocations().forEach(ore -> {
            this.setRandomOreMaterial(ore);
            Bukkit.getWorld(ore.getWorld()).getBlockAt(ore.getLocation()).setType(ore.getOreMaterial());
            ore.setHp(ore.getMaxHp());
        });
        Bukkit.broadcastMessage(Utils.format("&7&lKopalnia zostala zresetowana!"));
    }

    private void setRandomOreMaterial(Ore ore) {
        final int random = new Random().nextInt(7);
        switch (random) {
            case 0:
                ore.setOreMaterial(Material.COAL_ORE);
                ore.setMaxHp(10);
                return;
            case 1:
                ore.setOreMaterial(Material.IRON_ORE);
                ore.setMaxHp(12);
                return;
            case 2:
                ore.setOreMaterial(Material.GOLD_ORE);
                ore.setMaxHp(14);
                return;
            case 3:
                ore.setOreMaterial(Material.DIAMOND_ORE);
                ore.setMaxHp(25);
                return;
            case 4:
                ore.setOreMaterial(Material.EMERALD_ORE);
                ore.setMaxHp(20);
                return;
            case 5:
                ore.setOreMaterial(Material.REDSTONE_ORE);
                ore.setMaxHp(30);
                return;
            case 6:
                ore.setOreMaterial(Material.LAPIS_ORE);
                ore.setMaxHp(15);
        }
    }
}
