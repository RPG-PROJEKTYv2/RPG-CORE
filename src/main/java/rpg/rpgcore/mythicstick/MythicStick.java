package rpg.rpgcore.mythicstick;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.ItemBuilder;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

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
        if (args.length == 1) {
            player.getInventory().addItem(new ItemBuilder(Material.STICK).setName("&6&lMythic &4&lSTICK").setLore(Arrays.asList("&7Aktualnie ustawiasz spawner mobow: &c" + args[0], "&7Nazwa Spawnera: &c" + args[0] + "-RESP-0")).toItemStack().clone());
            return;
        }
        player.sendMessage(Utils.poprawneUzycie("mythicstick <nazwa>"));
    }
}
