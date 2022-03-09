package rpg.rpgcore.managers;

import org.bukkit.entity.Player;
import rpg.rpgcore.utils.Utils;

public class BanManager {

    //TODO dodanie funkcji odpowiadających za banowanie i mutowanie i warnowanie

    public void kickPlayer(final String sederName, final Player p, final StringBuilder reason, final boolean silent) {

        final String kickMessage =
                Utils.SERVERNAME + Utils.format(
                        "\n&7Zostales wyrzucony z serwera\n" +
                                "&7Przez: &c" + sederName +
                                "\n&7Za:&c" + reason +
                                "\n\n&8Jezeli chcesz zglosic skarge na administratora, ktory wyrzucil Cie z" +
                                " serwera\n" +
                                "&8zglos sie na przykladowyts3.pl lub przykladowydc z ss tego kicka!");
        p.kickPlayer(kickMessage);

        //TODO do dodania sprawdzanie czy wysłać wiadomość
    }

    public void kickPlayer(final String sederName, final Player p, final boolean silent) {

        final String kickMessage =
                Utils.SERVERNAME + Utils.format(
                        "\n&7Zostales wyrzucony z serwera\n&7Przez: &c" + sederName +
                                "\n&7Za:&c Brak powodu \n\n&8Jezeli chcesz zglosic skarge na administratora, ktory wyrzucil Cie z serwera\n" +
                                "&8zglos sie na przykladowyts3.pl lub przykladowydc z ss tego kicka!");
        p.kickPlayer(kickMessage);

        //TODO do dodania sprawdzanie czy wysłać wiadomość

    }

    public void kickPlayerForPemr(final String adminName, final Player target, final String reason){
        String kickMessgae = Utils.format(
                Utils.BANPREFIX +
                        "\n&7Zostales permamentnie zablokowany na tym serwerze\n"+
                        "&7Przez: &c" + adminName + "\n"+
                        "&7Powod: &c" + reason + "\n"+
                        "&8Jezeli uwazasz, ze to blad, skontaktuj sie z\n" +
                        "&cadministracja &8 tego serwera");
        target.kickPlayer(kickMessgae);
    }
}
