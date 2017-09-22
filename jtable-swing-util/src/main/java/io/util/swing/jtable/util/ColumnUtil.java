/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <b> Incluir aqui la descripcion de la clase. </b>
 * 
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: pocho $, $Date: 22/09/2017 $]
 *          </p>
 */
public class ColumnUtil implements Serializable {
    private static final long serialVersionUID = -4218822414304154875L;
    private final DefaultTableCellRenderer cellRenderer;

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     */
    private ColumnUtil() {
        cellRenderer = new DefaultTableCellRenderer();
    }

    /**
     * <b> Method for get a new instance. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @return {@link ColumnUtil}
     */
    public static ColumnUtil getInstance() {
        return new ColumnUtil();
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param table
     *            {@link JTable}
     * @param columns
     *            columns indexs
     */
    public final void apply(JTable table, int... columns) {
        for (Integer i : columns) {
            table.getColumnModel().getColumn(i).setCellRenderer(this.cellRenderer);
        }
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param table
     *            {@link JTable}
     */
    public final void apply(JTable table) {
        Enumeration<TableColumn> list = table.getColumnModel().getColumns();
        while (list.hasMoreElements()) {
            list.nextElement().setCellRenderer(this.cellRenderer);
        }
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param color
     *            {@link Color}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setBackgroundColor(Color color) {
        this.cellRenderer.setBackground(color);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param alignCode
     *            {@link SwingUtilities}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setVerticalAlignment(int alignCode) {
        this.cellRenderer.setVerticalAlignment(alignCode);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param alignCode
     *            {@link SwingUtilities} RIGHT, LEFT, CENTER
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setHorizontalAlignment(int alignCode) {
        this.cellRenderer.setHorizontalAlignment(alignCode);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param font
     *            {@link Font}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setFont(Font font) {
        this.cellRenderer.setFont(font);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param border
     *            {@link Border}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setBorder(Border border) {
        this.cellRenderer.setBorder(border);
        return this;
    }
}
