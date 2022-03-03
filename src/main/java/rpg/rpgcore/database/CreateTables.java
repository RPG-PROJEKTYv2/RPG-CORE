package rpg.rpgcore.database;

import rpg.rpgcore.RPGCORE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTables {

    private final ConnectionPoolManager pool;

    public CreateTables(final RPGCORE rpgcore) {
        this.pool = new ConnectionPoolManager(rpgcore);
    }

    public void createTables() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS banned_users(\n" +
                    "    ID INT AUTO_INCREMENT,\n" +
                    "    NICK TEXT NOT NULL,\n" +
                    "    UUID VARCHAR(36) NOT NULL,\n" +
                    "    SILENT BOOLEAN NOT NULL,\n" +
                    "    BAN_DATE TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    BAN_EXPIRE_DATE TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    ADMIN_NICK TEXT NOT NULL,\n" +
                    "    REASON TEXT NOT NULL,\n" +
                    "    PRIMARY KEY(ID)\n" +
                    ");");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS muted_users(\n" +
                    "    ID INT AUTO_INCREMENT,\n" +
                    "    NICK TEXT NOT NULL,\n" +
                    "    UUID VARCHAR(36) NOT NULL,\n" +
                    "    MUTE_DATE TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    MUTE_EXPIRE_DATE TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    ADMIN_NICK TEXT NOT NULL,\n" +
                    "    REASON TEXT NOT NULL,\n" +
                    "    PRIMARY KEY(ID)\n" +
                    ");");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS player_data(\n" +
                    "    ID INT AUTO_INCREMENT,\n" +
                    "    NICK TEXT NOT NULL,\n" +
                    "    UUID VARCHAR(36) NOT NULL,\n" +
                    "    RANK TEXT NOT NULL,\n" +
                    "    IP VARCHAR(16) NOT NULL ,\n" +
                    "    LVL INT NOT NULL,\n" +
                    "    DOSWIADCZENIE FLOAT NOT NULL,\n" +
                    "    MONEY FLOAT NOT NULL,\n" +
                    "    RANK_POINTS FLOAT NOT NULL,\n" +
                    "    PRIMARY KEY(ID)\n" +
                    ");");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `spawn` (\n" +
                    "  `world` VARCHAR(255) NOT NULL,\n" +
                    "  `x` DOUBLE NOT NULL,\n" +
                    "  `y` DOUBLE NOT NULL,\n" +
                    "  `z` DOUBLE NOT NULL,\n" +
                    "  `yaw` FLOAT NOT NULL,\n" +
                    "  `pitch` FLOAT NOT NULL);");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }


    }

}
