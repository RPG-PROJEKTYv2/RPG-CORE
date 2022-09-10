package rpg.rpgcore.npc.przyrodnik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class PrzyrodnikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, PrzyrodnikObject> userMap;

    public PrzyrodnikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllPrzyrodnik();
    }

    public void openMainGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final PrzyrodnikUser user = find(uuid).getUser();
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lPrzyrodnik"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        gui.setItem(10, this.getStatystykiItem(user));
        if (user.getMission() == 12) {
            gui.setItem(13, Missions.M_ERROR.getItemStack());
        } else {
            gui.setItem(13, this.getOddajItemyItem(user));
            gui.setItem(16, this.getCurrentItemToDrop(user));
        }

        player.openInventory(gui);
    }

    public ItemStack getStatystykiItem(final PrzyrodnikUser user) {
        return new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList("&7Srednie Obrazenia: &c" + user.getDmg() + "%", "&7Srednia Defensywa: &c" + user.getDef() + "%")).toItemStack().clone();
    }

    public ItemStack getOddajItemyItem(final PrzyrodnikUser user) {
        Missions missions = Missions.getByNumber(user.getMission());
        return new ItemBuilder(missions.getItemStack().getType(), missions.getItemStack().getAmount(), missions.getItemStack().getDurability()).setName(missions.getItemStack().getItemMeta().getDisplayName() + " &7x" + missions.getReqAmount())
                .setLore(Arrays.asList("&f&lNagroda", "&7Srednie Obrazenia: &c" + missions.getDmg() + "%", "&7Srednia Odpornosc: &c" + missions.getDef() + "%", "", "&7Postep: &6" + user.getProgress() + "&7/&6" + missions.getReqAmount() + "&8(&6" + Utils.convertIntegersToPercentage(user.getProgress(), missions.getReqAmount()) + "%&8)")).toItemStack().clone();
    }

    public ItemStack getCurrentItemToDrop(final PrzyrodnikUser user) {
        Missions missions = Missions.getByNumber(user.getMission());
        ItemStack item = missions.getItemStack().clone();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Utils.format(Arrays.asList("&7Wypada z: &6" + missions.getMobName(), "&7Szansa: &6" + missions.getDropChance() + "%")));
        item.setItemMeta(meta);
        return item;
    }


    public PrzyrodnikObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new PrzyrodnikObject(uuid));
        return this.userMap.get(uuid);
    }

    public void add(final PrzyrodnikObject przyrodnikObject) {
        this.userMap.put(przyrodnikObject.getId(), przyrodnikObject);
    }

    public ImmutableSet<PrzyrodnikObject> getPrzyrodnikObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isPrzyrodnikObject(final UUID uuid) {
        return this.userMap.containsKey(uuid);
    }


}
