package rpg.rpgcore.commands.admin.ustawieniakonta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class UstawieniaKontaManager {
    private final RPGCORE rpgcore;

    public UstawieniaKontaManager(final RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private int countPlayerItems(final ItemStack[] components) {
        int count = 0;
        for (final ItemStack item : components) {
            if (item == null || item.getType() == Material.AIR) continue;
            count++;
        }
        return count;
    }

    public void openUstawieniaKonta(final Player player, final User target) {
        final Inventory gui = Bukkit.createInventory(null, 27, Utils.format("&8&lKonto &c&l- &6&l" + target.getId().toString()));
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack());
        }

        int playerItemsCount;
        int playerEnderchestItemsCount;
        try {
            playerItemsCount = countPlayerItems(Utils.itemStackArrayFromBase64(target.getInventoriesUser().getInventory()));
            playerEnderchestItemsCount = countPlayerItems(Utils.itemStackArrayFromBase64(target.getInventoriesUser().getEnderchest()));
        } catch (final IOException e) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cWystapil blad podczas odczytywania ekwipunku gracza &e" + target.getName()));
            return;
        }

        gui.setItem(10, new ItemBuilder(Material.DIAMOND).setName("&cWyczysc &6Ekwipunek &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &6" + playerItemsCount + " &cprzedmiotow",
                "&7z &6Ekwipunku &cgracza &e" + target.getName() + " &7zostanie usunietych.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(11, new ItemBuilder(Material.EMERALD).setName("&cWyczysc &5Enderchest &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &6" + playerEnderchestItemsCount + " &cprzedmiotow",
                "&7z &5Enderchestu &cgracza &e" + target.getName() + " &7zostanie usunietych.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(12, new ItemBuilder(Material.CHEST).setName("&cWyczysc &6Magazyn &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &cprzedmioty &7z wybranego",
                "&6Magazynu &cgracza &e" + target.getName() + " &7zostana usuniete.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(13, new ItemBuilder(Material.GRASS).setName("&cResetuj &bOsiagniecia &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Uzywajac tego sprawisz, ze &cwybrane &7osiagniecia",
                "&cgracza &e" + target.getName() + " &7zostana zresetowane.",
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(14, new ItemBuilder(Material.BOOK).setName("&cUstaw/Resetuj &aMisje &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &aMisji &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(15, new ItemBuilder(Material.EXP_BOTTLE).setName("&cUstaw &ePoziom &ci EXP'a &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &ePoziomu &7i EXP'a &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(16, new ItemBuilder(Material.PAPER).setName("&cUstaw &2&l$ &ci &4&lH&6&lS &cgracza &e" + target.getName()).setLore(Arrays.asList(
                "&7Otwiera GUI do ustawiania &2&l$ &7i &4&lH&6&lS &7gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());

        gui.setItem(26, new ItemBuilder(Material.BEDROCK).setName("&4&lUSUN KONTO GRACZA &6&l" + target.getName()).setLore(Arrays.asList(
                "&7Usuwa calkowicie i nie odwracalnie konto gracza &e" + target.getName(),
                "",
                "&4&lPAMIETAJ !!! &cTa operacja jest nieodwracalna i moze popsuc komus rozgrywke",
                "&cna serwerze. &4&lUZYWAC TYLKO W UZASADNIONYCH PRZYPADKACH !!!"
        )).addGlowing().toItemStack().clone());


        player.openInventory(gui);
    }
}
