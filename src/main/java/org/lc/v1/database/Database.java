package org.lc.v1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:learny_crush.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement statement = conn.createStatement()) {

            statement.execute("""
            
                    CREATE TABLE IF NOT EXISTS progreso (
            
                nivel INTEGER PRIMARY KEY,
            
                estrellas INTEGER NOT NULL DEFAULT 0,
            
                completado INTEGER NOT NULL DEFAULT 0,
            
                desbloqueado INTEGER NOT NULL DEFAULT 0
            
            )
            """);
            statement.execute("""
            
                    INSERT OR IGNORE INTO progreso
                            (nivel, estrellas, completado, desbloqueado)
            
                            VALUES
            
                            (1, 0, 0, 1),
                            (2, 0, 0, 0),
                            (3, 0, 0, 0),
                            (4, 0, 0, 0),
                            (5, 0, 0, 0)
            
            """);
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
        }
    }
}
