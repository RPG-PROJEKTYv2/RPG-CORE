package rpg.rpgcore.klasy.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.klasy.enums.KlasyMain;
import rpg.rpgcore.klasy.enums.KlasySide;
import rpg.rpgcore.klasy.objects.Klasa;
import rpg.rpgcore.utils.Utils;

public class KlasyTask implements Runnable{
    private final RPGCORE rpgcore;

    public KlasyTask(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.rpgcore.getServer().getScheduler().scheduleAsyncRepeatingTask(rpgcore, this, 0L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final Klasa klasa = rpgcore.getKlasyManager().find(player.getUniqueId());
            if (klasa == null) continue;
            if (klasa.getMainKlasa() == KlasyMain.NIE_WYBRANO) continue;
            if (klasa.getPodKlasa() == KlasySide.NIE_WYBRANO) continue;

            if (klasa.getPodKlasa() == KlasySide.PALADYN) {
                if (klasa.hasLeftClickCooldown()) continue;
                if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() != null && (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                        || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) && player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    final EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) player.getLastDamageCause();

                    if (e.getDamager() == null || !(e.getDamager() instanceof Player)) continue;

                    if (player.getHealth() < player.getMaxHealth() / 2) {
                        klasa.setCdLMB(System.currentTimeMillis() + 180_000);
                        rpgcore.getKlasyManager().getPaladinPassive().asMap().put(player.getUniqueId(), System.currentTimeMillis() + 180_000);
                        player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie aktywowano &eTwardy Jak Skala &8(&6Pasywnie&8)"));
                        klasa.save();
                        continue;
                    }
                }
            }
            if (klasa.getPodKlasa() == KlasySide.SKRYTOBOJCA) {
                if (klasa.hasLeftClickCooldown()) continue;
                if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() != null && (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                        || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) && player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    final EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) player.getLastDamageCause();

                    if (e.getDamager() == null || !(e.getDamager() instanceof Player)) continue;

                    if (player.getHealth() < player.getMaxHealth() * 0.3) {
                        klasa.setCdLMB(System.currentTimeMillis() + 180_000);
                        rpgcore.getKlasyManager().getUsedSkrytoPassive().add(player.getUniqueId());
                        player.sendMessage(Utils.format("&c&lSkrytobojca &8>> &aPomyslnie aktywowano &dAsasynacja &8(&6Pasywnie&8)"));
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            for (Player rest : player.getWorld().getPlayers()) {
                                rest.hidePlayer(player);
                            }
                            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                for (Player rest : player.getWorld().getPlayers()) {
                                    rest.showPlayer(player);
                                }
                                rpgcore.getKlasyManager().getUsedSkrytoPassive().remove(player.getUniqueId());
                            }, 80L);
                        }, 1L);
                        klasa.save();
                    }
                }
            }
        }
    }
}
