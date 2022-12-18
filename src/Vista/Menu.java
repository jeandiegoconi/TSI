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
import java.util.Locale;
import java.text.NumberFormat;

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
    int item2;
    int Totalpagar = 0;
    int PagoCliente = 0;
    int Vueltototal = 0;
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public class func {

        public ResultSet find(int id) {
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
            Locale chileLocale = new Locale("es", "CL");
            NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
            String FormattedPrecio = "$" + nf.format(ListarProdHis.get(i).getPrecio());
            Object[] ob = new Object[3];
            ob[0] = ListarProdHis.get(i).getNombre();
            ob[2] = FormattedPrecio;
            ob[1] = ListarProdHis.get(i).getCantidad();
            modelo.addRow(ob);
        }
        TablaProductoHistorial.setModel(modelo);

    }

    public void ListarProductosHitorialCompra() {
        List<Detalle> ListarProdHisCom = DeDao.ListarProdHisCom(Integer.parseInt(txtIdCompraHis.getText()));
        modelo = (DefaultTableModel) TablaProductosComprasLista.getModel();
        for (int i = 0; i < ListarProdHisCom.size(); i++) {
            Object[] ob = new Object[3];

            ob[0] = ListarProdHisCom.get(i).getId_pro();
            ob[1] = ListarProdHisCom.get(i).getNombre();
            ob[2] = ListarProdHisCom.get(i).getCantidad();
            modelo.addRow(ob);
        }
        TablaProductosComprasLista.setModel(modelo);

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

    public void BuscarVentasProd() {
        List<Venta> VentasPro = Vdao.BuscarListaVentaProd(txtBuscarVentaProd.getText().trim());
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
            Locale chileLocale = new Locale("es", "CL");
            NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
            String FormattedPrecio = "$" + nf.format(ListarPro.get(i).getPrecio());
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = FormattedPrecio;

            modelo.addRow(ob);
        }
        TablaProducto.setModel(modelo);

    }

    public void ListarCompras() {
        List<Compra> ListarPro = comDao.ListarCompras();
        modelo = (DefaultTableModel) TablaCompra.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            Locale chileLocale = new Locale("es", "CL");
            NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
            String FormattedPrecio = "$" + nf.format(ListarPro.get(i).getPrecioCompra());
            ob[0] = ListarPro.get(i).getCodigoCompra();
            ob[1] = ListarPro.get(i).getProveedor();
            ob[2] = FormattedPrecio;
            ob[3] = ListarPro.get(i).getFechaCompra();

            modelo.addRow(ob);
        }
        TablaCompra.setModel(modelo);

    }

    public boolean validarRutCliente() {
        if (!Pattern.matches("^[0-9]+-[0-9kK]{1}$", TxtRutCliente.getText().strip()) || !(TxtRutCliente.getText().strip().length() >= 9)) {
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
        if (rut.charAt(rut.length() - 1) != digitoV) {
            JOptionPane.showMessageDialog(null, "Rut no valido.");
            return false;
        }
        return true;
    }

    public boolean validarRutProveedores() {
        if (!Pattern.matches("^[0-9]+-[0-9kK]{1}$", txtRutProveedor.getText().strip()) || !(txtRutProveedor.getText().strip().length() >= 9)) {
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
        if (rut.charAt(rut.length() - 1) != digitoV) {
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
            Locale chileLocale = new Locale("es", "CL");
            NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
            String FormattedTotal = "$" + nf.format(ListarVenta.get(i).getTotal());
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getNombre_cli();
            ob[2] = FormattedTotal;
            modelo.addRow(ob);
        }
        TablaHistorialVenta.setModel(modelo);

    }

    public Menu() {

        initComponents();

        //   btnEliminarVenta.setEnabled(false);
        this.setLocationRelativeTo(null);
        DateCompra.setDate(fechaCompra);
        Midate.setDate(fechaVenta);
        lblEnterCantidadCompra.setVisible(false);
        lblNomProdCom.setVisible(false);
        txtNombreProductoCompra.setVisible(false);
        LabelTotal.setVisible(false);
        LabelVuelto.setVisible(false);
        txtIdCompraHis.setVisible(false);
        LblEnterCantidad.setVisible(false);
        lblEnterNombre.setVisible(false);
        LblEnterCodigo.setVisible(false);
        lblEnterCompra.setVisible(false);
        txtIdCliente.setVisible(false);
        txtIdProdCompra.setVisible(false);
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
        LimpiarTablaProductosComprasLista();
        LimpiarTablaProductoCompraAgregar();
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
        jLabel39 = new javax.swing.JLabel();
        lblEnterNombre = new javax.swing.JLabel();
        LblEnterCodigo = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        LblEnterCantidad = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblVuelto = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        cmbMetodo = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaHistorialVenta = new javax.swing.JTable();
        btnPdfVenta = new javax.swing.JButton();
        txtIdVentaPdf = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaProductoHistorial = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        txtBuscarCodigoVenta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtBuscarVentaProd = new javax.swing.JTextField();
        BtnEliminarVentasCompletas = new javax.swing.JButton();
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
        lblAlerta = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaCliente = new javax.swing.JTable();
        TxtRutCliente = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TxtNombreCliente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        btnNuevoCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnGuardarCliente = new javax.swing.JButton();
        TxtDirecionCliente = new javax.swing.JTextField();
        btnEditarCliente = new javax.swing.JButton();
        TxtTelefonoCliente = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaProveedor = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        LabelVendedor = new javax.swing.JLabel();
        txtIdProveedor = new javax.swing.JTextField();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnguardarProveedor = new javax.swing.JButton();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtRutProveedor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
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
        lblNomProdCom = new javax.swing.JLabel();
        txtNombreProductoCompra = new javax.swing.JTextField();
        txtIdProdCompra = new javax.swing.JTextField();
        lblEnterCompra = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaProductosComprasLista = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        TablaProductoCompraAgregar = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        lblEnterCantidadCompra = new javax.swing.JLabel();
        txtIdCompraHis = new javax.swing.JTextField();
        btnEliminarProductoCompra = new javax.swing.JButton();
        btnEliminarCompra = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Inventario");
        setResizable(false);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel3.setLayout(null);

        txtRutVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutVentaKeyTyped(evt);
            }
        });
        jPanel3.add(txtRutVenta);
        txtRutVenta.setBounds(90, 630, 135, 22);

        txtNombreClienteventa.setEditable(false);
        txtNombreClienteventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteventaActionPerformed(evt);
            }
        });
        jPanel3.add(txtNombreClienteventa);
        txtNombreClienteventa.setBounds(300, 630, 137, 22);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("TOTAL A PAGAR :");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(750, 620, 97, 16);

        jLabel8.setText("NOMBRE:");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(240, 630, 64, 16);

        jLabel7.setText("RUT CLIENTE:");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(10, 630, 71, 16);

        TablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "PRODUCTO", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            TablaVenta.getColumnModel().getColumn(0).setResizable(false);
            TablaVenta.getColumnModel().getColumn(1).setResizable(false);
            TablaVenta.getColumnModel().getColumn(2).setResizable(false);
            TablaVenta.getColumnModel().getColumn(3).setResizable(false);
            TablaVenta.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(6, 356, 906, 260);

        jLabel4.setText("Cantidad:");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(540, 200, 51, 26);

        jLabel3.setText("Buscar por Nombre:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(10, 40, 106, 35);

        jLabel6.setText("Nombre:");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(10, 190, 47, 28);

        txtCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaActionPerformed(evt);
            }
        });
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanel3.add(txtCodigoVenta);
        txtCodigoVenta.setBounds(130, 100, 51, 22);

        txtDescripcionVenta.setEditable(false);
        jPanel3.add(txtDescripcionVenta);
        txtDescripcionVenta.setBounds(70, 190, 233, 28);

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("VENTAS SALGJEAN");
        jLabel9.setToolTipText("");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(310, 20, 295, 45);

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanel3.add(txtCantidadVenta);
        txtCantidadVenta.setBounds(610, 200, 42, 35);

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminarVenta.setText("Eliminar Venta");
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarVenta);
        btnEliminarVenta.setBounds(760, 320, 140, 30);

        LabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelTotal.setText("----");
        jPanel3.add(LabelTotal);
        LabelTotal.setBounds(830, 120, 50, 16);

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btnGenerarVenta.setText("Generar Venta");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel3.add(btnGenerarVenta);
        btnGenerarVenta.setBounds(600, 620, 140, 37);

        txtIdCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCVActionPerformed(evt);
            }
        });
        jPanel3.add(txtIdCV);
        txtIdCV.setBounds(818, 174, 28, 22);

        txtStockDisponible.setEditable(false);
        txtStockDisponible.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel3.add(txtStockDisponible);
        txtStockDisponible.setBounds(120, 290, 55, 30);

        jLabel19.setText("Stock Disponible:");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(10, 300, 91, 16);

        txtPrecioVenta.setEditable(false);
        jPanel3.add(txtPrecioVenta);
        txtPrecioVenta.setBounds(70, 240, 80, 30);

        jLabel20.setText("Precio:");
        jPanel3.add(jLabel20);
        jLabel20.setBounds(10, 250, 36, 16);
        jPanel3.add(txtIdPro);
        txtIdPro.setBounds(860, 220, 34, 22);

        Midate.setEnabled(false);
        jPanel3.add(Midate);
        Midate.setBounds(700, 40, 210, 30);

        jLabel21.setText("Fecha:");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(650, 40, 34, 28);
        jPanel3.add(lblFotoVenta);
        lblFotoVenta.setBounds(630, 250, 105, 97);

        jLabel22.setText("Imagen :");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(580, 280, 51, 16);

        jLabel29.setText("Codigo:");
        jPanel3.add(jLabel29);
        jLabel29.setBounds(10, 140, 42, 35);

        txtNombreVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreVentaKeyReleased(evt);
            }
        });
        jPanel3.add(txtNombreVenta);
        txtNombreVenta.setBounds(130, 50, 96, 22);

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
        jPanel3.add(txtCodigoVentaMostrar);
        txtCodigoVentaMostrar.setBounds(70, 150, 38, 22);

        jLabel30.setText("Buscar por Codigo:");
        jPanel3.add(jLabel30);
        jLabel30.setBounds(10, 100, 101, 22);

        jLabel32.setText("PAGO CLIENTE:");
        jPanel3.add(jLabel32);
        jLabel32.setBounds(450, 630, 82, 16);

        txtPagoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPagoClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtPagoCliente);
        txtPagoCliente.setBounds(540, 630, 50, 22);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("VUELTO:");
        jPanel3.add(jLabel31);
        jLabel31.setBounds(760, 650, 48, 16);

        LabelVuelto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LabelVuelto.setText("----");
        jPanel3.add(LabelVuelto);
        LabelVuelto.setBounds(830, 150, 50, 16);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel39.setText("(Ej: 123456789-9)");
        jPanel3.add(jLabel39);
        jLabel39.setBounds(10, 650, 120, 15);

        lblEnterNombre.setText("Presione Enter para aceptar.");
        jPanel3.add(lblEnterNombre);
        lblEnterNombre.setBounds(80, 20, 146, 16);

        LblEnterCodigo.setText("Presione Enter para aceptar.");
        jPanel3.add(LblEnterCodigo);
        LblEnterCodigo.setBounds(80, 80, 146, 16);
        jPanel3.add(jLabel45);
        jLabel45.setBounds(50, 80, 16, 16);
        jPanel3.add(jLabel42);
        jLabel42.setBounds(20, 20, 0, 0);

        LblEnterCantidad.setText("Presione Enter para agregar.");
        jPanel3.add(LblEnterCantidad);
        LblEnterCantidad.setBounds(560, 180, 160, 16);
        jPanel3.add(jLabel44);
        jLabel44.setBounds(660, 180, 0, 0);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotal.setText("----");
        jPanel3.add(lblTotal);
        lblTotal.setBounds(850, 620, 70, 16);

        lblVuelto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVuelto.setText("----");
        jPanel3.add(lblVuelto);
        lblVuelto.setBounds(850, 650, 70, 16);

        jLabel43.setText("Medio de pago:");
        jPanel3.add(jLabel43);
        jLabel43.setBounds(520, 130, 90, 16);

        cmbMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Debito/Credito", "Junaeb Edenred", "Junaeb Sodexo" }));
        jPanel3.add(cmbMetodo);
        cmbMetodo.setBounds(620, 130, 130, 30);

        jTabbedPane1.addTab("Nueva Venta", new javax.swing.ImageIcon(getClass().getResource("/Img/nuevaventa.png")), jPanel3); // NOI18N

        jPanel8.setLayout(null);

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

        jPanel8.add(jScrollPane7);
        jScrollPane7.setBounds(19, 175, 382, 402);

        btnPdfVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnPdfVenta.setText("Abrir PDF");
        btnPdfVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentaActionPerformed(evt);
            }
        });
        jPanel8.add(btnPdfVenta);
        btnPdfVenta.setBounds(774, 127, 114, 30);
        jPanel8.add(txtIdVentaPdf);
        txtIdVentaPdf.setBounds(704, 32, 27, 22);

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
                false, false, false
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

        jPanel8.add(jScrollPane8);
        jScrollPane8.setBounds(460, 175, 452, 402);

        jLabel38.setText("Buscar por Codigo Venta:");
        jPanel8.add(jLabel38);
        jLabel38.setBounds(20, 60, 134, 16);

        txtBuscarCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarCodigoVentaActionPerformed(evt);
            }
        });
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
        jPanel8.add(txtBuscarCodigoVenta);
        txtBuscarCodigoVenta.setBounds(160, 60, 91, 22);

        jLabel40.setText("Buscar por Producto:");
        jPanel8.add(jLabel40);
        jLabel40.setBounds(40, 130, 120, 16);

        txtBuscarVentaProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarVentaProdKeyReleased(evt);
            }
        });
        jPanel8.add(txtBuscarVentaProd);
        txtBuscarVentaProd.setBounds(160, 130, 90, 22);

        BtnEliminarVentasCompletas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        BtnEliminarVentasCompletas.setText("Eliminar Venta");
        BtnEliminarVentasCompletas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarVentasCompletasActionPerformed(evt);
            }
        });
        jPanel8.add(BtnEliminarVentasCompletas);
        BtnEliminarVentasCompletas.setBounds(20, 600, 150, 31);

        jTabbedPane1.addTab("Ventas", new javax.swing.ImageIcon(getClass().getResource("/Img/history.png")), jPanel8); // NOI18N

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });
        jPanel2.setLayout(null);

        jLabel1.setText("Nombre :");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(205, 43, 50, 16);

        txtIdproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdproductoActionPerformed(evt);
            }
        });
        jPanel2.add(txtIdproducto);
        txtIdproducto.setBounds(831, 94, 40, 40);
        jPanel2.add(txtNombreProducto);
        txtNombreProducto.setBounds(267, 40, 106, 22);

        lblCodProd.setText("Codigo Producto:");
        jPanel2.add(lblCodProd);
        lblCodProd.setBounds(6, 43, 94, 16);

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoProducto);
        txtCodigoProducto.setBounds(110, 40, 50, 22);

        TablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "", "CODIGO", "NOMBRE", "PROVEEDOR", "CANTIDAD", "PRECIO"
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

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(6, 274, 892, 269);

        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/blank.png"))); // NOI18N
        btnNuevoProducto.setText("Nuevo");
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });
        jPanel2.add(btnNuevoProducto);
        btnNuevoProducto.setBounds(90, 560, 130, 30);

        bt_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        bt_Guardar.setText("Guardar");
        bt_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_GuardarActionPerformed(evt);
            }
        });
        jPanel2.add(bt_Guardar);
        bt_Guardar.setBounds(310, 560, 130, 30);

        bt_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        bt_Actualizar.setText("Modificar");
        bt_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(bt_Actualizar);
        bt_Actualizar.setBounds(530, 560, 120, 30);

        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        bt_Eliminar.setText("Eliminar");
        bt_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_EliminarActionPerformed(evt);
            }
        });
        jPanel2.add(bt_Eliminar);
        bt_Eliminar.setBounds(730, 560, 110, 31);

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
        jPanel2.add(cbxProveedorPro);
        cbxProveedorPro.setBounds(477, 36, 143, 30);

        jLabel26.setText("Proveedor:");
        jPanel2.add(jLabel26);
        jLabel26.setBounds(408, 43, 57, 16);

        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });
        jPanel2.add(txtPrecioPro);
        txtPrecioPro.setBounds(739, 36, 97, 30);

        jLabel25.setText("Precio:");
        jPanel2.add(jLabel25);
        jLabel25.setBounds(685, 43, 36, 16);

        txtCantPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantProKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantPro);
        txtCantPro.setBounds(80, 80, 50, 30);

        jLabel24.setText("Cantidad:");
        jPanel2.add(jLabel24);
        jLabel24.setBounds(6, 88, 51, 16);
        jPanel2.add(lblFoto);
        lblFoto.setBounds(351, 142, 92, 82);

        jLabel2.setText("Imagen :");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(6, 193, 51, 16);

        txtNomImagen.setEditable(false);
        jPanel2.add(txtNomImagen);
        txtNomImagen.setBounds(63, 190, 135, 22);

        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
        jPanel2.add(btnSeleccionar);
        btnSeleccionar.setBounds(216, 190, 90, 23);

        jLabel37.setText("Buscar Producto:");
        jPanel2.add(jLabel37);
        jLabel37.setBounds(597, 205, 90, 16);

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyTyped(evt);
            }
        });
        jPanel2.add(txtBuscarProducto);
        txtBuscarProducto.setBounds(699, 202, 128, 22);

        lblAlerta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAlerta.setForeground(new java.awt.Color(255, 51, 51));
        jPanel2.add(lblAlerta);
        lblAlerta.setBounds(10, 130, 140, 20);

        jTabbedPane1.addTab("Productos", new javax.swing.ImageIcon(getClass().getResource("/Img/products.png")), jPanel2); // NOI18N

        jPanel1.setLayout(null);

        TablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "RUT", "NOMBRE", "TELEFONO", "DIRECCION"
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

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(354, 25, 540, 402);

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
        jPanel1.add(TxtRutCliente);
        TxtRutCliente.setBounds(70, 90, 153, 22);

        jLabel10.setText("RUT :");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(20, 90, 27, 16);
        jPanel1.add(TxtNombreCliente);
        TxtNombreCliente.setBounds(70, 130, 153, 22);

        jLabel11.setText("Nombre :");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(10, 130, 50, 16);

        jLabel12.setText("Telefono :");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(10, 170, 52, 16);

        jLabel13.setText("Direccion :");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 220, 56, 16);

        jLabel28.setText("(Ej: 123456789-9)");
        jPanel1.add(jLabel28);
        jLabel28.setBounds(70, 70, 88, 16);

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtIdCliente);
        txtIdCliente.setBounds(90, 430, 16, 22);

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/blank.png"))); // NOI18N
        btnNuevoCliente.setText("Nuevo Cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoCliente);
        btnNuevoCliente.setBounds(170, 350, 150, 40);

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminarCliente.setText("Eliminar Cliente");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarCliente);
        btnEliminarCliente.setBounds(11, 350, 150, 40);

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnGuardarCliente.setText("Guardar Cliente");
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardarCliente);
        btnGuardarCliente.setBounds(10, 300, 150, 37);
        jPanel1.add(TxtDirecionCliente);
        TxtDirecionCliente.setBounds(70, 210, 153, 22);

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnEditarCliente.setText("Editar Cliente");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditarCliente);
        btnEditarCliente.setBounds(170, 300, 150, 37);

        TxtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtTelefonoClienteKeyTyped(evt);
            }
        });
        jPanel1.add(TxtTelefonoCliente);
        TxtTelefonoCliente.setBounds(70, 170, 151, 22);

        jTabbedPane1.addTab("Clientes", new javax.swing.ImageIcon(getClass().getResource("/Img/clients.png")), jPanel1); // NOI18N

        jPanel5.setLayout(null);

        TablaProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CODIGO", "RUT", "NOMBRE", "TELEFONO", "DIRECCION"
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

        jPanel5.add(jScrollPane5);
        jScrollPane5.setBounds(389, 23, 523, 470);

        jLabel33.setText("(Ej: 123456789-9)");
        jPanel5.add(jLabel33);
        jLabel33.setBounds(97, 55, 88, 16);

        LabelVendedor.setText("Vendedor");
        jPanel5.add(LabelVendedor);
        LabelVendedor.setBounds(78, 409, 51, 16);
        jPanel5.add(txtIdProveedor);
        txtIdProveedor.setBounds(186, 230, 28, 22);

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/blank.png"))); // NOI18N
        btnNuevoProveedor.setText("Nuevo Proveedor");
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanel5.add(btnNuevoProveedor);
        btnNuevoProveedor.setBounds(190, 330, 160, 40);

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminarProveedor.setText("Eliminar Proveedor");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel5.add(btnEliminarProveedor);
        btnEliminarProveedor.setBounds(13, 330, 170, 40);

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/edit.png"))); // NOI18N
        btnEditarProveedor.setText("Editar Proveedor");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanel5.add(btnEditarProveedor);
        btnEditarProveedor.setBounds(190, 280, 160, 37);

        btnguardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnguardarProveedor.setText("Guardar Proveedor");
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });
        jPanel5.add(btnguardarProveedor);
        btnguardarProveedor.setBounds(13, 280, 170, 37);

        txtDireccionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionProveedorActionPerformed(evt);
            }
        });
        jPanel5.add(txtDireccionProveedor);
        txtDireccionProveedor.setBounds(97, 192, 125, 20);

        jLabel18.setText("Direccion:");
        jPanel5.add(jLabel18);
        jLabel18.setBounds(32, 194, 53, 16);

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jPanel5.add(txtTelefonoProveedor);
        txtTelefonoProveedor.setBounds(97, 158, 125, 22);

        jLabel17.setText("Telefono:");
        jPanel5.add(jLabel17);
        jLabel17.setBounds(30, 161, 49, 16);
        jPanel5.add(txtNombreProveedor);
        txtNombreProveedor.setBounds(97, 120, 125, 22);

        jLabel16.setText("Nombre:");
        jPanel5.add(jLabel16);
        jLabel16.setBounds(32, 123, 47, 16);

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
        jPanel5.add(txtRutProveedor);
        txtRutProveedor.setBounds(97, 81, 125, 22);

        jLabel15.setText("RUT:");
        jPanel5.add(jLabel15);
        jLabel15.setBounds(32, 77, 24, 31);

        jTabbedPane1.addTab("Proveedores", new javax.swing.ImageIcon(getClass().getResource("/Img/supplier.png")), jPanel5); // NOI18N

        jPanel7.setLayout(null);

        jLabel23.setText("Proveedor:");
        jPanel7.add(jLabel23);
        jLabel23.setBounds(6, 27, 65, 16);
        jPanel7.add(DateCompra);
        DateCompra.setBounds(77, 88, 139, 22);

        jLabel27.setText("Fecha:");
        jPanel7.add(jLabel27);
        jLabel27.setBounds(19, 88, 34, 16);

        TablaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CODIGO COMPRA", "PROVEEDOR", "PRECIO", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCompra.getTableHeader().setReorderingAllowed(false);
        TablaCompra.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TablaCompraAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TablaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaCompraMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TablaCompra);
        if (TablaCompra.getColumnModel().getColumnCount() > 0) {
            TablaCompra.getColumnModel().getColumn(0).setResizable(false);
            TablaCompra.getColumnModel().getColumn(1).setResizable(false);
            TablaCompra.getColumnModel().getColumn(2).setResizable(false);
            TablaCompra.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel7.add(jScrollPane6);
        jScrollPane6.setBounds(20, 370, 490, 260);

        jPanel7.add(cbxProveedorCom);
        cbxProveedorCom.setBounds(80, 20, 174, 25);

        btnAddCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnAddCompra.setText("Añadir Compra");
        btnAddCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompraActionPerformed(evt);
            }
        });
        jPanel7.add(btnAddCompra);
        btnAddCompra.setBounds(740, 20, 160, 30);

        jLabel34.setText("Codigo Producto:");
        jPanel7.add(jLabel34);
        jLabel34.setBounds(282, 27, 94, 16);

        txtCodProdCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodProdCompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodProdCompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodProdCompraKeyTyped(evt);
            }
        });
        jPanel7.add(txtCodProdCompra);
        txtCodProdCompra.setBounds(390, 20, 37, 25);

        jLabel35.setText("Cantidad:");
        jPanel7.add(jLabel35);
        jLabel35.setBounds(458, 27, 51, 16);

        txtCantidadCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadCompraKeyTyped(evt);
            }
        });
        jPanel7.add(txtCantidadCompra);
        txtCantidadCompra.setBounds(520, 20, 34, 22);

        jLabel36.setText("Precio a pagar:");
        jPanel7.add(jLabel36);
        jLabel36.setBounds(576, 27, 78, 16);

        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyTyped(evt);
            }
        });
        jPanel7.add(txtPrecioCompra);
        txtPrecioCompra.setBounds(660, 20, 50, 22);

        lblNomProdCom.setText("Nombre Producto:");
        jPanel7.add(lblNomProdCom);
        lblNomProdCom.setBounds(282, 91, 99, 16);

        txtNombreProductoCompra.setEditable(false);
        jPanel7.add(txtNombreProductoCompra);
        txtNombreProductoCompra.setBounds(387, 88, 160, 22);

        txtIdProdCompra.setEditable(false);
        txtIdProdCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdProdCompraActionPerformed(evt);
            }
        });
        jPanel7.add(txtIdProdCompra);
        txtIdProdCompra.setBounds(830, 70, 64, 22);

        lblEnterCompra.setText("Presione Enter para ver el nombre");
        jPanel7.add(lblEnterCompra);
        lblEnterCompra.setBounds(240, 60, 177, 16);
        jPanel7.add(jLabel41);
        jLabel41.setBounds(456, 54, 9, 16);

        TablaProductosComprasLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CODIGO PRODUCTO", "NOMBRE", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProductosComprasLista.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(TablaProductosComprasLista);
        if (TablaProductosComprasLista.getColumnModel().getColumnCount() > 0) {
            TablaProductosComprasLista.getColumnModel().getColumn(0).setResizable(false);
            TablaProductosComprasLista.getColumnModel().getColumn(1).setResizable(false);
            TablaProductosComprasLista.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel7.add(jScrollPane9);
        jScrollPane9.setBounds(520, 370, 390, 260);

        TablaProductoCompraAgregar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "CODIGO PRODUCTO", "NOMBRE", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProductoCompraAgregar.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(TablaProductoCompraAgregar);
        if (TablaProductoCompraAgregar.getColumnModel().getColumnCount() > 0) {
            TablaProductoCompraAgregar.getColumnModel().getColumn(0).setMinWidth(0);
            TablaProductoCompraAgregar.getColumnModel().getColumn(0).setPreferredWidth(0);
            TablaProductoCompraAgregar.getColumnModel().getColumn(0).setMaxWidth(0);
            TablaProductoCompraAgregar.getColumnModel().getColumn(1).setResizable(false);
            TablaProductoCompraAgregar.getColumnModel().getColumn(2).setResizable(false);
            TablaProductoCompraAgregar.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel7.add(jScrollPane10);
        jScrollPane10.setBounds(20, 140, 490, 170);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Lista de compras");
        jPanel7.add(jLabel14);
        jLabel14.setBounds(30, 340, 130, 20);

        lblEnterCantidadCompra.setText("Presione Enter para agregar");
        jPanel7.add(lblEnterCantidadCompra);
        lblEnterCantidadCompra.setBounds(470, 60, 160, 16);
        jPanel7.add(txtIdCompraHis);
        txtIdCompraHis.setBounds(820, 210, 64, 22);

        btnEliminarProductoCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminarProductoCompra.setText("Eliminar producto de la lista");
        btnEliminarProductoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoCompraActionPerformed(evt);
            }
        });
        jPanel7.add(btnEliminarProductoCompra);
        btnEliminarProductoCompra.setBounds(530, 140, 210, 30);

        btnEliminarCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminarCompra.setText("Eliminar Compra");
        btnEliminarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCompraActionPerformed(evt);
            }
        });
        jPanel7.add(btnEliminarCompra);
        btnEliminarCompra.setBounds(20, 640, 150, 30);

        jTabbedPane1.addTab("Compras", new javax.swing.ImageIcon(getClass().getResource("/Img/purchases.png")), jPanel7); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutProveedorActionPerformed

    private void txtDireccionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            com = comDao.BuscarProveedorCompra(Integer.parseInt(txtIdProveedor.getText().trim()));
            if (com.getProveedor()== null){
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar este proveedor?\n Todos los productos con este proveedor seran eliminados tambien.", "Seleccione una opcion.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTablaProveedores();
                LimpiarProveedor();
                ListarProveedor();
                cbxProveedorPro.removeAllItems();
                cbxProveedorCom.removeAllItems();
                llenarProveedor();
                LimpiarTablaProductos();
                ListarProductos();
            }
            }else{
                JOptionPane.showMessageDialog(null, "No se puede eliminar este proveedor porque tiene compras registradas.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
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
        String sqlDuplicate = "select codigo from productos where codigo = " + txtCodigoProducto.getText() + " ";
        String sqlDuplicate2 = "select nombre from productos where nombre = " + "'" + txtNombreProducto.getText().trim() + "' ";
        if (!"".equals(txtCodigoProducto.getText().trim()) && !"".equals(txtNombreProducto.getText().trim()) && !"".equals(cbxProveedorPro.getSelectedItem()) && !"".equals(txtCantPro.getText().trim()) && !"".equals(txtPrecioPro.getText().trim())) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = cn.getConnection();
                Statement stmt = con.createStatement();
                Statement stmt2 = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlDuplicate);
                ResultSet rs2 = stmt2.executeQuery(sqlDuplicate2);
                int precio = Integer.parseInt(txtPrecioPro.getText().trim());
                if (rs.next() == true) {
                    JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
                } else {
                    if (rs2.next() == true) {
                        JOptionPane.showMessageDialog(null, "Nombre ya en uso.");
                    } else {
                        if (precio == 0) {
                            JOptionPane.showMessageDialog(null, "El Precio debe ser mayor a 0.");
                        } else {

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

                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
        }
    }//GEN-LAST:event_bt_GuardarActionPerformed

    private void bt_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ActualizarActionPerformed
        String sqlDuplicate = "select codigo from productos where codigo = " + txtCodigoProducto.getText().trim() + " and id <> " + Integer.parseInt(txtIdproducto.getText()) + " ";

        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
        } else if ("".equals(txtBuscarProducto.getText().trim())) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = cn.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlDuplicate);
                if (rs.next() == true) {
                    JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
                } else {
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
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = cn.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlDuplicate);
                if (rs.next() == true) {
                    JOptionPane.showMessageDialog(null, "Codigo ya en uso.");
                } else {
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
            if (rs.next()) {
                byte[] img = rs.getBytes("imagen");
                ImageIcon image = new ImageIcon(img);
                Image im = image.getImage();
                Image myimg = im.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myimg);
                lblFoto.setIcon(newImage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (Integer.parseInt(txtCantPro.getText().trim()) == 0) {
            lblAlerta.setText("No hay Stock.");

        } else {
            if (Integer.parseInt(txtCantPro.getText().trim()) <= 5) {
                lblAlerta.setText("Stock Critico.");
            } else {
                lblAlerta.setText("");
            }
        }

// TODO add your handling code here:
    }//GEN-LAST:event_TablaProductoMouseClicked

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
            JOptionPane.showMessageDialog(null, "Selecione una fila.");
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
                cbxProveedorCom.removeAllItems();
                ListarProveedor();
                LimpiarProveedor();
                llenarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed

        String sqlDuplicate = "select rut from proveedor where rut = '" + txtRutProveedor.getText().trim() + "' ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = cn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlDuplicate);
            if (rs.next() == true) {
                JOptionPane.showMessageDialog(null, "El RUT ya esta registrado.");
            } else {

                if (!"".equals(txtRutProveedor.getText()) && !"".equals(txtNombreProveedor.getText().trim()) && !"".equals(txtTelefonoProveedor.getText().trim()) && !"".equals(txtDireccionProveedor.getText().trim())) {
                    if (validarRutProveedores()) {
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
                        btnguardarProveedor.setEnabled(true);
                    }
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
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila.");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_bt_EliminarActionPerformed

    private void txtIdproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdproductoActionPerformed

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
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivos JPEG(*.JPG;*.JPEG)", "jpg", "jpeg");
        //Si deseas que se muestre primero los filtros usa la linea q esta abajo de esta.
        //archivo.setFileFilter(filtro);
        // Agregamos el Filtro pero cuidado se mostrara despues de todos los archivos
        archivo.addChoosableFileFilter(filtro);
        archivo.setFileFilter(filtro);
        // Colocamos titulo a nuestra ventana de Seleccion
        archivo.setDialogTitle("Abrir Archivo");
        //Si deseamos que muestre una carpeta predetermina usa la siguiente linea
        File ruta = new File("C:/");
        //Le implementamos a nuestro ventana de seleccion
        archivo.setCurrentDirectory(ruta);
        //Abrimos nuestra Ventana de Selccion
        int ventana = archivo.showOpenDialog(null);
        //hacemos comparacion en caso de aprete el boton abrir
        if (ventana == JFileChooser.APPROVE_OPTION) {
            //Obtenemos la ruta de nuestra imagen seleccionada
            File file = archivo.getSelectedFile();
            //Lo imprimimos en una caja de texto para ver su ruta
            txtNomImagen.setText(String.valueOf(file));
            //de cierto modo necesitamos tener la imagen para ello debemos conocer la ruta de dicha imagen
            Image foto = getToolkit().getImage(txtNomImagen.getText());
            //Le damos dimension a nuestro label que tendra la imagen
            foto = foto.getScaledInstance(110, 110, Image.SCALE_SMOOTH);
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
        txtTelefonoProveedor.setText(TablaProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TablaProveedor.getValueAt(fila, 4).toString());        // TODO add your handling code here:
    }//GEN-LAST:event_TablaProveedorMouseClicked

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
        if (txtTelefonoProveedor.getText().trim().length() >= 9) { // limit to 3 characters
            evt.consume();
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

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
        
        
        if (!"".equals(txtIdCliente.getText()) ) {
            v = Vdao.BuscarClienteVenta(Integer.parseInt(txtIdCliente.getText().trim()));
            if (v.getNombre_cli()== null){
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar este cliente?", "Seleccione una opcion.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarCliente();
                LimpiarTablaCliente();
                ListarCliente();
                LimpiarTablaHistorialVentas();
                ListarHistorialVentas();
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se puede eliminar este cliente porque tiene ventas registradas.");}
        }else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
        }
            // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
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
        String sqlDuplicate = "select rut from clientes where rut = '" + TxtRutCliente.getText().trim() + "' ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = cn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlDuplicate);
            if (rs.next() == true) {
                JOptionPane.showMessageDialog(null, "El RUT ya esta registrado.");
            } else {

                if (!"".equals(TxtRutCliente.getText()) && !"".equals(TxtNombreCliente.getText()) && !"".equals(TxtTelefonoCliente.getText()) && !"".equals(TxtDirecionCliente.getText())) {
                    if (validarRutCliente()) {
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
                        btnGuardarCliente.setEnabled(true);
                    }
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

        if (TxtTelefonoCliente.getText().trim().length() >= 9) { // limit to 3 characters
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
        if (txtIdVentaPdf.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila.");
        } else {
            v = Vdao.BuscarVenta(Integer.parseInt(txtIdVentaPdf.getText()));
            Vdao.pdfV(v.getId(), v.getCliente(), v.getPago(), v.getTotal(), v.getVuelto(), v.getVendedor(), v.getMetodo());
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
        if (TablaProductoCompraAgregar.getRowCount() > 0) {

            if (!"".equals(txtPrecioCompra.getText().trim())) {
                RegistrarCompra();
                RegistrarDetalleCompra();
                LimpiarTablaProductoCompraAgregar();
                LimpiarCompra();
                LimpiarTablaCompra();
                ListarCompras();
            } else {
                JOptionPane.showMessageDialog(null, "Algunos campos estan vacios.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La compra esta vacia.");
        }
    }//GEN-LAST:event_btnAddCompraActionPerformed

    private void txtBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyTyped
// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProductoKeyTyped

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        if (!"".equals(txtBuscarProducto.getText().trim())) {
            LimpiarTablaProductos();
            BuscarProductos();
        } else {
            LimpiarTablaProductos();
            ListarProductos();
        }        // TODO add your handling code here:
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

        if (!"".equals(txtBuscarCodigoVenta.getText().trim())) {
            LimpiarTablaHistorialVentas();
            LimpiarProductosHistorial();
            BuscarVentas();
        } else {
            LimpiarTablaHistorialVentas();
            ListarHistorialVentas();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoVentaKeyReleased

    private void txtCodProdCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdCompraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodProdCompra.getText().trim())) {
                lblNomProdCom.setVisible(true);
                txtNombreProductoCompra.setVisible(true);
                String cod = txtCodProdCompra.getText().trim();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    txtIdProdCompra.setText("");
                    txtIdProdCompra.setText("" + pro.getId());
                    txtNombreProductoCompra.setText("" + pro.getNombre());
                } else {
                    lblNomProdCom.setVisible(false);
                    txtNombreProductoCompra.setVisible(false);
                    JOptionPane.showMessageDialog(null, "El producto no existe.");

                    LimpiarCompra();
                    txtCodProdCompra.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto.");
                txtCodProdCompra.requestFocus();
            }
        }// TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdCompraKeyPressed

    private void txtCodProdCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdCompraKeyReleased
        lblEnterCompra.setVisible(true);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            lblEnterCompra.setVisible(false);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_txtCodProdCompraKeyReleased

    private void txtPagoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoClienteKeyTyped
        event.numberDecimalKeyPress(evt, txtPagoCliente);                 // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoClienteKeyTyped

    private void txtPagoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (TablaVenta.getRowCount() > 0) {
                int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
                int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
                if (TmpPago >= TmpTotal) {

                    VueltoTotal();
                } else {
                    JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");

                }

            } else {
                JOptionPane.showMessageDialog(null, "No hay productos en la venta");
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoClienteKeyPressed

    private void txtCodigoVentaMostrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarKeyTyped

    private void txtCodigoVentaMostrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarKeyPressed

    private void txtCodigoVentaMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaMostrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaMostrarActionPerformed

    private void txtNombreVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            lblEnterNombre.setVisible(false);
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
                        if (rs.next()) {
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
                    JOptionPane.showMessageDialog(null, "El producto no existe.");
                    txtNombreVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre del producto.");
                txtNombreVenta.requestFocus();
            }
        }        // TODO add your handling code here:              // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreVentaKeyPressed

    private void txtIdCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCVActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed

        if (TablaVenta.getRowCount() > 0) {
            int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
            if ("".equals(txtPagoCliente.getText().trim())) {
                JOptionPane.showMessageDialog(null, "Pago del cliente esta vacio.");
            } else {
                int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
                if (TmpPago >= TmpTotal) {
                    if (!"".equals(txtNombreClienteventa.getText().trim())) {
                        VueltoTotalBotonVenta();
                        RegistrarVenta();
                        RegistrarDetalle();
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
                } else {
                    JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La venta esta vacia.");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        if (TablaVenta.getRowCount() > 0) {
            modelo = (DefaultTableModel) TablaVenta.getModel();
            modelo.removeRow(TablaVenta.getSelectedRow());
            TotalPagar();
            txtCodigoVenta.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "La venta esta vacia.");
            txtCodigoVenta.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        event.numberDecimalKeyPress(evt, txtCantidadVenta);         // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LblEnterCantidad.setVisible(false);
            if (!"".equals(txtCantidadVenta.getText().trim())) {
                if (!"0".equals(txtCantidadVenta.getText().trim())) {
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
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        event.numberDecimalKeyPress(evt, txtCodigoVenta);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText().trim())) {
                LblEnterCodigo.setVisible(false);
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
                        if (rs.next()) {
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
                    JOptionPane.showMessageDialog(null, "El producto no existe.");
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto.");
                txtCodigoVenta.requestFocus();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaActionPerformed

    private void TablaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaVentaMouseClicked

        int fila = TablaVenta.rowAtPoint(evt.getPoint());     // TODO add your handling code here:
    }//GEN-LAST:event_TablaVentaMouseClicked

    private void txtNombreClienteventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteventaActionPerformed

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

    private void txtNombreVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreVentaKeyReleased
        lblEnterNombre.setVisible(true);
        if ("".equals(txtNombreVenta.getText().trim())) {
            lblEnterNombre.setVisible(false);
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            lblEnterNombre.setVisible(false);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreVentaKeyReleased

    private void txtCodigoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyReleased
        LblEnterCodigo.setVisible(true);
        if ("".equals(txtCodigoVenta.getText().trim())) {
            LblEnterCodigo.setVisible(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LblEnterCodigo.setVisible(false);
        }   // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaKeyReleased

    private void txtCantidadVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyReleased
        LblEnterCantidad.setVisible(true);
        if ("".equals(txtCantidadVenta.getText().trim())) {
            LblEnterCantidad.setVisible(false);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LblEnterCantidad.setVisible(false);
        } // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaKeyReleased

    private void txtBuscarCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCodigoVentaActionPerformed

    private void txtIdProdCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdProdCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdProdCompraActionPerformed

    private void txtCantidadCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCompraKeyReleased
        lblEnterCantidadCompra.setVisible(true);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            lblEnterCantidadCompra.setVisible(false);
        }     // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadCompraKeyReleased

    private void txtCantidadCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadCompraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LblEnterCantidad.setVisible(false);
            if (!"".equals(txtCantidadCompra.getText().trim())) {
                if (!"0".equals(txtCantidadCompra.getText().trim())) {
                    String cod = txtCodProdCompra.getText().trim();
                    pro = proDao.BuscarPro(cod);
                    if (pro.getNombre() != null) {
                        txtIdProdCompra.setText("");
                        txtIdProdCompra.setText("" + pro.getId());
                        txtNombreProductoCompra.setText("" + pro.getNombre());
                    } else {
                        lblNomProdCom.setVisible(false);
                        txtNombreProductoCompra.setVisible(false);
                        JOptionPane.showMessageDialog(null, "El producto no existe.");
                    }
                    int id = Integer.parseInt(txtIdProdCompra.getText().trim());
                    int id_prod = Integer.parseInt(txtCodProdCompra.getText().trim());
                    String descripcion = txtNombreProductoCompra.getText().trim();
                    int cant = Integer.parseInt(txtCantidadCompra.getText().trim());
                    item2 = item2 + 1;
                    tmp = (DefaultTableModel) TablaProductoCompraAgregar.getModel();
                    for (int i = 0; i < TablaProductoCompraAgregar.getRowCount(); i++) {

                        if (TablaProductoCompraAgregar.getValueAt(i, 1).equals(txtNombreProductoCompra.getText().trim())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item2);
                    lista.add(id);
                    lista.add(id_prod);
                    lista.add(descripcion);
                    lista.add(cant);
                    Object[] O = new Object[4];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    tmp.addRow(O);
                    TablaProductoCompraAgregar.setModel(tmp);
                    LimpiarCompra();
                    txtIdProdCompra.setText("");
                    txtCodProdCompra.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese Cantidad mayor a 0.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadCompraKeyPressed

    private void TablaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaCompraMouseClicked
        int fila = TablaCompra.rowAtPoint(evt.getPoint());
        txtIdCompraHis.setText(TablaCompra.getValueAt(fila, 0).toString());
        com = comDao.BuscarCompra(Integer.parseInt(txtIdCompraHis.getText()));
        LimpiarTablaProductosComprasLista();
        ListarProductosHitorialCompra();
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaCompraMouseClicked

    private void btnEliminarProductoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoCompraActionPerformed
        if (TablaProductoCompraAgregar.getRowCount() > 0) {
            modelo = (DefaultTableModel) TablaProductoCompraAgregar.getModel();
            modelo.removeRow(TablaProductoCompraAgregar.getSelectedRow());
            txtCodProdCompra.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "La compra esta vacia.");
            txtCodProdCompra.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarProductoCompraActionPerformed

    private void txtBuscarVentaProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaProdKeyReleased
        if (!"".equals(txtBuscarVentaProd.getText().trim())) {
            LimpiarTablaHistorialVentas();
            LimpiarProductosHistorial();
            BuscarVentasProd();
        } else {
            LimpiarTablaHistorialVentas();
            ListarHistorialVentas();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarVentaProdKeyReleased

    private void BtnEliminarVentasCompletasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarVentasCompletasActionPerformed
        if (!"".equals(txtIdVentaPdf.getText()) ) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar esta venta?", "Seleccione una opcion.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdVentaPdf.getText());
                Vdao.EliminarVenta(id);
                LimpiarTablaHistorialVentas();
                LimpiarProductosHistorial();
                ListarHistorialVentas();
            }

        }                // TODO add your handling code here:
    }//GEN-LAST:event_BtnEliminarVentasCompletasActionPerformed

    private void btnEliminarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCompraActionPerformed
        if (!"".equals(txtIdCompraHis.getText()) ) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar esta compra?", "Seleccione una opcion.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCompraHis.getText());
                comDao.EliminarCompra(id);
                LimpiarTablaCompra();
                LimpiarTablaProductosComprasLista();
                ListarCompras();
            }

        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarCompraActionPerformed

    private void TablaCompraAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TablaCompraAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaCompraAncestorAdded

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
    private javax.swing.JButton BtnEliminarVentasCompletas;
    private com.toedter.calendar.JDateChooser DateCompra;
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelVendedor;
    private javax.swing.JLabel LabelVuelto;
    private javax.swing.JLabel LblEnterCantidad;
    private javax.swing.JLabel LblEnterCodigo;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TablaCliente;
    private javax.swing.JTable TablaCompra;
    private javax.swing.JTable TablaHistorialVenta;
    private javax.swing.JTable TablaProducto;
    private javax.swing.JTable TablaProductoCompraAgregar;
    private javax.swing.JTable TablaProductoHistorial;
    private javax.swing.JTable TablaProductosComprasLista;
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
    private javax.swing.JButton btnEliminarCompra;
    private javax.swing.JButton btnEliminarProductoCompra;
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
    private javax.swing.JComboBox<String> cmbMetodo;
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
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAlerta;
    private javax.swing.JLabel lblCodProd;
    private javax.swing.JLabel lblEnterCantidadCompra;
    private javax.swing.JLabel lblEnterCompra;
    private javax.swing.JLabel lblEnterNombre;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblFotoVenta;
    private javax.swing.JLabel lblNomProdCom;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVuelto;
    private javax.swing.JTextField txtBuscarCodigoVenta;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtBuscarVentaProd;
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
    private javax.swing.JTextField txtIdCompraHis;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProdCompra;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVentaPdf;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtNomImagen;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProductoCompra;
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

        Locale chileLocale = new Locale("es", "CL");
        NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
        String Formatted = "$" + nf.format(Totalpagar);

        lblTotal.setText(Formatted);
    }

    private void VueltoTotal() {
        Vueltototal = 0;
        PagoCliente = Integer.parseInt(txtPagoCliente.getText().trim());
        int aPagar = Integer.parseInt(LabelTotal.getText().trim());
        Vueltototal = PagoCliente - aPagar;

        LabelVuelto.setText(Integer.toString(Vueltototal));

        Locale chileLocale = new Locale("es", "CL");
        NumberFormat nf = NumberFormat.getNumberInstance(chileLocale);
        String Formatted = "$" + nf.format(Vueltototal);

        lblVuelto.setText(Formatted);

    }

    private void VueltoTotalBotonVenta() {
        if (TablaVenta.getRowCount() > 0) {
            int TmpTotal = Integer.parseInt(LabelTotal.getText().trim());
            int TmpPago = Integer.parseInt(txtPagoCliente.getText().trim());
            if (TmpPago >= TmpTotal) {
                VueltoTotal();
            } else {
                JOptionPane.showMessageDialog(null, "El Valor es menor que el total.");
            }

        } else {
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

    private void LimpiarCompra() {
        txtCodProdCompra.setText("");
        txtCantidadCompra.setText("");
        txtPrecioCompra.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdCV.getText().trim());
        String vendedor = LabelVendedor.getText().trim();
        String metodo = cmbMetodo.getSelectedItem().toString();
        int monto = Totalpagar;
        int pago = PagoCliente;
        int vuelto = Vueltototal;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setMetodo(metodo);
        v.setPago(pago);
        v.setTotal(monto);
        v.setVuelto(vuelto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarCompra() {

        int id = comDao.IdCompra();
        com.setCodigoCompra(id);
        Combo itemP = (Combo) cbxProveedorCom.getSelectedItem();
        com.setIdProveedor(itemP.getId());
        com.setPrecioCompra(Integer.parseInt(txtPrecioCompra.getText().trim()));
        com.setFechaCompra(fechaCompraActual);
        comDao.RegistrarCompra(com);
    }

    private void RegistrarVentaRapida() {
        String vendedor = LabelVendedor.getText().trim();
        int monto = Totalpagar;
        int pago = PagoCliente;
        String metodo = cmbMetodo.getSelectedItem().toString();

        int vuelto = Vueltototal;
        v.setCliente(0);
        v.setVendedor(vendedor);
        v.setMetodo(metodo);
        v.setPago(pago);
        v.setTotal(monto);
        v.setVuelto(vuelto);
        v.setFecha(fechaActual);
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalleCompra() {
        int id = comDao.IdCompra();
        for (int i = 0; i < TablaProductoCompraAgregar.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TablaProductoCompraAgregar.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(TablaProductoCompraAgregar.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setId(id);
            comDao.RegistrarDetalle(Dv);

        }
    }

    private void RegistrarDetalleRapido() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TablaVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TablaVenta.getValueAt(i, 0).toString());
            String nombre_prod = (TablaVenta.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(TablaVenta.getValueAt(i, 2).toString());
            int precio = Integer.parseInt(TablaVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setNombre(nombre_prod);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);

        }
        String metodo = cmbMetodo.getSelectedItem().toString();
        Vdao.pdfV(id, 0, Integer.parseInt(txtPagoCliente.getText().trim()), Totalpagar, Integer.parseInt(LabelVuelto.getText().trim()), LabelVendedor.getText().trim(), metodo);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TablaVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TablaVenta.getValueAt(i, 0).toString());
            String nombre_cliente = txtNombreClienteventa.getText().trim();
            String nombre_prod = (TablaVenta.getValueAt(i, 1).toString());
            int cant = Integer.parseInt(TablaVenta.getValueAt(i, 2).toString());
            int precio = Integer.parseInt(TablaVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setNombreCliente(nombre_cliente);
            Dv.setNombre(nombre_prod);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);

        }
        int cliente = Integer.parseInt(txtIdCV.getText());
        String metodo = cmbMetodo.getSelectedItem().toString();
        Vdao.pdfV(id, cliente, Integer.parseInt(txtPagoCliente.getText().trim()), Totalpagar, Integer.parseInt(LabelVuelto.getText().trim()), LabelVendedor.getText().trim(), metodo);
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

    private void LimpiarTablaProductoCompraAgregar() {
        tmp = (DefaultTableModel) TablaProductoCompraAgregar.getModel();
        int fila = TablaProductoCompraAgregar.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarTablaProductosComprasLista() {
        tmp = (DefaultTableModel) TablaProductosComprasLista.getModel();
        int fila = TablaProductosComprasLista.getRowCount();
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

    private void LimpiarTablaCompra() {
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
        lblVuelto.setText("----");
        lblTotal.setText("----");
    }

    private void llenarProveedor() {
        List<Proveedor> lista = PrDao.ListarProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new Combo(id, nombre));
            cbxProveedorCom.addItem(new Combo(id, nombre));
        }
    }
}
