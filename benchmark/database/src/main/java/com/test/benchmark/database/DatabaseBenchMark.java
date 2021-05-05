package com.test.benchmark.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.test.benchmark.BenchmarkTask;
import com.test.benchmark.ConcurrentBenchmark;

public class DatabaseBenchMark extends ConcurrentBenchmark {

    private MysqlDataSource dataSource;

    private List<Connection> connections = new ArrayList<Connection>();

    public DatabaseBenchMark(int defaultThreadCount, long defaultLoopCount) {
        super(defaultThreadCount, defaultLoopCount);
    }

    @Override
    protected void setUp() throws IOException {
        final Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/database.properties"));
        this.dataSource = new MysqlDataSource();
        this.dataSource.setURL(properties.getProperty("jdbc.url"));
        this.dataSource.setUser(properties.getProperty("jdbc.username"));
        this.dataSource.setPassword(properties.getProperty("jdbc.password"));
    }

    @Override
    protected BenchmarkTask createTask() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.connections.add(connection);
        return new QueryTask(connection);
    }

    @Override
    protected void tearDown() {
        for (Connection connection : this.connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                //
            }
        }
    }

    public class QueryTask extends BenchmarkTask {

        private Connection connection;

        public QueryTask(Connection connection) {
            this.connection = connection;
        }

        @Override
        protected void execute(@SuppressWarnings("unused") int requestSequence) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = this.connection.createStatement();
                rs = stmt.executeQuery("select * from PARTNER limit 1");

                while (rs.next()) {
                    rs.getString("PK");
                    rs.getBytes("PROPERTIES");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        //
                    }
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        //
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final DatabaseBenchMark benchMark = new DatabaseBenchMark(10, 100);
        benchMark.execute();
    }
}
