/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Conectar {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    private static final String URL = "http://localhost/phpmyadmin/index.php?route=/database/structure&db=bd_inventariofree";
    
    private Connection conn;
    
    public Conectar(){
        conn = null;
    }
    
    public Connection getConnection(){
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bd_inventariofree","root","");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al conectar con la base de datos", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return conn;
    }
    
    public void desconectar(){
        try {
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al cerrar la conexion con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

