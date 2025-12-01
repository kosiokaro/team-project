package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import entity.Movie;

import interface_adapter.clicking.ClickingController;
import interface_adapter.watchlist.load.LoadWatchListController;
import interface_adapter.watchlist.load.LoadWatchListState;
import interface_adapter.watchlist.load.LoadWatchListViewModel;
import interface_adapter.watchlist.WatchListController;

public class WatchlistView extends JPanel implements PropertyChangeListener {
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(139, 92, 246);
    private static final Color BACKGROUND_COLOR = new Color(17, 24, 39);
    private static final Color CARD_COLOR = new Color(31, 41, 55);
    private static final Color TEXT_COLOR = new Color(243, 244, 246);
    private static final Color ACCENT_COLOR = new Color(147, 51, 234);
    private static final Color HOVER_COLOR = new Color(45, 55, 72);

    private final LoadWatchListViewModel loadViewModel;
    private WatchListController controller;
    private LoadWatchListController loadController;
    private String currentUsername;
    private ClickingController clickingController;

    public final String viewName = "WATCHLIST";
    private JLabel titleLabel;
    private JButton switchButton;
    private JButton homeButton;

    private JTabbedPane filterTabs;
    private JPanel allPanel;
    private JComboBox<String> genreFilter;
    private JPanel filterPanel;
    private JScrollPane allScrollPane;

    private List<MediaCardData> allMediaData;

    public WatchlistView(LoadWatchListViewModel loadViewModel) {
        this.loadViewModel = loadViewModel;
        this.loadViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);
        this.allMediaData = new ArrayList<>();

        // Top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(CARD_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        titleLabel = new JLabel("My Watchlist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);

        createFilterPanel();

        filterTabs = new JTabbedPane();
        filterTabs.setBackground(BACKGROUND_COLOR);
        filterTabs.setForeground(TEXT_COLOR);

        allPanel = createMediaListPanel();

        allScrollPane = new JScrollPane(allPanel);
        allScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        allScrollPane.setBorder(null);
        allScrollPane.getViewport().setBackground(BACKGROUND_COLOR);

