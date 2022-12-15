package Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import Clases.DetalleDao;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import Clases.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



public final class Menu extends javax.swing.JFrame {
    private final Cls_productos CP;
    TableColumnModel columnModel;
    int num = 0;
    Date fechaVenta = new Date();
    Date fechaCompra = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    String fechaCompraActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaCompra);
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    Proveedor pr = new Proveedor();
    ProveedorDao PrDao = new ProveedorDao();
    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();
    Compra com = new Compra();
    ComprasDao comDao = new ComprasDao();
    Eventos event = new Eventos();
    DetalleDao DeDao = new DetalleDao();
    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();
    Detalle Dv = new Detalle();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    int Totalpagar = 0;
    int PagoCliente = 0;
    int Vueltototal = 0;
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;


    
    
    
   

     public class func{
        public ResultSet find(int id){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = cn.getConnection();
                ps = con.prepareStatement("select * from productos where id = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            return rs;
            
        }
    }



        public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    
    public void ListarCliente() {
        List<Cliente> ListarCl = client.ListarCliente();
        modelo = (DefaultTableModel) TablaCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 1; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getRut();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TablaCliente.setModel(modelo);

    }
    
    
        public void ListarProductosHitorial() {
        List<Detalle> ListarProdHis = DeDao.ListarProdHis(Integer.parseInt(txtIdVentaPdf.getText()));
        modelo = (DefaultTableModel) TablaProductoHistorial.getModel();
        for (int i = 0; i < ListarProdHis.size(); i++) {
        Object[] ob = new Object[3];
            ob[0] = ListarProdHis.get(i).getNombre();
            ob[2] = ListarProdHis.get(i).getPrecio();
            ob[1] = ListarProdHis.get(i).getCantidad();
            modelo.addRow(ob);
        }
        TablaProductoHistorial.setModel(modelo);

    }
    
    
    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) TablaProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRut();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TablaProveedor.setModel(modelo);

    }
    
    public void BuscarProductos() {
        List<Productos> ListarPro = proDao.BuscarProductos(txtBuscarProducto.getText().trim());
        modelo = (DefaultTableModel) TablaProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            
            modelo.addRow(ob);
        }
        TablaProducto.setModel(modelo);

    }
    
        public void BuscarVentas() {
        List<Venta> VentasPro = Vdao.BuscarListaVenta(Integer.parseInt(txtBuscarCodigoVenta.getText().trim()));
        modelo = (DefaultTableModel) TablaHistorialVenta.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < VentasPro.size(); i++) {
            ob[0] = VentasPro.get(i).getId();
            ob[1] = VentasPro.get(i).getNombre_cli();
            ob[2] = VentasPro.get(i).getTotal();
            
            modelo.addRow(ob);
        }
        TablaHistorialVenta.setModel(modelo);

        
 
        
    }
    
    
    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TablaProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            
            modelo.addRow(ob);
        }
        TablaProducto.setModel(modelo);

    }
    
        public void ListarCompras() {
        List<Compra> ListarPro = comDao.ListarCompras();
        modelo = (DefaultTableModel) TablaCompra.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getCodigoCompra();
            ob[1] = ListarPro.get(i).getCodigoProducto();
            ob[2] = ListarPro.get(i).getProveedor();
            ob[3] = ListarPro.get(i).getCantidad();
            ob[4] = ListarPro.get(i).getPrecioCompra();
            ob[5] = ListarPro.get(i).getFechaCompra();
            
            modelo.addRow(ob);
        }
        TablaCompra.setModel(modelo);

    }
        
    public boolean validarRutCliente(){
        if(!Pattern.matches("^[0-9]+-[0-9kK]{1}$", TxtRutCliente.getText().strip()) || !(TxtRutCliente.getText().strip().length() >= 9)){
            if (!(TxtRutCliente.getText().strip().equals(""))) {
                JOptionPane.showMessageDialog(null, "Ingrese el RUT sin puntos y con guion.");
            }
            return false;
        }
        String rut = TxtRutCliente.getText().strip().toUpperCase();
        int suma = 0;
        char digitoV = 0;
        int i = 2;
        for (char digito : new StringBuilder(rut).reverse().substring(2).toString().toCharArray()) {
            suma += i * (digito - '0');
            i++;
            if (i == 8) {
                i = 2;
            }
        }
        digitoV = (char) (11 - (suma - suma / 11 * 11));
        switch (digitoV) {
            case 10:
                digitoV = 'K';
            break;
            case 11:
                digitoV = '0';
            break;
            default:
                digitoV += '0';
        }
        if(rut.charAt(rut.length()-1) != digitoV){
            JOptionPane.showMessageDialog(null, "Rut no valido.");
            return false;
        }
        return true;
    }
    
        public boolean validarRutProveedores(){
        if(!Pattern.matches("^[0-9]+-[0-9kK]{1}$", txtRutProveedor.getText().strip()) || !(txtRutProveedor.getText().strip().length() >= 9)){
            if (!(txtRutProveedor.getText().strip().equals(""))) {
                JOptionPane.showMessageDialog(null, "Ingrese el RUT sin puntos y con guion.");
            }
            return false;
        }
        String rut = txtRutProveedor.getText().strip().toUpperCase();
        int suma = 0;
        char digitoV = 0;
        int i = 2;
        for (char digito : new StringBuilder(rut).reverse().substring(2).toString().toCharArray()) {
            suma += i * (digito - '0');
            i++;
            if (i == 8) {
                i = 2;
            }
        }
        digitoV = (char) (11 - (suma - suma / 11 * 11));
        switch (digitoV) {
            case 10:
                digitoV = 'K';
            break;
            case 11:
                digitoV = '0';
            break;
            default:
                digitoV += '0';
        }
        if(rut.charAt(rut.length()-1) != digitoV){
            JOptionPane.showMessageDialog(null, "Rut no valido.");
            return false;
        }
        return true;
    }
    


    
    
    public void ListarHistorialVentas() {
        List<Venta> ListarVenta = Vdao.Listarventas();
        modelo = (DefaultTableModel) TablaHistorialVenta.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getNombre_cli();
            ob[2] = ListarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        TablaHistorialVenta.setModel(modelo);
        
    }
    
        public Menu()  {

        initComponents();
        this.setLocationRelativeTo(null);
        DateCompra.setDate(fechaCompra);
        Midate.setDate(fechaVenta);
        txtIdCliente.setVisible(false);
        txtIdPro.setVisible(false);
        LabelVendedor.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdVentaPdf.setVisible(false);
        txtIdCV.setVisible(false);
        bt_Actualizar.setEnabled(false);
        bt_Eliminar.setEnabled(false);
        bt_Guardar.setEnabled(true);
        txtCantidadVenta.setEnabled(false);
        cbxProveedorPro.removeAllItems();
        cbxProveedorCom.removeAllItems();
        TablaVenta.setDefaultEditor(Object.class, null);
        DateCompra.getJCalendar().setMinSelectableDate(new Date());
        TablaProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaHistorialVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        llenarProveedor();
        LimpiarTabla();
        LimpiarTablaCompras();
        LimpiarTablaProductos();
        LimpiarTablaHistorialVentas();
        LimpiarProductosHistorial();
        ListarCompras();
        ListarProductos();
        LimpiarProveedor();
        LimpiarTablaCliente();
        LimpiarTablaProveedores();
        ListarProveedor();
        ListarCliente();
        ListarHistorialVentas();
        CP = new Cls_productos();
        columnModel = TablaProducto.getColumnModel();

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        txtRutVenta = new javax.swing.JTextField();
        txtNombreClienteventa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaVenta = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCantidadVenta = new javax.swing.JTextField();
        btnEliminarVenta = new javax.swing.JButton();
        LabelTotal = new javax.swing.JLabel();
        btnGenerarVenta = new javax.swing.JButton();
        txtIdCV = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtIdPro = new javax.swing.JTextField();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        lblFotoVenta = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtNombreVenta = new javax.swing.JTextField();
        txtCodigoVentaMostrar = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtPagoCliente = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        LabelVuelto = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaHistorialVenta = new javax.swing.JTable();
        btnPdfVenta = new javax.swing.JButton();
        txtIdVentaPdf = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaProductoHistorial = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        txtBuscarCodigoVenta = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdproducto = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        lblCodProd = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaProducto = new javax.swing.JTable();
        btnNuevoProducto = new javax.swing.JButton();
        bt_Guardar = new javax.swing.JButton();
        bt_Actualizar = new javax.swing.JButton();
        bt_Eliminar = new javax.swing.JButton();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        txtPrecioPro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCantPro = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        lblFoto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNomImagen = new javax.swing.JTextField();
        btnSeleccionar = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtBuscarProducto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        TxtRutCliente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtNombreCliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TxtTelefonoCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TxtDirecionCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaCliente = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtRutProveedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        btnguardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        LabelVendedor = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaProveedor = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        DateCompra = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TablaCompra = new javax.swing.JTable();
        cbxProveedorCom = new javax.swing.JComboBox<>();
        btnAddCompra = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtCodProdCompra = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtCantidadCompra = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Inventario");
        setResizable(false);

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        txtRutVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutVentaKeyTyped(evt);
            }
        });

        txtNombreClienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteventaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("TOTAL A PAGAR :");

        jLabel8.setText("NOMBRE:");

        jLabel7.setText("RUT CLIENTE:");

        TablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "PRODUCTO", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaVenta.getTableHeader().setReorderingAllowed(false);
        TablaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaVentaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaVenta);
        if (TablaVenta.getColumnModel().getColumnCount() > 0) {
            TablaVenta.getColumnModel().getColumn(1).setResizable(false);
            TablaVenta.getColumnModel().getColumn(2).setResizable(false);
            TablaVenta.getColumnModel().getColumn(3).setResizable(false);
            TablaVenta.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel4.setText("Cantidad:");

        jLabel3.setText("Buscar por Nombre:");

        jLabel6.setText("Nombre:");

        txtCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaActionPerformed(evt);
            }
        });
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("VENTAS SALGJEAN");
        jLabel9.setToolTipText("");

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        btnEliminarVenta.setText("Eliminar Venta");
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        LabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTotal.setText("----");

        btnGenerarVenta.setText("Generar Venta");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        txtIdCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCVActionPerformed(evt);
            }
        });

        txtStockDisponible.setEditable(false);
        txtStockDisponible.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel19.setText("Stock Disponible:");

        txtPrecioVenta.setEditable(false);

        jLabel20.setText("Precio:");

        Midate.setEnabled(false);

        jLabel21.setText("Fecha:");

        jLabel22.setText("Imagen :");

        jLabel29.setText("Codigo:");

        txtNombreVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreVentaKeyPressed(evt);
            }
        });

        txtCodigoVentaMostrar.setEditable(false);
        txtCodigoVentaMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaMostrarActionPerformed(evt);
            }
        });
        txtCodigoVentaMostrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaMostrarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaMostrarKeyTyped(evt);
            }
        });

        jLabel30.setText("Buscar por Codigo:");

        jLabel32.setText("PAGO CLIENTE:");

        txtPagoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPagoClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoClienteKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("VUELTO:");

        LabelVuelto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelVuelto.setText("----");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodigoVentaMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(115, 115, 115)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel21)
                                .addGap(18, 18, 18)
                                .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFotoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdCV, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnEliminarVenta)
                        .addGap(59, 59, 59))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRutVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreClienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPagoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(btnGenerarVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelVuelto)
                            .addComponent(LabelTotal))
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtIdCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 157, Short.MAX_VALUE)
                        .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(297, 297, 297))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodigoVentaMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel22))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(lblFotoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRutVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreClienteventa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(LabelTotal)
                    .addComponent(btnGenerarVenta)
                    .addComponent(jLabel32)
                    .addComponent(txtPagoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(LabelVuelto)))
        );

        jTabbedPane1.addTab("Nueva Venta", jPanel3);

        TablaHistorialVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CODIGO VENTA", "CLIENTE", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaHistorialVenta.getTableHeader().setReorderingAllowed(false);
        TablaHistorialVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaHistorialVentaMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TablaHistorialVenta);
        if (TablaHistorialVenta.getColumnModel().getColumnCount() > 0) {
            TablaHistorialVenta.getColumnModel().getColumn(0).setResizable(false);
            TablaHistorialVenta.getColumnModel().getColumn(1).setResizable(false);
            TablaHistorialVenta.getColumnModel().getColumn(2).setResizable(false);
        }

        btnPdfVenta.setText("Abrir PDF");
        btnPdfVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentaActionPerformed(evt);
            }
        });

        TablaProductoHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "PRODUCTO", "CANTIDAD", "PAGADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProductoHistorial.getTableHeader().setReorderingAllowed(false);
        TablaProductoHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProductoHistorialMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaProductoHistorial);
        if (TablaProductoHistorial.getColumnModel().getColumnCount() > 0) {
            TablaProductoHistorial.getColumnModel().getColumn(0).setResizable(false);
            TablaProductoHistorial.getColumnModel().getColumn(1).setResizable(false);
            TablaProductoHistorial.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel38.setText("Buscar por Codigo Venta:");

        txtBuscarCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarCodigoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCodigoVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCodigoVentaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnPdfVenta)
                .addGap(30, 30, 30))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscarCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdVentaPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdVentaPdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(txtBuscarCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(btnPdfVenta)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane8))
                .addGap(90, 90, 90))
        );

        jTabbedPane1.addTab("Ventas", jPanel8);

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });

        jLabel1.setText("Nombre :");

        txtIdproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdproductoActionPerformed(evt);
            }
        });

        lblCodProd.setText("Codigo Producto");

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });

        TablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "Codigo", "Nombre", "Proveedor", "Cantidad", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProducto.getTableHeader().setReorderingAllowed(false);
        TablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaProducto);
        if (TablaProducto.getColumnModel().getColumnCount() > 0) {
            TablaProducto.getColumnModel().getColumn(0).setMinWidth(0);
            TablaProducto.getColumnModel().getColumn(0).setPreferredWidth(0);
            TablaProducto.getColumnModel().getColumn(0).setMaxWidth(0);
            TablaProducto.getColumnModel().getColumn(1).setResizable(false);
            TablaProducto.getColumnModel().getColumn(2).setResizable(false);
            TablaProducto.getColumnModel().getColumn(3).setResizable(false);
            TablaProducto.getColumnModel().getColumn(4).setResizable(false);
            TablaProducto.getColumnModel().getColumn(5).setResizable(false);
        }

        btnNuevoProducto.setText("Nuevo");
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });

        bt_Guardar.setText("Guardar");
        bt_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_GuardarActionPerformed(evt);
            }
        });

        bt_Actualizar.setText("Modificar");
        bt_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ActualizarActionPerformed(evt);
            }
        });

        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_EliminarActionPerformed(evt);
            }
        });

        cbxProveedorPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProveedorProItemStateChanged(evt);
            }
        });
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });

        jLabel26.setText("Proveedor:");

        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });

        jLabel25.setText("Precio:");

        txtCantPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantProKeyTyped(evt);
            }
        });

        jLabel24.setText("Cantidad:");

        jLabel2.setText("Imagen :");

        txtNomImagen.setEditable(false);

        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        jLabel37.setText("Buscar Producto:");

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnNuevoProducto)
                        .addGap(116, 116, 116)
                        .addComponent(bt_Guardar)
                        .addGap(130, 130, 130)
                        .addComponent(bt_Actualizar)
                        .addGap(126, 126, 126)
                        .addComponent(bt_Eliminar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 892, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(29, 29, 29)
                                .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblCodProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSeleccionar)
                .addGap(45, 45, 45)
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(lblCodProd)
                        .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel24))
                    .addComponent(txtIdproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(66, 66, 66)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtNomImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSeleccionar)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoProducto)
                    .addComponent(bt_Guardar)
                    .addComponent(bt_Actualizar)
                    .addComponent(bt_Eliminar))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Productos", jPanel2);

        jLabel10.setText("RUT :");

        TxtRutCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtRutClienteActionPerformed(evt);
            }
        });
        TxtRutCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtRutClienteKeyTyped(evt);
            }
        });

        jLabel11.setText("Nombre :");

        jLabel12.setText("Telefono :");

        TxtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtTelefonoClienteKeyTyped(evt);
            }
        });

        jLabel13.setText("Direccion :");

        btnGuardarCliente.setText("Guardar Cliente");
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setText("Editar Cliente");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setText("Eliminar Cliente");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setText("Nuevo Cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        jLabel28.setText("(Ej: 123456789-9)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtDirecionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtRutCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TxtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TxtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(TxtDirecionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarCliente)
                    .addComponent(btnEditarCliente))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarCliente)
                    .addComponent(btnNuevoCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        TablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "RUT", "Nombre", "Telefono", "Direccion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCliente.getTableHeader().setReorderingAllowed(false);
        TablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaClienteMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaCliente);
        if (TablaCliente.getColumnModel().getColumnCount() > 0) {
            TablaCliente.getColumnModel().getColumn(0).setMinWidth(0);
            TablaCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
            TablaCliente.getColumnModel().getColumn(0).setMaxWidth(0);
            TablaCliente.getColumnModel().getColumn(1).setResizable(false);
            TablaCliente.getColumnModel().getColumn(2).setResizable(false);
            TablaCliente.getColumnModel().getColumn(3).setResizable(false);
            TablaCliente.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(218, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanel1);

        jLabel14.setText("Nuevo Proveedor");

        jLabel15.setText("RUT:");

        txtRutProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutProveedorActionPerformed(evt);
            }
        });
        txtRutProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutProveedorKeyTyped(evt);
            }
        });

        jLabel16.setText("Nombre:");

        jLabel17.setText("Telefono:");

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        jLabel18.setText("Direccion:");

        txtDireccionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionProveedorActionPerformed(evt);
            }
        });

        btnguardarProveedor.setText("Guardar Proveedor");
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setText("Editar Proveedor");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setText("Eliminar Proveedor");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setText("Nuevo Proveedor");
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        LabelVendedor.setText("Vendedor");

        jLabel33.setText("Ej: (Ej: 123456789-9)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 298, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnguardarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(LabelVendedor))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel17))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txtTelefonoProveedor)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(24, 24, 24)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRutProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnguardarProveedor)
                    .addComponent(btnEditarProveedor))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarProveedor)
                    .addComponent(btnNuevoProveedor))
                .addGap(48, 48, 48)
                .addComponent(LabelVendedor)
                .addGap(0, 69, Short.MAX_VALUE))
        );

        TablaProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "COD", "RUT", "NOMBRE", "TELEFONO", "DIRECCION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProveedor.getTableHeader().setReorderingAllowed(false);
        TablaProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProveedorMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TablaProveedor);
        if (TablaProveedor.getColumnModel().getColumnCount() > 0) {
            TablaProveedor.getColumnModel().getColumn(0).setResizable(false);
            TablaProveedor.getColumnModel().getColumn(1).setResizable(false);
            TablaProveedor.getColumnModel().getColumn(2).setResizable(false);
            TablaProveedor.getColumnModel().getColumn(3).setResizable(false);
            TablaProveedor.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(208, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedores", jPanel5);

        jLabel23.setText("Proveedor:");

        jLabel27.setText("Fecha:");

        TablaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cod  Compra", "Cod Producto", "Proveedor", "Cantidad", "Precio", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCompra.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(TablaCompra);
        if (TablaCompra.getColumnModel().getColumnCount() > 0) {
            TablaCompra.getColumnModel().getColumn(0).setResizable(false);
            TablaCompra.getColumnModel().getColumn(1).setResizable(false);
            TablaCompra.getColumnModel().getColumn(2).setResizable(false);
            TablaCompra.getColumnModel().getColumn(3).setResizable(false);
            TablaCompra.getColumnModel().getColumn(4).setResizable(false);
            TablaCompra.getColumnModel().getColumn(5).setResizable(false);
        }

        btnAddCompra.setText("Añadir Compra");
        btnAddCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompraActionPerformed(evt);
            }
        });

        jLabel34.setText("Codigo Producto:");

        txtCodProdCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodProdCompraKeyTyped(evt);
            }
        });

        jLabel35.setText("Cantidad:");

        txtCantidadCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyTyped(evt);
            }
        });

        jLabel36.setText("Precio:");

        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(712, 712, 712)
                                .addComponent(btnAddCompra))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(cbxProveedorCom, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodProdCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidadCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(DateCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cbxProveedorCom, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(txtCodProdCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(txtCantidadCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(DateCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(btnAddCompra)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Compras", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreClienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteventaActionPerformed

    private void txtCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaActionPerformed

    private void txtRutProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutProveedorActionPerformed

    private void txtDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTablaProveedores();
                LimpiarProveedor();
                ListarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }                    
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void cbxProveedorProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProveedorProItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProItemStateChanged

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        // TODO add your handling code here:
       event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        LimpiarProductos();
        
        bt_Guardar.setEnabled(true);   
        TablaProducto.getSelectionModel().clearSelection();
        LimpiarTablaProductos();
        ListarProductos();// TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void bt_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_GuardarActionPerformed
String sqlDuplicate = "select codigo from productos where codigo = "+txtCodigoProducto.getText()+" ";
String sqlDuplicate2 = "select nombre from productos where nombre = "+"'"+txtNombreProducto.getText().trim()+"' ";
if (!"".equals(txtCodigoProducto.getText().trim()) && !"".equals(txtNombreProducto.getText().trim()) && !"".equals(cbxProveedorPro.getSelectedItem()) && !"".equals(txtCantPro.getText().trim()) && !"".equals(txtPrecioPro.getText().trim())) {
                   try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             Statement stmt2 = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDuplicate);
             ResultSet rs2 = stmt2.executeQuery(sqlDuplicate2);
             int precio = Integer.parseInt(txtPrecioPro.getText().trim());
               if(rs.next()==true){
                   JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
               }else{
               if(rs2.next()==true){
                   JOptionPane.showMessageDialog(null, "Nombre ya en uso.");
               }else{
         if(precio == 0){
             JOptionPane.showMessageDialog(null, "El Precio debe ser mayor a 0.");
         }else{
               
        
            pro.setCodigo(txtCodigoProducto.getText().trim());
            pro.setNombre(txtNombreProducto.getText().trim());
            Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
            pro.setProveedor(itemP.getId());
            pro.setStock(Integer.parseInt(txtCantPro.getText().trim()));
            pro.setPrecio(Integer.parseInt(txtPrecioPro.getText().trim()));
            pro.setNomimagen(txtNomImagen.getText());
            try {
                proDao.RegistrarProductos(pro);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Productos Registrado");
            LimpiarTablaProductos();
            ListarProductos();
         cbxProveedorPro.removeAllItems();
            llenarProveedor();
            bt_Actualizar.setEnabled(false);
            bt_Eliminar.setEnabled(false);
            bt_Guardar.setEnabled(true);

               }}
               }
                   } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
            } else {
            JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
        }
    }//GEN-LAST:event_bt_GuardarActionPerformed

    private void txtIdCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCVActionPerformed

    private void bt_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ActualizarActionPerformed
       String sqlDuplicate = "select codigo from productos where codigo = "+txtCodigoProducto.getText().trim()+" and id <> "+Integer.parseInt(txtIdproducto.getText())+" ";

        
        
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else if("".equals(txtBuscarProducto.getText().trim())){
           try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDuplicate);
               if(rs.next()==true){
                   JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
               }else{
                   if (!"".equals(txtCodigoProducto.getText().trim()) || !"".equals(txtNombreProducto.getText().trim()) || !"".equals(txtCantPro.getText().trim()) || !"".equals(txtPrecioPro.getText().trim())) {
                       pro.setCodigo(txtCodigoProducto.getText().trim());
                       pro.setNombre(txtNombreProducto.getText().trim());
                       Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                       pro.setProveedor(itemP.getId());
                       pro.setStock(Integer.parseInt(txtCantPro.getText().trim()));
                       pro.setPrecio(Integer.parseInt(txtPrecioPro.getText().trim()));
                       pro.setNomimagen(txtNomImagen.getText());
                       pro.setId(Integer.parseInt(txtIdproducto.getText()));
                       try {
                           proDao.ModificarProductos(pro);
                       } catch (SQLException ex) {
                           Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       JOptionPane.showMessageDialog(null, "Producto Modificado");
                       LimpiarTablaProductos();
                       LimpiarProductos();
                       ListarProductos();;
                       cbxProveedorPro.removeAllItems();
                       llenarProveedor();
                       bt_Actualizar.setEnabled(false);
                       bt_Eliminar.setEnabled(false);
                       bt_Guardar.setEnabled(true);
                   }
               }
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
           }
}else{
                       try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDuplicate);
               if(rs.next()==true){
                   JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
               }else{
                   if (!"".equals(txtCodigoProducto.getText().trim()) || !"".equals(txtNombreProducto.getText().trim()) || !"".equals(txtCantPro.getText().trim()) || !"".equals(txtPrecioPro.getText().trim())) {
                       pro.setCodigo(txtCodigoProducto.getText().trim());
                       pro.setNombre(txtNombreProducto.getText().trim());
                       Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                       pro.setProveedor(itemP.getId());
                       pro.setStock(Integer.parseInt(txtCantPro.getText().trim()));
                       pro.setPrecio(Integer.parseInt(txtPrecioPro.getText().trim()));
                       pro.setNomimagen(txtNomImagen.getText());
                       pro.setId(Integer.parseInt(txtIdproducto.getText()));
                       try {
                           proDao.ModificarProductos(pro);
                       } catch (SQLException ex) {
                           Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       JOptionPane.showMessageDialog(null, "Producto Modificado");
                       LimpiarTablaProductos();
                       LimpiarProductos();
                       ListarProductos();;
                       cbxProveedorPro.removeAllItems();
                       llenarProveedor();
                       bt_Actualizar.setEnabled(false);
                       bt_Eliminar.setEnabled(false);
                       bt_Guardar.setEnabled(true);
                   }
               }
           } catch (ClassNotFoundException | SQLException ex) {
               Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
           }
                LimpiarTablaProductos();
        ListarProductos();
        
        }       // TODO add your handling code here:
    }//GEN-LAST:event_bt_ActualizarActionPerformed

    private void TablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProductoMouseClicked
        bt_Actualizar.setEnabled(true);
        bt_Eliminar.setEnabled(true);
        bt_Guardar.setEnabled(false);
        int fila = TablaProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TablaProducto.getValueAt(fila, 0).toString());
        pro = proDao.BuscarId(Integer.parseInt(txtIdproducto.getText()));
        txtCodigoProducto.setText(pro.getCodigo());
        txtNombreProducto.setText(pro.getNombre());
        txtCantPro.setText("" + pro.getStock());
        txtPrecioPro.setText("" + pro.getPrecio());
        txtNomImagen.setText(pro.getNomimagen());
        cbxProveedorPro.setSelectedItem(new Combo(pro.getProveedor(), pro.getProveedorPro()));



        try {
            // TODO add your handling code here:
            
            Menu.func f = new Menu.func();
            rs = f.find(pro.getId());
            if(rs.next()){
                byte[] img = rs.getBytes("imagen");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myimg = im.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myimg);
                lblFoto.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }  // TODO add your handling code here:
    }//GEN-LAST:event_TablaProductoMouseClicked

    private void TablaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaVentaMouseClicked
        int fila = TablaVenta.rowAtPoint(evt.getPoint());     // TODO add your handling code here:
    }//GEN-LAST:event_TablaVentaMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
       // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseEntered

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Selecione una fila");
        } else {
            if (!"".equals(txtRutProveedor.getText().trim()) || !"".equals(txtNombreProveedor.getText().trim()) || !"".equals(txtTelefonoProveedor.getText().trim()) || !"".equals(txtDireccionProveedor.getText().trim())) {
                pr.setRut(txtRutProveedor.getText().trim());
                pr.setNombre(txtNombreProveedor.getText().trim());
                pr.setTelefono(txtTelefonoProveedor.getText().trim());
                pr.setDireccion(txtDireccionProveedor.getText().trim());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTablaProveedores();
                cbxProveedorPro.removeAllItems();
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed
        
        String sqlDuplicate = "select rut from proveedor where rut = '"+txtRutProveedor.getText().trim()+"' ";

                           try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDuplicate);
               if(rs.next()==true){
                   JOptionPane.showMessageDialog(null, "El RUT ya esta registrado.");
               }else{
        
        if (!"".equals(txtRutProveedor.getText()) && !"".equals(txtNombreProveedor.getText().trim()) && !"".equals(txtTelefonoProveedor.getText().trim()) && !"".equals(txtDireccionProveedor.getText().trim())) {
            if(validarRutProveedores()){
            pr.setRut(txtRutProveedor.getText().trim());
            pr.setNombre(txtNombreProveedor.getText().trim());
            pr.setTelefono(txtTelefonoProveedor.getText().trim());
            pr.setDireccion(txtDireccionProveedor.getText().trim());
            PrDao.RegistrarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            LimpiarTablaProveedores();
            ListarProveedor();
            LimpiarProveedor();
            cbxProveedorPro.removeAllItems();
            cbxProveedorCom.removeAllItems();
            llenarProveedor();
            btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnguardarProveedor.setEnabled(true);}
        } else {
            JOptionPane.showMessageDialog(null, "Algunos campos esta vacios.");
        }
               }
                   } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnguardarProveedor.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText().trim())) {
                if(!"0".equals(txtCantidadVenta.getText().trim())){
                int id = Integer.parseInt(txtIdPro.getText());
                String descripcion = txtDescripcionVenta.getText().trim();
                int cant = Integer.parseInt(txtCantidadVenta.getText().trim());
                int precio = Integer.parseInt(txtPrecioVenta.getText().trim());
                int total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText().trim());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TablaVenta.getModel();
                    for (int i = 0; i < TablaVenta.getRowCount(); i++) {
                        if (TablaVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText().trim())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(id);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    TablaVenta.setModel(tmp);
                    TotalPagar();
                    LimpiarVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad mayor a 0.");
            }
        }else{
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
                // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        if (TablaVenta.getRowCount() > 0) {
        int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
        if("".equals(txtPagoCliente.getText().trim())){
            JOptionPane.showMessageDialog(null, "Pago del cliente esta vacio.");
        }else{
        int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
        if(TmpPago >= TmpTotal){
            if (!"".equals(txtNombreClienteventa.getText().trim())) {
                VueltoTotalBotonVenta();
                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                LimpiarTablaVenta();
                LimpiarClienteventa();
                LimpiarTablaProductos();
                ListarProductos();
                txtCantidadVenta.setEnabled(false);
            } else {
                txtNombreClienteventa.setText("Venta Rapida");
                VueltoTotalBotonVenta();
                RegistrarVentaRapida();
                RegistrarDetalleRapido();
                ActualizarStock();
                LimpiarTablaVenta();
                LimpiarClienteventa();
                LimpiarTablaProductos();
                ListarProductos();
                LimpiarTablaHistorialVentas();
                ListarHistorialVentas();
                LabelTotal.setText("----");
                LabelVuelto.setText("----");
                txtPagoCliente.setText("");
                txtCantidadVenta.setEnabled(false);
            }
        }else{
            JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");

        }
        }
        }else{
            JOptionPane.showMessageDialog(null, "La venta esta vacia.");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        
        
        modelo = (DefaultTableModel) TablaVenta.getModel();
        modelo.removeRow(TablaVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText().trim())) {
                String cod = txtCodigoVenta.getText().trim();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    txtIdPro.setText("" + pro.getId());
                    txtCodigoVentaMostrar.setText(cod);
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockDisponible.setText("" + pro.getStock());
                    txtCantidadVenta.setEnabled(true);
                    txtCantidadVenta.requestFocus();
                            try {
            // TODO add your handling code here:
            
            Menu.func f = new Menu.func();
            rs = f.find(pro.getId());
            if(rs.next()){
                byte[] img = rs.getBytes("imagen");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myimg = im.getScaledInstance(lblFotoVenta.getWidth(), lblFotoVenta.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myimg);
                lblFotoVenta.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } 
                } else {
                    LimpiarVenta();
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoVenta.requestFocus();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
event.numberDecimalKeyPress(evt, txtCodigoVenta);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
event.numberDecimalKeyPress(evt, txtCantidadVenta);         // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void bt_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_EliminarActionPerformed
        
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText().trim());
                lblFoto.setIcon(null);
                proDao.EliminarProductos(id);
                LimpiarTablaProductos();
                LimpiarProductos();
                ListarProductos();
                bt_Actualizar.setEnabled(false);
                bt_Eliminar.setEnabled(false);
                bt_Guardar.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_bt_EliminarActionPerformed

    private void txtIdproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdproductoActionPerformed

    private void txtRutVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRutVenta.getText().trim())) {
                String rut = txtRutVenta.getText().trim();
                cl = client.Buscarcliente(rut);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRutVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutVentaKeyPressed

    private void txtRutVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutVentaKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRutVenta.getText().trim())) {
                String rut = txtRutVenta.getText().trim();
                cl = client.Buscarcliente(rut);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRutVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutVentaKeyTyped

    private void TablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaClienteMouseClicked
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TablaCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TablaCliente.getValueAt(fila, 0).toString());
        TxtRutCliente.setText(TablaCliente.getValueAt(fila, 1).toString());
        TxtNombreCliente.setText(TablaCliente.getValueAt(fila, 2).toString());
        TxtTelefonoCliente.setText(TablaCliente.getValueAt(fila, 3).toString());
        TxtDirecionCliente.setText(TablaCliente.getValueAt(fila, 4).toString());        // TODO add your handling code here:
    }//GEN-LAST:event_TablaClienteMouseClicked

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        JFileChooser archivo = new JFileChooser();
        //Si deseamos crear filtros para la selecion de archivos
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg","jpeg");
        //Si deseas que se muestre primero los filtros usa la linea q esta abajo de esta.
        //archivo.setFileFilter(filtro);
        // Agregamos el Filtro pero cuidado se mostrara despues de todos los archivos
        archivo.addChoosableFileFilter(filtro);
        // Colocamos titulo a nuestra ventana de Seleccion
        archivo.setDialogTitle("Abrir Archivo");
        //Si deseamos que muestre una carpeta predetermina usa la siguiente linea
        File ruta = new File("D:/Productos");
        //Le implementamos a nuestro ventana de seleccion
         archivo.setCurrentDirectory(ruta);
         //Abrimos nuestra Ventana de Selccion
        int ventana = archivo.showOpenDialog(null);
        //hacemos comparacion en caso de aprete el boton abrir
        if(ventana == JFileChooser.APPROVE_OPTION)
        {
            //Obtenemos la ruta de nuestra imagen seleccionada
            File file = archivo.getSelectedFile();
            //Lo imprimimos en una caja de texto para ver su ruta
            txtNomImagen.setText(String.valueOf(file));
            //de cierto modo necesitamos tener la imagen para ello debemos conocer la ruta de dicha imagen
            Image foto= getToolkit().getImage(txtNomImagen.getText());
            //Le damos dimension a nuestro label que tendra la imagen
            foto= foto.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            //Imprimimos la imagen en el label
            lblFoto.setIcon(new ImageIcon(foto));
        }
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void TablaProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProveedorMouseClicked
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);
        int fila = TablaProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TablaProveedor.getValueAt(fila, 0).toString());
        txtRutProveedor.setText(TablaProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(TablaProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TablaProveedor.getValueAt(fila,3).toString());
        txtDireccionProveedor.setText(TablaProveedor.getValueAt(fila, 4).toString());        // TODO add your handling code here:
    }//GEN-LAST:event_TablaProveedorMouseClicked

    private void txtNombreVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtNombreVenta.getText().trim())) {
                String nom = txtNombreVenta.getText().trim();
                pro = proDao.BuscarProNombre(nom);
                if (pro.getNombre() != null) {
                    txtIdPro.setText("" + pro.getId());
                    txtCodigoVentaMostrar.setText("" + pro.getCodigo());
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockDisponible.setText("" + pro.getStock());
                    txtCantidadVenta.setEnabled(true);
                    txtCantidadVenta.requestFocus();
                            try {
            // TODO add your handling code here:
            
            Menu.func f = new Menu.func();
            rs = f.find(pro.getId());
            if(rs.next()){
                byte[] img = rs.getBytes("imagen");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myimg = im.getScaledInstance(lblFotoVenta.getWidth(), lblFotoVenta.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myimg);
                lblFotoVenta.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } 
                } else {
                    LimpiarVenta();
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                txtCodigoVenta.requestFocus();
            }
        }        // TODO add your handling code here:              // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreVentaKeyPressed

    private void txtCodigoVentaMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarActionPerformed

    private void txtCodigoVentaMostrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarKeyPressed

    private void txtCodigoVentaMostrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarKeyTyped

    private void txtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyTyped
event.numberDecimalKeyPress(evt, txtCodigoProducto);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProductoKeyTyped

    private void txtCantProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantProKeyTyped
event.numberDecimalKeyPress(evt, txtCantPro);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantProKeyTyped

    private void txtRutProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
event.numberDecimalKeyPress(evt, txtTelefonoProveedor);  
            if (txtTelefonoProveedor.getText().trim().length() >= 9 ){ // limit to 3 characters
                evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtPagoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoClienteKeyTyped
event.numberDecimalKeyPress(evt, txtPagoCliente);                 // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoClienteKeyTyped

    private void txtPagoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoClienteKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
    if (TablaVenta.getRowCount() > 0) {
        int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
        int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
        if(TmpPago >= TmpTotal){
       
        VueltoTotal();
        }else{
                    JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");

        }

            
        }else{
        JOptionPane.showMessageDialog(null, "No hay productos en la venta");
    }
}
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoClienteKeyPressed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        LimpiarCliente();
        
        TablaCliente.getSelectionModel().clearSelection();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarCliente();
                LimpiarTablaCliente();
                ListarCliente();
            }
        }    // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(TxtRutCliente.getText().trim()) || !"".equals(TxtNombreCliente.getText().trim()) || !"".equals(TxtTelefonoCliente.getText().trim())) {
                cl.setRut(TxtRutCliente.getText().trim());
                cl.setNombre(TxtNombreCliente.getText().trim());
                cl.setTelefono(TxtTelefonoCliente.getText().trim());
                cl.setDireccion(TxtDirecionCliente.getText().trim());
                cl.setId(Integer.parseInt(txtIdCliente.getText().trim()));
                client.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarCliente();
                LimpiarTablaCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
