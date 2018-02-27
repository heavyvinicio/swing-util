/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fochoac
 */
public class MultilineTextCellRender extends JTextArea implements TableCellRenderer {

    private static final long serialVersionUID = 8695700169021535411L;
    private final List<List<Integer>> rowColHeight;
    private Color backgroundColor1;
    private Color backgroundColor2;
    private Color backgroundColorTemp;
    private Color foregroundColor;
    private Integer[] rowsToPaint;
    private String lineSeparator;
    private Map<String, Color> mapa;

    public MultilineTextCellRender(Integer[] rowsToPain, Color backgroundColor, String separator) {
        this();
        this.rowsToPaint = rowsToPain;
        this.backgroundColorTemp = backgroundColor;
        this.lineSeparator = separator;
    }

    public MultilineTextCellRender() {
        mapa = new HashMap<>();
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false);
//        setFont(new Font("Arial", 0, 12));
        this.foregroundColor = new Color(125, 125, 125);
        setAlignmentY(0.5F);
        setAlignmentX(0.5F);
        this.backgroundColor1 = Color.WHITE;
        this.backgroundColor2 = new Color(250, 250, 250);
        this.rowColHeight = new ArrayList();
        this.rowsToPaint = null;
        this.lineSeparator = "";
        this.rowsToPaint = null;
    }

    public MultilineTextCellRender(Color backgroundcolor) {
        this();
        this.backgroundColorTemp = backgroundcolor;
    }

    public MultilineTextCellRender(Integer[] rowsToPain, Color backgroundColor) {
        this(backgroundColor);
        this.rowsToPaint = rowsToPain;
    }

    public void changeBackgroundColorByText(Map<String, Color> map) {
        this.mapa = map;
    }

    private void change(String value) {
        Color backgroundColor = mapa.get(value);
        if (backgroundColor != null) {
            setBackground(backgroundColor);
        }

    }

    private void alternateBackgroungColor(int row) {
        if ((this.rowsToPaint != null) && (containsRowValue(row))) {
            setBackground(this.backgroundColorTemp);
            return;
        }
        if ((this.rowsToPaint == null) && (this.backgroundColorTemp != null)) {
            setBackground(this.backgroundColorTemp);
            return;
        }
        if (row % 2 == 0) {
            setBackground(this.backgroundColor1);
        } else {
            setBackground(this.backgroundColor2);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setForeground(this.foregroundColor);
        setText(value.toString() + getLineSeparator());
        alternateBackgroungColor(row);
        if (isSelected) {
            setBackground(new Color(3, 169, 244));
            setForeground(this.backgroundColor1);
        }

        setBorder(table.getBorder());
        adjustRowHeight(table, row, column);
        if (!mapa.isEmpty()) {
            change(value.toString());
        }
        return this;
    }

    private void adjustRowHeight(JTable table, int row, int column) {
        int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
        setSize(new Dimension(cWidth, 1000));
        int prefH = getPreferredSize().height;
        while (this.rowColHeight.size() <= row) {
            this.rowColHeight.add(new ArrayList(column));
        }
        List<Integer> colHeights = (List) this.rowColHeight.get(row);
        while (colHeights.size() <= column) {
            colHeights.add(Integer.valueOf(0));
        }
        colHeights.set(column, Integer.valueOf(prefH));
        int maxH = prefH;
        for (Integer colHeight : colHeights) {
            if (colHeight.intValue() > maxH) {
                maxH = colHeight.intValue();
            }
        }
        if (table.getRowHeight(row) != maxH) {
            table.setRowHeight(row, maxH);
        }
    }

    public void changeBackgroungAllColumns(Color color) {
        backgroundColorTemp = color;
        this.backgroundColor1 = backgroundColorTemp;
        this.backgroundColor2 = backgroundColorTemp;
    }

    public void changeBackgroungOverColumns(Color color, Integer[] rows, Integer[] cols) {
        // this.bacgroundColor1 = color;
        // this.bacgroundColor2 = color;
        this.backgroundColorTemp = color;
        this.rowsToPaint = rows;
        //this.colums = cols;
    }

    private boolean containsRowValue(int row) {
        for (int i = 0; i < this.rowsToPaint.length; i++) {
            if (this.rowsToPaint[i].intValue() == row) {
                return true;
            }
        }
        return false;
    }

    public void setBackgroundColorTemp(Color backgroundColorTemp) {
        this.backgroundColorTemp = backgroundColorTemp;
    }

    public void setRowsTopaint(Integer[] rowsTopaint) {
        this.rowsToPaint = rowsTopaint;
    }

    public Color getBackgroundColorTemp() {
        return this.backgroundColorTemp;
    }

    public Integer[] getRowsTopaint() {
        return this.rowsToPaint;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
}
