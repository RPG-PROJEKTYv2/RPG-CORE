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
        this.sakiewkaUlepszaczy.add(new Items("1", 1.35, Ulepszacze.I_WILCZEFUTRO.getItem(),ChanceHelper.getRandInt(2, 3)));
        this.sakiewkaUlepszaczy.add(new Items("2", 1.5, Ulepszacze.I_TRUJACAROSLINA.getItem(),ChanceHelper.getRandInt(2, 4)));
        this.sakiewkaUlepszaczy.add(new Items("3", 1.5, Ulepszacze.I_JADPTASZNIKA.getItem(),ChanceHelper.getRandInt(2, 4)));
        this.sakiewkaUlepszaczy.add(new Items("4", 1.6, Ulepszacze.I_SKORAGORYLA.getItem(),ChanceHelper.getRandInt(2, 6)));
        this.sakiewkaUlepszaczy.add(new Items("5", 2.0, Ulepszacze.I_OGNISTYPYL.getItem(),ChanceHelper.getRandInt(2, 4)));
        this.sakiewkaUlepszaczy.add(new Items("6", 2.0, Ulepszacze.I_OKOGOBLINA.getItem(),ChanceHelper.getRandInt(2, 6)));
        this.sakiewkaUlepszaczy.add(new Items("7", 2.5, Ulepszacze.I_SZATAROZBOJNIKA.getItem(),ChanceHelper.getRandInt(2, 6)));
        this.sakiewkaUlepszaczy.add(new Items("8", 2.5, Ulepszacze.I_ZLAMANAKOSC.getItem(),ChanceHelper.getRandInt(2, 5)));
        this.sakiewkaUlepszaczy.add(new Items("9", 2.5, Ulepszacze.I_LZAOCEANU.getItem(),ChanceHelper.getRandInt(2, 5)));
    }


    public void getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.sakiewkaUlepszaczy) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getRewardItem().getAmount()) + " " + item.getRewardItem().getItemMeta().getDisplayName());
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        this.getDrawnItems(player);
    }
}
