package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;

public class StartGame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGame window = new StartGame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartGame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Ã∞≥‘…ﬂAI");
		frame.setResizable(false);

		SnakePanel panel = new SnakePanel();
		new Thread(panel).start();

		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(100, 100, 200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
