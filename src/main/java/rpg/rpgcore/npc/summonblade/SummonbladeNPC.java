package rpg.rpgcore.npc.summonblade;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.summonblade.objects.SummonbladeUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class SummonbladeNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, SummonbladeUser> userMap;

    public SummonbladeNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllSummonblade();
    }

    public void openSummonbladeGUI(final Player player) {
        final SummonbladeUser user = this.find(player.getUniqueId());
        if (user.isActivated()) {
            player.sendMessage(Utils.format("&6&lSummonblade &8>> &cPosiadasz juz aktywna &f&lKrysztalowa Bariere"));
            return;
        }
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lSummonblade"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        }

        gui.setItem(4, new ItemBuilder(Material.MONSTER_EGG).setName("&7Przyzwij &f&lEreb'a").setLore(Arrays.asList(
                "&f&lZabij:",
                "- &8[&4&lBOSS&8] &cDowodca Rozbojnikow &8(&7" + user.getBoss1_10progress() + "&8/&7" + 32 + "&8)",
                "- &8[&4&lBOSS&8] &a&lWodz Goblinow &8(&7" + user.getBoss10_20progress() + "&8/&7" + 28 + "&8)",
                "- &8[&4&lBOSS&8] &f&lKrol Goryli &8(&7" + user.getBoss20_30progress() + "&8/&7" + 24 + "&8)",
                "- &8[&4&lBOSS&8] &7&lPrzekleta Dusza &8(&7" + user.getBoss30_40progress() + "&8/&7" + 20 + "&8)",
                "- &8[&4&lBOSS&8] &e&lTryton &8(&7" + user.getBoss40_50progress() + "&8/&7" + 16 + "&8)",
                "- &8[&4&lBOSS&8] &c&lPiekielny Rycerz &8(&7" + user.getBoss60_70progress() + "&8/&7" + 12 + "&8)",
                "- &8[&4&lBOSS&8] &5&lPrzeklety Czarnoksieznik &8(&7" + user.getBoss70_80progress() + "&8/&7" + 4 + "&8)",
                "- &8[&4&lBOSS&8] &e&lMityczny Pajak &8(&7" + user.getBoss80_90progress() + "&8/&7" + 2 + "&8)",
                "- &8[&4&lBOSS&8] &5&lPodziemny Rozpruwacz &8(&7" + user.getBoss90_100progress() + "&8/&7" + 1 + "&8)",
                "- &8[&4&lBOSS&8] &b&lMistyczny Kraken &8(&7" + user.getBoss100_110progress() + "&8/&7" + 1 + "&8)"
        )).toItemStack().clone());

        player.openInventory(gui);
    }


    public void add(final SummonbladeUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public SummonbladeUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public ImmutableSet<SummonbladeUser> getSummonbladeUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
