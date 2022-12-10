/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;


public class Venta {
    private int id;
    private int cliente;
    private String nombre_cli;
    private String vendedor;
    private int pago;
    private int total;
    private int vuelto;
    private String fecha;
    
    public Venta(){
        
    }

    public Venta(int id, int cliente, String nombre_cli, String vendedor,int pago, int total,int vuelto, String fecha) {
        this.id = id;
        this.cliente = cliente;
        this.nombre_cli = nombre_cli;
        this.vendedor = vendedor;
        this.pago = pago;
        this.total = total;
        this.vuelto = vuelto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }
    
    public int getVuelto() {
        return vuelto;
    }

    public void setVuelto(int vuelto) {
        this.vuelto = vuelto;
    }
    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    


    
}

    

    