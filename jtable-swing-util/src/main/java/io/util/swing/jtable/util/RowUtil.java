/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import io.util.swing.jtable.util.rendered.MultilineTextCellRender;
import io.util.swing.jtable.util.rendered.TableButtonCellRender;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author fochoac
 */
public class RowUtil implements Serializable {

    private static final long serialVersionUID = -3987403750860293992L;
    // private static final RowUtil INSTANCE = new RowUtil();
    private JTable table;
    private Map<String, Object> properties = new HashMap<>();

    private RowUtil() {
        super();
        properties.clear();
    }

    private RowUtil(JTable table) {
        this();
        this.table = table;

    }

    public static RowUtil getInstance(JTable table) {
        RowUtil instance = new RowUtil(table);
        return instance;

    }

    public RowUtil setBackground(Color color) {
        properties.put("BACKGROUND", color);
        return this;
    }

    public void apply(String columnText) {
//        List<TableCellRenderer> listRenders = new ArrayList<>();
//
//        List<Integer> rows = new ArrayList<>();
//        for (int row = 0; row < table.getRowCount(); row++) {
//            for (int column = 0; column < table.getColumnCount(); column++) {
//                if (table.getValueAt(row, column).toString().equals(columnText)) {
//                    TableCellRenderer render=getCellRender(table.getColumn(column));
//                    table.prepareRenderer(render, row, column){
//                
//                };
//                    ;
//                }
//                }
//            }
//        }
//        for (Integer row : rows) {
//            listRenders.clear();
//            for (int column = 0; column < table.getColumnCount(); column++) {
//                TableColumn col = table.getColumnModel().getColumn(column);
//                listRenders.add(getCellRender(col));
//            }
//            applyProperties(listRenders);
//        }

    }

    private void applyProperties(List<TableCellRenderer> list) {

        for (TableCellRenderer render : list) {

            for (Map.Entry<String, Object> item : properties.entrySet()) {

                if (render instanceof JLabel) {
                    JLabel r = (JLabel) render;

                    if ("BACKGROUND".equals(item.getKey())) {
                        r.setBackground((Color) item.getValue());
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

                }
            }

        }

        table.repaint();
        // invalidateData();
    }

    private TableCellRenderer getCellRender(TableColumn column) {

        TableCellRenderer render = column.getCellRenderer();
        if (render == null) {
            render = table.getDefaultRenderer(table.getColumnClass(column.getModelIndex()));
        }
        if (render instanceof JTextArea) {
            return (MultilineTextCellRender) render;
        }
        return render;
    }

}