String sqlDuplicate = "select rut from clientes where rut = '"+TxtRutCliente.getText().trim()+"' ";

                           try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDuplicate);
               if(rs.next()==true){
                   JOptionPane.showMessageDialog(null, "El RUT ya esta registrado.");
               }else{
        
        
        if (!"".equals(TxtRutCliente.getText()) && !"".equals(TxtNombreCliente.getText()) && !"".equals(TxtTelefonoCliente.getText()) && !"".equals(TxtDirecionCliente.getText())) {
            if(validarRutCliente()){
                cl.setRut(TxtRutCliente.getText().trim());
                cl.setNombre(TxtNombreCliente.getText().trim());
                cl.setTelefono(TxtTelefonoCliente.getText().trim());
                cl.setDireccion(TxtDirecionCliente.getText().trim());
                client.RegistrarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Registrado");
                LimpiarCliente();
                LimpiarTablaCliente();
                ListarCliente();
                btnEditarCliente.setEnabled(false);
                btnEliminarCliente.setEnabled(false);
                btnGuardarCliente.setEnabled(true);}
        } else {
            JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
        }     
               }
                   } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void TxtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtTelefonoClienteKeyTyped
        event.numberDecimalKeyPress(evt, TxtTelefonoCliente);

        if (TxtTelefonoCliente.getText().trim().length() >= 9 ){ // limit to 3 characters
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelefonoClienteKeyTyped

    private void TxtRutClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtRutClienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtRutClienteKeyTyped

    private void TxtRutClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtRutClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtRutClienteActionPerformed

    private void btnPdfVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentaActionPerformed
        if(txtIdVentaPdf.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }else{
            v = Vdao.BuscarVenta(Integer.parseInt(txtIdVentaPdf.getText()));
            Vdao.pdfV(v.getId(),0,v.getPago(),v.getTotal(),v.getVuelto(),v.getVendedor());
        }      // TODO add your handling code here:
    }//GEN-LAST:event_btnPdfVentaActionPerformed

    private void TablaHistorialVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaHistorialVentaMouseClicked
        int fila = TablaHistorialVenta.rowAtPoint(evt.getPoint());
        txtIdVentaPdf.setText(TablaHistorialVenta.getValueAt(fila, 0).toString());  
        v = Vdao.BuscarVenta(Integer.parseInt(txtIdVentaPdf.getText()));
        LimpiarProductosHistorial();
        ListarProductosHitorial();
        
