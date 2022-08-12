package ui;

import ui.components.button.BasicButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * A launch screen that displays when the application first starts.
 */
public class Launcher extends JFrame {
    private final CitySimulator simulator;

    /**
     * Create a new {@link Launcher}.
     *
     * @param simulator The associated {@link CitySimulator} instance.
     */
    public Launcher(CitySimulator simulator) {
        super("Mini City Launcher");
        this.simulator = simulator;
    }

    /**
     * Initialize the launcher's frame properties and add components.
     * This is not a blocking call. The launcher will call {@link CitySimulator#loadCity()} if the
     * user chooses to load a city, and {@link CitySimulator#createNewCity(String)} if the user chooses
     * to create a new city, and provides a name for it.
     */
    public void run() {
        setSize(600, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
        addComponents();
        setVisible(true);
        toFront();
    }

    /**
     * Add all child components to the launcher's frame.
     */
    private void addComponents() {
        setBackground();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        JButton newButton = new BasicButton("Create a new city");
        newButton.addActionListener(new NewCityListener());
        buttonPanel.add(newButton);

        JButton loadButton = new BasicButton("Load previous city");
        loadButton.addActionListener(new LoadCityListener());
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Set the background of the launcher's splash screen.
     */
    private void setBackground() {
        try {
            Image img = ImageIO.read(new File("./data/background.png"));
            img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles button press for the "new city" button.
     */
    private class NewCityListener implements ActionListener {
        /**
         * Get city name and create new city
         *
         * @param e The {@link ActionEvent} (button press).
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Enter city name");
            if (name != null) {
                simulator.createNewCity(name);
                dispose();
            }
        }
    }

    /**
     * Handles button press for the "load city" button.
     */
    private class LoadCityListener implements ActionListener {
        /**
         * Handle action (button press) for the load city button in a {@link Launcher}.
         * This will attempt to load the previous city in the simulator when pressed.
         *
         * @param e The {@link ActionEvent} (button press).
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                simulator.loadCity();
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to load previous city");
            }
        }
    }
}
