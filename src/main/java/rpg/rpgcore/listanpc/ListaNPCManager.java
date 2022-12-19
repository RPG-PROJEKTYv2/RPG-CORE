package rpg.rpgcore.listanpc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ListaNPCManager {
    private final RPGCORE rpgcore;
    private final Map<Integer, NpcObject> npcMap;
    public ListaNPCManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
        this.npcMap = this.loadAll();
    }

    public Map<Integer, NpcObject> loadAll() {
        final Map<Integer, NpcObject> toLoad = new HashMap<>();
        int i = 1;
        for (String s : rpgcore.getConfig().getConfigurationSection("ListaNPC").getKeys(false)) {
            toLoad.put(i, new NpcObject(s, new Location(
                    Bukkit.getWorld(rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getString("world")),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getDouble("x"),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getDouble("y"),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getDouble("z"),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getLong("yaw"),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getLong("pitch")
                    ), rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getString("Type"),
                    rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s).getInt("ReqLvl")));
            i++;
        }


        return toLoad;
    }

    public void openMainGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&cLista NPC"));
        final int lvl = rpgcore.getUserManager().find(player.getUniqueId()).getLvl();
        for (int i = 0; i < npcMap.size(); i++) {
            gui.setItem(i, this.makeNpcItem(this.npcMap.get(i+1), lvl));
        }

        player.openInventory(gui);
    }

    private ItemStack makeNpcItem(final NpcObject npc, final int lvl) {
        short durability = 14;
        if (lvl >= npc.getReqLvl()) {
            durability = 5;
        }
        return new ItemBuilder(Material.STAINED_GLASS_PANE, 1, durability).setName("&6" + npc.getNpcName()).setLore(Arrays.asList(
                "&7Lokalizacja:",
                "&7x: &6" + npc.getLocation().getX(),
                "&7y: &6" + npc.getLocation().getY(),
                "&7z: &6" + npc.getLocation().getZ(),
                "",
                "&7Typ Npc: &c" + npc.getNpcType()
                )).addGlowing().toItemStack().clone();
    }

    public NpcObject find(final int i) {
        return this.npcMap.get(i);
    }
}
