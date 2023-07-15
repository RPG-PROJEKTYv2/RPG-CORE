package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.npc.gornik.objects.GornikUser;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

public class GornikZaplacCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public GornikZaplacCommand(final RPGCORE rpgcore) {
        super("gornikzaplac");
        this.rpgcore = rpgcore;
        this.setBlockedInNether(true);
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.GRACZ);
    }

    public void executeCommand(final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        if (args.length != 0) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cTo tak nie dziala"));
            return;
        }
        if (!rpgcore.getGornikNPC().isInPayList(player.getUniqueId())) {
            player.sendMessage(Utils.format("&6&lGornik &8» &cNie mozesz uzywac tej komendy!"));
            return;
        }
        if (!player.getLocation().getWorld().getName().equals("world")) {
            player.sendMessage(Utils.format("&6&lGornik &8» &cNie mozesz uzywac tej komendy poza spawnem!"));
            return;
        }

        final User user = rpgcore.getUserManager().find(player.getUniqueId());
        if (user.getKasa() < 1) { //250_000
            player.sendMessage(Utils.format("&6&lGornik &8» &cNie stac Cie, zeby zaplacic za te podroz!"));
            return;
        }
        user.setKasa(user.getKasa() - 1);
        final GornikUser gornikUser = rpgcore.getGornikNPC().find(player.getUniqueId());
        boolean isFullGornikArmor = true;
        long bonusTime = 0;
        for (final ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType().toString().contains("LEATHER") && armor.getItemMeta().hasDisplayName() && armor.getItemMeta().getDisplayName().contains("Gornika")) {
                bonusTime += Utils.getTagInt(armor, "bonusTime");
            } else {
                isFullGornikArmor = false;
            }
        }
        if (isFullGornikArmor) {
            bonusTime += 480_000;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 6_000, 0));
        }
        gornikUser.setTimeLeft(gornikUser.getMaxTimeLeft() + bonusTime);
        rpgcore.getGornikNPC().removeFromPayList(player.getUniqueId());
        rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
           rpgcore.getMongoManager().saveDataUser(user.getId(), user);
           rpgcore.getMongoManager().saveDataGornik(gornikUser.getUuid(), gornikUser);
        });
        player.teleport(new Location(Bukkit.getWorld("kopalnia"), -143.5, 76, -3.5, -45, 0));
    }
}
