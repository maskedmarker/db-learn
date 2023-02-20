package org.example.learn.db.export;

import org.example.learn.db.export.config.DbConfig;
import org.example.learn.db.export.util.SpringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbExportSchemaSpringBootApplicationTest {

    private DbConfig dbConfig;

    @Before
    public void init() {
        dbConfig = SpringUtils.getBean(DbConfig.class);
        assertNotNull(dbConfig);
    }

    @Test
    public void testDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbConfig.getDriver());
        Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());

        String sql = "select 1";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int col1 = resultSet.getInt(1);
                System.out.println("col1 = " + col1);
            }
        }
    }
}
