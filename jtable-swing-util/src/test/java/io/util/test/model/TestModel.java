/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.util.test.model;

import javax.swing.JButton;

import io.util.swing.jtable.anntotation.TableColumn;
import java.util.Objects;

/**
 *
 * @author fochoac
 */
public class TestModel {

    @TableColumn(order = 0, columnName = "COLUMNA A", editable = false)
    private String columnaUno;
    @TableColumn(order = 1, columnName = "Columna B", editable = true)
    private String columnaDos;
    @TableColumn(order = 2, columnName = "Columna C", editable = true)
    private String columnaTres;
    @TableColumn(order = 3, columnName = "Columna D \n muy permo muy \n laga para que \n se rezise", editable = true)
    private String columnaCuatro;
    @TableColumn(order = 4, columnName = "Certificado", editable = false)
    private JButton boton;
    @TableColumn(order = 5, columnName = "Historia clinica", editable = false)
    private JButton boton2;
    @TableColumn(order = 6, columnName = "es valido", editable = true)
    private boolean jchk;

    public TestModel() {
    }

    public TestModel(String columnaUno, String columnaDos, String columnaTres, String columnaCuatro) {
        this.columnaUno = columnaUno;
        this.columnaDos = columnaDos;
        this.columnaTres = columnaTres;
        this.columnaCuatro = columnaCuatro;
        boton = new JButton("Descargar");
        boton2 = new JButton("Descargar2");
        jchk = false;
    }

    @Override
    public String toString() {
        return "TestModel{" + "columnaUno=" + getColumnaUno() + ", columnaDos=" + getColumnaDos() + ", columnaTres=" + getColumnaTres() + ", columnaCuatro=" + getColumnaCuatro() + "check=" + jchk + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.columnaUno);
        hash = 53 * hash + Objects.hashCode(this.columnaDos);
        hash = 53 * hash + Objects.hashCode(this.columnaTres);
        hash = 53 * hash + Objects.hashCode(this.columnaCuatro);
        hash = 53 * hash + Objects.hashCode(this.boton);
        hash = 53 * hash + Objects.hashCode(this.boton2);
        hash = 53 * hash + (this.jchk ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestModel other = (TestModel) obj;
        if (this.jchk != other.jchk) {
            return false;
        }
        if (!Objects.equals(this.columnaUno, other.columnaUno)) {
            return false;
        }
        if (!Objects.equals(this.columnaDos, other.columnaDos)) {
            return false;
        }
        if (!Objects.equals(this.columnaTres, other.columnaTres)) {
            return false;
        }
        if (!Objects.equals(this.columnaCuatro, other.columnaCuatro)) {
            return false;
        }
        if (!Objects.equals(this.boton, other.boton)) {
            return false;
        }
        if (!Objects.equals(this.boton2, other.boton2)) {
            return false;
        }
        return true;
    }

    /**
     * @return the columnaUno
     */
    public String getColumnaUno() {
        return columnaUno;
    }

    /**
     * @param columnaUno the columnaUno to set
     */
    public void setColumnaUno(String columnaUno) {
        this.columnaUno = columnaUno;
    }

    /**
     * @return the columnaDos
     */
    public String getColumnaDos() {
        return columnaDos;
    }

    /**
     * @param columnaDos the columnaDos to set
     */
    public void setColumnaDos(String columnaDos) {
        this.columnaDos = columnaDos;
    }

    /**
     * @return the columnaTres
     */
    public String getColumnaTres() {
        return columnaTres;
    }

    /**
     * @param columnaTres the columnaTres to set
     */
    public void setColumnaTres(String columnaTres) {
        this.columnaTres = columnaTres;
    }

    /**
     * @return the columnaCuatro
     */
    public String getColumnaCuatro() {
        return columnaCuatro;
    }

    /**
     * @param columnaCuatro the columnaCuatro to set
     */
    public void setColumnaCuatro(String columnaCuatro) {
        this.columnaCuatro = columnaCuatro;
    }

    /**
     * @return the boton
     */
    public JButton getBoton() {
        return boton;
    }

    /**
     * @param boton the boton to set
     */
    public void setBoton(JButton boton) {
        this.boton = boton;
    }

    /**
     * @return the boton2
     */
    public JButton getBoton2() {
        return boton2;
    }

    /**
     * @param boton2 the boton2 to set
     */
    public void setBoton2(JButton boton2) {
        this.boton2 = boton2;
    }

    /**
     * @return the jchk
     */
    public boolean isJchk() {
        return jchk;
    }

    /**
     * @param jchk the jchk to set
     */
    public void setJchk(boolean jchk) {
        this.jchk = jchk;
    }

}
