package rpg.rpgcore.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import rpg.rpgcore.RPGCORE;
import rpg.rpgcore.managers.SpawnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLManager {

    private final RPGCORE rpgcore;
    private final ConnectionPoolManager pool;

    public SQLManager(final RPGCORE rpgcore) {
        this.pool = new ConnectionPoolManager(rpgcore);
        this.rpgcore = rpgcore;
    }

    private void setFirstSpawn() {

        final String w = "world";
        final double x = SpawnManager.defaultSpawnX;
        final double y = SpawnManager.defaultSpawnY;
        final double z = SpawnManager.defaultSpawnZ;

        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO `spawn` VALUES (?,?,?,?,?,?)");

            ps.setString(1, w);
            ps.setDouble(2, x);
            ps.setDouble(3, y);
            ps.setDouble(4, z);
            ps.setFloat(5, 0.0F);
            ps.setFloat(6, 0.0F);

            final Location newLocspawn = new Location(Bukkit.getWorld(w), x, y, z, 0.0F, 0.0F);
            rpgcore.getSpawnManager().setSpawn(newLocspawn);

            ps.executeUpdate();
        } catch (final SQLException e) {
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
                this.setFirstSpawn();
            }

            ps.executeQuery();
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM player;");
            rs = ps.executeQuery();

            while (rs.next()) {
                rpgcore.getPlayerManager().createPlayer(
                        rs.getString("nick"),
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("banInfo"),
                        rs.getString("punishmentHistory"));
            }

            ps.executeQuery();
        } catch (final SQLException e) {
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

            ps.executeUpdate();

            rpgcore.getSpawnManager().setSpawn(newLocspawn);
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void createPlayer(final String nick, final UUID uuid, final String banInfo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("INSERT INTO `player` VALUES (?,?,?)");

            ps.setString(1, String.valueOf(uuid));
            ps.setString(2, nick);
            ps.setString(3, banInfo);

            ps.executeUpdate();

            rpgcore.getPlayerManager().createPlayer(nick, uuid, "false", "");
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void banPlayer(final UUID uuid, final String banInfo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player` set banInfo=? WHERE uuid=?");

            ps.setString(1, banInfo);
            ps.setString(2, String.valueOf(uuid));

            ps.executeUpdate();

            rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, banInfo);
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void unBanPlayer(final UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player` set banInfo=? WHERE uuid=?");

            ps.setString(1, "false");
            ps.setString(2, String.valueOf(uuid));

            ps.executeUpdate();

            rpgcore.getPlayerManager().updatePlayerBanInfo(uuid, "false");
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void setPunishmentHistory(final UUID uuid, final String punishmentHistory) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = pool.getConnection();
            ps = conn.prepareStatement("UPDATE `player` set punishmentHistory=? WHERE uuid=?");

            ps.setString(1, String.valueOf(punishmentHistory));
            ps.setString(2, String.valueOf(uuid));

            ps.executeUpdate();

            rpgcore.getPlayerManager().updatePlayerPunishmentHistory(uuid, punishmentHistory);
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
    }

    public void onDisable() {
        pool.closePool();
    }
}
