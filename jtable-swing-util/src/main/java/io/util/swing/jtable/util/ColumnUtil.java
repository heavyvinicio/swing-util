/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author fochoac
 */
public class ColumnUtil implements Serializable {

    private static final long serialVersionUID = -4218822414304154875L;
    private final DefaultTableCellRenderer cellRenderer;

    private ColumnUtil() {
        cellRenderer = new DefaultTableCellRenderer();
    }

    public static ColumnUtil getInstance() {
        return new ColumnUtil();
    }

    public final void apply(JTable table, int... columns) {

        for (Integer i : columns) {
            table.getColumnModel().getColumn(i).setCellRenderer(this.cellRenderer);
        }
    }

    public final void apply(JTable table) {
        Enumeration<TableColumn> list = table.getColumnModel().getColumns();
        while (list.hasMoreElements()) {
            list.nextElement().setCellRenderer(this.cellRenderer);
        }
    }

    public final ColumnUtil setBackgroundColor(Color color) {
        this.cellRenderer.setBackground(color);
        return this;
    }

    public final ColumnUtil setVerticalAlignment(int alignCode) {
        this.cellRenderer.setVerticalAlignment(alignCode);
        return this;
    }

    /**
     *
     * @param alignCode
     * @return
     */
    public final ColumnUtil setHorizontalAlignment(int alignCode) {
        this.cellRenderer.setHorizontalAlignment(alignCode);
        return this;
    }

    public final ColumnUtil setFont(Font font) {
        this.cellRenderer.setFont(font);
        return this;
    }

    public final ColumnUtil setBorder(Border border) {
        this.cellRenderer.setBorder(border);
        return this;
    }

}
