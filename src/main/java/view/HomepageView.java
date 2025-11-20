package view;

import interface_adapter.home.HomeState;
import interface_adapter.home.HomeViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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

    public HomepageView(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        homeViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Homepage", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);


        JPanel welcomePanel = new JPanel(new BorderLayout());

        welcomeLabel = new JLabel("Hello, " + username + "!");
        welcomePanel.add(welcomeLabel, BorderLayout.WEST);

        logoutButton = new JButton("Logout");
        welcomePanel.add(logoutButton, BorderLayout.EAST);

        topPanel.add(welcomePanel);

        this.add(topPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        browseButton = new JButton("Browse titles");
        viewButton = new JButton("View Watchlist");
        favoritesButton = new JButton("View Favorites");

        browseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        favoritesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(browseButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(favoritesButton);

        this.add(buttonPanel, BorderLayout.CENTER);
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
        //System.out.println("propertyChange username: " + username);

    }

    public void propertyChange(PropertyChangeEvent evt) {
        HomeState state = (HomeState) evt.getNewValue();
        if(!state.getUsername().equals("")) {
            setUsername(state.getUsername());
        }

        //System.out.println("propertyChange username: " + state.getUsername());
    }


    public String getViewName() {
        return viewName;
    }
}
