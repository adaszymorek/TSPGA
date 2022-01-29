package TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


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
		newPopulation = rouletteSelection(cities, population);
		evolution(cities);
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
	private void mutation(List<Integer> order, double mutationRate) {
		Random rand = new Random();
		for(int i = 0; i < numberOfCities; i++) {
			if(rand.nextDouble() < mutationRate) {
				int indexA = rand.nextInt(numberOfCities);
				int indexB = rand.nextInt(numberOfCities);
				Collections.swap(order, indexA, indexB);
			}
		}
	}
	private List<ArrayList<Integer>> rouletteSelection(CitiesGrid cities, List<ArrayList<Integer>> population1) 
	{
		List<ArrayList<Integer>> newPopulation1 = new ArrayList<ArrayList<Integer>>();
		// this method is selecting solutions depending on values of fitness function using a roulette wheel selection algorithm.
		//Fitness function is inversely proportional to probability, so individuals who have low values of the distance
		//may have a high chance of being selected. 
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
						mutation(newPopulation1.get(j), 0.1);
						//System.out.print(rand+"\t" + i + "\t" + newPopulation1.get(j) + " disc: "+ 1/fitness[j] + " P: "+ probability[j] + " \n");
						break;
					}
				}
		}
		return newPopulation1;
	}
	private List<ArrayList<Integer>> selectParents(List<ArrayList<Integer>> population){
		List<ArrayList<Integer>> parents = new ArrayList<ArrayList<Integer>>(2);
		// get two random parents
				Random random = new Random();
				for(int i = 0; i < numberOfSolutions; i++) { 
					int random1 = random.nextInt(population.size());
					ArrayList<Integer> parent = population.get(random1);
					parents.add(parent);
					population.remove(random1);
				}
		return parents;
	}
	private void evolution(CitiesGrid cities) {
		for(int i = 0; i < numberOfPopulations; i++) {

			newPopulation = rouletteSelection(cities, newPopulation);
			List<ArrayList<Integer>> parents = selectParents(newPopulation);
			newPopulation.clear();
			for(int j = 0; j < (int)numberOfSolutions/2; j++) // did not check is number of solutions an odd number
			{
				List<ArrayList<Integer>> children = PMXcrossoverPopulation(parents.get(j), parents.get(j+1));
				newPopulation.add(children.get(0));
				newPopulation.add(children.get(1));
			}
			System.out.println(1/bestFitness());
		}
	}
	private double bestFitness()
	{
		double best = Arrays.stream(fitness).max().getAsDouble();
		return best;
	}
	//PMX 
		private ArrayList<ArrayList<Integer>> PMXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
		{
			Random random = new Random();
			int breakpoint = random.nextInt(numberOfCities);
			ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
			
			List<Integer> parent1Genome = new ArrayList<>(parent1);
		    List<Integer> parent2Genome = new ArrayList<>(parent2);
			
		    //child 1
		    for (int i = 0; i < breakpoint; i++) {
		        int newVal;
		        newVal = parent2Genome.get(i);
		        Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
		    }
		    children.add(new ArrayList<>(parent1Genome));
		    parent1Genome = parent1;
		    
		    //child 2
		    for (int i = breakpoint; i < numberOfCities; i++) {
		        int newVal;
		        newVal = parent1Genome.get(i);
		        Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
		    }
		    children.add(new ArrayList<>(parent2Genome));
		    
		    return children;
		}
		//OX
		private ArrayList<ArrayList<Integer>>  OXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
		{
			
			Random random = new Random();
			int number1 = random.nextInt(numberOfCities - 1);
			 int number2 = random.nextInt(numberOfCities);
			 int start = Math.min(number1, number2);
			 int end = Math.max(number1, number2);
			
			ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> child1 = new ArrayList<Integer>();
			ArrayList<Integer> child2 = new ArrayList<Integer>();
			
			child1.addAll(parent1.subList(start, end));
			child2.addAll(parent2.subList(start, end));
			
			int currentCityIndex = 0;
			int currentCityInTour1 = 0;
			int currentCityInTour2 = 0;
			for (int i = 0; i < numberOfCities ; i++) 
			{
				currentCityIndex = (end + i) % numberOfCities;
				currentCityInTour1 = parent1.get(currentCityIndex);
				currentCityInTour2 = parent2.get(currentCityIndex);
				if (!child1.contains(currentCityInTour2)) 
				{
					child1.add(currentCityInTour2);
				}
				if (!child2.contains(currentCityInTour1)) 
				{
					child2.add(currentCityInTour1);
				}
			}
			Collections.rotate(child1, start);
			Collections.rotate(child2, start);
			children.add(child1);
			children.add(child2);
			return children;
		}
		//CX
				private ArrayList<ArrayList<Integer>> CXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
				{
					Random random = new Random();
					int idx = random.nextInt(numberOfCities);
					ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
					int cycle = 1;
					
					ArrayList<Integer> child1 = new ArrayList<Integer>(parent1);
					ArrayList<Integer> child2 = new ArrayList<Integer>(parent2);
					
					final Set<Integer> visitedIndices = new HashSet<Integer>(numberOfCities);
					final List<Integer> indices = new ArrayList<Integer>(numberOfCities);

				    while (visitedIndices.size() < numberOfCities) {
			            indices.add(idx);
			            
			            int item = parent2.get(idx);
			            idx = parent1.indexOf(item);
			            
			            while (idx != indices.get(0)) {	              
			                indices.add(idx);	   
			                item = parent2.get(idx);	                
			                idx = parent1.indexOf(item);
			            }
			            if (cycle++ % 2 != 0) {
			                for (int i : indices) {
			                    Integer tmp = child1.get(i);
			                    child1.set(i, child2.get(i));
			                    child2.set(i, tmp);
			                }
			            }
			            visitedIndices.addAll(indices);
			            // find next starting index: last one + 1 until we find an unvisited index
			            idx = (indices.get(0) + 1) % numberOfCities;
			            while (visitedIndices.contains(idx) && visitedIndices.size() < numberOfCities) {
			                idx++;
			                if (idx >= numberOfCities) {
			                    idx = 0;
			                }
			            }
			            indices.clear();
				    }
				    children.add(child1);
				    children.add(child2);
				    
				    return children;
				}
}
