/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.DBConnection;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author USER
 */
public class GuestPage extends javax.swing.JFrame {

    // For connection to db
    Connection conn;
    ResultSet rs;
    PreparedStatement pstm;

    String kamarDipilih, hariPenginapanV;

    // for date 
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // for showing to tabelBuktiPemesanan
    private final DefaultTableModel ModelTbl;
    Object[] data = new Object[9];

    String uname;

    /**
     * Creates new form Home
     */

    public GuestPage(String username, String levelMysql) {
        initComponents();
        idPemesan();
        ModelTbl = (DefaultTableModel) tabelBuktiPemesanan.getModel();
        tampilFasilitasUmum();
        tampilFasilitasKamar();

        // display to comboBoxTipeKamar from db
        JComboBox comboBoxTipeKamar = new JComboBox();
        comboBoxTipeKamar.removeAllItems();
        tampilComboBoxTipeKamar();

        this.uname = username;
        txtWelcome1.setText(uname);

        // to set app's name
        this.setTitle("RizHotel v1.0");

        // set txtNamaPemesanD based on their username
        txtNamaPemesanD.setText(uname);

        // set level user
        String level = levelMysql;
        labelLevel.setText(level);
        
        // set fullscreen window
        //setSize(Toolkit.getDefaultToolkit().getScreenSize());

    }

