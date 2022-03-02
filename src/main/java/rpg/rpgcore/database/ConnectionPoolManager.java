package rpg.rpgcore.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import rpg.rpgcore.RPGCORE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final HikariDataSource dataSource;

    public ConnectionPoolManager(final RPGCORE rpgcore) {
        final String HOST = rpgcore.getConfig().getString("hostname");
        final int PORT = rpgcore.getConfig().getInt("port");
        final String DATABASE = rpgcore.getConfig().getString("database");
        final String USER = rpgcore.getConfig().getString("username");
        final String PASSWORD = rpgcore.getConfig().getString("password");

        final int threads = Runtime.getRuntime().availableProcessors() * 2 + 1;
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMinimumIdle(threads);
        config.setMaximumPoolSize(threads);
        config.setConnectionTimeout(300000);
        config.setConnectionTestQuery("SELECT 1");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(final Connection conn, final PreparedStatement ps, final ResultSet res) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
