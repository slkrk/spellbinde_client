package pl.softlink.spellbinder.server;

import java.sql.*;

public class JDBC {

    private Connection connection = null;
    private static JDBC instance = null;

    private JDBC() {

        try {

            try {
                Class.forName(Config.DB_DRIVER).newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Blad przy ladowaniu sterownika bazy!", e);
            }

            connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static JDBC getInstance() {
        if (instance == null) {
            instance = new JDBC();
        }
        return instance;
    }

    public static Connection getConnection() {
        return getInstance().connection;
    }

    public static int getGeneratedKey(PreparedStatement preparedStatement) {
        try {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Cant get autoincrement value", e);
        }
    }

}
