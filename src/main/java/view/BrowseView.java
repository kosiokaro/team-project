package view;

import interface_adapter.browse.BrowseController;
import interface_adapter.browse.BrowsePresenter;
import interface_adapter.browse.BrowseState;
import interface_adapter.browse.BrowseViewModel;
import use_case.browse.BrowseOutputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.List;


public class BrowseView extends JPanel implements PropertyChangeListener, ActionListener {
    private BrowsePresenter browsePresenter;
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(139, 92, 246);
    private static final Color BACKGROUND_COLOR = new Color(17, 24, 39);
    private static final Color CARD_COLOR = new Color(31, 41, 55);
    private static final Color TEXT_COLOR = new Color(243, 244, 246);
    private static final Color ACCENT_COLOR = new Color(147, 51, 234);
    private static final Color HOVER_COLOR = new Color(45, 55, 72);

    private final JButton browseButton = new JButton("Search");
    private final JButton homepageButton = new JButton("Home");

    private final JTextField searchField = new JTextField(20);
    private final JComboBox<String> sortBox = new JComboBox<>(new String[]{
            "Rating ↑ (Ascending)",
            "Rating ↓ (Descending)"
    });
    private final JButton loadMoreButton = new JButton("Load More");
    private final JLabel searchLabel = new JLabel("Search:");
    private final JLabel sortByLabel = new JLabel("Sort By:");
    private final JLabel yearLabel = new JLabel("Year:");
    private final JComboBox<String> yearBox = new JComboBox<>();
    private final JPanel gridPanel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(gridPanel);

    public static final String viewName = "BROWSE";
    private final BrowseViewModel viewModel;
    private BrowseController browseController = null;