// TODO add your handling code here:
    }//GEN-LAST:event_TablaHistorialVentaMouseClicked

    private void txtCodProdCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdCompraKeyTyped
event.numberDecimalKeyPress(evt, txtCodProdCompra);            // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdCompraKeyTyped

    private void txtCantidadCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCompraKeyTyped
event.numberDecimalKeyPress(evt, txtCantidadCompra);           // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadCompraKeyTyped

    private void txtPrecioCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyPressed
  // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioCompraKeyPressed

    private void txtPrecioCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyTyped
       event.numberDecimalKeyPress(evt, txtPrecioCompra);           // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioCompraKeyTyped

    private void btnAddCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCompraActionPerformed
String sqlFind = "select codigo from productos where codigo = "+txtCodProdCompra.getText().trim()+" ";
                   try {
            Class.forName("com.mysql.jdbc.Driver");
             con = cn.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlFind);
               if(rs.next()==true){
                   
                com.setCodigoProducto(Integer.parseInt(txtCodProdCompra.getText().trim()));
                com.setCantidad(Integer.parseInt(txtCantidadCompra.getText().trim()));
                Combo itemP = (Combo) cbxProveedorCom.getSelectedItem();
                com.setIdProveedor(itemP.getId());
                com.setPrecioCompra(Integer.parseInt(txtPrecioCompra.getText().trim()));
                com.setFechaCompra(fechaCompraActual);
                comDao.RegistrarCompra(com);
                JOptionPane.showMessageDialog(null, "Productos Registrado");
               }else{
                   JOptionPane.showMessageDialog(null, "El codigo de producto no existe.");
               }
               
               LimpiarTablaCompras();
               ListarCompras();
                   } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_btnAddCompraActionPerformed

    private void txtBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyTyped
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProductoKeyTyped

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        if (!"".equals(txtBuscarProducto.getText().trim())){
            LimpiarTablaProductos();
            BuscarProductos();
        }else{
        LimpiarTablaProductos();
        ListarProductos();}        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

    private void TablaProductoHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProductoHistorialMouseClicked

        

        // TODO add your handling code here:
    }//GEN-LAST:event_TablaProductoHistorialMouseClicked

    private void txtBuscarCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoVentaKeyPressed
