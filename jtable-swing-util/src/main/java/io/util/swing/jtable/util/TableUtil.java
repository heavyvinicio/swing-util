/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import io.util.swing.jtable.component.JTableModel;
import io.util.swing.jtable.util.rendered.DefaultRender;
import io.util.swing.jtable.util.rendered.TableButtonCellRender;
import io.util.swing.jtable.util.rendered.TableHeaderCellRender;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * <b> Utility class for generic properties in {@link JTable} . </b>
 *
 * @author pocho
 * @version $Revision: 1.0 $
 * <p>
 * [$Author: pocho $, $Date: 22/09/2017 $]
 * </p>
 */
public final class TableUtil implements Serializable {

    private static final long serialVersionUID = 5612732890060901713L;

    private TableUtil() {
    }

    public static void filterByText(JTable table, final String text) {
        TableRowSorter<TableModel> modeloOrdenado = new TableRowSorter<>(table.getModel());
        table.setRowSorter(modeloOrdenado);
        modeloOrdenado.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }

    /**
     * <b> Method for apply generic changes in {@link JTable}. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param table {@link JTable}
     */
    public static void applyGenericTableProperties(JTable table) {
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setDefaultRenderer(new TableHeaderCellRender());
        table.setDefaultRenderer(Object.class, new DefaultRender());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setAutoCreateRowSorter(false);
        table.setPreferredScrollableViewportSize(table.getParent().getPreferredSize());
        table.getParent().addComponentListener(getAutoScrollTable(table));

        table.repaint();
    }

    public static void applyGenericTableProperties(JTable table, final Integer... columnButtons) {
        applyGenericTableProperties(table);
        TableButtonCellRender.renderedButtons(table, columnButtons);
        table.repaint();
    }

    public static ComponentAdapter getAutoScrollTable(final JTable table) {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (table.getPreferredSize().width < table.getParent().getWidth()) {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                } else {
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                }
            }

        };
    }

}
