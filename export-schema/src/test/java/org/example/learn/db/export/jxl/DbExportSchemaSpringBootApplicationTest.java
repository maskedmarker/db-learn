package org.example.learn.db.export.jxl;

import org.example.learn.db.export.jxl.config.DbConfig;
import org.example.learn.db.export.jxl.util.SpringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbExportSchemaSpringBootApplicationTest {

    @Autowired
    private DbConfig dbConfig;

    @Before
    public void init() {
        assertNotNull(dbConfig);
    }

    @Test
    public void testSpringApplication() {
        DbConfig dbConfig = SpringUtils.getBean(DbConfig.class);
        assertNotNull(dbConfig);
        System.out.println("dbConfig = " + dbConfig);
    }

    @Test
    public void testDbConnection() throws ClassNotFoundException, SQLException {
        // load driver
        Class.forName(dbConfig.getDriver());
        // create db connection
        String pingSql = "select 1";
        try (Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
             Statement statement = connection.createStatement()
        ) {
            // ping db server
            ResultSet resultSet = statement.executeQuery(pingSql);
            while (resultSet.next()) {
                int pong = resultSet.getInt(1);
                System.out.println("col1 = " + pong);
                assertEquals(1, pong);
            }
        } finally {
            // noop
        }
    }
}