event.numberDecimalKeyPress(evt, txtBuscarCodigoVenta);        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoVentaKeyPressed

    private void txtBuscarCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoVentaKeyTyped

    private void txtBuscarCodigoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCodigoVentaKeyReleased

        if (!"".equals(txtBuscarCodigoVenta.getText().trim())){
            LimpiarTablaHistorialVentas();
            LimpiarProductosHistorial();
            BuscarVentas();
        }else{
        LimpiarTablaHistorialVentas();
        ListarHistorialVentas();}        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoVentaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateCompra;
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelVendedor;
    private javax.swing.JLabel LabelVuelto;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TablaCliente;
    private javax.swing.JTable TablaCompra;
    private javax.swing.JTable TablaHistorialVenta;
    private javax.swing.JTable TablaProducto;
    private javax.swing.JTable TablaProductoHistorial;
    private javax.swing.JTable TablaProveedor;
    private javax.swing.JTable TablaVenta;
    private javax.swing.JTextField TxtDirecionCliente;
    private javax.swing.JTextField TxtNombreCliente;
    private javax.swing.JTextField TxtRutCliente;
    private javax.swing.JTextField TxtTelefonoCliente;
    private javax.swing.JButton bt_Actualizar;
    private javax.swing.JButton bt_Eliminar;
    private javax.swing.JButton bt_Guardar;
    private javax.swing.JButton btnAddCompra;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVenta;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JButton btnguardarProveedor;
    private javax.swing.JComboBox<Object> cbxProveedorCom;
    private javax.swing.JComboBox<Object> cbxProveedorPro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCodProd;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblFotoVenta;
    private javax.swing.JTextField txtBuscarCodigoVenta;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCantidadCompra;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodProdCompra;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtCodigoVentaMostrar;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVentaPdf;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtNomImagen;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNombreVenta;
    private javax.swing.JTextField txtPagoCliente;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRutProveedor;
    private javax.swing.JTextField txtRutVenta;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables


