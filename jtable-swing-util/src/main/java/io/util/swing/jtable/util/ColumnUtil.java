/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import io.util.swing.jtable.util.rendered.DefaultRender;
import io.util.swing.jtable.util.rendered.MultilineTextCellRender;
import io.util.swing.jtable.util.rendered.TableButtonCellRender;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <b> Incluir aqui la descripcion de la clase. </b>
 *
 * @author pocho
 * @version $Revision: 1.0 $
 * <p>
 * [$Author: pocho $, $Date: 22/09/2017 $]
 * </p>
 */
public class ColumnUtil implements Serializable {

    private static final long serialVersionUID = -4218822414304154875L;
    private JTable table;
    Map<String, Object> options;

    public ColumnUtil() {
        this.options = new HashMap<>();
    }

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     */
    private ColumnUtil(JTable table) {
        this();
        this.table = table;

    }

    public static ColumnUtil getInstance(JTable table) {
        ColumnUtil cu = MyWrapper.INSTANCE;
        cu.table = table;
        return cu;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param table {@link JTable}
     * @param columns columns indexs
     */
    public final void applyOverColumns(int... columns) {
        List<TableCellRenderer> listRenders = new ArrayList<>();
        for (Integer i : columns) {
            TableColumn column = table.getColumnModel().getColumn(i);
            listRenders.add(getCellRender(column));

        }
        applyProperties(listRenders);

    }

    private TableCellRenderer getCellRender(TableColumn column) {

        TableCellRenderer render = column.getCellRenderer();
        if (render == null) {
            render = table.getDefaultRenderer(table.getColumnClass(column.getModelIndex()));
        }
        if (options.containsKey("MULTI_LINE") && (boolean) options.get("MULTI_LINE") && !(render instanceof TableButtonCellRender) && !(render instanceof JCheckBox) && !(render instanceof JTextArea)) {
            render = new MultilineTextCellRender();
            column.setCellRenderer(render);
        }
        return render;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param table {@link JTable}
     */
    public final void applyAllColumns() {
        List<TableCellRenderer> listRenders = new ArrayList<>();
        Enumeration<TableColumn> list = table.getColumnModel().getColumns();
        while (list.hasMoreElements()) {
            TableColumn column = list.nextElement();
            listRenders.add(getCellRender(column));
        }
        applyProperties(listRenders);

    }

    private void invalidateData() {
        this.options = new HashMap<>();
        this.table = null;
    }

    private void applyProperties(List<TableCellRenderer> list) {

        for (TableCellRenderer render : list) {

            for (Map.Entry<String, Object> item : options.entrySet()) {

                if (render instanceof JLabel) {
                    DefaultRender r = (DefaultRender) render;

                    if ("BACKGROUND".equals(item.getKey())) {
                        r.changeBackgroundColor(null, (Color) item.getValue());

                    } else if ("V_ALIGN".equals(item.getKey())) {
                        r.setVerticalAlignment((int) item.getValue());
                    } else if ("H_ALIGN".equals(item.getKey())) {
                        r.setHorizontalAlignment((int) item.getValue());
                    } else if ("FONT".equals(item.getKey())) {
                        r.setFont((Font) item.getValue());
                    } else if ("BORDER".equals(item.getKey())) {
                        r.setBorder((Border) item.getValue());
                    }
                } else if (render instanceof JTextArea) {
                    MultilineTextCellRender r = (MultilineTextCellRender) render;
                    if ("BACKGROUND".equals(item.getKey())) {
                        r.changeBackgroungAllColumns((Color) item.getValue());
                    } else if ("V_ALIGN".equals(item.getKey())) {

                    } else if ("H_ALIGN".equals(item.getKey())) {

                    } else if ("FONT".equals(item.getKey())) {
                        r.setFont((Font) item.getValue());
                    } else if ("BORDER".equals(item.getKey())) {
                        r.setBorder((Border) item.getValue());
                    } else if ("MULTI_LINE_NEW_LINE".equals(item.getKey())) {
                        r.agregarNuevaLinea(true);
                    }

                } else if (render instanceof JButton) {
                    TableButtonCellRender r = (TableButtonCellRender) render;
                    if ("BACKGROUND".equals(item.getKey())) {
                        r.getRenderButton().setBackground((Color) item.getValue());
                    } else if ("V_ALIGN".equals(item.getKey())) {

                    } else if ("H_ALIGN".equals(item.getKey())) {

                    } else if ("FONT".equals(item.getKey())) {
                        r.getRenderButton().setFont((Font) item.getValue());
                    } else if ("BORDER".equals(item.getKey())) {
                        r.getRenderButton().setBorder((Border) item.getValue());
                    }
                } else if (render instanceof JCheckBox) {

                }
            }

        }

        table.repaint();
        invalidateData();
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param color {@link Color}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setBackgroundColor(Color color) {
        options.put("BACKGROUND", color);
//  this.cellRenderer.setBackground(color);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param alignCode {@link SwingUtilities}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setVerticalAlignment(int alignCode) {
        options.put("V_ALIGN", alignCode);

        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param alignCode {@link SwingUtilities} RIGHT, LEFT, CENTER
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setHorizontalAlignment(int alignCode) {
        options.put("H_ALIGN", alignCode);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param font {@link Font}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setFont(Font font) {
        options.put("FONT", font);
// this.cellRenderer.setFont(font);
        return this;
    }

    public final ColumnUtil makeMultiLine() {

        options.put("MULTI_LINE", true);
        return this;
    }

    public final ColumnUtil addNewLineMultipleLIne() {
        options.put("MULTI_LINE_NEW_LINE", true);
        return this;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param border {@link Border}
     * @return {@link ColumnUtil}
     */
    public final ColumnUtil setBorder(Border border) {
        options.put("BORDER", border);
// this.cellRenderer.setBorder(border);
        return this;
    }

    private static class MyWrapper {

        public static final ColumnUtil INSTANCE = new ColumnUtil();
    }

}
