/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author fochoac
 */
public class DefaultRender extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 6714620316728398240L;

    private Color backgroundColor1;
    private Color backgroundColorTemp;
    private Color backgroundColor2;
    private Color foregroundColor;
    private Integer[] rowsTopaint;
    private Map<String, Color> mapa;

    public DefaultRender() {
        setOpaque(true);
        this.backgroundColor1 = Color.white;
        this.backgroundColor2 = new Color(250, 250, 250);
        this.foregroundColor = new Color(125, 125, 125);
        this.rowsTopaint = null;
        mapa = new HashMap<>();
    }

    public DefaultRender(Color backgroundcolor) {
        this();
        this.backgroundColorTemp = backgroundcolor;
    }

    public DefaultRender(Integer[] rowsToPaint, Color backgroundcolor) {
        this(backgroundcolor);
        this.rowsTopaint = rowsToPaint;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setForeground(this.foregroundColor);
        setFont(new Font("Arial", 0, 12));
        alternateBackgroungColor(row);
        if (isSelected) {
            setBackground(new Color(3, 169, 244));
            setForeground(this.backgroundColor1);
        }
        if (!mapa.isEmpty()) {
            change(value.toString());
        }
        setBorder(table.getBorder());
        return c;
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
        if ((getRowsTopaint() != null) && (containsRowValue(row))) {
            setBackground(getBackgroundColorTemp());
            return;
        }
        if ((getRowsTopaint() == null) && (getBackgroundColorTemp() != null)) {
            setBackground(getBackgroundColorTemp());
            return;
        }
        if (row % 2 == 0) {
            setBackground(this.backgroundColor1);
        } else {
            setBackground(this.backgroundColor2);
        }
    }

    private boolean containsRowValue(int row) {
        for (int i = 0; i < getRowsTopaint().length; i++) {
            if (getRowsTopaint()[i].intValue() == row) {
                return true;
            }
        }
        return false;
    }

    public void setBackgroundColorTemp(Color backgroundColorTemp) {
        this.backgroundColorTemp = backgroundColorTemp;
    }

    public void setRowsTopaint(Integer[] rowsTopaint) {
        this.rowsTopaint = rowsTopaint;
    }

    public Color getBackgroundColorTemp() {
        return this.backgroundColorTemp;
    }

    public Integer[] getRowsTopaint() {
        return this.rowsTopaint;
    }

}
