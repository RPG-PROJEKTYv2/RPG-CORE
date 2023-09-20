package rpg.rpgcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.npc.gornik.ore.enums.Ores;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class KopalniaTask implements Runnable {
    private final RPGCORE rpgcore;

    public KopalniaTask(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        rpgcore.getServer().getScheduler().runTaskTimer(rpgcore, this, 20, 20);
    }

    @Override
    public void run() {
        Bukkit.getWorld("Kopalnia").getPlayers().forEach(player -> {
            if (Utils.removeColor(player.getName()).equals("Gornik")) return;
            boolean hasFullSet = true;
            for (final ItemStack armor : player.getInventory().getArmorContents()) {
                if (!(armor != null && armor.getType().toString().contains("LEATHER") && armor.getItemMeta().hasDisplayName() && armor.getItemMeta().getDisplayName().contains("Gornika"))) {
                    hasFullSet = false;
                    break;
                }
            }
            if (hasFullSet) {
                if (player.getActivePotionEffects().stream().noneMatch(effect -> effect.getType().equals(PotionEffectType.FAST_DIGGING))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999, 0, true, true));
                }
            } else {
                if (player.getActivePotionEffects().stream().noneMatch(effect -> effect.getType().equals(PotionEffectType.FAST_DIGGING) &&
                        (effect.getDuration() <= 600 || effect.getAmplifier() == 1))) {
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                }
            }

            final GornikUser user = rpgcore.getGornikNPC().find(player.getUniqueId());
            if (user != null) {
                if (user.getTimeLeft() == 0L) {
                    final User userMain = rpgcore.getUserManager().find(player.getUniqueId());
                    if (!userMain.getRankUser().isHighStaff() || (userMain.getRankUser().isHighStaff() && !userMain.isAdminCodeLogin())) {
                        player.teleport(rpgcore.getSpawnManager().getSpawn());
                        player.sendMessage(Utils.format("&6&lGornik &8>> &7Czas twojego biletu dobiegl wlasnie konca!"));
                    }
                } else {
                    rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> rpgcore.getNmsManager().sendActionBar(player, "&7Pozostaly Czas: &a" + Utils.durationToString(user.getTimeLeft(), true)));
                    user.setTimeLeft(user.getTimeLeft() - 1_000L);
                }
            }
        });
        rpgcore.getOreManager().getOres().forEach(ore -> {
            if (ore.getRespawnTime() <= System.currentTimeMillis() && ore.getRespawnTime() != -1L) {
                Ores ores = Ores.getRandomOre();
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.COAL_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.25))
                    ores = Ores.O1;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.IRON_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.225))
                    ores = Ores.O2;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.GOLD_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.185))
                    ores = Ores.O3;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.LAPIS_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.125))
                    ores = Ores.O4;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.EMERALD_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.094))
                    ores = Ores.O5;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.DIAMOND_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.05))
                    ores = Ores.O6;
                if (rpgcore.getOreManager().getOreMap().values().stream().filter(ore1 -> ore1.getType().equals(Material.REDSTONE_ORE) || ore1.getType().equals(Material.GLOWING_REDSTONE_ORE)).count() < Math.ceil(rpgcore.getOreManager().getOreMap().values().size() * 0.03))
                    ores = Ores.O7;
                rpgcore.getOreManager().setOre(ore, ores);
            }
        });
    }
}
