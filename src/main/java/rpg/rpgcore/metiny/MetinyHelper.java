package rpg.rpgcore.metiny;

import com.mojang.authlib.yggdrasil.response.User;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
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
            //getDropMetin(id, player, entity);
            entity.remove();
            metiny.getMetins().setHealth(0);
            player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 3);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
            RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&2&lPoziom Zdrowia Metina: &f&l0&8&l/&f&l" + metiny.getMetins().getMaxhealth());
            return;
        }
        metiny.getMetins().setHealth(metiny.getMetins().getHealth() - damage);
        RPGCORE.getInstance().getNmsManager().sendActionBar(player, "&2&lPoziom Zdrowia Metina: &f&l" + metiny.getMetins().getHealth() + "&8&l/&f&l" + metiny.getMetins().getMaxhealth());
    }

    /*public static void getDropMetin(int id, Player player, Entity entity) {
        MobDropHelper.addDropPlayer(player, "I183", 1, 100.0, true, true, entity);
        if (id == 1 || id == 2 || id == 3 || id == 4 || id == 5 || id == 6 || id == 7 || id == 8 || id == 9 || id == 10 || id == 10001 || id == 10002 || id == 10003 || id == 10004 || id == 10005 || id == 10006 || id == 10007 || id == 10008 || id == 10009 || id == 10010) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 250);
            MessageHelper.build("&8[&2+&8] &f250$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 1-10-3 12 1-30," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 0) {
                MobDropHelper.addDropPlayer(player, "I25", 1, 25, true, true, entity);
            }
        }
        if (id == 11 || id == 12 || id == 13 || id == 14 || id == 15 || id == 16 || id == 17 || id == 18 || id == 19 || id == 20 || id == 10011 || id == 10012 || id == 10013 || id == 10014 || id == 10015 || id == 10016 || id == 10017 || id == 10018 || id == 10019 || id == 10020) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 500);
            MessageHelper.build("&8[&2+&8] &f500$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 10-20-3 12 10-20," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 1) {
                MobDropHelper.addDropPlayer(player, "I26", 1, 25, true, true, entity);
            }
        }
        if (id == 21 || id == 22 || id == 23 || id == 24 || id == 25 || id == 26 || id == 27 || id == 28 || id == 29 || id == 30 || id == 10021 || id == 10022 || id == 10023 || id == 10024 || id == 10025 || id == 10026 || id == 10027 || id == 10028 || id == 10029 || id == 10030) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 750);
            MessageHelper.build("&8[&2+&8] &f750$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 20-30-3 12 20-30," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 2) {
                MobDropHelper.addDropPlayer(player, "I27", 1, 25, true, true, entity);
            }
        }
        if (id == 31 || id == 32 || id == 33 || id == 34 || id == 35 || id == 36 || id == 37 || id == 38 || id == 39 || id == 40 || id == 10031 || id == 10032 || id == 10033 || id == 10034 || id == 10035 || id == 10036 || id == 10037 || id == 10038 || id == 10039 || id == 10040) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 1000);
            MessageHelper.build("&8[&2+&8] &f1 000$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 30-40-3 12 30-40," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 3) {
                MobDropHelper.addDropPlayer(player, "I28", 1, 25, true, true, entity);
            }
        }
        if (id == 41 || id == 42 || id == 43 || id == 44 || id == 45 || id == 46 || id == 47 || id == 48 || id == 49 || id == 50 || id == 10041 || id == 10042 || id == 10043 || id == 10044 || id == 10045 || id == 10046 || id == 10047 || id == 10048 || id == 10049 || id == 10050) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 1500);
            MessageHelper.build("&8[&2+&8] &f1 500$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 40-50-3 12 40-50," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 4) {
                MobDropHelper.addDropPlayer(player, "I29", 1, 25, true, true, entity);
            }
        }
        if (id == 51 || id == 52 || id == 53 || id == 54 || id == 55 || id == 56 || id == 57 || id == 58 || id == 59 || id == 60 || id == 10051 || id == 10052 || id == 10053 || id == 10054 || id == 10055 || id == 10056 || id == 10057 || id == 10058 || id == 10059 || id == 10060) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 2500);
            MessageHelper.build("&8[&2+&8] &f2 500$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 60-80-3 12 60-80," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 5) {
                MobDropHelper.addDropPlayer(player, "I30", 1, 25, true, true, entity);
            }
        }
        if (id == 61 || id == 62 || id == 63 || id == 64 || id == 65 || id == 66 || id == 67 || id == 68 || id == 69 || id == 70 || id == 10061 || id == 10062 || id == 10063 || id == 10064 || id == 10065 || id == 10066 || id == 10067 || id == 10068 || id == 10069 || id == 10070) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 3500);
            MessageHelper.build("&8[&2+&8] &f3 500$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 80-90-3 12 80-90," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 6) {
                MobDropHelper.addDropPlayer(player, "I31", 1, 25, true, true, entity);
            }
        }
        if (id == 71 || id == 72 || id == 73 || id == 74 || id == 75 || id == 76 || id == 77 || id == 78 || id == 79 || id == 80 || id == 10071 || id == 10072 || id == 10073 || id == 10074 || id == 10075 || id == 10076 || id == 10077 || id == 10078 || id == 10079 || id == 10080) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 6000);
            MessageHelper.build("&8[&2+&8] &f6 000$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 90-100-3 12 90-100," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 7) {
                MobDropHelper.addDropPlayer(player, "I32", 1, 25, true, true, entity);
            }
        }
        if (id == 81 || id == 82 || id == 83 || id == 84 || id == 85 || id == 86 || id == 87 || id == 88 || id == 89 || id == 90) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 8000);
            MessageHelper.build("&8[&2+&8] &f8 000$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 100-115-3 12 100-110," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 8) {
                MobDropHelper.addDropPlayer(player, "I33", 1, 25, true, true, entity);
            }
        }
        if (id == 91 || id == 92 || id == 93 || id == 94 || id == 95 || id == 96 || id == 97 || id == 98 || id == 99 || id == 100) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 10000);
            MessageHelper.build("&8[&2+&8] &f10 000$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 110-120-3 12 110-130," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 9) {
                MobDropHelper.addDropPlayer(player, "I34", 1, 25, true, true, entity);
            }
        }
        if (id == 101 || id == 102 || id == 103 || id == 104 || id == 105 || id == 106 || id == 107 || id == 108 || id == 109 || id == 110) {
            user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 12500);
            MessageHelper.build("&8[&2+&8] &f12 500$").send(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mm mobs spawn 120-130-3 12 110-130," + (int) entity.getLocation().getX() + "," + (int) entity.getLocation().getY() + "," + (int) entity.getLocation().getZ());
            if (QuestsHelper.getQuestLevel("Kolekcjoner Metiny", questsUser) == 10) {
                MobDropHelper.addDropPlayer(player, "I35", 1, 25, true, true, entity);
            }
        }
        if (id == 1000 || id == 1001 || id == 1002 || id == 1003) {
            if (Main.getInstance().getDungeonPiekielnyManager().getFale() == 1) {
                user.getLevelUser().setMoney(user.getLevelUser().getMoney() + 250000);
                MessageHelper.build("&8[&2+&8] &f250 000$").send(player);
                Main.getInstance().getDungeonPiekielnyManager().setAmountMobs(Main.getInstance().getDungeonPiekielnyManager().getAmountMobs() + 1);
                for (Player players : Bukkit.getWorld("Dungeon80-90").getPlayers()) {
                    PacketUtil.updateActionBar(players, "&4Postep rozwalonych Metinow: &f" + Main.getInstance().getDungeonPiekielnyManager().getAmountMobs());
                }
                if (Main.getInstance().getDungeonPiekielnyManager().getAmountMobs() == 4) {
                    DungeonPiekielnyManager.startEtap2();
                }
            }
        }
    }*/
}
