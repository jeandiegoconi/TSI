/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author diego
 */
public class Compra {
    
    
    private int codigo_compra;
    private int codigo_producto;
    private int id_proveedor;
    private String proveedor;
    private int cantidad;
    private int precio_compra;
    private String fecha_compra;
    
    public Compra(){
    }
    
    public Compra(int codigo_compra, int codigo_producto,int id_proveedor,String proveedor,int cantidad, int precio_compra,String fecha_compra){
        
        this.codigo_compra = codigo_compra;
        this.codigo_producto = codigo_producto;
        this.id_proveedor = id_proveedor;
        this.proveedor = proveedor;
        this.precio_compra = precio_compra;
        this.cantidad = cantidad;
        this.fecha_compra = fecha_compra;
    }
    
    public int getCodigoCompra(){
        return codigo_compra;
    }
    
    public void setCodigoCompra(int codigo_compra){
        this.codigo_compra = codigo_compra;
    }
    
    public int getCodigoProducto(){
        return codigo_producto;
    }
    
    public void setCodigoProducto(int codigo_producto){
        this.codigo_producto = codigo_producto;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    
    
    public int getPrecioCompra(){
        return precio_compra;
    }
    
    public void setPrecioCompra(int precio_compra){
        this.precio_compra = precio_compra;
    }
    
    public String getFechaCompra(){
        return fecha_compra;
    }
    
    public void setFechaCompra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }
    
     public String getProveedor(){
        return proveedor;
    }
    
    public void setProveedor (String proveedor) {
        this.proveedor = proveedor;
    }
    
    public int getIdProveedor(){
        return id_proveedor;
    }
    
    public void setIdProveedor (int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    
    
}
