/*
 * To change this license header, choose License Headers in Project
 * Properties. To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.swing.jtable.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import io.util.swing.jtable.reflection.ReflectionUtil;

/**
 * <b> Utility class for generate
 * {@link JTable#setModel(javax.swing.table.TableModel)}
 * {@link JTable}. </b>
 *
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: pocho $, $Date: 14/09/2017 $]
 *          </p>
 */
public class JTableModel<T> extends AbstractTableModel implements Serializable {
    private static final long serialVersionUID = -774784899858710143L;
    T t;
    private List<T> listaDatos;
    private Map<Integer, Map<Integer, Object>> rows;
    private List<String> columnNames;

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: pocho, Date: 14/09/2017]
     * </p>
     *
     */
    public JTableModel() {
        listaDatos = new ArrayList<>();
        rows = new TreeMap<>();
        columnNames = new ArrayList<>();
    }

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: pocho, Date: 14/09/2017]
     * </p>
     *
     * @param listaDatos
     *            lista de datos genericos
     */
    @SuppressWarnings("unchecked")
    public JTableModel(List<T> listaDatos) {
        this();
        this.listaDatos = listaDatos;
        rows = ReflectionUtil.convertListData2Map(this.listaDatos);
        if (rows.isEmpty()) {
            return;
        }
        columnNames = ReflectionUtil.getColumnNames(listaDatos.get(0));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int columnIndex) {
        if (columnNames == null) {
            return "";
        }
        return columnNames.get(columnIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (rows == null || rows.isEmpty()) {
            return super.getColumnClass(columnIndex);
        }
        if (rows.get(0).get(columnIndex) == null) {
            return Object.class;
        }
        return rows.get(0).get(columnIndex).getClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int,
     * int)
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        T obj = listaDatos.get(rowIndex);
        return ReflectionUtil.isCellEditable(obj).get(columnIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.
     * Object, int, int)
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        for (Map.Entry<Integer, Map<Integer, Object>> row : rows.entrySet()) {
            for (Map.Entry<Integer, Object> column : row.getValue().entrySet()) {
                super.setValueAt(column.getValue(), row.getKey(), column.getKey());
            }
        }
        updateCellValue(aValue, rowIndex, columnIndex);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * <b> Methos for update cell values. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param aValue
     *            {@link Object}
     * @param rowIndex
     *            row
     * @param columnIndex
     *            column
     */
    private void updateCellValue(Object aValue, int rowIndex, int columnIndex) {
        T obj = listaDatos.get(rowIndex);
        if (ReflectionUtil.isCellEditable(obj).get(columnIndex)) {
            rows.get(rowIndex).replace(columnIndex, aValue);
            super.setValueAt(aValue, rowIndex, columnIndex);
            ReflectionUtil.mergeChanges(obj, rows.get(rowIndex));
        }
    }

    /**
     * <b> Method for get value from row select. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param row
     *            row index
     * @return T
     */
    public T get(int row) {
        return listaDatos.get(row);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
        if (rows == null) {
            return 0;
        }
        return rows.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
        if (columnNames == null) {
            return 0;
        }
        return columnNames.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < rows.size() && columnIndex < columnNames.size()) {
            Object o = rows.get(rowIndex).get(columnIndex);
            return o;
        }
        return null;
    }

    /**
     * <b> Incluir aqui­ la descripcion del metodo. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param t
     */
    public void addNewRow(T t) {
        List<T> listTmp = new ArrayList<>(listaDatos);
        listTmp.add(t);
        listaDatos = new ArrayList<>(listTmp);
        rows = ReflectionUtil.convertListData2Map(this.listaDatos);
        fireTableDataChanged();
    }

    /**
     * <b> Method for remove row in {@link JTable} by T object. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param t
     *            T
     */
    public void removeRow(T t) {
        List<T> listTmp = new ArrayList<>(listaDatos);
        listTmp.remove(t);
        listaDatos = new ArrayList<>(listTmp);
        rows = ReflectionUtil.convertListData2Map(this.listaDatos);
        fireTableDataChanged();
    }

    /**
     * <b> Method for remove row by row index. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     * @param row
     */
    public void removeRow(int row) {
        List<T> listTmp = new ArrayList<>(listaDatos);
        listTmp.remove(get(row));
        listaDatos = new ArrayList<>(listTmp);
        rows = ReflectionUtil.convertListData2Map(this.listaDatos);
        fireTableDataChanged();
    }

    /**
     * <b> Method for remove all rows in {@link JTable}. </b>
     * <p>
     * [Author: pocho, Date: 22/09/2017]
     * </p>
     *
     */
    public void removeAll() {
        listaDatos = new ArrayList<>();
        rows.clear();
        fireTableDataChanged();
    }
}
