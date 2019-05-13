package com.webrtc.ether;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EtherApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EtherApplicationTests {

    @Autowired
    DataSource dataSource;

    final Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void contextLoads() throws SQLException {
       /* logger.trace("这是trance日志");
        logger.debug("这是debug日志");
        logger.info("这是info日志");
        logger.warn("这是warn日志");
        logger.error("这是error日志");*/

        System.out.println(dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
