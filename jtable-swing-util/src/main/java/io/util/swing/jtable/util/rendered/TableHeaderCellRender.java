/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util.rendered;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author fochoac
 */
public class TableHeaderCellRender extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 8557286170742587697L;

    private Color background;
    private Color foreground;
    private static LineBorder border = new LeftColorLineBorder((Color) UIManager.get("Button.focus"));

    public TableHeaderCellRender() {
        super();
        this.background = new Color(230, 233, 238);
        this.foreground = new Color(101, 99, 99);

    }

//    public static synchronized TableHeaderCellRender getInstance() {
//        return new TableHeaderCellRender();
//// return MyWrapper.INSTANCE;
//    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setPreferredSize(new Dimension(getPreferredSize().width, 25));
        setBackground(background);
        setForeground(foreground);
        setText(value != null ? value.toString() : "");
        //       setFont(new java.awt.Font("Open Sans", 1, 12));
//        comp.setBackground(background);
//        comp.setForeground(foreground);
        // comp.setFont();
        //  Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");

        setBorder(border);
        setHorizontalAlignment(SwingConstants.CENTER);

        return comp;
    }

//    private static class MyWrapper {
//
//        static TableHeaderCellRender INSTANCE = new TableHeaderCellRender();
//    }
    private static class LeftColorLineBorder extends LineBorder {

        private static final long serialVersionUID = -153667616447511677L;

        public LeftColorLineBorder(Color color) {
            super(color, 1, false);
        }

        public LeftColorLineBorder(Color color, int thickness) {
            super(color, thickness, false);
        }

        public LeftColorLineBorder(Color color, int thickness, boolean roundedCorners) {
            super(color, thickness, roundedCorners);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            super.paintBorder(c, g, x, y, width, height);
            g.setColor(Color.GRAY);
            g.fillRect(x, y, x, y + height);
            g.fillRect(x + width, y, x + width, y + height);
        }
    }
}
