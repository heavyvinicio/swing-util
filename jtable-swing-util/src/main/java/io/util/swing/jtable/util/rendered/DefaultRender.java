/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author fochoac
 */
public class DefaultRender extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 6714620316728398240L;

    private final Color bacgroundColor1 = Color.WHITE;
    private final Color bacgroundColor2 = new Color(250, 250, 250);
    private Color backgroungColor3 = null;
    private Color foregroungColor3 = null;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        setFont(new java.awt.Font("Arial", 0, 12));
        setForeground(new java.awt.Color(125, 125, 125));
        alternateBackgroungColor(row);
        if (isSelected) {
            setBackground(new Color(3, 169, 244));
            setForeground(bacgroundColor1);
        }
        setBorder(table.getBorder());

        // adjustRowHeight(table, row, column);
        return c;
    }

    public void changeBackgroundColor(Color foreground, Color background) {
        this.foregroungColor3 = foreground;
        this.backgroungColor3 = background;
    }

    private void alternateBackgroungColor(int row) {
        if (backgroungColor3 != null) {
            setBackground(backgroungColor3);
            if (foregroungColor3 != null) {
                setForeground(foregroungColor3);
            }
            return;
        }
        if (row % 2 == 0) {
            setBackground(bacgroundColor1);
        } else {
            setBackground(bacgroundColor2);
        }
    }

}
