package ATMTuto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBounds(0, 0, 600, 180);
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("ATM MANAGEMENT SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBounds(0, 50, 600, 50);
        headerPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("WORLD BANK", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        subtitleLabel.setForeground(new Color(255, 255, 255));
        subtitleLabel.setBounds(0, 110, 600, 40);
        headerPanel.add(subtitleLabel);

        mainPanel.add(headerPanel);

        JLabel iconLabel = new JLabel("\u2603", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 72));
        iconLabel.setForeground(new Color(0, 102, 204));
        iconLabel.setBounds(0, 200, 600, 80);
        mainPanel.add(iconLabel);

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        loadingLabel.setForeground(new Color(0, 102, 204));
        loadingLabel.setBounds(0, 290, 600, 30);
        mainPanel.add(loadingLabel);

        getContentPane().add(mainPanel);
        setSize(600, 380);
        setLocationRelativeTo(null);

        // Only dismiss splash — caller opens Login to avoid duplicate windows
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

    public static void main(String args[]) {
        SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        splash.dispose();
        new Login().setVisible(true);
    }
}
