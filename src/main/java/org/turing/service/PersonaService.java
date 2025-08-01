package org.turing.service;

import org.turing.model.Persona;

import javax.swing.*;
import java.awt.*;

public class PersonaService extends JDialog {

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField indirizzoField;
    private JTextField telefonoField;
    private JTextField etaField;

    private final Persona persona;
    private boolean salvato = false;
    private final boolean modifica;

    public PersonaService(Frame owner, Persona persona) {
        super(owner, true);
        this.modifica = persona != null;
        this.persona = (persona != null) ? persona : new Persona("", "", "", "", 0);

        setTitle(modifica ? "Modifica Persona" : "Nuova Persona");
        setLayout(new BorderLayout());

        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nomeField = new JTextField(this.persona.getNome());
        cognomeField = new JTextField(this.persona.getCognome());
        indirizzoField = new JTextField(this.persona.getIndirizzo());
        telefonoField = new JTextField(this.persona.getTelefono());
        etaField = new JTextField(String.valueOf(this.persona.getEta()));

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Cognome:"));
        formPanel.add(cognomeField);
        formPanel.add(new JLabel("Indirizzo:"));
        formPanel.add(indirizzoField);
        formPanel.add(new JLabel("Telefono:"));
        formPanel.add(telefonoField);
        formPanel.add(new JLabel("Età:"));
        formPanel.add(etaField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvaButton = new JButton("Salva");
        JButton annullaButton = new JButton("Annulla");

        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);
        add(buttonPanel, BorderLayout.SOUTH);

        salvaButton.addActionListener(e -> salva());
        annullaButton.addActionListener(e -> dispose());
    }

    private void salva() {
        int eta;
        try {
            eta = Integer.parseInt(etaField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'età deve essere un numero intero.", "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("l'età non è valida");
        }

        this.persona.setNome(nomeField.getText());
        this.persona.setCognome(cognomeField.getText());
        this.persona.setIndirizzo(indirizzoField.getText());
        this.persona.setTelefono(telefonoField.getText());
        this.persona.setEta(eta);

        this.salvato = true;
        dispose();
    }

    public boolean isModifica() {
        return modifica;
    }

    public boolean isSalvato() {
        return salvato;
    }

    public Persona getPersona() {
        return persona;
    }
}