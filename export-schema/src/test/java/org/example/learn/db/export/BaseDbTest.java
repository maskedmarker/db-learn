package org.example.learn.db.export;

import org.example.learn.db.export.config.DbConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Ignore
public abstract class BaseDbTest {

    @Autowired
    protected DbConfig dbConfig;

    protected Connection connection;

    @Before
    public void setUp() throws Exception {
        Class.forName(dbConfig.getDriver());
        connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
        Assert.assertNotNull(connection);
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
