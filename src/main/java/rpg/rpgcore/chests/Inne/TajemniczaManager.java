package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.bony.enums.BonType;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TajemniczaManager {


    private final Set<Items> tajemnicza = Sets.newConcurrentHashSet();



    public TajemniczaManager() {
        // bony 5%
        this.tajemnicza.add(new Items("1", 0.001, BonType.SREDNIE_5.getBon(), 1));
        this.tajemnicza.add(new Items("2", 0.001, BonType.DEFENSYWA_5.getBon(), 1));
        this.tajemnicza.add(new Items("3", 0.001, BonType.KRYTYK_5.getBon(), 1));
        // szkata z surowcami
        this.tajemnicza.add(new Items("4", 7.0, GlobalItem.getItem("I5", 1), 1));
        this.tajemnicza.add(new Items("5", 3.5, GlobalItem.getItem("I5", 2), 2));
        // set I
        this.tajemnicza.add(new Items("11", 6.0, ItemHelper.createArmor("&3Uszkodzony Tajemniczy Helm", Material.LEATHER_HELMET, 3, 1), 1));
        this.tajemnicza.add(new Items("12", 6.0, ItemHelper.createArmor("&3Uszkodzony Tajemniczy Napiersnik", Material.LEATHER_CHESTPLATE, 3, 1), 1));
        this.tajemnicza.add(new Items("13", 6.0, ItemHelper.createArmor("&3Uszkodzone Tajemnicze Spodnie", Material.LEATHER_LEGGINGS, 3, 1), 1));
        this.tajemnicza.add(new Items("14", 6.0, ItemHelper.createArmor("&3Uszkodzone Tajemnicze Buty", Material.LEATHER_BOOTS, 3, 1), 1));
        this.tajemnicza.add(new Items("15", 4.0, ItemHelper.createSword("&3Uszkodzony Tajemniczy Miecz", Material.STONE_SWORD, 5, 2, false), 1));
        // set II
        this.tajemnicza.add(new Items("11", 3.0, ItemHelper.createArmor("&3Tajemniczy Helm", Material.GOLD_HELMET, 8, 2), 1));
        this.tajemnicza.add(new Items("12", 3.0, ItemHelper.createArmor("&3Tajemniczy Napiersnik", Material.GOLD_CHESTPLATE, 8, 2), 1));
        this.tajemnicza.add(new Items("13", 3.0, ItemHelper.createArmor("&3Tajemnicze Spodnie", Material.GOLD_LEGGINGS, 8, 2), 1));
        this.tajemnicza.add(new Items("14", 3.0, ItemHelper.createArmor("&3Tajemnicze Buty", Material.GOLD_BOOTS, 8, 2), 1));
        this.tajemnicza.add(new Items("15", 2.0, ItemHelper.createSword("&3Tajemniczy Miecz", Material.IRON_SWORD, 11, 4, false), 1));
        // set III
        this.tajemnicza.add(new Items("16", 1.5, ItemHelper.createArmor("&3Ulepszony Tajemniczy Helm", Material.IRON_HELMET, 14, 2), 1));
        this.tajemnicza.add(new Items("17", 1.5, ItemHelper.createArmor("&3Ulepszony Tajemniczy Napiersnik", Material.IRON_CHESTPLATE, 14, 2), 1));
        this.tajemnicza.add(new Items("18", 1.5, ItemHelper.createArmor("&3Ulepszony Tajemnicze Spodnie", Material.IRON_LEGGINGS, 14, 2), 1));
        this.tajemnicza.add(new Items("19", 1.5, ItemHelper.createArmor("&3Ulepszony Tajemnicze Buty", Material.IRON_BOOTS, 14, 2), 1));
        this.tajemnicza.add(new Items("20", 1.25, ItemHelper.createSword("&3Ulepszony Tajemniczy Miecz", Material.DIAMOND_SWORD, 15, 7, false), 1));
        // wywar
        this.tajemnicza.add(new Items("21", 3.5, LesnikItems.getByItem("POTION", 1), 1));
        // kufer
        this.tajemnicza.add(new Items("22", 1.0, GlobalItem.getItem("I1", 1), 2));
    }


    public Items getDrawnItems(final Player player) {
        for (Items item : this.tajemnicza) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                return item;
            }
        }
        player.sendMessage(Utils.format("&7Skrzynia okazala sie byc pusta..."));
        return null;
    }
}
