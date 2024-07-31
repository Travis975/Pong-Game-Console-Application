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
    public MenuPanel(JFrame frame, List<Integer> topScores) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create the "Start Game" button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace the main menu panel with the game panel
                GamePanel gamePanel = new GamePanel(frame, topScores);
                frame.getContentPane().removeAll();
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
                gamePanel.requestFocusInWindow();
            }
        });

        // Create the "Records" button
        JButton recordsButton = new JButton("Records");
        recordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace the main menu panel with the records panel
                RecordsPanel recordsPanel = new RecordsPanel(frame, topScores);
                frame.getContentPane().removeAll();
                frame.add(recordsPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Create the "Exit" button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add buttons to the menu panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startGameButton, gbc);
        gbc.gridy = 1;
        add(recordsButton, gbc);
        gbc.gridy = 2;
        add(exitButton, gbc);
    }
}
