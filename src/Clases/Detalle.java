/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Kherfa
 */
public class Detalle {

    private int id;
    private int id_pro;
    private String nombre;
    private String nombre_cliente;
    private int cantidad;
    private int precio;
    private int id_venta;

    public Detalle() {

    }

    public Detalle(int id, int id_pro, String nombre_cliente, String nombre, int cantidad, int precio, int id_venta) {
        this.id = id;
        this.id_pro = id_pro;
        this.nombre = nombre;
        this.nombre_cliente = nombre_cliente;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id_venta = id_venta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getNombreCliente() {
        return nombre_cliente;
    }

    public void setNombreCliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

}
