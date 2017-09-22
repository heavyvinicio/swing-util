package io.util.swing.jtable.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.Statement;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.util.swing.jtable.anntotatio.TableColumn;

/**
 * <b> Clase utilitaria para gestionar la refleccion sobre los datos a mostrar
 * en el modelo. </b>
 *
 * @author fochoac
 * @version $Revision: 1.0 $
 * <p>
 * [$Author: fochoac $, $Date: 14/09/2017 $]
 * </p>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class ReflectionUtil implements Serializable {
    
    private static final long serialVersionUID = 5173782948934070993L;

    /**
     * <b> Constructor de la clase. </b>
     * <p>
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     */
    private ReflectionUtil() {
        super();
    }
    
    private static boolean isStaticOrFinal(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
    }

    /**
     * <b> Metodo para obtener los nombres de las columnas. </b>
     * <p>
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     * @param object objeto generico
     * @return lista de objetos
     */
    public static <T> List getColumnNames(T object) {
        Map columnNames = new TreeMap();
        for (Field field : getFieldsObject(object)) {
            if (isStaticOrFinal(field)) {
                continue;
            }
            if (field.isAnnotationPresent(TableColumn.class) && ((TableColumn) field.getDeclaredAnnotation(TableColumn.class)).visible() && !"DEFAULT".equalsIgnoreCase(field.getDeclaredAnnotation(TableColumn.class).columnName())) {
                columnNames.put(field.getDeclaredAnnotation(TableColumn.class).order(), field.getDeclaredAnnotation(TableColumn.class).columnName());
            }
            // else {
            // columnNames.add(field.getName());
            // }
        }
        return new ArrayList<>(columnNames.values());
    }

    /**
     * <b> Metodo para convertir una lista en un mapa. </b>
     * <p>
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     * @param dataList lista de datos
     * @return mapa de filas y columnas
     */
    public static <T> Map<Integer, Map<Integer, Object>> convertListData2Map(List<T> dataList) {
        Map<Integer, Map<Integer, Object>> rows = new TreeMap<>();
        int index = 0;
        if (dataList == null || dataList.isEmpty()) {
            return new TreeMap<>();
        }
        for (T object : dataList) {
            try {
                rows.put(index, convertObjectData2Map(object));
                index++;
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                 Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
                return new TreeMap<>();
            }
        }
        return rows;
    }

    /**
     * <b> Metodo para converir un objeto a una fila. </b>
     * <p>
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     * @param object
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static <T> Map<Integer, Object> convertObjectData2Map(T object) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<Integer, Object> row = new TreeMap<>();
        // int index = 0;
        for (Field field : getFieldsObject(object)) {
            if (isStaticOrFinal(field)) {
                continue;
            }
            TableColumn tableColumn = null;
            if (field.isAnnotationPresent(TableColumn.class)) {
                tableColumn = field.getDeclaredAnnotation(TableColumn.class);
            }
            if (tableColumn != null && tableColumn.visible()) {
                row.put(tableColumn.order(), loadValueColumn(object, field));
            }
            // else {
            // row.put(index, loadValueColumn(object, field));
            // }
            // index++;
        }
        return row;
    }

    /**
     * <b> Metodo para cargar el valor de la columna. </b>
     * <p>
     * [Author: fochoac, Date: 14/09/2017]
     * </p>
     *
     * @param object objeto generico
     * @param field {@link Field}
     * @return columna generada
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static <T> Object loadValueColumn(T object, Field field) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Modifier.isPrivate(field.getModifiers())) {
            return new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object, new Object[]{});
        }
        return null;
    }

    /**
     * <p>
     * Method for get Fields of object class by reflection
     * </p>
     *
     * @param object T class
     * @return array of Fields
     */
    private static <T> Field[] getFieldsObject(T object) {
        return object.getClass().getDeclaredFields();
    }

    /**
     * <p>
     * Method for valdiate if a cell is editable
     * </p>
     *
     * @param object Object
     * @return List of boolean values with column true when is editable
     */
    public static <T> List<Boolean> isCellEditable(T object) {
        List<Boolean> estados = new ArrayList<>();
        for (Field field : getFieldsObject(object)) {
            if (Modifier.isPrivate(field.getModifiers()) && field.isAnnotationPresent(TableColumn.class)) {
                TableColumn tableColumn = field.getDeclaredAnnotation(TableColumn.class);
                estados.add(tableColumn.editable());
            }
        }
        return estados;
    }

    /**
     * <p>
     * Method for make merge two object when the case is necesary
     * </p>
     *
     * @param object OBject
     * @param values row to validate
     */
    public static <T> void mergeChanges(T object, Map<Integer, Object> values) {
        int index = 0;
        for (Field field : getFieldsObject(object)) {
            if (Modifier.isPrivate(field.getModifiers()) && field.isAnnotationPresent(TableColumn.class)) {
                TableColumn tableColumn = field.getDeclaredAnnotation(TableColumn.class);
                try {
                    Object valorAnterior = loadValueColumn(object, field);
                    Object valorNuevo = values.get(index);
                    if (tableColumn.editable() && (null == valorAnterior || !valorAnterior.equals(valorNuevo))) {
                        changeValue(object, field, valorNuevo);
                    }
                } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            index++;
        }
    }

    /**
     * <p>
     * Method for update modificate object value from column modification
     * </p>
     *
     * @param object OBject
     * @param field FIeld
     * @param newValue new value
     * @throws Exception in case of error
     */
    private static <T> void changeValue(T object, Field field, Object newValue) throws Exception {
        Statement a = new Statement(object, "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), new Object[]{newValue});
        a.execute();
    }
}
