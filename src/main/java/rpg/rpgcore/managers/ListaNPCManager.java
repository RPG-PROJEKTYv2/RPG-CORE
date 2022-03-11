package rpg.rpgcore.managers;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.utils.Utils;

import java.io.File;
import java.util.Set;

public class ListaNPCManager {
    File citizensSavesFile = new File(CitizensAPI.getDataFolder().getPath(), "saves.yml");
    FileConfiguration conf = YamlConfiguration.loadConfiguration(citizensSavesFile);
    private ItemStack itemToGUI = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
    private ItemMeta meta = itemToGUI.getItemMeta();
    private Inventory listaNpcGUI = Bukkit.createInventory(null, 2*9, Utils.format("&4&lLista NPC"));

    public void createItemsForGUI(){
        Set<String> listOfNpc = conf.getConfigurationSection("npc").getKeys(false);
        for (int i = 0; i <= listOfNpc.size(); i++){

        }
    }

}
