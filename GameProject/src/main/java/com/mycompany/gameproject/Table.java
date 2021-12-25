package com.mycompany.gameproject;

import de.vandermeer.asciitable.AsciiTable;
import java.util.ArrayList;

public class Table {

    private int column;
    private int row;

    public Table(int numb) {
        this.column = numb;
        this.row = numb;
    }

    public void GenerateTable(String[] content) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Table.GenerateHeader(content));
        for (int i = 0; i < row; i++) {
            table.addRule();
            table.addRow(Table.GenerateRow(i, column, content[i]));
        }
        table.addRule();
        String rend = table.render();
        System.out.println(rend);
    }

    private static ArrayList<String> GenerateHeader(String[] headers) {
        ArrayList<String> header = new ArrayList<>();
        header.add("");
        for (int i = 0; i < headers.length; i++) {
            header.add(headers[i]);
        }
        return header;
    }

    private static ArrayList<String> GenerateRow(int rowIndex, int column, String header) {
        ArrayList<String> newRow = new ArrayList<>();
        newRow.add(header);
        GameRule rule = new GameRule(column);
        for (int i = 0; i < column; i++) {
            switch (rule.getWinner(rowIndex, i)) {
                case 0:
                    newRow.add("Win");
                    break;
                case 3:
                    newRow.add("Draw");
                    break;
                case -1:
                    newRow.add("Lose");
                    break;
            }
        }
        return newRow;
    }
}
