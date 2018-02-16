/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fochoac
 */
public class MultilineTextCellRender extends JTextArea implements TableCellRenderer {

    private static final long serialVersionUID = 8695700169021535411L;
    private final List<List<Integer>> rowColHeight = new ArrayList<>();
    private Color bacgroundColor1 = Color.WHITE;
    private Color bacgroundColor2 = new Color(250, 250, 250);
    private Integer[] rows = null;
    private Integer[] colums = null;
    private Color colorSelected;
    private boolean agregarLinea = false;
    private String linea = "";

    public void changeBackgroungAllColumns(Color color) {
        colorSelected = color;
        this.bacgroundColor1 = colorSelected;
        this.bacgroundColor2 = colorSelected;
    }

    public void changeBackgroungOverColumns(Color color, Integer[] rows, Integer[] cols) {
//        this.bacgroundColor1 = color;
//        this.bacgroundColor2 = color;
        this.colorSelected = color;
        this.rows = rows;
        this.colums = cols;
    }

    public MultilineTextCellRender() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    private void alternateBackgroungColor(int row) {

        if (row % 2 == 0) {
            setBackground(bacgroundColor1);
        } else {
            setBackground(bacgroundColor2);
        }
    }

    public void agregarNuevaLinea(boolean agregar) {
        if (agregar) {
            this.linea = "\n";
        } else {
            this.linea = "";
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        if (table.getColumnCount() > 0) {
//            return this;
//        }
//        for (int i = 0; i < table.getColumnCount(); i++) {
//            table.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderCellRender());
//        }
        setText(value == null ? "" : value.toString() + linea);
        setEditable(false);
        setFont(new java.awt.Font("Arial", 0, 12));
        setForeground(new java.awt.Color(125, 125, 125));
        setAlignmentY(CENTER_ALIGNMENT);
        setAlignmentX(CENTER_ALIGNMENT);
        if (rows == null || colums == null) {
            alternateBackgroungColor(row);
        } else if (rows != null && Arrays.asList(rows).contains(row)) {
            setBackground(colorSelected);
        } else if (colums != null && Arrays.asList(colums).contains(column)) {
            setBackground(colorSelected);
        }

        if (isSelected) {
            setBackground(new Color(3, 169, 244));
            setForeground(bacgroundColor1);
        }
        setBorder(table.getBorder());
        adjustRowHeight(table, row, column);
        // updateRowHeights(table);

        return this;
    }

//    private int getMaxcRowHeight(JTable table) {
//        for (int row = 0; row < table.getRowCount(); row++) {
//            int rowHeight = table.getRowHeight();
//
//            for (int column = 0; column < table.getColumnCount(); column++) {
//                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
//                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
//            }
//
//            return rowHeight;
//        }
//        return getPreferredSize().height;
//    }
    private void adjustRowHeight(JTable table, int row, int column) {

        int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
        setSize(new Dimension(cWidth, 1000));

        int prefH = getPreferredSize().height;//+ suma;
        while (rowColHeight.size() <= row) {
            rowColHeight.add(new ArrayList<Integer>(column));
        }
        List<Integer> colHeights = rowColHeight.get(row);
        while (colHeights.size() <= column) {
            colHeights.add(0);
        }
        colHeights.set(column, prefH);
        int maxH = prefH;
        for (Integer colHeight : colHeights) {
            if (colHeight > maxH) {
                maxH = colHeight;
            }
        }
        if (table.getRowHeight(row) != maxH) {
            table.setRowHeight(row, maxH);
        }
    }

}
