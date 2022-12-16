package rpg.rpgcore.commands.player.bossy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;


public class BossyCommand extends CommandAPI {

    public BossyCommand() {
        super("bossy");
        this.setRankLevel(RankType.GRACZ);
        this.setRestrictedForPlayer(true);
    }
    
    
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length > 0) {
            player.sendMessage(Utils.poprawneUzycie("bossy"));
            return;
        }
        this.openBossyInventory(player);
    }

    private void openBossyInventory(final Player player) {
        final Inventory gui = Bukkit.createInventory(null, 18, Utils.format("&3&lLista bossów"));

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
        }
        gui.setItem(0, new ItemBuilder(Material.PAPER, 1).setName("&9&lKról Wygnańców").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 1", "&8* &f&lResp: &6-26 &e/ &615 &e/ &6204", "&8✪ &f&lCzas: &62 minuty.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(1, new ItemBuilder(Material.PAPER, 1).setName("&a&lWodz Goblinow").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 2", "&8* &f&lResp: &6206 &e/ &617 &e/ &6-136", "&8✪ &f&lCzas: &65 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(2, new ItemBuilder(Material.PAPER, 1).setName("&f&lKrol Goryli").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 3", "&8* &f&lResp: &6-107 &e/ &611 &e/ &680", "&8✪ &f&lCzas: &610 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(3, new ItemBuilder(Material.PAPER, 1).setName("&7&lPrzekleta Dusza").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 4", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &615 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(4, new ItemBuilder(Material.PAPER, 1).setName("&e&lTryton").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 5", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &620 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(5, new ItemBuilder(Material.PAPER, 1).setName("&f&lMityczny Lodowy Golem").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 6", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &625 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(6, new ItemBuilder(Material.PAPER, 1).setName("&1&lPiekielny Rycerz").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 7", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &630 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(7, new ItemBuilder(Material.PAPER, 1).setName("&5&lPrzeklety Czarnoksieznik").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 8", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &635 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(8, new ItemBuilder(Material.PAPER, 1).setName("&e&lMityczny Pajak").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 9", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &640 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(9, new ItemBuilder(Material.PAPER, 1).setName("BRAK").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 10", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &645 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(10, new ItemBuilder(Material.PAPER, 1).setName("BRAK").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 11", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &650 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        gui.setItem(11, new ItemBuilder(Material.PAPER, 1).setName("BRAK").setLore(Arrays.asList(" ", "&8* &f&lMapa: &6Expowisko 12", "&8* &f&lResp: &6x , y , z", "&8✪ &f&lCzas: &655 minut.", " ", "&8✟ &cData zabicia: &f&l10.06.2022 10:54")).addGlowing().toItemStack().clone());

        player.openInventory(gui);
    }
}
