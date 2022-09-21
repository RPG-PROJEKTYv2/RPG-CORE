package rpg.rpgcore.npc.lesnik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class LesnikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, LesnikObject> userMap;

    public LesnikNPC(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllLesnik();
    }

    public void openLesnikGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final LesnikUser user = this.find(uuid).getUser();
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&2&lLesnik"));

        gui.setItem(10, new ItemBuilder(Material.PAPER).setName("&c&lStatystyki").setLore(Arrays.asList(
                "&7Przeszycie Bloku Ciosu: &c" + user.getPrzeszycie() + "&7%",
                "&7Szansa Na Wzocnienie Ciosu Krytycznego: &c" + user.getWzmKryta() + "&7%",
                "&7Defensywa Przeciwko Ludziom: &c" + user.getDefNaLudzi() + "&7%"
        )).toItemStack().clone());

        gui.setItem(13, this.getCurrentMissionItem(user));
        if (user.hasCooldown()) {
            gui.setItem(16, new ItemBuilder(Material.WATCH).setName("&cOdczekaj jeszcze &6" + Utils.durationToString(user.getCooldown(), false)).addGlowing().toItemStack().clone());
        } else {
            gui.setItem(16, new ItemBuilder(Material.WATCH).setName("&aGotowe do oddania!").toItemStack().clone());
        }

        gui.setItem(22, new ItemBuilder(Material.BARRIER).setName("&cBrak Potki Lesnika").toItemStack().clone());

        player.openInventory(gui);
    }



    private ItemStack getCurrentMissionItem(final LesnikUser lesnikUser) {
        final LesnikMissions mission = LesnikMissions.getByNumber(lesnikUser.getMission());
        return new ItemBuilder(mission.getReqItem().getType(), mission.getReqItem().getAmount(), mission.getReqItem().getDurability()).setName("&c&lMisja " + lesnikUser.getMission()).setLore(Arrays.asList(
                "&7Przynies mi jeszcze &c" + (mission.getReqAmount() - lesnikUser.getProgress()) + " &7" + mission.getReqItem().getItemMeta().getDisplayName(),
                "",
                "&f&lNagroda:",
                "&7Przeszycie Bloku Ciosu: &c" + mission.getPrzeszywka() + "&7%",
                "&7Szansa Na Wzocnienie Ciosu Krytycznego: &c" + mission.getWzmKryta() + "&7%",
                "&7Defensywa Przeciwko Ludziom: &c" + mission.getDefNaLudzi() + "&7%"
        )).toItemStack().clone();
    }


    public LesnikObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid, k -> new LesnikObject(uuid));
        return userMap.get(uuid);
    }

    public void add(final LesnikObject lesnikObject) {
        userMap.put(lesnikObject.getId(), lesnikObject);
    }

    public ImmutableSet<LesnikObject> getLesnikObjects() {
        return ImmutableSet.copyOf(userMap.values());
    }
}
