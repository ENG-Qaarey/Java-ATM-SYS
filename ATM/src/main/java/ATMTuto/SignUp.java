package ATMTuto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Signup form matching the classic ATM signup layout (blue theme).
 */
public class SignUp extends JFrame {

    private final Color themeBlue = new Color(0, 102, 204);
    private final Color labelBlue = new Color(0, 51, 204);
    private final Color white = Color.WHITE;
    private final Color dark = new Color(40, 40, 40);

    private JTextField AccNumTb;
    private JTextField AccNameTb;
    private JTextField FatherNameTb;
    private JTextField DobTb;
    private JTextField PinTb;
    private JComboBox<String> EducationCb;
    private JTextField OccupationTb;
    private JTextField PhoneTb;
    private JTextField AddressTb;
    private JButton SubmitBtn;

    public SignUp() {
        initUi();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUi() {
        setTitle("ATM Signup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(760, 560);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(themeBlue);
        header.setPreferredSize(new Dimension(0, 70));

        JLabel title = new JLabel("ATM MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(white);

        JLabel close = new JLabel(" x ", SwingConstants.CENTER);
        close.setFont(new Font("Tahoma", Font.BOLD, 22));
        close.setForeground(white);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.exit(0);
            }
        });

        header.add(title, BorderLayout.CENTER);
        header.add(close, BorderLayout.EAST);
        return header;
    }

    private JPanel buildBody() {
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(white);
        body.setBorder(BorderFactory.createEmptyBorder(18, 36, 10, 36));

        JLabel formTitle = new JLabel("SIGNUP FORM", SwingConstants.CENTER);
        formTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        formTitle.setForeground(themeBlue);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        body.add(formTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        AccNumTb = textField();
        AccNameTb = textField();
        FatherNameTb = textField();
        DobTb = textField();
        PinTb = textField();
        OccupationTb = textField();
        PhoneTb = textField();
        AddressTb = textField();

        EducationCb = new JComboBox<String>(new String[]{
            "Uneducated", "Primary", "Secondary", "Diploma", "Graduate", "Post Graduate"
        });
        EducationCb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        EducationCb.setForeground(themeBlue);
        EducationCb.setBackground(white);
        EducationCb.setPreferredSize(new Dimension(180, 28));

        int row = 0;
        // Left column / Right column pairs
        row = addPairRow(form, row,
                "ACC NUM :", AccNumTb,
                "PIN :", PinTb);
        row = addPairRow(form, row,
                "NAME :", AccNameTb,
                "EDUCATION :", EducationCb);
        row = addPairRow(form, row,
                "FATHER'S NAME :", FatherNameTb,
                "OCCUPATION :", OccupationTb);
        row = addPairRow(form, row,
                "DOB :", DobTb,
                "PHONE :", PhoneTb);

        // Address full width
        GridBagConstraints labGbc = gbc(0, row, GridBagConstraints.EAST);
        labGbc.insets = new Insets(12, 6, 8, 8);
        form.add(fieldLabel("ADDRESS :"), labGbc);

        GridBagConstraints addrGbc = gbc(1, row, GridBagConstraints.WEST);
        addrGbc.gridwidth = 3;
        addrGbc.fill = GridBagConstraints.HORIZONTAL;
        addrGbc.weightx = 1;
        addrGbc.insets = new Insets(12, 4, 8, 6);
        AddressTb.setPreferredSize(new Dimension(460, 28));
        form.add(AddressTb, addrGbc);
        row++;

        // Submit
        SubmitBtn = new JButton("Submit");
        SubmitBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        SubmitBtn.setForeground(themeBlue);
        SubmitBtn.setBackground(white);
        SubmitBtn.setFocusPainted(false);
        SubmitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SubmitBtn.setPreferredSize(new Dimension(120, 34));
        SubmitBtn.addActionListener(e -> submitSignup());

        GridBagConstraints btnGbc = gbc(0, row, GridBagConstraints.CENTER);
        btnGbc.gridwidth = 4;
        btnGbc.insets = new Insets(22, 0, 8, 0);
        form.add(SubmitBtn, btnGbc);
        row++;

        JLabel bank = new JLabel("WORLD BANK", SwingConstants.CENTER);
        bank.setFont(new Font("Tahoma", Font.BOLD, 18));
        bank.setForeground(themeBlue);
        GridBagConstraints bankGbc = gbc(0, row, GridBagConstraints.CENTER);
        bankGbc.gridwidth = 4;
        bankGbc.insets = new Insets(8, 0, 4, 0);
        form.add(bank, bankGbc);

        // Back to login link
        JLabel back = new JLabel("Already have an account? Login", SwingConstants.CENTER);
        back.setFont(new Font("Tahoma", Font.PLAIN, 12));
        back.setForeground(labelBlue);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });
        GridBagConstraints backGbc = gbc(0, row + 1, GridBagConstraints.CENTER);
        backGbc.gridwidth = 4;
        backGbc.insets = new Insets(6, 0, 0, 0);
        form.add(back, backGbc);