private void LimpiarCliente() {
        TxtRutCliente.setText("");
        TxtNombreCliente.setText("");
        TxtTelefonoCliente.setText("");
        TxtDirecionCliente.setText("");
    }



    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRutProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdPro.setText("");
        txtCodigoProducto.setText("");
        cbxProveedorPro.setSelectedItem(0);
        lblFoto.setIcon(null);
        txtNombreProducto.setText("");
        txtCantPro.setText("");
        txtPrecioPro.setText("");
        txtBuscarProducto.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0;
        int numFila = TablaVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            int cal = Integer.parseInt(String.valueOf(TablaVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(Integer.toString(Totalpagar));
    }
    
    
    private void VueltoTotal() {
        Vueltototal = 0;
        PagoCliente = Integer.parseInt(txtPagoCliente.getText().trim());
        int aPagar = Integer.parseInt(LabelTotal.getText().trim());
        Vueltototal = PagoCliente - aPagar;
        
        LabelVuelto.setText(Integer.toString(Vueltototal));

        
        
    }
    
    
    private void VueltoTotalBotonVenta() {
 if (TablaVenta.getRowCount() > 0) {
        int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
        int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
        if(TmpPago >= TmpTotal){
            VueltoTotal();
        }else{
            JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");
        }
            
        }else{
     JOptionPane.showMessageDialog(null, "No hay productos en la venta");
 }
        
    }
    private void LimpiarVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtCodigoVentaMostrar.setText("");
        lblFotoVenta.setIcon(null);
        txtNombreVenta.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdCV.getText().trim());
        String vendedor = LabelVendedor.getText().trim();
        int monto = Totalpagar;
        int pago = PagoCliente;
        int vuelto = Vueltototal;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setPago(pago);
        v.setTotal(monto);
        v.setVuelto(vuelto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }
    private void RegistrarVentaRapida() {
        String vendedor = LabelVendedor.getText().trim();
        int monto = Totalpagar;
        int pago = PagoCliente;
        int vuelto = Vueltototal;
        v.setCliente(0);
        v.setVendedor(vendedor);
        v.setPago(pago);
        v.setTotal(monto);
        v.setVuelto(vuelto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalleRapido() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TablaVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TablaVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TablaVenta.getValueAt(i, 2).toString());
            int precio = Integer.parseInt(TablaVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);


        }
        Vdao.pdfV(id, 0, Integer.parseInt(txtPagoCliente.getText().trim()),Totalpagar,Integer.parseInt(LabelVuelto.getText().trim()),LabelVendedor.getText().trim());
    }
    
        private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TablaVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TablaVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TablaVenta.getValueAt(i, 2).toString());
            int precio = Integer.parseInt(TablaVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);


        }
        int cliente = Integer.parseInt(txtIdCV.getText());
        Vdao.pdfV(id, cliente, Integer.parseInt(txtPagoCliente.getText().trim()),Totalpagar,Integer.parseInt(LabelVuelto.getText().trim()),LabelVendedor.getText().trim());
    }

    private void ActualizarStock() {
        for (int i = 0; i < TablaVenta.getRowCount(); i++) {
            int id = Integer.parseInt(TablaVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TablaVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarId(id);
            int StockActual = pro.getStock() - cant;
            Vdao.ActualizarStock(StockActual, id);

        }
    }
    private void LimpiarTablaProductos() {
        tmp = (DefaultTableModel) TablaProducto.getModel();
        int fila = TablaProducto.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    private void LimpiarTablaCompras() {
        tmp = (DefaultTableModel) TablaCompra.getModel();
        int fila = TablaCompra.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    private void LimpiarTablaHistorialVentas() {
        tmp = (DefaultTableModel) TablaHistorialVenta.getModel();
        int fila = TablaHistorialVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    private void LimpiarCompras() {
        tmp = (DefaultTableModel) TablaCompra.getModel();
        int fila = TablaCompra.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
        private void LimpiarProductosHistorial() {
        tmp = (DefaultTableModel) TablaProductoHistorial.getModel();
        int fila = TablaProductoHistorial.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    
    private void LimpiarTablaVenta() {
        tmp = (DefaultTableModel) TablaVenta.getModel();
        int fila = TablaVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
      private void LimpiarTablaProveedores() {
        tmp = (DefaultTableModel) TablaProveedor.getModel();
        int fila = TablaProveedor.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

 private void LimpiarTablaCliente() {
        tmp = (DefaultTableModel) TablaCliente.getModel();
        int fila = TablaCliente.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
      
    private void LimpiarClienteventa() {
        txtRutVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdCV.setText("");
    }

    private void llenarProveedor(){
        List<Proveedor> lista = PrDao.ListarProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new Combo(id, nombre));
            cbxProveedorCom.addItem(new Combo(id, nombre));
        }
    }
}