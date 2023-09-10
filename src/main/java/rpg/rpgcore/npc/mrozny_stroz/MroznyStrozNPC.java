package rpg.rpgcore.npc.mrozny_stroz;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.mrozny_stroz.enums.MroznyStrozMissions;
import rpg.rpgcore.npc.mrozny_stroz.objects.MroznyStrozUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class MroznyStrozNPC {
    private final Map<UUID, MroznyStrozUser> userMap;

    public MroznyStrozNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMroznyStroz();
    }

    public void click(final Player player) {
        final MroznyStrozUser user = this.find(player.getUniqueId());

        if (user.getMission() == 9) {
            player.sendMessage(Utils.format("&b&lMrozny Stroz &8>> &fUkonczyles juz wszystkie moje misje!"));
            return;
        }

        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&b&lMrozny Stroz"));

        for (int i = 0; i < 9; i++) gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        for (int i = 0; i < 8; i++) {
            final MroznyStrozMissions mission = MroznyStrozMissions.getMissionById(i + 1);

            if (user.getMission() > mission.getMissionId()) {
                gui.setItem(i, new ItemBuilder(Material.BOOK).setName("&7Misja &c" + (i+1)).setLore(Arrays.asList("", "&a&lWYKONANA!")).addGlowing().toItemStack().clone());
            } else if (user.getMission() == mission.getMissionId()) {
                gui.setItem(i, mission.getMissionItem(user.getProgress()));
            }
        }


        player.openInventory(gui);
    }






    public MroznyStrozUser find(final UUID uuid) {
        return userMap.get(uuid);
    }

    public void add(final MroznyStrozUser mroznyStrozUser) {
        userMap.put(mroznyStrozUser.getUuid(), mroznyStrozUser);
    }

    public ImmutableSet<MroznyStrozUser> getMroznyStrozUsers() {
        return ImmutableSet.copyOf(userMap.values());
    }

}
