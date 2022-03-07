package rpg.rpgcore.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.utils.Utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SQLManager {

    private final RPGCORE rpgcore;
    private final ConnectionPoolManager pool;

    public SQLManager(final RPGCORE rpgcore) {
        this.pool = new ConnectionPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }

    private void setFirstSpawn(final Location spawn) {

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO `spawn` VALUES (?,?,?,?,?,?)");

            ps.setString(1, w);
            ps.setDouble(2, x);
            ps.setDouble(3, y);
            ps.setDouble(4, z);
            ps.setFloat(5, yaw);
            ps.setFloat(6, pitch);

            final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);
            rpgcore.getSpawnManager().setSpawn(newLocspawn);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void loadAll() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM spawn");
            rs = ps.executeQuery();

            if (rs.next()) {
                this.setSpawn(new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch")));
            } else {
                this.setFirstSpawn(Bukkit.getWorld("world").getSpawnLocation());
            }

            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void setSpawn(final Location spawn) {

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `spawn` set x= ?, y = ?, z=?, yaw=?, pitch=? WHERE world=?");

            ps.setDouble(1, x);
            ps.setDouble(2, y);
            ps.setDouble(3, z);
            ps.setFloat(4, yaw);
            ps.setFloat(5, pitch);
            ps.setString(6, w);

            final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);
            rpgcore.getSpawnManager().setSpawn(newLocspawn);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void banujGracza(final Player player, final Player targetDoBana, final boolean silent ,final String powodDone){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            ps = con.prepareStatement("SELECT UUID FROM `banned_users` WHERE UUID = ?;");
            ps.setString(1, targetDoBana.getUniqueId().toString());
            rs = ps.executeQuery();
            if (rs.next()){
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&cGracz &6" + targetDoBana.getName() + " &cjest juz zbanowany"));
            } else {
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowDone = sdf.format(now);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 999);

                String wygasa = sdf.format(cal.getTime());

                ps = con.prepareStatement("INSERT INTO `banned_users` (NICK, UUID, SILENT, DATA_BANA, DATA_WYGASNIECIA_BANA, ADMIN_NICK, REASON) VALUES (?,?,?,?,?,?,?);");
                ps.setString(1, targetDoBana.getName());
                ps.setString(2, targetDoBana.getUniqueId().toString());
                ps.setBoolean(3, silent);
                ps.setString(4, nowDone);
                ps.setString(5, wygasa);
                ps.setString(6, player.getName());
                ps.setString(7, powodDone);

                ps.executeUpdate();
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&aPomyslnie zablokowano gracza &6" + targetDoBana.getName() + " &ana &6zawsze"));
                if (!silent) {
                    Bukkit.broadcastMessage(Utils.format(Utils.BANPREFIX + "Gracz &c" + targetDoBana.getName() + " &7zostal &cpermamentnie &7zablokowany przez &c" + player.getName() + " &7za &c" + powodDone));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            pool.close(con, ps, null);
        }
    }

    public void banujGracza(final Player player, final OfflinePlayer targetDoBanaOffline,  final boolean silent, final String powodDone){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            ps = con.prepareStatement("SELECT UUID FROM `baned_users` WHERE UUID = ?;");
            ps.setString(1, targetDoBanaOffline.getUniqueId().toString());
            rs = ps.executeQuery();
            if (rs.next()){
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&cGracz &6" + targetDoBanaOffline.getName() + " &cjest juz zbanowany"));
            } else {
                Date now = new Date();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowDone = sdf.format(now);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 999);

                String wygasa = sdf.format(cal.getTime());

                ps = con.prepareStatement("INSERT INTO `banned_users` (NICK, UUID, SILENT, DATA_BANA, DATA_WYGASNIECIA_BANA, ADMIN_NICK, REASON) VALUES (?,?,?,?,?,?,?);");
                ps.setString(1, targetDoBanaOffline.getName());
                ps.setString(2, targetDoBanaOffline.getUniqueId().toString());
                ps.setBoolean(3, silent);
                ps.setString(4, nowDone);
                ps.setString(5, wygasa);
                ps.setString(6, player.getName());
                ps.setString(7, powodDone);

                ps.executeUpdate();
                player.sendMessage(Utils.format(Utils.BANPREFIX + "&aPomyslnie zablokowano gracza &6" + targetDoBanaOffline.getName() + " &ana &6zawsze"));
                if (!silent) {
                    Bukkit.broadcastMessage(Utils.format(Utils.BANPREFIX + "Gracz &c" + targetDoBanaOffline.getName() + " &7zostal &cpermamentnie &7zablokowany przez &c" + player.getName() + " &7za &c" + powodDone));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            pool.close(con, ps, null);
        }
    }

    public void unBanPlayer(final Player player, final OfflinePlayer target){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = pool.getConnection();
            ps = con.prepareStatement("SELECT UUID FROM `banned_users` WHERE UUID = ?;");
            ps.setString(1, target.getUniqueId().toString());
            rs = ps.executeQuery();
            if (!rs.next()){
                player.sendMessage(Utils.format(Utils.UNBANPREFIX + "&cGracz &6" + target.getName() + " &cnie jest zbanowany"));
            } else {
                ps = con.prepareStatement("DELETE FROM `banned_users` WHERE UUID = ?;");
                ps.setString(1, target.getUniqueId().toString());
                ps.executeUpdate();
                player.sendMessage(Utils.format(Utils.UNBANPREFIX + "&aPomyslnie odblokowano gracza &6" + target.getName()));
                Bukkit.broadcastMessage(Utils.format(Utils.UNBANPREFIX + "&7Gracz &c" + target.getName() + " &7zostal odblokowany przez &c" + player.getName()));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            pool.close(con, ps , null);
        }
    }

    public void onDisable() {
        pool.closePool();
    }
}
