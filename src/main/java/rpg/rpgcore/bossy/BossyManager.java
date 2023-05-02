package rpg.rpgcore.bossy;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
        final int i = RPGCORE.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(RPGCORE.getInstance(), () -> {
            final Location loc = as.getLocation().clone();
            loc.setYaw(loc.getYaw() + 10);
            double delta = Math.sin(Math.toRadians(System.currentTimeMillis() / 100.0)*8) / 20;
            loc.add(0, delta, 0);
            as.teleport(loc);
        }, 0L, 2L);
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
        Bukkit.broadcastMessage(Utils.format(Stage70_80.SPAWNED.getMessage() + sb.substring(0, sb.toString().length() - 2)));

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
        Bukkit.broadcastMessage(Utils.format("&8&l(&4&lBOSS&8&l) &8>> &b&lMityczny Kraken &3przyplywa z glebin i czeka na swoja ofiare!"));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn 100-110-BOSS 1 100-110map,73.5,79,199.5");
        this.bossyUser.resetMobsCount100_110();
        this.bossyUser.save();
    }
}
