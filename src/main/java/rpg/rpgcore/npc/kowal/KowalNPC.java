package rpg.rpgcore.npc.kowal;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.GlobalItems;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.Utils;

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

    // ITEMY DO GUI
    private final ItemBuilder anvil = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder ulepszItem = new ItemBuilder(Material.ANVIL);
    private final ItemBuilder placeForItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder placeForZwoj = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5);
    private final ItemBuilder dalsze = new ItemBuilder(Material.REDSTONE_TORCH_ON);
    private final ItemBuilder fill = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14);

    public void openKowalMainGui(final Player player) {

        player.getInventory().addItem(GlobalItems.getZwojBlogoslawienstwa(1));

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
        if (rpgcore.getPlayerManager().getPlayerLvl(player.getUniqueId()) < 75) {
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

        ulepszanie.add(0.5, "udane");
        ulepszanie.add(0.5, "nieudane");

        final String result = ulepszanie.next();

        if (result.equals("nieudane")) {
            if (hasZwoj) {
                player.getInventory().addItem(itemToUpgrade);
                player.sendMessage(Utils.format("&4&lKowal &8>> &cNiestety nie udalo mi sie ulepszyc twojego przedmiotu, ale zwoj blogoslawienstwa ochronil go przed spaleniem"));
                return;
            } else {
                player.sendMessage(Utils.format("&4&lKowal &8>> &cNiestety nie udalo mi sie ulepszyc twojego przedmiotu i zostal on zniszczony"));
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

            this.upgradeArmor(player, itemToUpgrade, prot, thorns);
            return;
        }
        final int sharp = Utils.getSharpnessLevel(itemToUpgrade);
        final int obrazeniaMoby = Utils.getObrazeniaMobyLevel(itemToUpgrade);
        this.upgradeWeapon(player, itemToUpgrade, sharp, obrazeniaMoby);
    }

    public void upgradeArmor(final Player player, final ItemStack itemToUpgrade, final int prot, final int thorns) {

        this.runAnimation(player, itemToUpgrade);

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
        return;

    }

    public void upgradeWeapon(final Player player, final ItemStack itemToUpgrade, final int sharp, final int obrazeniaMoby) {
        //TODO Dodac animacje z armorstandow

        this.runAnimation(player, itemToUpgrade);

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
        return;
    }

    private void runAnimation(final Player player, final ItemStack is) {
        final WorldServer s = ((CraftWorld) player.getWorld()).getHandle();
        final EntityArmorStand stand = new EntityArmorStand(s);
        final net.minecraft.server.v1_8_R3.ItemStack item = CraftItemStack.asNMSCopy(is);

        if (String.valueOf(is.getType()).contains("SWORD")) {
            stand.setLocation(-31.30, 5.60, -2.666, 132.36932F, 30.1819F);
            stand.setRightArmPose(new Vector3f(2.1F * 55, 3.3F * 58, 1.4F * 60));
        } else {
            stand.setLocation(-31.55, 6.22, -2.966, 132.36932F, 30.1819F);
            stand.setRightArmPose(new Vector3f(2.1F, 3.3F, 1.4F));
        }
        stand.setArms(true);
        stand.setGravity(false);
        stand.setInvisible(true);

        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        final PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(stand.getId(), 0, item);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet2);

        final PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(),true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(meta);

        rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> {
            final PacketPlayOutEntityDestroy packet3 = new PacketPlayOutEntityDestroy(stand.getId());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet3);
        }, 200L);
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

}
