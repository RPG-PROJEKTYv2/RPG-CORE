package rpg.rpgcore.managers;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.Random;
import java.util.UUID;

import static rpg.rpgcore.utils.Utils.random;

public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final double dmg, final Location entityLocation, final Player p) {
        final Random random = new Random();

        final double randomx = (random.nextInt(11)-5) / 10.0;
        final double randomy = (random.nextInt(11)-5) / 10.0;
        final double randomz = (random.nextInt(11)-5) / 10.0;
        entityLocation.add(randomx, randomy, randomz);
        final WorldServer s = ((CraftWorld) entityLocation.getWorld()).getHandle();
        final EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(entityLocation.getX(), entityLocation.getY(), entityLocation.getZ(), 0, 0);
        stand.setCustomName(Utils.format("&c- " + Utils.df.format(dmg)));
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);

        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        rpgcore.getServer().getScheduler().scheduleSyncDelayedTask(rpgcore, () -> this.destroySendHologram(stand, p), 20L);
    }

    public void destroySendHologram(final EntityArmorStand stand, final Player p) {
        final PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroyPacket);

    }

    public double calculateDamage(final UUID uuid, final double dmgZMiecza) {
        double finalDamage = 0;
        double finalDamageMultiplier = 1;

        double srednieZBao;
        if (rpgcore.getBaoManager().getBaoBonusy(uuid).split(",")[0].equalsIgnoreCase("srednie obrazenia")){
            srednieZBao = Double.parseDouble(rpgcore.getBaoManager().getBaoBonusyWartosci(uuid).split(",")[0]) / 1000;
            finalDamageMultiplier += srednieZBao;
        }


        double baseDamage = 3.3;
        finalDamage += baseDamage;
        finalDamage += dmgZMiecza;

        double dmgZBao;
        if (rpgcore.getBaoManager().getBaoBonusy(uuid).split(",")[3].equalsIgnoreCase("dodatkowe obrazenia")) {
            dmgZBao = Double.parseDouble(rpgcore.getBaoManager().getBaoBonusyWartosci(uuid).split(",")[3]);
            finalDamage += dmgZBao;
        }

        System.out.println(finalDamage + " - dmg przed mnoznikiem");
        System.out.println(finalDamageMultiplier + " - mnoznikiem");

        return finalDamage * finalDamageMultiplier;
    }
}
