package TSP;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainInterface {

	public static void main(String[] args) 
	{
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					Algorithm ga = new Algorithm(5, 20);
				    JFrame frame = new JFrame("Generated path");
				    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				   // ga.getCities();
					frame.add(ga.getCities().getAnimation());
				    frame.setSize(600, 600);
				    frame.setLocationRelativeTo(null);
				    frame.setVisible(true);

				}
			});

}}
