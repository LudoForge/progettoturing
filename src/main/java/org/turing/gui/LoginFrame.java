package org.turing.gui;

import org.turing.auth.Autenticazione;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    /**
     * Configura il login della gui
     */
    public LoginFrame() {
        setTitle("Login Rubrica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(e -> login());
        panel.add(new JLabel());
        panel.add(loginButton);

        setContentPane(panel);
    }

    /**
     * Esegue il login
     */
    private void login() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (Autenticazione.verificaLogin(user, pass)) {
            dispose();
            SwingUtilities.invokeLater(() -> new RubricaGui().setVisible(true));
        } else {
            System.out.println("Credenziali errate per utente " + user);
            JOptionPane.showMessageDialog(this, "Login errato", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}