package rpg.rpgcore.entities;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import rpg.rpgcore.entities.CustomZombie.CustomZombie;
import rpg.rpgcore.utils.Utils;

import java.util.Map;

public enum EntityTypes {
    //NAME("Entity name", Entity ID, yourcustomclass.class);
    CUSTOM_ZOMBIE("Zombie", 54, CustomZombie.class); //You can add as many as you want.

    EntityTypes(String name, int id, Class<? extends Entity> custom)
    {
        addToMaps(custom, name, id);
    }

    public static void spawnEntity(Entity entity, Location loc, String name) {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        entity.setCustomName(Utils.format(name));
        entity.setCustomNameVisible(true);
        System.out.println(entity.length);
        System.out.println(entity.width);
        System.out.println(entity.getBoundingBox().a);
        System.out.println(entity.getBoundingBox().b);
        System.out.println(entity.getBoundingBox().c);
        System.out.println(entity.getBoundingBox().d);
        System.out.println(entity.getBoundingBox().e);
        System.out.println(entity.getBoundingBox().f);
        entity.setSize(2, 1);
        System.out.println(" ");
        System.out.println(entity.getBoundingBox().a);
        System.out.println(entity.getBoundingBox().b);
        System.out.println(entity.getBoundingBox().c);
        System.out.println(entity.getBoundingBox().d);
        System.out.println(entity.getBoundingBox().e);
        System.out.println(entity.getBoundingBox().f);
        //entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    private static void addToMaps(Class clazz, String name, int id) {
        //getPrivateField is the method from above.
        //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
        ((Map<String, Class<? extends Entity>>) Utils.getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map)Utils.getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        //((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
        ((Map)Utils.getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
        //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }
}
