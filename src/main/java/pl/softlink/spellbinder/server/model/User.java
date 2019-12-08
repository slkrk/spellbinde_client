package pl.softlink.spellbinder.server.model;

import pl.softlink.spellbinder.server.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class User extends Model{

    private Integer id = null;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, int id) {
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void save() {

        String sql = "`user` SET";
        sql += " `email`=?";
        sql += " `password`=?";

        if (isNew()) {
            sql = "INSERT INTO " + sql;
        } else {
            sql = "UPDATE " + sql + " where `id`=?";
        }

        try {
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString (1, this.email);
            preparedStatement.setString (2, this.password);

            if (isNew()) {
                preparedStatement.setInt(3, this.id);
            }

            preparedStatement.executeUpdate();

            if (isNew()) {
                id = JDBC.getGeneratedKey(preparedStatement);
            }

        } catch (SQLException e) {
            throw new RuntimeException("sql error with statement: " + sql, e);
        }
    }




}
