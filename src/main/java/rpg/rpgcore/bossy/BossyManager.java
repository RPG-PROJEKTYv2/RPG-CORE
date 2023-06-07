package rpg.rpgcore.bossy;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.bossy.enums.Stage70_80;
import rpg.rpgcore.bossy.objects.BossyUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.*;

public class BossyManager {
    @Getter
    private BossyUser bossyUser;
    public BossyManager() {
        bossyUser = RPGCORE.getInstance().getMongoManager().loadAllBossy();
    }

    public void set(final BossyUser user) {
        this.bossyUser = user;
    }

    // 60-70
    @Getter
    private int boss60_70count = 0;

    public void incrementBoss60_70count() {
        boss60_70count++;
    }

    public void decrementBoss60_70count() {
        if (boss60_70count <= 0) return;
        boss60_70count--;
    }

    // 70-80
    @Getter
    @Setter
    private Stage70_80 stage70_80 = Stage70_80.NOT_SPAWNED;

    @Getter
    private final Map<ArmorStand, UUID> location70_80Map = new HashMap<>();
    private final List<Location> placeLocations70_80 = Arrays.asList(
            new Location(Bukkit.getWorld("70-80map"), -1, 82, 296),
            new Location(Bukkit.getWorld("70-80map"), -49, 82, 248),
            new Location(Bukkit.getWorld("70-80map"), -1, 82, 200),
            new Location(Bukkit.getWorld("70-80map"), 47, 82, 248)
    );
    private final Map<ArmorStand, Integer> taskMap70_80 = new HashMap<>();
    private final Map<Location, ArmorStand> armorStands70_80Map = new HashMap<>();
    private final ItemStack skullForArmorStand = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwnerByURL(
            "d0518a50-4af8-4d18-8ff8-b4d2924e5b81", "skina030e545",
            "ewogICJ0aW1lc3RhbXAiIDogMTY4Mjg3OTgwODUzNywKICAicHJvZmlsZUlkIiA6ICJhODUxNzQ0MDNlNjg0MD" +
                    "gwYWNkODU3MzhlMjI5NGNhZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJEYVJpdmVyc09uZSIsCiAgInNpZ25hdHVy" +
                    "ZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOi" +
                    "AiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xODUxNGQ4MjMwYjc1NTExYTVhNWE2OWNh" +
                    "OTNkY2IyZDNlN2NkMWEyOGM0OGRjMzgwODdmMTU4ZDI4M2I3ZmE3IgogICAgfQogIH0KfQ==",
            "axvPTexJSrc15xLKlQ7UEPdN/g0V7zOKvwTuBPGQaKhwy0O9m+RRt1d8A83iBXqFsDtADjSjSf+IUH+Km9vq" +
                    "0ywub5UnszfftoiG8msde81PeI+ia7b32awhXSZKVKW4NNaqfV/6vzlkVsZeIip0X0fX9v04IsHowzoU/qkAP" +
                    "XDuH3M9LS6H762biX9RN5qAfTI+mVMU0fBekn9n/G5ilCfXgS08maMY3bNrxyDBDCJlpU/E84m3OhKXjh1H5J" +
                    "iU+UriiNW17QoBga0uxh4F/2yEWLMB+Udgk4tHx4CnfEBtUWo9wd7gYP4s4Jwe0BqjETEixg+gmVW/1f6PYPi" +
                    "o0ZSfKlGx2M7JPVExea6FyNtFQOdNiJD2UhQ3Y3orDUH1oAFPtooWuPrkK9BL1tSp6WWMc7J6Tm/FzRPOPgKl" +
                    "qa2lKXho5n/6m5DvNEws2zoONAotUP3KAt6JE00SLCP/n+hqrs03uajYcYwmN31rR4nlhsKwlL0hu5MjoJVPI" +
                    "RFBOB7RbFerZcDVlQPTwC9LSoa8qAK9GROVoOiymv5F1HtnvNoexmM/aVybu+QXq6thM0JtM767Aadfeec4r+" +
                    "ZRL/fq7Q4Ey4na2dJbFdPskd3Cn99mtP969PbM6qCnYz0S+XcvnLi7cfiXqFyzeQiWPdMyexAEyo31ncLuRy7Y/x8="
    ).toItemStack().clone();


