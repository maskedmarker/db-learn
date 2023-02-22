package org.example.learn.db.export.jxl.biz;

import org.example.learn.db.export.jxl.config.DbConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseDbTest {

    @Autowired
    protected DbConfig dbConfig;

    protected Connection connection;

    @Before
    public void setUp() throws Exception {
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
