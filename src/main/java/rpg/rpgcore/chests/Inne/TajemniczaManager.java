package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.RandomItems;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TajemniczaManager {


    private final Set<Items> tajemnicza = Sets.newConcurrentHashSet();



    public TajemniczaManager() {
        // bony 5%
        // TODO BONY ZMIENIC Z 1 NA 0.001
        this.tajemnicza.add(new Items("1", 1, BonType.SREDNIE_5.getBon(), 1));
        this.tajemnicza.add(new Items("2", 1, BonType.DEFENSYWA_5.getBon(), 1));
        this.tajemnicza.add(new Items("3", 1, BonType.KRYTYK_5.getBon(), 1));
        // szkata z surowcami
        this.tajemnicza.add(new Items("4", 7.0, GlobalItem.getItem("I5", 1), 1));
        this.tajemnicza.add(new Items("5", 3.5, GlobalItem.getItem("I5", 2), 2));
        // bao, ksiega
        this.tajemnicza.add(new Items("9", 0.5, GlobalItem.getItem("I_KAMIENBAO", 1), 1));
        this.tajemnicza.add(new Items("10", 0.25, GlobalItem.getItem("I_KSIEGAMAGII", 1), 1));
        // set I
        this.tajemnicza.add(new Items("11", 7.0, ItemHelper.createArmor("&3Uszkodzony Tajemniczy Helm", Material.IRON_HELMET, 4, 1, false), 1));
        this.tajemnicza.add(new Items("12", 7.0, ItemHelper.createArmor("&3Uszkodzony Tajemniczy Napiersnik", Material.IRON_CHESTPLATE, 4, 1, false), 1));
        this.tajemnicza.add(new Items("13", 7.0, ItemHelper.createArmor("&3Uszkodzone Tajemnicze Spodnie", Material.IRON_LEGGINGS, 4, 1, false), 1));
        this.tajemnicza.add(new Items("14", 7.0, ItemHelper.createArmor("&3Uszkodzone Tajemnicze Buty", Material.IRON_BOOTS, 4, 1, false), 1));
        this.tajemnicza.add(new Items("15", 5.0, ItemHelper.createSword("&3Uszkodzony Tajemniczy Miecz", Material.IRON_SWORD, 3, 2, false), 1));
        // set II
        this.tajemnicza.add(new Items("11", 3.0, ItemHelper.createArmor("&3Tajemniczy Helm", Material.DIAMOND_HELMET, 7, 7, false), 1));
        this.tajemnicza.add(new Items("12", 3.0, ItemHelper.createArmor("&3Tajemniczy Napiersnik", Material.DIAMOND_CHESTPLATE, 7, 2, false), 1));
        this.tajemnicza.add(new Items("13", 3.0, ItemHelper.createArmor("&3Tajemnicze Spodnie", Material.DIAMOND_LEGGINGS, 7, 2, false), 1));
        this.tajemnicza.add(new Items("14", 3.0, ItemHelper.createArmor("&3Tajemnicze Buty", Material.DIAMOND_BOOTS, 7, 2, false), 1));
        this.tajemnicza.add(new Items("15", 1.75, ItemHelper.createSword("&3Tajemniczy Miecz", Material.DIAMOND_SWORD, 6, 4, false), 1));
        // wywar
        this.tajemnicza.add(new Items("16", 3.5, LesnikItems.getByItem("POTION", 1), 1));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.tajemnicza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                player.sendMessage(Utils.format("&2+ &f" + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
