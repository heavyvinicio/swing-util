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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
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

    private static final ColumnUtil INSTANCE = new ColumnUtil();
    private static final long serialVersionUID = -4218822414304154875L;
    private JTable table;
    Map<String, Object> options;
    Map<Integer, Object> columns;
    Map<String, Color> mapa;

    private ColumnUtil() {
        this.options = new HashMap();
        this.columns = new HashMap();
        this.mapa = new HashMap();
    }

    private ColumnUtil(JTable table) {
        this();
        this.table = table;
    }

    public static ColumnUtil getInstance(JTable table) {
        ColumnUtil cu = INSTANCE;
        cu.table = table;
        return cu;
    }

    public final ColumnUtil setBackgroundColor(Color color) {
        this.options.put("BACKGROUND", color);
        return this;
    }

    public final ColumnUtil setVerticalAlignment(int alignCode) {
        this.options.put("V_ALIGN", Integer.valueOf(alignCode));
        return this;
    }

    public final ColumnUtil setHorizontalAlignment(int alignCode) {
        this.options.put("H_ALIGN", Integer.valueOf(alignCode));
        return this;
    }

    public final ColumnUtil setFont(Font font) {
        this.options.put("FONT", font);
        return this;
    }

    public final ColumnUtil makeMultiLine() {
        this.options.put("MULTI_LINE", Boolean.valueOf(true));
        return this;
    }

    public final ColumnUtil addNewLine() {
        this.options.put("MULTI_LINE_NEW_LINE_SEPARATOR", Boolean.valueOf(true));
        return this;
    }

    public final ColumnUtil setBorder(Border border) {
        this.options.put("BORDER", border);
        return this;
    }

    public final void applyOverColumns(int... columns) {
        this.columns.clear();
        int[] arrayOfInt = columns;
        int longitud = arrayOfInt.length;
        for (int j = 0; j < longitud; j++) {
            Integer i = Integer.valueOf(arrayOfInt[j]);
            TableColumn column = this.table.getColumnModel().getColumn(i.intValue());
            this.columns.put(i, getCellRender(column));
        }

    }

    private TableCellRenderer getCellRender(TableColumn column) {
        TableCellRenderer render = column.getCellRenderer();
        if (render == null) {
            render = this.table.getDefaultRenderer(this.table.getColumnClass(column.getModelIndex()));
        }
        if ((this.options.containsKey("MULTI_LINE")) && (((Boolean) this.options.get("MULTI_LINE")).booleanValue()) && (!(render instanceof TableButtonCellRender)) && (!(render instanceof JCheckBox)) && (!(render instanceof JTextArea))) {
            DefaultRender r = (DefaultRender) render;
            render = new MultilineTextCellRender(r.getRowsTopaint(), r.getBackgroundColorTemp());
        }
        return render;
    }

    public final void applyAllColumns() {
        this.columns.clear();
        for (int i = 0; i < this.table.getColumnCount(); i++) {
            TableColumn column = this.table.getColumnModel().getColumn(i);
            this.columns.put(Integer.valueOf(i), getCellRender(column));
        }
        applyProperties();
    }

    public ColumnUtil changeBackgroundColorByText(Map<String, Color> mapa) {
        this.mapa = mapa;
        return this;
    }

    private void invalidateData() {
        this.options = new HashMap();
        this.table = null;
    }

    private void applyProperties() {
        for (Map.Entry<Integer, Object> column : this.columns.entrySet()) {
            if ((column.getValue() instanceof JLabel)) {
                DefaultRender render = (DefaultRender) column.getValue();
                applyJLabelProperties(render);
                if (!mapa.isEmpty()) {
                    render.changeBackgroundColorByText(mapa);
                }
                this.table.getColumnModel().getColumn(((Integer) column.getKey()).intValue()).setCellRenderer(render);
            } else if ((column.getValue() instanceof JTextArea)) {
                MultilineTextCellRender render = (MultilineTextCellRender) column.getValue();
                applyJTextAreaProperties(render);
                if (!mapa.isEmpty()) {
                    render.changeBackgroundColorByText(mapa);
                }
                this.table.getColumnModel().getColumn(((Integer) column.getKey()).intValue()).setCellRenderer(render);
            }
        }
        this.table.repaint();
        invalidateData();
    }

    private void applyJLabelProperties(DefaultRender render) {
        for (Map.Entry<String, Object> item : this.options.entrySet()) {
            if ("BACKGROUND".equals(item.getKey())) {
                render.setBackgroundColorTemp((Color) item.getValue());
            } else if ("V_ALIGN".equals(item.getKey())) {
                render.setVerticalAlignment(((Integer) item.getValue()).intValue());
            } else if ("H_ALIGN".equals(item.getKey())) {
                render.setHorizontalAlignment(((Integer) item.getValue()).intValue());
            } else if ("FONT".equals(item.getKey())) {
                render.setFont((Font) item.getValue());
            } else if ("BORDER".equals(item.getKey())) {
                render.setBorder((Border) item.getValue());
            }
        }
    }

    private void applyJTextAreaProperties(MultilineTextCellRender render) {
        for (Map.Entry<String, Object> item : this.options.entrySet()) {
            if ("BACKGROUND".equals(item.getKey())) {
                render.setBackgroundColorTemp((Color) item.getValue());
            } else if ("FONT".equals(item.getKey())) {
                render.setFont((Font) item.getValue());
            } else if ("BORDER".equals(item.getKey())) {
                render.setBorder((Border) item.getValue());
            } else if ("MULTI_LINE_NEW_LINE_SEPARATOR".equals(item.getKey())) {
                render.setLineSeparator("\n");
            }
        }
    }
}
