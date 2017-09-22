/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import io.util.swing.jtable.component.JTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author fochoac
 */
public class EventUtil implements Serializable {

    private static final long serialVersionUID = -7488573098968767452L;

    private EventUtil() {
        super();
    }

    public static final void executeClick(MouseEvent event, final JTable table) {
        int column = table.getColumnModel().getColumnIndexAtX(event.getX());
        int row = event.getY() / table.getRowHeight();
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            System.out.println("Value :" + value.getClass().getName());
            if (value instanceof JButton) {
                ((JButton) value).doClick();
            }

        }

    }

    public static final Object getObjectFromClick(MouseEvent event, final JTable table) {
        int row = table.rowAtPoint(event.getPoint());
        int row2 = table.convertRowIndexToModel(row);
        return ((JTableModel) table.getModel()).get(row2);
    }

}
