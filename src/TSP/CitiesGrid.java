package TSP;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class CitiesGrid 
{
	int numberOfCities = 5;
	int[] x = {170, 213, 840, 791, 291, 251, 728, 210, 305, 521};
	int[] y = {143, 206, 593, 236, 287, 420, 758, 599, 753, 785};
	//best = 0, 1, 4, 5, 7, 8, 9, 6, 2, 3
	ArrayList<Point> citiesCoordinates = new ArrayList<Point>();
	Random generator = new Random();
	int gridSize[] = {600, 600};
	ArrayList<Integer> order;
	double distance;
	CitiesGridAnimation animation;

	public CitiesGrid(int numberOfCities) 
	{
		this.numberOfCities = numberOfCities;
		for(int i = 0; i < numberOfCities; i++)
		{
				Point city = new Point(generator.nextInt(600),generator.nextInt(600));
			//Point city = new Point(x[i], y[i]);
				citiesCoordinates.add(i, city);
		}
		
	}
	public double calculateDistance(ArrayList<Integer> order)
	{//calculate the total distance between points in selected order
		animation = new CitiesGridAnimation(this);
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
	public ArrayList<Point> getCitiesCoordinates() {
		return citiesCoordinates;
	}
	public void setCitiesCoordinates(ArrayList<Point> citiesCoordinates) {
		this.citiesCoordinates = citiesCoordinates;
	}
	public int[] getGridSize() {
		return gridSize;
	}
	public void setGridSize(int[] gridSize) {
		this.gridSize = gridSize;
	}
	public int getNumberOfCities() {
		return numberOfCities;
	}
	public void setNumberOfCities(int numberOfCities) {
		this.numberOfCities = numberOfCities;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
		animation.repaint();
	}
	
	public ArrayList<Integer> getOrder() {
		return order;
	}
	public void setOrder(ArrayList<Integer> order) {
		this.order = order;
		animation.order = order;
		animation.repaint();
	}
	public CitiesGridAnimation getAnimation() {
		return animation;
	}
	public void setAnimation(CitiesGridAnimation animation) {
		this.animation = animation;
	}
	
}
