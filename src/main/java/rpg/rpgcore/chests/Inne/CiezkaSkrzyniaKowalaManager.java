package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.objects.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CiezkaSkrzyniaKowalaManager {

    private final Set<Items> kowal = Sets.newConcurrentHashSet();

    public CiezkaSkrzyniaKowalaManager() {
        this.kowal.add(new Items("1", 0.32, new ItemBuilder(Material.MINECART).setName("&4&lEnergia Piekielnego Kowala").toItemStack(),1));
        this.kowal.add(new Items("2", 4.0, GlobalItem.I_KAMIENBAO.getItemStack(), 1));
        this.kowal.add(new Items("3", 8.0, GlobalItem.getItem("I_METAL", 1), 1));
        this.kowal.add(new Items("4", 10.0, GlobalItem.getItem("I_OCZYSZCZENIE", 2),1));
        this.kowal.add(new Items("5", 10.0, GlobalItem.getItem("I10", 2), 1));
        this.kowal.add(new Items("6", 12.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1),1));
        this.kowal.add(new Items("7", 12.0, GlobalItem.getItem("I10", 1), 1));
        this.kowal.add(new Items("8", 12.0, GlobalItem.getItem("I_FRAGMENT_STALI",3),1));
        this.kowal.add(new Items("9", 12.5, GlobalItem.getItem("I_FRAGMENT_STALI",2),1));
    }

    public void getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.kowal) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                if (item.getRewardItem().getType() == Material.MINECART) {
                    if (item.getRewardItem().getItemMeta().getDisplayName().contains("Energia Piekielnego Kowala")) {
                        Bukkit.getServer().broadcastMessage(" ");
                        Bukkit.getServer().broadcastMessage(Utils.format("&7&lCiezka Skrzynia Kowala &8&l>>"));
                        Bukkit.getServer().broadcastMessage(Utils.format("&eGracz: &c" + player.getName() + " &eznalazl &4&lENERGIE PIEKIELNEGO KOWALA&e!!!"));
                        Bukkit.getServer().broadcastMessage(" ");
                        player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-33, -25), ChanceHelper.getRandInt(27, 61), ChanceHelper.getRandInt(33, 68), ChanceHelper.getRandDouble(0.1, 0.4), ChanceHelper.getRandInt(-50, -45), ChanceHelper.getRandInt(60, 75), "&4&lEnergia Piekielnego Kowala"));
                        return;
                    }
                    return;
                }
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getRewardItem().getAmount()) + " " + item.getRewardItem().getItemMeta().getDisplayName());
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        this.getDrawnItems(player);
    }
}
