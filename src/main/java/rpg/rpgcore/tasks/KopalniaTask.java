package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;

public class KopalniaTask implements Runnable{
    private final RPGCORE rpgcore;
    public KopalniaTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 100, 36_000);
    }

    @Override
    public void run() {
//        rpgcore.getOreManager().getOreLocations().forEach(ore -> {
//            if (ore.getOreMaterial() == Material.BEDROCK) {
//                this.setRandomOreMaterial(ore);
//                ore.setHp(ore.getMaxHp());
//            }
//            Bukkit.getWorld(ore.getWorld()).getBlockAt(ore.getLocation()).setType(ore.getOreMaterial());
//        });
        Bukkit.broadcastMessage(Utils.format("&7&lKopalnia zostala zresetowana!"));
    }

//    private void setRandomOreMaterial(final Ore ore) {
//        GornikOres gornikOres = GornikOres.O1;
//        if (ChanceHelper.getChance(4)) {
//            gornikOres = GornikOres.O7;
//        } else if (ChanceHelper.getChance(6)) {
//            gornikOres = GornikOres.O6;
//        } else if (ChanceHelper.getChance(8)) {
//            gornikOres = GornikOres.O5;
//        } else if (ChanceHelper.getChance(12)) {
//            gornikOres = GornikOres.O4;
//        } else if (ChanceHelper.getChance(15)) {
//            gornikOres = GornikOres.O3;
//        } else if (ChanceHelper.getChance(25)) {
//            gornikOres = GornikOres.O2;
//        }
//
//        ore.setOreMaterial(gornikOres.getMaterial());
//        ore.setMaxHp(gornikOres.getMaxHp());
//    }
}
