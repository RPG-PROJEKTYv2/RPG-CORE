package rpg.rpgcore.chests.Dungeony.IceTower;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class KrolLoduManager {
    private final Set<Items> mroznywladca = Sets.newConcurrentHashSet();

    public KrolLoduManager() {
        this.mroznywladca.add(new Items("6", 1.0, GlobalItem.I_METAL.getItemStack().clone(), 3));
        this.mroznywladca.add(new Items("7", 1.5, GlobalItem.I_METAL.getItemStack().clone(), 2));
        this.mroznywladca.add(new Items("8", 2.5, GlobalItem.I_METAL.getItemStack().clone(), 1));
        this.mroznywladca.add(new Items("9", 2.0, GlobalItem.I10.getItemStack().clone(), 2));
        this.mroznywladca.add(new Items("10", 2.5, SkrzynkiOther.getItem("I2", 1), 1));
        this.mroznywladca.add(new Items("11", 4.0, GlobalItem.I10.getItemStack().clone(), 1));
        this.mroznywladca.add(new Items("12", 4.0, new ItemBuilder(Material.STORAGE_MINECART).setName("&b&lNaszyjnik Krola Lodu").toItemStack(), 1));
        this.mroznywladca.add(new Items("13", 4.0, new ItemBuilder(Material.WATCH).setName("&b&lDiadem Krola Lodu").toItemStack(), 1));
        this.mroznywladca.add(new Items("14", 4.0, new ItemBuilder(Material.EXPLOSIVE_MINECART).setName("&b&lPierscien Krola Lodu").toItemStack(), 1));
        this.mroznywladca.add(new Items("15", 4.0, new ItemBuilder(Material.ITEM_FRAME).setName("&b&lTarcza Krola Lodu").toItemStack(), 1));
        this.mroznywladca.add(new Items("16", 4.0, new ItemBuilder(Material.HOPPER_MINECART).setName("&b&lKolczyki Krola Lodu").toItemStack(),1 ));
        this.mroznywladca.add(new Items("17", 6.0, GlobalItem.I10.getItemStack().clone(), 1));
        this.mroznywladca.add(new Items("18", 9.0, ItemHelper.createSword("&b&lLodowy Sztylet", Material.DIAMOND_SWORD, 26, 16, false), 1));
        this.mroznywladca.add(new Items("19", 10.0, GlobalItem.getItem("I_FRAGMENT_STALI", 2), 1));

    }

    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.mroznywladca) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
