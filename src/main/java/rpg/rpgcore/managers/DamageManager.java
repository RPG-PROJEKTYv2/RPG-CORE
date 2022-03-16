package rpg.rpgcore.managers;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final Player target, final double dmg){
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(rpgcore.getNmsManager().makeDamageDisplay(target.getLocation(), Utils.df.format(dmg)));
    }
}
