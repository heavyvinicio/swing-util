/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JTable;

import io.util.swing.jtable.component.JTableModel;

/**
 * <b> Utility class for execute actions in {@link JTable}. </b>
 * 
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: pocho $, $Date: 22/09/2017 $]
 *          </p>
 */
public class EventUtil implements Serializable {
    private static final long serialVersionUID = -7488573098968767452L;

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     */
    private EventUtil() {
        super();
    }

    /**
     * <b> Method for execute click event in {@link JButton} from
     * {@link JTable}. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param event
     *            {@link MouseEvent}
     * @param table
     *            {@link JTable}
     */
    public static final void executeClick(MouseEvent event, final JTable table) {
        int column = table.getColumnModel().getColumnIndexAtX(event.getX());
        int row = event.getY() / table.getRowHeight();
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
            }
        }
    }

    /**
     * <b> Method for get object from click event in row from
     * {@link JTable}. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param event
     *            {@link MouseEvent}
     * @param table
     *            {@link JTable}
     * @return {@link Object} value
     */
    @SuppressWarnings("rawtypes")
    public static final Object getObjectFromClick(MouseEvent event, final JTable table) {
        int row = table.rowAtPoint(event.getPoint());
        int row2 = table.convertRowIndexToModel(row);
        return ((JTableModel) table.getModel()).get(row2);
    }
}
