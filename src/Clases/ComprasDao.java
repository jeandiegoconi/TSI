/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author diego
 */
public class ComprasDao {
        int r;
        Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
InputStream empty = new InputStream() {
    @Override
    public int read() {
        return -1;  // end of stream
    }
};
public int IdCompra(){
        int id = 0;
        String sql = "SELECT MAX(id) FROM compras";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }


    public int RegistrarDetalle(Detalle Dv){
       String sql = "INSERT INTO detalle_compra (id_pro, cantidad, id_compra) VALUES (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setInt(3, Dv.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    public int RegistrarCompra(Compra c){
        String sql = "INSERT INTO compras (id_proveedor,precio_compra, fecha_compra) VALUES (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1,c.getIdProveedor());
            ps.setInt(2,c.getPrecioCompra());
            ps.setString(3,c.getFechaCompra());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    
    
        public List ListarCompras(){
       List<Compra> Listarcom = new ArrayList();
       String sql = "SELECT c.id AS id_cli, c.nombre, v.* FROM proveedor c INNER JOIN compras v ON c.id = v.id_proveedor";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Compra com = new Compra();
               com.setCodigoCompra(rs.getInt("id"));
               com.setProveedor(rs.getString("nombre"));
               com.setPrecioCompra(rs.getInt("precio_compra"));
               com.setFechaCompra(rs.getString("fecha_compra"));
               Listarcom.add(com);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Listarcom;
   }
        
        
            public Compra BuscarCompra(int id){
        Compra com = new Compra();
        String sql = "SELECT * FROM compras WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                com.setCodigoCompra(rs.getInt("id"));
                com.setIdProveedor(rs.getInt("id_proveedor"));
                com.setPrecioCompra(rs.getInt("precio_compra"));
                com.setFechaCompra(rs.getString("fecha_compra"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return com;
    }
            
            
                
        public Compra BuscarProveedorCompra(int id) {
        String sql = "SELECT p.nombre, com.* FROM proveedor p INNER JOIN compras com ON p.id = com.id_proveedor where id_proveedor = "+ id+ "  order by  com.id_proveedor desc;";
        Compra com = new Compra();
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                com.setProveedor(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return com;
    }
    
}
