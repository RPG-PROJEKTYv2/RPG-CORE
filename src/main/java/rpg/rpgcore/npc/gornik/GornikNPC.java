package rpg.rpgcore.npc.gornik;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GornikNPC {
    private final RPGCORE rpgcore;
    private final Map<UUID, GornikObject> userMap;
    private final Map<Integer, String> missionMap = new HashMap<>(40);

    public GornikNPC(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllGornik();
        this.loadMissions();
    }

    public void loadMissions() {

    }

    public void onClick(final Player player) {
        final UUID uuid = player.getUniqueId();
        if (!this.userMap.containsKey(uuid)) {
            this.userMap.put(uuid, new GornikObject(uuid));
        }

        if (rpgcore.getUserManager().find(uuid).getLvl() < 75) {
            player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Musisz zdobyc jescze troche doswiadczenia aby uzyskac dostep do mojej kopalni."));
            return;
        }

        player.teleport(new Location(Bukkit.getWorld("kopalnia"), 0, 4, 0));
        player.sendMessage(Utils.format("&6&lGornik &8&l>> &7Witaj w mojej kopalni. Mam nadzieje, ze zostaniesz tu na dluzej."));
    }

    public void openGornikGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final GornikUser user = this.find(uuid).getGornikUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lGornik"));

        gui.setItem(0, new ItemBuilder(Material.BOOK).setName("&cKampania Gornika").addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }


    public void add(GornikObject gornikObject) {
        this.userMap.put(gornikObject.getID(), gornikObject);
    }

    public GornikObject find(final UUID uuid) {
        this.userMap.computeIfAbsent(uuid, k -> new GornikObject(uuid));
        return this.userMap.get(uuid);
    }

    public ImmutableSet<GornikObject> getGornikObject() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

    public boolean isGornikObject(final UUID string) {
        return this.userMap.containsKey(string);
    }
}
