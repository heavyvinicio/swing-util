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
 * <b> Clase utilitaria para gestionar el modelo de una {@link JTable}. </b>
 *
 * @author pocho
 * @version $Revision: 1.0 $
 * <p>
 * [$Author: pocho $, $Date: 14/09/2017 $]
 * </p>
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
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     * @param listaDatos lista de datos genericos
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

    @Override
    public String getColumnName(int columnIndex) {
        if (columnNames == null) {
            return "";
        }
        return columnNames.get(columnIndex);
    }

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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        T obj = listaDatos.get(rowIndex);
        return ReflectionUtil.isCellEditable(obj).get(columnIndex);
    }

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

    private void updateCellValue(Object aValue, int rowIndex, int columnIndex) {
        T obj = listaDatos.get(rowIndex);
        if (ReflectionUtil.isCellEditable(obj).get(columnIndex)) {
            rows.get(rowIndex).replace(columnIndex, aValue);
            super.setValueAt(aValue, rowIndex, columnIndex);
            ReflectionUtil.mergeChanges(obj, rows.get(rowIndex));
        }
    }

    /**
     *
     * @param row
     * @return
     */
    public T get(int row) {
        return listaDatos.get(row);
    }

    @Override
    public int getRowCount() {
        if (rows == null) {
            return 0;
        }
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        if (columnNames == null) {
            return 0;
        }
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < rows.size() && columnIndex < columnNames.size()) {

            Object o = rows.get(rowIndex).get(columnIndex);

            return o;

        }
        return null;
    }

    /**
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
     *
     * @param t
     */
    public void removeRow(T t) {
        List<T> listTmp = new ArrayList<>(listaDatos);
        listTmp.remove(t);
        listaDatos = new ArrayList<>(listTmp);
        rows = ReflectionUtil.convertListData2Map(this.listaDatos);
        fireTableDataChanged();
    }

    /**
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
     *
     */
    public void removeAll() {
        listaDatos = new ArrayList<>();
        rows.clear();
        fireTableDataChanged();
    }
}
