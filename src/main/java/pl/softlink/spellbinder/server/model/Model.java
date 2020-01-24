package pl.softlink.spellbinder.server.model;
import pl.softlink.spellbinder.server.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Model {

    static {
        String createUserSql = "CREATE TABLE IF NOT EXISTS `user` (\n" +
            "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `email` varchar(255) CHARACTER SET utf8mb4 NOT NULL,\n" +
            "  `password` char(32) CHARACTER SET utf8mb4 NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `email_UNIQUE` (`email`),\n" +
            "  KEY `email_password_index` (`email`,`password`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;";

        String createDocumentSql = "CREATE TABLE IF NOT EXISTS `document` (\n" +
            "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `content` longtext COLLATE utf8mb4_polish_ci DEFAULT NULL,\n" +
            "  `name` varchar(127) COLLATE utf8mb4_polish_ci DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;";

        String createDocumentUserSql = "CREATE TABLE IF NOT EXISTS `user_document` (\n" +
            "  `user_id` int(10) unsigned NOT NULL,\n" +
            "  `document_id` int(10) unsigned NOT NULL,\n" +
            "  PRIMARY KEY (`user_id`,`document_id`),\n" +
            "  KEY `document_fk_idx` (`document_id`),\n" +
            "  CONSTRAINT `document_fk` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
            "  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

        try {
            String[] sqlArray = {createUserSql, createDocumentSql, createDocumentUserSql};
            for (String sql: sqlArray) {
                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract Integer getId();

    public boolean isNew() {
        return (getId() == null) ? true : false;
    }

}
