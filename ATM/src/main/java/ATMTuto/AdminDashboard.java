package ATMTuto;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Maximized admin dashboard with sidebar navigation.
 */
public class AdminDashboard extends JFrame {

    private final String adminUsername;
    private String adminName;
    private final Color blue = new Color(0, 102, 204);
    private final Color sidebarBg = new Color(12, 36, 72);
    private final Color sidebarHover = new Color(0, 122, 220);
    private final Color white = Color.WHITE;
    private final Color pageBg = new Color(236, 242, 248);
    private final Color dark = new Color(28, 40, 55);
    private final Color accentTeal = new Color(0, 168, 150);
    private final Color accentOrange = new Color(255, 140, 66);
    private final Color accentPurple = new Color(110, 72, 200);
    private final Color accentPink = new Color(220, 70, 130);

    private final CardLayout contentCards = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentCards);
    private final Map<String, JButton> navButtons = new HashMap<String, JButton>();

    private JLabel totalAccountsLb;
    private JLabel totalBalanceLb;
    private JLabel totalTxLb;
    private JLabel activeAccountsLb;
    private JLabel inactiveAccountsLb;
    private JLabel totalDepositsLb;
    private JLabel totalWithdrawsLb;

    private JPanel statusChartHost;
    private JPanel moneyChartHost;
    private JPanel topAccountsChartHost;

    private DefaultTableModel accountsModel;
    private DefaultTableModel transactionsModel;
    private DefaultTableModel searchModel;
    private JTable accountsTable;
    private JTable transactionsTable;
    private JTable searchTable;
    private TableRowSorter<DefaultTableModel> accountsSorter;
    private TableRowSorter<DefaultTableModel> transactionsSorter;
    private JTextField accountsSearchTb;
    private JTextField transactionsSearchTb;

    private JTextField createAccNumTb;
    private JTextField createNameTb;
    private JTextField createFatherTb;
    private JTextField createDobTb;
    private JTextField createPinTb;
    private JComboBox<String> createEducationCb;
    private JTextField createOccupationTb;
    private JTextField createPhoneTb;
    private JTextField createAddressTb;
    private JTextField createBalanceTb;

    private JTextField searchTb;
    private JTextArea reportArea;

    private JTextField adjustAccTb;
    private JTextField adjustAmountTb;

    private JTextField adminUserTb;
    private JTextField adminNameTb;
    private JPasswordField adminPassTb;
    private DefaultTableModel adminsModel;
    private JTable adminsTable;
    private JLabel adminCountLb;

    private JLabel profileUsernameLb;
    private JTextField profileUsernameTb;
    private JTextField profileNameTb;
    private JPasswordField profileCurrentPassTb;
    private JPasswordField profileNewPassTb;
    private JPasswordField profileConfirmPassTb;
    private JLabel headerSubtitleLb;

    private JLabel pageTitleLb;

    public AdminDashboard(String adminName) {
        this("admin", adminName);
    }

    public AdminDashboard(String adminUsername, String adminName) {
        this.adminUsername = adminUsername == null ? "admin" : adminUsername;
        this.adminName = adminName == null ? "Admin" : adminName;
        initUi();
        showPage("dashboard");
        loadAllData();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void initUi() {
        setTitle("ATM Admin Dashboard - World Bank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMainArea(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 82, 180), getWidth(), 0, new Color(0, 150, 200)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(0, 96));
        header.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel(new GridLayout(2, 1, 0, 4));
        left.setOpaque(false);

        JLabel title = new JLabel("WORLD BANK  |  ATM ADMIN CONTROL CENTER");
        title.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        title.setForeground(white);

        JLabel subtitle = new JLabel("Signed in as " + adminName + " (@" + adminUsername + ")  •  Live banking operations dashboard");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(220, 240, 255));
        headerSubtitleLb = subtitle;

        left.add(title);
        left.add(subtitle);

        JButton logoutBtn = new JButton("Logout");
        stylePrimaryButton(logoutBtn);
        logoutBtn.setPreferredSize(new Dimension(120, 44));
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logoutBtn.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        header.add(left, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);
        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(sidebarBg);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 14, 28, 14));

        JLabel brand = new JLabel("ADMIN PANEL");
        brand.setForeground(white);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        brand.setAlignmentX(LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, 10, 6, 0));
        sidebar.add(brand);

        JLabel menuTitle = new JLabel("NAVIGATION");
        menuTitle.setForeground(new Color(140, 180, 230));
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuTitle.setAlignmentX(LEFT_ALIGNMENT);
        menuTitle.setBorder(BorderFactory.createEmptyBorder(8, 10, 16, 0));
        sidebar.add(menuTitle);

        sidebar.add(navItem("Dashboard", "dashboard"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Accounts", "accounts"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Create User", "createUser"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Transactions", "transactions"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Reports", "reports"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Search", "search"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Adjust Balance", "adjust"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("Manage Admins", "admins"));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(navItem("My Profile", "profile"));

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton navItem(String label, final String page) {
        final JButton btn = new JButton(label);
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btn.setPreferredSize(new Dimension(220, 52));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBackground(sidebarBg);
        btn.setForeground(white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> showPage(page));
        navButtons.put(page, btn);
        return btn;
    }

    private JPanel buildMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(pageBg);

        pageTitleLb = new JLabel("Dashboard Overview");
        pageTitleLb.setFont(new Font("Segoe UI", Font.BOLD, 28));
        pageTitleLb.setForeground(dark);
        pageTitleLb.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));
        main.add(pageTitleLb, BorderLayout.NORTH);

        contentPanel.setBackground(pageBg);
        contentPanel.add(buildDashboardPage(), "dashboard");
        contentPanel.add(buildAccountsPage(), "accounts");
        contentPanel.add(buildCreateUserPage(), "createUser");
        contentPanel.add(buildTransactionsPage(), "transactions");
        contentPanel.add(buildReportsPage(), "reports");
        contentPanel.add(buildSearchPage(), "search");
        contentPanel.add(buildAdjustBalancePage(), "adjust");
        contentPanel.add(buildAdminsPage(), "admins");
        contentPanel.add(buildProfilePage(), "profile");

        main.add(contentPanel, BorderLayout.CENTER);
        return main;
    }

    private void showPage(String page) {
        contentCards.show(contentPanel, page);
        String title = page;
        if ("dashboard".equals(page)) {
            title = "Dashboard Overview";
            refreshCharts();
        } else if ("accounts".equals(page)) {
            title = "Accounts Management";
        } else if ("createUser".equals(page)) {
            title = "Create New User";
        } else if ("transactions".equals(page)) {
            title = "Transaction History";
        } else if ("reports".equals(page)) {
            title = "Reports & Analytics";
            loadReports();
        } else if ("search".equals(page)) {
            title = "Search Accounts";
        } else if ("adjust".equals(page)) {
            title = "Adjust Balance";
        } else if ("admins".equals(page)) {
            title = "Manage Admins";
            loadAdmins();
        } else if ("profile".equals(page)) {
            title = "My Profile";
            loadMyProfile();
        }
        pageTitleLb.setText(title);

        for (Map.Entry<String, JButton> entry : navButtons.entrySet()) {
            if (entry.getKey().equals(page)) {
                entry.getValue().setBackground(sidebarHover);
            } else {
                entry.getValue().setBackground(sidebarBg);
            }
        }
    }

    private JPanel buildDashboardPage() {
        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setBackground(pageBg);
        page.setBorder(BorderFactory.createEmptyBorder(8, 28, 28, 28));

        JPanel stats = new JPanel(new GridLayout(2, 4, 16, 16));
        stats.setOpaque(false);
        stats.setPreferredSize(new Dimension(0, 240));

        totalAccountsLb = valueLabel();
        totalBalanceLb = valueLabel();
        activeAccountsLb = valueLabel();
        inactiveAccountsLb = valueLabel();
        totalTxLb = valueLabel();
        totalDepositsLb = valueLabel();
        totalWithdrawsLb = valueLabel();
        JLabel welcomeStat = valueLabel();
        welcomeStat.setText("LIVE");
        welcomeStat.setForeground(accentTeal);

        stats.add(statCard("Total Accounts", totalAccountsLb, blue));
        stats.add(statCard("Total Balance", totalBalanceLb, accentTeal));
        stats.add(statCard("Active Accounts", activeAccountsLb, new Color(46, 160, 90)));
        stats.add(statCard("Inactive Accounts", inactiveAccountsLb, accentOrange));
        stats.add(statCard("Transactions", totalTxLb, accentPurple));
        stats.add(statCard("Total Deposits", totalDepositsLb, new Color(0, 140, 180)));
        stats.add(statCard("Total Withdrawals", totalWithdrawsLb, accentPink));
        stats.add(statCard("System Status", welcomeStat, new Color(70, 90, 130)));

        JPanel charts = new JPanel(new GridLayout(1, 3, 16, 0));
        charts.setOpaque(false);
        charts.setPreferredSize(new Dimension(0, 420));

        statusChartHost = chartHostPanel("Account Status");
        moneyChartHost = chartHostPanel("Deposits vs Withdrawals");
        topAccountsChartHost = chartHostPanel("Top Accounts by Balance");
        charts.add(statusChartHost);
        charts.add(moneyChartHost);
        charts.add(topAccountsChartHost);

        JPanel actions = new JPanel(new BorderLayout());
        actions.setBackground(white);
        actions.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, new Color(210, 220, 230)),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));
        JLabel tipTitle = new JLabel("Quick Actions");
        tipTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tipTitle.setForeground(dark);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        buttons.setOpaque(false);
        JButton goCreate = bigActionButton("Create User");
        JButton goReports = bigActionButton("Open Reports");
        JButton goAccounts = bigActionButton("Manage Accounts");
        JButton goTx = bigActionButton("Transactions");
        JButton refresh = bigActionButton("Refresh Charts");
        goCreate.addActionListener(e -> showPage("createUser"));
        goReports.addActionListener(e -> showPage("reports"));
        goAccounts.addActionListener(e -> showPage("accounts"));
        goTx.addActionListener(e -> showPage("transactions"));
        refresh.addActionListener(e -> loadAllData());
        buttons.add(goCreate);
        buttons.add(goReports);
        buttons.add(goAccounts);
        buttons.add(goTx);
        buttons.add(refresh);

        actions.add(tipTitle, BorderLayout.NORTH);
        actions.add(buttons, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(charts, BorderLayout.CENTER);
        center.add(actions, BorderLayout.SOUTH);

        page.add(stats, BorderLayout.NORTH);
        page.add(center, BorderLayout.CENTER);
        return page;
    }

    private JButton bigActionButton(String text) {
        JButton btn = new JButton(text);
        stylePrimaryButton(btn);
        btn.setPreferredSize(new Dimension(170, 46));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return btn;
    }

    private JPanel chartHostPanel(String title) {
        JPanel host = new JPanel(new BorderLayout());
        host.setBackground(white);
        host.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, new Color(210, 220, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JLabel lb = new JLabel(title, SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lb.setForeground(dark);
        host.add(lb, BorderLayout.NORTH);
        JLabel loading = new JLabel("Loading chart...", SwingConstants.CENTER);
        loading.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loading.setForeground(new Color(120, 120, 120));
        host.add(loading, BorderLayout.CENTER);
        return host;
    }

    private JLabel valueLabel() {
        JLabel lb = new JLabel("0", SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lb.setForeground(dark);
        return lb;
    }

    private JPanel statCard(String title, JLabel valueLabel, final Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, 8, getHeight(), 8, 8);
                g2.dispose();
            }
        };
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, new Color(210, 220, 230)),
                BorderFactory.createEmptyBorder(18, 22, 18, 18)));

        JLabel titleLb = new JLabel(title.toUpperCase());
        titleLb.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLb.setForeground(new Color(110, 120, 135));

        valueLabel.setForeground(accent);

        card.add(titleLb, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildAccountsPage() {
        JPanel page = wrapPage();
        accountsModel = nonEditableModel(new String[]{
            "Account No", "Full Name", "PIN", "Balance", "Active", "Created"
        });
        accountsTable = styledTable(accountsModel);
        accountsSorter = new TableRowSorter<DefaultTableModel>(accountsModel);
        accountsTable.setRowSorter(accountsSorter);

        accountsSearchTb = searchInputField("Search by account no, name, PIN...");
        wireLiveFilter(accountsSearchTb, () -> filterAccountsTable());

        JButton clearSearchBtn = new JButton("Clear");
        stylePrimaryButton(clearSearchBtn);
        clearSearchBtn.addActionListener(e -> {
            accountsSearchTb.setText("");
            filterAccountsTable();
        });

        JButton refreshBtn = new JButton("Refresh");
        JButton toggleBtn = new JButton("Activate / Deactivate");
        JButton deleteBtn = new JButton("Delete Account");
        stylePrimaryButton(refreshBtn);
        stylePrimaryButton(toggleBtn);
        stylePrimaryButton(deleteBtn);
        refreshBtn.addActionListener(e -> loadAllData());
        toggleBtn.addActionListener(e -> toggleSelectedAccount());
        deleteBtn.addActionListener(e -> deleteSelectedAccount());

        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchRow.setOpaque(false);
        JLabel searchLb = new JLabel("Search:");
        searchLb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLb.setForeground(dark);
        searchRow.add(searchLb);
        searchRow.add(accountsSearchTb);
        searchRow.add(clearSearchBtn);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        actions.add(refreshBtn);
        actions.add(toggleBtn);
        actions.add(deleteBtn);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        searchRow.setAlignmentX(0f);
        actions.setAlignmentX(0f);
        top.add(searchRow);
        top.add(Box.createVerticalStrut(10));
        top.add(actions);

        page.add(top, BorderLayout.NORTH);
        page.add(new JScrollPane(accountsTable), BorderLayout.CENTER);
        return page;
    }

    private JPanel buildCreateUserPage() {
        JPanel page = wrapPage();

        JPanel hero = gradientHero(
                "Create Customer Account",
                "Register a new ATM user with full profile details");

        JPanel formCard = new JPanel(new BorderLayout(0, 14));
        formCard.setBackground(white);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, new Color(200, 215, 230)),
                BorderFactory.createEmptyBorder(22, 24, 22, 24)));

        JLabel formTitle = new JLabel("New User Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(dark);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        createAccNumTb = styledInputField();
        createNameTb = styledInputField();
        createFatherTb = styledInputField();
        createDobTb = styledInputField();
        createPinTb = styledInputField();
        createOccupationTb = styledInputField();
        createPhoneTb = styledInputField();
        createAddressTb = styledInputField();
        createBalanceTb = styledInputField();
        createBalanceTb.setText("0");
        createEducationCb = new JComboBox<String>(new String[]{
            "Uneducated", "Primary", "Secondary", "Diploma", "Graduate", "Post Graduate"
        });
        createEducationCb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        createEducationCb.setPreferredSize(new Dimension(220, 42));
        createEducationCb.setBackground(new Color(248, 250, 252));
        createEducationCb.setBorder(new RoundedBorder(8, new Color(190, 205, 220)));

        int r = 0;
        r = addPairCreateRow(form, r, "Account Number", createAccNumTb, "PIN", createPinTb);
        r = addPairCreateRow(form, r, "Full Name", createNameTb, "Education", createEducationCb);
        r = addPairCreateRow(form, r, "Father's Name", createFatherTb, "Occupation", createOccupationTb);
        r = addPairCreateRow(form, r, "DOB (dd/mm/yyyy)", createDobTb, "Phone", createPhoneTb);
        r = addPairCreateRow(form, r, "Opening Balance", createBalanceTb, "Address", createAddressTb);

        JButton createBtn = bigActionButton("Create User Account");
        createBtn.setPreferredSize(new Dimension(200, 46));
        createBtn.setBackground(new Color(0, 150, 80));
        createBtn.setBorder(new RoundedBorder(10, new Color(0, 150, 80)));
        createBtn.addActionListener(e -> createUser());

        JButton clearBtn = bigActionButton("Clear Form");
        clearBtn.setBackground(new Color(90, 110, 140));
        clearBtn.setBorder(new RoundedBorder(10, new Color(90, 110, 140)));
        clearBtn.addActionListener(e -> clearCreateUserForm());

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        btns.setOpaque(false);
        btns.add(createBtn);
        btns.add(clearBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = r;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(18, 0, 0, 0);
        form.add(btns, gbc);

        formCard.add(formTitle, BorderLayout.NORTH);
        formCard.add(form, BorderLayout.CENTER);

        page.add(hero, BorderLayout.NORTH);
        page.add(formCard, BorderLayout.CENTER);
        return page;
    }

    private JPanel gradientHero(String title, String subtitle) {
        JPanel hero = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(6, 42, 90), getWidth(), 0, new Color(0, 102, 204)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        hero.setOpaque(false);
        hero.setPreferredSize(new Dimension(0, 92));
        hero.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JPanel text = new JPanel(new GridLayout(2, 1, 0, 4));
        text.setOpaque(false);
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(white);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        s.setForeground(new Color(180, 220, 255));
        text.add(t);
        text.add(s);
        hero.add(text, BorderLayout.WEST);
        return hero;
    }

    private int addPairCreateRow(JPanel form, int row, String l1, java.awt.Component f1,
            String l2, java.awt.Component f2) {
        GridBagConstraints a = new GridBagConstraints();
        a.gridx = 0;
        a.gridy = row;
        a.anchor = GridBagConstraints.WEST;
        a.insets = new Insets(8, 4, 2, 12);
        JLabel lb1 = new JLabel(l1.toUpperCase());
        lb1.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lb1.setForeground(new Color(100, 115, 135));
        form.add(lb1, a);

        GridBagConstraints b = new GridBagConstraints();
        b.gridx = 0;
        b.gridy = row + 1;
        b.fill = GridBagConstraints.HORIZONTAL;
        b.weightx = 0.5;
        b.insets = new Insets(0, 4, 10, 18);
        f1.setPreferredSize(new Dimension(240, 42));
        form.add(f1, b);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = row;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 4, 2, 12);
        JLabel lb2 = new JLabel(l2.toUpperCase());
        lb2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lb2.setForeground(new Color(100, 115, 135));
        form.add(lb2, c);

        GridBagConstraints d = new GridBagConstraints();
        d.gridx = 1;
        d.gridy = row + 1;
        d.fill = GridBagConstraints.HORIZONTAL;
        d.weightx = 0.5;
        d.insets = new Insets(0, 4, 10, 4);
        f2.setPreferredSize(new Dimension(240, 42));
        form.add(f2, d);
        return row + 2;
    }

    private void clearCreateUserForm() {
        createAccNumTb.setText("");
        createNameTb.setText("");
        createFatherTb.setText("");
        createDobTb.setText("");
        createPinTb.setText("");
        createEducationCb.setSelectedIndex(0);
        createOccupationTb.setText("");
        createPhoneTb.setText("");
        createAddressTb.setText("");
        createBalanceTb.setText("0");
    }

    private JPanel buildProfilePage() {
        JPanel page = wrapPage();

        JPanel hero = gradientHero(
                "My Admin Profile",
                "View and update your admin account details");

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(white);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1),
                BorderFactory.createEmptyBorder(28, 32, 28, 32)));
        card.setMaximumSize(new Dimension(560, Integer.MAX_VALUE));

        JLabel who = new JLabel("Logged in account");
        who.setFont(new Font("Segoe UI", Font.BOLD, 12));
        who.setForeground(new Color(100, 115, 135));
        who.setAlignmentX(0f);

        profileUsernameLb = new JLabel("@" + adminUsername);
        profileUsernameLb.setFont(new Font("Segoe UI", Font.BOLD, 22));
        profileUsernameLb.setForeground(blue);
        profileUsernameLb.setAlignmentX(0f);

        profileUsernameTb = profileTextField(false);
        profileUsernameTb.setText(adminUsername);
        profileUsernameTb.setEditable(false);
        profileUsernameTb.setBackground(new Color(235, 240, 245));

        profileNameTb = profileTextField(true);
        profileNameTb.setText(adminName == null ? "" : adminName);

        profileCurrentPassTb = profilePasswordField();
        profileNewPassTb = profilePasswordField();
        profileConfirmPassTb = profilePasswordField();

        card.add(who);
        card.add(Box.createVerticalStrut(4));
        card.add(profileUsernameLb);
        card.add(Box.createVerticalStrut(18));
        card.add(profileFieldBlock("Username (read only)", profileUsernameTb));
        card.add(Box.createVerticalStrut(12));
        card.add(profileFieldBlock("Full Name", profileNameTb));
        card.add(Box.createVerticalStrut(12));
        card.add(profileFieldBlock("Current Password", profileCurrentPassTb));
        card.add(Box.createVerticalStrut(12));
        card.add(profileFieldBlock("New Password", profileNewPassTb));
        card.add(Box.createVerticalStrut(12));
        card.add(profileFieldBlock("Confirm New Password", profileConfirmPassTb));
        card.add(Box.createVerticalStrut(14));

        JLabel tip = new JLabel("<html>Leave new password empty to update name only."
                + "<br>Current password is required for any change.</html>");
        tip.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tip.setForeground(new Color(110, 120, 135));
        tip.setAlignmentX(0f);
        card.add(tip);
        card.add(Box.createVerticalStrut(16));

        JButton saveBtn = bigActionButton("Save Profile");
        saveBtn.setPreferredSize(new Dimension(160, 44));
        saveBtn.setMaximumSize(new Dimension(160, 44));
        saveBtn.setBackground(new Color(0, 150, 80));
        saveBtn.setBorder(new RoundedBorder(10, new Color(0, 150, 80)));
        saveBtn.addActionListener(e -> saveMyProfile());

        JButton reloadBtn = bigActionButton("Reload");
        reloadBtn.setPreferredSize(new Dimension(120, 44));
        reloadBtn.setMaximumSize(new Dimension(120, 44));
        reloadBtn.setBackground(new Color(90, 110, 140));
        reloadBtn.setBorder(new RoundedBorder(10, new Color(90, 110, 140)));
        reloadBtn.addActionListener(e -> loadMyProfile());

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        actions.setAlignmentX(0f);
        actions.add(saveBtn);
        actions.add(reloadBtn);
        card.add(actions);

        JPanel hold = new JPanel(new BorderLayout());
        hold.setOpaque(false);
        hold.add(card, BorderLayout.NORTH);

        page.add(hero, BorderLayout.NORTH);
        page.add(hold, BorderLayout.CENTER);
        return page;
    }

    private JPanel profileFieldBlock(String label, java.awt.Component field) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setOpaque(false);
        block.setAlignmentX(0f);
        JLabel lb = new JLabel(label.toUpperCase());
        lb.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lb.setForeground(new Color(90, 105, 125));
        lb.setAlignmentX(0f);
        block.add(lb);
        block.add(Box.createVerticalStrut(6));
        if (field instanceof javax.swing.JComponent) {
            ((javax.swing.JComponent) field).setAlignmentX(0f);
        }
        block.add(field);
        return block;
    }

    private JTextField profileTextField(boolean editable) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tf.setForeground(dark);
        tf.setCaretColor(dark);
        tf.setBackground(Color.WHITE);
        tf.setOpaque(true);
        tf.setEditable(editable);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 195), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        tf.setPreferredSize(new Dimension(480, 46));
        tf.setMinimumSize(new Dimension(280, 46));
        return tf;
    }

    private JPasswordField profilePasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        pf.setForeground(dark);
        pf.setCaretColor(dark);
        pf.setBackground(Color.WHITE);
        pf.setOpaque(true);
        pf.setEchoChar('\u2022');
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 195), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        pf.setPreferredSize(new Dimension(480, 46));
        pf.setMinimumSize(new Dimension(280, 46));
        return pf;
    }

    private JPanel buildTransactionsPage() {
        JPanel page = wrapPage();
        transactionsModel = nonEditableModel(new String[]{
            "ID", "Account No", "Type", "Amount", "Balance After", "Date"
        });
        transactionsTable = styledTable(transactionsModel);
        transactionsSorter = new TableRowSorter<DefaultTableModel>(transactionsModel);
        transactionsTable.setRowSorter(transactionsSorter);

        transactionsSearchTb = searchInputField("Search by account no, type, amount, date...");
        wireLiveFilter(transactionsSearchTb, () -> filterTransactionsTable());

        JButton clearSearchBtn = new JButton("Clear");
        stylePrimaryButton(clearSearchBtn);
        clearSearchBtn.addActionListener(e -> {
            transactionsSearchTb.setText("");
            filterTransactionsTable();
        });

        JButton refreshBtn = new JButton("Refresh");
        stylePrimaryButton(refreshBtn);
        refreshBtn.addActionListener(e -> loadAllData());

        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchRow.setOpaque(false);
        JLabel searchLb = new JLabel("Search:");
        searchLb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchLb.setForeground(dark);
        searchRow.add(searchLb);
        searchRow.add(transactionsSearchTb);
        searchRow.add(clearSearchBtn);
        searchRow.add(refreshBtn);

        page.add(searchRow, BorderLayout.NORTH);
        page.add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
        return page;
    }

    private JTextField searchInputField(String placeholder) {
        JTextField tf = new JTextField(28);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(dark);
        tf.setCaretColor(dark);
        tf.setBackground(Color.WHITE);
        tf.setOpaque(true);
        tf.setToolTipText(placeholder);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 195), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        tf.setPreferredSize(new Dimension(320, 38));
        return tf;
    }

    private void wireLiveFilter(JTextField field, Runnable filterAction) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterAction.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterAction.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterAction.run();
            }
        });
    }

    private void filterAccountsTable() {
        if (accountsSorter == null || accountsSearchTb == null) {
            return;
        }
        String q = accountsSearchTb.getText().trim();
        if (q.isEmpty()) {
            accountsSorter.setRowFilter(null);
        } else {
            accountsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(q)));
        }
    }

    private void filterTransactionsTable() {
        if (transactionsSorter == null || transactionsSearchTb == null) {
            return;
        }
        String q = transactionsSearchTb.getText().trim();
        if (q.isEmpty()) {
            transactionsSorter.setRowFilter(null);
        } else {
            transactionsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(q)));
        }
    }

    private JPanel buildReportsPage() {
        JPanel page = wrapPage();

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        reportArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        reportArea.setBackground(white);

        JLabel info = new JLabel("iReport Wizard .jrxml reports (JasperViewer)");
        info.setFont(new Font("Segoe UI", Font.BOLD, 16));
        info.setForeground(dark);

        JButton textBtn = new JButton("Text Summary");
        stylePrimaryButton(textBtn);
        textBtn.addActionListener(e -> loadReports());

        JButton accountsBtn = new JButton("Accounts Report");
        stylePrimaryButton(accountsBtn);
        accountsBtn.addActionListener(e -> JasperReportUtil.showReport(
                this, "src/main/resources/reports/AccountsReport.jrxml"));

        JButton txBtn = new JButton("Transactions Report");
        stylePrimaryButton(txBtn);
        txBtn.addActionListener(e -> JasperReportUtil.showReport(
                this, "src/main/resources/reports/TransactionsReport.jrxml"));

        JButton depositsBtn = new JButton("Deposits Report");
        stylePrimaryButton(depositsBtn);
        depositsBtn.addActionListener(e -> JasperReportUtil.showReport(
                this, "src/main/resources/reports/DepositsReport.jrxml"));

        JButton withdrawsBtn = new JButton("Withdrawals Report");
        stylePrimaryButton(withdrawsBtn);
        withdrawsBtn.addActionListener(e -> JasperReportUtil.showReport(
                this, "src/main/resources/reports/WithdrawsReport.jrxml"));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setOpaque(false);
        top.add(info);
        top.add(textBtn);
        top.add(accountsBtn);
        top.add(txBtn);
        top.add(depositsBtn);
        top.add(withdrawsBtn);

        page.add(top, BorderLayout.NORTH);
        page.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return page;
    }

    private JPanel buildSearchPage() {
        JPanel page = wrapPage();
        searchModel = nonEditableModel(new String[]{
            "Account No", "Full Name", "PIN", "Balance", "Active"
        });
        searchTable = styledTable(searchModel);

        searchTb = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        stylePrimaryButton(searchBtn);
        searchBtn.addActionListener(e -> searchAccounts());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);
        top.add(new JLabel("Account No or Name:"));
        top.add(searchTb);
        top.add(searchBtn);

        page.add(top, BorderLayout.NORTH);
        page.add(new JScrollPane(searchTable), BorderLayout.CENTER);
        return page;
    }

    private JPanel buildAdjustBalancePage() {
        JPanel page = wrapPage();
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(white);
        form.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        adjustAccTb = new JTextField(18);
        adjustAmountTb = new JTextField(18);

        int row = 0;
        row = addFormRow(form, row, "Account Number", adjustAccTb);
        row = addFormRow(form, row, "Amount (+ credit / - debit)", adjustAmountTb);

        JButton applyBtn = new JButton("Apply Adjustment");
        stylePrimaryButton(applyBtn);
        applyBtn.addActionListener(e -> adjustBalance());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(16, 8, 8, 8);
        form.add(applyBtn, gbc);

        JLabel note = new JLabel("Example: 500 adds money, -200 withdraws money.");
        note.setForeground(new Color(100, 100, 100));
        GridBagConstraints noteGbc = new GridBagConstraints();
        noteGbc.gridx = 1;
        noteGbc.gridy = row + 1;
        noteGbc.anchor = GridBagConstraints.WEST;
        noteGbc.insets = new Insets(8, 8, 8, 8);
        form.add(note, noteGbc);

        page.add(form, BorderLayout.NORTH);
        return page;
    }

    private JPanel buildAdminsPage() {
        JPanel page = wrapPage();

        JPanel hero = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(6, 42, 90), getWidth(), 0, new Color(0, 102, 204)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        hero.setOpaque(false);
        hero.setPreferredSize(new Dimension(0, 100));
        hero.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JPanel heroText = new JPanel(new GridLayout(2, 1, 0, 4));
        heroText.setOpaque(false);
        JLabel heroTitle = new JLabel("Administrator Control");
        heroTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heroTitle.setForeground(white);
        JLabel heroSub = new JLabel("Create, activate, and manage system admin accounts");
        heroSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        heroSub.setForeground(new Color(180, 220, 255));
        heroText.add(heroTitle);
        heroText.add(heroSub);

        adminCountLb = new JLabel("0 Admins");
        adminCountLb.setFont(new Font("Segoe UI", Font.BOLD, 18));
        adminCountLb.setForeground(white);
        adminCountLb.setHorizontalAlignment(SwingConstants.RIGHT);

        hero.add(heroText, BorderLayout.WEST);
        hero.add(adminCountLb, BorderLayout.EAST);

        JPanel listCard = new JPanel(new BorderLayout(0, 12));
        listCard.setBackground(white);
        listCard.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, new Color(200, 215, 230)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel listTitle = new JLabel("Registered Admins");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        listTitle.setForeground(dark);

        adminsModel = nonEditableModel(new String[]{"ID", "Username", "Full Name", "Active"});
        adminsTable = styledTable(adminsModel);
        adminsTable.setRowHeight(36);

        JButton addNewAdminBtn = bigActionButton("Add New Admin");
        addNewAdminBtn.setPreferredSize(new Dimension(180, 44));
        addNewAdminBtn.setBackground(new Color(0, 150, 80));
        addNewAdminBtn.setBorder(new RoundedBorder(10, new Color(0, 150, 80)));
        addNewAdminBtn.addActionListener(e -> openAddAdminModal());

        JButton refreshBtn = bigActionButton("Refresh");
        JButton toggleBtn = bigActionButton("Activate / Deactivate");
        JButton deleteBtn = bigActionButton("Delete Admin");
        deleteBtn.setBackground(new Color(190, 55, 55));
        deleteBtn.setBorder(new RoundedBorder(10, new Color(190, 55, 55)));
        refreshBtn.addActionListener(e -> loadAdmins());
        toggleBtn.addActionListener(e -> toggleSelectedAdmin());
        deleteBtn.addActionListener(e -> deleteSelectedAdmin());

        JPanel listActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        listActions.setOpaque(false);
        listActions.add(addNewAdminBtn);
        listActions.add(refreshBtn);
        listActions.add(toggleBtn);
        listActions.add(deleteBtn);

        JPanel listNorth = new JPanel(new BorderLayout(0, 10));
        listNorth.setOpaque(false);
        listNorth.add(listTitle, BorderLayout.NORTH);
        listNorth.add(listActions, BorderLayout.SOUTH);

        listCard.add(listNorth, BorderLayout.NORTH);
        listCard.add(new JScrollPane(adminsTable), BorderLayout.CENTER);

        page.add(hero, BorderLayout.NORTH);
        page.add(listCard, BorderLayout.CENTER);
        return page;
    }

    private void openAddAdminModal() {
        final JDialog dialog = new JDialog(this, "Add New Admin", true);
        dialog.setSize(460, 480);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, new Color(6, 42, 90), getWidth(), 0, new Color(0, 102, 204)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 70));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel headerTitle = new JLabel("Add New Admin");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerTitle.setForeground(white);
        JLabel headerSub = new JLabel("Fill the form to create a new administrator");
        headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        headerSub.setForeground(new Color(180, 220, 255));
        JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 2));
        headerText.setOpaque(false);
        headerText.add(headerTitle);
        headerText.add(headerSub);
        header.add(headerText, BorderLayout.WEST);

        JPanel body = new JPanel(new BorderLayout(0, 12));
        body.setBackground(white);
        body.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));

        JLabel hint = new JLabel("<html>New admins can sign in from the shared login page<br>using their username and password.</html>");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(new Color(110, 120, 135));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        adminUserTb = styledInputField();
        adminNameTb = styledInputField();
        adminPassTb = new JPasswordField(18);
        styleInputComponent(adminPassTb);
        adminUserTb.setPreferredSize(new Dimension(360, 38));
        adminNameTb.setPreferredSize(new Dimension(360, 38));
        adminPassTb.setPreferredSize(new Dimension(360, 38));

        int row = 0;
        row = addAdminFormRow(form, row, "Username", adminUserTb);
        row = addAdminFormRow(form, row, "Full Name", adminNameTb);
        row = addAdminFormRow(form, row, "Password", adminPassTb);

        body.add(hint, BorderLayout.NORTH);
        body.add(form, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        footer.setBackground(new Color(245, 248, 252));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 228, 238)));

        JButton cancelBtn = new JButton("Cancel");
        stylePrimaryButton(cancelBtn);
        cancelBtn.setBackground(new Color(110, 125, 145));
        cancelBtn.setBorder(new RoundedBorder(10, new Color(110, 125, 145)));
        cancelBtn.setPreferredSize(new Dimension(110, 40));
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton saveBtn = bigActionButton("Save Admin");
        saveBtn.setPreferredSize(new Dimension(140, 40));
        saveBtn.setBackground(new Color(0, 150, 80));
        saveBtn.setBorder(new RoundedBorder(10, new Color(0, 150, 80)));
        saveBtn.addActionListener(e -> {
            if (createAdmin()) {
                dialog.dispose();
            }
        });

        footer.add(cancelBtn);
        footer.add(saveBtn);

        dialog.add(header, BorderLayout.NORTH);
        dialog.add(body, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JTextField styledInputField() {
        JTextField tf = new JTextField(18);
        styleInputComponent(tf);
        return tf;
    }

    private void styleInputComponent(javax.swing.JComponent field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(dark);
        field.setBackground(Color.WHITE);
        field.setOpaque(true);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 195), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(220, 42));
        if (field instanceof javax.swing.JTextField) {
            javax.swing.JTextField tf = (javax.swing.JTextField) field;
            tf.setCaretColor(dark);
            tf.setMargin(new Insets(2, 4, 2, 4));
        }
    }

    private int addAdminFormRow(JPanel form, int row, String label, java.awt.Component field) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.insets = new Insets(10, 0, 4, 0);
        JLabel lb = new JLabel(label.toUpperCase());
        lb.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lb.setForeground(new Color(100, 115, 135));
        form.add(lb, labelGbc);

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 0;
        fieldGbc.gridy = row + 1;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.weightx = 1;
        fieldGbc.insets = new Insets(0, 0, 6, 0);
        form.add(field, fieldGbc);
        return row + 2;
    }

    private JPanel wrapPage() {
        JPanel page = new JPanel(new BorderLayout(0, 14));
        page.setBackground(pageBg);
        page.setBorder(BorderFactory.createEmptyBorder(8, 28, 28, 28));
        return page;
    }

    private int addFormRow(JPanel form, int row, String label, java.awt.Component field) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row;
        labelGbc.anchor = GridBagConstraints.EAST;
        labelGbc.insets = new Insets(8, 8, 8, 8);
        JLabel lb = new JLabel(label + ":");
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(lb, labelGbc);

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 1;
        fieldGbc.gridy = row;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.insets = new Insets(8, 8, 8, 8);
        form.add(field, fieldGbc);
        return row + 1;
    }

    private DefaultTableModel nonEditableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(210, 230, 255));
        return table;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(12, 36, 72));
        footer.setPreferredSize(new Dimension(0, 42));
        JLabel brand = new JLabel("WORLD BANK  •  Secure ATM Administration");
        brand.setForeground(white);
        brand.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        footer.add(brand);
        return footer;
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(blue);
        btn.setForeground(white);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new RoundedBorder(10, blue));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadAllData() {
        if (accountsModel != null) {
            accountsModel.setRowCount(0);
        }
        if (transactionsModel != null) {
            transactionsModel.setRowCount(0);
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            Statement st = con.createStatement();

            ResultSet rsAccounts = st.executeQuery(
                    "SELECT AccountNumber, FullName, Pin, Balance, IsActive, CreatedAt FROM Accounts ORDER BY AccountNumber");
            int accountCount = 0;
            int activeCount = 0;
            long balanceSum = 0;
            while (rsAccounts.next()) {
                accountCount++;
                balanceSum += rsAccounts.getLong("Balance");
                boolean active = rsAccounts.getBoolean("IsActive");
                if (active) {
                    activeCount++;
                }
                accountsModel.addRow(new Object[]{
                    rsAccounts.getInt("AccountNumber"),
                    rsAccounts.getString("FullName"),
                    rsAccounts.getInt("Pin"),
                    rsAccounts.getInt("Balance"),
                    active ? "Yes" : "No",
                    rsAccounts.getTimestamp("CreatedAt")
                });
            }
            rsAccounts.close();

            ResultSet rsTx = st.executeQuery(
                    "SELECT TransactionID, AccountNumber, TransactionType, Amount, BalanceAfter, TransactionDate "
                    + "FROM Transactions ORDER BY TransactionDate DESC");
            int txCount = 0;
            while (rsTx.next()) {
                txCount++;
                transactionsModel.addRow(new Object[]{
                    rsTx.getInt("TransactionID"),
                    rsTx.getInt("AccountNumber"),
                    rsTx.getString("TransactionType"),
                    rsTx.getInt("Amount"),
                    rsTx.getInt("BalanceAfter"),
                    rsTx.getTimestamp("TransactionDate")
                });
            }
            rsTx.close();

            long depositSum = 0;
            ResultSet rsDep = st.executeQuery("SELECT COALESCE(SUM(Amount),0) AS s FROM Deposits");
            if (rsDep.next()) {
                depositSum = rsDep.getLong("s");
            }
            rsDep.close();

            long withdrawSum = 0;
            ResultSet rsW = st.executeQuery("SELECT COALESCE(SUM(Amount),0) AS s FROM Withdraws");
            if (rsW.next()) {
                withdrawSum = rsW.getLong("s");
            }
            rsW.close();

            st.close();
            con.close();

            int inactiveCount = accountCount - activeCount;
            totalAccountsLb.setText(String.valueOf(accountCount));
            totalBalanceLb.setText(String.format("%,d", balanceSum));
            activeAccountsLb.setText(String.valueOf(activeCount));
            inactiveAccountsLb.setText(String.valueOf(inactiveCount));
            totalTxLb.setText(String.valueOf(txCount));
            totalDepositsLb.setText(String.format("%,d", depositSum));
            totalWithdrawsLb.setText(String.format("%,d", withdrawSum));

            refreshCharts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load admin data: " + e.getMessage());
        }
    }

    private void refreshCharts() {
        if (statusChartHost == null || moneyChartHost == null || topAccountsChartHost == null) {
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            Statement st = con.createStatement();

            int active = 0;
            int inactive = 0;
            ResultSet rsStatus = st.executeQuery(
                    "SELECT SUM(CASE WHEN IsActive=1 THEN 1 ELSE 0 END) AS a, "
                    + "SUM(CASE WHEN IsActive=0 THEN 1 ELSE 0 END) AS i FROM Accounts");
            if (rsStatus.next()) {
                active = rsStatus.getInt("a");
                inactive = rsStatus.getInt("i");
            }
            rsStatus.close();

            long deposits = 0;
            long withdraws = 0;
            ResultSet rsDep = st.executeQuery("SELECT COALESCE(SUM(Amount),0) AS s FROM Deposits");
            if (rsDep.next()) {
                deposits = rsDep.getLong("s");
            }
            rsDep.close();
            ResultSet rsW = st.executeQuery("SELECT COALESCE(SUM(Amount),0) AS s FROM Withdraws");
            if (rsW.next()) {
                withdraws = rsW.getLong("s");
            }
            rsW.close();

            DefaultCategoryDataset topDataset = new DefaultCategoryDataset();
            ResultSet rsTop = st.executeQuery(
                    "SELECT FullName, Balance FROM Accounts ORDER BY Balance DESC LIMIT 5");
            boolean anyTop = false;
            while (rsTop.next()) {
                anyTop = true;
                String name = rsTop.getString("FullName");
                if (name == null || name.trim().isEmpty()) {
                    name = "Account";
                }
                if (name.length() > 12) {
                    name = name.substring(0, 12) + "...";
                }
                topDataset.addValue(rsTop.getInt("Balance"), "Balance", name);
            }
            rsTop.close();
            if (!anyTop) {
                topDataset.addValue(0, "Balance", "No data");
            }

            st.close();
            con.close();

            // Pie: account status
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            pieDataset.setValue("Active", Math.max(active, 0));
            pieDataset.setValue("Inactive", Math.max(inactive, 0));
            if (active == 0 && inactive == 0) {
                pieDataset.setValue("No Accounts", 1);
            }
            JFreeChart pieChart = ChartFactory.createPieChart("Account Status", pieDataset, true, true, false);
            pieChart.setBackgroundPaint(white);
            PiePlot piePlot = (PiePlot) pieChart.getPlot();
            piePlot.setBackgroundPaint(white);
            piePlot.setOutlineVisible(false);
            piePlot.setSectionPaint("Active", new Color(46, 160, 90));
            piePlot.setSectionPaint("Inactive", accentOrange);
            piePlot.setSectionPaint("No Accounts", new Color(180, 180, 180));
            piePlot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
            pieChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
            setChartInHost(statusChartHost, pieChart, "Account Status");

            // Bar: deposits vs withdrawals
            DefaultCategoryDataset moneyDataset = new DefaultCategoryDataset();
            moneyDataset.addValue(deposits, "Amount", "Deposits");
            moneyDataset.addValue(withdraws, "Amount", "Withdrawals");
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Cash Flow", "Type", "Amount", moneyDataset,
                    PlotOrientation.VERTICAL, false, true, false);
            styleBarChart(barChart, blue);
            setChartInHost(moneyChartHost, barChart, "Deposits vs Withdrawals");

            // Bar: top accounts
            JFreeChart topChart = ChartFactory.createBarChart(
                    "Top Balances", "Account", "Balance", topDataset,
                    PlotOrientation.VERTICAL, false, true, false);
            styleBarChart(topChart, accentTeal);
            setChartInHost(topAccountsChartHost, topChart, "Top Accounts by Balance");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleBarChart(JFreeChart chart, Color barColor) {
        chart.setBackgroundPaint(white);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(248, 250, 252));
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(210, 220, 230));
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, barColor);
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.25);
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
    }

    private void setChartInHost(JPanel host, JFreeChart chart, String title) {
        host.removeAll();
        JLabel lb = new JLabel(title, SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lb.setForeground(dark);
        host.add(lb, BorderLayout.NORTH);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false);
        chartPanel.setPreferredSize(new Dimension(380, 340));
        host.add(chartPanel, BorderLayout.CENTER);
        host.revalidate();
        host.repaint();
    }

    private void loadReports() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("============================================\n");
        report.append("       WORLD BANK ATM - ADMIN REPORT\n");
        report.append("============================================\n\n");

        try {
            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery(
                    "SELECT COUNT(*) AS c, COALESCE(SUM(Balance),0) AS bal, "
                    + "SUM(CASE WHEN IsActive=1 THEN 1 ELSE 0 END) AS activeC, "
                    + "SUM(CASE WHEN IsActive=0 THEN 1 ELSE 0 END) AS inactiveC FROM Accounts");
            if (rs1.next()) {
                report.append("ACCOUNTS SUMMARY\n");
                report.append("----------------\n");
                report.append(String.format("Total Accounts     : %d%n", rs1.getInt("c")));
                report.append(String.format("Active Accounts    : %d%n", rs1.getInt("activeC")));
                report.append(String.format("Inactive Accounts  : %d%n", rs1.getInt("inactiveC")));
                report.append(String.format("Total Balance      : %d%n%n", rs1.getLong("bal")));
            }
            rs1.close();

            ResultSet rs2 = st.executeQuery(
                    "SELECT COALESCE(SUM(Amount),0) AS totalDep, COUNT(*) AS cnt FROM Deposits");
            if (rs2.next()) {
                report.append("DEPOSITS\n");
                report.append("--------\n");
                report.append(String.format("Deposit Count      : %d%n", rs2.getInt("cnt")));
                report.append(String.format("Total Deposited    : %d%n%n", rs2.getLong("totalDep")));
            }
            rs2.close();

            ResultSet rs3 = st.executeQuery(
                    "SELECT COALESCE(SUM(Amount),0) AS totalW, COUNT(*) AS cnt FROM Withdraws");
            if (rs3.next()) {
                report.append("WITHDRAWALS\n");
                report.append("-----------\n");
                report.append(String.format("Withdraw Count     : %d%n", rs3.getInt("cnt")));
                report.append(String.format("Total Withdrawn    : %d%n%n", rs3.getLong("totalW")));
            }
            rs3.close();

            ResultSet rs4 = st.executeQuery(
                    "SELECT TransactionType, COUNT(*) AS cnt, COALESCE(SUM(Amount),0) AS totalAmt "
                    + "FROM Transactions GROUP BY TransactionType");
            report.append("TRANSACTIONS BY TYPE\n");
            report.append("--------------------\n");
            boolean anyTx = false;
            while (rs4.next()) {
                anyTx = true;
                report.append(String.format("%-12s Count: %-5d Amount: %d%n",
                        rs4.getString("TransactionType"),
                        rs4.getInt("cnt"),
                        rs4.getLong("totalAmt")));
            }
            if (!anyTx) {
                report.append("No transactions yet.\n");
            }
            report.append("\n");
            rs4.close();

            ResultSet rs5 = st.executeQuery(
                    "SELECT AccountNumber, FullName, Balance FROM Accounts ORDER BY Balance DESC LIMIT 5");
            report.append("TOP 5 ACCOUNTS BY BALANCE\n");
            report.append("-------------------------\n");
            while (rs5.next()) {
                report.append(String.format("Acc %-10d %-20s Balance: %d%n",
                        rs5.getInt("AccountNumber"),
                        rs5.getString("FullName"),
                        rs5.getInt("Balance")));
            }
            rs5.close();

            report.append("\n============================================\n");
            report.append("Generated for admin: ").append(adminName).append("\n");
            report.append("============================================\n");

            st.close();
            con.close();
            reportArea.setText(report.toString());
            reportArea.setCaretPosition(0);
        } catch (Exception e) {
            reportArea.setText("Failed to generate report: " + e.getMessage());
        }
    }

    private void createUser() {
        String accText = createAccNumTb.getText().trim();
        String name = createNameTb.getText().trim();
        String father = createFatherTb.getText().trim();
        String dob = createDobTb.getText().trim();
        String pinText = createPinTb.getText().trim();
        String education = String.valueOf(createEducationCb.getSelectedItem());
        String occupation = createOccupationTb.getText().trim();
        String phone = createPhoneTb.getText().trim();
        String address = createAddressTb.getText().trim();
        String balText = createBalanceTb.getText().trim();

        if (accText.isEmpty() || name.isEmpty() || father.isEmpty() || dob.isEmpty()
                || pinText.isEmpty() || occupation.isEmpty() || phone.isEmpty()
                || address.isEmpty() || balText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        int accountNumber;
        int pin;
        int balance;
        try {
            accountNumber = Integer.parseInt(accText);
            pin = Integer.parseInt(pinText);
            balance = Integer.parseInt(balText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Account Number, PIN and Balance must be numbers.");
            return;
        }

        if (balance < 0) {
            JOptionPane.showMessageDialog(this, "Opening balance cannot be negative.");
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
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
                }
            }

            PreparedStatement check = con.prepareStatement(
                    "SELECT AccountNumber FROM Accounts WHERE AccountNumber = ?");
            check.setInt(1, accountNumber);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                rs.close();
                check.close();
                JOptionPane.showMessageDialog(this, "Account number already exists.");
                return;
            }
            rs.close();
            check.close();

            PreparedStatement insert = con.prepareStatement(
                    "INSERT INTO Accounts "
                    + "(AccountNumber, FullName, FatherName, DOB, Pin, Education, Occupation, Phone, Address, Balance, IsActive) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)");
            insert.setInt(1, accountNumber);
            insert.setString(2, name);
            insert.setString(3, father);
            insert.setString(4, dob);
            insert.setInt(5, pin);
            insert.setString(6, education);
            insert.setString(7, occupation);
            insert.setString(8, phone);
            insert.setString(9, address);
            insert.setInt(10, balance);
            insert.executeUpdate();
            insert.close();
            con.close();

            JOptionPane.showMessageDialog(this, "User account created successfully.");
            clearCreateUserForm();
            loadAllData();
            showPage("accounts");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Create user failed: " + e.getMessage());
        }
    }

    private void loadMyProfile() {
        if (profileNameTb == null) {
            return;
        }
        if (profileUsernameLb != null) {
            profileUsernameLb.setText("@" + adminUsername);
        }
        if (profileUsernameTb != null) {
            profileUsernameTb.setText(adminUsername);
        }
        profileNameTb.setText(adminName == null ? "" : adminName);
        profileCurrentPassTb.setText("");
        profileNewPassTb.setText("");
        profileConfirmPassTb.setText("");

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT Username, FullName FROM Admins WHERE Username = ?");
            ps.setString(1, adminUsername);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                adminName = rs.getString("FullName");
                if (adminName == null) {
                    adminName = "";
                }
                profileNameTb.setText(adminName);
                if (profileUsernameTb != null) {
                    profileUsernameTb.setText(rs.getString("Username"));
                }
                if (profileUsernameLb != null) {
                    profileUsernameLb.setText("@" + adminUsername);
                }
                if (headerSubtitleLb != null) {
                    headerSubtitleLb.setText("Signed in as " + adminName
                            + " (@" + adminUsername + ")  •  Live banking operations dashboard");
                }
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load profile: " + e.getMessage());
        }
    }

    private void saveMyProfile() {
        String fullName = profileNameTb.getText().trim();
        String currentPass = new String(profileCurrentPassTb.getPassword()).trim();
        String newPass = new String(profileNewPassTb.getPassword()).trim();
        String confirmPass = new String(profileConfirmPassTb.getPassword()).trim();

        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full name is required.");
            return;
        }
        if (currentPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your current password to save changes.");
            return;
        }
        if (!newPass.isEmpty()) {
            if (newPass.length() < 4) {
                JOptionPane.showMessageDialog(this, "New password must be at least 4 characters.");
                return;
            }
            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "New password and confirm password do not match.");
                return;
            }
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            PreparedStatement check = con.prepareStatement(
                    "SELECT AdminID FROM Admins WHERE Username = ? AND Password = ? AND IsActive = 1");
            check.setString(1, adminUsername);
            check.setString(2, currentPass);
            ResultSet rs = check.executeQuery();
            if (!rs.next()) {
                rs.close();
                check.close();
                JOptionPane.showMessageDialog(this, "Current password is incorrect.");
                return;
            }
            rs.close();
            check.close();

            PreparedStatement upd;
            if (newPass.isEmpty()) {
                upd = con.prepareStatement("UPDATE Admins SET FullName = ? WHERE Username = ?");
                upd.setString(1, fullName);
                upd.setString(2, adminUsername);
            } else {
                upd = con.prepareStatement(
                        "UPDATE Admins SET FullName = ?, Password = ? WHERE Username = ?");
                upd.setString(1, fullName);
                upd.setString(2, newPass);
                upd.setString(3, adminUsername);
            }
            upd.executeUpdate();
            upd.close();
            con.close();

            adminName = fullName;
            if (headerSubtitleLb != null) {
                headerSubtitleLb.setText("Signed in as " + adminName
                        + " (@" + adminUsername + ")  •  Live banking operations dashboard");
            }
            profileCurrentPassTb.setText("");
            profileNewPassTb.setText("");
            profileConfirmPassTb.setText("");
            JOptionPane.showMessageDialog(this, "Profile updated successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update profile failed: " + e.getMessage());
        }
    }

    private void searchAccounts() {
        String q = searchTb.getText().trim();
        searchModel.setRowCount(0);
        if (q.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter account number or name to search.");
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT AccountNumber, FullName, Pin, Balance, IsActive FROM Accounts "
                    + "WHERE CAST(AccountNumber AS CHAR) LIKE ? OR FullName LIKE ?");
            String like = "%" + q + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                searchModel.addRow(new Object[]{
                    rs.getInt("AccountNumber"),
                    rs.getString("FullName"),
                    rs.getInt("Pin"),
                    rs.getInt("Balance"),
                    rs.getBoolean("IsActive") ? "Yes" : "No"
                });
            }
            rs.close();
            ps.close();
            con.close();
            if (searchModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No accounts found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search failed: " + e.getMessage());
        }
    }

    private void adjustBalance() {
        String accText = adjustAccTb.getText().trim();
        String amountText = adjustAmountTb.getText().trim();
        if (accText.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter account number and amount.");
            return;
        }

        int accountNumber;
        int amount;
        try {
            accountNumber = Integer.parseInt(accText);
            amount = Integer.parseInt(amountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Account number and amount must be numbers.");
            return;
        }

        if (amount == 0) {
            JOptionPane.showMessageDialog(this, "Amount cannot be zero.");
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        try {
            con.setAutoCommit(false);
            PreparedStatement getBal = con.prepareStatement(
                    "SELECT Balance FROM Accounts WHERE AccountNumber = ? FOR UPDATE");
            getBal.setInt(1, accountNumber);
            ResultSet rs = getBal.executeQuery();
            if (!rs.next()) {
                rs.close();
                getBal.close();
                con.rollback();
                JOptionPane.showMessageDialog(this, "Account not found.");
                return;
            }
            int oldBalance = rs.getInt("Balance");
            rs.close();
            getBal.close();

            int newBalance = oldBalance + amount;
            if (newBalance < 0) {
                con.rollback();
                JOptionPane.showMessageDialog(this, "Adjustment would make balance negative.");
                return;
            }

            PreparedStatement upd = con.prepareStatement(
                    "UPDATE Accounts SET Balance = ? WHERE AccountNumber = ?");
            upd.setInt(1, newBalance);
            upd.setInt(2, accountNumber);
            upd.executeUpdate();
            upd.close();

            PreparedStatement tx = con.prepareStatement(
                    "INSERT INTO Transactions (AccountNumber, TransactionType, Amount, BalanceAfter) VALUES (?, ?, ?, ?)");
            tx.setInt(1, accountNumber);
            tx.setString(2, amount > 0 ? "AdminCredit" : "AdminDebit");
            tx.setInt(3, Math.abs(amount));
            tx.setInt(4, newBalance);
            tx.executeUpdate();
            tx.close();

            con.commit();
            con.close();
            JOptionPane.showMessageDialog(this,
                    "Balance updated.\nOld: " + oldBalance + "\nNew: " + newBalance);
            adjustAccTb.setText("");
            adjustAmountTb.setText("");
            loadAllData();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            JOptionPane.showMessageDialog(this, "Adjustment failed: " + e.getMessage());
        }
    }

    private boolean createAdmin() {
        if (adminUserTb == null || adminNameTb == null || adminPassTb == null) {
            JOptionPane.showMessageDialog(this, "Open Add New Admin first.");
            return false;
        }

        String username = adminUserTb.getText().trim();
        String fullName = adminNameTb.getText().trim();
        String password = new String(adminPassTb.getPassword()).trim();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all admin fields.");
            return false;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters.");
            return false;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return false;
        }

        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Admins (Username, Password, FullName, IsActive) VALUES (?, ?, ?, 1)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.executeUpdate();
            ps.close();
            con.close();
            JOptionPane.showMessageDialog(this, "Admin created successfully.\nThey can login with username/password.");
            adminUserTb.setText("");
            adminNameTb.setText("");
            adminPassTb.setText("");
            loadAdmins();
            return true;
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Admin username already exists.");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Create admin failed: " + e.getMessage());
            return false;
        }
    }

    private void loadAdmins() {
        if (adminsModel == null) {
            return;
        }
        adminsModel.setRowCount(0);
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT AdminID, Username, FullName, IsActive FROM Admins ORDER BY AdminID");
            int count = 0;
            while (rs.next()) {
                count++;
                adminsModel.addRow(new Object[]{
                    rs.getInt("AdminID"),
                    rs.getString("Username"),
                    rs.getString("FullName"),
                    rs.getBoolean("IsActive") ? "Yes" : "No"
                });
            }
            rs.close();
            st.close();
            con.close();
            if (adminCountLb != null) {
                adminCountLb.setText(count + (count == 1 ? " Admin" : " Admins"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load admins: " + e.getMessage());
        }
    }

    private void toggleSelectedAdmin() {
        int row = adminsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an admin first.");
            return;
        }
        int adminId = (Integer) adminsModel.getValueAt(row, 0);
        String username = String.valueOf(adminsModel.getValueAt(row, 1));
        boolean currentlyActive = "Yes".equals(adminsModel.getValueAt(row, 3));
        int newStatus = currentlyActive ? 0 : 1;

        if (username.equalsIgnoreCase("admin") && newStatus == 0) {
            JOptionPane.showMessageDialog(this, "The default 'admin' account cannot be deactivated.");
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Admins SET IsActive = ? WHERE AdminID = ?");
            ps.setInt(1, newStatus);
            ps.setInt(2, adminId);
            ps.executeUpdate();
            ps.close();
            con.close();
            loadAdmins();
            JOptionPane.showMessageDialog(this,
                    "Admin '" + username + "' is now " + (newStatus == 1 ? "active" : "inactive") + ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update failed: " + e.getMessage());
        }
    }

    private void deleteSelectedAdmin() {
        int row = adminsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an admin first.");
            return;
        }
        int adminId = (Integer) adminsModel.getValueAt(row, 0);
        String username = String.valueOf(adminsModel.getValueAt(row, 1));

        if (username.equalsIgnoreCase("admin")) {
            JOptionPane.showMessageDialog(this, "The default 'admin' account cannot be deleted.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete admin '" + username + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Admins WHERE AdminID = ?");
            ps.setInt(1, adminId);
            ps.executeUpdate();
            ps.close();
            con.close();
            loadAdmins();
            JOptionPane.showMessageDialog(this, "Admin deleted.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + e.getMessage());
        }
    }

    private void toggleSelectedAccount() {
        int viewRow = accountsTable.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an account first.");
            return;
        }
        int row = accountsTable.convertRowIndexToModel(viewRow);

        int accountNumber = (Integer) accountsModel.getValueAt(row, 0);
        boolean currentlyActive = "Yes".equals(accountsModel.getValueAt(row, 4));
        int newStatus = currentlyActive ? 0 : 1;

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Accounts SET IsActive = ? WHERE AccountNumber = ?");
            ps.setInt(1, newStatus);
            ps.setInt(2, accountNumber);
            ps.executeUpdate();
            ps.close();
            con.close();
            loadAllData();
            JOptionPane.showMessageDialog(this,
                    "Account " + accountNumber + " is now " + (newStatus == 1 ? "active" : "inactive") + ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update failed: " + e.getMessage());
        }
    }

    private void deleteSelectedAccount() {
        int viewRow = accountsTable.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Select an account first.");
            return;
        }
        int row = accountsTable.convertRowIndexToModel(viewRow);

        int accountNumber = (Integer) accountsModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete account " + accountNumber + " and related records?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }
        try {
            con.setAutoCommit(false);
            PreparedStatement[] stmts = new PreparedStatement[]{
                con.prepareStatement("DELETE FROM Transactions WHERE AccountNumber = ?"),
                con.prepareStatement("DELETE FROM Deposits WHERE AccountNumber = ?"),
                con.prepareStatement("DELETE FROM Withdraws WHERE AccountNumber = ?"),
                con.prepareStatement("DELETE FROM Accounts WHERE AccountNumber = ?")
            };
            for (PreparedStatement ps : stmts) {
                ps.setInt(1, accountNumber);
                ps.executeUpdate();
                ps.close();
            }
            con.commit();
            con.close();
            loadAllData();
            JOptionPane.showMessageDialog(this, "Account deleted.");
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {
            }
            JOptionPane.showMessageDialog(this, "Delete failed: " + e.getMessage());
        }
    }
}
