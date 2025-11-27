package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FavoritesView extends JPanel {
    public final String viewName = "FAVORITES";
    private JLabel titleLabel;
    private JButton switchButton;
    private JButton homeButton;

    public FavoritesView() {
        this.setLayout(new BorderLayout());

        titleLabel = new JLabel("My Favorites", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        switchButton = new JButton("Go to Watchlist View");
        homeButton = new JButton("Go back to homepage");

        switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(switchButton);
        buttonPanel.add(homeButton);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    public void setswitchtowatchButtonListener(ActionListener listener) {
        switchButton.addActionListener(listener);
    }

    public void sethomeButtonListener(ActionListener listener) {homeButton.addActionListener(listener);}

    public String getViewName() {
        return viewName;
    }
}