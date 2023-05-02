package rpg.rpgcore.npc.pustelnik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.npc.pustelnik.enums.PustelnikMissions;
import rpg.rpgcore.npc.pustelnik.objects.PustelnikUser;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;
import rpg.rpgcore.utils.globalitems.expowiska.Bossy;

import java.util.*;

public class PustelnikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, PustelnikUser> userMap;

    public PustelnikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllPustelnik();
    }

    public PustelnikUser find(final UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void add(final PustelnikUser pustelnikUser) {
        this.userMap.put(pustelnikUser.getUuid(), pustelnikUser);
    }

    public void click(final Player player) {
        PustelnikUser user = this.find(player.getUniqueId());
        if (user == null) {
            user = new PustelnikUser(player.getUniqueId());
            this.add(user);
        }

        if (user.getMissionId() != 0) {
            this.openGUI(player);
            return;
        }

        if (!player.getInventory().containsAtLeast(Bossy.I3.getItemStack(), 1)) {
            player.sendMessage(Utils.format("&e&lPustelnik &8>> &fPrzynies mi moj &6Zwoj &f... wtedy pogadamy"));
            player.getInventory().addItem(Bossy.I3.getItemStack().clone());
            return;
        }

        player.getInventory().removeItem(new ItemBuilder(Bossy.I3.getItemStack().clone()).setAmount(1).toItemStack());
        final PustelnikMissions newMission = PustelnikMissions.getRandom();
        if (newMission == null) {
            player.sendMessage(Utils.format("&e&lPustelnik &8>> &fChyba &e&lKleopatra &fCi dzis sprzyja... Oto twoja nagroda!"));
            rpgcore.getServer().getScheduler().runTaskLater(rpgcore, () -> player.getInventory().addItem(Bossy.I3_1.getItemStack().clone()),1L);
            return;
        }
        user.setMissionId(newMission.getId());
        user.setProgress(0);
        this.save(user);
        this.openGUI(player);
    }

    public void openGUI(final Player player) {
        final PustelnikUser user = this.find(player.getUniqueId());
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&e&lPustelnik"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName("").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.PAPER).setName("&6Rozpiska").setLore(Arrays.asList(
                "&e&lPogromca Ptasznikow &8- &e30%",
                "&e&lPogromca Podziemia &8- &e20%",
                "&e&lCzempion Areny &8- &e15%",
                "&e&lDoswiadczenie Trzeba Zdobyc &8- &e10%",
                "&e&lSloneczny Patrol &8- &e25%"
        )).toItemStack());
        gui.setItem(2, Objects.requireNonNull(PustelnikMissions.getById(user.getMissionId())).getItem(user.getProgress()));
        gui.setItem(4, new ItemBuilder(Material.HOPPER).setName("&e&lBoskie Losowanie").setLore(Arrays.asList(
                "&7Nie pasuje Ci wylosowana misja?",
                "&7Nie ma problemu! Wylosuj ja jeszcze raz",
                "&7Koszt:",
                "  &8- &7x1 &e&lDar Kleopatry"
        )).addGlowing().toItemStack());

        player.openInventory(gui);
    }






    public void save(final PustelnikUser pustelnikUser) {
        this.rpgcore.getServer().getScheduler().runTaskAsynchronously(this.rpgcore, () -> this.rpgcore.getMongoManager().saveDataPustelnik(pustelnikUser.getUuid(), pustelnikUser));
    }

    public ImmutableSet<PustelnikUser> getPustelnikUsers() {
        return ImmutableSet.copyOf(this.userMap.values());
    }
}
