package TSP;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CitiesGridAnimation extends JPanel implements Runnable{

	int cityBIndex;
	int cityAIndex;
	
	int cityCIndeks;
	int cityDIndex;
	
	int cityEIndex;
	int cityFIndex;
	
	int cityGIndex;
	int cityHIndex;
	
	int cityIIndex;
	int cityJIndex;
	int i=1;
	
	CitiesGrid cities;
	ArrayList<Integer> order;
	public CitiesGridAnimation(CitiesGrid cities){
		super();
		this.cities = cities;
		this.order = cities.order;
		
	}
	@Override
	public void run()
	{
		while(true)
		{
			for (i = -1; i < cities.numberOfCities; i++) 
			{
		
				if(i>=0)
				{
				cityAIndex = order.get(i);
				cityBIndex = order.get((i+1) % order.size());
				}
				
				if(i>=1)
				{
				cityCIndeks = order.get(i-1);
				cityDIndex = order.get((i) % order.size());
				}
				
				if(i>=2)
				{
				cityEIndex = order.get(i-2);
				cityFIndex = order.get((i-1) % order.size());
				}
				
				if(i>=3)
				{
				cityGIndex = order.get(i-3);
				cityHIndex = order.get((i-2) % order.size());
				}
				
				if(i>=4)
				{
				cityIIndex = order.get(i-4);
				cityJIndex = order.get((i-3) % order.size());
				}
				
	
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		
			}
		}
	}
	
	
	 public void paintComponent(Graphics g) {
		    super.paintComponent(g);

		    Graphics2D g2d = (Graphics2D) g;
		    Dimension size = getSize();
	        int w = size.width ;
	        int h = size.height;
		    for (int i = 0; i < cities.numberOfCities; i++) 
		    {	    	
		        g2d.drawOval(cities.getCitiesCoordinates().get(i).x % w, cities.getCitiesCoordinates().get(i).y % h, 5, 5);
		        g.drawString("Distance: " + cities.distance , w - 300, h - 50);
		        
		    }
		       g2d.setStroke(new BasicStroke(4));
		       
		       if(i>=0)
			   {
		    	   g2d.drawLine(cities.getCitiesCoordinates().get(cityAIndex).x, cities.getCitiesCoordinates().get(cityAIndex).y, cities.getCitiesCoordinates().get(cityBIndex).x, cities.getCitiesCoordinates().get(cityBIndex).y);
			   }
		       
		       if (i>=1)
		       {
		    	   g2d.drawLine(cities.getCitiesCoordinates().get(cityCIndeks).x, cities.getCitiesCoordinates().get(cityCIndeks).y, cities.getCitiesCoordinates().get(cityDIndex).x, cities.getCitiesCoordinates().get(cityDIndex).y);
		       }
		       
		       if (i>=2)
		       {
		    	   g2d.drawLine(cities.getCitiesCoordinates().get(cityEIndex).x, cities.getCitiesCoordinates().get(cityEIndex).y, cities.getCitiesCoordinates().get(cityFIndex).x, cities.getCitiesCoordinates().get(cityFIndex).y);
		       }
		       
		       if (i>=3)
		       {
		    	   g2d.drawLine(cities.getCitiesCoordinates().get(cityGIndex).x, cities.getCitiesCoordinates().get(cityGIndex).y, cities.getCitiesCoordinates().get(cityHIndex).x, cities.getCitiesCoordinates().get(cityHIndex).y);
		       }
		       
		       if (i>=4)
		       {
		    	   g2d.drawLine(cities.getCitiesCoordinates().get(cityIIndex).x, cities.getCitiesCoordinates().get(cityIIndex).y, cities.getCitiesCoordinates().get(cityJIndex).x, cities.getCitiesCoordinates().get(cityJIndex).y);
		       }
		       repaint();
		}

	

}
