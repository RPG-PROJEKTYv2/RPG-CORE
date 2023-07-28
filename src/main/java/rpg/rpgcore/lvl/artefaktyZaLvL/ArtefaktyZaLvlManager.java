package rpg.rpgcore.lvl.artefaktyZaLvL;

import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

public class ArtefaktyZaLvlManager {
    private final ArtefaktyZaLvl artefaktyZaLvl;

    public ArtefaktyZaLvlManager(final RPGCORE rpgcore) {
        this.artefaktyZaLvl = rpgcore.getMongoManager().loadArtefaktyZaLvl();
    }

    public void checkArteZaLvl(final Player player, final int lvl) {
        if (lvl == 50) {
            if (artefaktyZaLvl.getPoziom50().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom50().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom50().setNadanych(artefaktyZaLvl.getPoziom50().getNadanych() + 1);
            artefaktyZaLvl.getPoziom50().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom50().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 60) {
            if (artefaktyZaLvl.getPoziom60().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom60().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom60().setNadanych(artefaktyZaLvl.getPoziom60().getNadanych() + 1);
            artefaktyZaLvl.getPoziom60().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom60().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 70) {
            if (artefaktyZaLvl.getPoziom70().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom70().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom70().setNadanych(artefaktyZaLvl.getPoziom70().getNadanych() + 1);
            artefaktyZaLvl.getPoziom70().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom70().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 80) {
            if (artefaktyZaLvl.getPoziom80().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom80().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom80().setNadanych(artefaktyZaLvl.getPoziom80().getNadanych() + 1);
            artefaktyZaLvl.getPoziom80().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom80().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 90) {
            if (artefaktyZaLvl.getPoziom90().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom90().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom90().setNadanych(artefaktyZaLvl.getPoziom90().getNadanych() + 1);
            artefaktyZaLvl.getPoziom90().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom90().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 100) {
            if (artefaktyZaLvl.getPoziom100().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom100().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom100().setNadanych(artefaktyZaLvl.getPoziom100().getNadanych() + 1);
            artefaktyZaLvl.getPoziom100().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom100().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 110) {
            if (artefaktyZaLvl.getPoziom110().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom110().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom110().setNadanych(artefaktyZaLvl.getPoziom110().getNadanych() + 1);
            artefaktyZaLvl.getPoziom110().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom110().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 120) {
            if (artefaktyZaLvl.getPoziom120().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom120().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom120().setNadanych(artefaktyZaLvl.getPoziom120().getNadanych() + 1);
            artefaktyZaLvl.getPoziom120().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom120().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
            return;
        }
        if (lvl == 130) {
            if (artefaktyZaLvl.getPoziom130().getNadanych() == 4) return;
            if (artefaktyZaLvl.getPoziom130().getGracze().contains(player.getName())) return;
            artefaktyZaLvl.getPoziom130().setNadanych(artefaktyZaLvl.getPoziom130().getNadanych() + 1);
            artefaktyZaLvl.getPoziom130().getGracze().add(player.getName());
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format(Utils.LVLPREFIX + "&fOsiagnal go jako &3" + artefaktyZaLvl.getPoziom130().getNadanych() + "&f osoba na serwerze!"));
            RPGCORE.getInstance().getServer().broadcastMessage(Utils.format("    &8Po odbior artefaktu zapraszamy do &8do &4Wyzszej Administracji&8!"));
            save();
        }
    }

    public ArtefaktyZaLvl getArtefaktyZaLvl() {
        return this.artefaktyZaLvl;
    }

    public void save() {
        RPGCORE.getInstance().getServer().getScheduler().runTaskAsynchronously(RPGCORE.getInstance(), () -> RPGCORE.getInstance().getMongoManager().saveArtefaktyZaLvl(this.artefaktyZaLvl));
    }
}
