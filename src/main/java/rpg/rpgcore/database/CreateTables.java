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


    }

}
