package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.enums.GornikOres;
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
        GornikOres gornikOres = null;
        switch (random) {
            case 0:
                gornikOres = GornikOres.O1;
                break;
            case 1:
                gornikOres = GornikOres.O2;
                break;
            case 2:
                gornikOres = GornikOres.O3;
                break;
            case 3:
                gornikOres = GornikOres.O6;
                break;
            case 4:
                gornikOres = GornikOres.O5;
                break;
            case 5:
                gornikOres = GornikOres.O7;
                break;
            case 6:
                gornikOres = GornikOres.O4;
                break;
        }
        ore.setOreMaterial(gornikOres.getMaterial());
        ore.setMaxHp(gornikOres.getMaxHp());
    }
}
