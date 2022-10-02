package rpg.rpgcore.entities.PetArmorStand;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PetArmorStand extends EntityArmorStand {

    public UUID player;
    public PetArmorStand(net.minecraft.server.v1_8_R3.World world, Player player) {
        super(world);
        this.player = player.getUniqueId();
    }
    // This is the update method of the entity
    @Override
    public void t_() {

        // Getting the nms class of the player to have easier access to some
        // data.
        EntityPlayer p = ((CraftPlayer) Bukkit.getPlayer(player)).getHandle();
        // Setting the position
        double x = Math.cos(Math.toRadians(p.yaw - 180));
        double z = Math.sin(Math.toRadians(p.yaw - 180));
        setPosition(p.locX + x, p.locY + 0.8, p.locZ + z);
        this.world = p.getWorld();
        //Setting the yaw and pitch of the armor stand. If it fails we get another change to set it with the codes below.
        this.yaw = p.yaw;
        this.pitch = p.pitch;
        // Setting the yaw of the armor stand. I don't know for sure if this
        // works.
        setBodyPose(new Vector3f(0.0F, p.yaw, 0.0F));
        setSmall(true);
        setInvisible(true);
        // Setting the pitch of the head. I also don't know for sure if this
        // works.
        //setHeadPose(new Vector3f(p.pitch, 0.0F, 0.0F));

        // If you want to still do the normal entity update instead of just
        // standing totally still. Use this line.
        // super.m();
    }

    // This method has to be called on onEnable() in the main plugin class! This
    // has to be called to add the entity in the registry of types to prefent
    // crashing.
    /*public static void registerEntity() {
        try {
            Class<EntityTypes> entityTypeClass = EntityTypes.class;

            Field c = entityTypeClass.getDeclaredField("c");
            c.setAccessible(true);
            HashMap<String, Class<?>> c_map = (HashMap) c.get(null);
            c_map.put("customArmorStand", EntityArmorStandCustom.class);

            Field d = entityTypeClass.getDeclaredField("d");
            d.setAccessible(true);
            HashMap<Class<?>, String> d_map = (HashMap) d.get(null);
            d_map.put(EntityArmorStandCustom.class, "customArmorStand");

            Field e = entityTypeClass.getDeclaredField("e");
            e.setAccessible(true);
            HashMap<Integer, Class<?>> e_map = (HashMap) e.get(null);
            e_map.put(Integer.valueOf(63), EntityArmorStandCustom.class);

            Field f = entityTypeClass.getDeclaredField("f");
            f.setAccessible(true);
            HashMap<Class<?>, Integer> f_map = (HashMap) f.get(null);
            f_map.put(EntityArmorStandCustom.class, Integer.valueOf(63));

            Field g = entityTypeClass.getDeclaredField("g");
            g.setAccessible(true);
            HashMap<String, Integer> g_map = (HashMap) g.get(null);
            g_map.put("customArmorStand", Integer.valueOf(63));

        } catch (Exception exc) {
            Field d;
            int d_map;
            Method[] e;
            Class[] paramTypes = { Class.class, String.class, Integer.TYPE };
            try {
                Method method = EntityTypes.class.getDeclaredMethod(
                        "addMapping", paramTypes);
                method.setAccessible(true);
            } catch (Exception ex) {
                exc.addSuppressed(ex);
                try {
                    d_map = (e = EntityTypes.class.getDeclaredMethods()).length;
                    for (int d1 = 0; d1 < d_map; d1++) {
                        Method method = e[d1];
                        if (Arrays.equals(paramTypes,
                                method.getParameterTypes())) {
                            method.invoke(null, new Object[] {
                                    EntityArmorStandCustom.class,
                                    "customArmorStand", Integer.valueOf(63) });
                        }
                    }
                } catch (Exception exe) {
                    exc.addSuppressed(exe);
                }
                exc.printStackTrace();
            }
        }
    }*/

}
