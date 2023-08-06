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
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.rybak.objects.RybakUser;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.RybakItems;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class RybakNPC {

    private final RPGCORE rpgcore;
    private final Map<UUID, RybakUser> usersMap;
    private final Map<Integer, ArmorStand> armorStandMap;
    private final Map<UUID, Integer> failedAttemptMap = new HashMap<>();
    private final Map<UUID, Integer> fishingCount = new HashMap<>();
    private final Map<UUID, Integer> taskMap = new HashMap<>();
    private final Map<UUID, Map<String, Float>> locationMap = new HashMap<>();
    private final List<UUID> passed = new ArrayList<>();

    public RybakNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.usersMap = rpgcore.getMongoManager().loadAllRybak();
        this.spawnArmorStans();
        this.armorStandMap = rpgcore.getMongoManager().loadAllRybakArmorStands();
    }

    private void spawnArmorStans() {
        final ArmorStand as  = (ArmorStand) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -98.5, 97, -155.5), EntityType.ARMOR_STAND);
        as.setGravity(false);
        for (ArmorStand stand : as.getNearbyEntities(12, 12, 12).stream().filter(entity -> entity instanceof ArmorStand && entity.getType().equals(EntityType.ARMOR_STAND)).map(entity -> (ArmorStand) entity).collect(Collectors.toList())) {
            stand.remove();
        }
        as.remove();
    }

    public void clickWloczykij(final Player player) {
        if (player.getItemInHand().equals(RybakItems.I4.getItemStack())) {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Witaj &b" + player.getName() + "&7!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &eRybacka Wyspa?!")), 40L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &ePewnie&7! To tuz za rogiem")), 80L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.teleport(new Location(Bukkit.getWorld("Rybak"), -10.5, 157, -133.5, -90, 0));
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &ePuff!"));
            }, 120L);
            return;
        }
        if (player.getInventory().containsAtLeast(RybakItems.I1.getItemStack(), 5) && player.getInventory().containsAtLeast(RybakItems.I2.getItemStack(), 3) && player.getInventory().containsAtLeast(RybakItems.I3.getItemStack(), 2)) {
            player.getInventory().removeItem(new ItemBuilder(RybakItems.I1.getItemStack().clone()).setAmount(5).toItemStack(),
                    new ItemBuilder(RybakItems.I2.getItemStack().clone()).setAmount(3).toItemStack(),
                    new ItemBuilder(RybakItems.I3.getItemStack().clone()).setAmount(2).toItemStack());
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Dziekuje za pomoc!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Teraz jestesmy gotowi!")), 40L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7W sumie...!")), 80L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Prosze, wez ja!")), 120L);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                player.getInventory().addItem(RybakItems.I4.getItemStack().clone());
                player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ja i tak jestem juz za stary na te zabawe!"));
            }, 160L);
            return;
        }
        player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Witaj, &b" + player.getName() + "&7!"));
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Slyszalem, ze szukasz przygod!")), 40L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Swietnie sie sklada!")), 80L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Moge zabrac Cie, na taka jedna &ewyspe &7pelna rybackich zadan i tajemnic!")), 120L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Poznasz tam kilku moich &aprzyjaciol&7!")), 160L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ale...")), 200L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zeby sie tam dostac potrzebujemy mojej &6Starej Lodki.")), 240L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Niestety, po ostatniej podrozy uderzylem w &8Skale &7i lodka rozbila sie na kawalki")), 280L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Troche to dziwne...")), 320L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7W moim swiecie, Lodki zazwyczaj tona po takiej interakcji")), 360L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Ale no coz, najwidoczniej w tym Swiecie jest troche inaczej")), 400L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Do sedna...")), 440L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Zeby naprawic swoja lodke potrzebuje:")), 480L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e5 &6Zpruchnialych Desek"));
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e3 Podstawy Masztu"));
            player.sendMessage(Utils.format("&3&lWloczykij &8>> &8- &e2 &fPodartych Masztow"));
            }, 520L);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&3&lWloczykij &8>> &7Przynies mi te rzeczy i bedziemy gotowi do drogi")), 560L);
    }




    public void getAntyAfk(final Player player) {
        player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
        player.sendMessage(Utils.format("&7Kliknij w &bpodswietlona wedke&7, aby zakonczyc weryfikacje"));
        player.sendMessage(Utils.format("&7GUI zamknie sie &cautomatycznie po 10 sekundach&7, a na ciebie zostanie nalozona kara"));
        player.sendMessage(Utils.format("&7Ochrona &cAnty-AFK"));
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&6&lRybak &8>> &cAnty-AFK"));

        for (int i = 0; i < 18; i++) {
            gui.setItem(i, new ItemBuilder(Material.FISHING_ROD).setName("&c&lAnty-AFK").toItemStack());
        }

        gui.setItem(ChanceHelper.getRandInt(0, 17), new ItemBuilder(Material.FISHING_ROD).setName("&a&lAnty-AFK").setLore(Arrays.asList("&7Kliknij, zeby przejsc weryfikacje &cAnty-AFK")).addGlowing().toItemStack());

        player.openInventory(gui);
        int task = rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            if (player == null || !player.isOnline()) return;
            if (player.getOpenInventory().getTopInventory() == null) {
                return;
            }
            if (Utils.removeColor(player.getOpenInventory().getTopInventory().getTitle()).equals("Rybak » Anty-AFK")) {
                if (this.passed.contains(player.getUniqueId())) {
                    this.passed.remove(player.getUniqueId());
                    return;
                }
                player.closeInventory();
            }
        }, 200L).getTaskId();
        this.taskMap.put(player.getUniqueId(), task);

    }


    public int getFailedAttempts(final UUID uuid) {
        return failedAttemptMap.getOrDefault(uuid, 0);
    }

    public void addFailedAttempt(final UUID uuid) {
        if (failedAttemptMap.containsKey(uuid)) {
            failedAttemptMap.put(uuid, failedAttemptMap.get(uuid) + 1);
        } else {
            failedAttemptMap.put(uuid, 1);
        }
    }

    public void resetFailedAttempt(final UUID uuid) {
        if (failedAttemptMap.containsKey(uuid)) {
            failedAttemptMap.replace(uuid, 0);
        } else {
            failedAttemptMap.put(uuid, 0);
        }
    }

    public int getFishingCount(final UUID uuid) {
        return fishingCount.getOrDefault(uuid, 0);
    }

    public void addFishingCount(final UUID uuid) {
        if (fishingCount.containsKey(uuid)) {
            fishingCount.replace(uuid, fishingCount.get(uuid) + 1);
        } else {
            fishingCount.put(uuid, 1);
        }
    }

    public void resetFishingCount(final UUID uuid) {
        fishingCount.replace(uuid, 0);
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

    public List<UUID> getPassed() {
        return passed;
    }

    public void addLocation(final UUID uuid, final String type, final float loc) {
        if (!this.locationMap.containsKey(uuid)) this.locationMap.put(uuid, new HashMap<>());
        this.locationMap.get(uuid).put(type, loc);
    }

    public boolean isSameLocation(final UUID uuid, final String type, final float loc) {
        if (!this.locationMap.containsKey(uuid)) return false;
        if (!this.locationMap.get(uuid).containsKey(type)) return false;
        return this.locationMap.get(uuid).get(type) == loc;
    }

    public void add(final RybakUser rybakUser) {
        this.usersMap.put(rybakUser.getUuid(), rybakUser);
    }

    public RybakUser find(final UUID uuid) {
        return this.usersMap.get(uuid);
    }

    public ImmutableSet<RybakUser> getRybakObjects() {
        return ImmutableSet.copyOf(this.usersMap.values());
    }

    public boolean checkIfMapContainsArmorStand(final Location location) {
        return this.armorStandMap.values().stream().anyMatch(as -> as.getLocation().equals(location));
    }

    public int getKeyByValue(final ArmorStand as) {
        return this.armorStandMap.entrySet().stream().filter(entry -> Objects.equals(as, entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(-1);
    }
}
