package rpg.rpgcore.metiny;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.metinolog.MetinologItems;
import rpg.rpgcore.npc.metinolog.MetinologObject;
import rpg.rpgcore.utils.LocationHelper;
import rpg.rpgcore.utils.MobDropHelper;
import rpg.rpgcore.utils.Utils;

public class MetinyHelper {

    public static void spawnMetin(int id) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        if (metiny.getMetins().getHealth() == 0 || metiny.getMetins().getHealth() < 0) {
            String world = metiny.getMetins().getWorld();
            Location loc = LocationHelper.locFromString(metiny.getMetins().getCoordinates());
            Location location = new Location(Bukkit.getServer().getWorld(world), loc.getX(), loc.getY(), loc.getZ());
            Bukkit.getServer().getWorld(world).spawnEntity(location, EntityType.ENDER_CRYSTAL).setCustomName(String.valueOf(id));
            metiny.getMetins().setHealth(metiny.getMetins().getMaxhealth());
        }
    }

    public static void spawnMetinByPass(int id) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        String world = metiny.getMetins().getWorld();
        Location loc = LocationHelper.locFromString(metiny.getMetins().getCoordinates());
        Location location = new Location(Bukkit.getServer().getWorld(world), loc.getX(), loc.getY(), loc.getZ());
        Bukkit.getServer().getWorld(world).spawnEntity(location, EntityType.ENDER_CRYSTAL).setCustomName(String.valueOf(id));
        metiny.getMetins().setHealth(metiny.getMetins().getMaxhealth());
    }

    public static void respAllMetins() {
        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equals("Dungeon80-90")) {
                continue;
            }
            for (org.bukkit.entity.Entity e : w.getEntities()) {
                if (e.getType().equals(EntityType.ENDER_CRYSTAL)) {
                    e.remove();
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

    public static void attackMetin(int id, int damage, Entity entity, Player player) {
        if (!RPGCORE.getInstance().getMetinyManager().isMetin(id)) {
            return;
        }
        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
        if (player.getItemInHand() != null) {
            if (String.valueOf(player.getItemInHand().getType()).contains("_SWORD")) {
                if (player.getItemInHand().getItemMeta().getLore() != null) {
                    int sharp = Utils.getSharpnessLevel(player.getItemInHand());
                    if (sharp < 200 && sharp > 99) {
                        damage = damage + 1;
                    }
                    if (sharp < 300 && sharp > 199) {
                        damage = damage + 2;
                    }
                    if (sharp < 400 && sharp > 299) {
                        damage = damage + 3;
                    }
                    if (sharp < 500 && sharp > 399) {
                        damage = damage + 4;
                    }
                    if (sharp < 600 && sharp > 499) {
                        damage = damage + 5;
                    }
                    if (sharp < 700 && sharp > 599) {
                        damage = damage + 6;
                    }
                    if (sharp < 800 && sharp > 699) {
                        damage = damage + 7;
                    }
                    if (sharp < 900 && sharp > 799) {
                        damage = damage + 8;
                    }
                    if (sharp < 1000 && sharp > 899) {
                        damage = damage + 9;
                    }
                    if (sharp < 1100 && sharp > 999) {
                        damage = damage + 10;
                    }
                }
            }
        }
        if (damage >= metiny.getMetins().getHealth()) {
            getDropMetin(id, player, entity);
            entity.remove();
            metiny.getMetins().setHealth(0);
            player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 3);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
            RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&b&lMetin: &f&l0&8&l/&f&l" + metiny.getMetins().getMaxhealth());
            return;
        }
        metiny.getMetins().setHealth(metiny.getMetins().getHealth() - damage);
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&b&lMetin: &f&l" + metiny.getMetins().getHealth() + "&8&l/&f&l" + metiny.getMetins().getMaxhealth());
    }

    public static void getDropMetin(int id, Player player, Entity entity) {
        final MetinologObject metinolog = RPGCORE.getInstance().getMetinologNPC().find(player.getUniqueId());
        //MobDropHelper.addDropPlayer(player, "I183", 1, 100.0, true, true, entity);
        double kasaToAdd = 0;
        final double kasa = RPGCORE.getInstance().getPlayerManager().getPlayerKasa(player.getUniqueId());
        if ((id >= 1 && id <= 10) || (id >= 10001 && id <= 10010)) {
            kasaToAdd = 250;
            if (metinolog.getMetinologUser().getPostepGive() == 0) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I1-10", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 0) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 11 && id <= 20) || (id >= 10011 && id <= 10020)) {
            kasaToAdd = 500;
            if (metinolog.getMetinologUser().getPostepGive() == 1) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I10-20", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 1) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 21 && id <= 30) || (id >= 10021 && id <= 10030)) {
            kasaToAdd = 750;
            if (metinolog.getMetinologUser().getPostepGive() == 2) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I20-30", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 2) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 31 && id <= 40) || (id >= 10031 && id <= 10040)) {
            kasaToAdd = 1000;
            if (metinolog.getMetinologUser().getPostepGive() == 3) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I30-40", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 3) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 41 && id <= 50) || (id >= 10041 && id <= 10050)) {
            kasaToAdd = 1500;
            if (metinolog.getMetinologUser().getPostepGive() == 4) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I40-50", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 4) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 51 && id <= 60) || (id >= 10051 && id <= 10060)) {
            kasaToAdd = 2500;
            if (metinolog.getMetinologUser().getPostepGive() == 5) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I50-60", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 5) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 61 && id <= 70) || (id >= 10061 && id <= 10070)) {
            kasaToAdd = 3500;
            if (metinolog.getMetinologUser().getPostepGive() == 6) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I60-70", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 6) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if ((id >= 71 && id <= 80) || (id >= 10071 && id <= 10080)) {
            kasaToAdd = 6000;
            if (metinolog.getMetinologUser().getPostepGive() == 7) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I70-80", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 7) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 81 && id <= 90) {
            kasaToAdd = 8000;
            if (metinolog.getMetinologUser().getPostepGive() == 8) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I80-90", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 8) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 91 && id <= 100) {
            kasaToAdd = 10000;
            if (metinolog.getMetinologUser().getPostepGive() == 9) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I90-100", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 9) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 101 && id <= 110) {
           kasaToAdd = 12500;
            if (metinolog.getMetinologUser().getPostepGive() == 10) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I100-110", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 10) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 111 && id <= 120) {
           kasaToAdd = 15000;
            if (metinolog.getMetinologUser().getPostepGive() == 11) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I110-120", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 11) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        if (id >= 121 && id <= 130) {
           kasaToAdd = 17500;
            if (metinolog.getMetinologUser().getPostepGive() == 12) {
                MobDropHelper.addDropPlayer(player, MetinologItems.getItem("I120-130", 1), 25, true, true, entity);
            }
            if (metinolog.getMetinologUser().getPostepKill() == 12) {
                metinolog.getMetinologUser().setPostepMisjiKill(metinolog.getMetinologUser().getPostepMisjiKill() + 1);
            }
        }
        final String worldName = String.valueOf(entity.getWorld().getName()).replaceAll(" ", "");
        player.sendMessage(worldName);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn " + worldName.replace("exp", "") + "-LEVEL3 12 " + worldName + "," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
        RPGCORE.getInstance().getPlayerManager().updatePlayerKasa(player.getUniqueId(), kasa + kasaToAdd);
        RPGCORE.getInstance().getPlayerManager().updatePlayerOsMetiny(player.getUniqueId(), RPGCORE.getInstance().getPlayerManager().getPlayerOsMetiny(player.getUniqueId()) + 1);
        player.sendMessage(Utils.format("&2+ &a" + kasaToAdd + "&2$"));
    }
}
