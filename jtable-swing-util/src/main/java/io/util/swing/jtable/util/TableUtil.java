/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.JTable;

/**
 *
 * @author fochoac
 */
public final class TableUtil implements Serializable {

    private static final long serialVersionUID = 5612732890060901713L;

    private TableUtil() {
    }

    public static void applyGenericTableProperties(JTable table) {
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.setGridColor(new Color(0, 128, 0));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        Dimension d = table.getTableHeader().getPreferredSize();
        d.setSize(d.getWidth(), d.getHeight() + 10);
        table.getTableHeader().setPreferredSize(d);
        table.getTableHeader().setBackground(Color.BLUE);
        ColumnUtil.getInstance().setBackgroundColor(Color.white).apply(table);
    }
}
