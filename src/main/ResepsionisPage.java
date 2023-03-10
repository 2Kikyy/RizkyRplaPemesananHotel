/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import db.DBConnection;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.AbstractButton;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author USER
 */
public class ResepsionisPage extends javax.swing.JFrame {
    
    // for connection to db
    Connection conn;
    Statement stm;
    ResultSet rs;
    PreparedStatement pstm;
        
    DefaultTableModel ModelTbl;

    public ResepsionisPage(String username, String levelMysql) {
        initComponents();
        tampilDataKeReservasi();
        
        // set username on the screen
        String uname = username;
        txtWelcome1.setText(uname);
        
        // set level user
        String level = levelMysql;
        labelLevel.setText(level);
        
        // to set app's name
        this.setTitle("RizHotel v1.0");
    }

    public void tampilDataKeReservasi() {
        ModelTbl = (DefaultTableModel) tabelReservasiD.getModel();
        ModelTbl.addColumn("No Pemesan");
        ModelTbl.addColumn("Nama Pemesan");
        ModelTbl.addColumn("Email");
        ModelTbl.addColumn("No Hp");
        ModelTbl.addColumn("Tipe Kamar");
        ModelTbl.addColumn("Jumlah Kamar");
        ModelTbl.addColumn("Check In");
        ModelTbl.addColumn("Check Out");
        ModelTbl.addColumn("Harga");
        
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM pemesanan;");
            rs = pstm.executeQuery();
            
            while(rs.next()) {
                ModelTbl.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), 
                rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                rs.getString(9)});
            }
            tabelReservasiD.setModel(ModelTbl);
            tabelReservasiD.setDefaultEditor(Object.class, null);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
        JTableHeader header = tabelReservasiD.getTableHeader();
        Font headerFont = new Font("Segoe UI Semibold", Font.PLAIN, 16);
        header.setFont(headerFont);
    }
    
    public void prosesCari() {
        try {
            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement("SELECT * FROM pemesanan WHERE tgl_checkin LIKE '%" + txtKolomCari.getText() + "%'"
                    + "OR nama_pemesan LIKE '%" + txtKolomCari.getText() + "%';");
            rs = pstm.executeQuery();
            
            DefaultTableModel dtf = (DefaultTableModel) tabelReservasiD.getModel();
            dtf.setRowCount(0);
            
            while(rs.next()) {
                Object o [] = {rs.getString(1), rs.getString(2), rs.getString(3), 
                rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                rs.getString(9)};
                dtf.addRow(o);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Got an Exception");
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtWelcome2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtWelcome3 = new javax.swing.JLabel();
        txtWelcome1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelReservasiD = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKolomCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnLoginD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        labelLevel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        txtWelcome2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txtWelcome2.setText("Selamat datang user");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));

        txtWelcome3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcome3.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcome3.setText("Selamat datang");

        txtWelcome1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 22)); // NOI18N
        txtWelcome1.setForeground(new java.awt.Color(28, 68, 138));
        txtWelcome1.setText("username");

        tabelReservasiD.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        tabelReservasiD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelReservasiD.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelReservasiD);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(28, 68, 138));
        jLabel2.setText("Data Reservasi");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(28, 68, 138));
        jLabel3.setText("Cari berdasarkan nama atau tanggal check in");

        txtKolomCari.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        txtKolomCari.setForeground(new java.awt.Color(28, 68, 138));
        txtKolomCari.setPreferredSize(new java.awt.Dimension(6, 30));
        txtKolomCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKolomCariMouseClicked(evt);
            }
        });
        txtKolomCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKolomCariKeyPressed(evt);
            }
        });

        btnCari.setBackground(new java.awt.Color(28, 68, 138));
        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCari.setForeground(new java.awt.Color(255, 255, 255));
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnLoginD.setBackground(new java.awt.Color(255, 0, 0));
        btnLoginD.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btnLoginD.setForeground(new java.awt.Color(255, 255, 255));
        btnLoginD.setText("Kembali Login");
        btnLoginD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginDActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(28, 68, 138));
        jLabel1.setText("Anda adalah");

        labelLevel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        labelLevel.setForeground(new java.awt.Color(28, 68, 138));
        labelLevel.setText("level");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/about_24px.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(labelLevel))
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(txtWelcome3)
                            .addGap(4, 4, 4)
                            .addComponent(txtWelcome1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLoginD)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtKolomCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnCari))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtWelcome3)
                        .addComponent(txtWelcome1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelLevel))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKolomCari, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnLoginD, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        prosesCari();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnLoginDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginDActionPerformed
        LoginPage login = new LoginPage();
        login.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnLoginDActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        tabelReservasiD.clearSelection(); // to remove selection at row
    }//GEN-LAST:event_formMouseClicked

    private void txtKolomCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKolomCariMouseClicked
        tabelReservasiD.clearSelection(); // to remove selection at row
    }//GEN-LAST:event_txtKolomCariMouseClicked

    private void txtKolomCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKolomCariKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            prosesCari();
        }
    }//GEN-LAST:event_txtKolomCariKeyPressed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        AboutPage about = new AboutPage();
        about.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jLabel6MouseClicked

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
            java.util.logging.Logger.getLogger(ResepsionisPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResepsionisPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResepsionisPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResepsionisPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResepsionisPage("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnLoginD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelLevel;
    private javax.swing.JTable tabelReservasiD;
    private javax.swing.JTextField txtKolomCari;
    private javax.swing.JLabel txtWelcome1;
    private javax.swing.JLabel txtWelcome2;
    private javax.swing.JLabel txtWelcome3;
    // End of variables declaration//GEN-END:variables
}