    public boolean isLocationPlace70_80(final Location location) {
        return placeLocations70_80.contains(location);
    }
    public boolean isLocationPlaceUsed70_80(final Location location) {
        return armorStands70_80Map.containsKey(location);
    }

    public void addLocation70_80(final UUID uuid, final ArmorStand as) {
        location70_80Map.put(as, uuid);
    }

    public void removeLocation70_80(final ArmorStand as) {
        location70_80Map.remove(as);
    }

    public boolean isArmorStand70_80(final int id) {
        for (Map.Entry<ArmorStand, Integer> entry : taskMap70_80.entrySet()) {
            if (entry.getKey().getEntityId() == id) {
                return true;
            }
        }
        return false;
    }

    public Location getLocationByArmorStandId(final int id) {
        for (Map.Entry<Location, ArmorStand> entry : armorStands70_80Map.entrySet()) {
            if (entry.getValue().getEntityId() == id) return entry.getKey();
        }
        return null;
    }

    public boolean canBeSpawned70_80() {
        return stage70_80 == Stage70_80.NOT_SPAWNED || stage70_80 == Stage70_80.STAGE1 || stage70_80 == Stage70_80.STAGE2 || stage70_80 == Stage70_80.STAGE3;
    }

    public void incrementStage70_80() {
        Stage70_80 stage = getStage70_80();
        switch (stage) {
            case NOT_SPAWNED:
                stage = Stage70_80.STAGE1;
                break;
            case STAGE1:
                stage = Stage70_80.STAGE2;
                break;
            case STAGE2:
                stage = Stage70_80.STAGE3;
                break;
            case STAGE3:
                stage = Stage70_80.SPAWNING;
                RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), this::spawnBoss70_80, 20 * 5);
                break;
            case SPAWNING:
                stage = Stage70_80.SPAWNED;
                break;
        }
        setStage70_80(stage);
        if (stage == Stage70_80.SPAWNED) return;
        Bukkit.broadcastMessage(Utils.format(stage.getMessage()));
    }

    public void decrementStage70_80() {
        Stage70_80 stage = getStage70_80();
        switch (stage) {
            case STAGE1:
                stage = Stage70_80.NOT_SPAWNED;
                break;
            case STAGE2:
                stage = Stage70_80.STAGE1;
                break;
            case STAGE3:
                stage = Stage70_80.STAGE2;
                break;
            case SPAWNING:
                stage = Stage70_80.STAGE3;
                break;
            case SPAWNED:
                stage = Stage70_80.SPAWNING;
                break;
        }
        setStage70_80(stage);
    }

    private Location getSpawnLocation(final Location blockLocation) {
        if (blockLocation.getX() == -1 && blockLocation.getY() == 82 && blockLocation.getZ() == 296) return new Location(Bukkit.getWorld("70-80map"), -0.5, 83, 296.5);
        if (blockLocation.getX() == -49 && blockLocation.getY() == 82 && blockLocation.getZ() == 248) return new Location(Bukkit.getWorld("70-80map"), -48.5, 83, 248.5);
        if (blockLocation.getX() == -1 && blockLocation.getY() == 82 && blockLocation.getZ() == 200) return new Location(Bukkit.getWorld("70-80map"), -0.5, 83, 200.5);
        if (blockLocation.getX() == 47 && blockLocation.getY() == 82 && blockLocation.getZ() == 248) return new Location(Bukkit.getWorld("70-80map"), 47.5, 83, 248.5);
        return blockLocation;
    }

    public ArmorStand spawnArmorStandOnPlace70_80(final Location location) {
        final Location toSpawn = getSpawnLocation(location);
        System.out.println(toSpawn.serialize().toString());
        final ArmorStand as = location.getWorld().spawn(toSpawn, ArmorStand.class);
        final ItemStack item = Bossy.I2.getItemStack().clone();
        as.setGravity(false);
        as.setVisible(false);
        as.setBasePlate(false);
        as.setArms(false);
        as.setHelmet(skullForArmorStand);
        as.setCustomName(item.getItemMeta().getDisplayName());
        as.setCustomNameVisible(true);
        armorStands70_80Map.put(location, as);
        final int i = RPGCORE.getInstance().getServer().getScheduler().scheduleAsyncRepeatingTask(RPGCORE.getInstance(), () -> {
            final Location loc = as.getLocation().clone();
            loc.setYaw(loc.getYaw() + 10);
            double delta = Math.sin(Math.toRadians(System.currentTimeMillis() / 100.0)*8) / 20;
            loc.add(0, delta, 0);
            as.teleport(loc);
        }, 0L, 1L);
        taskMap70_80.put(as, i);
        return as;
    }

    public void spawnBoss70_80() {
        for (Integer i : taskMap70_80.values()) {
            Bukkit.getScheduler().cancelTask(i);
        }
        for (ArmorStand as : armorStands70_80Map.values()) {
            as.remove();
        }

        final StringBuilder sb = new StringBuilder();
        for (UUID uuid : location70_80Map.values()) {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            if (sb.toString().contains(player.getName())) continue;
            sb.append("&e").append(player.getName()).append(", ");
        }
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(Utils.format(Stage70_80.SPAWNED.getMessage() + sb.substring(0, sb.toString().length() - 2)));
        Bukkit.broadcastMessage(" ");

        location70_80Map.clear();
        armorStands70_80Map.clear();
        taskMap70_80.clear();
        incrementStage70_80();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 70-80-BOSS 1 70-80map,0.5,84,248.5");
    }

    public void returnSingle70_80(final Player player, final ArmorStand as) {
        final UUID uuid = player.getUniqueId();

        if (Bukkit.getPlayer(uuid) == null) return;
        Bukkit.getPlayer(uuid).getInventory().addItem(Bossy.I2.getItemStack().clone());
        removeLocation70_80(as);
        as.remove();
        armorStands70_80Map.remove(getLocationByArmorStandId(as.getEntityId()));
        Bukkit.getScheduler().cancelTask(taskMap70_80.get(as));
        taskMap70_80.remove(as);
        decrementStage70_80();
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &aPomyslnie wyjales/-as &cPrzeklete Serce&a!"));
    }

    public void reset70_80() {
        for (Integer i : taskMap70_80.values()) {
            Bukkit.getScheduler().cancelTask(i);
        }
        for (ArmorStand as : armorStands70_80Map.values()) {
            as.remove();
        }
        location70_80Map.clear();
        armorStands70_80Map.clear();
        taskMap70_80.clear();
        setStage70_80(Stage70_80.NOT_SPAWNED);
    }


    // 100-110
    public void spawn100_110Boss() {
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &b&lMityczny Kraken &3przyplywa z glebin i czeka na swoja ofiare!"));
        Bukkit.broadcastMessage(" ");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 100-110-BOSS 1 100-110map,73.5,79,199.5");
        this.bossyUser.resetMobsCount100_110();
        this.bossyUser.save();
    }

    // 120-130
    private final World w = Bukkit.getWorld("120-130map");
    private final List<Location> klejnot120_130_1Locations = Arrays.asList(
            new Location(w, 61, 83, 21),
            new Location(w, 61, 83, 22),
            new Location(w, 61, 83, 23),

            new Location(w, 61, 84, 20),
            new Location(w, 61, 84, 21),
            new Location(w, 61, 84, 22),
            new Location(w, 61, 84, 23),
            new Location(w, 61, 84, 24),

            new Location(w, 61, 85, 19),
            new Location(w, 61, 85, 20),
            new Location(w, 61, 85, 21),
            new Location(w, 61, 85, 22),
            new Location(w, 61, 85, 23),
            new Location(w, 61, 85, 24),
            new Location(w, 61, 85, 25),

            new Location(w, 61, 86, 20),
            new Location(w, 61, 86, 21),
            new Location(w, 61, 86, 22),
            new Location(w, 61, 86, 23),
            new Location(w, 61, 86, 24),

            new Location(w, 61, 87, 21),
            new Location(w, 61, 87, 22),
            new Location(w, 61, 87, 23)
    );
    private final List<Location> klejnot120_130_2Locations = Arrays.asList(
            new Location(w, -63, 83, 21),
            new Location(w, -63, 83, 20),
            new Location(w, -63, 83, 19),

            new Location(w, -63, 84, 22),
            new Location(w, -63, 84, 21),
            new Location(w, -63, 84, 20),
            new Location(w, -63, 84, 19),
            new Location(w, -63, 84, 18),

            new Location(w, -63, 85, 23),
            new Location(w, -63, 85, 22),
            new Location(w, -63, 85, 21),
            new Location(w, -63, 85, 20),
            new Location(w, -63, 85, 19),
            new Location(w, -63, 85, 18),
            new Location(w, -63, 85, 17),

            new Location(w, -63, 86, 22),
            new Location(w, -63, 86, 21),
            new Location(w, -63, 86, 20),
            new Location(w, -63, 86, 19),
            new Location(w, -63, 86, 18),

            new Location(w, -63, 87, 21),
            new Location(w, -63, 87, 20),
            new Location(w, -63, 87, 19)
    );

    public boolean isKlejnot120_130_1(Location location) {
        return klejnot120_130_1Locations.contains(location);
    }

    public boolean isKlejnot120_130_2(Location location) {
        return klejnot120_130_2Locations.contains(location);
    }



    private final List<Location> klejnot120_130_1BlocksE = Arrays.asList(
            new Location(w, 62, 83, 22),

            new Location(w, 62, 84, 22),

            new Location(w, 62, 85, 20),
            new Location(w, 62, 85, 21),
            new Location(w, 62, 85, 23),
            new Location(w, 62, 85, 24),

            new Location(w, 62, 86, 22),

            new Location(w, 62, 87, 22)
    );
    private final List<Location> klejnot120_130_1BlocksG = Arrays.asList(
            new Location(w, 62, 84, 21),
            new Location(w, 62, 84, 23),

            new Location(w, 62, 85, 22),

            new Location(w, 62, 86, 21),
            new Location(w, 62, 86, 23)
    );

    private final List<Location> klejnot120_130_2BlocksE = Arrays.asList(
            new Location(w, -64, 83, 20),

            new Location(w, -64, 84, 20),

            new Location(w, -64, 85, 18),
            new Location(w, -64, 85, 19),
            new Location(w, -64, 85, 21),
            new Location(w, -64, 85, 22),

            new Location(w, -64, 86, 20),

            new Location(w, -64, 87, 20)
    );
    private final List<Location> klejnot120_130_2BlocksG = Arrays.asList(
            new Location(w, -64, 84, 19),
            new Location(w, -64, 84, 21),

            new Location(w, -64, 85, 20),

            new Location(w, -64, 86, 19),
            new Location(w, -64, 86, 21)
    );
    private final List<Location> bossGateLocations120_130 = Arrays.asList(
            new Location(w, -2, 86, -15),
            new Location(w, -1, 86, -15),
            new Location(w, 0, 86, -15),
            new Location(w, 1, 86, -15),
            new Location(w, 2, 86, -15),

            new Location(w, -1, 87, -15),
            new Location(w, 0, 87, -15),
            new Location(w, 1, 87, -15),

            new Location(w, -1, 88, -15),
            new Location(w, 0, 88, -15),
            new Location(w, 1, 88, -15),

            new Location(w, -2, 89, -15),
            new Location(w, -1, 89, -15),
            new Location(w, 0, 89, -15),
            new Location(w, 1, 89, -15),
            new Location(w, 2, 89, -15)

    );
    @Getter
    private int klejnot120_130Count = 0;
    @Getter
    private final boolean[] klejnoty120_130 = new boolean[]{false, false};
    @Getter
    @Setter
    private  boolean gate120_130open = false;

    private void incrementKlejnot120_130Count() {
        klejnot120_130Count++;
    }

    private void decrementKlejnot120_130Count() {
        klejnot120_130Count--;
    }


    private void setKlejnot120_130_Active(final int index, final boolean value) {
        klejnoty120_130[index] = value;
    }

    public boolean isKlejnoty120_130Spawned() {
        return klejnoty120_130[0] && klejnoty120_130[1];
    }

    public void spawnKlejnot(final Player player, final int type) {
        switch (type) {
            case 1:
                for (final Location loc : klejnot120_130_1BlocksE) {
                    loc.getBlock().setType(Material.EMERALD_BLOCK);
                }
                for (final Location loc : klejnot120_130_1BlocksG) {
                    loc.getBlock().setType(Material.GLOWSTONE);
                }
                this.setKlejnot120_130_Active(0, true);
                break;
            case 2:
                for (final Location loc : klejnot120_130_2BlocksE) {
                    loc.getBlock().setType(Material.EMERALD_BLOCK);
                }
                for (final Location loc : klejnot120_130_2BlocksG) {
                    loc.getBlock().setType(Material.GLOWSTONE);
                }
                this.setKlejnot120_130_Active(1, true);
                break;
            default:
                return;
        }

        this.incrementKlejnot120_130Count();
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &aPomyslnie umiesciles/-as &a&lSmoczy Klejnot&a! &e(" + this.getKlejnot120_130Count() + "/2)"));
        if (this.getKlejnot120_130Count() == 1) {
            Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &a&lSmoczy Klejnot &5zostal osadzony! &5&lSmoczy Cesarz &5budzi sie ze snu! &6(" + this.getKlejnot120_130Count() + "/2)"));
        } else if (this.getKlejnot120_130Count() == 2) {
            Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &a&lSmoczy Klejnot &5zostal osadzony! &5&lSmoczy Cesarz &5wzbil sie w powietrze! &6(" + this.getKlejnot120_130Count() + "/2)"));
        }
    }

    public void despawnKlejnot(final Player player, final int type) {
        switch (type) {
            case 1:
                for (final Location loc : klejnot120_130_1BlocksE) {
                    loc.getBlock().setType(Material.AIR);
                }
                for (final Location loc : klejnot120_130_1BlocksG) {
                    loc.getBlock().setType(Material.AIR);
                }
                this.setKlejnot120_130_Active(0, false);
                break;
            case 2:
                for (final Location loc : klejnot120_130_2BlocksE) {
                    loc.getBlock().setType(Material.AIR);
                }
                for (final Location loc : klejnot120_130_2BlocksG) {
                    loc.getBlock().setType(Material.AIR);
                }
                this.setKlejnot120_130_Active(1, false);
                break;
            default:
                return;
        }
        this.decrementKlejnot120_130Count();
        if (player == null) return;
        player.sendMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &cPomyslnie wyjales/-elas &a&lSmoczy Klejnot&c! &e(" + this.getKlejnot120_130Count() + "/2)"));
    }

    public void open120_130BossGate(final Player player) {
        this.despawnKlejnot(null, 1);
        this.despawnKlejnot(null, 2);
        Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5Gracz &d" + player.getName() + " &5otworzyl brame do Smoczego Cesarza!"));
        for (final Location loc : bossGateLocations120_130) {
            loc.getBlock().setType(Material.AIR);
        }
        this.setGate120_130open(true);
        RPGCORE.getInstance().getServer().getScheduler().runTaskLater(RPGCORE.getInstance(), () -> {
            for (final Location loc : bossGateLocations120_130) {
                loc.getBlock().setType(Material.IRON_FENCE);
            }
            this.setGate120_130open(false);
            Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &5Brama do Smoczego Cesarza zostala zamknieta!"));
        }, 20 * 7);
    }
}
