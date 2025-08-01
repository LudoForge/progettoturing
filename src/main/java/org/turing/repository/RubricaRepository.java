package org.turing.repository;

import org.turing.config.DBConfig;
import org.turing.model.Persona;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Esegue le crud. Se si decide di farla in Spring attenzione al
 * rispetto dell'iniezione delle dipendenze
 */
public class RubricaRepository {

    private Connection conn;

    public RubricaRepository() throws SQLException, IOException {
        conn = DBConfig.getConnection();
    }

    public List<Persona> getAllPersone() throws SQLException {
        List<Persona> persone = new ArrayList<>();
        String sql = "SELECT nome, cognome, indirizzo, telefono, eta FROM persona";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                persone.add(new Persona(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                ));
            }
        }

        return persone;
    }

    public void inserisciPersona(Persona p) throws SQLException {
        String sql = "INSERT INTO persona (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.executeUpdate();
        }
    }

    public void aggiornaPersona(int id, Persona p) throws SQLException {
        String sql = "UPDATE persona SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setString(3, p.getIndirizzo());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getEta());
            ps.setInt(6, id);
            ps.executeUpdate();
        }
    }

    public void eliminaPersona(int id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

