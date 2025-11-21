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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClickingView extends JPanel implements PropertyChangeListener {
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
    private JButton rateButton = new JButton("⭐ Rate and Comment");
    private JButton backButton = new JButton("← Back");
    private JButton exitButton = new JButton("✕");

    private final String viewName = "clicking";
    private String previousViewName = "BROWSE";
    private String homeViewName = "HOMEPAGE";

    public ClickingView(ClickingViewModel viewModel, CommentViewModel comment, ViewManagerModel viewManager) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.commentViewModel = comment;
        this.viewManagerModel = viewManager;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE); // White background
        setBorder(new EmptyBorder(15, 15, 15, 15));

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
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setToolTipText("Return to Browse");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(backButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.RED);
        exitButton.setToolTipText("Return to Homepage");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(exitButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Title and error in center
        JPanel centerTopPanel = new JPanel();
        centerTopPanel.setLayout(new BoxLayout(centerTopPanel, BoxLayout.Y_AXIS));
        centerTopPanel.setBackground(Color.WHITE);

        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerTopPanel.add(errorLabel);
        centerTopPanel.add(Box.createVerticalStrut(10));
        centerTopPanel.add(titleLabel);

        topPanel.add(centerTopPanel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(Color.WHITE);

        JPanel posterPanel = new JPanel(new BorderLayout());
        posterPanel.setBackground(Color.WHITE);
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setVerticalAlignment(SwingConstants.CENTER);
        posterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        posterLabel.setForeground(Color.GRAY);
        posterPanel.add(posterLabel, BorderLayout.CENTER);

        JPanel overviewPanel = new JPanel(new BorderLayout());
        overviewPanel.setBackground(Color.WHITE);
        overviewPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel overviewTitle = new JLabel("Overview");
        overviewTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        overviewTitle.setForeground(new Color(66, 66, 66));
        overviewPanel.add(overviewTitle, BorderLayout.NORTH);

        overviewText.setLineWrap(true);
        overviewText.setWrapStyleWord(true);
        overviewText.setEditable(false);
        overviewText.setFont(new Font("Arial", Font.PLAIN, 14));
        overviewText.setForeground(new Color(33, 33, 33));
        overviewText.setBackground(Color.WHITE);
        overviewText.setBorder(new EmptyBorder(10, 0, 0, 0));

        JScrollPane scroll = new JScrollPane(overviewText);
        scroll.setBorder(null);
        overviewPanel.add(scroll, BorderLayout.CENTER);

        centerPanel.add(posterPanel);
        centerPanel.add(overviewPanel);

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 15));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Details Panel
        JPanel detailsCard = new JPanel();
        detailsCard.setLayout(new GridLayout(2, 2, 20, 15));
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(224, 224, 224), 1),
                new EmptyBorder(20, 25, 20, 25)
        ));

        Font detailFont = new Font("Arial", Font.PLAIN, 15);
        yearLabel.setFont(detailFont);
        ratingLabel.setFont(detailFont);
        languageLabel.setFont(detailFont);
        genresLabel.setFont(detailFont);

        yearLabel.setForeground(new Color(33, 33, 33));
        ratingLabel.setForeground(new Color(33, 33, 33));
        languageLabel.setForeground(new Color(33, 33, 33));
        genresLabel.setForeground(new Color(33, 33, 33));

        detailsCard.add(createDetailItem("📅 Release Year:", yearLabel));
        detailsCard.add(createDetailItem("⭐ Rating:", ratingLabel));
        detailsCard.add(createDetailItem("🌐 Language:", languageLabel));
        detailsCard.add(createDetailItem("🎬 Genres:", genresLabel));

        bottomPanel.add(detailsCard, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        rateButton.setFont(new Font("Arial", Font.BOLD, 16));
        rateButton.setPreferredSize(new Dimension(220, 40));

        buttonPanel.add(rateButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JPanel createDetailItem(String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(new Color(66, 66, 66));

        panel.add(label);
        panel.add(valueLabel);

        return panel;
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
        yearLabel.setText("Year: " + state.getYear());
        ratingLabel.setText("Rating: " + state.getRating());
        languageLabel.setText("Language: " + state.getLanguage());
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




