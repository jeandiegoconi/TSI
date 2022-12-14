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

    public int RegistrarCompra(Compra c){
        String sql = "INSERT INTO compras (codigo_producto ,id_proveedor,cantidad ,precio_compra,fecha_compra) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getCodigoProducto());
            ps.setInt(2,c.getIdProveedor());
            ps.setInt(3, c.getCantidad());
            ps.setInt(4,c.getPrecioCompra());
            ps.setString(5,c.getFechaCompra());
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
       String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN compras p ON pr.id = p.id_proveedor ORDER BY p.codigo_compra ASC;";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Compra com = new Compra();
               com.setCodigoCompra(rs.getInt("codigo_compra"));
               com.setCodigoProducto(rs.getInt("codigo_producto"));
               com.setIdProveedor(rs.getInt("id_proveedor"));
               com.setProveedor(rs.getString("nombre_proveedor"));
               com.setCantidad(rs.getInt("cantidad"));
               com.setPrecioCompra(rs.getInt("precio_compra"));
               com.setFechaCompra(rs.getString("fecha_compra"));
               Listarcom.add(com);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Listarcom;
   }
    
    
}
