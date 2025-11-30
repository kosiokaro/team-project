package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import entity.Movie;

import interface_adapter.watchlist.load.LoadWatchListController;
import interface_adapter.watchlist.load.LoadWatchListState;
import interface_adapter.watchlist.load.LoadWatchListViewModel;
import interface_adapter.watchlist.WatchListController;

public class WatchlistView extends JPanel implements PropertyChangeListener {
    private final LoadWatchListViewModel loadViewModel;
    private WatchListController controller;
    private LoadWatchListController loadController;
    private String currentUsername;

    public final String viewName = "WATCHLIST";
    private JLabel titleLabel;
    private JButton switchButton;
    private JButton homeButton;

    // Filtering components
    private JTabbedPane filterTabs;
    private JPanel allPanel;

    private JComboBox<String> genreFilter;
    private JPanel filterPanel;

    // Scroll panes
    private JScrollPane allScrollPane;

    // Store all media data for filtering
    private List<MediaCardData> allMediaData;
    private java.util.function.Consumer<Integer> detailsClickListener;


    public WatchlistView(LoadWatchListViewModel loadViewModel) {
        this.loadViewModel = loadViewModel;
        this.loadViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.allMediaData = new ArrayList<>();

        titleLabel = new JLabel("My Watchlist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        createFilterPanel();

        filterTabs = new JTabbedPane();

        allPanel = createMediaListPanel();

        allScrollPane = new JScrollPane(allPanel);
        allScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        allScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        filterTabs.addTab("All Movies", allScrollPane);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(filterTabs, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        switchButton = new JButton("Go to Favorites View");
        homeButton = new JButton("Go back to homepage");

        buttonPanel.add(switchButton);
        buttonPanel.add(homeButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
        System.out.println("setCurrentUsername called with: " + username);
        System.out.println("loadController at this point: " + (loadController != null ? "NOT NULL" : "NULL"));

        // Try to load, but if loadController isn't ready yet, we'll load on addNotify
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
        // Try to load when view becomes visible
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
            // Skip movies with empty titles
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



    /**
     * Adds a movie to the watchlist
     */
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
            @SuppressWarnings("unchecked")
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
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel filterLabel = new JLabel("Filter by Genre:");
        filterLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        // Common TMDb movie genres
        String[] genres = {
                "All Genres",
                "Action",
                "Adventure",
                "Animation",
                "Comedy",
                "Crime",
                "Documentary",
                "Drama",
                "Family",
                "Fantasy",
                "Horror",
                "Mystery",
                "Romance",
                "Science Fiction",
                "Thriller",
                "Western"
        };

        genreFilter = new JComboBox<>(genres);
        genreFilter.addActionListener(e -> filterByGenre());

        filterPanel.add(filterLabel);
        filterPanel.add(genreFilter);
    }

    private void filterByGenre() {
        String selectedGenre = (String) genreFilter.getSelectedItem();

        // Clear panel
        allPanel.removeAll();

        // Re-add filtered movies
        for (MediaCardData data : allMediaData) {
            if ("All Genres".equals(selectedGenre) || data.genres.contains(selectedGenre)) {
                ActionListener detailsListener = e -> showMovieDetails(data.id);
                JPanel card = createMediaRow(data.title, data.id, data.genres, data.description, data.rating, detailsListener);
                allPanel.add(card);
            }
        }

        // Refresh panel
        allPanel.revalidate();
        allPanel.repaint();
    }

    private void showMovieDetails(int movieId) {
        if (detailsClickListener != null) {
            detailsClickListener.accept(movieId);
        }
    }


    private JPanel createMediaListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel createMediaRow(String title, int id, List<String> genres,
                                  String description, double rating, ActionListener detailsListener) {
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout(15, 0));
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get the movie to access poster URL
        MediaCardData movieData = null;
        for (MediaCardData data : allMediaData) {
            if (data.id == id) {
                movieData = data;
                break;
            }
        }

        // Poster on the left
        JLabel posterLabel = new JLabel("POSTER", SwingConstants.CENTER);
        posterLabel.setPreferredSize(new Dimension(120, 180));
        posterLabel.setOpaque(true);
        posterLabel.setBackground(Color.LIGHT_GRAY);
        posterLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        // Try to load poster image if available
        if (movieData != null && movieData.posterUrl != null && !movieData.posterUrl.equals("null")) {
            try {
                URL url = new URL(movieData.posterUrl);
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaled));
                posterLabel.setText("");
            } catch (Exception e) {
                System.out.println("Failed to load poster for movie " + id + ": " + e.getMessage());
            }
        }

        row.add(posterLabel, BorderLayout.WEST);

        // Details panel on the right
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(titleLabel);

        detailsPanel.add(Box.createVerticalStrut(5));

        // Rating row
        JPanel metaPanel = new JPanel();
        metaPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        metaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (rating > 0) {
            JLabel ratingLabel = new JLabel("⭐ " + String.format("%.1f", rating));
            ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            ratingLabel.setForeground(new Color(255, 165, 0));
            metaPanel.add(ratingLabel);
        }

        detailsPanel.add(metaPanel);
        detailsPanel.add(Box.createVerticalStrut(5));

        // Genres
        if (!genres.isEmpty()) {
            JPanel genrePanel = new JPanel();
            genrePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            genrePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            for (String genre : genres) {
                JLabel genreTag = new JLabel(genre);
                genreTag.setFont(new Font("SansSerif", Font.PLAIN, 10));
                genreTag.setForeground(Color.WHITE);
                genreTag.setOpaque(true);
                genreTag.setBackground(new Color(70, 130, 180));
                genreTag.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
                genrePanel.add(genreTag);
            }

            detailsPanel.add(genrePanel);
            detailsPanel.add(Box.createVerticalStrut(8));
        }

        // Description
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setRows(3);
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailsPanel.add(descriptionArea);

        detailsPanel.add(Box.createVerticalStrut(8));

        // Action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
        removeButton.addActionListener(e -> {
            if (controller != null && currentUsername != null) {
                controller.removeFromWatchList(currentUsername, String.valueOf(id));
                allMediaData.removeIf(data -> data.id == id);
                filterByGenre();
            }
        });

        JButton detailsButton = new JButton("Details");
        detailsButton.setFont(new Font("SansSerif", Font.PLAIN, 11));

        if (detailsListener != null) {
            detailsButton.addActionListener(detailsListener);
        }

        buttonPanel.add(detailsButton);
        buttonPanel.add(removeButton);
        detailsPanel.add(buttonPanel);

        row.add(detailsPanel, BorderLayout.CENTER);

        return row;
    }

    /**
     * Clears all movies from watchlist
     */
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

    public void setDetailsClickListener(java.util.function.Consumer<Integer> listener) {
        this.detailsClickListener = listener;
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Inner class to store movie data
     */
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