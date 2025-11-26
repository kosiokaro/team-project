package view;

import interface_adapter.browse.BrowseController;
import interface_adapter.browse.BrowseState;
import interface_adapter.browse.BrowseViewModel;
import use_case.browse.BrowseOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BrowseView extends JPanel  implements PropertyChangeListener, ActionListener {
    public static final Color TOPBAR_BACKGROUND_COLOR = new Color(50, 50, 50);
    public static final Color BACKGROUND_COLOR = new Color(3, 9, 78);
    public static final Color MOVIE_GRID_BACKGROUND_COLOR = new Color(40, 40, 40);

    public final String viewName = "BROWSE";
    private final JButton browseButton = new JButton("Browse");
    private final JTextField searchField = new JTextField(20);
    private final JComboBox<String> sortBox = new JComboBox<>(new String[]{
            "Rating ↑ (Ascending)",
            "Rating ↓ (Descending)"
    });

    private final JComboBox<String> yearBox = new JComboBox<>();

    private final JPanel gridPanel = new JPanel();

    private BrowseController controller;
    private final BrowseViewModel viewModel;
    private BrowseController browseController = null;


    public BrowseView(BrowseViewModel viewModel) {
        this.viewModel = viewModel;
        createUIComponents();
    }

    private void createUIComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(TOPBAR_BACKGROUND_COLOR);

        topBar.add(new JLabel("Search:"));
        topBar.add(searchField);

        topBar.add(new JLabel("Sort By:"));
        topBar.add(sortBox);

        topBar.add(new JLabel("Year:"));
        yearBox.addItem("Any");
        for (int y = 2025; y >= 1950; y--) {
            yearBox.addItem(String.valueOf(y));
        }
        topBar.add(yearBox);
        topBar.add(browseButton);

        add(topBar, BorderLayout.NORTH);

        gridPanel.setLayout(new GridLayout(0, 4, 20, 20));
        gridPanel.setBackground(MOVIE_GRID_BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        displayMovies();




    }

    public void displayMovies() {
        gridPanel.removeAll();

        for (int i = 0; i < 20; i++) {
            gridPanel.add(createMovieCard());
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createMovieCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(60, 60, 60));
        card.setPreferredSize(new Dimension(150, 250));

        JLabel poster = new JLabel("<Poster>");
        poster.setPreferredSize(new Dimension(120, 160));
        poster.setOpaque(true);
        poster.setBackground(Color.GRAY);

        JLabel title = new JLabel("m.title");
        JLabel rating = new JLabel("★ " + "m.rating");
        JLabel runtime = new JLabel("m.runtime" + " min");
        JLabel genres = new JLabel("m.genres");

        card.add(poster);
        card.add(title);
        card.add(rating);
        card.add(runtime);
        card.add(genres);

        return card;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void setBrowseController(BrowseController browseController) {this.browseController=browseController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
