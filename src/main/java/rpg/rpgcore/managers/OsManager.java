package rpg.rpgcore.managers;

import com.sun.org.apache.bcel.internal.generic.RET;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;

public class OsManager {

    private Inventory gui;
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private ArrayList<String> itemLore = new ArrayList<>();

    private final RPGCORE rpgcore;

    public OsManager(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public Inventory osGuiMain() {
        this.gui = Bukkit.createInventory(null, 3*9, Utils.format("&6&lOsiagniecia"));

        this.itemStack = new ItemStack(Material.GOLD_SWORD);
        this.itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName("");



        return gui;
    }


}
