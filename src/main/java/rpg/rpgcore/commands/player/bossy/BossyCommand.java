package rpg.rpgcore.commands.player.bossy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.util.Arrays;
import java.util.UUID;


public class BossyCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public BossyCommand(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    private final ItemBuilder paper = new ItemBuilder(Material.PAPER, 1);

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;
        final UUID uuid = player.getUniqueId();

        if (!(player.hasPermission("rpg.bossy"))) {
            player.sendMessage(Utils.permisje("rpg.bossy"));
            return false;
        }

        if (args.length == 0) {
            this.openBossyInventory(player);
            return false;
        }
        player.sendMessage(Utils.poprawneUzycie("bossy"));
        return false;
    }

    private void openBossyInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18,  Utils.format("&3&lLista bossów"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }

        gui.setItem(0, paper.setName("&9&lKról Wygnańców").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 1","&8* &f&lResp: &6-26 &e/ &615 &e/ &6204","&8✪ &f&lCzas: &62 minuty."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(1, paper.setName("&2&lKról Elendil").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 2","&8* &f&lResp: &6206 &e/ &617 &e/ &6-136","&8✪ &f&lCzas: &65 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(2, paper.setName("&e&lKról Lew").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 3","&8* &f&lResp: &6-107 &e/ &611 &e/ &680","&8✪ &f&lCzas: &610 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(3, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 4","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &615 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(4, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 5","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &620 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(5, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 6","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &625 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(6, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 7","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &630 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(7, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 8","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &635 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(8, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 9","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &640 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(9, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 10","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &645 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(10, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 11","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &650 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());
        gui.setItem(11, paper.setName("&4&lBOSS").setLore(Arrays.asList(" ","&8* &f&lMapa: &6Expowisko 12","&8* &f&lResp: &6x , y , z","&8✪ &f&lCzas: &655 minut."," ","&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
