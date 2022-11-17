package rpg.rpgcore.entities;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.entities.CustomZombie.CustomZombie;
import rpg.rpgcore.entities.KsiazeMroku.KsiazeMroku;
import rpg.rpgcore.entities.PetArmorStand.PetArmorStand;
import rpg.rpgcore.entities.ZamekNieskonczonosciBoss.ZamekNieskonczonosciBoss;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum EntityTypes {
    //NAME("Entity name", Entity ID, yourcustomclass.class);
    CUSTOM_ZOMBIE("Zombie", 54, CustomZombie.class), //You can add as many as you want.
    PET_ARMOR_STAND("ArmorStand", 30, PetArmorStand.class),
    ZAMEK_NIESKONCZONOSCI_BOSS("WitherBoss", 64, ZamekNieskonczonosciBoss.class),
    KSIAZE_MROKU("PigZombie", 57, KsiazeMroku.class);


    EntityTypes(String name, int id, Class<? extends Entity> custom) {
        addToMaps(custom, name, id);
    }

    private static Map<UUID, Entity> petMap = new HashMap<>();

    public static Entity spawnEntity(Entity entity, UUID uuid, Location loc, String name) {
        if (entity instanceof EntityArmorStand) {
            addEntity(uuid, entity);
        }
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        entity.setCustomName(Utils.format(name));
        entity.setCustomNameVisible(true);
        //entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    private static void addToMaps(Class clazz, String name, int id) {
        //getPrivateField is the method from above.
        //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
        ((Map<String, Class<? extends Entity>>) Utils.getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map) Utils.getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        //((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        ((Map) Utils.getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }

    public static Entity getEntity(UUID uuid) {
        return petMap.get(uuid);
    }

    public static void addEntity(UUID uuid, Entity entity) {
        petMap.put(uuid, entity);
    }

    public static boolean isPetSpawned(final UUID uuid) {
        return petMap.containsKey(uuid);
    }

    public static void despawnPet(final UUID uuid) {
        if (petMap.containsKey(uuid)) {
            petMap.get(uuid).die();
            petMap.remove(uuid);
        }
    }

    public static void despawnAllEntities() {
        for (Entity entity : petMap.values()) {
            entity.die();
        }
        petMap.clear();
    }

    public static void updateName(final UUID uuid, final String name) {
        if (petMap.containsKey(uuid)) {
            petMap.get(uuid).setCustomName(Utils.format(name));
        }
    }

    /*public static void updatePet(final Player player, final ItemStack item) {
        despawnPet(player.getUniqueId());
        spawnEntity(new PetArmorStand(((CraftWorld) player.getLocation().getWorld()).getHandle(), player), player.getUniqueId(), player.getLocation(), item.clone().getItemMeta().getDisplayName()); //.substring(0, item.clone().getItemMeta().getDisplayName().indexOf(" "))
        addEquipment(EntityTypes.getEntity(player.getUniqueId()), item.clone());
    }*/

    public static void addEquipment(final Entity entity, final ItemStack item) {
        final PacketPlayOutEntityEquipment itemPacketItemArmorStand1 = new PacketPlayOutEntityEquipment(entity.getId(), 1, CraftItemStack.asNMSCopy(item));
        final PacketPlayOutEntityEquipment itemPacketItemArmorStand2 = new PacketPlayOutEntityEquipment(entity.getId(), 2, CraftItemStack.asNMSCopy(item));
        final PacketPlayOutEntityEquipment itemPacketItemArmorStand3 = new PacketPlayOutEntityEquipment(entity.getId(), 3, CraftItemStack.asNMSCopy(item));
        final PacketPlayOutEntityEquipment itemPacketItemArmorStand4 = new PacketPlayOutEntityEquipment(entity.getId(), 4, CraftItemStack.asNMSCopy(item));
        for (Player player : entity.getWorld().getWorld().getPlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketItemArmorStand1);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketItemArmorStand2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketItemArmorStand3);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketItemArmorStand4);
        }
    }

    public static void reloadAllPetsOnWorld(final World world) {
        for (Player player : world.getPlayers()) {
            final UUID uuid = player.getUniqueId();
            if (isPetSpawned(uuid)) {
                addEquipment(getEntity(uuid), RPGCORE.getInstance().getPetyManager().findActivePet(uuid).getPet().getItem().clone());
            }
        }
    }
}
