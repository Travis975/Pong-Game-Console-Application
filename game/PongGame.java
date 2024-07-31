package game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PongGame {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new PongGame().createAndShowGUI();
	            }
	        });
	    }

	    private void createAndShowGUI() {
	        JFrame frame = new JFrame("Pong Game");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(815, 635);

	        // Shared top scores list
	        List<Integer> topScores = new ArrayList<>();

	        // Create the main menu panel
	        MenuPanel mainMenuPanel = new MenuPanel(frame, topScores);

	        // Add the main menu panel to the frame
	        frame.add(mainMenuPanel);
	        frame.setVisible(true);
	    }
}
