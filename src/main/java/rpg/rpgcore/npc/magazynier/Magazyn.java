package rpg.rpgcore.npc.magazynier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.util.UUID;

public class Magazyn implements CommandExecutor {

    private final RPGCORE rpgcore;

    public Magazyn(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("rpg.magazyn")) {
            player.sendMessage(Utils.permisje("rpg.magazyn"));
            return false;
        }

        final UUID uuid = player.getUniqueId();
        final String playerGroup = rpgcore.getUserManager().getPlayerGroup(player);



        if (args.length == 1) {

            if (player.getName().equals(rpgcore.getMagazynierNPC().getArtifactOwner())) {
                try {
                    final int nrMagazynu = Integer.parseInt(args[0]);

                    if (nrMagazynu > 5) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
                        return false;
                    }
                    rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
                    return false;
                } catch (final NumberFormatException e) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
                    return false;
                }
            }

            if (playerGroup.equalsIgnoreCase("gracz") || playerGroup.equalsIgnoreCase("vip")) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz posiadac minimum &6&lS&e&lvip'a &club &4&lArtefakt &b&lMagazyniera&c, zeby moc zdalnie otworzyc magazyn"));
                return false;
            }

            try {
                final int nrMagazynu = Integer.parseInt(args[0]);

                if (nrMagazynu > 5) {
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
                    return false;
                }
                rpgcore.getMagazynierNPC().openMagazynierMagazyny(player);
                return false;
            } catch (final NumberFormatException e) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMusisz podac numer od 1 do 5"));
                return false;
            }
        }


        if (args.length == 2 && args[0].equalsIgnoreCase("artefakt")) {
            if (!player.hasPermission("admin.rpg.magazyn.artefakt")) {
                player.sendMessage(Utils.poprawneUzycie("magazyn <nr magazynu>"));
                return false;
            }

            player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie dodano &4&lArtefakt &b&lMagazyniera &az wlascicielem &6" + args[1]));
            rpgcore.getMagazynierNPC().setArtifactOwner(args[1]);
            // TODO rpgcore.getMongoManager().updateMagazynierNPCArtifactOwner(rpgcore.getMagazynierNPC().getArtifactOwner());
            return false;

        }

        player.sendMessage(Utils.poprawneUzycie("magazyn <nr magazynu>"));
        return false;
    }

}
