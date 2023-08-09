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
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bao.objects.BaoUser;
import rpg.rpgcore.bonuses.BonusesUser;
import rpg.rpgcore.dodatki.akcesoriaD.objects.AkcesoriaDodatUser;
import rpg.rpgcore.dodatki.akcesoriaP.objects.AkcesoriaPodstUser;
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

        stand.setLocation(entityLocation.getX() + randomx, entityLocation.getY() + 0.3, entityLocation.getZ() + randomz, 0, 0);
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
        double dmg = 4;
        double mnoznik = 100;
        double krytyk = 0;
        double wzmKryt = 0;
        double wzmKrytDmg = 150;

        double drugiMnoznik = 100;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {
            final String type = Utils.getTagString(weapon, "type");

            switch (type) {
                case "ks":
                    return 0;
                case "tyra":
                    mnoznik += Utils.getTagInt(weapon, "ludzie");
                    break;
                default:
                    break;
            }

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
        mnoznik += bonuses.getMinussrednieobrazenia();
        mnoznik += bonuses.getMinusobrazenianaludzi();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        wzmKryt += bonuses.getSzansanawzmocnieniekryta();
        wzmKrytDmg += bonuses.getWzmocnienieKryta();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (attacker.getInventory().getHelmet() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getHelmet(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getChestplate() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getChestplate(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getLeggings() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getLeggings(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getBoots() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getBoots(), "szansaWzmKryt");
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

        if (!victim.canSee(attacker)) {
            mnoznik += 5;
            for (Player rest : Bukkit.getOnlinePlayers()) rest.showPlayer(attacker);
            rpgcore.getKlasyManager().getUsedSkrytoPassive().remove(attacker.getUniqueId());
        }


        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser bao = rpgcore.getBaoManager().find(uuid).getBaoUser();
            if (!bao.getBonus1().equals("Silny Na Potwory")) {
                drugiMnoznik += bao.getValue1();
                mnoznik -= bao.getValue1();
            }
        }

        final BonyUser bony = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bony.getDmg5().getType().equals(Material.AIR)) {
            mnoznik -= 5;
            drugiMnoznik += 5;
        }
        if (!bony.getDmg10().getType().equals(Material.AIR)) {
            mnoznik -= 10;
            drugiMnoznik += 10;
        }
        if (!bony.getDmg15().getType().equals(Material.AIR)) {
            mnoznik -= 15;
            drugiMnoznik += 15;
        }

        final AkcesoriaDodatUser akcesoriaDodat = rpgcore.getDodatkiManager().find(uuid).getAkcesoriaDodatkowe();
        double dmgEnergia = 100;
        if (!akcesoriaDodat.getEnergia().getType().equals(Material.AIR)) {
            dmgEnergia += Utils.getTagDouble(akcesoriaDodat.getEnergia(), "mDmg");
            mnoznik += dmgEnergia;
        }

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
            mnoznik += rpgcore.getGuildManager().getGuildSilnyNaLudzi(tag);
        }

        dmg = (dmg * (mnoznik / 100) * (drugiMnoznik / 100)) / (dmgEnergia / 100);
        krytyk /= 4;

        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * (1.5 + (1.5 * (wzmKrytDmg / 100)));
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * (1.5 + (1.5 * (wzmKrytDmg / 100)));
            }
        }

        final double finalDmg = DoubleUtils.round(dmg, 2);

        if (RPGCORE.getInstance().getUserManager().find(uuid).getKrytyk() < finalDmg) {
            attacker.sendMessage(Utils.format("&4Damage &8>> &cUstanowiles swoj nowy najwiekszy zadany dmg! &4(" + finalDmg + " dmg)"));
            RPGCORE.getInstance().getUserManager().find(uuid).setKrytyk(finalDmg);
        }

        return finalDmg;
    }

    public double calculateAttackerDmgToEntity(final Player attacker, final Entity victim) {
        attacker.setItemInHand(ItemHelper.checkEnchants(attacker.getItemInHand(), attacker));
        final ItemStack weapon = attacker.getItemInHand();
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double dmg = 4;
        double mnoznik = 100;
        double krytyk = 0;
        double wzmKryt = 0;
        double wzmKrytDmg = 0;

        double drugiMnoznik = 100;

        // MIECZ DMG
        if (weapon != null && weapon.getType() != Material.AIR && String.valueOf(weapon.getType()).contains("SWORD")) {

            final String type = Utils.getTagString(weapon, "type");

            switch (type) {
                case "tyra":
                    return 0;
                case "ks":
                    mnoznik += Utils.getTagInt(weapon, "moby");
                    break;
                default:
                    break;
            }

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
        mnoznik += bonuses.getMinussrednieobrazenia();
        mnoznik += bonuses.getMinusobrazenianamoby();
        dmg += bonuses.getDodatkoweobrazenia();
        krytyk += bonuses.getSzansanakryta();
        wzmKryt += bonuses.getSzansanawzmocnieniekryta();
        wzmKrytDmg += bonuses.getWzmocnienieKryta();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (attacker.getInventory().getHelmet() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getHelmet(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getChestplate() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getChestplate(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getLeggings() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getLeggings(), "szansaWzmKryt");
        }
        if (attacker.getInventory().getBoots() != null) {
            wzmKryt += Utils.getTagDouble(attacker.getInventory().getBoots(), "szansaWzmKryt");
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

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
        }

        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser bao = rpgcore.getBaoManager().find(uuid).getBaoUser();
            if (!bao.getBonus1().equals("Silny Na Ludzi")) {
                drugiMnoznik += bao.getValue1();
                mnoznik -= bao.getValue1();
            }
        }

        final BonyUser bony = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bony.getDmg5().getType().equals(Material.AIR)) {
            mnoznik -= 5;
            drugiMnoznik += 5;
        }
        if (!bony.getDmg10().getType().equals(Material.AIR)) {
            mnoznik -= 10;
            drugiMnoznik += 10;
        }
        if (!bony.getDmg15().getType().equals(Material.AIR)) {
            mnoznik -= 15;
            drugiMnoznik += 15;
        }

        final AkcesoriaDodatUser akcesoriaDodat = rpgcore.getDodatkiManager().find(uuid).getAkcesoriaDodatkowe();
        double dmgEnergia = 100;
        if (!akcesoriaDodat.getEnergia().getType().equals(Material.AIR)) {
            dmgEnergia += Utils.getTagDouble(akcesoriaDodat.getEnergia(), "mDmg");
            mnoznik += dmgEnergia;
        }

        dmg = (dmg * (mnoznik / 100) * (drugiMnoznik / 100)) / (dmgEnergia / 100);
        krytyk /= 4;

        if (ChanceHelper.getChance(krytyk)) {
            dmg = dmg * (1.5 + (1.5 * (wzmKrytDmg / 100)));
            if (ChanceHelper.getChance(wzmKryt)) {
                dmg = dmg * (1.5 + (1.5 * (wzmKrytDmg / 100)));
            }
        }

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

        double drugiMnoznik = 100;

        mnoznik += bonuses.getSredniadefensywa();
        mnoznik += bonuses.getDefnaludzi();
        mnoznik += bonuses.getMinusdefnaludzi();
        mnoznik += bonuses.getMinussredniadefensywa();

        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznik += rpgcore.getGuildManager().getGuildDefNaLudzi(tag);
            mnoznik += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser bao = rpgcore.getBaoManager().find(uuid).getBaoUser();
            if (!bao.getBonus2().equals("Srednia defensywa przeciwko potworom")) {
                drugiMnoznik += bao.getValue2();
                mnoznik -= bao.getValue2();
            }
        }

        final BonyUser bony = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bony.getDef5().getType().equals(Material.AIR)) {
            mnoznik -= 5;
            drugiMnoznik += 5;
        }
        if (!bony.getDef10().getType().equals(Material.AIR)) {
            mnoznik -= 10;
            drugiMnoznik += 10;
        }
        if (!bony.getDef15().getType().equals(Material.AIR)) {
            mnoznik -= 15;
            drugiMnoznik += 15;
        }

        if (rpgcore.getKlasyManager().getPaladinPassive().asMap().containsKey(uuid)) {
            mnoznik += 10;
        }

        final AkcesoriaPodstUser akcesoriaPodst = rpgcore.getDodatkiManager().find(uuid).getAkcesoriaPodstawowe();
        if (!akcesoriaPodst.getTarcza().getType().equals(Material.AIR)) {
            final double defTarcza = Utils.getTagDouble(akcesoriaPodst.getTarcza(), "def");
            mnoznik -= defTarcza;
            drugiMnoznik += defTarcza;
        }
        final AkcesoriaDodatUser akcesoriaDodat = rpgcore.getDodatkiManager().find(uuid).getAkcesoriaDodatkowe();
        double energia = 100;
        if (!akcesoriaDodat.getEnergia().getType().equals(Material.AIR)) {
            energia += Utils.getTagDouble(akcesoriaDodat.getEnergia(), "def");
            mnoznik -= energia;
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

        def = (def * 2) * Math.pow((mnoznik / 100), 2) * 0.5 * (drugiMnoznik / 100) * (energia / 100);

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
        final UUID uuid = attacker.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double przebicie = bonuses.getPrzebiciePancerza();


        return DoubleUtils.round(przebicie, 2);
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

    public double calculatePlayerThornsDmg(final Player victim, final Entity attacker) {
        if (attacker instanceof Creature) {
            double thornsDmg = 0;
            double mnoznik = 0.1;

            thornsDmg += calculatePlayerThorns(victim);

            if (thornsDmg > 15) {
                mnoznik = 0.13;
            }
            if (thornsDmg > 25) {
                mnoznik = 0.17;
            }
            if (thornsDmg > 45) {
                mnoznik = 0.23;
            }
            if (thornsDmg > 80) {
                mnoznik = 0.29;
            }
            if (thornsDmg > 140) {
                mnoznik = 0.34;
            }
            if (thornsDmg > 185) {
                mnoznik = 0.38;
            }
            if (thornsDmg > 199) {
                mnoznik = 0.4;
            }


            return DoubleUtils.round(mnoznik, 2);
        }
        //TODO Do dokonczenia
        if (attacker instanceof Player) {
            final UUID uuid = victim.getUniqueId();
            final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
            double thornsDmg = this.calculatePlayerThorns(victim);
            double mnoznik = 100;

            mnoznik += bonuses.getSrednieobrazenia();
            mnoznik += bonuses.getSilnynaludzi();
            mnoznik += bonuses.getMinussrednieobrazenia();
            mnoznik += bonuses.getMinusobrazenianamoby();
            thornsDmg += bonuses.getDodatkoweobrazenia();

            // GILDIA
            if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
                final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
                mnoznik += rpgcore.getGuildManager().getGuildSredniDmg(tag);
            }


        }
        return 0;
    }

    public double calculateEntityDamageToPlayer(final Entity entity, final Player victim) {
        final UUID uuid = victim.getUniqueId();
        final BonusesUser bonuses = rpgcore.getBonusesManager().find(uuid).getBonusesUser();
        double mnoznikProcenty = 100;
        int prot = 0;
        double protMnoznik;
        final double mnoznikBaza = 0.1;
        final double mobDamage = EntityDamage.getByName(Utils.removeColor(entity.getName()).trim());
        double drugiMnoznik = 100;

        mnoznikProcenty += bonuses.getSredniadefensywa();
        mnoznikProcenty += bonuses.getDefnamoby();
        mnoznikProcenty += bonuses.getMinusdefnamoby();
        mnoznikProcenty += bonuses.getMinussredniadefensywa();

        // MAGICZNE ZACZAROWANIE (ZBROJA)
        if (victim.getInventory().getHelmet() != null) {
            mnoznikProcenty += Utils.getTagDouble(victim.getInventory().getHelmet(), "defMoby");
        }
        if (victim.getInventory().getChestplate() != null) {
            mnoznikProcenty += Utils.getTagDouble(victim.getInventory().getChestplate(), "defMoby");
        }
        if (victim.getInventory().getLeggings() != null) {
            mnoznikProcenty += Utils.getTagDouble(victim.getInventory().getLeggings(), "defMoby");
        }
        if (victim.getInventory().getBoots() != null) {
            mnoznikProcenty += Utils.getTagDouble(victim.getInventory().getBoots(), "defMoby");
        }

        if (rpgcore.getKlasyManager().getPaladinPassive().asMap().containsKey(uuid)) {
            mnoznikProcenty += 10;
        }


        // GILDIA
        if (!rpgcore.getGuildManager().getGuildTag(uuid).equals("Brak Klanu")) {
            final String tag = rpgcore.getGuildManager().getGuildTag(uuid);
            mnoznikProcenty += rpgcore.getGuildManager().getGuildSredniDef(tag);
        }

        if (!rpgcore.getBaoManager().isNotRolled(uuid)) {
            final BaoUser bao = rpgcore.getBaoManager().find(uuid).getBaoUser();
            if (!bao.getBonus2().equals("Srednia defensywa przeciwko ludziom")) {
                drugiMnoznik += bao.getValue2();
                mnoznikProcenty -= bao.getValue2();
            }
        }

        final BonyUser bony = rpgcore.getDodatkiManager().find(uuid).getBony();
        if (!bony.getDef5().getType().equals(Material.AIR)) {
            mnoznikProcenty -= 5;
            drugiMnoznik += 5;
        }
        if (!bony.getDef10().getType().equals(Material.AIR)) {
            mnoznikProcenty -= 10;
            drugiMnoznik += 10;
        }
        if (!bony.getDef15().getType().equals(Material.AIR)) {
            mnoznikProcenty -= 15;
            drugiMnoznik += 15;
        }

        final AkcesoriaPodstUser akcesoriaPodst = rpgcore.getDodatkiManager().find(uuid).getAkcesoriaPodstawowe();
        if (!akcesoriaPodst.getTarcza().getType().equals(Material.AIR)) {
            final double defTarcza = Utils.getTagDouble(akcesoriaPodst.getTarcza(), "def");
            mnoznikProcenty -= defTarcza;
            drugiMnoznik += defTarcza;
        }

        if (victim.getInventory().getHelmet() != null) {
            prot += Utils.getTagInt(victim.getInventory().getHelmet(), "prot");
        }
        if (victim.getInventory().getChestplate() != null) {
            prot += Utils.getTagInt(victim.getInventory().getChestplate(), "prot");
        }
        if (victim.getInventory().getLeggings() != null) {
            prot += Utils.getTagInt(victim.getInventory().getLeggings(), "prot");
        }
        if (victim.getInventory().getBoots() != null) {
            prot += Utils.getTagInt(victim.getInventory().getBoots(), "prot");
        }

        protMnoznik = prot * 0.01;

        double finalDmg = mobDamage / ((mnoznikProcenty / 100) + protMnoznik + mnoznikBaza) / (drugiMnoznik * 0.9 / 100);


        return DoubleUtils.round(finalDmg * 2, 3);
    }
}
