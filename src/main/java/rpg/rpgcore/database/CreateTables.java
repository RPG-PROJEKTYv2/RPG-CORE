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
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `player` (" +
                    " `uuid` VARCHAR(36) NOT NULL," +
                    " `nick` VARCHAR(255) NOT NULL, " +
                    " PRIMARY KEY (`uuid`))");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `spawn` (" +
                    " `world` VARCHAR(255) NULL DEFAULT NULL," +
                    " `x` DOUBLE NULL DEFAULT NULL," +
                    " `y` DOUBLE NULL DEFAULT NULL," +
                    " `z` DOUBLE NULL DEFAULT NULL," +
                    " `yaw` FLOAT NULL DEFAULT NULL," +
                    " `pitch` FLOAT NULL DEFAULT NULL);");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `banned_users` ( " +
                    "`ID` INT NOT NULL AUTO_INCREMENT, " +
                    "`NICK` VARCHAR(255) NOT NULL, " +
                    "`UUID` VARCHAR(36) NOT NULL, " +
                    "`SILENT` TINYINT(1) NOT NULL, " +
                    "`DATA_BANA` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "`DATA_WYGASNIECIA_BANA` DATETIME NOT NULL, " +
                    "`ADMIN_NICK` VARCHAR(255) NOT NULL, " +
                    "`REASON` TEXT NOT NULL, " +
                    "PRIMARY KEY (`ID`));");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `muted_users` ( " +
                    "`ID` INT NOT NULL AUTO_INCREMENT, " +
                    "`NICK` VARCHAR(255) NOT NULL, " +
                    "`UUID` VARCHAR(36) NOT NULL, " +
                    "`DATA_MUTA` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                    "`DATA_WYGASNIECIA_MUTA` DATETIME NOT NULL, " +
                    "`ADMIN_NICK` VARCHAR(255) NOT NULL, " +
                    "`REASON` TEXT NOT NULL, " +
                    "PRIMARY KEY (`ID`));");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

    }

}
