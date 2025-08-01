package org.turing.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GridPersone extends AbstractTableModel {

    private final List<Persona> persone;
    private final String[] columnNames = {"Nome", "Cognome", "Telefono"};

    public GridPersone(List<Persona> persone) {
        this.persone = persone;
    }

    @Override
    public int getRowCount() {
        return persone.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona persona = persone.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> persona.getNome();
            case 1 -> persona.getCognome();
            case 2 -> persona.getTelefono();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}