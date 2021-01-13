package com.epam.esm.model.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnectionPool {
    private static final Logger logger = Logger.getLogger(MysqlConnectionPool.class);
    private static final String DATASOURCE_NAME = "jdbc/module2";
    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            logger.error("can't init connection pool");
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
