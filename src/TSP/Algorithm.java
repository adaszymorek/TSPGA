package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Algorithm
{
	List<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>(); // initial population formed by a finite
	// number of solutions. 
	List<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>();
	int numberOfCities = 5;
	int numberOfSolutions = 10; // number of solutions in population
	double fitness[];
	double probability[];
	
	
	public Algorithm(int numberOfSolutions, int numberOfCities) 
	{

		this.numberOfCities = numberOfCities;
		CitiesGrid cities = new CitiesGrid(numberOfCities);
		this.numberOfSolutions = numberOfSolutions;
		this.fitness = new double[numberOfSolutions];
		this.probability = new double[numberOfSolutions];
		setPopulation();
		Selection(cities);
	}
	
	private void setPopulation()
	{
		// this method is setting population with sequences of randomly sorted numbers that correspond to cities.
		for(int i = 0; i < numberOfSolutions; i++)
		{
			population.add(new ArrayList<Integer>()); 
			for(int j = 0; j < numberOfCities; j++) population.get(i).add(j);
			Collections.shuffle(population.get(i));
		}
	}
	private void Selection(CitiesGrid cities) 
	{
		double sum = 0;
		//double sumOfProbabilities = 0;
		Random generator = new Random();
		for(int i = 0; i < population.size(); i++)
			{
				fitness[i] = 1/(cities.calculateDistance(population.get(i))); //calculate the fitness of a solution
				sum += fitness[i];
			}
		probability[0] = fitness[0]/sum;
		for(int i = 1; i < population.size(); i++) probability[i] = probability[i-1] + (fitness[i] / sum); 
		for(int j = 0; j < population.size(); j++)
		{
			double rand = generator.nextDouble();
			for(int i = 0; i < population.size() ; i++)
				{
					if( rand < probability[i]) 
					{
						newPopulation.add(population.get(i));
						//System.out.print(rand+"\t" + i + "\t" + newPopulation.get(j) + " disc: "+ 1/fitness[j] + " P: "+ probability[j] + " \n");
						break;
					}
				}
		}
	}
}
