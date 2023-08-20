package rpg.rpgcore.npc.przyrodnik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.przyrodnik.enums.PrzyrodnikMissions;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikObject;
import rpg.rpgcore.npc.przyrodnik.objects.PrzyrodnikUser;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class PrzyrodnikNPC {
    private final Map<UUID, PrzyrodnikObject> userMap;

    public PrzyrodnikNPC(final RPGCORE rpgcore) {
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
        if (user.getMission() == 13) {
            gui.setItem(13, PrzyrodnikMissions.M_ERROR.getItemStack());
        } else {
            gui.setItem(13, this.getOddajItemyItem(user));
            gui.setItem(16, this.getCurrentItemToDrop(user, player.getUniqueId()));
        }

        player.openInventory(gui);
    }

    public ItemStack getStatystykiItem(final PrzyrodnikUser user) {
        return new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList("&7Srednie Obrazenia: &c" + user.getDmg() + "%", "&7Srednia Defensywa: &c" + user.getDef() + "%")).toItemStack().clone();
    }

    public ItemStack getOddajItemyItem(final PrzyrodnikUser user) {
        PrzyrodnikMissions przyrodnikMissions = PrzyrodnikMissions.getByNumber(user.getMission());
        if (user.getMission() == 13) return PrzyrodnikMissions.M_ERROR.getItemStack();
        return new ItemBuilder(przyrodnikMissions.getItemStack().clone()).setName(przyrodnikMissions.getItemStack().getItemMeta().getDisplayName() + " &7x" + przyrodnikMissions.getReqAmount()).setLore(Arrays.asList(
                "&f&lNagroda",
                "&7Srednie Obrazenia: &c" + przyrodnikMissions.getDmg() + "%",
                "&7Srednia Odpornosc: &c" + przyrodnikMissions.getDef() + "%",
                "",
                "&7Szansa na przyjecie: &e" + przyrodnikMissions.getAcceptPercent() + "%",
                "&7Postep: &6" + user.getProgress() + "&7/&6" + przyrodnikMissions.getReqAmount() + " &8(&6" + DoubleUtils.round((user.getProgress() / (double) przyrodnikMissions.getReqAmount() * 100.0), 2) + "%&8)")
        ).toItemStack().clone();
    }

    public ItemStack getCurrentItemToDrop(final PrzyrodnikUser user, final UUID uuid) {
        PrzyrodnikMissions przyrodnikMissions = PrzyrodnikMissions.getByNumber(user.getMission());
        double chance = przyrodnikMissions.getDropChance();
        final double szczescie = RPGCORE.getInstance().getBonusesManager().find(uuid).getBonusesUser().getSzczescie();
        ItemStack item = przyrodnikMissions.getItemStack().clone();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Utils.format(
                Arrays.asList(
                        "&7Wypada z: &6" + przyrodnikMissions.getMobName(),
                        "&7Szansa na drop: &6" + DoubleUtils.round(chance + ((chance * szczescie) / 1000.0), 2) + "%"
                )
        ));
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

    public void set(final UUID uuid, final PrzyrodnikObject przyrodnikObject) {
        this.userMap.replace(uuid, przyrodnikObject);
    }

    public ImmutableSet<PrzyrodnikObject> getPrzyrodnikObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }


}
