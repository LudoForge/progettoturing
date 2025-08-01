package org.turing.gui;

import org.turing.model.GridPersone;
import org.turing.model.Persona;
import org.turing.repository.RubricaRepository;
import org.turing.service.PersonaService;
import org.turing.storage.RubricaStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Definisce l'interfaccia e le azioni all'interno di essa
 */
public class RubricaGui extends JFrame {

    private final List<Persona> rubrica;
    private final JTable tabellaPersone;
    private final GridPersone gridPersone;

    public RubricaGui() {
        setTitle("Rubrica Telefonica");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Persona> caricate;
        try {
            RubricaRepository repository = new RubricaRepository();
            caricate = repository.getAllPersone();
        } catch (Exception e) {
            caricate = new ArrayList<>();
            System.out.println("Non ci sono persone in rubrica");
        }
        rubrica = caricate;
        gridPersone = new GridPersone(rubrica);
        tabellaPersone = new JTable(gridPersone);
        tabellaPersone.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabellaPersone), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton nuovoButton = new JButton("Nuovo");
        JButton modificaButton = new JButton("Modifica");
        JButton eliminaButton = new JButton("Elimina");

        buttonPanel.add(nuovoButton);
        buttonPanel.add(modificaButton);
        buttonPanel.add(eliminaButton);
        add(buttonPanel, BorderLayout.SOUTH);

        nuovoButton.addActionListener(e -> creaNuovaPersona());
        modificaButton.addActionListener(e -> modificaPersonaSelezionata());
        eliminaButton.addActionListener(e -> eliminaPersonaSelezionata());
    }

    /**
     * Esegue l'azione di inseirmento della persona sull'interfaccia
     */
    private void creaNuovaPersona() {
        PersonaService editor = new PersonaService(this, null);
        editor.setVisible(true);

        if (editor.isSalvato()) {
            Persona nuovaPersona = editor.getPersona();
            rubrica.add(nuovaPersona);
            try {
                RubricaRepository repository = new RubricaRepository();
                repository.inserisciPersona(nuovaPersona);
                RubricaStorage.aggiungiPersona(nuovaPersona);
                gridPersone.fireTableDataChanged();
            } catch (Exception e) {
                System.out.println("Inserimento della persona non eseguito " + e);
                throw new RuntimeException();
            }
            System.out.println("inserito/a in rubrica " + nuovaPersona.getNome());
        }
    }

    /**
     * Modifica la persona sull'interfaccia
     */
    private void modificaPersonaSelezionata() {
        int rigaSelezionata = tabellaPersone.getSelectedRow();
        System.out.println("modifica sulla riga");
        if (rigaSelezionata == -1) {
            System.out.println("seleziona una persona da modificare");
            JOptionPane.showMessageDialog(this, "Per modificare è necessario prima selezionare una persona.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("persona non selezionata");
        }

        Persona personaDaModificare = rubrica.get(rigaSelezionata);
        PersonaService personaService = new PersonaService(this, personaDaModificare);
        personaService.setVisible(true);

        if (personaService.isSalvato() && personaService.isModifica()) {
            try {
                Persona personaModificata = personaService.getPersona();
                rubrica.set(rigaSelezionata, personaModificata);
                RubricaRepository repository = new RubricaRepository();
                repository.aggiornaPersona(rigaSelezionata + 1, personaModificata);
                RubricaStorage.aggiornaPersonaInRiga(rigaSelezionata, personaModificata, rubrica);
                gridPersone.fireTableRowsUpdated(rigaSelezionata, rigaSelezionata);
            } catch (Exception e) {
                System.out.println("Aggiornamento della persona non eseguito");
                throw new RuntimeException(e);
            }
            System.out.println("modificata in rubrica " + personaDaModificare.getNome());
        }
    }

    /**
     * Elimina la persona sull'interfaccia
     */
    private void eliminaPersonaSelezionata() {
        int rigaSelezionata = tabellaPersone.getSelectedRow();
        System.out.println("riga da eliminare " + rigaSelezionata);

        if (rigaSelezionata == -1) {
            System.out.println("Seleziona una persona da eliminare");
            JOptionPane.showMessageDialog(this, "Per eliminare è necessario prima selezionare una persona.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("persona non selezionata");
        }

        Persona personaDaEliminare = rubrica.get(rigaSelezionata);
        int scelta = JOptionPane.showConfirmDialog(
                this,"Eliminare la persona " + personaDaEliminare.
                        getNome() + " " + personaDaEliminare.getCognome() + "?",
                "Conferma Eliminazione", JOptionPane.YES_NO_OPTION
        );

        if (scelta == JOptionPane.YES_OPTION) {
            try {
                RubricaRepository repository = new RubricaRepository();
                repository.eliminaPersona(rigaSelezionata + 1);
                rubrica.remove(rigaSelezionata);
                RubricaStorage.sovrascriviFile(rubrica);
                gridPersone.fireTableDataChanged();
            } catch (Exception e) {
                System.out.println("Eliminazione dell'utente non eseguita");
                throw new RuntimeException(e);
            }
            System.out.println("eliminato/a dalla rubrica " + personaDaEliminare.getNome());
        }
    }
}