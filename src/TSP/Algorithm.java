package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Algorithm
{
	private List<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>(); // initial population formed by a finite number of solutions. 
	private List<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>(); // every new population will be stored there
	int numberOfCities = 5;
	int numberOfSolutions = 10; // number of solutions in population
	int numberOfPopulations = 10; // number of needed populations to achieve the optimal goal
	private double fitness[]; // values of fitness functions for each solution
	private double probability[]; // 
	
	
	public Algorithm(int numberOfSolutions, int numberOfCities, int numberOfPopulations) 
	{

		this.numberOfCities = numberOfCities;
		CitiesGrid cities = new CitiesGrid(numberOfCities);
		this.numberOfSolutions = numberOfSolutions;
		this.numberOfPopulations = numberOfPopulations;
		this.fitness = new double[numberOfSolutions];
		this.probability = new double[numberOfSolutions];
		setPopulation();
		Selection(cities, population, newPopulation);
		/*for(int i = 1; i < numberOfPopulations; i++)
			{
				Selection(cities, newPopulation, newPopulation);
				System.out.print(newPopulation + " \n");
			}*/
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
	private void Selection(CitiesGrid cities, List<ArrayList<Integer>> population1, List<ArrayList<Integer>> newPopulation1) 
	{
		// this method is selecting solutions depending on values of fitness function using a roulette wheel selection algorithm.
		//Fitness function is inversely proportional to probability, so individuals who have low values of the distance
		//may have a high chance of being selected. 
		newPopulation1.clear();
		double sum = 0;
		//double sumOfProbabilities = 0;
		Random generator = new Random();
		for(int i = 0; i < population1.size(); i++)
			{
				fitness[i] = 1/(cities.calculateDistance(population1.get(i))); //calculate the fitness of a solution
				sum += fitness[i];
			}
		probability[0] = fitness[0]/sum;
		for(int i = 1; i < population1.size(); i++) probability[i] = probability[i-1] + (fitness[i] / sum); 
		for(int j = 0; j < population1.size(); j++)
		{
			double rand = generator.nextDouble();
			for(int i = 0; i < population1.size() ; i++)
				{
					if( rand < probability[i]) 
					{
						newPopulation1.add(population1.get(i));
						//System.out.print(rand+"\t" + i + "\t" + newPopulation.get(j) + " disc: "+ 1/fitness[j] + " P: "+ probability[j] + " \n");
						break;
					}
				}
		}
	}
}
