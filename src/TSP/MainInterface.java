package TSP;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
				    CitiesGridAnimation cite = new CitiesGridAnimation(ga.getCities());
					frame.add(cite);
				    frame.setSize(800, 800);
				    frame.setLocationRelativeTo(null);
				    frame.setVisible(true);

				    
					ExecutorService exec = Executors.newFixedThreadPool(1);
					exec.execute(cite);
					exec.shutdown();
				}
			});

}}
