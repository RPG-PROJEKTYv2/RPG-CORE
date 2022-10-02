package rpg.rpgcore.akcesoria;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AkcesoriaManager {

    private final RPGCORE rpgcore;
    private final Map<UUID, AkcesoriaObject> userMap;

    private final Map<UUID, Inventory> akcesoriaMap = new HashMap<>();

    public AkcesoriaManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.userMap = rpgcore.getMongoManager().loadAllAkcesoria();
    }

    public void openAkcesoriaGUI(final Player player) {
        final UUID uuid = player.getUniqueId();
        final AkcesoriaUser user = rpgcore.getAkcesoriaManager().find(uuid).getAkcesoriaUser();
        final Inventory gui = Bukkit.createInventory(null, 9, Utils.format("&6&lAkcesoria"));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        if (user.getTarcza().length() > 0) {
            gui.setItem(1, Utils.deserializeItem(user.getTarcza()));
        } else {
            gui.setItem(1, this.noAkcesoriaItem("Tarczy"));
        }
        if (user.getMedalion().length() > 0) {
            gui.setItem(2, Utils.deserializeItem(user.getMedalion()));
        } else {
            gui.setItem(2, this.noAkcesoriaItem("Medalionu"));
        }
        if (user.getPas().length() > 0) {
            gui.setItem(3, Utils.deserializeItem(user.getPas()));
        } else {
            gui.setItem(3, this.noAkcesoriaItem("Pasa"));
        }
        if (user.getKolczyki().length() > 0) {
            gui.setItem(4, Utils.deserializeItem(user.getKolczyki()));
        } else {
            gui.setItem(4, this.noAkcesoriaItem("Kolczykow"));
        }
        if (user.getSygnet().length() > 0) {
            gui.setItem(5, Utils.deserializeItem(user.getSygnet()));
        } else {
            gui.setItem(5, this.noAkcesoriaItem("Syngetu"));
        }
        if (user.getEnergia().length() > 0) {
            gui.setItem(6, Utils.deserializeItem(user.getEnergia()));
        } else {
            gui.setItem(6, this.noAkcesoriaItem("Energii"));
        }
        if (user.getDiadem().length() > 0) {
            gui.setItem(7, Utils.deserializeItem(user.getDiadem()));
        } else {
            gui.setItem(7, this.noAkcesoriaItem("Diademu"));
        }

        player.openInventory(gui);
    }


    public final ItemStack noAkcesoriaItem(final String nazwaAkcesorium) {
        return new ItemBuilder(Material.IRON_FENCE).setName("&c&lBrak " + nazwaAkcesorium).addGlowing().toItemStack().clone();
    }


    public AkcesoriaObject find(final UUID uuid) {
        userMap.computeIfAbsent(uuid,k -> new AkcesoriaObject(uuid));
        return this.userMap.get(uuid);
    }

    public void add(final AkcesoriaObject akcesoriaObject) {
        this.userMap.put(akcesoriaObject.getId(), akcesoriaObject);
    }

    public ImmutableSet<AkcesoriaObject> getAkcesoriaObjects() {
        return ImmutableSet.copyOf(this.userMap.values());
    }

}
