package pl.softlink.spellbinder.server.model;

import pl.softlink.spellbinder.server.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Document extends Model{

    private Integer id = null;
    private String content;
    private String access_key;

    public Document(String content, String access_key, int id) {
        this.content = content;
        this.access_key = access_key;
        this.id = id;
    }

    public Document(String access_key) {
        this.access_key = access_key;
    }

    public Integer getId() {
        return id;
    }

    public void save() {

        String sql = "`document` SET";
        sql += " `content`=?";
        sql += " `access_key`=?";

        if (isNew()) {
            sql = "INSERT INTO " + sql;
        } else {
            sql = "UPDATE " + sql + " where `id`=?";
        }

        try {
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString (1, this.content);
            preparedStatement.setString (2, this.access_key);

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
