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

import javax.swing.JTable;

import io.util.swing.jtable.anntotation.TableColumn;

/**
 * <b> Utility class for work with reflection. </b>
 *
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: pocho $, $Date: 14/09/2017 $]
 *          </p>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ReflectionUtil implements Serializable {
	private static final long serialVersionUID = 5173782948934070993L;

	/**
	 * <b> Constructor de la clase. </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 */
	private ReflectionUtil() {
		super();
	}

	/**
	 * <b> Method for validate if field is static or final. </b>
	 * <p>
	 * [Author: pocho, Date: 22/09/2017]
	 * </p>
	 *
	 * @param field
	 *            {@link Field}
	 * @return <code>true</code> if field is static or final
	 */
	private static boolean isStaticOrFinal(Field field) {
		return Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers());
	}

	/**
	 * <b> Method for get columns names. </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param object
	 *            objeto generico
	 * @return lista de objetos
	 */
	public static <T> List getColumnNames(T object) {
		Map columnNames = new TreeMap();
		for (Field field : getFieldsObject(object)) {
			if (isStaticOrFinal(field)) {
				continue;
			}
			if (field.isAnnotationPresent(TableColumn.class)
					&& ((TableColumn) field.getDeclaredAnnotation(TableColumn.class)).visible()
					&& !"DEFAULT".equalsIgnoreCase(field.getDeclaredAnnotation(TableColumn.class).columnName())) {
				columnNames.put(field.getDeclaredAnnotation(TableColumn.class).order(),
						field.getDeclaredAnnotation(TableColumn.class).columnName());
			}
		}
		return new ArrayList<>(columnNames.values());
	}

	public static <T> List getColumnNames(T object, int... columns) {
		Map columnNames = new TreeMap();
		for (Field field : getFieldsObject(object)) {
			if (isStaticOrFinal(field)) {
				continue;
			}
			if (field.isAnnotationPresent(TableColumn.class)
					&& ((TableColumn) field.getDeclaredAnnotation(TableColumn.class)).visible()
					&& !"DEFAULT".equalsIgnoreCase(field.getDeclaredAnnotation(TableColumn.class).columnName())) {
				if (validateColumnOrder(field.getDeclaredAnnotation(TableColumn.class).order(), columns)) {
					columnNames.put(field.getDeclaredAnnotation(TableColumn.class).order(),
							field.getDeclaredAnnotation(TableColumn.class).columnName());
				}
			}
		}
		return new ArrayList<>(columnNames.values());
	}

	/**
	 * <b> Method for convert list objects to map. </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param dataList
	 *            lista de datos
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
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
				Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				return new TreeMap<>();
			}
		}
		return rows;
	}

	/**
	 * <b> Method for convert list objects to map. </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param dataList
	 *            lista de datos
	 * @return mapa de filas y columnas
	 */
	public static <T> Map<Integer, Map<Integer, Object>> convertListData2Map(List<T> dataList, String[] attributes) {
		Map<Integer, Map<Integer, Object>> rows = new TreeMap<>();
		int index = 0;
		if (dataList == null || dataList.isEmpty()) {
			return new TreeMap<>();
		}
		for (T object : dataList) {
			try {
				rows.put(index, convertObjectData2Map(object, attributes));
				index++;
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
				Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				return new TreeMap<>();
			}
		}
		return rows;
	}

	public static <T> Map<Integer, Map<Integer, Object>> convertListData2Map(List<T> dataList, int... columnas) {
		Map<Integer, Map<Integer, Object>> rows = new TreeMap<>();
		int index = 0;
		if (dataList == null || dataList.isEmpty()) {
			return new TreeMap<>();
		}
		for (T object : dataList) {
			try {
				rows.put(index, convertObjectData2Map(object, columnas));
				index++;
			} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException ex) {
				Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				return new TreeMap<>();
			}
		}
		return rows;
	}

	/**
	 * <b> Method for convert object to map (row in {@link JTable}). </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param object
	 *            T
	 * @return {@link Map}
	 * @throws IntrospectionException
	 *             error
	 * @throws IllegalAccessException
	 *             error
	 * @throws IllegalArgumentException
	 *             error
	 * @throws InvocationTargetException
	 *             error
	 */
	private static <T> Map<Integer, Object> convertObjectData2Map(T object)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<Integer, Object> row = new TreeMap<>();
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
		}
		return row;
	}

	public static <T> Integer[] getBooleanFields(T object, String[] attributes) {
		List<Integer> items = new ArrayList<>();
		for (Field field : getFieldsObject(object)) {
			if (isStaticOrFinal(field)) {
				continue;
			}
			for (int i = 0; i < attributes.length; i++) {
				if (field.getName().equals(attributes[i]) && field.getType().equals(boolean.class)) {
					items.add(i);
					break;
				}
			}

		}
		return items.toArray(new Integer[] {});
	}

	/**
	 * <b> Method for convert object to map (row in {@link JTable}). </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param object
	 *            T
	 * @return {@link Map}
	 * @throws IntrospectionException
	 *             error
	 * @throws IllegalAccessException
	 *             error
	 * @throws IllegalArgumentException
	 *             error
	 * @throws InvocationTargetException
	 *             error
	 */
	private static <T> Map<Integer, Object> convertObjectData2Map(T object, String[] attributes)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<Integer, Object> row = new TreeMap<>();
		for (int i = 0; i < attributes.length; i++) {
			for (Field field : getFieldsObject(object)) {
				if (isStaticOrFinal(field)) {
					continue;
				}
				if (attributes[i].equals(field.getName())) {
					row.put(i, loadValueColumn(object, field));
					break;
				}
			}
		}
		return row;
	}

	private static <T> Map<Integer, Object> convertObjectData2Map(T object, int... columns)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int order = 0;
		Map<Integer, Object> row = new TreeMap<>();
		for (Field field : getFieldsObject(object)) {
			if (isStaticOrFinal(field)) {
				continue;
			}
			TableColumn tableColumn = null;
			if (field.isAnnotationPresent(TableColumn.class)) {
				tableColumn = field.getDeclaredAnnotation(TableColumn.class);
			}
			if (tableColumn != null && tableColumn.visible()) {
				if (validateColumnOrder(tableColumn.order(), columns)) {
					row.put(order, loadValueColumn(object, field));
					order++;
				}
			}
		}
		return row;
	}

	private static boolean validateColumnOrder(int orden, int... columns) {
		for (int i = 0; i < columns.length; i++) {
			if (columns[i] == orden) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <b> Method for load value to column. </b>
	 * <p>
	 * [Author: pocho, Date: 14/09/2017]
	 * </p>
	 *
	 * @param object
	 *            objeto generico
	 * @param field
	 *            {@link Field}
	 * @return columna generada
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static <T> Object loadValueColumn(T object, Field field)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (Modifier.isPrivate(field.getModifiers())) {
			return new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object,
					new Object[] {});
		}
		return null;
	}

	/**
	 * <p>
	 * Method for get Fields of object class by reflection
	 * </p>
	 *
	 * @param object
	 *            T class
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
	 * @param object
	 *            Object
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
	 * @param object
	 *            OBject
	 * @param values
	 *            row to validate
	 */
	public static <T> void mergeChanges(T object, Map<Integer, Object> values) {
		int index = 0;
		for (Field field : getFieldsObject(object)) {
			if (Modifier.isPrivate(field.getModifiers()) && field.isAnnotationPresent(TableColumn.class)) {
				TableColumn tableColumn = field.getDeclaredAnnotation(TableColumn.class);
				try {
					if (tableColumn.editable()) {
						Object valorAnterior = loadValueColumn(object, field);
						Object valorNuevo = values.get(index);
						if (tableColumn.editable() && (null == valorAnterior || !valorAnterior.equals(valorNuevo))) {
							changeValue(object, field, valorNuevo);
						}
					}
				} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				} catch (Exception ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			index++;
		}
	}

	public static <T> void mergeChanges(T object, Map<Integer, Object> values, final Map<Integer, String> columnas) {
		for (Field field : getFieldsObject(object)) {
			if (Modifier.isPrivate(field.getModifiers())) {
				try {
					for (Map.Entry<Integer, String> item : columnas.entrySet()) {
						if (field.getName().equals(item.getValue())) {
							Object valorAnterior = loadValueColumn(object, field);
							Object valorNuevo = values.get(item.getKey());
							if (null == valorAnterior || !valorAnterior.equals(valorNuevo)) {
								changeValue(object, field, valorNuevo);
							}
						}
					}
				} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				} catch (Exception ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	/**
	 * <p>
	 * Method for make merge two object when the case is necesary
	 * </p>
	 *
	 * @param <T>
	 * @param object
	 *            OBject
	 * @param values
	 *            row to validate
	 */
	public static <T> void mergeChanges(T object, Map<Integer, Object> values, int... columns) {
		int index = 0;
		for (Field field : getFieldsObject(object)) {
			if (Modifier.isPrivate(field.getModifiers()) && field.isAnnotationPresent(TableColumn.class)) {
				TableColumn tableColumn = field.getDeclaredAnnotation(TableColumn.class);
				try {
					if (tableColumn.editable() && validateColumnOrder(tableColumn.order(), columns)) {
						Object valorAnterior = loadValueColumn(object, field);
						Object valorNuevo = values.get(index);
						if (tableColumn.editable() && (null == valorAnterior || !valorAnterior.equals(valorNuevo))) {
							changeValue(object, field, valorNuevo);
							index++;
						}
					}
				} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				} catch (Exception ex) {
					Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	/**
	 * <p>
	 * Method for update modificate object value from column modification
	 * </p>
	 *
	 * @param object
	 *            OBject
	 * @param field
	 *            FIeld
	 * @param newValue
	 *            new value
	 * @throws Exception
	 *             in case of error
	 */
	private static <T> void changeValue(T object, Field field, Object newValue) throws Exception {
		Statement a = new Statement(object,
				"set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1),
				new Object[] { newValue });
		a.execute();
	}
}
