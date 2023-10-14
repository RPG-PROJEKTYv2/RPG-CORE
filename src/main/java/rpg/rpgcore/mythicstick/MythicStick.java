package rpg.rpgcore.mythicstick;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.commands.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;

public class MythicStick extends CommandAPI {

    public MythicStick() {
        super("mythicstick");
        this.setAliases(Arrays.asList("ms"));
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(true);
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length == 0 || args.length == 1) {
            player.sendMessage(Utils.poprawneUzycie("mythicstick <nazwa> <leashrange>"));
            return;
        }
        if (args.length == 2) {
            player.getInventory().addItem(new ItemBuilder(Material.STICK).setName("&6&lMythic &4&lSTICK").setLore(Arrays.asList("&7Aktualnie ustawiasz spawner mobow: &c" + args[0], "&7Nazwa Spawnera: &c" + args[0] + "-RESP-0", "&7Leashrange: &c" + args[1])).toItemStack().clone());
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("mythicstick <nazwa>"));
    }
}
