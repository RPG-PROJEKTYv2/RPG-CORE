package rpg.rpgcore.commands.player.rangi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class RangiCommand extends CommandAPI {
    public RangiCommand() {
        super("rangi");
        this.setAliases(Arrays.asList("vip", "svip", "elita"));
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("rangi"));
            return;
        }
        this.openRangiInventory(player);
    }

    private void openRangiInventory(Player player) {
        final Inventory gui = Bukkit.createInventory(null, InventoryType.BREWING, Utils.format("&cRangi"));


        gui.setItem(0, new ItemBuilder(Material.BOOK).setName("&e&lVip").setLore(Arrays.asList(" ", "&7Ranga ta posiada:", "&7* &e15% dodatkowego expa", " ")).addGlowing().toItemStack().clone());
        gui.setItem(1, new ItemBuilder(Material.BOOK).setName("&6&lS&e&lvip").setLore(Arrays.asList(" ", "&7Ranga ta posiada:", "&7* &e25% dodatkowego expa", " ")).addGlowing().toItemStack().clone());
        gui.setItem(2, new ItemBuilder(Material.BOOK).setName("&5&lELITA").setLore(Arrays.asList(" ", "&7Ranga ta posiada:", "&7* &e30% dodatkowego expa", " ")).addGlowing().toItemStack().clone());
        gui.setItem(3, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("&c&lInformacje").setLore(Arrays.asList(" ", "&7Wszystkie rangi mozna zakupic na discordzie:", "&7* &edc.hellrpg.pl", "&7lub na naszej stronie", "&7* &ewww.hellrpg.pl")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
