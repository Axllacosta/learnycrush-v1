package org.lc.v1.database;

import java.sql.*;

public class Query {
    private static final String DB_URL = "jdbc:sqlite:learny_crush.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void saveProgress(int level, int stars) {
        int currentStars = getStars(level);
        int maxStars = Math.max(currentStars, stars);

        String sql = "UPDATE progreso SET estrellas = ?, completado = 1 WHERE nivel = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maxStars);
            pstmt.setInt(2, level);
            pstmt.executeUpdate();

            if (level < 5 && maxStars > 0 && !isUnlocked(level + 1)) {
                String unlockSql = "UPDATE progreso SET desbloqueado = 1 WHERE nivel = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(unlockSql)) {
                    pstmt2.setInt(1, level + 1);
                    pstmt2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error guardando progreso del nivel " + level, e);
        }
    }

    public static int getStars(int level) {
        String sql = "SELECT estrellas FROM progreso WHERE nivel = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("estrellas") : 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo estrellas del nivel " + level, e);
        }
    }

    public static boolean isUnlocked(int level) {
        String sql = "SELECT desbloqueado FROM progreso WHERE nivel = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt("desbloqueado") == 1;
        } catch (SQLException e) {
            if (level == 1) return true;
            throw new RuntimeException("Error verificando desbloqueo del nivel " + level, e);
        }
    }

    public static int getLastUnlockedLevel() {
        String sql = "SELECT MAX(nivel) FROM progreso WHERE desbloqueado = 1";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error obteniendo último nivel desbloqueado", e);
        }
    }

    public static boolean isCompleted(int level) {
        String sql = "SELECT completado FROM progreso WHERE nivel = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt("completado") == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void resetProgress() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("UPDATE progreso SET estrellas = 0, completado = 0, desbloqueado = 0");
            stmt.executeUpdate("UPDATE progreso SET desbloqueado = 1 WHERE nivel = 1");
        } catch (SQLException e) {
            throw new RuntimeException("Error reiniciando el progreso", e);
        }
    }
}