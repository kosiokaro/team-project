package view;

import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomepageView extends JPanel implements PropertyChangeListener {
    public final String viewName = "HOMEPAGE";
    private HomeViewModel homeViewModel;
    private String username;
    private JLabel titleLabel;
    private JLabel welcomeLabel;

    private JButton browseButton;
    private JButton viewButton;
    private JButton favoritesButton;
    private JButton logoutButton;

    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(139, 92, 246);
    private static final Color BACKGROUND_COLOR = new Color(17, 24, 39);
    private static final Color CARD_COLOR = new Color(31, 41, 55);
    private static final Color TEXT_COLOR = new Color(243, 244, 246);
    private static final Color ACCENT_COLOR = new Color(147, 51, 234);

    public HomepageView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);
        this.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Top Panel with gradient effect
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Title
        titleLabel = new JLabel("Your Watchlist Hub", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        welcomePanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        welcomeLabel = new JLabel("Hello, " + username + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.WEST);

        logoutButton = createStyledButton("Logout", new Color(239, 68, 68), new Color(220, 38, 38));
        logoutButton.setPreferredSize(new Dimension(100, 35));
        welcomePanel.add(logoutButton, BorderLayout.EAST);

        topPanel.add(welcomePanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(40, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create styled buttons
        browseButton = createMainButton("Browse Titles", "Discover new movies and shows");
        viewButton = createMainButton("View Watchlist", "See your saved titles");
        favoritesButton = createMainButton("View Favorites", "Access your favorite picks");

        contentPanel.add(browseButton, gbc);
        gbc.gridy = 1;
        contentPanel.add(viewButton, gbc);
        gbc.gridy = 2;
        contentPanel.add(favoritesButton, gbc);

        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createMainButton(String text, String subtitle) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(45, 55, 72));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2d.setColor(PRIMARY_COLOR);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                } else {
                    g2d.setColor(CARD_COLOR);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2d.setColor(new Color(55, 65, 81));
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }

                g2d.dispose();

                // Draw text
                super.paintComponent(g);
            }
        };

        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(500, 130));
        button.setMaximumSize(new Dimension(500, 130));
        button.setMinimumSize(new Dimension(500, 130));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel mainLabel = new JLabel(text);
        mainLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        mainLabel.setForeground(TEXT_COLOR);

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subLabel.setForeground(new Color(156, 163, 175));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        mainLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(mainLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(subLabel);

        button.add(textPanel, BorderLayout.CENTER);

        return button;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 20, 8, 20));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    public void setBrowseButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }

    public void setWatchlistButtonListener(ActionListener listener) {
        viewButton.addActionListener(listener);
    }

    public void setFavoritesButtonListener(ActionListener listener) {
        favoritesButton.addActionListener(listener);
    }

    public void setLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public void setUsername(String username) {
        this.username = username;
        welcomeLabel.setText("Hello, " + username + "!");
    }

    public void propertyChange(PropertyChangeEvent evt) {
        HomeState state = (HomeState) evt.getNewValue();
        if(!state.getUsername().equals("")) {
            setUsername(state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }
}