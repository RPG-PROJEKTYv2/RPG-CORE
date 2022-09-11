package rpg.rpgcore.klasy.choice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class KlasaCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public KlasaCommand(RPGCORE rpgcore) {
        super("klasa");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
        this.rpgcore = rpgcore;
    }
    
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("klasa"));
            return;
        }

        if (rpgcore.getklasyHelper().find(uuid).getKlasaUser() != null && !rpgcore.getklasyHelper().find(uuid).getKlasaUser().getName().equals("")) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWybrales juz swoja klase: &6" + rpgcore.getklasyHelper().find(uuid).getKlasaUser().getName()));
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&7Karte resetu klasy mozesz zakupic w naszym itemshopie www.hellrpg.pl"));
            rpgcore.getklasyHelper().reset(uuid);
            return;
        }
        this.openKlasyInventory(player);
    }

    private void openKlasyInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, Utils.format("&4&lKlasy"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("&4&lWojownik").setLore(Arrays.asList("&cOrzel/Fabi Ma wymyslic zajebisty lore")).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("&a&lObronca").setLore(Arrays.asList("&cOrzel/Fabi Ma wymyslic zajebisty lore")).addGlowing().toItemStack().clone());
        gui.setItem(4, new ItemBuilder(Material.RED_ROSE, 1, (short) 2).setName("&5&lMag").setLore(Arrays.asList("&cOrzel/Fabi Ma wymyslic zajebisty lore")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