        filterTabs.addTab("All Movies", allScrollPane);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(filterTabs, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // Bottom button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        switchButton = new JButton("Go to Favorites");
        homeButton = new JButton("Go to Homepage");

        styleButton(switchButton, PRIMARY_COLOR, SECONDARY_COLOR);
        styleButton(homeButton, ACCENT_COLOR, new Color(126, 34, 206));

        buttonPanel.add(switchButton);
        buttonPanel.add(homeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
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

    public void setClickingController(ClickingController controller) {
        this.clickingController = controller;
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        System.out.println("setCurrentUsername called with: " + username);
        System.out.println("loadController at this point: " + (loadController != null ? "NOT NULL" : "NULL"));
        attemptLoad();
    }

    private void attemptLoad() {
        if (currentUsername != null && loadController != null) {
            System.out.println("Attempting to load watchlist for: " + currentUsername);
            loadUserWatchlist();
        } else {
            System.out.println("Cannot load yet - username: " + (currentUsername != null ? "SET" : "NULL") +
                    ", loadController: " + (loadController != null ? "SET" : "NULL"));
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        System.out.println("WatchlistView.addNotify() called");
        attemptLoad();
    }

    public void loadUserWatchlist() {
        System.out.println("=== loadUserWatchlist called ===");
        System.out.println("currentUsername: " + (currentUsername != null ? currentUsername : "NULL"));
        System.out.println("loadController: " + (loadController != null ? "NOT NULL" : "NULL"));

        if (loadController != null && currentUsername != null) {
            System.out.println("✓ Both conditions met, calling loadController.loadWatchlist");
            loadController.loadWatchlist(currentUsername);
        } else {
            System.err.println("✗ Cannot load - currentUsername is " +
                    (currentUsername == null ? "NULL" : "SET") +
                    ", loadController is " +
                    (loadController == null ? "NULL" : "SET"));
        }
    }

    public void setLoadController(LoadWatchListController controller) {
        this.loadController = controller;
        System.out.println("setLoadController called, controller is: " + (controller != null ? "NOT NULL" : "NULL"));
    }

    public void loadWatchlist(List<Movie> movies) {
        System.out.println("=== loadWatchlist called with " + (movies != null ? movies.size() : "NULL") + " movies ===");
        clearAllMedia();

        for (Movie movie : movies) {
            if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
                System.out.println("Skipping movie with empty title, ID: " + movie.getReferenceNumber());
                continue;
            }

            System.out.println("\n--- Processing movie ---");
            System.out.println("Title: " + movie.getTitle());
            System.out.println("ID: " + movie.getReferenceNumber());
            System.out.println("Poster: " + movie.posterUrl);

            ArrayList<String> genres = new ArrayList<>();
            if (movie.getGenres() != null) {
                for (String genre : movie.getGenres()) {
                    genres.add(genre);
                }
            }

            addMediaCard(
                    movie.getTitle(),
                    movie.getReferenceNumber(),
                    genres,
                    movie.getOverview(),
                    movie.getRating(),
                    movie.posterUrl
            );
        }
        System.out.println("\n=== loadWatchlist finished ===");
    }

    public void addMediaCard(String title, int id, List<String> genres,
                             String description, double rating, String posterUrl) {
        System.out.println("=== addMediaCard called ===");
        System.out.println("Title param: " + title);
        System.out.println("ID param: " + id);
        System.out.println("Description param: " + (description != null ? description.substring(0, Math.min(50, description.length())) : "NULL"));
        System.out.println("Rating param: " + rating);
        System.out.println("PosterUrl param: " + posterUrl);

        MediaCardData data = new MediaCardData(title, id, genres, description, rating, posterUrl);
        allMediaData.add(data);
        filterByGenre();
    }

    public void setController(WatchListController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("WatchlistView propertyChange event: " + evt.getPropertyName());
        if ("state".equals(evt.getPropertyName())) {
            LoadWatchListState state = (LoadWatchListState) evt.getNewValue();
            if (state.getMovies() != null) {
                loadWatchlist(state.getMovies());
            }
        } else if ("message".equals(evt.getPropertyName())) {
            String message = (String) evt.getNewValue();
            if (message != null) {
                JOptionPane.showMessageDialog(this, message);
            }
        }
    }

    private void createFilterPanel() {
        filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        filterPanel.setBackground(CARD_COLOR);
        filterPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel filterLabel = new JLabel("Filter by Genre:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterLabel.setForeground(TEXT_COLOR);

        String[] genres = {
                "All Genres", "Action", "Adventure", "Animation", "Comedy", "Crime",
                "Documentary", "Drama", "Family", "Fantasy", "Horror", "Mystery",
                "Romance", "Science Fiction", "Thriller", "Western"
        };

        genreFilter = new JComboBox<>(genres);
        genreFilter.setBackground(BACKGROUND_COLOR);
        genreFilter.setForeground(TEXT_COLOR);
        genreFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        genreFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));
        genreFilter.addActionListener(e -> filterByGenre());

        filterPanel.add(filterLabel);
        filterPanel.add(genreFilter);
    }

    private void filterByGenre() {
        String selectedGenre = (String) genreFilter.getSelectedItem();
        allPanel.removeAll();

        for (MediaCardData data : allMediaData) {
            if ("All Genres".equals(selectedGenre) || data.genres.contains(selectedGenre)) {
                ActionListener detailsListener = e -> showMovieDetails(data.id);
                JPanel card = createMediaRow(data.title, data.id, data.genres, data.description, data.rating, detailsListener);
                allPanel.add(card);
            }
        }

        allPanel.revalidate();
        allPanel.repaint();
    }

    private JPanel createMediaListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }

