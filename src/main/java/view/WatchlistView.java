package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import data_access.TMDbAccountDataBaseImpl;
import entity.Movie;


public class WatchlistView extends JPanel {
    public final String viewName = "WATCHLIST";
    private JLabel titleLabel;
    private JTextArea watchlistDisplay;
    private TMDbAccountDataBaseImpl dataAccess;

    public WatchlistView() {
        this.setLayout(new BorderLayout());

        // Initialize data access
        dataAccess = new TMDbAccountDataBaseImpl();

        // Title
        titleLabel = new JLabel("My Watchlist (UC 7)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        // Text area to display results
        watchlistDisplay = new JTextArea(20, 50);
        watchlistDisplay.setEditable(false);
        watchlistDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(watchlistDisplay);
        this.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();

        // Test Get Movies button
        JButton testMoviesButton = new JButton("Test Get Movies");
        testMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testGetMovies();
            }
        });
        buttonPanel.add(testMoviesButton);

        // Test Get Rating button
        JButton testRatingButton = new JButton("Test Get Rating");
        testRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testGetRating();
            }
        });
        buttonPanel.add(testRatingButton);

        // Switch view button
        JButton switchButton = new JButton("Go to Favorites View");
        buttonPanel.add(switchButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void testGetMovies() {
        watchlistDisplay.setText("Fetching movies from TMDb API...\n\n");

        // Run in background thread to prevent UI freezing
        new Thread(() -> {
            try {
                System.out.println("=== Starting getMovies() API call ===");
                Movie[] movies = dataAccess.getMovies();
                System.out.println("API call completed. Movies returned: " + movies.length);

                // Update UI on Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    StringBuilder result = new StringBuilder();
                    result.append("=== GET MOVIES TEST ===\n");
                    result.append("Status: SUCCESS\n");
                    result.append("Total movies: ").append(movies.length).append("\n\n");

                    if (movies.length == 0) {
                        result.append("No movies returned.\n");
                        result.append("(Check console for 'Movies fetched successfully' message)\n");
                    } else {
                        for (int i = 0; i < Math.min(movies.length, 10); i++) {  // Show first 10
                            Movie movie = movies[i];
                            result.append((i + 1)).append(". ");
                            if (movie != null) {
                                result.append("Movie ID: ").append(movie.getId()).append("\n");
                                // If your Movie has more fields:
                                // result.append("   Title: ").append(movie.getTitle()).append("\n");
                            } else {
                                result.append("(null movie)\n");
                            }
                        }
                        if (movies.length > 10) {
                            result.append("\n... and ").append(movies.length - 10).append(" more");
                        }
                    }

                    watchlistDisplay.setText(result.toString());
                });

            } catch (JSONException ex) {
                System.err.println("JSON Exception: " + ex.getMessage());
                ex.printStackTrace();

                SwingUtilities.invokeLater(() -> {
                    watchlistDisplay.setText("ERROR: JSON Exception\n\n" +
                            ex.getMessage() + "\n\n" +
                            "Check console for full stack trace.");
                });
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
                ex.printStackTrace();

                SwingUtilities.invokeLater(() -> {
                    watchlistDisplay.setText("ERROR: " + ex.getClass().getSimpleName() + "\n\n" +
                            ex.getMessage() + "\n\n" +
                            "Check console for full stack trace.");
                });
            }
        }).start();
    }

    private void testGetRating() {
        watchlistDisplay.setText("Fetching rating from TMDb API...\n\n");

        new Thread(() -> {
            try {
                System.out.println("=== Starting getRating() API call ===");
                int rating = dataAccess.getRating();
                System.out.println("API call completed. Rating: " + rating);

                SwingUtilities.invokeLater(() -> {
                    watchlistDisplay.setText("=== GET RATING TEST ===\n" +
                            "Status: SUCCESS\n" +
                            "Rating: " + rating + "\n");
                });

            } catch (JSONException ex) {
                System.err.println("JSON Exception: " + ex.getMessage());
                ex.printStackTrace();

                SwingUtilities.invokeLater(() -> {
                    watchlistDisplay.setText("ERROR: JSON Exception\n\n" +
                            ex.getMessage() + "\n\n" +
                            "Check console for full stack trace.");
                });
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
                ex.printStackTrace();

                SwingUtilities.invokeLater(() -> {
                    watchlistDisplay.setText("ERROR: " + ex.getClass().getSimpleName() + "\n\n" +
                            ex.getMessage() + "\n\n" +
                            "Check console for full stack trace.");
                });
            }
        }).start();
    }

    public String getViewName() {
        return viewName;
    }
}
