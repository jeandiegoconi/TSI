package Clases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author diego
 */
public class DetalleDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    
    public List ListarProdHis(int idventa){
        List<Detalle> Listaprhis = new ArrayList();
        String sql = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idventa);
            rs = ps.executeQuery();
            while (rs.next()) {  
                Detalle de = new Detalle();
                de.setNombre(rs.getString("nombre"));
                de.setPrecio(rs.getInt("precio"));
                de.setCantidad(rs.getInt("cantidad"));
                Listaprhis.add(de);
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listaprhis;
        
        
    }
    
}