        body.add(form, BorderLayout.CENTER);
        return body;
    }

    private int addPairRow(JPanel form, int row,
            String leftLabel, java.awt.Component leftField,
            String rightLabel, java.awt.Component rightField) {
        GridBagConstraints l1 = gbc(0, row, GridBagConstraints.EAST);
        l1.insets = new Insets(8, 6, 8, 8);
        form.add(fieldLabel(leftLabel), l1);

        GridBagConstraints f1 = gbc(1, row, GridBagConstraints.WEST);
        f1.insets = new Insets(8, 4, 8, 18);
        leftField.setPreferredSize(new Dimension(180, 28));
        form.add(leftField, f1);

        GridBagConstraints l2 = gbc(2, row, GridBagConstraints.EAST);
        l2.insets = new Insets(8, 10, 8, 8);
        form.add(fieldLabel(rightLabel), l2);

        GridBagConstraints f2 = gbc(3, row, GridBagConstraints.WEST);
        f2.insets = new Insets(8, 4, 8, 6);
        rightField.setPreferredSize(new Dimension(180, 28));
        form.add(rightField, f2);

        return row + 1;
    }

    private GridBagConstraints gbc(int x, int y, int anchor) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x;
        g.gridy = y;
        g.anchor = anchor;
        return g;
    }

    private JLabel fieldLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Tahoma", Font.BOLD, 14));
        lb.setForeground(labelBlue);
        return lb;
    }

    private JTextField textField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tf.setForeground(dark);
        tf.setBackground(white);
        tf.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        return tf;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout());
        footer.setBackground(themeBlue);
        footer.setPreferredSize(new Dimension(0, 12));
        return footer;
    }

    private void clear() {
        AccNumTb.setText("");
        AccNameTb.setText("");
        FatherNameTb.setText("");
        DobTb.setText("");
        PinTb.setText("");
        EducationCb.setSelectedIndex(0);
        OccupationTb.setText("");
        PhoneTb.setText("");
        AddressTb.setText("");
    }

    private boolean accountExists(Connection connection, int accountNumber) throws Exception {
        PreparedStatement check = connection.prepareStatement(
                "SELECT AccountNumber FROM Accounts WHERE AccountNumber = ?");
        check.setInt(1, accountNumber);
        ResultSet rs = check.executeQuery();
        boolean exists = rs.next();
        rs.close();
        check.close();
        return exists;
    }

    private void submitSignup() {
        if (AccNumTb.getText().trim().isEmpty()
                || AccNameTb.getText().trim().isEmpty()
                || FatherNameTb.getText().trim().isEmpty()
                || DobTb.getText().trim().isEmpty()
                || PinTb.getText().trim().isEmpty()
                || OccupationTb.getText().trim().isEmpty()
                || PhoneTb.getText().trim().isEmpty()
                || AddressTb.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Missing Information!");
            return;
        }

        int accountNumber;
        int pin;
        try {
            accountNumber = Integer.parseInt(AccNumTb.getText().trim());
            pin = Integer.parseInt(PinTb.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Account Number and PIN must be numbers.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                return;
            }

            ensureAccountColumns(con);

            if (accountExists(con, accountNumber)) {
                JOptionPane.showMessageDialog(this,
                        "Account number " + accountNumber + " already exists.\nPlease choose a different account number.");
                AccNumTb.requestFocus();
                con.close();
                return;
            }

            PreparedStatement add = con.prepareStatement(
                    "INSERT INTO Accounts "
                    + "(AccountNumber, FullName, FatherName, DOB, Pin, Education, Occupation, Phone, Address, Balance, IsActive) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 1)");
            add.setInt(1, accountNumber);
            add.setString(2, AccNameTb.getText().trim());
            add.setString(3, FatherNameTb.getText().trim());
            add.setString(4, DobTb.getText().trim());
            add.setInt(5, pin);
            add.setString(6, String.valueOf(EducationCb.getSelectedItem()));
            add.setString(7, OccupationTb.getText().trim());
            add.setString(8, PhoneTb.getText().trim());
            add.setString(9, AddressTb.getText().trim());
            add.executeUpdate();
            add.close();
            con.close();

            JOptionPane.showMessageDialog(this, "Account Saved");
            JOptionPane.showMessageDialog(this,
                    "Your Account Number is " + accountNumber + " and your Pin is " + pin
                    + ".\nLogin to access your Account.");
            clear();
            dispose();
            new Login().setVisible(true);
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this,
                    "Account number already exists.\nPlease choose a different account number.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not create account: " + e.getMessage());
        }
    }

    /** Adds signup profile columns if the database was created with the old schema. */
    private void ensureAccountColumns(Connection con) {
        String[] alters = new String[]{
            "ALTER TABLE Accounts ADD COLUMN FatherName VARCHAR(100) NULL",
            "ALTER TABLE Accounts ADD COLUMN DOB VARCHAR(30) NULL",
            "ALTER TABLE Accounts ADD COLUMN Education VARCHAR(50) NULL",
            "ALTER TABLE Accounts ADD COLUMN Occupation VARCHAR(100) NULL",
            "ALTER TABLE Accounts ADD COLUMN Phone VARCHAR(30) NULL",
            "ALTER TABLE Accounts ADD COLUMN Address VARCHAR(255) NULL"
        };
        for (String sql : alters) {
            try {
                con.createStatement().execute(sql);
            } catch (Exception ignored) {
                // Column already exists
            }
        }
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
