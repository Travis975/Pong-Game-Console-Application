package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class RecordsPanel extends JPanel {
    public RecordsPanel(JFrame frame, List<Integer> topScores) {
        setLayout(new BorderLayout());

        // Display the top scores
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("Top 3 Scores:\n");
        for (int i = 0; i < topScores.size(); i++) {
            textArea.append((i + 1) + ". " + topScores.get(i) + "\n");
        }

        // Add a back button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPanel mainMenuPanel = new MenuPanel(frame, topScores);
                frame.getContentPane().removeAll();
                frame.add(mainMenuPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}