/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.util;

import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author fochoac
 */
public class FilterTextTable extends DocumentFilter implements Serializable {
    
    private static final long serialVersionUID = 4879957635988055525L;
    private JTable table;
    
    public FilterTextTable() {
    }
    
    public FilterTextTable(JTable table) {
        this.table = table;
    }
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {
        
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        
        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            // warn the user and don't allow the insert
        }
    }
    
    private boolean test(String text) {
        TableUtil.filterByText(table, text);
        return true;
//        try {
//            Integer.parseInt(text);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
    }
    
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {
        
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);
        
        if (test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
            
        } else {
            super.replace(fb, offset, length, "", attrs);
        }
    }
    
    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
//        sb.delete(offset, offset + length);

        if (test(sb.toString())) {
            sb.delete(offset, offset + length);
            super.remove(fb, offset, length);
            
        } else {
            // warn the user and don't allow the insert
        }
        
    }
    
}
