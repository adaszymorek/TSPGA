package TSP;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CitiesGridAnimation extends JPanel{

	CitiesGrid cities;
	ArrayList<Integer> order;
	CitiesGridAnimation(CitiesGrid cities){
		this.cities = cities;
		this.order = cities.order;
	}
	 public void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    Graphics2D g2d = (Graphics2D) g;

		    Dimension size = getSize();
	        int w = size.width ;
	        int h = size.height;
		    for (int i = 0; i < cities.numberOfCities; i++) {
		        g2d.drawOval(cities.getCitiesCoordinates().get(i).x % w, cities.getCitiesCoordinates().get(i).y % h, 5, 5);
		        int cityAIndex = order.get(i);
				int cityBIndex = order.get((i+1) % order.size());
		        g2d.drawLine(cities.getCitiesCoordinates().get(cityAIndex).x, cities.getCitiesCoordinates().get(cityAIndex).y, cities.getCitiesCoordinates().get(cityBIndex).x, cities.getCitiesCoordinates().get(cityBIndex).y);
		        g.drawString("Distance: " + cities.distance , w - 300, h - 50);
		      }
		}
}
