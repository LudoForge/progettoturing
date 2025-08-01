package org.turing.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe per la configurazione del database
 */
public class DBConfig {
    private static final String PROPERTIES_FILE = "application.properties";

    /**
     * Carica le informazioni di connessione al databse mysql
     * @return properties
     * @throws IOException se file non trovato
     */
    public static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = DBConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Impossibile caricare le properties");
                throw new IOException("File application.properties non trovato nel classpath");
            }
            props.load(input);
        }
        return props;
    }

    /**
     * Esegue la connessione al db
     * @return connection
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws IOException, SQLException {
        Properties props = loadProperties();
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
        );
    }

    /**
     * Crea il db se non esiste
     * @param sqlFile script di creazione db
     */
    public static void createDatabaseIfNotExists(String sqlFile) {
        String url = "jdbc:mysql://localhost:3306/?user=root&password=123456";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             InputStream is = DBConfig.class.getClassLoader().getResourceAsStream(sqlFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }

            for (String sql : sqlBuilder.toString().split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }

            System.out.println("Database e tabella creati correttamente da script.");
        } catch (Exception e) {
            System.out.println("qualcosa è andato storto" + e);
            throw new RuntimeException(e);
        }
    }

}
