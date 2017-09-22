package io.util.swing.jtable.anntotatio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.swing.JTable;

/**
 * <b> Utility model for {@link JTable}. </b>
 * 
 * @author pocho
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: fochoac $, $Date: 15/09/2017 $]
 *          </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableColumn {
    /**
     * <b> Metodo para gestioanr el orden de presentacion de las
     * columnas. </b>
     * <p>
     * [Author: pocho, Date: 14/09/2017]
     * </p>
     *
     * @return numero de orden
     */
    int order() default 1;

    /**
     * <b> Metodo para obtener el nombre de la columna. </b>
     * <p>
     * [Author: pocho, Date: 14/09/2017]
     * </p>
     *
     * @return nombre de la columna
     */
    String columnName() default "DEFAULT";

    /**
     * <b> Metodo para obtener si la columna es editable o no. </b>
     * <p>
     * [Author: pocho, Date: 14/09/2017]
     * </p>
     *
     * @return false si no es editable
     */
    boolean editable() default false;

    /**
     * <b> Define si es visible la columna. </b>
     * <p>
     * [Author: pocho, Date: 15/09/2017]
     * </p>
     *
     * @return
     */
    boolean visible() default true;
}