    private JPanel createMediaRow(String title, int id, List<String> genres,
                                  String description, double rating, ActionListener detailsListener) {
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout(20, 0));
        row.setBackground(CARD_COLOR);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                new EmptyBorder(15, 15, 15, 15)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add hover effect
        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                row.setBackground(HOVER_COLOR);
                row.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                row.setBackground(CARD_COLOR);
                row.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                        new EmptyBorder(15, 15, 15, 15)
                ));
            }
        });

        MediaCardData movieData = null;
        for (MediaCardData data : allMediaData) {
            if (data.id == id) {
                movieData = data;
                break;
            }
        }

        // Poster
        JLabel posterLabel = new JLabel("No Poster", SwingConstants.CENTER);
        posterLabel.setPreferredSize(new Dimension(120, 180));
        posterLabel.setOpaque(true);
        posterLabel.setBackground(new Color(55, 65, 81));
        posterLabel.setForeground(new Color(156, 163, 175));
        posterLabel.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99), 2));

        if (movieData != null && movieData.posterUrl != null && !movieData.posterUrl.equals("null")) {
            final String urlString = movieData.posterUrl;
            System.out.println("Loading poster: " + urlString);

            new Thread(() -> {
                try {
                    URL url = new URL(urlString);
                    ImageIcon icon = new ImageIcon(url);

                    SwingUtilities.invokeLater(() -> {
                        Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                        posterLabel.setIcon(new ImageIcon(scaled));
                        posterLabel.setText("");
                        System.out.println("✓ Poster loaded for: " + title);
                    });
                } catch (Exception e) {
                    System.out.println("Failed to load poster: " + e.getMessage());
                }
            }).start();
        }

        row.add(posterLabel, BorderLayout.WEST);

        // Details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(titleLabel);

        detailsPanel.add(Box.createVerticalStrut(8));

        if (rating > 0) {
            JLabel ratingLabel = new JLabel("Rating: " + String.format("%.1f", rating));
            ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            ratingLabel.setForeground(new Color(250, 204, 21));
            ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(ratingLabel);
            detailsPanel.add(Box.createVerticalStrut(8));
        }

        if (!genres.isEmpty()) {
            JPanel genrePanel = new JPanel();
            genrePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            genrePanel.setOpaque(false);
            genrePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            for (String genre : genres) {
                JLabel genreTag = new JLabel(genre);
                genreTag.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                genreTag.setForeground(Color.WHITE);
                genreTag.setOpaque(true);
                genreTag.setBackground(PRIMARY_COLOR);
                genreTag.setBorder(new EmptyBorder(3, 10, 3, 10));
                genrePanel.add(genreTag);
            }

            detailsPanel.add(genrePanel);
            detailsPanel.add(Box.createVerticalStrut(10));
        }

        if (description != null && !description.isEmpty()) {
            JTextArea descriptionArea = new JTextArea(description);
            descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            descriptionArea.setForeground(new Color(209, 213, 219));
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);
            descriptionArea.setOpaque(false);
            descriptionArea.setRows(2);
            descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            detailsPanel.add(descriptionArea);
            detailsPanel.add(Box.createVerticalStrut(10));
        }

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton detailsButton = new JButton("Details");
        styleSmallButton(detailsButton, PRIMARY_COLOR, SECONDARY_COLOR);
        if (detailsListener != null) {
            detailsButton.addActionListener(detailsListener);
        }

        JButton removeButton = new JButton("Remove");
        styleSmallButton(removeButton, new Color(239, 68, 68), new Color(220, 38, 38));
        removeButton.addActionListener(e -> {
            if (controller != null && currentUsername != null) {
                controller.removeFromWatchList(currentUsername, String.valueOf(id));
                allMediaData.removeIf(data -> data.id == id);
                filterByGenre();
            }
        });

        buttonPanel.add(detailsButton);
        buttonPanel.add(removeButton);
        detailsPanel.add(buttonPanel);

        row.add(detailsPanel, BorderLayout.CENTER);

        return row;
    }

    private void styleSmallButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(6, 15, 6, 15));

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

    public void clearAllMedia() {
        allMediaData.clear();
        allPanel.removeAll();
        allPanel.revalidate();
        allPanel.repaint();
    }

    public void setswitchtofavButtonListener(ActionListener listener) {
        switchButton.addActionListener(listener);
    }

    public void sethomeButtonListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }

    private void showMovieDetails(int movieId) {
        if (clickingController != null) {
            clickingController.onClick(movieId);
        } else {
            System.err.println("ClickingController is not set!");
        }
    }

    public String getViewName() {
        return viewName;
    }

    private static class MediaCardData {
        String title;
        int id;
        List<String> genres;
        String description;
        double rating;
        String posterUrl;

        MediaCardData(String title, int id, List<String> genres,
                      String description, double rating, String posterUrl) {
            this.title = title;
            this.id = id;
            this.genres = new ArrayList<>(genres);
            this.description = description;
            this.rating = rating;
            this.posterUrl = posterUrl;
        }
    }
}