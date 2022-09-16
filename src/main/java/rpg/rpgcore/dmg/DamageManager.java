package rpg.rpgcore.dmg;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.BaoUser;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final Map<UUID, Double> krytMap = new HashMap<>();

    public void sendDamagePacket(final String prefix, final double dmg, final Location entityLocation, final Player p) {
        final Random random = new Random();

        final double randomx = (random.nextInt(11)-5) / 10.0;
        final double randomy = (random.nextInt(11)-5) / 10.0;
        final double randomz = (random.nextInt(11)-5) / 10.0;
        entityLocation.add(randomx, randomy, randomz);
        final WorldServer s = ((CraftWorld) entityLocation.getWorld()).getHandle();
        final EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(entityLocation.getX(), entityLocation.getY(), entityLocation.getZ(), 0, 0);
        stand.setCustomName(Utils.format(prefix + "- " + Utils.spaceNumber(String.format("%.2f", dmg))));
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

    public  void sendDamageActionBarPacket(final Player player, final double damage, final LivingEntity entity) {
        final String bar = "&fNazwa: " + entity.getCustomName() + " &fStan Zdrowia: &c" + String.format("%.2f", entity.getHealth()) + "&f/&c" + entity.getMaxHealth() + "&f(&c-" + String.format("%.2f", damage) + "&f)";
        rpgcore.getNmsManager().sendActionBar(player, bar);
    }

    public double calculateAttackerDmgToPlayer(final Player attacker) {
        final RandomItems<String> krytykChance = new RandomItems<>();
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        double dmg = 1;
        double mnoznik = 100;
        double krytyk = 10;

        // MIECZ
        if (weapon != null) {
            dmg += Utils.getSharpnessLevel(weapon);
        }

        // AkcesoriaCommand
        //TODO Zrobic podpiecie Akcesori do klasy BONUSES

        // BAO
        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser user = rpgcore.getBaoManager().find(uuid).getBaoUser();

            if (user.getBonus1().equals("Srednie obrazenia") || user.getBonus1().equals("Srednie obrazenie przeciwko ludziom")) {
                mnoznik += user.getValue1();
            }
            if (user.getBonus3().equals("Szansa na Cios Krytyczny")) {
                krytyk += user.getValue3();
            }
            if (user.getBonus4().equals("Dodatkowe Obrazenia")) {
                dmg += user.getValue4();
            }
        }

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
            mnoznik += rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag);
        }

        // NPC
        // ...RYBAK
        dmg += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue3();
        mnoznik += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue1();
        // ...KOLEKCJONER
        mnoznik += rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser().getSilnyNaLudzi();
        krytyk += rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser().getDodatkowe();


        dmg = (dmg * (mnoznik / 100)) /3;
        rpgcore.getServer().broadcastMessage("Dmg przed krytem: " + dmg);
        if (krytyk > 200) {
            krytyk = 200;
        }

        if (krytyk > 100) {
            krytyk -= 100;
            dmg = dmg * 2;
        }
        krytykChance.add(krytyk / 100, "tak");
        krytykChance.add(1 - (krytyk / 100), "nie");
        String test = krytykChance.next();
        if (test.equals("tak")) {
            dmg = dmg * 1.5;
        }
        rpgcore.getServer().broadcastMessage("Dmg po krycie: " + dmg);
        rpgcore.getServer().broadcastMessage("krytyk: " +test);
        rpgcore.getServer().broadcastMessage("krytykChance: " + (krytyk / 100));

        return dmg;
    }

    public double calculateAttackerDmgToEntity(final Player attacker) {
        final RandomItems<String> krytykChance = new RandomItems<>();
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        double dmg = 1;
        double mnoznik = 100;
        double krytyk = 10;

        // MIECZ DMG
        if (!weapon.getType().equals(Material.AIR)) {
            dmg += Utils.getSharpnessLevel(weapon);
            dmg += Utils.getObrazeniaMobyLevel(weapon);
        }

        // AkcesoriaCommand
        //TODO Zrobic podpiecie Akceosir do klasy BONUSES

        // BAO
        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser user = rpgcore.getBaoManager().find(uuid).getBaoUser();

            if (user.getBonus1().equals("Srednie obrazenia") || user.getBonus1().equals("Srednie obrazenie przeciwko potworom")) {
                mnoznik += user.getValue1();
            }
            if (user.getBonus3().equals("Szansa na Cios Krytyczny")) {
                krytyk += user.getValue3();
            }
            if (user.getBonus4().equals("Dodatkowe Obrazenia")) {
                dmg += user.getValue4();
            }
        }

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
        }

        // NPC
        // ...RYBAK
        dmg += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue3();
        mnoznik += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue1();
        // ...KOLEKCJONER
        krytyk += rpgcore.getKolekcjonerNPC().find(uuid).getKolekcjonerUser().getDodatkowe();


        dmg = (dmg * (mnoznik / 100)) /3;
        rpgcore.getServer().broadcastMessage("Dmg przed krytem: " + dmg);
        if (krytyk > 200) {
            krytyk = 200;
        }

        if (krytyk > 100) {
            krytyk -= 100;
            dmg = dmg * 2;
        }
        krytykChance.add(krytyk / 100, "tak");
        krytykChance.add(1 - (krytyk / 100), "nie");
        String test = krytykChance.next();
        if (test.equals("tak")) {
            dmg = dmg * 1.5;
        }
        rpgcore.getServer().broadcastMessage("Dmg po krycie: " + dmg);
        rpgcore.getServer().broadcastMessage("krytyk: " +test);
        rpgcore.getServer().broadcastMessage("krytykChance: " + (krytyk / 100));

        return dmg;
    }

    public void updateKryt(UUID uuid, double kryt) {
        krytMap.replace(uuid, kryt);
    }

    public Double getKryt(final UUID uuid) {
        krytMap.computeIfAbsent(uuid, kryt -> 0.0);
        return krytMap.get(uuid);
    }

    public void setKryt(final UUID uuid, final double kryt) {
        krytMap.put(uuid, kryt);
    }
}