    public BrowseView(BrowseViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);
        createUIComponents();
    }

    public void setBrowsePresenter(BrowsePresenter presenter) {
        this.browsePresenter = presenter;
    }

    private void createUIComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Top bar with modern styling
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        topBar.setBackground(CARD_COLOR);
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Style labels
        searchLabel.setForeground(TEXT_COLOR);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topBar.add(searchLabel);

        // Style search field
        searchField.setBackground(BACKGROUND_COLOR);
        searchField.setForeground(TEXT_COLOR);
        searchField.setCaretColor(TEXT_COLOR);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        topBar.add(searchField);

        sortByLabel.setForeground(TEXT_COLOR);
        sortByLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topBar.add(sortByLabel);

        // Style combo boxes
        styleComboBox(sortBox);
        topBar.add(sortBox);

        yearLabel.setForeground(TEXT_COLOR);
        yearLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topBar.add(yearLabel);

        yearBox.addItem("Any");
        for (int y = 2025; y >= 1950; y--) {
            yearBox.addItem(String.valueOf(y));
        }
        styleComboBox(yearBox);
        topBar.add(yearBox);

        // Style buttons
        styleButton(browseButton, PRIMARY_COLOR, SECONDARY_COLOR);
        topBar.add(browseButton);

        styleButton(homepageButton, ACCENT_COLOR, new Color(126, 34, 206));
        topBar.add(homepageButton);

        add(topBar, BorderLayout.NORTH);

        // Movie grid
        gridPanel.setLayout(new GridLayout(0, 4, 25, 25));
        gridPanel.setBackground(BACKGROUND_COLOR);
        gridPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        // Load more button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        styleButton(loadMoreButton, PRIMARY_COLOR, SECONDARY_COLOR);
        loadMoreButton.setPreferredSize(new Dimension(200, 45));
        bottomPanel.add(loadMoreButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        yearBox.addActionListener(this);
        loadMoreButton.addActionListener(this);
        searchField.addActionListener(this);
        sortBox.addActionListener(this);
        browseButton.addActionListener(this);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(BACKGROUND_COLOR);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));
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

    public void ClearPage() {
        gridPanel.removeAll();
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public void displayMovies(List<BrowseOutputData.MovieCardData> movies) {
        for (int i = 0; i < movies.toArray().length; i++) {
            gridPanel.add(createMovieCard(movies.get(i)));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createMovieCard(BrowseOutputData.MovieCardData movie) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Rounded corners background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };

        card.putClientProperty("movieID", movie.getMovieID());
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setPreferredSize(new Dimension(200, 380));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            private Color originalColor = CARD_COLOR;

            @Override
            public void mouseClicked(MouseEvent e) {
                int id = (int) card.getClientProperty("movieID");
                System.out.println("id: " + id);
                browseController.selectMovie(id);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(HOVER_COLOR);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        new EmptyBorder(10, 10, 10, 10)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(originalColor);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(55, 65, 81), 2),
                        new EmptyBorder(10, 10, 10, 10)
                ));
            }
        });

        // Poster image
        if (movie.posterURL != null) {
            try {
                URL url = new URL(movie.posterURL);
                ImageIcon icon = new ImageIcon(url);
                Image scaled = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaled);

                JLabel poster = new JLabel(icon);
                poster.setAlignmentX(Component.CENTER_ALIGNMENT);
                poster.setBorder(new EmptyBorder(0, 0, 10, 0));
                card.add(poster);
            } catch (Exception ex) {
                ex.printStackTrace();
                JLabel poster = new JLabel("No Poster");
                poster.setForeground(TEXT_COLOR);
                poster.setAlignmentX(Component.CENTER_ALIGNMENT);
                card.add(poster);
            }
        } else {
            JLabel poster = new JLabel("No Poster");
            poster.setForeground(TEXT_COLOR);
            poster.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(poster);
        }

        // Movie details
        JLabel title = new JLabel("<html><center>" + movie.title + "</center></html>");
        title.setForeground(TEXT_COLOR);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(5));

        JLabel rating = new JLabel("Rating: " + movie.rating);
        rating.setForeground(new Color(250, 204, 21));
        rating.setFont(new Font("Segoe UI", Font.BOLD, 13));
        rating.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(rating);

        JLabel runtime = new JLabel(movie.runtime + " min");
        runtime.setForeground(new Color(156, 163, 175));
        runtime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        runtime.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(runtime);

        JLabel genres = new JLabel("<html><center>" + movie.genres + "</center></html>");
        genres.setForeground(new Color(156, 163, 175));
        genres.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        genres.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(genres);

        card.add(Box.createVerticalStrut(10));

        // Add to Watchlist button
        JButton addToWatchlistButton = new JButton("+ Watchlist");
        addToWatchlistButton.setBackground(PRIMARY_COLOR);
        addToWatchlistButton.setForeground(Color.WHITE);
        addToWatchlistButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addToWatchlistButton.setFocusPainted(false);
        addToWatchlistButton.setBorderPainted(false);
        addToWatchlistButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addToWatchlistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToWatchlistButton.setMaximumSize(new Dimension(150, 35));

        addToWatchlistButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addToWatchlistButton.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addToWatchlistButton.setBackground(PRIMARY_COLOR);
            }
        });

        addToWatchlistButton.addActionListener(e -> {
            if (browsePresenter != null) {
                browsePresenter.addToWatchList(movie.getMovieID());
                JOptionPane.showMessageDialog(this, "Added \"" + movie.title + "\" to watchlist!");

        card.add(addToWatchlistButton);

        return card;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("populating page");
        final BrowseState browseState = (BrowseState) evt.getNewValue();
        populatePage(browseState);
    }

    public void populatePage(BrowseState state) {
        displayMovies(state.getMovies(state.getCurrentPageNumber()));
    }

    public void setBrowseController(BrowseController browseController) {
        this.browseController = browseController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final BrowseState browseState = viewModel.getState();
        if (e.getSource() == browseButton || e.getSource() == loadMoreButton) {
            String pageNuber = browseState.getSearchState().getPageNumber();
            if (e.getSource() == loadMoreButton) {
                browseState.incrementPage();
                pageNuber = "" + browseState.getCurrentPageNumber();
            } else if (e.getSource() == browseButton) {
                browseState.resetCurrentPage();
                ClearPage();
            }
            browseController.executeQuery(
                    browseState.getSearchState().getYear(),
                    browseState.getSearchState().getQuery(),
                    pageNuber,
                    browseState.getSearchState().SortAscending(),
                    browseState.getSearchState().SortDescending()
            );
        } else if (e.getSource() == sortBox) {
            if (sortBox.getSelectedIndex() == 0) {
                browseState.getSearchState().setSortAscending();
            } else if (sortBox.getSelectedIndex() == 1) {
                browseState.getSearchState().setSortDescending();
            }
        } else if (e.getSource() == searchField) {
            browseState.getSearchState().setQuery(searchField.getText());
            System.out.println(browseState.getSearchState().getQuery());
        } else if (e.getSource() == yearBox) {
            if (yearBox.getSelectedItem().toString() != "Any") {
                browseState.getSearchState().setYear(yearBox.getSelectedItem().toString());
            }
            System.out.println(browseState.getSearchState().getYear());
        }
    }

    public void setHomeButtonListener(ActionListener listener) {
        homepageButton.addActionListener(listener);
    }
}