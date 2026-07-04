package main.java.ATMTuto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class SignUp extends javax.swing.JFrame {

    public SignUp() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        AccNumTb = new javax.swing.JTextField();
        SubmitBtn = new javax.swing.JButton();
        AccNameTb = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        PinTb = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(360, 180));
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 102, 51));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 1, 24));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ATM MANAGEMENT SYSTEM");

        jLabel8.setBackground(new java.awt.Color(255, 0, 102));
        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 0, 36));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("x");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(207, 207, 207)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("ACCOUNT NUMBER :");

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 1, 18));
        jLabel6.setForeground(new java.awt.Color(255, 102, 51));
        jLabel6.setText("WORLD BANK");

        AccNumTb.setFont(new java.awt.Font("Tahoma", 0, 14));
        AccNumTb.setForeground(new java.awt.Color(255, 102, 51));

        SubmitBtn.setFont(new java.awt.Font("Arial", 1, 18));
        SubmitBtn.setForeground(new java.awt.Color(255, 102, 51));
        SubmitBtn.setText("Submit");
        SubmitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SubmitBtnMouseClicked(evt);
            }
        });
        SubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitBtnActionPerformed(evt);
            }
        });

        AccNameTb.setFont(new java.awt.Font("Tahoma", 0, 14));
        AccNameTb.setForeground(new java.awt.Color(255, 102, 51));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("FULL NAME :");

        PinTb.setFont(new java.awt.Font("Tahoma", 0, 14));
        PinTb.setForeground(new java.awt.Color(255, 102, 51));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("PIN :");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 102, 51));
        jLabel1.setText("SIGNUP FORM");

        jPanel1.setBackground(new java.awt.Color(255, 102, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AccNumTb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(AccNameTb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(PinTb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(256, 256, 256))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SubmitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(273, 273, 273))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(AccNumTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(AccNameTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(PinTb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(SubmitBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void SubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {
    }

    Connection con = null;
    PreparedStatement put = null;
    ResultSet Rs = null;
    Statement st = null;
    
    private void clear(){
        AccNumTb.setText("");
        AccNameTb.setText("");
        PinTb.setText("");
    }
    
    private void SubmitBtnMouseClicked(java.awt.event.MouseEvent evt) {
        if(AccNumTb.getText().isEmpty() || AccNameTb.getText().isEmpty() || PinTb.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Missing Information!");
        }else{
            try{
                con = DBConnection.getConnection();
                if (con == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed.");
                    return;
                }
                PreparedStatement Add = con.prepareStatement("insert into Accounts (AccountNumber, FullName, Pin, Balance, IsActive) values(?, ?, ?, 0, 1)");
                Add.setInt(1, Integer.valueOf(AccNumTb.getText()));
                Add.setString(2, AccNameTb.getText());
                Add.setInt(3, Integer.valueOf(PinTb.getText()));
                int row = Add.executeUpdate();
                JOptionPane.showMessageDialog(this, "Account Saved");
                con.close();
                JOptionPane.showMessageDialog(this, "Your Account Number is "+AccNumTb.getText()+" and your Pin is "+PinTb.getText()+".\n Login to access your Account.");
                clear();
                this.dispose();
                new Login().setVisible(true);
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(1);
    }

    // Variables declaration
    private javax.swing.JTextField AccNameTb;
    private javax.swing.JTextField AccNumTb;
    private javax.swing.JTextField PinTb;
    private javax.swing.JButton SubmitBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
}
