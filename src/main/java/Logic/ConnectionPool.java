package Logic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private static HikariDataSource dataSource;
    
    // Static initializer block executes once when the class is loaded 
    static {
        HikariConfig config = new HikariConfig(); // Config parameters for connection pool
        config.setJdbcUrl("jdbc:postgresql://db.zjtaizpyifuycbatgkao.supabase.co:5432/postgres");
        config.setUsername("public_user");
        config.setPassword("mematosinosirve");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    /**
     * Uses HikariCP to handle connections automatically 
     * @return Connection from the connection pool
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
