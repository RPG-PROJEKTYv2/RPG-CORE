package rpg.rpgcore.metiny;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.dungeons.DungeonStatus;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.ranks.types.RankTypePlayer;
import rpg.rpgcore.utils.*;
import rpg.rpgcore.utils.globalitems.npc.MetinologItems;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;

import java.util.Map;

public class MetinyHelper {

    public static void spawnMetin(int id) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        final Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        if (metiny.getMetins().getHealth() == 0 || metiny.getMetins().getHealth() < 0) {
            final Location location = metiny.getLocation();

            final Entity e = location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
            e.setCustomName(Utils.format(metiny.getMetins().getName()));
            e.setCustomNameVisible(true);
            metiny.getMetins().setHealth(metiny.getMetins().getMaxhealth());
        }
    }

    public static void despawnMetin(int id) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        if (metiny.getMetins().getHealth() > 0) {
            String world = metiny.getMetins().getWorld();
            for (Entity e : Bukkit.getServer().getWorld(world).getEntities()) {
                if (e.getLocation().equals(metiny.getLocation())) {
                    e.remove();
                }
            }
            metiny.getMetins().setHealth(0);
        }
    }

    public static void spawnMetinByPass(int id) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        final Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        final Location location = metiny.getLocation();
        final Entity e = location.getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
        e.setCustomName(Utils.format(metiny.getMetins().getName()));
        e.setCustomNameVisible(true);
        metiny.getMetins().setHealth(metiny.getMetins().getMaxhealth());
    }

    public static void respAllMetins() {
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equals("zamekNieskonczonosci") && !w.getPlayers().isEmpty()) {
                continue;
            }
            if (w.getName().equals("DemonTower") && !w.getPlayers().isEmpty()) {
                continue;
            }
            if (w.getName().equals("Dungeon60-70") && !w.getPlayers().isEmpty()) {
                continue;
            }
            if (w.getName().equals("Dungeon70-80") && !w.getPlayers().isEmpty()) {
                continue;
            }
            for (org.bukkit.entity.Entity e : w.getEntities()) {
                if (e.getType().equals(EntityType.ENDER_CRYSTAL)) {
                    if (RPGCORE.getInstance().getMetinyManager().isMetin(e.getLocation())) {
                        e.remove();
                    }
                }
            }
        }
        for (final Metiny metiny : RPGCORE.getInstance().getMetinyManager().getMetins()) {
            if (metiny.getMetins().getResp() == 1) {
                metiny.getMetins().setHealth(0);
                spawnMetin(metiny.getId());
            }
        }
    }

    public static void attackMetin(final Location loc, final Entity entity, final Player player) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(loc)) {
            System.out.println(2);
            return;
        }
        int damage = 1;
        final Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(loc);
        final int id = metiny.getId();
        if (player.getWorld().getName().equals("zamekNieskonczonosci") && RPGCORE.getInstance().getZamekNieskonczonosciManager().rdzenMap.get(id) == null) {
            player.sendMessage(Utils.format("&c&lKsiaze Mroku: &fHAHAHAHAH... nic nie zdzialasz!"));
            player.damage(player.getMaxHealth() / 10);
            return;
        } else if (player.getWorld().getName().equals("zamekNieskonczonosci") && RPGCORE.getInstance().getZamekNieskonczonosciManager().rdzenMap.get(id) != null) {
            for (Map.Entry<Integer, ArmorStand> entry : RPGCORE.getInstance().getZamekNieskonczonosciManager().rdzenMap.entrySet()) {
                if (entry.getKey() == id && !entry.getValue().getCustomName().contains(player.getName())) {
                    player.sendMessage(Utils.format("&5&lZaginiony Wladca &8>> &7Niestety tylko &c" + Utils.removeColor(entry.getValue().getCustomName().replace("Rdzen: ", "")) + " &7moze zniszczyc ten rdzen"));
                    player.damage(player.getMaxHealth() / 10);
                    return;
                }
            }
        }
        if (player.getItemInHand() == null || !String.valueOf(player.getItemInHand().getType()).contains("_SWORD") || player.getItemInHand().getItemMeta().getLore() == null) return;
        damage += RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getDmgMetiny();
        if (loc.getWorld().getName().equals("Dungeon60-70")) {
            if (ChanceHelper.getChance(MobDropHelper.getDropChance(player.getUniqueId(), 10))) {
                for (int i = 0; i < 3; i++) {
                    final double addX = ChanceHelper.getRandDouble(-0.5, 0.5);
                    final double addZ = ChanceHelper.getRandDouble(-0.5, 0.5);
                    RPGCORE.getMythicMobs().getMobManager().spawnMob("PIEKIELNY-PRZEDSIONEK-MOB", loc.clone().add(addX, 0, addZ));
                }
            }
        }
        if (damage >= metiny.getMetins().getHealth()) {

            if (player.getWorld().getName().equals("zamekNieskonczonosci")) {
                RPGCORE.getInstance().getZamekNieskonczonosciManager().rdzenMap.get(id).remove();
                RPGCORE.getInstance().getZamekNieskonczonosciManager().rdzenMap.remove(id);
                RPGCORE.getInstance().getZamekNieskonczonosciManager().destroyed++;
                entity.remove();
                player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 3);
                player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
                RPGCORE.getInstance().getNmsManager().sendActionBar(player, metiny.getMetins().getName() + ": &f&l0&8&l/&f&l" + metiny.getMetins().getMaxhealth());
                if (RPGCORE.getInstance().getZamekNieskonczonosciManager().destroyed == 2) { //ZMIENIC NA 4
                    RPGCORE.getInstance().getZamekNieskonczonosciManager().spawnMiniBoses();
                    return;
                }
                return;
            }

            getDropMetin(id, player, entity);
            if (RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 5) {
                RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + 1);
            }
            entity.remove();
            metiny.getMetins().setHealth(0);
            player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 3);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
            RPGCORE.getInstance().getNmsManager().sendActionBar(player, metiny.getMetins().getName() + ": &f&l0&8&l/&f&l" + metiny.getMetins().getMaxhealth());
            return;
        }
        metiny.getMetins().setHealth(metiny.getMetins().getHealth() - damage);
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, metiny.getMetins().getName() + ": &f&l" + metiny.getMetins().getHealth() + "&8&l/&f&l" + metiny.getMetins().getMaxhealth());
    }

    public static void getDropMetin(int id, Player player, Entity entity) {
        final MetinologObject metinolog = RPGCORE.getInstance().getMetinologNPC().find(player.getUniqueId());
        double kasaToAdd = 0;
        if ((id >= 1 && id <= 10) || (id >= 10001 && id <= 10010)) {
            kasaToAdd = 100;
            if (metinolog.getMetinologUser().getPostepGive() == 0) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I1-10", 1), 65, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 0) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 11 && id <= 20) || (id >= 10011 && id <= 10020)) {
            kasaToAdd = 200;
            if (metinolog.getMetinologUser().getPostepGive() == 1) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I10-20", 1), 65, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 1) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 21 && id <= 30) || (id >= 10021 && id <= 10030)) {
            kasaToAdd = 350;
            if (metinolog.getMetinologUser().getPostepGive() == 2) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I20-30", 1), 60, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 2) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 31 && id <= 40) || (id >= 10031 && id <= 10040)) {
            kasaToAdd = 400;
            if (metinolog.getMetinologUser().getPostepGive() == 3) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I30-40", 1), 50, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 3) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 41 && id <= 50) || (id >= 10041 && id <= 10050)) {
            kasaToAdd = 500;
            if (metinolog.getMetinologUser().getPostepGive() == 4) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I40-50", 1), 45, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 4) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 51 && id <= 60) || (id >= 10051 && id <= 10060)) {
            kasaToAdd = 700;
            if (metinolog.getMetinologUser().getPostepGive() == 5) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I50-60", 1), 35, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 5) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 61 && id <= 70) || (id >= 10061 && id <= 10070)) {
            kasaToAdd = 1000;
            if (metinolog.getMetinologUser().getPostepGive() == 7) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I60-70", 1), 30, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 7) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 71 && id <= 80) || (id >= 10071 && id <= 10080)) {
            kasaToAdd = 2000;
            if (metinolog.getMetinologUser().getPostepGive() == 8) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I70-80", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 8) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 81 && id <= 90) {
            kasaToAdd = 3000;
            if (metinolog.getMetinologUser().getPostepGive() == 9) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I80-90", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 9) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
            final PustelnikUser pustelnikUser = RPGCORE.getInstance().getPustelnikNPC().find(player.getUniqueId());
            if (pustelnikUser.getMissionId() == 5) {
                pustelnikUser.setProgress(pustelnikUser.getProgress() + 1);
                RPGCORE.getInstance().getPustelnikNPC().save(pustelnikUser);
            }
        }
        if (id >= 91 && id <= 100) {
            kasaToAdd = 4000;
            if (metinolog.getMetinologUser().getPostepGive() == 10) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I90-100", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 10) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 101 && id <= 110) {
           kasaToAdd = 6000;
            if (metinolog.getMetinologUser().getPostepGive() == 11) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I100-110", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 11) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 111 && id <= 120) {
           kasaToAdd = 8000;
            if (metinolog.getMetinologUser().getPostepGive() == 12) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I110-120", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 12) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 121 && id <= 130) {
           kasaToAdd = 10000;
            if (metinolog.getMetinologUser().getPostepGive() == 13) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I120-130", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 13) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        // ---------------------------------------- ICE TOWER ----------------------------------------
        if (id >= 20000 && id <= 20007) {
            kasaToAdd = 650;
            if (metinolog.getMetinologUser().getPostepGive() == 6) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("ILodowej-Wiezy", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 6) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
            if (RPGCORE.getInstance().getIceTowerManager().getStatus() == DungeonStatus.ETAP_3) {
                RPGCORE.getInstance().getIceTowerManager().incrementCount();
            }
        }
        // ---------------------------------------- PIEKIELNY PRZEDSIONEK ----------------------------------------
        if (id >= 30_000 && id <= 30_011) {
            kasaToAdd = 1_500;
            RPGCORE.getInstance().getPrzedsionekManager().incrementCounter();
        }
        // ---------------------------------------- KOLOSEUM ----------------------------------------
        if (id >= 40_000 && id <= 40_005) {
            kasaToAdd = 3_000;
            RPGCORE.getInstance().getKoloseumManager().incrementCounter();
        }

        final String worldName = String.valueOf(entity.getWorld().getName()).replaceAll(" ", "");
        final int mobsToSpawn = RPGCORE.getInstance().getMetinyManager().find(id).getMetins().getMoby();
        for (int i = 0; i < mobsToSpawn; i++) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn " + worldName.replace("map", "") + "-MOB1 1" +  " " + worldName + "," + DoubleUtils.round(entity.getLocation().getX() + ChanceHelper.getRandDouble(-0.2, 0.2), 2) + "," + DoubleUtils.round(entity.getLocation().getY() + 0.5, 2) + "," + DoubleUtils.round(entity.getLocation().getZ() + ChanceHelper.getRandDouble(-0.2, 0.2), 2));
        }
        double mnozik = 1;
        final RankTypePlayer rank = RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getRankPlayerUser().getRankType();
        if (rank == RankTypePlayer.VIP) mnozik = 1.25;
        if (rank == RankTypePlayer.TWORCA) mnozik = 1.35;
        if (rank == RankTypePlayer.ELITA) mnozik = 1.5;

        RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).setKasa(RPGCORE.getInstance().getUserManager().find(player.getUniqueId()).getKasa() + DoubleUtils.round((kasaToAdd * mnozik), 2));
        if (RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getSelectedMission() == 7) {
            RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().setProgress(RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId()).getMissions().getProgress() + DoubleUtils.round((kasaToAdd * mnozik), 2));
            RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMagazynier(player.getUniqueId(), RPGCORE.getInstance().getMagazynierNPC().find(player.getUniqueId())));
        }
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataUser(player.getUniqueId(), RPGCORE.getInstance().getUserManager().find(player.getUniqueId())));
        RPGCORE.getInstance().getOsManager().find(player.getUniqueId()).setMetinyProgress(RPGCORE.getInstance().getOsManager().find(player.getUniqueId()).getMetinyProgress() + 1);
        player.sendMessage(Utils.format("&2+ &a" + DoubleUtils.round((kasaToAdd * mnozik), 2) + "&2$"));
        final int szczescie = RPGCORE.getInstance().getBonusesManager().find(player.getUniqueId()).getBonusesUser().getSzczescie();
        if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 0.002))) {
            MobDropHelper.addDropPlayer(player, BonType.DMG_METINY_5.getBon().clone(), 100, true, true, entity);
            Bukkit.broadcastMessage(Utils.format("&b&lKamienie Metin &3>> &bGracz &3" + player.getName() + " &bznalazl jeden z &3&lZAGINIONYCH &bprzedmiotow!"));
        } else if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 0.005))) {
            MobDropHelper.addDropPlayer(player, BonType.DMG_METINY_3.getBon().clone(), 100, true, true, entity);
            Bukkit.broadcastMessage(Utils.format("&b&lKamienie Metin &3>> &bGracz &3" + player.getName() + " &bznalazl jeden z &3&lRZADKICH &bprzedmiotow!"));
        } else if (ChanceHelper.getChance(MobDropHelper.getDropChance(szczescie, 0.01))) {
            MobDropHelper.addDropPlayer(player, BonType.DMG_METINY_2.getBon().clone(), 100, true, true, entity);
            Bukkit.broadcastMessage(Utils.format("&b&lKamienie Metin &3>> &bGracz &3" + player.getName() + " &bznalazl jeden z &3&lRZADKICH &bprzedmiotow!"));
        }
    }
}
