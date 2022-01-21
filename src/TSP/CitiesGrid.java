package TSP;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class CitiesGrid 
{
	int numberOfCities = 5;
	ArrayList<Point> citiesCoordinates = new ArrayList<Point>();
	Random generator = new Random();
	int gridSize[] = {600, 600};

	public CitiesGrid(int numberOfCities) 
	{
		this.numberOfCities = numberOfCities;
		for(int i = 0; i < numberOfCities; i++)
		{
				Point city = new Point(generator.nextInt(600),generator.nextInt(600));
				citiesCoordinates.add(i, city);
		}
	}
	public double calculateDistance(ArrayList<Integer> order)
	{//calculate the total distance between points in selected order
		double dist = 0;
		for(int i = 0; i < order.size(); i++)
		{
			int cityAIndex = order.get(i);
			int cityBIndex = order.get((i+1)%order.size());
			Point cityA = new Point(citiesCoordinates.get(cityAIndex).x, citiesCoordinates.get(cityAIndex).y);
			Point cityB = new Point(citiesCoordinates.get(cityBIndex).x, citiesCoordinates.get(cityBIndex).y);
			dist += cityA.distance(cityB);
		}
		
		return dist;
	}
	
}
