/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.forma;

import io.util.swing.jtable.util.rendered.DefaultRender;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;

/**
 *
 * @author fochoac
 */
public class ColorRender extends DefaultRender {

    private static final long serialVersionUID = -7404343469274169466L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
        c.setBackground(Color.red);
        return c;
    }

}
