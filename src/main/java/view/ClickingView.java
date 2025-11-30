package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.clicking.ClickingState;
import interface_adapter.clicking.ClickingViewModel;
import interface_adapter.rate_and_comment.CommentState;
import interface_adapter.rate_and_comment.CommentViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClickingView extends JPanel implements PropertyChangeListener {
    // Modern color palette matching homepage and browse
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(139, 92, 246);
    private static final Color BACKGROUND_COLOR = new Color(17, 24, 39);
    private static final Color CARD_COLOR = new Color(31, 41, 55);
    private static final Color TEXT_COLOR = new Color(243, 244, 246);
    private static final Color ACCENT_COLOR = new Color(147, 51, 234);
    private static final Color HOVER_COLOR = new Color(45, 55, 72);

    private final ClickingViewModel viewModel;
    private final CommentViewModel commentViewModel;
    private final ViewManagerModel viewManagerModel;
    private interface_adapter.clicking.ClickingController clickingController;

    private JLabel errorLabel = new JLabel();
    private JLabel languageLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel posterLabel = new JLabel();
    private JTextArea overviewText = new JTextArea();
    private JLabel yearLabel = new JLabel();
    private JLabel ratingLabel = new JLabel();
    private JLabel genresLabel = new JLabel();
    private JButton rateButton = new JButton("Rate and Comment");
    private JButton backButton = new JButton("Back");
    private JButton exitButton = new JButton("Home");

    private final String viewName = "clicking";
    private String previousViewName = "BROWSE";
    private String homeViewName = "HOMEPAGE";

    public ClickingView(ClickingViewModel viewModel, CommentViewModel comment, ViewManagerModel viewManager) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.commentViewModel = comment;
        this.viewManagerModel = viewManager;

        setLayout(new BorderLayout(10, 10));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        setupButtonActions();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        styleButton(backButton, ACCENT_COLOR, new Color(126, 34, 206));
        backButton.setToolTipText("Return to Browse");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        styleButton(exitButton, new Color(239, 68, 68), new Color(220, 38, 38));
        exitButton.setToolTipText("Return to Homepage");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.add(exitButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Title and error in center
        JPanel centerTopPanel = new JPanel();
        centerTopPanel.setLayout(new BoxLayout(centerTopPanel, BoxLayout.Y_AXIS));
        centerTopPanel.setBackground(BACKGROUND_COLOR);

        errorLabel.setForeground(new Color(239, 68, 68));
        errorLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerTopPanel.add(errorLabel);
        centerTopPanel.add(Box.createVerticalStrut(10));
        centerTopPanel.add(titleLabel);

        topPanel.add(centerTopPanel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Poster Panel
        JPanel posterPanel = new JPanel(new BorderLayout());
        posterPanel.setBackground(CARD_COLOR);
        posterPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                new EmptyBorder(20, 20, 20, 20)
        ));

        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        posterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        posterLabel.setForeground(new Color(156, 163, 175));
        posterPanel.add(posterLabel, BorderLayout.CENTER);

        // Overview Panel
        JPanel overviewPanel = new JPanel(new BorderLayout());
        overviewPanel.setBackground(CARD_COLOR);
        overviewPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel overviewTitle = new JLabel("Overview");
        overviewTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        overviewTitle.setForeground(TEXT_COLOR);
        overviewPanel.add(overviewTitle, BorderLayout.NORTH);

        overviewText.setLineWrap(true);
        overviewText.setWrapStyleWord(true);
        overviewText.setEditable(false);
        overviewText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        overviewText.setForeground(new Color(209, 213, 219));
        overviewText.setBackground(CARD_COLOR);
        overviewText.setBorder(new EmptyBorder(15, 0, 0, 0));

        JScrollPane scroll = new JScrollPane(overviewText);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(CARD_COLOR);
        overviewPanel.add(scroll, BorderLayout.CENTER);

        centerPanel.add(posterPanel);
        centerPanel.add(overviewPanel);

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 20));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Details Panel
        JPanel detailsCard = new JPanel();
        detailsCard.setLayout(new GridLayout(2, 2, 30, 20));
        detailsCard.setBackground(CARD_COLOR);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                new EmptyBorder(25, 30, 25, 30)
        ));

        Font detailFont = new Font("Segoe UI", Font.PLAIN, 15);
        yearLabel.setFont(detailFont);
        ratingLabel.setFont(detailFont);
        languageLabel.setFont(detailFont);
        genresLabel.setFont(detailFont);

        yearLabel.setForeground(TEXT_COLOR);
        ratingLabel.setForeground(new Color(250, 204, 21)); // Gold for rating
        languageLabel.setForeground(TEXT_COLOR);
        genresLabel.setForeground(TEXT_COLOR);

        detailsCard.add(createDetailItem("Release Year:", yearLabel));
        detailsCard.add(createDetailItem("Rating:", ratingLabel));
        detailsCard.add(createDetailItem("Language:", languageLabel));
        detailsCard.add(createDetailItem("Genres:", genresLabel));

        bottomPanel.add(detailsCard, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        styleButton(rateButton, PRIMARY_COLOR, SECONDARY_COLOR);
        rateButton.setPreferredSize(new Dimension(220, 45));
        rateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        buttonPanel.add(rateButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JPanel createDetailItem(String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(CARD_COLOR);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(new Color(156, 163, 175));

        panel.add(label);
        panel.add(valueLabel);

        return panel;
    }

    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 25, 10, 25));

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
    }

    private void setupButtonActions() {
        backButton.addActionListener(e -> goBack());

        exitButton.addActionListener(e -> goToHomepage());

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentState state = commentViewModel.getState();
                state.setMedianame(titleLabel.getText());
                commentViewModel.setState(state);
                commentViewModel.firePropertyChange();
                viewManagerModel.setState(commentViewModel.getViewName());
                viewManagerModel.firePropertyChange();
            }
        });
    }

    public void updateView() {
        ClickingState state = viewModel.getState();
        String errorMessage = state.getErrorMessage();

        errorLabel.setText(state.getErrorMessage());

        if (errorMessage != null && !errorMessage.isEmpty()) {
            titleLabel.setText("");
            yearLabel.setText("");
            ratingLabel.setText("");
            languageLabel.setText("");
            genresLabel.setText("");
            overviewText.setText("");
            posterLabel.setIcon(null);
            return;
        }

        titleLabel.setText(state.getTitle() != null ? state.getTitle() : "Unknown Title");
        yearLabel.setText(String.valueOf(state.getYear()));
        ratingLabel.setText(String.valueOf(state.getRating()));
        languageLabel.setText(state.getLanguage());
        overviewText.setText(state.getOverview());

        if (state.getGenres() != null && !state.getGenres().isEmpty()) {
            genresLabel.setText(String.join(", ", state.getGenres()));
        } else {
            genresLabel.setText("N/A");
        }

        if (state.getPosterUrl() != null && !state.getPosterUrl().isEmpty()) {
            posterLabel.setText("Loading image...");

            new Thread(() -> {
                try {
                    ImageIcon icon = new ImageIcon(new java.net.URL(state.getPosterUrl()));

                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(300, 450, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImg);

                    SwingUtilities.invokeLater(() -> {
                        posterLabel.setIcon(scaledIcon);
                        posterLabel.setText("");
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        posterLabel.setIcon(null);
                        posterLabel.setText("Image unavailable");
                    });
                }
            }).start();
        } else {
            posterLabel.setIcon(null);
            posterLabel.setText("No poster available");
        }
    }

    private void goBack() {
        viewManagerModel.setState(previousViewName);
        viewManagerModel.firePropertyChange();
    }

    private void goToHomepage() {
        viewManagerModel.setState(homeViewName);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateView();
    }

    public String getViewName() {
        return viewName;
    }

    public void setPreviousViewName(String viewName) {
        this.previousViewName = viewName;
    }

    public void setHomeViewName(String viewName) {
        this.homeViewName = viewName;
    }

    public void setClickingController(interface_adapter.clicking.ClickingController controller) {
        this.clickingController = controller;
    }
}