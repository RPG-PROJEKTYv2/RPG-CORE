package rpg.rpgcore.armor;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;

public class ArmorEffectsHelper {
    public static void addEffectChestPlate(Player player, int prot) {
        final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId());
        int k1 = 0;
        if (prot > 30) {
            k1 = 2;
        }
        if (prot > 39) {
            k1 = 4;
        }
        if (prot > 49) {
            k1 = 5;
        }
        if (prot > 59) {
            k1 = 6;
        }
        if (prot > 69) {
            k1 = 8;
        }
        if (prot > 79) {
            k1 = 12;
        }
        if (prot > 149) {
            k1 = 15;
        }
        if (prot > 199) {
            k1 = 17;
        }
        if (prot > 220) {
            k1 = 20;
        }
        if (prot > 249) {
            k1 = 25;
        }
        player.setMaxHealth((bonuses.getBonusesUser().getDodatkowehp() * 2) + (k1 * 2));

        if (player.getWorld().getName().equals("world")) {
            player.setHealth(player.getMaxHealth());
        }
    }

    public static void addEffectLeggings(Player player, int value) {
        int k = 0;
        if (value > 30) {
            k = 1;
        }
        if (value > 60) {
            k = 2;
        }
        if (value > 170) {
            k = 3;
        }
        if (value > 230) {
            k = 4;
        }
        if (k == 0) player.removePotionEffect(PotionEffectType.REGENERATION);
        if (k > 0) {
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999999, k-1));
        }
    }

    public static void addEffectBoots(Player player, int value) {
        int k = 0;
        if (value > 19) {
            k = 75;
        }
        if (value > 39) {
            k = 150;
        }
        if (value > 69) {
            k = 200;
        }
        if (value > 99) {
            k = 300;
        }
        if (value > 149) {
            k = 450;
        }
        if (value > 199) {
            k = 600;
        }
        if (value > 249) {
            k = 750;
        }
        if (player.getInventory().getBoots() != null && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasDisplayName()
                && player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Buty Gornika")) {
            k = 600;
        }

        final Bonuses bonuses = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId());

        float walkSpeed = Math.min(0.2F + ((bonuses.getBonusesUser().getSzybkosc() + k) / 1500.0F), 1.0F);
        if (RPGCORE.getInstance().getKlasyManager().getNinjaRMB().asMap().containsKey(player.getUniqueId())) {
            walkSpeed = Math.min(walkSpeed * 1.15F, 1.0F);
        }
        if (player.getActivePotionEffects().stream().anyMatch(potion -> potion.getType() == PotionEffectType.SLOW && potion.getAmplifier() == 8)) walkSpeed = 0F;
        player.setWalkSpeed(walkSpeed);
        //player.removePotionEffect(PotionEffectType.SPEED);
        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, (int) Math.floor((bonuses.getBonusesUser().getSzybkosc() + k) / 100.0) - 1));
    }

    public static void addEffectHelmet(Player player, int value) {
        int k = 0;
        if (value > 39) {
            k = 1;
        }
        if (value > 79) {
            k = 2;
        }
        if (value > 119) {
            k = 3;
        }
        if (value > 159) {
            k = 4;
        }
        if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().hasItemMeta()
                && player.getInventory().getHelmet().getItemMeta().hasDisplayName() && player.getInventory().getHelmet().getItemMeta().getDisplayName().contains("Helm Gornika")) {
            k = 3;
        }
        if (k == 0) player.removePotionEffect(PotionEffectType.JUMP);
        if (k > 0) {
            player.removePotionEffect(PotionEffectType.JUMP);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, k));
        }
    }

    public static void addEffectsArmor(Player player) {
        if (player.getInventory().getHelmet() == null) {
            if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                player.removePotionEffect(PotionEffectType.JUMP);
            }
        } else {
            player.getInventory().setHelmet(ItemHelper.checkEnchants(player.getInventory().getHelmet(), player));
            addEffectHelmet(player, Utils.getTagInt(player.getInventory().getHelmet(), "prot"));
        }
        if (player.getInventory().getChestplate() == null) {
            player.setMaxHealth(RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getDodatkowehp() * 2);
        } else {
            player.getInventory().setChestplate(ItemHelper.checkEnchants(player.getInventory().getChestplate(), player));
            addEffectChestPlate(player, Utils.getTagInt(player.getInventory().getChestplate(), "prot"));
        }
        if (player.getInventory().getLeggings() == null) {
            if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                player.removePotionEffect(PotionEffectType.REGENERATION);
            }
        } else {
            player.getInventory().setLeggings(ItemHelper.checkEnchants(player.getInventory().getLeggings(), player));
            addEffectLeggings(player, Utils.getTagInt(player.getInventory().getLeggings(), "prot"));
        }
        if (player.getInventory().getBoots() == null) {
            addEffectBoots(player, 0);
        } else {
            player.getInventory().setBoots(ItemHelper.checkEnchants(player.getInventory().getBoots(), player));
            addEffectBoots(player, Utils.getTagInt(player.getInventory().getBoots(), "prot"));
        }
    }
}
