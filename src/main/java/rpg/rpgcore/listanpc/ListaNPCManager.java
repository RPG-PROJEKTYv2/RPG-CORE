package rpg.rpgcore.listanpc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
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
            final ConfigurationSection section = rpgcore.getConfig().getConfigurationSection("ListaNPC").getConfigurationSection(s);
            final Material material = Material.getMaterial(section.getConfigurationSection("Item").getString("Material").toUpperCase(Locale.ROOT));
            final ItemStack guiItem = (section.getConfigurationSection("Item").getInt("Short") == 0 ? new ItemStack(material, 1) : new ItemStack(material, 1, (short) section.getConfigurationSection("Item").getInt("Short")));

            toLoad.put(i, new NpcObject(s, new Location(
                    Bukkit.getWorld(section.getString("world")),
                    section.getDouble("x"),
                    section.getDouble("y"),
                    section.getDouble("z"),
                    section.getLong("yaw"),
                    section.getLong("pitch")), section.getString("Type"), section.getInt("ReqLvl"), guiItem));
            i++;
        }


        return toLoad;
    }

    public void openMainGUI(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&cLista NPC"));
        final int lvl = rpgcore.getUserManager().find(player.getUniqueId()).getLvl();
        for (int i = 0; i < npcMap.size(); i++) {
            gui.setItem(i, this.makeNpcItem(this.npcMap.get(i + 1), lvl));
        }

        player.openInventory(gui);
    }

    private ItemStack makeNpcItem(final NpcObject npc, final int lvl) {
        return (lvl >= npc.getReqLvl() ?
                new ItemBuilder(npc.getGuiItem()).setName("&6" + npc.getNpcName()).setLore(Arrays.asList(
                        "&7Lokalizacja:",
                        "&7x: &6" + npc.getLocation().getX(),
                        "&7y: &6" + npc.getLocation().getY(),
                        "&7z: &6" + npc.getLocation().getZ(),
                        "",
                        "&7Typ Npc: &c" + npc.getNpcType()
                )).addGlowing().toItemStack().clone()
                :
                new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 14).setName("&6" + npc.getNpcName()).setLore(Arrays.asList(
                        "&7Lokalizacja:",
                        "&7x: &6" + npc.getLocation().getX(),
                        "&7y: &6" + npc.getLocation().getY(),
                        "&7z: &6" + npc.getLocation().getZ(),
                        "",
                        "&7Typ Npc: &c" + npc.getNpcType()
                )).addGlowing().toItemStack().clone());
    }

    public NpcObject find(final int i) {
        return this.npcMap.get(i);
    }
}
