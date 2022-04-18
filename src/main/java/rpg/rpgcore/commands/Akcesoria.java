package rpg.rpgcore.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Akcesoria implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Akcesoria(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private ItemBuilder testTarcza = new ItemBuilder(Material.ITEM_FRAME, 1);
    private ItemBuilder testNaszyjnik = new ItemBuilder(Material.STORAGE_MINECART, 1);
    private ItemBuilder testBransoleta = new ItemBuilder(Material.POWERED_MINECART, 1);
    private ItemBuilder testKolczyki = new ItemBuilder(Material.HOPPER_MINECART, 1);
    private ItemBuilder testPierscien = new ItemBuilder(Material.EXPLOSIVE_MINECART, 1);
    private ItemBuilder testEnergia = new ItemBuilder(Material.MINECART, 1);
    private ItemBuilder testZegarek = new ItemBuilder(Material.WATCH, 1);

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            if (!(rpgcore.getAkcesoriaManager().getAkcesoriaMap().containsKey(uuid))) {
                rpgcore.getAkcesoriaManager().createAkcesoriaGUINew(uuid);
            }
            player.openInventory(rpgcore.getAkcesoriaManager().getAkcesoriaGUI(uuid));
            this.dajTestAkce(player);
            return false;
        }


        return false;
    }

    private void dajTestAkce(final Player player) {
        List<String> testlore = new ArrayList<>();

        testTarcza.setName("&7&lTestowa Tarcza");
        testlore.add("&3Obrona: &f45%");
        testlore.add("&3Blok Ciosu: &f25%");
        testlore.add("&3Obrazenia: &f120");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &640");
        testTarcza.setLore(testlore);

        testlore.clear();
        testNaszyjnik.setName("&7&lTestowy Naszyjnik");
        testlore.add("&3Przeszycie Bloku: &f25%");
        testlore.add("&3Obrazenia: &f1200");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &660");
        testNaszyjnik.setLore(testlore);

        testlore.clear();
        testBransoleta.setName("&7&lTestowa Bransoleta");
        testlore.add("&3Cios Krytyczny: &f25%");
        testlore.add("&3Srednie Obrazenia: &f50%");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &670");
        testBransoleta.setLore(testlore);

        testlore.clear();
        testKolczyki.setName("&7&lTestowe Kolczyki");
        testlore.add("&3Dodatkowe HP: &f5");
        testlore.add("&3Silny przeciwko Ludziom: &f35%");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &645");
        testKolczyki.setLore(testlore);

        testlore.clear();
        testPierscien.setName("&7&lTestowy Pierscien");
        testlore.add("&3Blok Ciosu: &f25%");
        testlore.add("&3Dodatkowe HP: &f20");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &680");
        testPierscien.setLore(testlore);

        testlore.clear();
        testEnergia.setName("&7&lTestowa Energia");
        testlore.add("&3Obrona: &f50%");
        testlore.add("&3Blok Ciosu: &f30%");
        testlore.add("&3Srednie Obrazenia: &f-25%");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &655");
        testEnergia.setLore(testlore);

        testlore.clear();
        testZegarek.setName("&7&lTestowy Zegarek");
        testlore.add("&3Dodatkowe Obrazenia: &f750");
        testlore.add("&3Silny przeciwko Ludziom: &f30%");
        testlore.add("&3Obrona: &f-25%");
        testlore.add(" ");
        testlore.add("&c&oWymagany Poziom: &655");
        testZegarek.setLore(testlore);

        player.getInventory().addItem(testTarcza.toItemStack(), testNaszyjnik.toItemStack(), testBransoleta.toItemStack(), testKolczyki.toItemStack(), testPierscien.toItemStack(), testEnergia.toItemStack(), testZegarek.toItemStack());
    }
}
