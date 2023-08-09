package rpg.rpgcore.chests.Inne;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.chat.ChatUser;
import rpg.rpgcore.chests.Items;
import rpg.rpgcore.dodatki.akcesoriaD.helpers.AkcesoriaDodatHelper;
import rpg.rpgcore.dodatki.akcesoriaP.helpers.AkcesoriaPodsHelper;
import rpg.rpgcore.utils.ChanceHelper;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.ItemHelper;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.GlobalItem;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CiezkaSkrzyniaKowalaManager {

    private final Set<Items> kowal = Sets.newConcurrentHashSet();

    public CiezkaSkrzyniaKowalaManager() {
        // fragmenty stali
        this.kowal.add(new Items("1", 12.5, GlobalItem.getItem("I_FRAGMENT_STALI",2 ),2));
        this.kowal.add(new Items("2", 12.0, GlobalItem.getItem("I_FRAGMENT_STALI",3 ),3));
        // podreczniki
        this.kowal.add(new Items("3", 12.0, GlobalItem.getItem("I10", 1), 1));
        this.kowal.add(new Items("4", 10.0, GlobalItem.getItem("I10", 1), 2));
        // oczyszczenie
        this.kowal.add(new Items("5", 10.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1),1));
        this.kowal.add(new Items("6", 8.0, GlobalItem.getItem("I_OCZYSZCZENIE", 1),2));
        // stal
        this.kowal.add(new Items("7", 6.0, GlobalItem.getItem("I_METAL", 1), 1));
        // kamien bao
        this.kowal.add(new Items("8", 4.0, GlobalItem.I_KAMIENBAO.getItemStack(), 1));
        // ENERGIA RARE
        this.kowal.add(new Items("9", 0.35, new ItemBuilder(Material.MINECART).setName("&4&lEnergia Piekielnego Kowala").toItemStack(),1));
    }

    public void getDrawnItems(final Player player) {
        final ChatUser user = RPGCORE.getInstance().getChatManager().find(player.getUniqueId());
        for (Items item : this.kowal) {
            if (item.getChance() >= 100.0 || item.getChance() > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                item.getRewardItem().setAmount(item.getAmount());
                if (user.isChestDropEnabled()) player.sendMessage(Utils.format("&2+ &fx" + item.getAmount() + " " + item.getRewardItem().getItemMeta().getDisplayName()));
                if (item.getRewardItem().getType() == Material.MINECART) {
                    if (item.getRewardItem().getItemMeta().getDisplayName().contains("Energia Piekielnego Kowala")) {
                        Bukkit.getServer().broadcastMessage(" ");
                        Bukkit.getServer().broadcastMessage(Utils.format("&7&lCiezka Skrzynia Kowala &8&l>>"));
                        Bukkit.getServer().broadcastMessage(Utils.format("&eGracz: &c" + player.getName() + " &eznalazl &4&lENERGIE PIEKIELNEGO KOWALA&e!!!"));
                        Bukkit.getServer().broadcastMessage(" ");
                        player.getInventory().addItem(AkcesoriaDodatHelper.createEnergia(ChanceHelper.getRandInt(-33, -25), ChanceHelper.getRandInt(27, 61), ChanceHelper.getRandInt(33, 68), ChanceHelper.getRandDouble(0.1, 0.4), ChanceHelper.getRandInt(-50, -45), ChanceHelper.getRandInt(60, 75), "&4&lEnergia Piekielnego Kowala"));
                        return;
                    }
                }
                player.getInventory().addItem(item.getRewardItem());
                return;
            }
        }
        this.getDrawnItems(player);
    }
}
