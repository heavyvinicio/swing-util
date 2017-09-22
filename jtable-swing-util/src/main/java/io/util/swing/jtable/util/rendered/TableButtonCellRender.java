/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author fochoac
 */
public class TableButtonCellRender extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private static final long serialVersionUID = 2278885091561298376L;

    private final Border originalBorder;
    private Border focusBorder;
    private final JButton renderButton;
    private final JButton editButton;
    private final JTable table;
    private Object editorValue;

    private TableButtonCellRender(JTable tabla) {
        this.table = tabla;
        renderButton = new JButton();
        editButton = new JButton();
        originalBorder = editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));

    }

    public static final TableButtonCellRender renderedButtons(JTable tabla, Integer[] columns) {
        TableButtonCellRender br = new TableButtonCellRender(tabla);
        br.loadColumnToRenderButton(columns);
        return br;
    }

    private void loadColumnToRenderButton(Integer[] column) {
        for (Integer i : column) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(this);
            this.table.getColumnModel().getColumn(i).setCellEditor(this);
        }
    }

    /**
     * The foreground color of the button when the cell has focus
     *
     * @param focusBorder the foreground color
     */
    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        editButton.setBorder(focusBorder);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus) {
            renderButton.setBorder(focusBorder);
        } else {
            renderButton.setBorder(originalBorder);
        }

        if (value == null) {
            renderButton.setText("");
            renderButton.setIcon(null);
        } else if (value instanceof Icon) {
            renderButton.setText("");
            renderButton.setIcon((Icon) value);
        } else {
            renderButton.setText(((JButton) value).getText());
            renderButton.setIcon(null);
        }

        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            editButton.setText("");
            editButton.setIcon(null);
        } else if (value instanceof Icon) {
            editButton.setText("");
            editButton.setIcon((Icon) value);
        } else {
            editButton.setText(((JButton) value).getText());
            editButton.setIcon(null);
        }

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return this.editorValue;
    }

}
