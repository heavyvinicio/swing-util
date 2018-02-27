/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
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
 * <b> Class for render button in table cell. </b>
 * 
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: pocho $, $Date: 22/09/2017 $]
 *          </p>
 */
public class TableButtonCellRender extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private static final long serialVersionUID = 2278885091561298376L;
    private final Border originalBorder;
    private Border focusBorder;
    private final JButton renderButton;
    private final JButton editButton;
    private final JTable table;
    private Object editorValue;

    public TableButtonCellRender(JTable tabla) {
        this.table = tabla;
        this.renderButton = new JButton();
        this.editButton = new JButton();
        this.originalBorder = this.editButton.getBorder();
        setFocusBorder(new LineBorder(Color.BLUE));
    }

    public static final TableButtonCellRender renderedButtons(JTable tabla) {
        TableButtonCellRender br = new TableButtonCellRender(tabla);
        br.loadColumnToRenderButton();
        return br;
    }

    private void loadColumnToRenderButton() {
        for (int i = 0; i < this.table.getColumnCount(); i++) {
            Object value = this.table.getValueAt(0, i);
            if ((value instanceof JButton)) {
                this.table.getColumnModel().getColumn(i).setCellRenderer(this);
                this.table.getColumnModel().getColumn(i).setCellEditor(this);
            }
        }
    }

    public void setFocusBorder(Border focusBorder) {
        this.focusBorder = focusBorder;
        getEditButton().setBorder(focusBorder);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            getRenderButton().setForeground(table.getSelectionForeground());
            getRenderButton().setBackground(table.getSelectionBackground());
        } else {
            getRenderButton().setForeground(table.getForeground());
            getRenderButton().setBackground(UIManager.getColor("Button.background"));
        }
        if (hasFocus) {
            getRenderButton().setBorder(this.focusBorder);
        } else {
            getRenderButton().setBorder(this.originalBorder);
        }
        if (value == null) {
            getRenderButton().setText("");
            getRenderButton().setIcon(null);
        } else if ((value instanceof Icon)) {
            getRenderButton().setText("");
            getRenderButton().setIcon((Icon) value);
        } else {
            getRenderButton().setText(((JButton) value).getText());
            getRenderButton().setIcon(null);
        }
        return getRenderButton();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            getEditButton().setText("");
            getEditButton().setIcon(null);
        } else if ((value instanceof Icon)) {
            getEditButton().setText("");
            getEditButton().setIcon((Icon) value);
        } else {
            getEditButton().setText(((JButton) value).getText());
            getEditButton().setIcon(null);
        }
        this.editorValue = value;
        return getEditButton();
    }

    public Object getCellEditorValue() {
        return this.editorValue;
    }

    public JButton getRenderButton() {
        return this.renderButton;
    }

    public JButton getEditButton() {
        return this.editButton;
    }
}