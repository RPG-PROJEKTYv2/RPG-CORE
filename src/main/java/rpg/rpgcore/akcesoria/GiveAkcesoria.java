package rpg.rpgcore.akcesoria;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.discord.EmbedUtil;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.AkcesoriaHelper;
import rpg.rpgcore.utils.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class GiveAkcesoria extends CommandAPI {

    public GiveAkcesoria() {
        super("giveakcesoria");
        this.setRankLevel(RankType.HA);
        this.setAliases(Arrays.asList("gakce", "giveakce", "dajakce"));
    }
    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        if (args.length < 5 || args[0] == null || args[1] == null || args[2] == null || args[3] == null || args[4] == null) {
            return;
        }

        final Player player = (Player) sender;
        ItemStack item;
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "tarcza":
                item = AkcesoriaHelper.createTarcza(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                break;
            case "medalion":
                item = AkcesoriaHelper.createMedalion(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case "pas":
                item = AkcesoriaHelper.createPas(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case "kolczyki":
                item = AkcesoriaHelper.createKolczyki(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case "sygnet":
                item = AkcesoriaHelper.createSygnet(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            case "energia":
                item = AkcesoriaHelper.createEnergia(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                break;
            case "zegarek":
                item = AkcesoriaHelper.createZegarek(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
                break;
            default:
                return;
        }
        player.getInventory().addItem(item);
        RPGCORE.getDiscordBot().sendChannelMessage("admin-akcesoria-logs", (new MessageBuilder()).setEmbeds(new MessageEmbed[]{EmbedUtil.create("**Stworzono Akcesorium**",
                "**Przez: **`" + player.getName() + "`\n**Item: **" + item.getItemMeta() + "\n", Color.red).build()}).build());

    }
}
