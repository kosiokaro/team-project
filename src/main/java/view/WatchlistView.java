package view;

import interface_adapter.home.HomeState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;


public class WatchlistView extends JPanel {
    public final String viewName = "WATCHLIST";
    private JLabel titleLabel;
    private JButton switchButton;
    private JButton homeButton;

    public WatchlistView() {
        this.setLayout(new BorderLayout());

        titleLabel = new JLabel("My Watchlist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        switchButton = new JButton("Go to Favorites View");
        homeButton = new JButton("Go back to homepage");

        switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(switchButton);
        buttonPanel.add(homeButton);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    public void setswitchtofavButtonListener(ActionListener listener) {
        switchButton.addActionListener(listener);
    }

    public void sethomeButtonListener(ActionListener listener) {homeButton.addActionListener(listener);}

    public String getViewName() {
        return viewName;
    }
}