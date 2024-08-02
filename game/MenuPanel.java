package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MenuPanel extends JPanel {
    private JButton startGameButton;
    private JButton recordsButton;
    private JButton exitButton;

    public MenuPanel(JFrame frame, List<Integer> topScores) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50, 50, 50, 50);

        // Create the "Start Game" button
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            if (startGameListener != null) {
                startGameListener.actionPerformed(e);
            }
        });

        // Create the "Records" button
        recordsButton = new JButton("Records");
        recordsButton.addActionListener(e -> {
            // Replace the main menu panel with the records panel
            RecordsPanel recordsPanel = new RecordsPanel(frame, topScores);
            frame.getContentPane().removeAll();
            frame.add(recordsPanel);
            frame.revalidate();
            frame.repaint();
        });

        // Create the "Exit" button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the menu panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startGameButton, gbc);
        gbc.gridy = 1;
        add(recordsButton, gbc);
        gbc.gridy = 2;
        add(exitButton, gbc);
    }

    private ActionListener startGameListener;

    public void setStartGameListener(ActionListener listener) {
        this.startGameListener = listener;
    }
}
