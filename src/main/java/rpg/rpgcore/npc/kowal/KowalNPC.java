package rpg.rpgcore.npc.kowal;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.*;

import java.util.*;

public class KowalNPC {

    private final RPGCORE rpgcore;

    public KowalNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    // WAZNE MAPY I LISTY
    private final List<UUID> upgradeList = new ArrayList<>();
    private final Map<UUID, List<ItemStack>> ostatniUlepszonyItem = new HashMap<>();
    private final RandomItems<String> ulepszanie = new RandomItems<>();
    private final List<UUID> animationList = new ArrayList<>();

    // ITEMY DO GUI
    private final ItemBuilder anvil = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder ulepszItem = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder placeForItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder placeForZwoj = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder dalsze = new ItemBuilder(Material.REDSTONE_TORCH_ON);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);

    public void openKowalMainGui(final Player player) {

        player.getInventory().addItem(GlobalItem.getItem("I19", 1));

        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&4&lKowal"));
        final List<String> lore = new ArrayList<>();

        fill.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill.toItemStack());
        }

        lore.add(" ");
        lore.add("&8Pamietaj, ze &4Administracja&8 nie ponosi");
        lore.add("&8odpowiedzialnosci za itemy stracone");
        lore.add("&8przez blad wlasny gracza");

        gui.setItem(12, anvil.setName("&aUlepsz swoj przedmiot").setLore(lore).toItemStack());

        lore.clear();
        lore.add(" ");
        lore.add("&7Wymagania: ");
        lore.add("&8>> &61 000 000&2$");
        if (rpgcore.getUserManager().find(player.getUniqueId()).getLvl() < 75) {
            lore.add("&8>> &c75 &7Lvl");
        } else {
            lore.add("&8>> &a75 &7Lvl");
        }
        gui.setItem(14, dalsze.setName("&cDalsze Etapy &4DT").setLore(lore).toItemStack());

        player.openInventory(gui);
    }

    public void openKowalUlepszanieGui(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 36, Utils.format("&4&lKowal - &6&lUlepszanie"));
        final List<String> lore = new ArrayList<>();

        fill.setName(" ");

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fill.toItemStack());
        }

        gui.setItem(12, this.getPlaceForItem());
        gui.setItem(14, this.getPlaceForZwoj());

        lore.add("&7Szansa na ulepszenie przedmiotu: &c50%");
        lore.add(" ");
        lore.add("&8Pamietaj, ze &cAdministracja &8nie ponosi");
        lore.add("&8odpowiedzialnosci za spalone itemy");
        lore.add("&8jezeli bylo to spowodowane bledem serwera");
        lore.add("&cmusisz posiadac nagranie &8potwierdzajace blad");

        gui.setItem(22, ulepszItem.setName("&cUlepsz swoj przedmiot").setLore(lore).toItemStack());


        player.openInventory(gui);
    }

    public void upgradeItem(final Player player, final ItemStack itemToUpgrade, final boolean hasZwoj) {
        this.upgradeList.add(player.getUniqueId());
        this.clearOstatniUlepszonyItem(player.getUniqueId());
        this.addToAnimationList(player.getUniqueId());
        player.teleport(new Location(player.getWorld(), -33.5, 6, -3.5, -90, -4.5F));

        /*Location kowalLocation = new Location(Bukkit.getWorld("dt"), 0, 0, 0);

        for (Player p : Bukkit.getWorld("dt").getPlayers()) {
            if (Utils.removeColor(p.getName()).equals("Kowal")) {
               kowalLocation = p.getLocation();
            }
        }

        Collection<Entity> nearbyEntities = Bukkit.getWorld("dt").getNearbyEntities(kowalLocation, 2, 2, 2);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                if (!Utils.removeColor(entity.getName()).equals("Kowal")) {
                    player.hidePlayer((Player) entity);
                }
            }
        }*/

        ulepszanie.add(0.5, "udane");
        ulepszanie.add(0.5, "nieudane");

        final String result = ulepszanie.next();

        if (result.equals("nieudane")) {
            if (hasZwoj) {
                this.runAnimation(player, itemToUpgrade, false);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    player.getInventory().addItem(itemToUpgrade);
                    player.sendMessage(Utils.format("&4&lKowal &8>> &cNiestety nie udalo mi sie ulepszyc twojego przedmiotu, ale zwoj blogoslawienstwa ochronil go przed spaleniem"));
                }, 70L);
                return;
            } else {
                this.runAnimation(player, itemToUpgrade, false);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.sendMessage(Utils.format("&4&lKowal &8>> &cNiestety nie udalo mi sie ulepszyc twojego przedmiotu i zostal on zniszczony")), 70L);
                return;
            }
        }

        final ItemMeta meta = itemToUpgrade.getItemMeta();

        if (meta.getDisplayName().contains("+1")) {
            meta.setDisplayName(meta.getDisplayName().replace("+1", "+2"));
        } else if (meta.getDisplayName().contains("+2")) {
            meta.setDisplayName(meta.getDisplayName().replace("+2", "+3"));
        } else if (!(meta.getDisplayName().contains("+1") || meta.getDisplayName().contains("+2") || meta.getDisplayName().contains("+3"))) {
            meta.setDisplayName(meta.getDisplayName() + Utils.format(" &8&l+1"));
        }
        itemToUpgrade.setItemMeta(meta);

        final String itemToUpgradeType = String.valueOf(itemToUpgrade.getType());
        if (itemToUpgradeType.contains("HELMET") || itemToUpgradeType.contains("CHESTPLATE") || itemToUpgradeType.contains("LEGGINGS") || itemToUpgradeType.contains("BOOTS")) {
            final int prot = Utils.getProtectionLevel(itemToUpgrade);
            final int thorns = Utils.getThornsLevel(itemToUpgrade);
            this.runAnimation(player, itemToUpgrade, true);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.upgradeArmor(player, itemToUpgrade, prot, thorns), 70L);
            return;
        }
        final int sharp = Utils.getSharpnessLevel(itemToUpgrade);
        final int obrazeniaMoby = Utils.getObrazeniaMobyLevel(itemToUpgrade);
        this.runAnimation(player, itemToUpgrade, true);
        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> this.upgradeWeapon(player, itemToUpgrade, sharp, obrazeniaMoby), 70L);
    }

    public void upgradeArmor(final Player player, final ItemStack itemToUpgrade, final int prot, final int thorns) {
        final ItemMeta meta = itemToUpgrade.getItemMeta();
        final List<String> lore = meta.getLore();

        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if (Utils.removeColor(s).contains("❣ Obrona: ")) {
                lore.set(i, Utils.format(s.substring(0, s.lastIndexOf(" ")) + " &f" + (prot + 2)));
            } else if (Utils.removeColor(s).contains("✪ Thorns: ")) {
                lore.set(i, Utils.format(s.substring(0, s.lastIndexOf(" ")) + " &f" + (thorns + 1)));
            }
        }

        meta.setLore(lore);
        itemToUpgrade.setItemMeta(meta);

        player.getInventory().addItem(itemToUpgrade.clone());
        player.sendMessage(Utils.format("&4&lKowal &8>> &aPomyslnie ulepszylem twoj przedmiot"));
    }

    public void upgradeWeapon(final Player player, final ItemStack itemToUpgrade, final int sharp, final int obrazeniaMoby) {
        final ItemMeta meta = itemToUpgrade.getItemMeta();
        final List<String> lore = meta.getLore();

        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if (Utils.removeColor(s).contains("⚔ Obrazenia: ")) {
                lore.set(i, Utils.format(s.substring(0, s.lastIndexOf(" ")) + " &c" + (sharp + 4)));
            } else if (Utils.removeColor(s).contains("☠ Obrazenia na potwory: ")) {
                lore.set(i, Utils.format(s.substring(0, s.lastIndexOf(" ")) + " &c" + (obrazeniaMoby + 2)));
            }
        }

        meta.setLore(lore);
        itemToUpgrade.setItemMeta(meta);

        player.getInventory().addItem(itemToUpgrade.clone());
        player.sendMessage(Utils.format("&4&lKowal &8>> &aPomyslnie ulepszylem twoj przedmiot"));
    }

    private void runAnimation(final Player player, final ItemStack is, final boolean ulepszylo) {
        final WorldServer s = ((CraftWorld) player.getWorld()).getHandle();
        final EntityArmorStand itemArmorStand = new EntityArmorStand(s);
        final EntityArmorStand hammerArmorStand = new EntityArmorStand(s);
        final net.minecraft.server.v1_8_R3.ItemStack item = CraftItemStack.asNMSCopy(is);
        final net.minecraft.server.v1_8_R3.ItemStack hammer = CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_AXE));

        if (String.valueOf(is.getType()).contains("SWORD")) {
            itemArmorStand.setLocation(-31.30, 5.60, -2.666, 132.36932F, 30.1819F);
            itemArmorStand.setRightArmPose(new Vector3f(2.1F * 55, 3.3F * 58, 1.4F * 60));
        } else {
            itemArmorStand.setLocation(-31.55, 6.22, -2.966, 132.36932F, 30.1819F);
            itemArmorStand.setRightArmPose(new Vector3f(2.1F, 3.3F, 1.4F));
        }
        itemArmorStand.setArms(true);
        itemArmorStand.setGravity(false);
        itemArmorStand.setInvisible(true);


        hammerArmorStand.setLocation(-32, 6.4, -2.6, 180F, 0F);
        hammerArmorStand.setRightArmPose(new Vector3f(-45F, hammerArmorStand.rightArmPose.getY(), hammerArmorStand.rightArmPose.getZ()));
        hammerArmorStand.setArms(true);
        hammerArmorStand.setGravity(false);
        hammerArmorStand.setInvisible(true);

        final PacketPlayOutSpawnEntityLiving itemArmorStandSpawnPacket = new PacketPlayOutSpawnEntityLiving(itemArmorStand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemArmorStandSpawnPacket);

        final PacketPlayOutEntityEquipment itemPacketItemArmorStand = new PacketPlayOutEntityEquipment(itemArmorStand.getId(), 0, item);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketItemArmorStand);

        final PacketPlayOutSpawnEntityLiving hammerArmorStandSpawnPacket = new PacketPlayOutSpawnEntityLiving(hammerArmorStand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(hammerArmorStandSpawnPacket);

        final PacketPlayOutEntityEquipment itemPacketHammerArmorStand = new PacketPlayOutEntityEquipment(hammerArmorStand.getId(), 0, hammer);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(itemPacketHammerArmorStand);

        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            this.changeArmPossition(player, hammerArmorStand, -15F);
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                this.changeArmPossition(player, hammerArmorStand, -45F);
                rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                    this.changeArmPossition(player, hammerArmorStand, -15F);
                    rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                        this.changeArmPossition(player, hammerArmorStand, -45F);
                        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                            this.changeArmPossition(player, hammerArmorStand, -15F);
                            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
                                this.changeArmPossition(player, hammerArmorStand, -45F);
                                if (ulepszylo) {
                                    this.spawnPossitiveUpgradeParticles(player, new Location(player.getWorld(), hammerArmorStand.locX, hammerArmorStand.locY, hammerArmorStand.locZ));
                                } else {
                                    this.spawnNegativeUpgradeParticles(player, new Location(player.getWorld(), hammerArmorStand.locX, hammerArmorStand.locY, hammerArmorStand.locZ));
                                }
                                this.destroyPackets(player, itemArmorStand.getId(), hammerArmorStand.getId());
                                this.removeFromAnimationList(player.getUniqueId());
                            }, 10L);
                        }, 10L);
                    }, 10L);
                }, 10L);
            }, 10L);
        }, 10L);
    }

    private void changeArmPossition(final Player player, final EntityArmorStand entityArmorStand, final float x) {
        entityArmorStand.setRightArmPose(new Vector3f(x, entityArmorStand.rightArmPose.getY(), entityArmorStand.rightArmPose.getZ()));
        final PacketPlayOutEntityMetadata metaHammerArmorStand = new PacketPlayOutEntityMetadata(entityArmorStand.getId(), entityArmorStand.getDataWatcher(), true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(metaHammerArmorStand);
    }

    private void spawnPossitiveUpgradeParticles(final Player player, final Location location) {
        float x = 0.1F;
        float y = 1.5F;
        float z = 1.3F;
        for (int i = 0; i < 5; i++) {
            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) location.getX() - x, (float) location.getY() + y, (float) location.getZ() - z, 0, 0, 0, 0, 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
            y -= 0.1F;
            z -= 0.1F;
        }
        for (int i = 0; i < 10; i++) {
            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) location.getX() - x, (float) location.getY() + y, (float) location.getZ() - z, 0, 0, 0, 0, 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
            y += 0.1F;
            z -= 0.1F;
        }
    }

    private void spawnNegativeUpgradeParticles(final Player player, final Location location) {
        float x = 0.1F;
        float y = 1.5F;
        float z = 1.3F;
        float z2 = 0.7F;
        for (int i = 0; i < 10; i++) {
            PacketPlayOutWorldParticles particles1 = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) location.getX() - x, (float) location.getY() + y, (float) location.getZ() - z, 0, 0, 0, 0, 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles1);
            PacketPlayOutWorldParticles particles2 = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, (float) location.getX() - x, (float) location.getY() + y, (float) location.getZ() - z + z2, 0, 0, 0, 0, 0, 0);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles2);
            y -= 0.1F;
            z -= 0.1F;
            z2 -= 0.2F;
        }
    }

    private void destroyPackets(final Player player, final int id1, final int id2) {
        final PacketPlayOutEntityDestroy packet1 = new PacketPlayOutEntityDestroy(id1);
        final PacketPlayOutEntityDestroy packet2 = new PacketPlayOutEntityDestroy(id2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);
    }


    public ItemStack getPlaceForItem() {
        return placeForItem.setName("&aMiejsce na przedmiot").toItemStack();
    }

    public ItemStack getPlaceForZwoj() {
        return placeForZwoj.setName("&aMiejsce na zwoj").toItemStack();
    }

    public List<ItemStack> getOstatniUlepszonyItem(final UUID uuid) {
        return ostatniUlepszonyItem.get(uuid);
    }

    public void updateOstatniUlepszonyItem(final UUID uuid, final List<ItemStack> item) {
        ostatniUlepszonyItem.remove(uuid);
        ostatniUlepszonyItem.put(uuid, item);
    }

    public void clearOstatniUlepszonyItem(final UUID uuid) {
        ostatniUlepszonyItem.remove(uuid);
    }

    public void addOstatniUlepszonyItem(final UUID uuid, final ItemStack is) {
        ostatniUlepszonyItem.computeIfAbsent(uuid, k -> new ArrayList<>());
        ostatniUlepszonyItem.get(uuid).add(is);
    }

    public void removeOstatniUlepszonyItem(final UUID uuid, final ItemStack is) {
        ostatniUlepszonyItem.get(uuid).remove(is);
    }

    public boolean hasAlreadyUpgraded(final UUID uuid) {
        return upgradeList.contains(uuid);
    }

    public void resetUpgradeList() {
        upgradeList.clear();
    }

    public void addToAnimationList(final UUID uuid) {
        animationList.add(uuid);
    }

    public void removeFromAnimationList(final UUID uuid) {
        animationList.remove(uuid);
    }

    public boolean isInAnimationList(final UUID uuid) {
        return animationList.contains(uuid);
    }

}
