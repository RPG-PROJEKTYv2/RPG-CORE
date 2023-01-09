package rpg.rpgcore.dmg;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.Random;
import java.util.UUID;


public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final String prefix, final double dmg, final Location entityLocation, final Player p) {
        final Random random = new Random();

        final double randomx = (random.nextInt(11) - 5) / 10.0;
        final double randomy = (random.nextInt(11) - 5) / 10.0;
        final double randomz = (random.nextInt(11) - 5) / 10.0;
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

    public void sendDamageActionBarPacket(final Player player, final double damage, final LivingEntity entity) {
        final String bar = "&fNazwa: " + entity.getCustomName() + " &fStan Zdrowia: &c" + String.format("%.2f", entity.getHealth()) + "&f/&c" + entity.getMaxHealth() + "&f(&c-" + String.format("%.2f", damage) + "&f)";
        rpgcore.getNmsManager().sendActionBar(player, bar);
    }

    public double calculateAttackerDmgToPlayer(final Player attacker, final Player victim) {
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 2;
        double mnoznik = 100;
        double krytyk = 1;
        double wzmKryt = 1;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {
            dmg += Utils.getTagInt(weapon, "dmg");
            dmg += Utils.getTagInt(weapon, "dodatkowe");
            final String silnyLvl = Utils.getTagString(weapon, "silny-lvl");
            final int attackerLvl = rpgcore.getUserManager().find(uuid).getLvl();
            final int victimLvl = rpgcore.getUserManager().find(victim.getUniqueId()).getLvl();
            switch (silnyLvl) {
                case "mniejsze":
                    if (victimLvl < attackerLvl) {
                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
                    }
                    break;
                case "rowne":
                    if (victimLvl == attackerLvl) {
                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
                    }
                    break;
                case "wyzsze":
                    if (victimLvl > attackerLvl) {
                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
                    }
                    break;
                default:
                    break;
            }
            krytyk += Utils.getTagDouble(weapon, "krytyk");
        } else {
            dmg = 1;
        }

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

        dmg = dmg * (mnoznik / 100);
        if (krytyk > 100) {
            krytyk = 100;
        }

        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * 1.5;
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * 1.5;
            }
        }

        dmg = dmg/100;

        final double finalDmg = DoubleUtils.round(dmg, 2);

        if (RPGCORE.getInstance().getUserManager().find(uuid).getKrytyk() < finalDmg) {
            attacker.sendMessage(Utils.format("&4Damage &8>> &cUstanowiles swoj nowy najwiekszy zadany dmg! &4(" + finalDmg + " dmg)"));
            RPGCORE.getInstance().getUserManager().find(uuid).setKrytyk(finalDmg);
        }

        return finalDmg;
    }

    public double calculatePlayerDef(final Player player) {
        final UUID uuid = player.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double def = 10;
        double mnoznik = 100;

        mnoznik += bonuses.getSredniadefensywa();
        mnoznik += bonuses.getDefnaludzi();
        mnoznik -= bonuses.getMinusdefnaludzi();
        mnoznik -= bonuses.getMinussredniadefensywa();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildDefNaLudzi(tag);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        if (player.getInventory().getHelmet() != null) {
            def += Utils.getTagInt(player.getInventory().getHelmet(), "prot");
        }
        if (player.getInventory().getChestplate() != null) {
            def += Utils.getTagInt(player.getInventory().getChestplate(), "prot");
        }
        if (player.getInventory().getLeggings() != null) {
            def += Utils.getTagInt(player.getInventory().getLeggings(), "prot");
        }
        if (player.getInventory().getBoots() != null) {
            def += Utils.getTagInt(player.getInventory().getBoots(), "prot");
        }

        def = def * (mnoznik / 100);

        return DoubleUtils.round(def / (def + 100), 3);
    }

    public double calculateAttackerDmgToEntity(final Player attacker, final Entity victim) {
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 2;
        double mnoznik = 100;
        double krytyk = 1;
        double wzmKryt = 1;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {
            dmg += Utils.getTagInt(weapon, "dmg");
            dmg += Utils.getTagInt(weapon, "moby");
            dmg += Utils.getTagInt(weapon, "dodatkowe");
            if (Utils.removeColor(victim.getCustomName()).contains(Utils.removeColor(Utils.getTagString(weapon, "silny-na-mob")))) {
                mnoznik += Utils.getTagInt(weapon, "silny-na-val");
            }
            krytyk += Utils.getTagDouble(weapon, "krytyk");
        } else {
            dmg = 1;
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

        dmg = dmg * (mnoznik / 100);
        if (krytyk > 100) {
            krytyk = 100;
        }

        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * 1.5;
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * 1.5;
            }
        }

        final double finalDmg = DoubleUtils.round(dmg, 2);

        if (RPGCORE.getInstance().getUserManager().find(uuid).getKrytyk() < finalDmg) {
            attacker.sendMessage(Utils.format("&4Damage &8>> &cUstanowiles swoj nowy najwiekszy zadany dmg! &4(" + finalDmg + " dmg)"));
            RPGCORE.getInstance().getUserManager().find(uuid).setKrytyk(finalDmg);
        }

        return finalDmg;
    }

    public double calculatePlayerDefToEntity(final Player player) {
        final UUID uuid = player.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double def = 10;
        double mnoznik = 100;

        mnoznik += bonuses.getSredniadefensywa();
        mnoznik += bonuses.getDefnamoby();
        mnoznik -= bonuses.getMinusdefnamoby();
        mnoznik -= bonuses.getMinussredniadefensywa();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        if (player.getInventory().getHelmet() != null) {
            def += Utils.getTagInt(player.getInventory().getHelmet(), "prot");
        }
        if (player.getInventory().getChestplate() != null) {
            def += Utils.getTagInt(player.getInventory().getChestplate(), "prot");
        }
        if (player.getInventory().getLeggings() != null) {
            def += Utils.getTagInt(player.getInventory().getLeggings(), "prot");
        }
        if (player.getInventory().getBoots() != null) {
            def += Utils.getTagInt(player.getInventory().getBoots(), "prot");
        }

        def = def * (mnoznik / 100);

        return DoubleUtils.round(def / (def + 100), 3);
    }

    public double calculateVictimBlok(final Player victim, final Player attacker) {
        final UUID victimUUID = victim.getUniqueId();
        final UUID attackerUUID = attacker.getUniqueId();
        final BonusesUser victimBonuses = rpgcore.getBonusesManager().find(victimUUID).getBonusesUser();
        final BonusesUser attackerBonuses = rpgcore.getBonusesManager().find(attackerUUID).getBonusesUser();
        double victimBlok = victimBonuses.getBlokciosu();
        double attackerPrzeszywka = attackerBonuses.getPrzeszyciebloku();
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR && String.valueOf(attacker.getItemInHand().getType()).contains("SWORD")) {
            attackerPrzeszywka += Utils.getTagInt(attacker.getItemInHand(), "przeszywka");
        }

        return DoubleUtils.round(victimBlok - attackerPrzeszywka, 2);
    }
    public double calculatePrzebicie(final Player attacker) {
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double przebicie = bonuses.getPrzebiciePancerza();



        return DoubleUtils.round(przebicie, 2);
    }

    public double calculatePlayerThornsDmg(final Player victim) {
        final UUID uuid = victim.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double thornsDmg = 0;
        double mnoznik = 100;

        mnoznik += bonuses.getSrednieobrazenia();
        mnoznik += bonuses.getSilnynapotwory();
        mnoznik -= bonuses.getMinussrednieobrazenia();
        mnoznik -= bonuses.getMinusobrazenianamoby();
        thornsDmg += bonuses.getDodatkoweobrazenia();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
        }

        if (victim.getInventory().getHelmet() != null) {
            thornsDmg += Utils.getTagInt(victim.getInventory().getHelmet(), "thorns");
        }
        if (victim.getInventory().getChestplate() != null) {
            thornsDmg += Utils.getTagInt(victim.getInventory().getChestplate(), "thorns");
        }
        if (victim.getInventory().getLeggings() != null) {
            thornsDmg += Utils.getTagInt(victim.getInventory().getLeggings(), "thorns");
        }
        if (victim.getInventory().getBoots() != null) {
            thornsDmg += Utils.getTagInt(victim.getInventory().getBoots(), "thorns");
        }

        thornsDmg = (thornsDmg * (mnoznik / 100));


        return DoubleUtils.round(thornsDmg, 2);
    }
}
