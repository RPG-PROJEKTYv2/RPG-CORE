package rpg.rpgcore.chests.Dungeony.Inne;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Ulepszacze;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SakiewkaUlepszaczyManager {

    private final Set<Items> sakiewkaUlepszaczy = Sets.newConcurrentHashSet();

    public SakiewkaUlepszaczyManager() {
        this.sakiewkaUlepszaczy.add(new Items("1", 1.35, Ulepszacze.getItem("50-60", ChanceHelper.getRandInt(2, 5)),1));
        this.sakiewkaUlepszaczy.add(new Items("2", 1.5, Ulepszacze.getItem("70-80", ChanceHelper.getRandInt(3, 5)),1));
        this.sakiewkaUlepszaczy.add(new Items("3", 1.5, Ulepszacze.getItem("80-90", ChanceHelper.getRandInt(3, 5)),1));
        this.sakiewkaUlepszaczy.add(new Items("4", 1.6, Ulepszacze.getItem("20-30", ChanceHelper.getRandInt(3, 6)),1));
        this.sakiewkaUlepszaczy.add(new Items("5", 2.0, Ulepszacze.getItem("60-70",ChanceHelper.getRandInt(3, 4)),1));
        this.sakiewkaUlepszaczy.add(new Items("6", 2.0, Ulepszacze.getItem("10-20", ChanceHelper.getRandInt(3, 6)),1));
        this.sakiewkaUlepszaczy.add(new Items("7", 2.5, Ulepszacze.getItem("1-10",ChanceHelper.getRandInt(3, 6)),1));
        this.sakiewkaUlepszaczy.add(new Items("8", 2.5, Ulepszacze.getItem("30-40",ChanceHelper.getRandInt(3, 5)),1));
        this.sakiewkaUlepszaczy.add(new Items("9", 2.5, Ulepszacze.getItem("40-50",ChanceHelper.getRandInt(3, 5)),1));
    }


    public void getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.sakiewkaUlepszaczy) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getRewardItem().getAmount()) + " " + item.getRewardItem().getItemMeta().getDisplayName());
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        this.getDrawnItems(player);
    }
}
