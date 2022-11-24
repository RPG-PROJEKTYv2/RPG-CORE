package rpg.rpgcore.npc.rybak;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.*;

@Getter
@Setter
public class RybakNPC {

    private final RPGCORE rpgcore;
    private final Map<UUID, RybakObject> usersMap;
    private final Map<UUID, Integer> taskMap = new HashMap<>();

    public RybakNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.usersMap = rpgcore.getMongoManager().loadAllRybak();
    }



    public int getTaskId(final UUID uuid) {
        return taskMap.get(uuid);
    }

    public void addTaskId(final UUID uuid, final int taskId) {
        taskMap.put(uuid, taskId);
    }

    public void removeTaskId(final UUID uuid) {
        taskMap.remove(uuid);
    }

    private List<String> setLoreSell(final double cena) {
        List<String> lore2 = new ArrayList<>();

        lore2.add(" ");
        lore2.add("&2Cena: &6" + Utils.spaceNumber(String.format("%.0f", cena)) + " &2$");
        lore2.add(" ");
        lore2.add("&6LMB &7- Sprzedaj &61 &7sztuke");
        lore2.add("&6RMB &7- Sprzedaj &6wszystkie &7posiadane w ekwipunku");

        return lore2;
    }

    public void runFishAnimation(final Player player, final Entity entity) {
        double pushX = player.getLocation().getDirection().normalize().getX() * -2;
        double pushY = player.getLocation().getDirection().normalize().getY() * -2;
        double pushZ = player.getLocation().getDirection().normalize().getZ() * -2;

        Vector push = new Vector(pushX, pushY, pushZ);

        entity.setVelocity(push);
    }

    public void spawnNurekGlebinowy(final Player player, final Location location) {
        final LivingEntity entity = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.ZOMBIE);
        final double maxHealth = 150000;
        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
        entity.setCustomName(Utils.format("&3Nurek Glebinowy"));
        player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + entity.getCustomName()));
        entity.setCustomName(Utils.format("&3Nurek Glebinowy &c" + (int) entity.getHealth() + "&7/&c" + (int) entity.getMaxHealth() + " ❤"));
        runFishAnimation(player, entity);

        EntityEquipment entityInv = entity.getEquipment();
        final ItemBuilder helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
        final ItemBuilder klata = new ItemBuilder(Material.LEATHER_CHESTPLATE);
        final ItemBuilder spodnie = new ItemBuilder(Material.LEATHER_LEGGINGS);
        final ItemBuilder buty = new ItemBuilder(Material.LEATHER_BOOTS);
        final ItemBuilder miecz = new ItemBuilder(Material.GOLD_SWORD);

        helm.setSkullOwnerByURL("470342fb-1116-3032-9b24-6f674e1e52b0", "Glebinowy_nurek",
                "eyJ0aW1lc3RhbXAiOjE0OTE2NjU5Nzc1NDAsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNj" +
                        "YTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dH" +
                        "VyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzg1NjkyNDUzNzFlZGZmNjNhMjY5MTNhOTcyZjJj" +
                        "NDRmYmU0MWY3NWU0MjY2OGE3MjU5OTc1OWNmNDUyZjNhIn19fQ==",
                "SoovlSRBotT4WJa/7ta9ecVEyV8iEel7Ln3qtbRESHpiWwNwqmiPpV8vtjtL9YB3c2D+z/0Xao2BVaBJICMBB5UeS7MgxV6Pp1dqZ0uuxrsS2H4rvceQzX" +
                        "s7lphLvxIveVu4z7VBZL/sEj2pAcIDCqvb5T2F9Fi2PMROBcDNZI/D5f088MbCZ1pgyi5DZWRhAGLwFwAPu6j7iyo+rq9LKWsOP7QPXmzmtuj545duhc2yEMZ" +
                        "RLYyJY6nVM/PrwtqIoUB6r6tm0ETLmL/H8idoauqwNwZfOFsFVgxKeZWHHr6xCz0vTNk/vs43k5ZF8szzdCmHeKGffe9YfO6ftXwMuR/KLVv1YaYsNkSD3VcFuGa" +
                        "IJl17VmvxLlo01KfZqYfZoKEK4YLR2sqGSLNwcf46UWlXtawXf/AscPy6V38+qJYTnQHDxa7wVbzUQaANYxz42XwPxPDO2fTWlPkw3Y1WL4mRZ3I10QwiXgPh4CRpL" +
                        "1UsVvNcljZncanI0W8So3b9S9fsEWce7vipQvMZTCjH9p7lC1B8orRxNfwx9lZ+94bpOkHcD+JI1l+TGS4Z2gRSF/+CBLGeU71XROGYX1Ocvc/gfpMofUVAWQxMjGkW5" +
                        "wXJmSJiyLpD7TOd098ll8nsi7vRrCGPoYBFrSA6vJyDIbaUeQiDmlA8euKpOtuYeAw=");
        player.getInventory().addItem(helm.toItemStack());
        klata.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        spodnie.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        buty.setLeatherArmorColorHEX(19, 46, 110).addGlowing();
        miecz.addGlowing();

        entityInv.setHelmet(helm.toItemStack());
        entityInv.setChestplate(klata.toItemStack());
        entityInv.setLeggings(spodnie.toItemStack());
        entityInv.setBoots(buty.toItemStack());
        entityInv.setItemInHand(miecz.toItemStack());

        entityInv.setHelmetDropChance(0f);
        entityInv.setChestplateDropChance(0f);
        entityInv.setLeggingsDropChance(0f);
        entityInv.setBootsDropChance(0f);
        entityInv.setItemInHandDropChance(0f);


    }

    public void setNoAi(final Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound tag = new NBTTagCompound();

        nmsEntity.c(tag);

        tag.setBoolean("NoAI", true);

        EntityLiving el = (EntityLiving) nmsEntity;
        el.a(tag);
    }

    public void spawnPodwodnyWladca(final Player player, final Location location) {
        final LivingEntity entity = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.GUARDIAN);
        final LivingEntity entityPassenger = (LivingEntity) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.ZOMBIE);
        setNoAi(entityPassenger);
        entity.setPassenger(entityPassenger);
        entityPassenger.setCustomNameVisible(true);
        entityPassenger.setCustomName(Utils.format("&6&lPodwodny Wladca"));
        runFishAnimation(player, entity);
        player.sendMessage(Utils.format(Utils.RYBAK + "&aPomyslnie wylowiles &6" + entityPassenger.getCustomName()));
        final double maxHealth = 500000;
        entity.setMaxHealth(maxHealth);
        entity.setHealth(maxHealth);
        entityPassenger.setMaxHealth(maxHealth);
        entityPassenger.setHealth(maxHealth);
        entityPassenger.setCustomName(Utils.format("&6&lPodwodny Wladca &c" + (int) entityPassenger.getHealth() + "&7/&c" + (int) entityPassenger.getMaxHealth() + " ❤"));

        final ItemBuilder helm = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3);
        final ItemBuilder klata = new ItemBuilder(Material.LEATHER_CHESTPLATE);
        final ItemBuilder spodnie = new ItemBuilder(Material.LEATHER_LEGGINGS);
        final ItemBuilder buty = new ItemBuilder(Material.DIAMOND_CHESTPLATE);
        final ItemBuilder topor = new ItemBuilder(Material.GOLD_AXE);

        helm.setSkullOwnerByURL("6e315266-5e72-3f8e-8119-3c76b40759f3", "skina5d6712e",
                "eyJ0aW1lc3RhbXAiOjE0OTE5NTE5MjE5NTMsInByb2ZpbGVJZCI6IjQzYTgzNzNkNjQyOTQ1M" +
                        "TBhOWFhYjMwZjViM2NlYmIzIiwicHJvZmlsZU5hbWUiOiJTa3VsbENsaWVudFNraW42Iiwic2" +
                        "lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDo" +
                        "vL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xNjkwOTkzNmI1ZmExOTI0ZjkxZDFk" +
                        "ZTNlODcwZWQ3MTQwYmY0MzVjMWExYTU1ZGE1NzNmYzNhODhlYmQ1YmVjIn19fQ==",
                "mjdFL9WeVt9RTTFRpk5MD0tkU0yboelX5VEZV2gAYPEnQLGmeVWP5q9tUedIC1y" +
                        "XFd12qY8R843quCC27bHODR1Fb+nOUElaw/xd3uQ1DEiROvAbMI+Ua5El4vlAUmDWO" +
                        "h+kMD386t6VsmgMI4CdOOLjKc6uoFYzKS7xTzPkCrSdnU3C//Erv4QcKCwqzOXcTAsF" +
                        "vWqUXWfc23jJmxlHI2jYqhpN3vWllhdt5A16g3y6F+x2rXDSCqZTyaxITRYeEsKNix2" +
                        "yVCtSa92vJdTCfgUOtqN2UDft5/eEWGsNUvi81AuojWha3ht8DhksJCcZFLTahg30B/" +
                        "1YKVyYZTsgpFF1tQxSpd/RQnGKAEwWAEgxAdQvFu0CmJZdK3y4FMX6TB7lcp+hSXF/G" +
                        "KDPVfprVSeS2/DvGSI4AJQvIQADbeF0kiqBbVG+dy3c7n7OvvDqXjUs4vKoGcLAirrS" +
                        "dpSKvxtGGLVr33MDciTwuTyrCI5+Z+wFHydW3WXuzCi+v3Hu79SkkGMazzzCmlXf9J5" +
                        "JL+3LAIy4uVcSfYjl72zpyLEbv6i4oYJGRihqY6X9v4LvXnKhsWnG0w7Uk4TMnRl8EM" +
                        "+e5LpXK41kj0OpOT2f2pe2PnOPQyLJzBoA3Q+UDSaVneAm2R2DjHkg+ou3uL9raUkk1" +
                        "2qEHGHDk3N+WXyA/4o=");

        klata.setLeatherArmorColorHEX(92, 25, 23);
        spodnie.setLeatherArmorColorHEX(10, 10, 10);
        buty.addGlowing();
        topor.addGlowing().addEnchant(Enchantment.KNOCKBACK, 4);


        EntityEquipment eq = entityPassenger.getEquipment();

        eq.setHelmet(helm.toItemStack());
        eq.setChestplate(klata.toItemStack());
        eq.setLeggings(spodnie.toItemStack());
        eq.setBoots(buty.toItemStack());
        eq.setItemInHand(topor.toItemStack());

        eq.setHelmetDropChance(0f);
        eq.setChestplateDropChance(0f);
        eq.setLeggingsDropChance(0f);
        eq.setBootsDropChance(0f);
        eq.setItemInHandDropChance(0f);


    }

    public void add(final RybakObject rybakObject) {
        this.usersMap.put(rybakObject.getId(), rybakObject);
    }

    public RybakObject find(final UUID uuid) {
        usersMap.computeIfAbsent(uuid, k -> new RybakObject(uuid));
        return this.usersMap.get(uuid);
    }

    public ImmutableSet<RybakObject> getRybakObjects() {
        return ImmutableSet.copyOf(this.usersMap.values());
    }
}
