package rpg.rpgcore.wyszkolenie;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.wyszkolenie.objects.WyszkolenieUser;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class WyszkolenieManager {
    private final RPGCORE rpgcore;
    private final Map<UUID, WyszkolenieUser> userMap;

    public WyszkolenieManager(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllWyszkolenie();
        this.rpgcore = rpgcore;
    }

    public void openWyszkolenieGUI(final Player player) {
        final WyszkolenieUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, 54, Utils.format("&3&lStatus Wyszkolenia " + player.getName()));
        final ItemStack fillItem = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).setName(" ").toItemStack();

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, fillItem);
        }

        final ItemStack fillItem2 = new ItemBuilder(Material.IRON_FENCE).setName(" ").toItemStack();

        gui.setItem(10, fillItem2);
        gui.setItem(11, fillItem2);
        gui.setItem(12, fillItem2);
        gui.setItem(14, fillItem2);
        gui.setItem(15, fillItem2);
        gui.setItem(16, fillItem2);

        gui.setItem(18, fillItem2);
        gui.setItem(22, fillItem2);
        gui.setItem(26, fillItem2);

        gui.setItem(28, fillItem2);
        gui.setItem(29, fillItem2);
        gui.setItem(30, fillItem2);
        gui.setItem(32, fillItem2);
        gui.setItem(33, fillItem2);
        gui.setItem(34, fillItem2);


        gui.setItem(19, new ItemBuilder(Material.DIAMOND_SWORD).setName("&6&lSrednie Obrazenia").setLore(Arrays.asList("&7Postep: &e" + user.getSrDmg() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(20, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&6&lSrednia Defensywa").setLore(Arrays.asList("&7Postep: &e" + user.getSrDef() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(21, new ItemBuilder(Material.BOW).setName("&6&lSzansa Na Cios Krytyczny").setLore(Arrays.asList("&7Postep: &e" + user.getKryt() + "%&7/&e10%")).hideFlag().toItemStack().clone());
        gui.setItem(23, new ItemBuilder(Material.GOLD_INGOT).setName("&6&lSzczescie").setLore(Arrays.asList("&7Postep: &e" + user.getSzczescie() + "&7/&e10")).toItemStack().clone());
        gui.setItem(24, new ItemBuilder(Material.BONE).setName("&6&lBlok Ciosu").setLore(Arrays.asList("&7Postep: &e" + user.getBlok() + "%&7/&e5%")).toItemStack().clone());
        gui.setItem(25, new ItemBuilder(Material.APPLE).setName("&6&lDodatkowe HP").setLore(Arrays.asList("&7Postep: &e" + user.getHp() + "HP&7/&e5HP")).toItemStack().clone());


        gui.setItem(53, new ItemBuilder(Material.WORKBENCH).setName("&4Reset Wyszkolenia").setLore(Arrays.asList("&7Kliknij aby zresetowac postep swojego wyszkolenie", " ", "&7Cooldown: " + (user.hasCooldown() ? "&c" + Utils.durationToString(user.getResetCooldown(), false) : "&aGotowe"), " ", "&7Koszt:", "   &8- &61 000 000 &2$", " ", "&7Resetujac otrzymasz &5" + user.getTotalPoints() + " punktow wyszkolenia", "&7oraz cooldown na kolejny reset", "&7w wysokosci &c1 godziny!")).toItemStack().clone());
        player.openInventory(gui);
    }

    public WyszkolenieUser find(final UUID uuid) {
        return userMap.get(uuid);
    }
    public void add(final WyszkolenieUser wyszkolenieUser) {
        userMap.put(wyszkolenieUser.getUuid(), wyszkolenieUser);
    }
    public ImmutableSet<WyszkolenieUser> getWyszkolenieUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }

}
