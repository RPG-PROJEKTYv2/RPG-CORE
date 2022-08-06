package rpg.rpgcore.metiny;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.LocationHelper;
import rpg.rpgcore.utils.Utils;

public class MetinCommand implements CommandExecutor {

    private final RPGCORE rpgcore;

    public MetinCommand(RPGCORE rpgcore) {
        this.rpgcore = rpgcore;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.NIEGRACZ);
            return false;
        }

        final Player player = (Player) sender;

        if (!(player.hasPermission("admin.rpg.metin"))) {
            player.sendMessage(Utils.permisje("admin.rpg.metin"));
            return false;
        }


        if (args.length == 4) {
            if (args[0].equals("create")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o ID:&6 " + id + " &cjest juz utworzony."));
                        return false;
                    }
                    int health = Integer.parseInt(args[2]);
                    int resp = Integer.parseInt(args[3]);
                    if (!this.rpgcore.getMetinyManager().isMetin(id)) {
                        final Metiny newMetiny = new Metiny(id);
                        this.rpgcore.getMetinyManager().add(newMetiny);
                        this.rpgcore.getMongoManager().addDataMetins(newMetiny);
                    }
                    Metiny metiny = this.rpgcore.getMetinyManager().find(id);
                    metiny.getMetins().setCoordinates(LocationHelper.locToString(player.getLocation()));
                    metiny.getMetins().setWorld(String.valueOf(player.getWorld().getName()));
                    metiny.getMetins().setMaxhealth(health);
                    metiny.getMetins().setHealth(0);
                    metiny.getMetins().setResp(resp);
                    this.rpgcore.getMongoManager().saveDataMetins(id, metiny);
                    player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie stowrzyles lokacje metina ID:&6 " + args[1] + "\n"
                            + Utils.SERVERNAME + "&aSwiat metina:&6 " + metiny.getMetins().getWorld() + "\n"
                            + Utils.SERVERNAME + "&aKordy metina:&6 " + metiny.getMetins().getCoordinates() + "\n"
                            + Utils.SERVERNAME + "&aHP metina:&6 " + metiny.getMetins().getMaxhealth()));
                    MetinyHelper.spawnMetin(id);
                    return false;
                }
            }
            player.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <resp(0/1)>"));
            return false;
        }
        if (args.length == 2) {
            if (args[0].equals("spawn")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        MetinyHelper.spawnMetin(id);
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aPomyslnie zrespiles metin o ID&6 " + id));
                    }
                    if (!this.rpgcore.getMetinyManager().isMetin(id)) {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o podanym ID nie istnieje!"));
                    }
                    return false;
                }
            }
            if (args[0].equals("tp")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
                        String world = metiny.getMetins().getWorld();
                        Location loc = LocationHelper.locFromString(metiny.getMetins().getCoordinates());
                        Location location = new Location(Bukkit.getServer().getWorld(world), loc.getX(), loc.getY(), loc.getZ());
                        player.teleport(location);
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aZostales pomyslnie przeteleportowany na lokacje metina ID:&6 " + id + "&a."));
                        return false;
                    }
                }
            }
            if (args[0].equals("usun")) {
                if (args[1] != null) {
                    int id = Integer.parseInt(args[1]);
                    if (this.rpgcore.getMetinyManager().isMetin(id)) {
                        this.rpgcore.getMongoManager().removeDataMetins(id);
                        Metiny metiny = RPGCORE.getInstance().getMetinyManager().find(id);
                        this.rpgcore.getServer().getWorld(metiny.getMetins().getWorld()).getEntities().stream().filter((entity) -> (entity.getCustomName() != null && entity.getCustomName().equals(String.valueOf(id)))).forEachOrdered(Entity::remove);
                        this.rpgcore.getMetinyManager().remove(metiny);
                        this.rpgcore.getMongoManager().saveAllMetins();
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&aMetin o podanym ID zostal usuniety!"));
                    } else {
                        player.sendMessage(Utils.format(Utils.SERVERNAME + "&cMetin o podanym ID nie istnieje!"));
                    }
                    return false;
                }
            }
            player.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <resp(0/1)>"));
            return false;
        }
        if (args.length == 1) {
            if (args[0].equals("spawnall")) {
                MetinyHelper.respAllMetins();
                return false;
            }
            if (args[0].equalsIgnoreCase("list")) {
                player.sendMessage(Utils.format(Utils.SERVERNAME + "&aLista metin:"));
                for (final Metiny metin : this.rpgcore.getMetinyManager().getMetins()) {
                    player.sendMessage(Utils.format("&8- &6" + metin.getId() + "&8: &6" + metin.getMetins().getWorld() + "&8: &6" + metin.getMetins().getCoordinates()));
                }
                return false;
            }
            player.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <resp(0/1)>"));
            return false;
        }
        player.sendMessage(Utils.poprawneUzycie("metin <create/spawn/tp/spawnall/usun/list> <id> <hp> <resp(0/1)>"));
        return false;
    }
}
