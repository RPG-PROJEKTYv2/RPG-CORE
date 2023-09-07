package rpg.rpgcore.metiny;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.api.CommandAPI;
import rpg.rpgcore.ranks.types.RankType;
import rpg.rpgcore.utils.DoubleUtils;
import rpg.rpgcore.utils.Utils;

import java.io.IOException;

public class MetinCommand extends CommandAPI {

    private final RPGCORE rpgcore;

    public MetinCommand(RPGCORE rpgcore) {
        super("metin");
        this.setRankLevel(RankType.ADMIN);
        this.setRestrictedForPlayer(false);
        this.rpgcore = rpgcore;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) throws IOException {
        

        if (args.length < 1) {
            sender.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <moby> <resp(0/1)> <nazwa>"));
            return;
        }
        if (args.length >= 6) {
            if (args[0].equals("create")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.format("Tej opcji moze uzywac tylko gracz"));
                    return;
                }
                if (args[1] != null) {
                    final Player player = (Player) sender;
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o ID:&6 " + id + " &cjest juz utworzony."));
                        return;
                    }
                    int health = Integer.parseInt(args[2]);
                    int moby = Integer.parseInt(args[3]);
                    int resp = Integer.parseInt(args[4]);
                    final StringBuilder name = new StringBuilder();
                    for (int i = 5; i < args.length; ++i) {
                        name.append(args[i]).append(" ");
                    }
                    final Metiny metiny = new Metiny(id);
                    metiny.getMetins().setName(name.toString().trim());
                    metiny.getMetins().setX(DoubleUtils.round(player.getLocation().getX(), 2));
                    metiny.getMetins().setY(DoubleUtils.round(player.getLocation().getY(), 2));
                    metiny.getMetins().setZ(DoubleUtils.round(player.getLocation().getZ(), 2));
                    metiny.getMetins().setWorld(String.valueOf(player.getWorld().getName()));
                    metiny.getMetins().setMaxhealth(health);
                    metiny.getMetins().setHealth(0);
                    metiny.getMetins().setResp(resp);
                    metiny.getMetins().setMoby(moby);
                    this.rpgcore.getMetinyManager().add(metiny);
                    this.rpgcore.getMongoManager().addDataMetins(metiny);
                    sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie stowrzyles lokacje metina ID:&6 " + args[1] + "\n"
                            + Utils.SERVERNAME + "&aNazwa metina:&6 " + metiny.getMetins().getName() + "\n"
                            + Utils.SERVERNAME + "&aSwiat metina:&6 " + metiny.getMetins().getWorld() + "\n"
                            + Utils.SERVERNAME + "&aKordy metina: &6" + metiny.getMetins().getX() + "&a, &6" + metiny.getMetins().getY() + "&a, &6" + metiny.getMetins().getZ() + "&a, &6" + "\n"
                            + Utils.SERVERNAME + "&aHP metina:&6 " + metiny.getMetins().getMaxhealth() + "\n"
                            + Utils.SERVERNAME + "&aIlosc potworow:&6 " + metiny.getMetins().getMoby() + "\n"));
                    MetinyHelper.spawnMetin(id);
                    return;
                }
            }
            sender.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <moby> <resp(0/1)> <nazwa>"));
            return;
        }
        if (args.length == 2) {
            if (args[0].equals("spawn")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        MetinyHelper.spawnMetin(id);
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zrespiles metin o ID&6 " + id));
                    }
                    if (!this.rpgcore.getMetinyManager().isMetin(id)) {
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o podanym ID nie istnieje!"));
                    }
                    return;
                }
            }
            if (args[0].equals("despawn")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        MetinyHelper.despawnMetin(id);
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie despawnowales metin o ID&6 " + id));
                    }
                    if (!this.rpgcore.getMetinyManager().isMetin(id)) {
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o podanym ID nie istnieje!"));
                    }
                    return;
                }
            }
            if (args[0].equals("tp")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Utils.format("Tej opcji moze uzywac tylko gracz"));
                    return;
                }
                if (args[1] != null) {
                    final Player player = (Player) sender;
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        final Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
                        player.teleport(metiny.getLocation());
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales pomyslnie przeteleportowany na lokacje metina ID:&6 " + id + "&a."));
                        return;
                    }
                }
            }
            if (args[0].equals("usun")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        this.rpgcore.getMongoManager().removeDataMetins(id);
                        final Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
                        this.rpgcore.getServer().getWorld(metiny.getMetins().getWorld()).getEntities().stream().filter((entity) -> (entity.getLocation().equals(metiny.getLocation()))).forEachOrdered(Entity::remove);
                        this.rpgcore.getMetinyManager().remove(metiny);
                        this.rpgcore.getMongoManager().saveAllMetins();
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aMetin o podanym ID zostal usuniety!"));
                    } else {
                        sender.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o podanym ID nie istnieje!"));
                    }
                    return;
                }
            }
            sender.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <moby> <resp(0/1)> <nazwa>"));
            return;
        }
        if (args.length == 1) {
            if (args[0].equals("spawnall")) {
                Bukkit.getServer().broadcastMessage(Utils.format("&b&lMetiny zostaly zresetowane!"));
                MetinyHelper.respAllMetins();
                return;
            }
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(Utils.format(Utils.SERVERNAME + "&aLista metin:"));
                for (final Metiny metin : this.rpgcore.getMetinyManager().getMetins()) {
                    sender.sendMessage(Utils.format("&8- &6" + metin.getId()));
                    sender.sendMessage(Utils.format("  &8- &6Nazwa&8: " + metin.getMetins().getName()));
                    sender.sendMessage(Utils.format("  &8- &6Swiat&8: &e" + metin.getMetins().getWorld()));
                    sender.sendMessage(Utils.format("  &8- &6X&8: &e" + metin.getMetins().getX()));
                    sender.sendMessage(Utils.format("  &8- &6Y&8: &e" + metin.getMetins().getY()));
                    sender.sendMessage(Utils.format("  &8- &6Z&8: &e" + metin.getMetins().getZ()));
                }
                return;
            }
            sender.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <moby> <moby> <resp(0/1)> <nazwa>"));
        }
    }
}
