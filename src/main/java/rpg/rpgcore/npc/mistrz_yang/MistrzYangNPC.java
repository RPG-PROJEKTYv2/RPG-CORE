package rpg.rpgcore.npc.mistrz_yang;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.mistrz_yang.enums.MistrzYangMissions;
import rpg.rpgcore.npc.mistrz_yang.objects.MistrzYangUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.Map;
import java.util.UUID;

public class MistrzYangNPC {
    private final Map<UUID, MistrzYangUser> userMap;

    public MistrzYangNPC(final RPGCORE rpgcore) {
        this.userMap = rpgcore.getMongoManager().loadAllMistrzYang();
    }

    public void click(final Player player) {
        MistrzYangUser user = this.find(player.getUniqueId());
        if (user == null) {
            user = new MistrzYangUser(player.getUniqueId());
            this.add(user);
        }

        if (user.getMissionId() != 0) {
            this.openGUI(player);
            return;
        }

        if (!player.getInventory().containsAtLeast(Bossy.I5.getItemStack(), 1)) {
            player.sendMessage(Utils.format("&c&lMistrz Yang &8>> &fOwszem, mam dla ciebie zadanie, ale nie mam gdzie go zapisac..."));
            player.getInventory().addItem(Bossy.I3.getItemStack().clone());
            return;
        }

        player.getInventory().removeItem(new ItemBuilder(Bossy.I5.getItemStack().clone()).setAmount(1).toItemStack());
        final MistrzYangMissions newMission = MistrzYangMissions.getRandom();
        user.setMissionId(newMission.getId());
        user.setReqAmount(newMission.getRandomAmount(newMission.getId()));
        user.setProgress(0);
        this.save(user);
        this.openGUI(player);
    }


    public void openGUI(final Player player) {
        final MistrzYangUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, InventoryType.DISPENSER, Utils.format("&c&lMistrz Yang"));
        for (int i = 0; i < gui.getSize(); i++) {
            if (i % 2 == 0) {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName(" ").toItemStack());
            } else {
                gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
            }
        }
        final MistrzYangMissions mission = MistrzYangMissions.getById(user.getMissionId());
        assert mission != null;
        gui.setItem(4, mission.getItem(user.getReqAmount(), user.getProgress()));

        player.openInventory(gui);
    }







    public MistrzYangUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final MistrzYangUser user) {
        this.userMap.put(user.getUuid(), user);
    }

    public void save(final MistrzYangUser user) {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveDataMistrzYang(user.getUuid(), user));
    }

    public ImmutableSet<MistrzYangUser> getMistrzYangUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