    private void idPemesan() {
        ActionListener taskPerform = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "", nol_mnt = "", nol_dtk = "";
                Date dtTime = new Date();
                int nilaiJam = dtTime.getHours();
                int nilaiMnt = dtTime.getMinutes();
                int nilaiDtk = dtTime.getSeconds();
                if (nilaiJam <= 9) {
                    nol_jam = "0";
                }
                if (nilaiMnt <= 9) {
                    nol_mnt = "0";
                }
                if (nilaiDtk <= 9) {
                    nol_dtk = "0";
                }
                String jam = nol_jam + Integer.toString(nilaiJam);
                String mnt = nol_mnt + Integer.toString(nilaiMnt);
                String dtk = nol_dtk + Integer.toString(nilaiDtk);
                Date skrg = new Date();
                DateFormat tgl = new SimpleDateFormat("ddMMyyyy");
                String id = "000" + tgl.format(skrg) + jam + mnt + dtk;
                noPemesanD.setText(id);
            }
        };
        new Timer(1000, taskPerform).start();
    }

    public String hitungHariPenginapan() {
        try {
            sdf.setLenient(false);
            Date tglCheckIn = sdf.parse(txtCheckInD.getText());
            Date tglCheckOut = sdf.parse(txtCheckOutD.getText());

            long awal = tglCheckIn.getTime();
            long akhir = tglCheckOut.getTime();
            long lama = akhir - awal;

            long lama2 = TimeUnit.MILLISECONDS.toDays(lama);
            hariPenginapanV = String.valueOf(lama2);
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(rootPane, "Masukkan tanggal sesuai format!\nContoh: 22/02/2022");
        }

        return hariPenginapanV;
    }

    public String hitungTotalBayar() {
        hitungHariPenginapan();
        hargaKamar();
        // rumus selisihhari * hargatipekamar * jumlahkamar 
        int selisihHariInt = Integer.parseInt(hariPenginapanV);
        int hargaTipeKamarInt = Integer.parseInt(hargaKamarDipilih.getText());
        int jumlahKamarInt = Integer.parseInt(txtJumlahKamar.getText());
        int totalHarga = selisihHariInt * hargaTipeKamarInt * jumlahKamarInt;
        txtTtlHargaD.setText(String.valueOf(totalHarga));

        return txtTtlHargaD.getText();
    }

    public void setBuktiPemesananTabel() {
        data[0] = noPemesanD.getText();
        data[1] = txtNamaPemesanD.getText();
        data[2] = txtEmailD.getText();
        data[3] = txtNoHPD.getText();
        data[4] = comboBoxTipeKamar.getSelectedItem().toString();
        data[5] = txtJumlahKamar.getText();
        data[6] = txtCheckInD.getText();
        data[7] = txtCheckOutD.getText();
        data[8] = txtTtlHargaD.getText();
        ModelTbl.addRow(data);

        for (int i = 0; i < tabelBuktiPemesanan.getRowCount(); i++) {
            String cetakTeks = tabelBuktiPemesanan.getValueAt(i, 0) + "\t" + tabelBuktiPemesanan.getValueAt(i, 1) + "\t"
                    + tabelBuktiPemesanan.getValueAt(i, 2) + "\t" + tabelBuktiPemesanan.getValueAt(i, 4) + "\t"
                    + tabelBuktiPemesanan.getValueAt(i, 5) + "\t" + tabelBuktiPemesanan.getValueAt(i, 6) + "\t"
                    + tabelBuktiPemesanan.getValueAt(i, 7) + "\t" + tabelBuktiPemesanan.getValueAt(i, 8) + "\n";
        }
        
        JTableHeader header = tabelBuktiPemesanan.getTableHeader();
        Font headerFont = new Font("Segoe UI Semibold", Font.PLAIN, 16);
        header.setFont(headerFont);
    }

    public void tampilFasilitasUmum() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM fasilitas_umum");
            rs = pstm.executeQuery();

            DefaultTableModel dtf = (DefaultTableModel) tabelFasilitasUmum.getModel();

            // set table in order to not be able to edit through double click
            tabelFasilitasUmum.setDefaultEditor(Object.class, null);

            // set font style and size for table heading
            JTableHeader header = tabelFasilitasUmum.getTableHeader();
            Font headerFont = new Font("Segoe UI Semibold", Font.PLAIN, 16);
            header.setFont(headerFont);

            // to remove all columns that we set before
            dtf.setColumnCount(0);

            // to add columns
            dtf.addColumn("No.");
            dtf.addColumn("Fasilitas");

            while (rs.next()) {
                Object o[] = {rs.getString(1), rs.getString(2)};
                dtf.addRow(o);
            }
            tabelFasilitasUmum.setModel(dtf);

            // set width for first and second column
            tabelFasilitasUmum.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelFasilitasUmum.getColumnModel().getColumn(1).setPreferredWidth(450);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error!", "Error", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null, e);
            System.err.println(e);
        }
    }

    public void tampilFasilitasKamar() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM fasilitas_kamar;");
            rs = pstm.executeQuery();

            DefaultTableModel dtf = (DefaultTableModel) tabelFasilitasKamarD.getModel();

            // set table in order to not be able to edit through double click
            tabelFasilitasKamarD.setDefaultEditor(Object.class, null);

            // set font style and size for table heading
            JTableHeader header = tabelFasilitasKamarD.getTableHeader();
            Font headerFont = new Font("Segoe UI Semibold", Font.PLAIN, 16);
            header.setFont(headerFont);

            // to remove all columns that we set before
            dtf.setColumnCount(0);

            // to add columns
            dtf.addColumn("No.");
            dtf.addColumn("Tipe Kamar");
            dtf.addColumn("Harga");

            while (rs.next()) {
                Object o[] = {rs.getString(1), rs.getString(2), rs.getString(3)};
                dtf.addRow(o);
            }
            tabelFasilitasKamarD.setModel(dtf);

            // set width for first, 2nd, 3rd columns
            tabelFasilitasKamarD.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelFasilitasKamarD.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabelFasilitasKamarD.getColumnModel().getColumn(2).setPreferredWidth(200);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Got an error!");
            System.err.println(e);
        }
    }

    public void tampilComboBoxTipeKamar() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM fasilitas_kamar");
            rs = pstm.executeQuery();

            comboBoxTipeKamar.addItem("=== PILIH TIPE KAMAR ANDA ===");
            while (rs.next()) {
                comboBoxTipeKamar.addItem(rs.getString("tipe_kamar"));
                System.out.println(rs.getString(2) + rs.getString("harga"));
            }
            hargaKamar();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void hargaKamar() {
        comboBoxTipeKamar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    conn = DBConnection.getConnection();
                    pstm = conn.prepareStatement("SELECT * FROM fasilitas_kamar WHERE tipe_kamar=?");
                    pstm.setString(1, (String) comboBoxTipeKamar.getSelectedItem());
                    rs = pstm.executeQuery();

                    while (rs.next()) {
                        hargaKamarDipilih.setText(rs.getString("harga"));
                        System.out.println(rs.getString(2) + rs.getString("harga"));
                    }
                } catch (Exception e) {
                    System.err.println(e);
                } //To change body of generated methods, choose Tools | Templates.
            }
        });

    }
    
    public void printPdfBuktiPemesanan() {
        // this is will print at pemesanan's tab
        PrinterJob print = PrinterJob.getPrinterJob();
        print.setJobName("Cetak Data Pemesanan");
        
        print.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                pf.setOrientation(PageFormat.PORTRAIT);
                if(pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                
                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.65,0.60);
                
                jPanel2.print(g2);
                
                return Printable.PAGE_EXISTS;
            }
        });
        
        boolean ok = print.printDialog();
        if(ok) {
            try{
                print.print();
            } catch(Exception e) {
                
            }
        }
    }
    
    public void printPdfBuktiPemesananTabel() {
        // this is will print at bukti pemesanan's tab
        MessageFormat header = new MessageFormat("Bukti Reservasi Anda"); // set title
        MessageFormat footer = new MessageFormat("By RizHotel"); // set footer
        try {
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.PORTRAIT);
            tabelBuktiPemesanan.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
            JOptionPane.showMessageDialog(null, "\n" + "Printed Successfully");
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error!", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void bersihkanBuktiPemesanan() {
        txtNamaPemesanD.setText(uname);
        txtEmailD.setText(null);
        txtNoHPD.setText(null);
        txtJumlahKamar.setText(null);
        txtCheckInD.setText(null);
        txtCheckOutD.setText(null);
        hargaKamarDipilih.setText(null);
        txtTtlHargaD.setText(null);
        comboBoxTipeKamar.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtwelcome = new javax.swing.JLabel();
        txtWelcome1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelLevel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelFasilitasUmum = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelFasilitasKamarD = new javax.swing.JTable();
        btnKeluar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtNamaPemesanD = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtEmailD = new javax.swing.JTextField();
        txtNoHPD = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comboBoxTipeKamar = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        txtCheckInD = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCheckOutD = new javax.swing.JTextField();
        noPemesanD = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTtlHargaD = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtJumlahKamar = new javax.swing.JTextField();
        hargaKamarDipilih = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnKonfirmasiPesanan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnKeluar2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelBuktiPemesanan = new javax.swing.JTable();
        btnCetak = new javax.swing.JButton();
        txtwelcome1 = new javax.swing.JLabel();
        txtWelcome4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        jLabel1.setText("Tipe Kamar");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/home_top_bar1000.png"))); // NOI18N
        jLabel4.setToolTipText("");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        jLabel7.setText("Fasilitas");

        jPanel5.setBackground(new java.awt.Color(28, 68, 138));
        jPanel5.setMaximumSize(new java.awt.Dimension(500, 32767));
        jPanel5.setPreferredSize(new java.awt.Dimension(800, 99));

        jLabel3.setBackground(new java.awt.Color(254, 243, 229));
        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(246, 245, 245));
        jLabel3.setText("RizHotel");

        txtwelcome.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtwelcome.setForeground(new java.awt.Color(246, 245, 245));
        txtwelcome.setText("Selamat datang");

        txtWelcome1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcome1.setForeground(new java.awt.Color(246, 245, 245));
        txtWelcome1.setText("username");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Anda adalah");

        labelLevel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        labelLevel.setForeground(new java.awt.Color(255, 255, 255));
        labelLevel.setText("level");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtwelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWelcome1))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(labelLevel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 512, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(39, 39, 39))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtwelcome)
                    .addComponent(txtWelcome1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelLevel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabelFasilitasUmum.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        tabelFasilitasUmum.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Fasilitas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelFasilitasUmum.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tabelFasilitasUmum);

        tabelFasilitasKamarD.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        tabelFasilitasKamarD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Tipe Kamar", "Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelFasilitasKamarD.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tabelFasilitasKamarD);

        btnKeluar.setBackground(new java.awt.Color(255, 0, 0));
        btnKeluar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnKeluar.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/illustration_hotel350.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                                .addComponent(jScrollPane7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(95, 95, 95))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1480, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("Home", jScrollPane1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(28, 68, 138));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nama Pemesan");

        txtNamaPemesanD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Email");

        txtEmailD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtNoHPD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("No. Handphone");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tipe Kamar");

        comboBoxTipeKamar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        comboBoxTipeKamar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboBoxTipeKamarFocusLost(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tanggal Check In");

        txtCheckInD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tanggal Check Out");

        txtCheckOutD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtCheckOutD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCheckOutDFocusLost(evt);
            }
        });

        noPemesanD.setEditable(false);
        noPemesanD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("No. Pemesan");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Total Harga");

        txtTtlHargaD.setEditable(false);
        txtTtlHargaD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Jumlah Kamar");

        txtJumlahKamar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        hargaKamarDipilih.setEditable(false);
        hargaKamarDipilih.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Harga Kamar");

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(238, 237, 237));
        jLabel9.setText("Form Pemesanan");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel23)
                    .addComponent(jLabel22)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21))
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTtlHargaD)
                    .addComponent(txtCheckInD)
                    .addComponent(txtNamaPemesanD)
                    .addComponent(noPemesanD)
                    .addComponent(txtEmailD)
                    .addComponent(txtNoHPD)
                    .addComponent(comboBoxTipeKamar, 0, 348, Short.MAX_VALUE)
                    .addComponent(hargaKamarDipilih)
                    .addComponent(txtJumlahKamar)
                    .addComponent(txtCheckOutD))
                .addContainerGap(170, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(335, 335, 335))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(noPemesanD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaPemesanD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoHPD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxTipeKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hargaKamarDipilih, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlahKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheckInD, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheckOutD, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTtlHargaD, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(15, 15, 15))
        );

        btnKonfirmasiPesanan.setBackground(new java.awt.Color(0, 204, 0));
        btnKonfirmasiPesanan.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnKonfirmasiPesanan.setText("Konfirmasi Pesanan");
        btnKonfirmasiPesanan.setPreferredSize(new java.awt.Dimension(125, 35));
        btnKonfirmasiPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiPesananActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Batal Isi");
        jButton1.setPreferredSize(new java.awt.Dimension(71, 35));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnKeluar2.setText("Keluar");
        btnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(810, 810, 810)
                        .addComponent(btnKeluar2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(btnKonfirmasiPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(2106, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKonfirmasiPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1170, 1170, 1170)
                .addComponent(btnKeluar2)
                .addContainerGap(1170, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        jTabbedPane1.addTab("Pemesanan", jScrollPane2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        tabelBuktiPemesanan.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        tabelBuktiPemesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Pemesanan", "Nama", "Email", "No. HP", "Tipe Kamar", "Jumlah Kamar", "Check In", "Check Out", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBuktiPemesanan.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelBuktiPemesanan);

        btnCetak.setBackground(new java.awt.Color(255, 255, 255));
        btnCetak.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/print_26px.png"))); // NOI18N
        btnCetak.setContentAreaFilled(false);
        btnCetak.setPreferredSize(new java.awt.Dimension(101, 35));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        txtwelcome1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtwelcome1.setForeground(new java.awt.Color(235, 235, 235));
        txtwelcome1.setText("Selamat datang,");

        txtWelcome4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtWelcome4.setForeground(new java.awt.Color(235, 235, 235));
        txtWelcome4.setText("username");

        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(28, 68, 138));
        jLabel13.setText("Bukti Reservasi");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/3illustration_hotel400.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(282, 282, 282)
                            .addComponent(jLabel20)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(2098, 2098, 2098))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(1045, 1045, 1045)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtwelcome1)
                        .addComponent(txtWelcome4))
                    .addContainerGap(1845, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(2612, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(1034, 1034, 1034)
                    .addComponent(txtwelcome1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtWelcome4)
                    .addContainerGap(2096, Short.MAX_VALUE)))
        );

        jScrollPane3.setViewportView(jPanel3);

        jTabbedPane1.addTab("Bukti Pemesanan", jScrollPane3);

        jButton4.setBackground(new java.awt.Color(28, 68, 138));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("About Page");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(441, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1338, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("About", jPanel8);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Kembali ke Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(424, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap(402, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1352, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kembali Login", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1476, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKonfirmasiPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiPesananActionPerformed
        if (noPemesanD.getText().equalsIgnoreCase("") || txtNamaPemesanD.getText().equalsIgnoreCase("")
                || txtEmailD.getText().equalsIgnoreCase("") || txtNoHPD.getText().equalsIgnoreCase("")
                || comboBoxTipeKamar.getSelectedItem().toString().equalsIgnoreCase("=== PILIH TIPE KAMAR ANDA ===")
                || hargaKamarDipilih.getText().equalsIgnoreCase("") || txtJumlahKamar.getText().equalsIgnoreCase("")
                || txtCheckInD.getText().equalsIgnoreCase("") || txtCheckOutD.getText().equalsIgnoreCase("")
                || txtTtlHargaD.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi lengkap!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String sql = "INSERT INTO pemesanan VALUES ('" + noPemesanD.getText() + "', '" + txtNamaPemesanD.getText() + "', '" + txtEmailD.getText() + "', '" + txtNoHPD.getText() + "'"
                        + ", '" + comboBoxTipeKamar.getSelectedItem().toString() + "', '" + txtJumlahKamar.getText() + "', '" + txtCheckInD.getText() + "', '" + txtCheckOutD.getText() + "'"
                        + ", '" + txtTtlHargaD.getText() + "')";

                conn = DBConnection.getConnection();
                pstm = conn.prepareStatement(sql);
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Pemesanan Berhasil!");
                jTabbedPane1.setSelectedIndex(2);
                setBuktiPemesananTabel();
            } catch (HeadlessException | SQLException e) {
                System.err.println(e);
                JOptionPane.showMessageDialog(null, "Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnKonfirmasiPesananActionPerformed

    private void txtCheckOutDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCheckOutDFocusLost
        hitungTotalBayar();
    }//GEN-LAST:event_txtCheckOutDFocusLost

    private void comboBoxTipeKamarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboBoxTipeKamarFocusLost
        hargaKamar();
    }//GEN-LAST:event_comboBoxTipeKamarFocusLost

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluar2ActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluar2ActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        printPdfBuktiPemesanan();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // to remove selection at row
        tabelFasilitasUmum.clearSelection();
        tabelFasilitasKamarD.clearSelection();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        bersihkanBuktiPemesanan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        tabelBuktiPemesanan.clearSelection(); // to remove selection at row
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LoginPage login = new LoginPage();
        login.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // to remove selection at row
        tabelFasilitasKamarD.clearSelection();
        tabelFasilitasUmum.clearSelection();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AboutPage about = new AboutPage();
        about.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GuestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuestPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestPage("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnKeluar2;
    private javax.swing.JButton btnKonfirmasiPesanan;
    private javax.swing.JComboBox comboBoxTipeKamar;
    private javax.swing.JTextField hargaKamarDipilih;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelLevel;
    private javax.swing.JTextField noPemesanD;
    private javax.swing.JTable tabelBuktiPemesanan;
    private javax.swing.JTable tabelFasilitasKamarD;
    private javax.swing.JTable tabelFasilitasUmum;
    private javax.swing.JTextField txtCheckInD;
    private javax.swing.JTextField txtCheckOutD;
    private javax.swing.JTextField txtEmailD;
    private javax.swing.JTextField txtJumlahKamar;
    private javax.swing.JTextField txtNamaPemesanD;
    private javax.swing.JTextField txtNoHPD;
    private javax.swing.JTextField txtTtlHargaD;
    private javax.swing.JLabel txtWelcome1;
    private javax.swing.JLabel txtWelcome4;
    private javax.swing.JLabel txtwelcome;
    private javax.swing.JLabel txtwelcome1;
    // End of variables declaration//GEN-END:variables

    private Object setSize(Toolkit defaultToolkit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
