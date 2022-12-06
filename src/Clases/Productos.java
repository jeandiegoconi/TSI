/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Kherfa
 */
public class Productos {
    private int id;
    private String codigo;
    private String nombre;
    private int proveedor;
    private String proveedorPro;
    private int stock;
    private int precio;
    private String nomimagen;
    
    public Productos(){
        
    }

    public Productos(int id, String codigo, String nombre, int proveedor, String proveedorPro, int stock, int precio, String nomimagen) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.proveedorPro = proveedorPro;
        this.stock = stock;
        this.precio = precio;
        this.nomimagen = nomimagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public String getProveedorPro() {
        return proveedorPro;
    }

    public void setProveedorPro(String proveedorPro) {
        this.proveedorPro = proveedorPro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
   
     public String getNomimagen() {
        return nomimagen;
    }
     
         public void setNomimagen(String nomimagen) {
        this.nomimagen = nomimagen;
    }
    
         public String getImagen() {
        return nomimagen;
    }
     
         public void setImagen(String nomimagen) {
        this.nomimagen = nomimagen;
    }
    
    }

   