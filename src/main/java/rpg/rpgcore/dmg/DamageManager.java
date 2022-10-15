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
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.utils.ChanceHelper;
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
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 10.5;
        double mnoznik = 100;
        double krytyk = 1;
        double wzmKryt = 1;

        if (weapon != null) {
            dmg += Utils.getSharpnessLevel(weapon);
        }

        // BAO
        mnoznik += bonuses.getSrednieobrazenia();
        mnoznik += bonuses.getSilnynaludzi();
        mnoznik -= bonuses.getMinussrednieobrazenia();
        mnoznik -= bonuses.getMinusobrazenianaludzi();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        wzmKryt += bonuses.getSzansanawzmocnieniekryta();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
            mnoznik += rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag);
        }
        // ...RYBAK
        dmg += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue3();
        mnoznik += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue1();

        dmg = (dmg * (mnoznik / 100)) /3;
        attacker.sendMessage("Dmg - " + dmg);
        if (krytyk > 200) {
            krytyk = 200;
        }

        if (krytyk > 100) {
            krytyk -= 100;
            dmg = dmg * 2;
        }
        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * 1.5;
            attacker.sendMessage("Krytyk!");
            attacker.sendMessage("DMG po krycie - " + dmg);
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * 1.5;
                attacker.sendMessage("Wzmocniony krytyk!");
                attacker.sendMessage("DMG po wzmocnionym krycie - " + dmg);
            }
        }

        return Double.parseDouble(String.format("%.2f", dmg));
    }

    public double calculateDef(final Player player, final String type) {
        final UUID uuid = player.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double def = 10;
        double mnoznik = 100;

        mnoznik += bonuses.getSredniadefensywa();

        if (player.getInventory().getHelmet() != null) {
            def += Utils.getProtectionLevel(player.getInventory().getHelmet());
        }
        if (player.getInventory().getChestplate() != null) {
            def += Utils.getProtectionLevel(player.getInventory().getChestplate());
        }
        if (player.getInventory().getLeggings() != null) {
            def += Utils.getProtectionLevel(player.getInventory().getLeggings());
        }
        if (player.getInventory().getBoots() != null) {
            def += Utils.getProtectionLevel(player.getInventory().getBoots());
        }


        if (type.equalsIgnoreCase("ludzie")) {
            mnoznik += bonuses.getDefnaludzi();
            if (rpgcore.getGuildManager().hasGuild(uuid)) {
                mnoznik += rpgcore.getGuildManager().getGuildDefNaLudzi(rpgcore.getGuildManager().getGuildTag(uuid));
            }
        } else if (type.equalsIgnoreCase("moby")) {
            mnoznik += bonuses.getDefnamoby();

        }

        return Double.parseDouble(String.format("%.2f", def * (mnoznik / 100)));
    }

    public double calculateAttackerDmgToEntity(final Player attacker) {
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 10.5;
        double mnoznik = 100;
        double krytyk = 1;
        double wzmKryt = 1;

        // MIECZ DMG
        if (!weapon.getType().equals(Material.AIR)) {
            dmg += Utils.getSharpnessLevel(weapon);
            dmg += Utils.getObrazeniaMobyLevel(weapon);
        }

        mnoznik += bonuses.getSrednieobrazenia();
        mnoznik += bonuses.getSilnynapotwory();
        mnoznik -= bonuses.getMinussrednieobrazenia();
        mnoznik -= bonuses.getMinusobrazenianamoby();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        wzmKryt += bonuses.getSzansanawzmocnieniekryta();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
        }
        // ...RYBAK
        dmg += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue3();
        mnoznik += rpgcore.getRybakNPC().find(uuid).getRybakUser().getValue1();

        dmg = (dmg * (mnoznik / 100)) /3;
        attacker.sendMessage("Dmg - " + dmg);
        if (krytyk > 200) {
            krytyk = 200;
        }

        if (krytyk > 100) {
            krytyk -= 100;
            dmg = dmg * 2;
        }
        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * 1.5;
            attacker.sendMessage("Krytyk!");
            attacker.sendMessage("DMG po krycie - " + dmg);
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * 1.5;
                attacker.sendMessage("Wzmocniony krytyk!");
                attacker.sendMessage("DMG po wzmocnionym krycie - " + dmg);
            }
        }

        return Double.parseDouble(String.format("%.2f", dmg));
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
