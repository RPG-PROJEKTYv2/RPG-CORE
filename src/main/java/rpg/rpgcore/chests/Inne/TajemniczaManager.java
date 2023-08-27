package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.globalitems.GlobalItem;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.SkrzynkiOther;
import rpg.rpgcore.utils.globalitems.npc.LesnikItems;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TajemniczaManager {


    private final Set<Items> tajemnicza = Sets.newConcurrentHashSet();



    public TajemniczaManager() {
        // set I
        this.tajemnicza.add(new Items("1", 10.0, ItemHelper.createArmor("&3Tajemniczy Helm &8&l[&f&lI&8&l]", Material.IRON_HELMET, ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("2", 10.0, ItemHelper.createArmor("&3Tajemnicza Klata &8&l[&f&lI&8&l]", Material.IRON_CHESTPLATE, ChanceHelper.getRandInt(4, 7), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("3", 10.0, ItemHelper.createArmor("&3Tajemnicze Spodnie &8&l[&f&lI&8&l]", Material.IRON_LEGGINGS, ChanceHelper.getRandInt(3, 6), ChanceHelper.getRandInt(1, 2)), 1));
        this.tajemnicza.add(new Items("4", 10.0, ItemHelper.createArmor("&3Tajemnicze Buty &8&l[&f&lI&8&l]", Material.IRON_BOOTS, ChanceHelper.getRandInt(3, 5), ChanceHelper.getRandInt(1, 2)), 1));
        // set II
        this.tajemnicza.add(new Items("5", 8.0, ItemHelper.createArmor("&3Tajemniczy Helm &8&l[&f&lII&8&l]", Material.DIAMOND_HELMET, ChanceHelper.getRandInt(7, 20), ChanceHelper.getRandInt(2, 4)), 1));
        this.tajemnicza.add(new Items("6", 8.0, ItemHelper.createArmor("&3Tajemnicza Klata &8&l[&f&lII&8&l]", Material.DIAMOND_CHESTPLATE, ChanceHelper.getRandInt(8, 22), ChanceHelper.getRandInt(2, 5)), 1));
        this.tajemnicza.add(new Items("7", 8.0, ItemHelper.createArmor("&3Tajemnicze Spodnie &8&l[&f&lII&8&l]", Material.DIAMOND_LEGGINGS, ChanceHelper.getRandInt(7, 18), ChanceHelper.getRandInt(2, 4)), 1));
        this.tajemnicza.add(new Items("8", 8.0, ItemHelper.createArmor("&3Tajemnicze Buty &8&l[&f&lII&8&l]", Material.DIAMOND_BOOTS, ChanceHelper.getRandInt(6, 17), ChanceHelper.getRandInt(2, 7)), 1));
        // fragment stali
        this.tajemnicza.add(new Items("9", 7.0, GlobalItem.getItem("I_FRAGMENT_STALI", 2), 2));
        this.tajemnicza.add(new Items("10", 7.0, LesnikItems.POTION.getItem(), 1));
        // zmianka
        this.tajemnicza.add(new Items("11", 6.0, GlobalItem.getItem("I50", 1), 1));
        // wywar z kory
        // skrzynia z surowcami
        this.tajemnicza.add(new Items("12", 4.2, SkrzynkiOther.I4.getItemStack(), 1));
        // podrecznik
        // oczyszczenie
        this.tajemnicza.add(new Items("13", 4.0, GlobalItem.getItem("I10", 2), 2));
        this.tajemnicza.add(new Items("14", 4.0, GlobalItem.getItem("I_OCZYSZCZENIE", 2),2));
        // kamien bao
        this.tajemnicza.add(new Items("15", 2.0, GlobalItem.I_KAMIENBAO.getItemStack(), 1));
        // pozlacany skarb
        this.tajemnicza.add(new Items("16", 1.0, SkrzynkiOther.getItem("I1", 1),1));
        // fragment bona
        this.tajemnicza.add(new Items("17", 0.06, GlobalItem.getItem("I_FRAGMENT_BONA", 1), 2));
    }


    public Items getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.tajemnicza) {
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
