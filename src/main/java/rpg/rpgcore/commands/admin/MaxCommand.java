package rpg.rpgcore.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.bonuses.Bonuses;
import rpg.rpgcore.npc.gornik.GornikObject;
import rpg.rpgcore.npc.lowca.LowcaObject;
import rpg.rpgcore.npc.metinolog.objects.MetinologObject;
import rpg.rpgcore.npc.przyrodnik.PrzyrodnikObject;

import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.user.User;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;
import java.util.UUID;

public class MaxCommand extends CommandAPI {
    private final RPGCORE rpgcore;

    public MaxCommand(RPGCORE rpgcore) {
        super("max");
        this.rpgcore = rpgcore;
        this.setRestrictedForPlayer(true);
        this.setRankLevel(RankType.DEV);
    }

    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        final Player player = (Player) sender;
        if (args.length < 2) {
            player.sendMessage(Utils.poprawneUzycie("max <player> <npc>"));
            return;
        }

        final User user = rpgcore.getUserManager().find(args[0]);

        if (user == null) {
            player.sendMessage(Utils.format(Utils.SERVERNAME + "&cNie znaleziono gracza!"));
            return;
        }

        final UUID userUUID = user.getId();
        final Bonuses bonuses = rpgcore.getBonusesManager().find(userUUID);

        switch (args[1]) {
            case "gornik":
                final GornikObject gornikObject = rpgcore.getGornikNPC().find(userUUID);
                gornikObject.getGornikUser().setSredniaOdpornosc(30);
                gornikObject.getGornikUser().setPrzeszycieBloku(5);
                gornikObject.getGornikUser().setBlokCiosu(5);

                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 30);
                bonuses.getBonusesUser().setPrzeszyciebloku(bonuses.getBonusesUser().getPrzeszyciebloku() + 5);
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 5);

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZrobione &6Gornik!"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataGornik(userUUID, gornikObject);
                    rpgcore.getMongoManager().saveDataBonuses(userUUID, bonuses);
                });
                return;
            case "lowca":
                final LowcaObject lowcaObject = rpgcore.getLowcaNPC().find(userUUID);
                lowcaObject.getLowcaUser().setSzczescie(26);
                lowcaObject.getLowcaUser().setSzybkosc(39);
                lowcaObject.getLowcaUser().setTruedmg(3);

                bonuses.getBonusesUser().setSzczescie(bonuses.getBonusesUser().getSzczescie() + 26);
                bonuses.getBonusesUser().setSzybkosc(bonuses.getBonusesUser().getSzybkosc() + 39);
                bonuses.getBonusesUser().setTruedamage(bonuses.getBonusesUser().getTruedamage() + 3);
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZrobione &6Lowca!"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataLowca(userUUID, lowcaObject);
                    rpgcore.getMongoManager().saveDataBonuses(userUUID, bonuses);
                });
                return;
            case "metinolog":
                final MetinologObject metinologObject = rpgcore.getMetinologNPC().find(userUUID);
                metinologObject.getMetinologUser().setPrzeszycie(350); //DODATKOWE DMG
                metinologObject.getMetinologUser().setSrOdpo(20); //OODPORNOSC NA LUDZI
                metinologObject.getMetinologUser().setDodatkowedmg(20); //SR ODPO

                bonuses.getBonusesUser().setDodatkoweobrazenia(bonuses.getBonusesUser().getDodatkoweobrazenia() + 350);
                bonuses.getBonusesUser().setDefnaludzi(bonuses.getBonusesUser().getDefnaludzi() + 20);
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 20);
                bonuses.getBonusesUser().setBlokciosu(bonuses.getBonusesUser().getBlokciosu() + 7.5);

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZrobione &6Metinolog!"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataMetinolog(userUUID, metinologObject);
                    rpgcore.getMongoManager().saveDataBonuses(userUUID, bonuses);
                });
                return;
            case "przyrodnik":
                final PrzyrodnikObject przyrodnikObject = rpgcore.getPrzyrodnikNPC().find(userUUID);
                przyrodnikObject.getUser().setDmg(22.5);
                przyrodnikObject.getUser().setDef(22.5);

                bonuses.getBonusesUser().setSrednieobrazenia(bonuses.getBonusesUser().getSrednieobrazenia() + 22.5);
                bonuses.getBonusesUser().setSredniadefensywa(bonuses.getBonusesUser().getSredniadefensywa() + 22.5);

                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZrobione &6Przyrodnik!"));
                rpgcore.getServer().getScheduler().runTaskAsynchronously(rpgcore, () -> {
                    rpgcore.getMongoManager().saveDataPrzyrodnik(userUUID, przyrodnikObject);
                    rpgcore.getMongoManager().saveDataBonuses(userUUID, bonuses);
                });
        }

    }
}
