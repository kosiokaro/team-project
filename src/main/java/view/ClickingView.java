package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.clicking.ClickingState;
import interface_adapter.clicking.ClickingViewModel;
import interface_adapter.rate_and_comment.CommentState;
import interface_adapter.rate_and_comment.CommentViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ClickingView extends JPanel implements PropertyChangeListener {
    private final ClickingViewModel viewModel;
    private final CommentViewModel commentViewModel;
    private final ViewManagerModel viewManagerModel;
    private interface_adapter.clicking.ClickingController controller;

    private JLabel errorLabel = new JLabel();
    private JLabel languageLabel = new JLabel();
    private JLabel titleLabel = new JLabel();
    private JLabel posterLabel = new JLabel();
    private JTextArea overviewText = new JTextArea();
    private JLabel yearLabel = new JLabel();
    private JLabel ratingLabel = new JLabel();
    private JLabel genresLabel = new JLabel();
    private JButton rateButton = new JButton("Rate and Comment");

    private final String viewName = "clicking";

    public ClickingView(ClickingViewModel viewModel, CommentViewModel comment, ViewManagerModel viewManager) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.commentViewModel = comment;
        this.viewManagerModel = viewManager;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(errorLabel, BorderLayout.NORTH);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JPanel posterPanel = new JPanel(new BorderLayout());
        posterPanel.add(posterLabel, BorderLayout.CENTER);
        centerPanel.add(posterPanel);

        overviewText.setLineWrap(true);
        overviewText.setWrapStyleWord(true);
        overviewText.setEditable(false);
        overviewText.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(overviewText);
        centerPanel.add(scroll);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel detailPanel = new JPanel(new GridLayout(4, 1));
        detailPanel.add(yearLabel);
        detailPanel.add(ratingLabel);
        detailPanel.add(languageLabel);
        detailPanel.add(genresLabel);
        bottomPanel.add(detailPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rateButton.setFont(new Font("Arial", Font.BOLD, 16));
        rateButton.setPreferredSize(new Dimension(200, 40));
        buttonPanel.add(rateButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

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
        titleLabel.setText(state.getTitle());
        yearLabel.setText("Year: " + state.getYear());
        ratingLabel.setText("Rating: " + state.getRating());
        languageLabel.setText("Language: " + state.getLanguage());
        overviewText.setText(state.getOverview());
        if (state.getGenres() != null && !state.getGenres().isEmpty()) {
            genresLabel.setText("Genres: " + String.join(", ", state.getGenres()));
        } else {
            genresLabel.setText("Genres: N/A");
        }
        if (state.getPosterUrl() != null && !state.getPosterUrl().isEmpty()) {
            posterLabel.setText("Loading...");
            new Thread(() -> {
                try {
                    ImageIcon icon = new ImageIcon(new java.net.URL(state.getPosterUrl()));
                    SwingUtilities.invokeLater(() -> {
                        posterLabel.setIcon(icon);
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
            posterLabel.setText("No poster");
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        updateView();
    }

    public String getViewName() {
        return viewName;
    }

    public void setClickingController(interface_adapter.clicking.ClickingController controller) {
        this.controller = controller;
    }
}


//    public ClickingViewModel getViewModel() {
//        return viewModel;
//    }





