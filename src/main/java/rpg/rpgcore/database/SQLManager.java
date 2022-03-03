package rpg.rpgcore.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager {

    private final RPGCORE rpgcore;
    private final ConnectionPoolManager pool;

    public SQLManager(final RPGCORE rpgcore) {
        this.pool = new ConnectionPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }

    public void loadAll(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM spawn");
            rs = ps.executeQuery();

            if (rs.next()) {

                rpgcore.setSpawn(new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z")));

            }

            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void firstLocationSpawn(final Location spawn){

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO `spawn` VALUES (?,?,?,?,?,?)");

            ps.setString(1, w);
            ps.setDouble(2, x);
            ps.setDouble(3, y);
            ps.setDouble(4, z);
            ps.setFloat(5, yaw);
            ps.setFloat(6, pitch);

        ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void setSpawn(final Location spawn){

        final String w = spawn.getWorld().getName();
        final double x = spawn.getX();
        final double y = spawn.getY();
        final double z = spawn.getZ();
        final float yaw = spawn.getYaw();
        final float pitch = spawn.getPitch();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `spawn` set x= ?, y = ?, z=?, yaw=?, pitch=? WHERE world=?");

            ps.setDouble(1, x);
            ps.setDouble(2, y);
            ps.setDouble(3, z);
            ps.setFloat(4, yaw);
            ps.setFloat(5, pitch);
            ps.setString(6, w);


            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void onDisable() {
        pool.closePool();
    }
}
