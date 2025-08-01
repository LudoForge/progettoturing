package org.turing;

import org.turing.config.DBConfig;
import org.turing.gui.LoginFrame;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DBConfig.createDatabaseIfNotExists("schema_database.sql");
        try (Connection conn = DBConfig.getConnection()) {
            System.out.println("Connessione riuscita al database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}

