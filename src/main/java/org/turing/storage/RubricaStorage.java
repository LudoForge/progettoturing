package org.turing.storage;

import org.turing.model.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esegue le crud sul file informazioni.txt
 */
public class RubricaStorage {

    private static final String FILE_NAME = "informazioni.txt";

    /**
     * Legge il file e restituisce una lista di persone.
     * Ora si usa RubricaRepository.getAllPersone()
     */
    @Deprecated(forRemoval = true)
    public static List<Persona> caricaPersoneDaFile() {
        List<Persona> persone = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return persone;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] campi = linea.split(";");
                if (campi.length == 5) {
                    String nome = campi[0];
                    String cognome = campi[1];
                    String indirizzo = campi[2];
                    String telefono = campi[3];
                    int eta = Integer.parseInt(campi[4]);

                    Persona persona = new Persona(nome, cognome, indirizzo, telefono, eta);
                    persone.add(persona);
                }
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println("Qualcosa è andato storto");
            throw new RuntimeException("Caricamento persone non riuscito");
        }

        return persone;
    }

    public static void aggiungiPersona(Persona persona) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE_NAME, true))) {
            writer.println(persona.getNome() + ";" + persona.getCognome() + ";" + persona.getIndirizzo() + ";" + persona.getTelefono() + ";" + persona.getEta());
        } catch (IOException e) {
            System.out.println("inserimento non eseguito nel file");
            throw new RuntimeException("Inserimento non andato a buon fine");
        }
    }

    public static void sovrascriviFile(List<Persona> rubrica) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE_NAME, false))) {
            for (Persona persona : rubrica) {
                writer.println(persona.getNome() + ";" + persona.getCognome() + ";" + persona.getIndirizzo() + ";" + persona.getTelefono() + ";" + persona.getEta());
            }
        } catch (IOException e) {
            System.out.println("aggiornamento non eseguito nel file");
            throw new RuntimeException("aggiornamento non andato a buon fine");
        }
    }

    public static void aggiornaPersonaInRiga(int riga, Persona nuovaPersona, List<Persona> rubrica) {
        if (riga >= 0 && riga < rubrica.size()) {
            rubrica.set(riga, nuovaPersona);
            sovrascriviFile(rubrica);
        }
    }

    public static void eliminaPersonaInRiga(int riga, List<Persona> rubrica) {
        if (riga >= 0 && riga < rubrica.size()) {
            rubrica.remove(riga);
            sovrascriviFile(rubrica);
        }
    }


}