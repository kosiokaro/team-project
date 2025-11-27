package view;

import interface_adapter.browse.BrowseController;
import interface_adapter.browse.BrowseState;
import interface_adapter.browse.BrowseViewModel;
import interface_adapter.watchlist.WatchListController;
import use_case.browse.BrowseOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.List;

public class BrowseView extends JPanel  implements PropertyChangeListener, ActionListener {
    private WatchListController watchListController;

    public static final Color TOPBAR_BACKGROUND_COLOR = new Color(50, 50, 50);
    public static final Color BACKGROUND_COLOR = new Color(3, 9, 78);
    public static final Color MOVIE_GRID_BACKGROUND_COLOR = new Color(40, 40, 40);
    public static final Color SEARCH_LABEL_COLOR = new Color(0, 255, 129, 255);

    private final JButton browseButton = new JButton("Browse");
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

    private void createUIComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(TOPBAR_BACKGROUND_COLOR);

        searchLabel.setForeground(SEARCH_LABEL_COLOR);
        topBar.add(searchLabel);

        topBar.add(searchField);


        sortByLabel.setForeground(SEARCH_LABEL_COLOR);
        topBar.add(sortByLabel);
        topBar.add(sortBox);

        yearLabel.setForeground(SEARCH_LABEL_COLOR);
        topBar.add(yearLabel);

        yearBox.addItem("Any");
        for (int y = 2025; y >= 1950; y--) {
            yearBox.addItem(String.valueOf(y));
        }
        topBar.add(yearBox);
        topBar.add(browseButton);

        add(topBar, BorderLayout.NORTH);

        gridPanel.setLayout(new GridLayout(0, 4, 20, 20));
        gridPanel.setBackground(MOVIE_GRID_BACKGROUND_COLOR);

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        add(loadMoreButton, BorderLayout.SOUTH);
        yearBox.addActionListener(this);
        loadMoreButton.addActionListener(this);
        searchField.addActionListener(this);
        sortBox.addActionListener(this);
        browseButton.addActionListener(this);
    }

    public void ClearPage(){
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

    public void setWatchListController(WatchListController controller) {
        this.watchListController = controller;
    }

    private JPanel createMovieCard(BrowseOutputData.MovieCardData movie) {


        JPanel card = new JPanel();
        card.putClientProperty("movieID", movie.getMovieID());
        card.addMouseListener(new MouseAdapter() {
            public  void mouseClicked(MouseEvent e) {
                int id = (int) card.getClientProperty("movieID");
                System.out.println("id: " + id);
                browseController.selectMovie(id);
            }
        });

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(60, 60, 60));
        card.setPreferredSize(new Dimension(150, 250));

        JLabel title = new JLabel(movie.title);
        title.setForeground(SEARCH_LABEL_COLOR);
        JLabel rating = new JLabel("★ " + movie.rating);
        rating.setForeground(SEARCH_LABEL_COLOR);
        JLabel runtime = new JLabel(movie.runtime+ " min");
        runtime.setForeground(SEARCH_LABEL_COLOR);
        JLabel genres = new JLabel(movie.genres);
        genres.setForeground(SEARCH_LABEL_COLOR);
        if(movie.posterURL != null) {
            try {
                URL url = new URL(movie.posterURL);
                ImageIcon icon = new ImageIcon(url);

                // Optional: scale the image to fit the label size
                Image scaled = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaled);

                JLabel poster = new JLabel(icon);
                poster.setPreferredSize(new Dimension(120, 160));
                card.add(poster);

            } catch (Exception ex) {
                ex.printStackTrace();
                JLabel poster = new JLabel("Poster");
                card.add(poster);
            }
        }
        else {
            JLabel poster = new JLabel("Cant Find Poster");
            card.add(poster);
        }



        card.add(title);
        card.add(rating);
        card.add(runtime);
        card.add(genres);

        // ADD TO WATCHLIST BUTTON
        JButton addToWatchlistBtn = new JButton("+ Watchlist");
        addToWatchlistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToWatchlistBtn.setMaximumSize(new Dimension(120, 25));
        addToWatchlistBtn.addActionListener(e -> {
            if (watchListController != null) {
                watchListController.addToWatchList("testuser", String.valueOf(movie.getMovieID()));
                JOptionPane.showMessageDialog(this, "Added \"" + movie.title + "\" to watchlist!");
            }
        });
        card.add(Box.createVerticalStrut(5));
        card.add(addToWatchlistBtn);

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
    public void populatePage(BrowseState state){
        displayMovies(state.getMovies(state.getCurrentPageNumber()));
    }

    public void setBrowseController(BrowseController browseController) {this.browseController=browseController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final BrowseState browseState = viewModel.getState();
        if(e.getSource() == browseButton || e.getSource() == loadMoreButton ) {
            String pageNuber = browseState.getSearchState().getPageNumber();
            if(e.getSource() == loadMoreButton){
                browseState.incrementPage();
                pageNuber = "" + browseState.getCurrentPageNumber();
            }
            else if(e.getSource() == browseButton){
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
        }
        else if(e.getSource() == sortBox) {
            if (sortBox.getSelectedIndex() == 0) {
                browseState.getSearchState().setSortAscending();

            }
            else if (sortBox.getSelectedIndex() == 1) {
                browseState.getSearchState().setSortDescending();
            }

        }
        else if(e.getSource() == searchField) {
            browseState.getSearchState().setQuery(searchField.getText());
            System.out.println(browseState.getSearchState().getQuery());

        }
        else if(e.getSource() == yearBox) {
            if(yearBox.getSelectedItem().toString() != "Any"){
                browseState.getSearchState().setYear(yearBox.getSelectedItem().toString());
            }
            System.out.println(browseState.getSearchState().getYear());
        }

    }

}

