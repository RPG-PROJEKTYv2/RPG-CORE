package rpg.rpgcore.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class SprawdzMojeBonusy implements CommandExecutor {

    private final RPGCORE rpgcore;

    public SprawdzMojeBonusy(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;


        if (args.length == 0){

            player.sendMessage(Utils.format(this.createMessage(player.getUniqueId())));

            return false;
        }

        player.sendMessage(Utils.poprawneUzycie("sprawdzmojebonusy"));
        return false;
    }


    private String createMessage(final UUID uuid) {
        return "&8--------------->> &6Twoje Bonusy &8<<---------------" +
                               "\n&7Nick: &6" + rpgcore.getPlayerManager().getPlayerName(uuid) +
                               "\n&7Ranga: &6" + rpgcore.getPlayerManager().getPlayerGroup(Bukkit.getPlayer(uuid)) +
                               "\n&7Srednie Obrazenia: &6" + rpgcore.getPlayerManager().getPlayerSrednie(uuid) +
                               "\n&7Minusowe Srednie Obrazenia: &6- " + rpgcore.getPlayerManager().getPlayerMinusSrednie(uuid) +
                               "\n&7Srednia Defensywa: &6" + rpgcore.getPlayerManager().getPlayerDef(uuid) +
                               "\n&7Minusowa Srednia Defensywa: &6- " + rpgcore.getPlayerManager().getPlayerMinusDef(uuid) +
                               "\n&7Silny na Ludzi: &6" + rpgcore.getPlayerManager().getPlayerSilnyNaLudzi(uuid) +
                               "\n&7Defensywa na Ludzi: &6" + rpgcore.getPlayerManager().getPlayerDefNaLudzi(uuid) +
                               "\n&7Dodatkowe Obrazenia: &6" + rpgcore.getPlayerManager().getPlayerDamage(uuid) +
                               "\n&7Blok Ciosu: &6" + rpgcore.getPlayerManager().getPlayerBlok(uuid) +
                               "\n&7Cios krytyczny: &6" + rpgcore.getPlayerManager().getPlayerKryt(uuid) +
                               "\n&7Przeszycie Bloku: &6" + rpgcore.getPlayerManager().getPlayerPrzeszywka(uuid) +
                               "\n&7Dodatkowe HP: &6" + rpgcore.getPlayerManager().getPlayerHP(uuid) +
                               "\n&8--------------->> &6Twoje Bonusy &8<<---------------";
    }
}
