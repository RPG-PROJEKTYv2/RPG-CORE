package rpg.rpgcore.dmg;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.dodatki.bony.objects.BonyUser;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;


public class DamageManager {

    private final RPGCORE rpgcore;

    public DamageManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public void sendDamagePacket(final String prefix, final double dmg, final Entity entity, final Player p) {
        final Vector inverseDirectionVec = entity.getLocation().getDirection().normalize().multiply(-1);
        final Location entityLocation = entity.getLocation().add(inverseDirectionVec);
        // TODO Przetestować czy działa (ma sie respic ZA graczem/mobem)
        final double randomx = ChanceHelper.getRandDouble(-0.2, 0.2);
        final double randomz = ChanceHelper.getRandDouble(-0.2, 0.2);

        final WorldServer s = ((CraftWorld) entityLocation.getWorld()).getHandle();
        final EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(entityLocation.getX() + randomx, entityLocation.getY() + 1.5, entityLocation.getZ() + randomz, 0, 0);
        stand.setCustomName(Utils.format(prefix + "- " + Utils.spaceNumber(String.format("%.2f", dmg))));
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setSmall(true);
        stand.setArms(false);
        stand.setBasePlate(false);
        stand.n(true);

        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        rpgcore.getServer().getScheduler().runTaskLaterAsynchronously(rpgcore, () -> this.destroySendHologram(stand, p), 20L);
        if (rpgcore.getChatManager().find(p.getUniqueId()).isDmgHologramsVisable()) {
            for (Entity e : p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 30, 30, 30)) {
                if (e == null) continue;
                if (e instanceof Player) {
                    Player player = (Player) e;
                    if (player != p) {
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                        rpgcore.getServer().getScheduler().runTaskLaterAsynchronously(rpgcore, () -> this.destroySendHologram(stand, player), 20L);
                    }
                }
            }
        }
    }

    public void destroySendHologram(final EntityArmorStand stand, final Player p) {
        final PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroyPacket);
    }

    public double calculateAttackerDmgToPlayer(final Player attacker, final Player victim) {
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 1;
        double mnoznik = 100;
        double krytyk = 0;
        double wzmKrytDmg = 0;
        double bony = 100;

        double tyra = 100;
        double gildia = 100;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {
            final String type = Utils.getTagString(weapon, "type");

            switch (type) {
                case "ks":
                    attacker.sendMessage(Utils.format("&cTym mieczem mozesz bic tylko potwory!"));
                    return 0;
                case "tyra":
                    if (weapon.getItemMeta().getDisplayName().contains("Wzmocniony")) tyra += Utils.getTagInt(weapon, "ludzieProcentTYRA") * 2.25;
                    else tyra += Utils.getTagInt(weapon, "ludzieProcentTYRA");
                    break;
                default:
                    break;
            }

            dmg += Utils.getTagInt(weapon, "dmg") * 3.25;
            dmg += Utils.getTagInt(weapon, "dodatkowe");
//            final String silnyLvl = Utils.getTagString(weapon, "silny-lvl");
//            final int attackerLvl = rpgcore.getUserManager().find(uuid).getLvl();
//            final int victimLvl = rpgcore.getUserManager().find(victim.getUniqueId()).getLvl();
//            switch (silnyLvl) {
//                case "mniejsze":
//                    if (victimLvl < attackerLvl) {
//                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
//                    }
//                    break;
//                case "rowne":
//                    if (victimLvl == attackerLvl) {
//                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
//                    }
//                    break;
//                case "wyzsze":
//                    if (victimLvl > attackerLvl) {
//                        mnoznik += Utils.getTagDouble(weapon, "silny-lvl-val");
//                    }
//                    break;
//                default:
//                    break;
//            }
            //krytyk += Utils.getTagDouble(weapon, "krytyk");
        } else {
            dmg = 1;
        }

        mnoznik += bonuses.getSrednieobrazenia();
        mnoznik += bonuses.getSilnynaludzi();
        mnoznik += bonuses.getMinussrednieobrazenia();
        mnoznik += bonuses.getMinusobrazenianaludzi();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        krytyk += bonuses.getSzansanawzmocnieniekryta();
        wzmKrytDmg += bonuses.getWzmocnienieKryta();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (attacker.getInventory().getHelmet() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getHelmet(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getChestplate() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getChestplate(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getLeggings() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getLeggings(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getBoots() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getBoots(), "szansaWzmKryt");
        }

        switch (rpgcore.getKlasyManager().find(uuid).getMainKlasa()) {
            case WOJOWNIK:
                mnoznik += 5;
                break;
            case ZABOJCA:
                mnoznik += 8;
                krytyk += 5;
                break;
            case CZARODZIEJ:
                mnoznik += 3;
                break;
            default:
                break;
        }

        if (rpgcore.getKlasyManager().getBerserkLMB().asMap().containsKey(uuid)) {
            mnoznik += 10;
        }
        if (rpgcore.getKlasyManager().getNinjaRMB().asMap().containsKey(uuid)) {
            mnoznik += 5;
        }

        if (!victim.canSee(attacker) && !rpgcore.getVanishManager().isVanished(attacker.getUniqueId())) {
            mnoznik += 5;
            for (Player rest : Bukkit.getOnlinePlayers()) rest.showPlayer(attacker);
            rpgcore.getKlasyManager().getUsedSkrytoPassive().remove(attacker.getUniqueId());
        }

        // BONY
        final BonyUser bonyUser = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bonyUser.getDmg5().getType().equals(Material.AIR)) {
            bony += 5;
            mnoznik -= 5;
        }
        if (!bonyUser.getDmg10().getType().equals(Material.AIR)) {
            bony += 10;
            mnoznik -= 10;
        }
        if (!bonyUser.getDmg15().getType().equals(Material.AIR)) {
            bony += 15;
            mnoznik -= 15;
        }

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            gildia += rpgcore.getGuildManager().getGuildSredniDmg(tag) / 2;
            gildia += rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag) / 2;
        }

        /*attacker.sendMessage(Utils.format("&6&lDEBUG"));
        attacker.sendMessage(Utils.format("&cMnoznik: " + mnoznik));
        attacker.sendMessage(Utils.format("&cBony: " + bony));
        attacker.sendMessage(Utils.format("&cGildia: " + gildia));
        attacker.sendMessage(Utils.format("&cDmg: " + dmg));
        attacker.sendMessage(Utils.format("&cTYRA: " + tyra));*/


        dmg *= (gildia / 100);
        dmg = dmg * 2.5 * (mnoznik / 100);
        dmg *= (bony / 100);
        dmg = dmg * (tyra / 100);

        krytyk /= 4;

        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * (1.5 + (wzmKrytDmg / 100));
        }

        double finalDmg = DoubleUtils.round(dmg, 2);
        /*attacker.sendMessage(Utils.format("&6&lDmg PO:" + finalDmg));
        attacker.sendMessage(Utils.format("&6&lDEBUG"));*/
        if (finalDmg < 0) finalDmg = 0;

        return finalDmg;
    }

    public double calculateAttackerDmgToEntity(final Player attacker, final Entity victim, final boolean thornsDmg) {
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 1;
        double mnoznik = 100;
        double krytyk = 0;
        double wzmKrytDmg = 0;
        double bony = 100;

        double ks = 100;
        double gildie = 100;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {

            final String type = Utils.getTagString(weapon, "type");

            switch (type) {
                case "tyra":
                    attacker.sendMessage(Utils.format("&cTym mieczem mozesz bic tylko graczy!"));
                    return 0;
                case "ks":
                    if (weapon.getItemMeta().getDisplayName().contains("Wzmocniony")) ks += Utils.getTagInt(weapon, "mobyProcentKS") * 2;
                    else ks += Utils.getTagInt(weapon, "mobyProcentKS");
                    break;
                default:
                    break;
            }

            dmg += Utils.getTagInt(weapon, "dmg") * 1.75;
            dmg += Utils.getTagInt(weapon, "moby") * 1.25;
            dmg += Utils.getTagInt(weapon, "dodatkowe");
            if (Utils.removeColor(victim.getCustomName()).contains(Utils.removeColor(Utils.getTagString(weapon, "silny-na-mob")))) {
                mnoznik += Utils.getTagDouble(weapon, "silny-na-val") * 2;
            }
            //krytyk += Utils.getTagDouble(weapon, "krytyk");
        } else {
            dmg = 1;
        }

        mnoznik += bonuses.getSrednieobrazenia();
        mnoznik += bonuses.getSilnynapotwory();
        mnoznik += bonuses.getMinussrednieobrazenia();
        mnoznik += bonuses.getMinusobrazenianamoby();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        krytyk += bonuses.getSzansanawzmocnieniekryta();
        wzmKrytDmg += bonuses.getWzmocnienieKryta();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (attacker.getInventory().getHelmet() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getHelmet(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getChestplate() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getChestplate(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getLeggings() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getLeggings(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getBoots() != null) {
            krytyk += Utils.getTagDouble(attacker.getInventory().getBoots(), "szansaWzmKryt");
        }


        switch (rpgcore.getKlasyManager().find(uuid).getMainKlasa()) {
            case WOJOWNIK:
                mnoznik += 5;
                break;
            case ZABOJCA:
                mnoznik += 8;
                krytyk += 5;
                break;
            case CZARODZIEJ:
                mnoznik += 3;
                break;
            default:
                break;
        }

        if (rpgcore.getKlasyManager().getBerserkLMB().asMap().containsKey(uuid)) {
            mnoznik += 10;
        }

        if (rpgcore.getKlasyManager().getNinjaRMB().asMap().containsKey(uuid)) {
            mnoznik += 5;
        }

        if (rpgcore.getKlasyManager().getBerserkRMB().contains(uuid)) {
            mnoznik += 250;
            rpgcore.getKlasyManager().getBerserkRMB().remove(uuid);
        }

        // BONY
        final BonyUser bonyUser = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bonyUser.getDmg5().getType().equals(Material.AIR)) {
            bony += 5;
            mnoznik -= 5;
        }
        if (!bonyUser.getDmg10().getType().equals(Material.AIR)) {
            bony += 10;
            mnoznik -= 10;
        }
        if (!bonyUser.getDmg15().getType().equals(Material.AIR)) {
            bony += 15;
            mnoznik -= 15;
        }


        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            gildie += rpgcore.getGuildManager().getGuildSredniDmg(tag);
        }

        /*attacker.sendMessage(Utils.format("&6&lDEBUG"));
        attacker.sendMessage(Utils.format("&cMnoznik: " + mnoznik));
        attacker.sendMessage(Utils.format("&cBony: " + bony));
        attacker.sendMessage(Utils.format("&cGildia: " + gildie));
        attacker.sendMessage(Utils.format("&cDmg: " + dmg));
        attacker.sendMessage(Utils.format("&cKS: " + ks));
        attacker.sendMessage(Utils.format("&cSilny Na: " + Utils.getTagDouble(weapon, "silny-na-val")));*/


        dmg *= (gildie / 100);
        dmg = dmg * 2 * (mnoznik / 100);
        dmg *= (bony / 100);
        dmg = dmg * (ks / 100);

        krytyk /= 4;


        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * (1.5 + (wzmKrytDmg / 100));
        }

        if (thornsDmg) {
            if (weapon != null && weapon.getType() != Material.AIR && weapon.hasItemMeta() && Utils.removeColor(weapon.getItemMeta().getDisplayName()).contains("Przekleta Smocza Skora")) {
                dmg = dmg * 1.25;
            }
        }

        double finalDmg = DoubleUtils.round(dmg, 2);

        /*attacker.sendMessage(Utils.format("&6&lDmg PO:" + finalDmg));
        attacker.sendMessage(Utils.format("&6&lDEBUG"));*/

        if (finalDmg < 0) finalDmg = 0;

        return finalDmg;
    }

    public double calculatePlayerDef(final Player player) {
        final UUID uuid = player.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double def = 1;
        double mnoznik = 100;
        double bony = 100;
        double gildia = 100;

        mnoznik += bonuses.getSredniadefensywa();
        mnoznik += bonuses.getDefnaludzi();
        mnoznik += bonuses.getMinusdefnaludzi();
        mnoznik += bonuses.getMinussredniadefensywa();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            gildia += rpgcore.getGuildManager().getGuildDefNaLudzi(tag);
            gildia += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        if (rpgcore.getKlasyManager().getPaladinPassive().asMap().containsKey(uuid)) {
            mnoznik += 10;
        }

        final BonyUser bonyUser = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bonyUser.getDef5().getType().equals(Material.AIR)) {
            mnoznik -= 5;
            bony += 5;
        }
        if (!bonyUser.getDef10().getType().equals(Material.AIR)) {
            mnoznik -= 10;
            bony += 10;
        }
        if (!bonyUser.getDef15().getType().equals(Material.AIR)) {
            mnoznik -= 15;
            bony += 15;
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

        /*player.sendMessage(Utils.format("&6&lDEBUG"));
        player.sendMessage(Utils.format("&cMnoznik: " + mnoznik));
        player.sendMessage(Utils.format("&cBony: " + bony));
        player.sendMessage(Utils.format("&cGildia: " + gildia));
        player.sendMessage(Utils.format("&cDef Przed: " + def));*/

        def *= (gildia / 100);
        def *= 0.85 * (mnoznik / 100) * (bony / 100);
        def *= (bony / 100);

        /*player.sendMessage(Utils.format("&6&lDef PO: " + def));
        player.sendMessage(Utils.format("&6&lDEBUG"));*/

        return DoubleUtils.round(def, 3);
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
        if (rpgcore.getKlasyManager().find(victimUUID).getMainKlasa() == KlasyMain.CZARODZIEJ) {
            victimBlok += 5;
        }

        return DoubleUtils.round(victimBlok - attackerPrzeszywka, 2);
    }

    public double calculatePrzebicie(final Player attacker) {
        return DoubleUtils.round(rpgcore.getBonusesManager().find(attacker.getUniqueId()).getBonusesUser().getPrzebiciePancerza(), 2);
    }

    public int calculatePlayerThorns(final Player player) {
        int thorns = 0;
        if (player.getInventory().getHelmet() != null) {
            thorns += Utils.getTagInt(player.getInventory().getHelmet(), "thorns");
        }
        if (player.getInventory().getChestplate() != null) {
            thorns += Utils.getTagInt(player.getInventory().getChestplate(), "thorns");
        }
        if (player.getInventory().getLeggings() != null) {
            thorns += Utils.getTagInt(player.getInventory().getLeggings(), "thorns");
        }
        if (player.getInventory().getBoots() != null) {
            thorns += Utils.getTagInt(player.getInventory().getBoots(), "thorns");
        }
        return thorns;
    }

    public double calculatePlayerThornsDmg(final Player victim) {
        final double thornsDmg = calculatePlayerThorns(victim);
        double mnoznik = 0.1;


        if (thornsDmg > 15) {
            mnoznik = 0.15;
        }
        if (thornsDmg > 25) {
            mnoznik = 0.19;
        }
        if (thornsDmg > 45) {
            mnoznik = 0.25;
        }
        if (thornsDmg > 80) {
            mnoznik = 0.33;
        }
        if (thornsDmg > 140) {
            mnoznik = 0.37;
        }
        if (thornsDmg > 185) {
            mnoznik = 0.42;
        }
        if (thornsDmg > 199) {
            mnoznik = 0.5;
        }


        return DoubleUtils.round(mnoznik, 2);
    }

    public double calculateEntityDamageToPlayer(final Entity entity, final Player victim) {
        final UUID uuid = victim.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double mnoznik = 100;
        int prot = 1;
        double bony = 100;
        double gildia = 100;
        final double mobDamage = EntityDamage.getByName(Utils.removeColor(entity.getName()).trim());

        mnoznik += bonuses.getSredniadefensywa();
        mnoznik += bonuses.getDefnamoby();
        mnoznik += bonuses.getMinusdefnamoby();
        mnoznik += bonuses.getMinussredniadefensywa();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (victim.getInventory().getHelmet() != null) {
            prot += Utils.getTagInt(victim.getInventory().getHelmet(), "prot");
            mnoznik += Utils.getTagDouble(victim.getInventory().getHelmet(), "defMoby");
        }
        if (victim.getInventory().getChestplate() != null) {
            prot += Utils.getTagInt(victim.getInventory().getChestplate(), "prot");
            mnoznik += Utils.getTagDouble(victim.getInventory().getChestplate(), "defMoby");
        }
        if (victim.getInventory().getLeggings() != null) {
            prot += Utils.getTagInt(victim.getInventory().getLeggings(), "prot");
            mnoznik += Utils.getTagDouble(victim.getInventory().getLeggings(), "defMoby");
        }
        if (victim.getInventory().getBoots() != null) {
            prot += Utils.getTagInt(victim.getInventory().getBoots(), "prot");
            mnoznik += Utils.getTagDouble(victim.getInventory().getBoots(), "defMoby");
        }

        if (rpgcore.getKlasyManager().getPaladinPassive().asMap().containsKey(uuid)) {
            mnoznik += 10;
        }

        final BonyUser bonyUser = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bonyUser.getDef5().getType().equals(Material.AIR)) {
            mnoznik -= 5;
            bony += 5;
        }
        if (!bonyUser.getDef10().getType().equals(Material.AIR)) {
            mnoznik -= 10;
            bony += 10;
        }
        if (!bonyUser.getDef15().getType().equals(Material.AIR)) {
            mnoznik -= 15;
            bony += 15;
        }




        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            gildia += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        double finalValue = prot;

        finalValue *= 2;
        finalValue *= (gildia / 100);
        finalValue *= 1.25 * (mnoznik / 100);
        finalValue *= (bony / 100);

        double wartoscDefa = DoubleUtils.round(finalValue, 2);
        //victim.sendMessage(Utils.format("&6&lDEBUG"));
        //victim.sendMessage(Utils.format("&aWartosc defa: " + wartoscDefa));
        //victim.sendMessage(Utils.format("&aMnoznik: " + mnoznik));

        double finalDmg = DoubleUtils.round((mobDamage / wartoscDefa) * 2, 3);

        //victim.sendMessage(Utils.format("&aFinal Dmg: " + finalDmg));

        //victim.sendMessage(Utils.format("&6&lDEBUG"));

        return finalDmg;
    }
}
