package view;

import interface_adapter.browse.BrowseController;
import interface_adapter.browse.BrowseViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class BrowseView extends JPanel  implements PropertyChangeListener, ActionListener {
    public final String viewName = "BROWSE";
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox searchTypeSelect;
    private final BrowseViewModel viewModel;
    private BrowseController browseController = null;


    public BrowseView(BrowseViewModel viewModel) {
        this.viewModel = viewModel;
        createUIComponents();
    }

    private void createUIComponents() {

        this.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        searchField = new JTextField();
        searchButton = new JButton("Search");
        searchTypeSelect = new JComboBox();
        searchTypeSelect.setModel(new DefaultComboBoxModel(new String[] {"Movie", "Actor"}));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(searchTypeSelect);


        JPanel mediaDisplayPanel = new JPanel();
        mediaDisplayPanel.setLayout(new GridLayout(0, 4, 10, 10));

        JScrollPane scrollPane = new JScrollPane(mediaDisplayPanel);

        this.add(searchPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

    }
    public void BuildMovieDisplay(JPanel mediaDisplayPanel){
        for (int i = 0; i < 20; i++) { // Example: 20 movies
            mediaDisplayPanel.add(createMovieItemPanel(
                    viewModel.getState().getTitles().get(i),
                    viewModel.getState().getTitles().get(i),
                    viewModel.getState().getRunTimes().get(i).toString(),
                    Arrays.toString(viewModel.getState().getGenreIDS().get(i)),
                    viewModel.getState().getImages().get(i)
            ));
        }
    }

    public JPanel createMovieItemPanel(String movieName, String rating, String runtime, String genres,String poster_url) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

//        ImageIcon icon = new ImageIcon(new java.net.URL(poster_url));
//        Image img = icon.getImage();
//        Image scaledImg = img.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
//        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel imagePlaceholder = new JLabel();
        imagePlaceholder.setPreferredSize(new Dimension(150, 200));
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePlaceholder.setText(poster_url);

        JLabel nameLabel = new JLabel(movieName);
        JLabel ratingLabel = new JLabel("Rating: " + rating + " - Runtime: " + runtime);
        JLabel genresLabel = new JLabel("Genres: " + genres);

        itemPanel.add(imagePlaceholder);
        itemPanel.add(nameLabel);
        itemPanel.add(ratingLabel);
        itemPanel.add(genresLabel);

        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return itemPanel;
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
