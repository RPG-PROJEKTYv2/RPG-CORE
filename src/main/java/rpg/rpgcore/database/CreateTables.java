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
                    " `nick` TEXT NOT NULL, " +
                    " `lvl` INT NOT NULL," +
                    " `exp` DOUBLE NOT NULL," +
                    " `banInfo` TEXT NOT NULL, " +
                    " `muteInfo` TEXT NOT NULL, " +
                    " `punishmentHistory` TEXT NULL DEFAULT NULL," +
                    " `osMoby` INT NOT NULL DEFAULT 0," +
                    " `osLudzie` INT NOT NULL DEFAULT 0," +
                    " `osSakwy` INT NOT NULL DEFAULT 0," +
                    " `osNiesy` INT NOT NULL DEFAULT 0," +
                    " `osRybak` INT NOT NULL DEFAULT 0," +
                    " `osDrwal` INT NOT NULL DEFAULT 0," +
                    " `osGornik` INT NOT NULL DEFAULT 0," +
                    "  PRIMARY KEY (`uuid`))");
            ps.execute();
        } catch (final SQLException e) {
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
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }
        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `npc` (" +
                    " `uuid` VARCHAR(36) NOT NULL," +
                    " `BAO_BONUSY` TEXT NOT NULL, " +
                    " `BAO_WARTOSCI` TEXT NOT NULL," +
                    " `RYBAK_MISJE` TEXT NOT NULL," +
                    " `RYBAK_SRDMG` DOUBLE NOT NULL," +
                    " `RYBAK_SRDEF` DOUBLE NOT NULL," +
                    " `RYBAK_DDMG` DOUBLE NOT NULL," +
                    " `RYBAK_BLOK` DOUBLE NOT NULL," +
                    "  PRIMARY KEY (`uuid`))");
            ps.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

        try {
            conn = pool.getConnection();
            ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS `Inventories` (" +
                    " `uuid` VARCHAR(36) NOT NULL," +
                    " `Akcesoria` TEXT NOT NULL, " +
                    " `Targ` TEXT NOT NULL, " +
                    "  PRIMARY KEY (`uuid`))");
            ps.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, null);
        }

    }
}
