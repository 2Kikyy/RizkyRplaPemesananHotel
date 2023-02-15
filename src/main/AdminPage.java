/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.DBConnection;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static java.awt.font.TextAttribute.FONT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author USER
 */
public class AdminPage extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;

    DefaultTableModel dtmFasilitasUmum;
    DefaultTableModel dtmFasilitasKamar;

    public AdminPage(String username, String levelMysql) {
        initComponents();

        tabelFasilitasKamarD.setDefaultEditor(Object.class, null);
        tabelFasilitasUmumD.setDefaultEditor(Object.class, null);
        tampilkanFasilitasKamar();
        tampilkanFasilitasUmum();        
        
        // set username to say hello on welcome screen
        String uname = username;
        txtWelcomee1.setText(uname);
        txtWelcomee2.setText(uname);
        
        // set level user
        String level = levelMysql;
        labelLevel.setText(level);
        
        // to set app's name
        this.setTitle("RizHotel v1.0");
        
        // shortcut to go search bar
        Action searchAction = new AbstractAction("Search") {
            public void actionPerformed(ActionEvent e) {
                txtCariKamarD.requestFocus();
                txtCariUmumD.requestFocus();
            }
        };
        
        String key = "Search";
        txtCariKamarD.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), key);
        txtCariKamarD.getActionMap().put(key, searchAction);
        
        txtCariUmumD.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), key);
        txtCariUmumD.getActionMap().put(key, searchAction);
    }

    public void tampilkanFasilitasKamar() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM fasilitas_kamar WHERE id LIKE '%" + txtCariKamarD.getText() + "%'"
                    + " OR tipe_kamar LIKE '%" + txtCariKamarD.getText() + "%' OR harga LIKE '%" + txtCariKamarD.getText() + "%';");
            rs = pstm.executeQuery();

            dtmFasilitasKamar = new DefaultTableModel();
            
            // set font style & size for heading columns
            JTableHeader header = tabelFasilitasKamarD.getTableHeader();
            Font headerFont = new Font("Verdana", Font.PLAIN, 15);
            header.setFont(headerFont);
            
            // to add columns on table
            dtmFasilitasKamar.addColumn("No.");
            dtmFasilitasKamar.addColumn("ID Kamar");
            dtmFasilitasKamar.addColumn("Tipe Kamar");
            dtmFasilitasKamar.addColumn("Harga");
                        
            int i = 1;
            
            while (rs.next()) {
                dtmFasilitasKamar.addRow(new Object[]{(i++ + "."), rs.getString(1), rs.getString(2), rs.getString(3)});                
            }
            tabelFasilitasKamarD.setModel(dtmFasilitasKamar);            
            
            // to set width on table columns
            tabelFasilitasKamarD.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelFasilitasKamarD.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabelFasilitasKamarD.getColumnModel().getColumn(2).setPreferredWidth(300);
            tabelFasilitasKamarD.getColumnModel().getColumn(3).setPreferredWidth(200);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane,
                    "Error!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println(e);
        }
    }
    
    public void tampilkanFasilitasUmum() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(
                    "SELECT * FROM fasilitas_umum WHERE id_fasilitas LIKE '%" + txtCariUmumD.getText() + "%'"
                            + " OR nama_fasilitas LIKE '%" + txtCariUmumD.getText() + "%';"
            );
            rs = pstm.executeQuery();
            
            dtmFasilitasUmum = new DefaultTableModel();
            
            // to set font style & size
            JTableHeader header = tabelFasilitasUmumD.getTableHeader();
            Font headerFont = new Font("Verdana", Font.PLAIN, 15);
            header.setFont(headerFont);
            
            // to add columns to table
            dtmFasilitasUmum.addColumn("No.");
            dtmFasilitasUmum.addColumn("ID Fasilitas");
            dtmFasilitasUmum.addColumn("Fasilitas");
            
            int i = 1;
            
            while(rs.next()) {
                dtmFasilitasUmum.addRow(new Object[] {(i++ + "."), rs.getString(1), rs.getString(2)});
            }
            tabelFasilitasUmumD.setModel(dtmFasilitasUmum);
            
            // to set all columns width on table
            tabelFasilitasUmumD.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelFasilitasUmumD.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabelFasilitasUmumD.getColumnModel().getColumn(2).setPreferredWidth(500);
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,
                    "Error!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void kosongkanFieldFasKamar() {
        txtIdKamarD.setText("");
        txtTipeKamarD.setText("");
        txtHargaKamarD.setText("");
    }
    
    public void kosongkanFieldFasUmum() {
        txtIdUmumD.setText("");
        txtFasilitasUmumD.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelFasilitasKamarD = new javax.swing.JTable();
        btnEditKamarD = new javax.swing.JButton();
        btnTambahKamarD = new javax.swing.JButton();
        btnHapusKamarD = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtIdKamarD = new javax.swing.JTextField();
        txtTipeKamarD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHargaKamarD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCariKamarD = new javax.swing.JTextField();
        btnCariKamarD = new javax.swing.JButton();
        txtWelcome2 = new javax.swing.JLabel();
        txtWelcomee1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelLevel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelFasilitasUmumD = new javax.swing.JTable();
        btnHapusUmumD = new javax.swing.JButton();
        btnTambahUmumD = new javax.swing.JButton();
        btnEditUmumD = new javax.swing.JButton();
        btnCariD = new javax.swing.JButton();
        txtCariUmumD = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdUmumD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFasilitasUmumD = new javax.swing.JTextField();
        txtWelcome1 = new javax.swing.JLabel();
        txtWelcomee2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        tabelFasilitasKamarD.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelFasilitasKamarD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelFasilitasKamarD.getTableHeader().setReorderingAllowed(false);
        tabelFasilitasKamarD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelFasilitasKamarDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelFasilitasKamarD);

        btnEditKamarD.setBackground(new java.awt.Color(255, 153, 0));
        btnEditKamarD.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnEditKamarD.setText("Edit");
        btnEditKamarD.setPreferredSize(new java.awt.Dimension(50, 35));
        btnEditKamarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditKamarDActionPerformed(evt);
            }
        });

        btnTambahKamarD.setBackground(new java.awt.Color(0, 204, 0));
        btnTambahKamarD.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnTambahKamarD.setText("Tambah");
        btnTambahKamarD.setPreferredSize(new java.awt.Dimension(50, 35));
        btnTambahKamarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKamarDActionPerformed(evt);
            }
        });

        btnHapusKamarD.setBackground(new java.awt.Color(255, 0, 0));
        btnHapusKamarD.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnHapusKamarD.setForeground(new java.awt.Color(244, 244, 244));
        btnHapusKamarD.setText("Hapus");
        btnHapusKamarD.setPreferredSize(new java.awt.Dimension(50, 35));
        btnHapusKamarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKamarDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(28, 68, 138));
        jLabel4.setText("ID Kamar");

        txtIdKamarD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtIdKamarD.setForeground(new java.awt.Color(28, 68, 138));

        txtTipeKamarD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTipeKamarD.setForeground(new java.awt.Color(28, 68, 138));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(28, 68, 138));
        jLabel5.setText("Nama Tipe Kamar");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(28, 68, 138));
        jLabel6.setText("Harga");

        txtHargaKamarD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtHargaKamarD.setForeground(new java.awt.Color(28, 68, 138));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(28, 68, 138));
        jLabel7.setText("Cari berdasarkan ID, Tipe Kamar, & Harga");

        txtCariKamarD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtCariKamarD.setForeground(new java.awt.Color(28, 68, 138));
        txtCariKamarD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCariKamarDMouseClicked(evt);
            }
        });
        txtCariKamarD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKamarDKeyPressed(evt);
            }
        });

        btnCariKamarD.setBackground(new java.awt.Color(28, 68, 138));
        btnCariKamarD.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCariKamarD.setForeground(new java.awt.Color(255, 255, 255));
        btnCariKamarD.setText("Cari");
        btnCariKamarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKamarDActionPerformed(evt);
            }
        });

        txtWelcome2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcome2.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcome2.setText("Selamat datang");

        txtWelcomee1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcomee1.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcomee1.setText("username");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(28, 68, 138));
        jLabel8.setText("Anda adalah");

        labelLevel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        labelLevel.setForeground(new java.awt.Color(28, 68, 138));
        labelLevel.setText("level");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(4, 4, 4)
                        .addComponent(labelLevel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtWelcome2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWelcomee1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnTambahKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)))
                                .addComponent(jLabel4))
                            .addGap(66, 66, 66)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnEditKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnHapusKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtHargaKamarD)
                                .addComponent(txtTipeKamarD)
                                .addComponent(txtIdKamarD)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtCariKamarD)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCariKamarD))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(195, 208, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWelcome2)
                    .addComponent(txtWelcomee1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelLevel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCariKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTipeKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditKamarD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusKamarD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(308, 308, 308))
        );

        jTabbedPane1.addTab("Data Fasilitas Kamar", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        tabelFasilitasUmumD.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelFasilitasUmumD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelFasilitasUmumD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelFasilitasUmumDMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelFasilitasUmumD);

        btnHapusUmumD.setBackground(new java.awt.Color(255, 0, 0));
        btnHapusUmumD.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnHapusUmumD.setForeground(new java.awt.Color(249, 249, 249));
        btnHapusUmumD.setText("Hapus");
        btnHapusUmumD.setPreferredSize(new java.awt.Dimension(80, 35));
        btnHapusUmumD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusUmumDActionPerformed(evt);
            }
        });

        btnTambahUmumD.setBackground(new java.awt.Color(51, 204, 0));
        btnTambahUmumD.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTambahUmumD.setText("Tambah");
        btnTambahUmumD.setPreferredSize(new java.awt.Dimension(80, 35));
        btnTambahUmumD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahUmumDActionPerformed(evt);
            }
        });

        btnEditUmumD.setBackground(new java.awt.Color(255, 153, 0));
        btnEditUmumD.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnEditUmumD.setText("Edit");
        btnEditUmumD.setPreferredSize(new java.awt.Dimension(80, 35));
        btnEditUmumD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUmumDActionPerformed(evt);
            }
        });

        btnCariD.setBackground(new java.awt.Color(28, 68, 138));
        btnCariD.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCariD.setForeground(new java.awt.Color(255, 255, 255));
        btnCariD.setText("Cari");
        btnCariD.setPreferredSize(new java.awt.Dimension(51, 35));
        btnCariD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDActionPerformed(evt);
            }
        });

        txtCariUmumD.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtCariUmumD.setForeground(new java.awt.Color(28, 68, 138));
        txtCariUmumD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCariUmumDMouseClicked(evt);
            }
        });
        txtCariUmumD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariUmumDKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(28, 68, 138));
        jLabel1.setText("Cari berdasarkan ID atau fasilitas ");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(28, 68, 138));
        jLabel2.setText("ID");

        txtIdUmumD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtIdUmumD.setForeground(new java.awt.Color(28, 68, 138));
        txtIdUmumD.setPreferredSize(new java.awt.Dimension(6, 35));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(28, 68, 138));
        jLabel3.setText("Nama Fasilitas");

        txtFasilitasUmumD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtFasilitasUmumD.setForeground(new java.awt.Color(28, 68, 138));
        txtFasilitasUmumD.setPreferredSize(new java.awt.Dimension(6, 35));

        txtWelcome1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcome1.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcome1.setText("Selamat datang");

        txtWelcomee2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcomee2.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcomee2.setText("username");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtWelcome1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWelcomee2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnTambahUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(47, 47, 47)
                            .addComponent(btnEditUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(508, 508, 508)
                            .addComponent(btnHapusUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addGap(89, 89, 89)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIdUmumD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFasilitasUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(2, 2, 2)
                            .addComponent(txtCariUmumD)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCariD, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWelcome1)
                    .addComponent(txtWelcomee2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtIdUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFasilitasUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusUmumD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(391, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Data Fasilitas Umum", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Kembali ke Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jButton1)
                .addContainerGap(565, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(860, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kembali Login", jPanel3);

        jScrollPane1.setViewportView(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelFasilitasKamarDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelFasilitasKamarDMouseClicked
        txtIdKamarD.setText(dtmFasilitasKamar.getValueAt(tabelFasilitasKamarD.getSelectedRow(), 1).toString());
        txtTipeKamarD.setText(dtmFasilitasKamar.getValueAt(tabelFasilitasKamarD.getSelectedRow(), 2).toString());
        txtHargaKamarD.setText(dtmFasilitasKamar.getValueAt(tabelFasilitasKamarD.getSelectedRow(), 3).toString());
    }//GEN-LAST:event_tabelFasilitasKamarDMouseClicked

    private void btnTambahKamarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKamarDActionPerformed
        if(txtIdKamarD.getText().equalsIgnoreCase("") || txtTipeKamarD.getText().equalsIgnoreCase("") ||
           txtHargaKamarD.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {            
            try {
                conn = DBConnection.getConnection();
                pstm = conn.prepareStatement("INSERT INTO fasilitas_kamar VALUES ('" + txtIdKamarD.getText() + "'"
                        + ", '" + txtTipeKamarD.getText() + "', '" + txtHargaKamarD.getText() + "');");

                pstm.execute();
                JOptionPane.showMessageDialog(null, 
                        "Data Berhasil Disimpan!",
                        "SUCCESS",
                        JOptionPane.INFORMATION_MESSAGE);
                kosongkanFieldFasKamar();
                tampilkanFasilitasKamar();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
                System.err.println(e);
            }
        }
    }//GEN-LAST:event_btnTambahKamarDActionPerformed

    private void btnCariKamarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKamarDActionPerformed
        tampilkanFasilitasKamar();
    }//GEN-LAST:event_btnCariKamarDActionPerformed

    private void btnEditKamarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditKamarDActionPerformed
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("UPDATE fasilitas_kamar SET id='"+ txtIdKamarD.getText() +"',"
                    + "tipe_kamar='"+ txtTipeKamarD.getText() +"', harga='"+ txtHargaKamarD.getText() +"'"
                    + "WHERE id='"+ txtIdKamarD.getText() +"'");
//            pstm.setString(1, txtIdKamarD.getText());
//            pstm.setString(2, txtTipeKamarD.getText());
//            pstm.setString(3, txtHargaKamarD.getText());
//            pstm.setString(4, txtIdKamarD.getText());
            pstm.execute();
        
            JOptionPane.showMessageDialog(null, 
                    "Data berhasil diedit!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println(e);
        }
            
        tampilkanFasilitasKamar(); 
    }//GEN-LAST:event_btnEditKamarDActionPerformed

    private void btnHapusKamarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKamarDActionPerformed
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("DELETE FROM fasilitas_kamar WHERE id = '" + txtIdKamarD.getText() + "'");
            pstm.execute();
            
            JOptionPane.showMessageDialog(null, "Hapus data berhasil!");
            kosongkanFieldFasKamar();
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        
        tampilkanFasilitasKamar();
    }//GEN-LAST:event_btnHapusKamarDActionPerformed

    private void btnCariDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDActionPerformed
        tampilkanFasilitasUmum();
    }//GEN-LAST:event_btnCariDActionPerformed

    private void btnTambahUmumDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahUmumDActionPerformed
        if(txtIdUmumD.getText().equalsIgnoreCase("") || txtFasilitasUmumD.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {            
            try {
                conn = DBConnection.getConnection();
                pstm = conn.prepareStatement("INSERT INTO fasilitas_umum VALUES ('" + txtIdUmumD.getText() +"',"
                        + "'" + txtFasilitasUmumD.getText() + "');");
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                kosongkanFieldFasUmum();
                tampilkanFasilitasUmum();
            } catch(Exception e) {
                System.err.println(e);
                JOptionPane.showMessageDialog(null, "Error!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnTambahUmumDActionPerformed

    private void btnHapusUmumDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusUmumDActionPerformed
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("DELETE FROM fasilitas_umum WHERE id_fasilitas = '" + txtIdUmumD.getText() +"'");
            pstm.execute();
            
            kosongkanFieldFasUmum();
            tampilkanFasilitasUmum();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusUmumDActionPerformed

    private void btnEditUmumDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUmumDActionPerformed
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(
                    "UPDATE fasilitas_umum SET id_fasilitas=?, nama_fasilitas=? WHERE id_fasilitas=?"
            );
            pstm.setString(1, txtIdUmumD.getText());
            pstm.setString(2, txtFasilitasUmumD.getText());
            pstm.setString(3, txtIdUmumD.getText());
            pstm.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data berhasil diedit!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
           
        tampilkanFasilitasUmum();
    }//GEN-LAST:event_btnEditUmumDActionPerformed

    private void tabelFasilitasUmumDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelFasilitasUmumDMouseClicked
        txtIdUmumD.setText(dtmFasilitasUmum.getValueAt(tabelFasilitasUmumD.getSelectedRow(), 1).toString());
        txtFasilitasUmumD.setText(dtmFasilitasUmum.getValueAt(tabelFasilitasUmumD.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tabelFasilitasUmumDMouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        kosongkanFieldFasUmum();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void txtCariUmumDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCariUmumDMouseClicked
        kosongkanFieldFasUmum();
    }//GEN-LAST:event_txtCariUmumDMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        kosongkanFieldFasKamar();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void txtCariKamarDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCariKamarDMouseClicked
        kosongkanFieldFasKamar();
    }//GEN-LAST:event_txtCariKamarDMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoginPage login = new LoginPage();
        login.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCariKamarDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKamarDKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilkanFasilitasKamar();
        }
    }//GEN-LAST:event_txtCariKamarDKeyPressed

    private void txtCariUmumDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariUmumDKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilkanFasilitasUmum();
        }
    }//GEN-LAST:event_txtCariUmumDKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariD;
    private javax.swing.JButton btnCariKamarD;
    private javax.swing.JButton btnEditKamarD;
    private javax.swing.JButton btnEditUmumD;
    private javax.swing.JButton btnHapusKamarD;
    private javax.swing.JButton btnHapusUmumD;
    private javax.swing.JButton btnTambahKamarD;
    private javax.swing.JButton btnTambahUmumD;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelLevel;
    private javax.swing.JTable tabelFasilitasKamarD;
    private javax.swing.JTable tabelFasilitasUmumD;
    private javax.swing.JTextField txtCariKamarD;
    private javax.swing.JTextField txtCariUmumD;
    private javax.swing.JTextField txtFasilitasUmumD;
    private javax.swing.JTextField txtHargaKamarD;
    private javax.swing.JTextField txtIdKamarD;
    private javax.swing.JTextField txtIdUmumD;
    private javax.swing.JTextField txtTipeKamarD;
    private javax.swing.JLabel txtWelcome1;
    private javax.swing.JLabel txtWelcome2;
    private javax.swing.JLabel txtWelcomee1;
    private javax.swing.JLabel txtWelcomee2;
    // End of variables declaration//GEN-END:variables
}
