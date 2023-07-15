package rpg.rpgcore.klasy.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
                if (player.getHealth() < player.getMaxHealth() / 2) {
                    if (klasa.hasLeftClickCooldown()) continue;
                    klasa.setCdLMB(System.currentTimeMillis() + 180_000);
                    rpgcore.getKlasyManager().getPaladinPassive().asMap().put(player.getUniqueId(), System.currentTimeMillis() + 180_000);
                    player.sendMessage(Utils.format("&c&lDowodca Strazy &8>> &aPomyslnie aktywowano &eTwardy Jak Skala &8(&6Pasywnie&8)"));
                    klasa.save();
                    continue;
                }
            }
            if (klasa.getPodKlasa() == KlasySide.NINJA) {
                if (rpgcore.getKlasyManager().getNinjaRMB().asMap().containsKey(player.getUniqueId())) {
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        player.setWalkSpeed(0.4F);
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            player.setWalkSpeed(0.2F);
                        }, 200L);
                    }, 1L);
                }
                continue;
            }
            if (klasa.getPodKlasa() == KlasySide.SKRYTOBOJCA) {
                if (player.getHealth() < player.getMaxHealth() * 0.3) {
                    if (klasa.hasLeftClickCooldown()) continue;
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
